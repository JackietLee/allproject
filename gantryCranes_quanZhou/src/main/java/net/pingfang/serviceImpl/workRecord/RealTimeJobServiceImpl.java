package net.pingfang.serviceImpl.workRecord;

import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.config.mq.MQConfig;
import net.pingfang.config.mq.SendToExchange;
import net.pingfang.dao.workRecord.RealTimeJobServiceDao;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.async.AsyncService;
import net.pingfang.service.workRecord.RealTimeJobService;

@Service
public class RealTimeJobServiceImpl implements RealTimeJobService{
	
	@Autowired
	private RealTimeJobServiceDao realTimeJobServiceDao;
	@Autowired
	private AsyncService asyncService;
	
	//创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程
    @Resource(name = "cachedThreadPool")
    private ExecutorService cachedThreadPool;
    
    @Autowired
    private MQConfig mqConfig;
    
	public static final String LANE_NUM_0 = "0"; 		//0号车道
	public static final String LANE_NUM_10  = "10"; 	//10号车道
	
	private final static Logger logger = LoggerFactory.getLogger(RealTimeJobServiceImpl.class);

	/**
	 * 更新状态
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateWorkRecordStateById(WorkRecordVo workRecordVo) {
		return realTimeJobServiceDao.updateWorkRecordStateById(workRecordVo);
	}
	/**
	 *  根据id查询作业记录信息
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public WorkRecordVo getRtWorkRecordById(int id) {
		return realTimeJobServiceDao.getRtWorkRecordById(id);
	}
	/**
	 * 查询BAY位是否被占用
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int getCountBay(WorkRecordVo workRecordVo) {
		return realTimeJobServiceDao.getCountBay(workRecordVo);
	}
	/**
	 * 合并作业数据图片
	 * @param imgInfoVo
	 * @return
	 */
	@Override
	public int updateImgMerge(String imgInfo, ImgInfoVo imgInfoVo) {
		return realTimeJobServiceDao.updateImgMerge(imgInfo, imgInfoVo);
	}
	/**
	 * 更新N4状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	public int updateN4Status(int id,int status) {
		return realTimeJobServiceDao.updateN4Status(id, status);
	}
	/**
	 * 更新N4状态
	 * @param seqNo
	 * @param contaid
	 * @param status
	 * @return
	 */
	@Override
	public int updateN4StatusTwo(String seqNo, String contaid, int status){
		return realTimeJobServiceDao.updateN4StatusTwo(seqNo, contaid, status);
	}
	/**
	 * 更新或者新增OCR数据
	 * {
		  "container": [
		    {
		      "containerNumber": "GCXU2079413",
		      "damaged": "false",
		      "iso": "22G1",
		      "position": "F"
		    }
		  ],
		  "craneNumber": "QC19",
		  "jobType": 0,
		  "truckNumber": "110",
		  "vesselVoyageNumber": "487139"
		}
	 * @param workRecordVo
	 * 	@param cancel 操作状态 1取消操作 0同步操作 
	 */
	public String addOrUpdateWlData(WorkRecordVo workRecordVo, int cancel) {
		String msg = "";
		//2020-06-15
		//cachedThreadPool.execute(new OcrThread(workRecordVo, cancel));
		
		logger.info("************************** 进入调用OCI接口方法addOrUpdateWlData**********************");
		try {
			int workType = workRecordVo.getWorkType();
			JSONObject reqObj = new JSONObject();
			reqObj.put("vesselVoyageNumber", workRecordVo.getVesselNumber());
			reqObj.put("craneNumber", workRecordVo.getCraneNum());
			reqObj.put("jobType", workType);
			reqObj.put("truckNumber", workRecordVo.getUpdateTopPlate());
			
			//2020-12-22				
			reqObj.put("seq_no", workRecordVo.getSeqNo());//唯一任务编号
			reqObj.put("areaNum", workRecordVo.getAreaNum());//站点名称
			reqObj.put("vesselNameCn", workRecordVo.getVesselNameCn());//中文船名
			reqObj.put("laneNum", workRecordVo.getLaneNum());//车道号
			//reqObj.put("time", workRecordVo.getPassTime());//作业时间
			reqObj.put("passtime", workRecordVo.getPassTime());//作业时间
			reqObj.put("contaWeight", workRecordVo.getContaWeight());//吊具称重
			//2021-01-10泉州新增字段			
			reqObj.put("carNO", workRecordVo.getUpdateTopPlate()); 	//车号
			reqObj.put("ZXGID", workRecordVo.getStevedoreId());//装卸工ID
			reqObj.put("shipPosition", workRecordVo.getShipPosition());//卸船要用
			reqObj.put("cancel", cancel);	//操作状态 1取消操作 0同步操作
			if(null !=workRecordVo.getCreateTime()) {
				reqObj.put("endTime", workRecordVo.getCreateTime());	//结束时间
			}else {
				reqObj.put("endTime", workRecordVo.getPassTime());	//结束时间
			}				
			//舱盖板需要    开舱或者关舱 0:关舱  1:开舱 int
			if(4 == workType) {
				reqObj.put("operateType", 1);
			}else if(5 == workType){
				reqObj.put("operateType", 0);
			}else{
				reqObj.put("operateType", "");
			}
			//1单吊或者2双吊 int
			if(2 == workRecordVo.getContainerType()) {
				reqObj.put("mode", 2);
			}else {
				reqObj.put("mode", 1);
			}
			 JSONArray containerArray = new JSONArray();		 
				// int container_type = workRecordVo.getContainerType(); //箱类型 0：长箱,1：短箱,2：双箱,10：未知
				 int laneNum = workRecordVo.getLaneNum(); //"lane_num": "01", //车道 
				 int state = Integer.parseInt(workRecordVo.getState());//如果是0：长箱,1：短箱，则只取第一条记录
						 /**
						  * 需要插入数据到OCR表
						  * state 状态（0表未理货，1表示已理货，2表示数据异常，9为仓盖板，10为作废数据）
						  * 2、作业车道增加0和10，0表示海侧作业，10表示陆侧作业，0车道和10车道作业数据不进行完整性要求，产生数据后自动出现在未处理，由工作人员判定勾选仓盖板点提交，不用匹配舱单，需要插入OCR，
						  */
						 if((1 == state || 3 == state || 9 == state || LANE_NUM_0.equals(laneNum) || LANE_NUM_10.equals(laneNum))
								 && (null !=workRecordVo.getUpdateContaid() && !"".equals(workRecordVo.getUpdateContaid()))) {
							 JSONObject containerObj = new JSONObject();
							 containerObj.put("containerNumber", workRecordVo.getUpdateContaid());
							 containerObj.put("slot", workRecordVo.getBayInfo());//箱位具体船位 0170502
							 containerObj.put("n4Status", workRecordVo.getN4Status());	//1为成功，2为失败，3为N4接口成功社区接口失败
							 String damaged = workRecordVo.getDamaged();
							 if(null == damaged) {
								 damaged = "false";
							 }
							 containerObj.put("damaged", damaged);	//临时为不残损
							 containerObj.put("iso", workRecordVo.getIso());
							 containerObj.put("position", this.getNote(workRecordVo));
							 //2020-12-22
							 containerObj.put("orderid", workRecordVo.getOrderid()); //箱序号
							 //箱门朝向(0：前；1：后)
							 containerObj.put("boxDoor", workRecordVo.getDoorDir());	//箱门方向 卸船需要   箱门1(反向)/0(正向)
							 containerArray.put(containerObj);
						 }
			 if(containerArray.length() >0) {
				 reqObj.put("container", containerArray);
				 try {
						//调用第三方API更新外理数据库BAY
						ResponseEntity<String> respMsg = asyncService.doPost(reqObj, "/etlservice/tally_cntr_ocr/synchronization");
						if(null !=respMsg) {
							 String objStr = respMsg.getBody();										
							 if(null !=objStr && !"".equals(objStr)) {
								 JSONObject returnObj = new JSONObject(objStr);
								 if("200".equals(returnObj.get("status").toString())) {
									 //同步数据返回的JSON对象
									 JSONArray dataJson = returnObj.getJSONArray("data");
									 if(null !=dataJson && dataJson.length() >0) {
										 JSONObject reObj = new JSONObject(dataJson.get(0).toString());
										 //状态200为成功，3为调用N4接口成功，调用社区接口失败
										 if("200".equals(reObj.get("status").toString())) {											 
											 msg = "200";
											 logger.info("*********************************/etlservice/tally_cntr_ocr/synchronization调用成功!*********************************");
										 }else if("3".equals(reObj.get("status").toString())) {
											 //调用N4接口成功，调用社区接口失败
											 msg = "3";
										 }
									 }else {
										 msg = "调用同步确认数据接口失败2！";
										 logger.error("/etlservice/tally_cntr_ocr/synchronization调用失败2："+returnObj);
									 }									 
								}else {
									msg = "调用同步确认数据接口失败1！";
									logger.error("/etlservice/tally_cntr_ocr/synchronization调用失败1："+returnObj);
								}
							}
						}
					}catch(JSONException e) {
						msg = "调用同步确认数据接口出错1！";
						logger.error("调用接口/etlservice/tally_cntr_ocr/synchronization出错："+e.getMessage());
						e.printStackTrace();
					}catch(Exception e) {
						msg = "调用同步确认数据接口出错2！";
						logger.error("调用接口/etlservice/tally_cntr_ocr/synchronization出错："+e.getMessage());
						e.printStackTrace();
					}		
				 
			 }else {
				 msg = "数据不校验不通过，不调用同步确认数据接口！";
				 logger.info("数据不校验不通过，不调用OCR接口："+workRecordVo.toString());
			 }
		}catch(Exception e) {
			msg = "数据处理出错！";
			logger.error("调用OCR接口 API /etlservice/tally_cntr_ocr/synchronization  API错误："+e.getMessage());
			e.printStackTrace();
		}
		return msg;		
	}
	private String getNote(WorkRecordVo workRecordVo) {
		String note = "其他";
		//箱序号(0为'F'前箱；1为'A'后箱；2为'M'长箱；5为其他)
		int orderid = workRecordVo.getOrderid();
		if(0 == orderid) {
			note = "F";
		}else if(1 == orderid) {
			note = "A";
		}else if(2 == orderid) {
			note = "M";
		}
		return note;
	}
	
	
	class OcrThread implements Runnable {
		private WorkRecordVo workRecordVo;
		private int cancel;	//操作状态 1取消操作 0同步操作 
		public OcrThread(WorkRecordVo workRecordVo,int cancel) {
			this.workRecordVo = workRecordVo;
			this.cancel = cancel;
		}
		public void run() {
			logger.info("************************** 进入调用OCI接口方法addOrUpdateWlData**********************");
			try {
				int workType = workRecordVo.getWorkType();
				JSONObject reqObj = new JSONObject();
				reqObj.put("vesselVoyageNumber", workRecordVo.getVesselNumber());
				reqObj.put("craneNumber", workRecordVo.getCraneNum());
				reqObj.put("jobType", workType);
				reqObj.put("truckNumber", workRecordVo.getUpdateTopPlate());
				
				//2020-12-22				
				reqObj.put("seq_no", workRecordVo.getSeqNo());//唯一任务编号
				reqObj.put("areaNum", workRecordVo.getAreaNum());//站点名称
				reqObj.put("vesselNameCn", workRecordVo.getVesselNameCn());//中文船名
				reqObj.put("laneNum", workRecordVo.getLaneNum());//车道号
				reqObj.put("time", workRecordVo.getPassTime());//作业时间
				 
				reqObj.put("contaWeight", workRecordVo.getContaWeight());//吊具称重
				//2021-01-10泉州新增字段
				reqObj.put("slot", workRecordVo.getBayInfo());//箱位具体船位 0170502
				reqObj.put("carNO", workRecordVo.getUpdateTopPlate()); 	//车号
				reqObj.put("ZXGID", workRecordVo.getStevedoreId());//装卸工ID
				reqObj.put("shipPosition", workRecordVo.getShipPosition());//卸船要用
				reqObj.put("cancel", cancel);	//操作状态 1取消操作 0同步操作
				if(null !=workRecordVo.getCreateTime()) {
					reqObj.put("endTime", workRecordVo.getCreateTime());	//结束时间
				}else {
					reqObj.put("endTime", workRecordVo.getPassTime());	//结束时间
				}				
				//舱盖板需要    开舱或者关舱 0:关舱  1:开舱 int
				if(4 == workType) {
					reqObj.put("operateType", 1);
				}else if(5 == workType){
					reqObj.put("operateType", 0);
				}else{
					reqObj.put("operateType", "");
				}
				//1单吊或者2双吊 int
				if(2 == workRecordVo.getContainerType()) {
					reqObj.put("mode", 2);
				}else {
					reqObj.put("mode", 1);
				}
				 JSONArray containerArray = new JSONArray();		 
					// int container_type = workRecordVo.getContainerType(); //箱类型 0：长箱,1：短箱,2：双箱,10：未知
					 int laneNum = workRecordVo.getLaneNum(); //"lane_num": "01", //车道 
					 int state = Integer.parseInt(workRecordVo.getState());//如果是0：长箱,1：短箱，则只取第一条记录
							 /**
							  * 需要插入数据到OCR表
							  * state 状态（0表未理货，1表示已理货，2表示数据异常，9为仓盖板，10为作废数据）
							  * 2、作业车道增加0和10，0表示海侧作业，10表示陆侧作业，0车道和10车道作业数据不进行完整性要求，产生数据后自动出现在未处理，由工作人员判定勾选仓盖板点提交，不用匹配舱单，需要插入OCR，
							  */
							 if((1 == state || 9 == state || LANE_NUM_0.equals(laneNum) || LANE_NUM_10.equals(laneNum))
									 && (null !=workRecordVo.getUpdateContaid() && !"".equals(workRecordVo.getUpdateContaid()))) {
								 JSONObject containerObj = new JSONObject();
								 containerObj.put("containerNumber", workRecordVo.getUpdateContaid());
								 //containerObj.put("damaged", "false");	//临时为不残损
								 String damaged = workRecordVo.getDamaged();
								 if(null == damaged) {
									 damaged = "false";
								 }
								 containerObj.put("damaged", damaged);	//临时为不残损
								 containerObj.put("iso", workRecordVo.getIso());
								 containerObj.put("position", this.getNote(workRecordVo));
								 //2020-12-22
								 containerObj.put("orderid", workRecordVo.getOrderid()); //箱序号
								 
								 //箱门朝向(0：前；1：后)
								 containerObj.put("boxDoor", workRecordVo.getDoorDir());	//箱门方向 卸船需要   箱门1(反向)/0(正向)
									
								 containerArray.put(containerObj);
							 }
				 if(containerArray.length() >0) {
					 reqObj.put("container", containerArray);
					 try {
							//调用第三方API更新外理数据库BAY
							ResponseEntity<String> respMsg = asyncService.doPost(reqObj, "/etlservice/tally_cntr_ocr/synchronization");
							if(null !=respMsg) {
								 String objStr = respMsg.getBody();										
								 if(null !=objStr && !"".equals(objStr)) {
									 JSONObject returnObj = new JSONObject(objStr);
									 if(200 == (Integer)returnObj.get("status")) {
										 logger.info("*********************************/etlservice/tally_cntr_ocr/synchronization调用成功!*********************************");
									}
								}
							}
						}catch(JSONException e) {
							logger.error("调用接口/etlservice/tally_cntr_ocr/synchronization出错："+e.getMessage());
							e.printStackTrace();
						}catch(Exception e) {
							logger.error("调用接口/etlservice/tally_cntr_ocr/synchronization出错："+e.getMessage());
							e.printStackTrace();
						}		
					 
				 }else {
					 logger.info("数据不校验不通过，不调用OCR接口："+workRecordVo.toString());
					 
				 }
			}catch(Exception e) {
				System.out.println("OCR接口 API /etlservice/tally_cntr_ocr/synchronization 出错！");
				logger.error("调用OCR接口 API /etlservice/tally_cntr_ocr/synchronization  API错误："+e.getMessage());
				e.printStackTrace();
			}
		}
		private String getNote(WorkRecordVo workRecordVo) {
			String note = "其他";
			//箱序号(0为'F'前箱；1为'A'后箱；2为'M'长箱；5为其他)
			int orderid = workRecordVo.getOrderid();
			if(0 == orderid) {
				note = "F";
			}else if(1 == orderid) {
				note = "A";
			}else if(2 == orderid) {
				note = "M";
			}
			return note;
		}
	}
	
	
	/**
	 * 发送箱号给码头
	 * @param workRecordVo
	 */
	@Override
	public void mqSendToContainerid(WorkRecordVo workRecordVo) {
		//MQ发送箱号信息
		cachedThreadPool.execute(new MQContainerThread(workRecordVo));
	}
	/**
	 * MQ发送箱号信息给码头
	 * @author Administrator
	 *
	 */
	class MQContainerThread implements Runnable {
		 private WorkRecordVo workRecordVo;
		 public MQContainerThread(WorkRecordVo workRecordVo) {
			 this.workRecordVo = workRecordVo;
		 }
		 public void run() {		
				try {
					JSONObject reqObj = new JSONObject();
					//岸桥号
					reqObj.put("POINT_OF_WORK", workRecordVo.getCraneNum());
					//作业类型，装船LD; 卸船DS
					if(0 == workRecordVo.getWorkType()) {
						reqObj.put("WORK_TYPE", "LD");
					}else {
						reqObj.put("WORK_TYPE", "DS");
					}
					reqObj.put("VOYAGE_NO", workRecordVo.getVoyageNo());  //华东系统船舶艘次号
					//是否可以继续装卸船，如果是卸船，就一直是True,可以继续为True,否则为False
					reqObj.put("CONTINUEFLAG", "True");
					//作业时间
					reqObj.put("UPDATE_TIME", workRecordVo.getPassTime());
					//CONTAINER_ID
					reqObj.put("CONTAINER_ID", workRecordVo.getUpdateContaid());
					try {
		   	    	 	// 发送消息到队列
						 if("Y".equals(mqConfig.getSend_message())) {
							 SendToExchange.sendToRabbitMq(mqConfig.getContainerid_queue_name()+workRecordVo.getCraneNum(), reqObj.toString());
						 }
					} catch (Exception e) {
						logger.error("MQ发送箱号失败："+e.getMessage());
						e.printStackTrace();
					}
				}catch(Exception e) {
					logger.error("MQ发送箱号出错："+e.getMessage());
					e.printStackTrace();
				}
		 }
	 }
}
