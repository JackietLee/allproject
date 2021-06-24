package net.pingfang.controller.systemManage;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.role.Trolemenu;
import net.pingfang.handler.Result;
import net.pingfang.service.systemManage.RolePermissionsService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("Role Permissions api")
@RestController
@RequestMapping("/rolePermissions")
public class RolePermissionsController {

	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	private final static Logger logger = LoggerFactory.getLogger(RolePermissionsController.class);
	
	/**
	 * 查询角色权限
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询用户角色", notes="查询用户角色")
	@ApiParam(name = "id", value = "用户id")
	@GetMapping("/getRolePermissionsListById")
	@RequiresPermissions(value = {"getRolePermissionsListById"})
	public Result<Object> getRolePermissionsListById(int id) {
		return ResultUtil.success(rolePermissionsService.getRolePermissionsListById(id));
	}
	
	/**
	 * 分配角色权限
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="分配角色权限", notes="分配角色权限")
	@ApiParam(name = "list", value = "权限JSON数据")
	@PostMapping("/addRolePermissionsList")
	@RequiresPermissions(value = {"addRolePermissionsList"})
	public Result<Object> addRolePermissionsList(@RequestBody List<Trolemenu> list) {
		//新增
		int count = rolePermissionsService.addRolePermissionsList(list);
		if(count >0) {
			return ResultUtil.success("分配角色权限成功！");
		}else {
			logger.error("分配角色权限数据失败！");
			logger.error(JsonUtil.javaToJsonStr(list));
			return ResultUtil.error(1, "分配角色权限数据失败！");
		}
	}
	
}
