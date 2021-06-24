package net.pingfang.cgfTest.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MqTest1 {

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
			
			String qureName = channel.queueDeclare().getQueue();
			System.out.println(qureName);
			channel.exchangeDeclare("cgfFanoutExchan", "fanout",true);
			//将这个队列绑定到交换机中，由于fanout类型的交换机，因此不需指定Routingkey进行绑定
			channel.queueBind(qureName, "cgfFanoutExchan", "");
			/**
			 * 监听某个队列并获取队列中的数据
			 * 注意：
			 * 当前被绑定的队列必须已经存在并正确的绑定到了某个交换机中
			 */
		/*	channel.basicConsume(qureName, true, "", new DefaultConsumer(channel) {
				public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties b,byte[] body) {
					String msg = new String(body);
					System.out.println("消费者001： "+ msg);
				}
			});
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
	}
}
