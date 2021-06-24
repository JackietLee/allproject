package net.pingfang.controller.vessel;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.TruckInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselContainerSeizeSeatService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 *船舶集装箱信息Controller
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Api("VesselContainer Api")
@RestController
@RequestMapping("/vessel")
public class VesselContainerController {

	@Autowired
	private VesselContainerService vesselContainerService;
	@Autowired
	private VesselContainerSeizeSeatService vesselContainerSeizeSeatService;
	private final static Logger logger = LoggerFactory.getLogger(VesselContainerController.class);
	/**
	 * 分页获查询船舶集装箱数据
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselContainerVo
	 * @return
	 */
//	@ApiOperation(value="分页获查询船舶集装箱数据", notes="分页获查询船舶集装箱数据")
//	@ApiParam(name = "VesselContainerVo", value = "船舶集装箱JSON数据")
//	@RequestMapping(value = "getPageVesselContainerList", method = RequestMethod.PUT)
//	@RequiresPermissions(value = {"getPageVesselContainerList"})
//	public Result<Object> getPageVesselContainerList(@RequestBody VesselContainerVo vesselContainer) {
//		return ResultUtil.success(vesselContainerService.getPageVesselContainerList(vesselContainer));
//
//	}
	/**
	 * 查询车顶下拉列表数据
	 * 下拉列表框
	 * @param vesselContainerList
	 * @return
	 */
	@ApiOperation(value="查询车顶下拉列表数据", notes="查询车顶下拉列表数据")
	@PutMapping("/getTruckInfoList")
	@RequiresPermissions(value = {"getTruckInfoList"})
	public Result<Object> getTruckInfoList(@RequestBody List<VesselContainerVo> vesselContainerList) {
		Map<String,List<TruckInfoVo>> map = vesselContainerService.getTruckInfoList(vesselContainerList);		
		if(null !=map && !map.isEmpty()) {
			return ResultUtil.success(map);
		}else {
			logger.error("数据库tos_truck表中暂无数据！");
			return ResultUtil.error(1, "数据库tos_truck表中暂无数据！");
		}
	}
	
	/**
	 * 查询集装箱在船上的位置数据
	 * @param vesselContainerVo
	 * @return
	 */
	@ApiOperation(value="查询装箱在船上的位置数据", notes="查询装箱在船上的位置数据")
	@ApiParam(name = "vesselContainerList", value = "船舶集装箱JSON数据")
	@PutMapping("/getVesselContainerList")
	@RequiresPermissions(value = {"getVesselContainerList"})
	public Result<Object> getVesselContainerList(@RequestBody List<VesselContainerVo> vesselContainerList) {
		if(null !=vesselContainerList && vesselContainerList.size() >0) {
			List<Object> objList = vesselContainerService.getVesselContainerList(vesselContainerList);
			if(objList.size()>0) {
				return ResultUtil.success(objList);
			}else {
				logger.error("集装箱的位置在数据库里不存在！");
				return ResultUtil.error(1, "集装箱的位置在数据库里不存在！");
			}
		}else {
			logger.error("参数不正确！");
			return ResultUtil.error(1, "参数不正确！");
		}
	}
	
	
	/**
	 * 更新贝位
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="更新贝位", notes="更新贝位")
	@ApiParam(name = "VesselContainerVo", value = "作业信息JSON数据")
	@PostMapping("/updateBayInfo")
	@RequiresPermissions(value = {"updateBayInfo"})
	public Result<Object> updateBayInfo(@RequestBody VesselContainerVo vesselContainer) {
		int count = 0;
		if(null !=vesselContainer) {
			String containerNumber = vesselContainer.getContainerNumber();
			if(null !=containerNumber && !"".equals(containerNumber) && 
					!"longTrunk".equals(containerNumber) && !"seizeSeat".equals(containerNumber)) {
				//更新箱表（" 表: tos_vessel_container"）
				count = vesselContainerService.updateBayInfo(vesselContainer);
				//调用亮哥API更新外理数据库表
				if(count >0) {
					vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
				}				
			}else {
				//更新占位箱表（" 表: tos_vessel_container_seize_seat"）
				count = vesselContainerSeizeSeatService.updateContainerSeizeSeatBayInfo(vesselContainer);
			}
			
			if(count >0) {
				return ResultUtil.success(vesselContainer.getStdBay());
			}else {
				String bayInfo = vesselContainer.getStdBay()+vesselContainer.getStdRow()+vesselContainer.getStdTier();
				logger.error(bayInfo+"BAY可能已经存在，更新贝位失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselContainer));
				return ResultUtil.error(1, bayInfo+"BAY可能已经存在，更新贝位失败！");
			}
		}else{
			logger.error("VesselContainerVo入参解析出错！");
			return ResultUtil.error(1, "VesselContainerVo入参解析出错！");
		}		
	}
	
	/**
	 * 匹配仓单（查询码头的表看这个箱是否在这条船上）
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱（只能效验单箱）
	 * @param vesselNumber
	 * @param jobType
	 * @param containerNumber  
	 * @return
	 */
	@ApiOperation(value="匹配舱单", notes="匹配舱单")
	@ApiParam(name = "VesselContainerVo", value = "船舶集装箱JSON数据")
	@PostMapping("/booleanVesselContainer")
	public Result<Object> booleanVesselContainer(@RequestBody VesselContainerVo vesselContainer) {
		if(null !=vesselContainer) {
			boolean isOk = vesselContainerService.booleanVesselContainer(vesselContainer);
			if(isOk) {
				return ResultUtil.success(isOk);
			}else {
				return ResultUtil.error(1, "匹配舱单失败！");
			}
						
		}else {
			logger.error("参数不正确！");
			return ResultUtil.error(1, "参数不正确！");
		}
	}
	
	
}
