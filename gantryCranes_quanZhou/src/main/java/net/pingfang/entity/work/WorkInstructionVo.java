package net.pingfang.entity.work;
/**
 * 作业指令(workInstruction表)
 * @author Administrator
 * @since 2019-06-01
 *
 */
public class WorkInstructionVo {
	private int id; 					//identity (1,1) primary key    
	private String vesselNumber;		//vessel_voyage_number --船舶唯一编码			
	private String  containerNumber;	//container_number	--集装箱号			
	private String  craneName;			//crane_name	--门机名称			
	private String  truckName;			//truck_name	--拖车名称			
	private String  vesselPisition;		//vessel_pisition	--船上位置			
	private String  workType;			//work_type	--作业类型	(说明：'DS'为'卸船', 'DD'为'直提', 'LD'为'装船','DL'为'直装', 'LN'为'捣卸', 'RS'为'捣装','SH'为'搬移','DT'为'中转不落地')	
	private String  shipSide;			//ship_side	--L左/R右旋靠泊			
	private String  createTime;       	//创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVesselNumber() {
		return vesselNumber;
	}
	public void setVesselNumber(String vesselNumber) {
		this.vesselNumber = vesselNumber;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getCraneName() {
		return craneName;
	}
	public void setCraneName(String craneName) {
		this.craneName = craneName;
	}
	public String getTruckName() {
		return truckName;
	}
	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}
	public String getVesselPisition() {
		return vesselPisition;
	}
	public void setVesselPisition(String vesselPisition) {
		this.vesselPisition = vesselPisition;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getShipSide() {
		return shipSide;
	}
	public void setShipSide(String shipSide) {
		this.shipSide = shipSide;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
