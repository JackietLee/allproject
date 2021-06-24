package net.pingfang.dao.systemManage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.role.Trolemenu;

public interface RolePermissionsDao {
	/**
	 * 根据角色ID获取角色权限
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
	 * 根据角色ID删除角色权限
	 * @param id
	 * @return
	 */
	public int deleteRolePermissions(@Param("roleId") int roleId, @Param("permissionsId") int permissionsId);
	/**
	 * 根据权限ID查询权限是否被引用
	 * @param permissionsId
	 * @return
	 */
	public int getCountRpBypermissionsId(int permissionsId);

}
