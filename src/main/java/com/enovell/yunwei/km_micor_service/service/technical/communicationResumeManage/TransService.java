package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;

public interface TransService {
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
    List<Document> findAllDocument(String collectionName, String workshop, String workArea, String deviceName, String publicType,String deviceClass,String deviceType,String manufacturers, int start, int limit);
    /**
     * 分页查询数量
     * @param collectionName 表名
     * @param name   数据名称
     * @param startUploadDate 起始上传时间
     * @param endUploadDate 结束上传时间
     * @param orgId 用户Id
     * @return 条数
     */
    long findAllDocumentCount(String collectionName,  String workshop, String workArea, String deviceName, String publicType,String deviceClass,String deviceType,String manufacturers);
    /**
     * 根据车间科室名获取组织机构id
     */
    String getOrgIdByWorkshop(String workshop);
    /**
     * 根据车间科室名获取组织类型
     */
    String getOrgTypeByWorkshop(String workshop);
    /**
     * 根据组织机构Id获取组织类型
     */
    List<Map<String, Object>> getChildIdByOrgId(String orgId);
    /**
     * 获取车间数据
     * @return
     */
	public List<String> getCadreAndShop();
	
	/**
	 * 
	 * getAllDocumentByPublicType 这里添加描述信息
	 * @param collectionName
	 * @param publicType
	 * @return
	 */
    List<Document> getAllDocumentByPublicType(String collectionName, String publicType);
    /**
     * 
     * importTransDwdm 导入方法
     * @param publicType
     * @param file
     * @param userId
     * @param orgId
     * @return
     */
    public ResultMsg importTransDwdm(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importTransSdh(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importTransNumberTrans(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importTransOther(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importTransOtn(String publicType, MultipartFile file, String userId, String orgId);
}

