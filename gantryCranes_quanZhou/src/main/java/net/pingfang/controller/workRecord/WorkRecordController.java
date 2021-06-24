package net.pingfang.controller.workRecord;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselContainerSeizeSeatService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.service.workRecord.RealTimeJobService;
import net.pingfang.service.workRecord.WorkRecordExportExcelService;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.DateUtil;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 作业记录Controller
 * @author Administrator
 * @since 2019-05-22
 *
 */
@Api("WorkRecord Api")
@RestController
@RequestMapping("/work")
public class WorkRecordController {
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private WorkRecordExportExcelService workRecordExportExcelService;
	@Autowired
	private VesselContainerService vesselContainerService;
	@Autowired
	private RealTimeJobService realTimeJobService;
	@Autowired
	private VesselContainerSeizeSeatService vesselContainerSeizeSeatService;
	
	private final static Logger logger = LoggerFactory.getLogger(WorkRecordController.class); 
	
	/**
	 * 查询作业记录列表
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询作业记录列表", notes="查询作业记录列表")
	@ApiParam(name = "workRecordVo", value = "作业信息JSON数据")
	@RequestMapping(value = "getWorkRecordList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getWorkRecordList"})
	public Result<Object> getWorkRecordList(@RequestBody WorkRecordVo workRecordVo) {
		return ResultUtil.success(workRecordService.getAllWorkRecordList(workRecordVo));
	}
	
	/**
	 * 删除作业记录
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="删除作业记录", notes="删除作业记录")
	@ApiParam(name = "id", value = "作业记录编号")
	@DeleteMapping("/deleteWorkRecordBySeqNo")
	@RequiresPermissions(value = {"deleteWorkRecordBySeqNo"})
	public Result<Object> deleteWorkRecordBySeqNo(@RequestParam("id") int id) {
		//根据seqNo任务编号获取所有作业记录
//		int count = workRecordService.getCountWorkRecordBySeqNo(seqNo);
//		if(count >0) {
//			//删除作业记录
//			count = workRecordService.deleteWorkRecordBySeqNo(seqNo);
//			if(count >0) {
//				return ResultUtil.success("seqNo");
//			}else {
//				logger.error(seqNo+"删除作业记录失败！");
//				return ResultUtil.error(1, seqNo+"删除作业记录失败！");
//			}
//		}else {
//			logger.info(seqNo+"作业记录不存在！");
//			return ResultUtil.error(1, seqNo+"作业记录不存在！");
//		}
		
		//删除作业记录
		int count = workRecordService.deleteWorkRecordById(id);
		if(count >0) {
			return ResultUtil.success("seqNo");
		}else {
			logger.error(id+"删除作业记录失败！");
			return ResultUtil.error(1, id+"删除作业记录失败！");
		}
	}
	
	/**
	 *查询作业记录详情
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询作业记录详情", notes="查询作业记录详情")
	@ApiParam(name = "id", value = "作业记录ID")
	@GetMapping("/getWorkRecordById")
	@RequiresPermissions(value = {"getWorkRecordById"})
	public Result<Object> getWorkRecordById(Integer id) {
		if(null !=id && id >0) {
			//根据id查询作业记录信息
			WorkRecordVo workRecord = workRecordService.getWorkRecordById(id);
			if(null !=workRecord) {
				return ResultUtil.success(workRecord);
			}else {
				logger.error("根据+id+查询作业记录信息失败！");
				return ResultUtil.error(1, "根据+id+查询作业记录信息失败！");
			}
		}else {
			logger.info("id不能为空并且要大于0！");
			return ResultUtil.error(1,"id不能为空并且要大于0！");
		}
	}
	
	/**
	 * 更新已理货数据
	 * 已理货数据二次提交更新
	 * 保存按钮
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="更新已理货数据", notes="更新已理货数据")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@PostMapping("/updateWorkRecordById")
	@RequiresPermissions(value = {"updateWorkRecordById"})
	public Result<Object> updateWorkRecordById(@RequestBody WorkRecordVo workRecordVo) {
		int count = 0;
		if(null !=workRecordVo) {
			//把箱号字母转成大写
			workRecordVo.setUpdateContaid(workRecordVo.getUpdateContaid().toUpperCase());
			
			int id = workRecordVo.getId();
			workRecordVo.setContaid(workRecordVo.getUpdateContaid());
			boolean isOk = true;
			//检查箱号是否有重复
			List<WorkRecordVo> list = workRecordService.getRecord(workRecordVo);
			if(null !=list && list.size() >0) {
				for(int i=0; i<list.size(); i++) {
					if(id !=list.get(i).getId()) {
						isOk = false;
						break;
					}
				}
			}
			if(isOk) {
				//获取BAY
				String bayInfo = workRecordVo.getBayInfo();
				//判断BAY的长度是否有7位，如果有7位则判断该BAY位是否有被其他箱占用
				if(null !=bayInfo && 7 == bayInfo.length()) {
					count = realTimeJobService.getCountBay(workRecordVo);
					if(count >0) {
						isOk = false;
					}else {
						//查询占位箱
						VesselContainerVo vc = new VesselContainerVo();
						vc.setVesselNumber(workRecordVo.getVesselNumber());
						vc.setStdBay(bayInfo.substring(0,3));
						vc.setStdRow(bayInfo.substring(3,5));
						vc.setStdTier(bayInfo.substring(5));
						//查询当前位置是否有占位箱		
						count = vesselContainerSeizeSeatService.getCountContainerSeizeSeat(vc);
						if(count >0) {
							isOk = false;
						}
					}
				}
				if(isOk) {
					count = workRecordService.updateWorkRecordById(workRecordVo);
					if(count >0) {
						//调用亮哥的接口同步BAY位（2020-09-11）
						//更新码头数据库表BAY(2020-04-15)
						if(7 == bayInfo.length()) {
							logger.info("更新码头数据库表BAY:"+bayInfo);
							VesselContainerVo vesselContainer = new VesselContainerVo();
							vesselContainer.setContainerNumber(workRecordVo.getUpdateContaid());
							vesselContainer.setVesselNumber(workRecordVo.getVesselNumber());
							//vesselContainer.setJobType(""+workRecordVo.getWorkType());
							vesselContainer.setStdBay(bayInfo.substring(0,3));
							vesselContainer.setStdRow(bayInfo.substring(3,5));
							vesselContainer.setStdTier(bayInfo.substring(5));
							vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
						}else {
							logger.info("实装BAY:"+bayInfo+"没有7位数，不更新码头数据库表BAY");
						}
						return ResultUtil.success(workRecordVo.getId());
					}else {
						logger.error(workRecordVo.getId()+"更新已理货数据失败！");
						logger.error(JsonUtil.javaToJsonStr(workRecordVo));
						return ResultUtil.error(1, workRecordVo.getId()+"更新已理货数据失败！");
					}
				}else {
					logger.error("BAY位："+bayInfo+"已经被占用！");
					logger.error(JsonUtil.javaToJsonStr(workRecordVo));
					return ResultUtil.error(1, "BAY位："+bayInfo+"已经被占用！");
				}
				
			}else {
				logger.error(workRecordVo.getUpdateContaid()+" 箱号已经存在！");
				logger.error(JsonUtil.javaToJsonStr(workRecordVo));
				return ResultUtil.error(1, workRecordVo.getUpdateContaid()+" 箱号已经存在！");
			}
			
		}else{
			logger.error("WorkRecordVo入参解析出错！");
			return ResultUtil.error(1, "WorkRecordVo入参解析出错！");
		}		
	}
	
	/**
	 * exportExcel历史作业记录
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value="Export Excel所有作业记录", notes="Export Excel所有作业记录")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@RequestMapping(value = "exportExcelWorkRecord", method = RequestMethod.GET)
	@RequiresPermissions(value = {"exportExcelWorkRecord"})
	public void exportExcelWorkRecord(HttpServletResponse response, WorkRecordVo workRecordVo) throws UnsupportedEncodingException {
		if(StringUtils.isEmpty(workRecordVo.getVesselCode())||workRecordVo.getWorkType()<0){
			return;
		}
		//设置Http响应头告诉浏览器下载这个附件
		response.setContentType("text/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Disposition","attachment;filename="+URLEncoder.encode("workrecord" + System.currentTimeMillis() + ".xml","UTF-8"));
		workRecordExportExcelService.exportExcelWorkRecord(response, workRecordVo);
	}
	
	/**
	 *统计作业数据
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="统计作业数据", notes="统计作业数据")
	@ApiParam(name = "workRecordVo", value = "作业信息JSON数据")
	@RequestMapping(value = "getWorkRecordStatistics", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getWorkRecordStatistics"})
	public Result<Object> getWorkRecordStatistics(@RequestBody WorkRecordVo workRecordVo){
		return ResultUtil.success(workRecordService.getWorkRecordStatistics(workRecordVo));
	}
	
	/**
	 * 查询已理货数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询已理货数据", notes="查询已理货数据")
	@ApiParam(name = "craneNumList", value = "作业信息JSON数据")
	@RequestMapping(value = "getAlreadyWorkRecordList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getAlreadyWorkRecordList"})
	public Result<Object> getAlreadyWorkRecordList(@RequestBody List<CranePreparationVo> cranePreparationList) {
		return ResultUtil.success(workRecordService.getAlreadyWorkRecordList(cranePreparationList,1));
	}
	/**
	 * 查询未理货数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询已理货数据", notes="查询已理货数据")
	@ApiParam(name = "craneNumList", value = "作业信息JSON数据")
	@RequestMapping(value = "getNoTallyWorkRecordList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getNoTallyWorkRecordList"})
	public Result<Object> getNoTallyWorkRecordList(@RequestBody List<CranePreparationVo> cranePreparationList) {
		return ResultUtil.success(workRecordService.getAlreadyWorkRecordList(cranePreparationList,0));
	}
	/**
	 * 查询异常数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询异常数据", notes="查询异常数据")
	@ApiParam(name = "cranePreparationList", value = "作业信息JSON数据")
	@RequestMapping(value = "getAlreadyWorkRecordListError", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getAlreadyWorkRecordListError"})
	public Result<Object> getAlreadyWorkRecordListError(@RequestBody List<CranePreparationVo> cranePreparationList) {
		return ResultUtil.success(workRecordService.getAlreadyWorkRecordList(cranePreparationList,10));
	}
	/**
	 *查询已理货数据详情
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="根据seqNo查询已理货数据信息", notes="根据seqNo查询已理货数据信息")
	@ApiParam(name = "id", value = "作业记录ID")
	@PutMapping("/getAlreadyWorkRecordById")
	@RequiresPermissions(value = {"getAlreadyWorkRecordById"})
	public Result<Object> getAlreadyWorkRecordById(@RequestBody WorkRecordVo workRecord) {
		if(null !=workRecord) {
			//根据id查询作业记录信息
			workRecord = workRecordService.getAlreadyWorkRecordById(workRecord);
			if(null !=workRecord && null !=workRecord.getSeqNo()) {
				return ResultUtil.success(workRecord);
			}else {
				logger.error("查询已理货数据详情失败！");
				return ResultUtil.error(1, "查询已理货数据详情失败！");
			}
		}else {
			logger.info("参数不能为空！");
			return ResultUtil.error(1,"参数不能为空！");
		}
	}

	
	/**
	 * 获取 装货港，卸货港，目的港
	 * @param vesselCode   船舶代码
	 * @param vesselNumber 船舶艘次号
	 * @return
	 */
	@ApiOperation(value="获取 装货港，卸货港，目的港", notes="获取 装货港，卸货港，目的港")
	@ApiParam(name = "VesselContainerVo", value = "JSON数据")
	@PutMapping("/getPortOrigin")
	@RequiresPermissions(value = {"getPortOrigin"})
	public Result<Object> getPortOrigin(@RequestBody VesselContainerVo vesselContainer) {
		if(null !=vesselContainer) {
			//作业类型(说明：'DS'为'卸船', 'DD'为'直提', 'LD'为'装船', 'DL'为'直装', 'LN'为'捣卸', 'RS'为'捣装', 'SH'为'搬移', 'DT'为'中转不落地')
			if("0".equals(vesselContainer.getJobType())) {
				vesselContainer.setJobType("LD");
			}else if("1".equals(vesselContainer.getJobType())) {
				vesselContainer.setJobType("DS");
			}
			//根据id查询作业记录信息
			VesselContainerVo vc = workRecordService.getVesselContainer(vesselContainer);
			if(null !=vc) {
				return ResultUtil.success(vc);
			}else {
				logger.error("数据库里没有相应的装货港，卸货港，目的港");
				return ResultUtil.error(1, "数据库里没有相应的装货港，卸货港，目的港");
			}
		}else {
			logger.info("参数不正确！");
			return ResultUtil.error(1,"参数不正确！");
		}
	}
	
