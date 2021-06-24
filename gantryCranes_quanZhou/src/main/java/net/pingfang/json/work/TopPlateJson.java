package net.pingfang.json.work;
/**
 * 货柜箱顶板信息
 * @author Administrator
 *
 */
public class TopPlateJson {
	private String top_plate; //"top_plate": "051",  //车顶号
	private String update_top_plate; //"update_top_plate": "051", //理货员更新后的车顶号
	private String trust; //"trust": 65.0,      //可信度
	private String note; //"note": "plateNote"  //备注
	
	public String getTop_plate() {
		return top_plate;
	}
	public void setTop_plate(String top_plate) {
		this.top_plate = top_plate;
	}	
	public String getUpdate_top_plate() {
		return update_top_plate;
	}
	public void setUpdate_top_plate(String update_top_plate) {
		this.update_top_plate = update_top_plate;
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

}
