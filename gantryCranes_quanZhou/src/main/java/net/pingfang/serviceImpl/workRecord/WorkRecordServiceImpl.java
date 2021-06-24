package net.pingfang.serviceImpl.workRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.pingfang.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import net.pingfang.dao.vessel.VesselContainerDao;
import net.pingfang.dao.workRecord.WorkRecordDao;
import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.json.work.BayInfoJson;
import net.pingfang.json.work.BayResultJson;
import net.pingfang.json.work.CcrResultJson;
import net.pingfang.json.work.ContaInforJson;
import net.pingfang.json.work.DamagedInfoJson;
import net.pingfang.json.work.DamagedResultJson;
import net.pingfang.json.work.FileInfoJson;
import net.pingfang.json.work.PicDataJson;
import net.pingfang.json.work.PlateResultJson;
import net.pingfang.json.work.TpplateResultJson;
import net.pingfang.json.work.WorkJson;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.ocr.WL_VesselContainerService;
import net.pingfang.service.vessel.CraneInfoService;
import net.pingfang.service.vessel.VesselContainerSeizeSeatService;
import net.pingfang.service.workRecord.CranePreparationService;
import net.pingfang.service.workRecord.RealTimeJobService;
//import net.pingfang.synchronousService.SynchroWorkRecordService;
//import net.pingfang.synchronousServiceImpl.SynchroWorkRecordThread;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.DateUtil;
import net.pingfang.utils.ImgUtil;


/**
 *  作业记录ServiceImpl
 * @author Administrator
 * @since 2019-05-22
 *
 */
@Service
public class WorkRecordServiceImpl implements WorkRecordService {
	@Autowired
	private WorkRecordDao workRecordDao;
	@Autowired
	private CranePreparationService cranePreparationService;
	@Autowired
	private VesselContainerSeizeSeatService vesselContainerSeizeSeatService;
	
	@Autowired
	private WL_BerthPlanService wl_berthPlanService;
	@Autowired
	private WL_VesselContainerService wl_vesselContainerService;
	
	@Autowired
	private CraneInfoService craneInfoService;
	@Autowired
	private RealTimeJobService realTimeJobService;
	
	private final static Logger logger = LoggerFactory.getLogger(WorkRecordServiceImpl.class); 
	private final static String LR = "LR";
	private final static String UPDATE = "update";
	private final static String AUTO = "Auto";
	//private final static String NICT = "NICT";
	private final static String JPG = ".jpg";
	private final static String COMPRESS_JPG = "_compress.jpg";
	
