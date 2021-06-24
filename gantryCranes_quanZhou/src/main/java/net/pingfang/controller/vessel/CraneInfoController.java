package net.pingfang.controller.vessel;

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
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.CraneInfoService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 岸桥信息Controller
 * @author Administrator
 * @since 2019-06-10
 *
 */
@Api("CraneInfo Api")
@RestController
@RequestMapping("/vessel")
public class CraneInfoController {
	
	@Autowired
	private CraneInfoService craneInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(CraneInfoController.class);
	/**
	*查询岸桥列表
	* @return
	*/
	@ApiOperation(value="查询岸桥列表", notes="查询岸桥列表")
	@PutMapping("/getAllCraneInfoData")
	@RequiresPermissions(value = {"getAllCraneInfoData"})
	public Result<Object> getAllCraneInfoData(@RequestBody CraneInfoVo craneInfoVo) {
		return ResultUtil.success(craneInfoService.getCraneInfoList(craneInfoVo));
	}

	/**
	 * 查询岸桥详情
	 * @param 查询条件 id
	 * @return
	 */
	@ApiOperation(value="查询岸桥详情", notes="查询岸桥详情")
	@ApiParam(name = "id", value = "泊位计划id")
	@GetMapping("/getCraneInfoById")
	@RequiresPermissions(value = {"getCraneInfoById"})
	public Result<Object> getCraneInfoById(Integer id) {
		if(null !=id && id>0) {
			CraneInfoVo craneInfo = craneInfoService.getCraneInfoById(id);			
			if(null != craneInfo) {
				return ResultUtil.success(craneInfo);
			}else {
				logger.error("岸桥信息id:"+id+"在数据库里不存在！");
				return ResultUtil.error(1, "岸桥信息id:"+id+"在数据库里不存在！");
			}			
		}else {
			logger.error("岸桥信息id不能为空并且大于0！");
			return ResultUtil.error(1, "岸桥信息id不能为空并且大于0！");
		}
	}
	
	/**
	 * 新增或更新岸桥数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增或更新岸桥数据", notes="新增或更新岸桥数据")
	@ApiParam(name = "craneInfoVo", value = "岸桥信息JSON数据")
	@PostMapping("/insertCraneInfo")
	@RequiresPermissions(value = {"insertCraneInfo"})
	public Result<Object> insertCraneInfo(@RequestBody CraneInfoVo craneInfoVo) {
		if(craneInfoVo.getId() >0) {
			//更新
			int count = craneInfoService.updateCraneInfo(craneInfoVo);
			if(count >0) {
				return ResultUtil.success("id: "+craneInfoVo.getId());
			}else {
				logger.error("更新岸桥信息失败！");
				logger.error(JsonUtil.javaToJsonStr(craneInfoVo));
				return ResultUtil.error(1, "更新岸桥信息失败！");
			}
		}else {
			//新增
			int count = craneInfoService.insertCraneInfo(craneInfoVo);
			if(count >0) {
				return ResultUtil.success("id: "+craneInfoVo.getId());
			}else {
				logger.error("新增岸桥信息失败！");
				logger.error(JsonUtil.javaToJsonStr(craneInfoVo));
				return ResultUtil.error(1, "新增岸桥信息失败！");
			}
		}
	}
	/**
	 * 删除岸桥数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除岸桥数据", notes="删除岸桥数据")
	@ApiParam(name = "id", value = "岸桥信息id")
	@DeleteMapping("/deleteCraneInfo")
	@RequiresPermissions(value = {"deleteCraneInfo"})
	public Result<Object> deleteCraneInfo(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			int count = craneInfoService.deleteCraneInfo(id);
			if(count >0) {
				return ResultUtil.success("id: "+id);
			}else {
				logger.error("删除岸桥信息失败！");
				return ResultUtil.error(1, "删除岸桥信息失败！");
			}
		}else {
			return ResultUtil.error(1, "岸桥信息ID不能为空并且必须大0！");
		}
	}
	
	/**
	 * 重启岸桥相应的控制程序
	 * @param CraneInfoVo
	 * @return
	 */
	@ApiOperation(value="重启岸桥相应的控制程序", notes="重启岸桥相应的控制程序")
	@ApiParam(name = "CraneInfoVo", value = "岸桥信息")
	@PostMapping("/restartProgram")
	@RequiresPermissions(value = {"restartProgram"})
	public Result<Object> restartProgram(@RequestBody CraneInfoVo craneInfoVo) {
		if(null !=craneInfoVo.getControlIp() && craneInfoVo.getControlPort()>0 && null !=craneInfoVo.getType()) {
			String code = craneInfoService.restartProgram(craneInfoVo);
			if(null != code && "200".equals(code)) {
				return ResultUtil.success("重启命令发送成功！");
			}else {
				return ResultUtil.error(1,"重启命令发送失败！");
			}
		}else {
			return ResultUtil.error(1, "提交的参数不正确！");
		}
	}
	
	/**
	 *查询黄埔门机下拉列表
	 * @return
	 */
	@ApiOperation(value="查询黄埔门机下拉列表", notes="查询黄埔门机下拉列表")
	@GetMapping("/getHpCraneOptions")
	@RequiresPermissions(value = {"getHpCraneOptions"})
	public Result<Object> getHpCraneOptions() {
		return ResultUtil.success(craneInfoService.getHpCraneOptions());
	}
	
}
