package com.enovell.yunwei.km_micor_service.service.securityManage.accidentQuery;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.AccidentQueryFindDto;

public interface AccidentQueryService {
	
	Document findDocumentById(String id,String collectionName);
	/**
	 * 分页查询
	 * @return
	 */
	long findAllDocumentCount(String collectionName,String startDate, String endDate,String obligationDepart ,String accidentLineName);
	List<Document> findAllDocument(String collectionName,String startDate, String endDate,String obligationDepart  ,String accidentLineName, int start, int limit);
	/**
	 * 数据导出
	 */
	Workbook exportExcel(AccidentQueryFindDto dto)throws IOException;
}