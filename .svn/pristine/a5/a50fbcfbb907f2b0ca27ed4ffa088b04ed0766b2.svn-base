package com.enovell.yunwei.km_micor_service.service.constructionManage.constructProtocol;

import java.util.List;

import org.bson.Document;

public interface ConstructProtocolService {
	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String proName,String proCompany,String line,String workShop,String beginDateStart,String beginDateEnd,String proPlace,String overDateStart,String overDateEnd);
	List<Document> findAllDocument(String collectionName,String proName,String proCompany,String line,String workShop,String beginDateStart,String beginDateEnd,String proPlace,String overDateStart,String overDateEnd,int start, int limit);
	
	/**
	 * 新增信息
	 */
	public Document addDocument(Document doc, String collectionName);
	
	/**
	 * 查询车间数据
	 */
	public List<String> getWorkShops();
	/**
	 * 根据id修改数据
	 * @param id
	 * @return
	 */
	Document findDatasById(String id,String collectionName);
	/**
	 * 修改数据
	 * @param doc
	 * @param collectionName
	 * @return
	 */
	Document updateDocument(Document doc, String collectionName);
    /**
     * 删除数据对象
     * @param ids 数据对象Id
     * @param collectionName 表名
     */
    void removeDocument(List<String> ids,String collectionName);

}
