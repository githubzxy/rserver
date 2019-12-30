package com.enovell.yunwei.km_micor_service.service.productionManage.taskManage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("taskQueryService")
public class TaskQueryServiceImpl implements  TaskQueryService{
	
	@Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
    @Value("${user.uploadPath}")
    private String uploadPath;

	@Override
	public long findAllDocumentCount(String collectionName, String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate, String endUploadDate,String orgId) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate,orgId);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate, String endUploadDate, int start, int limit,String orgId) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, currentDay, workOrderName, systemType, flowState, startUploadDate,endUploadDate,orgId);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
	public Document findDocumentById(String id, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("_id", new ObjectId(id)).append("status",1));
            Document doc = findIterable.first();
            doc.append("docId",doc.getObjectId("_id").toHexString());
            doc.remove("_id");
            return doc;
        }
	}

	/**
	 * 分页查询条件封装
	 * @param userId 登录用户ID
	 * @param currentDay 当前日期字符串
	 * @param workOrderName 电路工单名称
	 * @param flowState 流程状态
	 * @param startUploadDate 开始时间
	 * @param endUploadDate 结束时间
	 * @param orgId 
	 * @return 查询条件 Bson
	 */
    private Bson getFilter(String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate, String endUploadDate, String orgId) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workOrderName)){
        	filter = Filters.and(filter,Filters.regex("taskName",workOrderName));
        }
        if(StringUtils.isNotBlank(systemType)){
        	filter = Filters.and(filter,Filters.eq("systemType",systemType));
        }
        if(StringUtils.isNotBlank(flowState)){
        	filter = Filters.and(filter,Filters.eq("flowState",flowState));
        }
        if(StringUtils.isNotBlank(startUploadDate)){
            filter = Filters.and(filter,Filters.gte("createDate",startUploadDate));
        }
        if(StringUtils.isNotBlank(endUploadDate)){
            filter = Filters.and(filter,Filters.lte("createDate",endUploadDate));
        }
        if (StringUtils.isNotBlank(orgId)) {
        	filter = Filters.and(filter,Filters.in("sendOrgId", orgId));
		}
        return filter;
    }
}