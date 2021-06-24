package net.pingfang.serviceImpl.huangPu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pingfang.dao.huangPu.HpImgInfoDao;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.service.huangPu.HpImgInfoService;

@Service
public class HpImgInfoServiceImpl implements HpImgInfoService{

	@Autowired
	private HpImgInfoDao hpImgInfoDao;
	/**
	 * 插入一条图片信息记录
	 * @param imgInfoList
	 * @return
	 */
	@Override
	public int insertImgInfo(List<ImgInfoVo> imgInfoList) {
		return hpImgInfoDao.insertImgInfo(imgInfoList);
	}
	/**
	 * 获取作业记录对应的图片
	 * @param workId
	 * @return
	 */
	@Override
	public List<ImgInfoVo> getHpImgInfoListByWorkId(int workId){
		return hpImgInfoDao.getHpImgInfoListByWorkId(workId);
	}
}
