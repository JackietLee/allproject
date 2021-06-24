package net.pingfang.serviceImpl.qhdPdf.style;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import static com.itextpdf.text.Rectangle.BOTTOM;

public class TallyVoucherPdfStyle {
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
    
    
    public static PdfPCell createHeadCell_1_1(String value){
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
    public static  PdfPCell createHeadCell_1_2(String value){
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
  
  //表格表头样式1
    public static  PdfPCell createHeadCell_1_3(String value){
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
    public static  PdfPCell createHeadCell_1_4(String value){
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
    public static PdfPCell createHeadCell_1_5(String value){
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
    public static  PdfPCell createHeadCell_1_6(String value){
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
    public static PdfPCell createHeadCell_1_7(String value){
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
    public static PdfPCell createHeadCell_1_8(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(8);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
       // cell.setPadding(0);
        cell.setPaddingLeft(22.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    
    public static PdfPCell createHeadCell_2_1(String value){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
       // cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_2_2(String value){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
       // cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_2_3(String value,int rows){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(rows);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
       // cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_2_4(String value){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
       // cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    
    public static  PdfPCell createHeadCell_3_1(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
//        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont2));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(BOTTOM);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static PdfPCell createHeadCell_3_2(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(5);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(0);
        cell.setPadding(0);
        cell.setPaddingTop(2);
        cell.setPaddingLeft(12.0f);
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
        int widths[] = {5,20,5,10,8,15,16,30 };
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
    
  //生成表格1
    public static PdfPTable createTable3(int colNumber){
        int widths[] = {8,10,20,10,10};
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
}