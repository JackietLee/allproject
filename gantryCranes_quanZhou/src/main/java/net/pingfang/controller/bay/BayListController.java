package net.pingfang.controller.bay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.role.Tuser;
import net.pingfang.entity.vessel.VesselBayVo;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.handler.Result;
import net.pingfang.service.bay.BayListService;
import net.pingfang.utils.JsonUtil;
import net.pingfang.utils.ResultUtil;
import com.alibaba.fastjson.JSONObject;
/**
 * Bay图批量展示（页面只读）
 * @author caoguofeng
 *
 */
@Api("BayList Api")
@RestController
@RequestMapping("/bay")
public class BayListController {
	
	@Autowired
	private BayListService bayListService;
	@Autowired
	private HttpSession session;
	
	private final static Logger logger = LoggerFactory.getLogger(BayListController.class);
	
	
	
	/**
	 * 查询船图结构数据
	 * 通过bay和VesselCode获取船图结构
	 * @return
	 */
	@ApiOperation(value="查询船图结构数据", notes="查询船图结构数据")
	@ApiParam(name = "getBayList", value = "船舶Bay JSON数据")
	@PutMapping("/getBayList")
	@RequiresPermissions(value = {"getBayList"})
	public Result<Object> getBayList(@RequestBody List<VesselBayVo> vesselBayList) {
		return ResultUtil.success(bayListService.getBayList(vesselBayList));
	}
	
	/**
	 * 批量查询BAY图数据
	 * @param vesselContainerVo
	 * @return
	 */
	@ApiOperation(value="批量查询BAY图数据", notes="批量查询BAY图数据")
	@ApiParam(name = "getBayInfoList", value = "船舶集装箱JSON数据")
	@PutMapping("/getBayInfoList")
	@RequiresPermissions(value = {"getBayInfoList"})
	public Result<Object> getBayInfoList(@RequestBody List<VesselContainerVo> vesselContainerList) {
		if(null !=vesselContainerList && vesselContainerList.size() >0) {
			Map<String,Object> map = bayListService.getBayInfoList(vesselContainerList);
			if(!map.isEmpty()) {
				return ResultUtil.success(map);
			}else {
				logger.error("集装箱的位置在数据库里不存在！");
				return ResultUtil.error(1, "集装箱的位置在数据库里不存在！");
			}
		}else {
			logger.error("参数不正确！");
			return ResultUtil.error(1, "参数不正确！");
		}
	}

	
	/**
	 * 批量插入船图结构数据
	 * @param berthPlanInfo
	 * @return
	 */
	@ApiOperation(value="批量插入船图结构数据", notes="批量插入船图结构数据")
	@ApiParam(name = "map", value = "摄像机信息JSON数据")
	@PostMapping("/insertVesselBayList")
	@RequiresPermissions(value = {"insertVesselBayList"})
	public Result<Object> insertVesselBayList(@RequestBody Map<String,Object> map) {
		List<VesselBayVo> vesselBayList = JSONObject.parseArray(JsonUtil.javaToJsonStr(map.get("vesselBayList")), VesselBayVo.class);
		List<String> bayList = JSONObject.parseArray(JsonUtil.javaToJsonStr(map.get("bayList")), String.class);
		
		if(null !=vesselBayList && null !=bayList) {
			List<VesselBayVo> allVesselBayList = new ArrayList<VesselBayVo>();
			String userName = this.getUserName();
			//String userName = "张飞";
			VesselBayVo vb = null;
			for(String bay : bayList) {
				for(VesselBayVo vesselBay : vesselBayList) {
					vb = new VesselBayVo();
					vb.setVesselName(vesselBay.getVesselName());
					vb.setVesselNumber(vesselBay.getVesselNumber());
					vb.setVesselCode(vesselBay.getVesselCode());
					vb.setRow(vesselBay.getRow());
					vb.setTier(vesselBay.getTier());
					vb.setBay(bay);
					vb.setTallyClerk(userName);
					
					allVesselBayList.add(vb);
				}
			}
			//新增
			int count = bayListService.insertVesselBayList(allVesselBayList);
			if(count >0) {
				return ResultUtil.success(count);
			}else {
				logger.error("批量插入船图结构数据失败！");
				logger.error(JsonUtil.javaToJsonStr(vesselBayList));
				return ResultUtil.error(1, "批量插入船图结构数据失败！");
			}
		}else {
			logger.error("参数不能为空,插入船图结构数据失败！");
			return ResultUtil.error(1, "参数不能为空,插入船图结构数据失败！");
		}
		
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
