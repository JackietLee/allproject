package net.pingfang.serviceImpl.vessel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.vessel.VesselContainerDao;
import net.pingfang.dao.vessel.VesselContainerSeizeSeatDao;
import net.pingfang.entity.vessel.TruckInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.DataResult;
import net.pingfang.service.async.AsyncService;
import net.pingfang.service.feignClient.QzFeignClient;
import net.pingfang.service.ocr.WL_VesselContainerService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.service.workRecord.WorkRecordService;


/**
* 船舶集装箱ServiceImpl
* @author Administrator
* @since 2019-06-01
*
*/
@Service
public class VesselContainerServiceImpl implements VesselContainerService {
	
	@Autowired
	private VesselContainerDao vesselContainerDao;
	@Autowired
	private VesselContainerSeizeSeatDao vesselContainerSeizeSeatDao;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private WL_VesselContainerService wl_vesselContainerService;
	@Autowired
	private QzFeignClient qzFeignClient;
	
	private final static Logger logger = LoggerFactory.getLogger(VesselContainerServiceImpl.class);
	/**
	 * 分页获取所有船舶集装箱
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselContainerVo
	 * @return
	 */
//	@Override
//	public PageVo<VesselContainerVo> getPageVesselContainerList(VesselContainerVo vesselContainer) {
//		/*
//		PageVo<VesselContainerVo> pageVo = new PageVo<VesselContainerVo>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("vesselContainer", vesselContainer);
//		//箱状态 0: 未理货 1：已理货 4：理货异常
//		String containerType = vesselContainer.getContainerType();
//		if(null !=containerType && containerType.length() >0) {
//			String[] cStatus = containerType.split(",");
//			map.put("cStatus", cStatus);
//		}
//		//作业类型：DS：卸船 DD：直提 LD：装船 DL：直装 LN：捣卸 RS：捣装 SH：搬移 DT：中转不落地
//		String jobType = vesselContainer.getJobType();
//		if(null !=jobType && jobType.length() >0) {
//			String[] jType = jobType.split(",");
//			map.put("jType", jType);
//		}
//		int totalCount = vesselContainerDao.getCountVesselContainer(map);
//		if(totalCount >0) {			
//			pageVo.initPage(vesselContainer.getCurrentPage(), vesselContainer.getPageSize(), totalCount);
//			map.put("pageVo", pageVo);
//			pageVo.setList(vesselContainerDao.getPageVesselContainerList(map));
//		}
//		return pageVo;
//		*/
//		return wl_vesselContainerService.getPageVesselContainerList(vesselContainer);
//	}
	/**
	 * 匹配仓单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber
	 * @return
	 */
	/*@Override
	public List<VesselContainerVo> getVesselContainerByVesselNumber(String vesselNumber,String containerNumber) {
		return vesselContainerDao.getVesselContainerByVesselNumber(vesselNumber, containerNumber,null);
	}*/
	/**
	 * 匹配舱单（数据服务调用）
	 * 查询外理数据库
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselContainer 箱号
	 * @return
	 */
	@Override
	public boolean booleanVesselContainer(VesselContainerVo vesselContainer) {
		boolean isOk = false;
		//调用第三方外理的接口（亮哥）
		try {
			JSONObject reqObj = new JSONObject();
			reqObj.put("containers",vesselContainer.getContainerNumber().toUpperCase());
			reqObj.put("vesselVoyageNumber", vesselContainer.getVesselNumber());
			/**
			 * 用于标识是API调用比对舱单接口（API调用这个接口不会判断是否为特殊框，
			 * 数据服务调用这个接口会判断是否为特殊为，如果是特殊柜则直接将数据拦截到未处理里面人工处理）
			 */
			reqObj.put("isApi", true);	
			//作业类型：DS：卸船 DD：直提 LD：装船 DL：直装 LN：捣卸 RS：捣装 SH：搬移 DT：中转不落地
			if("LD".equals(vesselContainer.getJobType()) || "0".equals(vesselContainer.getJobType())) {
				reqObj.put("jobType", 0);
			}else {
				reqObj.put("jobType", 1);
			}
			//ResponseEntity<String> respMsg = asyncService.doPost(reqObj, "http://192.168.1.63:8070/etlservice/outer_link_vessel_container/container");
			ResponseEntity<String> respMsg = asyncService.doPost(reqObj, "/etlservice/outer_link_vessel_container/container");
			if(null !=respMsg) {
				 String objStr = respMsg.getBody();										
				 if(null !=objStr && !"".equals(objStr)) {
					 JSONObject returnObj = new JSONObject(objStr);
					 if(200 == returnObj.getInt("status")) {
						 isOk = true;
						 logger.info("*********************************匹配仓单 /etlservice/outer_link_vessel_container/container调用成功!*********************************");
						 //设置箱属性值
						 this.setVesselContainer(vesselContainer, returnObj.getJSONObject("data"));
					 }
				}
			}
		}catch(JSONException e) {
			logger.error("匹配仓单接口/etlservice/outer_link_vessel_container/container出错："+e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			logger.error("匹配仓单接口/etlservice/outer_link_vessel_container/container出错："+e.getMessage());
			e.printStackTrace();
		}
		return isOk;
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
	 * @param vesselContainer
	 * @param returnObj
	 */
	private void setVesselContainer(VesselContainerVo vesselContainer, JSONObject jsonObj) {
		//预配BAY
		try {
			vesselContainer.setVesselPosition(jsonObj.getString("vesselPosition"));
			//装货港
			vesselContainer.setPortLoading(jsonObj.getString("portLoading"));
			//卸货港
			vesselContainer.setPortDischarge(jsonObj.getString("portDischarge"));
			//目的港
			vesselContainer.setPortDestination(jsonObj.getString("portDestination"));
			//空重状态 重（F，full）,重（E，empty）
			vesselContainer.setStuffingStatus(jsonObj.getString("stuffingStatus"));
			//危险标志（0为非危险品，1为危险品）
			vesselContainer.setDangerSigns(jsonObj.getInt("dangerSigns"));
			//危险类型
			vesselContainer.setDangerClass(jsonObj.getString("dangerClass"));
			//箱类型（危品箱，普通箱，冷藏箱）
			vesselContainer.setContainerClass(jsonObj.getString("containerClass"));
			//华东系统船舶艘次号
			vesselContainer.setVoyageNo(jsonObj.getString("vesselVoyageNumberInDock"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取车顶号下拉列表
	 * @return
	 */
	@Override
	public Map<String,List<TruckInfoVo>> getTruckInfoList(List<VesselContainerVo> vesselContainerList){
		Map<String,List<TruckInfoVo>> map = new HashMap<String,List<TruckInfoVo>>();
		List<TruckInfoVo> newList = null;
		List<TruckInfoVo> tiList = vesselContainerDao.getTruckInfoList();
		logger.info("获取车顶号下拉列表:"+tiList.size());
		try {
			for(VesselContainerVo vc : vesselContainerList) {
				newList = new ArrayList<TruckInfoVo>();
				newList.addAll(tiList);
				logger.info("DataResult<List<TruckInfoVo>> data");
				DataResult<List<TruckInfoVo>> data = qzFeignClient.getQzTruckInfoList(vc.getJobType(), vc.getVesselNumber());
				logger.info("DataResult<List<TruckInfoVo>> data    2");
				if(null !=data) {
					List<TruckInfoVo> list = data.getData();
					if(null !=list && list.size() >0) {
						newList.addAll(list);
					}
				}
				map.put(vc.getCraneNum(), newList);
			}			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("获取泉州车顶号出错！");
			logger.error(e.getMessage());
		}
		return map;
	}
	/**
	 * 查询车顶号是否存在
	 * @return
	 */
	@Override
	public int getCountTruckNumber(String truckNumber) {
		if(null !=truckNumber && !"".equals(truckNumber.trim())) {
			return vesselContainerDao.getCountTruckNumber(truckNumber);
		}else {
			return 0;
		}
	}
	/**
	 * 获取集装箱在船上的位置
	 * 画到船图上的箱
	 * 1. 卸船未理货
	 * 2. 装船已理货
	 * @param vesselContainerVo
	 * @return
	 */
	@Override
	public List<Object> getVesselContainerList(List<VesselContainerVo> vesselContainerList) {
		List<Object> objList = new ArrayList<Object>();
		Map<String,Integer> portTypeMap = new HashMap<String,Integer>();
		VesselContainerVo vesselContainer = vesselContainerList.get(0);
		//获取作业数据
		List<VesselContainerVo> vcList = vesselContainerDao.getWorkRecordContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), vesselContainerList);
		
		List<VesselContainerVo> newVcList = new ArrayList<VesselContainerVo>();
		List<VesselContainerVo> errorVcList = new ArrayList<VesselContainerVo>();
		List<VesselContainerVo> nullErrorVcList = new ArrayList<VesselContainerVo>();
		int maxId = 0;	//获取最新BAY的ID
		if(null !=vcList && vcList.size() >0) {
			//调用这可接口获取箱属性
			List<VesselContainerVo> containerList = wl_vesselContainerService.getContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), "LD", vcList);
			String str0 = "0";
			String str100 = "100";
			for(VesselContainerVo v : vcList) {
				if(str0.equals(v.getContainerType())) {
					v.setContainerType(str100);
				}
				if(null !=v.getContainerNumber() && !"".equals(v.getContainerNumber())) {
					if(7 == v.getStdBay().length()) {
						newVcList.add(v);
					}else {
						errorVcList.add(v);	//异常BAY箱
					}										
				}
			}			
			if(newVcList.size() >0) {		
				//List<VesselContainerVo> containerList = wl_vesselContainerService.getContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), "LD", newVcList);
				String bay = null;
				for(VesselContainerVo vc : newVcList) {
					if(maxId < vc.getId()) {
						maxId = vc.getId();
					}
					bay = vc.getStdBay().trim();
					if(7 == bay.length()) {
						vc.setStdBay(bay.substring(0,3));
						vc.setStdRow(bay.substring(3,5));
						vc.setStdTier(bay.substring(5));						
					}else {
						vc.setBayState(0); //0表示实装BAY为空
					}
					if(null !=containerList && containerList.size() >0) {
						this.setVesselContainerVo(vc, containerList,bay,portTypeMap);
					}
				}
			}
			if(errorVcList.size() >0) {
				int cSize = containerList.size();
				for(VesselContainerVo vc : errorVcList) {
					for(int i=0; i<cSize; i++) {
						if(vc.getContainerNumber().equals(containerList.get(i).getContainerNumber())) {
							vc.setSizeType(containerList.get(i).getSizeType());
							break;
						}
					}
				}
			}
			
		}
		
		//获取占位箱
		List<VesselContainerVo> containerSeizeSeatList = vesselContainerDao.getContainerSeizeSeatList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), vesselContainerList);
		boolean isOk = true;
		for(VesselContainerVo vc : vesselContainerList) {
			//List<VesselContainerVo> newList = this.getVcList(vc.getStdBay(), vcList);
			List<VesselContainerVo> newList = this.getVcList(vc.getStdBay(), newVcList,maxId);
			List<VesselContainerVo> newcSList = this.getVcList(vc.getStdBay(), containerSeizeSeatList,0);
			if(isOk) {
				objList.add(getVesselContainerMap(vc, newList, newcSList, errorVcList));
				isOk = false;
			}else {
				objList.add(getVesselContainerMap(vc, newList, newcSList, nullErrorVcList));				
			}
		}
		return objList;
	}
	private List<VesselContainerVo> getVcList(String bay, List<VesselContainerVo> vcList,int maxId){
		List<VesselContainerVo> list = new ArrayList<VesselContainerVo>();
		if(null !=bay && vcList.size()>0) {
			int bayLength = bay.length();
			for(VesselContainerVo v : vcList) {
				if(maxId >0 && maxId == v.getId()) {
					v.setLatestBay(true); //标识最新BAY位箱
				}
				if(bay.equals(v.getStdBay().substring(0,bayLength))) {
					list.add(v);
				}
			}
		}
		return list;
	}
		
