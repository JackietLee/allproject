package net.pingfang.config.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
 
/**
 * RabbitMQq客户端代码
 * @author skyfeng
 *
 */
public class CustomerMqClient {
 
//     final static Logger log = LoggerFactory.getLogger(CustomerMqClient.class);
//     private final static String RABBITMQ_NAME = "mq_name";
//     private final static String EXCHANGE_NAME = "Exchange_name";
//     private final static String QUEUE_NAME = "queue_name";
//     private static Channel channel = null;
//     private static Connection connection = null;
     
     /**
      * 初始化客户端代码
      */
     /*
     public static void initClient() {
         //重新链接时判断之前的channel是否关闭，没有关闭先关闭
         if(null != channel  && channel.isOpen()){
             try {
                 channel.close();
             } catch (Exception e) {
                 log.error("mq name =[" +RABBITMQ_NAME+"] close old channel exception.e={}",e);
             }finally {
                 channel = null;
             }
         }
         //重新链接时判断之前的connection是否关闭，没有关闭先关闭
         if (null != connection && connection.isOpen()) {
             try {
                 connection.close();
             } catch (Exception e) {
                 log.error("mq name =[" +RABBITMQ_NAME+"] close old connection exception.e={}",e);
             }finally{
                 connection = null;
             }
         }
         //从链接池中获取链接
         connection = RabbitMqConnectFactory.getConnection(RABBITMQ_NAME);
         try {
             channel = connection.createChannel();
             channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
             channel.queueDeclare(QUEUE_NAME, false, false, false, null);
             channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "#");//#号接收所有的数据
            // Consumer consumer = new CustomerMqConsumer(channel);//具体的业务逻辑在CustomerMqConsumer中
            // channel.basicConsume(QUEUE_NAME, false, consumer);
         } catch (Exception e) {
             log.error("CustomerMqClient mq client connection fail .....{}", e);
             //发生异常时，重连
             reConnect();
         }
     }
 
     // 异常时，重连的方法
     public static void reConnect() {
         log.error("等待5s后重连");
         try {
             Thread.sleep(5000);
         } catch (InterruptedException e) {
         }
         initClient();
     }
 */
 }