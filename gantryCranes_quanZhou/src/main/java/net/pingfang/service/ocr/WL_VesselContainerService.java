package net.pingfang.service.ocr;

import java.util.List;
import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;

/**
* 船舶集装箱Dao
* @author Administrator
* @since 2019-06-01
*
*/
public interface WL_VesselContainerService {
	/**
	 * 分页获取所有船舶集装箱
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselContainerVo
	 * @return
	 */
//	public PageVo<VesselContainerVo> getPageVesselContainerList(VesselContainerVo vesselContainer);
	/**
	 * 获取所有船舶集装箱记录总数
	 * @return
	 */
	//public int getCountVesselContainer(Map<String, Object> map);
	
	/**
	 * 匹配仓单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber
	 * @return
	 */
	//public List<VesselContainerVo> getVesselContainerByVesselNumber(String vesselNumber, String containerNumber, String jobType);
	
	/**
	 * 获取 装货港，卸货港，目的港
	 * @param vesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getVesselContainerList(VesselContainerVo vesselContainerVo);
	
	public List<VesselContainerVo> getContainerList(String vesselNumber, String vesselCode,String jobType,List<VesselContainerVo> vesselContainerVo);
	/**
	 * 更新贝位
	 * @param vesselContainer
	 * @return
	 */
	//public int updateBayInfo(VesselContainerVo vesselContainer);
	//public int getCountContainer(VesselContainerVo vesselContainer);
	
	
	
	
	
	/**
	 * WorkRecordDao
	 *作业数据统计
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	//public List<WorkRecordVo> getWorkRecordStatistics(WorkRecordVo workRecordVo);
	/**
	 * WorkRecordDao
	 *集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
	 *集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public int getMountStatistics(List<WorkRecordVo> list, String jobType);
	/**
	 * WorkRecordDao
	 * @param cranePreparationList
	 * @return
	 */
//	public List<VesselContainerVo> getVesselPositionByVesselNumber(List<CranePreparationVo> cranePreparationList);
	
	
	
}
