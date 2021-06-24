package net.pingfang.serviceImpl.vessel;

//import java.io.OutputStream;
//import java.util.List;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.vessel.BerthPlanDao;
//import net.pingfang.entity.page.PageVo;
import net.pingfang.entity.vessel.BerthPlanInfoVo;
import net.pingfang.service.ocr.WL_BerthPlanService;
import net.pingfang.service.vessel.BerthPlanService;

/**
 * 泊位计划信息ServiceImpl
 * @author Administrator
 * @since 2019-06-3
 *
 */
@Service
public class BerthPlanServiceImpl implements BerthPlanService {
	@Autowired
	private BerthPlanDao berthPlanDao;
	
	@Autowired
	private WL_BerthPlanService wl_berthPlanService;

	/**
	 * 分页获取所有泊位计划信息
	 * Map
	 * @param 当前页 currentPage
	 * @param 每页多少数据 pageSize
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@Override
//	public PageVo<BerthPlanInfoVo> getPageBerthPlanInfoList(BerthPlanInfoVo berthPlanInfo) {
//		/*
//		PageVo<BerthPlanInfoVo> pageVo = new PageVo<BerthPlanInfoVo>();
//		int totalCount = this.getCountBerthPlanInfo(berthPlanInfo);
//		if(totalCount >0) {			
//			pageVo.initPage(berthPlanInfo.getCurrentPage(), berthPlanInfo.getPageSize(), totalCount);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("pageVo", pageVo);
//			map.put("berthPlanInfo", berthPlanInfo);
//			
//			pageVo.setList(berthPlanDao.getPageBerthPlanInfoList(map));
//		}
//		return pageVo;
//		*/
//		return wl_berthPlanService.getPageBerthPlanInfoList(berthPlanInfo);
//	}

	/**
	 * 获取所有泊位计划信息记录总数
	 * @return
	 */
	/*@Override
	public int getCountBerthPlanInfo(BerthPlanInfoVo berthPlanInfo) {
		return berthPlanDao.getCountBerthPlanInfo(berthPlanInfo);
	}*/
	
	/**
	 * 插入一条船泊信息
	 * @param berthPlanInfo
	 * @return
	 */
	@Override
	public int insertBerthPlan(BerthPlanInfoVo berthPlanInfo){
		return berthPlanDao.insertBerthPlan(berthPlanInfo);
	}
	/**
	 * 根据ID获取一条泊位计划信息
	 * @param id
	 * @return
	 */
//	@Override
//	public BerthPlanInfoVo getBerthPlanInfoById(String vesselVoyageNumber) {
//		return wl_berthPlanService.getBerthPlanInfoById(vesselVoyageNumber);
//	}

