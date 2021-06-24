package net.pingfang.json.work;

import java.util.List;

/**
 * 验残信息
 * @author Administrator
 *
 */
public class DamagedResultJson {
	private int com_damaged_num; //"com_damaged_num": 0,   //残损数量
	private List<DamagedInfoJson> damaged_info; //残损信息
	private FileInfoJson file_info; //图片信息
	
	public int getCom_damaged_num() {
		return com_damaged_num;
	}
	public void setCom_damaged_num(int com_damaged_num) {
		this.com_damaged_num = com_damaged_num;
	}
	public List<DamagedInfoJson> getDamaged_info() {
		return damaged_info;
	}
	public void setDamaged_info(List<DamagedInfoJson> damaged_info) {
		this.damaged_info = damaged_info;
	}
	public FileInfoJson getFile_info() {
		return file_info;
	}
	public void setFile_info(FileInfoJson file_info) {
		this.file_info = file_info;
	}
	
	
}
