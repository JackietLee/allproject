package net.pingfang.serviceImpl.importFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
/**
 * 车牌EXCEL导入
 * @author cgf
 * 
 */
public class VesselListReadExcel {
	
	private final static Logger logger = LoggerFactory.getLogger(VesselListReadExcel.class);
    // 总行数
    private int totalRows = 0;
    // 总条数
    private int totalCells = 0;
    // 错误信息接收器
    private String errorMsg;

    // 构造方法
    public VesselListReadExcel() {
    }

    // 获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    // 获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    // 获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取信息集合
     * 
     * @param fielName
     * @return
     */
    public List<Map<String, Object>> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();// 获取文件名
        try {
        	  if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
        	  }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            return createExcel(mFile.getInputStream(), isExcel2003);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    /**
     * 根据excel里面的内容读取客户信息
     * 
     * @param is      输入流
     * @param isExcel2003   excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> createExcel(InputStream is, boolean isExcel2003) {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelValue(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel里面客户的信息
     * 
     * @param wb
     * @return
     */
    private List<Map<String, Object>> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
       /* if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }*/
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(4).getPhysicalNumberOfCells();
        }
        List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();
      //  String formatDate = "yyyy-MM-dd HH:mm";
        // 循环Excel行数
       // for (int r = 1; r < totalRows; r++) {
        for (int r = 5; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null || null == row.getCell(0)) {
                continue;
            }
            // 循环Excel的列
            Map<String, Object> map = new HashMap<String, Object>();
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if(null != cell) {
                    if(c == 0) {
                        // 如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String name = String.valueOf(cell.getNumericCellValue());
                            map.put("vesselName", name.substring(0, name.length() - 2 > 0 ? name.length() - 2 : 1));//车牌号
                        } else {
                            map.put("vesselName", cell.getStringCellValue());//船名
                        }
                    }else if (c == 1) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String plateTypeId = String.valueOf(cell.getNumericCellValue());
                            map.put("voyage",plateTypeId.substring(0, plateTypeId.length() - 2 > 0 ? plateTypeId.length() - 2 : 1));// 车牌类型id
                        } else {
                            map.put("voyage",cell.getStringCellValue());//航次
                        }
                    }else if (c == 2) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String vehicleType = String.valueOf(cell.getNumericCellValue());
                            map.put("billNumber", vehicleType.substring(0, vehicleType.length() - 2 > 0 ? vehicleType.length() - 2 : 1));//车辆类型
                        } else {
                            map.put("billNumber", cell.getStringCellValue());//提单号
                        }
                    }else if (c == 3) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String enterprise = String.valueOf(cell.getNumericCellValue());
                            map.put("containerNumber", enterprise.substring(0, enterprise.length() - 2 > 0 ? enterprise.length() - 2 : 1));//所属车组
                        } else {
                            map.put("containerNumber", cell.getStringCellValue());//箱号
                        }
                    }else if (c == 4) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String enterprise = String.valueOf(cell.getNumericCellValue());
                            map.put("sizeType", enterprise.substring(0, enterprise.length() - 2 > 0 ? enterprise.length() - 2 : 1));//所属企业
                        } else {
                            map.put("sizeType", cell.getStringCellValue());//尺寸
                        }
                    }else if (c == 5) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String isEnabled = String.valueOf(cell.getNumericCellValue());
                            map.put("cargoType", isEnabled.substring(0, isEnabled.length() - 2 > 0 ? isEnabled.length() - 2 : 1));//是否启用(Y为启用，N为不启用,默认为Y)
                        } else {
                            map.put("cargoType", cell.getStringCellValue());//类型
                        }
                    }else if (c == 6) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String vehicleLane = String.valueOf(cell.getNumericCellValue());
                            map.put("stuffingStatus", vehicleLane.substring(0, vehicleLane.length() - 2 > 0 ? vehicleLane.length() - 2 : 1));//车道号
                        } else {
                            map.put("stuffingStatus", cell.getStringCellValue());//空/重
                        }
                    }else if (c == 7) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String numberPasses = String.valueOf(cell.getNumericCellValue());
                            map.put("sealNumber", numberPasses.substring(0, numberPasses.length() - 2 > 0 ? numberPasses.length() - 2 : 1));//通行次数
                        } else {
                            map.put("sealNumber", cell.getStringCellValue());//铅封号
                        }
                    }else if (c == 8) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("portLoading", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("portLoading", cell.getStringCellValue());//装货港
                        }
                    }else if (c == 9) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("portDischarge", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("portDischarge", cell.getStringCellValue());//卸货港
                        }
                    }else if (c == 10) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("portDestination", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("portDestination", cell.getStringCellValue());//目的港
                        }
                    }else if (c == 11) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("goodsName", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("goodsName", cell.getStringCellValue());//货名
                        }
                    }else if (c == 12) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("terms", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("terms", cell.getStringCellValue());//条款
                        }
                    }else if (c == 13) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("number", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("number", cell.getStringCellValue());//件数
                        }
                    }else if (c == 14) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("volume", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("volume", cell.getStringCellValue());//体积
                        }
                    }else if (c == 15) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("weight", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("weight", cell.getStringCellValue());//重量
                        }
                    }else if (c == 16) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("operator", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("operator", cell.getStringCellValue());//箱公司
                        }
                    }else if (c == 17) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("tradeType", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("tradeType", cell.getStringCellValue());//内外贸
                        }
                    }else if (c == 18) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("damaged", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("damaged", cell.getStringCellValue());//残损
                        }
                    }else if (c == 19) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("temperature", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("temperature", cell.getStringCellValue());//冷藏温度
                        }
                    }else if (c == 20) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("dangerClass", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("dangerClass", cell.getStringCellValue());//危险等级
                        }
                    }else if (c == 21) {
                    	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String passesType = String.valueOf(cell.getNumericCellValue());
                            map.put("payCostUnit", passesType.substring(0, passesType.length() - 2 > 0 ? passesType.length() - 2 : 1));//通行类型(1为可直接通行、2为识别后人工控制通行、3为不可通行)
                        } else {
                            map.put("payCostUnit", cell.getStringCellValue());//缴费单位
                        }
                    }
                    
                }
            }
            // 添加到list
            vehicleList.add(map);
        }
        return vehicleList;
    }

    /**
     * 验证EXCEL文件
     * 
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    
    /*
    public static void main(String[] args) {
   	 //创建处理EXCEL的类  
	    VesselListReadExcel readExcel = new VesselListReadExcel(); 
	    String path = "F:\\123456\\importTest.xlsx";
       InputStream is;
		try {
			is = new FileInputStream(path);
	    //解析excel，获取上传的事件单  
	    List<Map<String, Object>> vehicleList = readExcel.createExcel(is, false);  
	    
	    if(vehicleList != null && !vehicleList.isEmpty()){  
	    	List<VehicleVo> list = new ArrayList<VehicleVo>();
	    	VehicleVo vehicle = null;
		    //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,  
		    for(Map<String, Object> obj:vehicleList){
		    	vehicle = new VehicleVo();
		    	vehicle.setPlate(obj.get("plate").toString());	//车牌号
		    	vehicle.setPlateTypeId(Integer.parseInt(obj.get("plateTypeId").toString()));	//车牌类型id
		    	vehicle.setVehicleType(obj.get("vehicleType").toString());	//车辆类型
		    	vehicle.setEnterprise(obj.get("enterprise").toString());	//所属企业
		    	vehicle.setIsEnabled(obj.get("isEnabled").toString());	//是否启用(Y为启用，N为不启用,默认为Y)
		    	
		    	list.add(vehicle);
		    }
		    if(list.size() >0) {
		    	for(VehicleVo v : list) {
		    		System.out.println(v.getPlate());
		    		System.out.println(v.getPlateTypeId());
		    		System.out.println(v.getVehicleType());
		    		System.out.println(v.getEnterprise());
		    		System.out.println(v.getIsEnabled());
		    	}
		    		System.out.println("***********插入权限*********");
		    }
	    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    */
}
