package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

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

import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("dayToJobService")
public class DayToJobServiceImpl implements DayToJobService {
	@Value("${spring.data.mongodb.host}")
	private String mongoHost;
	@Value("${spring.data.mongodb.port}")
	private int mongoPort;
	@Value("${spring.data.mongodb.database}")
	private String mongoDatabase;
	@Value("${modelPath}")
	private String modelPath;
	@Value("${createPath}")
	private String createPath;
	@Resource(name="dayToJobSumService")
	private DayToJobSumService dayToJobSumService;
	@Override
	public List<Document> quryAllDatas(String collectionName, String startDate, String endDate, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(startDate,endDate);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("createDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
		}
		results.stream().forEach(d-> {
            d.append("docId",d.getObjectId("_id").toHexString());
            d.append("userIdAndDate",d.get("userId").toString()+","+d.get("date").toString());
            d.remove("_id");
        });
		return results;
	}
	@Override
	public long findAllDocumentCount(String collectionName, String startDate, String endDate) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(startDate,endDate);
            return  md.getCollection(collectionName).count(filter);
        }
	}
	private Bson getFilter(String startDate, String endDate) {
		Bson filters=Filters.eq("status",1);
//		if(startDate!=null){
//			filters=Filters.and(filters,Filters.gte("date", startDate));
//		}
//		if(endDate!=null){
//			filters=Filters.and(filters,Filters.lte("date", endDate));
//		}
		if(StringUtils.isNotBlank(startDate)){
			filters = Filters.and(filters,Filters.gte("date",startDate));
        }
        if(StringUtils.isNotBlank(endDate)){
        	filters = Filters.and(filters,Filters.lte("date",endDate));
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
    
    //检查数据字段是否存在
    private String checkDataExist(String data){
    	if(data=="null"){
    		return "";
    	}else{
    		return data;
    	}
    }
    
    //将数据库中的时间字段内容里的时分秒去掉
    private String dateFormat(String dateStr){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat sdfStr = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
    	String str = "";
    	try {
            if (StringUtils.isNotBlank(dateStr)) {
            	date = sdfDate.parse(dateStr);
            }
            str = sdfStr.format(date);
        } catch (ParseException e) {
        	e.printStackTrace();
        }
    	return str;
    }
    
	@Override
	public String createGenerateSumTable(String collectionName,String docId,String userName, String userId,String leader,String cadre,String date,String dispatch) {
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		String currentDay = "";
		Date currentDate = new Date();
		currentDay = sdfDay.format(currentDate);
		//获取需要汇总的数据
		Map<String,List<Document>> data = dayToJobSumService.allSum(currentDay, userId);
		String result = createTable(data,modelPath,createPath,leader,cadre,date,dispatch);
		//保存汇总信息（更改汇总人汇总日期）
		dayToJobSumService.saveSumInfo(data, userId, userName);
		//将生成的汇总文件路径保存到日交班管理信息表中
        Document doc = findDocumentById(docId,collectionName);
		doc.put("filePath", result);
		updateDocument(doc, collectionName);
		return result;
	}
	
	private String createTable(Map<String,List<Document>> data, String modelPath, String createPath,String leader,String cadre,String date,String dispatch){
		String createTablePath = createPath + System.currentTimeMillis() + ".xlsx";
		FileInputStream fis = null;
		FileOutputStream fos = null;
		XSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(modelPath);
			wb =  new XSSFWorkbook(fis);
			XSSFSheet s = wb.getSheetAt(0);
			fos = new FileOutputStream(createTablePath);
			insertData(s, data, leader,cadre,date,dispatch);
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
	
	private void insertData(XSSFSheet s,Map<String,List<Document>> data, String leader,String cadre,String date,String dispatch){
		
		XSSFRow row1 = s.getRow(1);
		XSSFCell cell3 = row1.getCell(3);//日期
		XSSFCell cell6 = row1.getCell(6);//值班调度
		cell3.setCellValue(String.valueOf(date));
		cell6.setCellValue(String.valueOf(dispatch));
		XSSFRow row2 = s.getRow(2);
		XSSFCell cell1 = row2.getCell(1);//值班领导
		XSSFCell cell5 = row2.getCell(5);//值班干部
		cell1.setCellValue(String.valueOf(leader));
		cell5.setCellValue(String.valueOf(cadre));
		
		int num = shiftAccidentSum(s, data);
		num =  shiftObstacleSum(s, data, num);
		num =  shiftLostSum(s, data, num);
		num =  shiftOtherSum(s, data, num);
		num =  shiftNetSum(s, data, num);
		num =  shiftRepairSum(s, data, num, date);
		num =  shiftVideoSum(s, data, num);
		
//		insertConstructCooperateContent(s, data, date, num);
		num =  outsideWorkConditionSum(s, data, num, date);
		
//		num =  shiftMainJobSum(s, data, num+2, date);//因为施工配合信息不用动态生成所以加2（num+2）
		num =  shiftMainJobSum(s, data, num, date);//因为施工配合信息不用动态生成所以加2（num+2）
		num =  shiftLeaderSafeSum(s, data, num);
		num =  shiftDuanSafeSum(s, data, num);
		num =  shiftCompanyCheckSum(s, data, num);
		num =  shiftDuanCheckSum(s, data, num);
		
		cadreDutyContent(s, data, num);
		
		num =  inspectionSuperiorsSum(s, data, num+2);//因为干部值班情况不用动态生成所以加2（num+2）
		
	}
	
	private int outsideWorkConditionSum(XSSFSheet s, Map<String, List<Document>> data, int num, String date) {
		List<Document> outsideWorkCondition = data.get("outsideSum");
		if(CollectionUtils.isEmpty(outsideWorkCondition)) return 1+num;
		int outsideWorkConditionNum=outsideWorkCondition.size();
		int rows =  outsideWorkConditionNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//内容
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(checkDataExist(String.valueOf(outsideWorkCondition.get(0).get("noticeContent"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,2,2));
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,3,3));
		
		for (int i = 0 ; i < outsideWorkCondition.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//内容
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(checkDataExist(String.valueOf(outsideWorkCondition.get(i).get("noticeContent"))));
		}
		
		return outsideWorkConditionNum+num;
	}
	
	private void cadreDutyContent(XSSFSheet s, Map<String, List<Document>> data, int num) {
		
		List<Document> cadreDuty = data.get("cadreDutySum");
		
		if(CollectionUtils.isEmpty(cadreDuty)){
			s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,0,0));
//			s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,2,2));
			return;
		}
		
		String dutyLeaders = "";
		String dutyCadres = "";
		for(int i = 0 ; i<cadreDuty.size() ; i++){
			String noticeDateStr = cadreDuty.get(i).getString("noticeDateStr");
			String ymd = noticeDateStr.substring(0, 10);
			String[] ymds = ymd.split("-");
			String dateStr = ymds[0]+"年"+ymds[1]+"月"+ymds[2]+"日";
			String dutyLeader = cadreDuty.get(i).getString("dutyLeader");
			String dutyCadre = cadreDuty.get(i).getString("dutyCadre");
			
			dutyLeaders += dutyLeader;
			
			dutyCadres += dutyCadre;
		}
		
		XSSFRow cadreDutyRow1 = s.getRow(4+num);//获取干部值班情况的第一行
		XSSFCell cadreDutyCell12 = cadreDutyRow1.getCell(2);
		cadreDutyCell12.setCellValue(String.valueOf(dutyLeaders));
		
		XSSFRow cadreDutyRow2 = s.getRow(4+num+1);//获取干部值班情况的第二行
		XSSFCell cadreDutyCell22 = cadreDutyRow2.getCell(2);
		cadreDutyCell22.setCellValue(String.valueOf(dutyCadres));
		
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,0,0));
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,2,2));
	}
	
