package net.pingfang.serviceImpl.vessel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.vessel.DamagedTypeDao;
import net.pingfang.entity.vessel.DamagedTypeVo;
import net.pingfang.service.vessel.DamagedTypeService;
/**
 * 残损类型信息ServiceImpl
 * "damaged_type"表
 * @author Administrator
 * 2019-07-29
 *
 */
@Service
public class DamagedTypeServiceImpl implements DamagedTypeService {

	@Autowired
	private DamagedTypeDao damagedInforDao;
	
	/**
	 * 获取所有残损类型信息
	 * @return
	 */
	@Override
	public List<DamagedTypeVo> getDamagedInforList(){
		return damagedInforDao.getDamagedInforList();
	}
	/**
	 * 根据ID获取一条残损类型信息
	 * @param id
	 * @return
	 */
	@Override
	public DamagedTypeVo getDamagedInforById(int id){
		return damagedInforDao.getDamagedInforById(id);
	}
	/**
	 * 新增残损类型信息
	 * @return
	 */
	@Transactional
	@Override
	public int insertDamagedInfor(DamagedTypeVo damagedInfor){
		return damagedInforDao.insertDamagedInfor(damagedInfor);
	}
	/**
	 * 更新残损类型信息
	 * @return
	 */
	@Transactional
	@Override
	public int updateDamagedInfor(DamagedTypeVo damagedInfor){
		return damagedInforDao.updateDamagedInfor(damagedInfor);
	}
	/**
	 * 根据id删除残损类型信息
	 * @return
	 */
	@Transactional
	@Override
	public int deleteDamagedInfor(int id){
		return damagedInforDao.deleteDamagedInfor(id);
	}
	/**
	 * 获取所有残损类型下拉列表
	 * @return
	 */
	@Override
	public List<DamagedTypeVo> getDamagedInforSelect(){
		return damagedInforDao.getDamagedInforSelect();
	}
	/**
	 * 获取所有残损位置下拉列表
	 * @return
	 */
	@Override
	public List<DamagedTypeVo> getDamagedPositionSelect(){
		return damagedInforDao.getDamagedPositionSelect();
	}
}
