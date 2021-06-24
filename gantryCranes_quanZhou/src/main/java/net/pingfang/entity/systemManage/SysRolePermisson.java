package net.pingfang.entity.systemManage;

import java.io.Serializable;

public class SysRolePermisson implements Serializable{
	private static final long serialVersionUID = 1L;
	//角色
    private String roleId;
    private String roleName;

    //权限
    private String permissionId;
    private String url;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
    
    
}
