package net.pingfang.serviceImpl.equipmentMonitoring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import net.pingfang.config.jedis.JedisConfig;
import com.alibaba.fastjson.JSON;
import net.pingfang.dao.equipmentMonitoring.EmCraneDao;
import net.pingfang.entity.equipmentMonitoring.EmCamera;
import net.pingfang.entity.equipmentMonitoring.EmCrane;
import net.pingfang.entity.config.Config;
import net.pingfang.service.equipmentMonitoring.EmCraneService;
//import net.pingfang.utils.JedisConfigUtil;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;

@Service
public class EmCraneServiceImpl implements EmCraneService{
	
	@Autowired
	private EmCraneDao emCraneDao;
	
//	@Resource(name = "jedisPool")
//	private JedisPool jedisPool;
	
	@Autowired
    private HashOperations<String, String, Object> hashOperations;
	@Resource
	private Config config;
	
	/**
	 * 获取所有岸桥监控信息
	 * @return
	 */
	@Override
	public List<EmCrane> getEmCraneList(){
		return emCraneDao.getEmCraneList();
	}
	/**
	 * 获取岸桥上的相机监控信息
	 * @return
	 */
	@Override
	public List<EmCamera> getEmCameraListByCraneId(EmCrane emCrane){
		String craneName = emCrane.getCraneName();
		List<EmCamera> list = null;
		/*	
//		Jedis jedis = jedisPool.getResource();
		//Jedis jedis = JedisConfigUtil.getJedis();
		
		
		 try {
			//查询报文在更新MAP里是否存在
		    if(null !=craneName && jedis.hexists("update_crane_equipment_map", craneName)) {
		    	 //从Redis里读取的报文
				 EmCrane readCrane = JSON.parseObject(jedis.hget("update_crane_equipment_map", craneName),EmCrane.class);
				 String time = readCrane.getTime();
			     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				 try {
					 Date  date = df.parse(time);
					 SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 time = df2.format(date);
				 } catch (ParseException e) {
					 e.printStackTrace();
				 }
			     list = readCrane.getCameraList();
			     for(EmCamera e : list) {
			    	 e.setEmTime(time);
			     }		    	
		    }else {
		    	list = emCraneDao.getEmCameraListByCraneId(emCrane.getId());
		    }
	    }catch(Exception e) {
   		 	e.printStackTrace();
   	 	}finally {
   	 		JedisConfigUtil.closeJedis(jedis);
   	 	}
		 */
		 try {
				//查询报文在更新MAP里是否存在
			    if(null !=craneName && hashOperations.hasKey("realTime_crane_equipment_map", craneName)) {
			    	 //从Redis里读取的报文
					 EmCrane readCrane = JSON.parseObject(hashOperations.get("realTime_crane_equipment_map", craneName).toString(),EmCrane.class);
					 String time = readCrane.getTime();
				     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					 try {
						 Date  date = df.parse(time);
						 SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						 time = df2.format(date);
					 } catch (ParseException e) {
						 e.printStackTrace();
					 }
				     list = readCrane.getCameraList();
				     for(EmCamera e : list) {
				    	 e.setEmTime(time);
				     }		    	
			    }else {
			    	list = emCraneDao.getEmCameraListByCraneId(emCrane.getId());
			    }
		    }catch(Exception e) {
	   		 	e.printStackTrace();
	   	 	}
		   
		 
		 
		/*
		String filePath = config.getCameraXmlUrl()+"/"+craneName;
		//读取本地文件数据
	    EmCrane readCrane = ReadXML.readXml(filePath+"/camera.xml");
	    if(null !=readCrane) {
	    	String time = readCrane.getTime();
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			try {
				  Date  date = df.parse(time);
				  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				  time = df2.format(date);
			} catch (ParseException e) {
				  e.printStackTrace();
			}
	    	list = readCrane.getCameraList();
	    	for(EmCamera e : list) {
	    		e.setEmTime(time);
	    	}
	    }else {
	    	list = emCraneDao.getEmCameraListByCraneId(emCrane.getId());
	    }	
	    */
	    return list;
	}
	
	/**
	 * 插入一条设备监控岸桥节点
	 * @param CameraInforVo
	 * @return
	 */
	@Transactional
	@Override
	public int addEmCrane(EmCrane crane) {
		int count = 0;
		EmCrane sCrane = emCraneDao.getEmCraneByName(crane.getCraneName());
		if(null !=sCrane) {
			//更新
			crane.setId(sCrane.getId());
			count = emCraneDao.updateEmCrane(crane);
		}else {
			count = emCraneDao.addEmCrane(crane);
		}
		List<EmCamera> cameraList = crane.getCameraList();
		if(null !=cameraList && cameraList.size()>0) {
			int craneId = crane.getId();
			for(EmCamera c : cameraList) {
				c.setCraneId(craneId);
			}
			count = emCraneDao.addEmCameraList(cameraList);
		}
		return count;
	}
	/**
	 * 更新一条岸桥设备监控为离线状态
	 * @param CameraInforVo
	 * @return
	 */
	@Transactional
	@Override
	public int updateIsActivity(String craneName, String activity) {
		return emCraneDao.updateIsActivity(craneName, activity);
	}

}
