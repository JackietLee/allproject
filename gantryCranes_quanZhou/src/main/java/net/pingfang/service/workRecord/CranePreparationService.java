package net.pingfang.service.workRecord;

import java.util.List;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.work.CranePreparationVo;

/**
 * 岸桥配制信息Service
 * @author Administrator
 * @since 2019-06-17
 *
 */
public interface CranePreparationService {
	/**
	 * 获取所有岸桥配制信息
	 * @return
	 */
	public List<CranePreparationVo> getAllCranePreparationList();
	
	/**
	 * 插入一条岸桥配制信息
	 * @param cranePreparationVo
	 * @return
	 */
	public int insertCranePreparation(CranePreparationVo cranePreparationVo);
	
	/**
	 * 删除一条岸桥配制信息
	 * @param id
	 * @return
	 */
	public int deleteCranePreparation(int id);
	/**
	 * 删除一条岸桥配制信息
	 * @param id
	 * @return
	 */
	public int deleteCranePreparationByCraneNum(CranePreparationVo cranePreparationVo);
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
	public List<CranePreparationVo> getListCranePreparation(List<String> craneNumList, String type);
	/**
	 * 获取所有岸桥信息 
	 * 下拉列表框 
	 * @return
	 */
	public List<CranePreparationVo> getCraneInfoList();
	/**
	 * 获取所有没被引用的岸桥信息
	 * @return
	 */
	public List<CraneInfoVo> getNotUsedCraneInfoList();
	/**
	 * 查询岸桥配制是否已经存在
	 * @return
	 */
	public int getCountCranePreparationByCraneNum(String craneNum);
	/**
	 * 根据岸桥编号更新BAY
	 * @return
	 */
	public int updateBayByCraneNum(CranePreparationVo cranePreparationVo);
	/**
	 * 调用第三方接口同步数据
	 * 亮哥的接口
	 * @return
	 */
	public boolean berthPlansSynchronization();
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	public String getAlongsideByVesselCood(String vesselCood);
	/**
	 * 更新BayWidth
	 * @param list
	 * @return
	 */
	public int updateBayWidth(CranePreparationVo cranePreparationVo);
}
