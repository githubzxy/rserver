package com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan;

import java.util.List;

import org.bson.Document;

public interface PointInnerSecondMaintainQueryService {

	/**
	 * 分页查询
	 * @return
	 */
	 /**
     * 获取车间（科室）数据
     * @return
     */
	public List<String> getAllworkArea();
	long findAllDocumentCount(String collectionName, String constructionProject, String flowState, String startDate, String endDate,String submitOrgName);
	List<Document> findAllDocument(String collectionName, String constructionProject, String flowState, String startDate, String endDate,String submitOrgName, int start, int limit);
}
