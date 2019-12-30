package com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.SkylightMaintenanceApplyDto;
import com.enovell.yunwei.km_micor_service.dto.SkylightMaintenanceApplyWordCell;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("skylightMaintenanceApplyService")
public class SkylightMaintenanceApplyServiceImpl implements  SkylightMaintenanceApplyService{
	 @Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
	    @Value("${user.uploadPath}")
	    private String uploadPath;
	    
	    @Value("${pointOuterPath}")
		private String pointOuterPath;//点外维修模板存放路径
		@Value("${pointOuterDownloadPath}")
		private String pointOuterDownloadPath;//生成文件的存放路径（用于后端写出）
		@Value("${pointOuterFileDownloadPath}")
		private String pointOuterFileDownloadPath;//生成文件的存放路径（用于前端导出）
	    
	    @Autowired
		private NamedParameterJdbcTemplate template;
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
	public List<String> getLineData() {
		String sql="select t.name_ from res_base_rail_line t";
		List<String> workShopList=template.queryForList(sql,new HashMap<>(), String.class);
		return workShopList;
	}

	@Override
	public long findAllDocumentCount(String collectionName, String userId, String orgId, String currentDay, String project,String type,String startUploadDate,
			String endUploadDate,String flowState) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, currentDay,project,type,startUploadDate,endUploadDate,flowState);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}
	public List<Document> findAllDocument(String collectionName, String userId, String orgId, String currentDay,
			String project,String type,String startUploadDate, String endUploadDate,String flowState, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, currentDay,project,type,startUploadDate,endUploadDate,flowState);
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
	 /**
     * 分页查询条件封装
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @return 查询条件
     */
    private Bson getFilter(String userId, String orgId, String currentDay,String project,String type,String startUploadDate, String endUploadDate,
    		String flowState
    		) {
    	Bson filter = Filters.eq("status",1);
    	if(StringUtils.isNotBlank(project)){
            filter = Filters.and(filter,Filters.regex("project",project));
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
        if(StringUtils.isNotBlank(flowState)){
            filter = Filters.and(filter,Filters.eq("flowState",flowState));
        }
//        filter = Filters.and(filter,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//        filter = Filters.and(filter,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
        filter = Filters.and(filter,Filters.eq("orgId",orgId));//表示查询的是当前登陆用户所属组织机构的数据
        return filter;
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
	
	@Override
	public String exportApplyFrom(SkylightMaintenanceApplyDto dto){
    	
    	String Day1 = dto.getCreateDateStr();
    	
    	String Day2 = Day1.replaceFirst("-", "年");
    	String Day3 = Day2.replace("-", "月");
    	
//		String outFilePath = Day3+"日"+dto.getUnit()+"天窗点外作业项目作业计划申请表"+dto.getSerial()+"号"+".docx";
		String outFilePath = System.currentTimeMillis()+".docx";
		try {
			FileInputStream fis = new FileInputStream(new File(pointOuterPath+"skylightMaintenance.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				if(StringUtils.isNotBlank(dto.getProject())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.project.getRow()).getCell(SkylightMaintenanceApplyWordCell.project.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(14);
					run.setText(dto.getProject());
				}
				
				if(StringUtils.isNotBlank(dto.getType())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.type.getRow()).getCell(SkylightMaintenanceApplyWordCell.type.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getType());
				}
				
				if(StringUtils.isNotBlank(dto.getOrgName())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.orgName.getRow()).getCell(SkylightMaintenanceApplyWordCell.orgName.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getOrgName());
				}
				
				if(StringUtils.isNotBlank(dto.getCreateDateStr())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.createDateStr.getRow()).getCell(SkylightMaintenanceApplyWordCell.createDateStr.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getCreateDateStr());
				}
				
				if(StringUtils.isNotBlank(dto.getCreateUserName())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.createUserName.getRow()).getCell(SkylightMaintenanceApplyWordCell.createUserName.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getCreateUserName());
				}
				
				if(dto.getUploadFileArr().size()!=0){
					
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.uploadFileArr.getRow()).getCell(SkylightMaintenanceApplyWordCell.uploadFileArr.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					String result = "";
					for(int i = 0 ; i < dto.getUploadFileArr().size() ; i++){
						result = result+dto.getUploadFileArr().get(i).getName()+"		";
					}
					run.setText(result);
				}
				
				if(StringUtils.isNotBlank(dto.getSkillAuditor())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.skillAuditor.getRow()).getCell(SkylightMaintenanceApplyWordCell.skillAuditor.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSkillAuditor());
				}
				
				if(StringUtils.isNotBlank(dto.getSkillAuditDate())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.skillAuditDate.getRow()).getCell(SkylightMaintenanceApplyWordCell.skillAuditDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSkillAuditDate());
				}
				
				if(StringUtils.isNotBlank(dto.getSafeAuditor())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.safeAuditor.getRow()).getCell(SkylightMaintenanceApplyWordCell.safeAuditor.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSafeAuditor());
				}
				if(StringUtils.isNotBlank(dto.getSafeAuditDate())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.safeAuditDate.getRow()).getCell(SkylightMaintenanceApplyWordCell.safeAuditDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSafeAuditDate());
				}
				if(StringUtils.isNotBlank(dto.getDispatchAuditor())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.dispatchAuditor.getRow()).getCell(SkylightMaintenanceApplyWordCell.dispatchAuditor.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getDispatchAuditor());
				}
				if(StringUtils.isNotBlank(dto.getDispatchAuditDate())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.dispatchAuditDate.getRow()).getCell(SkylightMaintenanceApplyWordCell.dispatchAuditDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getDispatchAuditDate());
				}
				if(StringUtils.isNotBlank(dto.getApprover())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.approver.getRow()).getCell(SkylightMaintenanceApplyWordCell.approver.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApprover());
				}
				if(StringUtils.isNotBlank(dto.getApproveDate())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.approveDate.getRow()).getCell(SkylightMaintenanceApplyWordCell.approveDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApproveDate());
				}
				if(StringUtils.isNotBlank(dto.getSkillAuditAdvice())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.skillAuditAdvice.getRow()).getCell(SkylightMaintenanceApplyWordCell.skillAuditAdvice.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSkillAuditAdvice());
				}
				if(StringUtils.isNotBlank(dto.getSafeAuditAdvice())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.safeAuditAdvice.getRow()).getCell(SkylightMaintenanceApplyWordCell.safeAuditAdvice.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSafeAuditAdvice());
				}
				if(StringUtils.isNotBlank(dto.getDispatchAuditAdvice())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.dispatchAuditAdvice.getRow()).getCell(SkylightMaintenanceApplyWordCell.dispatchAuditAdvice.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getDispatchAuditAdvice());
				}
				if(StringUtils.isNotBlank(dto.getApproveAdvice())){
					XWPFRun run = table.getRow(SkylightMaintenanceApplyWordCell.approveAdvice.getRow()).getCell(SkylightMaintenanceApplyWordCell.approveAdvice.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApproveAdvice());
				}
					
			};
			FileOutputStream fos = new FileOutputStream(new File(pointOuterDownloadPath+outFilePath));
//			System.out.println(rootPathTomcat+outFilePath);
			xdoc.write(fos);
			xdoc.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(pointOuterFileDownloadPath+outFilePath);
		return pointOuterFileDownloadPath+outFilePath;
	}
	
}