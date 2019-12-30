package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.util.List;
import java.util.Map;

import org.bson.Document;

/**
 * 日交班信息模块service
 * @author yangli
 *
 */
public interface DayToJobService {
	/**
	 * 日交班信息分页查询
	 * @return
	 */
	List<Document> quryAllDatas(String collectionName, String startDate, String endDate, int start, int limit);
	long findAllDocumentCount(String collectionName, String startDate, String endDate);
	/**
	 * 新增日交班信息
	 */
	public Document addDocument(Document doc, String collectionName);
	
	/**
     * 根据id查询单条数据
     * @param id 数据id
     * @param collectionName 表名
     * @return 单条数据对象
     */
    Document findDocumentById(String id,String collectionName);
    
    /**
     * 根据当前系统时间前一天获取记录
     * @param systemBeforeDate
     * @param collectionName
     * @return
     */
    Document findDocumentByBeforeDate(String systemBeforeDate,String collectionName);
    
    /**
     * 更新数据对象
     * @param doc 数据对象
     * @param collectionName 表名
     * @return 数据对象
     */
    Document updateDocument(Document doc,String collectionName);
    
    /**
     * 
     * @param userId 日交班模块登陆用户的ID
     * @return 保存需要打开文件的路径
     */
    String createGenerateSumTable(String collectionName,String docId,String userName, String userId,String leader,String cadre,String date,String dispatch);

}
