package net.pingfang.handler;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.classic.Logger;
import net.pingfang.exception.WorkException;
import net.pingfang.utils.ResultUtil;

@ControllerAdvice
public class ExceptionHandler {

	private final static Logger log = (Logger) LoggerFactory.getLogger(ExceptionHandler.class);

	@ResponseBody
	// @ExceptionHandler(value=Exception.class)
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public Result handler(Exception e) {
		if (e instanceof WorkException) {
			WorkException workException = (WorkException) e;
			return ResultUtil.error(workException.getCode(), workException.getMessage());
		} else {
			String msg = e.getMessage();
			log.error("系统异常:" + msg);
			log.info("系统异常:{}", e);
			if (null != this.getMsg(msg)) {
				// 没有权限
				return ResultUtil.error(403, msg);
			}
			return ResultUtil.error(-1, msg);
		}

	}

	private String getMsg(String message) {
		String msg = null;
		if (null != message && !"".equals(message)) {
			msg = message.substring(message.indexOf("[") + 1, message.indexOf("]"));
		}
		return msg;
	}

}
