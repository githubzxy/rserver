package com.enovell.yunwei.km_micor_service.service.integratedManage.attendanceManage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Service("attendanceManageCollectService")
public class AttendanceManageCollectServiceImpl implements AttendanceManageCollectService {
	 @Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	 @Value("${attendanceModelPath}")
	private String attendanceModelPath;
	@Value("${createPath}")
	private String createPath;

	@Override
	public List<Document> findAllDocument(String collectionName, String name, String startDate, String endDate,
			String auditStatus, String userId, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(name,startDate,endDate,userId,auditStatus);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}


	@Override
	public long findAllDocumentCount(String collectionName, String name, String startDate, String endDate,
			String auditStatus, String userId) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(name,startDate,endDate,userId,auditStatus);
            return  md.getCollection(collectionName).count(filter);
        }
	}
	
	private Bson getFilter(String name,String startDate, String endDate,String userId,String auditStatus) {
		Bson filters=Filters.eq("status",1);
		if(StringUtils.isNotBlank(userId)) {
			filters=Filters.and(filters,Filters.eq("userId",userId));
		}
		if(StringUtils.isNotBlank(name)) {
			filters=Filters.and(filters,Filters.regex("name",name));
		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("createDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("createDate", endDate));
		}
		if(StringUtils.isNotBlank(auditStatus)) {
			filters=Filters.and(filters,Filters.eq("auditStatus",auditStatus));
		}
		return filters;
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
	public Document findDatasById(String id, String collectionName) {
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
                            Filters.eq("_id", new ObjectId(doc.getString("id"))),new Document("$set",doc)
                    );
        }
	}
	
	@Override
	public Document updateCollectDocument(Document doc, String collectionName) {
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
	public List<Document> getUsersByOrgId(String orgId, String collectionName,String attendanceManage,String date,int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(orgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",orgId));
			}
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filters).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
					FindIterable<Document> findIterable2 = md.getCollection(attendanceManage).find(new Document("date", date).append("docId", d.getString("docId")).append("status",1));
		            Document doc = findIterable2.first();
		            if(doc!=null){
		            	d.append("morning", doc.getString("morning"));
		            	d.append("noon", doc.getString("noon"));
		            	d.append("night", doc.getString("night"));
		            }
			 });
		}
		return results;
	}


	@Override
	public long getUsersCountByOrgId(String orgId, String collectionName,String attendanceManage,String date) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(orgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",orgId));
			}
            return  md.getCollection(collectionName).count(filters);
        }
	}


	@Override
	public Document findDocumentById(String docId, String date, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("docId", docId).append("date", date).append("status",1));
            Document doc = findIterable.first();
            if(doc!=null){
            	doc.append("id",doc.getObjectId("_id").toHexString());
            	doc.remove("_id");
            	return doc;
            }else{
            	return null;
            }
        }
	}

	/**
	 * userInfoManage：人员信息表
	 * attendanceManage：考勤表
	 * 根据考勤用户的组织机构先从人员信息表中查询出所有人员再从考勤表中查询出考勤数据根据年月和docId对应封装
	 */
	@Override
	public String collectTable(String year, String month, String orgType, String attendanceOrgName, String attendanceOrgId, String attendanceManage, String userInfoManage) {
		String title = "中国铁路昆明局集团有限公司昆明通信段"+year+month+"考勤表";
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(attendanceOrgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",attendanceOrgId));
			}
			FindIterable<Document> findIterable = md.getCollection(userInfoManage).find(filters).sort(new Document("creatDateStr", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
					FindIterable<Document> findIterable2 = md.getCollection(attendanceManage).find(new Document("year", year).append("month", month).append("docId", d.getString("docId")).append("status",1)).sort(new Document("day", 1));
					for (Document document : findIterable2) {
						if(document!=null){
							d.append(document.getString("day")+"morning", document.getString("morning"));
							d.append(document.getString("day")+"noon", document.getString("noon"));
							d.append(document.getString("day")+"night", document.getString("night"));
							d.append(document.getString("day")+"daily", document.getString("daily"));
							d.append(document.getString("day")+"turn", document.getString("turn"));
						}
					}
			 });
		}
		return createTable(title,attendanceOrgName,results,orgType);
	}
	
	private String createTable(String title,String attendanceOrgName, List<Document> results,String orgType){
		String createTablePath = createPath + System.currentTimeMillis() + ".xlsx";
		FileInputStream fis = null;
		FileOutputStream fos = null;
		XSSFWorkbook wb = null;
		try{
			if("1503".equals(orgType)){
				fis = new FileInputStream(attendanceModelPath+"attendancegq.xlsx");
			}else{
				fis = new FileInputStream(attendanceModelPath+"attendancecjks.xlsx");
			}
			wb =  new XSSFWorkbook(fis);
			XSSFSheet s = wb.getSheetAt(0);
			fos = new FileOutputStream(createTablePath);
			insertData(s, title, attendanceOrgName, results);
			wb.write(fos);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
				fos.close();
				wb.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return createTablePath;
	}
	
	//检查数据字段是否存在
    private String checkDataExist(String data){
    	if(data=="null"){
    		return "";
    	}else{
    		return data;
    	}
    }
	
	private void insertData(XSSFSheet s,String title,String attendanceOrgName, List<Document> results){
		XSSFRow row0 = s.getRow(0);
		XSSFCell cell0 = row0.getCell(0);//标题
		cell0.setCellValue(String.valueOf(title));
		XSSFRow row1 = s.getRow(1);
		XSSFCell cell3 = row1.getCell(3);//部门
		cell3.setCellValue(String.valueOf(attendanceOrgName));
		insert(s, results);
	}
	
	private void insert(XSSFSheet s, List<Document> results) {
		int rows =results.size()-1;
		int lastRowNum = s.getLastRowNum();
		//将第8行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows*3;
		s.shiftRows(8, 17, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow1=s.getRow(5+i*3);//从row6开始复制
			XSSFRow targetRow1=s.createRow(8+i*3);//从row9开始粘贴
			copyRow(sourceRow1, targetRow1);
			XSSFRow sourceRow2=s.getRow(6+i*3);//从row7开始复制
			XSSFRow targetRow2=s.createRow(9+i*3);//从row10开始粘贴
			copyRow(sourceRow2, targetRow2);
			XSSFRow sourceRow3=s.getRow(7+i*3);//从row8开始复制
			XSSFRow targetRow3=s.createRow(10+i*3);//从row11开始粘贴
			copyRow(sourceRow3, targetRow3);
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,0,0));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,1,1));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,2,2));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,3,3));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,4,4));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,36,36));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,37,37));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,38,38));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,39,39));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,40,40));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,41,41));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,42,42));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,43,43));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,44,44));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,45,45));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,46,46));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,47,47));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,48,48));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,49,49));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,50,50));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,51,51));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,52,52));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,53,53));
			s.addMergedRegion(new CellRangeAddress(8+i*3,10+i*3,54,54));
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(8+lastRowNum+rows*3, 17+lastRowNum+rows*3, shiftUpRow, true, false);
		
		for (int i = 0 ; i < results.size() ; i++) {
			XSSFRow row1 = s.getRow(5+i*3);
			XSSFRow row2 = s.getRow(6+i*3);
			XSSFRow row3 = s.getRow(7+i*3);
			XSSFCell cell0 = row1.getCell(0);//序号
			XSSFCell cell1 = row1.getCell(1);//劳资号
			XSSFCell cell2 = row1.getCell(2);//姓名
			XSSFCell cell3 = row1.getCell(3);//职 名
			XSSFCell cell4 = row1.getCell(4);//班制
			XSSFCell cell15 = row1.getCell(5);
			XSSFCell cell25 = row2.getCell(5);
			XSSFCell cell35 = row3.getCell(5);
			XSSFCell cell16 = row1.getCell(6);
			XSSFCell cell26 = row2.getCell(6);
			XSSFCell cell36 = row3.getCell(6);
			XSSFCell cell17 = row1.getCell(7);
			XSSFCell cell27 = row2.getCell(7);
			XSSFCell cell37 = row3.getCell(7);
			XSSFCell cell18 = row1.getCell(8);
			XSSFCell cell28 = row2.getCell(8);
			XSSFCell cell38 = row3.getCell(8);
			XSSFCell cell19 = row1.getCell(9);
			XSSFCell cell29 = row2.getCell(9);
			XSSFCell cell39 = row3.getCell(9);
			XSSFCell cell110 = row1.getCell(10);
			XSSFCell cell210 = row2.getCell(10);
			XSSFCell cell310 = row3.getCell(10);
			XSSFCell cell111 = row1.getCell(11);
			XSSFCell cell211 = row2.getCell(11);
			XSSFCell cell311 = row3.getCell(11);
			XSSFCell cell112 = row1.getCell(12);
			XSSFCell cell212 = row2.getCell(12);
			XSSFCell cell312 = row3.getCell(12);
			XSSFCell cell113 = row1.getCell(13);
			XSSFCell cell213 = row2.getCell(13);
			XSSFCell cell313 = row3.getCell(13);
			XSSFCell cell114 = row1.getCell(14);
			XSSFCell cell214 = row2.getCell(14);
			XSSFCell cell314 = row3.getCell(14);
			XSSFCell cell115 = row1.getCell(15);
			XSSFCell cell215 = row2.getCell(15);
			XSSFCell cell315 = row3.getCell(15);
			XSSFCell cell116 = row1.getCell(16);
			XSSFCell cell216 = row2.getCell(16);
			XSSFCell cell316 = row3.getCell(16);
			XSSFCell cell117 = row1.getCell(17);
			XSSFCell cell217 = row2.getCell(17);
			XSSFCell cell317 = row3.getCell(17);
			XSSFCell cell118 = row1.getCell(18);
			XSSFCell cell218 = row2.getCell(18);
			XSSFCell cell318 = row3.getCell(18);
			XSSFCell cell119 = row1.getCell(19);
			XSSFCell cell219 = row2.getCell(19);
			XSSFCell cell319 = row3.getCell(19);
			XSSFCell cell120 = row1.getCell(20);
			XSSFCell cell220 = row2.getCell(20);
			XSSFCell cell320 = row3.getCell(20);
			XSSFCell cell121 = row1.getCell(21);
			XSSFCell cell221 = row2.getCell(21);
			XSSFCell cell321 = row3.getCell(21);
			XSSFCell cell122 = row1.getCell(22);
			XSSFCell cell222 = row2.getCell(22);
			XSSFCell cell322 = row3.getCell(22);
			XSSFCell cell123 = row1.getCell(23);
			XSSFCell cell223 = row2.getCell(23);
			XSSFCell cell323 = row3.getCell(23);
			XSSFCell cell124 = row1.getCell(24);
			XSSFCell cell224 = row2.getCell(24);
			XSSFCell cell324 = row3.getCell(24);
			XSSFCell cell125 = row1.getCell(25);
			XSSFCell cell225 = row2.getCell(25);
			XSSFCell cell325 = row3.getCell(25);
			XSSFCell cell126 = row1.getCell(26);
			XSSFCell cell226 = row2.getCell(26);
			XSSFCell cell326 = row3.getCell(26);
			XSSFCell cell127 = row1.getCell(27);
			XSSFCell cell227 = row2.getCell(27);
			XSSFCell cell327 = row3.getCell(27);
			XSSFCell cell128 = row1.getCell(28);
			XSSFCell cell228 = row2.getCell(28);
			XSSFCell cell328 = row3.getCell(28);
			XSSFCell cell129 = row1.getCell(29);
			XSSFCell cell229 = row2.getCell(29);
			XSSFCell cell329 = row3.getCell(29);
			XSSFCell cell130 = row1.getCell(30);
			XSSFCell cell230 = row2.getCell(30);
			XSSFCell cell330 = row3.getCell(30);
			XSSFCell cell131 = row1.getCell(31);
			XSSFCell cell231 = row2.getCell(31);
			XSSFCell cell331 = row3.getCell(31);
			XSSFCell cell132 = row1.getCell(32);
			XSSFCell cell232 = row2.getCell(32);
			XSSFCell cell332 = row3.getCell(32);
			XSSFCell cell133 = row1.getCell(33);
			XSSFCell cell233 = row2.getCell(33);
			XSSFCell cell333 = row3.getCell(33);
			XSSFCell cell134 = row1.getCell(34);
			XSSFCell cell234 = row2.getCell(34);
			XSSFCell cell334 = row3.getCell(34);
			XSSFCell cell135 = row1.getCell(35);
			XSSFCell cell235 = row2.getCell(35);
			XSSFCell cell335 = row3.getCell(35);
			XSSFCell cell036 = row1.getCell(36);//学习
			XSSFCell cell037 = row1.getCell(37);//公差
			XSSFCell cell038 = row1.getCell(38);//夜班（小）
			XSSFCell cell039 = row1.getCell(39);//夜班（大）
			XSSFCell cell040 = row1.getCell(40);//加班（日勤）
			XSSFCell cell041 = row1.getCell(41);//加班（轮班）
			XSSFCell cell042 = row1.getCell(42);//年休
			XSSFCell cell043 = row1.getCell(43);//探亲
			XSSFCell cell044 = row1.getCell(44);//婚假
			XSSFCell cell045 = row1.getCell(45);//丧假
			XSSFCell cell046 = row1.getCell(46);//事假
			XSSFCell cell047 = row1.getCell(47);//病假
			XSSFCell cell048 = row1.getCell(48);//住院
			XSSFCell cell049 = row1.getCell(49);//产假
			XSSFCell cell050 = row1.getCell(50);//计生
			XSSFCell cell051 = row1.getCell(51);//护理
			XSSFCell cell052 = row1.getCell(52);//息工
			XSSFCell cell053 = row1.getCell(53);//旷工
			XSSFCell cell054 = row1.getCell(54);//合计
			cell0.setCellValue(String.valueOf(1+i));
			cell1.setCellValue(String.valueOf(results.get(i).getString("number")));
			cell2.setCellValue(String.valueOf(results.get(i).getString("staffName")));
			cell3.setCellValue(String.valueOf(results.get(i).getString("position")));
			cell4.setCellValue("");
			cell15.setCellValue(checkDataExist(String.valueOf(results.get(i).get("1日morning"))));
			cell25.setCellValue(checkDataExist(String.valueOf(results.get(i).get("1日noon"))));
			cell35.setCellValue(checkDataExist(String.valueOf(results.get(i).get("1日night"))));
			cell16.setCellValue(checkDataExist(String.valueOf(results.get(i).get("2日morning"))));
			cell26.setCellValue(checkDataExist(String.valueOf(results.get(i).get("2日noon"))));
			cell36.setCellValue(checkDataExist(String.valueOf(results.get(i).get("2日night"))));
			cell17.setCellValue(checkDataExist(String.valueOf(results.get(i).get("3日morning"))));
			cell27.setCellValue(checkDataExist(String.valueOf(results.get(i).get("3日noon"))));
			cell37.setCellValue(checkDataExist(String.valueOf(results.get(i).get("3日night"))));
			cell18.setCellValue(checkDataExist(String.valueOf(results.get(i).get("4日morning"))));
			cell28.setCellValue(checkDataExist(String.valueOf(results.get(i).get("4日noon"))));
			cell38.setCellValue(checkDataExist(String.valueOf(results.get(i).get("4日night"))));
			cell19.setCellValue(checkDataExist(String.valueOf(results.get(i).get("5日morning"))));
			cell29.setCellValue(checkDataExist(String.valueOf(results.get(i).get("5日noon"))));
			cell39.setCellValue(checkDataExist(String.valueOf(results.get(i).get("5日night"))));
			cell110.setCellValue(checkDataExist(String.valueOf(results.get(i).get("6日morning"))));
			cell210.setCellValue(checkDataExist(String.valueOf(results.get(i).get("6日noon"))));
			cell310.setCellValue(checkDataExist(String.valueOf(results.get(i).get("6日night"))));
			cell111.setCellValue(checkDataExist(String.valueOf(results.get(i).get("7日morning"))));
			cell211.setCellValue(checkDataExist(String.valueOf(results.get(i).get("7日noon"))));
			cell311.setCellValue(checkDataExist(String.valueOf(results.get(i).get("7日night"))));
			cell112.setCellValue(checkDataExist(String.valueOf(results.get(i).get("8日morning"))));
			cell212.setCellValue(checkDataExist(String.valueOf(results.get(i).get("8日noon"))));
			cell312.setCellValue(checkDataExist(String.valueOf(results.get(i).get("8日night"))));
			cell113.setCellValue(checkDataExist(String.valueOf(results.get(i).get("9日morning"))));
			cell213.setCellValue(checkDataExist(String.valueOf(results.get(i).get("9日noon"))));
			cell313.setCellValue(checkDataExist(String.valueOf(results.get(i).get("9日night"))));
			cell114.setCellValue(checkDataExist(String.valueOf(results.get(i).get("10日morning"))));
			cell214.setCellValue(checkDataExist(String.valueOf(results.get(i).get("10日noon"))));
			cell314.setCellValue(checkDataExist(String.valueOf(results.get(i).get("10日night"))));
			cell115.setCellValue(checkDataExist(String.valueOf(results.get(i).get("11日morning"))));
			cell215.setCellValue(checkDataExist(String.valueOf(results.get(i).get("11日noon"))));
			cell315.setCellValue(checkDataExist(String.valueOf(results.get(i).get("11日night"))));
			cell116.setCellValue(checkDataExist(String.valueOf(results.get(i).get("12日morning"))));
			cell216.setCellValue(checkDataExist(String.valueOf(results.get(i).get("12日noon"))));
			cell316.setCellValue(checkDataExist(String.valueOf(results.get(i).get("12日night"))));
			cell117.setCellValue(checkDataExist(String.valueOf(results.get(i).get("13日morning"))));
			cell217.setCellValue(checkDataExist(String.valueOf(results.get(i).get("13日noon"))));
			cell317.setCellValue(checkDataExist(String.valueOf(results.get(i).get("13日night"))));
			cell118.setCellValue(checkDataExist(String.valueOf(results.get(i).get("14日morning"))));
			cell218.setCellValue(checkDataExist(String.valueOf(results.get(i).get("14日noon"))));
			cell318.setCellValue(checkDataExist(String.valueOf(results.get(i).get("14日night"))));
			cell119.setCellValue(checkDataExist(String.valueOf(results.get(i).get("15日morning"))));
			cell219.setCellValue(checkDataExist(String.valueOf(results.get(i).get("15日noon"))));
			cell319.setCellValue(checkDataExist(String.valueOf(results.get(i).get("15日night"))));
			cell120.setCellValue(checkDataExist(String.valueOf(results.get(i).get("16日morning"))));
			cell220.setCellValue(checkDataExist(String.valueOf(results.get(i).get("16日noon"))));
			cell320.setCellValue(checkDataExist(String.valueOf(results.get(i).get("16日night"))));
			cell121.setCellValue(checkDataExist(String.valueOf(results.get(i).get("17日morning"))));
			cell221.setCellValue(checkDataExist(String.valueOf(results.get(i).get("17日noon"))));
			cell321.setCellValue(checkDataExist(String.valueOf(results.get(i).get("17日night"))));
			cell122.setCellValue(checkDataExist(String.valueOf(results.get(i).get("18日morning"))));
			cell222.setCellValue(checkDataExist(String.valueOf(results.get(i).get("18日noon"))));
			cell322.setCellValue(checkDataExist(String.valueOf(results.get(i).get("18日night"))));
			cell123.setCellValue(checkDataExist(String.valueOf(results.get(i).get("19日morning"))));
			cell223.setCellValue(checkDataExist(String.valueOf(results.get(i).get("19日noon"))));
			cell323.setCellValue(checkDataExist(String.valueOf(results.get(i).get("19日night"))));
			cell124.setCellValue(checkDataExist(String.valueOf(results.get(i).get("20日morning"))));
			cell224.setCellValue(checkDataExist(String.valueOf(results.get(i).get("20日noon"))));
			cell324.setCellValue(checkDataExist(String.valueOf(results.get(i).get("20日night"))));
			cell125.setCellValue(checkDataExist(String.valueOf(results.get(i).get("21日morning"))));
			cell225.setCellValue(checkDataExist(String.valueOf(results.get(i).get("21日noon"))));
			cell325.setCellValue(checkDataExist(String.valueOf(results.get(i).get("21日night"))));
			cell126.setCellValue(checkDataExist(String.valueOf(results.get(i).get("22日morning"))));
			cell226.setCellValue(checkDataExist(String.valueOf(results.get(i).get("22日noon"))));
			cell326.setCellValue(checkDataExist(String.valueOf(results.get(i).get("22日night"))));
			cell127.setCellValue(checkDataExist(String.valueOf(results.get(i).get("23日morning"))));
			cell227.setCellValue(checkDataExist(String.valueOf(results.get(i).get("23日noon"))));
			cell327.setCellValue(checkDataExist(String.valueOf(results.get(i).get("23日night"))));
			cell128.setCellValue(checkDataExist(String.valueOf(results.get(i).get("24日morning"))));
			cell228.setCellValue(checkDataExist(String.valueOf(results.get(i).get("24日noon"))));
			cell328.setCellValue(checkDataExist(String.valueOf(results.get(i).get("24日night"))));
			cell129.setCellValue(checkDataExist(String.valueOf(results.get(i).get("25日morning"))));
			cell229.setCellValue(checkDataExist(String.valueOf(results.get(i).get("25日noon"))));
			cell329.setCellValue(checkDataExist(String.valueOf(results.get(i).get("25日night"))));
			cell130.setCellValue(checkDataExist(String.valueOf(results.get(i).get("26日morning"))));
			cell230.setCellValue(checkDataExist(String.valueOf(results.get(i).get("26日noon"))));
			cell330.setCellValue(checkDataExist(String.valueOf(results.get(i).get("26日night"))));
			cell131.setCellValue(checkDataExist(String.valueOf(results.get(i).get("27日morning"))));
			cell231.setCellValue(checkDataExist(String.valueOf(results.get(i).get("27日noon"))));
			cell331.setCellValue(checkDataExist(String.valueOf(results.get(i).get("27日night"))));
			cell132.setCellValue(checkDataExist(String.valueOf(results.get(i).get("28日morning"))));
			cell232.setCellValue(checkDataExist(String.valueOf(results.get(i).get("28日noon"))));
			cell332.setCellValue(checkDataExist(String.valueOf(results.get(i).get("28日night"))));
			cell133.setCellValue(checkDataExist(String.valueOf(results.get(i).get("29日morning"))));
			cell233.setCellValue(checkDataExist(String.valueOf(results.get(i).get("29日noon"))));
			cell333.setCellValue(checkDataExist(String.valueOf(results.get(i).get("29日night"))));
			cell134.setCellValue(checkDataExist(String.valueOf(results.get(i).get("30日morning"))));
			cell234.setCellValue(checkDataExist(String.valueOf(results.get(i).get("30日noon"))));
			cell334.setCellValue(checkDataExist(String.valueOf(results.get(i).get("30日night"))));
			cell135.setCellValue(checkDataExist(String.valueOf(results.get(i).get("31日morning"))));
			cell235.setCellValue(checkDataExist(String.valueOf(results.get(i).get("31日noon"))));
			cell335.setCellValue(checkDataExist(String.valueOf(results.get(i).get("31日night"))));
			
			int xue = 0;
			for(int j = 5 ; j<36 ; j++){
				if("学".equals(row1.getCell(j).getStringCellValue())||"学".equals(row2.getCell(j).getStringCellValue())||"学".equals(row3.getCell(j).getStringCellValue())){
					xue += 1;
				}
//				if("学".equals(row2.getCell(j).getStringCellValue())){
//					xue += 1;
//				}
//				if("学".equals(row3.getCell(j).getStringCellValue())){
//					xue += 1;
//				}
			}
			if(xue==0){
				cell036.setCellValue("");
			}else{
				cell036.setCellValue(String.valueOf(xue));
			}
			
			int chai = 0;
			for(int j = 5 ; j<36 ; j++){
				if("差".equals(row1.getCell(j).getStringCellValue())||"差".equals(row2.getCell(j).getStringCellValue())||"差".equals(row3.getCell(j).getStringCellValue())){
					chai += 1;
				}
//				if("差".equals(row2.getCell(j).getStringCellValue())){
//					chai += 1;
//				}
//				if("差".equals(row3.getCell(j).getStringCellValue())){
//					chai += 1;
//				}
			}
			if(chai==0){
				cell037.setCellValue("");
			}else{
				cell037.setCellValue(String.valueOf(chai));
			}
			
			int xiaoyeban = 0;
			for(int j = 5 ; j<36 ; j++){
				if("×".equals(row1.getCell(j).getStringCellValue())||"×".equals(row2.getCell(j).getStringCellValue())||"×".equals(row3.getCell(j).getStringCellValue())){
					xiaoyeban += 1;
				}
//				if("×".equals(row2.getCell(j).getStringCellValue())){
//					xiaoyeban += 1;
//				}
//				if("×".equals(row3.getCell(j).getStringCellValue())){
//					xiaoyeban += 1;
//				}
			}
			if(xiaoyeban==0){
				cell038.setCellValue("");
			}else{
				cell038.setCellValue(String.valueOf(xiaoyeban));
			}
			
			int dayeban = 0;
			for(int j = 5 ; j<36 ; j++){
				if("*".equals(row1.getCell(j).getStringCellValue())||"*".equals(row2.getCell(j).getStringCellValue())||"*".equals(row3.getCell(j).getStringCellValue())){
					dayeban += 1;
				}
//				if("*".equals(row2.getCell(j).getStringCellValue())){
//					dayeban += 1;
//				}
//				if("*".equals(row3.getCell(j).getStringCellValue())){
//					dayeban += 1;
//				}
			}
			if(dayeban==0){
				cell039.setCellValue("");
			}else{
				cell039.setCellValue(String.valueOf(dayeban));
			}
			
			cell040.setCellValue(
					checkDoubleZero(Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("1日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("2日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("3日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("4日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("5日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("6日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("7日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("8日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("9日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("10日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("11日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("12日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("13日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("14日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("15日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("16日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("17日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("18日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("19日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("20日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("21日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("22日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("23日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("24日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("25日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("26日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("27日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("28日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("29日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("30日daily")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("31日daily")))))));
			cell041.setCellValue(
					checkDoubleZero(Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("1日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("2日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("3日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("4日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("5日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("6日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("7日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("8日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("9日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("10日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("11日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("12日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("13日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("14日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("15日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("16日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("17日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("18日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("19日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("20日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("21日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("22日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("23日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("24日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("25日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("26日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("27日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("28日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("29日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("30日turn")))))+
							Double.valueOf(checkValue(checkDataExist(String.valueOf(results.get(i).get("31日turn")))))));
			
			int nianxiu = 0;
			for(int j = 5 ; j<36 ; j++){
				if("年".equals(row1.getCell(j).getStringCellValue())||"年".equals(row2.getCell(j).getStringCellValue())||"年".equals(row3.getCell(j).getStringCellValue())){
					nianxiu += 1;
				}
//				if("年".equals(row2.getCell(j).getStringCellValue())){
//					nianxiu += 1;
//				}
//				if("年".equals(row3.getCell(j).getStringCellValue())){
//					nianxiu += 1;
//				}
			}
			if(nianxiu==0){
				cell042.setCellValue("");
			}else{
				cell042.setCellValue(String.valueOf(nianxiu));
			}
			
			int tanqin = 0;
			for(int j = 5 ; j<36 ; j++){
				if("探".equals(row1.getCell(j).getStringCellValue())||"探".equals(row2.getCell(j).getStringCellValue())||"探".equals(row3.getCell(j).getStringCellValue())){
					tanqin += 1;
				}
//				if("探".equals(row2.getCell(j).getStringCellValue())){
//					tanqin += 1;
//				}
//				if("探".equals(row3.getCell(j).getStringCellValue())){
//					tanqin += 1;
//				}
			}
			if(tanqin==0){
				cell043.setCellValue("");
			}else{
				cell043.setCellValue(String.valueOf(tanqin));
			}
			
			int hunjia = 0;
			for(int j = 5 ; j<36 ; j++){
				if("婚".equals(row1.getCell(j).getStringCellValue())||"婚".equals(row2.getCell(j).getStringCellValue())||"婚".equals(row3.getCell(j).getStringCellValue())){
					hunjia += 1;
				}
//				if("婚".equals(row2.getCell(j).getStringCellValue())){
//					hunjia += 1;
//				}
//				if("婚".equals(row3.getCell(j).getStringCellValue())){
//					hunjia += 1;
//				}
			}
			if(hunjia==0){
				cell044.setCellValue("");
			}else{
				cell044.setCellValue(String.valueOf(hunjia));
			}
			
			int sangjia = 0;
			for(int j = 5 ; j<36 ; j++){
				if("丧".equals(row1.getCell(j).getStringCellValue())||"丧".equals(row2.getCell(j).getStringCellValue())||"丧".equals(row3.getCell(j).getStringCellValue())){
					sangjia += 1;
				}
//				if("丧".equals(row2.getCell(j).getStringCellValue())){
//					sangjia += 1;
//				}
//				if("丧".equals(row3.getCell(j).getStringCellValue())){
//					sangjia += 1;
//				}
			}
			if(sangjia==0){
				cell045.setCellValue("");
			}else{
				cell045.setCellValue(String.valueOf(sangjia));
			}
			
			int shijia = 0;
			for(int j = 5 ; j<36 ; j++){
				if("事".equals(row1.getCell(j).getStringCellValue())||"事".equals(row2.getCell(j).getStringCellValue())||"事".equals(row3.getCell(j).getStringCellValue())){
					shijia += 1;
				}
//				if("事".equals(row2.getCell(j).getStringCellValue())){
//					shijia += 1;
//				}
//				if("事".equals(row3.getCell(j).getStringCellValue())){
//					shijia += 1;
//				}
			}
			if(shijia==0){
				cell046.setCellValue("");
			}else{
				cell046.setCellValue(String.valueOf(shijia));
			}
			
			int bingjia = 0;
			for(int j = 5 ; j<36 ; j++){
				if("病".equals(row1.getCell(j).getStringCellValue())||"病".equals(row2.getCell(j).getStringCellValue())||"病".equals(row3.getCell(j).getStringCellValue())){
					bingjia += 1;
				}
//				if("病".equals(row2.getCell(j).getStringCellValue())){
//					bingjia += 1;
//				}
//				if("病".equals(row3.getCell(j).getStringCellValue())){
//					bingjia += 1;
//				}
			}
			if(bingjia==0){
				cell047.setCellValue("");
			}else{
				cell047.setCellValue(String.valueOf(bingjia));
			}
			
			int zhuyuan = 0;
			for(int j = 5 ; j<36 ; j++){
				if("住".equals(row1.getCell(j).getStringCellValue())||"住".equals(row2.getCell(j).getStringCellValue())||"住".equals(row3.getCell(j).getStringCellValue())){
					zhuyuan += 1;
				}
//				if("住".equals(row2.getCell(j).getStringCellValue())){
//					zhuyuan += 1;
//				}
//				if("住".equals(row3.getCell(j).getStringCellValue())){
//					zhuyuan += 1;
//				}
			}
			if(zhuyuan==0){
				cell048.setCellValue("");
			}else{
				cell048.setCellValue(String.valueOf(zhuyuan));
			}
			
			int chanjia = 0;
			for(int j = 5 ; j<36 ; j++){
				if("产".equals(row1.getCell(j).getStringCellValue())||"产".equals(row2.getCell(j).getStringCellValue())||"产".equals(row3.getCell(j).getStringCellValue())){
					chanjia += 1;
				}
//				if("产".equals(row2.getCell(j).getStringCellValue())){
//					chanjia += 1;
//				}
//				if("产".equals(row3.getCell(j).getStringCellValue())){
//					chanjia += 1;
//				}
			}
			if(chanjia==0){
				cell049.setCellValue("");
			}else{
				cell049.setCellValue(String.valueOf(chanjia));
			}
			
			int shengji = 0;
			for(int j = 5 ; j<36 ; j++){
				if("计".equals(row1.getCell(j).getStringCellValue())||"计".equals(row2.getCell(j).getStringCellValue())||"计".equals(row3.getCell(j).getStringCellValue())){
					shengji += 1;
				}
//				if("计".equals(row2.getCell(j).getStringCellValue())){
//					shengji += 1;
//				}
//				if("计".equals(row3.getCell(j).getStringCellValue())){
//					shengji += 1;
//				}
			}
			if(shengji==0){
				cell050.setCellValue("");
			}else{
				cell050.setCellValue(String.valueOf(shengji));
			}
			
			int huli = 0;
			for(int j = 5 ; j<36 ; j++){
				if("护".equals(row1.getCell(j).getStringCellValue())||"护".equals(row2.getCell(j).getStringCellValue())||"护".equals(row3.getCell(j).getStringCellValue())){
					huli += 1;
				}
//				if("护".equals(row2.getCell(j).getStringCellValue())){
//					huli += 1;
//				}
//				if("护".equals(row3.getCell(j).getStringCellValue())){
//					huli += 1;
//				}
			}
			if(huli==0){
				cell051.setCellValue("");
			}else{
				cell051.setCellValue(String.valueOf(huli));
			}
			
			int xigong = 0;
			for(int j = 5 ; j<36 ; j++){
				if("息".equals(row1.getCell(j).getStringCellValue())||"息".equals(row2.getCell(j).getStringCellValue())||"息".equals(row3.getCell(j).getStringCellValue())){
					xigong += 1;
				}
//				if("息".equals(row2.getCell(j).getStringCellValue())){
//					xigong += 1;
//				}
//				if("息".equals(row3.getCell(j).getStringCellValue())){
//					xigong += 1;
//				}
			}
			if(xigong==0){
				cell052.setCellValue("");
			}else{
				cell052.setCellValue(String.valueOf(xigong));
			}
			
			int kuanggong = 0;
			for(int j = 5 ; j<36 ; j++){
				if("旷".equals(row1.getCell(j).getStringCellValue())||"旷".equals(row2.getCell(j).getStringCellValue())||"旷".equals(row3.getCell(j).getStringCellValue())){
					kuanggong += 1;
				}
//				if("旷".equals(row2.getCell(j).getStringCellValue())){
//					kuanggong += 1;
//				}
//				if("旷".equals(row3.getCell(j).getStringCellValue())){
//					kuanggong += 1;
//				}
			}
			if(kuanggong==0){
				cell053.setCellValue("");
			}else{
				cell053.setCellValue(String.valueOf(kuanggong));
			}
			
			if(nianxiu+tanqin+hunjia+sangjia+shijia+bingjia+zhuyuan+chanjia+shengji+huli+xigong+kuanggong==0){
				cell054.setCellValue("");
			}else{
				cell054.setCellValue(String.valueOf(nianxiu+tanqin+hunjia+sangjia+shijia+bingjia+zhuyuan+chanjia+shengji+huli+xigong+kuanggong));
			}
			/**
			 *  1、符号规定：
			 *  白班（／）、
			 *  小夜班（×）、
			 *  大夜班（*）、
			 *  休班（○）、
			 *  补休（补）、
			 *  学习（学）、
			 *  公差（差）、
			 *  探亲（探）、
			 *  年休（年）、
			 *  婚假（婚）、
			 *  丧假（丧）、
			 *  病假（病）、
			 *  住院（住）、
			 *  事假（事）、
			 *  产假（产）、
			 *  护理（护）、
			 *  息工假（息）、
			 *  计生假（计）、
			 *  旷工（旷）。
			 */
		}
		
		int Value36All = 0;
		int Value37All = 0;
		int Value38All = 0;
		int Value39All = 0;
		double Value40All = 0;
		double Value41All = 0;
		int Value42All = 0;
		int Value43All = 0;
		int Value44All = 0;
		int Value45All = 0;
		int Value46All = 0;
		int Value47All = 0;
		int Value48All = 0;
		int Value49All = 0;
		int Value50All = 0;
		int Value51All = 0;
		int Value52All = 0;
		int Value53All = 0;
		int Value54All = 0;
		
		for (int i = 0 ; i < results.size() ; i++) {
			XSSFRow row1 = s.getRow(5+i*3);
			XSSFRow row2 = s.getRow(6+i*3);
			XSSFRow row3 = s.getRow(7+i*3);
			XSSFRow rowAll = s.getRow(5+results.size()*3);//横向合计那行
			XSSFCell cell036 = row1.getCell(36);//学习
			XSSFCell cell037 = row1.getCell(37);//公差
			XSSFCell cell038 = row1.getCell(38);//夜班（小）
			XSSFCell cell039 = row1.getCell(39);//夜班（大）
			XSSFCell cell040 = row1.getCell(40);//加班（日勤）
			XSSFCell cell041 = row1.getCell(41);//加班（轮班）
			XSSFCell cell042 = row1.getCell(42);//年休
			XSSFCell cell043 = row1.getCell(43);//探亲
			XSSFCell cell044 = row1.getCell(44);//婚假
			XSSFCell cell045 = row1.getCell(45);//丧假
			XSSFCell cell046 = row1.getCell(46);//事假
			XSSFCell cell047 = row1.getCell(47);//病假
			XSSFCell cell048 = row1.getCell(48);//住院
			XSSFCell cell049 = row1.getCell(49);//产假
			XSSFCell cell050 = row1.getCell(50);//计生
			XSSFCell cell051 = row1.getCell(51);//护理
			XSSFCell cell052 = row1.getCell(52);//息工
			XSSFCell cell053 = row1.getCell(53);//旷工
			XSSFCell cell054 = row1.getCell(54);//合计
			
			String Value36 = cell036.getStringCellValue();
			Value36All += Integer.valueOf(checkValue(Value36));
			rowAll.getCell(36).setCellValue(checkZero(Value36All));
			
			String Value37 = cell037.getStringCellValue();
			Value37All += Integer.valueOf(checkValue(Value37));
			rowAll.getCell(37).setCellValue(checkZero(Value37All));
			
			String Value38 = cell038.getStringCellValue();
			Value38All += Integer.valueOf(checkValue(Value38));
			rowAll.getCell(38).setCellValue(checkZero(Value38All));
			
			String Value39 = cell039.getStringCellValue();
			Value39All += Integer.valueOf(checkValue(Value39));
			rowAll.getCell(39).setCellValue(checkZero(Value39All));
			
			String Value40 = cell040.getStringCellValue();
			Value40All += Double.valueOf(checkValue(Value40));
			rowAll.getCell(40).setCellValue(checkDoubleZero(Value40All));
			
			String Value41 = cell041.getStringCellValue();
			Value41All += Double.valueOf(checkValue(Value41));
			rowAll.getCell(41).setCellValue(checkDoubleZero(Value41All));
			
			String Value42 = cell042.getStringCellValue();
			Value42All += Integer.valueOf(checkValue(Value42));
			rowAll.getCell(42).setCellValue(checkZero(Value42All));
			
			String Value43 = cell043.getStringCellValue();
			Value43All += Integer.valueOf(checkValue(Value43));
			rowAll.getCell(43).setCellValue(checkZero(Value43All));
			
			String Value44 = cell044.getStringCellValue();
			Value44All += Integer.valueOf(checkValue(Value44));
			rowAll.getCell(44).setCellValue(checkZero(Value44All));
			
			String Value45 = cell045.getStringCellValue();
			Value45All += Integer.valueOf(checkValue(Value45));
			rowAll.getCell(45).setCellValue(checkZero(Value45All));
			
			String Value46 = cell046.getStringCellValue();
			Value46All += Integer.valueOf(checkValue(Value46));
			rowAll.getCell(46).setCellValue(checkZero(Value46All));
			
			String Value47 = cell047.getStringCellValue();
			Value47All += Integer.valueOf(checkValue(Value47));
			rowAll.getCell(47).setCellValue(checkZero(Value47All));
			
			String Value48 = cell048.getStringCellValue();
			Value48All += Integer.valueOf(checkValue(Value48));
			rowAll.getCell(48).setCellValue(checkZero(Value48All));
			
			String Value49 = cell049.getStringCellValue();
			Value49All += Integer.valueOf(checkValue(Value49));
			rowAll.getCell(49).setCellValue(checkZero(Value49All));
			
			String Value50 = cell050.getStringCellValue();
			Value50All += Integer.valueOf(checkValue(Value50));
			rowAll.getCell(50).setCellValue(checkZero(Value50All));
			
			String Value51 = cell051.getStringCellValue();
			Value51All += Integer.valueOf(checkValue(Value51));
			rowAll.getCell(51).setCellValue(checkZero(Value51All));
			
			String Value52 = cell052.getStringCellValue();
			Value52All += Integer.valueOf(checkValue(Value52));
			rowAll.getCell(52).setCellValue(checkZero(Value52All));
			
			String Value53 = cell053.getStringCellValue();
			Value53All += Integer.valueOf(checkValue(Value53));
			rowAll.getCell(53).setCellValue(checkZero(Value53All));
			
			String Value54 = cell054.getStringCellValue();
			Value54All += Integer.valueOf(checkValue(Value54));
			rowAll.getCell(54).setCellValue(checkZero(Value54All));
		}
		
	}
	
	private String checkValue(String value){
		if("".equals(value)){
			return "0";
		}
		return value;
	}
	private String checkZero(Integer value){
		if(value==0){
			return "";
		}
		return String.valueOf(value);
	}
	private String checkDoubleZero(Double value){
		if(value==0){
			return "";
		}
		return String.format("%.2f",value);
	}
	
	public static void copyRow(final XSSFRow sourceRow,final XSSFRow targetRow) {
//		targetRow.setHeight(sourceRow.getHeight());//不设置行高（为了在单元格内填入数据时能根据内容多少自动调整行高）
		IntStream.range(0, sourceRow.getPhysicalNumberOfCells()).forEach(i -> {
			XSSFCell sourceCell = sourceRow.getCell(i);
			if(sourceCell == null) return;
			XSSFCell targetCell = targetRow.createCell(i);
			copyCellStyleAndFont(sourceCell,targetCell);
		});
		copyMergeArea(sourceRow,targetRow);
	}
	
	public static void copyCellStyleAndFont(XSSFCell sourceCell,XSSFCell targetCell) {
		Map<String, Object> styleMap = getCopyStyle(sourceCell.getCellStyle());
		CellUtil.setCellStyleProperties(targetCell, styleMap);
		CellUtil.setFont(targetCell, sourceCell.getSheet().getWorkbook().getFontAt(sourceCell.getCellStyle().getFontIndex()));
	}
	
	private static void copyMergeArea(XSSFRow sourceRow, XSSFRow targetRow) {
		List<CellRangeAddress> sourceMerge = hasMergeArea(sourceRow);
		if(CollectionUtils.isEmpty(sourceMerge)) return;
		XSSFSheet sheet = targetRow.getSheet();
		sourceMerge.stream().forEach(s -> {
			sheet.addMergedRegion(new CellRangeAddress(
					targetRow.getRowNum(), 
					targetRow.getRowNum(), 
					s.getFirstColumn(), 
					s.getLastColumn()));
		});
	}
	
	private static List<CellRangeAddress> hasMergeArea(XSSFRow sourceRow) {
		return sourceRow.getSheet().getMergedRegions().stream()
		.filter(s -> 
			s.getFirstRow() == sourceRow.getRowNum() &&
			s.getLastRow() == sourceRow.getRowNum()
		).collect(Collectors.toList());
	}
	
	public static Map<String, Object> getCopyStyle(CellStyle cellStyle) {
        if(cellStyle == null) {
            return new HashMap<String, Object>();
        }   
        /*  
         * 不使用“newCellStyle.cloneStyleFrom(cellStyle)”样式拷贝的原因：
         * Office Excel 中弹出提示信息： 此文件中的某些文本格式可能已经更改，因为它已经超出最多允许字体数。关闭其他文档再试一次可能有用。文件错误。数据可能丢失。
         * WPS Excel 正常
         * 
         * 解决方案：每个样式逐一拷贝
         */
        Map<String, Object> styleMap = new HashMap<String, Object>();
        // 是否换行
        styleMap.put(CellUtil.WRAP_TEXT, true);
        // 单元格边框样式
        styleMap.put(CellUtil.BORDER_BOTTOM, cellStyle.getBorderBottomEnum().getCode());
        styleMap.put(CellUtil.BORDER_LEFT, cellStyle.getBorderLeftEnum().getCode());
        styleMap.put(CellUtil.BORDER_RIGHT, cellStyle.getBorderRightEnum().getCode());
        styleMap.put(CellUtil.BORDER_TOP, cellStyle.getBorderTopEnum().getCode());
        // 单元格背景颜色
        styleMap.put(CellUtil.FILL_PATTERN, cellStyle.getFillPatternEnum().getCode());
        styleMap.put(CellUtil.FILL_FOREGROUND_COLOR, cellStyle.getFillForegroundColor());
        // 单元格平水和垂直对齐方式                                                                                                                                          
        styleMap.put(CellUtil.ALIGNMENT, cellStyle.getAlignmentEnum().getCode());
        styleMap.put(CellUtil.VERTICAL_ALIGNMENT, cellStyle.getVerticalAlignmentEnum().getCode());
        return styleMap;
    }


	@Override
	public long findAllDataCount(String attendanceManageCollect, String orgId, int orgType, String startUploadDate,String endUploadDate) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getDataFilter(orgId,orgType,startUploadDate,endUploadDate);
            return  md.getCollection(attendanceManageCollect).count(filter);
        }
	}


	@Override
	public List<Document> findAllData(String attendanceManageCollect, String orgId, int orgType, String startUploadDate, String endUploadDate, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getDataFilter(orgId,orgType,startUploadDate,endUploadDate);
			FindIterable<Document> findIterable = md.getCollection(attendanceManageCollect).find(filter).skip(start).limit(limit).sort(new Document("createTime", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
				 	d.append("f&w&a",d.getString("flowState")+","+d.getString("workshopQueryData")+","+d.getString("attendanceOrgId"));
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	
	private Bson getDataFilter(String orgId, int orgType, String startUploadDate, String endUploadDate) {
		Bson filters=Filters.eq("status",1);
		if(orgType==1500){
			filters=filters;
		}else if(orgType==1502){
			filters=Filters.and(filters,Filters.eq("workshopQueryData",orgId));
		}else if(orgType==1501&&"402891b45b5fd02c015b74c913260035".equals(orgId)){//办公室
			filters=Filters.and(filters,Filters.eq("workshopQueryData",orgId));
		}else if(orgType==1501&&"402891b45b5fd02c015b75395c3a0099".equals(orgId)){//职教科
			filters=Filters.and(filters,Filters.eq("workshopQueryData",orgId));
		}else{
			filters=Filters.and(filters,Filters.eq("attendanceOrgId",orgId));
		}
		if(StringUtils.isNotBlank(startUploadDate)) {
			filters=Filters.and(filters,Filters.gte("createTime",startUploadDate));
		}
		if(StringUtils.isNotBlank(endUploadDate)) {
			filters=Filters.and(filters,Filters.lte("createTime",endUploadDate));
		}
		return filters;
	}


	@Override
	public long getDataByYearMonthOrg(String year, String month, String attendanceOrgName, String attendanceManageCollect) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filters=Filters.eq("status",1);
            filters = Filters.and(filters,Filters.eq("date",year+month));
            filters = Filters.and(filters,Filters.eq("attendanceOrgName",attendanceOrgName));
            return  md.getCollection(attendanceManageCollect).count(filters);
        }
	}


	@Override
	public long checkStatusByIds(List<String> ids,String collectionName) {
		List<ObjectId> objIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
		List<Document> results=new ArrayList<>();
		Document query = new Document("_id",new Document("$in",objIds));
        try(MongoClient mc = new MongoClient(mongoHost, mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(query.append("status",1));
            findIterable.forEach((Block<? super Document>) results::add);
			results.stream().forEach(d -> {
				d.append("docId", d.getObjectId("_id").toHexString());
				d.remove("_id");
			});
        }
        long count = 0L;
        for (Document document : results) {
			if("-1".equals(String.valueOf(document.get("flowState")))){
				count++;
			}
		}
		return count;
	}
}