package net.pingfang.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	/**
	 * java对象转JSON字符串
	 * @author Administrator
	 *
	 */
	public static String javaToJsonStr(Object obj) {
		if(null !=obj) {
			//JSONObject json = (JSONObject)JSONObject.toJSON(obj);
			//jsonStr = json.toString();
			return JSONObject.toJSON(obj).toString();
		}else {
			return "";
		}		
	}
	/*
	public static void main(String[] args) {
		List<WorkRecordVo> workList = new ArrayList<WorkRecordVo>();
		WorkRecordVo wr = new WorkRecordVo();
		wr.setCraneNum("QC15");
		wr.setSeqNo("20200703230300627");
		wr.setVesselNumber("499556");
		wr.setVesselCode("ZG20");
		wr.setBayInfo("0400682");
		wr.setUpdateContaid("TLLU5818486");
		
		workList.add(wr);
		System.out.println(JsonUtil.javaToJsonStr(workList));
	}*/

}
