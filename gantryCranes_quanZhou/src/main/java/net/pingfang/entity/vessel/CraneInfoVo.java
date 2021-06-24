package net.pingfang.entity.vessel;
/**
 * 岸桥信息(crane_infor表)
 * @author Administrator
 *
 */
public class CraneInfoVo {
	private int id;					//自增主键',   
	private String craneNameCn;		//岸桥中文名,
	private String craneNameEn;		//岸桥英文名,
	private String craneNum;		//岸桥编号,
	private String createtime; 		//创建时间
	
	private String controlIp; 		//中控机IP
	private int controlPort; 		//中控机端口号
	private String type;			//"0,1",  //操作类型,例如重启程序、重启电脑等，多项操作时,使用英文逗号分隔；
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCraneNameCn() {
		return craneNameCn;
	}
	public void setCraneNameCn(String craneNameCn) {
		this.craneNameCn = craneNameCn;
	}
	public String getCraneNameEn() {
		return craneNameEn;
	}
	public void setCraneNameEn(String craneNameEn) {
		this.craneNameEn = craneNameEn;
	}
	public String getCraneNum() {
		return craneNum;
	}
	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getControlIp() {
		return controlIp;
	}
	public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	}
	public int getControlPort() {
		return controlPort;
	}
	public void setControlPort(int controlPort) {
		this.controlPort = controlPort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
}
