package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.util.List;
import java.util.Map;

import org.bson.Document;

/**
 * 施工配合模块service
 * @author yangli
 *
 */
public interface ConstructCooperateService {
	/**
	 * 分页查询
	 * @return
	 */
	List<Document> quryAllDatas(String collectionName, String startDate, String endDate, int start, int limit,String currentDay,String userId);
	long findAllDocumentCount(String collectionName, String startDate, String endDate,String currentDay,String userId);
	/**
	 * 新增信息
	 */
	public Document addDocument(Document doc, String collectionName);
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
	 * 根据id修改数据
	 * @param id
	 * @return
	 */
	Document findDatasById(String id,String collectionName);
	/**
	 * 根据部门名字查找配合人员数据
	 * @param id
	 * @return
	 */
	List<String> getcooperManById(String orgName);
	/**
	 * 获取先别下拉选数据
	 * @return
	 */
	List<String> getLineData();
	/**
	 * 修改数据
	 * @param doc
	 * @param collectionName
	 * @return
	 */
	Document updateDocument(Document doc, String collectionName);

}
