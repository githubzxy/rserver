package com.enovell.yunwei.km_micor_service.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolDTO;
import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolExportDTO;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * kunmingTXD
 *
 * @author bili
 * @date 18-11-20
 */
@Service("workAssortProtocolService")
public class WorkAssortProtocolServiceImpl implements WorkAssortProtocolService {
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
    @Value("${user.uploadPath}")
    private String uploadPath;
    
    
	@Override
	public List<WorkAssortProtocolExportDTO> getAllFile(WorkAssortProtocolDTO dto) {
        List<Document> results = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            try {
            	if (StringUtils.isNotBlank(dto.getStartUploadDate())) {
            		startDate = sdf.parse(dto.getStartUploadDate());
            	}
            	if (StringUtils.isNotBlank(dto.getEndUploadDate())) {
                    endDate = sdf.parse(dto.getEndUploadDate());
                }
            	
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(dto.getName(),startDate,endDate,dto.getOrgId(),"");
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(new Document("createDate", -1));
            findIterable.forEach((Block<? super Document>) results::add);
        }
        results.stream().forEach(d-> {
            d.append("docId",d.getObjectId("_id").toHexString());
            d.remove("_id");
        });
        
        List<WorkAssortProtocolExportDTO> dataList = results.stream().map(d->{
			WorkAssortProtocolExportDTO exdto = new WorkAssortProtocolExportDTO();
			exdto.setFileName(d.get("name").toString());
			exdto.setOrganization(d.get("orgName").toString());
			if(d.get("createDate") instanceof Date) {
				Date time = (Date)d.get("createDate");
				exdto.setCreateDate(time);
			}
			exdto.setCreateUser(d.get("createUserName").toString());
			return exdto;
		}).collect(Collectors.toList());
        
        return dataList;
	}
    
	
	 /**
     * 分页查询条件封装
     * @param name 查询名称
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @param orgId 组织机构id
     * @return 查询条件
     */
	 private Bson getFilter(String name, Date startUploadDate, Date endUploadDate,String orgId,String auditStatus) {
	        Bson filter = Filters.eq("status",1);
	        if(StringUtils.isNotBlank(name)){
	            filter = Filters.and(filter,Filters.regex("name",name));
	        }
	        if(startUploadDate != null){
	            filter = Filters.and(filter,Filters.gte("createDate",startUploadDate));
	        }
	        if(endUploadDate != null){
	            filter = Filters.and(filter,Filters.lte("createDate",endUploadDate));
	        }
	        //根据用户查询组织机构
	        if(StringUtils.isNotBlank(orgId)){
	            filter = Filters.and(filter,Filters.eq("orgId",orgId));
	        }
	        //审核状态
	        if(StringUtils.isNotBlank(auditStatus)){
	            filter = Filters.and(filter,Filters.eq("auditStatus",Integer.valueOf(auditStatus)));
	        }
	        return filter;
	    }
}