	/**
	 * 获取集装箱在船上的位置
	 * 画到船图上的箱
	 * 2019-11-12 更新
	 * @param vesselContainerVo  作业数据
	 * @param containerSeizeSeatList  占位箱
	 * @return
	 */
	private Map<String,List<VesselContainerVo>> getVesselContainerMap(VesselContainerVo vesselContainerVo, List<VesselContainerVo> vcList, List<VesselContainerVo> containerSeizeSeatList,List<VesselContainerVo> errorVcList){
		Map<String,List<VesselContainerVo>> map = new HashMap<String,List<VesselContainerVo>>();
	//	List<VesselContainerVo> newVcList = new ArrayList<VesselContainerVo>();
		//异常BAY箱
		//List<VesselContainerVo> errorVcList = new ArrayList<VesselContainerVo>();
		//甲板上的VesselContainerVo
		List<VesselContainerVo> vcListD = new ArrayList<VesselContainerVo>();
		//甲板下的VesselContainerVo
		List<VesselContainerVo> vcListH = new ArrayList<VesselContainerVo>();
		
		//获取箱数据
		/*
		if(null !=vcList && vcList.size() >0) {
			for(VesselContainerVo v : vcList) {
				if(null !=v.getContainerNumber() && !"".equals(v.getContainerNumber())) {
					if(7 == v.getStdBay().length()) {
						newVcList.add(v);
					}else {
						errorVcList.add(v);	//异常BAY箱
					}										
				}
			}
			if(newVcList.size() >0) {
				List<VesselContainerVo> containerList = wl_vesselContainerService.getContainerList(vesselContainerVo.getVesselNumber(), vesselContainerVo.getVesselCode(), "LD", newVcList);
				String bay = null;
				for(VesselContainerVo vc : newVcList) {
					bay = vc.getStdBay().trim();
					if(7 == bay.length()) {
						vc.setStdBay(bay.substring(0,3));
						vc.setStdRow(bay.substring(3,5));
						vc.setStdTier(bay.substring(5));						
					}else {
						vc.setBayState(0); //0表示实装BAY为空
					}
					if(null !=containerList && containerList.size() >0) {
						this.setVesselContainerVo(vc, containerList,bay);
					}
				}
			}
		}
		*/
			
		if(null !=containerSeizeSeatList && containerSeizeSeatList.size()>0) {
			vcList.addAll(containerSeizeSeatList);
		}
		if(vcList.size() >0) {	
			for(VesselContainerVo vc : vcList) {
				//格式化作业类型DS：卸船 DD：直提 LD：装船 DL：直装 LN：捣卸 RS：捣装 SH：搬移 DT：中转不落地
				//vc.setJobType(this.fomatJobType(vc.getJobType()));
				//甲板下的层小于82
				if(null !=vc.getStdTier() && Integer.parseInt(vc.getStdTier()) < 82) {
					vcListH.add(vc);
				}else {
					vcListD.add(vc);					
				}
			}
		}
		map.put("vesselContainerListD", vcListD);
		map.put("vesselContainerListH", vcListH);
	//	map.put("errorVcList", this.errorVcListSort(errorVcList));
		map.put("errorVcList", errorVcList);
		return map;
	}
	/*
	 * 按作业时间从大到小排序
	 * @param list
	 * @return
	 *
	private List<VesselContainerVo> errorVcListSort(List<VesselContainerVo> list) {
		if(null !=list && list.size()>1) {
			List<String> dateList = new ArrayList<String>();
			for(VesselContainerVo vo : list) {
				dateList.add(vo.getCreateTime());
			}
			DateUtil.dateSort(dateList);			
			List<VesselContainerVo> newList = new ArrayList<VesselContainerVo>();
			for(String vo : dateList) {
				for(int i=0; i<list.size(); i++) {
					if(vo.equals(list.get(i).getCreateTime())) {
						newList.add(list.get(i));
						break;
					}
				}
			}
			if(newList.size() >0) {
				return newList;
			}else {
				return list;
			}
			
		}
		return list;
	}
	*/
	/**
	 * 设置箱数据
	 * @param vesselContainerVo
	 * @param containerList
	 */
	private void setVesselContainerVo(VesselContainerVo vesselContainerVo, List<VesselContainerVo> containerList,String bayInfo,Map<String,Integer> portTypeMap) {
		VesselContainerVo vc = null;
		String portDestination = null;
		for(int i=0; i<containerList.size(); i++) {
			vc = containerList.get(i);
			if(vesselContainerVo.getContainerNumber().equals(vc.getContainerNumber())) {
				vesselContainerVo.setCargoType(vc.getCargoType());
				vesselContainerVo.setSizeType(vc.getSizeType());
				vesselContainerVo.setContainerType(vc.getContainerType());
				vesselContainerVo.setJobType(vc.getJobType());
				vesselContainerVo.setWeight(vc.getWeight());
				vesselContainerVo.setVesselPosition(vc.getVesselPosition());
				
				if(bayInfo.equals(vc.getVesselPosition())) {
					vesselContainerVo.setBayState(2); //2表示实装BAY不为空并且和预配BAY相等
				}else {
					vesselContainerVo.setBayState(1);//1表示实装BAY不为空，并且和预配BAY不相等
				}
				
				//目的港
				portDestination = vc.getPortDestination();
				//设置目地港口颜色类型
				if(null !=portDestination) {
					vesselContainerVo.setPortDestination(portDestination);
					if(portTypeMap.containsKey(portDestination)) {
						vesselContainerVo.setPortType(portTypeMap.get(portDestination));
					}else {
						vesselContainerVo.setPortType(portTypeMap.keySet().size()+1);
						portTypeMap.put(portDestination, portTypeMap.keySet().size()+1);
					}
				}
				break;
			}
		}
	}
	
