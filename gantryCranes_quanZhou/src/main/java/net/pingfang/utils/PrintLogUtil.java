package net.pingfang.utils;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
/**
 * 打印日志
 * @author Administrator
 *
 */
public class PrintLogUtil {
	
	/**
	 * 记录请求参数
	 * @param url
	 * @param param
	 */
	public static void requestParamPrintLog(Logger logger, String url, Object param) {
		logger.info(url+" 请求参数: "+JSON.toJSONString(param));
	}
	/**
	 * 记录请求返回数据
	 * @param url
	 * @param param
	 */
	public static void returnDataPrintLog(Logger logger, String url, Object data) {
		logger.info(url+"  API返回信息：" + JSON.toJSONString(data));
	}

}
