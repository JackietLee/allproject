package net.pingfang.serviceImpl.qhdPdf.style;

import static com.itextpdf.text.Rectangle.BOTTOM;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class OverDamagePdfStyle {
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
    
 public static PdfPCell createImgCell(String imgUrl){
    	
    	//隐藏全部
    	//cell.disableBorderSide(15);//全没了
    	
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(15);
        cell.setHorizontalAlignment(15);
//        cell.setColspan(6);
//        cell.setRowspan(6);
      //  cell.setPhrase(new Phrase(value,headfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        cell.setPadding(10.0f);
        cell.setBorder(0);
//        cell.setPaddingTop(10.0f);
//        cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(200);
        
        /**/
        if(null !=imgUrl) {
        	Image image;
    		try {
    			image = Image.getInstance(imgUrl);
    			image.setAlignment(Image.ALIGN_CENTER);
    		    //image.setWidthPercentage(100);
    		    // image.setAbsolutePosition(1, 1);
    		    image.scaleToFit(280, 110);	//设置图片大小（宽与高）
    		    image.scalePercent(40);//依照比例缩放
    		    cell.addElement(image); 
    		} catch (BadElementException e) {
    			e.printStackTrace();
    		} catch (MalformedURLException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }else {
        	cell.setPhrase(new Phrase("没有找到残损图片",headfont));
        }
        
        return cell;
    }
    
    public static PdfPCell createHeadCell(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
       // cell.setColspan(3);
        cell.setPhrase(new Phrase(value,keyfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        cell.setBorder(0);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
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
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
//        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        //cell.setPadding(10.0f);
        cell.setBorder(BOTTOM);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    public static  PdfPCell createHeadCell_1_3(String value){
        PdfPCell cell = new PdfPCell();
      //  cell.setVerticalAlignment(10);
      //  cell.setHorizontalAlignment(10);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
//        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont));
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
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
//        cell.setColspan(2);
        cell.setPhrase(new Phrase(value,textfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        //cell.setPadding(10.0f);
       // cell.setBorder(BOTTOM);
        cell.setPadding(0);
       // cell.setPaddingBottom(10.0f);
        cell.setFixedHeight(15);
        return cell;
    }
    //生成表格
    public static PdfPTable createTable(int widths[],float percen){
        PdfPTable baseTable  = new PdfPTable(widths.length);
        baseTable.setWidthPercentage(100);
        baseTable.setSpacingBefore(percen);
        try {
            baseTable.setWidths(widths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baseTable;
    }

}
