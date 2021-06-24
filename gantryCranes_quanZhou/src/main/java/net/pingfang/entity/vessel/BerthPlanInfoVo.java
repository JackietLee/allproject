package net.pingfang.entity.vessel;
/**
 * 泊位计划信息(berthPlanInfo表)
 * @author Administrator
 * @since 2019-06-01
 *
 */
public class BerthPlanInfoVo {
	private int id;						//entity (1,1) primary key,   
	private String vesselCode;			//vessel_code  --船舶代码
	private String vesselNameCn;		//vessel_name_cn	中文船名
	private String vesselNameEn;		//vessel_name_en	--英文船名
	private String vesselNumber; 		//vessel_voyage_number 船舶艘次号
	private String flag;				//flag	   --船籍
	private String vesselType;				//vessel_type	  --船舶类型
	private String operatorCode;		//operator_code	--船公司代码
	private String agent;				//agent	--船代理
	private String inVoyage;			//in_voyage	 --进口航次
	private String outVoyage;			//out_voyage	 --出口航次
	private String inLaneCode;			//in_lane_code	--进口航线代码
	private String outLaneCode;			//out_lane_code	--出口航线代码
	private String alongside;			//alongside	  --船舷方向
	
	private int vesselStatus;			//vessel_status	--船舶状态
	private String allLength;			//length_over_all 	 --船舶长度
	private String betweenLength;		//between_length  --两柱间长度
	private String bowToBridge;			//bow_to_bridge --船首到舰桥距离
	private String sternToBridge;		//stern_to_bridge	--船尾到舰桥距离
	private String berthName;			//berth_name	  --泊位
	private String startBitt;			//start_bitt	   --起始桩位
	private String endBitt;				//end_bitt	     --结束桩位
	private String aberthingTime;		//atb	--实际靠泊时间
	private String adepartureTime;		//atd  --实际离泊时间
	private int loadMount;				//loading_amount	 --装箱量
	private int dischargMount;			//discharging_amount	 --卸箱量
	
	private int ldMount;				//集装箱表里装箱数量（箱状态 0: 未理货 ，作业类型：LD：装船）
	private int dsMount;				//集装箱表里卸箱数量（箱状态 0: 未理货 ，作业类型：DS：卸船）
	
	private String etaTime;		//eta  --预计到港时间
	private String etbTime;		//etb   --预计靠泊时间
	private String etdTime;		//etd  --预计离泊时间
	
	private String startTime;			//cod  --卸船开工时间
	private String endTime;			//col	   --卸船计完工时间
	
	
	private String aarrivalTime;		//actual_arrival_time	--实际到港时间
	
	private String fstartTime;			//fdd	--卸船开工时间
	private String fendTime;			//fld --卸船完工时间
	
	private String yardOpenTime;		//yard_open_time --堆场开放时间
	private String yardCloseTime;		//yard_close_time --堆场关闭时间
	
	private String jobProgress;			//job_progress	 --作业进度
	private String isDownloaded;		//is_downloaded	 --是否下载箱清单
	private String isFinished;			//is_finished 是否已完工
	private String updateTime;			//update_time     --更新时间
	private String createtime;          //创建时间
	
