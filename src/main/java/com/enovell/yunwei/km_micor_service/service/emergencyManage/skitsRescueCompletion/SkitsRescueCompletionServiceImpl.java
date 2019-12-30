package com.enovell.yunwei.km_micor_service.service.emergencyManage.skitsRescueCompletion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.UploadFileDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoDocumentDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sun.javafx.collections.MappingChange.Map;



@Service(value = "skitsRescueCompletionService")
public class SkitsRescueCompletionServiceImpl implements SkitsRescueCompletionService{

	 @Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	

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
	public Long findAllDocumentCount(String collectionName,String line,String orgDepart,String joinDepart ,String startDate, String endDate, 
			String currentDay, String userId) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(line,orgDepart,joinDepart,startDate,endDate,currentDay,userId);
            return  md.getCollection(collectionName).count(filter);
        }
	}
	/**
	 * 
	 * getFilter 设置mongo的查询拦截条件
	 * @param startDate
	 * @param endDate
	 * @param project
	 * @param currentDay
	 * @param userId
	 * @return
	 */
	private Bson getFilter(String line,String orgDepart,String joinDepart,String startDate,String endDate,String currentDay,String userId) {
		Bson filters=Filters.eq("status",1);
		if (StringUtils.isNotBlank(line)) {
			filters = Filters.and(filters,Filters.eq("line", line));
		}
		if (StringUtils.isNotBlank(orgDepart)) {
			filters = Filters.and(filters,Filters.eq("orgDepart", orgDepart));
		}
		if (StringUtils.isNotBlank(joinDepart)) {
			filters = Filters.and(filters,Filters.eq("joinDepart", joinDepart));
		}
		if(StringUtils.isNotBlank(startDate)){
			filters = Filters.and(filters,Filters.gte("date",startDate));
        }
        if(StringUtils.isNotBlank(endDate)){
        	filters = Filters.and(filters,Filters.lte("date",endDate));
        }
		
		return filters;
	}

	@Override
	public List<Document> findAllDocument(String collectionName,String line,String orgDepart,String joinDepart ,String createStartDate, String createEndDate,
			int start, int limit, String currentDay, String userId) {
			List<Document> results=new ArrayList<>();
			try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
				MongoDatabase md=mc.getDatabase(mongoDatabase);
				Bson filter=getFilter(line,orgDepart,joinDepart,createStartDate,createEndDate,currentDay,userId);
				FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("date", -1));
				findIterable.forEach((Block<? super Document>) results::add);
				 results.stream().forEach(d-> {
			            d.append("docId",d.getObjectId("_id").toHexString());
			            d.remove("_id");
			        });
			}
			return results;
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
