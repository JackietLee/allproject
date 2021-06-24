package net.pingfang.json.work;

/**
 * 作业信息JSON数据
 * @author Administrator
 *
 */
public class WorkJson {
	private String message_type; 	//"message_type": "03", //01开始报文，02是结束报文，03是识别数据报文
	private String seq_no;			//"seq_no": "20190521172207799",//唯一任务编号 
	private String passtime;		//"passtime": "2019-05-21T17:22:07",//报文形成时间 
	private String crane_num;		//"crane_num": "QT01",//岸桥编号
	private String area_num;        //"area_num":"DPN",//站点名称 
	private int work_type;			//"work_type": 0,//作业类型 0：装船作业；1:卸船作业；2：其他作业
	private int container_type;		//"container_type": 0, //箱类型 0：长箱,1：短箱,2：双箱,10：未知
	private String lane_num;		//"lane_num": "01", //车道
	private String vessel_code;		//"vessel_code":""//船舶代码
	private String vessel_number;	//"vessel_number" :"70434",//外理系统船舶艘次号
	private String voyage_no;		//华东系统船舶艘次号
	private String vessel_name_cn;	//"vessel_name_cn":"中文船名",  //中文船名
	private String vessel_name_en;	//"vessel_name_En":"中文船名",  //英文船名
	private String out_voyage;		//进口航次
	private String in_voyage;		//出口航次
	private String tally_clerk;		// "tally_clerk":"理货员",    //理货员
	private String state;			//是否理货（0表示未理货，1表示已理货,2表示异常数据）
	
	private CcrResultJson ccr_result;	//货柜箱识别结果
	private TpplateResultJson tpplate_result; //车辆检测结果
	private PlateResultJson plate_result; //车牌识别结果
	private DamagedResultJson damaged_result;   //验残信息
	private PicDataJson plc_data;		//吊具信息
	private BayResultJson bay_result;	//贝位识别结果信息
	
	private String operationType;		//操作类型
	private String msgTypeTwo;			//是否有合并02号作业结束报文
	
	private String stevedore_id;	//装卸工ID
	private int ship_position;		// 1甲板2船舱
	
	private int alarm_state;	//是否报警（0为不报警，1为报警）
	
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public String getPasstime() {
		return passtime;
	}
	public void setPasstime(String passtime) {
		this.passtime = passtime;
	}
	public String getCrane_num() {
		return crane_num;
	}
	public void setCrane_num(String crane_num) {
		this.crane_num = crane_num;
	}
	
	public String getArea_num() {
		return area_num;
	}
	public void setArea_num(String area_num) {
		this.area_num = area_num;
	}
	public int getWork_type() {
		return work_type;
	}
	public void setWork_type(int work_type) {
		this.work_type = work_type;
	}
	public int getContainer_type() {
		return container_type;
	}
	public void setContainer_type(int container_type) {
		this.container_type = container_type;
	}
	public String getLane_num() {
		return lane_num;
	}
	public void setLane_num(String lane_num) {
		this.lane_num = lane_num;
	}
	
	public String getVessel_code() {
		return vessel_code;
	}
	public void setVessel_code(String vessel_code) {
		this.vessel_code = vessel_code;
	}
	public String getVessel_number() {
		return vessel_number;
	}
	public void setVessel_number(String vessel_number) {
		this.vessel_number = vessel_number;
	}	
	public String getVessel_name_cn() {
		return vessel_name_cn;
	}
	public void setVessel_name_cn(String vessel_name_cn) {
		this.vessel_name_cn = vessel_name_cn;
	}
	
	public String getVessel_name_en() {
		return vessel_name_en;
	}
	public void setVessel_name_en(String vessel_name_en) {
		this.vessel_name_en = vessel_name_en;
	}
	public String getOut_voyage() {
		return out_voyage;
	}
	public void setOut_voyage(String out_voyage) {
		this.out_voyage = out_voyage;
	}
	public String getIn_voyage() {
		return in_voyage;
	}
	public void setIn_voyage(String in_voyage) {
		this.in_voyage = in_voyage;
	}
	public String getTally_clerk() {
		return tally_clerk;
	}
	public void setTally_clerk(String tally_clerk) {
		this.tally_clerk = tally_clerk;
	}	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public CcrResultJson getCcr_result() {
		return ccr_result;
	}
	public void setCcr_result(CcrResultJson ccr_result) {
		this.ccr_result = ccr_result;
	}
	public TpplateResultJson getTpplate_result() {
		return tpplate_result;
	}
	public void setTpplate_result(TpplateResultJson tpplate_result) {
		this.tpplate_result = tpplate_result;
	}
	public PlateResultJson getPlate_result() {
		return plate_result;
	}
	public void setPlate_result(PlateResultJson plate_result) {
		this.plate_result = plate_result;
	}
	public DamagedResultJson getDamaged_result() {
		return damaged_result;
	}
	public void setDamaged_result(DamagedResultJson damaged_result) {
		this.damaged_result = damaged_result;
	}
	public PicDataJson getPlc_data() {
		return plc_data;
	}
	public void setPlc_data(PicDataJson plc_data) {
		this.plc_data = plc_data;
	}
	public BayResultJson getBay_result() {
		return bay_result;
	}
	public void setBay_result(BayResultJson bay_result) {
		this.bay_result = bay_result;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getMsgTypeTwo() {
		return msgTypeTwo;
	}
	public void setMsgTypeTwo(String msgTypeTwo) {
		this.msgTypeTwo = msgTypeTwo;
	}
	public int getAlarm_state() {
		return alarm_state;
	}
	public void setAlarm_state(int alarm_state) {
		this.alarm_state = alarm_state;
	}
	public String getVoyage_no() {
		return voyage_no;
	}
	public void setVoyage_no(String voyage_no) {
		this.voyage_no = voyage_no;
	}
	public String getStevedore_id() {
		return stevedore_id;
	}
	public void setStevedore_id(String stevedore_id) {
		this.stevedore_id = stevedore_id;
	}
	public int getShip_position() {
		return ship_position;
	}
	public void setShip_position(int ship_position) {
		this.ship_position = ship_position;
	}
	
}