	@Value("${img_Path}")
	private String img_Path;
	/**
	 * 获取所有作业记录
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@Override
	public PageVo<WorkRecordVo> getAllWorkRecordList(WorkRecordVo workRecordVo) {
		if(null == workRecordVo) {
			workRecordVo = new WorkRecordVo();
		}
		PageVo<WorkRecordVo> pageVo = new PageVo<WorkRecordVo>();
		int totalCount = workRecordDao.getCountWorkRecord(workRecordVo);
		if(totalCount >0) {			
			pageVo.initPage(workRecordVo.getCurrentPage(), workRecordVo.getPageSize(), totalCount);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageVo", pageVo);
			map.put("workRecordVo", workRecordVo);
			
			List<WorkRecordVo> wrList = workRecordDao.getAllWorkRecordList(map);
			/**
			 * 2020-04-18
			//获取残损记录
			List<DamageInforRecordVo> dfList = this.getDamageInforList(wrList);
			if(null !=dfList && dfList.size() >0) {
				for(WorkRecordVo wr : wrList) {
					for(int i=0; i<dfList.size(); i++) {
						if(wr.getId() == dfList.get(i).getWorkId()) {
							//设置残损程度
							wr.setTrust(dfList.get(i).getTrust());
						}
					}
				}				
			}
			*/
			pageVo.setList(wrList);
		}
		return pageVo;
	}
	/**
	 * 获取残损记录
	 * @param list
	 * @return
	 */
	/*
	private List<DamageInforRecordVo> getDamageInforList(List<WorkRecordVo> list) {
		List<DamageInforRecordVo> newDfList = null;
		if(null !=list && list.size() >0) {
			List<DamageInforRecordVo> dfList = workRecordDao.getDamageInforRecordList(list);			
			if(null !=dfList && dfList.size() >0) {
				String yz = "严重";
				String yb = "一般";
				newDfList = new ArrayList<DamageInforRecordVo>();
				boolean isOk = false;
				//按残损程度从大到小去重（严重，一般，轻微）
				for(DamageInforRecordVo df : dfList) {
					if(0 == newDfList.size()) {
						newDfList.add(df);
					}else {
						isOk = false;
						for(int i=0; i<newDfList.size(); i++) {
							//如果作业记录ID相等
							if(df.getWorkId() == newDfList.get(i).getWorkId()) {
								isOk = true;
								if(yz.equals(newDfList.get(i).getTrust())) {
									break;
								}else if(yz.equals(df.getTrust())) {
									newDfList.get(i).setTrust(df.getTrust());
									break;
								}if(yb.equals(newDfList.get(i).getTrust())) {
									break;
								}else if(yb.equals(df.getTrust())) {
									newDfList.get(i).setTrust(df.getTrust());
									break;
								}						
							}
						}
						if(!isOk) {
							newDfList.add(df);
						}
					}
				}
			}
		}
		return newDfList;
	}
	*/
	/**
	 * 插入作业记录
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public List<WorkRecordVo> insertWorkRecord(WorkJson workJson,String type) {
		int count = 0;
		//获取作业记录
		List<WorkRecordVo> workList = this.getWorkRecordList(workJson);
		//插入作业记录
		count = this.insertWorkRecordList(workList);
		//插入作业记录
		if(count >0) {
			int workId = workList.get(0).getId(); //前箱生成的作业记录唯一记录ID	uuid	外键作业记录表
			if(workList.size() >1) {
				logger.info("新增作业记录成功   workId: "+workId+", "+workList.get(1).getId());
			}else {
				logger.info("新增作业记录成功   workId: "+workId);
			}
			List<DamageInforRecordVo> damageInforRecordList = this.getDamageInforRecord(workJson, workList);
			if(damageInforRecordList !=null && damageInforRecordList.size() >0) {
				//插入残损信息记录
				count = this.insertDamageInforRecord(damageInforRecordList);
				logger.info("新增残损信息记录成功！   count: "+count);
			}
			List<ImgInfoVo> imgInfoList = null;
			if(UPDATE.equals(type)) {
				count = workRecordDao.updateImgInforBySeqNo(workId, workJson.getSeq_no());
			}else {
				imgInfoList = this.getImgInfoList(workId, workJson);
				if(null !=imgInfoList && imgInfoList.size() >0) {
					//插入图片信息记录
					count = this.insertImgInfo(imgInfoList);
					logger.info("新增图片信息记录成功！   count: "+count);
				}
			}	
			//同步作业数据
			/*if("true".equals(is_synchronous)) {
				new Thread(new SynchroWorkRecordThread(synWorkRecordService, type, workList, imgInfoList)).start();
			}*/			
		}
		//return count;
		return workList;
	}
	/**
	 * 插入一条残损信息记录
	 * @param damageInforRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertDamageInforRecord(List<DamageInforRecordVo> damageInforRecordList) {
		return workRecordDao.insertDamageInforRecord(damageInforRecordList);
	}
	/**
	 * 插入一条图片信息记录
	 * @param imgInfoVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertImgInfo(List<ImgInfoVo> imgInfoList) {
		return workRecordDao.insertImgInfo(imgInfoList);
	}
	
	/**
	 * 根据seqNo任务编号获取所有作业记录总数
	 * @return
	 */
	@Override
	public int getCountWorkRecordBySeqNo(String seqNo) {
		return workRecordDao.getCountWorkRecordBySeqNo(seqNo);
	}
	@Override
	public List<WorkRecordVo> getWorkRecordBySeqNo(String seqNo) {
		return workRecordDao.getWorkRecordBySeqNo(seqNo);
	}
	/**
	 * 根据seqNo任务编号更新作业记录
	 * @param seqNo任务编号
	 * @return
	 */
	@Transactional
	@Override
	public int updateWorkRecordBySeqNo(WorkJson workJson){
		String seqNo = workJson.getSeq_no();
		//根据seqNo任务编号删除残损信息记录
		int count = workRecordDao.deleteDamageInforRecord(seqNo);
		//根据seqNo任务编号删除图片信息记录
		//count = workRecordDao.deleteImgInfo(seqNo);
		//根据seqNo任务编号删除作业记录
		count = workRecordDao.deletetWorkRecord(seqNo);
		//插入作业记录
		List<WorkRecordVo> list = this.insertWorkRecord(workJson,UPDATE);
		count = list.size();
		return count;
	}
	/**
	 * 根据seqNo任务编号删除作业记录
	 * @param workJson
	 * @return
	 */
	@Transactional
	@Override
	public int deleteWorkRecordBySeqNo(String seqNo) {
		//根据seqNo任务编号删除残损信息记录
		int count = workRecordDao.deleteDamageInforRecord(seqNo);
		//根据seqNo任务编号删除图片信息记录
		count = workRecordDao.deleteImgInfo(seqNo);
		//根据seqNo任务编号删除作业记录
		count = workRecordDao.deletetWorkRecord(seqNo);
		return count;
	}
	/**
	 * 根据seqNo任务编号删除作业记录
	 * @param workJson
	 * @return
	 */
	@Transactional
	@Override
	public int deleteWorkRecordById(int id) {
		//根据seqNo任务编号删除残损信息记录
		int count = workRecordDao.deleteDamageInforByWorkId(id);
		//根据id删除作业记录
		count = workRecordDao.deletetWorkRecordById(id);
		return count;
	}
	/**
	 *  根据id查询作业记录信息
	 * @param workJson
	 * @return
	 */
	@Override
	public WorkRecordVo getWorkRecordById(int id){
		WorkRecordVo workRecord = null;
		List<WorkRecordVo> workRecordList = workRecordDao.getWorkRecordListById(id);
		if(null !=workRecordList && workRecordList.size() >0){
			workRecord = workRecordList.get(0);
			List<String> vesselNumberList = new ArrayList<String>();
			vesselNumberList.add(workRecord.getVesselNumber());
			//获取in_voyage, out_voyage
			List<BerthPlanInfoVo> bpList = wl_berthPlanService.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
			if(null !=bpList && bpList.size()>0) {
				workRecord.setInVoyage(bpList.get(0).getInVoyage());
				workRecord.setOutVoyage(bpList.get(0).getOutVoyage());
				workRecord.setVesselNameEn(bpList.get(0).getVesselNameEn());
			}
			VesselContainerVo vesselContainer = new VesselContainerVo();
			vesselContainer.setVesselCode(workRecord.getVesselCode());
			vesselContainer.setVesselNumber(workRecord.getVesselNumber());
			vesselContainer.setContainerNumber(workRecord.getUpdateContaid());
			if(0 == workRecord.getWorkType()) {
				vesselContainer.setJobType("LD");
			}else {
				vesselContainer.setJobType("DS");
			}
			vesselContainer = this.getVesselContainer(vesselContainer);
			if(null !=vesselContainer) {
				workRecord.setVesselPosition(vesselContainer.getVesselPosition());//预配bay位
			}
			
			//根据作业id查询残损记录信息
			List<DamageInforRecordVo> dirList = workRecordDao.getDamageInforRecordByWorkId(id);
			workRecord.setDirList(dirList);
			/*
			if(null !=dirList && dirList.size() >0) {
				workRecord.setDirList(dirList);
				String yz = "严重";
				String yb = "一般";
				for(int i=0; i<dirList.size(); i++) {
					if(yz.equals(dirList.get(i).getTrust())) {
						workRecord.setDamaged(yz);
						break;
					}else if(yb.equals(dirList.get(i).getTrust())) {
						workRecord.setDamaged(yb);
					}
				}
				if(null == workRecord.getDamaged()) {
					workRecord.setDamaged("轻微");
				}
			}
			*/
			//根据seqNo查询图片记录信息
			//List<ImgInfoVo> imgList = workRecordDao.getImgInfoBySeqNo(workRecord.getSeqNo());
			//workRecord.setImgUrl(this.getImgUrl(imgList));
			
			
			StringBuilder sb = new StringBuilder();
			String str = ",";
			for(WorkRecordVo wr : workRecordList) {
				if(null !=wr.getImgUrl()) {
					sb.append(wr.getImgUrl()).append(str);
				}
			}
			//MP4
			List<String> mp4List = new ArrayList<String>();
			String urlStr = sb.toString();
			if(urlStr.length() >0) {
				urlStr = urlStr.substring(0, urlStr.length()-1);
				String areaNum = workRecord.getAreaNum();
				//设置图片对应的MP4
				String strName[] = urlStr.split(str);
				
				String craneNum = workRecord.getCraneNum();
				String controlIp = null; 		//中控机IP(默认为250服务器地址)
				CraneInfoVo craneInfoVo = craneInfoService.getCraneInfoByCraneNum(craneNum);
				if(null !=craneInfoVo && null !=craneInfoVo.getControlIp()) {
					controlIp = craneInfoVo.getControlIp();
				}
				if(strName.length >0) {
					String mp4 = null;
					String path = null;
					String nweMp4 = null;
					StringBuilder newSb = new StringBuilder();
					for(int i=0; i<strName.length; i++) {
						path = strName[i];
						if(null !=path && path.trim().length() >0) {
							mp4 = path.replace(".jpg", ".mp4");
							nweMp4 = img_Path + mp4.substring(mp4.indexOf(areaNum)-1);
							//判断MP4文件是否存在
							if(ImgUtil.existsFile(nweMp4)) {
								mp4List.add(mp4);
							}
							
							//判断图片文件是否存在
							if(path.length() >6) {
								String sUrl = img_Path + path.substring(path.indexOf(areaNum)-1);
								//如果文件存在
								if(ImgUtil.existsFile(sUrl)) {
									newSb.append(path).append(str);
								}else {
									sUrl = this.getUrlReplace(path, areaNum, craneNum, controlIp);
									newSb.append(sUrl).append(str);
								}
							}							
						}
					}
					str = newSb.toString();
					str = str.substring(0, str.length()-1);
					workRecord.setImgUrl(str);
				}				
			}
			workRecord.setMp4Url(mp4List);
		}
		return workRecord;
	}
	@Override
	public WorkRecordVo getNewWorkRecordById(int id) {
		return workRecordDao.getNewWorkRecordById(id);
	}
	
	/**
	 * 根据作业编号更新贝位
	 * @param workJson
	 * @return
	 */
	@Transactional
	@Override
	public int updateBayInfoBySeqNo(List<WorkRecordVo> workList) {
		logger.info("进入BAY位更新方法 updateBayInfoBySeqNo");
		logger.info(JsonUtil.javaToJsonStr(workList));
		int count = 0;
		//获取作业记录
	//	List<WorkRecordVo> workList = this.getWorkRecordList(workJson);
		if(null !=workList && workList.size() >0) {
			int i = 0;
			List<WorkRecordVo> updateWorkList = new ArrayList<WorkRecordVo>();
			
			List<WorkRecordVo> sList = new ArrayList<WorkRecordVo>();	//如果BAY位已经被占用，则直接更新状态为未处理
			for(WorkRecordVo wr :workList) {
				//根据crane_num，vessel_voyage_number，bay_info查询数据是否存在，如果存在则不更新
				i = this.getCountBay(wr);
				logger.info("进入BAY位更新方法 i:"+i);
				if(0 == i) {								
					String bayInfo = wr.getBayInfo();
					if(null !=bayInfo && 7 == bayInfo.length()) {
						VesselContainerVo vesselContainer = new VesselContainerVo();
						vesselContainer.setVesselCode(wr.getVesselCode());
						vesselContainer.setVesselNumber(wr.getVesselNumber());
						vesselContainer.setStdBay(bayInfo.substring(0,3));
						vesselContainer.setStdRow(bayInfo.substring(3,5));
						vesselContainer.setStdTier(bayInfo.substring(5));
						//查询当前位置是否有占位箱		
						i = vesselContainerSeizeSeatService.getCountContainerSeizeSeat(vesselContainer);
					}					
					if(0 == i) {
						updateWorkList.add(wr);
					}else {
						wr.setState("0");
						sList.add(wr);
						logger.error("箱号："+wr.getUpdateContaid()+"识别BAY位"+wr.getBayInfo()+"失败！该位置已经有占位箱了！");
					}
				}else {
					wr.setState("0");
					sList.add(wr);
					logger.error("箱号："+wr.getUpdateContaid()+"识别BAY位"+wr.getBayInfo()+"失败！该位置已经有箱了！");
				}
			}
			logger.info("进入BAY位更新方法 updateWorkList.size():"+updateWorkList.size());
			if(updateWorkList.size() >0) {
				count = workRecordDao.updateBayInfoBySeqNoList(updateWorkList);
				//查询需要同步数据
				List<WorkRecordVo> wrList = workRecordDao.getWorkRecord(updateWorkList.get(0).getVesselNumber(), updateWorkList);
				logger.info("查询需要同步数据:"+wrList.size());
				//调用亮哥API更新外理数据库表
				for(WorkRecordVo wr : wrList) {
					logger.info("调用亮哥API更新外理数据库表");
					String msg = realTimeJobService.addOrUpdateWlData(wr, 0);
					logger.info("调用亮哥API更新外理数据库表  msg:"+msg);
					//根据返回状态更新n4_status,默认为1成功，2为失败，3为N4接口调用成功社区接口调用失败
					if("200".equals(msg)) {
						wr.setState("1");
						sList.add(wr);
					}if("3".equals(msg)) {
						realTimeJobService.updateN4Status(wr.getId(), 3);
					}else {
						realTimeJobService.updateN4Status(wr.getId(), 2);
					}
				}
				
			}
			if(sList.size() >0) {
				//更新数据为未处理或者已处理
				workRecordDao.updateWorkRecord(sList.get(0).getVesselNumber(), sList);
			}
		}
		return count;
	}
	
	/**
	 * 根据作业编号更新贝位
	 * @param workJson
	 * 2020-06-03
	 * @return
	 */
	@Transactional
	@Override
	public int updateBayInfo(List<WorkRecordVo> workList) {
		int count = 0;
		//获取作业记录
		if(null !=workList && workList.size() >0) {
			int i = 0;
			List<WorkRecordVo> updateWorkList = new ArrayList<WorkRecordVo>();
			for(WorkRecordVo wr :workList) {
				//根据crane_num，vessel_voyage_number，bay_info查询数据是否存在，如果存在则不更新
				i = this.getCountBay(wr);
				if(0 == i) {								
					String bayInfo = wr.getBayInfo();
					if(null !=bayInfo && 7 == bayInfo.length()) {
						VesselContainerVo vesselContainer = new VesselContainerVo();
						vesselContainer.setVesselCode(wr.getVesselCode());
						vesselContainer.setVesselNumber(wr.getVesselNumber());
						vesselContainer.setStdBay(bayInfo.substring(0,3));
						vesselContainer.setStdRow(bayInfo.substring(3,5));
						vesselContainer.setStdTier(bayInfo.substring(5));
						//查询当前位置是否有占位箱		
						i = vesselContainerSeizeSeatService.getCountContainerSeizeSeat(vesselContainer);
					}					
					if(0 == i) {
						updateWorkList.add(wr);
					}
				}
			}
			if(updateWorkList.size() >0) {
				//count = workRecordDao.updateBayInfoBySeqNo(workList);
				count = workRecordDao.updateBayInfoBySeqNoList(updateWorkList);
				if(count >0) {
					//VesselContainerVo vesselContainer = null;
					//String bayInfo = null;
					//调用亮哥API更新外理数据库表
					for(WorkRecordVo wr : updateWorkList) {
						/*
						 * 2020-07-04
						 * 
						vesselContainer = new VesselContainerVo();
						bayInfo = wr.getBayInfo();
						if(null !=wr.getUpdateContaid() && 7 == bayInfo.length()) {
							vesselContainer.setContainerNumber(wr.getUpdateContaid());
							vesselContainer.setVesselNumber(wr.getVesselNumber());							
							vesselContainer.setStdBay(bayInfo.substring(0,3));
							vesselContainer.setStdRow(bayInfo.substring(3,5));
							vesselContainer.setStdTier(bayInfo.substring(5));
							vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
						}*/
						this.insertContainerBay(wr);
					}
				}else {
					//2020-07-04补救mybatis批量更新，一条成功一条失败
					if(updateWorkList.size() >1) {
						for(WorkRecordVo wr : updateWorkList) {
							count = workRecordDao.updateBayInfoBySeqNo(wr);
							if(count >0) {
								this.insertContainerBay(wr);
							}
						}
					}
				}
			}
		}
		return count;
	}
	
	
	
	
	
	
	/**
	 * 根据id更新贝位
	 * @param id
	 * @param bayInfo
	 * @return
	 */
	public int updateBayInfoById(int id, String bayInfo) {
		return workRecordDao.updateBayInfoById(id, bayInfo);
	}
	/**
	 * 调用亮哥接口同步BAY位到华东系统
	 * @param wr
	 */
	private void insertContainerBay(WorkRecordVo wr) {
		VesselContainerVo vesselContainer = new VesselContainerVo();
		String bayInfo = wr.getBayInfo();
		if(null !=wr.getUpdateContaid() && 7 == bayInfo.length()) {
			vesselContainer.setContainerNumber(wr.getUpdateContaid());
			vesselContainer.setVesselNumber(wr.getVesselNumber());							
			vesselContainer.setStdBay(bayInfo.substring(0,3));
			vesselContainer.setStdRow(bayInfo.substring(3,5));
			vesselContainer.setStdTier(bayInfo.substring(5));
			
			vesselContainer.setBillNumber(wr.getSeqNo());	//设置作业编号用于二次更新
			vesselContainerSeizeSeatService.insertContainerBay(vesselContainer);
			
			
		}
	}
	
	/**
	 * 已理货数据二次提交更新
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateWorkRecordById(WorkRecordVo workRecordVo) {
		return workRecordDao.updateWorkRecordById(workRecordVo);
	}
	/**
	 *作业数据统计
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@Override
	public Map<String,Object> getWorkRecordStatistics(WorkRecordVo workRecordVo){
		Map<String,Object> map = null;
		//获取所有作业数据（已理货和未理货）
		List<WorkRecordVo> workRecordList = workRecordDao.getWorkRecordStatistics(workRecordVo);
		if(null !=workRecordList && workRecordList.size() >0) {
			map = new HashMap<String,Object>();
			map.put("workRecordList", workRecordList);
			//已理货数据
			List<WorkRecordVo> newWorkRecordList = new ArrayList<WorkRecordVo>();
			for(WorkRecordVo wr : workRecordList) {
				//如果state等于1则为已理货数据
				if("1".equals(wr.getState())) {
					newWorkRecordList.add(wr);
				}
			}	
			//装船总量
			int ldMountCount = wl_vesselContainerService.getMountStatistics(workRecordList,"LD");				//集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
			//卸船总量
			int dsMountCount = wl_vesselContainerService.getMountStatistics(workRecordList,"DS");				//集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
			//已理货数据
			int ldMountCount2 = wl_vesselContainerService.getMountStatistics(newWorkRecordList,"LD");				//集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
			int dsMountCount2 = wl_vesselContainerService.getMountStatistics(newWorkRecordList,"DS");				//集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
			//剩余量 = 总量 - 理货数据
			int ldSurplus = ldMountCount - ldMountCount2; //装船剩余量
			int dsSurplus = dsMountCount - dsMountCount2; //卸船剩余量
			
			map.put("ldMountCount", ldMountCount);
			map.put("dsMountCount", dsMountCount);	
			map.put("ldSurplus", ldSurplus);
			map.put("dsSurplus", dsSurplus);
		}
		return map;
	}
	
	/**
	 *  查询已理货数据
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@Override
	public Map<String,List<WorkRecordVo>> getAlreadyWorkRecordList(List<CranePreparationVo> cranePreparationList, int state){
		Map<String,List<WorkRecordVo>> map = new HashMap<String,List<WorkRecordVo>>();
		List<String> bayList = null;
		String[] bay = null;
		String bayStr = null;
		String str = ";";
		for(CranePreparationVo cp : cranePreparationList) {
			bayList = new ArrayList<String>();
			bayStr = cp.getBay();
			if(null !=bayStr && bayStr.length() >2){
				//如果是两个BAY，则把中间的那个BAY补上
				bay = setBayArray(cp.getBay().split(str));
				if(null !=bay && bay.length >0) {
					for(int i=0; i<bay.length; i++) {
						bayList.add(bay[i]);
					}
				}
				cp.setBayList(bayList);
			}
			
			map.put(cp.getCraneNum(), new ArrayList<WorkRecordVo>());
		}
		List<WorkRecordVo> list = workRecordDao.getAlreadyWorkRecordList(cranePreparationList, state);	
		
		if(null !=list && list.size() >0) {
			/*
			List<VesselContainerVo> vcList = wl_vesselContainerService.getVesselPositionByVesselNumber(cranePreparationList);
			int vcListSize = vcList.size();
			*/
			//设置预配bay位
			for(WorkRecordVo wr : list) {
				/*
				for(int i=0; i<vcListSize; i++) {
					if(wr.getVesselNumber().equals(vcList.get(i).getVesselNumber()) && null !=wr.getUpdateContaid() && wr.getUpdateContaid().equals(vcList.get(i).getContainerNumber())) {
						wr.setVesselPosition(vcList.get(i).getVesselPosition());
						break;
					}
				}*/
				if(null !=wr.getBayInfo() && 7 == wr.getBayInfo().length()) {
					if(wr.getBayInfo().equals(wr.getVesselPosition())) {
						wr.setBayState(2); //2表示实装BAY不为空并且和预配BAY相等
					}else {
						wr.setBayState(1);//1表示实装BAY不为空，并且和预配BAY不相等
					}
				}else {
					wr.setBayState(0); //0表示实装BAY为空
				}
			}	
			//根据岸桥编号分组
			for(WorkRecordVo workRecord : list) {
				map.get(workRecord.getCraneNum()).add(workRecord);
			}
		}
		return map;
	}
	@Override
	public WorkRecordVo getNewAlreadyWorkRecordById(WorkRecordVo workRecord) {
		return workRecordDao.getAlreadyWorkRecordById(workRecord);
	}
	/**
	 *  根据id查询已理货数据信息
	 * @param 查询条件 workRecordVo
	 * 2019-10-22
	 * @return
	 */
	@Override
	public WorkRecordVo getAlreadyWorkRecordById(WorkRecordVo workRecord) {
		workRecord = workRecordDao.getAlreadyWorkRecordById(workRecord);	
		if(null !=workRecord && null !=workRecord.getSeqNo()) {
			workRecord.setPassTime(DateUtil.dateParse("yyyy-MM-dd HH:mm:ss", workRecord.getPassTime()));
			List<String> vesselNumberList = new ArrayList<String>();
			vesselNumberList.add(workRecord.getVesselNumber());
			//获取alongside,in_voyage, out_voyage
			List<BerthPlanInfoVo> bpList = wl_berthPlanService.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
			if(null !=bpList && bpList.size()>0) {
				BerthPlanInfoVo bp = bpList.get(0);
				workRecord.setAlongside(bp.getAlongside());
				workRecord.setInVoyage(bp.getInVoyage());
				workRecord.setOutVoyage(bp.getOutVoyage());
				workRecord.setVesselNameEn(bp.getVesselNameEn());
			}
			//根据seqNo查询图片记录信息
			List<ImgInfoVo> imgList = workRecordDao.getImgInfoBySeqNo(workRecord.getSeqNo());
			if(null !=imgList && imgList.size()>0) {
				//过滤残损图片
				List<ImgInfoVo> newImgList = new ArrayList<ImgInfoVo>();
				for(ImgInfoVo img : imgList) {
					if(4!= img.getSnapImgType()) {
						newImgList.add(img);
					}
				}
				if(null !=newImgList && newImgList.size() >0) {
					//设置图片
					//workRecord.setImgUrl(this.getImgUrl(newImgList));
					//workRecord.setImgUrl(this.getNewImgUrl(newImgList));
					this.setListImgUrl(workRecord,newImgList);
					
					//设置图片对应的MP4
					List<String> mp4List = new ArrayList<String>();
					String pathName = null;
					String str = ",";
					String path = null;
					String mp4 = null;
					String nweMp4 = null;
					String areaNum = workRecord.getAreaNum();
					for(ImgInfoVo img :newImgList) {
						pathName = img.getImgPathName();
						if(null !=pathName) {
							String strName[] = pathName.split(str);
							if(strName.length >0) {
								for(int i=0; i<strName.length; i++) {
									path = strName[i];
									if(null !=path && path.trim().length() >0) {
										mp4 = path.replace(".jpg", ".mp4");
										//nweMp4 = img_Path + mp4.substring(mp4.indexOf(NICT)-1);
										nweMp4 = img_Path + mp4.substring(mp4.indexOf(areaNum)-1);
										//判断MP4文件是否存在
										if(ImgUtil.existsFile(nweMp4)) {
											mp4List.add(mp4);
										}
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
			//'DS'为'卸船', 'DD'为'直提', 'LD'为'装船', 'DL'为'直装', 'LN'为'捣卸', 'RS'为'捣装', 'SH'为'搬移', 'DT'为'中转不落地'
			if(1 == workRecord.getWorkType()) {
				vesselContainer.setJobType("DS");
			}else {
				vesselContainer.setJobType("LD");
				
			}
			vesselContainer = this.getVesselContainer(vesselContainer);
			if(null !=vesselContainer) {
				workRecord.setPortLoading(vesselContainer.getPortLoading());	//装货港
				workRecord.setPortDischarge(vesselContainer.getPortDischarge());	//卸货港
				workRecord.setPortDestination(vesselContainer.getPortDestination());	//目的港
				workRecord.setVesselPosition(vesselContainer.getVesselPosition());	//预配bay位
				workRecord.setStuffingStatus(vesselContainer.getStuffingStatus());	// 装载状态	重（F、full）、空（E、empty）
			}
			/*
			 * 2021-01-23
			if(!(null !=workRecord.getBayInfo() && 7 == workRecord.getBayInfo().length())) {
				workRecord.setBayInfo("");
			}
			*/
		}
		return workRecord;
	}
	
	/**
	 * 获取 装货港，卸货港，目的港
	 * 'DS'为'卸船', 'DD'为'直提', 'LD'为'装船', 'DL'为'直装', 'LN'为'捣卸', 'RS'为'捣装', 'SH'为'搬移', 'DT'为'中转不落地'
	 * @param vesselCode   船舶代码
	 * @param vesselNumber 船舶艘次号
	 * @return
	 */
	@Override
	public VesselContainerVo getVesselContainer(VesselContainerVo vesselContainer) {
		VesselContainerVo vc = null;
		if(null !=vesselContainer.getVesselCode() && null !=vesselContainer.getVesselNumber() && 
				null !=vesselContainer.getContainerNumber() && null !=vesselContainer.getJobType()) {
			List<VesselContainerVo> vcList = wl_vesselContainerService.getVesselContainerList(vesselContainer);
			if(null !=vcList && vcList.size()>0) {
				vc =  vcList.get(0);
				if(null == vc.getVesselPosition() || vc.getVesselPosition().length() <7) {
					vesselContainer.setVesselPosition("0000000"); 		//预配bay位
					vc.setVesselPosition("0000000"); 		//预配bay位
				}			
			}
		}		
		return vc;
	}
	/**
	 *第三方调用接口
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@Override
	public List<WorkRecordVo> getWorkRecordData(WorkRecordVo workRecordVo) {
		return workRecordDao.getWorkRecordData(workRecordVo);
	}
	/**
	 * 更新X,Y坐标
	 * 第三方调用接口
	 * @param workRecordVo
	 * @return
	 */
	
	@Transactional
	@Override
	@SuppressWarnings("static-access")
	public int updateWorkRecordXY(WorkRecordVo workRecordVo) {
		int count = 0;
		//根据作业编号查询作业记录
		//List<WorkRecordVo> wrList = this.getWorkRecordBySeqNo(workRecordVo.getSeqNo());
		WorkRecordVo wr = workRecordDao.getWorkRecordId(workRecordVo.getSeqNo());
		//如果作业编号对应的作业记录不存在则线程睡眠50毫秒后再次查询
		if(null ==wr) {
			try {
				logger.info("更新X,Y坐标时在作业表里没有找到作业编号为："+workRecordVo.getSeqNo()+"的记录，延迟50毫秒后二次查询！");
				Thread.currentThread().sleep(50);// 毫秒
                wr = workRecordDao.getWorkRecordId(workRecordVo.getSeqNo());
                if(null ==wr) {
                	logger.info("更新X,Y坐标时在作业表里没有找到作业编号为："+workRecordVo.getSeqNo()+"的记录，延迟50毫秒后三次查询！");
    				Thread.currentThread().sleep(50);// 毫秒
                    wr = workRecordDao.getWorkRecordId(workRecordVo.getSeqNo());
                }
            } catch (InterruptedException e) {
            	logger.error("延迟执行失败！" + e.getMessage());
            }
		}
		if(null !=wr) {
			//更新X,Y坐标
			if(null !=workRecordVo.getPointX() && null !=workRecordVo.getPointY()) {
				count = workRecordDao.updateWorkRecordXY(workRecordVo);			
			}
			//解析图片并新增数据到数据库
			FileInfoJson fileInfoJson = workRecordVo.getFileInfoJson();
			if(null !=fileInfoJson) {
				ImgInfoVo imgInfoVo = this.getImgInfoVo(wr.getId(), workRecordVo.getSeqNo(), fileInfoJson);
				if(null !=imgInfoVo) {
					List<ImgInfoVo> imgList = new ArrayList<ImgInfoVo>();
					imgList.add(imgInfoVo);
					//插入图片信息记录
					count = this.insertImgInfo(imgList);
				}
			}					
		}else {
			logger.info("更新X,Y坐标时在作业表里没有找到作业编号为："+workRecordVo.getSeqNo()+"的记录，查询了三次，中间延迟了两次50毫秒！");
		}	
		return count;
	}
	/**
	 * 新增历史作业数据
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertHistoricalWorkRecord(WorkRecordVo workRecordVo) {
		return workRecordDao.insertWorkRecord(workRecordVo);
	}
	/**
	 * 批量插入作业记录
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertWorkRecordList(List<WorkRecordVo> list) {
		return workRecordDao.insertWorkRecordList(list);
	}
	/**
	 * 更新历史作业数据
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateHistoricalWorkRecord(WorkRecordVo workRecordVo){
		return workRecordDao.updateHistoricalWorkRecord(workRecordVo);
	}
	/**
	 * 更新状态
	 * 状态（0表未理货，1表示已理货，2表示数据异常,10为作废数据）
	 * @param workRecordVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateWorkRecordState(WorkRecordVo workRecordVo) {
		return workRecordDao.updateWorkRecordState(workRecordVo);
	}
	/**
	 * 数据重复校验
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public int getCountRecord(WorkRecordVo workRecordVo) {
		return workRecordDao.getCountRecord(workRecordVo);
	}
	/**
	 * 数据重复校验
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public List<WorkRecordVo> getRecord(WorkRecordVo workRecordVo) {
		return workRecordDao.getRecord(workRecordVo);
	}
	/**
	 * 设置残损类型
	 * @param diList
	 * @return
	 */
	/*private String getDamage(List<DamageInforRecordVo> diList) {
		String damage = null;
		if(null !=diList && diList.size() >0) {
			StringBuilder sb = new StringBuilder();
			String str = "/";
			for(DamageInforRecordVo di :diList) {
				sb.append(di.getDamagedType()).append(str);
			}
			damage = sb.toString();
			if(!"".equals(damage)) {
				damage = damage.substring(0, damage.length()-1);
			}
		}
		return damage;
	}*/
	/**
	 * 设置图片URL
	 * @param diList
	 * @return
	 */
/*	private String getImgUrl(List<ImgInfoVo> imgList) {
		String urlStr = null;
		if(null !=imgList && imgList.size() >0) {
			StringBuilder sb = new StringBuilder();
			String str = ",";
			for(ImgInfoVo img :imgList) {
				if(null !=img.getImgPathName()) {
					sb.append(img.getImgPathName()).append(str);
				}
			}
			urlStr = sb.toString();
			if(!"".equals(urlStr)) {
				urlStr = urlStr.substring(0, urlStr.length()-1);
			}
		}
		return urlStr;
	}*/
	
	/**
	 * 设置图片URL
	 * 如果图片还没有FTP到服务器上则把URL替换成工控机上的URL
	 * @param diList
	 * @return
	 * 2020-08-22 更新
	 */
	private void setListImgUrl(WorkRecordVo workRecord,List<ImgInfoVo> imgList) {
		if(null !=imgList && imgList.size() >0) {
			//大图
			StringBuilder sb = new StringBuilder();
			//小图
			StringBuilder sb2 = new StringBuilder();
			String str = ",";
			String pathName = null;
			String imgUrl = null;
			String imgName = null;
			String areaNum = workRecord.getAreaNum();
			String craneNum = workRecord.getCraneNum();
			String controlIp = null; 		//中控机IP(默认为250服务器地址)
			CraneInfoVo craneInfoVo = craneInfoService.getCraneInfoByCraneNum(craneNum);
			if(null !=craneInfoVo && null !=craneInfoVo.getControlIp()) {
				controlIp = craneInfoVo.getControlIp();
			}
			for(ImgInfoVo img :imgList) {
				if(null !=img.getImgPathName()) {
					pathName = img.getImgPathName();
					String[] strarray = pathName.split(",");
					if(strarray.length >0) {
						for(int i=0;i<strarray.length;i++) {
							if(strarray[i].length() >6) {
								//imgUrl = img_Path + strarray[i].substring(strarray[i].indexOf(NICT)-1);
								imgUrl = img_Path + strarray[i].substring(strarray[i].indexOf(areaNum)-1);
								//如果文件存在
								if(ImgUtil.existsFile(imgUrl)) {
									sb.append(strarray[i]).append(str);
									
									//检查缩略图是否存在
									imgUrl = imgUrl.replace(JPG, COMPRESS_JPG);
									if(ImgUtil.existsFile(imgUrl)) {
										sb2.append(strarray[i].replace(JPG, COMPRESS_JPG)).append(str);
									}else {
										imgName = this.getUrlReplace(strarray[i],areaNum, craneNum,controlIp);
										sb2.append(imgName.replace(JPG, COMPRESS_JPG)).append(str);
									}
								}else {
									imgName = this.getUrlReplace(strarray[i],areaNum, craneNum,controlIp);
									sb.append(imgName).append(str);
									sb2.append(imgName.replace(JPG, COMPRESS_JPG)).append(str);
								}
							}
						}
					}					
				}
			}
			//大图
			String urlStr = sb.toString();
			if(!"".equals(urlStr)) {
				urlStr = urlStr.substring(0, urlStr.length()-1);
				workRecord.setImgUrl(urlStr);
			}
			//小图
			String compressUrl = sb2.toString();
			if(!"".equals(compressUrl)) {
				compressUrl = compressUrl.substring(0, compressUrl.length()-1);
				workRecord.setCompressUrl(compressUrl);
			}
			
		}
	}
	/**
	 * IP替换
	 * @param url
	 * @return
	 */
	private String getUrlReplace(String url,String areaNum, String craneNum,String controlIp) {
		if(null !=controlIp) {
			//参数
			//String url1 = "http://192.168.1.250:5005/NICT/QC15/20200811/20200811103913801/20200811103913801_SeaL_box.jpg";
			//更新后返回的字符串
			//String url2 = "http://192.168.1.5:5005/QC15/20200811/103913801/20200811103913801_SeaL_box.jpg";
			String ip = url.substring(7,url.lastIndexOf(":"));
			//String newUrl =url.substring(0,url.indexOf("QC")).replace(ip, newIp).replace("/"+areaNum, "")+craneNum;
			String newUrl =url.substring(0,url.indexOf(craneNum)).replace(ip, controlIp).replace("/"+areaNum, "")+craneNum;
			
			int index = url.substring(0,url.lastIndexOf("/")).lastIndexOf("/");
			String imgName = url.substring(index);
			
			return newUrl+imgName.substring(0,9)+"/"+imgName.substring(9);
		}else {
			return url;
		}		
	}
	
	/**
	 * 组装作业记录集合
	 * @param workJson
	 * @return
	 */
	private List<WorkRecordVo> getWorkRecordList(WorkJson workJson){
		List<WorkRecordVo> list = null;
		if(null !=workJson) {
			list = new ArrayList<WorkRecordVo>();
			//货柜箱信息
			CcrResultJson ccr_result = workJson.getCcr_result();
			List<ContaInforJson> conta_resultList = null;
			if(null !=ccr_result) {
				conta_resultList = ccr_result.getConta_result(); 
			}
			//如果货柜箱信息不为空
			if(null !=conta_resultList && conta_resultList.size() >0) {
				//残损信息
				DamagedResultJson damaged_result = workJson.getDamaged_result();
				int damagedNum =  damaged_result.getCom_damaged_num();//"com_damaged_num": 0,   //残损数量
				List<DamagedInfoJson> damaged_infoList = null;
				if(null !=damaged_result) {
					damaged_infoList = workJson.getDamaged_result().getDamaged_info(); //残损信息
				}			
				//贝位识别结果信息
				BayResultJson bay_result = workJson.getBay_result();
				List<BayInfoJson> bayInfoList = null;
				if(null !=bay_result) {
					bayInfoList = bay_result.getBay_info();	//贝位信息
				}
				int size = conta_resultList.size(); //货箱总记录
				int container_type = workJson.getContainer_type(); //箱类型 0：长箱,1：短箱,2：双箱,10：未知
				//如果是0：长箱,1：短箱，则只取第一条记录
				if(0 == container_type || 1 == container_type) {
					size = 1;					
				}
				String updateContaid = null;
				String note = null;
				//设置作业信息数据
				for(int i=0; i<size; i++) {
					ContaInforJson contaInfor = conta_resultList.get(i);
					if(null !=contaInfor) {
						WorkRecordVo wr = this.getWorkRecord(workJson);						 
						//箱序号(0为'F'前箱；1为'A'后箱；2为'M'长箱；5为其他)
						note = contaInfor.getNote();
						if("F".equals(note)) {
							wr.setOrderid(0);
						}else if("A".equals(note)) {
							wr.setOrderid(1);
						}else if("M".equals(note)) {
							wr.setOrderid(2);
						}else{
							wr.setOrderid(5);
						}
						wr.setContaid(contaInfor.getId()); //箱号	string	11个长度字符串
						wr.setUpdateContaid(contaInfor.getId());
						updateContaid = contaInfor.getUpdateId();
						if(null !=updateContaid && !"".equals(updateContaid)) {
							wr.setUpdateContaid(updateContaid);
						}						
						wr.setIso(contaInfor.getIso());
						wr.setIscheck(String.valueOf(contaInfor.isCheck()));
						wr.setDoorDir(i);  //箱门朝向	0：左；1：右
						wr.setDoorLock(i); //是否有铅封，0:lock,1:unlock
						String bayInfo = this.getBayInfo(i, bayInfoList,wr.getCraneNum());
						wr.setBay(bayInfo);
						wr.setBayInfo(bayInfo); //贝位		
						wr.setDamaged(contaInfor.getDamaged()); //是否残损
						wr.setState(""+contaInfor.getState());
						if(damagedNum > 0) {
							wr.setDamaged(this.isDamaged(i, damaged_infoList)); //是否残损
						}else if(null == wr.getDamaged() || "".equals(wr.getDamaged())){
							wr.setDamaged("false"); //是否残损
						}
						
						wr.setVesselPosition(contaInfor.getVesselPosition());			//预配BAY
						wr.setPortLoading(contaInfor.getPortLoading());				//装货港
						wr.setPortDischarge(contaInfor.getPortDischarge());			//卸货港
						wr.setPortDestination(contaInfor.getPortDestination());			//目的港
						wr.setStuffingStatus(contaInfor.getStuffingStatus());			//空重状态 重（F，full）,重（E，empty）
						wr.setDangerSigns(contaInfor.getDangerSigns());  			//危险标志（0为非危险品，1为危险品）
						wr.setDangerClass(contaInfor.getDangerClass());				//危险类型
						wr.setContainerClass(contaInfor.getContainerClass());			//箱类型（危品箱，普通箱，冷藏箱）
						list.add(wr);
					}
				}
			}			
		}
		return list;
	}
	//获取贝位信息
	private String getBayInfo(int i, List<BayInfoJson> bayInfoList,String craneNum) {
		String note = null;
		if(null !=bayInfoList && bayInfoList.size() >0 && i<=bayInfoList.size()-1) {
			BayInfoJson bayInfo = bayInfoList.get(i);
			if(null !=bayInfo && !LR.equals(bayInfo.getNote())) {
				note = bayInfo.getNote();
			}
		}
		/**
		 * 2019-12-17更新
		 * 防止BAY为空或者小于3位
		 */
		if(null == note || "".equals(note) || note.length()<3) {
			List<CranePreparationVo> list = cranePreparationService.getCranePreparation(craneNum);
			if(null !=list && list.size() >0) {
				CranePreparationVo cp= list.get(0);
				if(null !=cp) {
					note = cp.getBay();
				}
			}
		}
		return note;
	}
	/**
	 * 是否残损
	 * @param conta_index
	 * @param damaged_infoList
	 * @return
	 */
	private String isDamaged(int conta_index, List<DamagedInfoJson> damaged_infoList) {
		String isOk = "false"; //默认为"false"不残损
		if(null !=damaged_infoList && damaged_infoList.size()>0){
			for(int i=0,size=damaged_infoList.size(); i<size; i++) {
				if(null !=damaged_infoList.get(i) && conta_index == damaged_infoList.get(i).getConta_index()){
					isOk = "true"; //残损
					break;
				}
			}
		}
		return isOk;
	}
	
	/**
	  * 解析作业信息记录数据
	 * @param workJson
	 * @return
	 */
	private WorkRecordVo getWorkRecord(WorkJson workJson) {
		WorkRecordVo wr = null;
		if(null !=workJson) {
			wr = new WorkRecordVo();
			wr.setSeqNo(workJson.getSeq_no());
			wr.setAreaNum(workJson.getArea_num());
			wr.setCraneNum(workJson.getCrane_num());
			wr.setLaneNum(Integer.parseInt(workJson.getLane_num()));    //此字段类型待确认
			wr.setWorkType(workJson.getWork_type());
			wr.setPlate(workJson.getPlate_result().getP_result().getPlate());
			//车顶号
			wr.setTopPlate(workJson.getTpplate_result().getTp_result().getTop_plate());
			//理货员更新后的车顶号
			wr.setUpdateTopPlate(workJson.getTpplate_result().getTp_result().getTop_plate());
			String updateTopPlate = workJson.getTpplate_result().getTp_result().getUpdate_top_plate();
			if(null !=updateTopPlate && !"".equals(updateTopPlate)) {
				//理货员更新后的车顶号
				wr.setUpdateTopPlate(updateTopPlate);
			}
			wr.setContainerType(workJson.getContainer_type());
			wr.setVesselCode(workJson.getVessel_code());
			wr.setVesselNumber(workJson.getVessel_number());
			if(null !=workJson.getVessel_name_cn()) {
				wr.setVesselNameCn(workJson.getVessel_name_cn());
			}else {
				wr.setVesselNameCn("船名待更新");
			}
			wr.setTallyClerk(workJson.getTally_clerk());
			wr.setState("0"); //待定默认0
			/*String state = workJson.getState();
			if(null !=state && !"0".equals(state)) {
				wr.setState(state); //是否理货（0表示未理货，1表示已理货）
			}*/
			
			PicDataJson plc_data = workJson.getPlc_data();		//吊具信息
			if(null != plc_data) {
				wr.setPointX(""+workJson.getPlc_data().getX());// //吊具海陆位置X
				wr.setPointY(""+workJson.getPlc_data().getY());// //吊具海陆位置Y
			}
			if(null !=workJson.getCcr_result()) {
				wr.setContaWeight(workJson.getCcr_result().getConta_weight());
			}
			wr.setPassTime(workJson.getPasstime());
			//alarmState;	//是否报警（0为不报警，1为报警）
			wr.setAlarmState(workJson.getAlarm_state());
		}
		return wr;
	}
	/**
	 * 残损信息记录数据
	 * @param workId  前箱生成的作业ID
	 * @param workId2 后箱生成的作业ID
	 * @param workJson
	 * @return
	 */
	private List<DamageInforRecordVo> getDamageInforRecord(WorkJson workJson,List<WorkRecordVo> workList) {
		List<DamageInforRecordVo> diList = null;	//返回的结果集
		DamagedResultJson damaged_result = workJson.getDamaged_result();   //验残信息
		if(null !=damaged_result) {
			//残损信息
			List<DamagedInfoJson> damaged_infoList = damaged_result.getDamaged_info(); 
			int damagedNum = damaged_result.getCom_damaged_num();	//残损数量
			//残损信息不为空
			if(damagedNum >0 && null !=damaged_infoList && damaged_infoList.size() >0) {
				diList = new ArrayList<DamageInforRecordVo>();
				int wSize = workList.size();
				for(int i=0,size=damaged_infoList.size(); i<size; i++) {
					//限制一个箱只有一条残损数据
					if(i == wSize) {
						break;
					}
					DamagedInfoJson damagedInfo = damaged_infoList.get(i);
					if(null !=damagedInfo) {
						DamageInforRecordVo dr = new DamageInforRecordVo();//new DamageInforRecordVo();
						WorkRecordVo workRecord = null;
						//箱号坐标，0前箱，1后箱
						/*
						 * 20200513
						if(0 == damagedInfo.getConta_index()) {
							workRecord = workList.get(0);
							
						}else if(1 == damagedInfo.getConta_index() && workList.size() >1){
							workRecord = workList.get(1);
						}*/
						if(0 ==i) {
							workRecord = workList.get(0);
						}if(1 ==i && wSize >1) {
							workRecord = workList.get(1);
						}
						if(null != dr) {
							dr.setWorkId(workRecord.getId());
							dr.setSeqNo(workJson.getSeq_no());
							dr.setContainerNumber(workRecord.getUpdateContaid());
							if(0 == damagedInfo.getDamaged_type()) {
								//默认为其他类型
								dr.setDamagedTypeId(8);	//残损类型
							}else {
								dr.setDamagedTypeId(damagedInfo.getDamaged_type());	//残损类型
							}							
							dr.setTrust(damagedInfo.getTrust());	//残损程度							
							//dr.setPosition(damagedInfo.getPosition());//残损位置
							dr.setPositionId(1); //残损位置临时默认为“左侧” 2020-05-13
							
							dr.setCreateName(AUTO);
							diList.add(dr);
						}
					}
				}
			}
		}
		return diList;
	}
	/**
	 * 解析图片信息
	 * @param workId
	 * @param workJson
	 * @return
	 */
	private List<ImgInfoVo> getImgInfoList(int workId, WorkJson workJson){
		List<ImgInfoVo> list = null;
		if(null !=workJson) {
			list = new ArrayList<ImgInfoVo>();
			String seqNo = workJson.getSeq_no(); //唯一任务编号
			//货柜箱识别结果
			CcrResultJson cr = workJson.getCcr_result();
			if(null !=cr) {
				ImgInfoVo imgInfoVo = this.getImgInfoVo(workId, seqNo, cr.getFile_info());
				if(null !=imgInfoVo) {
					list.add(imgInfoVo);
				}
			}
			//车辆检测结果
			TpplateResultJson tr = workJson.getTpplate_result(); 
			if(null !=tr) {
				ImgInfoVo imgInfoVo = this.getImgInfoVo(workId, seqNo, tr.getFile_info());
				if(null !=imgInfoVo) {
					list.add(imgInfoVo);
				}
			}
			//车牌识别结果
			PlateResultJson pr = workJson.getPlate_result(); 
			if(null !=pr) {
				ImgInfoVo imgInfoVo = this.getImgInfoVo(workId, seqNo, pr.getFile_info());
				if(null !=imgInfoVo) {
					list.add(imgInfoVo);
				}
			}
			//验残信息
			DamagedResultJson dr = workJson.getDamaged_result();   
			if(null !=dr) {
				ImgInfoVo imgInfoVo = this.getImgInfoVo(workId, seqNo, dr.getFile_info());
				if(null !=imgInfoVo) {
					list.add(imgInfoVo);
				}
			}
			//贝位识别信息
			BayResultJson br = workJson.getBay_result();   
			if(null !=br) {
				ImgInfoVo imgInfoVo = this.getImgInfoVo(workId, seqNo, br.getFile_info());
				if(null !=imgInfoVo) {
					list.add(imgInfoVo);
				}
			}
		}
		return list;
	}
	/**
	 * 图片信息记录数据
	 * @param workId
	 * @return
	 */
	private ImgInfoVo getImgInfoVo(int workId, String seqNo, FileInfoJson fileInfoJson) {
		ImgInfoVo imgInfo = null;
		if(null !=fileInfoJson && fileInfoJson.getImg_num() >0) {
			String split = ",";
			imgInfo = new ImgInfoVo();
			imgInfo.setWorkId(workId);
			imgInfo.setSeqNo(seqNo);
			imgInfo.setLocation(this.arrayToString(fileInfoJson.getLocation(),split));
			imgInfo.setSnapImgType(fileInfoJson.getSnap_img_type());
			imgInfo.setImgNum(fileInfoJson.getImg_num());
			imgInfo.setImgPathName(this.arrayToString(fileInfoJson.getImg_path_name(),split));
			imgInfo.setImgDectRect(this.arrayToString(fileInfoJson.getImg_dect_rect(),"/"));
		}		
		return imgInfo;
	}
	/**
	 * 数组转字符串
	 * @param arr
	 * @param split ("/")
	 * @return
	 */
	private String arrayToString(String[] arr,String split) {
		String str = null;
		if(null !=arr && arr.length >0) {
			StringBuilder sb = new StringBuilder();
			String s = "";
			for(int i = 0;i<arr.length;i++){
				if(null !=arr[i] && !s.equals(arr[i])) {
					sb.append(arr[i]+split);
				}
			}
			str = sb.toString();
			if(!"".equals(str)) {				
				str = str.substring(0,str.length()-1);
			}
		}
		return str;
	}
	
	/**
	 * 如果是两个BAY，则把中间的那个BAY补上
	 * @param bay
	 * @return
	 */
	private String[] setBayArray(String[] bay) {
		String[] newBayArray = new String[3];
		if(null !=bay && bay.length == 2) {
			int bay1 = Integer.parseInt(bay[0]);
			int bay2 = Integer.parseInt(bay[1]);
			
			newBayArray[0] = bay[0];
			newBayArray[1] = bay[1];
			int newBay = 0;
			if(bay1 > bay2) {
				newBay = bay2 +1;
			}else {
				newBay = bay1 +1;
			}
			if(newBay <10) {
				newBayArray[2] = "00"+newBay;
			}else {
				newBayArray[2] = "0"+newBay;
			}
			return newBayArray;
		}else {
			return bay;
		}
	}
	
	/**
	 * 1查询BAY位是否已经存在
	 * @param workRecordVo
	 * @return
	 */
	@Override
	public int getCountBay(WorkRecordVo workRecordVo) {
		return workRecordDao.getCountBay(workRecordVo);
	}
	@Override
	public int getNewCountBay(String vesselNumber, List<String> bayInfoList) {
		return workRecordDao.getNewCountBay(vesselNumber, bayInfoList);
	}
}
