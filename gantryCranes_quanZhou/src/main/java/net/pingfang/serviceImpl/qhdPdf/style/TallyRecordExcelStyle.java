package net.pingfang.serviceImpl.qhdPdf.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TallyRecordExcelStyle {
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {
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
        cellStyle.setWrapText(true);
        
        return cellStyle;
	}
	
	public static HSSFCellStyle getBodyStyle(HSSFWorkbook wb) {
		 //字段样式（垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //设置边框样式
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
       
       return cellStyle;
	}
	
	public static HSSFCellStyle getBodyStyle2(HSSFWorkbook wb) {
		 // 第四步，创建单元格，并设置值表头 设置表头居中
       HSSFCellStyle cellStyle = wb.createCellStyle();
       // 创建一个居中格式
       cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);//垂直居中
       
       //设置边框样式
       cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
       cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
       cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
       cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

       HSSFFont fontStyle = wb.createFont();
       fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

       cellStyle.setFont(fontStyle);
       
       return cellStyle;
	}

}
