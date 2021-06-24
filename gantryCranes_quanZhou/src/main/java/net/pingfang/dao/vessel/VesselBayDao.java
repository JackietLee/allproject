package net.pingfang.dao.vessel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;

/**
 * 船舶Bay Dao（tos_vessel_bay表）
 * @author Administrator
 * @since 2019-06-01
 *
 */
public interface VesselBayDao {
	
	
	/**
	 * 获取所有岸桥信息下拉列表
	 * @return
	 */
	public List<CraneInfoVo> getSelectCraneInfoList();
	/**
	 * 获取所有等待作业的船舶信息
	 * 0是表示等待作业，1是作业已完成("is_finished = 0")
	 * @return
	 */
	//public List<BerthPlanInfoVo> getIsFinishedBerthPlanList(BerthPlanInfoVo berthPlanInfoVo);
	
	/**
	 * 获取船舶Bay下拉列表
	 * @return
	 */
	public List<VesselBayVo> getBayListByVesselCode(String vesselCode);
	
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
	public List<VesselBayVo> getVesselSlotBayList(@Param("vesselCode") String vesselCode, @Param("list")List<String> bayList);
	
}
