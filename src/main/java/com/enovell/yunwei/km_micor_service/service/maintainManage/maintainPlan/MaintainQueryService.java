package com.enovell.yunwei.km_micor_service.service.maintainManage.maintainPlan;

import java.util.List;

import org.bson.Document;

public interface MaintainQueryService {

	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String name, String startDate, String endDate,String auditStatus);
	List<Document> findAllDocument(String collectionName,String name, String startDate, String endDate, String auditStatus,int start, int limit);
}
