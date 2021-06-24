package net.pingfang.entity.systemManage;

import java.io.Serializable;
import java.util.List;

/**
 * 权限表(t_permissions)
 * @author Administrator
 *
 */
public class PermissionsVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	//父节点id
    private int parentId;
    
    //权限名称
    private String name;
    
    //授权链接
    private String url;
    
    //权限类型：menu为菜单，operation为操作
    private String type;
    
    //菜单ID
    private int menuId;
    
    //菜单名称
    private String menuName;
    
    //权限描述
    private String remarks;
    
    private List<PermissionsVo> childrens;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<PermissionsVo> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<PermissionsVo> childrens) {
		this.childrens = childrens;
	}
    
}
