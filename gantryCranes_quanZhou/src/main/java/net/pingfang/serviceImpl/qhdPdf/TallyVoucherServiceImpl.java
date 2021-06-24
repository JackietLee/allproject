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
import net.pingfang.service.qhdPdf.TallyVoucherService;
import net.pingfang.serviceImpl.qhdPdf.style.PDFBuilder;
import net.pingfang.serviceImpl.qhdPdf.style.TallyVoucherPdfStyle;
import net.pingfang.utils.DateUtil;

/**
 * 理货业务凭证
 * @author Administrator
 *
 */
@Service
public class TallyVoucherServiceImpl implements TallyVoucherService{

	@Value("${pdf_img_path}")
	private String pdf_img_path;
	@Value("${pdf_file_path}")
	private String pdf_file_path;
	
	@Override
	public String pdfReport(WorkRecordVo workRecordVo) {
		String strUrl = null;
        try {
        	Document document = new Document();// 建立一个Document对象
        	//strUrl = pdf_file_path+File.separator+"理货业务凭证_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	strUrl = pdf_file_path+"/理货业务凭证_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	File file = new File(strUrl);
            file.createNewFile();
            Rectangle pageSize = new Rectangle(PageSize.A4);
            document.setPageSize(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            PDFBuilder builder = new PDFBuilder();
            writer.setPageEvent(builder);
            document.open();
           
            /**/
            Image image = Image.getInstance(pdf_img_path+File.separator+"qhd_liHuoPingZheng.png");
            image.setAlignment(Image.ALIGN_CENTER);
            //image.setWidthPercentage(100);
           // image.setAbsolutePosition(1, 1);
            image.scaleToFit(280, 110);	//设置图片大小（宽与高）
            image.scalePercent(40);//依照比例缩放
            //image.scaleToFit(160, 70);
            document.add(image);

            document.add(generatePDF1());  
            document.add(generatePDF2());  
            document.add(generatePDF3()); 
//            document.add(generatePDF4()); 
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strUrl;
	}
	
	private PdfPTable generatePDF1() {
		//设置单元格为3列
        PdfPTable table = TallyVoucherPdfStyle.createTable1(8);
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1("船名："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_2("伊坎帕丹"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1("航次："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_3("7140X"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1("国籍："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_4("巴拿马"));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_5("开工日期："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_6("2020年06月21日"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_3(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_3(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1("制单日期："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_4("2020年06月21日"));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_8("在货物/集装箱交接过程中，本公司为贵方办理完成了下列理货业务，请签本业务凭证，据以按照主管部门颁布"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_7("的收费规定和标准结算费用。"));
        return table;
	}
	private PdfPTable generatePDF2() {
		//设置单元格为3列
        PdfPTable table = TallyVoucherPdfStyle.createTable2(8);
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("编号"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_2("理化项目"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("数量"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("单位"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("节假日"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("夜班"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("备注"));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("1"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_2("件货"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_3("2",8));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_3("集装箱（重箱）",3));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("20`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("488"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("CONT"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("40`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("254"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("CONT"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("45`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("CONT"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_3("集装箱（重箱）",3));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("20`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("59"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("CONT"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("40`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("45`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_3("",2));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("20`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("40`"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("3"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4("散货单证手续业务"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("4"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4("分标志"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("5"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4("非一般货舱"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("6"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4("待时"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("7"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4("翻捣（重箱）"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("8"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4("翻捣（轻箱）"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("9"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1("10"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_4(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_2_1(""));
        return table;
	}
	
	private PdfPTable generatePDF3() {
		//设置单元格为3列
        PdfPTable table = TallyVoucherPdfStyle.createTable3(5);
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1("理货组长："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_3_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1(""));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_1_1("船长/大副/委托方："));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_3_1(""));
        
//        table.addCell(TallyVoucherPdfUtil.createHeadCell_1_8("在货物/集装箱交接过程中，本公司为贵方办理完成了下列理货业务，请签本业务凭证，据以按照主管部门颁布"));
        table.addCell(TallyVoucherPdfStyle.createHeadCell_3_2("CT-FORM-08"));
        return table;
	}
}
