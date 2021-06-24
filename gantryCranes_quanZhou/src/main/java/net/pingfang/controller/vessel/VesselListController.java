package net.pingfang.controller.vessel;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.importFile.VesselListVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselListService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("VesselList Api")
@RestController
@RequestMapping("/vesselList")
public class VesselListController {
	@Autowired
	private VesselListService vesselListService;
	
	private final static Logger logger = LoggerFactory.getLogger(VesselListController.class);
	
	/**
	 * 分页查询船清单数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@ApiOperation(value="分页查询船清单数据", notes="分页查询船清单数据")
	@ApiParam(name = "VesselListVo", value = "船清单JSON数据")
	@RequestMapping(value = "getPageVesselList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getPageVesselList"})
	public Result<Object> getPageVesselList(@RequestBody VesselListVo vesselList) {
		return ResultUtil.success(vesselListService.getAllVesselList(vesselList));
	}
	
	/**
	 * 更新船清单数据
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="更新船清单数据", notes="更新船清单数据")
	@ApiParam(name = "VesselListVo", value = "船清单JSON数据")
	@PostMapping("/updateVesselListById")
	@RequiresPermissions(value = {"updateVesselListById"})
	public Result<Object> updateVesselListById(@RequestBody VesselListVo vesselList) {
		int count = 0;
		if(null !=vesselList) {
			count = vesselListService.updateVesselListById(vesselList);
			if(count >0) {
				return ResultUtil.success(vesselList.getId());
			}else {
				logger.error(vesselList.getId()+"更新失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselList));
				return ResultUtil.error(1, vesselList.getId()+"更新失败！");
			}
		}else{
			logger.error("VesselListVo入参解析出错！");
			return ResultUtil.error(1, "VesselListVo入参解析出错！");
		}
	}
	/**
	 * 删除船清单数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除船清单数据", notes="删除船清单数据")
	@ApiParam(name = "idList", value = "船清单数据id")
	@DeleteMapping("/deletetVesselListById")
	@RequiresPermissions(value = {"deletetVesselListById"})
	public Result<Object> deletetVesselListById(@RequestBody List<Integer> idList) {
		//删除船清单数据
		int count = vesselListService.deletetVesselListById(idList);
		if(count >0) {
			return ResultUtil.success(idList);
		}else {
			logger.error(idList+"删除船清单数据失败！");
			return ResultUtil.error(1, idList+"删除船清单数据失败！");
		}
	}

}
