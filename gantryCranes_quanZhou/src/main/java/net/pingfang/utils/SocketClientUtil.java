package net.pingfang.utils;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClientUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(SocketClientUtil.class);

	public static String socketClient (String socket_ip, int socket_port, JSONObject msgObj) {
		String resultStr = null;
		Socket socket=null;
		BufferedWriter writer = null;
		OutputStreamWriter ow = null;
		OutputStream os = null;
		
//		InputStream is = null;
//		DataInputStream dis = null;
		try{
			String send = msgObj.toString();
			logger.info("准备发送的数据："+send);
			logger.info("建立连接："+socket_ip+":"+socket_port);
			socket=new Socket(socket_ip,socket_port);
			logger.info("建立连接成功："+socket_ip+":"+socket_port);
			
			os = socket.getOutputStream();
			ow = new OutputStreamWriter(os);
			writer = new BufferedWriter(ow);
			
//			is = socket.getInputStream();
//			dis = new DataInputStream(is);
			
			char [] sendChar = send.toCharArray();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(char ch:sendChar){
			    list.add((int)ch);
			}
			Iterator<Integer> it = list.iterator();
			while(it.hasNext()){
			     writer.write(it.next());
			}
			writer.flush();
			//通知服务器已经发送完了
			socket.shutdownOutput();
			
//			resultStr = dis.readLine();
//			
//			System.out.println(resultStr);
//			logger.info(resultStr);
			resultStr = "200";
			logger.info("发送成功："+send);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			logger.info("closed......");
			try{
			    if(writer!=null){
			    	writer.close();
			    }
			    if(ow!=null){
			        ow.close();
			    }
			    if(os!=null){
			        os.close();
			    }
			    
//			    if(is!=null){
//			    	is.close();
//			    }
//			    if(dis!=null){
//			    	dis.close();
//			    }
		       // socket.shutdownOutput();//流关闭之后，相当于关闭底层的连接，除非新<br>new个socket，否则和客户端的连接相当于断开
			}catch(Exception e){
				e.printStackTrace();
			}
	 }
		return resultStr;
	}
}
