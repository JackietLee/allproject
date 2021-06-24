package net.pingfang.entity.work;
import java.util.List;
import net.pingfang.json.work.FileInfoJson;
/**
 * 作业记录表(work_record)
 * @author Administrator
 * @since 2019-05-22
 *
 */
public class WorkRecordVo {
	
	private int id;  //identity (1,1) primary key 
	private String seqNo;//unique唯一任务编号
	private String areaNum;        //"area_num":"DPN",//站点名称 
	private String craneNum; //岸桥编号
	private int laneNum;	//车道号	int	1、2、3、4、5、6、7
	private Integer workType; //作业类型	int	0：装船作业；1:卸船作业；2：其他作业
	private String plate; //车牌号	string	10长度
	private String topPlate; //车顶号	string	5长度
	private String contaid; //箱号	string	11个长度字符串
	private String updateTopPlate; //更新后的车顶号	string	5长度
	private String updateContaid; //更新后的箱号	string	11个长度字符串
	private int updateContaidCount; //箱号更新次数
	private int updateTopPlateCount; //车顶号更新次数
	
	private int containerType; //箱类型	int	0：长箱,1：短箱,2：双箱,10：未知,
	private int orderid; //箱序号	int	1：箱1；2：箱2	
	private String iso; //箱号ISO	string	4个长度字符串
	private String ischeck; //箱号校验结果	true/false	
	private String damaged; //箱号是否残损	true/false	
	private int doorDir; //箱门朝向	0：左；1：右
	private int doorLock; //是否有铅封，0:lock,1:unlock,2:unknow
	private String bayInfo; //更新后贝位	
	private String bay; //初始化的贝位
	private int updateBayCount; //bay更新次数
	
	private String state; //状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	private int contaWeight; //吊具称重	
	private String pointX; //坐标x 		
	private String pointY; //坐标y ,
	private String passTime; //作业时间  	
	private String createTime; //创建时间
	
	private Integer currentPage;
	private Integer pageSize;
	private String imgUrl;	//图片URL
	private String compressUrl;	//缩略图URL
	private String vesselCode;		//"vessel_code"//船舶代码
	private String vesselNumber;	//vessel_voyage_number //外理系统船舶艘次号
	private String voyageNo;		//华东系统船舶艘次号
	private int ldMount;				//集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
	private int dsMount;				//集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	
	private String vesselNameCn;		//vessel_name_cn	中文船名
	private String vesselNameEn;		//vessel_name_cn	中文船名
	private String tallyClerk;			//tally_clerk  理货员
	private String trust;	//残损程度
	private List<DamageInforRecordVo> dirList;	//残损记录
	
	private String portLoading;			//port_of_loading	--装货港	
	private String portDischarge;		//port_of_discharge		--卸货港	
	private String portDestination;		//port_of_destination	--目的港	
	private String stuffingStatus;		// 装载状态	重（F、full）、空（E、empty）
	private String vesselPosition;		//预配bay位
	private String alongside;			//船舷方向
	private String inVoyage;			//in_voyage 进口航次
	private String outVoyage;			//out_voyage 出口航次
	private FileInfoJson fileInfoJson;
	private int bayState;
	private String operationType;		//操作类型
	private String msgTypeTwo;			//是否有合并02号作业结束报文
	private String tradeType;
	private List<String> mp4Url;	//图片对应的MP4
	
	private int dangerSigns;  			//危险标志（0为非危险品，1为危险品）
	private String dangerClass;				//危险类型
	private String containerClass;			//箱类型（危品箱，普通箱，冷藏箱）
	
	private int alarmState;	//是否报警（0为不报警，1为报警）
	private String stevedoreId;	//装卸工ID
	private int shipPosition;	// 1甲板2船舱
	private String isTransfer;	// 是否中转
	private String billNumber;
	private String sizeType;
	private String cargoType;
	private String sealNumber;

