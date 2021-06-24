package net.pingfang.controller.workRecord;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.work.WorkRecordManualVo;
import net.pingfang.handler.Result;
import net.pingfang.service.workRecord.WorkRecordManualService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

@Api("WorkRecord Manual Api")
@RestController
@RequestMapping("/workRecordManual")
public class WorkRecordManualController {
	
	@Autowired
	private WorkRecordManualService workRecordManualService;
	
	private final static Logger logger = LoggerFactory.getLogger(WorkRecordManualController.class);
	
	/**
	 * 作业识别率统计
	 * @param workRecordManual
	 * @return
	 */
	@ApiOperation(value="作业识别率统计", notes="作业识别率统计")
	@ApiParam(name = "workRecordManual", value = "作业信息JSON数据")
	@PostMapping("/identificationRateStatistics")
	@RequiresPermissions(value = {"identificationRateStatistics"})
	public Result<Object> identificationRateStatistics(@RequestBody WorkRecordManualVo workRecordManual){
		return ResultUtil.success(workRecordManualService.identificationRateStatistics(workRecordManual));
	}
	
	/**
	 * 插入一条作业记录
	 * 先查询数据是否存在，如果存在则更新，否则新增
	 * @param workRecordManual
	 * @return
	 */
	@ApiOperation(value="插入人工理货数据", notes="插入人工理货数据")
	@ApiParam(name = "workRecordManual", value = "作业信息JSON数据")
	@PostMapping("/insertWorkRecordManual")
	@RequiresPermissions(value = {"insertWorkRecordManual"})
	public Result<Object> insertWorkRecordManual(@RequestBody WorkRecordManualVo workRecordManual) {
		Result<Object> result = ResultUtil.success();
		//先查询数据是否存在，如果存在则更新，否则新增
		int count = workRecordManualService.getWrmExists(workRecordManual);
		int updateCount = 0;
		String msg = null;
		if(count >0) {
			updateCount =  workRecordManualService.updateWorkRecordManual(workRecordManual);
			msg = "更新";
		}else {
			updateCount =  workRecordManualService.insertWorkRecordManual(workRecordManual);
			msg = "新增";
		}	
		if(updateCount >0) {
			result.setMsg(msg +"成功！");						
		}else{
			result.setMsg(msg +"失败！");	
			result.setCode(1);
			logger.error(msg +"失败！");
			logger.error(JsonUtil.javaToJsonStr(workRecordManual));
		}
		return result;
	}

}
