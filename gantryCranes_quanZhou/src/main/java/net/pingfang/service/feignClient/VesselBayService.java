package net.pingfang.service.feignClient;

import java.util.List;
//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.handler.DataResult;

@FeignClient(value = "user", url = "${wlApiUrl}")
//@FeignClient(value = "user", url = "http://192.168.1.20:18070")
public interface VesselBayService {
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
	@RequestMapping(value = "/etlservice/slot/vessels/bays", method = RequestMethod.POST)
	public DataResult<List<VesselBayVo>> getVesselSlotListByVesselCode(@RequestBody List<VesselBayVo> list);
	/**
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@RequestMapping(value = "/etlservice/slot/vessel/bays", method = RequestMethod.POST)
	public DataResult<List<VesselBayVo>> getVesselSlotBayList(@RequestParam("vesselCode") String vesselCode, @RequestParam("bays") List<String> bays);
	

	
	
	
	/**       tos_vessel_bay （船舶bay）  **/
	
	/**
	 * 获取船舶Bay下拉列表
	 * @return
	 */
	@RequestMapping(value = "/etlservice/slot/bays", method = RequestMethod.GET)
	public DataResult<List<VesselBayVo>> getBayListByVesselCode(@RequestParam("vesselCode")String vesselCode);
	
	
	
	
	
	
	/**       tos_berth_plan  (泊位计划)  **/
	
	
	
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	@RequestMapping(value = "/etlservice/berth_plans/alongside", method = RequestMethod.GET)
	public DataResult<String> getAlongsideByVesselCood(@RequestParam("vesselCode")String vesselCode);
	
	/**
	 * 获取所有等待作业的船舶信息
	 * 0是表示等待作业，1是作业已完成("is_finished = 0")
	 * @return
	 */
	@RequestMapping(value = "/etlservice/berth_plans/vesselNameCN", method = RequestMethod.GET)
	public DataResult<List<BerthPlanInfoVo>> getIsFinishedBerthPlanList(@RequestParam("vesselNameCN")String vesselNameCn);
	

}
