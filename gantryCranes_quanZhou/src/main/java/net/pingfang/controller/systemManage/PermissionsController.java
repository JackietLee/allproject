package net.pingfang.controller.systemManage;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.systemManage.PermissionsVo;
import net.pingfang.handler.Result;
import net.pingfang.service.systemManage.PermissionsService;
import net.pingfang.service.systemManage.RolePermissionsService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("Permissions api")
@RestController
@RequestMapping("/permissions")
public class PermissionsController {

	@Autowired
	private PermissionsService permissionsService;
	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	private final static Logger logger = LoggerFactory.getLogger(PermissionsController.class);
	
	/**
	 *查询树形权限数据
	 * @return
	 */
	@ApiOperation(value="查询树形权限数据", notes="查询树形权限数据")
	@GetMapping("/getListPermissions")
	@RequiresPermissions(value = {"getListPermissions"})
	public Result<Object> getListPermissions() {
		return ResultUtil.success(permissionsService.getListPermissions());
	}
	
	/**
	 * 新增权限数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增权限数据", notes="新增权限数据")
	@ApiParam(name = "PermissionsVo", value = "权限JSON数据")
	@PostMapping("/addPermissions")
	@RequiresPermissions(value = {"addPermissions"})
	public Result<Object> addPermissions(@RequestBody PermissionsVo permissions) {
		//如果该权限为菜单权限，则权限ID必须大于0
		if("menu".equals(permissions.getType()) && 0 == permissions.getMenuId()) {
			logger.error("新增权限数据失败，菜单不能为空！");
			logger.error(JsonUtil.javaToJsonStr(permissions));
			return ResultUtil.error(1, "新增权限数据失败，菜单不能为空！");
		}		
		//新增
		int count = permissionsService.addPermissions(permissions);
		if(count >0) {
			return ResultUtil.success("id: "+permissions.getId());
		}else {
			logger.error("新增权限数据失败！");
			logger.error(JsonUtil.javaToJsonStr(permissions));
			return ResultUtil.error(1, "新增权限数据失败！");
		}
	}
	
	/**
	 * 更新权限数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新权限数据", notes="更新权限数据")
	@ApiParam(name = "PermissionsVo", value = "权限JSON数据")
	@PostMapping("/updatePermissions")
	@RequiresPermissions(value = {"updatePermissions"})
	public Result<Object> updatePermissions(@RequestBody PermissionsVo permissions) {
		//更新
		int count = permissionsService.updatePermissions(permissions);
		if(count >0) {
			return ResultUtil.success("id: "+permissions.getId());
		}else {
			logger.error("更新权限数据失败！");
			logger.error(JsonUtil.javaToJsonStr(permissions));
			return ResultUtil.error(1, "更新权限数据失败！");
		}
	}
	
	/**
	 * 删除权限数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除权限数据", notes="删除权限数据")
	@ApiParam(name = "id", value = "权限数据id")
	@DeleteMapping("/deletePermissions")
	@RequiresPermissions(value = {"deletePermissions"})
	public Result<Object> deletePermissions(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			//查询该权限是否被引用
			int count = rolePermissionsService.getCountRpBypermissionsId(id);
			if(0 == count) {
				count = permissionsService.deletePermissions(id);
				if(count >0) {
					return ResultUtil.success("id: "+id);
				}else {
					logger.error("删除权限数据失败！");
					return ResultUtil.error(1, "删除权限数据失败！");
				}
			}else {
				return ResultUtil.error(1, "该权限已被引用，请先解除引用！");
			}
		}else {
			return ResultUtil.error(1, "权限数据ID不能为空并且必须大0！");
		}
	}
}
