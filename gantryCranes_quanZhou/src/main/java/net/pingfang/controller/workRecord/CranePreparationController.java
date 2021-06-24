package net.pingfang.controller.workRecord;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.handler.Result;
import net.pingfang.service.workRecord.CranePreparationService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 岸桥配制Controller
 * @author Administrator
 * @since 2019-05-22
 *
 */
@Api("CranePreparation Api")
@RestController
@RequestMapping("/crane")
public class CranePreparationController {
	@Autowired
	private CranePreparationService cranePreparationService;
	private final static Logger logger = LoggerFactory.getLogger(CranePreparationController.class); 
	
	/**
	 * 查询岸桥配制列表数据
	 * @return
	 */
	@ApiOperation(value="查询岸桥配制列表数据", notes="查询岸桥配制列表数据")
	@RequestMapping(value = "getAllCranePreparationList", method = RequestMethod.GET)
	@RequiresPermissions(value = {"getAllCranePreparationList"})
	public Result<Object> getAllCranePreparationList() {
		return ResultUtil.success(ResultUtil.success(cranePreparationService.getAllCranePreparationList()));

	}
	
	/**
	 * 新增岸桥配制数据
	 * @param cranePreparationVo
	 * @return
	 */
	@ApiOperation(value="新增岸桥配制数据", notes="新增岸桥配制数据")
	@ApiParam(name = "CranePreparationVo", value = "岸桥配制JSON数据")
	@PostMapping("/insertCranePreparation")
	@RequiresPermissions(value = {"insertCranePreparation"})
	public Result<Object> insertCranePreparation(@RequestBody CranePreparationVo cranePreparationVo) {
		int count = 0;
		if(null !=cranePreparationVo) {
			count = cranePreparationService.getCountCranePreparationByCraneNum(cranePreparationVo.getCraneNum());
			if(count >0) {
				logger.error("新增的岸桥编号"+cranePreparationVo.getCraneNum()+"已经被使用！");
				logger.error(JsonUtil.javaToJsonStr(cranePreparationVo));
				return ResultUtil.error(1, "新增的岸桥编号"+cranePreparationVo.getCraneNum()+"已经被使用！");
			}else {
				//插入一条岸桥配制信息
				count = cranePreparationService.insertCranePreparation(cranePreparationVo);
				if(count >0) {
					return ResultUtil.success(cranePreparationVo.getId());
				}else {
					logger.error(cranePreparationVo.getVesselNameEn()+"新增岸桥配制数据失败！");
					logger.error(JsonUtil.javaToJsonStr(cranePreparationVo));
					return ResultUtil.error(1, cranePreparationVo.getVesselNameEn()+"新增岸桥配制数据失败!");
				}
			}
		}else{
			logger.error("CranePreparationVo入参解析出错！");
			return ResultUtil.error(1, "CranePreparationVo入参解析出错！");
		}
	}
	
	/**
	 * 删除岸桥配制数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除岸桥配制数据", notes="删除岸桥配制数据")
	@ApiParam(name = "CranePreparationVo", value = "岸桥配制JSON")
	@PutMapping("/deleteCranePreparation")
	@RequiresPermissions(value = {"deleteCranePreparation"})
	public Result<Object> deleteCranePreparation(@RequestBody CranePreparationVo cranePreparationVo) {
		//int count = cranePreparationService.deleteCranePreparation(id);
		cranePreparationVo.setType("1");
		int count = cranePreparationService.deleteCranePreparationByCraneNum(cranePreparationVo);
		if(count >0) {
			return ResultUtil.success(cranePreparationVo.getCraneNum());
		}else {
			logger.error(cranePreparationVo.getCraneNum()+"删除岸桥配制数据失败！");
			return ResultUtil.error(1, cranePreparationVo.getCraneNum()+"删除岸桥配制数据失败！");
		}
	}
	/**
	 * 查询所有岸桥 下拉列表
	 * 下拉列表框
	 * @return
	 */
	@ApiOperation(value="查询所有岸桥 下拉列表", notes="查询所有岸桥 下拉列表")
	@GetMapping("/getCraneInfoList")
	@RequiresPermissions(value = {"getCraneInfoList"})
	public Result<Object> getCraneInfoList() {
		return ResultUtil.success(cranePreparationService.getCraneInfoList());
	}
	
	/**
	 * 查询没被引用的岸桥 下拉列表 
	 * 下拉列表框
	 * @return
	 */
	@ApiOperation(value="查询没被引用岸桥 的下拉列表 ", notes="查询没被引用岸桥 的下拉列表 ")
	@GetMapping("/getNotUsedCraneInfoList")
	@RequiresPermissions(value = {"getNotUsedCraneInfoList"})
	public Result<Object> getNotUsedCraneInfoList() {
		return ResultUtil.success(cranePreparationService.getNotUsedCraneInfoList());
	}
	/**
	 * 根据岸桥编号更新Bay
	 * @return
	 */
	@ApiOperation(value="根据岸桥编号更新Bay", notes="根据岸桥编号更新Bay ")
	@PostMapping("/updateBayByCraneNum")
	@RequiresPermissions(value = {"updateBayByCraneNum"})
	public Result<Object> updateBayByCraneNum(@RequestBody CranePreparationVo cranePreparationVo) {
		int count = cranePreparationService.updateBayByCraneNum(cranePreparationVo);
		if(count > 0) {
			return ResultUtil.success(cranePreparationService.getNotUsedCraneInfoList());
		}else {
			logger.error("根据岸桥编号更新Bay失败！");
			return ResultUtil.error(1, "根据岸桥编号更新Bay失败！");
		}		
	}
	
	/**
	 * 调用第三方接口同步数据
	 * 亮哥的接口
	 * @return
	 */
	@ApiOperation(value="调用第三方接口同步数据", notes="调用第三方接口同步数据")
	@GetMapping("/berthPlansSynchronization")
	@RequiresPermissions(value = {"berthPlansSynchronization"})
	public Result<Object> berthPlansSynchronization() {
		boolean isOk = cranePreparationService.berthPlansSynchronization();
		if(isOk) {
			return ResultUtil.success(isOk);
		}else {
			logger.error("调用第三方接口同步数据失败！");
			return ResultUtil.error(1, "调用第三方接口同步数据失败！");
		}		
	}
	
	/**
	 * 更新BayWidth
	 * @param id
	 * @return
	 */
	@ApiOperation(value="更新BayWidth", notes="更新BayWidth")
	@ApiParam(name = "CranePreparationVo", value = "岸桥配制JSON")
	@PutMapping("/updateBayWidth")
	@RequiresPermissions(value = {"updateBayWidth"})
	public Result<Object> updateBayWidth(@RequestBody CranePreparationVo cranePreparationVo) {
		int count = cranePreparationService.updateBayWidth(cranePreparationVo);
		if(count >0) {
			return ResultUtil.success(cranePreparationVo.getCraneNum());
		}else {
			logger.error(cranePreparationVo.getCraneNum()+"更新BayWidth失败！");
			return ResultUtil.error(1, cranePreparationVo.getCraneNum()+"更新BayWidth失败！");
		}
	}
}
