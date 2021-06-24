package net.pingfang.serviceImpl.systemManage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.systemManage.PermissionsDao;
import net.pingfang.entity.role.Trole;
import net.pingfang.entity.systemManage.PermissionsVo;
import net.pingfang.service.systemManage.PermissionsService;
import net.pingfang.utils.MenuUtil;

@Service
public class PermissionsServiceImpl implements PermissionsService{
	
	@Autowired
	private PermissionsDao permissionsDao;
	
	/**
	 * 获取所有权限
	 * @return
	 */
	@Override
	public PermissionsVo getListPermissions(){
		PermissionsVo permissions = null;
		List<PermissionsVo> list = permissionsDao.getListPermissions();
		if(null !=list && list.size()>0) {
			permissions = MenuUtil.treePermissions(list, list.get(0));
		}
		return permissions;
	}
	/**
	 * 根据角色获取相应权限数据
	 * @return
	 */
	@Override
	public List<PermissionsVo> getPermissionsListByRole(List<Trole> list){
		return permissionsDao.getPermissionsListByRole(list);
	}
	/**
	 * 新增权限
	 * @return
	 */
	@Transactional
	@Override
	public int addPermissions(PermissionsVo permissions){
		return permissionsDao.addPermissions(permissions);
	}
	/**
	 * 更新权限
	 * @return
	 */
	@Transactional
	@Override
	public int updatePermissions(PermissionsVo permissions){
		return permissionsDao.updatePermissions(permissions);
	}
	/**
	 * 删除权限
	 * @return
	 */
	@Transactional
	@Override
	public int deletePermissions(int id){
		return permissionsDao.deletePermissions(id);
	}
	/**
	 * 获取权限最小的ID
	 * @return
	 */
	@Override
	public int getMinPermissionsId(){
		return permissionsDao.getMinPermissionsId();
	}
	/**
	 * 根据菜单ID查询权限数据
	 * @return
	 */
	@Override
	public int getCountPermissionsByMeuId(int meuId){
		return permissionsDao.getCountPermissionsByMeuId(meuId);
	}

}
