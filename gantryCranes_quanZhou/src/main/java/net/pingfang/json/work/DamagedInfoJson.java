package net.pingfang.json.work;
/**
 * 残损信息
 * @author Administrator
 *
 */
public class DamagedInfoJson {
	private int conta_index; //"conta_index": 0,  //箱号坐标，0前箱，1后箱
	private int damaged_type; //"damaged_type": 0,   //残损类型
	private String trust; //"trust": 0.0,   //可信度
	private String note; //"note": "A"    //备注
	private String position;	//残损位置
	
	public int getConta_index() {
		return conta_index;
	}
	public void setConta_index(int conta_index) {
		this.conta_index = conta_index;
	}
	public int getDamaged_type() {
		return damaged_type;
	}
	public void setDamaged_type(int damaged_type) {
		this.damaged_type = damaged_type;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}	

}
