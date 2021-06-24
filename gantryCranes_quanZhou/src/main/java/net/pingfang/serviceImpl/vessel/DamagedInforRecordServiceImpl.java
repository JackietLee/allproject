package net.pingfang.serviceImpl.vessel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.vessel.DamagedInforRecordDao;
import net.pingfang.dao.workRecord.WorkRecordDao;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.vessel.DamagedInforRecordService;
import net.pingfang.utils.ImgUtil;

@Service
public class DamagedInforRecordServiceImpl implements DamagedInforRecordService {
	@Autowired
	private DamagedInforRecordDao damagedInforRecordDao;
	@Autowired
	private WorkRecordDao workRecordDao;
	@Autowired
	private WL_BerthPlanService wl_berthPlanService;

	/**
	 * 获取作业数据箱号
	 * @return
	 */
	@Override
	public List<WorkRecordVo> getWorkRecordSelect(WorkRecordVo workRecordVo){
		return damagedInforRecordDao.getWorkRecordSelect(workRecordVo);
	}
	/**
	 * 获取所有残损信息
	 * @return
	 */
	@Override
	public List<DamageInforRecordVo> getDamageInforRecordList(DamageInforRecordVo damageInforRecord){
		return damagedInforRecordDao.getDamageInforRecordList(damageInforRecord);
	}
	/**
	 * 根据ID获取一条残损信息
	 * @param id
	 * @return
	 */
	/*@Override
	public Map<String,Object> getDamageInforRecordById(String containerNumber){
		Map<String,Object> map = null;
		List<DamageInforRecordVo> damageInforList = damagedInforRecordDao.getDamageInforRecordById(containerNumber);
		if(null !=damageInforList && damageInforList.size() >0) {
			map = new HashMap<String, Object>();
			//根据seqNo查询图片记录信息
			List<ImgInfoVo> imgList = workRecordDao.getImgInfoBySeqNo(damageInforList.get(0).getSeqNo());
			map.put("damageInforList", damageInforList);
			map.put("imgList", this.getImgUrl(imgList));
			map.put("damageimgList", this.getDamageImgUrl(imgList));
		}
		return map;
	}*/
	/**
	 * 根据ID获取一条残损信息
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> getDamageInforRecordBySeqNo(DamageInforRecordVo damageInforRecord){
		Map<String,Object> map = new HashMap<String, Object>();
		//DamageInforRecordVo damageInfor = damagedInforRecordDao.getDamageInforRecordBySeqNo(damageInforRecord);
		map.put("damageInfor", damagedInforRecordDao.getDamageInforRecordBySeqNo(damageInforRecord));
		//根据seqNo查询图片记录信息
		List<ImgInfoVo> imgList = workRecordDao.getImgInfoBySeqNo(damageInforRecord.getSeqNo());
		if(null !=imgList && imgList.size() >0) {
			map.put("imgList", this.getImgUrl(imgList));
			map.put("damageimgList", this.getDamageImgUrl(imgList));
		}
		return map;
	}
	
	/**
	 * 根据作业ID查询数据是否已经存在
	 * @return
	 */
	@Override
	public int getCountByWorkId(int workId) {
		return damagedInforRecordDao.getCountByWorkId(workId);
	}
	/**
	 * 新增残损信息
	 * @return
	 */
	@Transactional
	@Override
	public int insertDamagedInforRecord(DamageInforRecordVo damageInforRecord){
		return damagedInforRecordDao.insertDamagedInforRecord(damageInforRecord);
	}
	/**
	 * 更新残损信息
	 * @return
	 */
	@Transactional
	@Override
	public int updateDamagedInforRecord(DamageInforRecordVo damageInforRecord){
		return damagedInforRecordDao.updateDamagedInforRecord(damageInforRecord);
	}
	/**
	 * 根据作业编号更新残损信息
	 * @return
	 */
	@Transactional
	@Override
	public int updateDamagedInforRecordByWorkId(DamageInforRecordVo damageInforRecord) {
		return damagedInforRecordDao.updateDamagedInforRecordByWorkId(damageInforRecord);
	}
	/**
	 * 更新残损信息同步状态为'Y'
	 * @return
	 */
	@Transactional
	@Override
	public int updateSynchronizationById(int id) {
		return damagedInforRecordDao.updateSynchronizationById(id);
	}
	/**
	 * 根据id删除残损信息
	 * @return
	 */
	@Transactional
	@Override
	public int deleteDamagedInforRecord(int id){
		return damagedInforRecordDao.deleteDamagedInforRecord(id);
	}
	
	/**
	 * 获取作业记录
	 * @return
	 */
	@Override
	public Map<String,List<WorkRecordVo>> getWorkRecordList(List<DamageInforRecordVo> list){
		Map<String,List<WorkRecordVo>> map = new HashMap<String,List<WorkRecordVo>>();
		for(DamageInforRecordVo di : list) {
			map.put(di.getVesselCode(), new ArrayList<WorkRecordVo>());
		}
		List<WorkRecordVo> wrList = damagedInforRecordDao.getWorkRecordList(list);
		if(null !=wrList && wrList.size() >0) {
			for(WorkRecordVo wr : wrList) {
				map.get(wr.getVesselCode()).add(wr);
			}
		}
		return map;
	}

	/**
	 * 发送残损数据到华东系统
	 */
	@Override
	public void damageSynchronization(DamageInforRecordVo damageInforRecord) {
		new Thread(new Runnable() {
            @Override
            public void run() {
            	wl_berthPlanService.damageSynchronization(damageInforRecord);
            }
        }).start(); 
	}
	
	/**
	 * 设置图片URL
	 * @param diList
	 * @return
	 */
	private String getImgUrl(List<ImgInfoVo> imgList) {
		String urlStr = null;
		if(null !=imgList && imgList.size() >0) {
			StringBuilder sb = new StringBuilder();
			String str = ",";
			for(ImgInfoVo img :imgList) {
				/*if(4!= img.getSnapImgType() && null !=img.getImgPathName()) {
					sb.append(img.getImgPathName()).append(str);
				}*/
				if(1 == img.getSnapImgType() && null !=img.getImgPathName()) {
					sb.append(img.getImgPathName()).append(str);
				}
			}
			urlStr = sb.toString();
			if(!"".equals(urlStr)) {
				urlStr = urlStr.substring(0, urlStr.length()-1);
			}
		}
		return urlStr;
	}
	/**
	 * 获取残损图片URL
	 * @param diList
	 * @return
	 */
	private List<ImgInfoVo> getDamageImgUrl(List<ImgInfoVo> imgList) {
		List<ImgInfoVo> damageimgList = null;
		if(null !=imgList && imgList.size() >0) {
			damageimgList = new ArrayList<ImgInfoVo>();
			String str = "/";
			for(ImgInfoVo img :imgList) {
				if(4 == img.getSnapImgType() && null !=img.getImgPathName()) {
					String widthHeightStr = "";
					String imgPathName = img.getImgPathName();
					String[] imgPathArray = imgPathName.split(",");
					for(int i=0; i<imgPathArray.length; i++) {
						widthHeightStr += ImgUtil.getImgWidthHeight(imgPathArray[i]) + str;
					}
					if(!"".equals(widthHeightStr)) {
						widthHeightStr = widthHeightStr.substring(0, widthHeightStr.length()-1);
						img.setWidthHeight(widthHeightStr);
					}
					damageimgList.add(img);
				}
			}
		}
		return damageimgList;
	}
}
