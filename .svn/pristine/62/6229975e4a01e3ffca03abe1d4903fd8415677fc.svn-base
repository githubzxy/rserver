package com.enovell.yunwei.km_micor_service.service.integratedManage.attendanceManage;

import java.util.List;

import org.bson.Document;

public interface AttendanceManageService {
	
	long getUsersCountByOrgId(String orgId,String collectionName,String attendanceManage,String attendanceUserChange,String date);
	List<Document> getUsersByOrgId(String orgId,String collectionName,String attendanceManage,String attendanceUserChange,String date,int start, int limit);
	
	Document findDocumentById(String docId,String date,String collectionName);
	
	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String name, String startDate, String endDate,String auditStatus,String userId);
	List<Document> findAllDocument(String collectionName,String name, String startDate, String endDate, String auditStatus,String userId,int start, int limit);
	/**
	 * 新增申请信息
	 */
	public Document addDocument(Document doc, String collectionName);
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
	 * 根据部门名称查询出该部门的所有人员
	 * @param department
	 * @param userInfoManage
	 * @return
	 */
	List<Document> getUserByDepartmentName(String department, String orgId, String userInfoManage);
	
	/**
	 * 根据主键获取一条数据
	 * @param id
	 * @param collectionName
	 * @return
	 */
//	Document getDataById(String id, String collectionName);
	
	/**
	 * 根据docId 获取一条数据
	 * @param id
	 * @param collectionName
	 * @return
	 */
	Document getDataByDocId(String docId, String collectionName);
	
	
//	Document removeToBackDoc(Document doc, String collectionName);
	
	
	Document removeBackDoc(Document doc, String collectionName);
}
