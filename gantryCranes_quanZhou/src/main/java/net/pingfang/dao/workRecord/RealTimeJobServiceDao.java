package net.pingfang.dao.workRecord;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface RealTimeJobServiceDao {

	/**
	 * 更新状态
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordStateById(WorkRecordVo workRecordVo);
	
	/**
	 *  根据id查询作业记录信息
	 * @param workJson
	 * @return
	 */
	public WorkRecordVo getRtWorkRecordById(int id);
	/**
	 * 查询BAY位是否被占用
	 * @param workRecordVo
	 * @return
	 */
	public int getCountBay(WorkRecordVo workRecordVo);
	/**
	 * 合并作业数据图片
	 * @param imgInfoVo
	 * @return
	 */
	public int updateImgMerge(@Param("seqNo")String seqNo, @Param("imgInfo")ImgInfoVo imgInfoVo);
	/**
	 * 更新N4状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateN4Status(@Param("id")int id,@Param("status")int status);
	/**
	 * 更新N4状态
	 * @param seqNo
	 * @param contaid
	 * @param status
	 * @return
	 */
	public int updateN4StatusTwo(@Param("seqNo")String seqNo, @Param("contaid")String contaid, @Param("status")int status);
}
