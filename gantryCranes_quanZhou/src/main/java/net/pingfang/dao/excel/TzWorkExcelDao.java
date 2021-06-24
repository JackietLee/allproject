package net.pingfang.dao.excel;

import java.io.UnsupportedEncodingException;
import java.util.List;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface TzWorkExcelDao {
	/**
	 * exportExcel集装箱残损单
	 * @param workRecordVo
	 * @return
	 */
	public List<DamageInforRecordVo> exportExcelTzDamageInforRecord(WorkRecordVo workRecordVo);
	/**
	 * exportExcel Bay位数据
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public List<WorkRecordVo> exportExcelTzBayInfo(WorkRecordVo workRecordVo);
	/**
	 * exportExcel 作业箱量统计
	 * @param 查询条件 workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public List<WorkRecordVo> exportExcelContainerStatistics(WorkRecordVo workRecordVo);
}
