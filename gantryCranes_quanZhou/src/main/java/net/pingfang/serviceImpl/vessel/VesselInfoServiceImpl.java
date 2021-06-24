package net.pingfang.serviceImpl.vessel;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.vessel.VesselInfoDao;
import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.VesselInfoVo;
import net.pingfang.service.vessel.VesselInfoService;

/**
* 船舶信息ServiceImpl
* @author Administrator
* @since 2019-06-01
*
*/
@Service
public class VesselInfoServiceImpl implements VesselInfoService {
	
	@Autowired
	private VesselInfoDao vesselInfoDao;

	/**
	 * 分页获取所有船舶信息
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselInfoVo
	 * @return
	 */
	@Override
	public PageVo<VesselInfoVo> getPageVesselInfoList(VesselInfoVo  vesselInfo) {
		PageVo<VesselInfoVo> pageVo = new PageVo<VesselInfoVo>();
		int totalCount = this.getCountBerthPlanInfo(vesselInfo);
		if(totalCount >0) {			
			pageVo.initPage(vesselInfo.getCurrentPage(), vesselInfo.getPageSize(), totalCount);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageVo", pageVo);
			map.put("vesselInfo", vesselInfo);
			
			pageVo.setList(vesselInfoDao.getPageVesselInfoList(map));
		}
		return pageVo;
	}

	/**
	 * 获取所有船舶信息记录总数
	 * @return
	 */
	@Override
	public int getCountBerthPlanInfo(VesselInfoVo vesselInfo) {
		return vesselInfoDao.getCountBerthPlanInfo(vesselInfo);
	}

	/**
	 * 根据ID获取一条船舶信息
	 * @param id
	 * @return
	 */
	@Override
	public VesselInfoVo getVesselInfoById(int id) {
		return vesselInfoDao.getVesselInfoById(id);
	}

	/**
	 * 插入一条船舶信息
	 * @param insertVesselInfo
	 * @return
	 */
	@Transactional
	@Override
	public int insertVesselInfo(VesselInfoVo vesselInfo) {
		return vesselInfoDao.insertVesselInfo(vesselInfo);
	}

	/**
	 * 更新一条船舶信息
	 * @param vesselInfo
	 * @return
	 */
	@Transactional
	@Override
	public int updateVesselInfo(VesselInfoVo vesselInfo) {
		return vesselInfoDao.updateVesselInfo(vesselInfo);
	}

	/**
	 * 删除一条船舶信息
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteVesselInfoById(int id) {
		return vesselInfoDao.deleteVesselInfoById(id);
	}

}
