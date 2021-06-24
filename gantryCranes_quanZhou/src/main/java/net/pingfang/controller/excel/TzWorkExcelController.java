package net.pingfang.controller.excel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.excel.TzWorkExcelService;

/**
 * 泰州作业数据EXCEL导出
 * @author Administrator
 *
 */
@Api("WorkRecord Api")
@RestController
@RequestMapping("/tzWorkExcel")
public class TzWorkExcelController {
	@Autowired
	private TzWorkExcelService tzWorkExcelService;

	/**
	 * exportExcel集装箱残损单
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value="Export Excel集装箱残损单", notes="Export Excel集装箱残损单")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@RequestMapping(value = "exportExcelTzDamageInforRecord", method = RequestMethod.GET)
//	@RequiresPermissions(value = {"exportExcelTzDamageInforRecord"})
	public void exportExcelTzDamageInforRecord(HttpServletResponse response, WorkRecordVo workRecordVo) throws UnsupportedEncodingException {
		//设置Http响应头告诉浏览器下载这个附件
		response.setContentType("application/vnd..ms-excel");
        response.setHeader("content-Disposition","attachment;filename="+URLEncoder.encode("集装箱残损单" + System.currentTimeMillis() + ".xls","utf-8"));
        tzWorkExcelService.exportExcelTzDamageInforRecord(response, workRecordVo);
	}
	
	/**
	 * exportExcel Bay位数据
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value="Export Bay位数据", notes="Export ExcelBay位数据")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@RequestMapping(value = "exportExcelTzBayInfo", method = RequestMethod.GET)
//	@RequiresPermissions(value = {"exportExcelTzBayInfo"})
	public void exportExcelTzBayInfo(HttpServletResponse response, WorkRecordVo workRecordVo) throws UnsupportedEncodingException {
		//设置Http响应头告诉浏览器下载这个附件
		response.setContentType("application/vnd..ms-excel");
        response.setHeader("content-Disposition","attachment;filename="+URLEncoder.encode("Bay位数据" + System.currentTimeMillis() + ".xls","utf-8"));
        tzWorkExcelService.exportExcelTzBayInfo(response, workRecordVo);
	}
	
	/**
	 * exportExcel 作业箱量统计
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value="Export 作业箱量统计", notes="Export 作业箱量统计")
	@ApiParam(name = "workJson", value = "作业信息JSON数据")
	@RequestMapping(value = "exportExcelContainerStatistics", method = RequestMethod.GET)
//	@RequiresPermissions(value = {"exportExcelContainerStatistics"})
	public void exportExcelContainerStatistics(HttpServletResponse response, WorkRecordVo workRecordVo) throws UnsupportedEncodingException {
		//设置Http响应头告诉浏览器下载这个附件
		response.setContentType("application/vnd..ms-excel");
        response.setHeader("content-Disposition","attachment;filename="+URLEncoder.encode("作业箱量统计" + System.currentTimeMillis() + ".xls","utf-8"));
        tzWorkExcelService.exportExcelContainerStatistics(response, workRecordVo);
	}
	
}
