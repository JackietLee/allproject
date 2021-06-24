package net.pingfang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import net.pingfang.service.websoket.StartSoketIo;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 *  岸桥系统入口
 * @author Administrator
 * @since 2020-07-22
 *
 */
@MapperScan("net.pingfang.dao.*")	
@EnableTransactionManagement
@EnableSwagger2
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class GantryCranesApplication {
	final Logger log = LoggerFactory.getLogger(GantryCranesApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(GantryCranesApplication.class, args);
	}
	/**
	 * 1初始化消息服务
	 * @return
	 */
	@Bean
	public StartSoketIo startupRunner() {
		return new StartSoketIo();
	}
	/**
     * 1.创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程
     * 2.当任务数增加时，此线程池又可以智能的添加新线程来处理任务
     * 3.此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小
     * @return
     */
	@Bean(name = "cachedThreadPool")	
	public ExecutorService cachedThreadPool(){
		return Executors.newCachedThreadPool();
	}

	/**
	     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
	     * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     * @return
     */
	@Bean(name = "singleThreadExecutor")	
	public ExecutorService singleThreadExecutor(){
		return Executors.newSingleThreadExecutor();
	}
	/*
	@Bean(name = "jedisPool")	
	public JedisPool jedisPool(){
		//获得池子对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// poolConfig.setMaxActive//可分配多少个jedis实例
		//最大闲置个数
		poolConfig.setMaxIdle(30);
		//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
		poolConfig.setMaxWaitMillis(9000);
		//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。)
		poolConfig.setMinIdle(10);
		//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		poolConfig.setMaxTotal(10000);//最大连接数
		//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		poolConfig.setTestOnBorrow(true);
		log.info("*********************************************jedisPool初始化成功***************************");
		log.info("*********************************************jedisPool初始化成功***************************");
		log.info("*********************************************jedisPool初始化成功***************************");
		return new JedisPool(poolConfig, "127.0.0.1", Integer.parseInt("6379"));
	}
	*/
}
