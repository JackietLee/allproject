package net.pingfang.dao.vessel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.WorkRecordVo;

/**
 * 残损信息DAO
 * "damaged_infor_record"表
 * @author Administrator
 * 2019-07-29
 *
 */
public interface DamagedInforRecordDao {

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
	//public List<DamageInforRecordVo> getDamageInforRecordById(String containerNumber);
	/**
	 * 根据作业编号获取一条残损信息
	 * @param id
	 * @return
	 */
	public DamageInforRecordVo getDamageInforRecordBySeqNo(DamageInforRecordVo damageInforRecord);
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
	//public List<WorkRecordVo> getWorkRecordList(WorkRecordVo workRecordVo);
	//public List<WorkRecordVo> getWorkRecordList2(@Param("workRecord")WorkRecordVo workRecordVo, @Param("vesselNumberList") List<String> vesselNumberList);
	public List<WorkRecordVo> getWorkRecordList( @Param("list") List<DamageInforRecordVo> list);
	
	/**
	 * 根据作业ID查询数据是否已经存在
	 * @return
	 */
	public int getCountByWorkId(int workId);
}
