package net.pingfang.service.vessel;

import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.VesselInfoVo;

/**
* 船舶信息Service
* @author Administrator
* @since 2019-06-01
*
*/
public interface VesselInfoService {
	/**
	 * 分页获取所有船舶信息
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselInfoVo
	 * @return
	 */
	public PageVo<VesselInfoVo> getPageVesselInfoList(VesselInfoVo  vesselInfo);
	/**
	 * 获取所有船舶信息记录总数
	 * @return
	 */
	public int getCountBerthPlanInfo(VesselInfoVo vesselInfo);
	/**
	 * 根据ID获取一条船舶信息
	 * @param id
	 * @return
	 */
	public VesselInfoVo getVesselInfoById(int id);
	/**
	 * 插入一条船舶信息
	 * @param insertVesselInfo
	 * @return
	 */
	public int insertVesselInfo(VesselInfoVo vesselInfo);
	/**
	 * 更新一条船舶信息
	 * @param vesselInfo
	 * @return
	 */
	public int updateVesselInfo(VesselInfoVo vesselInfo);
	/**
	 * 删除一条船舶信息
	 * @param id
	 * @return
	 */
	public int deleteVesselInfoById(int id);
}
