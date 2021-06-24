package net.pingfang.serviceImpl.ocr;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.VesselContainerVo;
//import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.DataResult;
import net.pingfang.service.feignClient.VesselContainerService;
import net.pingfang.service.ocr.WL_VesselContainerService;
import net.pingfang.utils.PrintLogUtil;

@Service
public class WL_VesselContainerServiceImpl implements WL_VesselContainerService{
	
	@Autowired
	private VesselContainerService vcs;
	
	private final static Logger logger = LoggerFactory.getLogger(WL_VesselContainerServiceImpl.class);
	
	/**
	 * 分页获取所有船舶集装箱
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselContainerVo
	 * @return
	 */
//	@Override
//	public PageVo<VesselContainerVo> getPageVesselContainerList(VesselContainerVo vesselContainer){
//		String url = "/etlservice/outer_link_vessel_container/page/container";
//		PrintLogUtil.requestParamPrintLog(logger, url, vesselContainer);
//		PageVo<VesselContainerVo> pageVo = null;
//		try {
//			DataResult<PageVo<VesselContainerVo>> result = this.vcs.getPageVesselContainerList(vesselContainer);
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
	 * 获取所有船舶集装箱记录总数
	 * @return
	 */
/*	public int getCountVesselContainer(Map<String, Object> map){
		return 0;
	}*/
	
	/**
	 * 匹配仓单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber
	 * @return
	 */
/*	@Override
	public List<VesselContainerVo> getVesselContainerByVesselNumber(String vesselNumber, String containerNumber, String jobType){
		String url = "/etlservice/outer_link_vessel_container/container";
		PrintLogUtil.requestParamPrintLog(logger, url, "vesselNumber:"+vesselNumber+",containerNumber:"+containerNumber+",jobType"+jobType);
		List<VesselContainerVo> vcList = null;
		try {
			MatchingBillVO matchingBill = new MatchingBillVO();
			matchingBill.setContainers(vesselNumber);
			matchingBill.setVesselVoyageNumber(containerNumber);
			matchingBill.setJobType(Integer.parseInt(jobType));
			DataResult<List<VesselContainerVo>> result = this.vcs.getVesselContainerByVesselNumber(matchingBill);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				vcList = result.getData();
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return vcList;
	}*/
	
	/**
	 * 获取 装货港，卸货港，目的港
	 * @param vesselContainerVo
	 * @return
	 */
	@Override
	public List<VesselContainerVo> getVesselContainerList(VesselContainerVo vesselContainerVo){
		String url = "/etlservice/outer_link_vessel_container/container/bay";
		PrintLogUtil.requestParamPrintLog(logger, url, vesselContainerVo);
		List<VesselContainerVo> vcList = null;
		try {
			DataResult<List<VesselContainerVo>> result = this.vcs.getVesselContainerList(vesselContainerVo);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				vcList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return vcList;
	}
	@Override
	public List<VesselContainerVo> getContainerList(String vesselNumber, String vesselCode,String jobType,List<VesselContainerVo> vesselContainerList){
		String url = "/etlservice/outer_link_vessel_container/containers";
		List<String> containerNumberList = new ArrayList<String>();
		for(VesselContainerVo vc : vesselContainerList) {
			containerNumberList.add(vc.getContainerNumber());
		}
		PrintLogUtil.requestParamPrintLog(logger, url+" vesselNumber:"+vesselNumber+",vesselCode:"+vesselCode+",jobType:"+jobType, containerNumberList);
		List<VesselContainerVo> vcList = null;
		try {
			DataResult<List<VesselContainerVo>> result = this.vcs.getContainerList(vesselNumber, vesselCode, jobType, containerNumberList);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				vcList = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return vcList;
	}
	/**
	 * 更新贝位
	 * @param vesselContainer
	 * @return
	 */
	/*@Override
	public int updateBayInfo(VesselContainerVo vesselContainer){
		String url = "";
		PrintLogUtil.requestParamPrintLog(logger, url, vesselContainer);
		int count = 0;
		try {
			DataResult<Integer> result = this.vcs.updateBayInfo(vesselContainer);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				count = result.getData();
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return count;
	}*/
/*	@Override
	public int getCountContainer(VesselContainerVo vesselContainer){
		String url = "";
		PrintLogUtil.requestParamPrintLog(logger, url, vesselContainer);
		int count = 0;
		try {
			DataResult<Integer> result = this.vcs.getCountContainer(vesselContainer);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				count = result.getData();
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	*/
	
	
	
	
	/**
	 * WorkRecordDao
	 *作业数据统计
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	/**
	 * 待定
	@Override
	public List<WorkRecordVo> getWorkRecordStatistics(WorkRecordVo workRecordVo){
		String url = "";
		PrintLogUtil.requestParamPrintLog(logger, url, workRecordVo);
		List<WorkRecordVo> list = null;
		try {
			DataResult<List<WorkRecordVo>> result = this.vcs.getWorkRecordStatistics(workRecordVo);
			PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				list = result.getData();
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	*/
	/**
	 * WorkRecordDao
	 *集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
	 *集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@Override
	public int getMountStatistics(List<WorkRecordVo> list, String jobType){
		String url = "etlservice/outer_link_vessel_container/jobAmount";
		PrintLogUtil.requestParamPrintLog(logger, url+" jobType:"+jobType, list);
		int count = 0;
		try {
			DataResult<Integer> result = this.vcs.getMountStatistics(list, jobType);
			//PrintLogUtil.returnDataPrintLog(logger, url, result);
			if(null !=result && 200 == result.getStatus()) {
				count = result.getData();
			}else {
				PrintLogUtil.returnDataPrintLog(logger, url, result);
			}
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * WorkRecordDao
	 * @param cranePreparationList
	 * @return
	 */
//	@Override
//	public List<VesselContainerVo> getVesselPositionByVesselNumber(List<CranePreparationVo> cranePreparationList){
//		String url = "etlservice/outer_link_vessel_container/vesselVoyageNumber";
//		PrintLogUtil.requestParamPrintLog(logger, url, cranePreparationList);
//		List<VesselContainerVo> list = null;
//		try {
//			DataResult<List<VesselContainerVo>> result = this.vcs.getVesselPositionByVesselNumber(cranePreparationList);
//			//PrintLogUtil.returnDataPrintLog(logger, url, result);
//			if(null !=result && 200 == result.getStatus()) {
//				list = result.getData();
//			}else {
//				PrintLogUtil.returnDataPrintLog(logger, url, result);
//			}
//		}catch(Exception e) {
//			logger.error("API请求出错："+e.getMessage());
//			e.printStackTrace();
//		}
//		return list;
//	}
	
}
