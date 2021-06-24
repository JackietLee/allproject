package net.pingfang.entity.camera;
/**
 * 摄像机信息VO
 * @author Administrator
 *
 */
public class CameraInforVo {
	 private int id;				//'自增主键',
	 private String name; 			//'摄像机名称',
	 private String code;			//'摄像机代码',
	 private int craneId;			//'岸桥ID', 
	 private String craneNum;		//'岸桥编号',
	 private String position;		//'摄像头安装位置',
	 private String ipAddress;		//摄像机IP地址
	 private String port;			//'端口号',  
	 private String createtime;		//'创建时间',
	 
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCraneId() {
		return craneId;
	}
	public void setCraneId(int craneId) {
		this.craneId = craneId;
	}
	public String getCraneNum() {
		return craneNum;
	}
	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
