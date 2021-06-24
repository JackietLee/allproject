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
import net.pingfang.entity.role.Trole;
import net.pingfang.handler.Result;
import net.pingfang.service.systemManage.RoleService;
import net.pingfang.service.systemManage.UserRroleService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("Role api")
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRroleService userRroleService;
	
	private final static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 *查询角色列表
	 * @return
	 */
	@ApiOperation(value="查询角色列表", notes="查询角色列表")
	@GetMapping("/getListRole")
	@RequiresPermissions(value = {"getListRole"})
	public Result<Object> getListRole() {
		return ResultUtil.success(roleService.getListRole());
	}
	
	/**
	 * 新增角色数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增角色数据", notes="新增角色数据")
	@ApiParam(name = "Trole", value = "角色JSON数据")
	@PostMapping("/addRole")
	@RequiresPermissions(value = {"addRole"})
	public Result<Object> addRole(@RequestBody Trole role) {
		//新增
		int count = roleService.addRole(role);
		if(count >0) {
			return ResultUtil.success("id: "+role.getId());
		}else {
			logger.error("新增角色数据失败！");
			logger.error(JsonUtil.javaToJsonStr(role));
			return ResultUtil.error(1, "新增角色数据失败！");
		}
	}
	
	/**
	 * 更新角色数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新角色数据", notes="更新角色数据")
	@ApiParam(name = "Trole", value = "角色JSON数据")
	@PostMapping("/updateRole")
	@RequiresPermissions(value = {"updateRole"})
	public Result<Object> updateRole(@RequestBody Trole role) {
		//更新
		int count = roleService.updateRole(role);
		if(count >0) {
			return ResultUtil.success("id: "+role.getId());
		}else {
			logger.error("更新角色数据失败！");
			logger.error(JsonUtil.javaToJsonStr(role));
			return ResultUtil.error(1, "更新角色数据失败！");
		}
	}
	
	/**
	 * 删除角色数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除角色数据", notes="删除角色数据")
	@ApiParam(name = "id", value = "角色数据id")
	@DeleteMapping("/deleteRole")
	@RequiresPermissions(value = {"deleteRole"})
	public Result<Object> deleteRole(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			int count = userRroleService.getCountUrByRoleId(id);
			if(0 == count) {
				count = roleService.deleteRole(id);
				if(count >0) {
					return ResultUtil.success("id: "+id);
				}else {
					logger.error("删除角色数据失败！");
					return ResultUtil.error(1, "删除角色数据失败！");
				}
			}else {
				logger.error("该角色已经被引用，请先解除被引用的关系！");
				return ResultUtil.error(1, "该角色已经被引用，请先解除被引用的关系！");
			}
		}else {
			return ResultUtil.error(1, "角色数据ID不能为空并且必须大0！");
		}
	}
	
}
