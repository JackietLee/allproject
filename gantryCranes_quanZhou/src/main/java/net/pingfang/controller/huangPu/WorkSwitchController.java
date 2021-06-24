package net.pingfang.controller.huangPu;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.handler.Result;
import net.pingfang.service.huangPu.WorkSwitchService;
import net.pingfang.utils.ResultUtil;

/**
 * 黄埔作业开关Controller
 * @author Administrator
 * @since 2020-09-26
 *
 */
@Api("WorkSwitch Api")
@RestController
@RequestMapping("/crane")
public class WorkSwitchController {
	
	@Autowired
	public WorkSwitchService workSwitchService;
	
	@ApiOperation(value="获取门机配制", notes="获取门机配制")
	@GetMapping("/getHpCranePreparationList")
	@RequiresPermissions(value = {"getHpCranePreparationList"})
	public Result<Object> getHpCranePreparationList() {
		return ResultUtil.success(workSwitchService.getHpCranePreparationList());
	}
	
	/**
	 * 新增或者更新门机配制
	 * 1、设置门机工作相机协议
	 * 2、门机切换球机摄像头
	 *  {
			"messageType": "MJ01",
			"area": "场区",
			"driverCode": "设备号",
			"driverName": "设备名称",
			"acquisitionTime": "指令时间",
			"workType": "作业类型：bay,conta,vehicle",
			"clientId": "客户端ID",
			"vessel_voyage_number": “航次”,
			"cameraList": [{
				"cameraCode": "相机编号",
				"cameraIp": "相机IP",
				"cameraType": "相机工作类型"
			}]
		}

	 * @return
	 */
	@ApiOperation(value="黄埔门机切换球机", notes="黄埔门机切换球机")
	@ApiParam(name = "CranePreparationVo", value = "摄像机信息JSON数据")
	@PostMapping("/hpSwitchCameraList")
	@RequiresPermissions(value = {"hpSwitchCameraList"})
	public Result<Object> hpSwitchCameraList(@RequestBody CranePreparationVo cranePreparationVo) {
		int code = workSwitchService.hpSwitchCameraList(cranePreparationVo);
		if(code <=0) {
			return ResultUtil.error(1,"球机摄像机命令发送失败！");
		}else if(2 == code){
			return ResultUtil.error(1,"球机摄像机已经被其他门机占用，命令发送失败！");
		}else{
			return ResultUtil.success("球机摄像机命令发送成功！");
		}
	}
	
	/**
	 * 1、设置门机开始或停止工作协议
	 * 2、启动门机作业
	 * 
	 * {
			"messageType": "MJ02",
			"area": "场区",
			"driverCode": "设备号",
			"driverName": "设备名称",
			"acquisitionTime": "指令时间",
			"workType": "作业类型：bay,conta,vehicle",
			"cmdType": "命令类型：start,stop",
			"clientId": "客户端ID",
			"vesselVoyageNumber": "航次",
			"jobQueueCode": "作业号"
		}
	 * @return
	 */
	@ApiOperation(value="启动门机作业", notes="启动门机作业")
	@ApiParam(name = "CameraInforVo", value = "摄像机信息JSON数据")
	@PostMapping("/hpStartHomeWork")
	@RequiresPermissions(value = {"hpStartHomeWork"})
	public Result<Object> hpStartHomeWork(@RequestBody CranePreparationVo cranePreparationVo) {
		int code = workSwitchService.hpStartHomeWork(cranePreparationVo);
		if(code >0) {
			return ResultUtil.success("启动门机作业命令发送成功！");
		}else {
			return ResultUtil.error(1,"启动门机作业命令发送失败！");
		}
	}
	/**
	 * 查询门机配制列表数据
	 * @return
	 */
	@ApiOperation(value="查询门机配制列表数据", notes="查询门机配制列表数据")
	@RequestMapping(value = "getAllMjPreparationList", method = RequestMethod.GET)
	@RequiresPermissions(value = {"getAllMjPreparationList"})
	public Result<Object> getAllMjPreparationList() {
		return ResultUtil.success(ResultUtil.success(workSwitchService.getAllMjPreparationList()));
	}
	
	/**
	 * 删除门机配制数据
	 * @return
	 */
	@ApiOperation(value="删除门机配制数据", notes="删除门机配制数据")
	@ApiParam(name = "id", value = "门机配制ID")
	@DeleteMapping("/deleteHpCranePreparation")
	@RequiresPermissions(value = {"deleteHpCranePreparation"})
	public Result<Object> deleteHpCranePreparation(String craneNum) {
		if(null !=craneNum && !"".equals(craneNum)) {
			int code = workSwitchService.deleteHpCranePreparationByCraneNum(craneNum);
			if(code >0) {
				return ResultUtil.success("删除门机配制数据成功！");
			}else {
				return ResultUtil.error(1,"删除门机配制数据失败！");
			}
		}else {
			return ResultUtil.error(1,"参数不正确！");
		}
	}
	

}
