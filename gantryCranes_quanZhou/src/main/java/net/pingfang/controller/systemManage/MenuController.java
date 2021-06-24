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
import net.pingfang.entity.systemManage.MenuVo;
import net.pingfang.handler.Result;
import net.pingfang.service.systemManage.MenuService;
import net.pingfang.service.systemManage.PermissionsService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;
@Api("Menu api")
@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private PermissionsService permissionsService;
	
	private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

	/**
	 *查询树形菜单数据
	 * @return
	 */
	@ApiOperation(value="查询树形菜单数据", notes="查询树形菜单数据")
	@GetMapping("/getListMenu")
	@RequiresPermissions(value = {"getListMenu"})
	public Result<Object> getListMenu() {
		return ResultUtil.success(menuService.getListMenu());
	}
	
	/**
	 * 新增菜单数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增菜单数据", notes="新增菜单数据")
	@ApiParam(name = "MenuVo", value = "菜单JSON数据")
	@PostMapping("/addMenu")
	@RequiresPermissions(value = {"addMenu"})
	public Result<Object> addMenu(@RequestBody MenuVo menu) {
		//新增
		int count = menuService.addMenu(menu);
		if(count >0) {
			return ResultUtil.success("id: "+menu.getId());
		}else {
			logger.error("新增菜单数据失败！");
			logger.error(JsonUtil.javaToJsonStr(menu));
			return ResultUtil.error(1, "新增菜单数据失败！");
		}
	}
	
	/**
	 * 更新菜单数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新菜单数据", notes="更新菜单数据")
	@ApiParam(name = "MenuVo", value = "菜单JSON数据")
	@PostMapping("/updateMenu")
	@RequiresPermissions(value = {"updateMenu"})
	public Result<Object> updateMenu(@RequestBody MenuVo menu) {
		//更新
		int count = menuService.updateMenu(menu);
		if(count >0) {
			return ResultUtil.success("id: "+menu.getId());
		}else {
			logger.error("更新菜单数据失败！");
			logger.error(JsonUtil.javaToJsonStr(menu));
			return ResultUtil.error(1, "更新菜单数据失败！");
		}
	}
	
	/**
	 * 删除菜单数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除菜单数据", notes="删除菜单数据")
	@ApiParam(name = "id", value = "菜单数据id")
	@DeleteMapping("/deleteMenu")
	@RequiresPermissions(value = {"deleteMenu"})
	public Result<Object> deleteMenu(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			//查询该菜单下是否有子菜单
			int count = menuService.getCountMenuByparentId(id);
			//查询该菜单是否已经关联权限
			int pCount = permissionsService.getCountPermissionsByMeuId(id);
			if(count >0) {
				logger.error("请先删除子菜单后再删除父菜单数据失败！");
				return ResultUtil.error(1, "请先删除子菜单后再删除父菜单数据失败！");
			}else if(pCount >0) {
				logger.error("该菜单已经关联权限，请先解除相应的权限！");
				return ResultUtil.error(1, "该菜单已经关联权限，请先解除相应的权限！");
			}else {
				//删除菜单
				count = menuService.deleteMenu(id);
				if(count >0) {
					return ResultUtil.success("id: "+id);
				}else {
					logger.error("删除菜单数据失败！");
					return ResultUtil.error(1, "删除菜单数据失败！");
				}				
			}
		}else {
			return ResultUtil.error(1, "菜单数据ID不能为空并且必须大0！");
		}
	}

}
