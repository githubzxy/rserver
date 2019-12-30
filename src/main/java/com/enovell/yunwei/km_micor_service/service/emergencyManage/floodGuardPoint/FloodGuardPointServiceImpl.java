package com.enovell.yunwei.km_micor_service.service.emergencyManage.floodGuardPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.FloodDuardPointDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.FloodDuardPointFindDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("floodGuardPointService")
public class FloodGuardPointServiceImpl implements FloodGuardPointService{
		@Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
	    @Autowired
		private ReadFileUtil readFileUtil;
	    /**
		  * 导出Excel的模板路径
		  */
		private static final String REPAIR_MODEL_PATH = "/static/exportModel/floodGuardPointTemp.xls";

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
	            doc.append("userId",doc.get("userId"));
	            doc.remove("_id");
	            return doc;
	        }
	}

	@Override
    public List<Document> findAllDocument(String collectionName,  String userId, String orgSelectName,String workArea, String lineName, String section, String guardName, String ksStatus,int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgSelectName, workArea, lineName,  section,  guardName, ksStatus);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
    public long findAllDocumentCount(String collectionName,  String userId, String orgSelectName,String workArea, String lineName, String section, String guardName,String ksStatus) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, orgSelectName, workArea,lineName,  section, guardName,ksStatus);
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
    private Bson getFilter(String userId , String orgSelectName,String workArea, String lineName, String section, String guardName,String ksStatus) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(orgSelectName)){
            filter = Filters.and(filter,Filters.regex("orgSelectName",orgSelectName));
        }
        if(StringUtils.isNotBlank(workArea)){
        	filter = Filters.and(filter,Filters.regex("workArea",workArea));
        }
        if(StringUtils.isNotBlank(lineName)){
            filter = Filters.and(filter,Filters.regex("lineName",lineName));
        }
        if(StringUtils.isNotBlank(section)){
            filter = Filters.and(filter,Filters.regex("section",section));
        }
        if(StringUtils.isNotBlank(guardName)){
            filter = Filters.and(filter,Filters.regex("guardName",guardName));
        }
        if(StringUtils.isNotBlank(ksStatus)){
        	filter = Filters.and(filter,Filters.regex("ksStatus",ksStatus));
        }
//        filter = Filters.and(filter,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//        filter = Filters.and(filter,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
        return filter;
    }
	
	public Map<String,List<FloodDuardPointDto>> getAllFile(FloodDuardPointFindDto dto) {

        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(null,dto.getOrgSelectName(),dto.getWorkArea(),dto.getLineName(),dto.getSection(),dto.getGuardName(),dto.getKsStatus());
            Document doc=new Document();
            doc.append("creatDateStr", -1);
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(doc);
            findIterable.forEach((Block<? super Document>) results::add);
        }
        Map<String,List<FloodDuardPointDto>> dataMap=dataToSheet(results);
        return dataMap;
	
	}