	/**
	 * 判断集装箱是否在船上
	 * false表在集合里找不到，该集装箱在船上
	 * true表在集合里存在，该集装箱不在船上
	 * @param containerNumber
	 * @param workRecordList
	 * @return
	 */
	/*private boolean ContainerExist(String containerNumber, List<WorkRecordVo> workRecordList) {
		boolean isExist = false;
		if(null !=workRecordList && workRecordList.size() >0) {
			for(int i=0; i<workRecordList.size(); i++) {
				if(containerNumber.equals(workRecordList.get(i).getContaid())) {
					isExist = true;
				}
			}
		}
		return isExist;
	}*/
	/**
	 * 格式化作业类型
	 * 作业类型：DS：卸船 DD：直提 LD：装船 DL：直装 LN：捣卸 RS：捣装 SH：搬移 DT：中转不落地
	 * @param jobType
	 * @return
	 */
	/*private String fomatJobType(String jobType) {
		//作业类型：DS：卸船 DD：直提 LD：装船 DL：直装 LN：捣卸 RS：捣装 SH：搬移 DT：中转不落地
		switch(jobType){
	    case "DS" :
	       jobType =  "卸船";
	       break; 
	    case "DD" :
	    	jobType =  "直提";
	       break; 
	    case "LD" :
	    	jobType =  "装船";
	    	break; 
	    case "DL" :
	    	jobType =  "直装";
	    	break; 
	    case "LN" :
	    	jobType =  "捣卸";
	    	break; 
	    case "RS" :
	    	jobType =  "捣装";
	    	break; 
	    case "SH" :
	    	jobType =  "搬移";
	    	break; 
	    case "DT" :
	    	jobType =  "中转不落地";
	    	break; 
	    default : 
	    	jobType =  "未知";
	}
		return jobType;
	}*/


