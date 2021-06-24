package net.pingfang.controller.vessel;

import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.pingfang.config.mq.MQConfig;
import net.pingfang.config.mq.SendToExchange;
import net.pingfang.handler.Result;
import net.pingfang.utils.DateUtil;
import net.pingfang.utils.ResultUtil;

/**
 * 车牌信息Controller
 * @author Administrator
 * @since 2020-09-23
 *
 */
@Api("Plate Api")
@RestController
@RequestMapping("/plate")
public class PlateController {
	
	 @Autowired
     private MQConfig mqConfig;
	
	// @Resource(name = "connectionMQ")
	// private Connection connection;

	 //创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程
	 @Resource(name = "cachedThreadPool")
	 private ExecutorService cachedThreadPool;
	    
	 final Logger log = LoggerFactory.getLogger(PlateController.class);
	 
	/**
	 *录入车牌号
	 * @param 岸桥编号
	 * @param 车牌号
	 * @param 车道号
	 * @return
	 */
	@ApiOperation(value="录入车牌号", notes="录入车牌号")
	@PostMapping("/inputPlateInfo")
	@RequiresPermissions(value = {"inputPlateInfo"})
	public Result<Object> inputPlateInfo(String craneNum,String plate,String laneNum) {
		if(null !=craneNum && null !=plate && null !=laneNum) {
			System.out.println(craneNum+"  "+plate+"  "+laneNum);
			cachedThreadPool.execute(new MqPlateThread(craneNum,plate,laneNum));
			return ResultUtil.success();
		}else {
			return ResultUtil.error(1, "提交的参数不正确！");
		}
		
	}
	
	/**/
	class MqPlateThread implements Runnable {
		private String crane_num;
		private  String top_plate;
		private String lane_num;
		
		public MqPlateThread(String crane_num, String top_plate, String lane_num) {
			this.crane_num = crane_num;
			this.top_plate = top_plate;  
			this.lane_num = lane_num;
		}
		public void run() {
//			Channel channel = null;
			try {
//				channel = connection.createChannel();
				JSONObject msgObj = new JSONObject();
    			msgObj.put("PONT_OF_WORK", crane_num);
    			msgObj.put("VEHICLE_ID", top_plate);
    			msgObj.put("LaneID", lane_num);
    			msgObj.put("UPDATE_TIME", DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
    			
   	    	 	// 发送消息到队列
//   	    	 	channel.basicPublish(mqConfig.getSwitch_name(), mqConfig.getPlate_queue_name(), null, msgObj.toString().getBytes()); 
    			SendToExchange.sendToRabbitMq(mqConfig.getPlate_queue_name()+this.crane_num, msgObj.toString());
			} catch (Exception e) {
				log.error("MQ发送车顶号失败："+e.getMessage());
				e.printStackTrace();
			}
		}    		
	}
	
}
