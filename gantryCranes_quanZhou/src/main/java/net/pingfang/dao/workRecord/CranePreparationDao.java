package net.pingfang.dao.workRecord;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.work.CranePreparationVo;

/**
 *  岸桥配制信息Dao（Crane_Preparation表）
 * @author Administrator
 * @since 2019-05-22
 *
 */
public interface CranePreparationDao {

	/**
	 * 获取所有岸桥配制信息
	 * @return
	 */
	public List<CranePreparationVo> getAllCranePreparationList();
	
	/**
	 * 批量插入岸桥配制信息
	 * @param list
	 * @return
	 */
	public int insertCranePreparation(List<CranePreparationVo> list);
	
	/**
	 * 删除一条岸桥配制信息
	 * @param id
	 * @return
	 */
	public int deleteCranePreparation(int id);
	/**
	 * 根据岸桥编号获取一条岸桥配制信息
	 * @param craneNum
	 * @return
	 */
	public List<CranePreparationVo> getCranePreparation(String craneNum);
	/**
	 * 根据岸桥编号批量获取岸桥配制信息
	 * @param craneNumList
	 * @return
	 */
	public List<CranePreparationVo> getListCranePreparation(@Param("list")List<String> craneNumList, @Param("type")String type);
	/**
	 * 获取所有岸桥信息 
	 * 下拉列表框 
	 * @return
	 */
	public List<CranePreparationVo> getCraneInfoList();
	/**
	 * 获取所有没被引用的岸桥信息
	 * 下拉列表框 
	 * @return
	 */
	public List<CraneInfoVo> getNotUsedCraneInfoList();
	/**
	 * 查询岸桥配制是否已经存在
	 * @return
	 */
	public int getCountCranePreparationByCraneNum(String craneNum);
	
	/**
	 * 删除一条岸桥配制信息
	 * @param id
	 * @return
	 */
	public int deleteCranePreparationByCraneNum(CranePreparationVo cranePreparationVo);
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	public String getAlongsideByVesselCood(String vesselCood);
	/**
	 * 更新BayWidth
	 * @param cranePreparationVo
	 * @return
	 */
	public int updateBayWidth(CranePreparationVo cranePreparationVo);
}
