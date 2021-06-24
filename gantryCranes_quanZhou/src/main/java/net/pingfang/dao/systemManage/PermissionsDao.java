package net.pingfang.dao.systemManage;

import java.util.List;
import net.pingfang.entity.role.Trole;
import net.pingfang.entity.systemManage.PermissionsVo;

public interface PermissionsDao {
	
	/**
	 * 获取所有权限
	 * @return
	 */
	public List<PermissionsVo> getListPermissions();
	
	/**
	 * 根据角色获取相应权限数据
	 * @return
	 */
	public List<PermissionsVo> getPermissionsListByRole(List<Trole> list);
	
	/**
	 * 新增权限
	 * @return
	 */
	public int addPermissions(PermissionsVo menu);
	/**
	 * 更新权限
	 * @return
	 */
	public int updatePermissions(PermissionsVo menu);
	/**
	 * 删除权限
	 * @return
	 */
	public int deletePermissions(int id);
	
	/**
	 * 获取权限最小的ID
	 * @return
	 */
	public int getMinPermissionsId();
	
	/**
	 * 根据菜单ID查询权限数据
	 * @return
	 */
	public int getCountPermissionsByMeuId(int meuId);

}
