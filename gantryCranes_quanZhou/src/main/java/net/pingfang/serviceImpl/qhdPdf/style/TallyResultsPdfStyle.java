package net.pingfang.serviceImpl.qhdPdf.style;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import static com.itextpdf.text.Rectangle.BOTTOM;

public class TallyResultsPdfStyle {
    private static Font headfont ; // 设置字体大小
    private static Font keyfont;   // 设置字体大小
    private static Font textfont;  // 设置字体大小
    private static Font textfont2;

    static{
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            headfont = new Font(bfChinese, 24, Font.BOLD,BaseColor.BLACK);// 设置字体大小
            keyfont = new Font(bfChinese, 12, Font.BOLD,BaseColor.BLACK);// 设置字体大小
            textfont = new Font(bfChinese, 10, Font.NORMAL,BaseColor.BLACK);// 设置字体大小
            textfont2 = new Font(bfChinese, 10, Font.NORMAL|Font.UNDERLINE,BaseColor.BLACK);// 设置字体大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    //表格标题
    public static PdfPCell createHeadCell(String value){
    	
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    
    
  //表格表头样式1
    public static  PdfPCell createHeadCell_1(String value){
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont2));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
  //表格表头样式1
    public static  PdfPCell createHeadCell_1_3(String value){
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont2));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    
    public static  PdfPCell createHeadCell_1_1(String value){
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
       cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont2));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
       // cell.setBorder(BOTTOM);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
  //表格标题
    public static PdfPCell createHeadCell_1_2(String value){
    	
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
  //表格标题
    public static PdfPCell createHeadCell_1_4(String value){
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_RIGHT);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_1_44(String value){
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_RIGHT);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
  //表格表头样式1
    public static  PdfPCell createHeadCell_1_5(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(6);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
       // cell.setBorder(0);
        cell.setBorder(BOTTOM);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    //表格标题
    public static PdfPCell createHeadCell_1_6(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(8);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
        cell.setPaddingLeft(4.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    
    //表格标题
    public static PdfPCell createHeadCell_1_7(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(8);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        cell.setPaddingTop(5.0f);
        cell.setBorder(0);
        cell.setPadding(0);
        cell.setPaddingLeft(4.0f);
        cell.setFixedHeight(15);
        return cell;
    }
  //表格表头样式1
    public static  PdfPCell createHeadCell_1_8(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(BOTTOM);
        cell.setPadding(0);
        cell.setFixedHeight(15);
        return cell;
    }
    //表格标题
    public static PdfPCell createHeadCell_1_9(String value){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_1_10(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        cell.setBorder(0);
        //cell.setPaddingLeft(4.0f);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static  PdfPCell createHeadCell_1_11(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        //cell.setColspan(2);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(BOTTOM);
        cell.setPadding(0);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_3_0(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(9);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        cell.setPaddingTop(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
        cell.setPaddingLeft(4.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_3_1(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        //cell.setPadding(10.0f);
       // cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    
  //表格标题
    public static PdfPCell createHeadCell_4_1(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
  //生成表格1
    public static PdfPTable createTable1(int colNumber){
        int widths[] = { 18,10,20,80,18,60,30,40 };
        PdfPTable baseTable  = new PdfPTable(colNumber);
        baseTable.setWidthPercentage(100);
        baseTable.setSpacingBefore(10);
        
        try {
            baseTable.setWidths(widths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baseTable;
    }
  //生成表格2
    public static PdfPTable createTable2(int colNumber){
        int widths[] = { 7,12,2,12,30,25,25,25 };
        PdfPTable baseTable  = new PdfPTable(colNumber);
        baseTable.setWidthPercentage(100);
        baseTable.setSpacingBefore(2);
        try {
            baseTable.setWidths(widths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baseTable;
    }
  //生成表格3
    public static PdfPTable createTable3(int colNumber){
        int widths[] = { 20,10,22,20,10,22,20,10,22};
        PdfPTable baseTable  = new PdfPTable(colNumber);
        baseTable.setWidthPercentage(100);
        baseTable.setSpacingBefore(5);
        try {
            baseTable.setWidths(widths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baseTable;
    }
  //生成表格3
    public static PdfPTable createTable4(int colNumber){
        int widths[] = { 3,12,4,8,2,7};
        PdfPTable baseTable  = new PdfPTable(colNumber);
        baseTable.setWidthPercentage(100);
        baseTable.setSpacingBefore(30);
        try {
            baseTable.setWidths(widths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baseTable;
    }

  
}