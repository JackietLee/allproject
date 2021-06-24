package net.pingfang.service.bay;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;

public interface HandlerBayListService {
	/**
	 * 查询船图结构数据
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	public Map<String,Object> getBatchBayList(List<VesselBayVo> vesselBayList);
	public Map<String,Object> getBatchBayInfoList(List<VesselContainerVo> vesselContainerList);

}
