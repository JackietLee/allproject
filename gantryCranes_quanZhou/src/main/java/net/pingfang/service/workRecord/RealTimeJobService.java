package net.pingfang.service.workRecord;

import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;

public interface RealTimeJobService {

	/**
	 * 更新状态
	 * @param workRecordVo
	 * @return
	 */
	public int updateWorkRecordStateById(WorkRecordVo workRecordVo);
	/**
	 * 更新或者新增OCR数据
	 * @param workRecordVo
	 */
	public String addOrUpdateWlData(WorkRecordVo workRecordVo, int cancel);
	/**
	 *  根据id查询作业记录信息
	 * @param id
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
	public int updateImgMerge(String imgInfo, ImgInfoVo imgInfoVo);
	/**
	 * 发送箱号给码头
	 * @param workRecordVo
	 */
	public void mqSendToContainerid(WorkRecordVo workRecordVo);
	
	/**
	 * 更新N4状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateN4Status(int id,int status);
	/**
	 * 更新N4状态
	 * @param seqNo
	 * @param contaid
	 * @param status
	 * @return
	 */
	public int updateN4StatusTwo(String seqNo, String contaid, int status);
	
}
