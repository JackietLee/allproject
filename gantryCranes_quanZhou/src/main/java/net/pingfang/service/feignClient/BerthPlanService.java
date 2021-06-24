package net.pingfang.service.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.handler.DataResult;

/**
 * 泊位计划信息Service
 * tos_berth_plan  (泊位计划)
 * @author Administrator
 * @since 2019-06-01
 *
 */
@FeignClient(value = "user", url = "${wlApiUrl}")
//@FeignClient(value = "user", url = "http://192.168.1.20:18070")
public interface BerthPlanService {
	/**
	 * 分页获取所有泊位计划信息
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@RequestMapping(value = "/etlservice/berth_plans/_search", method = RequestMethod.POST)
//	public DataResult<PageVo<BerthPlanInfoVo>> getPageBerthPlanInfoList(BerthPlanInfoVo berthPlanInfoVo);
	
	/**
	 * 根据ID获取一条泊位计划信息
	 * @param id
	 * @return
	 */
	
//	@RequestMapping(value = "/etlservice/berth_plans/vesselVoyageNumber/jobAmount", method = RequestMethod.GET)
//	public DataResult<BerthPlanInfoVo> getBerthPlanInfoById(@RequestParam("vesselVoyageNumber") String vesselVoyageNumber);	
	/**
	 * 根据船舶艘次号获取一条泊位计划信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/etlservice/berth_plans/vesselVoyageNumber", method = RequestMethod.GET)
	public DataResult<BerthPlanInfoVo> getBerthPlanInfoByVesselNumber(@RequestParam("vesselVoyageNumber") String vesselNumber);	
	/**
	 * 更新船舷方向
	 * @param berthPlanInfo
	 * @return
	 */
	//@RequestMapping(value = "/etlservice/berth_plans/vesselVoyageNumber", method = RequestMethod.GET)
	//public DataResult<Integer> updateAlongside(BerthPlanInfoVo berthPlanInfo);
	
	/**
	 * Excel导出所有泊位计划信息
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
	//etlservice/berth_plans/export_excel
//	@RequestMapping(value = "/etlservice/berth_plans/export_excel", method = RequestMethod.POST)
//	public DataResult<List<BerthPlanInfoVo>> exportBerthPlanInfo(@RequestBody BerthPlanInfoVo berthPlanInfoVo);
	
	/**
	 * 获取船下拉列表框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@RequestMapping(value = "/etlservice/berth_plans/box/vesselNameCN", method = RequestMethod.GET)
//	public DataResult<List<BerthPlanInfoVo>> getBerthPlanListBox(@RequestParam("vesselNameCN") String vesselNameCN);
//	
	
	
	
	
	/**
	 * 通过vesselNumber获取船信息
	 * @return
	 */
	@RequestMapping(value = "/etlservice/berth_plans/vesselVoyageNumbers", method = RequestMethod.GET)
	public DataResult<List<BerthPlanInfoVo>> getBerthPlanInfoVoListByVesselNumber(@RequestParam("vesselVoyageNumber") List<String> vesselNumberList);
	
	/**
	 * WorkRecordDao
	 *  根据id查询作业记录信息
	 * @param workJson
	 * @return
	 */
	//public WorkRecordVo getWorkRecordById(int id);
	/**
	 * WorkRecordDao
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * @return
	 */
//	public WorkRecordVo getAlreadyWorkRecordById(WorkRecordVo workRecordVo);
	/**
	 * DamagedInforRecordDao
	 * 获取作业记录
	 * @return
	 */
//	public List<WorkRecordVo> getWorkRecordList(WorkRecordVo workRecordVo);
	
	/**
	 * 通过航次获取船舶艘次号
	 * @return
	 */
//	@RequestMapping(value = "/etlservice/berth_plans/voyage", method = RequestMethod.GET)
//	public DataResult<List<String>> getVesselNumberListByVoyage(@RequestParam("inVoyage") String inVoyage, @RequestParam("outVoyage") String outVoyage);
	
	/**
	 * 发送残损数据到华东系统
	 * @return
	 */
	@RequestMapping(value = "/etlservice/damage/container", method = RequestMethod.POST)
	public DataResult<Object> damageSynchronization(@RequestParam("vesselVoyageNumber") String vesselVoyageNumber, 
			@RequestParam("containerNumber") String containerNumber,
			@RequestParam("damageCode") String damageCode,
			@RequestParam("damageAreaCode") String damageAreaCode,
			@RequestParam("tallyClerk") String tallyClerk,
			@RequestParam("damageRemark") String damageRemark);
	
	
	
}
