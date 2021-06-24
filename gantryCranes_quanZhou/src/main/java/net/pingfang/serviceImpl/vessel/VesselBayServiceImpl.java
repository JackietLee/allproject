package net.pingfang.serviceImpl.vessel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.pingfang.dao.vessel.VesselBayDao;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.ocr.WL_VesselBayService;
import net.pingfang.service.vessel.VesselBayService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.service.workRecord.CranePreparationService;
import net.pingfang.service.workRecord.WorkRecordService;

/**
 * 船舶Bay信息ServiceImpl
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Service
public class VesselBayServiceImpl implements VesselBayService {
	@Autowired
	private VesselBayDao vesselBayDao;
	
	//外理数据库API
	@Autowired
	private WL_VesselBayService wl_vesselBayService;
	
	@Autowired
	private CranePreparationService cranePreparationService;
	
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private VesselContainerService vesselContainerService;
	
	//private final static Logger logger = LoggerFactory.getLogger(VesselBayServiceImpl.class);
	/**
	 * 通过岸桥编号获取船舶Bay信息
	 * @return
	 */
	@Override
	public List<VesselBayVo> getVesselSlotListByCraneNum(String craneNum){
		List<VesselBayVo> vesselBayList = null;
		List<CranePreparationVo> list = cranePreparationService.getCranePreparation(craneNum);
		if(null !=list && list.size()>0) {
			List<String> bayList = new ArrayList<String>();
			for(CranePreparationVo cp : list) {
				bayList.add(cp.getBay());
			}
			vesselBayList = wl_vesselBayService.getVesselSlotBayList(list.get(0).getVesselCode(),bayList);
		}
		return vesselBayList;
	}
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	@Override
	public List<String> getAlongsideByVesselCood(String vesselCood) {		
		return wl_vesselBayService.getAlongsideByVesselCood(vesselCood);
	}	
	/**
	 * 获取所有岸桥信息
	 * @return
	 */
	@Override
	public List<CraneInfoVo> getCraneInfoList(){
		return vesselBayDao.getSelectCraneInfoList();
	}
	/**
	 * 获取所有等待作业的船舶信息
	 * 0是表示等待作业，1是作业已完成("is_finished = 0")
	 * @return
	 */
	@Override
	public List<BerthPlanInfoVo> getIsFinishedBerthPlanList(BerthPlanInfoVo berthPlanInfoVo){
		/**
		List<BerthPlanInfoVo> berthPlanInfoList = wl_vesselBayService.getIsFinishedBerthPlanList(berthPlanInfoVo);
		if(null !=berthPlanInfoList && berthPlanInfoList.size() >0) {
			List<String> timeList = new ArrayList<String>();
			Map<String,BerthPlanInfoVo> map = new HashMap<String,BerthPlanInfoVo>();
			String t = "T";
			String str = " ";
			for(BerthPlanInfoVo bp : berthPlanInfoList) {
				String time = bp.getUpdateTime();
				time = time.replace(t, str);
				timeList.add(time);
				map.put(time, bp);
			}
			//按照日期从大到小排序
			DateUtil.dataSort(timeList);
			//清空
			berthPlanInfoList.clear();
			//添加返回到前端的数据
			for(String dt : timeList) {
				berthPlanInfoList.add(map.get(dt));
			}
		}
		return berthPlanInfoList;
		*/
		return wl_vesselBayService.getIsFinishedBerthPlanList(berthPlanInfoVo);
	}
	/**
	 *  获取船舶Bay下拉列表
	 * @return
	 */
	@Override
	public List<VesselBayVo> getBayListByVesselCode(String vesselCode){
		List<VesselBayVo> bayList = wl_vesselBayService.getBayListByVesselCode(vesselCode);
		if(null !=bayList && bayList.size() >0) {
			List<VesselBayVo> bList = new ArrayList<VesselBayVo>();
			String bay = null;
			//移除tmov偶数
			for(VesselBayVo vesselBay : bayList) {
				bay = vesselBay.getBay();
				if(null !=bay && Integer.parseInt(bay) %2 >0) {
					bList.add(vesselBay);
				}
			}
			bayList.clear();
			bayList.addAll(bList);
		}
		return bayList;
	}
	
	
	/**
	 * 2020-01-10
	 * @param vesselBayList
	 */
	@Override
	public Map<String,Object> getVesselBayMap(List<VesselBayVo> vesselBayList) {
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
				for(VesselBayVo vb : vesselBayList) {
					Map<String,Object> map = this.getVesselBayList(bayMap.get(vb.getBay()));
					if(null !=map) {
						maxOrMinRowList.add(map.get("maxRow").toString());
						maxOrMinRowList.add(map.get("minRow").toString());
						objList.add(map);
					}				
				}
				objMap.put("objList", objList);
				objMap.put("maxOrMinRowList", maxOrMinRowList);
			}			
		}
		return objMap;
	}
	
	
	/**
	 * 获取船图结构
	 * 2020-01-11
	 * @param vesselBayList
	 */
	@Override
	public Map<String,Object> getVesselBayMapNew(List<VesselBayVo> vesselBayList) {
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
				String maxRow = "0";	//最大排
				String minRow = "0";	//最小排
				for(VesselBayVo vb : vesselBayList) {
					Map<String,Object> map = this.getVesselBayList(bayMap.get(vb.getBay()));
					if(null !=map) {
						Map<String,Object> newMap = new HashMap<String,Object>();
						newMap.put("bayList", this.getBayMap(map));		//所有BAY
						newMap.put("tierList", this.getTierList(map));	//所有层
						
						if(Integer.parseInt(maxRow) < Integer.parseInt(map.get("maxRow").toString())) {
							maxRow = map.get("maxRow").toString();
						}
						if(Integer.parseInt(minRow) > Integer.parseInt(map.get("minRow").toString())) {
							minRow = map.get("minRow").toString();
						}
						objList.add(newMap);
					}				
				}
				objMap.put("objList", objList);
				objMap.put("maxRow", maxRow);
				objMap.put("minRow", minRow);
			}			
		}
		return objMap;
	}
	private Map<String,String> getBayMap(Map<String,Object> map){
		Map<String,String> bayMap = new HashMap<String,String>();
		if(map.containsKey("bayListD")) {
			@SuppressWarnings("unchecked")
			List<VesselBayVo> bayListD = (List<VesselBayVo>)map.get("bayListD");
			if(bayListD.size() >0) {
				String str = null;
				for(VesselBayVo vb : bayListD) {
					str = vb.getBay()+vb.getRow()+vb.getTier();
					bayMap.put(str, str);
				}				
			}			
		}
		if(map.containsKey("bayListH")) {
			@SuppressWarnings("unchecked")
			List<VesselBayVo> bayListH = (List<VesselBayVo>)map.get("bayListH");
			if(bayListH.size() >0) {
				String strH = null;
				for(VesselBayVo vb : bayListH) {
					strH = vb.getBay()+vb.getRow()+vb.getTier();
					bayMap.put(strH, strH);
				}				
			}			
		}
		return bayMap;
	}
	//合并层
	private List<String> getTierList(Map<String,Object> map){
		List<String> list = new ArrayList<String>();
		if(map.containsKey("tierListD")) {
			@SuppressWarnings("unchecked")
			List<String> tierListD = (List<String>)map.get("tierListD");//层数组
			if(tierListD.size() >0) {
				list.addAll(tierListD);
			}
		}
		if(map.containsKey("tierListH")) {
			@SuppressWarnings("unchecked")
			List<String> tierListH = (List<String>)map.get("tierListH");//层数组
			if(tierListH.size() >0) {
				list.addAll(tierListH);
			}
		}
		return list;
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
			//int rowCountD = 0;  //排
			//甲板下的BAY，层，排
			List<VesselBayVo> bayListH = null;
			List<String> tierListH = null; //层数组
			//int rowCountH = 0;  //排
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
					//boolean rIsOK = false;
					stdBay = vesselBay.getStdBay();
					//甲板上
					if(stdBay.indexOf("D") >0) {
						//去重
						if(!tierListD.contains(vesselBay.getTier())) {
							tierListD.add(vesselBay.getTier());
						}					
					/*	if(bayListD.size() >0) {	
							rIsOK = this.rowIsOk(vesselBay.getRowNumber(), bayListD);
						}					
						if(!rIsOK) {
							rowCountD +=1;
						}*/
						bayListD.add(vesselBay);
					}else {//甲板下
						//去重
						if(!tierListH.contains(vesselBay.getTier())) {
							tierListH.add(vesselBay.getTier());
						}
						/*if(bayListH.size() >0) {						
							rIsOK = this.rowIsOk(vesselBay.getRowNumber(), bayListH);
						}					
						if(!rIsOK) {
							rowCountH +=1;
						}*/
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
				//map.put("tierCountD", tierListD.size());
				map.put("tierListD", tierListD);
				//map.put("rowCountD", rowCountD);
				//甲板下的BAY，层，排
				map.put("bayListH", bayListH);
				//map.put("tierCountH", tierListH.size());
				map.put("tierListH", tierListH);
			//	map.put("rowCountH", rowCountH);
				map.put("maxRow", maxRow);
				map.put("minRow", minRow);
				/*
				//甲板上的BAY，层，排
				System.out.println("bayListD:"+bayListD);
				System.out.println("tierCountD:"+tierListD.size());
				System.out.println("rowCountD:"+ rowCountD);
				//甲板下的BAY，层，排
				System.out.println("bayListH:"+ bayListH);
				System.out.println("tierCountH:"+ tierListH.size());
				System.out.println("rowCountH:"+ tierListH);
				*/
			}
		}
		return map;
	}
	/**
	 * 层
	 * @param tierNumber
	 * @param bayList
	 * @return
	 */
	/*private boolean tierIsOk(int tierNumber, List<VesselBayVo> bayList) {
		boolean tIsOK = false;
		for(int i=0; i<bayList.size(); i++) {
			if(tierNumber == bayList.get(i).getTierNumber()) {
				tIsOK = true;
				break;
			}
		}
		return tIsOK;
	}*/
	/**
	 * 排
	 * @param rowNumber
	 * @param bayList
	 * @return
	 */
	/*private boolean rowIsOk(int rowNumber, List<VesselBayVo> bayList) {
		boolean rIsOK = false;
		for(int i=0; i<bayList.size(); i++) {
			if(rowNumber == bayList.get(i).getRowNumber()) {
				rIsOK = true;
				break;
			}
		}
		return rIsOK;
	}*/
	
	
	public static void main(String[] args) {
		List<String> strList = new ArrayList<String>();
		strList.add("009");
		strList.add("011");

		int i = Integer.parseInt(strList.get(0));
		i +=1;
		String s = "00";
		if(i >9) {
			s = "0";
		}
		strList.add(s+i);
		for(String str : strList) {
			System.out.println(str);
		}
	}
	/**
	 * 获取船舶Bay位数据
	 * 翁总BAY位识别调用
	 * 2020-05-19
	 * @return
	 */
	@Override
	public Map<String,Object> getVesselBayData(String craneNum, String passTime){
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
		Map<String, Object> map = new HashMap<String, Object>();
		int workType = 0; //默认为装船作业
		//通过岸桥编号获取岸桥配制
		List<CranePreparationVo> list = cranePreparationService.getCranePreparation(craneNum);
		if(null !=list && list.size()>0) {
			int id = 0;
			List<String> bayList = new ArrayList<String>();	
			String bay = null;
			for(CranePreparationVo cp : list) {
				bay = cp.getBay();
				if(null !=bay && !"".equals(bay)) {
					bayList.add(bay);
				}
				if(cp.getId() >id) {
					id = cp.getId();
					//1、船旋方向(shipSide)
					map.put("alongside", cp.getAlongside());	//取最大ID的船舷方向
				}
			}
			if(bayList.size() >0) {
				CranePreparationVo cranePreparation = list.get(0);
				//2、作业Bay(Bay结构)
				map.put("vesselBayList", wl_vesselBayService.getVesselSlotBayList(cranePreparation.getVesselCode(),bayList));
				
				WorkRecordVo workRecord = new WorkRecordVo();
				workRecord.setCraneNum(craneNum);							//岸桥编号
				workRecord.setVesselNumber(cranePreparation.getVesselNumber());	//船舶艘次号
				workRecord.setWorkType(workType);
				workRecord.setPassTime(passTime);
				//作业类型
				//3、获取当前桥和船的最近1吊的装船作业（包含箱号、Bay位信息）
				map.put("workRecordList", workRecordService.getWorkRecordData(workRecord));
				
				//把中间的BAY加上去
				int index1 = Integer.parseInt(bayList.get(0));
				if(bayList.size() >1) {
					int index2 = Integer.parseInt(bayList.get(1));
					if(index1 >index2) {
						index1 = index2;
					}
				}
				index1 +=1;
				String s = "00";
				if(index1 >9) {
					s = "0";
				}
				bayList.add(s+index1);
				List<VesselContainerVo> vesselContainerList = new ArrayList<VesselContainerVo>();
				VesselContainerVo vc = null;
				for(String str : bayList) {
					vc = new VesselContainerVo();
					vc.setVesselNumber(cranePreparation.getVesselNumber());
					vc.setVesselCode(cranePreparation.getVesselCode());
					vc.setStdBay(str);
					vesselContainerList.add(vc);
				}
				//4、获取当前桥和船的作业Bay集装箱使用情况（包含箱号、箱位，如01、02、03）
				List<Object> objList = vesselContainerService.getVesselContainerList(vesselContainerList);
				map.put("vesselContainerList", objList);
			}
		}
		return map;
	}
}
