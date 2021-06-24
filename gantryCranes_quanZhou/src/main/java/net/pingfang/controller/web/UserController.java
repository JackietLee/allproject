package net.pingfang.controller.web;

import net.pingfang.entity.role.Trole;
import net.pingfang.entity.role.Tuser;
import net.pingfang.entity.systemManage.PermissionsVo;
import net.pingfang.handler.Result;
import net.pingfang.service.web.TroleService;
import net.pingfang.service.web.TuserService;
import net.pingfang.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.pingfang.service.systemManage.MenuService;
import net.pingfang.service.systemManage.PermissionsService;

/**
* @Author: caoguofeng
* @Description: 当前登录用户控制器
* @Date: Created in 2018/2/8 19:28
* @param 
*/
@RestController
@Api("UserController Api")
@RequestMapping("/user")
public class UserController {
	@Resource
	private TroleService troleService;
	@Resource
	private TuserService tuserService;
	@Resource
	private MenuService menuService;
	@Autowired
	private PermissionsService permissionsService;
	
	/**
     * 用户登录请求
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Map<String,Object> login(String imageCode, @Valid Tuser user, HttpSession session,HttpServletRequest request){
    	// 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
    	
    	Map<String,Object> map=new HashMap<String,Object>();
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(), user.getPassword());
		try{
			subject.login(token); // 登录认证
			String userName=(String) SecurityUtils.getSubject().getPrincipal();
			Example tuserExample=new Example(Tuser.class);
			tuserExample.or().andEqualTo("userName",userName);
			Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
			session.setAttribute("currentUser", currentUser);
			List<Trole> roleList=troleService.selectRolesByUserId(currentUser.getId());
			map.put("roleList", roleList);
			map.put("roleSize", roleList.size());
			map.put("success", true);
			//logService.save(new Log(Log.LOGIN_ACTION,"用户登录")); // 写入日志
			//保存角色信息
			session.setAttribute("currentRoleList", roleList);
			//获取权限
			List<PermissionsVo> permissionsList = permissionsService.getPermissionsListByRole(roleList);
			//保存权限信息
			session.setAttribute("currentPermissionsList", permissionsList);
			map.put("permissionsList", permissionsList);
			return map;
		}catch(UnknownAccountException e){
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "用户名不存在！");
			return map;
		}catch(IncorrectCredentialsException e){
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "密码错误！");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "登录异常！");
			return map;
		}
    }

	/**
	 * 安全退出
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public String logout() throws Exception {
//		logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
		SecurityUtils.getSubject().logout();
		return "redirect:/tologin";
	}

	/**
	 *加载权限菜单
	 * @return
	 */
	@ApiOperation(value="加载权限菜单", notes="加载权限菜单")
	@GetMapping("/loadMenuInfo")
	public Result<Object> loadMenuInfo(HttpSession session) {
		Object objList = session.getAttribute("currentRoleList");
		if(null !=objList) {
			List<Trole> roleList = (List<Trole>)objList;
			if(null !=roleList && roleList.size() >0) {
				//根据当前用户的角色id查询所有菜单及子菜单
				return ResultUtil.success(menuService.getListMenuByRole(roleList));
			}else {
				return ResultUtil.error(1, "该用户没有角色权限，获取菜单数据失败！");
			}
		}else {
			return ResultUtil.error(1, "该用户没有角色权限，获取菜单数据失败！");
		}		
	}
	
}
