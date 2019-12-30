package com.enovell.yunwei.km_micor_service.service.securityManage.accidentTroubleObstacleManage;

import java.util.List;

import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.AccidentTroubleDto;

public interface AccidentTroubleObstacleQueryService {
	
	Document findDocumentById(String id,String collectionName);
	
	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName, String infoResult, String startDate, String endDate);
	List<Document> findAllDocument(String collectionName, String infoResult, String startDate, String endDate, int start, int limit);

	void convertInfoResult(List<AccidentTroubleDto> dataList);
}
