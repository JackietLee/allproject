package net.pingfang.controller.user;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.user.UserVo;
import net.pingfang.handler.Result;
import net.pingfang.service.user.UserService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 用户管理Controller
 * @author Administrator
 * @since 2019-05-22
 *
 */
@Api("UserManage api")
@RestController
@RequestMapping("/user")
public class UserManageController {
	
	@Autowired
	private UserService userService;
	
	private final static Logger logger = LoggerFactory.getLogger(UserManageController.class);
	
	/**
	 *  查询用户详情
	 * @return
	 */
	@ApiOperation(value="查询用户详情", notes="查询用户详情")
	@ApiParam(name = "id", value = "用户数据id")
	@PostMapping("/getUserById")
	@RequiresPermissions(value = {"getUserById"})
	public @ResponseBody Result<Object> getUserById(int id) {
		UserVo user = userService.getUserById(id);
		if(null == user) {
			return ResultUtil.error(1, "用户不存在！");
		}else {
			return ResultUtil.success(user);
		}		
	}
	
	/**
	 *查询用户列表信息
	 * @return
	 */
	@ApiOperation(value="查询用户列表信息", notes="查询用户列表信息")
	@GetMapping("/getListUser")
	//@RequiresPermissions(value = {"getListUser"})
	public Result<Object> getListUser() {
		return ResultUtil.success(userService.getListUser());
	}
	
	/**
	 * 新增用户数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增用户数据", notes="新增用户数据")
	@ApiParam(name = "UserVo", value = "信息JSON数据")
	@PostMapping("/addUser")
	@RequiresPermissions(value = {"addUser"})
	public Result<Object> addUser(@RequestBody UserVo user) {
		//新增
		int count = userService.addUser(user);
		if(count >0) {
			return ResultUtil.success("id: "+user.getId());
		}else {
			logger.error("新增用户数据失败！");
			logger.error(JsonUtil.javaToJsonStr(user));
			return ResultUtil.error(1, "新增用户数据失败！");
		}
	}
	
	/**
	 * 更新用户数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新用户数据", notes="更新用户数据")
	@ApiParam(name = "UserVo", value = "用户JSON数据")
	@PostMapping("/updateUser")
	@RequiresPermissions(value = {"updateUser"})
	public Result<Object> updateUser(@RequestBody UserVo user) {
		//更新
		int count = userService.updateUser(user);
		if(count >0) {
			return ResultUtil.success("id: "+user.getId());
		}else {
			logger.error("更新用户数据失败！");
			logger.error(JsonUtil.javaToJsonStr(user));
			return ResultUtil.error(1, "更新用户数据失败！");
		}
	}
	
	/**
	 * 删除用户数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除用户数据", notes="删除用户数据")
	@ApiParam(name = "id", value = "用户数据id")
	@DeleteMapping("/deleteUser")
	@RequiresPermissions(value = {"deleteUser"})
	public Result<Object> deleteUser(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			int count = userService.deleteUser(id);
			if(count >0) {
				return ResultUtil.success("id: "+id);
			}else {
				logger.error("删除用户数据失败！");
				return ResultUtil.error(1, "删除用户数据失败！");
			}
		}else {
			return ResultUtil.error(1, "用户数据ID不能为空并且必须大0！");
		}
	}
	

}
