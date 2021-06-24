package net.pingfang.service.qhdPdf;

import net.pingfang.entity.work.WorkRecordVo;

/**
 * 秦皇岛交接单
 * @author Administrator
 *
 */
public interface ReceiptTxtService {

	public String receiptTxt(WorkRecordVo workRecordVo);
}
