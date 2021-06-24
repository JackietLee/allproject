package net.pingfang.entity.qz;

public class DirectContainerAndCar {
	
	private String vesselVoyageNumber; //船号
    private String containerNumber; //箱号
    private String carNO; //车号
    private String zydt; //闸口直装  卸船直提
    private String jobType; //业务类型
    
	public String getVesselVoyageNumber() {
		return vesselVoyageNumber;
	}
	public void setVesselVoyageNumber(String vesselVoyageNumber) {
		this.vesselVoyageNumber = vesselVoyageNumber;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getCarNO() {
		return carNO;
	}
	public void setCarNO(String carNO) {
		this.carNO = carNO;
	}
	
	public String getZydt() {
		return zydt;
	}
	public void setZydt(String zydt) {
		this.zydt = zydt;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
    
}
