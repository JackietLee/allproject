package net.pingfang.controller.vessel;

import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.equipmentMonitoring.EmCrane;
import net.pingfang.entity.importFile.VesselListVo;
import net.pingfang.entity.vessel.TruckInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.Result;
import net.pingfang.json.work.WorkJson;
import net.pingfang.service.equipmentMonitoring.EmCraneService;
import net.pingfang.service.vessel.VesselBayService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.service.vessel.VesselListService;
//import net.pingfang.service.websoket.SocketIoClient_Bay;
import net.pingfang.service.workRecord.CranePreparationService;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.ImgUtil;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 这个类里所有的API接口提供给数据服务调用，不加访问权限控制
 * @author Administrator
 *
 */
@Api("Data Service Api")
@RestController
@RequestMapping("/dataService")
public class DataServiceController {

	@Autowired
	private VesselBayService vesselBayService;
	
	@Autowired
	private WorkRecordService workRecordService;
	
	@Autowired
	private CranePreparationService cranePreparationService;
	
	@Autowired
	private VesselContainerService vesselContainerService;
	
//	@Autowired
//	private SocketIoClient_Bay SocketIoClient_Bay;
	
	@Autowired
	private EmCraneService emCraneService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private VesselListService vesselListService;
	//交换机名
	@Value("${rabbit.exchange.serviceToWeb}")
	private String exchange;
	
	private final static Logger logger = LoggerFactory.getLogger(DataServiceController.class);
	
	/**
	 * BAY识别
	 * 第三方服务调用的API
	 * 翁总调用
	 * @since 2020-05-19
	 * @param craneNum
	 * @return
	 */
	@ApiOperation(value="获取BAY位识别数据", notes="获取BAY位识别数据")
	@ApiParam(name = "craneNum", value = "岸桥编号")
	@GetMapping("/getVesselBayData")
	public Result<Object> getVesselBayData(@RequestParam("craneNum")String craneNum, @RequestParam("passTime")String passTime) {
		logger.info("********************** 进入BAY位识别接口getVesselBayData**************************");
		/*
		1、船旋方向(shipSide)
		http://192.168.1.63:5010/dataService/getAlongsideByVesselCood
		2、作业Bay(Bay结构)
		http://192.168.1.63:5010/dataService/getVesselSlotListByCraneNum
		3、获取当前桥和船的最近1吊的装船作业（包含箱号、Bay位信息）
		http://192.168.1.63:5010/dataService/getWorkRecordData
		4、获取当前桥和船的作业Bay集装箱使用情况（包含箱号、箱位，如01、02、03）
		http://192.168.1.63:5010/dataService/getVesselContainerList
		*/
		Result<Object> result = null;
		if(null == craneNum) {
			logger.error("岸桥编号不能为空！");
			result = ResultUtil.error(1, "岸桥编号不能为空！");
		}else {
			Map<String,Object> map = vesselBayService.getVesselBayData(craneNum, passTime);
			if(map.containsKey("vesselBayList")) {
				result = ResultUtil.success(map);
			}else {
				result = ResultUtil.success();
				result.setMsg("岸桥"+craneNum+"没有配制BAY位！");
				logger.info("岸桥"+craneNum+"没有配制BAY位！");
			}
		}
		logger.info("********************** 结束BAY位识别接口getVesselBayData**************************");
		return result;
	}
	
	
	/**
	 * 通过岸桥编号获取船舶Bay信息
	 * 第三方服务调用的API
	 * @param craneNum
	 * @return
	 */
	@ApiOperation(value="根据岸桥编号获取VesselSlot信息", notes="根据岸桥编号获取VesselSlot信息")
	@ApiParam(name = "craneNum", value = "岸桥编号")
	@GetMapping("/getVesselSlotListByCraneNum")
	public Result<Object> getVesselSlotListByCraneNum(String craneNum) {
		if(null == craneNum) {
			logger.error("岸桥编号不能为空！");
			return ResultUtil.error(1, "岸桥编号不能为空！");
		}else {
			return ResultUtil.success(vesselBayService.getVesselSlotListByCraneNum(craneNum));
		}
	}
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * 第三方服务调用的API
	 * @param vesselCood
	 * @return
	 */
	@ApiOperation(value="根据船舶代码获取船舷方向", notes="根据船舶代码获取船舷方向")
	@ApiParam(name = "vesselCood", value = "船舶代码数据")
	@GetMapping("/getAlongsideByVesselCood")
	public Result<Object> getAlongsideByVesselCood(String vesselCood) {
		if(null == vesselCood && "".equals(vesselCood)) {
			logger.error("船舶代码不能为空！");
			return ResultUtil.error(1, "船舶代码不能为空！");
		}else {
			return ResultUtil.success(cranePreparationService.getAlongsideByVesselCood(vesselCood));
			
		}
	}
	/**
	 * 新增作业记录
	 * @param workRecordVo
	 * 第三方服务调用的API
	 * @return
	 */
	@ApiOperation(value="新增作业记录", notes="新增作业记录")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@PostMapping("/insertWorkRecord")
	public Result<Object> insertWorkRecord(@RequestBody WorkJson workJson) {
		if(null !=workJson) {
			//插入作业记录
			List<WorkRecordVo> list = workRecordService.insertWorkRecord(workJson,"insert");
			if(list.size() >0) {
				//发送消息刷新数据
				//SocketIoClient_Bay.addJobQueueDate("insertWorkRecord","9",workJson.getCrane_num(),JSON.toJSON(list).toString());
				this.mqFanoutSend("9", list);
				return ResultUtil.success(workJson.getSeq_no());
			}else {
				logger.error(workJson.getSeq_no()+"新增作业记录失败！");
				logger.error(JsonUtil.javaToJsonStr(workJson));
				return ResultUtil.error(1, workJson.getSeq_no()+"新增作业记录失败!");
			}	
		}else{
			logger.error("workJson入参解析出错！");
			return ResultUtil.error(1, "workJson入参解析出错！");
		}		
	}
	
