package net.pingfang.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Config {
	
	@Value("${serviceCode}")
	private  String serviceCode;
	
	@Value("${con_url}")
	private String con_url;
	
	@Value("${con_port}")
	private int con_port;

	//岸桥设备监控文件URL 
//	@Value("${cameraXmlUrl}")
//	private String cameraXmlUrl;
		
	
	//服务器图片URL 
//	@Value("${server_ImgUrl}")
//	private String serverImgUrl;
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getCon_url() {
		return con_url;
	}

	public void setCon_url(String con_url) {
		this.con_url = con_url;
	}

	public int getCon_port() {
		return con_port;
	}

	public void setCon_port(int con_port) {
		this.con_port = con_port;
	}

//	public String getCameraXmlUrl() {
//		return cameraXmlUrl;
//	}
//
//	public void setCameraXmlUrl(String cameraXmlUrl) {
//		this.cameraXmlUrl = cameraXmlUrl;
//	}

//	public String getServerImgUrl() {
//		return serverImgUrl;
//	}
//
//	public void setServerImgUrl(String serverImgUrl) {
//		this.serverImgUrl = serverImgUrl;
//	}
	
		
}
