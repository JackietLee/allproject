package net.pingfang.controller.workRecord;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.qz.DirectContainerAndCar;
import net.pingfang.entity.role.Tuser;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.DataResult;
import net.pingfang.handler.Result;
import net.pingfang.service.feignClient.QzFeignClient;
import net.pingfang.service.vessel.VesselContainerSeizeSeatService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.service.workRecord.RealTimeJobService;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 实时作业界面按钮处理
 * @author Administrator
 *
 */
@Api("RealTime Job Api")
@RestController
@RequestMapping("/realTimeJob")
public class RealTimeJobController {
	
	@Autowired
	private RealTimeJobService realTimeJobService;
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private VesselContainerService vesselContainerService;
	@Autowired
	private VesselContainerSeizeSeatService vesselContainerSeizeSeatService;
	@Autowired
	private HttpSession session;
	@Autowired
	private QzFeignClient qzFeignClient;
	
	private final static Logger logger = LoggerFactory.getLogger(RealTimeJobController.class);
	
	private final static String ONE = "1";

	
	/**
	 * 作业数据作废（更新状态）
	 * 状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="作业数据作废", notes="作业数据作废")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@RequiresPermissions(value = {"workRecordToVoid"})
	@PostMapping("/workRecordToVoid")
	public Result<Object> workRecordToVoid(@RequestBody WorkRecordVo workRecordVo) {
		logger.info(this.getUserName() + "录入系统操作！");
		int count = 0;
		if(null !=workRecordVo) {
			String bayInfo = workRecordVo.getBayInfo();
			if(null !=workRecordVo.getContaid() && !"".equals(workRecordVo.getContaid())
					&& null !=bayInfo && 7==bayInfo.length() && 0 == workRecordVo.getWorkType()) {
				
				//箱号，船舶艘次号，作业类型
				//检查箱号是否有重复
				List<WorkRecordVo> list = workRecordService.getRecord(workRecordVo);
				if(1 == list.size()) {
					//组装参数直接调用亮哥接口清空华东数据库里的BAY
					VesselContainerVo vesselContainer = new VesselContainerVo();
					vesselContainer.setContainerNumber(workRecordVo.getContaid());
					vesselContainer.setVesselNumber(workRecordVo.getVesselNumber());
					
					vesselContainer.setBillNumber(list.get(0).getSeqNo());
					vesselContainer.setStdBay("");
					vesselContainer.setStdRow("");
					vesselContainer.setStdTier("");
					vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
				}else {
					int id = workRecordVo.getId();
					for(int i=0; i<list.size(); i++) {
						if(id !=list.get(i).getId()) {
							bayInfo = list.get(i).getBayInfo();
							if(null !=bayInfo && 7==bayInfo.length()) {
								VesselContainerVo vesselContainer = new VesselContainerVo();
								vesselContainer.setContainerNumber(workRecordVo.getContaid());
								vesselContainer.setVesselNumber(workRecordVo.getVesselNumber());
								
								vesselContainer.setBillNumber(list.get(i).getSeqNo());
								vesselContainer.setStdBay(bayInfo.substring(0,3));
								vesselContainer.setStdRow(bayInfo.substring(3,5));
								vesselContainer.setStdTier(bayInfo.substring(5));
								vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
							}							
						}
					}
				}
			}
			workRecordVo.setState("10");
			bayInfo = workRecordVo.getBayInfo();
			//如果等于bayInfo有7位就截取前三们
			if(null !=bayInfo && 7==bayInfo.length()) {
				bayInfo = bayInfo.substring(0,3);
				workRecordVo.setBayInfo(bayInfo);
			}
			count = realTimeJobService.updateWorkRecordStateById(workRecordVo);
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
	 *录入系统操作（只有作业类型为0和1会比对舱单）
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
	 * 16、直装(装船)作业类型 作业类型：16
	 * 17、直提(卸船)作业类型 作业类型：17
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="录入系统", notes="录入系统")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@PostMapping("/inputWorkRecord")
	@RequiresPermissions(value = {"inputWorkRecord"})
	public Result<Object> inputWorkRecord(@RequestBody WorkRecordVo workRecordVo) {
		logger.info(this.getUserName() + "录入系统操作！");
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
				//拖车号校验
				isOk = this.booleanPlate(workRecordVo.getUpdateTopPlate());
				if(isOk) {
					//获取BAY
					String bayInfo = workRecordVo.getBayInfo();
					//判断BAY的长度是否有7位，如果没有则从数据库里获取
					if(null == bayInfo || 7 !=bayInfo.length()) {
						WorkRecordVo wr = realTimeJobService.getRtWorkRecordById(id);
						if(null !=wr) {
							bayInfo = wr.getBayInfo();
						}
					}else {
						//如果有7位则判断该BAY位是否有被其他箱占用
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
						//只有装船和卸船作业才比对舱单
						if (0 == workRecordVo.getWorkType() || 1 == workRecordVo.getWorkType()) {
							//比对舱单
							isOk = vesselContainerService.booleanVesselContainer(vesselContainer);
						}
						//直装(装船)作业类型 作业类型：16 ,直提(卸船)作业类型 作业类型：17
						if(isOk && (16 == workRecordVo.getWorkType() || 17 == workRecordVo.getWorkType())) {
							DirectContainerAndCar directContainerAndCar = new DirectContainerAndCar();
							directContainerAndCar.setVesselVoyageNumber(workRecordVo.getVesselNumber());
							directContainerAndCar.setContainerNumber(workRecordVo.getUpdateContaid());
							directContainerAndCar.setJobType(""+workRecordVo.getWorkType());
							directContainerAndCar.setCarNO(workRecordVo.getUpdateTopPlate());
							logger.info("提交直装,直提接口校验参数："+JsonUtil.javaToJsonStr(workRecordVo));
							DataResult<DirectContainerAndCar> dr = qzFeignClient.isDirectLegal(directContainerAndCar);
							if(null !=dr) {
								if(200 == dr.getStatus()) {
									isOk = true;
								}else {
									isOk = false;
									logger.error("直装,直提接口校验失败！");
									logger.error(JsonUtil.javaToJsonStr(workRecordVo));
									return ResultUtil.error(1, dr.getMessage());
								}
							}else {
								logger.error("直装,直提接口请求失败！");
								logger.error(JsonUtil.javaToJsonStr(workRecordVo));
							}
						}					
						if(isOk) {
							//设置箱属性
							this.setWorkRecord(workRecordVo, vesselContainer);
							workRecordVo.setState(ONE);//设置状态为理货成功
							WorkRecordVo wr = workRecordService.getNewWorkRecordById(id);
							String msg = "200";
							if(!"1".equals(wr.getState())) {
								//更新外理数据库OCR表
								msg = realTimeJobService.addOrUpdateWlData(workRecordVo,0);
							}else {
								logger.info("此数据已经同步过，不进行二次同步："+JsonUtil.javaToJsonStr(workRecordVo));
							}
							
							if("200".equals(msg)) {
								//录入舱单
								count = workRecordService.updateWorkRecordById(workRecordVo);
								realTimeJobService.updateN4Status(id, 1);
								if(count >0) {
									workRecordVo.setVoyageNo(vesselContainer.getVoyageNo());
									//MQ发送箱号信息
									realTimeJobService.mqSendToContainerid(workRecordVo);
									return ResultUtil.success(workRecordVo.getId());								
								}else {
									logger.error(workRecordVo.getId()+"更新已理货数据失败！！");
									logger.error(JsonUtil.javaToJsonStr(workRecordVo));
									return ResultUtil.error(1, workRecordVo.getId()+"更新已理货数据失败！");
								}							
							}else if("3".equals(msg)) {
								realTimeJobService.updateN4Status(id, 3);
								logger.error(workRecordVo.getId()+"更新已理货数据失败！"+msg);
								logger.error(JsonUtil.javaToJsonStr(workRecordVo));
								return ResultUtil.error(1, msg);
							}else {
								logger.error(workRecordVo.getId()+"更新已理货数据失败！"+msg);
								logger.error(JsonUtil.javaToJsonStr(workRecordVo));
								return ResultUtil.error(1, msg);
							}
						}else {
							logger.error("比对舱单失败！");
							logger.error(JsonUtil.javaToJsonStr(workRecordVo));
							return ResultUtil.error(1, "比对舱单失败！");
						}
					}else {
						logger.error("BAY位："+bayInfo+"已经被占用！");
						logger.error(JsonUtil.javaToJsonStr(workRecordVo));
						return ResultUtil.error(1, "BAY位："+bayInfo+"已经被占用！");
					}
				}else {
					logger.error("拖车号:"+workRecordVo.getUpdateTopPlate()+"在数据库里不存在！");
					logger.error(JsonUtil.javaToJsonStr(workRecordVo));
					return ResultUtil.error(1, "拖车号:"+workRecordVo.getUpdateTopPlate()+"在数据库里不存在！");
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
	 * 指令数据合并
	 * 未处理合并到已处理数据里，合并图片和BAY位，作废未处理数据
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="指令数据合并", notes="指令数据合并")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@RequiresPermissions(value = {"workRecordMerge"})
	@PostMapping("/workRecordMerge")
	public Result<Object> workRecordMerge(@RequestBody List<WorkRecordVo> workRecordList) {
		logger.info(this.getUserName() + "指令数据合并操作！");
		int count = 1;
		if(null !=workRecordList && workRecordList.size() >1) {
			logger.info("合并作业数据："+JsonUtil.javaToJsonStr(workRecordList));
			WorkRecordVo WorkRecord1 = workRecordList.get(0);
			WorkRecordVo WorkRecord2 = workRecordList.get(1);
			
			String bayInfo = WorkRecord1.getBayInfo();
			//BAY位有7位数字才合并
			if(null !=bayInfo && bayInfo.length() == 7) {
				WorkRecord2.setBayInfo(WorkRecord1.getBayInfo());
				//更新BAY位
				workRecordService.updateBayInfoById(WorkRecord2.getId(), WorkRecord1.getBayInfo());
			}
			ImgInfoVo imgInfoVo = new ImgInfoVo();
			imgInfoVo.setWorkId(WorkRecord2.getId());
			imgInfoVo.setSeqNo(WorkRecord2.getSeqNo());
			//合并图片
			count = realTimeJobService.updateImgMerge(WorkRecord1.getSeqNo(), imgInfoVo);
			//作废数据
			//if(count >0) {
				//如果等于bayInfo有7位就截取前三们
				if(null !=bayInfo && 7==bayInfo.length()) {
					bayInfo = bayInfo.substring(0,3);
					WorkRecord1.setBayInfo(bayInfo);
				}
				WorkRecord1.setState("10");
				count = realTimeJobService.updateWorkRecordStateById(WorkRecord1);
			//}
			if(count >0) {
				logger.info("指令数据合并成功！");
				return ResultUtil.success(workRecordList);
			}else {
				logger.error("指令数据合并失败！");
				logger.error(JsonUtil.javaToJsonStr(workRecordList));
				return ResultUtil.error(1, "指令数据合并失败！");
			}
		}else{
			logger.error("workRecordList入参解析出错！");
			return ResultUtil.error(1, "workRecordList入参解析出错！");
		}		
	}

	/**
	 * 综合查询页面取消指令按钮
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="取消指令", notes="取消指令")
	@ApiParam(name = "workId", value = "作业信息Id")
	@RequiresPermissions(value = {"cancelWorkRecord"})
	@GetMapping("/cancelWorkRecord")
	public Result<Object> cancelWorkRecord(int workId) {
		logger.info(this.getUserName() + "取消指令workId："+workId);
		if(workId >0) {
			WorkRecordVo workRecord = new WorkRecordVo();
			workRecord.setId(workId);
			workRecord = workRecordService.getNewAlreadyWorkRecordById(workRecord);
			int count = 0;
			if(null !=workRecord) {
				//更新外理数据库OCR表
				//realTimeJobService.addOrUpdateWlData(workRecord,1);
				//更新外理数据库OCR表
				String msg = realTimeJobService.addOrUpdateWlData(workRecord,0);
				if("200".equals(msg)) {
					//作废数据
					realTimeJobService.updateWorkRecordStateById(workRecord);
					return ResultUtil.success(count);
				}else {
					return ResultUtil.error(1, msg);
				}
			}else {
				return ResultUtil.error(1, "该数据不存在！");
			}			
		}else{
			logger.error("workId不能为空！");
			return ResultUtil.error(1, "workId不能为空！");
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
	/**
	 * 获取登录用户名
	 * @return
	 */
	private String getUserName() {
		String userName = null;
		Object objUser = session.getAttribute("currentUser");
		if(null !=objUser) {
			Tuser currentUser = (Tuser)objUser;
			userName = currentUser.getUserName();
		}
		return userName;
	}
	/**
	 * 拖车号校验
	 * @param topPlate
	 * @return
	 */
	private boolean booleanPlate(String topPlate) {
		boolean isOk = false;
		//拖车号
		if(null !=topPlate) {
			int count = vesselContainerService.getCountTruckNumber(topPlate);
			if(count >0) {
				isOk = true;
			}
		}
		return isOk;
	}

}
