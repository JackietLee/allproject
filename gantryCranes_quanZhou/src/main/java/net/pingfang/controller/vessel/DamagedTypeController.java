package net.pingfang.controller.vessel;

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
//import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.DamagedTypeVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.DamagedTypeService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 残损信息Controller
 * "damaged_infor"表
 * @author Administrator
 * 2019-07-29
 *
 */
@Api("DamagedType Api")
@RestController
@RequestMapping("/damaged")
public class DamagedTypeController {

	@Autowired
	private DamagedTypeService damagedInforService;
	
	private final static Logger logger = LoggerFactory.getLogger(DamagedTypeController.class);
	
	/**
	 * 查询残损类型列表
	 * @return
	 */
	@ApiOperation(value="查询残损类型列表", notes="查询残损类型列表")
	@GetMapping("/getDamagedInforList")
	@RequiresPermissions(value = {"getDamagedInforList"})
	public Result<Object> getDamagedInforList() {
		return ResultUtil.success(damagedInforService.getDamagedInforList());
	}
	/**
	 * 查询残损数据详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询残损数据详情", notes="查询残损数据详情")
	@ApiParam(name = "id", value = "残损类型记录id")
	@GetMapping("/getDamagedInforById")
	@RequiresPermissions(value = {"getDamagedInforById"})
	public Result<Object> getDamagedInfor(int id) {
		if(id >0) {
			DamagedTypeVo damagedInforVo = damagedInforService.getDamagedInforById(id);
			if(null !=damagedInforVo) {
				return ResultUtil.success(damagedInforVo);
			}else {
				logger.error("id:"+id+"不在残损类型记录表'damaged_type'中！");
				return ResultUtil.error(1, "id:"+id+"不在残损类型记录表'damaged_type'中！");
			}		
		}else {
			logger.error("id:"+id+"不能为空并且大于0！");
			return ResultUtil.error(1, "id:"+id+"不能为空并且大于0！");
		}
	}
	
	/**
	 * 新增或更新残损数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增或更新残损数据", notes="新增或更新残损数据")
	@ApiParam(name = "DamagedInforVo", value = "残损类型JSON数据")
	@PostMapping("/insertDamagedInfor")
	@RequiresPermissions(value = {"insertDamagedInfor"})
	public Result<Object> insertDamagedInfor(@RequestBody DamagedTypeVo damagedInfor) {
		if(damagedInfor.getId() >0) {
			//更新
			int count = damagedInforService.updateDamagedInfor(damagedInfor);
			if(count >0) {
				return ResultUtil.success("id: "+damagedInfor.getId());
			}else {
				logger.error("更新一条残损类型数据失败！");
				logger.error(JsonUtil.javaToJsonStr(damagedInfor));
				return ResultUtil.error(1, "更新一条残损类型数据失败！");
			}
		}else {
			//新增
			int count = damagedInforService.insertDamagedInfor(damagedInfor);
			if(count >0) {
				return ResultUtil.success("id: "+damagedInfor.getId());
			}else {
				logger.error("新增一条残损类型数据失败！");
				logger.error(JsonUtil.javaToJsonStr(damagedInfor));
				return ResultUtil.error(1, "新增一条残损类型数据失败！");
			}
		}
	}
	
	/**
	 * 删除残损数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除残损数据", notes="删除残损数据")
	@ApiParam(name = "id", value = "残损类型记录id")
	@DeleteMapping("/deleteCraneInfo")
	@RequiresPermissions(value = {"deleteCraneInfo"})
	public Result<Object> deleteCraneInfo(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			int count = damagedInforService.deleteDamagedInfor(id);
			if(count >0) {
				return ResultUtil.success("id: "+id);
			}else {
				logger.error("删除残损类型记录失败！");
				return ResultUtil.error(1, "删除残损类型记录失败！");
			}
		}else {
			return ResultUtil.error(1, "id:"+id+"不能为空并且大于0！");
		}
	}
	/**
	 * 查询残损类型下拉列表
	 * 下拉列表框
	 * @return
	 */
	@ApiOperation(value="查询残损类型下拉列表", notes="查询残损类型下拉列表")
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	@GetMapping("/getDamagedInforSelect")
	@RequiresPermissions(value = {"getDamagedInforSelect"})
	public Result<Object> getDamagedInforSelect() {
		return ResultUtil.success(damagedInforService.getDamagedInforSelect());
	}
	/**
	 * 查询残损位置下拉列表
	 * @return
	 */
	@ApiOperation(value="查询残损位置下拉列表", notes="查询残损位置下拉列表")
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	@GetMapping("/getDamagedPositionSelect")
	@RequiresPermissions(value = {"getDamagedPositionSelect"})
	public Result<Object> getDamagedPositionSelect() {
		return ResultUtil.success(damagedInforService.getDamagedPositionSelect());
	}
}