	/**
	 * 更新船舷方向
	 * @param berthPlanInfo
	 * @return
	 */
	@Transactional
	@Override
	public int updateAlongside(BerthPlanInfoVo berthPlanInfo) {
		int count = 0;
		String alongside = berthPlanInfo.getAlongside();
		String vesselNumber = berthPlanInfo.getVesselNumber();
		if(null != alongside && null != vesselNumber) {
			BerthPlanInfoVo newBerthPlanInfo = wl_berthPlanService.getBerthPlanInfoByVesselNumber(berthPlanInfo.getVesselNumber());
			//如果前端提交的船舷方向和数据库里的一样则不更新
			if(alongside.equals(newBerthPlanInfo.getAlongside())) {
				count = 1;
			}else {
				count = wl_berthPlanService.updateAlongside(berthPlanInfo);
			}
		}
		return count;		
	}
	/**
	 * 获取船下拉列表框
	 * 下拉联想框
	 * @param 查询条件 BerthPlanInfoVo
	 * @return
	 */
//	@Override
//	public List<BerthPlanInfoVo> getBerthPlanListBox(BerthPlanInfoVo berthPlanInfoVo){
//		return wl_berthPlanService.getBerthPlanListBox(berthPlanInfoVo);
//	}
	/**
	 * Excel导出所有泊位计划信息
	 * @param 查询条件 berthPlanInfoVo
	 * @return
	 */
//	@Override
//	public void exportExcelBerthPlanInfo(HttpServletResponse response, BerthPlanInfoVo berthPlanInfoVo){
//		//excel title
//		String[] excelTitle = BerthPlanServiceImpl.getExcelTitle();
//		//从数据库里获取导出数据
//		List<BerthPlanInfoVo> berthPlanInfoList = wl_berthPlanService.exportBerthPlanInfo(berthPlanInfoVo);
//		// 第一步，创建一个webbook，对应一个Excel文件
//		HSSFWorkbook wb = new HSSFWorkbook();
//		this.createSheet(wb, berthPlanInfoList, excelTitle);
//		// 响应到客户端
//		try{
//			OutputStream os = response.getOutputStream();
//			wb.write(os);
//			os.flush();
//			os.close();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	 /**
     * @功能：手工构建一个简单格式的Excel Sheet面签
     */
//    private void createSheet(HSSFWorkbook wb, List<BerthPlanInfoVo> berthPlanList, String[] strArray) {
//        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//        HSSFSheet sheet = wb.createSheet("泊位计划");
//        sheet.setDefaultColumnWidth(20);// 默认列宽
//        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//        HSSFRow row = sheet.createRow(0);
//        row.setHeightInPoints(20);
//        // 第四步，创建单元格，并设置值表头 设置表头居中
//        HSSFCellStyle cellStyle = wb.createCellStyle();
//        // 创建一个居中格式
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//        
//        //设置边框样式
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//
//        HSSFFont fontStyle = wb.createFont();
//        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//        cellStyle.setFont(fontStyle);
//
//
//        //字段样式（垂直居中）
//        HSSFCellStyle cellStyle2 = wb.createCellStyle();
//        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//
//        //设置边框样式
//        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//        
//
//        // 添加excel title
//        HSSFCell cell = null;
//        for(int i=0;i<strArray.length;i++){
//            cell = row.createCell(i);
//            cell.setCellValue(strArray[i]);
//            cell.setCellStyle(cellStyle);
//        }
//        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
//        for(int i=0; i<berthPlanList.size(); i++) {
//        	HSSFRow rowBody = sheet.createRow(i+1);
//        	rowBody.setHeightInPoints(16);
//        	//船舶状态
//        	HSSFCell cellIsFinished = rowBody.createCell(0);
//        	cellIsFinished.setCellValue(this.getIsFinished(berthPlanList.get(i).getIsFinished()));
//        	cellIsFinished.setCellStyle(cellStyle2);
//        	//船舶代码
//            HSSFCell cellVesselCode = rowBody.createCell(1);
//            cellVesselCode.setCellValue(berthPlanList.get(i).getVesselCode());
//            cellVesselCode.setCellStyle(cellStyle2);
//            //中文船名
//            HSSFCell cellVesselNameCn = rowBody.createCell(2);
//            cellVesselNameCn.setCellValue(berthPlanList.get(i).getVesselNameCn());
//            cellVesselNameCn.setCellStyle(cellStyle2);
//            //英文船名
//            HSSFCell cellVesselNameEn = rowBody.createCell(3);
//            cellVesselNameEn.setCellValue(berthPlanList.get(i).getVesselNameEn());
//            cellVesselNameEn.setCellStyle(cellStyle2);
//            //进 /出口航次
//            HSSFCell cellVoyage = rowBody.createCell(4);
//            cellVoyage.setCellValue(berthPlanList.get(i).getInVoyage()+"/"+berthPlanList.get(i).getOutVoyage());
//            cellVoyage.setCellStyle(cellStyle2);
//            //船公司
//            HSSFCell cellOperatorCode = rowBody.createCell(5);
//            cellOperatorCode.setCellValue(berthPlanList.get(i).getOperatorCode());
//            cellOperatorCode.setCellStyle(cellStyle2);
//            //泊位
//            HSSFCell cellBerthName = rowBody.createCell(6);
//            cellBerthName.setCellValue(berthPlanList.get(i).getBerthName());
//            cellBerthName.setCellStyle(cellStyle2);
//            //船舶类型
//            HSSFCell cellVesselType = rowBody.createCell(7);
//            cellVesselType.setCellValue(berthPlanList.get(i).getVesselType());
//            cellVesselType.setCellStyle(cellStyle2);
//            //卸量 /装量 /总量
//            HSSFCell cellMount = rowBody.createCell(8);
//            cellMount.setCellValue(berthPlanList.get(i).getDischargMount()+"/"+berthPlanList.get(i).getLoadMount()+"/"+(berthPlanList.get(i).getDischargMount()+berthPlanList.get(i).getLoadMount()));
//            cellMount.setCellStyle(cellStyle2);
//            //靠泊时间
//            HSSFCell cellAberthingTime = rowBody.createCell(9);
//            cellAberthingTime.setCellValue(berthPlanList.get(i).getAberthingTime());
//            cellAberthingTime.setCellStyle(cellStyle2);
//            //离泊时间
//          /*  HSSFCell cellAdepartureTime = rowBody.createCell(10);
//            cellAdepartureTime.setCellValue(berthPlanList.get(i).getAdepartureTime());
//            cellAdepartureTime.setCellStyle(cellStyle2);*/
//        }
//    }
//	
//	/**
//	  * 创建excel title
//    */
//   public static String[] getExcelTitle() {
//       String[] strArray = {"船舶状态","船舶代码","中文船名","英文船名","进 /出口航次","船公司","泊位","船舶类型","卸量 /装量 /总量","靠泊时间" };
//       return strArray;
//   }
//   private String getIsFinished(String isFinished) {
//	   if("0".equals(isFinished)) {
//		   return "待作业";
//	   }else {
//		   return "完成作业";
//	   }
//   }
//	
}
