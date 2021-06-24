package net.pingfang.service.websoket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

@Order(1)
public class StartSoketIo implements CommandLineRunner {
	@Autowired
	private SocketIoClient_Bay socketIoClient_Bay;
	
	@Override
	public void run(String... args) throws Exception {
		socketIoClient_Bay.connectSocket();
	}
	
}
