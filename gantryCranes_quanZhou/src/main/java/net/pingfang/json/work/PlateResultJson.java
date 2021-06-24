package net.pingfang.json.work;

/**
 * 车牌识别结果
 * @author Administrator
 *
 */
public class PlateResultJson {
	private PlateInfoJson p_result;	//车牌信息
	private FileInfoJson file_info; //图片信息
	
	public PlateInfoJson getP_result() {
		return p_result;
	}
	public void setP_result(PlateInfoJson p_result) {
		this.p_result = p_result;
	}
	public FileInfoJson getFile_info() {
		return file_info;
	}
	public void setFile_info(FileInfoJson file_info) {
		this.file_info = file_info;
	}
	
	
}
