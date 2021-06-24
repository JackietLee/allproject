package net.pingfang.controller.huangPu;

import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import net.pingfang.config.redis.RedisService;
import net.pingfang.service.huangPu.HpWorkRecordService;


/**
 * 1,黄埔作业数据监听
 * @author Administrator
 *
 */
@Component
@Order(value = 1)
public class HpWorkRecordListen{
//public class HpWorkRecordListen implements CommandLineRunner{
	
	@Autowired
	private HpWorkRecordService hpWorkRecordService;
	
	
	@Autowired
    private ListOperations<String, Object> listOperations;
	//leo 2020-10-29
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	private ListOperations<String, String> opsForList;
	
	@Resource
    private RedisService redisService;
	
	 //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
    @Resource(name = "singleThreadExecutor")
    private ExecutorService singleThreadExecutor;
	
	private static final String LIST_HPMJ_START_WORKMSG_RETURN = "List_hpMj_start_workMsg_return";   //存入Redis的门机开始作业报文
	final Logger log = LoggerFactory.getLogger(HpWorkRecordListen.class);

	/*
	 * 2020-12-17
	 * 
	@Override
	public void run(String... args) throws Exception {
		//leo 2020-10-29
		opsForList = redisTemplate.opsForList();
		new Thread(new MessageJobThread_Mj()).start();
		log.info(">>>>>>>>>>>>>>>门机作业数据监听服务已启动  ListeningHpWorkRecord order 1 <<<<<<<<<<<<<");
	}
	*/
	
	/**
     * brpop 作业识别报文处理线程
     *
     * @author Administrator
     */
    class MessageJobThread_Mj extends Thread {
    	//@SuppressWarnings("static-access")
        public void run() {
            while (true) {
                try {
//                	//在没有任务的时候阻塞,设置timeout=0,即是有list中有任务来到的时候就会自动将任务pop出去,由consumer消费掉
                	//log.info("等待Redis作业");
                	if(redisService.existsKey(LIST_HPMJ_START_WORKMSG_RETURN)) {
                		//Object obj = listOperations.rightPop(LIST_HPMJ_START_WORKMSG_RETURN)
                		String s = opsForList.rightPop(LIST_HPMJ_START_WORKMSG_RETURN);
                		log.info("Redis原始报文:{}",s);
	           	    	 if(null !=s) {
	           	    		// System.out.println(obj);
	           	    		singleThreadExecutor.execute(
		                        	new Thread(){  
		                        		@Override  
		                                public void run() {  
		                        			hpWorkRecordService.insertHpWorkRecord(s);
		                                }  
		                           }
		                       );
	           	    	 }else {
	           	    		log.error("*********** 门机作业报文为空 ************");
	           	    	 }
                	}else {
                		//log.info("*********** 没有门机作业数据，阻塞2秒钟 *********");
                		Thread.sleep(2000);
                	}
                } catch (Exception e) {
                    log.info("黄埔门机作业报文失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