//	private void insertConstructCooperateContent(XSSFSheet s, Map<String, List<Document>> data, String date, int num) {
//		
//		List<Document> cooper = data.get("cooperSum");
//		
//		if(CollectionUtils.isEmpty(cooper)){
//			s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,0,0));
//			s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,2,2));
//			return;
//		}
//		
//		int manCount = 0;
//		for(int i = 0 ; i<cooper.size() ; i++){
//			String man = String.valueOf(cooper.get(i).get("cooperMan"));
//			String[] mans = null;
//			if(man!="null"&&StringUtils.isNotBlank(man)){
//				mans = man.split(",");
//			}else{
//				continue;
//			}
//			if(mans.length == 1){
//				manCount += 1;
//			}else{
//				manCount += mans.length;
//			}
//		}
//		
//		String cooperContent = "施工配合共计"+cooper.size()+"项，出动配合"+manCount+"人次。";
//		
//		XSSFRow constructCooperRow1 = s.getRow(4+num);//获取施工配合信息的第一行
//		XSSFCell constructCooperCell12 = constructCooperRow1.getCell(2);//时间
//		XSSFCell constructCooperCell14 = constructCooperRow1.getCell(4);//内容
//		constructCooperCell12.setCellValue(String.valueOf(date));
//		constructCooperCell14.setCellValue(String.valueOf(cooperContent));
//		
//		List<Document> pointOuter = data.get("pointOuterSum");
//		
//		String[] dates = date.split("-");
//		String month = dates[1];
//		String day = dates[2];
//		String afterDay= String.valueOf(Integer.valueOf(day)+1);
//		
//		int planCount1 = 0;
//		int sectionCount1 = 0;
//		int stationCount1 = 0;
//		for(int i = 0 ; i<pointOuter.size() ; i++){
//			String workDate = String.valueOf(pointOuter.get(i).get("workDate"));
//			String applyDate = String.valueOf(pointOuter.get(i).get("applyDate"));
//			String flowState = String.valueOf(pointOuter.get(i).get("flowState"));
//			if(workDate.equals(date)&&date.equals(applyDate)){
//				if("5".equals(flowState)||"6".equals(flowState)){//审批通过和已完成的数据
//					planCount1=planCount1+1;
//				}
//				String section = String.valueOf(pointOuter.get(i).get("section"));
//				String station = String.valueOf(pointOuter.get(i).get("station"));
//				if(StringUtils.isNotBlank(section)){
//					sectionCount1 = sectionCount1+1;
//				}
//				if(StringUtils.isNotBlank(station)){
//					stationCount1 = stationCount1+1;
//				}
//			}
//		}
//		int planCount2 = 0;
//		int sectionCount2 = 0;
//		int stationCount2 = 0;
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dispatchDate = null;
//        try {
//            if (StringUtils.isNotBlank(date)) {
//            	dispatchDate = sdf.parse(date);
//            }
//        } catch (ParseException e) {
//        	e.printStackTrace();
//        }
//		
//		Calendar ca = Calendar.getInstance();
//		ca.setTime(dispatchDate);
//		ca.add(Calendar.DATE, +1);
//		Date afterDispatchDate = ca.getTime();
//		
//	 	String afterDispatchDay = JsonUtil.getSystemDateToYearMonthDay(afterDispatchDate);
//	 	
//		for(int i = 0 ; i<pointOuter.size() ; i++){
//			String workDate = String.valueOf(pointOuter.get(i).get("workDate"));
//			String applyDate = String.valueOf(pointOuter.get(i).get("applyDate"));
//			String flowState = String.valueOf(pointOuter.get(i).get("flowState"));
//			if(workDate.equals(afterDispatchDay)&&date.equals(applyDate)){
//				if("5".equals(flowState)||"6".equals(flowState)){//审批通过和已完成的数据
//					planCount2=planCount2+1;
//				}
//				String section = String.valueOf(pointOuter.get(i).get("section"));
//				String station = String.valueOf(pointOuter.get(i).get("station"));
//				if(StringUtils.isNotBlank(section)){
//					sectionCount2 = sectionCount2+1;
//				}
//				if(StringUtils.isNotBlank(station)){
//					stationCount2 = stationCount2+1;
//				}
//			}
//		}
//		int planCount3 = 0;
//		int finish3 = 0;
//		int sectionCount3 = 0;
//		int stationCount3 = 0;
//		for(int i = 0 ; i<pointOuter.size() ; i++){
//			String workDate = String.valueOf(pointOuter.get(i).get("workDate"));
////			String applyDate = String.valueOf(pointOuter.get(i).get("applyDate"));
//			String flowState = String.valueOf(pointOuter.get(i).get("flowState"));
//			
//			if(date.equals(workDate)){
//				if(workDate.equals(date)){
//					if("6".equals(flowState)){//已完成的数据
//						finish3 = finish3+1;
//					}
//				}
//				if("5".equals(flowState)||"6".equals(flowState)){//审批通过和已完成的数据
//					planCount3=planCount3+1;
//				}
//				String section = String.valueOf(pointOuter.get(i).get("section"));
//				String station = String.valueOf(pointOuter.get(i).get("station"));
//				if(StringUtils.isNotBlank(section)){
//					sectionCount3 = sectionCount3+1;
//				}
//				if(StringUtils.isNotBlank(station)){
//					stationCount3 = stationCount3+1;
//				}
//			}
//		}
//		
////		String pointOuterContent = month+"月"+day+"日补报作业计划"+planCount1+"条，其中车站"+stationCount1+"条，区间"+sectionCount1+"条；"+month+"月"+day+"日各车间上报"+month+"月"+afterDay+"日作业计划"+planCount2+"条，其中车站"+stationCount2+"条，区间"+sectionCount2+"条；【"+month+"月"+day+"日上报作业计划共"+planCount3+"条，其中车站"+stationCount3+"条，区间"+sectionCount3+"条，兑现"+finish3+"条】";
//		String pointOuterContent = month+"月"+day+"日上报作业计划共"+planCount3+"条，其中车站"+stationCount3+"条，区间"+sectionCount3+"条，兑现"+finish3+"条；"+month+"月"+day+"日补报作业计划"+planCount1+"条，其中车站"+stationCount1+"条，区间"+sectionCount1+"条；";
//		
//		XSSFRow constructCooperRow2 = s.getRow(4+num+1);//获取施工配合信息的第二行
//		XSSFCell constructCooperCell24 = constructCooperRow2.getCell(4);//内容
//		constructCooperCell24.setCellValue(String.valueOf(pointOuterContent));
//		
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,0,0));
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+1,2,2));
//	}
	private int shiftAccidentSum(XSSFSheet s, Map<String, List<Document>> data) {
		List<Document> accidentTrouble = data.get("accidentSum");
//		System.out.println(accidentTrouble);
		if(CollectionUtils.isEmpty(accidentTrouble)) return 1;
		int accidentTroubleNum=accidentTrouble.size();
		int rows =accidentTroubleNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(accidentTrouble.get(0).get("createDate")));
			cell3.setCellValue(String.valueOf(accidentTrouble.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(accidentTrouble.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(accidentTrouble.get(0).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(accidentTrouble.get(0).get("remark"))));
			return 1;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5, 18, shiftDownRow, true, false);
		s.shiftRows(5, 20, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+i);//从row5开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+i);//从row6开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+lastRowNum+rows, 18+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+lastRowNum+rows, 20+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4,4+rows,0,0));
		
		for (int i = 0 ; i < accidentTrouble.size() ; i++) {
			XSSFRow row = s.getRow(4+i);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(accidentTrouble.get(i).get("createDate")));
			cell3.setCellValue(String.valueOf(accidentTrouble.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(accidentTrouble.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(accidentTrouble.get(i).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(accidentTrouble.get(i).get("remark"))));
		}
		
		return accidentTroubleNum;
	}
	private int shiftObstacleSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> obstacle = data.get("obstacleSum");
		if(CollectionUtils.isEmpty(obstacle)) return 1+num;
		int obstacleNum=obstacle.size();
		int rows =  obstacleNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(obstacle.get(0).get("createDate")));
			cell3.setCellValue(String.valueOf(obstacle.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(obstacle.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(obstacle.get(0).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(obstacle.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < obstacle.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(obstacle.get(i).get("createDate")));
			cell3.setCellValue(String.valueOf(obstacle.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(obstacle.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(obstacle.get(i).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(obstacle.get(i).get("remark"))));
		}
		
		return obstacleNum+num;
	}
	
	private int shiftLostSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> lost = data.get("lostSum");
		if(CollectionUtils.isEmpty(lost)) return 1+num;
		int lostNum=lost.size();
		int rows =  lostNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(lost.get(0).get("createDate")));
			cell3.setCellValue(String.valueOf(lost.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(lost.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(lost.get(0).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(lost.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < lost.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(lost.get(i).get("createDate")));
			cell3.setCellValue(String.valueOf(lost.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(lost.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(lost.get(i).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(lost.get(i).get("remark"))));
		}
		
		return lostNum+num;
	}
	
	private int shiftOtherSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> other = data.get("otherSum");
		if(CollectionUtils.isEmpty(other)) return 1+num;
		int otherNum=other.size();
		int rows =  otherNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(other.get(0).get("createDate")));
			cell3.setCellValue(String.valueOf(other.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(other.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(other.get(0).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(other.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < other.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(other.get(i).get("createDate")));
			cell3.setCellValue(String.valueOf(other.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(other.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(other.get(i).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(other.get(i).get("remark"))));
		}
		
		return otherNum+num;
	}
	
	private int shiftNetSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> net = data.get("netSum");
		if(CollectionUtils.isEmpty(net)) return 1+num;
		int netNum=net.size();
		int rows =  netNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(net.get(0).get("createDate")));
			cell3.setCellValue(String.valueOf(net.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(net.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(net.get(0).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(net.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < net.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(net.get(i).get("createDate")));
			cell3.setCellValue(String.valueOf(net.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(net.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(net.get(i).get("detail")));
			cell6.setCellValue(checkDataExist(String.valueOf(net.get(i).get("remark"))));
		}
		
		return netNum+num;
	}
	
	private int shiftRepairSum(XSSFSheet s, Map<String, List<Document>> data, int num, String date) {
		//保留停点施工、维修天窗及干部把关盯控情况需要合并的第一行行数
		int row = num;
		
		List<Document> repair = data.get("repairSum");
		
		if(CollectionUtils.isEmpty(repair)){
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+1,0,0));
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+1,2,2));
			return num+2;
		}
		
		List<Document> roadWorkRepair = new ArrayList<Document>();
		List<Document> maintainRepair = new ArrayList<Document>();
		
		for (Document document : repair) {
			String project = (String) document.get("project");
			if("施工".equals(project)){
				roadWorkRepair.add(document);
			}else{
				maintainRepair.add(document);
			}
		}
		
//		System.out.println("roadWorkRepair:"+roadWorkRepair.size());
//		System.out.println("maintainRepair:"+maintainRepair.size());
//		System.out.println("maintainRepair:"+maintainRepair.size());
//		System.out.println("roadWorkRepair:"+roadWorkRepair.size());
		
//		num = shiftRoadWorkRepair(s, roadWorkRepair, num, date);
//		num = shiftMaintainRepair(s, maintainRepair, num, roadWorkRepair.size());
		num = shiftMaintainRepair(s, maintainRepair, num, date);
		num = shiftRoadWorkRepair(s, roadWorkRepair, num, maintainRepair.size());
		if(roadWorkRepair.size()!=0&&maintainRepair.size()!=0){
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+repair.size()-1,0,0));
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+repair.size()-1,2,2));
		}else if(roadWorkRepair.size()==0&&maintainRepair.size()==0){
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+1,0,0));
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+1,2,2));
		}else{
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+repair.size(),0,0));
			s.addMergedRegion(new CellRangeAddress(4+row,4+row+repair.size(),2,2));
		}
		return num;
	}
