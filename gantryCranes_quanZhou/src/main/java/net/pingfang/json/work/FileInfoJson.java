package net.pingfang.json.work;
/**
 * 图片信息
 * @author Administrator
 *
 */
public class FileInfoJson {
	private String note; //"note": "note",    //图片备注
	private String[] location; //"location": "leftCamera", //图片位置
	private int snap_img_type; //"snap_img_type": 5,     //图片类型
	private int img_num; //"img_num": 1,        //图片数量
	private String[] img_path_name; //"img_path_name": ["111.jpg", null], //图片集合
	private String[] img_dect_rect; //"img_dect_rect": ["0,0,576,768", null]//检测坐标
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String[] getLocation() {
		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public int getSnap_img_type() {
		return snap_img_type;
	}
	public void setSnap_img_type(int snap_img_type) {
		this.snap_img_type = snap_img_type;
	}
	public int getImg_num() {
		return img_num;
	}
	public void setImg_num(int img_num) {
		this.img_num = img_num;
	}
	public String[] getImg_path_name() {
		return img_path_name;
	}
	public void setImg_path_name(String[] img_path_name) {
		this.img_path_name = img_path_name;
	}
	public String[] getImg_dect_rect() {
		return img_dect_rect;
	}
	public void setImg_dect_rect(String[] img_dect_rect) {
		this.img_dect_rect = img_dect_rect;
	}
	
}
