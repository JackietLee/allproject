package net.pingfang.service.equipmentMonitoring;

import java.util.List;

import net.pingfang.entity.equipmentMonitoring.EmCamera;
import net.pingfang.entity.equipmentMonitoring.EmCrane;

public interface EmCraneService {
	
	/**
	 * 获取所有岸桥监控信息
	 * @return
	 */
	public List<EmCrane> getEmCraneList();
	/**
	 * 获取岸桥上的相机监控信息
	 * @return
	 */
	public List<EmCamera> getEmCameraListByCraneId(EmCrane emCrane);
	
	
	
	
	/**
	 * 插入一条设备监控岸桥节点
	 * @param CameraInforVo
	 * @return
	 */
	public int addEmCrane(EmCrane crane);
	/**
	 * 更新一条岸桥设备监控为离线状态
	 * @param CameraInforVo
	 * @return
	 */
	public int updateIsActivity(String craneName, String activity);

}
