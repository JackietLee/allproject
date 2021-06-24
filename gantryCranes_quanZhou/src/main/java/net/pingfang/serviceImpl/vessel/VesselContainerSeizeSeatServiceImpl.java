package net.pingfang.serviceImpl.vessel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.vessel.VesselContainerSeizeSeatDao;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.async.AsyncService;
import net.pingfang.service.vessel.VesselContainerSeizeSeatService;
import net.pingfang.service.workRecord.WorkRecordService;

@Service
public class VesselContainerSeizeSeatServiceImpl implements VesselContainerSeizeSeatService{
	@Autowired
	private VesselContainerSeizeSeatDao vesselContainerSeizeSeatDao;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private WorkRecordService workRecordService;
	
	//创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程
    @Resource(name = "cachedThreadPool")
    private ExecutorService cachedThreadPool;
	
	private final static Logger logger = LoggerFactory.getLogger(VesselContainerSeizeSeatServiceImpl.class);
	/**
	 * 新增集装箱占位
	 * @param vesselContainer
	 * @return
	 */
	@Transactional
	@Override
	public int insertContainerSeizeSeat(VesselContainerVo vesselContainer) {
		int count = 0;
		String containerNumber = vesselContainer.getContainerNumber();
		if(null !=containerNumber && !"".equals(containerNumber) && 
				!"longTrunk".equals(containerNumber) && !"seizeSeat".equals(containerNumber)) {
			WorkRecordVo workRecord = new WorkRecordVo();
			workRecord.setCraneNum(vesselContainer.getCraneNum());
			workRecord.setVesselCode(vesselContainer.getVesselCode());
			workRecord.setVesselNumber(vesselContainer.getVesselNumber());
			workRecord.setUpdateContaid(vesselContainer.getContainerNumber());
			workRecord.setBayInfo(vesselContainer.getStdBay()+vesselContainer.getStdRow()+vesselContainer.getStdTier());
			/**
			 * 2020-11-09
			 */
			//更新作业记录表（"work_record"）里Bay
			count = vesselContainerSeizeSeatDao.updateBayInfo(workRecord);
			if(count >0) {
				//调用第三方接口把数据插入到外理的数据库
				insertContainerBay(vesselContainer);
			}
			
			/**
			 * 查询数据是否存在
			 * 2020-03-31
			 *
			 *2020-11-09
			 *
			//先查询该BAY是否存在，如果存在则不能更新
			count = workRecordService.getCountBay(workRecord);
			if(0 ==count) {
				//更新作业记录表（"work_record"）里Bay
				count = vesselContainerSeizeSeatDao.updateBayInfo(workRecord);
				if(count >0) {
					//调用第三方接口把数据插入到外理的数据库
					insertContainerBay(vesselContainer);
				}
			}else {
				count = 0;
				logger.info(workRecord.getBayInfo()+"BAY已经存在！");
			}
			*/
		}else {
			count = vesselContainerSeizeSeatDao.insertContainerSeizeSeat(vesselContainer);
		}
		return count;
	}
	
