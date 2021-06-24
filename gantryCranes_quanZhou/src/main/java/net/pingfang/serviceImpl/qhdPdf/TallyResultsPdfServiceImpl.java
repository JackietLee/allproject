package net.pingfang.serviceImpl.qhdPdf;

import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.qhdPdf.TallyResultsPdfService;
import net.pingfang.serviceImpl.qhdPdf.style.PDFBuilder;
import net.pingfang.serviceImpl.qhdPdf.style.TallyResultsPdfStyle;
import net.pingfang.utils.DateUtil;

/**
 * 理货结果汇总证明（秦皇岛）导出PDF
 * @author Administrator
 *
 */
@Service
public class TallyResultsPdfServiceImpl implements TallyResultsPdfService{
	
	@Value("${pdf_img_path}")
	private String pdf_img_path;
	@Value("${pdf_file_path}")
	private String pdf_file_path;
	
	@Override
    public String pdfReport(WorkRecordVo workRecordVo) {
		String strUrl = null;
        try {
        	Document document = new Document();// 建立一个Document对象
//        	strUrl = pdf_file_path+File.separator+"理货结果汇总证明_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	strUrl = pdf_file_path+"/理货结果汇总证明_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	File file = new File(strUrl);
            file.createNewFile();
            Rectangle pageSize = new Rectangle(PageSize.A4);
            document.setPageSize(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            PDFBuilder builder = new PDFBuilder();
            writer.setPageEvent(builder);
            document.open();
           
            /**/
            Image image = Image.getInstance(pdf_img_path+File.separator+"qhd_tallyResults.png");
            image.setAlignment(Image.ALIGN_CENTER);
            //image.setWidthPercentage(100);
           // image.setAbsolutePosition(1, 1);
            image.scaleToFit(280, 110);	//设置图片大小（宽与高）
            image.scalePercent(40);//依照比例缩放
            //image.scaleToFit(160, 70);
            document.add(image);
         //   document.addTitle("Title 测试");
            /*
            Paragraph pHeader = new Paragraph();
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            pHeader.add(new Paragraph("字体编辑测试",new Font(bfChinese, 8.0F, 1)));
            pHeader.add(new Paragraph("你要生成文字写这里", new Font(bfChinese, 8.0F, 1)));
            
            pHeader.setIndentationLeft(12);// 左缩进  

            pHeader.setIndentationRight(12);// 右缩进  

            pHeader.setFirstLineIndent(24);// 首行缩进  
            
            document.add(pHeader);
            document.add(new Paragraph("你要生成文字写这里Paragraph", new Font(bfChinese, 8.0F, 1)));
            */
           // PdfPTable table = generatePDF();
          //  document.add(generatePDF2(damage));
          //  document.add(generatePDF3());
           
//            Paragraph pHeader = new Paragraph();
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
//            pHeader.add(new Paragraph("理货结果汇总证明",new Font(bfChinese, 8.0F, 1)));
//            pHeader.setAlignment(Element.ALIGN_CENTER);
//            document.add(pHeader);   
//            
//            
//            BaseFont  bfChinese2=BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//jar包
//            Font topfont =new Font(bfChinese2,14);

            /**
             * 10 为字体大小
             * Font.BOLD 字体加粗
             * Font.UNDERLINE 字体加下划线
             */
//            Font textfont =new Font(bfChinese2,10,Font.BOLD|Font.UNDERLINE);
//            
//            document.add(new Paragraph("理货AAAA",topfont));  
//            document.add(new Paragraph("理货BBBB",textfont));  
//            
            
            
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
	
	private PdfPTable generatePDF1() {
		//设置单元格为3列
        PdfPTable table = TallyResultsPdfStyle.createTable1(8);
       // table.addCell(TallyPdfUtil.createHeadCell("船名："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_4("船名："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_1("伊坎帕丹"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_4("航次："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1("7140X"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_4("国籍："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1("巴拿马"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_2("开工日期："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_3("2020年06月21日"));
        table.addCell(TallyResultsPdfStyle.createHeadCell(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_4("制单日期："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1("2020年06月21日"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_2("货物名称："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_5("风力发电机叶片和风力发电机叶片用支架"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_6("本公司对上述船舶在装卸货物、集装箱过程中进行了公正理货，确认了货物、集装箱的数字及完好状况，"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_6("现将理货结果汇总如下："));
        return table;
	}

	private PdfPTable generatePDF2() {
		//设置单元格为3列
        PdfPTable table = TallyResultsPdfStyle.createTable2(8);
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_7("一、装货单上列明的汇总数字"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_4("货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_8(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_9("/"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_8(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell("件/吨，集装箱"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X20"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X40"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X45"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_7("二、理货结果汇总数字"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_4("货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_8(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_9("/"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_8(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell("件/吨，集装箱"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X20"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X40"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X45"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_7("三、理货结果分类汇总数字"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_10("1、溢   卸：货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_11(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell("件/吨，集装箱"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X20"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X40"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X45"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_10("2、短   卸：货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_11(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell("件/吨，集装箱"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X20"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X40"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X45"));
        
      //  table.addCell(TallyPdfUtil.createHeadCell_1_10("3、残  损：货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_10("3、残   损：货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_11(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell("件/吨，集装箱"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X20"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X40"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X45"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_10("4、危   险：货物"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_11(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell("件/吨，集装箱"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X20"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X40"));
        table.addCell(TallyResultsPdfStyle.createHeadCell("X45"));
        
        return table;
	}
	
	private PdfPTable generatePDF3() {
		//设置单元格为9列
        PdfPTable table = TallyResultsPdfStyle.createTable3(9);
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_0("四、理货有关情况说明："));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("S/O"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("件数"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("吨数"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("S/O"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("件数"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("吨数"));
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("S/O"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("件数"));
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_1("吨数"));
        
        for(int i=0;i<11;i++) {
        	table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
            table.addCell(TallyResultsPdfStyle.createHeadCell_3_1(""));
        }
        
        table.addCell(TallyResultsPdfStyle.createHeadCell_3_0("备注："));
        return table;
	}
	private PdfPTable generatePDF4() {
		//设置单元格为9列
        PdfPTable table = TallyResultsPdfStyle.createTable4(6);
        table.addCell(TallyResultsPdfStyle.createHeadCell_4_1("理货长："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_8(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell_4_1("业务主管："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_1_8(""));
        table.addCell(TallyResultsPdfStyle.createHeadCell_4_1("公章："));
        table.addCell(TallyResultsPdfStyle.createHeadCell_4_1(" "));
        return table;
	}
}
