package net.pingfang.config.mq;

//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class MQConfig {
	
	
	final Logger log = LoggerFactory.getLogger(MQConfig.class);
	/**
	 * 连接地址
	 */
	@Value("${mq.host}")
	private String host;
	/**
	 * 用户名
	 */
	@Value("${mq.userName}")
	private String userName;
	/**
	 * 密码
	 */
	@Value("${mq.passWord}")
	private String passWord;
	/**
	 * 端口号
	 */
	@Value("${mq.port}")
	private Integer port;
	/**
	 * 声明车顶号消息队列名
	 */
	@Value("${mq.plate_queue_name}")
	private String plate_queue_name;
	/**
	 * 声明箱号消息队列名
	 */
	@Value("${mq.containerid_queue_name}")
	private String containerid_queue_name;
	/**
	 * 交换机名称
	 */
	@Value("${mq.switch_name}")
	private String switch_name;
	
	/**
	 * 是否发送消息
	 * Y为发送消息，N为不发送
	 */
	@Value("${mq.send_message}")
	private String send_message;
	
	
	/**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
     * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     * @return
     */
	//@Bean(name = "connectionMQ")	
/*	public Connection mqConnection(){
		 ConnectionFactory factory = new ConnectionFactory();  
		 factory.setHost(host);
		 factory.setUsername(userName);
		 factory.setPassword(passWord);
		 factory.setPort(port);
		 Connection connection = null;
		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			log.error("连接RabbitMQ失败："+e.getMessage());
			e.printStackTrace();
		} catch (TimeoutException e) {
			log.error("连接RabbitMQ失败："+e.getMessage());
			e.printStackTrace();
		}  
		if(null !=connection) {
			log.info("**************** 连接RabbitMQ成功！*************");
			log.info("RabbitMQ地址："+connection.getAddress() +":"+connection.getPort());
		}else {
			log.info("**************** 连接RabbitMQ失败！Connection 为 null *************");
		}
		return connection;
	}
	*/
	public String getHost() {
		return host;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public int getPort() {
		if(null == port || 0 == port) {
			port = 5672;
		}
		return port;
	}
	
	public String getPlate_queue_name() {
		return plate_queue_name;
	}
	public String getSwitch_name() {
		if(null == switch_name) {
			switch_name = "";
		}
		return switch_name;
	}
	public String getSend_message() {
		return send_message;
	}
	public void setSend_message(String send_message) {
		this.send_message = send_message;
	}
	public String getContainerid_queue_name() {
		return containerid_queue_name;
	}

}
