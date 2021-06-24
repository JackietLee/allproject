package net.pingfang.service.importFile;

import org.springframework.web.multipart.MultipartFile;
import net.pingfang.handler.Result;

public interface ImportVesselListService {
	
	/**
	 * 解析EXCEL船清单数据,生成list 
	 * @param file
	 * @return
	 */
	public Result<Object> insertExcelFile(MultipartFile file);

	Result<Object> insertXmlFile(MultipartFile file);
}
