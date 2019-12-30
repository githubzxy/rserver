package com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan;

import java.util.List;

import org.bson.Document;

public interface SkylightMaintenanceApplyQueryService {

//	/**
//     * 申请
//     * @param doc 数据对象
//     * @param collectionName 表名
//     * @return 新增的数据对象
//     */
//    Document addDocument(Document doc,String collectionName);
    long findAllDocumentCount(String collectionName, String userId, String orgId, String currentDay,String project, String type, String startUploadDate,String endUploadDateString , String flowState,String orgSelectName);
    
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param start 起始数据
     * @param limit 展示条数
     * @return 数据对象列表
     */
    List<Document> findAllDocument(String collectionName,  String userId, String orgId, String currentDay,String project, String type, String startUploadDate,String endUploadDate, String flowState,String orgSelectName, int start, int limit);
    
    /**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String collectionName);

	
	
}
