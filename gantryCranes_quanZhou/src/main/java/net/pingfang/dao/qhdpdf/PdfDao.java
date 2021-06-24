package net.pingfang.dao.qhdpdf;

import java.util.List;

import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface PdfDao {
	/**
	 * 获取残损数据导出PDF
	 * @return
	 */
	public List<DamageInforRecordVo> getQhdDamageInforRecordList(WorkRecordVo workRecordVo);
	public List<ImgInfoVo> getQhdImgInfoVo(List<String> list);
	
	/**
	 * 秦皇岛交接单导出
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getReceiptTxt(WorkRecordVo workRecordVo);

}
