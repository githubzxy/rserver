package com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage;

import java.util.List;
import java.util.Map;

import org.bson.Document;

public interface CircuitWorkOrderApplyService {
	
	/**
	 * 获取车间下拉选数据
	 * @return
	 */
	List<Map<String, Object>> getWorkShops();

	/**
     * 申请
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 新增的数据对象
     */
    Document addDocument(Document doc,String collectionName);
	/**
	 * 获取线名下拉选数据
	 * @return
	 */
	List<String> getLineData();
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @return 条数
     */
    long findAllDocumentCount(String collectionName, String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate);
    
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param start 起始数据
     * @param limit 展示条数
     * @return 数据对象列表
     */
    List<Document> findAllDocument(String collectionName,  String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate,String endUploadDate, int start, int limit);
    
    /**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String collectionName);
    
    /**
     * 更新数据对象
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 数据对象
     */
    Document modifyDocument(Document doc,String collectionName);
    
    
    
    
    Document updateDocument(Document doc,String collectionName);

    /**
     * 删除数据对象
     * @param ids 数据对象Id
     * @param collectionName 表名
     */
    void removeDocument(List<String> ids,String collectionName);
	
	
}
