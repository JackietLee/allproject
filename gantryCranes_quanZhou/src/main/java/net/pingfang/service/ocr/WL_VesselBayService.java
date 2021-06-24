package net.pingfang.service.ocr;

import java.util.List;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;

public interface WL_VesselBayService {
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
	public List<VesselBayVo> getVesselSlotListByVesselCode(List<VesselBayVo> list);
	/**
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	public List<VesselBayVo> getVesselSlotBayList(String vesselCode, List<String> bayList);
	

	
	
	
	/**       tos_vessel_bay （船舶bay）  **/
	
	/**
	 * 获取船舶Bay下拉列表
	 * @return
	 */
	public List<VesselBayVo> getBayListByVesselCode(String vesselCode);
	
	
	
	
	
	
	/**       tos_berth_plan  (泊位计划)  **/
	
	
	
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	public List<String> getAlongsideByVesselCood(String vesselCood);
	
	/**
	 * 获取所有等待作业的船舶信息
	 * 0是表示等待作业，1是作业已完成("is_finished = 0")
	 * @return
	 */
	public List<BerthPlanInfoVo> getIsFinishedBerthPlanList(BerthPlanInfoVo berthPlanInfoVo);
	

}
