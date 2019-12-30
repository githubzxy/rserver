package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * kunmingTXD
 * 昆明新增功能通用服务
 * @author yangsy
 * @date 19-1-16
 */
public interface NetworkManageInfoService {
	
	/**
	 * 获取车间下拉选数据
	 * @return
	 */
	List<Map<String, Object>> getWorkShops();
	/**
	 * 获取部门下拉选数据
	 */
	List<Map<String, Object>> getDeparts(String workShopName);
	
	/**
	 * 获取线名下拉选数据
	 * @return
	 */
	List<String> getLineData();
	
	List<String> getStationNoConditionData();
	
	/**
	 * 根据线别查询站点数据
	 * @return
	 */
	List<Map<String, Object>> getStationsData(String lineName);
	
	/**
     * 新增数据
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 新增的数据对象
     */
    Document addDocument(Document doc,String collectionName);
    
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param detail 
     * @param systemType 
     * @param system 
     * @return 条数
     */
//    long findAllDocumentCount(String collectionName, String userId, String currentDay, Date startUploadDate,Date endUploadDate);
    long findAllDocumentCount(String collectionName, String userId, String orgId, String currentDay,String infoResult,String type, String startUploadDate,String endUploadDate, String detail, String system, String systemType);
    
    /**
     * 分页查询当前表中的数据
     * @param collectionName 表名
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param start 起始数据
     * @param limit 展示条数
     * @param detail 
     * @param systemType 
     * @param system 
     * @return 数据对象列表
     */
//    List<Document> findAllDocument(String collectionName,  String userId, String currentDay, Date startUploadDate,Date endUploadDate, int start, int limit);
    List<Document> findAllDocument(String collectionName,  String userId, String orgId, String currentDay, String infoResult,String type,String startUploadDate,String endUploadDate, int start, int limit, String detail, String system, String systemType);
    
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
    Document updateDocument(Document doc,String collectionName);

    /**
     * 删除数据对象
     * @param ids 数据对象Id
     * @param collectionName 表名
     */
    void removeDocument(List<String> ids,String collectionName);
    
    /**
     * 根据busiId查询单条数据
     * @param busiId 自定义的业务ID
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentByBusiId(String busiId,String collectionName);
	
    /**
     * 上传附件
     * @param files 上传文件
     * @return 附件实体
     */
    List<Document> uploadFile(List<MultipartFile> files);
}