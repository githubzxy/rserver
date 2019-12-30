package com.enovell.yunwei.km_micor_service.service.technical.technicalDocumentManagement;

import java.util.List;

import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.UploadFileDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoDocumentDto;

public interface TechnicalDocumentManageService {

	/**
	 * 
	 * addTechnicalInfo 新增技术资料
	 * 
	 * @param technicalInfo
	 * @return
	 */
	public TechnicalInfoDocumentDto addTechnicalInfo(TechnicalInfoDocumentDto technicalInfo,UploadFileDto[] dtos);

	/**
	 * 
	 * updateTechnicalInfo 修改技术资料
	 * @param technicalInfo
	 * @return
	 */
	public TechnicalInfoDocumentDto updateTechnicalInfo(TechnicalInfoDocumentDto technicalInfo,UploadFileDto[] dtos);

	/**
	 * 
	 * getTechnicalInfoById 根据id获取技术资料
	 * @param id
	 * @return
	 */
	public TechnicalInfoDocumentDto getTechnicalInfoById(String id);

	/**
	 * 批量删除技术资料
	 */
	public void deleteTechnicalInfos(String ids);


	/**
	 * 
	 * 查询技术资料
	 * 
	 * @param technicalInfoDTO
	 * @param first
	 * @param max
	 * @return
	 */
	public List<TechnicalInfoDocumentDto> getAllTechnicalInfoInfos(
			TechnicalInfoDocumentDto completionInfoDTO, int first, int max);

	/**
	 * 获取技术资料记录数
	 * 
	 * @param technicalInfoDTO
	 * @return
	 */
	public int getTechnicalInfoInfoCount(TechnicalInfoDocumentDto technicalInfoDTO);

	public Document addDocument(Document document, String collectionName);

	public Document findDatasById(String id, String collectionName);

	public Long findAllDocumentCount(String collectionName,String name,String machineRoom,String  railLine, String createStartDate, String createEndDate,
			String currentDay, String userId);

	public List<Document> findAllDocument(String collectionName,String name, String machineRoom,String  railLine,String createStartDate, String createEndDate,
			int start, int limit, String currentDay, String userId);

	public Document updateDocument(Document document, String collectionName);

	public List<String> getMachineRooms(String railLine);
	
	
}
