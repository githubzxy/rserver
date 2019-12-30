package com.enovell.yunwei.km_micor_service.service.technical.systemDevice;

import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.SystemDeviceDto;

public interface SystemDeviceService {

	GridDto<SystemDeviceDto> getDataBy(String system, String deviceType, int start, int limit);

	SystemDeviceDto getDataById(String id);

	SystemDeviceDto updateTechnicalInfo(String id, String system, String deviceType, String remark);

	void remove(List<String> ids);

	List<String> getAllSystemData();

	List<String> getAllDeviceData(String system);

	SystemDeviceDto addInfo(String system, String deviceType, String remark);

}
