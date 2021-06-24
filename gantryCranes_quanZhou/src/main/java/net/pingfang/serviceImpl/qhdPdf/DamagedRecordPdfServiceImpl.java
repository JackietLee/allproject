package net.pingfang.serviceImpl.qhdPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.pingfang.dao.qhdpdf.PdfDao;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.entity.work.DamageInforRecordVo;
import net.pingfang.entity.work.ImgInfoVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.qhdPdf.DamagedRecordPdfService;
//import net.pingfang.controller.qhdPdf.TCorp;
import net.pingfang.serviceImpl.qhdPdf.style.PDFBuilder;
import net.pingfang.serviceImpl.qhdPdf.style.DamagedPdfStyle;
import net.pingfang.utils.DateUtil;
/**
 * 残损记录导出PDF
 * @author Administrator
 *
 */
@Service
public class DamagedRecordPdfServiceImpl implements DamagedRecordPdfService{

	@Autowired
    private PdfDao pdfDao;
	@Autowired
	private WL_BerthPlanService wl_berthPlanService;
	@Value("${pdf_img_path}")
	private String pdf_img_path;
	@Value("${pdf_file_path}")
	private String pdf_file_path;
	
	
    @Override
    public String pdfReport(WorkRecordVo workRecordVo) {
    	String strUrl = null;
        try {
        	Document document = new Document();// 建立一个Document对象
//        	strUrl = pdf_file_path+File.separator+"残损记录_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	strUrl = pdf_file_path+"/残损记录_"+DateUtil.getDate("yyyyMMddHHmmss")+".pdf";
        	File file = new File(strUrl);
            file.createNewFile();
            Rectangle pageSize = new Rectangle(PageSize.A4);
            document.setPageSize(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            PDFBuilder builder = new PDFBuilder();
            writer.setPageEvent(builder);
            document.open();
           
            /**/
            Image image = Image.getInstance(pdf_img_path+File.separator+"qhd_demeged.PNG");
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
            
            //获取数据
            List<DamageInforRecordVo> list = pdfDao.getQhdDamageInforRecordList(workRecordVo);
            if(null !=list && list.size() >0) {
            	List<String> seqNoList = new ArrayList<String>();
            	List<ImgInfoVo> imgList = new ArrayList<ImgInfoVo>();
            	int count=0;
            	for(DamageInforRecordVo damage : list) {
            		seqNoList.add(damage.getSeqNo());
            		count++;
            		if(100 == count) {
            			imgList.addAll(pdfDao.getQhdImgInfoVo(seqNoList));
            			seqNoList.clear();
            			count = 0;
            		}
            	}
            	 if(seqNoList.size() >0) {
            		 imgList.addAll(pdfDao.getQhdImgInfoVo(seqNoList));
            	 }
            	 //把图片合并到残损记录
            	 for(DamageInforRecordVo damage : list) {
            		 for(int i=0; i<imgList.size();i++) {
            			 if(damage.getSeqNo().equals(imgList.get(i).getSeqNo())) {
            				 if(null !=imgList.get(i).getImgPathName()) {
            					 String[] imgName = imgList.get(i).getImgPathName().split(",");
            					 damage.setImgUrl(imgName[0]);
            				 }
            				 break;            				 
            			 }
            		 }
            	 }
            	document.add(generatePDF1(list.get(0).getVesselNumber()));
            	for(DamageInforRecordVo damage : list) {
            		document.add(generatePDF2(damage));
                    document.add(generatePDF3());
            	}
            }            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strUrl;
    }

    public PdfPTable generatePDF1(String vesselNumber) {
    	//设置单元格为3列
        PdfPTable table = DamagedPdfStyle.createTable1(3);
        table.addCell(DamagedPdfStyle.createHeadCell("进口/出口"));
        table.addCell(DamagedPdfStyle.createHeadCell("INWARD/OUTWARD"));
        
    	List<String> vesselNumberList = new ArrayList<String>();
		vesselNumberList.add(vesselNumber);
		//获取alongside,in_voyage, out_voyage
		List<BerthPlanInfoVo> bpList = wl_berthPlanService.getBerthPlanInfoVoListByVesselNumber(vesselNumberList);
		if(null !=bpList && bpList.size()>0) {
			BerthPlanInfoVo bp = bpList.get(0);
			
	        table.addCell(DamagedPdfStyle.createTitleCell_0("船名/航次"));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("时间:"));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("编号:ONLY"));
	        
	        table.addCell(DamagedPdfStyle.createTitleCell_0("VESSEL NAME/VOYAGE "+bp.getVesselNameCn()+"/"+bp.getInVoyage()));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("TIME:"+bp.getFstartTime()));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("NO."));
		}else {
	        table.addCell(DamagedPdfStyle.createTitleCell_0("船名/航次"));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("时间:"));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("编号:ONLY"));
	        
	        table.addCell(DamagedPdfStyle.createTitleCell_0(""));
	        table.addCell(DamagedPdfStyle.createTitleCell_0(""));
	        table.addCell(DamagedPdfStyle.createTitleCell_0("NO."));
		}
        return table;
    }

    public PdfPTable generatePDF2(DamageInforRecordVo damage) {
        //设置单元格为6列
        PdfPTable table = DamagedPdfStyle.createTable(6);

        table.addCell(DamagedPdfStyle.createTitleCell_1("箱号CONTAINER NO"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("铅封SEAL"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("重/空箱FULL/EMPTY"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("尺寸/类型SIZE/TYPE"));
        
        table.addCell(DamagedPdfStyle.createTitleCell_1(damage.getContainerNumber()));
        //是否有铅封(0:LOCK,1:UNLOCK,2:unknow)
        if(0 == damage.getDoorLock()) {
        	table.addCell(DamagedPdfStyle.createTitleCell_2("LOCK"));
        }else {
        	table.addCell(DamagedPdfStyle.createTitleCell_2("UNLOCK"));
        }
        
        table.addCell(DamagedPdfStyle.createTitleCell_2(damage.getStuffingStatus()));
        table.addCell(DamagedPdfStyle.createTitleCell_2(damage.getIso()));
        
       // String imgUrl = "G:\\pdfTest\\qhd_demeged.PNG";
        table.addCell(DamagedPdfStyle.createImgCell(damage.getImgUrl()));
        
        
        table.addCell(DamagedPdfStyle.createTitleCell_2("编号"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("残损类型"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("残损部位"));
        table.addCell(DamagedPdfStyle.createTitleCell_3("残损描述"));
        
        table.addCell(DamagedPdfStyle.createTitleCell_2("01"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("HO"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("T"));
        table.addCell(DamagedPdfStyle.createTitleCell_3("A HOLE OF 20 COM"));
        
        table.addCell(DamagedPdfStyle.createTitleCell_2("02"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("HO222"));
        table.addCell(DamagedPdfStyle.createTitleCell_2("T"));
        table.addCell(DamagedPdfStyle.createTitleCell_3("变形"));
        
        table.addCell(DamagedPdfStyle.createTitleCell_2("03"));
        table.addCell(DamagedPdfStyle.createTitleCell_2(""));
        table.addCell(DamagedPdfStyle.createTitleCell_2(""));
        table.addCell(DamagedPdfStyle.createTitleCell_3(""));
        
        table.addCell(DamagedPdfStyle.createTitleCell_2("04"));
        table.addCell(DamagedPdfStyle.createTitleCell_2(""));
        table.addCell(DamagedPdfStyle.createTitleCell_2(""));
        table.addCell(DamagedPdfStyle.createTitleCell_3(""));
        
        table.addCell(DamagedPdfStyle.createRemakCell("备注（REMAK）:"));
        
        return table;
    }
    
    public PdfPTable generatePDF3() {
    	//设置单元格为3列
        PdfPTable table = DamagedPdfStyle.createTable1(3);
        table.addCell(DamagedPdfStyle.createTitleCell_0("理货员"));
        table.addCell(DamagedPdfStyle.createTitleCell_0("船方"));
        table.addCell(DamagedPdfStyle.createTitleCell_0("码头"));
        
        table.addCell(DamagedPdfStyle.createTitleCell_0("TALLYMAN:"));
        table.addCell(DamagedPdfStyle.createTitleCell_0("VESSEL:"));
        table.addCell(DamagedPdfStyle.createTitleCell_0("TERMINAL:"));
        return table;
    }


}
