package net.pingfang.service.vessel;

import java.util.List;
import net.pingfang.entity.importFile.VesselListVo;
import net.pingfang.entity.page.PageVo;

public interface VesselListService {
	
	/**
	 * 获取所有船清单数据
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public PageVo<VesselListVo> getAllVesselList(VesselListVo vesselList);
	/**
	 * 获取所有船清单数据总数
	 * @return
	 */
	public int getCountVesselList(VesselListVo vesselList);

	/**
	 * 插入船清单数据
	 * @param list
	 * @return
	 */
	public int insertVesselList(List<VesselListVo> list);
	
	/**
	 * 根据ID更新船清单数据
	 * @param list
	 * @return
	 */
	public int updateVesselListById(VesselListVo vesselList);
	/**
	 * 根据ID删除船清单数据
	 * @param id
	 * @return
	 */
	public int deletetVesselListById(List<Integer> idList);
	
	
	/**
	 * 根据船舶艘次号，箱号，作业类型查询舱单数据
	 * @param id
	 * @return
	 */
	public List<VesselListVo> getVesselListByNumber(VesselListVo vesselList);
	
	
}
