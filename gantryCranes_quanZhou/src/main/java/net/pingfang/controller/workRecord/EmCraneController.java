package net.pingfang.controller.workRecord;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.equipmentMonitoring.EmCrane;
import net.pingfang.handler.Result;
import net.pingfang.service.equipmentMonitoring.EmCraneService;
import net.pingfang.utils.ResultUtil;

@Api("EmCraneController Api")
@RestController
@RequestMapping("/emCrane")
public class EmCraneController {
	@Autowired
	private EmCraneService emCraneService;
	
	/**
	 * 获取所有岸桥监控信息
	 * @return
	 */
	@ApiOperation(value="获取所有岸桥监控信息", notes="获取所有岸桥监控信息")
	@RequestMapping(value = "getEmCraneList", method = RequestMethod.GET)
	@RequiresPermissions(value = {"getEmCraneList"})
	public Result<Object> getEmCraneList() {
		return ResultUtil.success(ResultUtil.success(emCraneService.getEmCraneList()));
	}
	
	/**
	 * 获取岸桥上的相机监控信息
	 * @return
	 */
	@ApiOperation(value="获取岸桥上的相机监控信息", notes="获取岸桥上的相机监控信息")
	@ApiParam(name = "EmCrane", value = "岸桥JSON数据")
	@RequestMapping(value = "getEmCameraListByCraneId", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getEmCameraListByCraneId"})
	public Result<Object> getEmCameraListByCraneId(@RequestBody EmCrane emCrane) {
		return ResultUtil.success(ResultUtil.success(emCraneService.getEmCameraListByCraneId(emCrane)));
	}

}
