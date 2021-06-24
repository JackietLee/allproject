package net.pingfang.controller.bay;

import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.handler.Result;
import net.pingfang.service.bay.HandlerBayListService;
import net.pingfang.utils.ResultUtil;
/**
 * Bay图批量处理
 * @author caoguofeng
 *
 */
@Api("HandlerBayList Api")
@RestController
@RequestMapping("/bay")
public class HandlerBayListController {
	
	@Autowired
	private HandlerBayListService handlerBayListService;
	
	private final static Logger logger = LoggerFactory.getLogger(HandlerBayListController.class);
	
	
	
	/**
	 * 查询船图结构数据
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@ApiOperation(value="查询船图结构数据", notes="查询船图结构数据")
	@ApiParam(name = "getBatchBayList", value = "船舶Bay JSON数据")
	@PutMapping("/getBatchBayList")
	@RequiresPermissions(value = {"getBatchBayList"})
	public Result<Object> getBatchBayList(@RequestBody List<VesselBayVo> vesselBayList) {
		return ResultUtil.success(handlerBayListService.getBatchBayList(vesselBayList));
	}
	
	/**
	 * 批量查询BAY图数据
	 * @param vesselContainerVo
	 * @return
	 */
	@ApiOperation(value="批量查询BAY图数据", notes="批量查询BAY图数据")
	@ApiParam(name = "getBatchBayInfoList", value = "船舶集装箱JSON数据")
	@PutMapping("/getBatchBayInfoList")
	@RequiresPermissions(value = {"getBayInfoList"})
	public Result<Object> getBatchBayInfoList(@RequestBody List<VesselContainerVo> vesselContainerList) {
		if(null !=vesselContainerList && vesselContainerList.size() >0) {
			Map<String,Object> map = handlerBayListService.getBatchBayInfoList(vesselContainerList);
			if(!map.isEmpty()) {
				return ResultUtil.success(map);
			}else {
				logger.error("集装箱的位置在数据库里不存在！");
				return ResultUtil.error(1, "集装箱的位置在数据库里不存在！");
			}
		}else {
			logger.error("参数不正确！");
			return ResultUtil.error(1, "参数不正确！");
		}
	}

}
