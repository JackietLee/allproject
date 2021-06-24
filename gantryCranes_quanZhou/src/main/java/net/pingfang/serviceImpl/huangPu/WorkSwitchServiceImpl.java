package net.pingfang.serviceImpl.huangPu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
//import net.pingfang.config.redis.RedisUtils;
import net.pingfang.dao.huangPu.WorkSwitchDao;
import net.pingfang.entity.camera.CameraInforVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.service.camera.CameraInforService;
import net.pingfang.service.huangPu.WorkSwitchService;
import net.pingfang.service.workRecord.CranePreparationService;
import net.pingfang.utils.DateUtil;

/**
 * 门机配制信息ServiceImpl
 * 
 * @author Administrator
 * @since 2020-09-29
 *
 */

@Service
public class WorkSwitchServiceImpl implements WorkSwitchService {

//	@Autowired
//	private RedisUtils redisUtils;

	@Autowired
	private WorkSwitchDao workSwitchDao;

	@Autowired
	private CameraInforService cameraInforService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private CranePreparationService cranePreparationService;

	// private static final String LIST_HPMJ_SWITCHCAMERA_WORKMSG =
	// "List_hpMj_switchCamera_msg"; //存入Redis的门机切换摄像头报文

	// private static final String LIST_HPMJ_START_WORKMSG =
	// "List_hpMj_start_workMsg"; //存入Redis的门机开始作业报文

	final Logger log = LoggerFactory.getLogger(WorkSwitchServiceImpl.class);

	/**
	 * 获取门机配制
	 * 
	 * @return
	 */
	@Override
	public Map<String, Object> getHpCranePreparationList() {
		Map<String, Object> map = new HashMap<String, Object>();
		//List<CranePreparationVo> cpList = workSwitchDao.getHpCranePreparationList();
		List<CranePreparationVo> cpList = this.getAllMjPreparationList();
		map.put("cpList", cpList);
		if (null != cpList && cpList.size() > 0) {
			List<CameraInforVo> ciList = cameraInforService.getHpCamera();
			Map<String, List<CameraInforVo>> crMap = new HashMap<String, List<CameraInforVo>>();
			String str = ",";
			for (CranePreparationVo cp : cpList) {
				if (null != cp.getCameraId()) {
					crMap.put("" + cp.getId(), this.getCameraInforList(cp.getCameraId().split(str), ciList));
				}
			}
			map.put("crMap", crMap);
		}
		return map;
	}

	private List<CameraInforVo> getCameraInforList(String[] arrayId, List<CameraInforVo> ciList) {
		List<CameraInforVo> newList = new ArrayList<CameraInforVo>();
		if (null != arrayId && arrayId.length > 0) {
			for (int i = 0; i < arrayId.length; i++) {
				for (int j = 0; j < ciList.size(); j++) {
					if (!"".equals(arrayId[i].trim()) && Integer.parseInt(arrayId[i]) == ciList.get(j).getId()) {
						newList.add(ciList.get(j));
						break;
					}
				}
			}
		}
		return newList;
	}

	@Override
	public CranePreparationVo getHpCranePreparationByCraneNum(String craneNum) {
		return workSwitchDao.getHpCranePreparationByCraneNum(craneNum);
	}

