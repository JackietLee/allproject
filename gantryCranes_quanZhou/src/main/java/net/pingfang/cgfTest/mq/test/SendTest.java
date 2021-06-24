package net.pingfang.cgfTest.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class SendTest {

	public static void main(String[] args) {
		  ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("192.168.1.50");
          factory.setPort(5672);
          factory.setUsername("admin");
          factory.setPassword("admin");
          
          Connection connection = null;
          Channel channel = null;
          
          try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			/**
			 * 由于使用fanout类型的交换机，因此消费都可能会有多个，因此不建议在消息发送时来创建队列，
			 * 以及绑定交换机，建议在消费者中创建队列并绑定交换机
			 * 但是在发送消息时就确保交换机存在
			 */
			
			//String qureName = channel.queueDeclare().getQueue();
			channel.exchangeDeclare("cgfFanoutExchan", "fanout",true);
			//将这个队列绑定到交换机中，由于fanout类型的交换机，因此不需指定Routingkey进行绑定
		//	channel.queueBind(qureName, "cgfFanoutExchan", "");
			String msg = "cgfFanoutExchan fanout的消息测试！";
			String str = null;
			try {
				for(int i=0; i<100000; i++) {
					str = msg + "第"+i+"次";
					System.out.println(str);
					channel.basicPublish("cgfFanoutExchan", "", null, str.getBytes("utf-8"));
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
	}




}
