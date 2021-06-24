package net.pingfang.json.work;

/**
 * 车辆检测结果
 * @author Administrator
 *
 */
public class TpplateResultJson {
	
	private String car_dir; //"car_dir": "left",  //车头朝向
	private TopPlateJson tp_result; //货柜箱顶板信息
	private FileInfoJson file_info; //图片信息
	
	public String getCar_dir() {
		return car_dir;
	}
	public void setCar_dir(String car_dir) {
		this.car_dir = car_dir;
	}
	public TopPlateJson getTp_result() {
		return tp_result;
	}
	public void setTp_result(TopPlateJson tp_result) {
		this.tp_result = tp_result;
	}
	public FileInfoJson getFile_info() {
		return file_info;
	}
	public void setFile_info(FileInfoJson file_info) {
		this.file_info = file_info;
	}

}
