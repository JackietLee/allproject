package net.pingfang.json.work;

import java.util.List;

/**
 * 贝位识别信息
 * @author Administrator
 *
 */
public class BayResultJson {
	private List<BayInfoJson> bay_info;	//贝位信息
	private FileInfoJson file_info; //图片信息
	
	public List<BayInfoJson> getBay_info() {
		return bay_info;
	}
	public void setBay_info(List<BayInfoJson> bay_info) {
		this.bay_info = bay_info;
	}
	public FileInfoJson getFile_info() {
		return file_info;
	}
	public void setFile_info(FileInfoJson file_info) {
		this.file_info = file_info;
	}
}
