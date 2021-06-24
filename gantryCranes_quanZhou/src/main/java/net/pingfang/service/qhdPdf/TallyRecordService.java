package net.pingfang.service.qhdPdf;

import net.pingfang.entity.work.WorkRecordVo;
/**
 * QHD导出理货记录EXCEL
 * @author Administrator
 *
 */
public interface TallyRecordService {

	public String exportExcelWorkRecord( WorkRecordVo workRecordVo);
}
