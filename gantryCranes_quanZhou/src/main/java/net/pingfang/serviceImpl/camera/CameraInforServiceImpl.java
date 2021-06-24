package net.pingfang.serviceImpl.camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.camera.CameraInforDao;
import net.pingfang.entity.camera.CameraInforVo;
import net.pingfang.service.camera.CameraInforService;

@Service
public class CameraInforServiceImpl implements CameraInforService {
	@Autowired
	private CameraInforDao cameraInforDao;
	/**
	 * 获取所有摄像机信息
	 * @return
	 */
	@Override
	public List<CameraInforVo> getCameraInforList(CameraInforVo cameraInforVo){
		return cameraInforDao.getCameraInforList(cameraInforVo);
	}
	/**
	 * 根据ID获取一条摄像机信息
	 * @param id
	 * @return
	 */
	@Override
	public CameraInforVo getCameraInforById(int id) {
		return cameraInforDao.getCameraInforById(id);
	}
	@Override
	public List<CameraInforVo> getListCameraInforById(List<Integer> list){
		return cameraInforDao.getListCameraInforById(list);
	}
	/**
	 * 插入一条摄像机信息
	 * @param CameraInforVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertCameraInfor(CameraInforVo cameraInforVo){
		return cameraInforDao.insertCameraInfor(cameraInforVo);
	}
	/**
	 * 更新一条摄像机信息
	 * @param CameraInforVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateCameraInfor(CameraInforVo cameraInforVo){
		return cameraInforDao.updateCameraInfor(cameraInforVo);
	}
	/**
	 * 删除一条摄像机信息
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteCameraInfor(int id){
		return cameraInforDao.deleteCameraInfor(id);
	}
	/**
	 * 根据岸桥ID获取所有摄像机集合
	 * @return
	 */
	@Override
	public List<CameraInforVo> getCameraInforListByCraneId(int craneId){
		return cameraInforDao.getCameraInforListByCraneId(craneId);
	}
	/**
	 * 获取所有门机下的球机相机
	 * @return
	 */
	@Override
	public Map<String, List<CameraInforVo>> getHpCameraOptions(){
		Map<String, List<CameraInforVo>> map = new HashMap<String, List<CameraInforVo>>();
		List<CameraInforVo> list = cameraInforDao.getHpCameraOptions();
		if(null !=list && list.size() >0) {
			List<CameraInforVo> newList = null;
			String craneNum = null;
			for(CameraInforVo ci : list) {
				craneNum = ci.getCraneNum();
				if(!map.containsKey(craneNum)) {
					newList = new ArrayList<CameraInforVo>();
					newList.add(ci);
					map.put(craneNum, newList);
				}else {
					map.get(craneNum).add(ci);
				}
			}
		}
		return map;
	}
	
	@Override
	public List<CameraInforVo> getHpCamera(){
		return cameraInforDao.getHpCamera();
	}
	/**
	 * 更新一条摄像机连接状态
	 * @param CameraInforVo
	 * @return
	 */
	/*@Override
	public int updateCameraInforActive(CameraInforVo cameraInforVo) {
		return cameraInforDao.updateCameraInforActive(cameraInforVo);
	}*/

}
