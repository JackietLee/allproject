package net.pingfang.controller.vessel;

import org.apache.shiro.authz.annotation.RequiresPermissions;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.BerthPlanService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 泊位计划信息Controller
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Api("BerthPlan Api")
@RestController
@RequestMapping("/vessel")
public class BerthPlanController {

	@Autowired
	private BerthPlanService berthPlanService;
	
	private final static Logger logger = LoggerFactory.getLogger(BerthPlanController.class);
	/**
	 * 分页查询泊位计划数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
//	@ApiOperation(value="分页查询泊位计划数据", notes="分页查询泊位计划数据")
//	@ApiParam(name = "workRecordVo", value = "泊位计划JSON数据")
//	@RequiresPermissions(value = {"getPageBerthPlanInfoList"})
//	@RequestMapping(value = "getPageBerthPlanInfoList", method = RequestMethod.PUT)
//	public Result<Object> getPageBerthPlanInfoList(@RequestBody BerthPlanInfoVo berthPlanInfoVo) {
//		return ResultUtil.success(berthPlanService.getPageBerthPlanInfoList(berthPlanInfoVo));
//
//	}
	
	/**
	 * 新增船泊信息数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增船泊信息数据", notes="新增船泊信息数据")
	@ApiParam(name = "BerthPlanInfoVo", value = "船泊信息JSON数据")
	@PostMapping("/insertBerthPlan")
	//@RequiresPermissions(value = {"insertBerthPlan"})
	public Result<Object> insertBerthPlan(@RequestBody BerthPlanInfoVo berthPlanInfo) {
		//新增
		int count = berthPlanService.insertBerthPlan(berthPlanInfo);
		if(count >0) {
			return ResultUtil.success("id: "+berthPlanInfo.getId());
		}else {
			logger.error("新增船泊信息失败！");
			logger.error(JsonUtil.javaToJsonStr(berthPlanInfo));
			return ResultUtil.error(1, "新增船泊信息失败！");
		}
	}
	
	/**
	 * 查询泊位计划详情
	 * @param 查询条件 id
	 * @return
	 */
//	@ApiOperation(value="查询泊位计划详情", notes="查询泊位计划详情")
//	@ApiParam(name = "id", value = "泊位计划id")
//	@GetMapping("/getBerthPlanInfoById")
//	@RequiresPermissions(value = {"getBerthPlanInfoById"})
//	public Result<Object> getBerthPlanInfoById(String vesselVoyageNumber) {
//		if(null !=vesselVoyageNumber) {
//			BerthPlanInfoVo berthPlanInfo = berthPlanService.getBerthPlanInfoById(vesselVoyageNumber);			
//			if(null != berthPlanInfo) {
//				return ResultUtil.success(berthPlanInfo);
//			}else {
//				logger.error("泊位计划vesselVoyageNumber:"+vesselVoyageNumber+"在数据库里不存在！");
//				return ResultUtil.error(1, "泊位计划vesselVoyageNumber:"+vesselVoyageNumber+"在数据库里不存在！");
//			}			
//		}else {
//			logger.error("泊位计划id不能为空并且大于0！");
//			return ResultUtil.error(1, "泊位计划id不能为空并且大于0！");
//		}
//	}
	/**
	 * 新增或更新一条泊位计划信息
	 * @param berthPlanInfo
	 * @return
	 */
	/*@ApiOperation(value="新增一条泊位计划数据", notes="新增一条泊位计划数据")
	@ApiParam(name = "berthPlanInfoVo", value = "泊位计划JSON数据")
	@PostMapping("/insertBerthPlanInfo")
	public Result<Object> insertBerthPlanInfo(@RequestBody BerthPlanInfoVo berthPlanInfoVo) {
		if(berthPlanInfoVo.getId() >0) {
			//更新
			int count = berthPlanService.updateBerthPlanInfo(berthPlanInfoVo);
			if(count >0) {
				return ResultUtil.success("id: "+berthPlanInfoVo.getId());
			}else {
				logger.error("更新泊位计划信息失败！");
				logger.error(JsonUtil.javaToJsonStr(berthPlanInfoVo));
				return ResultUtil.error(1, "更新泊位计划信息失败！");
			}
		}else {
			//新增
			int count = berthPlanService.insertBerthPlanInfo(berthPlanInfoVo);
			if(count >0) {
				return ResultUtil.success("id: "+berthPlanInfoVo.getId());
			}else {
				logger.error("新增泊位计划信息失败！");
				logger.error(JsonUtil.javaToJsonStr(berthPlanInfoVo));
				return ResultUtil.error(1, "新增泊位计划信息失败！");
			}
		}
	}*/
	
