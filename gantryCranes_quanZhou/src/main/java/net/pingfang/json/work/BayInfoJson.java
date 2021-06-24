package net.pingfang.json.work;
/**
 * 贝位信息
 * @author Administrator
 *
 */
public class BayInfoJson {
	 private int x;	//"x": 0, //贝位坐标
	 private int y;	//"y": 0, //贝位坐标
	 private int z;	//"z": 0, //贝位坐标
	 private String note;	//"note": "" //贝位文本坐标
	 
	 public int getX() {
		return x;
	 }
	 public void setX(int x) {
		this.x = x;
	 }
	 public int getY() {
		return y;
	 }
	 public void setY(int y) {
		this.y = y;
	 }
	 public int getZ() {
		return z;
	 }
	 public void setZ(int z) {
		this.z = z;
	 }
	 public String getNote() {
		return note;
	 }
	 public void setNote(String note) {
		this.note = note;
	 }
}
