package com.enovell.yunwei.km_micor_service.service;



import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolDTO;
import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolExportDTO;

/**
 * kunmingTXD
 * 昆明新增功能通用服务
 * @author bili
 * @date 18-11-20
 */
public interface WorkAssortProtocolService {
	
	 List<WorkAssortProtocolExportDTO> getAllFile(WorkAssortProtocolDTO dto);
}
