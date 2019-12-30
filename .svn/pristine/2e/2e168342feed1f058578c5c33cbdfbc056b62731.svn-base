package com.enovell.yunwei.km_micor_service.service.securityManage.securityQuery;

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
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.SecurityQueryDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.SecurityQueryFindDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("securityQueryService")
public class SecurityQueryServiceImpl implements SecurityQueryService {
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
		private static final String REPAIR_MODEL_PATH = "/static/exportModel/securityExport.xls";
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
	public long findAllDocumentCount(String collectionName , String startDate, String endDate,String obligationDepart,String securityLineName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(startDate,endDate,obligationDepart,securityLineName);
            return  md.getCollection(collectionName).count(filter);
        }
	}

	@Override
	public List<Document> findAllDocument(String collectionName , String startDate, String endDate,String obligationDepart, String securityLineName, int start, int limit) {
		List<Document> results=new ArrayList<>();
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson filter=getFilter(startDate,endDate,obligationDepart,securityLineName);
			FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("securityDate", -1));
			findIterable.forEach((Block<? super Document>) results::add);
			 results.stream().forEach(d-> {
		            d.append("docId",d.getObjectId("_id").toHexString());
		            d.remove("_id");
		        });
		}
		return results;
	}
	private Bson getFilter( String startDate, String endDate,String obligationDepart,String securityLineName) {
		Bson filters=Filters.eq("status",1);
		filters=Filters.and(filters,Filters.eq("infoResult","4"));
		filters=Filters.and(filters,Filters.eq("securityFillState","1"));

//		if(StringUtils.isNotBlank(infoResult)) {
//			filters=Filters.and(filters,Filters.eq("infoResult",infoResult));
//		}else{
//			//查询信息：1：事故；2：故障；3：障碍；4：安全隐患
//			filters=Filters.and(filters,Filters.in("infoResult","1","2","3","4"));
//		}
		if(startDate!=null&&!startDate.equals("")){
			filters=Filters.and(filters,Filters.gte("securityDate", startDate));
		}
		if(endDate!=null&&!endDate.equals("")){
			filters=Filters.and(filters,Filters.lte("securityDate", endDate));
		}
		if(StringUtils.isNotBlank(obligationDepart)) {
		filters=Filters.and(filters,Filters.eq("securityObligationDepart",obligationDepart));
	    }
		if(StringUtils.isNotBlank(securityLineName)) {
			filters=Filters.and(filters,Filters.regex("securityLineName",securityLineName));
		}
		return filters;
	}
	public Map<String,List<SecurityQueryDto>> getAllFile(SecurityQueryFindDto dto) {

        List<Document> results = new ArrayList<>();
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(dto.getStartUploadDate(),dto.getEndUploadDate(),dto.getSecurityObligationDepart(),dto.getSecurityLineName());
            Document doc=new Document();
            doc.append("securityDate", -1);
            FindIterable<Document> findIterable = md.getCollection(dto.getCollectionName()).find(filter).sort(doc);
            findIterable.forEach((Block<? super Document>) results::add);
        }
        Map<String,List<SecurityQueryDto>> dataMap=dataToSheet(results);
        return dataMap;
	
	}
	@Override
	public Workbook exportExcel(SecurityQueryFindDto dto) throws IOException {		//1.查出数据
				Map<String,List<SecurityQueryDto>> map = getAllFile(dto);
				//2.将数据写入excel模板中
				HSSFWorkbook wb = writeDataInExcel(map);
				return wb;
	}
	private Map<String,List<SecurityQueryDto>> dataToSheet(List<Document> results){
		List<SecurityQueryDto> list1=new ArrayList<>();
		Map<String,List<SecurityQueryDto>> map=new HashMap<String,List<SecurityQueryDto>>();
		map.put("01", list1);
		for (Document d : results) {
			SecurityQueryDto exdto = new SecurityQueryDto();
			if(StringUtils.isNotBlank((String)d.get("securityObligationDepart"))){
				exdto.setObligationDepart(d.get("securityObligationDepart").toString());
			}else{
				exdto.setObligationDepart("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityLineName"))){
				exdto.setSecurityLineName(d.get("securityLineName").toString());
			}else{
				exdto.setSecurityLineName("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityDate"))){
				exdto.setSecurityDate(d.get("securityDate").toString());
			}else{
				exdto.setSecurityDate("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityLevel"))){
				exdto.setSecurityLevel(d.get("securityLevel").toString());
			}else{
				exdto.setSecurityLevel("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityDetailLevel"))){
				exdto.setSecurityDetailLevel(d.get("securityDetailLevel").toString());
			}else{
				exdto.setSecurityDetailLevel("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityDigest"))){
				exdto.setSecurityDigest(d.get("securityDigest").toString());
			}else{
				exdto.setSecurityDigest("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityReason"))){
				exdto.setSecurityReason(d.get("securityReason").toString());
			}else{
				exdto.setSecurityReason("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityGeneral"))){
				exdto.setSecurityGeneral(d.get("securityGeneral").toString());
			}else{
				exdto.setSecurityGeneral("");
			}
			if(StringUtils.isNotBlank((String)d.get("securityDutySituation"))){
				exdto.setSecurityDutySituation(d.get("securityDutySituation").toString());
			}else{
				exdto.setSecurityDutySituation("");
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
	private HSSFWorkbook writeDataInExcel(Map<String,List<SecurityQueryDto>> map) {
		HSSFWorkbook wb = null;
		try {
			wb =  readFileUtil.getWorkBook(REPAIR_MODEL_PATH);
				HSSFSheet s = wb.getSheetAt(0);//获取sheet页
				List<SecurityQueryDto> list;
				//获取对应sheet页的数据list
				list=map.get("01");
				if(list.size()>0){
					//遍历集合，将数据写入
					for(int r=0;r<list.size();r++){
						HSSFRow valueRow = s.getRow(r+4);//从空白行开始写入
						if(valueRow==null){
							valueRow=s.createRow(r+4);
							valueRow.createCell(0).setCellValue(r+1);//序号
							valueRow.createCell(1).setCellValue(list.get(r).getSecurityDate());
							valueRow.createCell(2).setCellValue(list.get(r).getObligationDepart());
							valueRow.createCell(3).setCellValue(list.get(r).getSecurityLineName());
							valueRow.createCell(4).setCellValue(list.get(r).getSecurityLevel());
							valueRow.createCell(5).setCellValue(list.get(r).getSecurityDetailLevel());
							valueRow.createCell(6).setCellValue(list.get(r).getSecurityDigest());
							valueRow.createCell(7).setCellValue(list.get(r).getSecurityReason());
							valueRow.createCell(8).setCellValue(list.get(r).getSecurityGeneral());
							valueRow.createCell(9).setCellValue(list.get(r).getSecurityDutySituation());
						}
						valueRow.getCell(0).setCellValue(r+1);//序号
						valueRow.getCell(1).setCellValue(list.get(r).getSecurityDate());
						valueRow.getCell(2).setCellValue(list.get(r).getObligationDepart());
						valueRow.getCell(3).setCellValue(list.get(r).getSecurityLineName());
						valueRow.getCell(4).setCellValue(list.get(r).getSecurityLevel());
						valueRow.getCell(5).setCellValue(list.get(r).getSecurityDetailLevel());
						valueRow.getCell(6).setCellValue(list.get(r).getSecurityDigest());
						valueRow.getCell(7).setCellValue(list.get(r).getSecurityReason());
						valueRow.getCell(8).setCellValue(list.get(r).getSecurityGeneral());
						valueRow.getCell(9).setCellValue(list.get(r).getSecurityDutySituation());
						
						}
					}
		    return wb;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}


		
}