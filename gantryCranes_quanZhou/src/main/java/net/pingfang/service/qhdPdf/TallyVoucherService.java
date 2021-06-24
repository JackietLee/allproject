package net.pingfang.service.qhdPdf;

import net.pingfang.entity.work.WorkRecordVo;

/**
 * 理货业务凭证
 * @author Administrator
 *
 */
public interface TallyVoucherService {
	public String pdfReport(WorkRecordVo workRecordVo);
}
