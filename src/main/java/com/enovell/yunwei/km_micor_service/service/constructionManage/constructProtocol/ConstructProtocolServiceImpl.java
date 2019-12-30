package com.enovell.yunwei.km_micor_service.service.constructionManage.constructProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@Service("constructProtocolService")
public class ConstructProtocolServiceImpl implements ConstructProtocolService {
	@Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
    @Autowired
	private NamedParameterJdbcTemplate template;
	@Override
	public long findAllDocumentCount(String collectionName, String proName, String proCompany, String line,
			String workShop, String beginDateStart, String beginDateEnd, String proPlace, String overDateStart,
			String overDateEnd) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(proName,proCompany,line,workShop,beginDateStart,beginDateEnd,proPlace,overDateStart,overDateEnd);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String proName, String proCompany, String line,
			String workShop, String beginDateStart, String beginDateEnd, String proPlace, String overDateStart,
			String overDateEnd, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(proName,proCompany,line,workShop,beginDateStart,beginDateEnd,proPlace,overDateStart,overDateEnd);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.append("userId",d.get("userId"));
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter(String proName,String proCompany,String line,String workShop,String beginDateStart,String beginDateEnd,String proPlace,String overDateStart,String overDateEnd) {
		Bson filters=Filters.eq("status",1);
		if(StringUtils.isNotBlank(proName)) {
			filters=Filters.and(filters,Filters.regex("proName",proName));
		}
		if(StringUtils.isNotBlank(proCompany)) {
			filters=Filters.and(filters,Filters.regex("proCompany",proCompany));
		}
		if(StringUtils.isNotBlank(line)) {
			filters=Filters.and(filters,Filters.regex("line",line));
		}
		if(StringUtils.isNotBlank(workShop)) {
			filters=Filters.and(filters,Filters.regex("workShop",workShop));
		}
		if(StringUtils.isNotBlank(proPlace)) {
			filters=Filters.and(filters,Filters.regex("proPlace",proPlace));
		}
		if(StringUtils.isNotBlank(beginDateStart)){
			filters=Filters.and(filters,Filters.gte("beginDate", beginDateStart));
		}
		if(StringUtils.isNotBlank(beginDateEnd)){
			filters=Filters.and(filters,Filters.lte("beginDate", beginDateEnd));
		}
		if(StringUtils.isNotBlank(overDateStart)) {
			filters=Filters.and(filters,Filters.gte("overDate",overDateStart));
		}
		if(StringUtils.isNotBlank(overDateEnd)) {
			filters=Filters.and(filters,Filters.lte("overDate",overDateEnd));
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
	public List<String> getWorkShops() {
		String sql="select t.org_name_ from cfg_base_organization t where t.type_ = :type";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", 1502);
		List<String> workShopList=template.queryForList(sql,paramMap, String.class);
		return workShopList;
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

}
