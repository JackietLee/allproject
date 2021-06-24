package net.pingfang.serviceImpl.ocr;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.handler.DataResult;
import net.pingfang.service.feignClient.VesselBayService;
import net.pingfang.service.ocr.WL_VesselBayService;
import net.pingfang.utils.PrintLogUtil;
@Service
public class WL_VesselBayServiceImpl implements WL_VesselBayService{
	
	@Autowired
	private VesselBayService vbs;
	
	private final static Logger logger = LoggerFactory.getLogger(WL_VesselBayServiceImpl.class);
	
	/**       tos_vessel_slot （船舶结构，箱位）  **/
	
	/**
	 * 2020-02-27(待删除)
	 * 获取船舶Bay层数量
	 * @return
	 */
	//public List<VesselBayVo> getStdTierList(VesselBayVo vesselBayVo);
	/**
	 * 2020-02-27(待删除)
	 * 获取船舶Bay排数量
	 * @return
	 */
	//public List<VesselBayVo> getStdRowList(VesselBayVo vesselBayVo);
	/**
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@Override
	public List<VesselBayVo> getVesselSlotListByVesselCode(List<VesselBayVo> list){
		String url = "/etlservice/slot/vessels/bays";
		PrintLogUtil.requestParamPrintLog(logger, url, list);
		List<VesselBayVo> vbList = null;
		try {
			DataResult<List<VesselBayVo>> result = this.vbs.getVesselSlotListByVesselCode(list);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				vbList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return vbList;
	}
	/**
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@Override
	public List<VesselBayVo> getVesselSlotBayList(String vesselCode, List<String> bayList){
		String url = "/etlservice/slot/vessel/bays";
		PrintLogUtil.requestParamPrintLog(logger, url+" vesselCode:"+vesselCode, bayList);
		List<VesselBayVo> vbList = null;
		try {
			DataResult<List<VesselBayVo>> result = this.vbs.getVesselSlotBayList(vesselCode, bayList);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				vbList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return vbList;
	}
	

	
	
	
	/**       tos_vessel_bay （船舶bay）  **/
	
	/**
	 * 获取船舶Bay下拉列表
	 * @return
	 */
	@Override
	public List<VesselBayVo> getBayListByVesselCode(String vesselCode){
		String url = "/etlservice/slot/bays";
		logger.info(url+" 请求参数: vesselCode:"+vesselCode);
		List<VesselBayVo> vbList = null;
		try {
			DataResult<List<VesselBayVo>> result = this.vbs.getBayListByVesselCode(vesselCode);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				vbList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return vbList;
	}
	
	
	
	
	
	
	/**       tos_berth_plan  (泊位计划)  **/
	
	
	
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	@Override
	public List<String> getAlongsideByVesselCood(String vesselCode){
		String url = "/etlservice/berth_plans/alongside";
		PrintLogUtil.requestParamPrintLog(logger, url, vesselCode);
		List<String> list = null;
		try {
			DataResult<String> result = this.vbs.getAlongsideByVesselCood(vesselCode);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				list = new ArrayList<String>();
				list.add(result.getData());
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取所有等待作业的船舶信息
	 * 0是表示等待作业，1是作业已完成("is_finished = 0")
	 * @return
	 */
	@Override
	public List<BerthPlanInfoVo> getIsFinishedBerthPlanList(BerthPlanInfoVo berthPlanInfoVo){
		String url = "/etlservice/berth_plans/vesselNameCN";
		String vesselNameCn = berthPlanInfoVo.getVesselNameCn();
		
		PrintLogUtil.requestParamPrintLog(logger, url, vesselNameCn);
		List<BerthPlanInfoVo> list = null;
		try {
			DataResult<List<BerthPlanInfoVo>> result = this.vbs.getIsFinishedBerthPlanList(vesselNameCn);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				list = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

}
