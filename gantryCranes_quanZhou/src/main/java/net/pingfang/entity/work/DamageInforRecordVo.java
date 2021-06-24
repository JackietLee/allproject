package net.pingfang.entity.work;

/**
 * 残损信息记录表(damageInforRecord)
 * @author Administrator
 * @since 2019-05-22
 *
 */
public class DamageInforRecordVo {
	
	private int id; //identity (1,1) primary key,   
	private int workId; //作业记录唯一记录ID	uuid	外键作业记录表
	private String seqNo;//unique,   --唯一任务编号
	private String containerNumber;	//集装箱号
	private String craneNum;  //岸桥编号
	private String vesselName;	//中文船名
	private String vesselNumber;	//船舶艘次号
	private String vesselCode;		//"vessel_code"//船舶代码
	
	private int damagedTypeId;  //残损类型ID
	private String damagedType;  //残损类型
	private String damagedCode;  //残损类型代码

	
	private int positionId;	//残损位置Id
	private String position;	//残损位置
	private String positionCode;	//残损位置代码

	private String imgUrl;
	private String desc;  //残损描述
	private String synchronization;	//残损记录是否已经同步到华东系统，Y为是，N为否
	private String createName;		//创建人
	private String trust;	//残损程度
	private String createTime; //创建时间
	
	private int doorLock;	//是否有铅封(0:LOCK,1:UNLOCK,2:unknow)
	private String iso;
	private String stuffingStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getCraneNum() {
		return craneNum;
	}
	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
	public String getVesselName() {
		return vesselName;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	public String getVesselNumber() {
		return vesselNumber;
	}
	public void setVesselNumber(String vesselNumber) {
		this.vesselNumber = vesselNumber;
	}
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public int getDamagedTypeId() {
		return damagedTypeId;
	}
	public void setDamagedTypeId(int damagedTypeId) {
		this.damagedTypeId = damagedTypeId;
	}
	public String getDamagedType() {
		return damagedType;
	}
	public void setDamagedType(String damagedType) {
		this.damagedType = damagedType;
	}
	public String getDamagedCode() {
		return damagedCode;
	}
	public void setDamagedCode(String damagedCode) {
		this.damagedCode = damagedCode;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSynchronization() {
		return synchronization;
	}
	public void setSynchronization(String synchronization) {
		this.synchronization = synchronization;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getTrust() {
		return trust;
	}
	public void setTrust(String trust) {
		this.trust = trust;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getDoorLock() {
		return doorLock;
	}
	public void setDoorLock(int doorLock) {
		this.doorLock = doorLock;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getStuffingStatus() {
		return stuffingStatus;
	}
	public void setStuffingStatus(String stuffingStatus) {
		this.stuffingStatus = stuffingStatus;
	}
	
}
