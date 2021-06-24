package net.pingfang.serviceImpl.systemManage;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.systemManage.RoleDao;
import net.pingfang.entity.role.Trole;
import net.pingfang.entity.role.Trolemenu;
import net.pingfang.service.systemManage.PermissionsService;
import net.pingfang.service.systemManage.RolePermissionsService;
import net.pingfang.service.systemManage.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	@Autowired
	private PermissionsService permissionsService;
	/**
	 * 获取所有角色
	 * @return
	 */
	@Override
	public List<Trole> getListRole(){
		return roleDao.getListRole();
	}
	/**
	 * 根据用户ID获取角色数据
	 * @return
	 */
	@Override
	public List<Trole> getRoleListByUserId(int userId){
		return roleDao.getRoleListByUserId(userId);
	}
	/**
	 * 新增角色
	 * @return
	 */
	@Transactional
	@Override
	public int addRole(Trole role){
		//新增角色
		int count = roleDao.addRole(role);
		//给角色赋默认根节点权限
		if(count >0) {
			int permissionsId = permissionsService.getMinPermissionsId();
			if(permissionsId >0) {
				List<Trolemenu> list = new ArrayList<Trolemenu>();
				Trolemenu tm = new Trolemenu();
				tm.setRoleId(role.getId());
				tm.setPermissionsId(permissionsId);
				list.add(tm);
				count = rolePermissionsService.addRolePermissionsList(list);
			}
		}
		return count;
	}
	/**
	 * 更新角色
	 * @return
	 */
	@Transactional
	@Override
	public int updateRole(Trole role){
		return roleDao.updateRole(role);
	}
	/**
	 * 删除角色
	 * @return
	 */
	@Transactional
	@Override
	public int deleteRole(int id){
		//删除该角色引用的权限
		int count = rolePermissionsService.deleteRolePermissions(id);
		count = roleDao.deleteRole(id);
		return count;
	}

}
