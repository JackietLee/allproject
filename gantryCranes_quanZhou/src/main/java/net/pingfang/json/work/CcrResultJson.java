package net.pingfang.json.work;

import java.util.List;

/**
 * 箱号识别结果
 * @author Administrator
 *
 */
public class CcrResultJson {
	private String[] door_dir; //"door_dir": ["left", "right"], //箱门朝向
	private String[] door_lock;//"door_lock": ["unknow", null], //铅封判别
	private int conta_weight; //"conta_weight": 19270,   //称重
	
	private List<ContaInforJson> conta_result; //货柜箱信息
	private FileInfoJson file_info; //图片信息
	
	public String[] getDoor_dir() {
		return door_dir;
	}
	public void setDoor_dir(String[] door_dir) {
		this.door_dir = door_dir;
	}
	public String[] getDoor_lock() {
		return door_lock;
	}
	public void setDoor_lock(String[] door_lock) {
		this.door_lock = door_lock;
	}
	public int getConta_weight() {
		return conta_weight;
	}
	public void setConta_weight(int conta_weight) {
		this.conta_weight = conta_weight;
	}
	public List<ContaInforJson> getConta_result() {
		return conta_result;
	}
	public void setConta_result(List<ContaInforJson> conta_result) {
		this.conta_result = conta_result;
	}
	public FileInfoJson getFile_info() {
		return file_info;
	}
	public void setFile_info(FileInfoJson file_info) {
		this.file_info = file_info;
	}
}
