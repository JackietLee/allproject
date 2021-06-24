package net.pingfang.serviceImpl.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import org.json.JSONObject;

import net.pingfang.service.async.AsyncService;

	
@Service
public class AsyncServiceImpl implements AsyncService{
	
	@Value("${wlApiUrl}")
	private String wlApiUrl;
	
	private final static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
	/*
	private RestTemplate restTemplate = null;
	private HttpHeaders headers = null;
	
	public AsyncServiceImpl() {
		if(null == restTemplate) {
			restTemplate = new RestTemplate();
		}
		if(null == headers) {
			headers = new HttpHeaders();
			headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
		}
	}
	*/
	
	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;
	@Resource(name = "headers")
	private HttpHeaders headers;
		
	@Override
	public ResponseEntity<String> doPost(JSONObject date,String url) {
		url = wlApiUrl + url;
		HttpEntity<String> entity = new HttpEntity<String>(date.toString(), headers);
		logger.info("提交给"+url+"的参数:"+date.toString());
		ResponseEntity<String> respMsg = restTemplate.exchange(url, HttpMethod.POST,entity, String.class);
		logger.info(url+"  API返回信息：" + respMsg);
		return respMsg;
	}
	
	@Override
	public ResponseEntity<String> doGet(String url) {
		url = wlApiUrl + url;
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		logger.info("API: "+url);
		ResponseEntity<String> respMsg = restTemplate.exchange(url, HttpMethod.GET,entity, String.class);
		logger.info(url+"  API返回信息：" + respMsg);
		return respMsg;
	}
	public ResponseEntity<String> doPut(JSONObject date,String url){
		url = wlApiUrl + url;
		//url = "http://192.168.1.67:8070/etlservice/tally_container/bay?vesselCode=PUS&jobType=1&inVoyage=0DD4XS1NCI&outVoyage=0DD4XS1NC&slot=0030886";
		HttpEntity<String> entity = new HttpEntity<String>(date.toString(), headers);
		logger.info("提交给"+url+"的参数:"+date.toString());
		ResponseEntity<String> respMsg = restTemplate.exchange(url, HttpMethod.PUT,entity, String.class);
		logger.info(url+"  API返回信息：" + respMsg);
		return respMsg;
	}
	
	
}
