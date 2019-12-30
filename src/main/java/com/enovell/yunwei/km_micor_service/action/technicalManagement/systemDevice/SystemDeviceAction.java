package com.enovell.yunwei.km_micor_service.action.technicalManagement.systemDevice;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.SystemDeviceDto;
import com.enovell.yunwei.km_micor_service.service.technical.systemDevice.SystemDeviceService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：SystemDeviceAction   
 * 类描述:  系统线路
 * 创建人：zhouxingyu
 * 创建时间：2019年8月14日 下午3:24:07
 * 修改人：zhouxingyu 
 * 修改时间：2019年8月14日 下午3:24:07   
 *
 */
@RestController
@RequestMapping("/systemDeviceAction")
public class SystemDeviceAction {
	@Resource(name = "systemDeviceService")
	private SystemDeviceService systemDeviceService;
	
	@PostMapping(value = "/findAll")
	public GridDto<SystemDeviceDto> getAllSystemDeviceInfos(
			@RequestParam("start") int start,
			@RequestParam("limit") int limit,
			String system,String deviceType){
		GridDto<SystemDeviceDto> systemDeviceDtos = systemDeviceService.getDataBy(system,deviceType,start,limit);
		return systemDeviceDtos;
	}
	@PostMapping(value = "/findById")
	public SystemDeviceDto findById(@RequestParam("id")String id) {
		return systemDeviceService.getDataById(id);
	}
	@PostMapping(value = "/updateInfo")
	public ResultMsg updateTechnicalInfo(String id ,String system,String deviceType,String remark) {
		SystemDeviceDto systemDeviceDto = systemDeviceService.updateTechnicalInfo(id,system,deviceType,remark);
		return ResultMsg.getSuccess("修改成功！",systemDeviceDto);
	}
	
	@PostMapping(value = "/addInfo")
	public ResultMsg addInfo(String system,String deviceType,String remark) {
		SystemDeviceDto systemDeviceDto = systemDeviceService.addInfo(system,deviceType,remark);
		return ResultMsg.getSuccess("添加成功！",systemDeviceDto);
	}
	@PostMapping(value = "/remove")
	public ResultMsg rmInfo(String id ) {
		List<String> ids = Arrays.asList(id.split(","));
		systemDeviceService.remove(ids);
		return ResultMsg.getSuccess("删除成功！",id);
	
	}
	@PostMapping(value = "getSystem")
	public List<String> getSystem() {
		List<String> sysList = systemDeviceService.getAllSystemData();
		return sysList;
		
	}
	@PostMapping(value = "getDevice")
	public  List<String> getDevice(String system) {
		List<String> DevList = systemDeviceService.getAllDeviceData(system);
		return DevList;
	}
}
