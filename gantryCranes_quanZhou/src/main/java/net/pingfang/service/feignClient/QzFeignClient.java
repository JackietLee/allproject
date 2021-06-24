package net.pingfang.service.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.pingfang.entity.qz.DirectContainerAndCar;
import net.pingfang.entity.user.StevedoreUserVo;
import net.pingfang.entity.vessel.TruckInfoVo;
import net.pingfang.handler.DataResult;

/**
 * 泉州异步调用Service
 * @author Administrator
 * @since 2021-01-10
 *
 */
@FeignClient(value = "user", url = "${wlApiUrl}")
public interface QzFeignClient {
	/**
	 * 获取装卸工用户下拉列表
	 * @param vesselVoyageNumber
	 * @return
	 */
	@RequestMapping(value = "/etlservice/workerInfo", method = RequestMethod.GET)
	public DataResult<List<StevedoreUserVo>> getStevedoreOptions(@RequestParam("vesselVoyageNumber") String vesselVoyageNumber);

	/**
	 * 获取泉州托车号信息
	 * @param jobType
	 * @return
	 */
	@RequestMapping(value = "/etlservice/direct/directCarList", method = RequestMethod.GET)
	public DataResult<List<TruckInfoVo>> getQzTruckInfoList(@RequestParam("jobType")String jobType, @RequestParam("vesselVoyageNumber") String vesselVoyageNumber);
	/**
	 * 判断直装直提是否合法
	 * @param directContainerAndCar
	 * @return
	 */
	@RequestMapping(value = "/etlservice/direct/isDirectLegal", method = RequestMethod.POST)
	public DataResult<DirectContainerAndCar> isDirectLegal(@RequestBody DirectContainerAndCar directContainerAndCar);

}
