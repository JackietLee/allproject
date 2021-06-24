package net.pingfang.serviceImpl.workRecord;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.workRecord.CranePreparationDao;
import net.pingfang.entity.role.Tuser;
//import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.work.CranePreparationVo;
//import net.pingfang.service.vessel.BerthPlanService;
import net.pingfang.service.workRecord.CranePreparationService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.service.async.AsyncService;
//import net.pingfang.service.ocr.WL_BerthPlanService;
/**
 * 岸桥配制信息ServiceImpl
 * @author Administrator
 * @since 2019-06-17
 *
 */
@Service
public class CranePreparationServiceImpl implements CranePreparationService {
	@Autowired
	private CranePreparationDao cranePreparationDao;
	//@Autowired
	//private BerthPlanService berthPlanService;
	@Autowired
	private AsyncService asyncService;
//	@Autowired
//	private WL_BerthPlanService wl_berthPlanService;
	
	@Autowired
	private HttpSession session;
	
	private final static Logger logger = LoggerFactory.getLogger(CranePreparationServiceImpl.class); 
	/**
	 * 获取所有岸桥配制信息
	 * @return
	 */
	@Override
	public List<CranePreparationVo> getAllCranePreparationList() {
		List<CranePreparationVo> newList = new ArrayList<CranePreparationVo>();
		List<CranePreparationVo> list = cranePreparationDao.getAllCranePreparationList();
		if(null !=list && list.size()>0) {
			String str = " / ";
			boolean isOk = false;
			for(CranePreparationVo cp : list) {
				if(newList.size()>0) {
					isOk = false;
					for(int i=0; i<newList.size(); i++) {
						if(cp.getCraneNum().equals(newList.get(i).getCraneNum()) && cp.getVesselNumber().equals(newList.get(i).getVesselNumber())) {
							if(Integer.parseInt(cp.getBay()) < Integer.parseInt(newList.get(i).getBay())) {
								newList.get(i).setBay(cp.getBay()+str+newList.get(i).getBay());
							}else {
								newList.get(i).setBay(newList.get(i).getBay()+str+cp.getBay());
							}
							isOk = true;
							break;
						} 
					}
					if(!isOk){
						newList.add(cp);
					}
				}else {
					newList.add(cp);
				}				
			}
		}
		return newList;
	}

	/**
	 * 插入一条岸桥配制信息
	 * @param cranePreparationVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertCranePreparation(CranePreparationVo cranePreparationVo) {
		logger.info(this.getUserName()+"插入岸桥配制信息");
		int count = 0;
		String bay = cranePreparationVo.getBay();
		if(null !=bay) {
			String[] bayStr = bay.split(",");
			 
			if(null !=bayStr && bayStr.length >0) {
				int bayLength = bayStr.length;
				CranePreparationVo cp = null;
				int workMode = cranePreparationVo.getWorkMode();
				if(0 == workMode) {
					workMode = 1;
				}
				List<CranePreparationVo> cpList = new ArrayList<CranePreparationVo>(bayLength);
				for(int i=0; i<bayLength; i++) {
					cp = new CranePreparationVo();
					cp.setVesselNameCn(cranePreparationVo.getVesselNameCn());
					cp.setVesselNameEn(cranePreparationVo.getVesselNameEn());
					cp.setVesselCode(cranePreparationVo.getVesselCode());
					cp.setVesselNumber(cranePreparationVo.getVesselNumber());
					cp.setCraneNum(cranePreparationVo.getCraneNum());
					cp.setBay(bayStr[i]);
					cp.setIsAuto(cranePreparationVo.getIsAuto());
					cp.setWorkMode(workMode);
					cp.setWorkType(cranePreparationVo.getWorkType());
					cp.setAlongside(cranePreparationVo.getAlongside());
					cp.setInVoyage(cranePreparationVo.getInVoyage());
					cp.setOutVoyage(cranePreparationVo.getOutVoyage());
					cp.setType(cranePreparationVo.getType());
					
					cp.setStevedoreId(cranePreparationVo.getStevedoreId());	//装卸工ID
					cp.setShipPosition(cranePreparationVo.getShipPosition());	// 1甲板2船舱
					
					if(null == cp.getType() || "".equals(cp.getType())) {
						cp.setType("1");
					}
					cpList.add(cp);
				}
//				BerthPlanInfoVo berthPlanInfo = new BerthPlanInfoVo();
//				berthPlanInfo.setVesselNumber(cranePreparationVo.getVesselNumber());
//				berthPlanInfo.setAlongside(cranePreparationVo.getAlongside());
				//更新船舷方向
				//count = berthPlanService.updateAlongside(berthPlanInfo);
				count =  cranePreparationDao.insertCranePreparation(cpList);
				logger.info(this.getUserName()+"完成岸桥配制信息插入");
			}
		}
		return count;
	}
	/**
	 * 删除一条岸桥配制信息
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteCranePreparation(int id) {
		logger.info(this.getUserName()+"删除id为"+id+"岸桥配制信息");
		return cranePreparationDao.deleteCranePreparation(id);
	}
	/**
	 * 删除一条岸桥配制信息
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteCranePreparationByCraneNum(CranePreparationVo cranePreparationVo){
		logger.info(this.getUserName()+"删除岸桥配制信息"+JsonUtil.javaToJsonStr(cranePreparationVo));
		return cranePreparationDao.deleteCranePreparationByCraneNum(cranePreparationVo);
	}
	/**
	 * 根据岸桥编号获取一条岸桥配制信息
	 * @param craneNum
	 * @return
	 */
	@Override
	public List<CranePreparationVo> getCranePreparation(String craneNum) {
		return cranePreparationDao.getCranePreparation(craneNum);
	}
	/**
	 * 根据岸桥编号批量获取岸桥配制信息
	 * @param craneNumList
	 * @return
	 */
	@Override
	public List<CranePreparationVo> getListCranePreparation(List<String> craneNumList, String type){
		return cranePreparationDao.getListCranePreparation(craneNumList, type);
	}
	
