package net.pingfang.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import net.pingfang.entity.systemManage.MenuVo;
import net.pingfang.entity.systemManage.PermissionsVo;
/**
 * 构建树形菜单
 * MenuUtils
 * @author cgf
 * 2019年8月17日
 */
public class MenuUtil {
    /**
     * 针对二级菜单
     * treeMenuList:( ). <br/> 
     * @author lishang 
     * @param sourceList
     * @return
     */
    public static List<MenuVo> treeMenuList(List<MenuVo> sourceList){
        List<MenuVo> targetList=new ArrayList<>();
        if (sourceList==null) {
            return null;
        }
        List<MenuVo> pmenus=new ArrayList<>();
        for (MenuVo menu : sourceList) {
            if(menu.getParentId()== 0||menu==null){
                pmenus.add(menu);
            }
        }
        sourceList.removeAll(pmenus);
        for (int i = 0; i < pmenus.size(); i++) {
        	MenuVo pmenu=pmenus.get(i);
            List<MenuVo> cmenus=new ArrayList<>();
            for (MenuVo menu : sourceList) {
                if(pmenu.getId() == menu.getParentId()){
                    cmenus.add(menu);
                }
            }
            pmenu.setChildrens(cmenus);
            sourceList.removeAll(cmenus);
        }
        return targetList;
    }
    
    public static List<MenuVo> treeRoot(List<MenuVo> sourceList){
        return sourceList;
    }
    
    
    /**
     * 递归获取菜单
     * treeRoot:( ). <br/> 
     * @author lishang 
     * @param sourceList
     * @param rootMenu
     * @return
     */
    public static MenuVo treeMenu(List<MenuVo> sourceList,MenuVo rootMenu)
    {
        if (sourceList == null)
        {
            return null;
        }
        List<MenuVo> childList=new ArrayList<>();
        for (MenuVo menu : sourceList) {
            if(rootMenu.getId() == menu.getParentId()){
            	MenuVo menuChild = treeMenu(sourceList, menu);
                childList.add(menuChild);
            }
        }
        if(childList.size()==0){
            return rootMenu;
        }
        rootMenu.setChildrens(childList);
        return rootMenu;
    }
    
    /**
     * 递归获取权限
     * treeRoot:( ). <br/> 
     * @author lishang 
     * @param sourceList
     * @param rootMenu
     * @return
     */
    public static PermissionsVo treePermissions(List<PermissionsVo> sourceList,PermissionsVo permissions)
    {
        if (sourceList == null)
        {
            return null;
        }
        List<PermissionsVo> childList=new ArrayList<>();
        for (PermissionsVo p : sourceList) {
            if(permissions.getId() == p.getParentId()){
            	PermissionsVo menuChild = treePermissions(sourceList, p);
                childList.add(menuChild);
            }
        }
        if(childList.size()==0){
            return permissions;
        }
        permissions.setChildrens(childList);
        return permissions;
    }
    
    
    public static void main(String[] args) {
        List<MenuVo> sourceList=new ArrayList<>();
        
        MenuVo menu=new MenuVo();
        menu.setParentId(0);
        menu.setId(1);
        menu.setName("菜单一级");
        sourceList.add(menu);
        
        MenuVo menu2=new MenuVo();
        menu2.setParentId(1);
        menu2.setId(2);
        menu2.setName("菜单二级1");
        sourceList.add(menu2);
        
        MenuVo menu3=new MenuVo();
        menu3.setParentId(2);
        menu3.setId(3);
        menu3.setName("菜单三级");
        sourceList.add(menu3);
        
        MenuVo menu4=new MenuVo();
        menu4.setParentId(3);
        menu4.setId(4);
        menu4.setName("菜单四级");
        sourceList.add(menu4);
        
        MenuVo menu5=new MenuVo();
        menu5.setParentId(1);
        menu5.setId(6);
        menu5.setName("菜单二级2");
        sourceList.add(menu5);
        
        MenuVo childrens = treeMenu(sourceList, menu);
        System.out.println(JSONObject.toJSON(childrens));
    }

}
