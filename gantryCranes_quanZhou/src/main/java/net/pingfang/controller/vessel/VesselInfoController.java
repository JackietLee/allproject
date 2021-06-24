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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.VesselInfoVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselInfoService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 *船舶信息Controller
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Api("VesselInfo Api")
@RestController
@RequestMapping("/vessel")
public class VesselInfoController {

	@Autowired
	private VesselInfoService vesselInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(VesselInfoController.class);
	/**
	 * 分页查询船舶数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
	@ApiOperation(value="分页查询船舶数据", notes="分页查询船舶数据")
	@ApiParam(name = "workRecordVo", value = "泊位计划JSON数据")
	@RequestMapping(value = "getPageVesselInfoList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getPageVesselInfoList"})
	public Result<Object> getPageVesselInfoList(@RequestBody VesselInfoVo vesselInfo) {
		return ResultUtil.success(vesselInfoService.getPageVesselInfoList(vesselInfo));

	}
	/**
	 * 查询船舶数据详情
	 * @param 查询条件 id
	 * @return
	 */
	@ApiOperation(value="查询船舶数据详情", notes="查询船舶数据详情")
	@ApiParam(name = "id", value = "船舶id")
	@GetMapping("/getVesselInfoById")
	@RequiresPermissions(value = {"getVesselInfoById"})
	public Result<Object> getVesselInfoById(Integer id) {
		if(null !=id && id>0) {
			VesselInfoVo vesselInfo = vesselInfoService.getVesselInfoById(id);			
			if(null != vesselInfo) {
				return ResultUtil.success(vesselInfo);
			}else {
				logger.error("船舶id:"+id+"在数据库里不存在！");
				return ResultUtil.error(1, "船舶id:"+id+"在数据库里不存在！");
			}			
		}else {
			logger.error("船舶id不能为空并且大于0！");
			return ResultUtil.error(1, "船舶id不能为空并且大于0！");
		}
	}
	/**
	 * 新增或更新船舶数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增或更新船舶数据", notes="新增或更新船舶数据")
	@ApiParam(name = "vesselInfo", value = "泊位计划JSON数据")
	@PostMapping("/insertVesselInfo")
	@RequiresPermissions(value = {"insertVesselInfo"})
	public Result<Object> insertVesselInfo(@RequestBody VesselInfoVo vesselInfo) {
		if(vesselInfo.getId() >0) {
			//更新
			int count = vesselInfoService.updateVesselInfo(vesselInfo);
			if(count >0) {
				return ResultUtil.success("id: "+vesselInfo.getId());
			}else {
				logger.error("更新船舶信息失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselInfo));
				return ResultUtil.error(1, "更新船舶信息失败！");
			}
		}else {
			//新增
			int count = vesselInfoService.insertVesselInfo(vesselInfo);
			if(count >0) {
				return ResultUtil.success("id: "+vesselInfo.getId());
			}else {
				logger.error("新增船舶信息失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselInfo));
				return ResultUtil.error(1, "新增船舶信息失败！");
			}
		}
	}
	/**
	 * 删除船舶数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="删除船舶数据", notes="删除船舶数据")
	@ApiParam(name = "id", value = "泊位计划id")
	@DeleteMapping("/deleteVesselInfoById")
	@RequiresPermissions(value = {"deleteVesselInfoById"})
	public Result<Object> deleteVesselInfoById(int id) {
		int count = vesselInfoService.deleteVesselInfoById(id);
		if(count >0) {
			return ResultUtil.success("id: "+id);
		}else {
			logger.error("删除船舶信息失败！");
			return ResultUtil.error(1, "删除船舶信息失败！");
		}
	}
}