	/**
	 * 更新船舷方向
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新船舷方向", notes="更新船舷方向")
	@ApiParam(name = "berthPlanInfoVo", value = "泊位计划JSON数据")
	@PostMapping("/updateAlongside")
	public Result<Object> updateAlongside(@RequestBody BerthPlanInfoVo berthPlanInfoVo) {
		if(null !=berthPlanInfoVo && null !=berthPlanInfoVo.getVesselNumber()) {
			//更新
			int count = berthPlanService.updateAlongside(berthPlanInfoVo);
			if(count >0) {
				return ResultUtil.success("id: "+berthPlanInfoVo.getId());
			}else {
				logger.error("更新船舷方向失败！");
				logger.error(JsonUtil.javaToJsonStr(berthPlanInfoVo));
				return ResultUtil.error(1, "更新船舷方向失败！");
			}
		}else {
			return ResultUtil.error(1, "参数不正确！");
		}
	}
	/**
	 * 删除泊位计划数据
	 * @param berthPlanInfo
	 * @return
	 */
	/*@ApiOperation(value="删除泊位计划数据", notes="删除泊位计划数据")
	@ApiParam(name = "id", value = "泊位计划id")
	@DeleteMapping("/deleteBerthPlanInfoById")
	@RequiresPermissions(value = {"deleteBerthPlanInfoById"})
	public Result<Object> deleteBerthPlanInfoById(@RequestParam("id") int id) {
		int count = berthPlanService.deleteBerthPlanInfoById(id);
		if(count >0) {
			return ResultUtil.success("id: "+id);
		}else {
			logger.error("删除泊位计划信息失败！");
			return ResultUtil.error(1, "删除泊位计划信息失败！");
		}
	}*/
	/**
	 * 查询船下拉联想框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@ApiOperation(value="查询船下拉联想框", notes="查询船下拉联想框")
//	@ApiParam(name = "vesselNameCn", value = "船名")
//	@GetMapping("/getBerthPlanListBox")
//	@RequiresPermissions(value = {"getBerthPlanListBox"})
//	public Result<Object> getBerthPlanListBox(@RequestParam("vesselNameCn") String vesselNameCn) {
//		BerthPlanInfoVo berthPlanInfoVo = new BerthPlanInfoVo();
//		berthPlanInfoVo.setVesselNameCn(vesselNameCn);
//		return ResultUtil.success(berthPlanService.getBerthPlanListBox(berthPlanInfoVo));
//
//	}
	/**
	 * export Excel所有泊位计划数据
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
//	@ApiOperation(value="export Excel所有泊位计划数据", notes="export Excel所有泊位计划数据")
//	@ApiParam(name = "BerthPlanInfoVo", value = "泊位计划信息JSON数据")
//	@RequestMapping(value = "exportExcelBerthPlanInfo", method = RequestMethod.GET)
//	@RequiresPermissions(value = {"exportExcelBerthPlanInfo"})
//	public void exportExcelBerthPlanInfo(HttpServletResponse response, BerthPlanInfoVo berthPlanInfoVo) throws UnsupportedEncodingException {
//		//设置Http响应头告诉浏览器下载这个附件
//		response.setContentType("application/vnd..ms-excel");
//        response.setHeader("content-Disposition","attachment;filename="+URLEncoder.encode("泊位计划" + System.currentTimeMillis() + ".xls","utf-8"));
//        berthPlanService.exportExcelBerthPlanInfo(response, berthPlanInfoVo);
//	}
	
}
