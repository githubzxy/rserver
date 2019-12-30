package com.enovell.yunwei.km_micor_service.service.emergencyManage.skitsRescueCompletion;

import java.util.List;

import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.UploadFileDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoDocumentDto;

public interface SkitsRescueCompletionService {

	public Document addDocument(Document document, String collectionName);

	
	public Document findDatasById(String id, String collectionName);

	
	public Long findAllDocumentCount(String collectionName,String line,String orgDepart,String joinDepart,String createStartDate, String createEndDate,
			String currentDay, String userId);

	
	public List<Document> findAllDocument(String collectionName,String line,String orgDepart,String joinDepart ,String createStartDate, String createEndDate,
			int start, int limit, String currentDay, String userId);

	
	public Document updateDocument(Document document, String collectionName);

	
	
}
