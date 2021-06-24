package net.pingfang.service.workRecord;

import java.util.List;
import java.util.Map;
import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.json.work.WorkJson;

/**
 * 作业记录Service
 * @author Administrator
 * @since 2019-05-22
 *
 */
public interface WorkRecordService {
	
	/**
	 * 获取所有作业记录
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public PageVo<WorkRecordVo> getAllWorkRecordList(WorkRecordVo workRecordVo);
	/**
	 * 插入一条作业记录
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> insertWorkRecord(WorkJson workJson,String type);
	/**
	 * 插入一条残损信息记录
	 * @param damageInforRecordList
	 * @return
	 */
	public int insertDamageInforRecord(List<DamageInforRecordVo> damageInforRecordList);
	/**
	 * 插入一条图片信息记录
	 * @param imgInfoList
	 * @return
	 */
	public int insertImgInfo(List<ImgInfoVo> imgInfoList);
	
	/**
	 * 根据seqNo任务编号获取所有作业记录总数
	 * @return
	 */
	public int getCountWorkRecordBySeqNo(String seqNo);	
	public List<WorkRecordVo> getWorkRecordBySeqNo(String seqNo);
	/**
	 * 根据seqNo任务编号更新作业记录
	 * @param workJson
	 * @return
	 */
	public int updateWorkRecordBySeqNo(WorkJson workJson);
	/**
	 * 根据seqNo任务编号删除作业记录
	 * @param workJson
	 * @return
	 */
	public int deleteWorkRecordBySeqNo(String seqNo);	
	public int deleteWorkRecordById(int id);
	
	/**
	 *  根据int查询作业记录信息
	 * @param int
	 * @return
	 */
	public WorkRecordVo getWorkRecordById(int id);
	public WorkRecordVo getNewWorkRecordById(int id);
	
	/**
	 * 根据作业编号更新贝位
	 * @param workJson
	 * @return
	 */
	public int updateBayInfoBySeqNo(List<WorkRecordVo> workList);
	/**
	 * 根据作业编号更新贝位
	 * @param workJson
	 * 2020-06-03
	 * @return
	 */
	public int updateBayInfo(List<WorkRecordVo> workList);
	/**
	 * 根据id更新贝位
	 * @param id
	 * @param bayInfo
	 * @return
	 */
	public int updateBayInfoById(int id, String bayInfo);
	/**
	 *作业数据统计
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public Map<String,Object> getWorkRecordStatistics(WorkRecordVo workRecordVo);
	/**
	 *  查询已理货数据
	 * @param 查询条件 workRecordVo
	 * 2019-10-22
	 * @return
	 */
	public Map<String,List<WorkRecordVo>> getAlreadyWorkRecordList(List<CranePreparationVo> cranePreparationList, int state);
	/**
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * 2019-10-22
	 * @return
	 */
	public WorkRecordVo getAlreadyWorkRecordById(WorkRecordVo workRecord);
	public WorkRecordVo getNewAlreadyWorkRecordById(WorkRecordVo workRecord);
	/**
	 * 获取 装货港，卸货港，目的港
	 * @param vesselCode   船舶代码
	 * @param vesselNumber 船舶艘次号
	 * @return
	 */
	public VesselContainerVo getVesselContainer(VesselContainerVo vesselContainer);
	/**
	 * 根据ID更新贝位
	 * @param workRecordVo
	 * @return
	 */
	//public int updateBayInfo(WorkRecordVo workRecordVo);
	/**
	 * 已理货数据二次提交更新
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordById(WorkRecordVo workRecordVo);
	/**
	 *第三方调用接口
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getWorkRecordData(WorkRecordVo workRecordVo);
	/**
	 * 更新X,Y坐标
	 * 第三方调用接口
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordXY(WorkRecordVo workRecordVo);
	/**
	 * 新增历史作业数据
	 * @param workRecordVo
	 * @return
	 */
	public int insertHistoricalWorkRecord(WorkRecordVo workRecordVo);
	/**
	 * 批量插入作业记录
	 * @param workRecordVo
	 * @return
	 */
	public int insertWorkRecordList(List<WorkRecordVo> list);
	/**
	 * 更新历史作业数据
	 * @param workRecordVo
	 * @return
	 */
	public int updateHistoricalWorkRecord(WorkRecordVo workRecordVo);
	/**
	 * 更新状态
	 * 状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordState(WorkRecordVo workRecordVo);
	/**
	 * 数据重复校验
	 * @param workRecordVo
	 * @return
	 */
	public int getCountRecord(WorkRecordVo workRecordVo);
	/**
	 * 数据重复校验
	 * @param workRecordVo
	 * @return
	 */
	public List<WorkRecordVo> getRecord(WorkRecordVo workRecordVo);
	/**
	 * 1查询BAY位是否已经存在
	 * @param workRecordVo
	 * @return
	 */
	public int getCountBay(WorkRecordVo workRecordVo);
	public int getNewCountBay(String vesselNumber, List<String> bayInfoList);
	
	
}
