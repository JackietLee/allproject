package net.pingfang.dao.workRecord;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import net.pingfang.entity.work.WorkRecordManualVo;

/**
 * 人工理货操作记录（‘work_record_manual’）
 * @author Administrator
 *
 */
public interface WorkRecordManualDao {
	/**
	 * 插入一条作业记录
	 * @param workRecordManual
	 * @return
	 */
	public int insertWorkRecordManual(WorkRecordManualVo workRecordManual);
	/**
	 * 查询作业记录是否存在
	 * @param workRecordManual
	 * @return
	 */
	public int getWrmExists(WorkRecordManualVo workRecordManual);
	
	/**
	 * 插入一条作业记录
	 * @param workRecordManual
	 * @return
	 */
	public int updateWorkRecordManual(WorkRecordManualVo workRecordManual);
	
	
	
	/**
	 * 获取所有记录总数
	 * @param workRecordManual
	 * @return
	 */
	//public int getCountWorkRecord(WorkRecordManualVo workRecordManual);
	
	/**
	 * 获取所有更新过的车顶号记录总数
	 * @param workRecordManual
	 * @return
	 */
	//public int getCountUpdateTopPlate(WorkRecordManualVo workRecordManual);
	
	/**
	 * 获取所有更新过的箱号记录总数
	 * @param workRecordManual
	 * @return
	 */
	//public int getCountUpdateContaid(WorkRecordManualVo workRecordManual);
	
	/**
	 * 获取所有更新过的bayInfo记录总数
	 * @param workRecordManual
	 * @return
	 */
	//public int getCountUpdateBay(WorkRecordManualVo workRecordManual);
	
	/**
	 * 获取所有自动理货记录总数
	 * @param workRecordManual
	 * @return
	 */
	//public int getCountAutoRecord(WorkRecordManualVo workRecordManual);
	
	/**
	 * 理货记录统计
	 * @param workRecordManual
	 * @return
	 */
	public List<WorkRecordManualVo> identificationRateStatistics(@Param("wrm")WorkRecordManualVo workRecordManual,@Param("list")List<String> craneNumList);
	public List<WorkRecordManualVo> identificationRateStatisticsTwo(WorkRecordManualVo workRecordManual);

}
