package net.pingfang.service.websoket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import net.pingfang.cgfTest.redis.UserVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.json.work.WorkJson;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.serviceImpl.workRecord.WorkRecordServiceImpl;
/**
 * websocket测试前端控制器
 */
//@Controller
public class SoketIoTest {
//	@Autowired
//	private WorkRecordService workRecordService;
//	@Autowired
//	private SocketIoClient_Bay SocketIoClient_Bay;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	int i=0;
    //定时5秒给页面推一次数据
//    @Scheduled(cron="0/5 * * * * ?")
    //@Scheduled(cron="0/1 * * * * ?")
    public void callback() throws Exception {
    	System.out.println("AAAAAAAAAAAAAAAAAAAAA");
        //SocketIoClient_Bay.addJobQueueDate("0060606","10","QC13","1");
    	//String str = "{\"message_type\":\"03\",\"area_num\":\"NICT\",\"seq_no\":\"20200805162921076\",\"passtime\":\"2020-08-05T16:29:21\",\"crane_num\":\"QC19\",\"work_type\":1,\"container_type\":1,\"lane_num\":\"1\",\"vessel_code\":\"\",\"vessel_number\":\"\",\"vessel_name_cn\":\"\",\"tally_clerk\":\"\",\"state\":\"0\",\"is_lock\":\"false\",\"lock_user\":\"\",\"lock_time\":\"\",\"ccr_result\":{\"door_dir\":[\"UNKNOW\",\"\"],\"door_lock\":[null,null],\"conta_weight\":0,\"conta_result\":[{\"id\":\"BMOU112628\",\"updateId\":\"BMOU112628\",\"iso\":\"22G1\",\"check\":false,\"trust\":60.0,\"note\":\"F\"},{\"id\":\"\",\"updateId\":\"\",\"iso\":\"\",\"check\":true,\"trust\":100.0,\"note\":\"\"}],\"file_info\":{\"note\":\"\",\"snap_img_type\":1,\"img_num\":8,\"location\":[\"SeaL\",\"SeaM\",\"landM\",\"landR\",\"contCL0\",\"contCR0\",\"contCL2\",\"contCR2\",null,null],\"img_path_name\":[\"20200805162921076_SeaL_0.jpg\",\"20200805162921076_SeaM_0.jpg\",\"20200805162921076_landM_0.jpg\",\"20200805162921076_landR_0.jpg\",\"20200805162921076_contCL0_0.jpg\",\"20200805162921076_contCR0_0.jpg\",\"20200805162921076_contCL2_TP_ex.jpg\",\"20200805162921076_contCR2_TP_ex.jpg\",null,null],\"img_dect_rect\":[null,null,null,null,null,null,null,null,null,null]}},\"tpplate_result\":{\"car_dir\":\"\",\"tp_result\":{\"top_plate\":\"\",\"update_top_plate\":null,\"trust\":0.0,\"note\":\"\"},\"file_info\":{\"note\":\"\",\"snap_img_type\":3,\"img_num\":0,\"location\":[null,null],\"img_path_name\":[null,null],\"img_dect_rect\":[null,null]}},\"plate_result\":{\"p_result\":{\"plate\":\"\",\"plate_color\":\"\",\"trust\":0.0,\"note\":\"\"},\"file_info\":{\"note\":\"\",\"snap_img_type\":0,\"img_num\":0,\"location\":[\"\"],\"img_path_name\":[null,null],\"img_dect_rect\":[null,null]}},\"damaged_result\":{\"com_damaged_num\":0,\"damaged_info\":[null,null,null,null,null,null,null,null],\"file_info\":{\"note\":\"\",\"snap_img_type\":4,\"img_num\":0,\"location\":[null,null,null,null,null,null,null,null],\"img_path_name\":[null,null,null,null,null,null,null,null],\"img_dect_rect\":[null,null,null,null,null,null,null,null]}},\"bay_result\":{\"bay_info\":[{\"x\":0,\"y\":0,\"z\":0,\"note\":\"LR\"}],\"file_info\":{\"note\":\"\",\"snap_img_type\":0,\"img_num\":1,\"location\":[\"\"],\"img_path_name\":[\"20200805162921076_overViewCAM_start_work.jpg\"],\"img_dect_rect\":[\"\"]}},\"plc_data\":{\"std_sea_x\":0,\"std_land_x\":0,\"x\":4018,\"y\":-95},\"alarm_state\":1}";
    	
//    	String str = "{\"seq_no\":\"20200819130059266\",\"area_num\":\"NICT\",\"tally_clerk\":\"Auto\",\"vessel_code\":\"PNHX339\",\"alarm_state\":1,\"work_type\":1,\"message_type\":\"03\",\"ccr_result\":{\"conta_result\":[{\"trust\":0,\"updateId\":\"Null1\",\"note\":\"M\",\"iso\":\"\",\"id\":\"Null1\",\"check\":false,\"state\":0}],\"file_info\":{\"note\":\"\",\"img_dect_rect\":[null,null,null,null,null,null,null,null],\"img_path_name\":[\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_SeaL_box.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_SeaM_box.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_landM_box.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_landR_box.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_contCL0_box.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_contCL0_closeup.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_contCR0_box.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_contCR0_closeup.jpg\"],\"location\":[\"SeaL\",\"SeaM\",\"landM\",\"landR\",\"contCL0\",\"contCL0\",\"contCR0\",\"contCR0\"],\"img_num\":8,\"snap_img_type\":1},\"conta_weight\":0,\"door_dir\":[\"UNKNOW\",\"\"],\"door_lock\":[null,null]},\"lock_user\":\"\",\"vessel_number\":\"4103118\",\"lock_time\":\"\",\"tpplate_result\":{\"car_dir\":\"left\",\"tp_result\":{\"trust\":0,\"note\":\"\",\"update_top_plate\":\"\",\"top_plate\":\"\"},\"file_info\":{\"note\":\"\",\"img_dect_rect\":[null,null],\"img_path_name\":[\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_contCL0_TP.jpg\",\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_contCR0_TP.jpg\"],\"location\":[\"contCL0\",\"contCR0\"],\"img_num\":2,\"snap_img_type\":3}},\"damaged_result\":{\"damaged_info\":[null,null,null,null,null,null],\"file_info\":{\"note\":\"\",\"img_dect_rect\":[null,null,null,null,null,null],\"img_path_name\":[null,null,null,null,null,null],\"location\":[null,null,null,null,null,null],\"img_num\":0,\"snap_img_type\":4},\"com_damaged_num\":0},\"bay_result\":{\"bay_info\":[{\"note\":\"LR\",\"x\":0,\"y\":0,\"z\":0}],\"file_info\":{\"note\":\"\",\"img_dect_rect\":[\"\"],\"img_path_name\":[\"http://192.168.1.250:5005/NICT/QC18/20200819/20200819130059266/20200819130059266_overViewCAM_start_work.jpg\"],\"location\":[\"\"],\"img_num\":1,\"snap_img_type\":0}},\"passtime\":\"2020-08-19T13:00:59\",\"vessel_name_cn\":\"平南华翔339\",\"container_type\":0,\"plate_result\":{\"file_info\":{\"note\":\"\",\"img_dect_rect\":[null,null],\"img_path_name\":[null,null],\"location\":[\"\"],\"img_num\":0,\"snap_img_type\":0},\"p_result\":{\"trust\":0,\"note\":\"\",\"plate_color\":\"\",\"plate\":\"\"}},\"is_lock\":\"false\",\"crane_num\":\"QC18\",\"state\":0,\"plc_data\":{\"x\":-1,\"std_sea_x\":0,\"y\":-1,\"std_land_x\":0},\"serviceID\":\"serviceCode01\",\"lane_num\":\"1\"}";
//    	WorkJson workJson = JSON.parseObject(str, WorkJson.class);
//    	
//    	List<WorkRecordVo> list = workRecordService.insertWorkRecord(workJson,"insert");
//    	System.out.println(list.size());
//    	
// 
//    	SocketIoClient_Bay.addJobQueueDate("insertWorkRecord","9","QC18",JSON.toJSON(list).toString());
    	
    	WorkRecordVo workRecordVo = new WorkRecordVo();
    	workRecordVo.setId(i);
    	workRecordVo.setCraneNum("门机101");
    	workRecordVo.setContaid("test123456"+i);
    	workRecordVo.setVesselNumber("666666");
    	workRecordVo.setVesselNameCn("大船测试");
//    	SocketIoClient_Bay.addJobQueueDate("insertWorkRecord_hpMj","8","门机101",JSON.toJSON(workRecordVo).toString());
    	System.out.println("JSON.toJSON(workRecordVo).toString()");
    	i++;
    	//测试BAY位更新
    	//SocketIoClient_Bay.addJobQueueDate("updateBay","10","QC31", "");
    }
    
    
    /**
     * 测试广播消息
     */
  //  @Scheduled(fixedRate = 5000)
  //  @Scheduled(cron="0/5 * * * * ?")
    public void pubMsg() {
     UserVo user=new UserVo();
     user.setName("cgf TEST"+i);
     user.setAddress("深圳"+i);
     
     redisTemplate.convertAndSend("phone", user);
     i++;
//     log.info("Publisher sendes Topic... ");
//     return "success";
    }
}
