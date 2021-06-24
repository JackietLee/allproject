package net.pingfang.serviceImpl.systemManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.systemManage.RolePermissionsDao;
import net.pingfang.entity.role.Trolemenu;
import net.pingfang.service.systemManage.RolePermissionsService;

@Service
public class RolePermissionsServiceImpl implements RolePermissionsService{
	@Autowired
	private RolePermissionsDao rolePermissionsDao;
	/**
	 * 根据用户ID获取角色权限
	 * @param id
	 * @return
	 */
	@Override
	public List<Trolemenu> getRolePermissionsListById(int id){
		return rolePermissionsDao.getRolePermissionsListById(id);
	}
	/**
	 * 批量新增角色权限
	 * @param list
	 * @return
	 */
	@Transactional
	@Override
	public int addRolePermissionsList(List<Trolemenu> list){
		int count = 0;
		if(null !=list && list.size() >0) {
			int roleId = list.get(0).getRoleId();
			//删除权限
			count = rolePermissionsDao.deleteRolePermissions(roleId,1);
			//分配权限
			count = rolePermissionsDao.addRolePermissionsList(list);
		}
		return count;
	}
	
	/**
	 * 根据角色ID删除角色权限
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteRolePermissions(int id){
		return rolePermissionsDao.deleteRolePermissions(id,0);
	}
	
	/**
	 * 根据权限ID查询权限是否被引用
	 * @param permissionsId
	 * @return
	 */
	@Override
	public int getCountRpBypermissionsId(int permissionsId) {
		return rolePermissionsDao.getCountRpBypermissionsId(permissionsId);
	}


}
