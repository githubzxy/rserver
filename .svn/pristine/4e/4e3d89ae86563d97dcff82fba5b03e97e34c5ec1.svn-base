package com.enovell.yunwei.km_micor_service.service.maintainManage.maintainPlan;

import java.util.List;

import org.bson.Document;

public interface MaintainAuditService {

	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String name, String startDate, String endDate,String auditStatus,String orgId);
	List<Document> findAllDocument(String collectionName,String name, String startDate, String endDate, String auditStatus,int start, int limit,String orgId);
	/**
	 * 根据id修改数据
	 * @param id
	 * @return
	 */
	Document findDatasById(String id,String collectionName);
	/**
	 * 修改数据
	 * @param doc
	 * @param collectionName
	 * @return
	 */
	Document updateDocument(Document doc, String collectionName);
}
