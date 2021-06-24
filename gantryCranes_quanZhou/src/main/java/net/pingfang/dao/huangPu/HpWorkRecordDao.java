package net.pingfang.dao.huangPu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface HpWorkRecordDao {
	
	/**
	  * 导出
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> exportAllHpWorkRecord(WorkRecordVo workRecordVo);
	/**
	 * 获取所有作业记录
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getHpAllWorkRecordList(Map<String, Object> map);
	/**
	 * 获取所有作业记录总数
	 * @return
	 */
	public int getHpCountWorkRecord(WorkRecordVo workRecordVo);
	/**
	 *  根据int查询作业记录信息
	 * @param int
	 * @return
	 */
	//public WorkRecordVo getHpWorkRecordById(int id);
	
	/**
	 * 查询黄埔实时作业数据
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getHpRealTimeWorkRecordList(@Param("list")List<CranePreparationVo> cranePreparationList, @Param("state")int state);

	/**
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public WorkRecordVo getHpAlreadyWorkRecordById(int id);
	
	/**
	 * 插入门机作业数据
	 * @param workRecordVo
	 * @return
	 */
	public int insertHpWorkRecord(WorkRecordVo workRecordVo);
	
	/**
	 * 更新门机作业数据
	 * @param workRecordVo
	 * @return
	 */
	public int updateHpWorkRecord(WorkRecordVo workRecordVo);
	/**
	 * 查询箱号是否存在
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getHpRecord(WorkRecordVo workRecordVo);
	/**
	 * 更新门机作业数据状态
	 * @param workRecordVo
	 * @return
	 */
	public int updateHpWorkRecordStateById(WorkRecordVo workRecordVo);
	/**
	 * 删除门机作业记录
	 * @param id
	 * @return
	 */
	public int deleteHpMjWorkRecordById(int id);
	/**
	 * 删除门机作业记录图片
	 * @param workId
	 * @return
	 */
	public int deleteHpMjImgByWorkId(int workId);
}
