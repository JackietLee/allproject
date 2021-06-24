package net.pingfang.serviceImpl.ocr;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.handler.DataResult;
import net.pingfang.service.feignClient.BerthPlanService;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.vessel.DamagedInforRecordService;
import net.pingfang.utils.PrintLogUtil;

@Service
public class WL_BerthPlanServiceImpl implements WL_BerthPlanService{
	
	
	@Autowired
	private BerthPlanService bps;
	@Autowired
	private DamagedInforRecordService damagedInforRecordService;
	
	private final static Logger logger = LoggerFactory.getLogger(WL_BerthPlanServiceImpl.class);
	/**
	 * 分页获取所有泊位计划信息
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@Override
//	public PageVo<BerthPlanInfoVo> getPageBerthPlanInfoList(BerthPlanInfoVo berthPlanInfoVo){
//		String url = "/etlservice/berth_plans/_search";
//		PrintLogUtil.requestParamPrintLog(logger, url, berthPlanInfoVo);
//		PageVo<BerthPlanInfoVo> pageVo = null;
//		try {
//			DataResult<PageVo<BerthPlanInfoVo>> result = this.bps.getPageBerthPlanInfoList(berthPlanInfoVo);
//			//PrintLogUtil.returnDataPrintLog(logger, url, result);
//			if(null !=result && 200 == result.getStatus()) {
//				pageVo = result.getData();
//			}else {
//				PrintLogUtil.returnDataPrintLog(logger, url, result);
//			}
//		}catch(Exception e) {
//			logger.error("API请求出错："+e.getMessage());
//			e.printStackTrace();
//		}
//		return pageVo;
//	}
	/**
	 * 获取所有泊位计划信息记录总数
	 * @return
	 */
	/*@Override
	public int getCountBerthPlanInfo(BerthPlanInfoVo berthPlanInfo){
		return 0;
	}*/
	/**
	 * 些接口待更新，把ID改成vesselVoyageNumber
	 * 根据ID获取一条泊位计划信息
	 * @param id
	 * @return
	 */
//	@Override
//	public BerthPlanInfoVo getBerthPlanInfoById(String vesselVoyageNumber){
//		//临时测试
//		//String vesselVoyageNumber = "20179643";
//		
//		String url = "/etlservice/berth_plans/vesselVoyageNumber/jobAmount";
//		PrintLogUtil.requestParamPrintLog(logger, url, vesselVoyageNumber);
//		BerthPlanInfoVo berthPlanInfo = null;
//		try {
//			DataResult<BerthPlanInfoVo> result = this.bps.getBerthPlanInfoById(vesselVoyageNumber);
//			PrintLogUtil.returnDataPrintLog(logger, url, result);
//			if(null !=result && 200 == result.getStatus()) {
//				berthPlanInfo = result.getData();
//			}
//		}catch(Exception e) {
//			logger.error("API请求出错："+e.getMessage());
//			e.printStackTrace();
//		}
//		return berthPlanInfo;
//	}	
	/**
	 * 根据船舶艘次号获取一条泊位计划信息
	 * @param id
	 * @return
	 */
	@Override
	public BerthPlanInfoVo getBerthPlanInfoByVesselNumber(String vesselNumber){
		String url = "/etlservice/berth_plans/vesselVoyageNumber";
		PrintLogUtil.requestParamPrintLog(logger, url, vesselNumber);
		BerthPlanInfoVo berthPlanInfo = null;
		try {
			DataResult<BerthPlanInfoVo> result = this.bps.getBerthPlanInfoByVesselNumber(vesselNumber);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				berthPlanInfo = result.getData();
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return berthPlanInfo;
	}	
	/**
	 * 更新船舷方向
	 * 
	 * 外理DB没有更新（此方法待定）
	 * 
	 * @param berthPlanInfo
	 * @return
	 */
	@Override
	public int updateAlongside(BerthPlanInfoVo berthPlanInfo){
		int count = 0;
		/*String url = "/etlservice/berth_plans/vesselVoyageNumber";
		logger.info(url+" 请求参数: "+berthPlanInfo);
		try {
			DataResult<Integer> result = this.bps.getBerthPlanInfoByVesselNumber(berthPlanInfo);
			logger.info(url+"  API返回信息：" + JSON.toJSONString(result));
			if(null !=result && 200 == result.getStatus()) {
				count = result.getData();
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}*/
		return count;
	}
	
	/**
	 * Excel导出所有泊位计划信息
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
/*	@Override
	public List<BerthPlanInfoVo> exportBerthPlanInfo(BerthPlanInfoVo berthPlanInfoVo){
		String url = "/etlservice/berth_plans/export_excel";
		PrintLogUtil.requestParamPrintLog(logger, url, berthPlanInfoVo);
		List<BerthPlanInfoVo> bpList = null;
		try {
			DataResult<List<BerthPlanInfoVo>> result = this.bps.exportBerthPlanInfo(berthPlanInfoVo);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				bpList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return bpList;
	}
	*/
	/**
	 * 获取船下拉列表框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@Override
//	public List<BerthPlanInfoVo> getBerthPlanListBox(BerthPlanInfoVo berthPlanInfoVo){
//		String url = "/etlservice/berth_plans/box/vesselNameCN";
//		PrintLogUtil.requestParamPrintLog(logger, url, berthPlanInfoVo);
//		List<BerthPlanInfoVo> bpList = null;
//		try {
//			DataResult<List<BerthPlanInfoVo>> result = this.bps.getBerthPlanListBox(berthPlanInfoVo.getVesselNameCn());
//			//PrintLogUtil.returnDataPrintLog(logger, url, result);
//			if(null !=result && 200 == result.getStatus()) {
//				bpList = result.getData();
//			}else {
//				PrintLogUtil.returnDataPrintLog(logger, url, result);
//			}
//		}catch(Exception e) {
//			logger.error("API请求出错："+e.getMessage());
//			e.printStackTrace();
//		}
//		return bpList;
//	}
	
	
	
	
	
	/**
	 * 
	 * CranePreparationDao
	 * 获取所有岸桥信息 
	 * 下拉列表框 
	 * @return
	 */
	/*@Override
	public List<CranePreparationVo> getCraneInfoList(){
		
		return null;
	}*/
	/**
	 * 通过vesselNumber获取船信息
	 * @return
	 */
	@Override
	public List<BerthPlanInfoVo> getBerthPlanInfoVoListByVesselNumber(List<String> vesselNumberList){
		String url = "/etlservice/berth_plans/vesselVoyageNumbers";
		PrintLogUtil.requestParamPrintLog(logger, url, vesselNumberList);
		List<BerthPlanInfoVo> bpList = null;
		try {
			DataResult<List<BerthPlanInfoVo>> result = this.bps.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				bpList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return bpList;
	}
	/**
	 * WorkRecordDao
	 *  根据id查询作业记录信息
	 * @param workJson
	 * @return
	 */
	/*@Override
	public WorkRecordVo getWorkRecordById(int id){
		return null;
	}*/
	/**
	 * WorkRecordDao
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	/*@Override
	public WorkRecordVo getAlreadyWorkRecordById(WorkRecordVo workRecordVo){
		return null;
	}*/
	/**
	 * DamagedInforRecordDao
	 * 获取作业记录
	 * @return
	 */
	/*@Override
	public List<WorkRecordVo> getWorkRecordList(WorkRecordVo workRecordVo){
		return null;
	}*/
	/**
	 * 通过航次获取船舶艘次号
	 * @return
	 */
//	@Override
//	public List<String> getVesselNumberListByVoyage(String inVoyage, String outVoyage){
//		String url = "/etlservice/berth_plans/voyage?inVoyage="+inVoyage+"&outVoyage="+outVoyage;
//		logger.info(url);
//		List<String> vesselNumberList= null;
//		if((null !=inVoyage && !"".equals(inVoyage)) || (null !=outVoyage && !"".equals(outVoyage))) {
//			try {
//				DataResult<List<String>> result = this.bps.getVesselNumberListByVoyage(inVoyage, outVoyage);
//				//PrintLogUtil.returnDataPrintLog(logger, url, result);
//				if(null !=result && 200 == result.getStatus()) {
//					vesselNumberList = result.getData();
//				}else {
//					PrintLogUtil.returnDataPrintLog(logger, url, result);
//				}
//			}catch(Exception e) {
//				logger.error("API请求出错："+e.getMessage());
//				e.printStackTrace();
//			}
//		}		
//		return vesselNumberList;
//	}
	/**
	 * 发送残损数据到华东系统
	 * @return
	 */
	@Override
	public void damageSynchronization(DamageInforRecordVo damageInforRecord){
			String vesselVoyageNumber = damageInforRecord.getVesselNumber();	//船舶艘次号 
			String containerNumber = damageInforRecord.getContainerNumber();//箱号
			String damageCode = damageInforRecord.getDamagedCode();	//残损类型编码
			String damageAreaCode = damageInforRecord.getPositionCode();//残损区域编码
			String damageRemark = damageInforRecord.getDesc();	//残损描述
			String tallyClerk = damageInforRecord.getCreateName();//理货员
		String url = "/etlservice/damage/container?vesselVoyageNumber="+vesselVoyageNumber+"&containerNumber="+containerNumber+
				"&damageCode="+damageCode+
				"&damageAreaCode="+damageAreaCode+
				"&tallyClerk="+tallyClerk;
		logger.info(url);
		if(null !=vesselVoyageNumber && null !=containerNumber && null !=damageCode && null !=damageAreaCode && null !=tallyClerk) {
			try {
				DataResult<Object> result = this.bps.damageSynchronization(vesselVoyageNumber, containerNumber,damageCode,damageAreaCode,tallyClerk,damageRemark);
				//PrintLogUtil.returnDataPrintLog(logger, url, result);
				if(null !=result && 200 == result.getStatus()) {
					//更新残损记录为已经发送
					damagedInforRecordService.updateSynchronizationById(damageInforRecord.getId());
				}else {
					PrintLogUtil.returnDataPrintLog(logger, url, result);
				}
			}catch(Exception e) {
				logger.error(url +"API请求出错："+e.getMessage());
				e.printStackTrace();
			}
		}		
	}
	


}
