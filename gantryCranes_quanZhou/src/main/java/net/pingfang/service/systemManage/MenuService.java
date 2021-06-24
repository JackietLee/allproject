package net.pingfang.service.systemManage;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.role.Trole;
import net.pingfang.entity.systemManage.MenuVo;

public interface MenuService {
	/**
	 * 获取所有菜单
	 * @return
	 */
	public MenuVo getListMenu();
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
	public Map<String,Object> getListMenuByRole(List<Trole> list);
}