	/**
	 * 查询岸桥编号的岸桥配制数据
	 * 数据服务调用的API
	 * @param craneNum
	 * @return
	 */
	@ApiOperation(value="查询岸桥编号的岸桥配制数据", notes="查询岸桥编号的岸桥配制数据")
	@ApiParam(name = "craneNum", value = "岸桥编号")
	@GetMapping("/getCranePreparationData")
	public List<CranePreparationVo> getCranePreparationData(@RequestParam("craneNum") String craneNum) {
		if(null !=craneNum && !"".equals(craneNum)) {
			return cranePreparationService.getCranePreparation(craneNum);
		}else {
			return null;
		}
	}
	
	/**
	 * 更新贝位
	 * @param workJson
	 * BAY位识别（翁总调用）
	 * @return
	 */
	/*
	 * 2021-02-06
	 * cgf
	 * 
	@ApiOperation(value="更新贝位", notes="更新贝位")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@PostMapping("/updateBayInfoBySeqNo")
	public Result<Object> updateBayInfoBySeqNo(@RequestBody WorkJson workJson) {
		int count = 0;
		if(null !=workJson) {
			if(null == workJson.getBay_result()) {
				//SocketIoClient_Bay.addJobQueueDate("updateBay","10",workJson.getCrane_num(), "");
				this.mqFanoutSend("10", "{craneNum:"+workJson.getCrane_num()+"}");
				return ResultUtil.success("bay_result为null,不更新BAY");
			}else {
				count = workRecordService.updateBayInfoBySeqNo(workJson);
				//SocketIoClient_Bay.addJobQueueDate("updateBay","10",workJson.getCrane_num(), "");
				this.mqFanoutSend("10", "{craneNum:"+workJson.getCrane_num()+"}");
				if(count >0) {
					return ResultUtil.success(workJson.getSeq_no());
				}else {
					logger.error(workJson.getSeq_no()+"根据作业编号更新贝位失败！");
					logger.error(JsonUtil.javaToJsonStr(workJson));
					return ResultUtil.error(count, workJson.getSeq_no()+"根据作业编号更新贝位失败！");
				}		
			}				
		}else{
			logger.error("workJson入参解析出错！");
			return ResultUtil.error(count, "workJson入参解析出错！");
		}		
	}
	*/
	/**
	 * 更新贝位
	 * 同步OCR数据
	 * @param workJson
	 * BAY位识别（翁总调用）
	 * @return
	 */
	@ApiOperation(value="更新贝位", notes="更新贝位")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@PostMapping("/updateBayInfo")
	public Result<Object> updateBayInfo(@RequestBody List<WorkRecordVo> workList) {
		int count = 0;
		if(null !=workList && workList.size()>0) {
			count = workRecordService.updateBayInfoBySeqNo(workList);
			this.mqFanoutSend("10", "{craneNum:"+workList.get(0).getCraneNum()+"}");
			if(count >0) {
				return ResultUtil.success(workList.get(0).getSeqNo());
			}else {
				logger.error(workList.get(0).getSeqNo()+"根据作业编号更新贝位失败！");
				logger.error(JsonUtil.javaToJsonStr(workList));
				return ResultUtil.error(count, workList.get(0).getSeqNo()+"根据作业编号更新贝位失败！");
			}				
		}else{
			logger.error("workList入参解析出错！");
			return ResultUtil.error(count, "workList入参解析出错！");
		}		
		
	}
	/**
	 * 更新BAY图上的贝位
	 * @param workJson
	 * BAY位识别（理货平台调用）
	 * 2021-02-06
	 * @return
	 */
	@ApiOperation(value="更新BAY图上的贝位", notes="更新BAY图上的贝位")
	@ApiParam(name = "workList", value = "作业信息JSON数据")
	@PostMapping("/updateViewBayInfo")
	public Result<Object> updateViewBayInfo(@RequestBody List<WorkRecordVo> workList) {
		int count = 0;
		if(null !=workList && workList.size()>0) {
			count = workRecordService.updateBayInfo(workList);
			this.mqFanoutSend("10", "{craneNum:"+workList.get(0).getCraneNum()+"}");
			if(count >0) {
				return ResultUtil.success(workList.get(0).getSeqNo());
			}else {
				logger.error(workList.get(0).getSeqNo()+"根据作业编号更新贝位失败！");
				logger.error(JsonUtil.javaToJsonStr(workList));
				return ResultUtil.error(count, workList.get(0).getSeqNo()+"根据作业编号更新贝位失败！");
			}				
		}else{
			logger.error("workList入参解析出错！");
			return ResultUtil.error(count, "workList入参解析出错！");
		}		
	}
	/**
	 * 查询一条作业数据
	 * 翁总调用的API
	 * @param craneNum
	 * @return
	 */
	@ApiOperation(value="查询一条作业数据", notes="查询一条作业数据")
	@ApiParam(name = "WorkRecordVo", value = "JSON参数")
	@PostMapping("/getWorkRecordData")
	public Result<Object> getWorkRecordData(@RequestBody WorkRecordVo workRecordVo) {
		if(null !=workRecordVo) {
			List<WorkRecordVo> newWorkRecordList = workRecordService.getWorkRecordData(workRecordVo);
			if(null !=newWorkRecordList && newWorkRecordList.size() >0) {
				return ResultUtil.success(newWorkRecordList);
			}else {
				return ResultUtil.error(1, "没有查到相应的数据！");
			}
		}else{
			logger.error("WorkRecordVo入参解析出错！");
			return ResultUtil.error(1, "WorkRecordVo入参解析出错！");
		}
	}
	/**
	 * 查询装箱在船上的位置数据
	 * 翁总调用的 API
	 * @param vesselContainerVo
	 * @return
	 */
	@ApiOperation(value="查询装箱在船上的位置数据", notes="查询装箱在船上的位置数据")
	@ApiParam(name = "vesselContainerList", value = "船舶集装箱JSON数据")
	@PutMapping("/getVesselContainerList")
	public Result<Object> getVesselContainerList(@RequestBody List<VesselContainerVo> vesselContainerList) {
		if(null !=vesselContainerList && vesselContainerList.size() >0) {
			List<Object> objList = vesselContainerService.getVesselContainerList(vesselContainerList);
			if(objList.size()>0) {
				return ResultUtil.success(objList);
			}else {
				logger.error("集装箱的位置在数据库里不存在！");
				return ResultUtil.error(1, "集装箱的位置在数据库里不存在！");
			}
		}else {
			logger.error("参数不正确！");
			return ResultUtil.error(1, "参数不正确！");
		}
	}
	/**
	 * 更新X,Y坐标
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="更新X,Y坐标", notes="更新X,Y坐标")
	@ApiParam(name = "WorkRecordVo", value = "作业信息JSON数据")
	@PostMapping("/updateWorkRecordXY")
	public Result<Object> updateWorkRecordXY(@RequestBody WorkRecordVo workRecordVo) {
		int count = 0;
		if(null !=workRecordVo) {
			count = workRecordService.updateWorkRecordXY(workRecordVo);
			if(count >0) {
				return ResultUtil.success(workRecordVo.getSeqNo());
			}else {
				logger.error(workRecordVo.getSeqNo()+"根据作业编号更新X,Y坐标失败！");
				logger.error(JsonUtil.javaToJsonStr(workRecordVo));
				return ResultUtil.error(count, workRecordVo.getSeqNo()+"根据作业编号更新X,Y坐标失败！");
			}
		}else{
			logger.error("WorkRecordVo入参解析出错！");
			return ResultUtil.error(count, "WorkRecordVo入参解析出错！");
		}		
	}
	
	/**
	 * 查询箱号是否存在
	 * 数据服务调用的API
	 * @param workRecordVo
	 * @return
	 */
	@ApiOperation(value="查询箱号是否存在", notes="查询箱号是否存在")
	@ApiParam(name = "workRecordVo", value = "JSON参数")
	@PostMapping("/getCountRecord")
	public Result<Object> getCountRecord(@RequestBody WorkRecordVo workRecordVo) {
		return ResultUtil.success(workRecordService.getCountRecord(workRecordVo));
	}
	/**
	 * 插入一条岸桥设备监控数据
	 * @param EmCrane
	 * @return
	 */
	@ApiOperation(value="插入一条岸桥设备监控数据", notes="插入一条岸桥设备监控数据")
	@ApiParam(name = "EmCrane", value = "作业信息JSON数据")
	@PostMapping("/addEmCrane")
	public Result<Object> addEmCrane(@RequestBody EmCrane crane) {
		int count = 0;
		if(null !=crane) {
			crane.setActivity("Y");
			crane.setPlcStatus(crane.getPlc().getStatus());
			count = emCraneService.addEmCrane(crane);
			if(count >0) {
				//SocketIoClient_Bay.addJobQueueDate("equipmentMonitoring","6",crane.getCraneName(),JSON.toJSON(crane).toString());
				this.mqFanoutSend("6", crane);
				return ResultUtil.success(crane.getId());
			}else {
				logger.error(crane.getCraneName()+"插入一条岸桥设备监控数据！");
				logger.error(JsonUtil.javaToJsonStr(crane));
				return ResultUtil.error(count, crane.getCraneName()+"插入一条岸桥设备监控数据！");
			}
		}else{
			logger.error("EmCrane入参解析出错！");
			return ResultUtil.error(count, "EmCrane入参解析出错！");
		}	
	}
	/**
	 * 更新一条岸桥设备监控为离线状态
	 * @param EmCrane
	 * @return
	 */
	@ApiOperation(value="更新一条岸桥设备监控为离线状态", notes="更新一条岸桥设备监控为离线状态")
	@ApiParam(name = "craneName", value = "岸桥编号")
	@PostMapping("/updateIsActivity")
	public Result<Object> updateIsActivity(String craneName) {
		int count = 0;
		if(null !=craneName) {
			count = emCraneService.updateIsActivity(craneName, "N");
			if(count >0) {
				//SocketIoClient_Bay.addJobQueueDate("equipmentMonitoring","6",craneName,"");
				this.mqFanoutSend("6", "{craneName:"+craneName+"}");
				return ResultUtil.success(craneName);
			}else {
				logger.error(craneName+"更新岸桥设备监控为离线状态失败！");
				logger.error(craneName);
				return ResultUtil.error(count, craneName+"更新岸桥设备监控为离线状态失败！");
			}
		}else{
			logger.error("岸桥编号不能为空！");
			return ResultUtil.error(1, "岸桥编号不能为空！");
		}	
	}
	/**
	 * 查询车顶号是否存在
	 * @param truckNumber
	 * @return
	 */
	@ApiOperation(value="查询车顶号是否存在", notes="查询车顶号是否存在")
	@ApiParam(name = "truckNumber", value = "车顶号")
	@GetMapping("/getCountTruckNumber")
	public Result<Object> getCountTruckNumber(String truckNumber) {
		int count = vesselContainerService.getCountTruckNumber(truckNumber);
		if(count >0) {
			return ResultUtil.success(count);
		}else {
			logger.error("拖车号:"+truckNumber+"在数据库里不存在！");
			logger.error(truckNumber);
			return ResultUtil.error(1, "拖车号:"+truckNumber+"在数据库里不存在！");
		}
	}
	
