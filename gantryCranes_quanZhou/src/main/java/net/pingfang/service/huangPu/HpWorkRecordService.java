package net.pingfang.service.huangPu;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface HpWorkRecordService {
	/**
	 * 获取所有作业记录
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public PageVo<WorkRecordVo> getHpAllWorkRecordList(WorkRecordVo workRecordVo);
	/**
	 *  根据int查询作业记录信息
	 * @param int
	 * @return
	 */
	public WorkRecordVo getHpWorkRecordById(int id);
	/**
	 * 查询黄埔实时作业数据
	 * @param workRecordVo
	 * @return
	 */
	public Map<String,List<WorkRecordVo>> getHpRealTimeWorkRecordList(List<CranePreparationVo> cranePreparationList, int state);
	
	/**
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * 2020-10-10
	 * @return
	 */
	public WorkRecordVo getHpAlreadyWorkRecordById(int id);
	
	/**
	 * 插入门机作业数据
	 * @param workRecordVo
	 * @return
	 */
	public int insertHpWorkRecord(String hpMjMsg);
	
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
