package net.pingfang.dao.vessel;

import java.util.List;
import java.util.Map;
import net.pingfang.entity.vessel.BerthPlanInfoVo;

/**
 * 泊位计划信息Dao
 * @author Administrator
 * @since 2019-06-01
 *
 */
public interface BerthPlanDao {
	/**
	 * 分页获取所有泊位计划信息
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
	//public List<BerthPlanInfoVo> getPageBerthPlanInfoList(Map<String, Object> map);
	/**
	 * 获取所有泊位计划信息记录总数
	 * @return
	 */
	//public int getCountBerthPlanInfo(BerthPlanInfoVo berthPlanInfo);
	/**
	 * 插入一条泊位计划信息
	 * @param berthPlanInfo
	 * @return
	 */
	public int insertBerthPlan(BerthPlanInfoVo berthPlanInfo);
	/**
	 * 根据ID获取一条泊位计划信息
	 * @param id
	 * @return
	 */
//	public BerthPlanInfoVo getBerthPlanInfoById(int id);	
	/**
	 * 根据船舶艘次号获取一条泊位计划信息
	 * @param id
	 * @return
	 */
	//public BerthPlanInfoVo getBerthPlanInfoByVesselNumber(String vesselNumber);	
	/**
	 * 更新船舷方向
	 * @param berthPlanInfo
	 * @return
	 */
	//public int updateAlongside(BerthPlanInfoVo berthPlanInfo);
	/**
	 * Excel导出所有泊位计划信息
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
	//public List<BerthPlanInfoVo> exportBerthPlanInfo(BerthPlanInfoVo berthPlanInfoVo);
	
	/**
	 * 获取船下拉列表框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	public List<BerthPlanInfoVo> getBerthPlanListBox(BerthPlanInfoVo berthPlanInfoVo);
	
}
