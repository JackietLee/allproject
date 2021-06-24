package net.pingfang.cgfTest.mq;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import net.pingfang.cgfTest.redis.UserVo;
import net.pingfang.config.mq.RabbitMqConnectFactory;
import net.pingfang.config.redis.RedisKeyUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqMsgListenTest {

	@Test
    public void testObj() throws Exception{
    	
		Connection connection = RabbitMqConnectFactory.getConnection("connection");
		Channel channel = connection.createChannel();
		String qureName = channel.queueDeclare().getQueue();
		channel.exchangeDeclare("cgfFanoutExchan", "fanout",true);
		//将这个队列绑定到交换机中，由于fanout类型的交换机，因此不需指定Routingkey进行绑定
		channel.queueBind(qureName, "cgfFanoutExchan", "");
		/**
		 * 监听某个队列并获取队列中的数据
		 * 注意：
		 * 当前被绑定的队列必须已经存在并正确的绑定到了某个交换机中
		 */
		channel.basicConsume(qureName, true, "", new DefaultConsumer(channel) {
			//public void handleDelivery(String consumerTag, Envelope envelope,BasicProperties p,byte[] body) {}
			public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties b,byte[] body) {
				String msg = new String(body);
				System.out.println("消费者： "+ msg);
			}
		});
    }
}
