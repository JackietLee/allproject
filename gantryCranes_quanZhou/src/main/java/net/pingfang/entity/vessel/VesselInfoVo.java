package net.pingfang.entity.vessel;
/**
 * 船舶信息（vesselInfo表）
 * @author Administrator
 *
 */
public class VesselInfoVo {
	private int id;					//identity (1,1) primary key
	private String vesselCode;		//vessel_code --船舶代码
	private String vesselNameCn;	//vessel_name_cn --中文船名
	private String vesselNameEn;	//vessel_name_en --英文船名
	private String operatorCode;	//operator_code --船公司代码
	private String agent;			//agent	--船代理
	private String vesselType;		//vessel_type --船舶类型
	private Float allLength;		//all_length --船舶长度
	private Float perLength;		//perpendiculars_length	--两柱间长度
	private Float vesselWidth;		//vessel_width	--船舶宽度
	private String imo;				//国际海事组织识别码
	private String mmsi;			//水上移动通信业务标识码
	private String callSign;		//call_sign	--通信呼号
	private String flag;			//船籍
	private Float bowBridge;		//bow_to_bridge --船首到舰桥距离
	private Float sternBridge;		//stern_to_bridge	--船尾到舰桥距离
	private Float grossTonnage;		//gross_tonnage --总吨位
	private Float netTonnage;		//net_tonnage	--净吨位
	private Float summerDraft;		//summer_draft	--吃水深度
	private int maxCapacity;		//max_capacity --最大装载量
	private int deckMaxRow;			//deck_max_row  --甲板最大排数
	private int deckMaxTier;		//deck_max_tier --甲板最大层数
	private int holdMaxRow;			//hold_max_row  --船舱最大排数
	private int holdMaxTier;		//hold_max_tier  --船舱最大层数
	private String createTime;		//创建时间
	
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
	public String getVesselType() {
		return vesselType;
	}
	public void setVesselType(String vesselType) {
		this.vesselType = vesselType;
	}
	public Float getAllLength() {
		return allLength;
	}
	public void setAllLength(Float allLength) {
		this.allLength = allLength;
	}
	public Float getPerLength() {
		return perLength;
	}
	public void setPerLength(Float perLength) {
		this.perLength = perLength;
	}
	public Float getVesselWidth() {
		return vesselWidth;
	}
	public void setVesselWidth(Float vesselWidth) {
		this.vesselWidth = vesselWidth;
	}
	public String getImo() {
		return imo;
	}
	public void setImo(String imo) {
		this.imo = imo;
	}
	public String getMmsi() {
		return mmsi;
	}
	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Float getBowBridge() {
		return bowBridge;
	}
	public void setBowBridge(Float bowBridge) {
		this.bowBridge = bowBridge;
	}
	public Float getSternBridge() {
		return sternBridge;
	}
	public void setSternBridge(Float sternBridge) {
		this.sternBridge = sternBridge;
	}
	public Float getGrossTonnage() {
		return grossTonnage;
	}
	public void setGrossTonnage(Float grossTonnage) {
		this.grossTonnage = grossTonnage;
	}
	public Float getNetTonnage() {
		return netTonnage;
	}
	public void setNetTonnage(Float netTonnage) {
		this.netTonnage = netTonnage;
	}
	public Float getSummerDraft() {
		return summerDraft;
	}
	public void setSummerDraft(Float summerDraft) {
		this.summerDraft = summerDraft;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public int getDeckMaxRow() {
		return deckMaxRow;
	}
	public void setDeckMaxRow(int deckMaxRow) {
		this.deckMaxRow = deckMaxRow;
	}
	public int getDeckMaxTier() {
		return deckMaxTier;
	}
	public void setDeckMaxTier(int deckMaxTier) {
		this.deckMaxTier = deckMaxTier;
	}
	public int getHoldMaxRow() {
		return holdMaxRow;
	}
	public void setHoldMaxRow(int holdMaxRow) {
		this.holdMaxRow = holdMaxRow;
	}
	public int getHoldMaxTier() {
		return holdMaxTier;
	}
	public void setHoldMaxTier(int holdMaxTier) {
		this.holdMaxTier = holdMaxTier;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
