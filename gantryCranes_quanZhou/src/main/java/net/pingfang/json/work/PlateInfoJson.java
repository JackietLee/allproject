package net.pingfang.json.work;
/**
 * 车牌信息
 * @author Administrator
 *
 */
public class PlateInfoJson {
	private String plate; //"plate": "Plate01", //车牌号
	private String plate_color; //"plate_color": "蓝", //车牌颜色
	private String trust; //"trust": 60.0,    //可信度
	private String note; //"note": "备注" 
	
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getPlate_color() {
		return plate_color;
	}
	public void setPlate_color(String plate_color) {
		this.plate_color = plate_color;
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
