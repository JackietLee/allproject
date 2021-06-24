package net.pingfang.controller.qz;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.user.StevedoreUserVo;
import net.pingfang.handler.DataResult;
import net.pingfang.handler.Result;
import net.pingfang.service.feignClient.QzFeignClient;
import net.pingfang.utils.ResultUtil;

/**
 * 获取泉州数据Controller
 * @author Administrator
 * @since 2021-10-11
 *
 */
@Api("QzDataController Api")
@RestController
@RequestMapping("/qzData")
public class QzDataController {
	
	@Autowired
	private QzFeignClient qzFeignClient;
	
	private final static Logger logger = LoggerFactory.getLogger(QzDataController.class);
		
	/**
	 *获取装卸工用户下拉列表
	 * @return
	 */
	@ApiOperation(value="获取装卸工用户下拉列表", notes="获取装卸工用户下拉列表")
	@GetMapping("/getStevedoreOptions")
	@ApiParam(name = "getStevedoreOptions", value = "船泊艘次号")
	@RequiresPermissions(value = {"getStevedoreOptions"})
	public Result<Object> getStevedoreOptions(@RequestParam("vesselVoyageNumber") String vesselVoyageNumber) {
//		List<StevedoreUserVo> list = new ArrayList<StevedoreUserVo>();
//		for(int i=0; i<10; i++) {
//			StevedoreUserVo s = new StevedoreUserVo();
//			s.setWorkerId("00"+i+1);
//			s.setWorkerName("name"+i);
//			list.add(s);
//		}
		DataResult<List<StevedoreUserVo>> data = qzFeignClient.getStevedoreOptions(vesselVoyageNumber);
		if(null !=data) {
			return ResultUtil.success(data.getData());
		}else {
			return ResultUtil.error(1,"没有获取到数据！");
		}
		
	}
	
}
