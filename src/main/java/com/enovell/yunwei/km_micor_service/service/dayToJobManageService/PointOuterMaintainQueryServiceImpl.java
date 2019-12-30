package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("pointOuterMaintainQueryService")
public class PointOuterMaintainQueryServiceImpl implements  PointOuterMaintainQueryService{
	 @Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
	    @Value("${user.uploadPath}")
	    private String uploadPath;
	    @Autowired
		private NamedParameterJdbcTemplate template;

	@Override
	public long findAllDocumentCount(String collectionName, String userId, String orgId, String unitName, String unitId, String unitType, String lineName, String lineType, String flowState, String currentDay, String startUploadDate,
			String endUploadDate) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, unitName, unitId, unitType, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String userId, String orgId, String unitName, String unitId, String unitType, String lineName, String lineType, String flowState, String currentDay,
			String startUploadDate, String endUploadDate, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, unitName, unitId, unitType, lineName, lineType, flowState, currentDay, startUploadDate,endUploadDate);
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
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @return 查询条件
     */
    private Bson getFilter(String userId, String orgId, String unitName, String unitId, String unitType, String lineName, String lineType, String flowState, String currentDay, String startUploadDate, String endUploadDate) {
        Bson filter = Filters.eq("status",1);
        if("1503".equals(unitType)){//选择是工区
    		filter = Filters.and(filter,Filters.eq("unit",unitName));
    	}
    	if("1502".equals(unitType)){//选择是车间
    		filter = Filters.and(filter,Filters.eq("parentId",unitId));
    	}
        
        if(StringUtils.isNotBlank(lineName)){
        	filter = Filters.and(filter,Filters.regex("lineName",lineName));
        }
        if(StringUtils.isNotBlank(lineType)){
        	filter = Filters.and(filter,Filters.eq("lineType",lineType));
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
        return filter;
    }
}