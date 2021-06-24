package net.pingfang.service.systemManage;

import java.util.List;
import net.pingfang.entity.role.Trole;

public interface RoleService {
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Trole> getListRole();
	/**
	 * 根据用户ID获取角色数据
	 * @return
	 */
	public List<Trole> getRoleListByUserId(int userId);
	/**
	 * 新增角色
	 * @return
	 */
	public int addRole(Trole trole);
	/**
	 * 更新角色
	 * @return
	 */
	public int updateRole(Trole trole);
	/**
	 * 删除角色
	 * @return
	 */
	public int deleteRole(int id);

}
