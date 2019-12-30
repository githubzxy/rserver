package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage.dispatch;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("dispatchService")
public class DispatchServiceImpl implements DispatchService{
	@Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
	@Autowired
	private NamedParameterJdbcTemplate template;
	@Resource(name = "trunk")
	private DispatchService trunk;
	@Resource(name = "dispatchChanger")
	private DispatchService dispatchChanger;
	@Resource(name = "dispatchStationChanger")
	private DispatchService dispatchStationChanger;
	@Resource(name = "duty")
	private DispatchService duty;
	@Resource(name = "dispatchOther")
	private DispatchService dispatchOther;
	@Resource(name = "dispatchStation")
	private DispatchService dispatchStation;
	
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
	public ResultMsg importDispatchTrunk(String publicType, MultipartFile file, String userId, String orgId) {
		return trunk.importDispatchTrunk(publicType, file, userId, orgId);
	}

	@Override
	public ResultMsg importDispatchDispatchChanger(String publicType, MultipartFile file, String userId, String orgId) {
		return dispatchChanger.importDispatchDispatchChanger(publicType, file, userId, orgId);
	}

	@Override
	public ResultMsg importDispatchStationChanger(String publicType, MultipartFile file, String userId, String orgId) {
		return dispatchStationChanger.importDispatchStationChanger(publicType, file, userId, orgId);
	}

	@Override
	public ResultMsg importDispatchDuty(String publicType, MultipartFile file, String userId, String orgId) {
		return duty.importDispatchDuty(publicType, file, userId, orgId);
	}

	@Override
	public ResultMsg importDispatchOther(String publicType, MultipartFile file, String userId, String orgId) {
		return dispatchOther.importDispatchOther(publicType, file, userId, orgId);
	}

	@Override
	public ResultMsg importDispatchStation(String publicType, MultipartFile file, String userId, String orgId) {
		return dispatchStation.importDispatchStation(publicType, file, userId, orgId);
	}
}
