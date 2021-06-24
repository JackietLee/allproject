package net.pingfang.service.systemManage;

import java.util.List;

import net.pingfang.entity.role.Tuserrole;

public interface UserRroleService {

	/**
	 * 根据用户ID获取用户角色
	 * @param id
	 * @return
	 */
	public List<Tuserrole> getUserRoleList(int id);
	
	/**
	 * 批量新增用户角色
	 * @param id
	 * @return
	 */
	public int addUserRoleList(List<Tuserrole> list);
	
	/**
	 * 根据用户ID删除用户角色
	 * @param id
	 * @return
	 */
	public int deleteUserRoleList(int id);
	
	/**
	 * 根据角色ID查询角色是否被引用
	 * @param id
	 * @return
	 */
	public int getCountUrByRoleId(int roleId);


}
