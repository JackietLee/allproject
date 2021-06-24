package net.pingfang.json.work;

/**
 * 吊具信息
 * @author Administrator
 *
 */
public class PicDataJson {
	private int std_sea_x;	//"std_sea_x": 1900, //海陆侧分界线
	private int std_land_x;	//"std_land_x": 2900, //陆侧与陆侧外分界线
	private int x;	//"x": 900, //吊具海陆位置
	private int y;	//"y": 1800 //吊具高度值
	
	public int getStd_sea_x() {
		return std_sea_x;
	}
	public void setStd_sea_x(int std_sea_x) {
		this.std_sea_x = std_sea_x;
	}
	public int getStd_land_x() {
		return std_land_x;
	}
	public void setStd_land_x(int std_land_x) {
		this.std_land_x = std_land_x;
	}
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
	
	

}
