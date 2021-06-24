package net.pingfang.service.websoket;

import java.net.URISyntaxException;
import javax.annotation.Resource;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import net.pingfang.entity.config.Config;
import net.pingfang.utils.DateUtil;

/**
 * @author：cgf
 * @describe:连接消息服务，进入房间，更新消息服务等操作。
 * @date：2019年10月16日上午14:00:30
 */
@Component
public class SocketIoClient_Bay {

	private static final Logger log = LoggerFactory.getLogger(SocketIoClient_Bay.class);
	private Socket socket;
	@Resource
	private Config config;
	public static final String MESSAGE_TYPE_FOUR = "16"; 	//车牌检查作业报文类型
	/**
	 * 连接消息服务器方法
	 **/
	public void connectSocket() throws URISyntaxException {
		socket = IO.socket(config.getCon_url() + ":" + config.getCon_port() + "?clientid=" + config.getServiceCode());

		/**
		 * 输出连接消息服务信息
		 **/
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				log.info("用户 [" + config.getServiceCode() + "] 已连接消息服务！");
				try {
					inToServiceToRoom();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		/**
		 * 进入或者离开房间提示
		 **/
		socket.on("JoinOrLeaveRoom", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				String Date = args[0].toString();
				JSONObject roomDataJson = null;
				try {
					roomDataJson = new JSONObject(Date);
					log.info("用户 [" + config.getServiceCode() + "] 已进入 [" + roomDataJson.getString("roomName") + "] 房间！");
				} catch (JSONException e) {
					log.error("json 错误：" + e.getMessage());
				}
			}
		});

		/**
		 * 编辑JobQueue数据后返回uuid
		 **/
		socket.on("uuid", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				log.info("编辑redis缓存返回uuid：" + args[0].toString());
				String uuid = args[0].toString();
				String jsonStr = args[1].toString();
				if (null != uuid && !"".equals(uuid) && null != jsonStr && !"".equals(jsonStr)) {
					try {
						JSONObject json = new JSONObject(jsonStr);
						/*String conmmandName = json.getString("commandName");
						if("updateBay".equals(conmmandName)) {
							messageContent(uuid, conmmandName); // 广播编辑消息
						}*/
						messageContent(uuid, json.getString("commandName"),json.getString("crane_num"),json.get("state").toString(),json.getString("workData")); // 广播编辑消息
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});

		/**
		 * 删除缓存返回UUID
		 **/
		socket.on("returnUuid", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				log.info("删除redis缓存uuid：" + args[0].toString());
				String uuid = args[0].toString(); 
				String jsonStr = args[1].toString();
				if (null != uuid && !"".equals(uuid) && null != jsonStr && !"".equals(jsonStr)) {
					//VariableTemp vt = JSON.parseObject(jsonStr, VariableTemp.class);
					//messageContent(uuid, vt.getCommandName()); // 广播编辑消息
					//log.info("岸桥编号：" + vt.getSenderCode());
					//log.info("commandName：" + vt.getCommandName());
				}
			}
		});

		/***
		 * 输出返回消息
		 */
		socket.on("message", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				log.info("消息服务返回信息：" + args[0].toString());
			}
		});
		

		/**
		 * 接收（service）房间广播消息和点对点消息
		 */
