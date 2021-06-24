package net.pingfang.serviceImpl.vessel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.pingfang.dao.vessel.CraneInfoDao;
import net.pingfang.entity.vessel.CraneInfoVo;
import net.pingfang.service.vessel.CraneInfoService;
import net.pingfang.utils.DateUtil;
import net.pingfang.utils.SocketClientUtil;

import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 岸桥信息ServiceImpl
 * @author Administrator
 * @since 2019-06-10
 *
 */
@Service
public class CraneInfoServiceImpl implements CraneInfoService{
	
	@Autowired
	private CraneInfoDao craneInfoDao;
	
	private final static Logger logger = LoggerFactory.getLogger(CraneInfoServiceImpl.class);
	
	/**
	 * 获取所有岸桥信息
	 * @return
	 */
	@Override
	public List<CraneInfoVo> getCraneInfoList(CraneInfoVo craneInfoVo){
		return craneInfoDao.getCraneInfoList(craneInfoVo);
	}
	/**
	 * 根据ID获取一条岸桥信息
	 * @param id
	 * @return
	 */
	@Override
	public CraneInfoVo getCraneInfoById(int id){
		return craneInfoDao.getCraneInfoById(id);
	}	
	/**
	 * 根据岸桥编号获取一条岸桥信息
	 * @param craneNum
	 * @return
	 */
	@Override
	public CraneInfoVo getCraneInfoByCraneNum(String craneNum) {
		return craneInfoDao.getCraneInfoByCraneNum(craneNum);
	}
	/**
	 * 插入一条岸桥信息
	 * @param craneInfoVo
	 * @return
	 */
	@Transactional
	@Override
	public int insertCraneInfo(CraneInfoVo craneInfoVo){
		if(null == craneInfoVo.getType()) {
			craneInfoVo.setType("1");
		}
		return craneInfoDao.insertCraneInfo(craneInfoVo);
	}
	/**
	 * 更新一条岸桥信息
	 * @param CraneInfoVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateCraneInfo(CraneInfoVo CraneInfoVo){
		return craneInfoDao.updateCraneInfo(CraneInfoVo);
	}
	/**
	 * 删除一条岸桥信息
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteCraneInfo(int id){
		return craneInfoDao.deleteCraneInfo(id);
	}
	/**
	 *查询黄埔门机下拉列表
	 * @return
	 */
	@Override
	public List<CraneInfoVo> getHpCraneOptions() {
		return craneInfoDao.getHpCraneOptions();
	}
	/**
	 * 重启岸桥相应的控制程序
	 * @param CraneInfoVo
	 * @return
	 * 
	 * 报文内容：
		{
			"seq":"唯一标识符",
			"time": "20200825104025", //时间
			"ip": "127.0.0.1", //接收返回报文的IP
			"port": 2020, //接收返回报文端口
			"type": "0,1,2,3,4",  //操作类型,例如2为重启程序、3为重启电脑、4为重启相机等，多项操作时,使用英文逗号分隔；详细见操作类型；
			"back": "0,0",  //返回结果,多个返回值,使用英文逗号分隔,与操作类型一一对应；详细见返回结果
			"note": "",             //备注
		}

	 */
	@Override
	public String restartProgram(CraneInfoVo craneInfoVo) {
		JSONObject msgObj = new JSONObject();
		msgObj.put("seq", craneInfoVo.getCraneNum()+DateUtil.getDate("yyyyMMddHHmmsssss"));
		msgObj.put("time", DateUtil.getDate("yyyyMMddHHmmss"));
		msgObj.put("ip", craneInfoVo.getControlIp());
		msgObj.put("port", craneInfoVo.getControlPort());
		msgObj.put("type", craneInfoVo.getType());
		msgObj.put("back", "");
		msgObj.put("note", "");
		
		return SocketClientUtil.socketClient(craneInfoVo.getControlIp(), craneInfoVo.getControlPort(), msgObj);
		//return this.socketClient(msgObj);
	}
	/**
	 * 发送岸桥重启程序数据
	 * 
	 * 报文编码：UTF-8
	 报文格式：JSON
	报文内容：
	{
		"seq":"唯一标识符",
		"time": "20200825104025", //时间
		"ip": "127.0.0.1", //接收返回报文的IP
		"port": 2020, //接收返回报文端口
		"type": "0,1",  //操作类型,例如重启程序、重启电脑等，多项操作时,使用英文逗号分隔；详细见操作类型；
		(tyep 2为重启程序，3为重启电脑，4为重启相机)
		"back": "0,0",  //返回结果,多个返回值,使用英文逗号分隔,与操作类型一一对应；详细见返回结果
		"note": "",             //备注
	}
	 * @param send
	 */
	private String socketClient (JSONObject msgObj) {
		String resultStr = null;
		Socket socket=null;
		BufferedWriter writer = null;
		OutputStreamWriter ow = null;
		OutputStream os = null;
		
//		InputStream is = null;
//		DataInputStream dis = null;
		try{
			String socket_ip = msgObj.getString("ip");
			int socket_port = msgObj.getInt("port");
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
			resultStr = "重启命令发送成功！";
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