	/**
	 * 测试图片长宽
	 * @param imgUrl
	 * @return
	 */
	@ApiOperation(value="测试图片长宽", notes="测试图片长宽")
	@GetMapping("/getTestStr")
	public String getTestStr(String imgUrl) {
		return ImgUtil.getImgWidthHeight(imgUrl);
	}
	
	private Channel getChannel(String queue) {
		   Channel channel = null;
		   try {
			   channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(true);
			   //与生产者使用同一个交换机
			   channel.exchangeDeclare(exchange, "direct",true);
			   //把队列绑定到交换机上
			   channel.queueBind(queue, exchange, queue);
		   }catch(Exception e) {}
		   
		   return channel;
	   }
	/**
	 * 向MQ发送消息(前端WEB接收)
	 * 
	 * msgType为16，数据服务收到车号预识别报文处理完后需要通知WEB前端广播；
	 * msgType为9，API将作业数据入库后需要通知前端刷新实时作业数据；
	 * msgType为10，API更新BAY位后需要通知前端刷新BAY位图；
	 * msgType为6，设备维护；
	 * msgType为17，拖车号识别错误
	 * @param msgType
	 * @param objMst
	 */
	private void mqFanoutSend(String msgType, Object objMst) {
		try {
			 JSONObject jsonMsg = null;
			logger.info("广播消息："+objMst);
			if("9".equals(msgType)) {
				jsonMsg = new JSONObject();
				JSONArray jsonWorkData = new JSONArray(JSON.toJSON(objMst).toString());
				jsonMsg.put("workData", jsonWorkData);
			}else {
				jsonMsg = new JSONObject(JSON.toJSON(objMst).toString());
			}
			jsonMsg.put("msgType", msgType);
			//System.out.println(jsonMsg.toString());
			Message message = new Message(jsonMsg.toString().getBytes(),new MessageProperties());
			rabbitTemplate.send(exchange,"fanout",message);
			logger.info("广播消息成功："+jsonMsg);
		}catch(Exception e) {
			logger.error("向MQ发送消息时解析数据出错！");
    		e.printStackTrace();
    	}
	}
	
	
	
