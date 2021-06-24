package net.pingfang.config.mq;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
 
/**
 * 把数据发送到rabbitmq的exchange，
 */
@Component
public class SendToExchange {
	@Autowired
 	private MQConfig config;
	
	private static MQConfig mqConfig;
     static Logger log = LoggerFactory.getLogger(SendToExchange.class);
     
     final static String TYPE = "topic";
     final static String CHARSET_UTF8 = "UTF-8";
     //MQ生产者exchange，把数据发给这个exchange
     final static String rabbitExchangeName = "shipmach";	//交换机名称
     static boolean mqConnected = false;//mq当前处于连接状态(true为连接，false为断开)
     
     static Channel channel=null;
     
    @PostConstruct
  	public void init() {
//    	ste = this;
    	mqConfig = config;
    	initConnection();
  	}
     
//     static{
//         initS();
//     }
     public static void initConnection(){
         log.info(" rabbit mq init begin...");
         try {
             //在mq连接中断后，发送程序判断已经断开，启动重连的时候会执行
             if(channel!=null){
                 try {
                     channel.close();
                 } catch (Exception e) {
                     log.error("关闭老channel 异常",e);
                 }finally{
                     channel = null;
                 }
             }
             if("Y".equals(mqConfig.getSend_message())) {
            	 Connection connection = RabbitMqConnectFactory.getConnection("connection");
                 channel = connection.createChannel();
                 /**
                  * 声明一个交换机
                  * 这里只定义exchange，因为每个业务模块都会从这里接入数据，所以不在这里定义队列
                  * 队列的定义在各个业务模块自己的消费端定义
                  *
                  * exchange :交换器的名称
    			  * type : 交换器的类型，常见的有direct,fanout,topic,headers等
    			  * durable :设置是否持久化。durable设置为true时表示持久化，反之非持久化.持久化可以将交换器存入磁盘，在服务器重启的时候不会丢失相关信息。
    			  * autoDelete：设置是否自动删除。autoDelete设置为true时，则表示自动删除。自动删除的前提是至少有一个队列或者交换器与这个交换器绑定，之后，所有与这个交换器绑定的队列或者交换器都与此解绑。不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
    			  * internal：设置是否内置的。如果设置为true，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式。
    			  * arguments:其它一些结构化的参数，比如：alternate-exchange
                  */
              //   channel.exchangeDeclare(rabbitExchangeName, TYPE, true, false, null);
                 /**
                  * 声明一个队列
                  * 参数1：队列名
                  * 参数2：是否为持久化的队列
                  * 参数3：是否排外，如果排外则这个队列只允许一个消费者监听
                  * 参数4：是否自动删除，如果为true则表示当前队列中没有消息，也没有消费者连接时就会自动删除队列中的消息
                  * 参数5：为队列的一些属性，通常为null即可
                  * 注意：
                  *    1.声明队列时，这个队列名称如果已经存在则放弃声明，如果队列不存在则会声明一个新的队列
                  *    2.队列名可以任意取值，但是要与消息接收时完全一致
                  *    3.这行代码是可有可无的，但是一定要在消息发送前确认队列名称在RabbitMq中存在，否则会报错
                  * 2020-11-24
                  */
                // channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments)
                 log.info(" rabbit mq init OK");
                 mqConnected = true;
             }else {
            	 log.error("rabbitmq 开关send_message值为N，不建立连接！");
                 mqConnected = false;
             }
         } catch (Exception e) {
             log.error("rabbitmq初始化错误",e);
             mqConnected = false;
         }
     }
     /**
      * 往rabbitmq发数据
      * @param message
      */
     public static void sendToRabbitMq(String routingKey,String message){
         try {
             if(StringUtils.isEmpty(message)){
                 log.debug("message is empty");
                 return;
             }
             log.info(routingKey+"："+message);
             /**
              	* 参数1：交换机名称
              	* 参数2：队列名或routingKey，当指定了交换机名称以后这个值就是routingKey
              	* 参数3：消息属性，通常为空即可
              	* 参数4：具体的消息数据的字节数组
              */
             channel.basicPublish(rabbitExchangeName, routingKey, null, message.getBytes(CHARSET_UTF8));
             log.info("向MQ "+routingKey+"发送数据成功："+message);
         }catch(AlreadyClosedException ex){
             log.error("往rabbitmq发数据报错,可能连接已关闭,尝试重连,data:",message,ex);
             initConnection();
         }catch (Exception e) {
             log.error("往rabbitmq发数据报错,data:",message,e);
         }
     }
 }
