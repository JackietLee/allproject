package net.pingfang.entity.vessel;
/**
 * 拖车信息（truckInfo）
 * @author Administrator
 * @since 2019-06-01
 */
public class TruckInfoVo {
	private int id;					//identity (1,1) primary key
	private String truckNumber;	//truck_number --拖车号
	private String truckName;		//truck_name --拖车名称
	private String createTime;    //创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTruckNumber() {
		return truckNumber;
	}
	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}
	public String getTruckName() {
		return truckName;
	}
	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
