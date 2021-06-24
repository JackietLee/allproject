package net.pingfang.service.vessel;

import java.util.List;

//import javax.servlet.http.HttpServletResponse;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;

/**
 * 泊位计划信息Service
 * @author Administrator
 * @since 2019-06-3
 *
 */
public interface BerthPlanService {
	/**
	 * 分页获取所有泊位计划信息
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
	//public PageVo<BerthPlanInfoVo> getPageBerthPlanInfoList(BerthPlanInfoVo berthPlanInfoVo);
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
//	public BerthPlanInfoVo getBerthPlanInfoById(String vesselVoyageNumber);
	/**
	 * 更新船舷方向
	 * @param berthPlanInfo
	 * @return
	 */
	public int updateAlongside(BerthPlanInfoVo berthPlanInfo);
	/**
	 * Excel导出所有泊位计划信息
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
	//public void exportExcelBerthPlanInfo(HttpServletResponse response, BerthPlanInfoVo berthPlanInfoVo);
	
	/**
	 * 获取船下拉列表框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	public List<BerthPlanInfoVo> getBerthPlanListBox(BerthPlanInfoVo berthPlanInfoVo);
	
}
