package net.pingfang.dao.vessel;

import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.WorkRecordVo;
/**
* 船舶集装箱点位Dao
* 表（"tos_vessel_container_seize_seat"）
* @author Administrator
* @since 2019-11-03
*
*/
public interface VesselContainerSeizeSeatDao {
	
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
	
	public int getCountWorkRecord(WorkRecordVo workRecord);
	public int updateBayInfo(WorkRecordVo workRecord);
	
	public int updateContainerSeizeSeatBayInfo(VesselContainerVo vesselContainer);
	
	public int getCountContainerSeizeSeat(VesselContainerVo vesselContainer);
	public int getNewCountContainerSeizeSeat(VesselContainerVo vesselContainer);
	
	public WorkRecordVo getWrecord(WorkRecordVo workRecord);
	
}
