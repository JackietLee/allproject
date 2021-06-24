package net.pingfang.service.huangPu;

import java.util.List;
import java.util.Map;
import net.pingfang.entity.work.CranePreparationVo;

public interface WorkSwitchService {
	
	/**
	 * 获取门机配制
	 * @return
	 */
	public Map<String,Object> getHpCranePreparationList();
	public CranePreparationVo getHpCranePreparationByCraneNum(String craneNum);
	
	/**
	 * 1、设置门机工作相机协议
	 * 2、门机切换球机摄像头
	 *  {
			"messageType": "MJ01",
			"area": "场区",
			"driverCode": "设备号",
			"driverName": "设备名称",
			"acquisitionTime": "指令时间",
			"workType": "作业类型：bay,conta,vehicle",
			"clientId": "客户端ID",
			"vessel_voyage_number": “航次”,
			"cameraList": [{
				"cameraCode": "相机编号",
				"cameraIp": "相机IP",
				"cameraType": "相机工作类型"
			}]
		}

	 * @return
	 */
	public int hpSwitchCameraList(CranePreparationVo cranePreparationVo);
	
	/**
	 * 1、设置门机开始停止工作协议
	 * 2、启动门机作业
	 * 
	 * {
			"messageType": "MJ02",
			"area": "场区",
			"driverCode": "设备号",
			"driverName": "设备名称",
			"acquisitionTime": "指令时间",
			"workType": "作业类型：bay,conta,vehicle",
			"cmdType": "命令类型：start,stop",
			"clientId": "客户端ID",
			"vesselVoyageNumber": "航次",
			"jobQueueCode": "作业号"
		}

	 * @return
	 */
	public int hpStartHomeWork(CranePreparationVo cranePreparationVo);
	/**
	 * 查询门机配制列表数据
	 * @return
	 */
	public List<CranePreparationVo> getAllMjPreparationList();
	/**
	 * 删除门机配制数据
	 * @param id
	 * @return
	 */
	public int deleteHpCranePreparation(int id);
	/**
	 * 删除门机配制数据
	 * @param id
	 * @return
	 */
	public int deleteHpCranePreparationByCraneNum(String craneNum);
	/**
	 * 更新门机配制开始或停止作业
	 * @param cranePreparationVo
	 * @return
	 */
	public int updateActivity(CranePreparationVo cranePreparationVo);

}
