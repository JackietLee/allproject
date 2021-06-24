package net.pingfang.entity.importFile;

import java.util.List;

public class VesselListVo {
	private int id;

	private String vesselName;	// 船名
	private String vesselNumber;
	private String voyage;	// 航次
	private String inOut;	//1为进口航次，2为出口航次，默认为1
	private String billNumber;	// 提单号
	private String containerNumber;	//箱号
	private int sizeType;	// 尺寸
	private String cargoType;	// 类型
	private String stuffingStatus;	// 空/重
	private String sealNumber;	// 铅封号
	private String portLoading;	// 装货港
	private String portDischarge;	// 卸货港
	private String portDestination;	// 目的港
	private String goodsName;	// 货名
	private String terms;	//  条款
	private String number;	// 件数
	private String volume;	// 体积
	private String weight;	// 重量
	private String operator;	// 箱公司
	private String tradeType;	// 内外贸
	private String damaged;	// 残损
	private String temperature;	// 冷藏温度
	private String dangerClass;	// 危险等级
	private String payCostUnit;	// 缴费单位
	private String tallyClerk;
	private String fileName;
	private String isTransfer;
	private Integer currentPage;
	private Integer pageSize;
	
	private List<String> contaidList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getVoyage() {
		return voyage;
	}
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}
	
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public int getSizeType() {
		return sizeType;
	}
	public void setSizeType(int sizeType) {
		this.sizeType = sizeType;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	public String getStuffingStatus() {
		return stuffingStatus;
	}
	public void setStuffingStatus(String stuffingStatus) {
		this.stuffingStatus = stuffingStatus;
	}
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	public String getPortLoading() {
		return portLoading;
	}
	public void setPortLoading(String portLoading) {
		this.portLoading = portLoading;
	}
	public String getPortDischarge() {
		return portDischarge;
	}
	public void setPortDischarge(String portDischarge) {
		this.portDischarge = portDischarge;
	}
	public String getPortDestination() {
		return portDestination;
	}
	public void setPortDestination(String portDestination) {
		this.portDestination = portDestination;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getDamaged() {
		return damaged;
	}
	public void setDamaged(String damaged) {
		this.damaged = damaged;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getDangerClass() {
		return dangerClass;
	}
	public void setDangerClass(String dangerClass) {
		this.dangerClass = dangerClass;
	}
	public String getPayCostUnit() {
		return payCostUnit;
	}
	public void setPayCostUnit(String payCostUnit) {
		this.payCostUnit = payCostUnit;
	}
	public String getTallyClerk() {
		return tallyClerk;
	}
	public void setTallyClerk(String tallyClerk) {
		this.tallyClerk = tallyClerk;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<String> getContaidList() {
		return contaidList;
	}
	public void setContaidList(List<String> contaidList) {
		this.contaidList = contaidList;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}
}
