package net.pingfang.controller.huangPu.bay;

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
import net.pingfang.service.huangPu.bay.MjBayService;
import net.pingfang.utils.ResultUtil;
/**
 * Bay图批量处理
 * @author caoguofeng
 *
 */
@Api("HandlerBayList Api")
@RestController
@RequestMapping("/mjBay")
public class MjBayController {
	
	@Autowired
	private MjBayService mjBayService;
	
	private final static Logger logger = LoggerFactory.getLogger(MjBayController.class);
	
	
	
	/**
	 * 查询门机船图结构数据
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@ApiOperation(value="查询门机船图结构数据", notes="查询门机船图结构数据")
	@ApiParam(name = "getBatchMjBayList", value = "门机船舶Bay JSON数据")
	@PutMapping("/getBatchMjBayList")
	@RequiresPermissions(value = {"getBatchMjBayList"})
	public Result<Object> getBatchBayList(@RequestBody List<VesselBayVo> vesselBayList) {
		return ResultUtil.success(mjBayService.getBatchMjBayList(vesselBayList));
	}
	
	/**
	 * 批量查询门机BAY图数据
	 * @param vesselContainerVo
	 * @return
	 */
	@ApiOperation(value="批量查询门机BAY图数据", notes="批量查询门机BAY图数据")
	@ApiParam(name = "getBatchBayInfoList", value = "船舶集装箱JSON数据")
	@PutMapping("/getBatchBayInfoList")
	@RequiresPermissions(value = {"getBayInfoList"})
	public Result<Object> getBatchBayInfoList(@RequestBody List<VesselContainerVo> vesselContainerList) {
		if(null !=vesselContainerList && vesselContainerList.size() >0) {
			Map<String,Object> map = mjBayService.getBatchMjBayInfoList(vesselContainerList);
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
