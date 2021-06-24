package net.pingfang.service.excel;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import net.pingfang.entity.work.WorkRecordVo;

public interface TzWorkExcelService {

	/**
	 * exportExcel集装箱残损单
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public void exportExcelTzDamageInforRecord(HttpServletResponse response, WorkRecordVo workRecordVo);
	
	/**
	 * exportExcel Bay位数据
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public void exportExcelTzBayInfo(HttpServletResponse response, WorkRecordVo workRecordVo);
	
	/**
	 * exportExcel 作业箱量统计
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public void exportExcelContainerStatistics(HttpServletResponse response, WorkRecordVo workRecordVo);
}
