package com.enovell.yunwei.km_micor_service.service.integratedManage.attendanceManage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

import com.enovell.yunwei.km_micor_service.dto.AttendanceSumCountDto;
import com.enovell.yunwei.km_micor_service.dto.AttendanceSumDto;
import com.enovell.yunwei.km_micor_service.util.ReadExcelAttendance;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Service("attendanceManageCollectQueryService")
public class AttendanceManageCollectQueryServiceImpl implements AttendanceManageCollectQueryService {
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
			FindIterable<Document> findIterable = md.getCollection(attendanceManageCollect).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	
	private Bson getDataFilter(String orgId, int orgType, String startUploadDate, String endUploadDate) {
		Bson filters=Filters.eq("status",1);
		filters=Filters.and(filters,Filters.in("flowState","1","-1"));
		if(StringUtils.isNotBlank(startUploadDate)) {
			filters=Filters.and(filters,Filters.gte("createTime",startUploadDate));
		}
		if(StringUtils.isNotBlank(endUploadDate)) {
			filters=Filters.and(filters,Filters.lte("createTime",endUploadDate));
		}
		return filters;
	}
	
	private List<Document> findAllDocumentByDateAndOrg(String attendanceManageSum,String date,String attendanceOrgName,String attendanceOrgId){
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			if(StringUtils.isNotBlank(date)) {
				filters=Filters.and(filters,Filters.eq("yearmonth",date));
			}
			if(StringUtils.isNotBlank(attendanceOrgId)) {
				filters=Filters.and(filters,Filters.eq("orgId",attendanceOrgId));
			}
			FindIterable<Document> findIterable = md.getCollection(attendanceManageSum).find(filters).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
			 });
		}
		return results;
	}
	
	private Document addDocument(Document doc, String collectionName) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
			 MongoDatabase md = mc.getDatabase(mongoDatabase);
			 doc.append("status",1);
			 md.getCollection(collectionName).insertOne(doc);
        }
        return doc;
	}
	
	private void removeDocument(List<Document> listAll,String date,String attendanceOrgName, String attendanceOrgId, String collectionName) {
		 try(MongoClient mc = new MongoClient(mongoHost, mongoPort)){
			 Bson filters=Filters.eq("status",1);
			 if(StringUtils.isNotBlank(date)) {
				 filters=Filters.and(filters,Filters.eq("yearmonth",date));
			 }
			 if(StringUtils.isNotBlank(attendanceOrgId)) {
				 filters=Filters.and(filters,Filters.eq("orgId",attendanceOrgId));
			 }
			 MongoDatabase md = mc.getDatabase(mongoDatabase);
			 md.getCollection(collectionName).deleteMany(filters);
		 }
	}

	@Override
	public void importSumData(String filePath,String date,String attendanceOrgName, String attendanceOrgId) {
		try {
			
			ReadExcelAttendance readExcel =new ReadExcelAttendance();
	    	 
			List<AttendanceSumDto> fdpList = readExcel.getExcelInfo(filePath,date,attendanceOrgName,attendanceOrgId);
			 
			List<Document> listAll = findAllDocumentByDateAndOrg("attendanceManageSum", date, attendanceOrgName , attendanceOrgId);
			
			removeDocument(listAll,date,attendanceOrgName,attendanceOrgId, "attendanceManageSum");
	    	 
			fdpList.stream().forEach(s->{
				String collectionName ="attendanceManageSum";
	    	    Document document = new Document();
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
	    		String createDateStr = "";
	    		try {
	    			createDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("createDate", new Date());
	    		document.put("createDateStr", createDateStr);
	    		document.put("yearmonth", s.getYearmonth());
	    		document.put("orgName", s.getOrgName());
	    		document.put("orgId", s.getOrgId());
	    		document.put("number", s.getNumber());
	    		document.put("peopleName", s.getPeopleName());
	    		document.put("postName", s.getPostName());
	    		document.put("xuexi", s.getXuexi());
	    		document.put("gongchai", s.getGongchai());
	    		document.put("yebanxiao", s.getYebanxiao());
	    		document.put("yebanda", s.getYebanda());
	    		document.put("riqin", s.getRiqin());
	    		document.put("lunban", s.getLunban());
	    		document.put("nianxiu", s.getNianxiu());
	    		document.put("tanqin", s.getTanqin());
	    		document.put("hunjia", s.getHunjia());
	    		document.put("sangjia", s.getSangjia());
	    		document.put("shijia", s.getShijia());
	    		document.put("bingjia", s.getBingjia());
	    		document.put("zhuyuan", s.getZhuyuan());
	    		document.put("chanjia", s.getChanjia());
	    		document.put("jisheng", s.getJisheng());
	    		document.put("huli", s.getHuli());
	    		document.put("xigong", s.getXigong());
	    		document.put("kuanggong", s.getKuanggong());
	    		document.put("total", s.getTotal());
	    		addDocument(document, collectionName);
	    	 });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRange(XSSFSheet s, List<AttendanceSumCountDto> results) {
		int rows =results.size()-1;
		int lastRowNum = s.getLastRowNum();
		//将第8行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
		s.shiftRows(5, 6, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow1=s.getRow(4+i);//从row5开始复制
			XSSFRow targetRow1=s.createRow(5+i);//从row6开始粘贴
			copyRow(sourceRow1, targetRow1);
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(5+lastRowNum+rows, 6+lastRowNum+rows, shiftUpRow, true, false);
		for (int i = 0 ; i < results.size() ; i++) {
			XSSFRow row = s.getRow(i+4);
			XSSFCell cell0 = row.getCell(0);//序号
			XSSFCell cell1 = row.getCell(1);//所属部门
			XSSFCell cell2 = row.getCell(2);//劳资号
			XSSFCell cell3 = row.getCell(3);//姓名
			XSSFCell cell4 = row.getCell(4);//职位
			XSSFCell cell5 = row.getCell(5);//学习
			XSSFCell cell6 = row.getCell(6);//公差
			XSSFCell cell7 = row.getCell(7);//夜班（小）
			XSSFCell cell8 = row.getCell(8);//夜班（大）
			XSSFCell cell9 = row.getCell(9);//加班（日勤）
			XSSFCell cell10 = row.getCell(10);//加班（轮班）
			XSSFCell cell11 = row.getCell(11);//年休
			XSSFCell cell12 = row.getCell(12);//探亲
			XSSFCell cell13 = row.getCell(13);//婚假
			XSSFCell cell14 = row.getCell(14);//丧假
			XSSFCell cell15 = row.getCell(15);//事假
			XSSFCell cell16 = row.getCell(16);//病假
			XSSFCell cell17 = row.getCell(17);//住院
			XSSFCell cell18 = row.getCell(18);//产假
			XSSFCell cell19 = row.getCell(19);//计生
			XSSFCell cell20 = row.getCell(20);//护理
			XSSFCell cell21 = row.getCell(21);//息工
			XSSFCell cell22 = row.getCell(22);//旷工
			XSSFCell cell23 = row.getCell(23);//合计
			cell0.setCellValue(String.valueOf(1+i));
			cell1.setCellValue(String.valueOf(results.get(i).getOrgName()));
			cell2.setCellValue(String.valueOf(results.get(i).getNumber()));
			cell3.setCellValue(String.valueOf(results.get(i).getPeopleName()));
			cell4.setCellValue(String.valueOf(results.get(i).getPostName()));
			Double xuexi = results.get(i).getXuexi();
			if(xuexi==0){
				cell5.setCellValue("");
			}else{
				cell5.setCellValue(xuexi);
			}
			Double gongchai = results.get(i).getGongchai();
			if(gongchai==0){
				cell6.setCellValue("");
			}else{
				cell6.setCellValue(gongchai);
			}
			Double yebanxiao = results.get(i).getYebanxiao();
			if(yebanxiao==0){
				cell7.setCellValue("");
			}else{
				cell7.setCellValue(yebanxiao);
			}
			Double yebanda = results.get(i).getYebanda();
			if(yebanda==0){
				cell8.setCellValue("");
			}else{
				cell8.setCellValue(yebanda);
			}
			Double riqin = results.get(i).getRiqin();
			if(riqin==0){
				cell9.setCellValue("");
			}else{
				cell9.setCellValue(riqin);
			}
			Double lunban = results.get(i).getLunban();
			if(lunban==0){
				cell10.setCellValue("");
			}else{
				cell10.setCellValue(lunban);
			}
			Double nianxiu = results.get(i).getNianxiu();
			if(nianxiu==0){
				cell11.setCellValue("");
			}else{
				cell11.setCellValue(nianxiu);
			}
			Double tanqin = results.get(i).getTanqin();
			if(tanqin==0){
				cell12.setCellValue("");
			}else{
				cell12.setCellValue(tanqin);
			}
			Double hunjia = results.get(i).getHunjia();
			if(hunjia==0){
				cell13.setCellValue("");
			}else{
				cell13.setCellValue(hunjia);
			}
			Double sangjia = results.get(i).getSangjia();
			if(sangjia==0){
				cell14.setCellValue("");
			}else{
				cell14.setCellValue(sangjia);
			}
			Double shijia = results.get(i).getShijia();
			if(shijia==0){
				cell15.setCellValue("");
			}else{
				cell15.setCellValue(shijia);
			}
			Double bingjia = results.get(i).getBingjia();
			if(bingjia==0){
				cell16.setCellValue("");
			}else{
				cell16.setCellValue(bingjia);
			}
			Double zhuyuan = results.get(i).getZhuyuan();
			if(zhuyuan==0){
				cell17.setCellValue("");
			}else{
				cell17.setCellValue(zhuyuan);
			}
			Double chanjia = results.get(i).getChanjia();
			if(chanjia==0){
				cell18.setCellValue("");
			}else{
				cell18.setCellValue(chanjia);
			}
			Double jisheng = results.get(i).getJisheng();
			if(jisheng==0){
				cell19.setCellValue("");
			}else{
				cell19.setCellValue(jisheng);
			}
			Double huli = results.get(i).getHuli();
			if(huli==0){
				cell20.setCellValue("");
			}else{
				cell20.setCellValue(huli);
			}
			Double xigong = results.get(i).getXigong();
			if(xigong==0){
				cell21.setCellValue("");
			}else{
				cell21.setCellValue(xigong);
			}
			Double kuanggong = results.get(i).getKuanggong();
			if(kuanggong==0){
				cell22.setCellValue("");
			}else{
				cell22.setCellValue(kuanggong);
			}
			Double total = results.get(i).getTotal();
			if(total==0){
				cell23.setCellValue("");
			}else{
				cell23.setCellValue(total);
			}
		}
	}
	
	private void insertRangeData(XSSFSheet s,String title, List<AttendanceSumCountDto> results){
		XSSFRow row0 = s.getRow(0);
		XSSFCell cell0 = row0.getCell(0);//标题
		cell0.setCellValue(String.valueOf(title));
		insertRange(s, results);
	}
	
	private String createRangeTable(String title, List<AttendanceSumCountDto> results){
		String createTablePath = createPath + System.currentTimeMillis() + ".xlsx";
		FileInputStream fis = null;
		FileOutputStream fos = null;
		XSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(attendanceModelPath+"attendancehz.xlsx");
			wb =  new XSSFWorkbook(fis);
			XSSFSheet s = wb.getSheetAt(0);
			fos = new FileOutputStream(createTablePath);
			insertRangeData(s, title, results);
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
	
	private List<AttendanceSumCountDto> getMonthRangeData(String year, List<String> month, String attendanceManageSum){
		List<String> yearmonth = new ArrayList<String>();
		for (String string : month) {
			yearmonth.add(year+string);
		}
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			filters=Filters.and(filters,Filters.in("yearmonth",yearmonth));
			FindIterable<Document> findIterable = md.getCollection(attendanceManageSum).find(filters).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			results.stream().forEach(d-> {
				d.append("docId",d.getObjectId("_id").toHexString());
				d.remove("_id");
			});
		}
		List<AttendanceSumCountDto> aList = new ArrayList<AttendanceSumCountDto>();
		for (Document document : results) {//将Document转换成AttendanceSumDto对象
			AttendanceSumCountDto dto = new AttendanceSumCountDto();
			dto.setOrgId(document.getString("orgId"));
			dto.setOrgName(document.getString("orgName"));
			dto.setNumber(document.getString("number"));
			dto.setPeopleName(document.getString("peopleName"));
			dto.setPostName(document.getString("postName"));
			dto.setYearmonth(document.getString("yearmonth"));
			dto.setXuexi(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("xuexi")))));
			dto.setGongchai(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("gongchai")))));
			dto.setYebanxiao(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("yebanxiao")))));
			dto.setYebanda(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("yebanda")))));
			dto.setRiqin(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("riqin")))));
			dto.setLunban(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("lunban")))));
			dto.setNianxiu(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("nianxiu")))));
			dto.setTanqin(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("tanqin")))));
			dto.setHunjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("hunjia")))));
			dto.setSangjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("sangjia")))));
			dto.setShijia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("shijia")))));
			dto.setBingjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("bingjia")))));
			dto.setZhuyuan(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("zhuyuan")))));
			dto.setChanjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("chanjia")))));
			dto.setJisheng(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("jisheng")))));
			dto.setHuli(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("huli")))));
			dto.setXigong(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("xigong")))));
			dto.setKuanggong(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("kuanggong")))));
			dto.setTotal(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("total")))));
			aList.add(dto);
		}
		return aList;
	}
	private List<AttendanceSumCountDto> getMonthData(String year, String month, String attendanceManageSum){
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			filters=Filters.and(filters,Filters.eq("yearmonth",year+month));
			FindIterable<Document> findIterable = md.getCollection(attendanceManageSum).find(filters).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
			});
		}
		List<AttendanceSumCountDto> aList = new ArrayList<AttendanceSumCountDto>();
		for (Document document : results) {//将Document转换成AttendanceSumDto对象
			AttendanceSumCountDto dto = new AttendanceSumCountDto();
			dto.setOrgId(document.getString("orgId"));
			dto.setOrgName(document.getString("orgName"));
			dto.setNumber(document.getString("number"));
			dto.setPeopleName(document.getString("peopleName"));
			dto.setPostName(document.getString("postName"));
			dto.setYearmonth(document.getString("yearmonth"));
			dto.setXuexi(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("xuexi")))));
			dto.setGongchai(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("gongchai")))));
			dto.setYebanxiao(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("yebanxiao")))));
			dto.setYebanda(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("yebanda")))));
			dto.setRiqin(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("riqin")))));
			dto.setLunban(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("lunban")))));
			dto.setNianxiu(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("nianxiu")))));
			dto.setTanqin(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("tanqin")))));
			dto.setHunjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("hunjia")))));
			dto.setSangjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("sangjia")))));
			dto.setShijia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("shijia")))));
			dto.setBingjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("bingjia")))));
			dto.setZhuyuan(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("zhuyuan")))));
			dto.setChanjia(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("chanjia")))));
			dto.setJisheng(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("jisheng")))));
			dto.setHuli(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("huli")))));
			dto.setXigong(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("xigong")))));
			dto.setKuanggong(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("kuanggong")))));
			dto.setTotal(Double.valueOf(checkDataExistChangeToZero(String.valueOf(document.getString("total")))));
			aList.add(dto);
		}
		return aList;
	}
	
	@Override
	public String collectRangeTable(String year, List<String> monthList, String attendanceManageSum) {
		String monthStart = monthList.get(0);
		String monthEnd = monthList.get(monthList.size()-1);
		String title = "中国铁路昆明局集团有限公司昆明通信段"+year+monthStart+"至"+monthEnd+"考勤统计表";
		
		List<AttendanceSumCountDto> allList = getMonthRangeData(year,monthList,attendanceManageSum);
		
//		List<List<AttendanceSumCountDto>> result = new ArrayList<>();
//		for(int i=0;i<monthList.size();i++){
//			result.add(getMonthData(year,monthList.get(i),attendanceManageSum));
//		}
//		List<AttendanceSumCountDto> allList = result.get(0);
//		for(int j=1;j<result.size();j++){
//			allList.addAll(result.get(j));
//		}
		
		List<AttendanceSumCountDto> allResultList = new ArrayList<AttendanceSumCountDto>();
		
		Map<String, List<AttendanceSumCountDto>> mapAllList = allList.stream().collect(Collectors.groupingBy(AttendanceSumCountDto::getNumber));
		for (Map.Entry<String, List<AttendanceSumCountDto>> entry : mapAllList.entrySet()) {
			Double xuexi = entry.getValue().stream().map(AttendanceSumCountDto::getXuexi).reduce(Double::sum).get();
			Double gongchai = entry.getValue().stream().map(AttendanceSumCountDto::getGongchai).reduce(Double::sum).get();
			Double yebanxiao = entry.getValue().stream().map(AttendanceSumCountDto::getYebanxiao).reduce(Double::sum).get();
			Double yebanda = entry.getValue().stream().map(AttendanceSumCountDto::getYebanda).reduce(Double::sum).get();
			Double riqin = entry.getValue().stream().map(AttendanceSumCountDto::getRiqin).reduce(Double::sum).get();
			Double lunban = entry.getValue().stream().map(AttendanceSumCountDto::getLunban).reduce(Double::sum).get();
			Double nianxiu = entry.getValue().stream().map(AttendanceSumCountDto::getNianxiu).reduce(Double::sum).get();
			Double tanqin = entry.getValue().stream().map(AttendanceSumCountDto::getTanqin).reduce(Double::sum).get();
			Double hunjia = entry.getValue().stream().map(AttendanceSumCountDto::getHunjia).reduce(Double::sum).get();
			Double sangjia = entry.getValue().stream().map(AttendanceSumCountDto::getSangjia).reduce(Double::sum).get();
			Double shijia = entry.getValue().stream().map(AttendanceSumCountDto::getShijia).reduce(Double::sum).get();
			Double bingjia = entry.getValue().stream().map(AttendanceSumCountDto::getBingjia).reduce(Double::sum).get();
			Double zhuyuan = entry.getValue().stream().map(AttendanceSumCountDto::getZhuyuan).reduce(Double::sum).get();
			Double chanjia = entry.getValue().stream().map(AttendanceSumCountDto::getChanjia).reduce(Double::sum).get();
			Double jisheng = entry.getValue().stream().map(AttendanceSumCountDto::getJisheng).reduce(Double::sum).get();
			Double huli = entry.getValue().stream().map(AttendanceSumCountDto::getHuli).reduce(Double::sum).get();
			Double xigong = entry.getValue().stream().map(AttendanceSumCountDto::getXigong).reduce(Double::sum).get();
			Double kuanggong = entry.getValue().stream().map(AttendanceSumCountDto::getKuanggong).reduce(Double::sum).get();
			Double total = entry.getValue().stream().map(AttendanceSumCountDto::getTotal).reduce(Double::sum).get();
			entry.getValue().get(0).setXuexi(xuexi);
			entry.getValue().get(0).setGongchai(gongchai);
			entry.getValue().get(0).setYebanxiao(yebanxiao);
			entry.getValue().get(0).setYebanda(yebanda);
			entry.getValue().get(0).setRiqin(riqin);
			entry.getValue().get(0).setLunban(lunban);
			entry.getValue().get(0).setNianxiu(nianxiu);
			entry.getValue().get(0).setTanqin(tanqin);
			entry.getValue().get(0).setHunjia(hunjia);
			entry.getValue().get(0).setSangjia(sangjia);
			entry.getValue().get(0).setShijia(shijia);
			entry.getValue().get(0).setBingjia(bingjia);
			entry.getValue().get(0).setZhuyuan(zhuyuan);
			entry.getValue().get(0).setChanjia(chanjia);
			entry.getValue().get(0).setJisheng(jisheng);
			entry.getValue().get(0).setHuli(huli);
			entry.getValue().get(0).setXigong(xigong);
			entry.getValue().get(0).setKuanggong(kuanggong);
			entry.getValue().get(0).setTotal(total);
			allResultList.add(entry.getValue().get(0));
        }
		allResultList = allResultList.stream().sorted(Comparator.comparing(AttendanceSumCountDto::getOrgName)).collect(Collectors.toList());
		return createRangeTable(title,allResultList);
	}

	@Override
	public String collectTable(String year, String month, String attendanceManageSum) {
		String title = "中国铁路昆明局集团有限公司昆明通信段"+year+month+"考勤统计表";
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filters=Filters.eq("status",1);
			filters=Filters.and(filters,Filters.eq("yearmonth",year+month));
			FindIterable<Document> findIterable = md.getCollection(attendanceManageSum).find(filters).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
			});
		}
		return createTable(title,results);
	}
	
	private String createTable(String title, List<Document> results){
		String createTablePath = createPath + System.currentTimeMillis() + ".xlsx";
		FileInputStream fis = null;
		FileOutputStream fos = null;
		XSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(attendanceModelPath+"attendancehz.xlsx");
			wb =  new XSSFWorkbook(fis);
			XSSFSheet s = wb.getSheetAt(0);
			fos = new FileOutputStream(createTablePath);
			insertData(s, title, results);
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
	
	private void insertData(XSSFSheet s,String title, List<Document> results){
		XSSFRow row0 = s.getRow(0);
		XSSFCell cell0 = row0.getCell(0);//标题
		cell0.setCellValue(String.valueOf(title));
		insert(s, results);
	}
	
	//检查数据字段是否存在 若不存在返回"0"
	private String checkDataExistChangeToZero(String data){
		if(StringUtils.isBlank(data)){
			return "0";
		}else{
			return data;
		}
	}
	//检查数据字段是否存在
    private String checkDataExist(String data){
    	if(data=="null"){
    		return "";
    	}else{
    		return data;
    	}
    }
	
	private void insert(XSSFSheet s, List<Document> results) {
		int rows =results.size()-1;
		int lastRowNum = s.getLastRowNum();
		//将第8行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
		s.shiftRows(5, 6, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow1=s.getRow(4+i);//从row5开始复制
			XSSFRow targetRow1=s.createRow(5+i);//从row6开始粘贴
			copyRow(sourceRow1, targetRow1);
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(5+lastRowNum+rows, 6+lastRowNum+rows, shiftUpRow, true, false);
		for (int i = 0 ; i < results.size() ; i++) {
			XSSFRow row = s.getRow(i+4);
			XSSFCell cell0 = row.getCell(0);//序号
			XSSFCell cell1 = row.getCell(1);//所属部门
			XSSFCell cell2 = row.getCell(2);//劳资号
			XSSFCell cell3 = row.getCell(3);//姓名
			XSSFCell cell4 = row.getCell(4);//职位
			XSSFCell cell5 = row.getCell(5);//学习
			XSSFCell cell6 = row.getCell(6);//公差
			XSSFCell cell7 = row.getCell(7);//夜班（小）
			XSSFCell cell8 = row.getCell(8);//夜班（大）
			XSSFCell cell9 = row.getCell(9);//加班（日勤）
			XSSFCell cell10 = row.getCell(10);//加班（轮班）
			XSSFCell cell11 = row.getCell(11);//年休
			XSSFCell cell12 = row.getCell(12);//探亲
			XSSFCell cell13 = row.getCell(13);//婚假
			XSSFCell cell14 = row.getCell(14);//丧假
			XSSFCell cell15 = row.getCell(15);//事假
			XSSFCell cell16 = row.getCell(16);//病假
			XSSFCell cell17 = row.getCell(17);//住院
			XSSFCell cell18 = row.getCell(18);//产假
			XSSFCell cell19 = row.getCell(19);//计生
			XSSFCell cell20 = row.getCell(20);//护理
			XSSFCell cell21 = row.getCell(21);//息工
			XSSFCell cell22 = row.getCell(22);//旷工
			XSSFCell cell23 = row.getCell(23);//合计
			cell0.setCellValue(String.valueOf(1+i));
			cell1.setCellValue(String.valueOf(results.get(i).getString("orgName")));
			cell2.setCellValue(String.valueOf(results.get(i).getString("number")));
			cell3.setCellValue(String.valueOf(results.get(i).getString("peopleName")));
			cell4.setCellValue(String.valueOf(results.get(i).getString("postName")));
			String xuexi = checkDataExist(String.valueOf(results.get(i).getString("xuexi")));
			if("".equals(xuexi)){
				cell5.setCellValue(xuexi);
			}else{
				cell5.setCellValue(Double.parseDouble(xuexi));
			}
//			cell5.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("xuexi"))));
			String gongchai = checkDataExist(String.valueOf(results.get(i).getString("gongchai")));
			if("".equals(gongchai)){
				cell6.setCellValue(gongchai);
			}else{
				cell6.setCellValue(Double.parseDouble(gongchai));
			}
//			cell6.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("gongchai"))));
			String yebanxiao = checkDataExist(String.valueOf(results.get(i).getString("yebanxiao")));
			if("".equals(yebanxiao)){
				cell7.setCellValue(yebanxiao);
			}else{
				cell7.setCellValue(Double.parseDouble(yebanxiao));
			}
//			cell7.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("yebanxiao"))));
			String yebanda = checkDataExist(String.valueOf(results.get(i).getString("yebanda")));
			if("".equals(yebanda)){
				cell8.setCellValue(yebanda);
			}else{
				cell8.setCellValue(Double.parseDouble(yebanda));
			}
//			cell8.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("yebanda"))));
			String riqin = checkDataExist(String.valueOf(results.get(i).getString("riqin")));
			if("".equals(riqin)){
				cell9.setCellValue(riqin);
			}else{
				cell9.setCellValue(Double.parseDouble(riqin));
			}
//			cell9.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("riqin"))));
			String lunban = checkDataExist(String.valueOf(results.get(i).getString("lunban")));
			if("".equals(lunban)){
				cell10.setCellValue(lunban);
			}else{
				cell10.setCellValue(Double.parseDouble(lunban));
			}
//			cell10.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("lunban"))));
			String nianxiu = checkDataExist(String.valueOf(results.get(i).getString("nianxiu")));
			if("".equals(nianxiu)){
				cell11.setCellValue(nianxiu);
			}else{
				cell11.setCellValue(Double.parseDouble(nianxiu));
			}
//			cell11.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("nianxiu"))));
			String tanqin = checkDataExist(String.valueOf(results.get(i).getString("tanqin")));
			if("".equals(tanqin)){
				cell12.setCellValue(tanqin);
			}else{
				cell12.setCellValue(Double.parseDouble(tanqin));
			}
//			cell12.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("tanqin"))));
			String hunjia = checkDataExist(String.valueOf(results.get(i).getString("hunjia")));
			if("".equals(hunjia)){
				cell13.setCellValue(hunjia);
			}else{
				cell13.setCellValue(Double.parseDouble(hunjia));
			}
//			cell13.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("hunjia"))));
			String sangjia = checkDataExist(String.valueOf(results.get(i).getString("sangjia")));
			if("".equals(sangjia)){
				cell14.setCellValue(sangjia);
			}else{
				cell14.setCellValue(Double.parseDouble(sangjia));
			}
//			cell14.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("sangjia"))));
			String shijia = checkDataExist(String.valueOf(results.get(i).getString("shijia")));
			if("".equals(shijia)){
				cell15.setCellValue(shijia);
			}else{
				cell15.setCellValue(Double.parseDouble(shijia));
			}
//			cell15.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("shijia"))));
			String bingjia = checkDataExist(String.valueOf(results.get(i).getString("bingjia")));
			if("".equals(bingjia)){
				cell16.setCellValue(bingjia);
			}else{
				cell16.setCellValue(Double.parseDouble(bingjia));
			}
//			cell16.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("bingjia"))));
			String zhuyuan = checkDataExist(String.valueOf(results.get(i).getString("zhuyuan")));
			if("".equals(zhuyuan)){
				cell17.setCellValue(zhuyuan);
			}else{
				cell17.setCellValue(Double.parseDouble(zhuyuan));
			}
//			cell17.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("zhuyuan"))));
			String chanjia = checkDataExist(String.valueOf(results.get(i).getString("chanjia")));
			if("".equals(chanjia)){
				cell18.setCellValue(chanjia);
			}else{
				cell18.setCellValue(Double.parseDouble(chanjia));
			}
//			cell18.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("chanjia"))));
			String jisheng = checkDataExist(String.valueOf(results.get(i).getString("jisheng")));
			if("".equals(jisheng)){
				cell19.setCellValue(jisheng);
			}else{
				cell19.setCellValue(Double.parseDouble(jisheng));
			}
//			cell19.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("jisheng"))));
			String huli = checkDataExist(String.valueOf(results.get(i).getString("huli")));
			if("".equals(huli)){
				cell20.setCellValue(huli);
			}else{
				cell20.setCellValue(Double.parseDouble(huli));
			}
//			cell20.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("huli"))));
			String xigong = checkDataExist(String.valueOf(results.get(i).getString("xigong")));
			if("".equals(xigong)){
				cell21.setCellValue(xigong);
			}else{
				cell21.setCellValue(Double.parseDouble(xigong));
			}
//			cell21.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("xigong"))));
			String kuanggong = checkDataExist(String.valueOf(results.get(i).getString("kuanggong")));
			if("".equals(kuanggong)){
				cell22.setCellValue(kuanggong);
			}else{
				cell22.setCellValue(Double.parseDouble(kuanggong));
			}
//			cell22.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("kuanggong"))));
			String total = checkDataExist(String.valueOf(results.get(i).getString("total")));
			if("".equals(total)){
				cell23.setCellValue(total);
			}else{
				cell23.setCellValue(Double.parseDouble(total));
			}
//			cell23.setCellValue(checkDataExist(String.valueOf(results.get(i).getString("total"))));
			
		}
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
}