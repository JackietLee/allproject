package net.pingfang.entity.work;

/**
 * 图片信息记录表(imgInfo)
 * @author Administrator
 * @since 2019-05-22
 *
 */
public class ImgInfoVo {
	
	private int id; //identity (1,1) primary key,   
	private int workId; //作业记录唯一记录ID	uuid	外键作业记录表
	private String seqNo; //unique唯一任务编号
	private String location; //相机位置			
	private int snapImgType; //图片采集类型，0：其他图片，1：箱号图片, 2:车辆图片，3：车顶图片，4：破损图片：5：贝位图片		
	private int imgNum; //图片数量			
	private String imgPathName; //图片路径	111.jpg;222.jpg;333.jpg		
	private String imgDectRect; //检测结果	aaa;bbb;ccc;		
	private String createTime; //创建时间
	private String widthHeight; //图片长和宽
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSnapImgType() {
		return snapImgType;
	}
	public void setSnapImgType(int snapImgType) {
		this.snapImgType = snapImgType;
	}
	public int getImgNum() {
		return imgNum;
	}
	public void setImgNum(int imgNum) {
		this.imgNum = imgNum;
	}
	public String getImgPathName() {
		return imgPathName;
	}
	public void setImgPathName(String imgPathName) {
		this.imgPathName = imgPathName;
	}
	public String getImgDectRect() {
		return imgDectRect;
	}
	public void setImgDectRect(String imgDectRect) {
		this.imgDectRect = imgDectRect;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getWidthHeight() {
		return widthHeight;
	}
	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}
	@Override
	public String toString() {
		return "ImgInfoVo [id=" + id + ", workId=" + workId + ", seqNo=" + seqNo + ", location=" + location
				+ ", snapImgType=" + snapImgType + ", imgNum=" + imgNum + ", imgPathName=" + imgPathName
				+ ", imgDectRect=" + imgDectRect + ", createTime=" + createTime + ", widthHeight=" + widthHeight + "]";
	}
	
	
}
