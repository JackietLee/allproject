package net.pingfang.controller.vessel;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselBayService;
import net.pingfang.utils.ResultUtil;

/**
 * 船舶Bay信息Controller
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Api("VesselBay Api")
@RestController
@RequestMapping("/vessel")
public class VesselBayController {

	@Autowired
	private VesselBayService vesselBayService;
	
//	private final static Logger logger = LoggerFactory.getLogger(VesselBayController.class);
	
	
	/**
	 *查询岸桥下拉列表数据
	 *下拉列表框
	 * @return
	 */
	@ApiOperation(value="查询岸桥下拉列表数据", notes="查询岸桥下拉列表数据")
	@GetMapping("/getCraneInfoList")
	@RequiresPermissions(value = {"getCraneInfoList"})
	public Result<Object> getCraneInfoList() {
		return ResultUtil.success(vesselBayService.getCraneInfoList());
	}
	/**
	 *查询等待作业的船舶数据
	 * 0是表示等待作业，1是作业已完成("is_finished = 0")
	 * @return
	 */
	@ApiOperation(value="查询等待作业的船舶数据", notes="查询等待作业的船舶数据")
	@ApiParam(name = "BerthPlanInfoVo", value = "船舶查询JSON数据")
	@GetMapping("/getIsFinishedBerthPlanList")
	@RequiresPermissions(value = {"getIsFinishedBerthPlanList"})
	public Result<Object> getIsFinishedBerthPlanList(BerthPlanInfoVo berthPlanInfoVo) {
		return ResultUtil.success(vesselBayService.getIsFinishedBerthPlanList(berthPlanInfoVo));
	}
	/**
	 * 查询船舶Bay下拉列表
	 * 根据船舶代码VesselCode获取船舶Bay下拉列表
	 * @return
	 */
	@ApiOperation(value="查询船舶Bay下拉列表", notes="查询船舶Bay下拉列表")
	@ApiParam(name = "vesselCode", value = "船舶代码")
	@GetMapping("/getBayListByVesselCode")
	@RequiresPermissions(value = {"getBayListByVesselCode"})
	public Result<Object> getBayListByVesselCode(String vesselCode) {
		return ResultUtil.success(vesselBayService.getBayListByVesselCode(vesselCode));
	}
	/**
	 * 查询船图结构数据
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@ApiOperation(value="查询船图结构数据", notes="查询船图结构数据")
	@ApiParam(name = "vesselBayList", value = "船舶Bay JSON数据")
	@PutMapping("/getVesselBayList")
	@RequiresPermissions(value = {"getVesselBayList"})
	public Result<Object> getVesselBayList(@RequestBody List<VesselBayVo> vesselBayList) {
		return ResultUtil.success(vesselBayService.getVesselBayMap(vesselBayList));
	}
	/**
	 * 查询船图结构数据new
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@ApiOperation(value="查询船图结构数据new", notes="查询船图结构数据new")
	@ApiParam(name = "vesselBayList", value = "船舶Bay JSON数据")
	@PutMapping("/getVesselBayMapNew")
	@RequiresPermissions(value = {"getVesselBayMapNew"})
	public Result<Object> getVesselBayMapNew(@RequestBody List<VesselBayVo> vesselBayList) {
		return ResultUtil.success(vesselBayService.getVesselBayMapNew(vesselBayList));
	}
}
