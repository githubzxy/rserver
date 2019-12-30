package com.enovell.yunwei.km_micor_service.service.productionManage.JobRecord;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.JobRecordDto;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：JobRecordQueryService   
 * 类描述:  工作日志
 * 创建人：zhouxingyu
 * 创建时间：2019年7月25日 下午5:50:34
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月25日 下午5:50:34   
 *
 */
public interface JobRecordQueryService {
	/**
	 * 
	 * getExcelInfo 读取excel信息
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
	 * deleteAllDocument 按条件删除文档
	 * @param collectionName
	 * @param currentDay
	 * @param orgId
	 */
	void deleteAllDocument(String collectionName, String currentDay, String orgId);
	/**
	 * 
	 * addDocument 添加一条文档
	 * @param document
	 * @param collectionName
	 * @return
	 */
	Document addDocument(Document document, String collectionName);
	/**
	 * 
	 * checkData 检查数据
	 * @param result
	 * @param startDate
	 * @param endDate
	 * @param project
	 * @return
	 */
	boolean checkData(GridDto<Document> result,String startDate,String endDate, String project);
	/**
	 * 
	 * findAllDocumentCount 按条件查询文档总数
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
	 * findAllDocument 按条件查询数据
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
	 * findDocumentById 按id查询文档
	 * @param id
	 * @param collectionName
	 * @return
	 */
	Document findDocumentById(String id, String collectionName);
	/**
	 * 
	 * modifyDocument 修改文档数据
	 * @param document
	 * @param collectionName
	 * @return
	 */
	Document modifyDocument(Document document, String collectionName);
	/**
	 * 
	 * exportExcel 导出数据到工作日志表格中
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
	 * getShopAndDepart 获取车间工区
	 * @param pid
	 * @param curId
	 * @return
	 */
	List<Map<String, Object>> getShopAndDepart(String pid, String curId);


}
