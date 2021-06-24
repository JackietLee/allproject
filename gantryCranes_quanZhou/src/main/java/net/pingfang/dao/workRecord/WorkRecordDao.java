package net.pingfang.dao.workRecord;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;

/**
 * 作业记录Dao
 * @author Administrator
 * @since 2019-05-22
 *
 */
public interface WorkRecordDao {

	/**
	 * 获取所有作业记录
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getAllWorkRecordList(Map<String, Object> map);
	/**
	 * 获取所有作业记录总数
	 * @return
	 */
	public int getCountWorkRecord(WorkRecordVo workRecordVo);
	/**
	 * 插入一条作业记录
	 * @param workRecordVo
	 * @return
	 */
	public int insertWorkRecord(WorkRecordVo workRecordVo);
	/**
	 * 批量插入作业记录
	 * @param workRecordVo
	 * @return
	 */
	public int insertWorkRecordList(List<WorkRecordVo> list);
	/**
	 * 插入一条残损信息记录
	 * @param damageInforRecordList
	 * @return
	 */
	public int insertDamageInforRecord(List<DamageInforRecordVo> damageInforRecordList);
	/**
	 * 插入一条图片信息记录
	 * @param imgInfoList
	 * @return
	 */
	public int insertImgInfo(List<ImgInfoVo> imgInfoList);
	
	/**
	 * 根据seqNo任务编号获取所有作业记录总数
	 * @return
	 */
	public int getCountWorkRecordBySeqNo(String seqNo);	
	public List<WorkRecordVo> getWorkRecordBySeqNo(String seqNo);
	public WorkRecordVo getWorkRecordId(String seqNo);
	/**
	 * 删除一条作业记录
	 * @param seqNo任务编号
	 * @return
	 */
	public int deletetWorkRecord(String seqNo);
	public int deletetWorkRecordById(int id);
	/**
	 * 删除一条残损信息记录
	 * @param seqNo任务编号
	 * @return
	 */
	public int deleteDamageInforRecord(String seqNo);
	public int deleteDamageInforByWorkId(int workId);
	/**
	 * 删除一条图片信息记录
	 * @param seqNo任务编号
	 * @return
	 */
	public int deleteImgInfo(String seqNo);
	/**
	 *  根据id查询作业记录信息
	 * @param workJson
	 * @return
	 */
	public WorkRecordVo getWorkRecordById(int id);
	public WorkRecordVo getNewWorkRecordById(int id);
	public List<WorkRecordVo> getWorkRecordListById(int id);
	/**
	 *  根据作业id查询残损记录信息
	 * @param workJson
	 * @return
	 */
	public List<DamageInforRecordVo> getDamageInforRecordByWorkId(int id);
	/**
	 *  根据seqNo查询图片记录信息
	 * @param workJson
	 * @return
	 */
	public List<ImgInfoVo> getImgInfoBySeqNo(String seqNo);
	/**
	 * 根据作业编号更新贝位
	 * @param list
	 * @return
	 */
	public int updateBayInfoBySeqNo(WorkRecordVo WorkRecord);
	public int updateBayInfoBySeqNoList(@Param("list")List<WorkRecordVo> list);
	public int updateBayInfoById(@Param("id")int id, @Param("bayInfo")String bayInfo);
	
	public int getCountBay(WorkRecordVo workRecordVo);
	public int getNewCountBay(@Param("vesselNumber")String vesselNumber, @Param("list")List<String> bayInfoList);
	
	
	/**
	 *导出所有作业记录
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> exportAllWorkRecord(WorkRecordVo workRecordVo);
	/**
	 *作业数据统计
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getWorkRecordStatistics(WorkRecordVo workRecordVo);
	/**
	 *集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
	 *集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public int getMountStatistics(@Param("list")List<WorkRecordVo> list, @Param("jobType")String jobType);
	/**
	 *集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	//public int getDsMountStatistics(List<WorkRecordVo> list);
	
	/**
	 *获取残损记录
	 * @param 查询条件 list
	 * @return
	 */
	public List<DamageInforRecordVo> getDamageInforRecordList(List<WorkRecordVo> list);
	/**
	 *  查询已理货数据
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	//public List<WorkRecordVo> getAlreadyWorkRecordList(@Param("list")List<CranePreparationVo> cranePreparationList, @Param("state")int state,@Param("bayList")List<String> bayList);
	public List<WorkRecordVo> getAlreadyWorkRecordList(@Param("list")List<CranePreparationVo> cranePreparationList, @Param("state")int state);
	public List<VesselContainerVo> getVesselPositionByVesselNumber(@Param("list")List<CranePreparationVo> cranePreparationList);
	
	/**
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public WorkRecordVo getAlreadyWorkRecordById(WorkRecordVo workRecordVo);
	/**
	 * 根据ID更新贝位
	 * @param workRecordVo
	 * @return
	 */
	//public int updateBayInfo(WorkRecordVo workRecordVo);
	/**
	 * 已理货数据二次提交更新
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordById(WorkRecordVo workRecordVo);
	public int updateHistoricalWorkRecord(WorkRecordVo workRecordVo);
	
	/**
	 *第三方调用接口
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getWorkRecordData(WorkRecordVo workRecordVo);
	/**
	 * 更新X,Y坐标
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordXY(WorkRecordVo workRecordVo);
	/**
	 * 更新状态
	 * 状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordState(WorkRecordVo workRecordVo);
	/**
	 * 更新图片
	 * @param workId
	 * @param seqNo
	 * @return
	 */
	public int updateImgInforBySeqNo(@Param("workId")int workId, @Param("seqNo")String seqNo);
	/**
	 * 数据重复校验
	 * @param workRecordVo
	 * @return
	 */
	public int getCountRecord(WorkRecordVo workRecordVo);
	/**
	 * 数据重复校验
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getRecord(WorkRecordVo workRecordVo);
	/**
	 * 根据船，箱号，作业状态查询数据
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getWorkRecord(@Param("vesselNumber") String vesselNumber, @Param("list")List<WorkRecordVo> list);
	/**
	 * 根据船，箱号，状态更新数据状态
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecord(@Param("vesselNumber") String vesselNumber, @Param("list")List<WorkRecordVo> list);
}
