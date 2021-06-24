package net.pingfang.dao.equipmentMonitoring;

import java.util.List;

import net.pingfang.entity.equipmentMonitoring.EmCamera;
import net.pingfang.entity.equipmentMonitoring.EmCrane;

/**
 * 设备监控
 * 岸桥节点
 * @author Administrator
 *
 */
public interface EmCraneDao {
	/**
	 * 获取所有岸桥监控信息
	 * @return
	 */
	public List<EmCrane> getEmCraneList();
	/**
	 * 根据岸桥名称获取岸桥监控数据
	 * @param craneName
	 * @return
	 */
	public EmCrane getEmCraneByName(String craneName);
	/**
	 * 获取岸桥上的相机监控信息
	 * @return
	 */
	public List<EmCamera> getEmCameraListByCraneId(int craneId);
	
	
	
	
	
	/**
	 * 插入一条设备监控岸桥节点
	 * @param CameraInforVo
	 * @return
	 */
	public int addEmCrane(EmCrane crane);
	
	/**
	 * 更新一条设备监控岸桥节点
	 * @param CameraInforVo
	 * @return
	 */
	public int updateEmCrane(EmCrane crane);
	/**
	 * 更新一条岸桥设备监控为离线状态
	 * @param CameraInforVo
	 * @return
	 */
	public int updateIsActivity(String craneName, String activity);
	/**
	 * 删除一条设备监控岸桥节点
	 * @param CameraInforVo
	 * @return
	 */
	public int deleteEmCrane(EmCrane crane);
	
	
	
	
	/**
	 * 批量新增设备监控相机节点
	 * @param CameraInforVo
	 * @return
	 */
	public int addEmCameraList(List<EmCamera> list);

}
