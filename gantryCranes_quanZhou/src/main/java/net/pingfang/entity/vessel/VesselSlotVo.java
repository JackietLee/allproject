package net.pingfang.entity.vessel;
/**
 * 船舶舱位表（vesselSlot表）
 * @author Administrator
 * @since 2019-06-01
 *
 */
public class VesselSlotVo {
	private int id; 				//identity (1,1) primary key
	private String vesselCode;		//vessel_code--船舶代码
	private String bayNumberStd;	//bay_number_std--国际Bay号
	private String bayNumber;		//bay_number--Bay号
	private int rowIndex;			//row_index--排位置
	private int tierIndex;			//tier_index--层位置
	private String createTime;     //createtime--创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public String getBayNumberStd() {
		return bayNumberStd;
	}
	public void setBayNumberStd(String bayNumberStd) {
		this.bayNumberStd = bayNumberStd;
	}
	public String getBayNumber() {
		return bayNumber;
	}
	public void setBayNumber(String bayNumber) {
		this.bayNumber = bayNumber;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getTierIndex() {
		return tierIndex;
	}
	public void setTierIndex(int tierIndex) {
		this.tierIndex = tierIndex;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
