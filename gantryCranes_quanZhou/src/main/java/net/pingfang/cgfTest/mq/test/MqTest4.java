package net.pingfang.cgfTest.mq.test;

import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
//@Controller
public class MqTest4 {
	
	 int i=1;
	 //@Autowired
	// private RabbitTemplate rabbitTemplate;
	 
	//定时5秒给页面推一次数据
	   // @Scheduled(cron="0/5 * * * * ?")
	  //  @Scheduled(cron="0/2 * * * * ?")
	    public void callback() throws Exception {
	    	String str = "消息测试" +i;
	    	//Message message = new Message(str.getBytes(),new MessageProperties());
	    	/**
	    	 * 1.	数据服务收到车号预识别报文处理完后需要通知WEB前端广播；
2.	API将作业数据入库后需要通知前端刷新实时作业数据；
3.	API更新BAY位后需要通知前端刷新BAY位图；
4.	拖车号识别错误
5.设备维护
	    	 */
	    	Message message = null;
	    	String msg = null;
	    	//if(i == 1) {
	    		//数据服务收到车号预识别报文处理完后需要通知WEB前端广播；
	    		msg = "{\"count_i\":"+i+",\"seq_no\":\"20200827141903789\",\"area_num\":\"NICT\",\"tpplate_result\":{\"car_dir\":\"right\",\"tp_result\":{\"trust\":99,\"note\":\"\",\"update_top_plate\":\"214\",\"top_plate\":\"214\"},\"file_info\":{\"note\":\"\",\"img_dect_rect\":[\"\"],\"img_path_name\":[\"\"],\"location\":[\"\"],\"img_num\":0,\"snap_img_type\":0}},\"passtime\":\"2020-08-27T14:19:03\",\"message_type\":\"16\",\"crane_num\":\"QC13\",\"plc_data\":{\"x\":0,\"std_sea_x\":0,\"y\":0,\"std_land_x\":0},\"serviceID\":\"serviceCode01\",\"lane_num\":\"4\"}";
	    		message = new Message(msg.getBytes(),new MessageProperties());
	    		System.out.println("成功发送："+msg);
	    	//}
	    	/*
	    	else if(i == 2) {
	    		//API将作业数据入库后需要通知前端刷新实时作业数据；
	    	}else if(i == 3) {
	    		//API更新BAY位后需要通知前端刷新BAY位图；
	    	}else if(i == 4) {
	    		//拖车号识别错误
	    	}else if(i == 5) {
	    		//设备维护
	    		
	    		i=0;
	    	}*/
	    	//广播消息
	        //rabbitTemplate.send(exchange,"",message);
	       // rabbitTemplate.send("fanoutServiceToWedExchange","fanout",message);
	        
	    	i++;
	    	System.out.println("成功发送："+str);
	    }
	    public static void main(String[] args) {
	    	Message s = new Message(null, null);
	    	String str = "aaaaaaaa";
			Object objMst = "{s:"+str+"}";
			JSONObject jsonJobData = new JSONObject(JSON.toJSON(objMst).toString());
			jsonJobData.put("msgType", 123);
			System.out.println(jsonJobData.toString());
	    }

}
