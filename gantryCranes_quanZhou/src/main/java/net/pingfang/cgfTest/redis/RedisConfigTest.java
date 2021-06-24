package net.pingfang.cgfTest.redis;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.netty.util.CharsetUtil;
import net.pingfang.config.redis.RedisKeyUtil;
import net.pingfang.config.redis.RedisService;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Resource
    private RedisService redisService;

    @Test
    public void testObj() throws Exception{
    	
        UserVo userVo = new UserVo();
        userVo.setAddress("上海");
        userVo.setName("测试dfas");
        userVo.setAge(123);
        System.out.println(userVo.toString());
        ValueOperations<String,Object> operations = redisTemplate.opsForValue();
        redisService.expireKey("name",20, TimeUnit.SECONDS);
        String key = RedisKeyUtil.getKey(UserVo.Table,"name",userVo.getName());
        UserVo vo = (UserVo) operations.get(key);
        System.out.println(vo);
    }

    @Test
    public void testValueOption( )throws  Exception{
        UserVo userVo = new UserVo();
        userVo.setAddress("上海123");
        userVo.setName("jantent123");
        userVo.setAge(23);
        valueOperations.set("test",userVo);

        System.out.println(valueOperations.get("test"));
    }

    @Test
    public void testSetOperation() throws Exception{
        UserVo userVo = new UserVo();
        userVo.setAddress("北京");
        userVo.setName("jantent");
        userVo.setAge(23);
        UserVo auserVo = new UserVo();
        auserVo.setAddress("n柜昂周");
        auserVo.setName("antent");
        auserVo.setAge(23);
        setOperations.add("user:test",userVo,auserVo);
        Set<Object> result = setOperations.members("user:test");
        System.out.println(result);
        
        UserVo userVo2 = new UserVo();
        userVo2.setAddress("北京2");
        userVo2.setName("jantent2");
        userVo2.setAge(23);
        UserVo auserVo3 = new UserVo();
        auserVo3.setAddress("n柜昂周3");
        auserVo3.setName("antent3");
        auserVo3.setAge(23);
        setOperations.add("user:test2",userVo2,auserVo3);
        Set<Object> result2 = setOperations.members("user:test2");
        System.out.println(result2);
    }

    @Test
    public void HashOperations() throws Exception{
        UserVo userVo = new UserVo();
        userVo.setAddress("北京");
        userVo.setName("jantent");
        userVo.setAge(23);
        hashOperations.put("hash:user",userVo.hashCode()+"",userVo);
        System.out.println(hashOperations.get("hash:user",userVo.hashCode()+""));
    }

    @Test
    public void  ListOperations() throws Exception{
      /*  UserVo userVo = new UserVo();
        userVo.setAddress("北京");
        userVo.setName("jantent");
        userVo.setAge(23);
        listOperations.leftPush("list:user",userVo);
       
    	for(int i=0; i<10; i++) {
    		 UserVo userVo2 = new UserVo();
    	     userVo2.setAddress("北京"+i);
    	     userVo2.setName("jantent"+i);
    	     userVo2.setAge(23);
    	     listOperations.leftPush("list:user2",userVo2.toString());
    	     
//    	     JSONObject msgObj = new JSONObject();
// 			msgObj.put("messageType", "MJ01"+i);
// 			msgObj.put("area", "NICT"+i);
// 			msgObj.put("driverCode", "driverCode"+i);
// 			listOperations.leftPush("list:user4",msgObj);
    	}
        */
        Object obj = listOperations.leftPop("list:user");
        if(null !=obj) {
        	UserVo user = (UserVo)obj;
        	UserVo user2 = (UserVo)obj;
        }
        System.out.println(obj);
        // pop之后 值会消失
     //   System.out.println(listOperations.leftPop("list:user2"));
    }
    // redisTemplate.getConnectionFactory().getConnection().publish(topic.getBytes(CharsetUtil.UTF_8), gson.toJson(messageVO).getBytes());
   
    @Test
    public void publishs() {
    	//redisTemplate.getConnectionFactory().getConnection().publish("QC13".getBytes(CharsetUtil.UTF_8), "广播消息测试".getBytes());
    	redisTemplate.convertAndSend("QC13", "广播消息测试".getBytes());
    }
    
    @Test
    public void t() {
    	//redisTemplate.getConnectionFactory().getConnection().subscribe("QC13", b);
    	
    }
    
    @Test
    public void  leftPops() throws Exception{
    	/*for(Object obj : listOperations.range("list:user", 0, -1)) {
    		System.out.println(obj);
    	}*/
    	// System.out.println(listOperations.leftPop("list:user2",100000000,TimeUnit.SECONDS));
    	 while (true) {
    		 if(redisService.existsKey("list:user2")) {
    			 //Object obj = listOperations.leftPop("list:user2");
    			 Object obj = listOperations.rightPop("list:user2");
    	    	 if(null !=obj) {
    	    		 System.out.println(obj);
    	    	 }
    		 }else {
	    		 System.out.println("*********** 没有数据，阻塞2秒钟 *********");
	    		 Thread.sleep(1000);
	    	 }
	    	 
    	 }
    	 //System.out.println(obj);
    }
    
    
    @Test
    public void  boundListOperations() throws Exception{
    	BoundListOperations boundListOperations = redisTemplate.boundListOps("list:user2");  
    	
    	//获取绑定的键中的值  
    	boundListOperations.range(0,-1).forEach(v -> System.out.println("获取绑定的键中的值" + v));
    }
}