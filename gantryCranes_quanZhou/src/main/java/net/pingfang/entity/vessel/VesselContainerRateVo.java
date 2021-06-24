package net.pingfang.entity.vessel;

public class VesselContainerRateVo {
    private String vesselName;		//船名
    private String voyageNo;       //航次
    private String workType;       //0 装船 1 卸船
    private String craneNum; 			//岸桥编号
    private String truckNum;       //拖车号
    private String iso;            //箱尺寸
    private String containerType;  //箱型  箱体类型(0：长箱,1：短箱,2：双箱,10：未知)
    private int containerWeight;   //吊具称重
    private String vesselPosition; //预配bay
    private String bayInfo;        //实装bay
    private String passtime;       //时间
    private String containerNum;   //箱号

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVoyageNo() {
        return voyageNo;
    }

    public void setVoyageNo(String voyageNo) {
        this.voyageNo = voyageNo;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getCraneNum() {
        return craneNum;
    }

    public void setCraneNum(String craneNum) {
        this.craneNum = craneNum;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public int getContainerWeight() {
        return containerWeight;
    }

    public void setContainerWeight(int containerWeight) {
        this.containerWeight = containerWeight;
    }

    public String getVesselPosition() {
        return vesselPosition;
    }

    public void setVesselPosition(String vesselPosition) {
        this.vesselPosition = vesselPosition;
    }

    public String getBayInfo() {
        return bayInfo;
    }

    public void setBayInfo(String bayInfo) {
        this.bayInfo = bayInfo;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }

    public String getContainerNum() {
        return containerNum;
    }

    public void setContainerNum(String containerNum) {
        this.containerNum = containerNum;
    }

    @Override
    public String toString() {
        return "VesselContainerRateVo{" +
                "vesselName='" + vesselName + '\'' +
                ", voyageNo='" + voyageNo + '\'' +
                ", workType='" + workType + '\'' +
                ", craneNum='" + craneNum + '\'' +
                ", truckNum='" + truckNum + '\'' +
                ", iso='" + iso + '\'' +
                ", containerType='" + containerType + '\'' +
                ", containerWeight=" + containerWeight +
                ", vesselPosition='" + vesselPosition + '\'' +
                ", bayInfo='" + bayInfo + '\'' +
                ", passtime='" + passtime + '\'' +
                ", containerNum='" + containerNum + '\'' +
                '}';
    }
}
