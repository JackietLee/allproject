package net.pingfang.dao.systemManage;

import java.util.List;

import net.pingfang.entity.role.Trole;
import net.pingfang.entity.systemManage.MenuVo;

public interface MenuDao {
	/**
	 * 根据ID获取一条菜单数据
	 * @return
	 */
	public MenuVo getMenuById(int id);
	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<MenuVo> getListMenu();
	/**
	 * 新增菜单
	 * @return
	 */
	public int addMenu(MenuVo menu);
	/**
	 * 更新菜单
	 * @return
	 */
	public int updateMenu(MenuVo menu);
	/**
	 * 删除菜单
	 * @return
	 */
	public int deleteMenu(int id);
	/**
	 * 根据ID子菜单数据
	 * @return
	 */
	public int getCountMenuByparentId(int id);
	
	/**
	 * 根据角色获取相应菜单
	 * @return
	 */
	public List<MenuVo> getListMenuByRole(List<Trole> list);

}
