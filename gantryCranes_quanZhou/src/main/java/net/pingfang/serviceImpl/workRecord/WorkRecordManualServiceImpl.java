package net.pingfang.serviceImpl.workRecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.workRecord.WorkRecordManualDao;
import net.pingfang.entity.work.WorkRecordManualVo;
import net.pingfang.service.workRecord.WorkRecordManualService;

@Service
public class WorkRecordManualServiceImpl implements WorkRecordManualService{
	
	@Autowired
	private WorkRecordManualDao workRecordManualDao;
	
	/**
	 * 作业识别率统计
	 * @param workRecordManual
	 * @return
	 */
	@Override
	public Map<String,Object> identificationRateStatistics(WorkRecordManualVo workRecordManual) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> retunList = new ArrayList<Map<String,Object>>();
		List<String> craneNumList = workRecordManual.getCraneNumList(); //岸桥编号
		//获取所有统计数据
		List<WorkRecordManualVo> list = null;
		if(null !=craneNumList && craneNumList.size()>0) {
			list = workRecordManualDao.identificationRateStatistics(workRecordManual,craneNumList);
			returnMap.put("craneNumList", craneNumList);
		}else {
			list = workRecordManualDao.identificationRateStatisticsTwo(workRecordManual);
			craneNumList = new ArrayList<String>();
			craneNumList.add(workRecordManual.getVesselNameCn());
			returnMap.put("craneNumList", craneNumList);
		}
		
		//记录总数
		List<Integer>  totalList  = new ArrayList<Integer>();
		List<Integer>  tList  = new ArrayList<Integer>();
		//获取所有更新过的车顶号记录总数
		List<Integer>  topPlateList  = new ArrayList<Integer>();
		List<Double>  tpList  = new ArrayList<Double>();
		//获取所有更新过的箱号记录总数
		List<Integer>  contaidList  = new ArrayList<Integer>();
		List<Double>  cList  = new ArrayList<Double>();
		//获取所有更新过的BAY记录总数
		List<Integer>  bayList  = new ArrayList<Integer>();
		List<Double>  bList  = new ArrayList<Double>();
		//获取所有自动理货记录总数
		List<Integer>  autoRecordList  = new ArrayList<Integer>();
		List<Double>  arList  = new ArrayList<Double>();
		//获取所有人工理货记录总数
		List<Integer>  handRecordList  = new ArrayList<Integer>();
		List<Double>  hrList  = new ArrayList<Double>();
		
		double total = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(WorkRecordManualVo wrm : list) {
			total = wrm.getTotal();
			if(total >0) {
				totalList.add(wrm.getTotal());
				tList.add(100);
				
				topPlateList.add(wrm.getCountTopPlate());
				//double do1 = wrm.getCountTopPlate();
				tpList.add(Double.parseDouble(df.format((total - wrm.getCountTopPlate())/total*100)));
				
				contaidList.add(wrm.getCountContaid());
				//double do2 = wrm.getCountContaid();
				cList.add(Double.parseDouble(df.format((total - wrm.getCountContaid())/total*100)));
				
				bayList.add(wrm.getCountBay());
				//double do3 = wrm.getCountBay();
				bList.add(Double.parseDouble(df.format((total - wrm.getCountBay())/total*100)));
				
				autoRecordList.add(wrm.getCountAutoRecord());
				//double do4 = wrm.getCountAutoRecord();
				arList.add(Double.parseDouble(df.format(wrm.getCountAutoRecord()/total*100)));
				
				handRecordList.add(wrm.getTotal() - wrm.getCountAutoRecord());
				//double do5 = wrm.getCountAutoRecord();
				hrList.add(Double.parseDouble(df.format((total - wrm.getCountAutoRecord())/total*100)));
			}else {
				totalList.add(0);
				tList.add(100);
				
				topPlateList.add(0);
				tpList.add(100.0);
				
				contaidList.add(0);
				cList.add(100.0);
				
				bayList.add(0);
				bList.add(100.0);
				
				autoRecordList.add(0);
				arList.add(100.0);
				
				handRecordList.add(0);
				hrList.add(100.0);
			}			
		}
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("name", "总记录数");
		totalMap.put("data", totalList);
		totalMap.put("percentage", tList);
		retunList.add(totalMap);
			
		Map<String,Object> topPlatelMap = new HashMap<String,Object>();
		topPlatelMap.put("name", "更新车顶号总数");
		topPlatelMap.put("data", topPlateList);
		topPlatelMap.put("percentage", tpList);
		retunList.add(topPlatelMap);
			
		Map<String,Object> contaidMap = new HashMap<String,Object>();
		contaidMap.put("name", "更新箱号总数");
		contaidMap.put("data", contaidList);
		contaidMap.put("percentage", cList);
		retunList.add(contaidMap);
			
		Map<String,Object> bayMap = new HashMap<String,Object>();
		bayMap.put("name", "更新BAY总数");
		bayMap.put("data", bayList);
		bayMap.put("percentage", bList);
		retunList.add(bayMap);
			
		Map<String,Object> autoMap = new HashMap<String,Object>();
		autoMap.put("name", "自动理货总数");
		autoMap.put("data", autoRecordList);
		autoMap.put("percentage", arList);
		retunList.add(autoMap);
			
		Map<String,Object> handMap = new HashMap<String,Object>();
		handMap.put("name", "人工干预理货总数");
		handMap.put("data", handRecordList);
		handMap.put("percentage", hrList);
		retunList.add(handMap);
			
		returnMap.put("data", retunList);
		return returnMap;
	}
	/**
	 * 插入一条作业记录
	 * @param workRecordManual
	 * @return
	 */
	@Transactional
	@Override
	public int insertWorkRecordManual(WorkRecordManualVo workRecordManual) {
		return workRecordManualDao.insertWorkRecordManual(workRecordManual);
	}
	/**
	 * 查询作业记录是否存在
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public int getWrmExists(WorkRecordManualVo workRecordManual) {
		return workRecordManualDao.getWrmExists(workRecordManual);
	}
	
	/**
	 * 更新一条作业记录
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateWorkRecordManual(WorkRecordManualVo workRecordManual) {
		return workRecordManualDao.updateWorkRecordManual(workRecordManual);
	}




}