	/**
	 * 1、设置门机工作相机协议 2、门机切换球机摄像头 { "messageType": "MJ01", "area": "场区", "driverCode":
	 * "设备号", "driverName": "设备名称", "acquisitionTime": "指令时间", "workType":
	 * "作业类型：bay,conta,vehicle", "clientId": "客户端ID", "vessel_voyage_number": “航次”,
	 * "cameraList": [{ "cameraCode": "相机编号", "cameraIp": "相机IP", "cameraType":
	 * "相机工作类型" }] }
	 * 
	 * @return
	 */
	@Override
	public int hpSwitchCameraList(CranePreparationVo cranePreparationVo) {
		int cpId = cranePreparationVo.getId();
		String craneNum = cranePreparationVo.getCraneNum();
		String cameraId = cranePreparationVo.getCameraId(); // 相机ID
		String[] cameraIdArray = null;
		int count = 0;
		// 获取所有门机配制
		//List<CranePreparationVo> cpList = workSwitchDao.getHpCranePreparationList();
		List<CranePreparationVo> cpList = this.getAllMjPreparationList();

		// 校验摄像头是否被其他门相机使用
		boolean isOk = false;
		if (null != cameraId && !"".equals(cameraId)) {
			cameraIdArray = cameraId.split(",");
			if(null !=cpList && cpList.size() >0) {
				for (int k = 0; k < cpList.size(); k++) {
					if ((cpId > 0 && cpId == cpList.get(k).getId()) || craneNum.equals(cpList.get(k).getCraneNum())) {
						continue;
					}
					String caId = cpList.get(k).getCameraId();
					if (null != caId && !"".equals(caId)) {
						String[] caIdArray = caId.split(",");
						for (int i = 0; i < cameraIdArray.length; i++) {
							for (int j = 0; j < caIdArray.length; j++) {
								if (cameraIdArray[i].equals(caIdArray[j])) {
									isOk = true;
									break;
								}
							}
							if (isOk) {
								break;
							}
						}
					}
					if (isOk) {
						break;
					}
				}
			}
		}
		if (isOk) {
			count = 2;
		} else {
			if (cpId > 0) {
				// 更新门机配制
				//count = workSwitchDao.updateHpCranePreparation(cranePreparationVo);
				cranePreparationVo.setType("2");
				count = cranePreparationService.deleteCranePreparationByCraneNum(cranePreparationVo);
				count = this.addHpCranePreparationList(cranePreparationVo);
			} else {
				// 新增门机配制
				//count = workSwitchDao.addHpCranePreparation(cranePreparationVo);
				count = this.addHpCranePreparationList(cranePreparationVo);
			}
			if(2 == count) {
				count = 1;
			}
			JSONObject msgObj = new JSONObject();
			msgObj.put("messageType", "MJ01");
			msgObj.put("area", "NICT");
			msgObj.put("driverCode", cranePreparationVo.getCraneNum());
			msgObj.put("driverName", cranePreparationVo.getCraneName());
			msgObj.put("acquisitionTime", DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
			msgObj.put("workType", "conta");
			msgObj.put("clientId", "");
			msgObj.put("vessel_voyage_number", cranePreparationVo.getVesselNumber());

			JSONArray cameraList = new JSONArray();

			if (null != cameraIdArray && cameraIdArray.length > 0) {
				List<Integer> idList = new ArrayList<Integer>();
				for (int i = 0; i < cameraIdArray.length; i++) {
					idList.add(Integer.parseInt(cameraIdArray[i]));
				}
				if (idList.size() > 0) {
					List<CameraInforVo> ciList = cameraInforService.getListCameraInforById(idList);
					if (null != ciList && ciList.size() > 0) {
						for (CameraInforVo c : ciList) {
							JSONObject cameraJson = new JSONObject();
							cameraJson.put("cameraCode", c.getCode());
							cameraJson.put("cameraIp", c.getIpAddress());
							cameraJson.put("cameraType", c.getCode());

							cameraList.put(cameraJson);
						}
					}
				}
			}
			msgObj.put("cameraList", cameraList);
			//System.out.println(msgObj.toString());
			try {
				// 给指定的频道发消息
				//redisUtils.publish(cranePreparationVo.getCraneNum(), msgObj);
				//leo 2020-10-30
				stringRedisTemplate.convertAndSend(cranePreparationVo.getCraneNum(), msgObj.toString());
				log.info("成功发布门机工作相机协议：" + msgObj.toString());
			} catch (Exception e) {
				log.error("发布门机工作相机协议出错：" + msgObj.toString());
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 1、设置门机开始停止工作协议 2、启动门机作业
	 * 
	 * { "messageType": "MJ02", "area": "场区", "driverCode": "设备号", "driverName":
	 * "设备名称", "acquisitionTime": "指令时间", "workType": "作业类型：bay,conta,vehicle",
	 * "cmdType": "命令类型：start,stop", "clientId": "客户端ID", "vessel_voyage_number":
	 * "航次", "jobQueueCode": "作业号" }
	 * 
	 * @return
	 */
	@Override
	public int hpStartHomeWork(CranePreparationVo cranePreparationVo) {
		JSONObject msgObj = new JSONObject();
		msgObj.put("messageType", "MJ02");
		msgObj.put("area", "NICT");
		msgObj.put("driverCode", cranePreparationVo.getCraneNum());
		msgObj.put("driverName", cranePreparationVo.getCraneNum());
		msgObj.put("acquisitionTime", DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
		msgObj.put("workType", "conta");
		msgObj.put("cmdType", cranePreparationVo.getCmdType());
		msgObj.put("clientId", "");
		msgObj.put("vessel_voyage_number", "");
		msgObj.put("jobQueueCode", "");
		try {
			//redisUtils.publish(cranePreparationVo.getCraneNum(), msgObj);
			//leo 2020-10-30
			stringRedisTemplate.convertAndSend(cranePreparationVo.getCraneNum(), msgObj.toString());
			log.info("成功发布门机开始或停止工作协议：" + msgObj.toString());
		} catch (Exception e) {
			log.error("发布门机开始或停止工作协议出错：" + msgObj.toString());
			e.printStackTrace();
		}
		// 启动
		if ("start".equals(cranePreparationVo.getCmdType())) {
			cranePreparationVo.setActivity("2");
		} else {
			// 停止
			cranePreparationVo.setActivity("1");
		}
		this.updateActivity(cranePreparationVo);
		return 1;
	}
	
	/**
	 * 查询门机配制列表数据
	 * @return
	 */
	@Override
	public List<CranePreparationVo> getAllMjPreparationList(){
		List<CranePreparationVo> newList = new ArrayList<CranePreparationVo>();
		List<CranePreparationVo> list = workSwitchDao.getAllMjPreparationList();
		if(null !=list && list.size()>0) {
			String str = " / ";
			boolean isOk = false;
			for(CranePreparationVo cp : list) {
				if(newList.size()>0) {
					isOk = false;
					for(int i=0; i<newList.size(); i++) {
						if(cp.getCraneNum().equals(newList.get(i).getCraneNum()) && cp.getVesselNumber().equals(newList.get(i).getVesselNumber())) {
							//小的BAY放前面
							if(Integer.parseInt(cp.getBay()) < Integer.parseInt(newList.get(i).getBay())) {
								newList.get(i).setBay(cp.getBay()+str+newList.get(i).getBay());
							}else {
								newList.get(i).setBay(newList.get(i).getBay()+str+cp.getBay());
							}
							isOk = true;
							break;
						} 
					}
					if(!isOk){
						newList.add(cp);
					}
				}else {
					newList.add(cp);
				}				
			}
		}
		return newList;
	}
	/**
	 * 删除门机配制数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteHpCranePreparation(int id) {
		return workSwitchDao.deleteHpCranePreparation(id);
	}
	
	/**
	 * 删除门机配制数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteHpCranePreparationByCraneNum(String craneNum) {
		CranePreparationVo cranePreparationVo = new CranePreparationVo();
		cranePreparationVo.setType("2");
		cranePreparationVo.setCraneNum(craneNum);
		return cranePreparationService.deleteCranePreparationByCraneNum(cranePreparationVo);
	}

	/**
	 * 更新门机配制开始或停止作业
	 * 
	 * @param cranePreparationVo
	 * @return
	 */
	@Override
	public int updateActivity(CranePreparationVo cranePreparationVo) {
		return workSwitchDao.updateActivity(cranePreparationVo);
	}
	
	/**
	 * 批量新增门机配制
	 * @param cranePreparationVo
	 * @return
	 */
	private int addHpCranePreparationList(CranePreparationVo cranePreparationVo) {
		int count = 0;
		String bay = cranePreparationVo.getBay();
		if(null !=bay) {
			String[] bayStr = bay.split(",");
			 
			if(null !=bayStr && bayStr.length >0) {
				int bayLength = bayStr.length;
				CranePreparationVo cp = null;
				int workMode = cranePreparationVo.getWorkMode();
				if(0 == workMode) {
					workMode = 1;
				}
				List<CranePreparationVo> cpList = new ArrayList<CranePreparationVo>(bayLength);
				for(int i=0; i<bayLength; i++) {
					cp = new CranePreparationVo();
					cp.setVesselNameCn(cranePreparationVo.getVesselNameCn());
					cp.setVesselNameEn(cranePreparationVo.getVesselNameEn());
					cp.setVesselCode(cranePreparationVo.getVesselCode());
					cp.setVesselNumber(cranePreparationVo.getVesselNumber());
					cp.setCraneNum(cranePreparationVo.getCraneNum());
					cp.setBay(bayStr[i]);
					cp.setIsAuto(cranePreparationVo.getIsAuto());
					cp.setWorkMode(workMode);
					cp.setWorkType(cranePreparationVo.getWorkType());
					cp.setAlongside(cranePreparationVo.getAlongside());
					cp.setInVoyage(cranePreparationVo.getInVoyage());
					cp.setOutVoyage(cranePreparationVo.getOutVoyage());
					cp.setCameraId(cranePreparationVo.getCameraId());
					cp.setType(cranePreparationVo.getType());
					if(null == cranePreparationVo.getType() || "".equals(cranePreparationVo.getType())) {
						cp.setType("2");
					}
					cpList.add(cp);
				}
				count =  workSwitchDao.addHpCranePreparationList(cpList);
			}
		}
		return count;
	}
}
