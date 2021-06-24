package net.pingfang.serviceImpl.bay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.pingfang.dao.bay.BayDao;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.service.bay.HandlerBayListService;
import net.pingfang.service.ocr.WL_VesselBayService;
import net.pingfang.service.ocr.WL_VesselContainerService;
import net.pingfang.service.workRecord.CranePreparationService;

@Service
public class HandlerBayListServiceImpl implements HandlerBayListService {
	
	@Autowired
	private BayDao bayDao;
	@Autowired
	private CranePreparationService cranePreparationService;
	@Autowired
	private WL_VesselContainerService wl_vesselContainerService;
	//外理数据库API
	@Autowired
	private WL_VesselBayService wl_vesselBayService;
	
	/**
	 * 组装船图结构
	 */
	@Override
	public Map<String,Object> getBatchBayList(List<VesselBayVo> vesselBayList) {
		//返回给前端的MAP
		Map<String,Object> returnMap = new HashMap<String, Object>();
		//前端提交过来的查询条件
		Map<String,List<VesselBayVo>> craneNumMap = new HashMap<String, List<VesselBayVo>>();
		
		List<VesselBayVo> bayList = null;
		List<String> craneNumList = new ArrayList<String>();
		String craneNum = null;
		//按船进行分组
		for(VesselBayVo vb : vesselBayList) {
			craneNum = vb.getCraneNum();
			if(0 == craneNumMap.size() || !craneNumMap.containsKey(craneNum)) {
				bayList = new ArrayList<VesselBayVo>();
				bayList.add(vb);
				craneNumList.add(craneNum);
				craneNumMap.put(craneNum, bayList);
			}else {
				bayList = craneNumMap.get(craneNum);
				bayList.add(vb);
				craneNumMap.put(craneNum, bayList);
			}
		}
		//通过岸桥编号获取岸桥配制
		List<CranePreparationVo> cnList = cranePreparationService.getListCranePreparation(craneNumList, "1");
		//BAY位数据		
		Map<String,Object> objMap = null;
		for(Map.Entry<String, List<VesselBayVo>> entry : craneNumMap.entrySet()) {
			craneNum = entry.getKey();
			objMap = this.getBayList(entry.getValue());
			for(int i=0; i<cnList.size(); i++) {
				if(craneNum.equals(cnList.get(i).getCraneNum())) {
					objMap.put(craneNum, cnList.get(i));
					break;
				}
			}
			// 通过bay和VesselCode获取船图结构
			returnMap.put(entry.getKey(), objMap);
		}
		return returnMap;
	}
	
	
	/**
	 * 获取船图结构数据
	 * 2020-09-9
	 * @param vesselBayList
	 */
	private Map<String,Object> getBayList(List<VesselBayVo> vesselBayList) {
		Map<String,Object> objMap = null;
		if(null !=vesselBayList && vesselBayList.size() >0) {
			if(2 == vesselBayList.size()) {
				//对BAY位排序，从小到大
				if(Integer.parseInt(vesselBayList.get(0).getBay()) > Integer.parseInt(vesselBayList.get(1).getBay())) {
					List<VesselBayVo> sortBayList = new ArrayList<VesselBayVo>();
					sortBayList.add(vesselBayList.get(1));
					sortBayList.add(vesselBayList.get(0));
					vesselBayList.clear();
					vesselBayList.addAll(sortBayList);
				}
			}
			
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
				Map<String,Map<String,Object>> returnBayMap = new LinkedHashMap<String, Map<String,Object>>();
				List<String> maxOrMinRowList = new ArrayList<String>();
				boolean isOk = false;
				for(VesselBayVo vb : vesselBayList) {
					Map<String,Object> map = this.getVesselBayList(bayMap.get(vb.getBay()));
					if(null !=map) {
						maxOrMinRowList.add(map.get("maxRow").toString());
						maxOrMinRowList.add(map.get("minRow").toString());
						returnBayMap.put(vb.getBay(), map);
						if(!isOk) {
							objMap.put("tierListD", map.get("tierListD"));
							objMap.put("tierListH", map.get("tierListH"));
							isOk = true;
						}
						
					}				
				}
				for(String key : returnBayMap.keySet()) {
					System.out.println(key);
				}
				objMap.put("bayMap", returnBayMap);
				objMap.put("maxOrMinRowList", maxOrMinRowList);
			}			
		}
		return objMap;
	}
	/**
	 * 组装显示在BAY位图上的箱数据
	 */
	@Override
	public Map<String,Object> getBatchBayInfoList(List<VesselContainerVo> vesselContainerList) {
		Map<String,VesselContainerVo> cnMap = new HashMap<String,VesselContainerVo>();
		//格式化查询条件所有船BAY
		this.formatVesselContainerList(vesselContainerList,cnMap);
		//获取作业数据
		List<VesselContainerVo> vcList = bayDao.getBatchBayInfoList(vesselContainerList);
		//获取占位箱
		List<VesselContainerVo> containerSeizeSeatList =bayDao.getBatchContainerSeizeSeatList(vesselContainerList);
		//用船区分每个桥的BAY
		Map<String,List<VesselContainerVo>> bayInfoMap = new HashMap<String,List<VesselContainerVo>>();
		String vesselNumber = null;
		List<VesselContainerVo> bayInfoList = null;
		VesselContainerVo vcVo = null;
		List<String> bayList = null;
		boolean isOk = false;
		if(null !=vcList && vcList.size() >0) {
			for(VesselContainerVo vc : vcList) {
				vesselNumber = vc.getVesselNumber();
				if(!bayInfoMap.containsKey(vesselNumber)) {
					bayInfoList = new ArrayList<VesselContainerVo>();
					bayInfoList.add(vc);
					bayInfoMap.put(vesselNumber, bayInfoList);
				}else {
					bayInfoMap.get(vesselNumber).add(vc);
				}
			}
		}
		//将占位箱按船分类
		Map<String,List<VesselContainerVo>> cssMap = new HashMap<String,List<VesselContainerVo>>();
		List<VesselContainerVo> cssList = null;
		List<VesselContainerVo> newCssList = null;
		if(null !=containerSeizeSeatList && containerSeizeSeatList.size() >0) {
			for(VesselContainerVo css : containerSeizeSeatList) {
				vesselNumber = css.getVesselNumber();
				if(!cssMap.containsKey(vesselNumber)) {
					cssList = new ArrayList<VesselContainerVo>();
					cssList.add(css);
					cssMap.put(vesselNumber, cssList);
				}else {
					cssMap.get(vesselNumber).add(css);
				}
			}
		}
		//返回到前端的Map
		Map<String,Object> craneNumMap = new HashMap<String,Object>();
		String newVesselNumber = null;
		List<VesselContainerVo> valList = null;
		List<VesselContainerVo> newValList = null;
		
		for(Map.Entry<String, VesselContainerVo> entry : cnMap.entrySet()) {
			vcVo = entry.getValue();
			if(null == newVesselNumber || !newVesselNumber.equals(vcVo.getVesselNumber())) {
				newVesselNumber = vcVo.getVesselNumber();
				if(bayInfoMap.containsKey(newVesselNumber)) {
					valList = bayInfoMap.get(newVesselNumber);
				}else {
					if(null !=valList) {
						valList.clear();
					}
				}
			}
			bayList = vcVo.getBayList();
			newValList = new ArrayList<VesselContainerVo>();
			if(null !=valList && valList.size() >0) {
				for(VesselContainerVo vcv : valList) {
					isOk = false;
					for(int i=0; i<bayList.size(); i++) {
						if(vcv.getStdBay().substring(0,3).equals(bayList.get(i))) {
							isOk = true;
							break;
						}
					}
					if(isOk) {
						newValList.add(vcv);
					}
				}
			}
			//处理占位箱
			newCssList = new ArrayList<VesselContainerVo>();
			if(cssMap.containsKey(newVesselNumber)) {
				cssList = cssMap.get(newVesselNumber);
				if(null !=cssList && cssList.size() >0) {
					for(VesselContainerVo css : cssList) {
						isOk = false;
						for(int i=0; i<bayList.size(); i++) {
							if(css.getStdBay().equals(bayList.get(i))) {
								isOk = true;
								break;
							}
						}
						if(isOk) {
							newCssList.add(css);
						}
					}
				}
			}
			craneNumMap.put(entry.getKey(), this.getBayInfoList(this.getStdBayList(vcVo), newValList, newCssList));
		}
		return craneNumMap;
	}
	/**
	 * 格式化查询条件
	 * @param vesselContainerList
	 * @return
	 */
	private void formatVesselContainerList(List<VesselContainerVo> vesselContainerList,Map<String,VesselContainerVo> craneNumMap){
		//查询条件所有船BAY
		List<VesselContainerVo> allList = new ArrayList<VesselContainerVo>();
		List<String> list = new ArrayList<String>();
		String vesselNumber = null;
		String craneNum= null;
		
		List<String> bayList = null;
		List<String> bList = null;
		
		boolean isOk = true;
		for(VesselContainerVo vc : vesselContainerList) {
			vesselNumber = vc.getVesselNumber();
			craneNum = vc.getCraneNum();
			//对BAY进行加1减1
			this.getStdBay(vc);
			
			if(!craneNumMap.containsKey(craneNum)) {
				craneNumMap.put(craneNum, vc);
			}else {
				bayList = vc.getBayList();
				bList = craneNumMap.get(craneNum).getBayList();
				//去重
				for(String b : bayList) {
					isOk = true;
					for(int j=0; j<bList.size(); j++) {
						if(b.equals(bList.get(j))) {
							isOk = false;
							break;
						}
					}
					if(isOk) {
						bList.add(b);
					}							
				}
				craneNumMap.get(craneNum).setBayList(bList);
			}
			
			if(!list.contains(vesselNumber)) {
				allList.add(vc);
				list.add(vesselNumber);
			}else {
				for(int i=0; i<allList.size(); i++) {
					if(vesselNumber.equals(allList.get(i).getVesselNumber())){
						bayList = vc.getBayList();
						bList = allList.get(i).getBayList();
						//去重
						for(String b : bayList) {
							isOk = true;
							for(int j=0; j<bList.size(); j++) {
								if(b.equals(bList.get(j))) {
									isOk = false;
									break;
								}
							}
							if(isOk) {
								bList.add(b);
							}							
						}
						allList.get(i).setBayList(bList);
						break;
					}
				}
			}			
		}
		vesselContainerList.clear();
		vesselContainerList.addAll(allList);
	}
	
	/**
	 * 对BAY进行加1减1
	 * @param vc
	 * @return
	 */
	private void getStdBay(VesselContainerVo vc) {
		List<String> list = new ArrayList<String>();
		list.add(vc.getStdBay());
		vc.setBayList(list);
		
		int bay = Integer.parseInt(vc.getStdBay());
		if(1 == bay) {
			vc.getBayList().add("00"+(bay +1));
		}else if(bay < 10 && bay >1){
			if(9 == bay) {
				vc.getBayList().add("0"+(bay +1));
			}else {
				vc.getBayList().add("00"+(bay +1));
			}
			vc.getBayList().add("00"+(bay -1));
		}else if(bay < 100){
			if(99 == bay) {
				vc.getBayList().add(""+(bay +1));
			}else {
				vc.getBayList().add("0"+(bay +1));
			}
			vc.getBayList().add("0"+(bay -1));
		}else{
			vc.getBayList().add(""+(bay +1));
			vc.getBayList().add(""+(bay -1));
		}
	}
	
	private List<VesselContainerVo> getStdBayList(VesselContainerVo vc){
		List<VesselContainerVo> list = new ArrayList<VesselContainerVo>();
		VesselContainerVo v = null;
		String vesselCode = vc.getVesselCode();
		String vesselNumber = vc.getVesselNumber();
		for(String bay : vc.getBayList()) {
			v = new VesselContainerVo();
			v.setStdBay(bay);
			v.setVesselCode(vesselCode);
			v.setVesselNumber(vesselNumber);
			list.add(v);
		}
		return list;
	}
	/**
	 * 获取集装箱在船上的位置
	 * 画到船图上的箱
	 * @param vesselContainerList
	 * @param vcList
	 * @param vesselContainerList
	 * @return
	 */
	private List<Object> getBayInfoList(List<VesselContainerVo> vesselContainerList, List<VesselContainerVo> vcList, List<VesselContainerVo> containerSeizeSeatList){
		List<Object> objList = new ArrayList<Object>();
		Map<String,Integer> portTypeMap = new HashMap<String,Integer>();
		VesselContainerVo vesselContainer = vesselContainerList.get(0);
		
		List<VesselContainerVo> newVcList = new ArrayList<VesselContainerVo>();
		List<VesselContainerVo> errorVcList = new ArrayList<VesselContainerVo>();
		List<VesselContainerVo> nullErrorVcList = new ArrayList<VesselContainerVo>();
		int maxId = 0;	//获取最新BAY的ID
		if(null !=vcList && vcList.size() >0) {
			//调用这可接口获取箱属性
			List<VesselContainerVo> containerList = wl_vesselContainerService.getContainerList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), "LD", vcList);
			VesselContainerVo newVc = null;
			String str0 = "0";
			String str100 = "100";
			for(VesselContainerVo v : vcList) {
				if(null !=v.getContainerNumber() && !"".equals(v.getContainerNumber())) {
					if(7 == v.getStdBay().length()) {
						newVc = new VesselContainerVo();
						newVc.setId(v.getId());
						newVc.setCraneNum(v.getCraneNum());
						newVc.setVesselNumber(v.getVesselNumber());
						newVc.setVesselCode(v.getVesselCode());
						if(str0.equals(v.getContainerType())) {
							newVc.setContainerType(str100);
						}else {
							newVc.setContainerType(v.getContainerType());
						}
						
						newVc.setContainerNumber(v.getContainerNumber());
						newVc.setIso(v.getIso());
						newVc.setStdBay(v.getStdBay());
						newVc.setCreateTime(v.getCreateTime());
						newVcList.add(newVc);
					}else {
						errorVcList.add(v);	//异常BAY箱
					}										
				}
			}			
			if(newVcList.size() >0) {		
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
		//List<VesselContainerVo> containerSeizeSeatList = vesselContainerDao.getContainerSeizeSeatList(vesselContainer.getVesselNumber(), vesselContainer.getVesselCode(), vesselContainerList);
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
		if(null !=bay && null !=vcList && vcList.size()>0) {
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
	/**
	 * 设置箱数据
	 * @param vesselContainerVo
	 * @param containerList
	 */
	private void setVesselContainerVo(VesselContainerVo vesselContainerVo, List<VesselContainerVo> containerList,String bayInfo,Map<String,Integer> portTypeMap) {
		VesselContainerVo vc = null;
		String portDestination = null;
		String portDischarge = null;
		for(int i=0; i<containerList.size(); i++) {
			vc = containerList.get(i);
			if(vesselContainerVo.getContainerNumber().equals(vc.getContainerNumber())) {
				vesselContainerVo.setCargoType(vc.getCargoType());
				//vesselContainerVo.setSizeType(vc.getSizeType());
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
					/*
					 * 2020-11-09
					 * 
					if(portTypeMap.containsKey(portDestination)) {
						vesselContainerVo.setPortType(portTypeMap.get(portDestination));
					}else {
						vesselContainerVo.setPortType(portTypeMap.keySet().size()+1);
						portTypeMap.put(portDestination, portTypeMap.keySet().size()+1);
					}*/
				}
				//2020-11-09更新按卸货港口颜色分类
				//卸货港 
				portDischarge = vc.getPortDischarge();
				//设置卸货港口颜色类型
				if(null !=portDischarge) {
					vesselContainerVo.setPortDischarge(portDischarge);
					if(portTypeMap.containsKey(portDischarge)) {
						vesselContainerVo.setPortType(portTypeMap.get(portDischarge));
					}else {
						vesselContainerVo.setPortType(portTypeMap.keySet().size()+1);
						portTypeMap.put(portDischarge, portTypeMap.keySet().size()+1);
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
	 * @return
	 */
	private Map<String,Object> getVesselBayList(List<VesselBayVo> allBayList){
		Map<String,Object> map = null;
		if(allBayList.size()>0) {
			map = new HashMap<String,Object>();
			//甲板上的BAY，层，排
			List<VesselBayVo> bayListD = null;
			List<String> tierListD = null;//层数组
			//甲板下的BAY，层，排
			List<VesselBayVo> bayListH = null;
			List<String> tierListH = null; //层数组
			// 通过bay和VesselCode获取船图结构
			if(null !=allBayList && allBayList.size() >0) {
				//甲板上的BAY
				bayListD = new ArrayList<VesselBayVo>();
				//甲板下的BAY
				bayListH = new ArrayList<VesselBayVo>();
				String stdBay = null;
				
				tierListD = new ArrayList<String>();
				tierListH = new ArrayList<String>();
				String maxRow = "0";
				String minRow = "100000";
				for(VesselBayVo vesselBay : allBayList) {
					if(Integer.parseInt(maxRow) < Integer.parseInt(vesselBay.getRow())) {
						maxRow = vesselBay.getRow();
					}
					if(Integer.parseInt(minRow) > Integer.parseInt(vesselBay.getRow())) {
						minRow = vesselBay.getRow();
					}
					stdBay = vesselBay.getStdBay();
					//甲板上
					if(stdBay.indexOf("D") >0) {
						//去重
						if(!tierListD.contains(vesselBay.getTier())) {
							tierListD.add(vesselBay.getTier());
						}					
						bayListD.add(vesselBay);
					}else {//甲板下
						//去重
						if(!tierListH.contains(vesselBay.getTier())) {
							tierListH.add(vesselBay.getTier());
						}
						bayListH.add(vesselBay);
					}
				}
				if(tierListD.size() >1) {
					Collections.sort(tierListD);
				}
				if(tierListH.size() >1) {
					Collections.sort(tierListH);
				}
				//甲板上的BAY，层，排
				map.put("bayListD", bayListD);
				map.put("tierListD", tierListD);
				
				//甲板下的BAY，层，排
				map.put("bayListH", bayListH);
				map.put("tierListH", tierListH);
				
				map.put("maxRow", maxRow);
				map.put("minRow", minRow);
			}
		}
		return map;
	}
	
	
}
