package net.pingfang.entity.vessel;

/**
 * 船舶Bay（vesselBay表）
 * @author Administrator
 * @since 2019-06-01
 */
public class VesselBayVo {
	private int id;					//identity (1,1) primary key
	private String bay;				//bay信息
	private String vesselName;		//船名
	private String vesselCode;		//vessel_code--船舶代码
	private String vesselNumber;	//vessel_voyage_number--船舶艘次号
	private String bayNumber;		//bay_number--贝编号
	private int rowNumber;			//std_row--标准排数量
	private int tierNumber;			//std_tier--标准层数量
	private String baySize;			//size_type --bay大小
	private String stdBay;			//std_bay  带D/H的 bay 信息
	private String row;				//实际排
	private String tier;			//实际层
	private String createTime;  	//创建时间
	private String craneNum; 		//岸桥编号
	private String tallyClerk;		//创建人
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getBayNumber() {
		return bayNumber;
	}
	public void setBayNumber(String bayNumber) {
		this.bayNumber = bayNumber;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getTierNumber() {
		return tierNumber;
	}
	public void setTierNumber(int tierNumber) {
		this.tierNumber = tierNumber;
	}
	public String getBaySize() {
		return baySize;
	}
	public void setBaySize(String baySize) {
		this.baySize = baySize;
	}
	
	public String getStdBay() {
		return stdBay;
	}
	public void setStdBay(String stdBay) {
		this.stdBay = stdBay;
	}
	
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getTallyClerk() {
		return tallyClerk;
	}
	public void setTallyClerk(String tallyClerk) {
		this.tallyClerk = tallyClerk;
	}

}
