package net.pingfang.service.workRecord;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import net.pingfang.entity.work.WorkRecordVo;

@Service
public interface WorkRecordExportExcelService {
	
	/**
	 * 综合查询导出
	 * @param response
	 * @param workRecordVo
	 */
	public void exportExcelWorkRecord(HttpServletResponse response, WorkRecordVo workRecordVo);
	
	/**
	 * （新增）黄埔门机综合查询导出
	 * @param response
	 * @param workRecordVo
	 */
	public void exportExcelHpWorkRecord(HttpServletResponse response,WorkRecordVo workRecordVo);
}
