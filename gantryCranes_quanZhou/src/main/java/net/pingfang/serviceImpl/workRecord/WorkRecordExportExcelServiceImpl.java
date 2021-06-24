package net.pingfang.serviceImpl.workRecord;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.pingfang.entity.vessel.BerthPlanInfoVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pingfang.dao.huangPu.HpWorkRecordDao;
import net.pingfang.dao.workRecord.WorkRecordDao;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.workRecord.WorkRecordExportExcelService;

@Service
public class WorkRecordExportExcelServiceImpl implements WorkRecordExportExcelService {

	@Autowired
	private WorkRecordDao workRecordDao;
	@Autowired
	private HpWorkRecordDao hpWorkRecordDao;
	
	private static String F = "F";
	private static String A = "A";
	private static String M = "M";
	private static String OTHER = "其他";
	
	/**
	 * 综合查询导出
	 */
	public void exportExcelWorkRecord(HttpServletResponse response, WorkRecordVo workRecordVo) {
		//excel title
		String[] excelTitle = WorkRecordExportExcelServiceImpl.getExcelTitle();
		//从数据库里获取导出数据
		List<WorkRecordVo> workRecordList = workRecordDao.exportAllWorkRecord(workRecordVo);
		// 第一步，创建一个webbook，对应一个Excel文件
		// 响应到客户端
		try{
			Document document = DocumentHelper.createDocument();
//        this.createSheet(wb, workRecordList, excelTitle);
			OutputFormat format = OutputFormat.createPrettyPrint();
			document.setXMLEncoding("UTF-8");
			format.setEncoding("UTF-8");
			this.createContainerListXml(workRecordVo,document, workRecordList);
			OutputStream os = response.getOutputStream();
			XMLWriter writer = new XMLWriter(os, format);
			// 设置是否转义，默认使用转义字符
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createContainerListXml(WorkRecordVo workRecordVoInput,Document document, List<WorkRecordVo> containerList) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 向bookstore根节点中添加子节点book
			Element root = document.addElement("ROOT");
			Element ship = root.addElement("SHIP");
			Element vesselNameEle = ship.addElement("船名");
			String vesselName = containerList.size()<=0?"":containerList.get(0).getVesselNameCn();
			vesselNameEle.setText(vesselName==null?"":vesselName);
			Element voyageNumberEle = ship.addElement("航次");
			String[] split = workRecordVoInput.getVesselCode().split("_");
			String voyageNumber = split[split.length-1];  //航次
			voyageNumberEle.setText(voyageNumber==null?"":voyageNumber);
			Element tradeTypeEle = ship.addElement("内外贸");
			String tradeType = containerList.size()<=0?"":containerList.get(0).getTradeType();
			tradeTypeEle.setText(tradeType==null?"":tradeType);
			Element workTypeEle = ship.addElement("进出口");
			String workType = (workRecordVoInput.getWorkType()==0||workRecordVoInput.getWorkType()==16)?"出口":((workRecordVoInput.getWorkType()==1||workRecordVoInput.getWorkType()==17)?"进口":"");
			workTypeEle.setText(workType);
			Element sumEle = ship.addElement("总数");
			int sum = containerList.size();
			sumEle.setText(sum+"");
			Element timeEle = ship.addElement("时间");
			String time = sdf.format(new Date());
			timeEle.setText(time);
			Element lihuoEle = ship.addElement("理货");
			lihuoEle.setText("平方");
			Element list = ship.addElement("LIST");
			for(WorkRecordVo workRecordVo:containerList) {
				Element raw = list.addElement("RAW");
				Element billNumber = raw.addElement("提单号");
				billNumber.setText(workRecordVo.getBillNumber()==null?"":workRecordVo.getBillNumber());
				Element agent = raw.addElement("代理");
				agent.setText("");
				Element operator = raw.addElement("营运人");
				operator.setText("");
				Element containerNo = raw.addElement("箱号");
				containerNo.setText(workRecordVo.getContaid()==null?"":workRecordVo.getContaid());
				Element cargoType = raw.addElement("箱型");
				cargoType.setText(workRecordVo.getCargoType()==null?"":workRecordVo.getCargoType());
				Element sizeType = raw.addElement("箱类");
				sizeType.setText(workRecordVo.getSizeType()==null?"":workRecordVo.getSizeType());
				Element stuffingStatus = raw.addElement("重空状态");
				stuffingStatus.setText(workRecordVo.getStuffingStatus()==null?"":workRecordVo.getStuffingStatus());
				Element sealNumber = raw.addElement("铅封号");
				sealNumber.setText(workRecordVo.getSealNumber()==null?"":workRecordVo.getSealNumber());
				Element portDestination = raw.addElement("目的港");
				portDestination.setText(workRecordVo.getPortDestination()==null?"":workRecordVo.getPortDestination());
				Element portDischarge = raw.addElement("卸货港");
				portDischarge.setText(workRecordVo.getPortDischarge()==null?"":workRecordVo.getPortDischarge());
				Element vesselPosition = raw.addElement("位置");
				vesselPosition.setText(workRecordVo.getVesselPosition()==null?"":workRecordVo.getVesselPosition());
				Element tallyClerk = raw.addElement("理货员");
				tallyClerk.setText(workRecordVo.getTallyClerk()==null?"":workRecordVo.getTallyClerk());
				Element containerTime = raw.addElement("时间");
				containerTime.setText(workRecordVo.getPassTime()==null?"":workRecordVo.getPassTime());
				Element IsTransfer = raw.addElement("中转");
				IsTransfer.setText(workRecordVo.getIsTransfer()==null?"":workRecordVo.getIsTransfer());

			}
			// 为book节点添加属性
			// 将book节点添加到bookstore根节点中
			// 将bookstore节点（已包含book）添加到dom树中
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * （新增）黄埔门机综合查询导出
	 */
	public void exportExcelHpWorkRecord(HttpServletResponse response,WorkRecordVo workRecordVo) {
		//excel title
		String[] excelTitle = WorkRecordExportExcelServiceImpl.getExcelTitle();
		//从数据库里获取导出数据
		List<WorkRecordVo> workRecordList = hpWorkRecordDao.exportAllHpWorkRecord(workRecordVo);
		// 第一步，创建一个webbook，对应一个Excel文件
	    HSSFWorkbook wb = new HSSFWorkbook();
		this.createSheet(wb, workRecordList, excelTitle);
		// 响应到客户端
		try{
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
     * @功能：手工构建一个简单格式的Excel Sheet面签
     */
    private void createSheet(HSSFWorkbook wb, List<WorkRecordVo> workRecordList, String[] strArray) {
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("历史作业数据");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 创建一个居中格式
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        
        //设置边框样式
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        cellStyle.setFont(fontStyle);


        //字段样式（垂直居中）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

        //设置边框样式
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        

        // 添加excel title
        HSSFCell cell = null;
        for(int i=0;i<strArray.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(cellStyle);
        }
        int orderid =0;
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
        for(int i=0; i<workRecordList.size(); i++) {
        	HSSFRow rowBody = sheet.createRow(i+1);
        	rowBody.setHeightInPoints(16);
        	/*中文船名待定
        	HSSFCell cellCount = rowBody.createCell(0);
        	cellCount.setCellValue(0);
        	cellCount.setCellStyle(cellStyle2);*/
        	
        	//作业类型
        	HSSFCell cellWorkType = rowBody.createCell(0);
            cellWorkType.setCellValue(this.getWorkTypeFormat(workRecordList.get(i).getWorkType()));
            cellWorkType.setCellStyle(cellStyle2);
        	//按桥编号
            HSSFCell cellCraneNum = rowBody.createCell(1);
            cellCraneNum.setCellValue(workRecordList.get(i).getCraneNum());
            cellCraneNum.setCellStyle(cellStyle2);
            //箱类型
            HSSFCell cellContainerType = rowBody.createCell(2);
            cellContainerType.setCellValue(this.getContainerTypeFormat(workRecordList.get(i).getContainerType()));
            cellContainerType.setCellStyle(cellStyle2);
            //箱号
            HSSFCell cellContaid = rowBody.createCell(3);
            cellContaid.setCellValue(workRecordList.get(i).getContaid());
            cellContaid.setCellStyle(cellStyle2);
            //箱号ISO
            HSSFCell cellIso = rowBody.createCell(4);
            cellIso.setCellValue(workRecordList.get(i).getIso());
            cellIso.setCellStyle(cellStyle2);
            //是否有异常（默认为无异常）
            HSSFCell cellError = rowBody.createCell(5);
            cellError.setCellValue("无异常");
            cellError.setCellStyle(cellStyle2);
            //车顶号
            HSSFCell cellTopPlate = rowBody.createCell(6);
            cellTopPlate.setCellValue(workRecordList.get(i).getUpdateTopPlate());
            cellTopPlate.setCellStyle(cellStyle2);
            //车上位置
            HSSFCell cellOrderid = rowBody.createCell(7);
            orderid = workRecordList.get(i).getOrderid();
            //箱序号(0为'F'前箱；1为'A'后箱；2为'M'长箱；5为其他)
            if(0 == orderid) {
            	cellOrderid.setCellValue(F);
            }else if(1 == orderid) {
            	cellOrderid.setCellValue(A);
            }else if(2 == orderid) {
            	cellOrderid.setCellValue(M);
            }else {
            	cellOrderid.setCellValue(OTHER);
            }
            cellOrderid.setCellStyle(cellStyle2);            
            //作业时间
            HSSFCell cellPassTime = rowBody.createCell(8);
            cellPassTime.setCellValue(workRecordList.get(i).getPassTime());
            cellPassTime.setCellStyle(cellStyle2);
        }
    }

	 /**
	  * 创建excel title
     */
    public static String[] getExcelTitle() {
        String[] strArray = {"作业类型", "岸桥编号", "箱类型", "箱号", "箱名", "异常类型","车顶号","车上位置", "作业时间" };
        return strArray;
    }
    /**
     * 作业类型	int	0：装船作业；1:卸船作业；2：其他作业
     * @param workType
     * @return
     */
    private String getWorkTypeFormat(int workType) {
    	String type = null;
    	switch(workType){
	    	case 0 :
	        	type = "装船作业";
	           break; //可选
	        case 1 :
	        	type = "卸船作业";
	           break; //可选
	        default : 
	        	type = "其他作业";
    	}
    	return type;
    }
    /**
     * 箱类型	int	0：长箱,1：短箱,2：双箱,10：未知
     * @param workType
     * @return
     */
    private String getContainerTypeFormat(int containerType) {
    	String type = null;
    	switch(containerType){
	    	case 0 :
	        	type = "长箱";
	           break;
	        case 1 :
	        	type = "短箱";
	           break;
	        case 2 :
	        	type = "双箱";
	           break;
	        default : 
	        	type = "未知";
    	}
    	return type;
    }
}
