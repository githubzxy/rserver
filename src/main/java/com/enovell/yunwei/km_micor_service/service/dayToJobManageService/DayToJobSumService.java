package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

public interface DayToJobSumService {
	
	/**
	 * 所有信息汇总
	 */
	Map<String,List<Document>> allSum( String currentDay, String userId);
	/**
	 * 汇总信息保存
	 */
	void saveSumInfo(Map<String,List<Document>> map,String userId,String userName);
	List<ObjectId> getSumId(Map<String, List<Document>> map,String userId,String userName,String currentDay,String key);
	void updateSumData(List<ObjectId> objIds,String userId,String userName,String currentDay,String collectionName);

}
