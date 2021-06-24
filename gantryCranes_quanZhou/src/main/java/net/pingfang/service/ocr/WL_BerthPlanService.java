package net.pingfang.service.ocr;

import java.util.List;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.work.DamageInforRecordVo;

/**
 * 泊位计划信息Service
 * tos_berth_plan  (泊位计划)
 * @author Administrator
 * @since 2019-06-01
 *
 */
public interface WL_BerthPlanService {
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
	 * 根据ID获取一条泊位计划信息
	 * @param id
	 * @return
	 */
//	public BerthPlanInfoVo getBerthPlanInfoById(String vesselVoyageNumber);	
	/**
	 * 根据船舶艘次号获取一条泊位计划信息
	 * @param id
	 * @return
	 */
	public BerthPlanInfoVo getBerthPlanInfoByVesselNumber(String vesselNumber);	
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
//	public List<BerthPlanInfoVo> exportBerthPlanInfo(BerthPlanInfoVo berthPlanInfoVo);
	
	/**
	 * 获取船下拉列表框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	public List<BerthPlanInfoVo> getBerthPlanListBox(BerthPlanInfoVo berthPlanInfoVo);
	
	
	
	
	
	/**
	 * CranePreparationDao
	 * 获取所有岸桥信息 
	 * 下拉列表框 
	 * @return
	 */
	//public List<CranePreparationVo> getCraneInfoList();
	/**
	 * 通过vesselNumber获取船信息
	 * @return
	 */
	public List<BerthPlanInfoVo> getBerthPlanInfoVoListByVesselNumber(List<String> vesselNumberList);
	
	/**
	 * WorkRecordDao
	 *  根据id查询作业记录信息
	 * @param workJson
	 * @return
	 */
	//public WorkRecordVo getWorkRecordById(int id);
	/**
	 * WorkRecordDao
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	//public WorkRecordVo getAlreadyWorkRecordById(WorkRecordVo workRecordVo);
	/**
	 * DamagedInforRecordDao
	 * 获取作业记录
	 * @return
	 */
	//public List<WorkRecordVo> getWorkRecordList(WorkRecordVo workRecordVo);
	/**
	 * 通过航次获取船舶艘次号
	 * @return
	 */
//	public List<String> getVesselNumberListByVoyage(String inVoyage, String outVoyage);
	/**
	 * 发送残损数据到华东系统
	 * @return
	 */
	public void damageSynchronization(DamageInforRecordVo damageInforRecord);
	
}
