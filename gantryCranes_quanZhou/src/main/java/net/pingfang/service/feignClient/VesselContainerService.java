package net.pingfang.service.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.VesselContainerVo;
//import net.pingfang.entity.work.CranePreparationVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.DataResult;

/**
* 船舶集装箱Dao
* @author Administrator
* @since 2019-06-01
*
*/
@FeignClient(value = "user", url = "${wlApiUrl}")
//@FeignClient(value = "user", url = "http://192.168.1.20:18070")
public interface VesselContainerService {
	/**
	 * 分页获取所有船舶集装箱
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 VesselContainerVo
	 * @return
	 */
//	@RequestMapping(value = "/etlservice/outer_link_vessel_container/page/container", method = RequestMethod.POST)
//	public DataResult<PageVo<VesselContainerVo>> getPageVesselContainerList(@RequestBody VesselContainerVo vesselContainer);
	/**
	 * 获取所有船舶集装箱记录总数
	 * @return
	 */
	//public int getCountVesselContainer(Map<String, Object> map);
	
	/**
	 * 匹配仓单
	 * 根据船舶艘次号，集装箱号获取一条船舶集装箱
	 * @param vesselNumber
	 * @param containerNumber
	 * @return
	 */
	//@RequestMapping(value = "/etlservice/outer_link_vessel_container/container", method = RequestMethod.POST)
	//public DataResult<List<VesselContainerVo>> getVesselContainerByVesselNumber(@RequestBody MatchingBillVO matchingBillVO);
	
	/**
	 * 获取集装箱在船上的位置
	 * @param vesselContainerVo
	 * @return
	 */
	@RequestMapping(value = "/etlservice/outer_link_vessel_container/container/bay", method = RequestMethod.POST)
	public DataResult<List<VesselContainerVo>> getVesselContainerList(@RequestBody VesselContainerVo vesselContainerVo);
	
	@RequestMapping(value = "/etlservice/outer_link_vessel_container/containers", method = RequestMethod.GET)
	public DataResult<List<VesselContainerVo>> getContainerList(@RequestParam("vesselVoyageNumber") String vesselNumber,@RequestParam("vesselCode") String vesselCode,@RequestParam("jobType") String jobType,@RequestParam ("containerNumbers")List<String> vesselContainerList);
	/**
	 * 更新贝位
	 * @param vesselContainer
	 * @return
	 */
	//@RequestMapping(value = "/etlservice/", method = RequestMethod.POST)
	//public DataResult<Integer> updateBayInfo(@RequestBody VesselContainerVo vesselContainer);
	
	//@RequestMapping(value = "/etlservice/", method = RequestMethod.POST)
	//public DataResult<Integer> getCountContainer(@RequestBody VesselContainerVo vesselContainer);
	
	
	
	
	
	/**
	 * WorkRecordDao
	 *作业数据统计
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	//@RequestMapping(value = "/etlservice/", method = RequestMethod.POST)
	//public DataResult<List<WorkRecordVo>> getWorkRecordStatistics(@RequestBody WorkRecordVo workRecordVo);
	/**
	 * WorkRecordDao
	 *集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
	 *集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	 * @param 查询条件 workRecordVo
	 * @return
	 */
	@RequestMapping(value = "/etlservice/outer_link_vessel_container/jobAmount", method = RequestMethod.POST)
	public DataResult<Integer> getMountStatistics(@RequestBody List<WorkRecordVo> list, @RequestParam("jobType")String jobType);
	/**
	 * WorkRecordDao
	 * @param cranePreparationList
	 * @return
	 */
//	@RequestMapping(value = "/etlservice/outer_link_vessel_container/vesselVoyageNumber", method = RequestMethod.POST)
//	public DataResult<List<VesselContainerVo>> getVesselPositionByVesselNumber(@RequestBody List<CranePreparationVo> cranePreparationList);
	
	
	
}
