package net.pingfang.serviceImpl.qhdPdf;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.qhdPdf.OverDamageService;
import net.pingfang.serviceImpl.qhdPdf.style.OverDamagePdfStyle;
import net.pingfang.serviceImpl.qhdPdf.style.PDFBuilder;
import net.pingfang.utils.DateUtil;

/**
 * 溢短残损单
 * @author Administrator
 *
 */
@Service
public class OverDamageServiceImpl implements OverDamageService{

	@Value("${pdf_img_path}")
	private String pdf_img_path;
	@Value("${pdf_file_path}")
	private String pdf_file_path;
	
	@Override
	public String pdfReport(WorkRecordVo workRecordVo) {
		String strUrl = null;
        try {
        	Document document = new Document();// 建立一个Document对象
        	//strUrl = pdf_file_path+File.separator+"溢短残损单_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	strUrl = pdf_file_path+"/溢短残损单_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
            File file = new File(strUrl);
            file.createNewFile();
            Rectangle pageSize = new Rectangle(PageSize.A4);
            document.setPageSize(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            PDFBuilder builder = new PDFBuilder();
            writer.setPageEvent(builder);
            document.open();
           
            /**/
            Image image = Image.getInstance(pdf_img_path+File.separator+"qhd_overDemeged.png");
            image.setAlignment(Image.ALIGN_CENTER);
            //image.setWidthPercentage(150);
           // image.setAbsolutePosition(1, 1);
            image.scaleToFit(280, 110);	//设置图片大小（宽与高）
            image.scalePercent(40);//依照比例缩放
            //image.scaleToFit(160, 70);
            document.add(image);

            document.add(generatePDF());
            document.add(generatePDF1());  
            document.add(generatePDF2());  
            document.add(generatePDF3()); 
            document.add(generatePDF4()); 
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return strUrl;
	}
	private PdfPTable generatePDF() {
		int widths[] = { 30};
		//设置单元格为8列
        PdfPTable table = OverDamagePdfStyle.createTable(widths,8);
       // table.addCell(OverDamagePdfUtil.createImgCell(pdf_img_path+File.separator+"qhd_overDemeged.png"));
        table.addCell(OverDamagePdfStyle.createHeadCell("集装箱溢短残损单"));
        return table;
	}
	private PdfPTable generatePDF1() {
		int widths[] = { 10,15,6,15,6,15,6,15};
		//设置单元格为8列
        PdfPTable table = OverDamagePdfStyle.createTable(widths,10);
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("船        名："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("仁建钦州"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("航次："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("2013"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("国籍："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("中国"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("泊位："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("NX.W.NO.11"));
        return table;
	}
	
	private PdfPTable generatePDF2() {
		int widths[] = { 10,8,2,3,2,3,2,15,8,8,2,3,2,3,15};
		//设置单元格为15列
        PdfPTable table = OverDamagePdfStyle.createTable(widths,10);
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("开工日期："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("2021"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("年"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("1"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("月"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("5"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("日"));
        
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1(""));
        
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("制单日期："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("2021"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("年"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("1"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("月"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("5"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_3("日"));
        return table;
	}
	
	private PdfPTable generatePDF3() {
		int widths[] = { 10,8,3,3,30};
		//设置单元格为5列
        PdfPTable table = OverDamagePdfStyle.createTable(widths,10);
        table.addCell(OverDamagePdfStyle.createHeadCell_1_4("箱号"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_4("铅封号"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_4("尺寸"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_4("空重"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_4("残损情况说明"));
        for(int i=0; i<10; i++) {
        	table.addCell(OverDamagePdfStyle.createHeadCell_1_4(""));
            table.addCell(OverDamagePdfStyle.createHeadCell_1_4(""));
            table.addCell(OverDamagePdfStyle.createHeadCell_1_4(""));
            table.addCell(OverDamagePdfStyle.createHeadCell_1_4(""));
            table.addCell(OverDamagePdfStyle.createHeadCell_1_4(""));
        }
        return table;
	}
	
	private PdfPTable generatePDF4() {
		int widths[] = { 12,15,20,12,15,10};
		//设置单元格为5列
        PdfPTable table = OverDamagePdfStyle.createTable(widths,40);
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("理货组长："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("张飞"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1(""));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1("船长/大副："));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_2("张飞"));
        table.addCell(OverDamagePdfStyle.createHeadCell_1_1(""));
        return table;
	}
}
