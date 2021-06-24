package net.pingfang.cgfTest.springBootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.workRecord.WorkRecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

	//@Autowired
	private WorkRecordService workRecordService;
	
	// @Test
	 public void testObj() throws Exception{
		 WorkRecordVo workRecordVo = new WorkRecordVo();
		 workRecordVo.setId(1821);
		 workRecordVo.setWorkType(0);
		 workRecordVo.setTallyClerk("张飞12");
	//	 workRecordVo.setDangerSigns(0);
		 int count = workRecordService.updateWorkRecordById(workRecordVo);
	        System.out.println(count);
	 }
}
