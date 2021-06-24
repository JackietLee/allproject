package net.pingfang.entity.systemManage;

import java.util.List;

/**
 * 系统菜单
 * @author Administrator
 *
 */
public class MenuVo {
	 
	/**
	 * 自增主键
	 */
	private int id;
	/**
	 * 父节点ID
	 */
	private int parentId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 文件名称
	 */
	private String fname;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 链接
	 */
	private String url;
	/**
	 * 排序
	 */
	private String sort;
	/**
	 * 排序
	 */
	private String display;	
	/**
	 * 描述
	 */
	private String remarks;
	
	private List<MenuVo> childrens;

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

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<MenuVo> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<MenuVo> childrens) {
		this.childrens = childrens;
	}
	 
}
