package net.pingfang.utils;

import net.pingfang.handler.Result;

public class ResultUtil {
	
	public static Result<Object> success(Object obj) {
		Result<Object> result = new Result<Object>();
		result.setCode(0); //0为成功，1为失败
		result.setMsg("成功");
		result.setData(obj);
		return result;
	}
	public static Result<Object> success() {
		return success(null);
	}
	public static Result<Object> error(Integer code, String message) {
		Result<Object> result = new Result<Object>();
		result.setCode(code);
		result.setMsg(message);
		result.setData(null);
		return result;
	}

}
