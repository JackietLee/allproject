package net.pingfang.serviceImpl.systemManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.systemManage.MenuDao;
import net.pingfang.entity.role.Trole;
import net.pingfang.entity.systemManage.MenuVo;
import net.pingfang.service.systemManage.MenuService;
import net.pingfang.utils.MenuUtil;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDao menuDao;
	/**
	 * 获取所有菜单
	 * @return
	 */
	@Override
	public MenuVo getListMenu(){
		MenuVo menu = null;
		List<MenuVo> menuList = menuDao.getListMenu();
		if(menuList !=null && menuList.size() >0) {
			//MenuVo menu2 = menuDao.getMenuById(1);
			menu = MenuUtil.treeMenu(menuList, menuList.get(0));
		}
		return menu;
	}
	/**
	 * 新增菜单
	 * @return
	 */
	@Transactional
	@Override
	public int addMenu(MenuVo menu){
		int parentId = menu.getParentId();
		if(0 == parentId) {
			menu.setParentId(1);
		}
		return menuDao.addMenu(menu);
	}
	/**
	 * 更新菜单
	 * @return
	 */
	@Transactional
	@Override
	public int updateMenu(MenuVo menu){
		return menuDao.updateMenu(menu);
	}
	/**
	 * 删除菜单
	 * @return
	 */
	@Transactional
	@Override
	public int deleteMenu(int id){
		return menuDao.deleteMenu(id);
	}
	/**
	 * 根据ID子菜单数据
	 * @return
	 */
	@Override
	public int getCountMenuByparentId(int id){
		return menuDao.getCountMenuByparentId(id);
	}
	/**
	 * 根据角色获取相应菜单
	 * @return
	 */
	@Override
	public Map<String,Object> getListMenuByRole(List<Trole> list){
		Map<String,Object> map = null;
		List<MenuVo> menuList = menuDao.getListMenuByRole(list);
		if(menuList !=null && menuList.size() >0) {
			map = new HashMap<String,Object>();
			List<MenuVo> displayYesList = new ArrayList<MenuVo>();
			List<MenuVo> displayNoList = new ArrayList<MenuVo>();
			String yes = "yes";
			for(MenuVo m : menuList) {
				if(yes.equals(m.getDisplay())) {
					displayYesList.add(m);
				}else {
					displayNoList.add(m);
				}
			}
			MenuVo menu = MenuUtil.treeMenu(displayYesList, menuList.get(0));
			map.put("treeMenu", menu);
			map.put("displayNoList", displayNoList);
		}
		return map;
	}

}
