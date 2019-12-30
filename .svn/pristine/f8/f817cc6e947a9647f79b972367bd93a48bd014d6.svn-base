package com.enovell.yunwei.km_micor_service.service;

import com.enovell.yunwei.km_micor_service.dto.FaultConditionDTO;
import com.enovell.yunwei.km_micor_service.dto.FaultExportDTO;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * kunmingTXD
 *
 * @author bili
 * @date 18-11-20
 */
@Service("faultManagementService")
public class FaultManagementServiceImpl implements FaultManagementService {
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
    @Value("${user.uploadPath}")
    private String uploadPath;


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
    public List<Document> findAllDocument(String collectionName, String location, String faultType, String departmentId, Date startDate, Date endDate, int start, int limit) {
        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(location,startDate,endDate,departmentId,faultType);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
            findIterable.forEach((Block<? super Document>) results::add);
        }
        results.stream().forEach(d-> {
            d.append("docId",d.getObjectId("_id").toHexString());
            d.remove("_id");
        });
        return results;
    }

    @Override
    public long findAllDocumentCount(String collectionName, String location, String faultType, String departmentId, Date startDate, Date endDate) {
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(location,startDate,endDate,departmentId,faultType);
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
    private Bson getFilter(String location, Date startDate, Date endDate, String departmentId, String faultType) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(location)){
            filter = Filters.and(filter,Filters.regex("location",location));
        }
        if(startDate != null){
            filter = Filters.and(filter,Filters.gte("occurrenceTime",startDate));
        }
        if(endDate != null){
            filter = Filters.and(filter,Filters.lte("occurrenceTime",endDate));
        }
        //根据用户查询组织机构
        if(StringUtils.isNotBlank(departmentId)){
            filter = Filters.and(filter,Filters.eq("departmentId",departmentId));
        }
        //审核状态
        if(StringUtils.isNotBlank(faultType)){
            filter = Filters.and(filter,Filters.eq("faultType",faultType));
        }
        return filter;
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
    public List<FaultExportDTO> getAllFile(FaultConditionDTO dto) {
    	List<FaultExportDTO> faultResult = new ArrayList<FaultExportDTO>();
        List<Document> results = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
        	
            try {
            	if (StringUtils.isNotBlank(dto.getStartTime())) {
            		startDate = sdf.parse(dto.getStartTime());
            	}
            	if (StringUtils.isNotBlank(dto.getEndTime())) {
                    endDate = sdf.parse(dto.getEndTime());
                }
            	
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(dto.getLocation(),startDate,endDate,dto.getDepartmentId(),dto.getFaultType());
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(new Document("createDate", -1));
            findIterable.forEach((Block<? super Document>) results::add);
        }
        results.stream().forEach(d-> {
            d.append("docId",d.getObjectId("_id").toHexString());
            d.remove("_id");
        });
        
        for (Document document : results) {
        	FaultExportDTO feDto = new FaultExportDTO();
        	feDto.setLocation(document.getString("location"));
        	feDto.setMainContent(document.getString("mainContent"));
        	feDto.setDepartmentName(document.getString("departmentName"));
        	feDto.setOrgName(document.getString("orgName"));
        	if("1".equals(document.getString("faultType"))){
        		feDto.setFaultType("事故");
        	}else if("2".equals(document.getString("faultType"))){
        		feDto.setFaultType("故障");
        	}else if("3".equals(document.getString("faultType"))){
        		feDto.setFaultType("障碍");
        	}
        	feDto.setDate(document.getDate("date"));
        	feDto.setOccurrenceTime(document.getDate("occurrenceTime"));
        	feDto.setRecoveryTime(document.getDate("recoveryTime"));
        	faultResult.add(feDto);
		}
        
        return faultResult;
    }
    
}
