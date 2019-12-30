package com.enovell.yunwei.km_micor_service.service.productionManage.JobRecord;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.JobRecordDto;

public interface JobRecordService {
	/**
	 * 
	 * getExcelInfo 获取excel信息
	 * @param file
	 * @return
	 */
	List<JobRecordDto> getExcelInfo(MultipartFile file);
	/**
	 * 
	 * uploadFile 上传文件
	 * @param files
	 * @return
	 */
	List<Document> uploadFile(List<MultipartFile> files);
	/**
	 * 
	 * deleteAllDocument 按组织机构 批量删除指定日期的数据
	 * @param collectionName
	 * @param currentDay
	 * @param orgId
	 */
	void deleteAllDocument(String collectionName, String currentDay, String orgId);
	/**
	 * 
	 * addDocument 添加信息
	 * @param document
	 * @param collectionName
	 * @return
	 */
	Document addDocument(Document document, String collectionName);
	/**
	 * 
	 * checkData 检查数据是否符合要求
	 * @param result
	 * @param startDate
	 * @param endDate
	 * @param project
	 * @return
	 */
	boolean checkData(GridDto<Document> result,String startDate,String endDate, String project);
	/**
	 * 
	 * findAllDocumentCount 查找文档总数
	 * @param collectionName
	 * @param workshopType
	 * @param workshopId
	 * @param workareaId
	 * @param startDate
	 * @param endDate
	 * @param project
	 * @param orgId
	 * @param orgType
	 * @return
	 */
	Long findAllDocumentCount(String collectionName,String workshopType,String workshopId,String workareaId, String startDate,String endDate, String project, String orgId, int orgType);
	/**
	 * 
	 * findAllDocument 查找文档
	 * @param collectionName
	 * @param workshopType
	 * @param workshopId
	 * @param workareaId
	 * @param startDate
	 * @param endDate
	 * @param project
	 * @param orgId
	 * @param orgType
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Document> findAllDocument(String collectionName,String workshopType,String workshopId,String workareaId, String startDate,String endDate, String project, String orgId, int orgType, int start, int limit);
	/**
	 * 
	 * findDocumentById 按id查找文档
	 * @param id
	 * @param collectionName
	 * @return
	 */
	Document findDocumentById(String id, String collectionName);
	/**
	 * 
	 * modifyDocument 按id修改文档
	 * @param document
	 * @param collectionName
	 * @return
	 */
	Document modifyDocument(Document document, String collectionName);
	/**
	 * 
	 * exportExcel 按条件统计导出工作日志数据
	 * @param date
	 * @param workShop
	 * @param workArea
	 * @param collectionName
	 * @return
	 * @throws Exception
	 */
	Workbook exportExcel(String date, String workShop, String workArea,String collectionName) throws Exception;
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
	 * 
	 * getShopAndDepart 获取车间工区数据
	 * @param pid
	 * @param curId
	 * @return
	 */
	List<Map<String, Object>> getShopAndDepart(String pid, String curId);
	List<JobRecordDto> findAllDocumentOfExport(String string, String workshopIdOfexport, String workareaIdOfexport,
			String startDateOfexport, String endDateOfexport, String projectDivOfexport);


}
