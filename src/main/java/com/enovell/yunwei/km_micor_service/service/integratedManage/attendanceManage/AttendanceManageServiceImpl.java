package com.enovell.yunwei.km_micor_service.service.integratedManage.attendanceManage;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import oracle.net.aso.d;

@Service("attendanceManageService")
public class AttendanceManageServiceImpl implements AttendanceManageService {
	 @Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	 @Autowired
	 private NamedParameterJdbcTemplate template;


	@Override
	public List<Document> findAllDocument(String collectionName, String name, String startDate, String endDate,
			String auditStatus, String userId, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(name,startDate,endDate,userId,auditStatus);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}


	@Override
	public long findAllDocumentCount(String collectionName, String name, String startDate, String endDate,
			String auditStatus, String userId) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(name,startDate,endDate,userId,auditStatus);
            return  md.getCollection(collectionName).count(filter);
        }
	}
	
	private Bson getFilter(String name,String startDate, String endDate,String userId,String auditStatus) {
		Bson filters=Filters.eq("status",1);
		if(StringUtils.isNotBlank(userId)) {
			filters=Filters.and(filters,Filters.eq("userId",userId));
		}
		if(StringUtils.isNotBlank(name)) {
			filters=Filters.and(filters,Filters.regex("name",name));
		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("createDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("createDate", endDate));
		}
		if(StringUtils.isNotBlank(auditStatus)) {
			filters=Filters.and(filters,Filters.eq("auditStatus",auditStatus));
		}
		return filters;
	}


	@Override
	public Document addDocument(Document doc, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            doc.append("status",1);
            md.getCollection(collectionName).insertOne(doc);
        }
        return doc;
	}


	@Override
	public Document findDatasById(String id, String collectionName) {
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
                            Filters.eq("_id", new ObjectId(doc.getString("id"))),new Document("$set",doc)
                    );
        }
	}


	@Override
	public List<Document> getUsersByOrgId(String orgId, String collectionName,String attendanceManage,String attendanceUserChange,String date,int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(orgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",orgId));
			}
			FindIterable<Document> findIterable0 = md.getCollection(attendanceUserChange).find(new Document("toOrgId", orgId).append("status",1));
			findIterable0.forEach((Block<? super Document>) results::add);
//			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filters).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filters).sort(new Document("creatDateStr", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
				 	if(StringUtils.isBlank(d.getString("docId"))){
				 		d.append("docId",d.getObjectId("_id").toHexString());
				 		d.remove("_id");
				 	}
					FindIterable<Document> findIterable2 = md.getCollection(attendanceManage).find(new Document("date", date).append("docId", d.getString("docId")).append("status",1));
		            Document doc = findIterable2.first();
		            if(doc!=null){
		            	d.append("morning", doc.getString("morning"));
		            	d.append("noon", doc.getString("noon"));
		            	d.append("night", doc.getString("night"));
//		            	d.append("shifts", doc.getString("shifts"));
		            	d.append("daily", doc.getString("daily"));
		            	d.append("turn", doc.getString("turn"));
		            }
			 });
			 results.stream().forEach(d-> {
					FindIterable<Document> findIterable3 = md.getCollection(attendanceUserChange).find(new Document("docId", d.getString("docId")).append("status",1));
		            Document doc = findIterable3.first();
		            if(doc!=null){
		            	d.append("shifts", "外派"+","+doc.getString("toOrgName"));
		            }else{
		            	d.append("shifts", "");
		            }
			 });
			 results.stream().forEach(d-> {
					FindIterable<Document> findIterable4 = md.getCollection(attendanceUserChange).find(new Document("toOrgId", d.get("toOrgId")).append("status",1));
		            Document doc = findIterable4.first();
		            if(doc!=null){
		            	d.append("shifts", doc.getString("department")+","+"人员");
		            }
			 });
		}
		return results;
	}


	@Override
	public long getUsersCountByOrgId(String orgId, String collectionName,String attendanceManage,String attendanceUserChange,String date) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(orgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",orgId));
			}
            return  md.getCollection(collectionName).count(filters);
        }
	}


	@Override
	public Document findDocumentById(String docId, String date, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("docId", docId).append("date", date).append("status",1));
            Document doc = findIterable.first();
            if(doc!=null){
            	doc.append("id",doc.getObjectId("_id").toHexString());
            	doc.remove("_id");
            	return doc;
            }else{
            	return null;
            }
        }
	}


	@Override
	public List<Document> getUserByDepartmentName(String department, String orgId, String userInfoManage) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(orgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",orgId));
			}
//			FindIterable<Document> findIterable0 = md.getCollection("attendanceUserChange").find(new Document("toOrgId", orgId).append("status",1));
//			findIterable0.forEach((Block<? super Document>) results::add);
			FindIterable<Document> findIterable = md.getCollection(userInfoManage).find(filters).sort(new Document("creatDateStr", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
					FindIterable<Document> findIterable2 = md.getCollection("attendanceUserChange").find(new Document("docId", d.getString("docId")).append("status",1));
		            Document doc = findIterable2.first();
		            if(doc!=null){
		            	d.append("noShow", "1");
		            }else{
		            	d.append("noShow", "0");
		            }
			 });
		}
		return results;
	}


//	@Override
//	public Document getDataById(String id, String collectionName) {
//		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
//            MongoDatabase md = mc.getDatabase(mongoDatabase);
//            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("_id", new ObjectId(id)).append("status",1));
//            Document doc = findIterable.first();
//            doc.append("id",doc.getObjectId("_id").toHexString());
//            doc.remove("_id");
//            return doc;
//        }
//	}


	@Override
	public Document getDataByDocId(String docId, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("docId", docId).append("status",1));
            Document doc = findIterable.first();
            return doc;
        }
	}


//	@Override
//	public Document removeToBackDoc(Document doc, String collectionName) {
//		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
//            MongoDatabase md = mc.getDatabase(mongoDatabase);
//            return md.getCollection(collectionName)
//                    .findOneAndUpdate(
//                            Filters.eq("_id", new ObjectId(doc.getString("id"))),new Document("$set",doc)
//                    );
//        }
//	}


	@Override
	public Document removeBackDoc(Document doc, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filters=Filters.eq("status",1);
			filters=Filters.and(filters,Filters.eq("docId", doc.getString("docId")));
            return md.getCollection(collectionName)
                    .findOneAndUpdate(
                    		filters,new Document("$set",doc)
                    );
        }
	}

}