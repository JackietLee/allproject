package net.pingfang.controller.vessel;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselContainerSeizeSeatService;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;

/**
 *船舶集装箱信息Controller
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Api("VesselContainerSeizeSeat Api")
@RestController
@RequestMapping("/vesselContainer")
public class VesselContainerSeizeSeatController {

	@Autowired
	private VesselContainerSeizeSeatService vesselContainerSeizeSeatService;
	@Autowired
	private WorkRecordService workRecordService;
	
	private final static Logger logger = LoggerFactory.getLogger(VesselContainerSeizeSeatController.class);
	
	/**
	 * 新增集装箱占位
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="新增集装箱占位", notes="新增集装箱占位")
	@ApiParam(name = "vesselContainer", value = "箱信息JSON数据")
	@PostMapping("/insertContainerSeizeSeat")
	@RequiresPermissions(value = {"insertContainerSeizeSeat"})
	public Result<Object> insertContainerSeizeSeat(@RequestBody VesselContainerVo vesselContainer) {
		/**
		 * 2020-11-09
		 * 判断此位置是否已经有箱，只查已理货数据和占位表；（短箱要查两个位置，当前位和当前位加一，长箱要查三个位，当前位和当前位的加一减一）
		 */
		int count = 0;
		if(null !=vesselContainer) {
			//0：长箱,1：短箱,2：双箱,10：未知
			this.setBayList(vesselContainer);
			
			//查询占位箱是否存在
		//	count = vesselContainerSeizeSeatService.getCountContainerSeizeSeat(vesselContainer);
			count = vesselContainerSeizeSeatService.getNewCountContainerSeizeSeat(vesselContainer);
			if(0 == count) {
				//2020-11-09
//				WorkRecordVo workRecordVo = new WorkRecordVo();
//				workRecordVo.setCraneNum(vesselContainer.getCraneNum());
//				workRecordVo.setVesselNumber(vesselContainer.getVesselNumber());
//				//bay+row+tier
//				workRecordVo.setBayInfo(vesselContainer.getStdBay()+vesselContainer.getStdRow()+vesselContainer.getStdTier());
				//查询该位置是否已经有箱了
				//count = workRecordService.getCountBay(workRecordVo);
				List<String> bayList = vesselContainer.getBayList();
				List<String> bayInfoList = new ArrayList<String>();
				for(String bay : bayList) {
					bayInfoList.add(bay+vesselContainer.getStdRow()+vesselContainer.getStdTier());
				}
				count = workRecordService.getNewCountBay(vesselContainer.getVesselNumber(), bayInfoList);
			}
			if(count >0) {
				logger.error(vesselContainer.getContainerNumber()+"新增集装箱占位失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselContainer));
				return ResultUtil.error(1, vesselContainer.getContainerNumber()+"此占位箱已经存在,新增集装箱占位失败！");
			}else {
				//新增集装箱占位
				count = vesselContainerSeizeSeatService.insertContainerSeizeSeat(vesselContainer);
				if(count >0) {
					return ResultUtil.success(vesselContainer.getId());
				}else {
					logger.error(vesselContainer.getContainerNumber()+"新增集装箱占位失败！");
					logger.error(JsonUtil.javaToJsonStr(vesselContainer));
					return ResultUtil.error(1, vesselContainer.getContainerNumber()+"新增集装箱占位失败！");
				}
			}
		}else{
			logger.error("VesselContainerVo入参解析出错！");
			return ResultUtil.error(1, "VesselContainerVo入参解析出错！");
		}		
	}
	/**
	 * 取消集装箱占位
	 * @param workJson
	 * @return
	 */
	@ApiOperation(value="删除集装箱占位", notes="删除集装箱占位")
	@ApiParam(name = "VesselContainerVo", value = "作业信息JSON数据")
	@PostMapping("/deleteContainerSeizeSeat")
	//@RequiresPermissions(value = {"deleteContainerSeizeSeat"})
	public Result<Object> deleteContainerSeizeSeat(@RequestBody VesselContainerVo vesselContainer) {
		int count = 0;
		if(null !=vesselContainer) {
			//更新箱表
			count = vesselContainerSeizeSeatService.deleteContainerSeizeSeat(vesselContainer);
			if(count >0) {
				return ResultUtil.success(vesselContainer.getId());
			}else {
				logger.error(vesselContainer.getContainerNumber()+"删除集装箱占位失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselContainer));
				return ResultUtil.error(1, vesselContainer.getContainerNumber()+"删除集装箱占位失败！");
			}
		}else{
			logger.error("VesselContainerVo入参解析出错！");
			return ResultUtil.error(1, "VesselContainerVo入参解析出错！");
		}		
	}
	
	/**
	 * 对BAY进行加1减1
	 * 0：长箱,1：短箱,2：双箱,10：未知
	 * @param vc
	 * @return
	 */
	private void setBayList(VesselContainerVo vc) {
		String containerType = vc.getContainerType();
		List<String> list = new ArrayList<String>();
		list.add(vc.getStdBay());
		vc.setBayList(list);
		
		int bay = Integer.parseInt(vc.getStdBay());
		if(1 == bay) {
			vc.getBayList().add("00"+(bay +1));
		}else if(bay < 10 && bay >1){
			if(9 == bay) {
				vc.getBayList().add("0"+(bay +1));
			}else {
				vc.getBayList().add("00"+(bay +1));
			}
			if("0".equals(containerType)) {
				vc.getBayList().add("00"+(bay -1));
			}			
		}else if(bay < 100){
			if(99 == bay) {
				vc.getBayList().add(""+(bay +1));
			}else {
				vc.getBayList().add("0"+(bay +1));
			}
			if("0".equals(containerType)) {
				vc.getBayList().add("0"+(bay -1));
			}
		}else{
			vc.getBayList().add(""+(bay +1));
			if("0".equals(containerType)) {
				vc.getBayList().add(""+(bay -1));
			}
		}
	}
}
