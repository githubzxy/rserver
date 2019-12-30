package com.enovell.yunwei.km_micor_service.service.userInfoManage;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("userInfoManageService")
public class UserInfoManageServiceImpl implements UserInfoManageService{
		@Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
		@Autowired
		private NamedParameterJdbcTemplate template;
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
    public List<Document> findAllDocument(String collectionName,  String userId,String loginOrgId, String loginOrgType,String workshop,String teamGroup,String selectOrgType,String phoneNum, String staffName, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, loginOrgId, loginOrgType,workshop,teamGroup,selectOrgType,phoneNum,  staffName);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.append("orgIdAndorgType",d.get("orgId")+","+d.get("orgType"));
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
    public long findAllDocumentCount(String collectionName,  String userId,String loginOrgId,String loginOrgType, String workshop,String teamGroup,String selectOrgType,String phoneNum, String staffName ) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId,loginOrgId, loginOrgType, workshop,teamGroup,selectOrgType, phoneNum, staffName);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}
	/**
     * 分页查询条件封装
     * @param name 查询名称
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @param orgId 组织机构id
     * @return 查询条件
     */
    private Bson getFilter(String userId ,String loginOrgId, String loginOrgType,String workshop,String teamGroup,String selectOrgType,String phoneNum, String staffName) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workshop)){
            filter = Filters.and(filter,Filters.eq("workshop",workshop));
        }
        if(StringUtils.isNotBlank(teamGroup)){
            filter = Filters.and(filter,Filters.regex("teamGroup",teamGroup));
        }else{
        	if(loginOrgType.equals("1502")&&StringUtils.isNotBlank(workshop)){//车间用户
        		filter = Filters.and(filter,Filters.eq("teamGroup",""));
        	}
        }
        if(StringUtils.isNotBlank(selectOrgType)){
        	filter = Filters.and(filter,Filters.regex("orgType",selectOrgType));
        }
        if(StringUtils.isNotBlank(phoneNum)){
            filter = Filters.and(filter,Filters.regex("phoneNum",phoneNum));
        }
        if(StringUtils.isNotBlank(staffName)){
            filter = Filters.and(filter,Filters.regex("staffName",staffName));
        }
        List<String> orgIds = new ArrayList<>();
        List<Map<String, Object>> orgIdList = getChildIdByOrgId(loginOrgId);
        for (Map<String, Object> map : orgIdList) {
        	orgIds.add((String) map.get("orgId"));
	    }
        //车间只能看自己和所管辖工区，工区只能看自己部门
		if (loginOrgType.equals("1502") || loginOrgType.equals("1503")) {
			orgIds.add(loginOrgId);
			filter = Filters.and(filter, Filters.in("orgId", orgIds));
		}
        return filter;
    }

	@Override
	public String getOrgIdByWorkshop(String workshop) {
		String sql="(select t.org_id_ from cfg_base_organization t where t.org_name_=:workshop and t.delete_state_=1)";
		 Map<String,String> param = new HashMap<>();
	        param.put("workshop",workshop);
	        try {
	            return template.queryForObject(sql, param, String.class);
	        }catch (EmptyResultDataAccessException e){
	            return null;
	        }
	
	}

	@Override
	public String getOrgTypeByWorkshop(String workshop) {
		String sql="(select t.type_ from cfg_base_organization t where t.org_name_=:workshop and t.delete_state_=1)";
		 Map<String,String> param = new HashMap<>();
	        param.put("workshop",workshop);
	        try {
	            return template.queryForObject(sql, param, String.class);
	        }catch (EmptyResultDataAccessException e){
	            return null;
	        }
	
	}
	@Override
	public List<Map<String, Object>> getChildIdByOrgId(String loginOrgId) {
		String sql="select t.org_id_ from cfg_base_organization t where t.parent_id_=:loginOrgId and t.delete_state_=1";
		 Map<String,Object> param = new HashMap<>();
	        param.put("loginOrgId",loginOrgId);
	        return template.query(sql, param,new ChildOrgIdMapper());
	        
	}

	@Override
	public List<String> getCadreAndShop() {
		String sql="select t.org_name_ from cfg_base_organization t where t.type_ in(:type,1501) and  t.delete_state_ = 1 order by t.type_ ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", 1502);
		List<String> workShopList=template.queryForList(sql,paramMap, String.class);
		return workShopList;
	}
	

}
class ChildOrgIdMapper implements RowMapper<Map<String, Object>> {
	
	public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("orgId",rs.getString("org_id_"));
		return p;
	}
	
}
