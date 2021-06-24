package net.pingfang.entity.equipmentMonitoring;

import java.util.List;

/**
 * 设备监控
 * 岸桥节点
 * @author Administrator
 *
 */
public class EmCrane {
	private int id;
	/**
	 * 消息类型
	 */
	private String message_type;
	/**
	 * 站点名称
	 */
	private String areaName;
	/**
	 * 岸桥名称
	 */
	private String craneName;
	/**
	 * 设备监控时间
	 */
	private String time;
	/**
	 * 状态(1为正常，2为异常)
	 * enum('1','2') DEFAULT '1' COMMENT '岸桥状态是否正常(1为正常,2为异常，默认为1)'
	 */
	private String status;
	/**
	 * 是否活动状态（在线或离线）
	 */
	private String activity;
	/**
	 * PLC设备
	 */
	private Plc plc;
	/**
	 * 相机
	 */
	private List<EmCamera> cameraList;
	/**
	 * PLC设备状态
	 * enum('1','2') DEFAULT '1' COMMENT 'PLC状态是否正常(1为正常,2为异常，默认为1)'
	 */
	private String plcStatus;
	/**
	 * 更新时间
	 */
	private String uadateTime;
	/**
	 * 创建时间
	 */
	private String createTime;
	private String serviceID;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCraneName() {
		return craneName;
	}
	public void setCraneName(String craneName) {
		this.craneName = craneName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Plc getPlc() {
		return plc;
	}
	public void setPlc(Plc plc) {
		this.plc = plc;
	}
	public List<EmCamera> getCameraList() {
		return cameraList;
	}
	public void setCameraList(List<EmCamera> cameraList) {
		this.cameraList = cameraList;
	}
	public String getPlcStatus() {
		return plcStatus;
	}
	public void setPlcStatus(String plcStatus) {
		this.plcStatus = plcStatus;
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
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	
}
