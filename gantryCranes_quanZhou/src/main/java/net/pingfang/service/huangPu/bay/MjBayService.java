package net.pingfang.service.huangPu.bay;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;

/**
 * 门机BAY
 * @author Administrator
 *
 */
public interface MjBayService {
	/**
	 * 查询船图结构数据
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	public Map<String,Object> getBatchMjBayList(List<VesselBayVo> vesselBayList);
	public Map<String,Object> getBatchMjBayInfoList(List<VesselContainerVo> vesselContainerList);

}
