package net.pingfang.controller.huangPu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import net.pingfang.service.huangPu.HpWorkRecordService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.service.workRecord.RealTimeJobService;
import net.pingfang.service.workRecord.WorkRecordExportExcelService;
import net.pingfang.utils.DateUtil;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("HpWorkRecord Api")
@RestController
@RequestMapping("/hpWork")
public class HpWorkRecordController {
	
	@Autowired
	private HpWorkRecordService hpWorkRecordService;
	
	@Autowired
	private VesselContainerService vesselContainerService;
	@Autowired
	private RealTimeJobService realTimeJobService;
	@Autowired
	private WorkRecordExportExcelService workRecordExportExcelService;
	
	private final static String ONE = "1";
	
	private final static Logger logger = LoggerFactory.getLogger(HpWorkRecordController.class);
	
	/**
	 * exportExcel历史作业记录
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value="Export Excel所有黄埔门机作业记录", notes="Export Excel所有黄埔门机作业记录")
	@ApiParam(name = "HpWorkJson", value = "黄埔门机作业信息JSON数据")
	@RequestMapping(value = "exportExcelHpWorkRecord", method = RequestMethod.GET)
	@RequiresPermissions(value = {"exportExcelHpWorkRecord"})
	public void exportExcelHpWorkRecord(HttpServletResponse response, WorkRecordVo workRecordVo) throws UnsupportedEncodingException {
		//设置Http响应头告诉浏览器下载这个附件
		response.setContentType("application/vnd..ms-excel");
        response.setHeader("content-Disposition","attachment;filename="+URLEncoder.encode("历史黄埔门机作业数据" + System.currentTimeMillis() + ".xls","utf-8"));
        workRecordExportExcelService.exportExcelHpWorkRecord(response, workRecordVo);
	}

	
	/**
	 * 查询黄埔门机作业记录列表
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询黄埔门机作业记录列表", notes="查询黄埔门机作业记录列表")
	@ApiParam(name = "workRecordVo", value = "作业信息JSON数据")
	@RequestMapping(value = "getHpWorkRecordList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getHpWorkRecordList"})
	public Result<Object> getHpWorkRecordList(@RequestBody WorkRecordVo workRecordVo) {
		return ResultUtil.success(hpWorkRecordService.getHpAllWorkRecordList(workRecordVo));
	}
	
	/**
	 *查询黄埔门机作业记录详情
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询黄埔门机作业记录详情", notes="查询黄埔门机作业记录详情")
	@ApiParam(name = "id", value = "作业记录ID")
	@GetMapping("/getHpWorkRecordById")
	@RequiresPermissions(value = {"getHpWorkRecordById"})
	public Result<Object> getHpWorkRecordById(Integer id) {
		if(null !=id && id >0) {
			//根据id查询作业记录信息
			WorkRecordVo workRecord = hpWorkRecordService.getHpWorkRecordById(id);
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
	 * 查询黄埔实时作业数据
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询黄埔实时作业数据", notes="查询黄埔实时作业数据")
	@ApiParam(name = "WorkRecordVo", value = "实时作业JSON数据")
	@PostMapping("/getHpRealTimeWorkRecordList")
	@RequiresPermissions(value = {"getHpRealTimeWorkRecordList"})
	public Result<Object> getHpRealTimeWorkRecordList(@RequestBody List<CranePreparationVo> cranePreparationList, @RequestParam("state") int state) {
		return ResultUtil.success(hpWorkRecordService.getHpRealTimeWorkRecordList(cranePreparationList, state));
	}
	
	/**
	 * 根据id查询黄埔实时作业数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询黄埔实时作业数据", notes="查询黄埔实时作业数据")
	@ApiParam(name = "id", value = "实时作业id数据")
	@GetMapping("/getHpAlreadyWorkRecordById")
	@RequiresPermissions(value = {"getHpAlreadyWorkRecordById"})
	public Result<Object> getHpAlreadyWorkRecordById(@RequestParam("id") int id) {
		return ResultUtil.success(hpWorkRecordService.getHpAlreadyWorkRecordById(id));
	}
	
	
	/**
	 *门机录入系统操作（只有作业类型为0和1会比对舱单）
	 *
	 * 0、车道内->海侧（装船）车道号：正常车道   作业类型：0
	 * 1、车道内->海侧（卸船）车道号：正常车道   作业类型：1
	 * 2、未知作业类型 作业类型：2
	 * 3、海侧->海侧（舱内翻倒）车道号：0   作业类型：3
     * 4、海侧->车道外（舱盖板或倒箱）车道号：10   作业类型：4
   	 * 5、车道外->海侧（舱盖板或倒箱）车道号：0   作业类型：5
   	 * 6、车道外->车道外（） 车道号：10   作业类型：6
	 * 7、车道外->车道内（）7车道号：具体车道号   作业类型：7
	 * 8、车道内->车道外（）车道号：10   作业类型：8
	 * 9、车道内->车道内（寄桥装船）车道号：作业车道号  作业类型：9
	 * 10、作废-> 作业类型：10
	 * 11、船上->车道内（寄桥卸船）车道号：作业车道号  作业类型：11
	 * 12、翻捣柜(装船)作业类型 作业类型：12
	 * 13、翻捣柜(卸船)作业类型 作业类型：13
	 * 14、溢短(卸船)作业类型 作业类型：14
	 * 15、溢箱()作业类型 作业类型：15
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="门机数据录入系统", notes="门机数据录入系统")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@PostMapping("/inputHpWorkRecord")
	@RequiresPermissions(value = {"inputHpWorkRecord"})
	public Result<Object> inputHpWorkRecord(@RequestBody WorkRecordVo workRecordVo) {
		int count = 0;
		if(null !=workRecordVo) {
			//把箱号字母转成大写
			workRecordVo.setUpdateContaid(workRecordVo.getUpdateContaid().toUpperCase());
			
			VesselContainerVo vesselContainer = new VesselContainerVo();
			vesselContainer.setContainerNumber(workRecordVo.getUpdateContaid());
			vesselContainer.setVesselNumber(workRecordVo.getVesselNumber());
			vesselContainer.setJobType(""+workRecordVo.getWorkType());
			
			int id = workRecordVo.getId();
			workRecordVo.setContaid(workRecordVo.getUpdateContaid());
			boolean isOk = true;
			//检查箱号是否有重复
			List<WorkRecordVo> list = hpWorkRecordService.getHpRecord(workRecordVo);
			if(null !=list && list.size() >0) {
				for(int i=0; i<list.size(); i++) {
					if(id !=list.get(i).getId()) {
						isOk = false;
						break;
					}
				}
			}
			if(isOk) {
				//只有装船和卸船作业才比对舱单
				if (0 == workRecordVo.getWorkType() || 1 == workRecordVo.getWorkType()) {
					//比对舱单
					isOk = vesselContainerService.booleanVesselContainer(vesselContainer);
				}
				if(isOk) {
					//设置箱属性
					this.setWorkRecord(workRecordVo, vesselContainer);
					workRecordVo.setState(ONE);//设置状态为理货成功
					//录入舱单
					count = hpWorkRecordService.updateHpWorkRecord(workRecordVo);
					if(count >0) {
						//更新外理数据库OCR表
						realTimeJobService.addOrUpdateWlData(workRecordVo,0);
						return ResultUtil.success(workRecordVo.getId());
					}else {
						logger.error(workRecordVo.getId()+"更新门机已理货数据！");
						logger.error(JsonUtil.javaToJsonStr(workRecordVo));
						return ResultUtil.error(1, workRecordVo.getId()+"更新门机已理货数据失败！");
					}
				}else {
					logger.error("门机比对舱单失败！");
					logger.error(JsonUtil.javaToJsonStr(workRecordVo));
					return ResultUtil.error(1, "门机比对舱单失败！");
				}
			}else {
				logger.error(workRecordVo.getUpdateContaid()+" 门机箱号已经存在！");
				logger.error(JsonUtil.javaToJsonStr(workRecordVo));
				return ResultUtil.error(1, workRecordVo.getUpdateContaid()+" 门机箱号已经存在！");
			}			
		}else{
			logger.error("WorkRecordVo门机入参解析出错！");
			return ResultUtil.error(1, "WorkRecordVo门机入参解析出错！");
		}		
	}
	
	
	/**
	 * 保存门机理货数据
	 * 已理货数据二次提交更新
	 * 保存按钮
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="保存门机理货数据", notes="保存门机理货数据")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@PostMapping("/saveHpWorkRecordById")
	@RequiresPermissions(value = {"saveHpWorkRecordById"})
	public Result<Object> saveHpWorkRecordById(@RequestBody WorkRecordVo workRecordVo) {
		int count = 0;
		if(null !=workRecordVo) {
			//把箱号字母转成大写
			workRecordVo.setUpdateContaid(workRecordVo.getUpdateContaid().toUpperCase());
			
			int id = workRecordVo.getId();
			workRecordVo.setContaid(workRecordVo.getUpdateContaid());
			boolean isOk = true;
			//检查箱号是否有重复
			List<WorkRecordVo> list = hpWorkRecordService.getHpRecord(workRecordVo);
			if(null !=list && list.size() >0) {
				for(int i=0; i<list.size(); i++) {
					if(id !=list.get(i).getId()) {
						isOk = false;
						break;
					}
				}
			}
			if(isOk) {
				count = hpWorkRecordService.updateHpWorkRecord(workRecordVo);
				if(count >0) {
					return ResultUtil.success(workRecordVo.getId());
				}else {
					logger.error(workRecordVo.getId()+"保存理货数据失败！");
					logger.error(JsonUtil.javaToJsonStr(workRecordVo));
					return ResultUtil.error(1, workRecordVo.getId()+"保存理货数据失败！");
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
	 * 门机作业数据作废（更新状态）
	 * 状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="门机作业数据作废", notes="门机作业数据作废")
	@ApiParam(name = "WorkRecordVo", value = "门机作业信息JSON数据")
	@RequiresPermissions(value = {"hpWorkRecordToVoid"})
	@PostMapping("/hpWorkRecordToVoid")
	public Result<Object> hpWorkRecordToVoid(@RequestBody WorkRecordVo workRecordVo) {
		int count = 0;
		if(null !=workRecordVo) {
			workRecordVo.setState("10");
			count = hpWorkRecordService.updateHpWorkRecordStateById(workRecordVo);
			if(count >0) {
				return ResultUtil.success(workRecordVo.getSeqNo());
			}else {
				logger.error(workRecordVo.getSeqNo()+"作业数据作废失败！");
				logger.error(JsonUtil.javaToJsonStr(workRecordVo));
				return ResultUtil.error(1, workRecordVo.getSeqNo()+"作业数据作废失败！");
			}
		}else{
			logger.error("WorkRecordVo入参解析出错！");
			return ResultUtil.error(1, "WorkRecordVo入参解析出错！");
		}		
	}
	
	
	/**
	 * 新增门机作业数据
	 * 
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="新增门机作业数据", notes="新增门机作业数据")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@PostMapping("/insertHpMjWorkRecord")
	@RequiresPermissions(value = {"insertHpMjWorkRecord"})
	public Result<Object> insertHpMjWorkRecord(@RequestBody WorkRecordVo workRecordVo) {
		int count = 0;
		if(null !=workRecordVo) {
			//把箱号字母转成大写
			workRecordVo.setUpdateContaid(workRecordVo.getUpdateContaid().toUpperCase());
			
			VesselContainerVo vesselContainer = new VesselContainerVo();
			vesselContainer.setContainerNumber(workRecordVo.getUpdateContaid());
			vesselContainer.setVesselNumber(workRecordVo.getVesselNumber());
			vesselContainer.setJobType(""+workRecordVo.getWorkType());
			
			int id = workRecordVo.getId();
			workRecordVo.setContaid(workRecordVo.getUpdateContaid());
			boolean isOk = true;
			//检查箱号是否有重复
			List<WorkRecordVo> list = hpWorkRecordService.getHpRecord(workRecordVo);
			if(null !=list && list.size() >0) {
				for(int i=0; i<list.size(); i++) {
					if(id !=list.get(i).getId()) {
						isOk = false;
						break;
					}
				}
			}
			if(isOk) {
				//只有装船和卸船作业才比对舱单
				if (0 == workRecordVo.getWorkType() || 1 == workRecordVo.getWorkType()) {
					//比对舱单
					isOk = vesselContainerService.booleanVesselContainer(vesselContainer);
				}
				if(isOk) {
					//设置箱属性
					this.setWorkRecord(workRecordVo, vesselContainer);
					workRecordVo.setState(ONE);//设置状态为理货成功
					String nict = "NICT";
					workRecordVo.setSeqNo(nict+DateUtil.getDate("yyyyMMddHHmmsssss"));
					workRecordVo.setAreaNum(nict);
					workRecordVo.setPassTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
					workRecordVo.setContaid(workRecordVo.getUpdateContaid());
					workRecordVo.setTopPlate(workRecordVo.getUpdateTopPlate());
					//新增门机作业数据
					count = hpWorkRecordService.insertHpWorkRecord(workRecordVo);
					if(count >0) {
						//更新外理数据库OCR表
						realTimeJobService.addOrUpdateWlData(workRecordVo,0);
						return ResultUtil.success(workRecordVo.getId());
					}else {
						logger.error(workRecordVo.getId()+"新增门机已理货数据失败！");
						logger.error(JsonUtil.javaToJsonStr(workRecordVo));
						return ResultUtil.error(1, workRecordVo.getId()+"新增门机货数据失败！");
					}
				}else {
					logger.error("门机比对舱单失败！");
					logger.error(JsonUtil.javaToJsonStr(workRecordVo));
					return ResultUtil.error(1, "门机比对舱单失败！");
				}
			}else {
				logger.error(workRecordVo.getUpdateContaid()+" 门机箱号已经存在！");
				logger.error(JsonUtil.javaToJsonStr(workRecordVo));
				return ResultUtil.error(1, workRecordVo.getUpdateContaid()+" 门机箱号已经存在！");
			}			
		}else{
			logger.error("WorkRecordVo门机入参解析出错！");
			return ResultUtil.error(1, "WorkRecordVo门机入参解析出错！");
		}		
	}
	
	/**
	 * 删除门机作业记录
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除门机作业记录", notes="删除门机作业记录")
	@ApiParam(name = "id", value = "作业记录id")
	@DeleteMapping("/deleteHpMjWorkRecordById")
	@RequiresPermissions(value = {"deleteHpMjWorkRecordById"})
	public Result<Object> deleteHpMjWorkRecordById(@RequestParam("id") int id) {
		//删除作业记录
		int count = hpWorkRecordService.deleteHpMjWorkRecordById(id);
		if(count >0) {
			return ResultUtil.success("id为"+id+"的门机记录删除成功！");
		}else {
			logger.error(id+"删除门机作业记录失败！");
			return ResultUtil.error(1, id+"删除门机作业记录失败！");
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
