package net.pingfang.serviceImpl.vessel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pingfang.dao.vessel.VesselListDao;
import net.pingfang.entity.importFile.VesselListVo;
import net.pingfang.entity.page.PageVo;
import net.pingfang.service.vessel.VesselListService;

@Service
public class VesselListServiceImpl implements VesselListService{
	@Autowired
	private VesselListDao vesselListDao;
	
	/**
	 * 获取所有船清单数据
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@Override
	public PageVo<VesselListVo> getAllVesselList(VesselListVo vesselList){
		if(null == vesselList) {
			vesselList = new VesselListVo();
		}
		PageVo<VesselListVo> pageVo = new PageVo<VesselListVo>();
		int totalCount = this.getCountVesselList(vesselList);
		if(totalCount >0) {			
			pageVo.initPage(vesselList.getCurrentPage(), vesselList.getPageSize(), totalCount);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageVo", pageVo);
			map.put("vesselList", vesselList);
			
			List<VesselListVo> wrList = vesselListDao.getAllVesselList(map);
			pageVo.setList(wrList);
		}
		return pageVo;
	}
	/**
	 * 获取所有船清单数据总数
	 * @return
	 */
	@Override
	public int getCountVesselList(VesselListVo vesselList) {
		return vesselListDao.getCountVesselList(vesselList);
	}
	
	/**
	 * 插入船清单数据
	 * @param list
	 * @return
	 */
	@Override
	public int insertVesselList(List<VesselListVo> list) {
		return vesselListDao.insertVesselList(list);
	}
	
	/**
	 * 根据ID更新船清单数据
	 * @param list
	 * @return
	 */
	@Override
	public int updateVesselListById(VesselListVo vesselList) {
		return vesselListDao.updateVesselListById(vesselList);
	}
	/**
	 * 根据ID删除船清单数据
	 * @param id
	 * @return
	 */
	@Override
	public int deletetVesselListById(List<Integer> idList) {
		return vesselListDao.deletetVesselListById(idList);
	}
	/**
	 * 根据船舶艘次号，箱号，作业类型查询舱单数据
	 * @param id
	 * @return
	 */
	@Override
	public List<VesselListVo> getVesselListByNumber(VesselListVo vesselList) {
		return vesselListDao.getVesselListByNumber(vesselList);
	}
}