	/**
	 * 获取所有岸桥信息 
	 * 下拉列表框 
	 * @return
	 */
	@Override
	public List<CranePreparationVo> getCraneInfoList(){
		List<CranePreparationVo> newList = null;
		List<CranePreparationVo> list = cranePreparationDao.getCraneInfoList();
		if(null !=list && list.size() >0) {
			newList = new ArrayList<CranePreparationVo>();
			boolean isOk = false;
			String str = ";";
			List<String> vesselNumberList = new ArrayList<String>();
			for(CranePreparationVo cp : list) {
				if(0 == newList.size()) {
					newList.add(cp);
					vesselNumberList.add(cp.getVesselNumber());
				}else {
					isOk = false;
					for(int i=0;i<newList.size(); i++) {
						if(cp.getCraneNum().equals(newList.get(i).getCraneNum())) {
							//把两个Bay合并
							newList.get(i).setBay(newList.get(i).getBay()+str+cp.getBay());
							isOk = true;
							break;
						}
					}
					if(!isOk) {
						newList.add(cp);
						vesselNumberList.add(cp.getVesselNumber());
					}
				}
				
			}
			/*
			 * 2021-01-04
			List<BerthPlanInfoVo> bpList = wl_berthPlanService.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
			if(null !=bpList && bpList.size() >0) {
				//设置alongside, in_voyage, out_voyage
				for(CranePreparationVo cp : newList) {
					for(int i=0,bpSize=bpList.size(); i<bpSize; i++) {
						if(cp.getVesselNumber().equals(bpList.get(i).getVesselNumber())) {
							cp.setInVoyage(bpList.get(i).getInVoyage());
							cp.setOutVoyage(bpList.get(i).getOutVoyage());
							break;
						}
					}
				}
			}
			*/
		}
		return newList;
	}
	/**
	 * 获取所有没被引用的岸桥信息
	 * 下拉列表框 
	 * @return
	 */
	@Override
	public List<CraneInfoVo> getNotUsedCraneInfoList(){
		return cranePreparationDao.getNotUsedCraneInfoList();
	}
	/**
	 * 查询岸桥配制是否已经存在
	 * @return
	 */
	@Override
	public int getCountCranePreparationByCraneNum(String craneNum) {
		return cranePreparationDao.getCountCranePreparationByCraneNum(craneNum);
	}
	/**
	 * 根据岸桥编号更新BAY
	 * @return
	 */
	@Transactional
	@Override
	public int updateBayByCraneNum(CranePreparationVo cranePreparationVo) {
		int count = 0;
		/*
		String craneNum = cranePreparationVo.getCraneNum();
		//List<CranePreparationVo> list = this.getCranePreparation(craneNum);
		//if(null !=list && list.size()>0) {
		//	CranePreparationVo cp = list.get(0);
			String bay = cranePreparationVo.getBay();
			if(null !=bay) {
				List<CranePreparationVo> cpList = new ArrayList<CranePreparationVo>();
				CranePreparationVo newCp = null;
				String[] bayStr = bay.split(",");
				for(int i=0; i<bayStr.length; i++) {
					newCp = new CranePreparationVo();
					newCp.setCraneNum(craneNum);
					newCp.setBay(bayStr[i]);
					newCp.setVesselNameCn(cranePreparationVo.getVesselNameCn());
					newCp.setVesselNameEn(cranePreparationVo.getVesselNameEn());
					newCp.setVesselCode(cranePreparationVo.getVesselCode());
					newCp.setVesselNumber(cranePreparationVo.getVesselNumber());
					cpList.add(newCp);					
				}
				//删除
				count = this.deleteCranePreparationByCraneNum(cranePreparationVo);
				//新增
				count = cranePreparationDao.insertCranePreparation(cpList); 
			}
		//}		
			*/
//		BerthPlanInfoVo berthPlanInfo = new BerthPlanInfoVo();
//		berthPlanInfo.setVesselNumber(cranePreparationVo.getVesselNumber());
//		berthPlanInfo.setAlongside(cranePreparationVo.getAlongside());
		//更新船舷方向
		//count = berthPlanService.updateAlongside(berthPlanInfo);
		cranePreparationVo.setType("1");
		//删除
		logger.info(this.getUserName()+"删除岸桥配制信息"+JsonUtil.javaToJsonStr(cranePreparationVo));
		count = this.deleteCranePreparationByCraneNum(cranePreparationVo);
		//新增
		logger.info(this.getUserName()+"新增岸桥配制信息"+JsonUtil.javaToJsonStr(cranePreparationVo));
		count = this.insertCranePreparation(cranePreparationVo); 
		return count;
	}
	/**
	 * 调用第三方接口同步数据
	 * 亮哥的接口
	 * @return
	 */
	@Override
	public boolean berthPlansSynchronization() {
		boolean isOk = false;
		try {
			//调用第三方API更新外理数据库BAY
			ResponseEntity<String> respMsg = asyncService.doGet("/etlservice/berth_plans/synchronization");
			if(null !=respMsg) {
				 String objStr = respMsg.getBody();										
				 if(null !=objStr && !"".equals(objStr)) {
					 JSONObject returnObj = new JSONObject(objStr);
					 if(200 == (Integer)returnObj.get("status")) {
						 isOk = true;
						 logger.info("*********************************/etlservice/berth_plans/synchronization调用成功!*********************************");
					}
				}
			}
		}catch(JSONException e) {
			logger.error("调用接口/etlservice/berth_plans/synchronization出错："+e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			logger.error("调用接口/etlservice/berth_plans/synchronization出错："+e.getMessage());
			e.printStackTrace();
		}		
		return isOk;
	}
	/**
	 * 根据船舶代码"vessel_code"获取船舷方向
	 * @param vesselCood
	 * @return
	 */
	@Override
	public String getAlongsideByVesselCood(String vesselCood) {
		return cranePreparationDao.getAlongsideByVesselCood(vesselCood);
	}
	/**
	 * 更新BayWidth
	 * @param list
	 * @return
	 */
	@Override
	public int updateBayWidth(CranePreparationVo cranePreparationVo) {
		logger.info(this.getUserName()+"更新BayWidth"+JsonUtil.javaToJsonStr(cranePreparationVo));
		return cranePreparationDao.updateBayWidth(cranePreparationVo);
	}
	/**
	 * 获取登录用户名
	 * @return
	 */
	private String getUserName() {
		String userName = null;
		Object objUser = session.getAttribute("currentUser");
		if(null !=objUser) {
			Tuser currentUser = (Tuser)objUser;
			userName = currentUser.getUserName();
		}
		return userName;
	}

}
