package com.enovell.yunwei.km_micor_service.service.securityManage.accidentQuery;

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

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.AccidentQueryDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.AccidentQueryFindDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ObstacleQueryDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("accidentQueryService")
public class AccidentQueryServiceImpl implements AccidentQueryService {
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
		private static final String REPAIR_MODEL_PATH = "/static/exportModel/accidentExport.xls";
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
	public long findAllDocumentCount(String collectionName , String startDate, String endDate,String obligationDepart,String accidentLineName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(startDate,endDate,obligationDepart,accidentLineName);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName , String startDate, String endDate,String obligationDepart, String accidentLineName, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(startDate,endDate,obligationDepart,accidentLineName);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("accidentDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter( String startDate, String endDate,String obligationDepart,String accidentLineName) {
		Bson filters=Filters.eq("status",1);
		filters=Filters.and(filters,Filters.eq("infoResult","1"));
		filters=Filters.and(filters,Filters.eq("accidentFillState","1"));
//		if(StringUtils.isNotBlank(infoResult)) {
//			filters=Filters.and(filters,Filters.eq("infoResult",infoResult));
//		}else{
//			//查询信息：1：事故；2：故障；3：障碍；4：安全隐患
//			filters=Filters.and(filters,Filters.in("infoResult","1","2","3","4"));
//		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("accidentDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("accidentDate", endDate));
		}
		if(StringUtils.isNotBlank(obligationDepart)) {
		filters=Filters.and(filters,Filters.eq("accidentObligationDepart",obligationDepart));
	    }
		if(StringUtils.isNotBlank(accidentLineName)) {
			filters=Filters.and(filters,Filters.regex("accidentLineName",accidentLineName));
		}
		return filters;
	}
	public Map<String,List<AccidentQueryDto>> getAllFile(AccidentQueryFindDto dto) {

        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(dto.getStartUploadDate(),dto.getEndUploadDate(),dto.getAccidentObligationDepart(),dto.getAccidentLineName());
            Document doc=new Document();
            doc.append("accidentDate", -1);
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(doc);
            findIterable.forEach((Block<? super Document>) results::add);
        }
        Map<String,List<AccidentQueryDto>> dataMap=dataToSheet(results);
        return dataMap;
	
	}
	@Override
	public Workbook exportExcel(AccidentQueryFindDto dto) throws IOException {		//1.查出数据
				Map<String,List<AccidentQueryDto>> map = getAllFile(dto);
				//2.将数据写入excel模板中
				HSSFWorkbook wb = writeDataInExcel(map);
				return wb;
	}
	private Map<String,List<AccidentQueryDto>> dataToSheet(List<Document> results){
		List<AccidentQueryDto> list1=new ArrayList<>();
		Map<String,List<AccidentQueryDto>> map=new HashMap<String,List<AccidentQueryDto>>();
		map.put("01", list1);
		for (Document d : results) {
			AccidentQueryDto exdto = new AccidentQueryDto();
			if(StringUtils.isNotBlank((String)d.get("accidentObligationDepart"))){
				exdto.setObligationDepart(d.get("accidentObligationDepart").toString());
			}else{
				exdto.setObligationDepart("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentLineName"))){
				exdto.setAccidentLineName(d.get("accidentLineName").toString());
			}else{
				exdto.setAccidentLineName("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentDate"))){
				exdto.setAccidentDate(d.get("accidentDate").toString());
			}else{
				exdto.setAccidentDate("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentLevel"))){
				exdto.setAccidentLevel(d.get("accidentLevel").toString());
			}else{
				exdto.setAccidentLevel("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentDetailLevel"))){
				exdto.setAccidentDetailLevel(d.get("accidentDetailLevel").toString());
			}else{
				exdto.setAccidentDetailLevel("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentDigest"))){
				exdto.setAccidentDigest(d.get("accidentDigest").toString());
			}else{
				exdto.setAccidentDigest("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentReason"))){
				exdto.setAccidentReason(d.get("accidentReason").toString());
			}else{
				exdto.setAccidentReason("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentGeneral"))){
				exdto.setAccidentGeneral(d.get("accidentGeneral").toString());
			}else{
				exdto.setAccidentGeneral("");
			}
			if(StringUtils.isNotBlank((String)d.get("accidentDutySituation"))){
				exdto.setAccidentDutySituation(d.get("accidentDutySituation").toString());
			}else{
				exdto.setAccidentDutySituation("");
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
	private HSSFWorkbook writeDataInExcel(Map<String,List<AccidentQueryDto>> map) {
		HSSFWorkbook wb = null;
		try {
			wb =  readFileUtil.getWorkBook(REPAIR_MODEL_PATH);
				HSSFSheet s = wb.getSheetAt(0);//获取sheet页
				List<AccidentQueryDto> list;
				//获取对应sheet页的数据list
				list=map.get("01");
				if(list.size()>0){
					//遍历集合，将数据写入
					for(int r=0;r<list.size();r++){
						HSSFRow valueRow = s.getRow(r+4);//从空白行开始写入
						if(valueRow==null){
							valueRow=s.createRow(r+4);
							valueRow.createCell(0).setCellValue(r+1);//序号
							valueRow.createCell(1).setCellValue(list.get(r).getAccidentDate());
							valueRow.createCell(2).setCellValue(list.get(r).getObligationDepart());
							valueRow.createCell(3).setCellValue(list.get(r).getAccidentLineName());
							valueRow.createCell(4).setCellValue(list.get(r).getAccidentLevel());
							valueRow.createCell(5).setCellValue(list.get(r).getAccidentDetailLevel());
							valueRow.createCell(6).setCellValue(list.get(r).getAccidentDigest());
							valueRow.createCell(7).setCellValue(list.get(r).getAccidentReason());
							valueRow.createCell(8).setCellValue(list.get(r).getAccidentGeneral());
							valueRow.createCell(9).setCellValue(list.get(r).getAccidentDutySituation());
						}
						valueRow.getCell(0).setCellValue(r+1);//序号
						valueRow.getCell(1).setCellValue(list.get(r).getAccidentDate());
						valueRow.getCell(2).setCellValue(list.get(r).getObligationDepart());
						valueRow.getCell(3).setCellValue(list.get(r).getAccidentLineName());
						valueRow.getCell(4).setCellValue(list.get(r).getAccidentLevel());
						valueRow.getCell(5).setCellValue(list.get(r).getAccidentDetailLevel());
						valueRow.getCell(6).setCellValue(list.get(r).getAccidentDigest());
						valueRow.getCell(7).setCellValue(list.get(r).getAccidentReason());
						valueRow.getCell(8).setCellValue(list.get(r).getAccidentGeneral());
						valueRow.getCell(9).setCellValue(list.get(r).getAccidentDutySituation());
						
						}
					}
		    return wb;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

		
}