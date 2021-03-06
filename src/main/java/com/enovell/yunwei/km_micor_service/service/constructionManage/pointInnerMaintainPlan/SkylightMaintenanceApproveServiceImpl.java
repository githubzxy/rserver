package com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan;

import java.util.ArrayList;
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
@Service("skylightMaintenanceApproveService")
public class SkylightMaintenanceApproveServiceImpl implements  SkylightMaintenanceApproveService{
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
	public Document addDocument(Document doc, String collectionName) {
		  try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            doc.append("status",1);
	            doc.append("busiId",UUIDUtils.getUUID());
	            md.getCollection(collectionName).insertOne(doc);
	        }
	        return doc;
	}

	@Override
	public long findAllDocumentCount(String collectionName, String userId, String orgId, String currentDay, String project,String type,String startUploadDate,
			String endUploadDate,String flowState,String orgSelectName) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, currentDay,project,type,startUploadDate,endUploadDate,flowState,orgSelectName);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String userId, String orgId, String currentDay,
			String project,String type,String startUploadDate, String endUploadDate,String flowState,String orgSelectName,int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, currentDay,project,type,startUploadDate,endUploadDate,flowState,orgSelectName);
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

	@Override
	public Document updateDocument(Document doc, String collectionName) {
		  try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            return md.getCollection(collectionName)
	                    .findOneAndUpdate(
	                            Filters.eq("_id", new ObjectId(doc.getString("docId"))),new Document("$set",doc)
	                    );
	        }
	}

	@Override
	public void removeDocument(List<String> ids, String collectionName) {
		List<ObjectId> objIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
        Document query = new Document("_id",new Document("$in",objIds));
        Document update= new Document("$set",new Document("status",0));
        try(MongoClient mc = new MongoClient(mongoHost, mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            md.getCollection(collectionName).updateMany(query,update);
        }
		
	}
	 /**
     * 分页查询条件封装
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @return 查询条件
     */
	private Bson getFilter(String userId, String orgId, String currentDay,String project,String type,String startUploadDate, String endUploadDate,
    		String flowState,
    		String orgSelectName
    		) {
		Bson filter = Filters.eq("status",1);
		if(StringUtils.isNotBlank(project)){
            filter = Filters.and(filter,Filters.regex("project",project));
        }
        if(StringUtils.isNotBlank(type)){
            filter = Filters.and(filter,Filters.eq("type",type));
        }
        if(StringUtils.isNotBlank(startUploadDate)){
            filter = Filters.and(filter,Filters.gte("createDate",startUploadDate));
        }
        if(StringUtils.isNotBlank(endUploadDate)){
            filter = Filters.and(filter,Filters.lte("createDate",endUploadDate));
        }
        if(StringUtils.isNotBlank(type)){
            filter = Filters.and(filter,Filters.eq("type",type));
        }
        if(StringUtils.isNotBlank(orgSelectName)){
            filter = Filters.and(filter,Filters.eq("orgSelectName",orgSelectName));
        }
//        filter = Filters.and(filter,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//        filter = Filters.and(filter,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
//        filter = Filters.and(filter,Filters.eq("parentId",orgId));//表示查询的是当前登陆用户所属组织机构下的所有机构的数据
        filter = Filters.and(filter,Filters.in("flowState","5"));//表示可以查询到除了草稿状态以外的数据
        return filter;
    }
    
}