//	private int shiftRoadWorkRepair(XSSFSheet s, List<Document> roadWorkRepair, int num, String date) {
//		if(CollectionUtils.isEmpty(roadWorkRepair)) return 1+num;
//		int roadWorkRepairNum=roadWorkRepair.size();
//		int rows =  roadWorkRepairNum-1;
//		if(rows==0){
//			XSSFRow row = s.getRow(4+num);
//			XSSFCell cell1 = row.getCell(1);//序号
//			XSSFCell cell2 = row.getCell(2);//时间
//			XSSFCell cell4 = row.getCell(4);//项数
//			XSSFCell cell5 = row.getCell(5);//内容及处理情况
//			XSSFCell cell6 = row.getCell(6);//备注
//			cell1.setCellValue(String.valueOf(1));
//			cell2.setCellValue(String.valueOf(date));
//			cell4.setCellValue(String.valueOf(roadWorkRepair.size()));
//			cell5.setCellValue(String.valueOf(roadWorkRepair.get(0).get("constructContent")));
//			cell6.setCellValue(checkDataExist(String.valueOf(roadWorkRepair.get(0).get("remark"))));
//			return 1+num;
//		}
//		int lastRowNum = s.getLastRowNum();
//		//将第5+num行以下的全部下移的行数
//		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
//		for(int i=0;i<rows;i++) {
//			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
//			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
//			copyRow(sourceRow, targetRow);
//		}
//		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,3,3));
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,4,4));
//		
//		for (int i = 0 ; i < roadWorkRepair.size() ; i++) {
//			XSSFRow row = s.getRow(4+i+num);
//			XSSFCell cell1 = row.getCell(1);//序号
//			XSSFCell cell2 = row.getCell(2);//时间
//			XSSFCell cell4 = row.getCell(4);//项数
//			XSSFCell cell5 = row.getCell(5);//内容及处理情况
//			XSSFCell cell6 = row.getCell(6);//备注
//			cell1.setCellValue(String.valueOf(1+i));
//			cell2.setCellValue(String.valueOf(date));
//			cell4.setCellValue(String.valueOf(roadWorkRepair.size()));
//			cell5.setCellValue(String.valueOf(roadWorkRepair.get(i).get("constructContent"))); 
//			cell6.setCellValue(checkDataExist(String.valueOf(roadWorkRepair.get(i).get("remark"))));
//		}
//		
//		return roadWorkRepairNum+num;
//	}
//	private int shiftMaintainRepair(XSSFSheet s, List<Document> maintainRepair, int num, int roadWorkRepairNum) {
//		if(CollectionUtils.isEmpty(maintainRepair)) return 1+num;
//		int maintainRepairNum=maintainRepair.size();
//		int rows =  maintainRepairNum-1;
//		if(rows==0){
//			XSSFRow row = s.getRow(4+num);
//			XSSFCell cell1 = row.getCell(1);//序号
////			XSSFCell cell2 = row.getCell(2);//时间
//			XSSFCell cell4 = row.getCell(4);//项数
//			XSSFCell cell5 = row.getCell(5);//内容及处理情况
//			XSSFCell cell6 = row.getCell(6);//备注
//			cell1.setCellValue(String.valueOf(1+roadWorkRepairNum));
////			cell2.setCellValue(String.valueOf(1));
//			cell4.setCellValue(String.valueOf(maintainRepair.size()));
//			cell5.setCellValue(String.valueOf(maintainRepair.get(0).get("constructContent")));
//			cell6.setCellValue(checkDataExist(String.valueOf(maintainRepair.get(0).get("remark"))));
//			return 1+num;
//		}
//		int lastRowNum = s.getLastRowNum();
//		//将第5+num行以下的全部下移的行数
//		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
//		for(int i=0;i<rows;i++) {
//			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
//			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
//			copyRow(sourceRow, targetRow);
//		}
//		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,3,3));
//		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,4,4));
//		
//		for (int i = 0 ; i < maintainRepair.size() ; i++) {
//			XSSFRow row = s.getRow(4+i+num);
//			XSSFCell cell1 = row.getCell(1);//序号
////			XSSFCell cell2 = row.getCell(2);//时间
//			XSSFCell cell4 = row.getCell(4);//项数
//			XSSFCell cell5 = row.getCell(5);//内容及处理情况
//			XSSFCell cell6 = row.getCell(6);//备注
//			cell1.setCellValue(String.valueOf(1+i+roadWorkRepairNum));
////			cell2.setCellValue(String.valueOf(1));
//			cell4.setCellValue(String.valueOf(maintainRepair.size()));
//			cell5.setCellValue(String.valueOf(maintainRepair.get(i).get("constructContent")));
//			cell6.setCellValue(checkDataExist(String.valueOf(maintainRepair.get(i).get("remark"))));
//		}
//		
//		return maintainRepairNum+num;
//	}
	private int shiftMaintainRepair(XSSFSheet s, List<Document> maintainRepair, int num, String date) {
		if(CollectionUtils.isEmpty(maintainRepair)){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(maintainRepair.size()));
			return 1+num;
		}
		int maintainRepairNum=maintainRepair.size();
		int rows =  maintainRepairNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(maintainRepair.size()));
			cell5.setCellValue(String.valueOf(maintainRepair.get(0).get("constructContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(maintainRepair.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,3,3));
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,4,4));
		
		for (int i = 0 ; i < maintainRepair.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(maintainRepair.size()));
			cell5.setCellValue(String.valueOf(maintainRepair.get(i).get("constructContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(maintainRepair.get(i).get("remark"))));
		}
		
		return maintainRepairNum+num;
	}
	private int shiftRoadWorkRepair(XSSFSheet s, List<Document> roadWorkRepair, int num, int maintainRepairNum) {
		if(CollectionUtils.isEmpty(roadWorkRepair)){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell4 = row.getCell(4);//项数
			cell1.setCellValue(String.valueOf(1+maintainRepairNum));
			cell4.setCellValue(String.valueOf(roadWorkRepair.size()));
			return 1+num;
		}
		int roadWorkRepairNum=roadWorkRepair.size();
		int rows =  roadWorkRepairNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
//			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+maintainRepairNum));
//			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(roadWorkRepair.size()));
			cell5.setCellValue(String.valueOf(roadWorkRepair.get(0).get("constructContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(roadWorkRepair.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,3,3));
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,4,4));
		
		for (int i = 0 ; i < roadWorkRepair.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
//			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i+maintainRepairNum));
//			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(roadWorkRepair.size()));
			cell5.setCellValue(String.valueOf(roadWorkRepair.get(i).get("constructContent"))); 
			cell6.setCellValue(checkDataExist(String.valueOf(roadWorkRepair.get(i).get("remark"))));
		}
		
		return roadWorkRepairNum+num;
	}
	
	private int shiftVideoSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> video = data.get("videoSum");
		if(CollectionUtils.isEmpty(video)) return 1+num;
		int videoNum=video.size();
		int rows =  videoNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(video.get(0).get("noticeDateStr")));
			cell3.setCellValue(String.valueOf(video.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(video.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(video.get(0).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(video.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < video.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(video.get(i).get("noticeDateStr")));
			cell3.setCellValue(String.valueOf(video.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(video.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(video.get(i).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(video.get(i).get("remark"))));
		}
		
		return videoNum+num;
	}
	private int shiftMainJobSum(XSSFSheet s, Map<String, List<Document>> data, int num, String date) {
		List<Document> mainJob = data.get("mainJobSum");
		if(CollectionUtils.isEmpty(mainJob)) return 1+num;
		int mainJobNum=mainJob.size();
		int rows =  mainJobNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(mainJob.get(0).get("workProgram")));
			cell5.setCellValue(String.valueOf(mainJob.get(0).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(mainJob.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,2,2));
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,3,3));
		
		for (int i = 0 ; i < mainJob.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell4 = row.getCell(4);//项数
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(date));
			cell4.setCellValue(String.valueOf(mainJob.get(i).get("workProgram")));
			cell5.setCellValue(String.valueOf(mainJob.get(i).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(mainJob.get(i).get("remark"))));
		}
		
		return mainJobNum+num;
	}
	private int shiftLeaderSafeSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> leaderSafe = data.get("leaderSafeSum");
		if(CollectionUtils.isEmpty(leaderSafe)) return 1+num;
		int leaderSafeNum=leaderSafe.size();
		int rows =  leaderSafeNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(leaderSafe.get(0).get("noticeDateStr")));
			cell3.setCellValue(String.valueOf(leaderSafe.get(0).get("backOrgName")));
			cell4.setCellValue(String.valueOf(leaderSafe.get(0).get("backPerson")));
			cell5.setCellValue(String.valueOf(leaderSafe.get(0).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(leaderSafe.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < leaderSafe.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//时间
			XSSFCell cell3 = row.getCell(3);//信息反馈部门
			XSSFCell cell4 = row.getCell(4);//信息反馈人
			XSSFCell cell5 = row.getCell(5);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(leaderSafe.get(i).get("noticeDateStr")));
			cell3.setCellValue(String.valueOf(leaderSafe.get(i).get("backOrgName")));
			cell4.setCellValue(String.valueOf(leaderSafe.get(i).get("backPerson")));
			cell5.setCellValue(String.valueOf(leaderSafe.get(i).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(leaderSafe.get(i).get("remark"))));
		}
		
		return leaderSafeNum+num;
	}
	private int shiftDuanSafeSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> duanSafe = data.get("duanSafeSum");
		if(CollectionUtils.isEmpty(duanSafe)) return 1+num;
		int duanSafeNum=duanSafe.size();
		int rows =  duanSafeNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(duanSafe.get(0).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(duanSafe.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < duanSafe.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(duanSafe.get(i).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(duanSafe.get(i).get("remark"))));
		}
		
		return duanSafeNum+num;
	}
	private int shiftCompanyCheckSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> companyCheck = data.get("companyCheckSum");
		if(CollectionUtils.isEmpty(companyCheck)) return 1+num;
		int companyCheckNum=companyCheck.size();
		int rows =  companyCheckNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(companyCheck.get(0).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(companyCheck.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < companyCheck.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(companyCheck.get(i).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(companyCheck.get(i).get("remark"))));
		}
		
		return companyCheckNum+num;
	}
	private int shiftDuanCheckSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> duanCheck = data.get("duanCheckSum");
		if(CollectionUtils.isEmpty(duanCheck)) return 1+num;
		int duanCheckNum=duanCheck.size();
		int rows =  duanCheckNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(duanCheck.get(0).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(duanCheck.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < duanCheck.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(duanCheck.get(i).get("noticeContent")));
			cell6.setCellValue(checkDataExist(String.valueOf(duanCheck.get(i).get("remark"))));
		}
		
		return duanCheckNum+num;
	}
	
	private int inspectionSuperiorsSum(XSSFSheet s, Map<String, List<Document>> data, int num) {
		List<Document> inspectionSuperiors = data.get("inspectionSuperiorsSum");
		if(CollectionUtils.isEmpty(inspectionSuperiors)) return 1+num;
		int inspectionSuperiorsNum=inspectionSuperiors.size();
		int rows =  inspectionSuperiorsNum-1;
		if(rows==0){
			XSSFRow row = s.getRow(4+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
//			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1));
			cell2.setCellValue(String.valueOf(inspectionSuperiors.get(0).get("noticeContent")));
//			cell6.setCellValue(checkDataExist(String.valueOf(duanCheck.get(0).get("remark"))));
			return 1+num;
		}
		int lastRowNum = s.getLastRowNum();
		//将第5+num行以下的全部下移的行数
		int shiftDownRow = lastRowNum+rows;
//		s.shiftRows(5+num, 18+num, shiftDownRow, true, false);
		s.shiftRows(5+num, 20+num, shiftDownRow, true, false);
		for(int i=0;i<rows;i++) {
			XSSFRow sourceRow=s.getRow(4+num+i);//从row5+num开始复制，即车间人员行
			XSSFRow targetRow=s.createRow(5+num+i);//从row6+num开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
//		s.shiftRows(5+num+lastRowNum+rows, 18+num+lastRowNum+rows, shiftUpRow, true, false);
		s.shiftRows(5+num+lastRowNum+rows, 20+num+lastRowNum+rows, shiftUpRow, true, false);
		s.addMergedRegion(new CellRangeAddress(4+num,4+num+rows,0,0));
		
		for (int i = 0 ; i < inspectionSuperiors.size() ; i++) {
			XSSFRow row = s.getRow(4+i+num);
			XSSFCell cell1 = row.getCell(1);//序号
			XSSFCell cell2 = row.getCell(2);//内容及处理情况
//			XSSFCell cell6 = row.getCell(6);//备注
			cell1.setCellValue(String.valueOf(1+i));
			cell2.setCellValue(String.valueOf(inspectionSuperiors.get(i).get("noticeContent")));
//			cell6.setCellValue(checkDataExist(String.valueOf(duanCheck.get(i).get("remark"))));
		}
		
		return inspectionSuperiorsNum+num;
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
	public Document findDocumentByBeforeDate(String systemBeforeDate, String collectionName) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("date", systemBeforeDate).append("status",1));
	            if(findIterable == null){
	            	return null;
	            }else{
	            	Document doc = findIterable.first();
	            	return doc;
	            }
//	            doc.append("docId",doc.getObjectId("_id").toHexString());
//	            doc.remove("_id");
	        }
	}

}
