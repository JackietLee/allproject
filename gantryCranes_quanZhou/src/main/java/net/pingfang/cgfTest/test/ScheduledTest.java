package net.pingfang.cgfTest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import net.pingfang.dao.test.CraneInfoDaoTest;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.workRecord.WorkRecordService;
import net.pingfang.utils.DateUtil;
/**
 * websocket测试前端控制器
 */
@Controller
public class ScheduledTest {
	@Autowired
	private WorkRecordService workRecordService;
	int i = 0;
	/*
	@Autowired
	private CraneInfoDaoTest craneInfoDaoTest;
	
	int count = 0;
	int qc = 1;
	
	private final static Logger logger = LoggerFactory.getLogger(ScheduledTest.class);
	
    //定时60秒给页面推一次数据
    @Scheduled(cron="0/60 * * * * ?")
    //@Scheduled(cron="0/1 * * * * ?")
    public void callback() throws Exception {
    	logger.info("callback: "+count);
    	CraneInfoVo craneInfo = new CraneInfoVo();
    	craneInfo.setCreatetime(DateUtil.getDate("yyyyMMddHHmmsssss"));
    	craneInfo.setCraneNameCn("NICT");
    	craneInfo.setCraneNameEn("NICT");
    	craneInfo.setCraneNum("QC"+qc);
    	craneInfo.setType("1");
    	craneInfo.setControlIp("12.37.236.209");
    	craneInfo.setControlPort(8080);
    	
    	craneInfoDaoTest.insertCraneInfoTest(craneInfo);
    	
    	qc ++;
    	count ++;
    	if(40 == qc) {
    		qc = 1;
    	}
    }
   */ 
	
	
	 //定时60秒给页面推一次数据
    //@Scheduled(cron="0/60 * * * * ?")
    @Scheduled(cron="0/2 * * * * ?")
    public void callback() throws Exception {
    	WorkRecordVo workRecordVo = new WorkRecordVo();
		 workRecordVo.setId(1821);
		 workRecordVo.setWorkType(1);
		 workRecordVo.setTallyClerk("张飞"+i);
		 workRecordVo.setDangerSigns(1);
//		 int count = workRecordService.updateWorkRecordById02(workRecordVo);
//	        System.out.println(count);
	        i++;
    }
    
}
