package com.enovell.yunwei.km_micor_service.service.securityManage.accidentTroubleObstacleManage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.AccidentTroubleDto;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("accidentTroubleObstacleQueryService")
public class AccidentTroubleObstacleQueryServiceImpl implements AccidentTroubleObstacleQueryService {
	@Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	 
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
	public long findAllDocumentCount(String collectionName, String infoResult, String startDate, String endDate) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(infoResult,startDate,endDate);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String infoResult, String startDate, String endDate, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(infoResult,startDate,endDate);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter(String infoResult, String startDate, String endDate) {
		Bson filters=Filters.eq("status",1);
		if(StringUtils.isNotBlank(infoResult)) {
			filters=Filters.and(filters,Filters.eq("infoResult",infoResult));
		}else{
			//查询信息：1：事故；2：故障；3：障碍；4：安全隐患
			filters=Filters.and(filters,Filters.in("infoResult","1","2","3","4"));
		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("createDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("createDate", endDate));
		}
		return filters;
	}

	@Override
	public void convertInfoResult(List<AccidentTroubleDto> dataList) {
		if (dataList==null) return;
		for (AccidentTroubleDto accidentTroubleDto :dataList) {
			String flagString = accidentTroubleDto.getInfoResult();
			if (flagString.equals("1")) {
				accidentTroubleDto.setInfoResult("事故");
				continue;
			}
			if (flagString.equals("2")) {
				accidentTroubleDto.setInfoResult("故障");
				continue;
			}
			if (flagString.equals("3")) {
				accidentTroubleDto.setInfoResult("障碍");
				continue;
			}
			if (flagString.equals("4")) {
				accidentTroubleDto.setInfoResult("安全隐患");
				continue;
			}
			
		}
		
		
	}
}