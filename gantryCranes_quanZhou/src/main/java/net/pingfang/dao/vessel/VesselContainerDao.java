package net.pingfang.dao.vessel;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import net.pingfang.entity.vessel.TruckInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;

/**
* 船舶集装箱Dao
* @author Administrator
* @since 2019-06-01
*
*/
public interface VesselContainerDao {
	/**
	 * 分页获取所有船舶集装箱
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getPageVesselContainerList(Map<String, Object> map);
	/**
	 * 获取所有船舶集装箱记录总数
	 * @return
	 */
	public int getCountVesselContainer(Map<String, Object> map);
	
	/**
	 * 2020-03-07 更新
	 * 匹配仓单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber
	 * @return
	 */
	//public List<VesselContainerVo> getVesselContainerByVesselNumber(@Param("vesselNumber")String vesselNumber, @Param("containerNumber")String containerNumber, @Param("jobType")String jobType);
	/**
	 * 获取车顶下拉列表
	 * @return
	 */
	public List<TruckInfoVo> getTruckInfoList();
	/**
	 * 获取集装箱在船上的位置
	 * @param vesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getVesselContainerList(VesselContainerVo vesselContainerVo);
	/**
	 * 获取装船作业箱
	 * @param vesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getWorkRecordContainerList(@Param("vesselNumber")String vesselNumber, @Param("vesselCode")String vesselCode,@Param("list")List<VesselContainerVo> vesselContainerVo);
	
	
	public List<VesselContainerVo> getContainerList(@Param("vesselNumber")String vesselNumber, @Param("vesselCode")String vesselCode,@Param("list")List<VesselContainerVo> vesselContainerVo);
	/**
	 * 获取箱
	 * @param vesselContainerVo
	 * @return
	 */
	public List<VesselContainerVo> getContainerSeizeSeatList(@Param("vesselNumber")String vesselNumber, @Param("vesselCode")String vesselCode,@Param("list")List<VesselContainerVo> vesselContainerVo);
	
	/**
	 * 查询车顶号是否存在
	 * @return
	 */
	public int getCountTruckNumber(String truckNumber);
	
	/**
	 * 新增贝位
	 * @param vesselContainer
	 * @return
	 */
	//public int insertBayInfo(VesselContainerVo vesselContainer);
	/**
	 * 2020-03-09 更新
	 * 更新贝位
	 * @param vesselContainer
	 * @return
	 */
	//public int updateBayInfo(VesselContainerVo vesselContainer);
	/*
	 * 2020-03-09 更新
	 
	//public int getCountContainer(VesselContainerVo vesselContainer);
	 * */
}
