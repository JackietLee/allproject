package net.pingfang.service.systemManage;

import java.util.List;

import net.pingfang.entity.role.Trolemenu;

public interface RolePermissionsService {
	/**
	 * 根据用户ID获取角色权限
	 * @param id
	 * @return
	 */
	public List<Trolemenu> getRolePermissionsListById(int id);
	/**
	 * 批量新增角色权限
	 * @param list
	 * @return
	 */
	public int addRolePermissionsList(List<Trolemenu> list);
	
	/**
	 * 根据用户ID删除角色权限
	 * @param id
	 * @return
	 */
	public int deleteRolePermissions(int id);
	
	/**
	 * 根据权限ID查询权限是否被引用
	 * @param permissionsId
	 * @return
	 */
	public int getCountRpBypermissionsId(int permissionsId);

}
