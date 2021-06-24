package net.pingfang.dao.camera;

import java.util.List;
import net.pingfang.entity.camera.CameraInforVo;

/**
 * 摄像机信息Dao
 * @param CameraInforDao
 */
public interface CameraInforDao {

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
	public List<CameraInforVo> getHpCameraOptions();
	public List<CameraInforVo> getHpCamera();
	
	
}
