package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

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

import com.enovell.yunwei.km_micor_service.dto.PointOuterApplyWordCell;
import com.enovell.yunwei.km_micor_service.dto.PointOuterMaintainApplyDto;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("pointOuterMaintainApplyService")
public class PointOuterMaintainApplyServiceImpl implements  PointOuterMaintainApplyService{
	 	@Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
	    @Value("${user.uploadPath}")
	    private String uploadPath;
	    @Autowired
		private NamedParameterJdbcTemplate template;
	    
	    
	    @Value("${pointOuterPath}")
		private String pointOuterPath;//点外维修模板存放路径
		@Value("${pointOuterDownloadPath}")
		private String pointOuterDownloadPath;//生成文件的存放路径（用于后端写出）
		@Value("${pointOuterFileDownloadPath}")
		private String pointOuterFileDownloadPath;//生成文件的存放路径（用于前端导出）
	    
	    
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
	public long findAllDocumentCount(String collectionName, String userId, String orgId, String lineName, String lineType, String flowState, String currentDay, String startUploadDate,
			String endUploadDate) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName, String userId, String orgId, String lineName, String lineType, String flowState, String currentDay,
			String startUploadDate, String endUploadDate, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgId, lineName, lineType, flowState, currentDay, startUploadDate,endUploadDate);
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
    private Bson getFilter(String userId, String orgId, String lineName, String lineType, String flowState, String currentDay, String startUploadDate, String endUploadDate) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(lineName)){
        	filter = Filters.and(filter,Filters.regex("lineName",lineName));
        }
        if(StringUtils.isNotBlank(lineType)){
        	filter = Filters.and(filter,Filters.eq("lineType",lineType));
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
        filter = Filters.and(filter,Filters.eq("orgId",orgId));//表示查询的是当前登陆用户所属组织机构的数据
        return filter;
    }
    
    @Override
	public String exportApplyFrom(PointOuterMaintainApplyDto dto){
    	
    	String Day1 = dto.getApplyDate();
    	
    	String Day2 = Day1.replaceFirst("-", "年");
    	String Day3 = Day2.replace("-", "月");
    	
//		String outFilePath = Day3+"日"+dto.getUnit()+"天窗点外作业项目作业计划申请表"+dto.getSerial()+"号"+".docx";
		String outFilePath = System.currentTimeMillis()+".docx";
		try {
			FileInputStream fis = new FileInputStream(new File(pointOuterPath+"pointOuterMaintain.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				if(StringUtils.isNotBlank(dto.getUnit())&&StringUtils.isNotBlank(dto.getSerial())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.unitAndSerial.getRow()).getCell(PointOuterApplyWordCell.unitAndSerial.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(14);
					run.setText("单位及编号：昆明通信段   "+dto.getAuditorOrg()+"					"+dto.getUnit()+dto.getSerial()+"号");
//					table.getRow(PointOuterApplyWordCell.unitAndSerial.getRow()).getCell(PointOuterApplyWordCell.unitAndSerial.getCol())
//					.setText(dto.getUnit()+"             "+dto.getSerial());
				}
				
				if(StringUtils.isNotBlank(dto.getWorkTime())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.workTime.getRow()).getCell(PointOuterApplyWordCell.workTime.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getWorkTime());
//					table.getRow(PointOuterApplyWordCell.workTime.getRow()).getCell(PointOuterApplyWordCell.workTime.getCol())
//					.setText(dto.getWorkTime());
				}
				
				if(StringUtils.isNotBlank(dto.getLineName())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.lineName.getRow()).getCell(PointOuterApplyWordCell.lineName.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(StringUtils.isNotBlank(dto.getSection())){
						run.setText(dto.getLineName()+dto.getSection());
					}
					else if(StringUtils.isNotBlank(dto.getStation())){
						run.setText(dto.getLineName()+dto.getStation());
					}
//					table.getRow(PointOuterApplyWordCell.lineName.getRow()).getCell(PointOuterApplyWordCell.lineName.getCol())
//					.setText(dto.getLineName());
				}
				
				if(StringUtils.isNotBlank(dto.getWorkPrincipal())&&StringUtils.isNotBlank(dto.getPhone())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.workPrincipalAndPhone.getRow()).getCell(PointOuterApplyWordCell.workPrincipalAndPhone.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getWorkPrincipal()+"（"+dto.getPhone()+"）");
//					table.getRow(PointOuterApplyWordCell.workPrincipalAndPhone.getRow()).getCell(PointOuterApplyWordCell.workPrincipalAndPhone.getCol())
//					.setText(dto.getWorkPrincipal()+"（"+dto.getPhone()+"）");
				}
				
				if(StringUtils.isNotBlank(dto.getAuditor())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.auditor.getRow()).getCell(PointOuterApplyWordCell.auditor.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getAuditor());
//					table.getRow(PointOuterApplyWordCell.auditor.getRow()).getCell(PointOuterApplyWordCell.auditor.getCol())
//					.setText(dto.getAuditor());
				}
				
				if(StringUtils.isNotBlank(dto.getAuditDate())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.auditDate.getRow()).getCell(PointOuterApplyWordCell.auditDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getAuditDate());
