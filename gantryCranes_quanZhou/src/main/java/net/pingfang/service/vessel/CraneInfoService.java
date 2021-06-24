package net.pingfang.service.vessel;

import java.util.List;

import net.pingfang.entity.vessel.CraneInfoVo;

/**
 * 岸桥信息Service
 * @author Administrator
 * @since 2019-06-10
 *
 */
public interface CraneInfoService {
	/**
	 * 获取所有岸桥信息
	 * @return
	 */
	public List<CraneInfoVo> getCraneInfoList(CraneInfoVo craneInfoVo);
	/**
	 * 根据ID获取一条岸桥信息
	 * @param id
	 * @return
	 */
	public CraneInfoVo getCraneInfoById(int id);	
	/**
	 * 根据岸桥编号获取一条岸桥信息
	 * @param craneNum
	 * @return
	 */
	public CraneInfoVo getCraneInfoByCraneNum(String craneNum);	
	/**
	 * 插入一条岸桥信息
	 * @param craneInfoVo
	 * @return
	 */
	public int insertCraneInfo(CraneInfoVo craneInfoVo);
	/**
	 * 更新一条岸桥信息
	 * @param CraneInfoVo
	 * @return
	 */
	public int updateCraneInfo(CraneInfoVo CraneInfoVo);
	/**
	 * 删除一条岸桥信息
	 * @param id
	 * @return
	 */
	public int deleteCraneInfo(int id);
	/**
	 * 重启岸桥相应的控制程序
	 * @param CraneInfoVo
	 * @return
	 */
	public String restartProgram(CraneInfoVo CraneInfoVo);
	
	/**
	 *查询黄埔门机下拉列表
	 * @return
	 */
	public List<CraneInfoVo> getHpCraneOptions();
	
	
	
}