	/**
	 * 新增历史作业数据
	 * 
	 * 1、车道内->海侧（装船）车道号：正常车道   作业类型：0
	 * 2、车道内->海侧（卸船）车道号：正常车道   作业类型：1
     * 3、海侧->车道外（舱盖板或倒箱）车道号：10   作业类型：4
   	 * 4、车道外->海侧（舱盖板或倒箱）车道号：0   作业类型：5
	 * 5、海侧->海侧（舱内翻倒）车道号：0   作业类型：3
	 * 6、车道内->车道外（）车道号：10   作业类型：8
	 * 7、车道外->车道内（）7车道号：具体车道号   作业类型：7
	 * 8、车道内->车道内（寄桥）车道号：作业车道号  作业类型：9
	 * 9、车道外->车道外（） 车道号：10   作业类型：6
	 * 10、未知作业类型 作业类型2
	 * 11、翻捣柜(装船)作业类型 作业类型12
	 * 12、翻捣柜(卸船)作业类型 作业类型13
	 * 13、溢短(卸船)作业类型 作业类型14
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="新增历史作业数据", notes="新增历史作业数据")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@PostMapping("/insertHistoricalWorkRecord")
	@RequiresPermissions(value = {"insertHistoricalWorkRecord"})
	public Result<Object> insertHistoricalWorkRecord(@RequestBody List<WorkRecordVo> list) {
		int count = 0;
		String msg = "";
		if(null !=list && list.size() >0) {
			boolean isOk = true;
			WorkRecordVo workRecordVo = null;
			String voyageNo = null;
			for(int i=0; i<list.size(); i++) {
				//把箱号字母转成大写
				list.get(i).setUpdateContaid(list.get(i).getUpdateContaid().toUpperCase());
				
				workRecordVo = list.get(i);
				VesselContainerVo vesselContainer = new VesselContainerVo();
				vesselContainer.setContainerNumber(workRecordVo.getUpdateContaid());
				vesselContainer.setVesselNumber(workRecordVo.getVesselNumber());
				vesselContainer.setJobType(""+workRecordVo.getWorkType());
				
				workRecordVo.setContaid(workRecordVo.getUpdateContaid());
				//查询该箱是否已经存在
				int countRecord = workRecordService.getCountRecord(workRecordVo);
				if(countRecord >0) {
					isOk = false;
					msg = workRecordVo.getUpdateContaid()+" 箱号已经存在！";
					break;
				}else {
					//2020-07-29
					//获取BAY
					String bayInfo = workRecordVo.getBayInfo();
					//判断BAY的长度是否有7位，如果有7位则判断该BAY位是否有被其他箱占用
					if(null !=bayInfo && 7 == bayInfo.length()) {
						count = realTimeJobService.getCountBay(workRecordVo);
						if(count >0) {
							isOk = false;
							msg = "BAY位："+bayInfo+"已经被占用！";
							break;
						}else {
							//查询占位箱
							VesselContainerVo vc = new VesselContainerVo();
							vc.setVesselNumber(workRecordVo.getVesselNumber());
							vc.setStdBay(bayInfo.substring(0,3));
							vc.setStdRow(bayInfo.substring(3,5));
							vc.setStdTier(bayInfo.substring(5));
							//查询当前位置是否有占位箱		
							count = vesselContainerSeizeSeatService.getCountContainerSeizeSeat(vc);
							if(count >0) {
								isOk = false;
								msg = "BAY位："+bayInfo+"已经被占用！";
								break;
							}
						}
					}
					
					//只有装船和卸船作业才比对舱单
					if (0 == workRecordVo.getWorkType() || 1 == workRecordVo.getWorkType()) {
						//比对舱单
						isOk = vesselContainerService.booleanVesselContainer(vesselContainer);
					}
					if(!isOk) {
						msg = workRecordVo.getUpdateContaid()+" 箱号比对舱单失败！";
						break;
					}else {
						//设置箱属性
						this.setWorkRecord(list.get(i), vesselContainer);
						voyageNo = vesselContainer.getVoyageNo();
					}
				}				
				String nict = "NICT";
				list.get(i).setSeqNo(nict+DateUtil.getDate("yyyyMMddHHmmsssss"));
				list.get(i).setAreaNum(nict);
				list.get(i).setPassTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
				list.get(i).setContaid(workRecordVo.getUpdateContaid());
				list.get(i).setTopPlate(workRecordVo.getUpdateTopPlate());
				list.get(i).setState("1");//设置状态为理货成功
			}
			if(isOk) {
				//插入作业记录
				count = workRecordService.insertWorkRecordList(list);
				if(count >0) {
					for(WorkRecordVo wr : list) {	
						//更新外理数据库OCR表
						msg = realTimeJobService.addOrUpdateWlData(wr,0);
						/*
						 * 2021-01-15
						 * 
						VesselContainerVo vesselContainer = new VesselContainerVo();
						vesselContainer.setContainerNumber(wr.getUpdateContaid());
						vesselContainer.setVesselNumber(wr.getVesselNumber());
						vesselContainer.setJobType(""+wr.getWorkType());
						
						//更新外理数据库OCR表
						realTimeJobService.addOrUpdateWlData(wr,0);
						//更新码头数据库表BAY(2020-04-15)
						String bayInfo = wr.getBayInfo();
						if(7 == bayInfo.length()) {
							logger.info("更新码头数据库表BAY:"+bayInfo);
							vesselContainer.setStdBay(bayInfo.substring(0,3));
							vesselContainer.setStdRow(bayInfo.substring(3,5));
							vesselContainer.setStdTier(bayInfo.substring(5));
							vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
						}else {
							logger.info("实装BAY:"+bayInfo+"没有7位数，不更新码头数据库表BAY");
						}
						*/
						wr.setVoyageNo(voyageNo);
						//MQ发送箱号信息
						realTimeJobService.mqSendToContainerid(wr);
					}					
					return ResultUtil.success(workRecordVo.getSeqNo());
				}else {
					msg = workRecordVo.getSeqNo()+"新增作业记录失败！";
					logger.error(msg);
					return ResultUtil.error(1, msg);
				}	
			}else {
				logger.error(msg);
				logger.error(JsonUtil.javaToJsonStr(workRecordVo));
				return ResultUtil.error(1, msg);
			}
		}else{
			logger.error("workJson入参解析出错！");
			return ResultUtil.error(count, "workJson入参解析出错！");
		}		
	}
	
	/**
	 * 设置箱属性
	 * 
	 * vesselPosition;			//预配BAY
	 * portLoading;				//装货港
	 * portDischarge;			//卸货港
	 * portDestination;			//目的港
	 * stuffingStatus;			//空重状态 重（F，full）,重（E，empty）
	 * dangerSigns;  			//危险标志（0为非危险品，1为危险品）
	 * String dangerClass;				//危险类型
	 * containerClass;			//箱类型（危品箱，普通箱，冷藏箱）
	 * 
	 * @param workRecord
	 * @param vesselContainer
	 */
	private void setWorkRecord(WorkRecordVo workRecord, VesselContainerVo vesselContainer) {
		workRecord.setVesselPosition(vesselContainer.getVesselPosition());
		//装货港
		workRecord.setPortLoading(vesselContainer.getPortLoading());
		//卸货港
		workRecord.setPortDischarge(vesselContainer.getPortDischarge());
		//目的港
		workRecord.setPortDestination(vesselContainer.getPortDestination());
		//空重状态 重（F，full）,重（E，empty）
		workRecord.setStuffingStatus(vesselContainer.getStuffingStatus());
		//危险标志（0为非危险品，1为危险品）
		workRecord.setDangerSigns(vesselContainer.getDangerSigns());
		//危险类型
		workRecord.setDangerClass(vesselContainer.getDangerClass());
		//箱类型（危品箱，普通箱，冷藏箱）
		workRecord.setContainerClass(vesselContainer.getContainerClass());
	}
}