	private String tallyClerk;		//创建人
	private Integer currentPage;
	private Integer pageSize;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
	public String getVesselNameCn() {
		return vesselNameCn;
	}
	public void setVesselNameCn(String vesselNameCn) {
		this.vesselNameCn = vesselNameCn;
	}
	public String getVesselNameEn() {
		return vesselNameEn;
	}
	public void setVesselNameEn(String vesselNameEn) {
		this.vesselNameEn = vesselNameEn;
	}
	public String getVesselNumber() {
		return vesselNumber;
	}
	public void setVesselNumber(String vesselNumber) {
		this.vesselNumber = vesselNumber;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getVesselType() {
		return vesselType;
	}
	public void setVesselType(String vesselType) {
		this.vesselType = vesselType;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getInVoyage() {
		return inVoyage;
	}
	public void setInVoyage(String inVoyage) {
		this.inVoyage = inVoyage;
	}
	public String getOutVoyage() {
		return outVoyage;
	}
	public void setOutVoyage(String outVoyage) {
		this.outVoyage = outVoyage;
	}
	public String getInLaneCode() {
		return inLaneCode;
	}
	public void setInLaneCode(String inLaneCode) {
		this.inLaneCode = inLaneCode;
	}
	public String getOutLaneCode() {
		return outLaneCode;
	}
	public void setOutLaneCode(String outLaneCode) {
		this.outLaneCode = outLaneCode;
	}
	public String getAlongside() {
		return alongside;
	}
	public void setAlongside(String alongside) {
		this.alongside = alongside;
	}
	public int getVesselStatus() {
		return vesselStatus;
	}
	public void setVesselStatus(int vesselStatus) {
		this.vesselStatus = vesselStatus;
	}
	public String getAllLength() {
		return allLength;
	}
	public void setAllLength(String allLength) {
		this.allLength = allLength;
	}
	public String getBetweenLength() {
		return betweenLength;
	}
	public void setBetweenLength(String betweenLength) {
		this.betweenLength = betweenLength;
	}
	public String getBowToBridge() {
		return bowToBridge;
	}
	public void setBowToBridge(String bowToBridge) {
		this.bowToBridge = bowToBridge;
	}
	public String getSternToBridge() {
		return sternToBridge;
	}
	public void setSternToBridge(String sternToBridge) {
		this.sternToBridge = sternToBridge;
	}
	public String getBerthName() {
		return berthName;
	}
	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}
	public String getStartBitt() {
		return startBitt;
	}
	public void setStartBitt(String startBitt) {
		this.startBitt = startBitt;
	}
	public String getEndBitt() {
		return endBitt;
	}
	public void setEndBitt(String endBitt) {
		this.endBitt = endBitt;
	}
	public String getAberthingTime() {
		return aberthingTime;
	}
	public void setAberthingTime(String aberthingTime) {
		this.aberthingTime = aberthingTime;
	}
	public String getAdepartureTime() {
		return adepartureTime;
	}
	public void setAdepartureTime(String adepartureTime) {
		this.adepartureTime = adepartureTime;
	}
	public int getLoadMount() {
		return loadMount;
	}
	public void setLoadMount(int loadMount) {
		this.loadMount = loadMount;
	}
	public int getDischargMount() {
		return dischargMount;
	}
	public void setDischargMount(int dischargMount) {
		this.dischargMount = dischargMount;
	}
	public int getLdMount() {
		return ldMount;
	}
	public void setLdMount(int ldMount) {
		this.ldMount = ldMount;
	}
	public int getDsMount() {
		return dsMount;
	}
	public void setDsMount(int dsMount) {
		this.dsMount = dsMount;
	}
	public String getEtaTime() {
		return etaTime;
	}
	public void setEtaTime(String etaTime) {
		this.etaTime = etaTime;
	}
	public String getEtbTime() {
		return etbTime;
	}
	public void setEtbTime(String etbTime) {
		this.etbTime = etbTime;
	}
	public String getEtdTime() {
		return etdTime;
	}
	public void setEtdTime(String etdTime) {
		this.etdTime = etdTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAarrivalTime() {
		return aarrivalTime;
	}
	public void setAarrivalTime(String aarrivalTime) {
		this.aarrivalTime = aarrivalTime;
	}
	public String getFstartTime() {
		return fstartTime;
	}
	public void setFstartTime(String fstartTime) {
		this.fstartTime = fstartTime;
	}
	public String getFendTime() {
		return fendTime;
	}
	public void setFendTime(String fendTime) {
		this.fendTime = fendTime;
	}
	public String getYardOpenTime() {
		return yardOpenTime;
	}
	public void setYardOpenTime(String yardOpenTime) {
		this.yardOpenTime = yardOpenTime;
	}
	public String getYardCloseTime() {
		return yardCloseTime;
	}
	public void setYardCloseTime(String yardCloseTime) {
		this.yardCloseTime = yardCloseTime;
	}
	public String getJobProgress() {
		return jobProgress;
	}
	public void setJobProgress(String jobProgress) {
		this.jobProgress = jobProgress;
	}
	public String getIsDownloaded() {
		return isDownloaded;
	}
	public void setIsDownloaded(String isDownloaded) {
		this.isDownloaded = isDownloaded;
	}
	public String getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(String isFinished) {
		this.isFinished = isFinished;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getTallyClerk() {
		return tallyClerk;
	}
	public void setTallyClerk(String tallyClerk) {
		this.tallyClerk = tallyClerk;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
