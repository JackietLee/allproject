package net.pingfang.serviceImpl.huangPu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import net.pingfang.dao.huangPu.HpWorkRecordDao;
//import net.pingfang.entity.config.Config;
//import net.pingfang.entity.config.IpConfig;
import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.huangPu.HpImgInfoService;
import net.pingfang.service.huangPu.HpWorkRecordService;
import net.pingfang.service.huangPu.WorkSwitchService;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.websoket.SocketIoClient_Bay;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.DateUtil;
import net.pingfang.utils.ImgUtil;

@Service
public class HpWorkRecordServiceImpl implements HpWorkRecordService {

	@Autowired
	private HpWorkRecordDao hpWorkRecordDao;
	@Autowired
	private HpImgInfoService hpImgInfoService;
	@Autowired
	private WorkSwitchService workSwitchService;
	@Autowired
	private WL_BerthPlanService wl_berthPlanService;
//	@Autowired
//	private Config config;
	//@Autowired
	//private IpConfig ipConfig;
	@Autowired
	private SocketIoClient_Bay SocketIoClient_Bay;
	@Autowired
	private WorkRecordService workRecordService;

	@Value("${img_Path}")
	private String img_Path;

	private static final String URL_STR = "/"; // 作业报文类型
	//private final static String JPG = ".jpg";
	//private final static String COMPRESS_JPG = "_compress.jpg";

	final Logger log = LoggerFactory.getLogger(HpWorkRecordServiceImpl.class);

	/**
	 * 获取所有作业记录
	 * 
	 * @param 当前页    currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件   workRecordVo
	 * @return
	 */
	@Override
	public PageVo<WorkRecordVo> getHpAllWorkRecordList(WorkRecordVo workRecordVo) {
		if (null == workRecordVo) {
			workRecordVo = new WorkRecordVo();
		}
		PageVo<WorkRecordVo> pageVo = new PageVo<WorkRecordVo>();
		int totalCount = hpWorkRecordDao.getHpCountWorkRecord(workRecordVo);
		if (totalCount > 0) {
			pageVo.initPage(workRecordVo.getCurrentPage(), workRecordVo.getPageSize(), totalCount);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageVo", pageVo);
			map.put("workRecordVo", workRecordVo);

			List<WorkRecordVo> wrList = hpWorkRecordDao.getHpAllWorkRecordList(map);
			pageVo.setList(wrList);
		}
		return pageVo;
	}

	/**
	 * 根据id查询作业记录信息
	 * 
	 * @param workJson
	 * @return
	 */
	@Override
	public WorkRecordVo getHpWorkRecordById(int id) {
		WorkRecordVo workRecord = hpWorkRecordDao.getHpAlreadyWorkRecordById(id);
		if (null != workRecord) {
			List<String> vesselNumberList = new ArrayList<String>();
			vesselNumberList.add(workRecord.getVesselNumber());
			// 获取in_voyage, out_voyage
			List<BerthPlanInfoVo> bpList = wl_berthPlanService.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
			if (null != bpList && bpList.size() > 0) {
				workRecord.setInVoyage(bpList.get(0).getInVoyage());
				workRecord.setOutVoyage(bpList.get(0).getOutVoyage());
			}
			/*
			 * VesselContainerVo vesselContainer = new VesselContainerVo();
			 * vesselContainer.setVesselCode(workRecord.getVesselCode());
			 * vesselContainer.setVesselNumber(workRecord.getVesselNumber());
			 * vesselContainer.setContainerNumber(workRecord.getUpdateContaid()); if(0 ==
			 * workRecord.getWorkType()) { vesselContainer.setJobType("LD"); }else {
			 * vesselContainer.setJobType("DS"); } vesselContainer =
			 * this.getVesselContainer(vesselContainer); if(null !=vesselContainer) {
			 * workRecord.setVesselPosition(vesselContainer.getVesselPosition());//预配bay位 }
			 */

			List<ImgInfoVo> imgList = hpImgInfoService.getHpImgInfoListByWorkId(id);
			if (null != imgList && imgList.size() > 0) {
				StringBuilder sb = new StringBuilder();
				String str = ",";
				for (ImgInfoVo img : imgList) {
					sb.append(img.getImgPathName()).append(str);
				}
				str = sb.toString();
				str = str.substring(0, str.length() - 1);
				workRecord.setImgUrl(str);

				// MP4
				List<String> mp4List = new ArrayList<String>();
				String areaNum = workRecord.getAreaNum();
				// 设置图片对应的MP4
				String mp4 = null;
				String nweMp4 = null;
				for (ImgInfoVo img : imgList) {
					mp4 = img.getImgPathName();
					if (null != mp4 && mp4.trim().length() > 0) {
						mp4.replace(".jpg", ".mp4");
						nweMp4 = img_Path + mp4.substring(mp4.indexOf(areaNum) - 1);
						if (ImgUtil.existsFile(nweMp4)) {
							mp4List.add(mp4);
						}
					}
				}
			}
		}
		return workRecord;
	}