	/**
	 * 新增贝位
	 * @param vesselContainer
	 * @return
	 */
	/*@Transactional
	@Override
	public int insertBayInfo(VesselContainerVo vesselContainer) {
		return vesselContainerDao.insertBayInfo(vesselContainer);
	}*/

	/**
	 * 更新贝位
	 * @param vesselContainer
	 * @return
	 */
	@Transactional
	@Override
	public int updateBayInfo(VesselContainerVo vesselContainer) {
		WorkRecordVo workRecord = new WorkRecordVo();
		workRecord.setCraneNum(vesselContainer.getCraneNum());
		workRecord.setVesselCode(vesselContainer.getVesselCode());
		workRecord.setVesselNumber(vesselContainer.getVesselNumber());
		workRecord.setUpdateContaid(vesselContainer.getContainerNumber());
		workRecord.setBayInfo(vesselContainer.getStdBay()+vesselContainer.getStdRow()+vesselContainer.getStdTier());
		workRecord.setUpdateBayCount(1);
		//先查询该BAY是否存在，如果存在则不能更新
		int count = workRecordService.getCountBay(workRecord);
		if(0 == count) {
			//更新作业记录表（"work_record"）里Bay
			return vesselContainerSeizeSeatDao.updateBayInfo(workRecord);
		}else {
			return 0;
		}
	}
}
