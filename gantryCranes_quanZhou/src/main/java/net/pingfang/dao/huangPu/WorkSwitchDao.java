package net.pingfang.dao.huangPu;

import java.util.List;

import net.pingfang.entity.work.CranePreparationVo;

public interface WorkSwitchDao {
	
	
	/**
	 * 获取门机配制
	 * @return
	 */
	public List<CranePreparationVo> getAllMjPreparationList();
	public CranePreparationVo getHpCranePreparationByCraneNum(String craneNum);
	
	/**
	 * 更新门机配制
	 * @param cranePreparationVo
	 * @return
	 */
	public int updateHpCranePreparation(CranePreparationVo cranePreparationVo);
	/**
	 * 更新门机配制开始或停止作业
	 * @param cranePreparationVo
	 * @return
	 */
	public int updateActivity(CranePreparationVo cranePreparationVo);
	
	
	/**
	 * 新增门机配制
	 * @param cranePreparationVo
	 * @return
	 */
	public int addHpCranePreparation(CranePreparationVo cranePreparationVo);
	/**
	 * 批量新增门机配制
	 * @param cranePreparationVo
	 * @return
	 */
	public int addHpCranePreparationList(List<CranePreparationVo> cranePreparationList);
	/**
	 * 删除门机配制数据
	 * @param id
	 * @return
	 */
	public int deleteHpCranePreparation(int id);
}
