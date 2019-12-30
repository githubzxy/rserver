package com.enovell.yunwei.km_micor_service.service.productionManage.procuratorialDaily;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ProcuratorialDailyDto;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("procuratorialDailyService")
public class  ProcuratorialDailyServiceImpl implements  ProcuratorialDailyService{
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
    public List<Document> findAllDocument(String collectionName,  String userId,String workshop,String date, int start, int limit,String endDate) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, workshop, date,endDate);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.append("userId",d.get("userId"));
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
    public long findAllDocumentCount(String collectionName,  String userId, String workshop,String date ,String endata) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, workshop, date,endata);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}
	/**
     * 分页查询条件封装
     * @param name 查询名称
     * @return 查询条件
     */
    private Bson getFilter(String userId ,String workshop,String date,String endDate) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workshop)){
            filter = Filters.and(filter,Filters.regex("workshop",workshop));
        }
        if(StringUtils.isNotBlank(date)){
            filter = Filters.and(filter,Filters.gte("date",date));
        }
        if(StringUtils.isNotBlank(endDate)){
            filter = Filters.and(filter,Filters.lte("date",endDate));
        }
        return filter;
    }

	@Override
	public List<ProcuratorialDailyDto> findAllDocumentByexport(String workshopOfexport, String dateOfexport,String endDate) {
		List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilterOfexport(workshopOfexport, dateOfexport,endDate);
            FindIterable<Document> findIterable = md.getCollection("procuratorialDaily").find(filter).sort(new Document("creatDateStr", -1));
            findIterable.forEach((Block<? super Document>) results::add);
        }
        List<ProcuratorialDailyDto> procuratorialDailyDtos= new ArrayList<ProcuratorialDailyDto>();
        results.stream().forEach(d-> {
        	ProcuratorialDailyDto procuratorialDailyDto= new ProcuratorialDailyDto();
            d.append("docId",d.getObjectId("_id").toHexString());
            d.append("userId",d.get("userId"));
            d.remove("_id");
            procuratorialDailyDto.setWorkshop( (String)d.get("workshop"));
            procuratorialDailyDto.setDepartment( (String)d.get("department"));
            procuratorialDailyDto.setDate( (String)d.get("date"));
            procuratorialDailyDto.setInspector( (String)d.get("inspector"));
            procuratorialDailyDto.setSite( (String)d.get("site"));
            procuratorialDailyDto.setContent( (String)d.get("content"));
            procuratorialDailyDto.setProblem( (String)d.get("problem"));
            procuratorialDailyDto.setRequire( (String)d.get("require"));
            procuratorialDailyDto.setCondition( (String) d.get("condition"));
            procuratorialDailyDto.setFunctionary( (String)d.get("functionary"));
            procuratorialDailyDto.setRemark( (String) d.get("remark"));
            procuratorialDailyDtos.add(procuratorialDailyDto);
        });
      
        
        
        return procuratorialDailyDtos;
	}

	private Bson getFilterOfexport(String workshopOfexport, String dateOfexport,String endDate) {
		Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workshopOfexport)){
            filter = Filters.and(filter,Filters.regex("workshop",workshopOfexport));
        }
        if(StringUtils.isNotBlank(dateOfexport)){
            filter = Filters.and(filter,Filters.gte("date",dateOfexport));
        }
        if(StringUtils.isNotBlank(endDate)){
            filter = Filters.and(filter,Filters.lte("date",endDate));
        }
        return filter;
	}


	

}