	@ApiOperation(value="查询车顶下拉列表数据", notes="查询车顶下拉列表数据")
	@PutMapping("/getTruckInfoListTest")
	public Result<Object> getTruckInfoListTest(@RequestBody List<VesselContainerVo> vesselContainerList) {
		Map<String,List<TruckInfoVo>> map = vesselContainerService.getTruckInfoList(vesselContainerList);		
		if(null !=map && !map.isEmpty()) {
			return ResultUtil.success(map);
		}else {
			logger.error("数据库tos_truck表中暂无数据！");
			return ResultUtil.error(1, "数据库tos_truck表中暂无数据！");
		}
	}
	
	@ApiOperation(value="比对舱单", notes="比对舱单")
	@PutMapping("/comparisonManifest")
	public Result<List<VesselListVo>> comparisonManifest(@RequestBody VesselListVo vesselList) {
		Result<List<VesselListVo>> result = new Result<List<VesselListVo>>();
		List<VesselListVo> list = vesselListService.getVesselListByNumber(vesselList);
		if(null !=list && !list.isEmpty()) {
			result.setCode(0); //0为成功，1为失败
			result.setMsg("成功");
			result.setData(list);
			return result;
		}else {
			result.setCode(1); //0为成功，1为失败
			result.setMsg("比对舱单没有找到"+vesselList.getVoyage()+"对应的箱数据！");
			logger.error("比对舱单没有找到"+vesselList.getVoyage()+"对应的箱数据！");
			logger.error(JsonUtil.javaToJsonStr(vesselList));
		}
		return result;
	}
	//Comparison manifest
	
	
	
//	public static void main(String[] args) {
//		
//		JSONObject objStr = new JSONObject();
//		objStr.put("crane_num", "QC13");
//		objStr.put("work_type", "0");
//		objStr.put("truckNumberMsg", "666666");
//		
//		
//		//JSONObject jsonMsg = new JSONObject(JSON.toJSON(objStr).toString());
//		objStr.put("msgType", "17");
//		System.out.println(objStr.toString());
//	}
//	
	
}