//					table.getRow(PointOuterApplyWordCell.auditDate.getRow()).getCell(PointOuterApplyWordCell.auditDate.getCol())
//					.setText(dto.getAuditDate());
				}
				
				if(StringUtils.isNotBlank(dto.getApprover())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.approver.getRow()).getCell(PointOuterApplyWordCell.approver.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApprover());
//					table.getRow(PointOuterApplyWordCell.approver.getRow()).getCell(PointOuterApplyWordCell.approver.getCol())
//					.setText(dto.getApprover());
				}
				
				if(StringUtils.isNotBlank(dto.getApproveDate())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.approveDate.getRow()).getCell(PointOuterApplyWordCell.approveDate.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getApproveDate());
//					table.getRow(PointOuterApplyWordCell.approveDate.getRow()).getCell(PointOuterApplyWordCell.approveDate.getCol())
//					.setText(dto.getApproveDate());
				}
				
//				if(StringUtils.isNotBlank(dto.getAttendPeople())){
//					XWPFRun run = table.getRow(PointOuterApplyWordCell.attendPeople.getRow()).getCell(PointOuterApplyWordCell.attendPeople.getCol()).getParagraphArray(0).createRun();
//					run.setFontFamily("宋体");
//					run.setFontSize(12);
//					run.setText(dto.getAttendPeople());
////					table.getRow(PointOuterApplyWordCell.attendPeople.getRow()).getCell(PointOuterApplyWordCell.attendPeople.getCol())
////					.setText(dto.getAttendPeople());
//				}
				
				if(StringUtils.isNotBlank(dto.getWorkContentRange())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.workContentRange.getRow()).getCell(PointOuterApplyWordCell.workContentRange.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getWorkContentRange());
//					table.getRow(PointOuterApplyWordCell.workContentRange.getRow()).getCell(PointOuterApplyWordCell.workContentRange.getCol())
//					.setText(dto.getWorkContentRange());
				}
				if(StringUtils.isNotBlank(dto.getWorkOrgCondition())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.workOrgCondition.getRow()).getCell(PointOuterApplyWordCell.workOrgCondition.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getWorkOrgCondition());
//					table.getRow(PointOuterApplyWordCell.workContentRange.getRow()).getCell(PointOuterApplyWordCell.workContentRange.getCol())
//					.setText(dto.getWorkContentRange());
				}
				if(StringUtils.isNotBlank(dto.getLocaleDefendMeasure())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.localeDefendMeasure.getRow()).getCell(PointOuterApplyWordCell.localeDefendMeasure.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getLocaleDefendMeasure());
