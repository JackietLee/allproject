package net.pingfang.entity.vessel;
/**
 * 残损类型信息VO
 * "damaged_type"表
 * @author Administrator
 * 2019-07-29
 *
 */
public class DamagedTypeVo {
	private int id;		//自增主键
	private String damagedCode;	 //damaged_code 残损代码
	private String damagedType; //damaged_type  残损类型
	private String eDamagedType; //e_damaged_type
	private String damagedLevel; //damaged_level 残损等级
	private String isAlarm;      //is_alarm 是否报警
	private String desc;      //desc 描述
	private String positionId;	//残损位置Id
	private String positionCode;	//残损位置代码
	private String position;	//残损位置
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDamagedCode() {
		return damagedCode;
	}
	public void setDamagedCode(String damagedCode) {
		this.damagedCode = damagedCode;
	}
	public String getDamagedType() {
		return damagedType;
	}
	public void setDamagedType(String damagedType) {
		this.damagedType = damagedType;
	}
	
	public String geteDamagedType() {
		return eDamagedType;
	}
	public void seteDamagedType(String eDamagedType) {
		this.eDamagedType = eDamagedType;
	}
	public String getDamagedLevel() {
		return damagedLevel;
	}
	public void setDamagedLevel(String damagedLevel) {
		this.damagedLevel = damagedLevel;
	}
	public String getIsAlarm() {
		return isAlarm;
	}
	public void setIsAlarm(String isAlarm) {
		this.isAlarm = isAlarm;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
