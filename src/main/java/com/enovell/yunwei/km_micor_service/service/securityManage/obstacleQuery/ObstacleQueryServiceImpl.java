package com.enovell.yunwei.km_micor_service.service.securityManage.obstacleQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ObstacleQueryDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ObstacleQueryFindDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("obstacleQueryService")
public class ObstacleQueryServiceImpl implements ObstacleQueryService {
	@Value("${spring.data.mongodb.host}")
	private String mongoHost;
	@Value("${spring.data.mongodb.port}")
	private int mongoPort;
	@Value("${spring.data.mongodb.database}")
	private String mongoDatabase;
	@Value("${createPath}")
	private String createPath;
	@Value("${wordPath}")
	private String troubleTempPath;
	 @Autowired
		private ReadFileUtil readFileUtil;
	    /**
		  * 导出Excel的模板路径
		  */
		private static final String REPAIR_MODEL_PATH = "/static/exportModel/obstacleExport.xls";
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
	public long findAllDocumentCount(String collectionName , String startDate, String endDate,String obligationDepart,String obstacleType) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(startDate,endDate,obligationDepart,obstacleType);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName , String startDate, String endDate,String obligationDepart, String obstacleType, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(startDate,endDate,obligationDepart,obstacleType);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("obstacleDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter( String startDate, String endDate,String obligationDepart,String obstacleType) {
		Bson filters=Filters.eq("status",1);
		filters=Filters.and(filters,Filters.eq("infoResult","3"));
		filters=Filters.and(filters,Filters.eq("obstacleFillState","1"));

//		if(StringUtils.isNotBlank(infoResult)) {
//			filters=Filters.and(filters,Filters.eq("infoResult",infoResult));
//		}else{
//			//查询信息：1：事故；2：故障；3：障碍；4：安全隐患
//			filters=Filters.and(filters,Filters.in("infoResult","1","2","3","4"));
//		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("obstacleDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("obstacleDate", endDate));
		}
		if(StringUtils.isNotBlank(obligationDepart)) {
		filters=Filters.and(filters,Filters.eq("obstacleObligationDepart",obligationDepart));
	    }
		if(StringUtils.isNotBlank(obstacleType)) {
			filters=Filters.and(filters,Filters.regex("obstacleType",obstacleType));
		}
		return filters;
	}
	public Map<String,List<ObstacleQueryDto>> getAllFile(ObstacleQueryFindDto dto) {

        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(dto.getStartUploadDate(),dto.getEndUploadDate(),dto.getObstacleObligationDepart(),dto.getObstacleType());
            Document doc=new Document();
            doc.append("obstacleDate", -1);
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(doc);
            findIterable.forEach((Block<? super Document>) results::add);
        }
        Map<String,List<ObstacleQueryDto>> dataMap=dataToSheet(results);
        return dataMap;
	
	}
	@Override
	public Workbook exportExcel(ObstacleQueryFindDto dto) throws IOException {		//1.查出数据
				Map<String,List<ObstacleQueryDto>> map = getAllFile(dto);
				//2.将数据写入excel模板中
				HSSFWorkbook wb = writeDataInExcel(map);
				return wb;
	}
	private Map<String,List<ObstacleQueryDto>> dataToSheet(List<Document> results){
		List<ObstacleQueryDto> list1=new ArrayList<>();
		Map<String,List<ObstacleQueryDto>> map=new HashMap<String,List<ObstacleQueryDto>>();
		map.put("01", list1);
		for (Document d : results) {
			ObstacleQueryDto exdto = new ObstacleQueryDto();
			if(StringUtils.isNotBlank((String)d.get("obstacleObligationDepart"))){
				exdto.setObligationDepart(d.get("obstacleObligationDepart").toString());
			}else{
				exdto.setObligationDepart("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleType"))){
				exdto.setObstacleType(d.get("obstacleType").toString());
			}else{
				exdto.setObstacleType("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleDuty"))){
				exdto.setObstacleDuty(d.get("obstacleDuty").toString());
			}else{
				exdto.setObstacleDuty("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleDate"))){
				exdto.setObstacleDate(d.get("obstacleDate").toString());
			}else{
				exdto.setObstacleDate("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstaclePlace"))){
				exdto.setObstaclePlace(d.get("obstaclePlace").toString());
			}else{
				exdto.setObstaclePlace("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleDeviceType"))){
				exdto.setObstacleDeviceType(d.get("obstacleDeviceType").toString());
			}else{
				exdto.setObstacleDeviceType("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleDeviceName"))){
				exdto.setObstacleDeviceName(d.get("obstacleDeviceName").toString());
			}else{
				exdto.setObstacleDeviceName("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleOccurDate"))){
				exdto.setObstacleOccurDate(d.get("obstacleOccurDate").toString());
			}else{
				exdto.setObstacleOccurDate("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleRecoverDate"))){
				exdto.setObstacleRecoverDate(d.get("obstacleRecoverDate").toString());
			}else{
				exdto.setObstacleRecoverDate("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleDelayMinutes"))){
				exdto.setObstacleDelayMinutes(d.get("obstacleDelayMinutes").toString());
			}else{
				exdto.setObstacleDelayMinutes("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleReceiver"))){
				exdto.setObstacleReceiver(d.get("obstacleReceiver").toString());
			}else{
				exdto.setObstacleReceiver("");
			}
			
			if(StringUtils.isNotBlank((String)d.get("obstacleDelayCoach"))){
				exdto.setObstacleDelayCoach(d.get("obstacleDelayCoach").toString());
			}else{
				exdto.setObstacleDelayCoach("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleDelayTruck"))){
				exdto.setObstacleDelayTruck(d.get("obstacleDelayTruck").toString());
			}else{
				exdto.setObstacleDelayTruck("");
			}
			
			if(StringUtils.isNotBlank((String)d.get("obstaclePhenomenon"))){
				exdto.setObstaclePhenomenon(d.get("obstaclePhenomenon").toString());
			}else{
				exdto.setObstaclePhenomenon("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleReasonAnalyse"))){
				exdto.setObstacleReasonAnalyse(d.get("obstacleReasonAnalyse").toString());
			}else{
				exdto.setObstacleReasonAnalyse("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleMeasure"))){
				exdto.setObstacleMeasure(d.get("obstacleMeasure").toString());
			}else{
				exdto.setObstacleMeasure("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleSuingPeople"))){
				exdto.setObstacleSuingPeople(d.get("obstacleSuingPeople").toString());
			}else{
				exdto.setObstacleSuingPeople("");
			}
			if(StringUtils.isNotBlank((String)d.get("obstacleSuingDate"))){
				exdto.setObstacleSuingDate(d.get("obstacleSuingDate").toString());
			}else{
				exdto.setObstacleSuingDate("");
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
	private HSSFWorkbook writeDataInExcel(Map<String,List<ObstacleQueryDto>> map) {
		HSSFWorkbook wb = null;
		try {
			wb =  readFileUtil.getWorkBook(REPAIR_MODEL_PATH);
				HSSFSheet s = wb.getSheetAt(0);//获取sheet页
				List<ObstacleQueryDto> list;
				//获取对应sheet页的数据list
				list=map.get("01");
				if(list.size()>0){
					//遍历集合，将数据写入
					for(int r=0;r<list.size();r++){
						HSSFRow valueRow = s.getRow(r+4);//从空白行开始写入
						if(valueRow==null){
							valueRow=s.createRow(r+4);
							valueRow.createCell(0).setCellValue(r+1);//序号
							valueRow.createCell(1).setCellValue(list.get(r).getObstacleDate());
							valueRow.createCell(2).setCellValue(list.get(r).getObligationDepart());
							valueRow.createCell(3).setCellValue(list.get(r).getObstaclePlace());
							
							valueRow.createCell(4).setCellValue(list.get(r).getObstacleDeviceType());
							valueRow.createCell(5).setCellValue(list.get(r).getObstacleDeviceName());
							valueRow.createCell(6).setCellValue(list.get(r).getObstacleOccurDate());
							valueRow.createCell(7).setCellValue(list.get(r).getObstacleRecoverDate());
							
							valueRow.createCell(8).setCellValue(list.get(r).getObstacleDelayMinutes());
							valueRow.createCell(9).setCellValue(list.get(r).getObstacleDelayCoach());
							valueRow.createCell(10).setCellValue(list.get(r).getObstacleDelayTruck());
							
							valueRow.createCell(11).setCellValue(list.get(r).getObstaclePhenomenon());
							valueRow.createCell(12).setCellValue(list.get(r).getObstacleReasonAnalyse());
							valueRow.createCell(13).setCellValue(list.get(r).getObstacleMeasure());
							
							valueRow.createCell(14).setCellValue(list.get(r).getObstacleSuingPeople());
							valueRow.createCell(15).setCellValue(list.get(r).getObstacleSuingDate());
							valueRow.createCell(16).setCellValue(list.get(r).getObstacleReceiver());
							valueRow.createCell(17).setCellValue(list.get(r).getObstacleType());
							valueRow.createCell(18).setCellValue(list.get(r).getObstacleDuty());
						}
						valueRow.getCell(0).setCellValue(r+1);//序号
						valueRow.getCell(1).setCellValue(list.get(r).getObstacleDate());
						valueRow.getCell(2).setCellValue(list.get(r).getObligationDepart());
						valueRow.getCell(3).setCellValue(list.get(r).getObstaclePlace());
						
						valueRow.getCell(4).setCellValue(list.get(r).getObstacleDeviceType());
						valueRow.getCell(5).setCellValue(list.get(r).getObstacleDeviceName());
						valueRow.getCell(6).setCellValue(list.get(r).getObstacleOccurDate());
						valueRow.getCell(7).setCellValue(list.get(r).getObstacleRecoverDate());
						
						valueRow.getCell(8).setCellValue(list.get(r).getObstacleDelayMinutes());
						valueRow.getCell(9).setCellValue(list.get(r).getObstacleDelayCoach());
						valueRow.getCell(10).setCellValue(list.get(r).getObstacleDelayTruck());
						
						valueRow.getCell(11).setCellValue(list.get(r).getObstaclePhenomenon());
						valueRow.getCell(12).setCellValue(list.get(r).getObstacleReasonAnalyse());
						valueRow.getCell(13).setCellValue(list.get(r).getObstacleMeasure());
						
						valueRow.getCell(14).setCellValue(list.get(r).getObstacleSuingPeople());
						valueRow.getCell(15).setCellValue(list.get(r).getObstacleSuingDate());
						valueRow.getCell(16).setCellValue(list.get(r).getObstacleReceiver());
						valueRow.getCell(17).setCellValue(list.get(r).getObstacleType());
						valueRow.getCell(18).setCellValue(list.get(r).getObstacleDuty());
						
						}
					}
		    return wb;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
		
}