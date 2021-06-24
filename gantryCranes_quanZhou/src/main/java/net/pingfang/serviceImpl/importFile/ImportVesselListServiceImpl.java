package net.pingfang.serviceImpl.importFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import net.pingfang.entity.importFile.VesselListVo;
import net.pingfang.handler.Result;
import net.pingfang.service.importFile.ImportVesselListService;
import net.pingfang.service.vessel.VesselListService;
import net.pingfang.utils.ResultUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class ImportVesselListServiceImpl implements ImportVesselListService{
	@Autowired
	private VesselListService vesselListService;
	
	private final static Logger logger = LoggerFactory.getLogger(ImportVesselListServiceImpl.class);
	
	/**
	 * 解析EXCEL船清单数据,生成list 
	 * @param file
	 * @return
	 */
	@Override
	public Result<Object> insertExcelFile(MultipartFile file){
		String fileName = file.getOriginalFilename();
	    //创建处理EXCEL的类  
		VesselListReadExcel readExcel = new VesselListReadExcel();  
	    //解析excel，获取上传的事件单  
	    List<Map<String, Object>> vehicleList = readExcel.getExcelInfo(file);  
	    if(vehicleList != null && !vehicleList.isEmpty()){  
	    	logger.info("解析车牌导入数据成功！");
	    	List<VesselListVo> list = new ArrayList<VesselListVo>();
	    	VesselListVo vesselList = null;
	    	
		    //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,  
		    for(Map<String, Object> obj:vehicleList){
		    	vesselList = new VesselListVo();
		    	vesselList.setVesselName(obj.get("vesselName").toString());// 船名
		    	vesselList.setVoyage(obj.get("voyage").toString());// 航次
		    	vesselList.setBillNumber(obj.get("billNumber").toString());// 提单号
		    	vesselList.setContainerNumber(obj.get("containerNumber").toString());// 箱号
		    	if(!"".equals(obj.get("sizeType").toString())) {
		    		vesselList.setSizeType(Integer.parseInt(obj.get("sizeType").toString()));// 尺寸
		    	}
		    	
		    	vesselList.setCargoType(obj.get("cargoType").toString());// 类型
		    	vesselList.setStuffingStatus(obj.get("stuffingStatus").toString());// 空/重
		    	vesselList.setSealNumber(obj.get("sealNumber").toString());// 铅封号
		    	vesselList.setPortLoading(obj.get("portLoading").toString());// 装货港
		    	vesselList.setPortDischarge(obj.get("portDischarge").toString());// 卸货港
		    	vesselList.setPortDestination(obj.get("portDestination").toString());// 目的港
		    	vesselList.setGoodsName(obj.get("goodsName").toString());// 货名
		    	vesselList.setTerms(obj.get("terms").toString());// 条款
		    	vesselList.setNumber(obj.get("number").toString());//件数
		    	vesselList.setVolume(obj.get("volume").toString());//体积
		    	vesselList.setWeight(obj.get("weight").toString());//重量
		    	vesselList.setOperator(obj.get("operator").toString());//箱公司
		    	vesselList.setTradeType(obj.get("tradeType").toString());//内外贸
		    	vesselList.setDamaged(obj.get("damaged").toString());//残损
		    	vesselList.setTemperature(obj.get("temperature").toString());//冷藏温度
		    	vesselList.setDangerClass(obj.get("dangerClass").toString());//危险等级
		    	vesselList.setPayCostUnit(obj.get("payCostUnit").toString());//危险等级
		    	
		    	vesselList.setTallyClerk("张飞");
		    	vesselList.setFileName(fileName);
		    	list.add(vesselList);
		    }
		    if(list.size() >0) {
		    	//List<VehicleVo> vList = addOrUpdateVehicle(list);//批量插入或更新车牌号
		    	int count = vesselListService.insertVesselList(list);
		    	System.out.println("车牌数据导入成功！count: "+count);
		    	logger.info("车牌数据导入成功！");
		    	return ResultUtil.success(list);
		    }else {
	        	return ResultUtil.error(1, "解析船清单EXCEL失败！");
	        }
	    }else{  
	    	logger.info("解析船清单EXCEL失败！");
	    	return ResultUtil.error(1, "解析船清单EXCEL失败！");
	    }  
	}

	@Override
	public Result<Object> insertXmlFile(MultipartFile file) {

		try {
			List<VesselListVo> vesselListVos = new ArrayList<>();
			SAXReader reader = new SAXReader();
			Document document = reader.read(file.getInputStream());
			Element root = document.getRootElement();
			String vesselName = root.element("SHIP").element("船名").getText();
			String vesselCode = root.element("SHIP").element("船号").getText();
			String voyage = root.element("SHIP").element("航次").getText();
			String tradeType = root.element("SHIP").element("内外贸").getText();
			String inOut = root.element("SHIP").element("进出口").getText();
			for (Iterator i = root.element("LIST").elementIterator("RAW"); i.hasNext();) {
				VesselListVo vesselList = new VesselListVo();
				Element foo = (Element) i.next();
				vesselList.setVesselName(vesselName);
				vesselList.setVoyage(voyage);
				vesselList.setVesselNumber(vesselCode);
				vesselList.setTradeType(tradeType);
				vesselList.setInOut(inOut.equalsIgnoreCase("进口")?"1":"2");
				vesselList.setBillNumber(foo.element("提单号").getText());
				vesselList.setContainerNumber(foo.element("箱号").getText());
				vesselList.setSizeType(StringUtils.isEmpty(foo.element("箱型").getText())?0:Integer.parseInt(foo.element("箱型").getText()));
				vesselList.setCargoType(foo.element("箱类").getText());
				vesselList.setStuffingStatus(foo.element("重空状态").getText());
				vesselList.setSealNumber(foo.element("铅封号").getText());
				vesselList.setPortDestination(foo.element("目的港").getText());
				vesselList.setPortDischarge(foo.element("卸货港").getText());
				foo.element("位置").getText();
				vesselList.setTallyClerk(foo.element("理货员").getText());
				foo.element("时间").getText();
				vesselList.setIsTransfer(foo.element("中转")==null?"":foo.element("中转").getText());
				vesselListVos.add(vesselList);
			}
			if(vesselListVos.size() >0) {
				//List<VehicleVo> vList = addOrUpdateVehicle(list);//批量插入或更新车牌号
				int count = vesselListService.insertVesselList(vesselListVos);
				System.out.println("车牌数据导入成功！count: "+count);
				logger.info("车牌数据导入成功！");
				return ResultUtil.success(vesselListVos);
			}else {
				return ResultUtil.error(1, "解析船清单EXCEL失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {

	}
}
