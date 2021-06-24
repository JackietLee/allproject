package net.pingfang.service.camera;

import java.util.List;
import java.util.Map;

import net.pingfang.entity.camera.CameraInforVo;

public interface CameraInforService {

	/**
	 * 获取所有摄像机信息
	 * @return
	 */
	public List<CameraInforVo> getCameraInforList(CameraInforVo cameraInforVo);
	/**
	 * 根据ID获取一条摄像机信息
	 * @param id
	 * @return
	 */
	public CameraInforVo getCameraInforById(int id);	
	public List<CameraInforVo> getListCameraInforById(List<Integer> list);
	/**
	 * 插入一条摄像机信息
	 * @param CameraInforVo
	 * @return
	 */
	public int insertCameraInfor(CameraInforVo cameraInforVo);
	/**
	 * 更新一条摄像机信息
	 * @param CameraInforVo
	 * @return
	 */
	public int updateCameraInfor(CameraInforVo cameraInforVo);
	/**
	 * 删除一条摄像机信息
	 * @param id
	 * @return
	 */
	public int deleteCameraInfor(int id);
	/**
	 * 根据岸桥ID获取所有摄像机集合
	 * @return
	 */
	public List<CameraInforVo> getCameraInforListByCraneId(int craneId);
	
	/**
	 * 获取所有门机下的球机相机
	 * @return
	 */
	public Map<String, List<CameraInforVo>> getHpCameraOptions();
	public List<CameraInforVo> getHpCamera();
	
	/**
	 * 更新一条摄像机连接状态
	 * @param CameraInforVo
	 * @return
	 */
	//public int updateCameraInforActive(CameraInforVo cameraInforVo);
}
