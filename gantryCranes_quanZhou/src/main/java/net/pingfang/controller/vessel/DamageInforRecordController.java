package net.pingfang.controller.vessel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.role.Tuser;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.DamagedInforRecordService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 残损信息Controller
 * "damaged_infor_record"表
 * @author Administrator
 * 2019-07-29
 *
 */
@Api("DamageInforRecord Api")
@RestController
@RequestMapping("/damagedInforRecord")
public class DamageInforRecordController {

	@Autowired
	private DamagedInforRecordService damagedInforRecordService;
	
	private final static Logger logger = LoggerFactory.getLogger(DamageInforRecordController.class);
	
	/**
	 * 查询残损数据列表
	 * @return
	 */
	@ApiOperation(value="查询残损数据列表", notes="查询残损数据列表")
	@ApiParam(name = "list", value = "查询条件参数")
	@RequestMapping(value = "getDamageInforRecordList", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getDamageInforRecordList"})
	public Result<Object> getDamageInforRecordList(@RequestBody List<DamageInforRecordVo> list) {
		return ResultUtil.success(damagedInforRecordService.getWorkRecordList(list));
	}
	/**
	 * 查询残损数据详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询残损数据详情", notes="查询残损数据详情")
	@ApiParam(name = "seqNo", value = "作业编号")
	@PutMapping("/getDamageInforRecordById")
	@RequiresPermissions(value = {"getDamageInforRecordById"})
	public Result<Object> getDamageInforRecordById(@RequestBody DamageInforRecordVo damageInforRecord) {
		Map<String,Object> damagedInforVo = damagedInforRecordService.getDamageInforRecordBySeqNo(damageInforRecord);
		if(null !=damagedInforVo) {
			return ResultUtil.success(damagedInforVo);
		}else {
			logger.error("seqNo:"+damageInforRecord.getSeqNo()+"不在残损记录表'damaged_infor_record'中！");
			return ResultUtil.error(1, "seqNo:"+damageInforRecord.getSeqNo()+"不在残损记录表'damaged_infor_record'中！");
		}		
	}
	
	/**
	 * 更新残损数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="更新残损数据", notes="更新残损数据")
	@ApiParam(name = "DamageInforRecordVo", value = "残损类型JSON数据")
	@PostMapping("/updateDamagedInforRecord")
	@RequiresPermissions(value = {"updateDamagedInforRecord"})
	public Result<Object> updateDamagedInforRecord(@RequestBody DamageInforRecordVo damageInforRecord) {
		//更新
		int count = damagedInforRecordService.updateDamagedInforRecord(damageInforRecord);
		if(count >0) {
			return ResultUtil.success("id: "+damageInforRecord.getId());
		}else {
			logger.error("更新一条残损数据失败！");
			logger.error(JsonUtil.javaToJsonStr(damageInforRecord));
			return ResultUtil.error(1, "更新一条残损数据失败！");
		}
	}
	/**
	 * 新增残损数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="新增残损数据", notes="新增残损数据")
	@ApiParam(name = "DamageInforRecordVo", value = "残损JSON数据")
	@PostMapping("/insertDamagedInforRecord")
	@RequiresPermissions(value = {"insertDamagedInforRecord"})
	public Result<Object> insertDamagedInforRecord(HttpSession session, @RequestBody DamageInforRecordVo damageInforRecord) {
		int count = 0;
		String damagedType = damageInforRecord.getDamagedType();
		//查询箱号是否已经存在
		if(null !=damagedType && "index".equals(damagedType)) {
			count = damagedInforRecordService.getCountByWorkId(damageInforRecord.getWorkId());
		}
		if(count >0) {
			//更新
			count = damagedInforRecordService.updateDamagedInforRecordByWorkId(damageInforRecord);
		}else {
			Tuser currentUser = (Tuser)session.getAttribute("currentUser");
			damageInforRecord.setCreateName(currentUser.getUserName());
			//新增
			count = damagedInforRecordService.insertDamagedInforRecord(damageInforRecord);
		}
		
		if(count >0) {
			return ResultUtil.success();
		}else {
			logger.error("新增一条残损数据失败！");
			logger.error(JsonUtil.javaToJsonStr(damageInforRecord));
			return ResultUtil.error(1, "新增一条残损数据失败！");
		}
	}
	
	/**
	 * 删除残损数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除残损数据", notes="删除残损数据")
	@ApiParam(name = "id", value = "残损记录id")
	@DeleteMapping("/deleteDamagedInforRecord")
	@RequiresPermissions(value = {"deleteDamagedInforRecord"})
	public Result<Object> deleteDamagedInforRecord(@RequestParam("id") Integer id) {
		if(null !=id && id>0) {
			int count = damagedInforRecordService.deleteDamagedInforRecord(id);
			if(count >0) {
				return ResultUtil.success("id: "+id);
			}else {
				logger.error("删除残损录失败！");
				return ResultUtil.error(1, "删除残损记录失败！");
			}
		}else {
			return ResultUtil.error(1, "id:"+id+"不能为空并且大于0！");
		}
	}
	/**
	 * 查询作业箱号下拉列表
	 * 下拉列表框
	 * @return
	 */
	@ApiOperation(value="查询作业箱号下拉列表", notes="查询作业箱号下拉列表")
	@ApiParam(name = "WorkRecordVo", value = "JSON对象")
	@RequestMapping(value = "getWorkRecordSelect", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"getWorkRecordSelect"})
	public Result<Object> getWorkRecordSelect(@RequestBody WorkRecordVo workRecordVo) {
		return ResultUtil.success(damagedInforRecordService.getWorkRecordSelect(workRecordVo));
	}
	
	/**
	 * 发送残损数据到华东系统
	 * @return
	 */
	@ApiOperation(value="发送残损数据到华东系统", notes="发送残损数据到华东系统")
	@ApiParam(name = "DamageInforRecordVo", value = "JSON对象")
	@RequestMapping(value = "damageSynchronization", method = RequestMethod.PUT)
	@RequiresPermissions(value = {"damageSynchronization"})
	public Result<Object> damageSynchronization(@RequestBody DamageInforRecordVo damageInforRecord) {
		damagedInforRecordService.damageSynchronization(damageInforRecord);
		return ResultUtil.success();
	}
	
}
