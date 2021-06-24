package net.pingfang.handler;
/**
 * Http请求返回的最外层对象
 * @author Administrator
 *
 */
public class DataResult<T> {
	
	private Integer status;
	
	private String message;
	
	private T data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
