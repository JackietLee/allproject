package net.pingfang.dao.systemManage;

import java.util.List;
import net.pingfang.entity.role.Trole;

public interface RoleDao {
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
	public int addRole(Trole menu);
	/**
	 * 更新角色
	 * @return
	 */
	public int updateRole(Trole menu);
	/**
	 * 删除角色
	 * @return
	 */
	public int deleteRole(int id);

}
