package com.itjay.springshiro.config;

//import java.io.IOException;
//import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//import org.springframework.context.annotation.Bean;
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
	@Value("${mq.username}")
	private String username;
	/**
	 * 密码
	 */
	@Value("${mq.password}")
	private String password;
	/**
	 * 端口号
	 */
	@Value("${mq.port}")
	private Integer port;

	public Logger getLog() {
		return log;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
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

}