/*		socket.on("messageevent", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject jsonData = new JSONObject(args[0].toString());
				log.info("用户  [" + jsonData.get("sourceClientId") + "] 发来的信息:" + jsonData.get("msgContent"));
				//1、解析消息
				String msgContent = jsonData.get("msgContent").toString();
				JSONObject contentJson = new JSONObject(msgContent);
				if(msgContent.contains("message_type")) {
					if(contentJson.getString("message_type").equals("06")){
						//1 调用API修改数据库
						JSONObject objt = new JSONObject(contentJson.get("parameter").toString());
						JSONObject jobJson = new JSONObject(objt.get("job").toString());
	                 	JobData jobData = new JobData();
	                 	jobData.setJobkey(objt.getString("jobkey"));
	                 	jobData.setJob(jobJson.toString());
	                 	//调用API放行结果保存到数据库!
	             		log.info("开始调用API修改数据");
	             		try {
	             			//apiUrl = config.getApiUrl() + "/work/insertWorkRecord";
	             			apiUrl = config.getApiUrl() + "/dataService/insertWorkRecord";
	    					HttpEntity<String> entity = new HttpEntity<String>(jobData.getJob(), headers);
	    					ResponseEntity<String> respMsg = restTemplate.exchange(apiUrl, HttpMethod.POST,entity, String.class);
	    					log.info("修改数据API返回信息：" + respMsg);
						} catch (Exception e) {
							log.error("调用API修改数据错误，错误信息："+e.getMessage());
						}
	             		//2删除redis
	             		 deleteByKey(jobData.getJobkey(),jobJson.getString("crane_num"),"deletejob");
	             	}else {
						log.info("报文类型错误！收到的报文类型是："+contentJson.getString("message_type"));
					}
				}else {
					//上锁或解锁
					  if(contentJson.getString("commandName").equals("lock")){
                 	    log.info("上锁指令!");
                      	VariableTemp variable = new VariableTemp();
                      	variable.setCommandName("lock");
                      	variable.setJobkey(contentJson.getJSONObject("parameter").getString("jobkey"));
                      	variable.setSenderCode(contentJson.getString("senderCode"));
                      	variable.setSendTime(contentJson.getString("sendTime"));
                      	findDateByRoomAndKeySave(contentJson.getJSONObject("parameter").getString("jobkey"),variable);
                    }else if(contentJson.getString("commandName").equals("unlock")) {
                      	 log.info("解锁指令!");
	                     VariableTemp variable = new VariableTemp();
                      	 variable.setCommandName("unlock");
                      	 variable.setJobkey(contentJson.getJSONObject("parameter").getString("jobkey"));
                      	 variable.setSenderCode("");
                       	 variable.setSendTime("");
                       	 findDateByRoomAndKeySave(contentJson.getJSONObject("parameter").getString("jobkey"),variable);
                    }
				}
			}
		});
*/
		/***
		 * 输出类型获取所有数据
		 */
		socket.on("findAllDate", new Emitter.Listener() {
			public void call(Object... args) {
				/*try {
					String jsonList = args[0].toString();//查找出来的数据
					JSONObject json = new JSONObject(jsonList);
					Iterator<String> it = json.keys();// 使用迭代器
					
					JSONObject jsonObject = new JSONObject(args[1].toString());	//传过来的参数
					//System.out.println("findAllDate ->" +jsonObject.toString());
					String craneNum = jsonObject.getString("laneCode");	//岸桥编号
					List<String> delKeyList = new ArrayList<String>();	//需要删除的KEY
					String key = null;
					while (it.hasNext()) {
						key = it.next(); // 获取key
						JSONObject jsonData = new JSONObject(json.getString(key).toString());
						//报文类型（"16"）
						if(MESSAGE_TYPE_FOUR.equals(jsonData.getString("message_type"))) {
							//岸桥编号
							if(craneNum.equals(jsonData.getString("crane_num"))) {
								delKeyList.add(key);
							//	System.out.println("key: "+key);
								//System.out.println(jsonData);
								log.info("删除16号报文key: "+key);
								log.info("删除16号报文key: "+jsonData.toString());
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});

		/**
		 * 输出断开连接方法消息
		 */
		socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				log.info("用户 [" + config.getServiceCode() + "] 已断开消息服务！");
			}
		});
		socket.connect();	
	}

	/***
	 * 进入房间
	 * 
	 * @throws JSONException
	 */
	public void inToServiceToRoom() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("roomName", "Service");
		obj.put("clientId", config.getServiceCode());
		obj.put("state", 1);
		socket.emit("joinOrLeaveRoom", obj);
	}

	/***
	 * 广播的消息内容
	 * @throws JSONException
	 **/
	public void messageContent(String uuid, String updateOrdel,String crane_num,String state,String workData) throws JSONException {
		/**
		 * "commandCode":"bfea0d75-7aff-4e1c-b4e4-93abe65ad6d8",
    	 * "commandName":"updatejob",
    	 * "senderCode":"webuser",
    	 * "recipientCode":"serviceid",
    	 * "parameter":{"jobkey":"0db9d70c-040b-4b19-914f-9de2504c79ce","job":"完整Jobqueue",},
    	 * "sendTime":"2018-08-11 16:17:55"
		 */
		JSONObject objectData = new JSONObject();
		JSONObject serverkeyObject = new JSONObject();
		serverkeyObject.put("jobkey", uuid);
		serverkeyObject.put("crane_num", crane_num);
		serverkeyObject.put("state", state);
		
		objectData.put("commandCode", "bfea0d75-7aff-4e1c-b4e4-93abe65ad6d8");
		objectData.put("commandName", updateOrdel);
		objectData.put("senderCode", config.getServiceCode());
		objectData.put("recipientCode", "Client");
		objectData.put("sendTime", DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
		if(null !=workData) {
			serverkeyObject.put("workData", workData);
		}
		objectData.put("parameter", serverkeyObject);
		sendBroadcast(objectData.toString());
	}

	/***
	 * 广播消息
	 * @throws JSONException
	 */
	public void sendBroadcast(String objectData) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("sourceClientId", config.getServiceCode());
		obj.put("roomsName", "Client");
		obj.put("state", 1);
		obj.put("msgContent", objectData);
		log.info("广播消息内容：" + obj);
		socket.emit("broadcast", obj);
	}
	/**
	 * 新增数据service房间JobQueue数据
	 * @param keyId   UUID
	 * @param crane_num  岸桥编号
	 * @param jobQueue 作业报文JSON字符串
	 * @throws InterruptedException
	 * @throws JSONException
	 */
	public void addJobQueueDate(String operationType,String msgType,String craneNum, String workData) {
		
		try {
			JSONObject jobQueueJson = new JSONObject();
			JSONObject objStr = new JSONObject();
			objStr.put("message_type", msgType);
			objStr.put("passtime", DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
			objStr.put("work_type", msgType);
			objStr.put("operation_type", operationType);	
			objStr.put("state", 1);
			jobQueueJson.put("d5a36f80-df93-449c-824f-bde81d89885f", objStr);
			jobQueueJson.put("message_type", msgType);
			/**
			 * sourceClientId:
	 		 * roomsName:房间名称
			 * msgContent:消息内容
			 * typeName:消息类型
			 * State:状态，状态为1
			 */
			JSONObject obj = new JSONObject();
			obj.put("sourceClientId", config.getServiceCode());
			obj.put("roomsName", "Service");
			obj.put("msgContent", jobQueueJson.toString());
			obj.put("typeName", "work");
			obj.put("State", 1);
			//obj.put("keyId", UUID.randomUUID().toString());
			obj.put("keyId", "d5a36f80-df93-449c-824f-bde81d89885f");
			
			JSONObject msgJson = new JSONObject();
			msgJson.put("msgType", msgType);
			msgJson.put("commandName", operationType);
			msgJson.put("crane_num", craneNum);
			msgJson.put("state", 1);
			msgJson.put("workData", workData);
			obj.put("railWayCode", msgJson.toString());
			//新增数据
			socket.emit("insertDateByRoomId", obj);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
	}

	
	/****
	 * 断开连接
	 **/
	public void disconnection() {
		if (socket.connected()) {
			socket.disconnect();
		}
	}
}
