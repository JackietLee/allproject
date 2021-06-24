package net.pingfang.serviceImpl.qhdPdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.qhdPdf.TallyRecordService;
import net.pingfang.serviceImpl.qhdPdf.style.TallyRecordExcelStyle;
import net.pingfang.serviceImpl.workRecord.WorkRecordExportExcelServiceImpl;
import net.pingfang.utils.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * QHD导出理货记录EXCEL
 * @author Administrator
 *
 */
@Service
public class TallyRecordServiceImpl implements TallyRecordService{

	@Value("${pdf_file_path}")
	private String pdf_file_path;
	@Autowired
	private WL_BerthPlanService wl_berthPlanService;
	
	public String exportExcelWorkRecord(WorkRecordVo workRecordVo) {

		String strUrl = null;
		FileOutputStream os = null;
		//从数据库里获取导出数据
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		this.createSheet(wb, workRecordVo);
//		this.createContainerListXml(wb, workRecordVo);
		// 响应到客户端
		try{
			strUrl = pdf_file_path+"/理货记录_"+DateUtil.getDate("yyyyMMddHHmmss")+".xls";
            File file = new File(strUrl);
            os = new FileOutputStream(file);
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strUrl;
	}

	/**
     * @功能：手工构建一个简单格式的Excel Sheet面签
     */
    private void createSheet(HSSFWorkbook wb, WorkRecordVo workRecordVo) {
    	BerthPlanInfoVo bp = wl_berthPlanService.getBerthPlanInfoByVesselNumber(workRecordVo.getVesselNumber());
    	
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("理货记录数据");
        sheet.setDefaultColumnWidth(8);// 默认列宽
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,6));
        sheet.addMergedRegion(new CellRangeAddress(0,0,7,8));
        sheet.addMergedRegion(new CellRangeAddress(0,0,9,14));
        //每一行单元格
        this.setHSSFCell(row.createCell(0), "船名 中文：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(2), bp.getVesselNameCn(), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(7), "外文：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(8), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(9), bp.getVesselNameEn(), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第二行
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(20);
        
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(1,1,5,6));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(1,1,7,8));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(1,1,9,10));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(1,1,11,14));//起始行,结束行,起始列,结束列
        
        this.setHSSFCell(row1.createCell(0), "国籍：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(1), bp.getFlag(), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(5), "船公司：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(6), "", TallyRecordExcelStyle.getBodyStyle(wb));
        this.setHSSFCell(row1.createCell(7), "大连集发", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(8), "", TallyRecordExcelStyle.getBodyStyle(wb));
        this.setHSSFCell(row1.createCell(9), "航次", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(10), "", TallyRecordExcelStyle.getBodyStyle(wb));
        this.setHSSFCell(row1.createCell(11), bp.getOutVoyage(), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row1.createCell(12), "", TallyRecordExcelStyle.getBodyStyle(wb));
        this.setHSSFCell(row1.createCell(13), "", TallyRecordExcelStyle.getBodyStyle(wb));
        this.setHSSFCell(row1.createCell(14), "", TallyRecordExcelStyle.getBodyStyle(wb));
        
        //第三行
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(2,2,5,7));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(2,2,9,10));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(2,2,11,13));//起始行,结束行,起始列,结束列
        
        this.setHSSFCell(row2.createCell(0), "出口清单情况、箱数：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
       // this.setHSSFCell(row2.createCell(5), "295", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(8), "箱", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(9), "重量：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
       // this.setHSSFCell(row2.createCell(11), "5759.604", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row2.createCell(14), "吨", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第四行
        HSSFRow row3 = sheet.createRow(3);
        row3.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(3,3,0,4));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(3,3,5,7));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(3,3,9,10));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(3,3,11,13));//起始行,结束行,起始列,结束列
        
        this.setHSSFCell(row3.createCell(0), "实际 签证 情况、箱数：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
     //   this.setHSSFCell(row3.createCell(5), "293", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(8), "箱", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(9), "重量：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        //this.setHSSFCell(row3.createCell(11), "5759.204", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row3.createCell(14), "吨", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第五行
        HSSFRow row4 = sheet.createRow(4);
        row4.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(4,4,2,4));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(4,4,6,14));//起始行,结束行,起始列,结束列
        
        this.setHSSFCell(row4.createCell(0), "泊位：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(1), "No.", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(2), bp.getBerthName(), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(5), " 倒箱：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(8), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(9), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row4.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第六行
        HSSFRow row5 = sheet.createRow(5);
        row5.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(5,5,0,2));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(5,5,6,7));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(5,5,12,13));//起始行,结束行,起始列,结束列
        String tartTime = bp.getFstartTime();
        this.setHSSFCell(row5.createCell(0), "开工日期、时间：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(3), TallyRecordServiceImpl.getTime(tartTime,"Y"), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(4), "年", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(5), TallyRecordServiceImpl.getTime(tartTime,"M"), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(6), "月", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        this.setHSSFCell(row5.createCell(8), TallyRecordServiceImpl.getTime(tartTime,"D"), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(9), "日", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(11), "时", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row5.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第七行
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(6,6,0,2));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(6,6,6,7));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(6,6,12,13));//起始行,结束行,起始列,结束列
        
        String endTime = bp.getFendTime();
        this.setHSSFCell(row6.createCell(0), "完工日期、时间：", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(3), TallyRecordServiceImpl.getTime(endTime,"Y"), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(4), "年", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(5), TallyRecordServiceImpl.getTime(endTime,"M"), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(6), "月", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(8), TallyRecordServiceImpl.getTime(endTime,"D"), TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(9), "日", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(11), "时", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row6.createCell(14), "分", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第八行
        HSSFRow row7 = sheet.createRow(7);
        row7.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(7,7,0,14));//起始行,结束行,起始列,结束列
        
        this.setHSSFCell(row7.createCell(0), "理 货 单 证 传 递 登 记 表", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(8), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(9), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row7.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第九行
        HSSFRow row8 = sheet.createRow(8);
        row8.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(8,8,0,6));//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(8,8,7,14));//起始行,结束行,起始列,结束列
        this.setHSSFCell(row8.createCell(0), "集装箱", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(7), "杂货", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(8), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(9), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row8.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第十行
        HSSFRow row9 = sheet.createRow(9);
        row9.setHeightInPoints(80);
        
      //画线(由左上到右下的斜线)  在A1的第一个cell（单位  分类）加入一条对角线 
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
        HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short)9, 0, (short)0, 9);  
        HSSFSimpleShape shape1 = patriarch.createSimpleShape(a);  
        shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);   
        shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID) ; 
        
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        String br = "\r\n"; //换行符
//        String br = String.valueOf((char)10); //换行符
        sheet.addMergedRegion(new CellRangeAddress(9,9,13,14));//起始行,结束行,起始列,结束列
        this.setHSSFCell(row9.createCell(0), "名单   份数", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(1), "交"+br+"接"+br+"单", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(2), "残损"+br+br+"记录", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(3), "溢短"+br+br+"残损", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(4), "汇总"+br+br+"证明", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(5), "意"+br+"见"+br+"书", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(6), "理货"+br+br+"证明", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(7), "计"+br+"数"+br+"单", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(8), "船"+br+br+"图", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(9), "场站"+br+br+"收据", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(10), "理货"+br+br+"证明", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(11), "汇总"+br+br+"证明", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(12), "交接"+br+br+"时间", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(13), "收单"+br+"人"+br+"签名", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row9.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
        
        //第十一行
        HSSFRow row10 = sheet.createRow(10);
        row10.setHeightInPoints(20);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(10,10,13,14));//起始行,结束行,起始列,结束列
        this.setHSSFCell(row10.createCell(0), "毕博", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(8), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(9), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row10.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
       
        //第十二行
        HSSFRow row11 = sheet.createRow(11);
        row11.setHeightInPoints(40);
        //1.1创建合并单元格对象
        //2.1加载合并单元格对象
        sheet.addMergedRegion(new CellRangeAddress(11,11,0,14));//起始行,结束行,起始列,结束列
        this.setHSSFCell(row11.createCell(0), "工  作  情  况  记  录	", TallyRecordExcelStyle.getBodyStyle2(wb));
        this.setHSSFCell(row11.createCell(1), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(2), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(3), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(4), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(5), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(6), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(7), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(8), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(9), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(10), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(11), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(12), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(13), "", TallyRecordExcelStyle.getTitleStyle(wb));
        this.setHSSFCell(row11.createCell(14), "", TallyRecordExcelStyle.getTitleStyle(wb));
    }
    /**
     * 设置单元格
     * @param cell
     * @param value
     * @param cellStyle
     * @return
     */
    private HSSFCell setHSSFCell(HSSFCell cell,String value, HSSFCellStyle cellStyle) {
    	cell.setCellValue(value);
    	cell.setCellStyle(cellStyle);
    	return cell;
    }
    
    private static String getTime(String time, String type) {
    	String str = "";
    	if("Y".equals(type)) {
    		str = time.substring(0,4);
    	}else if("M".equals(type)) {
    		str = time.substring(5,7);
    	}else if("D".equals(type)) {
    		str = time.substring(8,10);
    	}
    	return str;
    }

}
