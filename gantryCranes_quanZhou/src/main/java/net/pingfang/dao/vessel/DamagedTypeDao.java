package net.pingfang.dao.vessel;

import java.util.List;

import net.pingfang.entity.vessel.DamagedTypeVo;

/**
 * 残损信息DAO
 * "damaged_type"表
 * @author Administrator
 * 2019-07-29
 *
 */
public interface DamagedTypeDao {

	/**
	 * 获取所有残损类型信息
	 * @return
	 */
	public List<DamagedTypeVo> getDamagedInforList();
	/**
	 * 根据ID获取一条残损类型信息
	 * @param id
	 * @return
	 */
	public DamagedTypeVo getDamagedInforById(int id);
	/**
	 * 新增残损类型信息
	 * @return
	 */
	public int insertDamagedInfor(DamagedTypeVo damagedInfor);
	/**
	 * 更新残损类型信息
	 * @return
	 */
	public int updateDamagedInfor(DamagedTypeVo damagedInfor);
	/**
	 * 根据id删除残损类型信息
	 * @return
	 */
	public int deleteDamagedInfor(int id);
	/**
	 * 获取所有残损类型下拉列表
	 * @return
	 */
	public List<DamagedTypeVo> getDamagedInforSelect();
	/**
	 * 获取所有残损位置下拉列表
	 * @return
	 */
	public List<DamagedTypeVo> getDamagedPositionSelect();
}
