package net.pingfang.service.async;

import org.springframework.http.ResponseEntity;

import org.json.JSONObject;

public interface AsyncService {

	public ResponseEntity<String> doPost(JSONObject date,String url);
	
	public ResponseEntity<String> doGet(String url);
	
	public ResponseEntity<String> doPut(JSONObject date,String url);
}
