package net.pingfang.entity.work;

import java.util.List;

/**
 * 岸桥配制信息（Crane_Preparation表）
 * @author Administrator
 * @since 2019-06-17
 */
public class CranePreparationVo {

	private int id;					//自增主键
	private String vesselNameCn;	//中文船名
	private String vesselNameEn;	//英文船名
	private String craneName;		//岸桥中文名
	private String craneNum;		//岸桥编号
	private String bay;				//bay 信息
	private String vesselCode;		//vessel_code   船舶代码
	private String vesselNumber;	//vessel_voyage_number   船舶艘次号
	private String alongside;		//船舷方向
	private String createTime;		//创建时间
	private String inVoyage;		//in_voyage 进口航次
	private String outVoyage;		//out_voyage 出口航次
	private String isAuto;			//自动理货标识('Y'为自动理货，'N'为人工手动理货)
	private int workMode;			//作业模式(1为混装模式，2为定糟定位模式)，默认为1
	private List<String> bayList;
	/**
	 * 作业类型(0为装船作业；1为卸船作业；2为其他作业;8为倒箱;9为仓盖板)
	 */
	private Integer workType;
	private int bayWidth;		//显示在Bay位图上Bay宽度
	private String type;		//桥类型('1'为岸桥，'2'为门机)
	private String cameraId;	//相机ID，多个ID用逗号分隔
	private String cmdType;		// 命令类型：start,stop"
	private String activity;	//门机是否已经开始作业('1'为等待作业，'2'为作业中),默认为等待作业
	
	private String stevedoreId;	//装卸工ID
	private int shipPosition;	// 1甲板2船舱
	private String checkWay;	//校验方式(1只校验平台导入的舱单，2校验客户系统舱单，3两个校验都需要)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getVesselNameCn() {
		return vesselNameCn;
	}
	public void setVesselNameCn(String vesselNameCn) {
		this.vesselNameCn = vesselNameCn;
	}
	public String getVesselNameEn() {
		return vesselNameEn;
	}
	public void setVesselNameEn(String vesselNameEn) {
		this.vesselNameEn = vesselNameEn;
	}
	
	public String getCraneName() {
		return craneName;
	}
	public void setCraneName(String craneName) {
		this.craneName = craneName;
	}
	public String getCraneNum() {
		return craneNum;
	}
	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
	public String getBay() {
		return bay;
	}
	public void setBay(String bay) {
		this.bay = bay;
	}
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public String getVesselNumber() {
		return vesselNumber;
	}
	public void setVesselNumber(String vesselNumber) {
		this.vesselNumber = vesselNumber;
	}
	
	public String getAlongside() {
		return alongside;
	}
	public void setAlongside(String alongside) {
		this.alongside = alongside;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getInVoyage() {
		return inVoyage;
	}
	public void setInVoyage(String inVoyage) {
		this.inVoyage = inVoyage;
	}
	public String getOutVoyage() {
		return outVoyage;
	}
	public void setOutVoyage(String outVoyage) {
		this.outVoyage = outVoyage;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
	
	public int getWorkMode() {
		return workMode;
	}
	public void setWorkMode(int workMode) {
		this.workMode = workMode;
	}
	public List<String> getBayList() {
		return bayList;
	}
	public void setBayList(List<String> bayList) {
		this.bayList = bayList;
	}
	public Integer getWorkType() {
		return workType;
	}
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	public int getBayWidth() {
		return bayWidth;
	}
	public void setBayWidth(int bayWidth) {
		this.bayWidth = bayWidth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public String getCmdType() {
		return cmdType;
	}
	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getStevedoreId() {
		return stevedoreId;
	}
	public void setStevedoreId(String stevedoreId) {
		this.stevedoreId = stevedoreId;
	}
	public int getShipPosition() {
		return shipPosition;
	}
	public void setShipPosition(int shipPosition) {
		this.shipPosition = shipPosition;
	}
	public String getCheckWay() {
		return checkWay;
	}
	public void setCheckWay(String checkWay) {
		this.checkWay = checkWay;
	}
	
	
}
