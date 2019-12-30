package com.enovell.yunwei.km_micor_service.service.constructionManage.constructionPlan;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("constructQueryService")
public class ConstructQueryServiceImpl implements ConstructQueryService {
	@Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	@Override
	public long findAllDocumentCount(String collectionName, String name, String startDate, String endDate,
			String auditStatus) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(name,startDate,endDate,auditStatus);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String name, String startDate, String endDate,
			String auditStatus, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(name,startDate,endDate,auditStatus);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter(String name,String startDate, String endDate,String auditStatus) {
		Bson filters=Filters.eq("status",1);
		if(StringUtils.isNotBlank(name)) {
			filters=Filters.and(filters,Filters.eq("name",name));
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


}