	/**
	 * 删除一条船舶集装箱占位
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteContainerSeizeSeat(VesselContainerVo vesselContainer) {
		int count = 0;
		WorkRecordVo workRecord = new WorkRecordVo();
		workRecord.setVesselCode(vesselContainer.getVesselCode());
		workRecord.setVesselNumber(vesselContainer.getVesselNumber());
		workRecord.setUpdateContaid(vesselContainer.getContainerNumber());
		
		
		if(null !=vesselContainer.getContainerNumber() && !"".equals(vesselContainer.getContainerNumber())) {
			//先查询此数据在作业记录表（“work_record”）里是否存在
			count = vesselContainerSeizeSeatDao.getCountWorkRecord(workRecord);
			if(count >0) {
				//更新作业记录表（"work_record"）里Bay
				workRecord.setBayInfo(vesselContainer.getStdBay());	//bayInfo更新为3位数字
				count = vesselContainerSeizeSeatDao.updateBayInfo(workRecord);
				//调用亮哥API更新外理数据库表
				vesselContainer.setStdBay("");
				vesselContainer.setStdRow("");
				vesselContainer.setStdTier("");
				this.insertContainerBay(vesselContainer);
				
			}else {
				//删除占位箱表（" 表: tos_vessel_container_seize_seat"）
				count = vesselContainerSeizeSeatDao.deleteContainerSeizeSeat(vesselContainer);
			}
		}else {
			//删除占位箱表（" 表: tos_vessel_container_seize_seat"）
			count = vesselContainerSeizeSeatDao.deleteContainerSeizeSeat(vesselContainer);
		}
		return count;
	}
	
	@Transactional
	@Override
	public int updateContainerSeizeSeatBayInfo(VesselContainerVo vesselContainer) {
		return vesselContainerSeizeSeatDao.updateContainerSeizeSeatBayInfo(vesselContainer);
	}
	
	@Override
	public int getCountContainerSeizeSeat(VesselContainerVo vesselContainer) {
		return vesselContainerSeizeSeatDao.getCountContainerSeizeSeat(vesselContainer);
	}
	@Override
	public int getNewCountContainerSeizeSeat(VesselContainerVo vesselContainer) {
		return vesselContainerSeizeSeatDao.getNewCountContainerSeizeSeat(vesselContainer);
	}
	/**
	 * 调用第三方API更新外理数据库BAY
	 * http://192.168.1.67:8070/ etlservice/command/bay
	 * 亮哥的接口
	 * @param vesselContainer
	 * @return
	 */
	@Override
	public void insertContainerBay(VesselContainerVo vesselContainer) {
		//new Thread(new OcrBayThread(vesselContainer)).start();
		//2020-06-15
		//cachedThreadPool.execute(new OcrBayThread(vesselContainer));
		//String seqNo = vesselContainer.getBillNumber();
		if(null !=vesselContainer) {
			//根据作业编号获取作业数据
			//List<WorkRecordVo> wrList = workRecordService.getWorkRecordBySeqNo(vesselContainer);
			WorkRecordVo workRecordVo = new WorkRecordVo();
			workRecordVo.setBayInfo(vesselContainer.getStdBay()+vesselContainer.getStdRow()+vesselContainer.getStdTier());
			workRecordVo.setUpdateContaid(vesselContainer.getContainerNumber());
			workRecordVo.setVesselNumber(vesselContainer.getVesselNumber());
			workRecordVo = vesselContainerSeizeSeatDao.getWrecord(workRecordVo);
			if(null !=workRecordVo) {
				workRecordVo.setUpdateContaid(vesselContainer.getContainerNumber());
				logger.info("********************************调用 etlservice/command/bay 更新BAY!*********************************");
				//进行更新
				workRecordVo.setBayInfo(vesselContainer.getStdBay()+vesselContainer.getStdRow()+vesselContainer.getStdTier());
				cachedThreadPool.execute(new OcrBayThread(workRecordVo));
			}
		}else {
			logger.info("********************************作业编号为空，同步BAY位失败 *********************************");
		}
	}
	
	class OcrBayThread implements Runnable {
		private WorkRecordVo workRecordVo;
		public OcrBayThread(WorkRecordVo workRecordVo){
			this.workRecordVo = workRecordVo;
		}
		public void run() {
			//同步BAY
			if(0 == this.updateBay(workRecordVo)) {
				//50毫秒后再次更新
				try {
					Thread.currentThread().sleep(50);// 毫秒
					logger.info("********************************睡眠50毫秒后二次更新 *********************************");
					this.updateBay(workRecordVo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
		/**
		 * 调用第三方接口同步BAY
		 * @param vesselContainer
		 * @return
		 */
		private int updateBay(WorkRecordVo workRecordVo) {
			int status = 0;
			try {
				JSONObject reqObj = new JSONObject();
				reqObj.put("containerNumber",workRecordVo.getUpdateContaid());
				reqObj.put("vesselVoyageNumber",workRecordVo.getVesselNumber());
				reqObj.put("slot", workRecordVo.getBayInfo());
				
				//2021-01-10新增
				reqObj.put("craneNumber", workRecordVo.getCraneNum());	//岸桥号 
				reqObj.put("time", workRecordVo.getPassTime());	//时间   yyyy-MM-dd HH:mm:ss
				reqObj.put("isoCode", workRecordVo.getIso());//isoCode 箱型
				reqObj.put("carNO", workRecordVo.getUpdateTopPlate()); 	//车号
				reqObj.put("ZXGID", workRecordVo.getStevedoreId());//装卸工ID
				//箱子状态 1完好 2缺损 int
				if("true".equals(workRecordVo.getDamaged())) {
					reqObj.put("containerStatus", 2);
				}else {
					reqObj.put("containerStatus", 1);
				}
				//1单吊或者2双吊 int
				if(2 == workRecordVo.getContainerType()) {
					reqObj.put("mode", 2);
				}else {
					reqObj.put("mode", 1);
				}
				//调用第三方API更新外理数据库BAY
				ResponseEntity<String> respMsg = asyncService.doPut(reqObj, "/etlservice/command/bay");
				if(null !=respMsg) {
					 String objStr = respMsg.getBody();										
					 if(null !=objStr && !"".equals(objStr)) {
						 JSONObject returnObj = new JSONObject(objStr);
						 if(200 == (Integer)returnObj.get("status")) {
							 status = 200;
							 logger.info("*********************************etlservice/command/bay调用成功!*********************************");
						}else {
							logger.info("*********************************etlservice/command/bay 更新BAY失败!*********************************");
						}
					}
				}
			}catch(JSONException e) {
				logger.error("调用接口etlservice/tally_container/bay出错："+e.getMessage());
				e.printStackTrace();
			}catch(Exception e) {
				logger.error("调用接口etlservice/tally_container/bay出错："+e.getMessage());
				e.printStackTrace();
			}
			return status;
		}
	}
	
}