//	private Bson getExportFilter() {
//		Bson filters=Filters.eq("status",1);
//		return filters;
//	}
	@Override
	public Workbook exportExcel(FloodDuardPointFindDto dto) throws IOException {
		//1.查出数据
				Map<String,List<FloodDuardPointDto>> map = getAllFile(dto);
//				if(map.size()==0) return null;
				//2.将数据写入excel模板中
				HSSFWorkbook wb = writeDataInExcel(map);
				return wb;
	}
	private Map<String,List<FloodDuardPointDto>> dataToSheet(List<Document> results){
		List<FloodDuardPointDto> list1=new ArrayList<>();//sheet1数据
		Map<String,List<FloodDuardPointDto>> map=new HashMap<String,List<FloodDuardPointDto>>();
		map.put("01", list1);
		for (Document d : results) {
			FloodDuardPointDto exdto = new FloodDuardPointDto();
			if(StringUtils.isNotBlank((String)d.get("orgSelectName"))){
				exdto.setOrgSelectName(d.get("orgSelectName").toString());
			}else{
				exdto.setOrgSelectName("");
			}
			if(StringUtils.isNotBlank((String)d.get("workArea"))){
				exdto.setWorkArea(d.get("workArea").toString());
			}else{
				exdto.setWorkArea("");
			}
			if(StringUtils.isNotBlank((String)d.get("lineName"))){
				exdto.setLineName(d.get("lineName").toString());
			}else{
				exdto.setLineName("");
			}
			if(StringUtils.isNotBlank((String)d.get("section"))){
				exdto.setSection(d.get("section").toString());
			}else{
				exdto.setSection("");
			}
			if(StringUtils.isNotBlank((String)d.get("guardName"))){
				exdto.setGuardName(d.get("guardName").toString());
			}else{
				exdto.setGuardName("");
			}
			if(StringUtils.isNotBlank((String)d.get("typeLT"))){
				exdto.setTypeLT(d.get("typeLT").toString());
			}else{
				exdto.setTypeLT("");
			}
			if(StringUtils.isNotBlank((String)d.get("countLT"))){
				exdto.setCountLT(d.get("countLT").toString());
			}else{
				exdto.setCountLT("");
			}
			if(StringUtils.isNotBlank((String)d.get("typeYJ"))){
				exdto.setTypeYJ(d.get("typeYJ").toString());
			}else{
				exdto.setTypeYJ("");
			}
			if(StringUtils.isNotBlank((String)d.get("countYJ"))){
				exdto.setCountYJ(d.get("countYJ").toString());
			}else{
				exdto.setCountYJ("");
			}
			if(StringUtils.isNotBlank((String)d.get("typeGD"))){
				exdto.setTypeGD(d.get("typeGD").toString());
			}else{
				exdto.setTypeGD("");
			}
			
			if(StringUtils.isNotBlank((String)d.get("typeGW"))){
				exdto.setTypeGW(d.get("typeGW").toString());
			}else{
				exdto.setTypeGW("");
			}
			if(StringUtils.isNotBlank((String)d.get("countGW"))){
				exdto.setCountGW(d.get("countGW").toString());
			}else{
				exdto.setCountGW("");
			}
			if(StringUtils.isNotBlank((String)d.get("phoneNumGW"))){
				exdto.setPhoneNumGW(d.get("phoneNumGW").toString());
			}else{
				exdto.setPhoneNumGW("");
			}
			
			if(StringUtils.isNotBlank((String)d.get("condition"))){
				exdto.setCondition(d.get("condition").toString());
			}else{
				exdto.setCondition("");
			}
			if(StringUtils.isNotBlank((String)d.get("phoneNum"))){
				exdto.setPhoneNum(d.get("phoneNum").toString());
			}else{
				exdto.setPhoneNum("");
			}
			
			if(StringUtils.isNotBlank((String)d.get("phoneAP"))){
				exdto.setPhoneAP(d.get("phoneAP").toString());
			}else{
				exdto.setPhoneAP("");
			}
			if(StringUtils.isNotBlank((String)d.get("leadType"))){
				exdto.setLeadType(d.get("leadType").toString());
			}else{
				exdto.setLeadType("");
			}
			
			if(StringUtils.isNotBlank((String)d.get("leadExtent"))){
				exdto.setLeadExtent(d.get("leadExtent").toString());
			}else{
				exdto.setLeadExtent("");
			}
			if(StringUtils.isNotBlank((String)d.get("ksStatus"))){
				exdto.setKsStatus(d.get("ksStatus").toString());
			}else{
				exdto.setKsStatus("");
			}
			if(StringUtils.isNotBlank((String)d.get("remark"))){
				exdto.setRemark(d.get("remark").toString());
			}else{
				exdto.setRemark("");
			}
			map.get("01").add(exdto);
		}
		return map;
	}
	/**
	 * 将数据写入excel模板中
	 * @param list
	 * @return
	 */
	private HSSFWorkbook writeDataInExcel(Map<String,List<FloodDuardPointDto>> map) {
		HSSFWorkbook wb = null;
//		FileInputStream fis =null;
		
		try {
//			fis=new FileInputStream("d:/test/floodGuardPointExport.xls");
			wb =  readFileUtil.getWorkBook(REPAIR_MODEL_PATH);
//			wb=new HSSFWorkbook(fis);
				HSSFSheet s = wb.getSheetAt(0);//获取sheet页
				System.out.println("s:"+s);
				List<FloodDuardPointDto> list;
				//获取对应sheet页的数据list
				list=map.get("01");
                System.out.println(list);
				if(list.size()>0){
					//遍历集合，将数据写入
					for(int r=0;r<list.size();r++){
						HSSFRow valueRow = s.getRow(r+3);//从空白行开始写入
						if(valueRow==null){
							valueRow=s.createRow(r+3);
							//设置字体
//							HSSFFont font=wb.createFont();
//							font.setFontHeightInPoints((short) 10);
							//设置项目列单元格的格式
//							HSSFCellStyle cellStyle=wb.createCellStyle();
//							cellStyle.setWrapText(true);//强制换行
//							cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
//							cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
//							cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
//							cellStyle.setFont(font);
							
							valueRow.createCell(0).setCellValue(r+1);//序号
							valueRow.createCell(1).setCellValue(list.get(r).getOrgSelectName());
							valueRow.createCell(2).setCellValue(list.get(r).getWorkArea());
							valueRow.createCell(3).setCellValue(list.get(r).getLineName());
							valueRow.createCell(4).setCellValue(list.get(r).getSection());
							valueRow.createCell(5).setCellValue(list.get(r).getGuardName());
							valueRow.createCell(6).setCellValue(list.get(r).getTypeLT());
							valueRow.createCell(7).setCellValue(list.get(r).getCountLT());
							valueRow.createCell(8).setCellValue(list.get(r).getTypeYJ());
							valueRow.createCell(9).setCellValue(list.get(r).getCountYJ());
							valueRow.createCell(10).setCellValue(list.get(r).getTypeGD());
							
							valueRow.createCell(11).setCellValue(list.get(r).getTypeGW());
							valueRow.createCell(12).setCellValue(list.get(r).getCountGW());
							valueRow.createCell(13).setCellValue(list.get(r).getPhoneNumGW());
							
							valueRow.createCell(14).setCellValue(list.get(r).getCondition());
							valueRow.createCell(15).setCellValue(list.get(r).getPhoneNum());
							valueRow.createCell(16).setCellValue(list.get(r).getPhoneAP());
							valueRow.createCell(17).setCellValue(list.get(r).getLeadType());
							valueRow.createCell(18).setCellValue(list.get(r).getLeadExtent());
							valueRow.createCell(19).setCellValue(list.get(r).getKsStatus());
							valueRow.createCell(20).setCellValue(list.get(r).getRemark());
						
						}
						//设置字体
//						HSSFFont font=wb.createFont();
//						font.setFontHeightInPoints((short) 10);
//						//设置项目列单元格的格式
//						HSSFCellStyle cellStyle=wb.createCellStyle();
//						cellStyle.setWrapText(true);//强制换行
//						cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
//						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
//						cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
//						cellStyle.setFont(font);
//						valueRow.getCell(2).setCellStyle(cellStyle);
						valueRow.getCell(0).setCellValue(r+1);//序号
						valueRow.getCell(1).setCellValue(list.get(r).getOrgSelectName());
						valueRow.getCell(2).setCellValue(list.get(r).getWorkArea());

						valueRow.getCell(3).setCellValue(list.get(r).getLineName());
						valueRow.getCell(4).setCellValue(list.get(r).getSection());
						valueRow.getCell(5).setCellValue(list.get(r).getGuardName());
						valueRow.getCell(6).setCellValue(list.get(r).getTypeLT());
						valueRow.getCell(7).setCellValue(list.get(r).getCountLT());
						valueRow.getCell(8).setCellValue(list.get(r).getTypeYJ());
						valueRow.getCell(9).setCellValue(list.get(r).getCountYJ());
						valueRow.getCell(10).setCellValue(list.get(r).getTypeGD());
						valueRow.getCell(11).setCellValue(list.get(r).getTypeGW());
						valueRow.getCell(12).setCellValue(list.get(r).getCountGW());
						valueRow.getCell(13).setCellValue(list.get(r).getPhoneNumGW());
						valueRow.getCell(14).setCellValue(list.get(r).getCondition());
						valueRow.getCell(15).setCellValue(list.get(r).getPhoneNum());
						valueRow.getCell(16).setCellValue(list.get(r).getPhoneAP());
						valueRow.getCell(17).setCellValue(list.get(r).getLeadType());
						valueRow.getCell(18).setCellValue(list.get(r).getLeadExtent());
						valueRow.getCell(19).setCellValue(list.get(r).getKsStatus());

						valueRow.getCell(20).setCellValue(list.get(r).getRemark());
						
						}
					}
//				s.setForceFormulaRecalculation(true);
		    return wb;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

}
