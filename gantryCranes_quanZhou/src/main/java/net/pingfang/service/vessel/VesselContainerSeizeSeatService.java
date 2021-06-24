package net.pingfang.service.vessel;

import net.pingfang.entity.vessel.VesselContainerVo;
/**
* 船舶集装箱占位Dao
* 表（"tos_vessel_container_seize_seat"）
* @author Administrator
* @since 2019-11-03
*
*/
public interface VesselContainerSeizeSeatService {
	
	/**
	 * 新增集装箱占位
	 * @param vesselContainer
	 * @return
	 */
	public int insertContainerSeizeSeat(VesselContainerVo vesselContainer);
	
	/**
	 * 删除一条船舶集装箱占位
	 * @param id
	 * @return
	 */
	public int deleteContainerSeizeSeat(VesselContainerVo vesselContainer);
	public int updateContainerSeizeSeatBayInfo(VesselContainerVo vesselContainer);
	
	public void insertContainerBay(VesselContainerVo vesselContainer);
	
	public int getCountContainerSeizeSeat(VesselContainerVo vesselContainer);
	public int getNewCountContainerSeizeSeat(VesselContainerVo vesselContainer);
}
