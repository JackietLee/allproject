package net.pingfang.config.restTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import net.pingfang.handler.DataResult;
import net.pingfang.serviceImpl.async.AsyncServiceImpl;

@Component
public class RestUtil {
	//@Value("${wlApiUrl}")
//	private String wlApiUrl;
	
	private String wlApiUrl = "http://192.168.1.20:18070";
	
	private final static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HttpHeaders headers;
	
	/**
	 * GET请求
	 * 通过getForObject()调用
	 * @param url
	 * @return
	 */
	public DataResult getForObject(String url) {
		url = wlApiUrl + url;
		logger.info("API: "+url);
		DataResult result = null;
		try {
			result = this.restTemplate.getForObject(url, DataResult.class);
			logger.info(url+"  API返回信息：" + JSON.toJSONString(result));
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * GET请求
	 * 通过getForEntity()调用
	 * @param url
	 * @return
	 */
	public ResponseEntity<DataResult> getForEntity(String url) {
		url = wlApiUrl + url;
		logger.info("API: "+url);
		ResponseEntity<DataResult> responseEntity = null;
		try {
	        responseEntity = this.restTemplate.getForEntity(url, DataResult.class);
	        logger.info(url+"  API返回信息：" + JSON.toJSONString(responseEntity));
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return responseEntity;
	}
	/**
	 * POST请求
	 * 通过postForObject()调用
	 * @param url
	 * @param param
	 */
	public DataResult postForObject(String url, Object param) {
		url = wlApiUrl + url;
		logger.info("API: "+url);
		DataResult result = null;
		try {
			HttpEntity<Object> formEntity = new HttpEntity<Object>(param, headers);
			
			result = this.restTemplate.postForObject(url,formEntity, DataResult.class);
			logger.info(url+"  API返回信息：" + JSON.toJSONString(result));
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * POST请求
	 * 通过postForEntity()调用
	 * @param url
	 * @param param
	 * @return
	 */
	public ResponseEntity<DataResult> postForEntity(String url, Object param) {
		url = wlApiUrl + url;
		logger.info("API: "+url);
		
	    ResponseEntity<DataResult> responseEntity = null;
		try {
			HttpEntity<Object> formEntity = new HttpEntity<Object>(param, headers);
			
			responseEntity = this.restTemplate.postForEntity(url, formEntity,DataResult.class);
			logger.info(url+"  API返回信息：" + JSON.toJSONString(responseEntity));
		}catch(Exception e) {
			logger.error("API请求出错："+e.getMessage());
			e.printStackTrace();
		}
		return responseEntity;
	}
    

}
