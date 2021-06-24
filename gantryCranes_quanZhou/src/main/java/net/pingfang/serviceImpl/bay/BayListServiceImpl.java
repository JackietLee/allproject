package net.pingfang.serviceImpl.bay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pingfang.dao.bay.BayDao;
import net.pingfang.dao.vessel.VesselContainerDao;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.service.bay.BayListService;
import net.pingfang.service.ocr.WL_VesselBayService;
import net.pingfang.service.ocr.WL_VesselContainerService;

@Service
public class BayListServiceImpl implements BayListService {
	
	@Autowired
	private VesselContainerDao vesselContainerDao;
	@Autowired
	private WL_VesselContainerService wl_vesselContainerService;
	//外理数据库API
	@Autowired
	private WL_VesselBayService wl_vesselBayService;
	@Autowired
	private BayDao bayDao;
	
	/**
	 * 2020-01-10
	 * @param vesselBayList
	 */
	@Override
	public Map<String,Object> getBayList(List<VesselBayVo> vesselBayList) {
		Map<String,Object> objMap = null;
		if(null !=vesselBayList && vesselBayList.size() >0) {
			// 通过bay和VesselCode获取船图结构
			List<VesselBayVo> allBayList = wl_vesselBayService.getVesselSlotListByVesselCode(vesselBayList);
			if(null !=allBayList && allBayList.size() >0) {
				Map<String,List<VesselBayVo>> bayMap = new HashMap<String,List<VesselBayVo>>();
				for(VesselBayVo vbay : vesselBayList) {
					bayMap.put(vbay.getBay(),new ArrayList<VesselBayVo>());
				}
				//分类
				for(VesselBayVo vb : allBayList) {
					bayMap.get(vb.getBay()).add(vb);
				}				
				objMap = new HashMap<String, Object>();
				List<Object> objList = new ArrayList<Object>();
				List<String> maxOrMinRowList = new ArrayList<String>();
				Map<String,Object> returnMap = null;
				List<String> allTierList = new ArrayList<String>();
				for(VesselBayVo vb : vesselBayList) {
					Map<String,Object> map = this.getVesselBayList(bayMap.get(vb.getBay()),maxOrMinRowList,allTierList);
					
					if(null !=map) {
						//maxOrMinRowList.add(map.get("maxRow").toString());
						//maxOrMinRowList.add(map.get("minRow").toString());
						
						returnMap = new HashMap<String,Object>();
						returnMap.put(vb.getBay(), map);
						//objList.add(map);
						objList.add(returnMap);
					}				
				}
				objMap.put("objList", objList);
				objMap.put("maxOrMinRowList", maxOrMinRowList);
				objMap.put("allTierList", allTierList);
			}			
		}
		return objMap;
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
	public Map<String,Object> getBayInfoList(List<VesselContainerVo> vesselContainerList){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Integer> portTypeMap = new HashMap<String,Integer>();
		VesselContainerVo vesselContainer = vesselContainerList.get(0);
		//获取作业数据
		List<VesselContainerVo> vcList = vesselContainerDao.getWorkRecordContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), vesselContainerList);
		
