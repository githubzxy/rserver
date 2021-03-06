package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
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
import org.springframework.transaction.annotation.Transactional;

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ConstructRepairExportDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ConstructRepairFindDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


@Service("constructRepairService")
@Transactional
public class ConstructRepairServiceImpl implements ConstructRepairService {
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
	 private static final String REPAIR_MODEL_PATH = "/static/exportModel/constructRepairModel.xls";
	 /**
	  * 导出查询结果Excel的模板路径
	  */
	 private static final String REPAIR_QUERY_MODEL_PATH = "/static/exportModel/constructRepairQueryModel.xls";

	@Override
	public List<Document> quryAllDatas(String collectionName, String startDate, String endDate, String planNum, String project, int start,
			int limit, String currentDay, String userId, String constructContent) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(startDate,endDate,planNum,project,currentDay,userId,constructContent);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("date", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}

	@Override
	public long findAllDocumentCount(String collectionName, String startDate, String endDate, String planNum, String project,
			String currentDay, String userId, String constructContent) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(startDate,endDate,planNum,project,currentDay,userId,constructContent);
            return  md.getCollection(collectionName).count(filter);
        }
	}
	
	private Bson getFilter(String startDate, String endDate, String planNum, String project,String currentDay,String userId, String constructContent) {
		Bson filters=Filters.eq("status",1);
//		if(startDate!=null&&!startDate.equals("")){
//			filters=Filters.and(filters,Filters.gte("date", startDate));
//		}
//		if(endDate!=null&&!endDate.equals("")){
//			filters=Filters.and(filters,Filters.lte("date", endDate));
//		}
		if(StringUtils.isNotBlank(startDate)){
			filters = Filters.and(filters,Filters.gte("date",startDate));
        }
        if(StringUtils.isNotBlank(endDate)){
        	filters = Filters.and(filters,Filters.lte("date",endDate));
        }
        if(StringUtils.isNotBlank(planNum)){
        	filters=Filters.and(filters,Filters.regex("planNum", planNum));
        }
        if(StringUtils.isNotBlank(constructContent)){
        	filters=Filters.and(filters,Filters.regex("constructContent", constructContent));
        }
		if(StringUtils.isNotBlank(project)){
			if(project.equals("施工")){
				filters=Filters.and(filters,Filters.eq("project", "施工"));
			}else{
				filters=Filters.and(filters,Filters.ne("project", "施工"));
			}
		}
//		 filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//	     filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
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
	                            Filters.eq("_id", new ObjectId(doc.getString("docId"))),new Document("$set",doc)
	                    );
	        }
	}

	@Override
	public Map<String,List<ConstructRepairExportDto>> getAllFile(ConstructRepairFindDto dto,String currentDay) {

        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getExportFilter();
            Document doc=new Document();
            doc.append("date", 1);
            doc.append("projectNum", 1);
            doc.append("planAgreeTimeStart", 1);
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(doc);
            findIterable.forEach((Block<? super Document>) results::add);
        }
        Map<String,List<ConstructRepairExportDto>> dataMap=dataToSheet(results);
        return dataMap;
	
	}
	private Bson getExportFilter() {
		Bson filters=Filters.eq("status",1);
		return filters;
	}


	@Override
	public Workbook exportExcel(ConstructRepairFindDto dto,String currentDay) throws IOException {
		
		//1.查出数据
		Map<String,List<ConstructRepairExportDto>> map = getAllFile(dto,currentDay);
		if(map.size()==0) return null;
		//2.将数据写入excel模板中
		HSSFWorkbook wb = writeDataInExcel(map);
		return wb;
	}
	private Map<String,List<ConstructRepairExportDto>> dataToSheet(List<Document> results){
		List<ConstructRepairExportDto> list1=new ArrayList<>();//一月份数据
		List<ConstructRepairExportDto> list2=new ArrayList<>();//二月份数据
		List<ConstructRepairExportDto> list3=new ArrayList<>();//三月份数据
		List<ConstructRepairExportDto> list4=new ArrayList<>();//四月份数据
		List<ConstructRepairExportDto> list5=new ArrayList<>();//五月份数据
		List<ConstructRepairExportDto> list6=new ArrayList<>();//六月份数据
		List<ConstructRepairExportDto> list7=new ArrayList<>();//七月份数据
		List<ConstructRepairExportDto> list8=new ArrayList<>();//八月份数据
		List<ConstructRepairExportDto> list9=new ArrayList<>();//九月份数据
		List<ConstructRepairExportDto> list10=new ArrayList<>();//十月份数据
		List<ConstructRepairExportDto> list11=new ArrayList<>();//十一月份数据
		List<ConstructRepairExportDto> list12=new ArrayList<>();//十二月份数据
		Map<String,List<ConstructRepairExportDto>> map=new HashMap<String,List<ConstructRepairExportDto>>();
		map.put("01", list1);
		map.put("02", list2);
		map.put("03", list3);
		map.put("04", list4);
		map.put("05", list5);
		map.put("06", list6);
		map.put("07", list7);
		map.put("08", list8);
		map.put("09", list9);
		map.put("10", list10);
		map.put("11", list11);
		map.put("12", list12);
		
		for (Document d : results) {
			ConstructRepairExportDto exdto = new ConstructRepairExportDto();
			if(d.get("date")!=null&&d.get("date")!=""){
				exdto.setDate(d.get("date").toString());
			}else{
				exdto.setDate("");
			}
			if(d.get("project")!=null&&d.get("project")!=""){
				exdto.setProject(d.get("project").toString());
			}else{
				exdto.setProject("");
			}
			if(d.get("line")!=null&&d.get("line")!=""){
				exdto.setLine(d.get("line").toString());
			}else{
				exdto.setLine("");
			}
			if(d.get("workShop")!=null&&d.get("workShop")!=""){
				exdto.setWorkShop(d.get("workShop").toString());
			}else{
				exdto.setWorkShop("");
			}
			if(d.get("workArea")!=null&&d.get("workArea")!=""){
				exdto.setWorkArea(d.get("workArea").toString());
			}else{
				exdto.setWorkArea("");
			}
			if(d.get("checkLeader")!=null&&d.get("checkLeader")!=""){
				exdto.setCheckLeader(d.get("checkLeader").toString());
			}else{
				exdto.setCheckLeader("");
			}
			if(d.get("totalMan")!=null&&d.get("totalMan")!=""){
				exdto.setTotalMan(d.get("totalMan").toString());
			}else{
				exdto.setTotalMan("");
			}
			if(d.get("constructContent")!=null&&d.get("constructContent")!=""){
				exdto.setConstructContent(d.get("constructContent").toString());
			}else{
				exdto.setConstructContent("");
			}
			if(d.get("planAgreeTimeStart")!=null&&d.get("planAgreeTimeStart")!=""){
				exdto.setPlanAgreeTimeStart(d.get("planAgreeTimeStart").toString());
			}else{
				exdto.setPlanAgreeTimeStart("");
			}
			if(d.get("planAgreeTimeEnd")!=null&&d.get("planAgreeTimeEnd")!=""){
				exdto.setPlanAgreeTimeEnd(d.get("planAgreeTimeEnd").toString());
			}else{
				exdto.setPlanAgreeTimeEnd("");
			}
			if(d.get("actAgreeTime")!=null&&d.get("actAgreeTime")!=""){
				exdto.setActAgreeTime(d.get("actAgreeTime").toString());
			}else{
				exdto.setActAgreeTime("");
			}
//        	if(d.get("actOverTime")==null&&d.get("actOverTime")==""){
//        		exdto.setActOverTime("");
//        	}else{
//        		exdto.setActOverTime(d.get("actOverTime").toString());
//        	}
        	if(d.get("actOverTime")!=null&&d.get("actOverTime")!=""){
        		exdto.setActOverTime(d.get("actOverTime").toString());
        	}else{
        		exdto.setActOverTime("");
        	}
        	if(d.get("applyMinute")!=null&&d.get("applyMinute")!=""){
        		exdto.setApplyMinute(d.get("applyMinute").toString());
        	}else{
        		exdto.setApplyMinute("");
        	}
        	if(d.get("actMinute")!=null&&d.get("actMinute")!=""){
        		exdto.setActMinute(d.get("actMinute").toString());
        	}else{
        		exdto.setActMinute("");
        	}
        	if(d.get("timeCash")!=null&&d.get("timeCash")!=""){
        		exdto.setTimeCash(d.get("timeCash").toString());
        	}else{
        		exdto.setTimeCash("");
        	}
        	if(d.get("remark")!=null&&d.get("remark")!=""){
        		exdto.setRemark(d.get("remark").toString());
        	}else{
        		exdto.setRemark("");
        	}
        	if(d.get("repairType")!=null&&d.get("repairType")!=""){
        		exdto.setRepairType(d.get("repairType").toString());
        	}else{
        		exdto.setRepairType("");
        	}
        	if(d.getString("grade")!=null&&d.getString("grade")!=""){
        		exdto.setGrade(d.getString("grade").toString());
        	}else{
        		exdto.setGrade("");
        	}
        	
        	String m=exdto.getDate().substring(5, 7);//截取出月份
        	map.get(m).add(exdto);
		}
		return map;
		
	}
	/**
	 * 将数据写入excel模板中
	 * @param list
	 * @return
	 */
	private HSSFWorkbook writeDataInExcel(Map<String,List<ConstructRepairExportDto>> map) {
		HSSFWorkbook wb = null;
		try {
			wb =  readFileUtil.getWorkBook(REPAIR_MODEL_PATH);
			for(int j=1;j<13;j++){//往每个sheet页写入数据
				HSSFSheet s = wb.getSheetAt(j-1);//获取sheet页
				List<ConstructRepairExportDto> list;
				//获取对应sheet页的数据list
				if(j<10){
					list=map.get("0"+String.valueOf(j));
				}else{
					list=map.get(String.valueOf(j));
				}
				//汇总sheet页数据
				int construct=0;//施工
				int repairOne=0;//Ⅰ级维修
				int repairOneNot=0;//Ⅰ级维修（未兑现）
				int repairTwo=0;//Ⅱ级维修
				int repairTwoNot=0;//Ⅱ级维修（未兑现）
				int repairTemp=0;//临时修
				int constrUrgen=0;//紧急修
				if(list.size()>0){
					//遍历集合，将数据写入
					for(int r=0;r<list.size();r++){
						HSSFRow valueRow = s.getRow(r+2);//从第二行开始写入
						//日期单元格的值拼装
						String cellDate=list.get(r).getDate();
						String cellDateNew=(cellDate.substring(0,4)+"年")+(j+"月")+(cellDate.substring(8,10)+"日");
						
						//项目单元格拼装值
						String project=list.get(r).getProject();//项目类型
						String grade=list.get(r).getGrade();//等级
						String overTime=list.get(r).getActOverTime();//实际完成时间
						String cellProVal=null;
						
						if(project.equals("施工")){
							cellProVal=grade+project;
							construct++;
						}else{
							if(project.equals("维修")){
								cellProVal=grade+"维修\r\n"+list.get(r).getRepairType();
							}else{
								cellProVal=grade+"维修\r\n（"+project+"）\r\n"+list.get(r).getRepairType();
							}
							
							if(project.equals("临时修")){
								repairTemp++;
							}
							else if(project.equals("紧急修")){
								constrUrgen++;
							}
							else if(!overTime.equals("")&&grade.equals("Ⅰ")){
								repairOne++;
							}
							else if(overTime.equals("")&&grade.equals("Ⅰ")){
								repairOneNot++;
							}
							else if(!overTime.equals("")&&grade.equals("Ⅱ")){
								repairTwo++;
							}
							else if(overTime.equals("")&&grade.equals("Ⅱ")){
								repairTwoNot++;
							}
						}
						
						//计划批准时间的值拼装
						String cellPlanStart=list.get(r).getPlanAgreeTimeStart();
						String cellPlanEnd=list.get(r).getPlanAgreeTimeEnd();
						String cellPlanSrartNew="";
						String cellPlanEndNew="";
						if(cellPlanStart.equals("")){
							cellPlanSrartNew=" -";
						}else{
							cellPlanSrartNew=cellPlanStart.substring(11,16)+" - ";
						}
						
						if(cellPlanEnd.equals("")){
							cellPlanEndNew="";
						}else{
							cellPlanEndNew=cellPlanEnd.substring(11,16);
						}
						
						
						
						//设置字体
						HSSFFont font=wb.createFont();
						font.setFontHeightInPoints((short) 10);
						//设置项目列单元格的格式
						HSSFCellStyle cellStyle=wb.createCellStyle();
						cellStyle.setWrapText(true);//强制换行
						cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
						cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
						cellStyle.setFont(font);
						
						valueRow.getCell(2).setCellStyle(cellStyle);
						
						valueRow.getCell(0).setCellValue(r+1);
						valueRow.getCell(1).setCellValue(cellDateNew);
						valueRow.getCell(2).setCellValue(new HSSFRichTextString(cellProVal));
						valueRow.getCell(3).setCellValue(list.get(r).getLine());
						valueRow.getCell(4).setCellValue(list.get(r).getWorkShop());
						valueRow.getCell(5).setCellValue(list.get(r).getWorkArea());
						valueRow.getCell(6).setCellValue(list.get(r).getCheckLeader());
						valueRow.getCell(7).setCellValue(list.get(r).getTotalMan());
						valueRow.getCell(8).setCellValue(list.get(r).getConstructContent());
						valueRow.getCell(9).setCellValue(cellPlanSrartNew+cellPlanEndNew);
						if(!list.get(r).getActAgreeTime().equals(""))
						valueRow.getCell(10).setCellValue(list.get(r).getActAgreeTime().substring(11,16));
						if(!list.get(r).getActOverTime().equals(""))
						valueRow.getCell(11).setCellValue(list.get(r).getActOverTime().substring(11,16));
						if(!list.get(r).getApplyMinute().equals(""))
						valueRow.getCell(12).setCellValue(Integer.parseInt(list.get(r).getApplyMinute()));
						if(!list.get(r).getActMinute().equals(""))
						valueRow.getCell(13).setCellValue(Integer.parseInt(list.get(r).getActMinute()));
						if(!list.get(r).getTimeCash().equals(""))
						valueRow.getCell(14).setCellValue(list.get(r).getTimeCash());
						valueRow.getCell(15).setCellValue(list.get(r).getRemark());
						
						}
					}
				s.setForceFormulaRecalculation(true);
				//将汇总数据写入到汇总sheet页
				HSSFSheet hs = wb.getSheetAt(12);//获取汇总sheet页
				if(construct!=0)
				hs.getRow(3).getCell(j).setCellValue(construct);//从第四行开始写入
				if(repairOne!=0)
				hs.getRow(4).getCell(j).setCellValue(repairOne);
				if(repairOneNot!=0)
				hs.getRow(5).getCell(j).setCellValue(repairOneNot);
				if(repairTwo!=0)
				hs.getRow(6).getCell(j).setCellValue(repairTwo);
				if(repairTwoNot!=0)
				hs.getRow(7).getCell(j).setCellValue(repairTwoNot);
				if(repairTemp!=0)
				hs.getRow(8).getCell(j).setCellValue(repairTemp);
				if(constrUrgen!=0)
				hs.getRow(9).getCell(j).setCellValue(constrUrgen);
				hs.setForceFormulaRecalculation(true);
				
				
			}
			
		    return wb;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	@Override
	public Workbook exportQueryExcel(ConstructRepairFindDto dto) throws IOException {
		Map<String,List<ConstructRepairExportDto>> map = getQueryAllFile(dto);
		//2.将数据写入excel模板中
		HSSFWorkbook wb = writeQueryDataInExcel(map);
		return wb;
	}
	public Map<String,List<ConstructRepairExportDto>> getQueryAllFile(ConstructRepairFindDto dto) {

        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(dto.getStartUploadDate(),dto.getEndUploadDate(),dto.getPlanNum(),dto.getProject(),null,null,dto.getConstructContent());
            Document doc=new Document();
            doc.append("date", -1);
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(doc);
            findIterable.forEach((Block<? super Document>) results::add);
        }
        Map<String,List<ConstructRepairExportDto>> dataMap=queryDataToSheet(results);
        return dataMap;
	
	}
	private Map<String,List<ConstructRepairExportDto>> queryDataToSheet(List<Document> results){
		List<ConstructRepairExportDto> list1=new ArrayList<>();
		Map<String,List<ConstructRepairExportDto>> map=new HashMap<String,List<ConstructRepairExportDto>>();
		map.put("01", list1);
		for (Document d : results) {
			ConstructRepairExportDto exdto = new ConstructRepairExportDto();
			if(d.get("date")!=null&&d.get("date")!=""){
				exdto.setDate(d.get("date").toString());
			}else{
				exdto.setDate("");
			}
			if(d.get("project")!=null&&d.get("project")!=""){
				exdto.setProject(d.get("project").toString());
			}else{
				exdto.setProject("");
			}
			if(d.get("line")!=null&&d.get("line")!=""){
				exdto.setLine(d.get("line").toString());
			}else{
				exdto.setLine("");
			}
			if(d.get("workShop")!=null&&d.get("workShop")!=""){
				exdto.setWorkShop(d.get("workShop").toString());
			}else{
				exdto.setWorkShop("");
			}
			if(d.get("workArea")!=null&&d.get("workArea")!=""){
				exdto.setWorkArea(d.get("workArea").toString());
			}else{
				exdto.setWorkArea("");
			}
			if(d.get("checkLeader")!=null&&d.get("checkLeader")!=""){
				exdto.setCheckLeader(d.get("checkLeader").toString());
			}else{
				exdto.setCheckLeader("");
			}
			if(d.get("totalMan")!=null&&d.get("totalMan")!=""){
				exdto.setTotalMan(d.get("totalMan").toString());
			}else{
				exdto.setTotalMan("");
			}
			if(d.get("constructContent")!=null&&d.get("constructContent")!=""){
				exdto.setConstructContent(d.get("constructContent").toString());
			}else{
				exdto.setConstructContent("");
			}
			if(d.get("planAgreeTimeStart")!=null&&d.get("planAgreeTimeStart")!=""){
				exdto.setPlanAgreeTimeStart(d.get("planAgreeTimeStart").toString());
			}else{
				exdto.setPlanAgreeTimeStart("");
			}
			if(d.get("planAgreeTimeEnd")!=null&&d.get("planAgreeTimeEnd")!=""){
				exdto.setPlanAgreeTimeEnd(d.get("planAgreeTimeEnd").toString());
			}else{
				exdto.setPlanAgreeTimeEnd("");
			}
			if(d.get("actAgreeTime")!=null&&d.get("actAgreeTime")!=""){
				exdto.setActAgreeTime(d.get("actAgreeTime").toString());
			}else{
				exdto.setActAgreeTime("");
			}
        	if(d.get("actOverTime")!=null&&d.get("actOverTime")!=""){
        		exdto.setActOverTime(d.get("actOverTime").toString());
        	}else{
        		exdto.setActOverTime("");
        	}
        	if(d.get("applyMinute")!=null&&d.get("applyMinute")!=""){
        		exdto.setApplyMinute(d.get("applyMinute").toString());
        	}else{
        		exdto.setApplyMinute("");
        	}
        	if(d.get("actMinute")!=null&&d.get("actMinute")!=""){
        		exdto.setActMinute(d.get("actMinute").toString());
        	}else{
        		exdto.setActMinute("");
        	}
        	if(d.get("timeCash")!=null&&d.get("timeCash")!=""){
        		exdto.setTimeCash(d.get("timeCash").toString());
        	}else{
        		exdto.setTimeCash("");
        	}
        	if(d.get("remark")!=null&&d.get("remark")!=""){
        		exdto.setRemark(d.get("remark").toString());
        	}else{
        		exdto.setRemark("");
        	}
        	if(d.get("repairType")!=null&&d.get("repairType")!=""){
        		exdto.setRepairType(d.get("repairType").toString());
        	}else{
        		exdto.setRepairType("");
        	}
        	if(d.getString("grade")!=null&&d.getString("grade")!=""){
        		exdto.setGrade(d.getString("grade").toString());
        	}else{
        		exdto.setGrade("");
        	}
			map.get("01").add(exdto);
		}
		return map;
	}
	/**
	 * 将查询的数据写入excel模板中
	 * @param list
	 * @return
	 */
	private HSSFWorkbook writeQueryDataInExcel(Map<String,List<ConstructRepairExportDto>> map) {
		HSSFWorkbook wb = null;
		try {
			wb =  readFileUtil.getWorkBook(REPAIR_QUERY_MODEL_PATH);
				HSSFSheet s = wb.getSheetAt(0);//获取sheet页
				//获取对应sheet页的数据list
				List<ConstructRepairExportDto> list = map.get("01");
				if(list.size()>0){
					//遍历集合，将数据写入
					for(int r=0;r<list.size();r++){
						HSSFRow valueRow = s.getRow(r+2);//从第二行开始写入
						//日期单元格的值拼装
						String cellDate=list.get(r).getDate();
						String cellDateNew=(cellDate.substring(0,4)+"年")+(0+"月")+(cellDate.substring(8,10)+"日");
//						
//						//项目单元格拼装值
						String project=list.get(r).getProject();//项目类型
						String grade=list.get(r).getGrade();//等级
//						String overTime=list.get(r).getActOverTime();//实际完成时间
						String cellProVal=null;
						if(project.equals("施工")){
							cellProVal=grade+project;
						}else{
							if(project.equals("维修")){
								cellProVal=grade+"维修\r\n"+list.get(r).getRepairType();
							}else{
								cellProVal=grade+"维修\r\n（"+project+"）\r\n"+list.get(r).getRepairType();
							}
						}
						//计划批准时间的值拼装
						String cellPlanStart=list.get(r).getPlanAgreeTimeStart();
						String cellPlanEnd=list.get(r).getPlanAgreeTimeEnd();
						String cellPlanSrartNew="";
						String cellPlanEndNew="";
						if(cellPlanStart.equals("")){
							cellPlanSrartNew=" -";
						}else{
							cellPlanSrartNew=cellPlanStart.substring(11,16)+" - ";
						}
						
						if(cellPlanEnd.equals("")){
							cellPlanEndNew="";
						}else{
							cellPlanEndNew=cellPlanEnd.substring(11,16);
						}
						//设置字体
						HSSFFont font=wb.createFont();
						font.setFontHeightInPoints((short) 10);
						//设置项目列单元格的格式
						HSSFCellStyle cellStyle=wb.createCellStyle();
						cellStyle.setWrapText(true);//强制换行
						cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
						cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
						cellStyle.setFont(font);
						if(valueRow==null) {
							valueRow = s.createRow(r+2);
							valueRow.createCell(0).setCellValue(r+1);
							valueRow.createCell(1).setCellValue(list.get(r).getDate());
							valueRow.createCell(2).setCellValue(cellProVal);
							valueRow.createCell(3).setCellValue(list.get(r).getLine());
							valueRow.createCell(4).setCellValue(list.get(r).getWorkShop());
							valueRow.createCell(5).setCellValue(list.get(r).getWorkArea());
							valueRow.createCell(6).setCellValue(list.get(r).getCheckLeader());
							valueRow.createCell(7).setCellValue(list.get(r).getTotalMan());
							valueRow.createCell(8).setCellValue(list.get(r).getConstructContent());
							valueRow.createCell(9).setCellValue(cellPlanSrartNew+cellPlanEndNew);
							valueRow.createCell(10).setCellValue(list.get(r).getActAgreeTime());
							valueRow.createCell(11).setCellValue(list.get(r).getActOverTime());
							if(!list.get(r).getApplyMinute().equals("")) {
							valueRow.createCell(12).setCellValue(Integer.parseInt(list.get(r).getApplyMinute()));
							}else {
							valueRow.createCell(12).setCellValue("");
							}
							if(!list.get(r).getActMinute().equals("")) {
							valueRow.createCell(13).setCellValue(Integer.parseInt(list.get(r).getActMinute()));
							}else {
							valueRow.createCell(13).setCellValue("");
							}
							valueRow.createCell(14).setCellValue(list.get(r).getTimeCash());
							valueRow.createCell(15).setCellValue(list.get(r).getRemark());
						}else{
						valueRow.getCell(0).setCellValue(r+1);
						valueRow.getCell(1).setCellValue(list.get(r).getDate());
						valueRow.getCell(2).setCellValue(cellProVal);
						valueRow.getCell(3).setCellValue(list.get(r).getLine());
						valueRow.getCell(4).setCellValue(list.get(r).getWorkShop());
						valueRow.getCell(5).setCellValue(list.get(r).getWorkArea());
						valueRow.getCell(6).setCellValue(list.get(r).getCheckLeader());
						valueRow.getCell(7).setCellValue(list.get(r).getTotalMan());
						valueRow.getCell(8).setCellValue(list.get(r).getConstructContent());
						valueRow.getCell(9).setCellValue(cellPlanSrartNew+cellPlanEndNew);
						if(!list.get(r).getActAgreeTime().equals(""))
						valueRow.getCell(10).setCellValue(list.get(r).getActAgreeTime());
						if(!list.get(r).getActOverTime().equals(""))
						valueRow.getCell(11).setCellValue(list.get(r).getActOverTime());
						if(!list.get(r).getApplyMinute().equals("")) {
						valueRow.createCell(12).setCellValue(Integer.parseInt(list.get(r).getApplyMinute()));
						}else {
						valueRow.createCell(12).setCellValue("");
						}
						if(!list.get(r).getActMinute().equals("")) {
						valueRow.createCell(13).setCellValue(Integer.parseInt(list.get(r).getActMinute()));
						}else {
						valueRow.createCell(13).setCellValue("");
						}
						if(!list.get(r).getTimeCash().equals(""))
						valueRow.getCell(14).setCellValue(list.get(r).getTimeCash());
						valueRow.getCell(15).setCellValue(list.get(r).getRemark());
						}
					}
		    return wb;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

}
