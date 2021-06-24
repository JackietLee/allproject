package net.pingfang.service.vessel;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface DamagedInforRecordService {

	/**
	 * 获取作业数据箱号
	 * @return
	 */
	public List<WorkRecordVo> getWorkRecordSelect(WorkRecordVo workRecordVo);
	/**
	 * 获取所有残损信息
	 * @return
	 */
	public List<DamageInforRecordVo> getDamageInforRecordList(DamageInforRecordVo damageInforRecord);
	/**
	 * 根据ID获取一条残损信息
	 * @param id
	 * @return
	 */
	//public Map<String,Object> getDamageInforRecordById(String containerNumber);
	/**
	 * 根据作业编号获取一条残损信息
	 * @param id
	 * @return
	 */
	public Map<String,Object> getDamageInforRecordBySeqNo(DamageInforRecordVo damageInforRecord);
	/**
	 * 新增残损信息
	 * @return
	 */
	public int insertDamagedInforRecord(DamageInforRecordVo damageInforRecord);
	/**
	 * 更新残损信息
	 * @return
	 */
	public int updateDamagedInforRecord(DamageInforRecordVo damageInforRecord);
	/**
	 * 根据作业编号更新残损信息
	 * @return
	 */
	public int updateDamagedInforRecordByWorkId(DamageInforRecordVo damageInforRecord);
	/**
	 * 更新残损信息同步状态为'Y'
	 * @return
	 */
	public int updateSynchronizationById(int id);
	/**
	 * 根据id删除残损信息
	 * @return
	 */
	public int deleteDamagedInforRecord(int id);
	
	/**
	 * 获取作业记录
	 * @return
	 */
	public Map<String,List<WorkRecordVo>> getWorkRecordList(List<DamageInforRecordVo> list);
	/**
	 * 发送残损数据到华东系统
	 */
	public void damageSynchronization(DamageInforRecordVo damageInforRecord);
	/**
	 * 根据作业ID查询数据是否已经存在
	 * @return
	 */
	public int getCountByWorkId(int workId);
}
