package net.pingfang.serviceImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import net.pingfang.dao.qhdpdf.PdfDao;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.qhdPdf.ReceiptTxtService;
import net.pingfang.serviceImpl.qhdPdf.style.PDFBuilder;
import net.pingfang.utils.DateUtil;

/**
 * 秦皇岛交接单
 * @author Administrator
 *
 */
@Service
public class ReceiptTxtServiceImpl implements ReceiptTxtService{
	
	@Value("${pdf_file_path}")
	private String pdf_file_path;

	@Autowired
	private PdfDao pdfDao;
	@Override
	public String receiptTxt(WorkRecordVo workRecordVo) {
		String strUrl = null;
		BufferedOutputStream buff = null;
		FileOutputStream outputStream = null;
        try {
        	//strUrl = pdf_file_path+File.separator+"交接单_"+DateUtil.getDate("yyyyMMddHHmmss")+".txt";
        	strUrl = pdf_file_path+"/交接单_"+DateUtil.getDate("yyyyMMddHHmmss")+".txt";
            File file = new File(strUrl);
            outputStream = new FileOutputStream(file);
            buff = new BufferedOutputStream(outputStream);
            StringBuffer write = new StringBuffer();
            String name = "仁建2020";
            String hc = "C2021";
            String sTime = "2020年11月28日";
            String eTime = "2020年11月28日";
            write.append("船名："+name).append("\t航次："+hc+"\n");
            write.append("开工时间："+sTime).append("\t完工时间："+eTime+"\n");
            write.append("以下内容请交接人员核对一致后签字\t理货组长：\t\t新港湾调度签名：\n\n");
            buff.write(write.toString().getBytes("UTF-8"));
    		write = new StringBuffer();        
    		
            List<WorkRecordVo> workList = pdfDao.getReceiptTxt(workRecordVo);
            int size = workList.size();
            if(null !=workList && size>0) {
                int i=0;
                int j=0;
                int count = 0;
                for(WorkRecordVo work : workList) {
                	write.append(work.getUpdateContaid()).append("\t");
                	i++;
                	j++;
                	count ++;
                	if(13 == i) {
                		write.append("\n");
                		i=0;
                	}
                	if(50 == j) {
                		if(count == size) {
                			write.append("\n\t\t本航次集装箱数总计："+count+"箱");
                		}else {
                			write.append("\t小计："+count+"箱");
                    		write.append("\n");
                    		i=0;
                    		j=0;
                		}
                		buff.write(write.toString().getBytes("UTF-8"));
                		write = new StringBuffer();                 		
                	}
                }
                String str = write.toString();
                if(null !=str && str.length() >0) {
                	write.append("\n\t本航次集装箱数总计："+count+"箱");
                	buff.write(write.toString().getBytes("UTF-8"));
                }
            }
            outputStream.flush();
            buff.flush();       
        }catch (IOException ie) {
        	ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {       
            try {
            	if(null !=buff) {
            		buff.close();
            	}
            	if(null !=outputStream) {
            		outputStream.close();
            	}  
            } catch (Exception e) {       
                e.printStackTrace();       
           }       
       }
		return strUrl;
	}
	
}
