package net.pingfang.entity.vessel;

import java.util.List;

/**
 * 船舶集装箱（tos_vessel_container表）
 * @author Administrator
 * @since 2019-06-01
 */
public class VesselContainerVo {
	private int id;						//identity (1,1) primary key 
	private String billNumber;			//bill_number	--提单号
	private String cargoType;				//cargo_type --货物类型	
	private String containerNumber;		//container_number	--集装箱号	
	private int containerStatus;		//container_status	--箱状态 0：未理货 1：已理货 4：理货异常
	private String containerType;			//container_type	--箱类型	普通箱、危险品箱、冷藏箱、开顶箱……
	private String craneDriverName;		//crane_driver_name	--目的港	
	private String craneName;			//crane_name	--岸吊名称	
	private String craneNum; 			//岸桥编号
	private String inVoyage;			//in_voyage	--进口航次	卸船有值，装船为空
	private String outVoyage;			//out_voyage	--出口航次	装船有值，卸船为空
	private String jobType;			//job_type		--作业类型(说明：'DS'为'卸船', 'DD'为'直提', 'LD'为'装船', 'DL'为'直装', 'LN'为'捣卸', 'RS'为'捣装', 'SH'为'搬移', 'DT'为'中转不落地')
	private String operatorCode;		//operator_code	--箱公司代码	
	private String portLoading;			//port_of_loading	--装货港	
	private String portDischarge;		//port_of_discharge		--卸货港	
	private String portDestination;		//port_of_destination	--目的港	
	private String sealNumber;			//seal_number	--铅封号	
	private int sequenceNumber;		//sequence_number	--装卸顺序号	
	private String truckName;			//truck_name	--拖车名称	
	private String sizeType;			//size_type	--尺寸
	private String stdBay;			//std_bay	--标准贝号(带D/H的 bay 信息)
	private String stdRow;			//std_row	--标准行号
	private String stdTier;			//std_tier	--标准层号
	private String vesselNumber;		//vessel_voyage_number --外理船舶艘次号		
	private String voyageNo;		//华东系统船舶艘次号	
	private String stuffingStatus;		//stuffing_status	--装载状态	重（F、full）、空（E、empty）
	private String transferType;		//transfer_type	--付运方向	进口（import）、出口（export）
	private String tradeType;			//trade_type	--贸易类型	内贸（domestic）、外贸（foreign）
	private int weight;				//weight --重量	
	private String vesselCode;			//vessel_code	--船舶代码	
	private String yardPosition;		//yard_position	--堆场位置	
	//private String vesselPosition;		//vessel_position	--船上位置	
	//private String bookingNumber;		//booking_number	--订舱号	
	
	private String orderTime;			//order_time	--指令发送时间	
	private String finishTime;			//finish_time	--指令完成时间	
	private String updateTime;			//update_time	--更新时间	
	private String createTime;			//创建时间
	private String vesselPosition;		//预配bay位
	private Integer currentPage;
	private Integer pageSize;
	private int bayState;
	private String iso;
	
	private int dangerSigns;  			//危险标志（0为非危险品，1为危险品）
	private String dangerClass;				//危险类型
	private String containerClass;			//箱类型（危品箱，普通箱，冷藏箱）
	
	private int  portType;	//港口分类
	private boolean latestBay;	//最新BAY
	private List<String> bayList;
	private String isTransfer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public int getContainerStatus() {
		return containerStatus;
	}
	public void setContainerStatus(int containerStatus) {
		this.containerStatus = containerStatus;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getCraneDriverName() {
		return craneDriverName;
	}
	public void setCraneDriverName(String craneDriverName) {
		this.craneDriverName = craneDriverName;
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
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
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
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getTruckName() {
		return truckName;
	}
	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public String getStdBay() {
		return stdBay;
	}
	public void setStdBay(String stdBay) {
		this.stdBay = stdBay;
	}
	public String getStdRow() {
		return stdRow;
	}
	public void setStdRow(String stdRow) {
		this.stdRow = stdRow;
	}
	public String getStdTier() {
		return stdTier;
	}
	public void setStdTier(String stdTier) {
		this.stdTier = stdTier;
	}
	public String getVesselNumber() {
		return vesselNumber;
	}
	public void setVesselNumber(String vesselNumber) {
		this.vesselNumber = vesselNumber;
	}
	public String getStuffingStatus() {
		return stuffingStatus;
	}
	public void setStuffingStatus(String stuffingStatus) {
		this.stuffingStatus = stuffingStatus;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public String getYardPosition() {
		return yardPosition;
	}
	public void setYardPosition(String yardPosition) {
		this.yardPosition = yardPosition;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getVesselPosition() {
		return vesselPosition;
	}
	public void setVesselPosition(String vesselPosition) {
		this.vesselPosition = vesselPosition;
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
	public int getBayState() {
		return bayState;
	}
	public void setBayState(int bayState) {
		this.bayState = bayState;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public int getDangerSigns() {
		return dangerSigns;
	}
	public void setDangerSigns(int dangerSigns) {
		this.dangerSigns = dangerSigns;
	}
	public String getDangerClass() {
		return dangerClass;
	}
	public void setDangerClass(String dangerClass) {
		this.dangerClass = dangerClass;
	}
	public String getContainerClass() {
		return containerClass;
	}
	public void setContainerClass(String containerClass) {
		this.containerClass = containerClass;
	}
	public int getPortType() {
		return portType;
	}
	public void setPortType(int portType) {
		this.portType = portType;
	}
	public boolean getLatestBay() {
		return latestBay;
	}
	public void setLatestBay(boolean latestBay) {
		this.latestBay = latestBay;
	}
	public List<String> getBayList() {
		return bayList;
	}
	public void setBayList(List<String> bayList) {
		this.bayList = bayList;
	}
	public String getVoyageNo() {
		return voyageNo;
	}
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}
}
