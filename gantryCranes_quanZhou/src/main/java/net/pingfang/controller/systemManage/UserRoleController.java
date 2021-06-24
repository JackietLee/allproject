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
import net.pingfang.entity.role.Tuserrole;
import net.pingfang.handler.Result;
import net.pingfang.service.systemManage.UserRroleService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("UserRole api")
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

	@Autowired
	private UserRroleService userRroleService;
	
	private final static Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	
	/**
	 * 查询用户角色
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询用户角色", notes="查询用户角色")
	@ApiParam(name = "id", value = "用户id")
	@GetMapping("/getUserRoleList")
	@RequiresPermissions(value = {"getUserRoleList"})
	public Result<Object> getUserRoleList(int id) {
		return ResultUtil.success(userRroleService.getUserRoleList(id));
	}
	
	/**
	 * 分配用户角色
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="分配用户角色", notes="分配用户角色")
	@ApiParam(name = "list", value = "角色JSON数据")
	@PostMapping("/addUserRoleList")
	@RequiresPermissions(value = {"addUserRoleList"})
	public Result<Object> addUserRoleList(@RequestBody List<Tuserrole> list) {
		//新增
		int count = userRroleService.addUserRoleList(list);
		if(count >0) {
			return ResultUtil.success("分配用户角色成功！");
		}else {
			logger.error("分配用户角色数据失败！");
			logger.error(JsonUtil.javaToJsonStr(list));
			return ResultUtil.error(1, "分配用户角色数据失败！");
		}
	}
	
}
