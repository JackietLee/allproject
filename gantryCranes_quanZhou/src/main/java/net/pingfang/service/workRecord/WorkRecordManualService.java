package net.pingfang.service.workRecord;

import java.util.Map;
import net.pingfang.entity.work.WorkRecordManualVo;

public interface WorkRecordManualService {
	
	/**
	 * 作业识别率统计
	 * @param workRecordManual
	 * @return
	 */
	public Map<String,Object> identificationRateStatistics(WorkRecordManualVo workRecordManual);
	
	/**
	 * 插入一条作业记录
	 * @param workRecordVo
	 * @return
	 */
	public int insertWorkRecordManual(WorkRecordManualVo workRecordManual);
	/**
	 * 查询作业记录是否存在
	 * @param workRecordVo
	 * @return
	 */
	public int getWrmExists(WorkRecordManualVo workRecordManual);
	
	/**
	 * 插入一条作业记录
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordManual(WorkRecordManualVo workRecordManual);


}
