package net.pingfang.controller.qhdPdf;

//import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.Result;
import net.pingfang.service.qhdPdf.DamagedRecordPdfService;
import net.pingfang.service.qhdPdf.OverDamageService;
import net.pingfang.service.qhdPdf.ReceiptTxtService;
import net.pingfang.service.qhdPdf.TallyRecordService;
import net.pingfang.service.qhdPdf.TallyResultsPdfService;
import net.pingfang.service.qhdPdf.TallyVoucherService;
import net.pingfang.utils.ResultUtil;

@Api("DownloadPdfTest Api")
@RestController
@RequestMapping("/qhdPdf")
public class exportPdfController {
	
	@Autowired
	private DamagedRecordPdfService damagedRecordPdfService;
	
	@Autowired
	private TallyResultsPdfService tallyResultsPdfService;
	@Autowired
	private TallyVoucherService tallyVoucherService;
	@Autowired
	private OverDamageService overDamageService;
	@Autowired
	private ReceiptTxtService receiptTxtService;
	@Autowired
	private TallyRecordService tallyRecordService;
	
	@Value("${pdf_url}")
	private String pdf_url;
	
	//点击下载获取企业信息并存至Pdf
	//@RequestMapping(value="/downloadPdf.do")
	//@ResponseBody
	
	@ApiOperation(value="导出残损记录PDF", notes="导出残损记录PDF")
	@GetMapping("/exportDamagedRecordPdf")
	//@RequiresPermissions(value = {"exportDamagedRecordPdf"})
	public Result<Object> exportDamagedRecordPdf(WorkRecordVo workRecordVo) throws Exception {
	//public Result<Object> exportDamagedRecordPdf(HttpServletRequest request, WorkRecordVo workRecordVo) throws Exception {
//	    TCorp tCorp = new TCorp();
//	    tCorp.setId(1);
//        tCorp.setRegNo("A20201203");

	    //String realPath = request.getSession().getServletContext().getRealPath("/") + "\\icon\\logo_64.png";
	 //  String realPath ="G:\\pdfTest\\qhd_demeged.PNG";
	   // PDFReport.settCorp(tCorp);
	   // new PDFReport("test.pdf").generatePDF();
	   // new PDFReport("test.pdf");
		//new PDFReport("G:\\pdfTest\\qhd_demeged.pdf");
		//damagedRecordPdfService.pdfReport("G:\\pdfTest\\qhd_demeged.pdf",workRecordVo);
		String url = damagedRecordPdfService.pdfReport(workRecordVo);
	    
	  //  System.out.println("C:\\Users\\Administrator\\Desktop\\"+tCorp.getCorpName()+".pdf");
	    //PDFUtil.addImage("test.pdf", "C:\\Users\\Administrator\\Desktop\\"+tCorp.getCorpName()+".pdf",realPath);

	 //   PDFUtil.addImage("test.pdf", "G:\\pdfTest\\"+tCorp.getCorpName()+".pdf",realPath);
	    
	    //return "提示：数据导出成功!";
	    return ResultUtil.success(this.formatUrl(url));
	}
	
	@ApiOperation(value="导出理货结果汇总证明PDF", notes="导出理货结果汇总证明PDF")
	@GetMapping("/exportTallyResultsPdf")
	//@RequiresPermissions(value = {"exportTallyResultsPdf"})
	public Result<Object> exportTallyResultsPdf(WorkRecordVo workRecordVo) throws Exception {
		String url = tallyResultsPdfService.pdfReport(workRecordVo);
	    return ResultUtil.success(this.formatUrl(url));
	}
	
	@ApiOperation(value="导出理货业务凭证PDF", notes="导出理货业务凭证PDF")
	@GetMapping("/exportTallyVoucherPdf")
	//@RequiresPermissions(value = {"exportTallyVoucherPdf"})
	public Result<Object> exportTallyVoucherPdf(WorkRecordVo workRecordVo) throws Exception {
		String url = tallyVoucherService.pdfReport(workRecordVo);
	    return ResultUtil.success(this.formatUrl(url));
	}

	@ApiOperation(value="导出溢短残损单PDF", notes="导出溢短残损单PDF")
	@GetMapping("/exportOverDamagePdf")
	//@RequiresPermissions(value = {"exportOverDamagePdf"})
	public Result<Object> exportOverDamagePdf(WorkRecordVo workRecordVo) throws Exception {
		String url = overDamageService.pdfReport(workRecordVo);
	    return ResultUtil.success(this.formatUrl(url));
	}
	
	@ApiOperation(value="导出秦皇岛交接单", notes="导出秦皇岛交接单")
	@GetMapping("/exportReceiptTxt")
	//@RequiresPermissions(value = {"exportReceiptTxt"})
	public Result<Object> exportReceiptTxt(WorkRecordVo workRecordVo) throws Exception {
		String url = receiptTxtService.receiptTxt(workRecordVo);
	    return ResultUtil.success(this.formatUrl(url));
	}
	@ApiOperation(value="QHD导出理货记录EXCEL", notes="QHD导出理货记录EXCEL")
	@GetMapping("/exportExcelWorkRecord")
	//@RequiresPermissions(value = {"exportExcelWorkRecord"})
	public Result<Object> exportExcelWorkRecord(WorkRecordVo workRecordVo) throws Exception {
		if(null !=workRecordVo.getVesselNumber() && !"".equals(workRecordVo.getVesselNumber())) {
			String url = tallyRecordService.exportExcelWorkRecord(workRecordVo);
		    return ResultUtil.success(this.formatUrl(url));
		}else {
			return ResultUtil.error(1, "船舶艘次号不能为空！");
		}
		
	}
	/**
	 * 格式化URL
	 * 把G:/pdfTest格式化成http://127.0.0.1:5001/pdfTest
	 * @param url
	 * @return
	 */
	private String formatUrl(String url) {
		if(null !=url && url.length() >0) {
			url = pdf_url+url.substring(url.indexOf(":")+1);
		}
		return url;
	}

}
