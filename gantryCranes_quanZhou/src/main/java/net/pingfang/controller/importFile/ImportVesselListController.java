package net.pingfang.controller.importFile;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import net.pingfang.handler.Result;
import net.pingfang.service.importFile.ImportVesselListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 导入船清单数据
 * @author Administrator
 *
 */
@Api("ImportVesselList Api")
@RestController
@RequestMapping("/importVessel")
public class ImportVesselListController {
	
	@Autowired
	private ImportVesselListService importVesselListService;
	
	private final static Logger logger = LoggerFactory.getLogger(ImportVesselListController.class);
	
	/**
	 * 解析EXCEL船清单数据
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value="解析EXCEL船清单数据", notes="解析EXCEL船清单数据")
	@ApiParam(name = "MultipartFile", value = "船清单EXCEL数据文件")
    @PostMapping("/importExcelVesselList")
	@RequiresPermissions(value = {"importExcelVesselList"})
    public Result<Object> importExcelVesselList(@RequestParam(value="file",required = false) MultipartFile file){
        return importVesselListService.insertExcelFile(file);
    }

	@ApiOperation(value="解析xml船清单数据", notes="解析EXCEL船清单数据")
	@ApiParam(name = "MultipartFile", value = "船清单EXCEL数据文件")
	@PostMapping("/importXmlVesselList")
	@RequiresPermissions(value = {"importXmlVesselList"})
	public Result<Object> importXmlVesselList(@RequestParam(value="file",required = false) MultipartFile file){
		return importVesselListService.insertXmlFile(file);
	}
}
