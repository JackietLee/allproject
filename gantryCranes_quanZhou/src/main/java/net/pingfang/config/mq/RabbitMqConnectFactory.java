package net.pingfang.config.mq;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

	/**
	 * 获取RabbitMq连接
	 * @author skyfeng
	 */
@Component
public class RabbitMqConnectFactory {
     
    @Autowired
 	private MQConfig config;

 	public static MQConfig mqConfig;
 	
 	static Logger log = LoggerFactory.getLogger(RabbitMqConnectFactory.class);
    /**
     * 缓存连接工厂,将建立的链接放入map缓存
     */
    private static Map<String, ConnectionFactory> connectionFactoryMap = new HashMap<String, ConnectionFactory>();
   
 	
 	@PostConstruct
 	public void init() {
 		mqConfig = config;
 	}
     
     /**
      * 根据rabbitMqName获取一个连接，使用完记得要自己关闭连接 conn.close()
      */
     public static Connection getConnection(String rabbitMqName) {
         if(StringUtils.isEmpty(rabbitMqName)){
             log.error("rabbitMqName不能为空!");
             throw new java.lang.NullPointerException("rabbitMqName为空");
         }
         if(connectionFactoryMap.get(rabbitMqName)==null){
             initConnectionFactory(rabbitMqName);
         }
         ConnectionFactory connectionFactory = connectionFactoryMap.get(rabbitMqName);
         if(connectionFactory==null){
             log.info("没有找到对应的rabbitmq,name={}",rabbitMqName);
         }
         try {
             return connectionFactory.newConnection();
         }catch (Exception e) {
             log.error("创建rabbitmq连接异常！",e);
             return null;
         }
     }
     /**
      * 初始化一个连接工厂
      * @param rabbitMqName
      */
     private static void initConnectionFactory(String rabbitMqName){
         
         try {
             ConnectionFactory factory = new ConnectionFactory();
             //新增代码，如果连接断开会自动重连
             //factory.setAutomaticRecoveryEnabled(true);
             factory.setHost(mqConfig.getHost());
             factory.setPort(mqConfig.getPort());
             //factory.setVirtualHost(vhost);
             factory.setUsername(mqConfig.getUserName());
             factory.setPassword(mqConfig.getPassWord());
             connectionFactoryMap.put(rabbitMqName, factory);
         } catch (Exception e) {
             e.printStackTrace();
         }finally{
         }
     }
     
 }