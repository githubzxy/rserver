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
@Service("taskCheckService")
public class TaskCheckServiceImpl implements  TaskCheckService{
	
	@Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
    @Value("${user.uploadPath}")
    private String uploadPath;

	@Override
	public long findAllDocumentCount(String collectionName, String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate, String endUploadDate) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate, String endUploadDate, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, currentDay, workOrderName, systemType, flowState, startUploadDate,endUploadDate);
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
	public Document updateDocument(Document doc, String collectionName, String IssueTaskOfWorkShop, String flowState) {
		  try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
			  
			  if("6".equals(flowState)){
				  addIssueData(doc,IssueTaskOfWorkShop);
			  }
			  
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            return md.getCollection(collectionName)
	                    .findOneAndUpdate(
	                            Filters.eq("_id", new ObjectId(doc.getString("docId"))),new Document("$set",doc)
	                    );
	        }
	}
	
	private void addIssueData(Document doc,String IssueTaskOfWorkShop){
		String executiveStaffId = doc.getString("executiveStaffId");
		String executiveStaff = doc.getString("executiveStaff");
		String[] executiveStaffIds = executiveStaffId.split(",");
		String[] executiveStaffs = executiveStaff.split(",");
		for(int i=0 ; i<executiveStaffIds.length ; i++){
			Document document = new Document();
			document.put("docId", doc.getString("docId"));
			document.put("taskName", doc.getString("taskName"));
			document.put("systemType", doc.getString("systemType"));
			document.put("issueDate", doc.getString("checkTime"));
			document.put("flowState", doc.getString("flowState"));
			document.put("executiveOrgId", executiveStaffIds[i]);
			document.put("executiveOrgName", executiveStaffs[i]);
			addDocument(document,IssueTaskOfWorkShop);
		}
	}
	
	private void addDocument(Document doc,String IssueTaskOfWorkShop) {
		  try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            doc.append("status",1);
	            md.getCollection(IssueTaskOfWorkShop).insertOne(doc);
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
	 * @return 查询条件 Bson
	 */
    private Bson getFilter(String userId, String currentDay, String workOrderName, String systemType, String flowState, String startUploadDate, String endUploadDate) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workOrderName)){
        	filter = Filters.and(filter,Filters.regex("taskName",workOrderName));
        }
        if(StringUtils.isNotBlank(systemType)){
        	filter = Filters.and(filter,Filters.eq("systemType",systemType));
        }
        if(StringUtils.isNotBlank(flowState)){
        	filter = Filters.and(filter,Filters.eq("flowState",flowState));
        }else{
        	filter = Filters.and(filter,Filters.nin("flowState","0","1","2","3"));
//          filter = Filters.and(filter,Filters.in("flowState","3"));
        }
        if(StringUtils.isNotBlank(startUploadDate)){
            filter = Filters.and(filter,Filters.gte("createDate",startUploadDate));
        }
        if(StringUtils.isNotBlank(endUploadDate)){
            filter = Filters.and(filter,Filters.lte("createDate",endUploadDate));
        }
//        filter = Filters.and(filter,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//        filter = Filters.and(filter,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
//        filter = Filters.and(filter,Filters.eq("createUserId",userId));//表示查询的是当前登陆用户的数据
        return filter;
    }
}