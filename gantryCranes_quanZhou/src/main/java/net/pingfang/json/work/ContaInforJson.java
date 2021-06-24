package net.pingfang.json.work;

/**
 * 货柜箱信息
 * @author Administrator
 *
 */
public class ContaInforJson {
	private String id; //"id": "TBJU1234567",  //前箱号
	private String updateId; //"updateId":"TBJU1234567", //理货员更新后的前箱号
	private String iso; //"iso": "22G1",      //前ISO
	private boolean check; //"check": false,       //是否通过校验
	private String trust; //"trust": 70.0,        //可信度
	private String note; //"note": "前"           //备注
	private String damaged; //箱号是否残损	true/false
	private int state; //状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	private String msgCode;	//匹配舱单错误代码（'001'车顶号错误，'002'为箱号错误）
	
	private String vesselPosition;			//预配BAY
	private String portLoading;				//装货港
	private String portDischarge;			//卸货港
	private String portDestination;			//目的港
	private String stuffingStatus;			//空重状态 重（F，full）,重（E，empty）
	private int dangerSigns;  			//危险标志（0为非危险品，1为危险品）
	private String dangerClass;				//危险类型
	private String containerClass;			//箱类型（危品箱，普通箱，冷藏箱）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public String getTrust() {
		return trust;
	}
	public void setTrust(String trust) {
		this.trust = trust;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDamaged() {
		return damaged;
	}
	public void setDamaged(String damaged) {
		this.damaged = damaged;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getVesselPosition() {
		return vesselPosition;
	}
	public void setVesselPosition(String vesselPosition) {
		this.vesselPosition = vesselPosition;
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
	
}
