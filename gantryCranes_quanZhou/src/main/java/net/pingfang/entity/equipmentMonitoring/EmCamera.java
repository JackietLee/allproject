package net.pingfang.entity.equipmentMonitoring;
/**
 * 设备监控
 * 相机节点
 * @author Administrator
 *
 */
public class EmCamera {
	/**
	 * 自增主键
	 */
	private int id;
	/**
	 * 岸桥ID
	 */
	private int craneId;
	/**
	 * 相机名称
	 */
	private String name;
	/**
	 * 相机IP
	 */
	private String ip;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 设备监控时间
	 */
	private String emTime;
	/**
	 * 更新时间
	 */
	private String uadateTime;
	/**
	 * 创建时间
	 */
	private String createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCraneId() {
		return craneId;
	}
	public void setCraneId(int craneId) {
		this.craneId = craneId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmTime() {
		return emTime;
	}
	public void setEmTime(String emTime) {
		this.emTime = emTime;
	}
	public String getUadateTime() {
		return uadateTime;
	}
	public void setUadateTime(String uadateTime) {
		this.uadateTime = uadateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
