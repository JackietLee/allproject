package net.pingfang.service.vessel;

import java.util.List;
import java.util.Map;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;

/**
 * 船舶Bay信息Service
 * @author Administrator
 * @since 2019-06-3
 *
 */
public interface VesselBayService {
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	public List<String> getAlongsideByVesselCood(String vesselCood);
	/**
	 * 获取所有岸桥信息
	 * @return
	 */
	public List<CraneInfoVo> getCraneInfoList();
	/**
	 * 获取所有等待作业的船舶信息
	 * is_finished = 1
	 * @return
	 */
	public List<BerthPlanInfoVo> getIsFinishedBerthPlanList(BerthPlanInfoVo berthPlanInfoVo);
	/**
	 * 获取船舶Bay下拉列表
	 * @return
	 */
	public List<VesselBayVo> getBayListByVesselCode(String vesselCode);
	/**
	 * 获取船舶Bay
	 * @return
	 */
	public Map<String,Object> getVesselBayMap(List<VesselBayVo> vesselBayList);
	/**
	 * 通过VesselCode获取船舶Bay信息
	 * @return
	 */
	public List<VesselBayVo> getVesselSlotListByCraneNum(String craneNum);
	/**
	 * 获取船舶Bay
	 * 2020-10-11
	 * @return
	 */
	public Map<String,Object> getVesselBayMapNew(List<VesselBayVo> vesselBayList);
	
	
	
	/**
	 * 获取船舶Bay位数据
	 * 翁总BAY位识别调用
	 * 2020-05-19
	 * @return
	 */
	public Map<String,Object> getVesselBayData(String craneNum, String passTime);

}
