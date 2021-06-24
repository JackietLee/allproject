package net.pingfang.serviceImpl.equipmentMonitoring;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;

import net.pingfang.entity.equipmentMonitoring.EmCamera;
import net.pingfang.entity.equipmentMonitoring.EmCrane;
import net.pingfang.entity.equipmentMonitoring.Plc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadXML {
	 static final Logger log = LoggerFactory.getLogger(ReadXML.class);
	/*
     *filePath:xml文件路径
     */
    public static EmCrane readXml(String filePath){
    	EmCrane crane = null;
        InputStream in = null;
        // 解析xml文档内容
        try {
        	File file = new File(filePath);
        	//判断文件是否存在
        	if (file.isFile() && file.exists()) { 
	            SAXReader reader = new SAXReader();
	            in = new FileInputStream(file);
	            Document doc = reader.read(in);
	            //获取根节点
	            Element craneElem = doc.getRootElement();
	                //获取user的index属性值
	                List<Element> textElem = craneElem.elements();
	                crane = new EmCrane();
	                crane.setMessage_type(textElem.get(0).getText());
	                crane.setAreaName(textElem.get(1).getText());        //站点名称文本值
	                crane.setCraneName(textElem.get(2).getText());    	 //岸桥名称文本值
	                crane.setTime(textElem.get(3).getText());			//时间
	                //PLC设备
	                List<Element> plcElem = textElem.get(4).elements();
	                Plc plc = new Plc();
	            	plc.setStatus(plcElem.get(0).getText());
	            	crane.setPlc(plc);
	            	
	            	//相机
	            	List<Element> cameraElem = textElem.get(5).elements();
	            	List<EmCamera> cameraList = new ArrayList<EmCamera>();
	                for(Element ca : cameraElem) {
	                	List<Element> caTextList = ca.elements();
	                	EmCamera camera = new EmCamera();
	                	camera.setName(caTextList.get(0).getText());
	                	camera.setIp(caTextList.get(1).getText());
	                	camera.setStatus(caTextList.get(2).getText());
	                	
	                	cameraList.add(camera);
	                }
	                crane.setCameraList(cameraList);
        	}else {
        		log.info("文件不存在："+filePath);
        		System.out.println("文件不存在："+filePath);
        	}
        } catch (Exception e) {
            System.out.println("error: "+ e);
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
        	if(null !=in) {
        		try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return crane;
    }
    
}
