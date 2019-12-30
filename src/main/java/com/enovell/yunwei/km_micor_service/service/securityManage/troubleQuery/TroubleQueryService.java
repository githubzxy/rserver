package com.enovell.yunwei.km_micor_service.service.securityManage.troubleQuery;

import java.util.List;

import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.TroubleQueryDto;

public interface TroubleQueryService {
	
	Document findDocumentById(String id,String collectionName);
	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String startDate, String endDate,String obligationDepart,String troubleLineName,String troubleSite,String troubleGeneral);
	List<Document> findAllDocument(String collectionName,String startDate, String endDate,String obligationDepart,String troubleLineName,String troubleSite,String troubleGeneral, int start, int limit);
	/**
	 * 导出故障填写表单
	 * exportTable 这里添加描述信息
	 * @param dto
	 * @return
	 */
	public ResultMsg exportTable(TroubleQueryDto dto);
}