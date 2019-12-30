package com.enovell.yunwei.km_micor_service.service.productionManage.taskManage;

import java.util.List;

import org.bson.Document;

public interface TaskQueryService {
	
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @return 条数
     */
    long findAllDocumentCount(String collectionName, String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate,String orgId);
    
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param start 起始数据
     * @param limit 展示条数
     * @return 数据对象列表
     */
    List<Document> findAllDocument(String collectionName,  String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate, int start, int limit,String orgId);
    
    /**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String collectionName);
    
}