//					table.getRow(PointOuterApplyWordCell.workContentRange.getRow()).getCell(PointOuterApplyWordCell.workContentRange.getCol())
//					.setText(dto.getWorkContentRange());
				}
				if(StringUtils.isNotBlank(dto.getRelevantDemand())){
					XWPFRun run = table.getRow(PointOuterApplyWordCell.relevantDemand.getRow()).getCell(PointOuterApplyWordCell.relevantDemand.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getRelevantDemand());
//					table.getRow(PointOuterApplyWordCell.workContentRange.getRow()).getCell(PointOuterApplyWordCell.workContentRange.getCol())
//					.setText(dto.getWorkContentRange());
				}
					
//				if(StringUtils.isNotBlank(dto.getWorkPrepareCarry())){
//					XWPFRun run = table.getRow(PointOuterApplyWordCell.workPrepareCarry.getRow()).getCell(PointOuterApplyWordCell.workPrepareCarry.getCol()).getParagraphArray(0).createRun();
//					run.setFontFamily("宋体");
//					run.setFontSize(12);
//					run.setText(dto.getWorkPrepareCarry());
////					table.getRow(PointOuterApplyWordCell.workPrepareCarry.getRow()).getCell(PointOuterApplyWordCell.workPrepareCarry.getCol())
////					.setText(dto.getWorkPrepareCarry());
//				}
//					
//				if(StringUtils.isNotBlank(dto.getPeopleWorkSiteBackWay())){
//					XWPFRun run = table.getRow(PointOuterApplyWordCell.peopleWorkSiteBackWay.getRow()).getCell(PointOuterApplyWordCell.peopleWorkSiteBackWay.getCol()).getParagraphArray(0).createRun();
//					run.setFontFamily("宋体");
//					run.setFontSize(12);
//					run.setText(dto.getPeopleWorkSiteBackWay());
////					table.getRow(PointOuterApplyWordCell.peopleWorkSiteBackWay.getRow()).getCell(PointOuterApplyWordCell.peopleWorkSiteBackWay.getCol())
////					.setText(dto.getPeopleWorkSiteBackWay());
//				}
//					
//				if(StringUtils.isNotBlank(dto.getWorkPeopleDivision())){
//					XWPFRun run = table.getRow(PointOuterApplyWordCell.workPeopleDivision.getRow()).getCell(PointOuterApplyWordCell.workPeopleDivision.getCol()).getParagraphArray(0).createRun();
//					run.setFontFamily("宋体");
//					run.setFontSize(12);
//					run.setText(dto.getWorkPeopleDivision());
////					table.getRow(PointOuterApplyWordCell.workPeopleDivision.getRow()).getCell(PointOuterApplyWordCell.workPeopleDivision.getCol())
////					.setText(dto.getWorkPeopleDivision());
//				}
//					
//				if(StringUtils.isNotBlank(dto.getSafeguardDivision())){
//					XWPFRun run = table.getRow(PointOuterApplyWordCell.safeguardDivision.getRow()).getCell(PointOuterApplyWordCell.safeguardDivision.getCol()).getParagraphArray(0).createRun();
//					run.setFontFamily("宋体");
//					run.setFontSize(12);
//					run.setText(dto.getSafeguardDivision());
////					table.getRow(PointOuterApplyWordCell.safeguardDivision.getRow()).getCell(PointOuterApplyWordCell.safeguardDivision.getCol())
////					.setText(dto.getSafeguardDivision());
//				}
//					
//				if(StringUtils.isNotBlank(dto.getSafetyAttentionMatter())){
//					XWPFRun run = table.getRow(PointOuterApplyWordCell.safetyAttentionMatter.getRow()).getCell(PointOuterApplyWordCell.safetyAttentionMatter.getCol()).getParagraphArray(0).createRun();
//					run.setFontFamily("宋体");
//					run.setFontSize(12);
//					run.setText(dto.getSafetyAttentionMatter());
////					table.getRow(PointOuterApplyWordCell.safetyAttentionMatter.getRow()).getCell(PointOuterApplyWordCell.safetyAttentionMatter.getCol())
////					.setText(dto.getSafetyAttentionMatter());
//				}
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