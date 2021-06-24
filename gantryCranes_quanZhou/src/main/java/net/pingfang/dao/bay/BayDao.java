package net.pingfang.dao.bay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;

public interface BayDao {

	/**
	 * 获取装船作业箱
	 * @param vesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getBatchBayInfoList(@Param("list")List<VesselContainerVo> list);
	/**
	 * 获取箱
	 * @param vesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getBatchContainerSeizeSeatList(@Param("list")List<VesselContainerVo> list);
	
	
	
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
