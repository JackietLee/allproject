package net.pingfang.service.vessel;

import java.util.List;
import java.util.Map;
import net.pingfang.entity.vessel.TruckInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;

/**
* 船舶集装箱Service
* @author Administrator
* @since 2019-06-01
*
*/
public interface VesselContainerService {
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
	 * 匹配仓单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber
	 * @return
	 */
	//public List<VesselContainerVo> getVesselContainerByVesselNumber(String vesselNumber,String containerNumber);
	/**
	 * 匹配仓单（数据服务调用）
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselContainer
	 * @return
	 */
	public boolean booleanVesselContainer(VesselContainerVo vesselContainer);
	/**
	 * 获取车顶下拉列表
	 * @return
	 */
	public Map<String,List<TruckInfoVo>> getTruckInfoList(List<VesselContainerVo> vesselContainerList);
	/**
	 * 获取集装箱在船上的位置
	 * @param vesselContainerVo
	 * @return
	 */
	public List<Object> getVesselContainerList(List<VesselContainerVo> vesselContainerList);
	/**
	 * 根据ID获取一条船舶集装箱
	 * @param id
	 * @return
	 */
	//public VesselContainerVo getVesselContainerById(int id);
	/**
	 * 新增贝位
	 * @param vesselContainer
	 * @return
	 */
	//public int insertBayInfo(VesselContainerVo vesselContainer);
	/**
	 * 更新一条船舶集装箱
	 * @param vesselContainer
	 * @return
	 */
	public int updateBayInfo(VesselContainerVo vesselContainer);
	//public int getCountContainer(VesselContainerVo vesselContainer);
	
	/**
	 * 查询车顶号是否存在
	 * @return
	 */
	public int getCountTruckNumber(String truckNumber);
	
	/**
	 * 匹配舱单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber  箱号（如果是双箱则用分号隔开，如："TBJU77889999;TBJU88999999"）
	 * @serialData 2019-11-15
	 * @return
	 */
	//public Map<String,Object>matchManifest(VesselContainerVo vesselContainer);
	/**
	 * 删除一条船舶集装箱Bay
	 * @param id
	 * @return
	 */
	//public int deleteBayInfo(VesselContainerVo vesselContainer);
}
