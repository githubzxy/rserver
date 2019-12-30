package com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("pointInnerSecondMaintainQueryService")
public class PointInnerSecondMaintainQueryServiceImpl implements PointInnerSecondMaintainQueryService {
	@Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
		@Autowired
	 private NamedParameterJdbcTemplate template;
	@Override
	public long findAllDocumentCount(String collectionName, String constructionProject, String flowState, String startDate, String endDate,String submitOrgName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(constructionProject,flowState,startDate,endDate,submitOrgName);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String constructionProject, String flowState, String startDate, String endDate,String submitOrgName, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(constructionProject,flowState,startDate,endDate,submitOrgName);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter(String constructionProject, String flowState, String startDate, String endDate,String submitOrgName) {
		Bson filters=Filters.eq("status",1);
		if(StringUtils.isNotBlank(constructionProject)) {
			filters=Filters.and(filters,Filters.regex("constructionProject",constructionProject));
		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("createDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("createDate", endDate));
		}
		if(StringUtils.isNotBlank(flowState)) {
			filters=Filters.and(filters,Filters.eq("flowState",flowState));
		}
		if(StringUtils.isNotBlank(submitOrgName)) {
			filters=Filters.and(filters,Filters.eq("submitOrgName",submitOrgName));
		}
		return filters;
	}

	@Override
		public List<String> getAllworkArea() {
			String sql="select t.org_name_ from cfg_base_organization t where t.type_ =:type and  t.delete_state_ = 1 order by t.type_ ";
			Map<String,Object> paramMap = new HashMap<String,Object>();
	        paramMap.put("type", 1503);
			List<String> workShopList=template.queryForList(sql,paramMap, String.class);
			return workShopList;
		}
		
	
}