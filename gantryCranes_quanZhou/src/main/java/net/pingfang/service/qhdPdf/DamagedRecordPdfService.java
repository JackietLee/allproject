package net.pingfang.service.qhdPdf;

import net.pingfang.entity.work.WorkRecordVo;
/**
 * 残损记录
 * @author Administrator
 *
 */
public interface DamagedRecordPdfService {

	
	public String pdfReport(WorkRecordVo workRecordVo);

}