	/**
	 * 查询黄埔实时作业数据
	 * 
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public Map<String, List<WorkRecordVo>> getHpRealTimeWorkRecordList(List<CranePreparationVo> cranePreparationList,
			int state) {
		Map<String, List<WorkRecordVo>> map = new HashMap<String, List<WorkRecordVo>>();
		for (CranePreparationVo cp : cranePreparationList) {
			map.put(cp.getCraneNum(), new ArrayList<WorkRecordVo>());
		}
		List<WorkRecordVo> list = hpWorkRecordDao.getHpRealTimeWorkRecordList(cranePreparationList, state);
		if (null != list && list.size() > 0) {
			// 根据岸桥编号分组
			for (WorkRecordVo workRecord : list) {
				map.get(workRecord.getCraneNum()).add(workRecord);
			}
		}
		return map;
	}

	/**
	 * 根据id查询已理货数据信息
	 * 
	 * @param 查询条件 workRecordVo 2020-10-10
	 * @return
	 */
	@Override
	public WorkRecordVo getHpAlreadyWorkRecordById(int id) {
		WorkRecordVo workRecord = hpWorkRecordDao.getHpAlreadyWorkRecordById(id);
		if (null != workRecord && null != workRecord.getSeqNo()) {
			workRecord.setPassTime(DateUtil.dateParse("yyyy-MM-dd HH:mm:ss", workRecord.getPassTime()));
			List<String> vesselNumberList = new ArrayList<String>();
			vesselNumberList.add(workRecord.getVesselNumber());

			/**/
			// 获取alongside,in_voyage, out_voyage
			List<BerthPlanInfoVo> bpList = wl_berthPlanService.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
			if (null != bpList && bpList.size() > 0) {
				BerthPlanInfoVo bp = bpList.get(0);
				workRecord.setAlongside(bp.getAlongside());
				workRecord.setInVoyage(bp.getInVoyage());
				workRecord.setOutVoyage(bp.getOutVoyage());
			}
			// 根据seqNo查询图片记录信息
			// List<ImgInfoVo> imgList =
			// workRecordDao.getImgInfoBySeqNo(workRecord.getSeqNo());
			List<ImgInfoVo> imgList = hpImgInfoService.getHpImgInfoListByWorkId(id);
			if (null != imgList && imgList.size() > 0) {
				// 过滤残损图片
				List<ImgInfoVo> newImgList = new ArrayList<ImgInfoVo>();
				for (ImgInfoVo img : imgList) {
					if (4 != img.getSnapImgType()) {
						newImgList.add(img);
					}
				}
				if (null != newImgList && newImgList.size() > 0) {
					// 设置图片
					// workRecord.setImgUrl(this.getImgUrl(newImgList));
					// workRecord.setImgUrl(this.getNewImgUrl(newImgList));
					this.setListImgUrl(workRecord, newImgList);

					// 设置图片对应的MP4
					List<String> mp4List = new ArrayList<String>();
					String pathName = null;
					String str = ",";
					String mp4 = null;
					String nweMp4 = null;
					String areaNum = workRecord.getAreaNum();
					for (ImgInfoVo img : newImgList) {
						pathName = img.getImgPathName();
						if (null != pathName) {
							String strName[] = pathName.split(str);
							if (strName.length > 0) {
								for (int i = 0; i < strName.length; i++) {
									mp4 = strName[i].replace(".jpg", ".mp4");
									nweMp4 = img_Path + mp4.substring(mp4.indexOf(areaNum) - 1);
									// 判断MP4文件是否存在
									if (ImgUtil.existsFile(nweMp4)) {
										mp4List.add(mp4);
									}
								}
							}
						}
					}
					workRecord.setMp4Url(mp4List);
				}
			}
			VesselContainerVo vesselContainer = new VesselContainerVo();
			vesselContainer.setVesselCode(workRecord.getVesselCode());
			vesselContainer.setVesselNumber(workRecord.getVesselNumber());
			vesselContainer.setContainerNumber(workRecord.getUpdateContaid());
			// 'DS'为'卸船', 'DD'为'直提', 'LD'为'装船', 'DL'为'直装', 'LN'为'捣卸', 'RS'为'捣装', 'SH'为'搬移',
			// 'DT'为'中转不落地'
			if (1 == workRecord.getWorkType()) {
				vesselContainer.setJobType("DS");
			} else {
				vesselContainer.setJobType("LD");

			}
			vesselContainer = workRecordService.getVesselContainer(vesselContainer);
			if (null != vesselContainer) {
				workRecord.setPortLoading(vesselContainer.getPortLoading()); // 装货港
				workRecord.setPortDischarge(vesselContainer.getPortDischarge()); // 卸货港
				workRecord.setPortDestination(vesselContainer.getPortDestination()); // 目的港
				workRecord.setVesselPosition(vesselContainer.getVesselPosition()); // 预配bay位
				workRecord.setStuffingStatus(vesselContainer.getStuffingStatus()); // 装载状态 重（F、full）、空（E、empty）
			}

			/*
			 * List<ImgInfoVo> imgList = hpImgInfoService.getHpImgInfoListByWorkId(id);
			 * if(null !=imgList && imgList.size() >0) { StringBuilder sb = new
			 * StringBuilder(); String str = ";"; for(ImgInfoVo img : imgList) {
			 * sb.append(img.getImgPathName()).append(str); } str = sb.toString(); str =
			 * str.substring(0,str.length()-1); workRecord.setImgUrl(str); }
			 */
		}
		return workRecord;
	}