		List<VesselContainerVo> newVcList = new ArrayList<VesselContainerVo>();
		if(null !=vcList && vcList.size() >0) {
			String str0 = "0";
			String str100 = "100";
			for(VesselContainerVo v : vcList) {
				if(null !=v.getContainerNumber() && !"".equals(v.getContainerNumber())) {
					if(str0.equals(v.getContainerType())) {
						v.setContainerType(str100);
					}
					if(7 == v.getStdBay().length()) {
						newVcList.add(v);
					}										
				}
			}
			
			if(newVcList.size() >0) {
				
//				List<VesselContainerVo> containerList = wl_vesselContainerService.getContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), "LD", newVcList);
				//箱属性集合
				List<VesselContainerVo> containerList = new ArrayList<VesselContainerVo>();	
				//请求条件集合
				List<VesselContainerVo> reqVcList = new ArrayList<VesselContainerVo>();
				//计数
				int i = 1;
				for(VesselContainerVo v : newVcList) {
					reqVcList.add(v);
					//一次查询500条数据
					if(100 == i) {
						containerList.addAll(wl_vesselContainerService.getContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), "LD", reqVcList));
						i = 0;
						reqVcList.clear();
					}
					i++;
				}
				//如果还有没发请求的数据则再次发请求查询
				if(!reqVcList.isEmpty()) {
					containerList.addAll(wl_vesselContainerService.getContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), "LD", reqVcList));
				}
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
						this.setVesselContainerVo(vc, containerList,bay,portTypeMap);
					}
				}
			}
		}
		
		//获取占位箱
		List<VesselContainerVo> containerSeizeSeatList = vesselContainerDao.getContainerSeizeSeatList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), vesselContainerList);
		
		//List<Object> objList = new ArrayList<Object>();
		for(VesselContainerVo vc : vesselContainerList) {
			List<VesselContainerVo> newList = this.getVcList(vc.getStdBay(), newVcList);
			List<VesselContainerVo> newcSList = this.getVcList(vc.getStdBay(), containerSeizeSeatList);
			map.put(vc.getStdBay(), getVesselContainerMap(vc, newList, newcSList));
		}
		//map.put("portTypeMap", portTypeMap);
		return map;
	}
	
	private List<VesselContainerVo> getVcList(String bay, List<VesselContainerVo> vcList){
		List<VesselContainerVo> list = new ArrayList<VesselContainerVo>();
		if(null !=bay && vcList.size()>0) {
			int bayLength = bay.length();
			for(VesselContainerVo v : vcList) {
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
	private Map<String,List<VesselContainerVo>> getVesselContainerMap(VesselContainerVo vesselContainerVo, List<VesselContainerVo> vcList, List<VesselContainerVo> containerSeizeSeatList){
		Map<String,List<VesselContainerVo>> map = new HashMap<String,List<VesselContainerVo>>();
		//甲板上的VesselContainerVo
		List<VesselContainerVo> vcListD = new ArrayList<VesselContainerVo>();
		//甲板下的VesselContainerVo
		List<VesselContainerVo> vcListH = new ArrayList<VesselContainerVo>();
		
			
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
		return map;
	}
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
				vesselContainerVo.setContainerClass(vc.getContainerType());	//箱类型（危品箱，普通箱，冷藏箱）
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
				/*
				vesselContainerVo.setPortLoading(vc.getPortLoading());				//装货港
				vesselContainerVo.setPortDischarge(vc.getPortDischarge());			//卸货港
				vesselContainerVo.setStuffingStatus(vc.getStuffingStatus());		//空重状态 重（F，full）,重（E，empty）
				vesselContainerVo.setDangerSigns(vc.getDangerSigns());  			//危险标志（0为非危险品，1为危险品）
				vesselContainerVo.setDangerClass(vc.getDangerClass());				//危险类型
				*/
				break;
			}
		}
	}

	
	/**
	 * 通过bay和VesselCode获取船图结构
	 * @param allBayList bay位
	 * @param maxOrMinRowList 最大行和最小行
	 * @param allTierList 去重后的所有层
	 * @return
	 */
	private Map<String,Object> getVesselBayList(List<VesselBayVo> allBayList,List<String> maxOrMinRowList,List<String> allTierList){
		Map<String,Object> map = null;
		if(allBayList.size()>0) {
			map = new HashMap<String,Object>();
			//甲板上的BAY，层，排
			List<VesselBayVo> bayListD = null;
//			List<String> tierListD = null;//层数组
			//int rowCountD = 0;  //排
			//甲板下的BAY，层，排
			List<VesselBayVo> bayListH = null;
//			List<String> tierListH = null; //层数组
			//int rowCountH = 0;  //排
			// 通过bay和VesselCode获取船图结构
			if(null !=allBayList && allBayList.size() >0) {
				//甲板上的BAY
				bayListD = new ArrayList<VesselBayVo>();
				//甲板下的BAY
				bayListH = new ArrayList<VesselBayVo>();
				String stdBay = null;
				
//				tierListD = new ArrayList<String>();
//				tierListH = new ArrayList<String>();
				
				//List<String> allTierList = new ArrayList<String>();
				String maxRow = "0";
				String minRow = "100000";
				for(VesselBayVo vesselBay : allBayList) {
					if(Integer.parseInt(maxRow) < Integer.parseInt(vesselBay.getRow())) {
						maxRow = vesselBay.getRow();
					}
					if(Integer.parseInt(minRow) > Integer.parseInt(vesselBay.getRow())) {
						minRow = vesselBay.getRow();
					}
					//boolean rIsOK = false;
					stdBay = vesselBay.getStdBay();
					//甲板上
					if(stdBay.indexOf("D") >0) {
						//去重
//						if(!tierListD.contains(vesselBay.getTier())) {
//							tierListD.add(vesselBay.getTier());
//						}
						bayListD.add(vesselBay);
					}else {//甲板下
						//去重
//						if(!tierListH.contains(vesselBay.getTier())) {
//							tierListH.add(vesselBay.getTier());
//						}
						bayListH.add(vesselBay);
					}
					if(!allTierList.contains(vesselBay.getTier())) {
						allTierList.add(vesselBay.getTier());
					}
				}
//				if(tierListD.size() >1) {
//					Collections.sort(tierListD);
//				}
//				if(tierListH.size() >1) {
//					Collections.sort(tierListH);
//				}
				Collections.sort(allTierList);
				//甲板上的BAY，层，排
				map.put("bayListD", bayListD);
//				map.put("tierListD", tierListD);
				//甲板下的BAY，层，排
				map.put("bayListH", bayListH);
//				map.put("tierListH", tierListH);
//				map.put("maxRow", maxRow);
//				map.put("minRow", minRow);
				maxOrMinRowList.add(maxRow);
				maxOrMinRowList.add(minRow);
//				map.put("allTierList", allTierList);
			}
		}
		return map;
	}
	
	
	/**
	 * 根据船舶艘次号查询BAY总数
	 * @param vesselNumber
	 * @return
	 */
	@Override
	public int getCountBayByVesselNumber(String vesselNumber) {
		return bayDao.getCountBayByVesselNumber(vesselNumber);
	}
	/**
	 * 根据船舶艘次号删除BAY数据
	 * @param vesselNumber
	 * @return
	 */
	@Override
	public int deleteBayByVesselNumber(String vesselNumber) {
		return bayDao.deleteBayByVesselNumber(vesselNumber);
	}
	/**
	 * 批量插入船图结构数据
	 * @param vesselBayList
	 * @return
	 */
	@Override
	public int insertVesselBayList(List<VesselBayVo> vesselBayList) {
		//查询船上是否有BAY
		int count = this.getCountBayByVesselNumber(vesselBayList.get(0).getVesselNumber());
		if(count >0) {
			//删除船上的BAY
			this.deleteBayByVesselNumber(vesselBayList.get(0).getVesselNumber());
		}
		//新增
		count = bayDao.insertVesselBayList(vesselBayList);
		return count;
	}
	
	
}
