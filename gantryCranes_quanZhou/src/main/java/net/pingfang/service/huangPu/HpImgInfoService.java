package net.pingfang.service.huangPu;

import java.util.List;

import net.pingfang.entity.work.ImgInfoVo;

public interface HpImgInfoService {
	/**
	 * 插入一条图片信息记录
	 * @param imgInfoList
	 * @return
	 */
	public int insertImgInfo(List<ImgInfoVo> imgInfoList);
	/**
	 * 获取作业记录对应的图片
	 * @param workId
	 * @return
	 */
	public List<ImgInfoVo> getHpImgInfoListByWorkId(int workId);
}
