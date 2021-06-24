package net.pingfang.service.qhdPdf;

import net.pingfang.entity.work.WorkRecordVo;

public interface TallyResultsPdfService {

	public String pdfReport(WorkRecordVo workRecordVo);
}
