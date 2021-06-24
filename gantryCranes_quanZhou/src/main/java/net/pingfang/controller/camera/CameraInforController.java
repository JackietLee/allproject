package net.pingfang.controller.camera;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.camera.CameraInforVo;
import net.pingfang.handler.Result;
import net.pingfang.service.camera.CameraInforService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 摄像机信息Controller
 * @author Administrator
 * @since 2019-06-10
 *
 */
@Api("CameraInfor Api")
@RestController
@RequestMapping("/camera")
public class CameraInforController {
	
	@Autowired
	private CameraInforService cameraInforService;
	
	private final static Logger logger = LoggerFactory.getLogger(CameraInforController.class);
	/**
	 *查询摄像机列表
	 * @return
	 */
	@ApiOperation(value="查询摄像机列表", notes="查询摄像机列表")
	@ApiParam(name = "CameraInforVo", value = "摄像机信息JSON数据")
	@PutMapping("/getAllCameraInforData")
	@RequiresPermissions(value = {"getAllCameraInforData"})
	public Result<Object> getCameraInforList(@RequestBody CameraInforVo cameraInforVo) {
		return ResultUtil.success(cameraInforService.getCameraInforList(cameraInforVo));
	}
	/**
	 * 查询摄像机详情
	 * @param 查询条件 id
	 * @return
	 */
	@ApiOperation(value="查询摄像机详情", notes="查询摄像机详情")
	@ApiParam(name = "id", value = "摄像机id")
	@GetMapping("/getCameraInforById")
	@RequiresPermissions(value = {"getCameraInforById"})
	public Result<Object> getCameraInforById(Integer id) {
		if(null !=id && id>0) {
			CameraInforVo cameraInforVo = cameraInforService.getCameraInforById(id);			
			if(null != cameraInforVo) {
				return ResultUtil.success(cameraInforVo);
			}else {
				logger.error("摄像机信息id:"+id+"在数据库里不存在！");
				return ResultUtil.error(1, "摄像机信息id:"+id+"在数据库里不存在！");
			}			
		}else {
			logger.error("摄像机信息id不能为空并且大于0！");
			return ResultUtil.error(1, "摄像机信息id不能为空并且大于0！");
		}
	}
	
	/**
	 * 新增摄像机数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增摄像机数据", notes="新增摄像机数据")
	@ApiParam(name = "CameraInforVo", value = "摄像机信息JSON数据")
	@PostMapping("/insertCameraInfor")
	@RequiresPermissions(value = {"insertCameraInfor"})
	public Result<Object> insertCameraInfor(@RequestBody CameraInforVo cameraInforVo) {
		//新增
		int count = cameraInforService.insertCameraInfor(cameraInforVo);
		if(count >0) {
			return ResultUtil.success("id: "+cameraInforVo.getId());
		}else {
			logger.error("新增一条摄像机信息失败！");
			logger.error(JsonUtil.javaToJsonStr(cameraInforVo));
			return ResultUtil.error(1, "新增一条摄像机信息失败！");
		}
	}
	/**
	 * 更新摄像机数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新摄像机数据", notes="更新摄像机数据")
	@ApiParam(name = "CameraInforVo", value = "摄像机信息JSON数据")
	@PostMapping("/updateCameraInfor")
	@RequiresPermissions(value = {"updateCameraInfor"})
	public Result<Object> updateCameraInfor(@RequestBody CameraInforVo cameraInforVo) {
		//更新
		int count = cameraInforService.updateCameraInfor(cameraInforVo);
		if(count >0) {
			return ResultUtil.success("id: "+cameraInforVo.getId());
		}else {
			logger.error("更新一条摄像机信息失败！");
			logger.error(JsonUtil.javaToJsonStr(cameraInforVo));
			return ResultUtil.error(1, "更新一条摄像机信息失败！");
		}
	}
	/**
	 * 删除摄像机数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除摄像机数据", notes="删除摄像机数据")
	@ApiParam(name = "id", value = "摄像机信息id")
	@DeleteMapping("/deleteCameraInfor")
	@RequiresPermissions(value = {"deleteCameraInfor"})
	public Result<Object> deleteCameraInfor(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			int count = cameraInforService.deleteCameraInfor(id);
			if(count >0) {
				return ResultUtil.success("id: "+id);
			}else {
				logger.error("删除摄像机信息失败！");
				return ResultUtil.error(1, "删除摄像机信息失败！");
			}
		}else {
			return ResultUtil.error(1, "摄像机信息ID不能为空并且必须大0！");
		}
	}
	
	/**
	 *查询岸桥的所有摄像机
	 * @return
	 */
	@ApiOperation(value="查询岸桥的所有摄像机", notes="查询岸桥的所有摄像机")
	@ApiParam(name = "craneId", value = "岸桥id")
	@GetMapping("/getCameraInforListByCraneId")
	@RequiresPermissions(value = {"getCameraInforListByCraneId"})
	public Result<Object> getCameraInforListByCraneId(Integer craneId) {
		return ResultUtil.success(cameraInforService.getCameraInforListByCraneId(craneId));
	}
	
	/**
	 *获取所有门机下的球机相机
	 * @return
	 */
	@ApiOperation(value="获取所有门机下的球机相机", notes="获取所有门机下的球机相机")
	@GetMapping("/getHpCameraOptions")
	@RequiresPermissions(value = {"getHpCameraOptions"})
	public Result<Object> getHpCameraOptions() {
		return ResultUtil.success(cameraInforService.getHpCameraOptions());
	}
	
}
