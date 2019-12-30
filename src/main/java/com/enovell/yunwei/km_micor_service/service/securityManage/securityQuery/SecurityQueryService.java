package com.enovell.yunwei.km_micor_service.service.securityManage.securityQuery;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.SecurityQueryFindDto;

public interface SecurityQueryService {
	
	Document findDocumentById(String id,String collectionName);
	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String startDate, String endDate,String obligationDepart ,String securityLineName);
	List<Document> findAllDocument(String collectionName,String startDate, String endDate,String obligationDepart  ,String securityLineName, int start, int limit);
	/**
	 * 数据导出
	 */
	Workbook exportExcel(SecurityQueryFindDto dto)throws IOException;
}