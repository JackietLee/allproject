package net.pingfang.serviceImpl.excel;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.pingfang.dao.excel.TzWorkExcelDao;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.excel.TzWorkExcelService;
import net.pingfang.utils.DateUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service
public class TzWorkExcelServiceImpl implements TzWorkExcelService{
	
	@Autowired
	private TzWorkExcelDao tzWorkExcelDao;
	
	/**
	 * exportExcel集装箱残损单
	 * @param workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public void exportExcelTzDamageInforRecord(HttpServletResponse response, WorkRecordVo workRecordVo) {
		//excel title
		String[] excelTitle = TzWorkExcelServiceImpl.getDamageInforExcelTitle();
		//从数据库里获取导出数据
		List<DamageInforRecordVo> damageInforRecordList = tzWorkExcelDao.exportExcelTzDamageInforRecord(workRecordVo);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		this.createDamageInforSheet(wb, damageInforRecordList, excelTitle);
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
	 * exportExcel Bay位数据
	 * @param workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public void exportExcelTzBayInfo(HttpServletResponse response, WorkRecordVo workRecordVo) {
		//excel title
		String[] excelTitle = {"箱号", "贝位", "卸货港"};
		//从数据库里获取导出数据
		List<WorkRecordVo> bayInfoList = tzWorkExcelDao.exportExcelTzBayInfo(workRecordVo);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		this.createBayInforSheet(wb, bayInfoList, excelTitle);
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
	 * exportExcel 作业箱量统计
	 * @param workRecordVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public void exportExcelContainerStatistics(HttpServletResponse response, WorkRecordVo workRecordVo) {
		//excel title
		String[] excelTitle = {"日期", "船名", "航次","进/出","20F","40F","20E","40E"};
		//从数据库里获取导出数据
		//List<WorkRecordVo> bayInfoList = tzWorkExcelDao.exportExcelTzBayInfo(workRecordVo);
		List<WorkRecordVo> containerList = new ArrayList<WorkRecordVo>();
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		this.createContainerListSheet(wb, containerList, excelTitle);
//		this.createContainerListXml(wb, containerList);
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
	 * 集装箱残损单
     * @功能：手工构建一个简单格式的Excel Sheet面签
     */
    private void createDamageInforSheet(HSSFWorkbook wb, List<DamageInforRecordVo> damageInforRecordList, String[] strArray) {
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("集装箱残损单");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        
        
        
        
        
        
        
        
        
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
        
        //字段样式（垂直居中）
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

          
        //设置边框样式
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        

        
        
        
        
        
        
        
        /*
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(20);
        
        //合并单元格
        CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 6); // 起始行, 终止行, 起始列, 终止列  
        sheet.addMergedRegion(cra);
        
        //合并单元格
        CellRangeAddress cra2 =new CellRangeAddress(0, 3, 1, 7); // 起始行, 终止行, 起始列, 终止列  
        sheet.addMergedRegion(cra2);
        
        //合并单元格
        CellRangeAddress cra3 =new CellRangeAddress(3, 3, 1, 7); // 起始行, 终止行, 起始列, 终止列  
        sheet.addMergedRegion(cra3);
        
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("船名");
        cell0.setCellStyle(cellStyle);
        
        HSSFCell cell = null;
       // for 
        for(int i=0;i<strArray.length;i++){
        	cell = row1.createCell(i);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(cellStyle2);
        }
        */
        
        //集装箱残损单
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(20);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7)); // 起始行, 终止行, 起始列, 终止列 

        HSSFCell cell = null;
        for(int i=0;i<strArray.length;i++){
        	if(i == 0) {
        		cell = row0.createCell(i);
                cell.setCellValue("集装箱残损单");
                cell.setCellStyle(cellStyle2);
        	}else {
        		cell = row0.createCell(i);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle2);
        	}
        	
        }
        String vesselName = "";	//中文船名
    	String vesselNumber = "";	//船舶艘次号
    	String passTime = ""; //创建时间
        if(null !=damageInforRecordList && damageInforRecordList.size() >0) {
        	DamageInforRecordVo dir = damageInforRecordList.get(0);
        	vesselName = dir.getVesselName();	//中文船名
        	vesselNumber = dir.getVesselNumber();	//船舶艘次号
        	passTime = dir.getCreateTime(); //创建时间
        }
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(20);
        
        HSSFCell cell0 = row1.createCell(0);
        cell0.setCellValue("船名");
        cell0.setCellStyle(cellStyle);
        
        HSSFCell cell1 = row1.createCell(1);
        cell1.setCellValue(vesselName);
        cell1.setCellStyle(cellStyle);
        
        HSSFCell cell2 = row1.createCell(2);
        cell2.setCellValue("航次");
        cell2.setCellStyle(cellStyle);
        
        HSSFCell cell3 = row1.createCell(3);
        cell3.setCellValue(vesselNumber);
        cell3.setCellStyle(cellStyle);
        
        HSSFCell cell4 = row1.createCell(4);
        cell4.setCellValue("开工日期");
        cell4.setCellStyle(cellStyle);
        
        HSSFCell cell5 = row1.createCell(5);
        cell5.setCellValue(passTime);
        cell5.setCellStyle(cellStyle);
        
        HSSFCell cell6 = row1.createCell(6);
        cell6.setCellValue("制单日期");
        cell6.setCellStyle(cellStyle);
        
        HSSFCell cell7 = row1.createCell(7);
        cell7.setCellValue(DateUtil.getDate("yyyy-MM-dd"));
        cell7.setCellStyle(cellStyle);
        
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeightInPoints(20);
        
        //合并单元格
        CellRangeAddress cra =new CellRangeAddress(2, 2, 2, 7); // 起始行, 终止行, 起始列, 终止列  
        sheet.addMergedRegion(cra);
        
        // 添加excel title
        for(int i=0;i<strArray.length;i++){
        	cell = row2.createCell(i);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(cellStyle2);
        }
        
        if(null !=damageInforRecordList && damageInforRecordList.size() >0) {
	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
	        for(int i=0; i<damageInforRecordList.size(); i++) {
	        	HSSFRow rowBody = sheet.createRow(i+3);
	        	rowBody.setHeightInPoints(16);
	        	 //合并单元格
	            sheet.addMergedRegion(new CellRangeAddress(i+3, i+3, 2, 7));// 起始行, 终止行, 起始列, 终止列  
	        	//箱号
	        	HSSFCell cellWorkType = rowBody.createCell(0);
	            cellWorkType.setCellValue(damageInforRecordList.get(i).getContainerNumber());
	            cellWorkType.setCellStyle(cellStyle2);
	        	//铅封号
	            HSSFCell cellCraneNum = rowBody.createCell(1);
	            cellCraneNum.setCellValue(this.getDoorLock(damageInforRecordList.get(i).getDoorLock()));
	            cellCraneNum.setCellStyle(cellStyle2);
	            
	            //残损情况
	            HSSFCell cellContainerType = rowBody.createCell(2);
	            cellContainerType.setCellValue(damageInforRecordList.get(i).getDamagedType());
	            cellContainerType.setCellStyle(cellStyle2);
	            
	            //补充空格
	            for(int j=3;j<strArray.length;j++){
	            	cell = rowBody.createCell(j);
	                cell.setCellValue("");
	                cell.setCellStyle(cellStyle2);
	            }
	            
	        }
        }
        /* */
    }
	 /**
	  * 创建excel title
    */
   public static String[] getDamageInforExcelTitle() {
       String[] strArray = {"箱号", "铅封号", "残损情况","","","","",""};
       return strArray;
   }
   /**
    * 是否有铅封(0:LOCK,1:UNLOCK,2:unknow)
    * @param doorLock
    * @return
    */
   private String getDoorLock(int doorLock) {
	   String str = "unknow";	//默认值
	   if(0 == doorLock) {
		   str = "LOCK"; 
	   }else if(1 == doorLock) {
		   str = "UNLOCK";
	   }
	   return str;
   }
 
   
   /**
	 * Bay位数据
    * @功能：手工构建一个简单格式的Excel Sheet面签
    */
   private void createBayInforSheet(HSSFWorkbook wb, List<WorkRecordVo> bayInforRecordList, String[] titleArray) {
       // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
       HSSFSheet sheet = wb.createSheet("Bay位数据");
       sheet.setDefaultColumnWidth(20);// 默认列宽
       
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
       
       //集装箱残损单
       HSSFRow row0 = sheet.createRow(0);
       row0.setHeightInPoints(20);
       // 添加excel title
       HSSFCell cell = null;
       for(int i=0;i<titleArray.length;i++){
    	   cell = row0.createCell(i);
           cell.setCellValue(titleArray[i]);
           cell.setCellStyle(cellStyle);
       }
       
       if(null !=bayInforRecordList && bayInforRecordList.size() >0) {
	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
	        for(int i=0; i<bayInforRecordList.size(); i++) {
	        	HSSFRow rowBody = sheet.createRow(i+1);
	        	rowBody.setHeightInPoints(16);
	        	//箱号
	        	HSSFCell cellWorkType = rowBody.createCell(0);
	            cellWorkType.setCellValue(bayInforRecordList.get(i).getUpdateContaid());
	            cellWorkType.setCellStyle(cellStyle2);
	        	//贝位
	            HSSFCell cellCraneNum = rowBody.createCell(1);
	            cellCraneNum.setCellValue(bayInforRecordList.get(i).getBayInfo());
	            cellCraneNum.setCellStyle(cellStyle2);
	            
	            //卸货港
	            HSSFCell cellContainerType = rowBody.createCell(2);
	            cellContainerType.setCellValue(bayInforRecordList.get(i).getPortDischarge());
	            cellContainerType.setCellStyle(cellStyle2);
	        }
       }
       /* */
   }
   
   
   
   /**
	 * 作业箱量统计
   * @功能：手工构建一个简单格式的Excel Sheet面签
   */
  private void createContainerListSheet(HSSFWorkbook wb, List<WorkRecordVo> containerList, String[] titleArray) {
      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
      HSSFSheet sheet = wb.createSheet("作业箱量统计");
      sheet.setDefaultColumnWidth(20);// 默认列宽
      
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
      
      //集装箱残损单
      HSSFRow row0 = sheet.createRow(0);
      row0.setHeightInPoints(20);
      // 添加excel title
      HSSFCell cell = null;
      for(int i=0;i<titleArray.length;i++){
   	   cell = row0.createCell(i);
          cell.setCellValue(titleArray[i]);
          cell.setCellStyle(cellStyle);
      }
      
      if(null !=containerList && containerList.size() >0) {
	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
	        for(int i=0; i<containerList.size(); i++) {
	        	HSSFRow rowBody = sheet.createRow(i+1);
	        	rowBody.setHeightInPoints(16);
	        	//箱号
	        	HSSFCell cellWorkType = rowBody.createCell(0);
	            cellWorkType.setCellValue(containerList.get(i).getUpdateContaid());
	            cellWorkType.setCellStyle(cellStyle2);
	        	//贝位
	            HSSFCell cellCraneNum = rowBody.createCell(1);
	            cellCraneNum.setCellValue(containerList.get(i).getBayInfo());
	            cellCraneNum.setCellStyle(cellStyle2);
	            
	            //卸货港
	            HSSFCell cellContainerType = rowBody.createCell(2);
	            cellContainerType.setCellValue(containerList.get(i).getPortDischarge());
	            cellContainerType.setCellStyle(cellStyle2);
	        }
      }
      /* */
  }


}
