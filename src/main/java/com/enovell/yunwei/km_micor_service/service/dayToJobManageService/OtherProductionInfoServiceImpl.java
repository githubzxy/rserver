package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * kunmingTXD
 * @author yangsy
 * @date 19-1-18
 */
@Service("otherProductionInfoService")
public class OtherProductionInfoServiceImpl implements OtherProductionInfoService {
	
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
    @Value("${user.uploadPath}")
    private String uploadPath;
    
    @Override
    public Document addDocument(Document doc, String collectionName) {
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            doc.append("status",1);
            doc.append("busiId",UUIDUtils.getUUID());
            md.getCollection(collectionName).insertOne(doc);
        }
        return doc;
    }
    
    @Override
    public List<Document> findAllDocument(String collectionName,  String userId,  String orgId, String currentDay,String infoResult,String type, String startUploadDate, String endUploadDate, int start, int limit,String detail, String system, String systemType) {
        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(userId, orgId, currentDay, infoResult, type,startUploadDate,endUploadDate,detail,system,systemType);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
            findIterable.forEach((Block<? super Document>) results::add);
        }
        results.stream().forEach(d-> {
            d.append("docId",d.getObjectId("_id").toHexString());
            d.append("summaryPersonIdAndInfoResult",d.get("summaryPersonId")+","+d.get("infoResult"));
            d.remove("_id");
        });
        return results;
    }

    @Override
    public long findAllDocumentCount(String collectionName,  String userId,  String orgId, String currentDay,String infoResult,String type, String startUploadDate, String endUploadDate,String detail, String system, String systemType) {
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(userId, orgId, currentDay, infoResult, type, startUploadDate, endUploadDate,detail,system,systemType);
            return  md.getCollection(collectionName).count(filter);
        }
    }
    
    /**
     * 分页查询条件封装
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @return 查询条件
     */
    private Bson getFilter(String userId, String orgId, String currentDay,String infoResult,String type, String startUploadDate, String endUploadDate,String detail,String system,String systemType) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(system)){
            filter = Filters.and(filter,Filters.eq("system",system));
        }
        if(StringUtils.isNotBlank(systemType)){
            filter = Filters.and(filter,Filters.eq("systemType",systemType));
        }
        
        if(StringUtils.isNotBlank(infoResult)){
            filter = Filters.and(filter,Filters.eq("infoResult",infoResult));
        }
        if(StringUtils.isNotBlank(type)){
            filter = Filters.and(filter,Filters.eq("type",type));
        }
        if(StringUtils.isNotBlank(startUploadDate)){
        	
            filter = Filters.and(filter,Filters.gte("createDate",startUploadDate));
        }
        if(StringUtils.isNotBlank(endUploadDate)){
            filter = Filters.and(filter,Filters.lte("createDate",endUploadDate));
        }
        if(StringUtils.isNotBlank(detail)){
            filter = Filters.and(filter,Filters.regex("detail",detail));
        }
        if("402891b45b5fd02c015b74a20fd10033".equals(orgId)){//使安全科账号能够查询全部数据
        	filter = filter;
        }else{
        	filter = Filters.and(filter,Filters.in("orgId",orgId,null,""));//查询数据以登陆用户过滤
        }
        filter = Filters.and(filter,Filters.eq("lost","0"));//表示查询的是遗留条件为否的
        filter = Filters.and(filter,Filters.eq("projectType","other"));//表示查询的是其他生产信息的数据
        return filter;
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
	public Document findDocumentByBusiId(String busiId, String collectionName) {
    	try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("busiId", busiId).append("status",1));
            Document doc = findIterable.first();
            doc.append("docId",doc.getObjectId("_id").toHexString());
            doc.remove("_id");
            return doc;
        }
	}

    @Override
    public List<Document> uploadFile(List<MultipartFile> files) {
        List<Document> uploadFiles = new ArrayList<>();
        //读取上传文件，封装为上传文件对象
        files.forEach(file -> {
            if(StringUtils.isBlank(file.getOriginalFilename())){
                return;
            }
            Document uploadFile = new Document();
            uploadFile.put("name", file.getOriginalFilename());
            uploadFile.put("date", new Date());
            String id = UUIDUtils.getUUID();
            uploadFile.put("id",id);
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String savePath = uploadPath + "/" + id + suffix ;
            uploadFile.put("path", savePath);
            try {
                file.transferTo(new File(savePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadFiles.add(uploadFile);
        });
        return uploadFiles;
    }

}