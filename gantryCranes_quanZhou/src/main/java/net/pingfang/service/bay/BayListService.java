package net.pingfang.service.bay;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.camera.CameraInforVo;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;

public interface BayListService {
	public Map<String,Object> getBayList(List<VesselBayVo> vesselBayList);
	public Map<String,Object> getBayInfoList(List<VesselContainerVo> vesselContainerList);
	
	/**
	 * 根据船舶艘次号查询BAY总数
	 * @param vesselNumber
	 * @return
	 */
	public int getCountBayByVesselNumber(String vesselNumber);
	/**
	 * 根据船舶艘次号删除BAY数据
	 * @param vesselNumber
	 * @return
	 */
	public int deleteBayByVesselNumber(String vesselNumber);
	/**
	 * 批量插入船图结构数据
	 * @param CameraInforVo
	 * @return
	 */
	public int insertVesselBayList(List<VesselBayVo> vesselBayList);

}
