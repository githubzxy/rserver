package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.util.Date;
import java.util.List;

import org.bson.Document;

public interface CompletionOfKeyTasksService {
	/**
     * 新增数据
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 新增的数据对象
     */
    Document addDocument(Document doc,String collectionName);

    /**
     * 更新数据对象
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 数据对象
     */
    Document updateDocument(Document doc,String collectionName);

    /**
     * 删除数据对象
     * @param ids 数据对象Id
     * @param collectionName 表名
     */
    void removeDocument(List<String> ids,String collectionName);
    /**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String collectionName);
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param name   数据名称
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param orgId 用户Id
     * @param start 起始数据
     * @param limit 展示条数
     * @return 数据对象列表
     */
    List<Document> findAllDocument(String collectionName,  String userId, String currentDay, String startUploadDate,String endUploadDate, int start, int limit);
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param name   数据名称
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param orgId 用户Id
     * @return 条数
     */
    long findAllDocumentCount(String collectionName, String userId, String currentDay, String startUploadDate,String endUploadDate);
    
}
