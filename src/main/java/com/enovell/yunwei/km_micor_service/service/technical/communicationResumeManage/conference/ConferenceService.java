package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage.conference;

import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;

public interface ConferenceService {

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
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String collectionName);
    
    ResultMsg importConferenceMcu(String publicType, MultipartFile file, String userId, String orgId);
    
    ResultMsg importConferenceOther(String publicType, MultipartFile file, String userId, String orgId);
}
