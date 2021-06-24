package net.pingfang.entity.systemManage;

import java.io.Serializable;

/**
 * 角色表（sys_role）
 * @author Administrator
 *
 */
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String description;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
