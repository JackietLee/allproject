package net.pingfang.dao.test;

import net.pingfang.entity.vessel.CraneInfoVo;

/**
 * 岸桥信息Dao(crane_infor表)
 * @author Administrator
 * @since 2019-06-10
 *
 */
public interface CraneInfoDaoTest {

	/**
	 * 插入一条岸桥信息
	 * @param craneInfoVo
	 * @return
	 */
	public int insertCraneInfoTest(CraneInfoVo craneInfoVo);

	
}
