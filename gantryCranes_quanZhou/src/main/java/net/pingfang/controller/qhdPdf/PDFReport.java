package net.pingfang.controller.qhdPdf;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

public class PDFReport {
    private static TCorp tCorp;

    Document document = new Document();// 建立一个Document对象

    public PDFReport(String out) {
        try {
            File file = new File(out);
            file.createNewFile();
            Rectangle pageSize = new Rectangle(PageSize.A4);
            document.setPageSize(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            PDFBuilder builder = new PDFBuilder();
            writer.setPageEvent(builder);
            document.open();
           
            /**/
            Image image = Image.getInstance("G:\\pdfTest\\qhd_demeged.PNG");
            image.setAlignment(Image.ALIGN_CENTER);
            //image.setWidthPercentage(100);
           // image.setAbsolutePosition(1, 1);
            image.scaleToFit(280, 110);	//设置图片大小（宽与高）
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
            document.add(generatePDF1());
            document.add(generatePDF2());
            document.add(generatePDF3());
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void settCorp(TCorp tCorp) {
        PDFReport.tCorp = tCorp;
    }

    public PdfPTable generatePDF1() {
    	//设置单元格为3列
        PdfPTable table = PDFUtil.createTable1(3);
        table.addCell(PDFUtil.createTitleCell_0("船名/航次"));
        table.addCell(PDFUtil.createTitleCell_0("时间:"));
        table.addCell(PDFUtil.createTitleCell_0("编号:ONLY"));
        
        table.addCell(PDFUtil.createTitleCell_0("VESSEL NAME/VOYAGE. XIN YU JIN XIANG/2046E刘彦钊"));
        table.addCell(PDFUtil.createTitleCell_0("TIME:2020-12-28"));
        table.addCell(PDFUtil.createTitleCell_0("NO."));
        return table;
    }

    public PdfPTable generatePDF2() {
        //设置单元格为6列
        PdfPTable table = PDFUtil.createTable(6);

        table.addCell(PDFUtil.createTitleCell_1("箱号CONTAINER NO"));
        table.addCell(PDFUtil.createTitleCell_2("铅封SEAL"));
        table.addCell(PDFUtil.createTitleCell_2("重/空箱FULL/EMPTY"));
        table.addCell(PDFUtil.createTitleCell_2("尺寸/类型SIZE/TYPE"));
        
        table.addCell(PDFUtil.createTitleCell_1("BRCU2181880"));
        table.addCell(PDFUtil.createTitleCell_2("QIN009028"));
        table.addCell(PDFUtil.createTitleCell_2("F"));
        table.addCell(PDFUtil.createTitleCell_2("20"));
        
        table.addCell(PDFUtil.createHeadCell("残损图片"));
        
        table.addCell(PDFUtil.createTitleCell_2("编号"));
        table.addCell(PDFUtil.createTitleCell_2("残损类型"));
        table.addCell(PDFUtil.createTitleCell_2("残损部位"));
        table.addCell(PDFUtil.createTitleCell_3("残损描述"));
        
        table.addCell(PDFUtil.createTitleCell_2("01"));
        table.addCell(PDFUtil.createTitleCell_2("HO"));
        table.addCell(PDFUtil.createTitleCell_2("T"));
        table.addCell(PDFUtil.createTitleCell_3("A HOLE OF 20 COM"));
        
        table.addCell(PDFUtil.createTitleCell_2("02"));
        table.addCell(PDFUtil.createTitleCell_2("HO222"));
        table.addCell(PDFUtil.createTitleCell_2("T"));
        table.addCell(PDFUtil.createTitleCell_3("变形"));
        
        table.addCell(PDFUtil.createTitleCell_2("03"));
        table.addCell(PDFUtil.createTitleCell_2(""));
        table.addCell(PDFUtil.createTitleCell_2(""));
        table.addCell(PDFUtil.createTitleCell_3(""));
        
        table.addCell(PDFUtil.createTitleCell_2("04"));
        table.addCell(PDFUtil.createTitleCell_2(""));
        table.addCell(PDFUtil.createTitleCell_2(""));
        table.addCell(PDFUtil.createTitleCell_3(""));
        
        table.addCell(PDFUtil.createRemakCell("备注（REMAK）:"));
        
        return table;
    }
    
    public PdfPTable generatePDF3() {
    	//设置单元格为3列
        PdfPTable table = PDFUtil.createTable1(3);
        table.addCell(PDFUtil.createTitleCell_0("理货员"));
        table.addCell(PDFUtil.createTitleCell_0("船方"));
        table.addCell(PDFUtil.createTitleCell_0("码头"));
        
        table.addCell(PDFUtil.createTitleCell_0("TALLYMAN:"));
        table.addCell(PDFUtil.createTitleCell_0("VESSEL:"));
        table.addCell(PDFUtil.createTitleCell_0("TERMINAL:"));
        return table;
    }
}
