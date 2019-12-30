package com.enovell.yunwei.km_micor_service.service.productionManage.taskManage;

import java.util.List;

import org.bson.Document;

public interface TaskWorkAreaReplyService {
	
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @return 条数
     */
    long findAllDocumentCount(String collectionName, String docId,String executiveOrgId, String orgId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate);
    
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param start 起始数据
     * @param limit 展示条数
     * @return 数据对象列表
     */
    List<Document> findAllDocument(String collectionName,String docId,String executiveOrgId,  String orgId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate, int start, int limit);
    
    /**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String distributeOrgId,String collectionName);
    
    /**
     * 更新数据对象
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 数据对象
     */
    Document updateDocument(Document doc,String collectionName,String flowState);
    
    
    void setFlowState(String docId, String executiveOrgId, String collectionName);
	
    /**
     * 工区回复数据新增
     * @param doc
     * @param collectionName
     * @return
     */
	void addReplyDocument(Document newDocument, String taskWorkAreaReply, Document document, String IssueTaskOfWorkArea);
	
	/**
     * 查询工区回复的条数
     * @param collectionName
     * @param docId
     * @param executiveOrgId
     * @return
     */
    long findDistributeReplyAllDocumentCount(String collectionName, String docId, String distributeOrgId);
	/**
	 * 查询工区回复的数据
	 * @param collectionName
	 * @param docId
	 * @param executiveOrgId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Document> findDistributeReplyAllDocument(String collectionName, String docId, String distributeOrgId, int start,int limit);
}
