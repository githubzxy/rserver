package com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
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

import com.enovell.yunwei.km_micor_service.dto.PointInnerSecondMaintainApplyDto;
import com.enovell.yunwei.km_micor_service.dto.PointInnerSecondMaintainApplyWordCell;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("pointInnerSecondMaintainApplyService")
public class PointInnerSecondMaintainApplyServiceImpl implements  PointInnerSecondMaintainApplyService{
	
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
	public long findAllDocumentCount(String collectionName, String userId, String currentDay, String constructionProject, String flowState, String startUploadDate, String endUploadDate) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, currentDay, constructionProject, flowState, startUploadDate, endUploadDate);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String userId, String currentDay, String constructionProject, String flowState, String startUploadDate, String endUploadDate, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, currentDay, constructionProject, flowState, startUploadDate,endUploadDate);
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
	 * @param userId 登录用户ID
	 * @param currentDay 当前日期字符串
	 * @param constructionProject 施工项目
	 * @param flowState 流程状态
	 * @param startUploadDate 开始时间
	 * @param endUploadDate 结束时间
	 * @return 查询条件 Bson
	 */
    private Bson getFilter(String userId, String currentDay, String constructionProject, String flowState, String startUploadDate, String endUploadDate) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(constructionProject)){
        	filter = Filters.and(filter,Filters.regex("constructionProject",constructionProject));
        }
        if(StringUtils.isNotBlank(flowState)){
        	filter = Filters.and(filter,Filters.eq("flowState",flowState));
        }
        if(StringUtils.isNotBlank(startUploadDate)){
            filter = Filters.and(filter,Filters.gte("createDate",startUploadDate));
        }
        if(StringUtils.isNotBlank(endUploadDate)){
            filter = Filters.and(filter,Filters.lte("createDate",endUploadDate));
        }
//        filter = Filters.and(filter,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//        filter = Filters.and(filter,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
        filter = Filters.and(filter,Filters.eq("userId",userId));//表示查询的是当前登陆用户的数据
        return filter;
    }

	@Override
	public String exportApplyFrom(PointInnerSecondMaintainApplyDto dto) {
        String Day1 = dto.getCreateDate();
    	
    	String Day2 = Day1.replaceFirst("-", "年");
    	String Day3 = Day2.replace("-", "月");
    	
//		String outFilePath = Day3+"日"+dto.getUnit()+"天窗点外作业项目作业计划申请表"+dto.getSerial()+"号"+".docx";
		String outFilePath = System.currentTimeMillis()+".docx";
		try {
			FileInputStream fis = new FileInputStream(new File(pointOuterPath+"pointInnerSecondMaintainApply.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				if(StringUtils.isNotBlank(dto.getConstructionProject())){
					XWPFRun run = table.getRow(PointInnerSecondMaintainApplyWordCell.constructionProject.getRow()).getCell(PointInnerSecondMaintainApplyWordCell.constructionProject.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(14);
					run.setText(dto.getConstructionProject());
				}
				
				if(StringUtils.isNotBlank(dto.getSubmitOrgName())){
					XWPFRun run = table.getRow(PointInnerSecondMaintainApplyWordCell.submitOrgName.getRow()).getCell(PointInnerSecondMaintainApplyWordCell.submitOrgName.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getSubmitOrgName());
				}
				
           	   if(dto.getUploadFileArr().size()!=0){
					
					XWPFRun run = table.getRow(PointInnerSecondMaintainApplyWordCell.uploadFileArr.getRow()).getCell(PointInnerSecondMaintainApplyWordCell.uploadFileArr.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					String result = "";
					for(int i = 0 ; i < dto.getUploadFileArr().size() ; i++){
						result = result+dto.getUploadFileArr().get(i).getName()+"		";
					}
					run.setText(result);
				}
				
				if(StringUtils.isNotBlank(dto.getApprover())){
					XWPFRun run = table.getRow(PointInnerSecondMaintainApplyWordCell.approver.getRow()).getCell(PointInnerSecondMaintainApplyWordCell.approver.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApprover());
				}
				
				if(StringUtils.isNotBlank(dto.getApproveDate())){
					XWPFRun run = table.getRow(PointInnerSecondMaintainApplyWordCell.approveDate.getRow()).getCell(PointInnerSecondMaintainApplyWordCell.approveDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApproveDate());
				}
				
				
				if(StringUtils.isNotBlank(dto.getApproveAdvice())){
					XWPFRun run = table.getRow(PointInnerSecondMaintainApplyWordCell.approveAdvice.getRow()).getCell(PointInnerSecondMaintainApplyWordCell.approveAdvice.getCol()).getParagraphArray(0).createRun();
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