	private int n4Status;	//1为成功，2为失败，3为N4接口成功社区接口失败
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getSizeType() {
		return sizeType;
	}

	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getSealNumber() {
		return sealNumber;
	}

	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}
	public String getCraneNum() {
		return craneNum;
	}
	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
	public int getLaneNum() {
		return laneNum;
	}
	public void setLaneNum(int laneNum) {
		this.laneNum = laneNum;
	}
	public Integer getWorkType() {
		return workType;
	}
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getTopPlate() {
		return topPlate;
	}
	public void setTopPlate(String topPlate) {
		this.topPlate = topPlate;
	}
	
	public int getUpdateContaidCount() {
		return updateContaidCount;
	}
	public void setUpdateContaidCount(int updateContaidCount) {
		this.updateContaidCount = updateContaidCount;
	}
	public int getUpdateTopPlateCount() {
		return updateTopPlateCount;
	}
	public void setUpdateTopPlateCount(int updateTopPlateCount) {
		this.updateTopPlateCount = updateTopPlateCount;
	}
	public int getContainerType() {
		return containerType;
	}
	public void setContainerType(int containerType) {
		this.containerType = containerType;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getContaid() {
		return contaid;
	}
	public void setContaid(String contaid) {
		this.contaid = contaid;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public String getDamaged() {
		return damaged;
	}
	public void setDamaged(String damaged) {
		this.damaged = damaged;
	}
	public int getDoorDir() {
		return doorDir;
	}
	public void setDoorDir(int doorDir) {
		this.doorDir = doorDir;
	}
	public int getDoorLock() {
		return doorLock;
	}
	public void setDoorLock(int doorLock) {
		this.doorLock = doorLock;
	}
	public String getBayInfo() {
		return bayInfo;
	}
	public void setBayInfo(String bayInfo) {
		this.bayInfo = bayInfo;
	}
	
	public String getBay() {
		return bay;
	}
	public void setBay(String bay) {
		this.bay = bay;
	}
	public int getUpdateBayCount() {
		return updateBayCount;
	}
	public void setUpdateBayCount(int updateBayCount) {
		this.updateBayCount = updateBayCount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getContaWeight() {
		return contaWeight;
	}
	public void setContaWeight(int contaWeight) {
		this.contaWeight = contaWeight;
	}
	public String getPointX() {
		return pointX;
	}
	public void setPointX(String pointX) {
		this.pointX = pointX;
	}
	public String getPointY() {
		return pointY;
	}
	public void setPointY(String pointY) {
		this.pointY = pointY;
	}
	public String getPassTime() {
		return passTime;
	}
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getCompressUrl() {
		return compressUrl;
	}
	public void setCompressUrl(String compressUrl) {
		this.compressUrl = compressUrl;
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
	public int getLdMount() {
		return ldMount;
	}
	public void setLdMount(int ldMount) {
		this.ldMount = ldMount;
	}
	public int getDsMount() {
		return dsMount;
	}
	public void setDsMount(int dsMount) {
		this.dsMount = dsMount;
	}
	public String getUpdateTopPlate() {
		return updateTopPlate;
	}
	public void setUpdateTopPlate(String updateTopPlate) {
		this.updateTopPlate = updateTopPlate;
	}
	public String getUpdateContaid() {
		return updateContaid;
	}
	public void setUpdateContaid(String updateContaid) {
		this.updateContaid = updateContaid;
	}
	public String getVesselNameCn() {
		return vesselNameCn;
	}
	public void setVesselNameCn(String vesselNameCn) {
		this.vesselNameCn = vesselNameCn;
	}
	public String getTallyClerk() {
		return tallyClerk;
	}
	public void setTallyClerk(String tallyClerk) {
		this.tallyClerk = tallyClerk;
	}
	public String getTrust() {
		return trust;
	}
	public void setTrust(String trust) {
		this.trust = trust;
	}
	public List<DamageInforRecordVo> getDirList() {
		return dirList;
	}
	public void setDirList(List<DamageInforRecordVo> dirList) {
		this.dirList = dirList;
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
	public String getStuffingStatus() {
		return stuffingStatus;
	}
	public void setStuffingStatus(String stuffingStatus) {
		this.stuffingStatus = stuffingStatus;
	}
	public String getVesselPosition() {
		return vesselPosition;
	}
	public void setVesselPosition(String vesselPosition) {
		this.vesselPosition = vesselPosition;
	}
	public String getAlongside() {
		return alongside;
	}
	public void setAlongside(String alongside) {
		this.alongside = alongside;
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
	public FileInfoJson getFileInfoJson() {
		return fileInfoJson;
	}
	public void setFileInfoJson(FileInfoJson fileInfoJson) {
		this.fileInfoJson = fileInfoJson;
	}
	public int getBayState() {
		return bayState;
	}
	public void setBayState(int bayState) {
		this.bayState = bayState;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getMsgTypeTwo() {
		return msgTypeTwo;
	}
	public void setMsgTypeTwo(String msgTypeTwo) {
		this.msgTypeTwo = msgTypeTwo;
	}
	public List<String> getMp4Url() {
		return mp4Url;
	}
	public void setMp4Url(List<String> mp4Url) {
		this.mp4Url = mp4Url;
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
	public int getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(int alarmState) {
		this.alarmState = alarmState;
	}
	public String getVoyageNo() {
		return voyageNo;
	}
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}
	
	public String getVesselNameEn() {
		return vesselNameEn;
	}
	public void setVesselNameEn(String vesselNameEn) {
		this.vesselNameEn = vesselNameEn;
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

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public int getN4Status() {
		return n4Status;
	}

	public void setN4Status(int n4Status) {
		this.n4Status = n4Status;
	}

	@Override
	public String toString() {
		return "WorkRecordVo [id=" + id + ", seqNo=" + seqNo + ", areaNum=" + areaNum + ", craneNum=" + craneNum
				+ ", laneNum=" + laneNum + ", workType=" + workType + ", plate=" + plate + ", topPlate=" + topPlate
				+ ", contaid=" + contaid + ", updateTopPlate=" + updateTopPlate + ", updateContaid=" + updateContaid
				+ ", updateContaidCount=" + updateContaidCount + ", updateTopPlateCount=" + updateTopPlateCount
				+ ", containerType=" + containerType + ", orderid=" + orderid + ", iso=" + iso + ", ischeck=" + ischeck
				+ ", damaged=" + damaged + ", doorDir=" + doorDir + ", doorLock=" + doorLock + ", bayInfo=" + bayInfo
				+ ", bay=" + bay + ", updateBayCount=" + updateBayCount + ", state=" + state + ", contaWeight="
				+ contaWeight + ", pointX=" + pointX + ", pointY=" + pointY + ", passTime=" + passTime + ", createTime="
				+ createTime + ", currentPage=" + currentPage + ", pageSize=" + pageSize + ", imgUrl=" + imgUrl
				+ ", compressUrl=" + compressUrl + ", vesselCode=" + vesselCode + ", vesselNumber=" + vesselNumber
				+ ", ldMount=" + ldMount + ", dsMount=" + dsMount + ", vesselNameCn=" + vesselNameCn + ", tallyClerk="
				+ tallyClerk + ", trust=" + trust + ", dirList=" + dirList + ", portLoading=" + portLoading
				+ ", portDischarge=" + portDischarge + ", portDestination=" + portDestination + ", stuffingStatus="
				+ stuffingStatus + ", vesselPosition=" + vesselPosition + ", alongside=" + alongside + ", inVoyage="
				+ inVoyage + ", outVoyage=" + outVoyage + ", fileInfoJson=" + fileInfoJson + ", bayState=" + bayState
				+ ", operationType=" + operationType + ", msgTypeTwo=" + msgTypeTwo + ", mp4Url=" + mp4Url
				+ ", dangerSigns=" + dangerSigns + ", dangerClass=" + dangerClass + ", containerClass=" + containerClass
				+ ", alarmState=" + alarmState + "]";
	}


}
