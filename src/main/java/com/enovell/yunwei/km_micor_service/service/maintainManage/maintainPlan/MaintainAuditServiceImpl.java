package com.enovell.yunwei.km_micor_service.service.maintainManage.maintainPlan;

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
@Service("maintainAuditService")
public class MaintainAuditServiceImpl implements MaintainAuditService {
	@Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;

	@Override
	public long findAllDocumentCount(String collectionName, String name, String startDate, String endDate,
			String auditStatus,String orgId) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(name,startDate,endDate,auditStatus,orgId);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String name, String startDate, String endDate,
			String auditStatus, int start, int limit,String orgId) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(name,startDate,endDate,auditStatus,orgId);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter(String name,String startDate, String endDate,String auditStatus,String orgId) {
		Bson filters=Filters.eq("status",1);
		
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
		}else if(auditStatus==null||auditStatus.equals("")){
			if(orgId.equals("402891b45b5fd02c015b74a20fd10033")) {//安全科
				filters=Filters.and(filters,Filters.in("auditStatus","1","4"));
			}else if(orgId.equals("402891b45b5fd02c015b74c97d740037")) {
				filters=Filters.and(filters,Filters.in("auditStatus","1","3"));
			}
		}
		return filters;
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
                            Filters.eq("_id", new ObjectId(doc.getString("docId"))),new Document("$set",doc)
                    );
        }
	}


}