	/**
	 * 插入门机作业数据
	 * 
	 * @param hpMjMsg
	 * @return
	 */
	public int insertHpWorkRecord(String hpMjMsg) {
		int count = 0;
		log.info("门机报文处理：" + hpMjMsg);
		try {
			JSONObject jsonJobData = new JSONObject(hpMjMsg);
			// leo 2020-10-29 只处理CCR_RESULT的报文
			String messageType = jsonJobData.getString("messageType");
			if (!"CCR_RESULT".equals(messageType)) {
				log.info("报文处理：非CCR_RESULT报文，不处理，返回0");
				return 0;
			}

			JSONObject ccrJson = jsonJobData.getJSONObject("ccr");
			// "det_num": 0,//检测到的箱号数量
			// String det_num = jsonJobData.get("det_num").toString();
			String det_num = "1";
			if (null != ccrJson && "1".equals(det_num)) {
				WorkRecordVo workRecord = new WorkRecordVo();

				// leo 2020-10-30
				String containerNumber = StringUtils.isEmpty(ccrJson.getString("ccode")) ? ""
						: ccrJson.getString("ccode");
				workRecord.setContaid(containerNumber);
				workRecord.setUpdateContaid(containerNumber.toUpperCase());
				// workRecord.setIso("ISO");
				// leo 2020-10-30
				workRecord.setIso(ccrJson.getString("ISO"));

				String crane_num = jsonJobData.getString("crane_num");
				workRecord.setSeqNo(jsonJobData.getString("seq_no"));
				// workRecord.setCraneNum(jsonJobData.getString("crane_num"));
				workRecord.setCraneNum(crane_num);

				workRecord.setAreaNum(jsonJobData.getString("area_num"));
				workRecord.setWorkType(Integer.parseInt(jsonJobData.getString("work_type")));
				workRecord.setContainerType(Integer.parseInt(jsonJobData.getString("container_type")));
				workRecord.setLaneNum(Integer.parseInt(jsonJobData.getString("lane_num")));
				workRecord.setState(jsonJobData.getString("state"));
				workRecord.setPassTime(jsonJobData.getString("passtime"));

				// 拖车识别节点
				JSONObject tpplate_resultJson = jsonJobData.getJSONObject("tpplate_result");
				JSONObject tp_result = tpplate_resultJson.getJSONObject("tp_result");
				//workRecord.setPlate(tp_result.getString("top_plate"));
				// leo 2020-10-30
				String truckNumber = tp_result.getString("top_plate");
				workRecord.setPlate(truckNumber);
				workRecord.setTopPlate(truckNumber);
				workRecord.setUpdateTopPlate(truckNumber);

				// 获取门机配制
				CranePreparationVo cp = workSwitchService.getHpCranePreparationByCraneNum(crane_num);
				workRecord.setVesselNumber(cp.getVesselNumber());
				workRecord.setVesselCode(cp.getVesselCode());
				workRecord.setVesselNameCn(cp.getVesselNameCn());
				if(null == workRecord.getWorkType() || 2 ==workRecord.getWorkType()) {
					workRecord.setWorkType(cp.getWorkType());
				}

				count = this.insertHpWorkRecord(workRecord);
				log.info("报文处理：门机数据入库：{}", count);
				if (count > 0) {
					List<ImgInfoVo> imgInfoList = this.getImgInfoList(workRecord.getId(), jsonJobData);
					if (null != imgInfoList && imgInfoList.size() > 0) {
						count = hpImgInfoService.insertImgInfo(imgInfoList);
						log.info("报文处理：图片数据入库：{}", count);
					}
					// 广播消息
					// SocketIoClient_Bay.addJobQueueDate("insertWorkRecord_hpMj","8","门机101",JSON.toJSON(workRecord).toString());
					// leo 2020-10-29
					SocketIoClient_Bay.addJobQueueDate("insertWorkRecord_hpMj", "8", workRecord.getCraneNum(),
							JSON.toJSON(workRecord).toString());
				}
			}
		} catch (Exception e) {
			log.info("报文处理：异常-{}", e);
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 解析图片信息
	 * 
	 * @param workId
	 * @param workJson
	 * @return
	 */
	private List<ImgInfoVo> getImgInfoList(int workId, JSONObject jsonJobData) {
		List<ImgInfoVo> list = null;
		if (null != jsonJobData) {
			list = new ArrayList<ImgInfoVo>();
			String seqNo = jsonJobData.getString("seq_no"); // 唯一任务编号
			// det_result图片节点
			JSONArray det_resultJson = jsonJobData.getJSONArray("det_result");
			if (null != det_resultJson && det_resultJson.length() > 0) {
				JSONObject newJson = null;
				ImgInfoVo imgInfo = null;

//				String imgUrl = config.getServerImgUrl() + jsonJobData.getString("area_num") + URL_STR
//						+ jsonJobData.getString("crane_num") + URL_STR + seqNo.substring(0, 8) + URL_STR + seqNo + URL_STR;
				//2020-12-28（由于门机功能已经合并到岸桥，此功能暂时不会用）临时替换Url 
				String imgUrl = "http://192.168.1.250:5001/"+jsonJobData.getString("area_num") + URL_STR
						+ jsonJobData.getString("crane_num") + URL_STR + seqNo.substring(0, 8) + URL_STR + seqNo + URL_STR;

				for (int i = 0; i < det_resultJson.length(); i++) {
					newJson = new JSONObject(det_resultJson.get(i).toString());
					if (null != newJson.getString("img_path_name") && !"".equals(newJson.getString("img_path_name"))) {
						imgInfo = new ImgInfoVo();
						imgInfo.setWorkId(workId);
						imgInfo.setSeqNo(seqNo);
						imgInfo.setLocation(null);
						imgInfo.setSnapImgType(1); // 1：箱号图片
						imgInfo.setImgNum(1);
						imgInfo.setImgPathName(imgUrl + newJson.getString("img_path_name"));
						imgInfo.setImgDectRect(null);
						list.add(imgInfo);
					}
				}
			}
			// 车辆检测结果
			// 拖车识别节点
			JSONObject tpplate_resultJson = jsonJobData.getJSONObject("tpplate_result");
			JSONObject file_info = tpplate_resultJson.getJSONObject("file_info");
			int img_num = file_info.getInt("img_num");
			if (img_num > 0) {
				JSONArray top_img_path_name = jsonJobData.getJSONArray("img_path_name");
				if (null != top_img_path_name && top_img_path_name.length() > 0) {
					ImgInfoVo imgInfo = null;
					for (int i = 0; i < top_img_path_name.length(); i++) {
						if (null != top_img_path_name.get(i) && !"".equals(top_img_path_name.get(i).toString())) {
							imgInfo = new ImgInfoVo();
							imgInfo.setWorkId(workId);
							imgInfo.setSeqNo(seqNo);
							imgInfo.setLocation(null);
							imgInfo.setSnapImgType(3); // 3：车顶号图片
							imgInfo.setImgNum(1);
							imgInfo.setImgPathName(top_img_path_name.get(i).toString());
							imgInfo.setImgDectRect(null);
							list.add(imgInfo);
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 插入门机作业数据
	 * 
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public int insertHpWorkRecord(WorkRecordVo workRecordVo) {
		return hpWorkRecordDao.insertHpWorkRecord(workRecordVo);
	}

	/**
	 * 更新门机作业数据
	 * 
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public int updateHpWorkRecord(WorkRecordVo workRecordVo) {
		return hpWorkRecordDao.updateHpWorkRecord(workRecordVo);
	}

	/**
	 * 查询箱号是否存在
	 * 
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public List<WorkRecordVo> getHpRecord(WorkRecordVo workRecordVo) {
		return hpWorkRecordDao.getHpRecord(workRecordVo);
	}

	/**
	 * 更新门机作业数据状态
	 * 
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public int updateHpWorkRecordStateById(WorkRecordVo workRecordVo) {
		return hpWorkRecordDao.updateHpWorkRecordStateById(workRecordVo);
	}
	/**
	 * 删除门机作业记录
	 * @param id
	 * @return
	 */
	@Override
	public int deleteHpMjWorkRecordById(int id) {
		//删除门机作业记录图片
		int count = this.deleteHpMjImgByWorkId(id);
		//删除门机作业记录
		count = hpWorkRecordDao.deleteHpMjWorkRecordById(id);
		return count;
	}
	/**
	 * 删除门机作业记录图片
	 * @param workId
	 * @return
	 */
	public int deleteHpMjImgByWorkId(int workId) {
		return hpWorkRecordDao.deleteHpMjImgByWorkId(workId);
	}

	/**
	 * 设置图片URL 如果图片还没有FTP到服务器上则把URL替换成工控机上的URL
	 * 
	 * @param diList
	 * @return 2020-08-22 更新
	 */
	/*private void setListImgUrl(WorkRecordVo workRecord, List<ImgInfoVo> imgList) {
		
		log.info("设置图片URL:workRecord-{}",workRecord.toString());
		log.info("设置图片URL:imgList-{}",imgList.toString());
		if (null != imgList && imgList.size() > 0) {
			// 大图
			StringBuilder sb = new StringBuilder();
			// 小图
			StringBuilder sb2 = new StringBuilder();
			String str = ",";
			String pathName = null;
			String imgUrl = null;
			String imgName = null;
			String areaNum = workRecord.getAreaNum();
			for (ImgInfoVo img : imgList) {
				if (null != img.getImgPathName()) {
					pathName = img.getImgPathName();
					String[] strarray = pathName.split(",");
					if (strarray.length > 0) {
						for (int i = 0; i < strarray.length; i++) {
							if (strarray[i].length() > 6) {
								imgUrl = img_Path + strarray[i].substring(strarray[i].indexOf(areaNum) - 1);
								// 如果文件存在
								if (ImgUtil.existsFile(imgUrl)) {
									sb.append(strarray[i]).append(str);

									// 检查缩略图是否存在
									imgUrl = imgUrl.replace(JPG, COMPRESS_JPG);
									if (ImgUtil.existsFile(imgUrl)) {
										sb2.append(strarray[i].replace(JPG, COMPRESS_JPG)).append(str);
									} else {
										imgName = this.getUrlReplace(strarray[i], areaNum);
										sb2.append(imgName.replace(JPG, COMPRESS_JPG)).append(str);
									}
								} else {
									imgName = this.getUrlReplace(strarray[i], areaNum);
									sb.append(imgName).append(str);
									sb2.append(imgName.replace(JPG, COMPRESS_JPG)).append(str);
								}
							}
						}
					}
				}
			}
			// 大图
			String urlStr = sb.toString();
			if (!"".equals(urlStr)) {
				urlStr = urlStr.substring(0, urlStr.length() - 1);
				workRecord.setImgUrl(urlStr);
			}
			// 小图
			String compressUrl = sb2.toString();
			if (!"".equals(compressUrl)) {
				compressUrl = compressUrl.substring(0, compressUrl.length() - 1);
				workRecord.setCompressUrl(compressUrl);
			}

		}
	}*/
	
	//leo 2020-10-31 直接使用数据库中的图片地址，且大图小图一样
	private void setListImgUrl(WorkRecordVo workRecord, List<ImgInfoVo> imgList) {

		StringBuilder sb = new StringBuilder();
		for(ImgInfoVo i : imgList) {
			sb.append(",").append(i.getImgPathName());
		}
		String s = sb.substring(1).toString();
		workRecord.setImgUrl(s);
		workRecord.setCompressUrl(s);
	}

	/**
	 * IP替换
	 * 
	 * @param url
	 * @return
	 */
/*	private String getUrlReplace(String url, String areaNum) {
		// 参数
		// String url1 =
		// "http://192.168.1.250:5005/NICT/QC15/20200811/20200811103913801/20200811103913801_SeaL_box.jpg";
		// 更新后返回的字符串
		// String url2 =
		// "http://192.168.1.5:5005/QC15/20200811/103913801/20200811103913801_SeaL_box.jpg";
		String ip = url.substring(7, url.lastIndexOf(":"));
		String craneNum = url.substring(url.indexOf("QC"), url.indexOf("QC") + 4);
		String newIp = ipConfig.getCraneNumIp(craneNum);

		// String newUrl =url.substring(0,url.indexOf("QC")).replace(ip,
		// newIp).replace("/NICT", "")+craneNum;
		String newUrl = url.substring(0, url.indexOf("QC")).replace(ip, newIp).replace("/" + areaNum, "") + craneNum;

		int index = url.substring(0, url.lastIndexOf("/")).lastIndexOf("/");
		String imgName = url.substring(index);

		return newUrl + imgName.substring(0, 9) + "/" + imgName.substring(9);
	}
*/
	/**
	 * 数组转字符串
	 * 
	 * @param arr
	 * @param split ("/")
	 * @return
	 */
	/*private String arrayToString(String[] arr, String split) {
		String str = null;
		if (null != arr && arr.length > 0) {
			StringBuilder sb = new StringBuilder();
			String s = "";
			for (int i = 0; i < arr.length; i++) {
				if (null != arr[i] && !s.equals(arr[i])) {
					sb.append(arr[i] + split);
				}
			}
			str = sb.toString();
			if (!"".equals(str)) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}*/
}
