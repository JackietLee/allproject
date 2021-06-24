package net.pingfang.entity.work;

import java.util.List;

/**
 * 人工理货操作记录（‘work_record_manual’）
 * @author Administrator
 *
 */
public class WorkRecordManualVo {
	
	private int id;  //identity (1,1) primary key 
	private String seqNo;//unique唯一任务编号
	private String craneNum; //岸桥编号
	private Integer workType; //作业类型	int	0：装船作业；1:卸船作业；2：其他作业
	private String contaid; //箱号	string	11个长度字符串
	
	private String passTime; //作业时间  	
	private String createTime; //创建时间
	
	private String vesselCode;		//"vessel_code"//船舶代码
	private String vesselNumber;	//vessel_voyage_number //船舶艘次号
	
	private String vesselNameCn;		//vessel_name_cn	中文船名
	private String tallyClerk;			//tally_clerk  理货员
	private String desc;		//操作描述
	
	private int total;
	private int countTopPlate;
	private int countContaid;
	private int countBay;
	private int countAutoRecord;
	
	private List<String> craneNumList; //岸桥编号
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getCraneNum() {
		return craneNum;
	}
	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
	public Integer getWorkType() {
		return workType;
	}
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	public String getContaid() {
		return contaid;
	}
	public void setContaid(String contaid) {
		this.contaid = contaid;
	}
	public String getPassTime() {
		return passTime;
	}
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public String getVesselNumber() {
		return vesselNumber;
	}
	public void setVesselNumber(String vesselNumber) {
		this.vesselNumber = vesselNumber;
	}
	public String getVesselNameCn() {
		return vesselNameCn;
	}
	public void setVesselNameCn(String vesselNameCn) {
		this.vesselNameCn = vesselNameCn;
	}
	public String getTallyClerk() {
		return tallyClerk;
	}
	public void setTallyClerk(String tallyClerk) {
		this.tallyClerk = tallyClerk;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<String> getCraneNumList() {
		return craneNumList;
	}
	public void setCraneNumList(List<String> craneNumList) {
		this.craneNumList = craneNumList;
	}
	public int getCountTopPlate() {
		return countTopPlate;
	}
	public void setCountTopPlate(int countTopPlate) {
		this.countTopPlate = countTopPlate;
	}
	public int getCountContaid() {
		return countContaid;
	}
	public void setCountContaid(int countContaid) {
		this.countContaid = countContaid;
	}
	public int getCountBay() {
		return countBay;
	}
	public void setCountBay(int countBay) {
		this.countBay = countBay;
	}
	public int getCountAutoRecord() {
		return countAutoRecord;
	}
	public void setCountAutoRecord(int countAutoRecord) {
		this.countAutoRecord = countAutoRecord;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
