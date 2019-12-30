package com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage;

import java.util.List;
import java.util.Map;

import org.bson.Document;

public interface CircuitWorkOrderWorkshopReplyService {
	
	/**
	 * 获取部门下拉选数据
	 */
	List<Map<String, Object>> getDeparts(String workShopName);
	
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @return 条数
     */
    long findAllDocumentCount(String collectionName, String docId, String orgId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate);
    
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param start 起始数据
     * @param limit 展示条数
     * @return 数据对象列表
     */
    List<Document> findAllDocument(String collectionName, String docId, String orgId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate, int start, int limit);
    
    /**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String executiveOrgId,String collectionName);
    
    /**
     * 更新数据对象
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 数据对象
     */
    Document updateDocument(Document doc,String collectionName,String flowState);
    
    /**
     * 车间回复数据新增
     * @param doc
     * @param collectionName
     * @return
     */
    void addReplyDocument(Document newDocument,String circuitIssueWorkOrderReply, Document document,String circuitIssueWorkOrder);
    
    
    void setFlowState(String docId,String collectionName);
    
    /**
     * 查询车间回复的条数
     * @param collectionName
     * @param docId
     * @param executiveOrgId
     * @return
     */
    long findIssueReplyAllDocumentCount(String collectionName, String docId, String executiveOrgId);
	/**
	 * 查询车间回复的数据
	 * @param collectionName
	 * @param docId
	 * @param executiveOrgId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Document> findIssueReplyAllDocument(String collectionName, String docId, String executiveOrgId, int start,int limit);
	
}