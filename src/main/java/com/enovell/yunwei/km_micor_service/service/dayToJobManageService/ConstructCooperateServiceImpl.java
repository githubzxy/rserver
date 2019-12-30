package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Service("constructCooperateService")
public class ConstructCooperateServiceImpl implements ConstructCooperateService {
	 @Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	 @Autowired
	 private NamedParameterJdbcTemplate template;

	@Override
	public List<Document> quryAllDatas(String collectionName, String startDate, String endDate, int start, int limit,String currentDay,String userId) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(startDate,endDate,currentDay,userId);
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
	public long findAllDocumentCount(String collectionName, String startDate, String endDate,String currentDay,String userId) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(startDate,endDate,currentDay,userId);
            return  md.getCollection(collectionName).count(filter);
        }
	}
	private Bson getFilter(String startDate, String endDate,String currentDay,String userId) {
		Bson filters=Filters.eq("status",1);
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("date", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("date", endDate));
		}
//		 filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//	     filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
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
	public List<Map<String, Object>> getWorkShops() {
		String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.type_ = :type";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", "1502");
		List<Map<String, Object>> workShopList=template.query(sql,paramMap,new CCOrganizationMapper());
		return workShopList;
	}

	@Override
	public List<Map<String, Object>> getDeparts(String workShopName) {
		String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.parent_id_=(select t.org_id_ from cfg_base_organization t where t.org_name_=:workShopName)";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("workShopName", workShopName);
        List<Map<String, Object>> departList=template.query(sql, paramMap,new CCOrganizationMapper());
		return departList;
	}

	@Override
	public Document findDatasById(String id,String collectionName) {
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
	public List<String> getcooperManById(String orgName) {
		String sql="select t.user_name_ from cfg_safe_user t where t.organization_id_ = (select a.org_id_ from cfg_base_organization a where a.org_name_ = :orgName)";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("orgName", orgName);
		List<String> cooperManList=template.queryForList(sql, paramMap, String.class);
		return cooperManList;
	}

	@Override
	public List<String> getLineData() {
		String sql="select t.name_ from res_base_rail_line t";
		List<String> workShopList=template.queryForList(sql,new HashMap<>(), String.class);
		return workShopList;
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
class CCOrganizationMapper implements RowMapper<Map<String, Object>> {

	public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("orgId", rs.getString("ORG_ID_"));
		p.put("orgName", rs.getString("ORG_NAME_"));
		return p;
	}

}
