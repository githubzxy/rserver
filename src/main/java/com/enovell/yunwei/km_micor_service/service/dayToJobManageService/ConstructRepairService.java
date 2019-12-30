package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolDTO;
import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolExportDTO;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ConstructRepairExportDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ConstructRepairFindDto;

public interface ConstructRepairService {
	/**
	 * 分页查询
	 * @return
	 */
	List<Document> quryAllDatas(String collectionName, String startDate, String endDate,String planNum,String project, int start, int limit,String currentDay,String userId, String constructContent);
	long findAllDocumentCount(String collectionName, String startDate, String endDate,String planNum,String project,String currentDay,String userId, String constructContent);
	/**
	 * 新增信息
	 */
	public Document addDocument(Document doc, String collectionName);
	/**
	 * 根据id修改数据
	 * @param id
	 * @return
	 */
	Document findDatasById(String id,String collectionName);
	/**
	 * 修改
	 * @param doc
	 * @param collectionName
	 * @return
	 */
	Document updateDocument(Document doc, String collectionName);
	
	Map<String,List<ConstructRepairExportDto>> getAllFile(ConstructRepairFindDto dto,String currentDay);
	/**
	 * 查询要导出的数据
	 * @param dto
	 * @return
	 */
//	List<ConstructRepairExportDto> queryExportDataList(ConstructRepairFindDto dto);
	/**
	 * 数据导出
	 */
	Workbook exportExcel(ConstructRepairFindDto dto,String currentDay)throws IOException;
	/**
	 * 查询数据导出
	 */
	Workbook exportQueryExcel(ConstructRepairFindDto dto)throws IOException;
}
