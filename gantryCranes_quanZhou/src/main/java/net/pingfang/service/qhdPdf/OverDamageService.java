package net.pingfang.service.qhdPdf;

import net.pingfang.entity.work.WorkRecordVo;

/**
 * 溢短残损单
 * @author Administrator
 *
 */
public interface OverDamageService {

	public String pdfReport(WorkRecordVo workRecordVo);
}
