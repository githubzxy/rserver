package com.enovell.yunwei.km_micor_service.service.productionManage.JobRecord;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ProcuratorialDailyDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.JobRecordDto;
import com.enovell.yunwei.km_micor_service.util.ReadFileUtil;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Service(value = "JobRecordService")
public class JobRecordServiceImpl implements JobRecordService{
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
    @Autowired
	 private ReadFileUtil readFileUtil;
	 /**
	  * 导出Excel的模板路径
	  */
	 private static final String MONTH_WORKAREA_MODEL = "/static/exportModel/jobRecord_WorkAreaMonth.xls";
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    @Override
    public List < JobRecordDto > getExcelInfo(MultipartFile mfile) {
        String fileName = mfile.getOriginalFilename(); //获取文件名
        List < JobRecordDto > crdList = null;
        try {
            if (!validateExcel(fileName)) { // 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true; // 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            crdList = createExcel(mfile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crdList;
    }
    private List < JobRecordDto > createExcel(InputStream inputStream, boolean isExcel2003) throws ParseException {
        List < JobRecordDto > crdList = null;
        try {
            Workbook wb = null;
            if (isExcel2003) { // 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(inputStream);
            } else { // 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(inputStream);
            }
            crdList = readExcelValue(wb); // 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crdList;
    }
    /**
     * 
     * convertCheckDate 转换日期格式
     * @param cell
     * @param maintenanceMemorendunDto
     * @throws ParseException
     */
    private String  convertCheckDate(Cell cell) throws ParseException {
        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            //用于转化为日期格式
            Date d = cell.getDateCellValue();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formater.format(d);
            return dateString;
        } else {
            // 用于格式化数字，只保留数字的整数部分
            DecimalFormat df = new DecimalFormat("0");
            String dateString = df.format(cell.getNumericCellValue());
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formater.format(dateString);
           return dateStr;
        }
    }
    /**
     * 修改版本
     * @param wb
     * @return
     * @throws ParseException
     */
    private List < JobRecordDto > readExcelValue(Workbook wb) throws ParseException {
        // 得到第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(1) != null) {
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List < JobRecordDto > crdList = new ArrayList < JobRecordDto > ();
        //获取公共参数
        Row rowFirst = sheet.getRow(0);
        String workAreaString = "";
        if(rowFirst.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
        	workAreaString = String.valueOf(new Double(rowFirst.getCell(1).getNumericCellValue()).intValue());
        }else{
        	workAreaString = rowFirst.getCell(1).getStringCellValue();//工长
        }
        String onlineNumber = "";
        if(rowFirst.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
        	onlineNumber = String.valueOf(new Double(rowFirst.getCell(3).getNumericCellValue()).intValue());
        }else{
        	onlineNumber = rowFirst.getCell(3).getStringCellValue();//在册人数
        }
//        float allJoinerSum = 0;
//        float alldirectHourSum = 0;
//        float allotherHourSum= 0;
//        float allHourSum = 0;
        // 循环Excel行数,从第三行读取
        for (int r = 2; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            JobRecordDto jobRecordDto = new JobRecordDto("",workAreaString,"",onlineNumber);
//            if (r==this.totalRows-2) {
//            	jobRecordDto.setProject("总合计");;
//            	jobRecordDto.setContent("");
//            	jobRecordDto.setJoiner("");
//            	jobRecordDto.setJoinerNumber(String.valueOf(allJoinerSum));
//            	jobRecordDto.setDirectHour(String.valueOf(alldirectHourSum));
//            	jobRecordDto.setOtherHour(String.valueOf(allotherHourSum));
//            	jobRecordDto.setAllHour(String.valueOf(allHourSum));
//            	 // 添加到list
//                crdList.add(jobRecordDto);
//            	continue;
//            }
            String directHourNumString = new String();
            String otherHourString = new String();
            // 循环Excel的列
            for (int c = 1; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
               
               if (null != cell) {
                    if (c == 1) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    		jobRecordDto.setProject(String.valueOf(new Double(cell.getNumericCellValue()).intValue()));
							continue;
                    	}
                		jobRecordDto.setProject(cell.getStringCellValue());
                		continue;
                    }
                    if (c == 2) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    		jobRecordDto.setContent(String.valueOf(new Double(cell.getNumericCellValue()).intValue()));
							continue;
                    	}
                    	jobRecordDto.setContent(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 3) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    		jobRecordDto.setJoiner(String.valueOf(new Double(cell.getNumericCellValue()).intValue()));
							continue;
                    	}
                    	jobRecordDto.setJoiner(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 4) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    		String joinerNumber = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
                    		jobRecordDto.setJoinerNumber(joinerNumber);
//                    		allJoinerSum+=Float.valueOf(joinerNumber);
							continue;
                    	}
                    	jobRecordDto.setJoinerNumber(cell.getStringCellValue());
//                    	allJoinerSum+=Float.valueOf(joinerNumber);
                        continue;
                    }
                    
//                    if (c == 5) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		directHourNumString = String.valueOf(cell.getNumericCellValue());
//                    		jobRecordDto.setDirectHour(String.valueOf(cell.getNumericCellValue()));
//                    		alldirectHourSum+=Float.valueOf(directHourNumString);
//							continue;
//                    	}
//                    	directHourNumString = cell.getStringCellValue();
//                    	jobRecordDto.setDirectHour(directHourNumString);
//                    	alldirectHourSum+=Float.valueOf(directHourNumString);
//                        continue;
//                    }
                   
//                    if (c == 6) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		otherHourString = String.valueOf(cell.getNumericCellValue());
//                    		jobRecordDto.setOtherHour(otherHourString);
//                    		allotherHourSum+=Float.valueOf(otherHourString);
//							continue;
//                    	}
//                    	 otherHourString = cell.getStringCellValue();
//                    	jobRecordDto.setOtherHour(otherHourString);
//                    	allotherHourSum+=Float.valueOf(otherHourString);
//                        continue;
//                    }
//                    if (c == 7) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		String allHourString = String.valueOf(cell.getNumericCellValue());
//                    		if (StringUtils.isNotBlank(allHourString)) {
//                    			jobRecordDto.setAllHour(allHourString);
//                    			allHourSum+=Float.valueOf(allHourString);
//                    			continue;
//							}
//                    		Integer sumInteger = Integer.valueOf(directHourNumString)+Integer.valueOf(otherHourString);
//                    		jobRecordDto.setAllHour(String.valueOf(sumInteger));
//                    		allHourSum+=Float.valueOf(sumInteger);
//                    		continue;
//                    	}else {
//                    		String allHourString = cell.getStringCellValue();
//							if (StringUtils.isNotBlank(allHourString)) {
//								jobRecordDto.setAllHour(allHourString);
//                    			allHourSum+=Float.valueOf(allHourString);
//                    			continue;
//							}
//							float sumInteger = Float.valueOf(directHourNumString)+Float.valueOf(otherHourString);
//	                		jobRecordDto.setAllHour(String.valueOf(sumInteger));
//	                		allHourSum+=sumInteger;
//	                        continue;
//						}
//                    }
               }
            }
            // 添加到list
            crdList.add(jobRecordDto);
        }
        return crdList;
    }
    
    /**
     * 第一版本
     */
//    private List < JobRecordDto > readExcelValue(Workbook wb) throws ParseException {
//        // 得到第一个shell
//        Sheet sheet = wb.getSheetAt(0);
//        // 得到Excel的行数
//        this.totalRows = sheet.getPhysicalNumberOfRows();
//        // 得到Excel的列数(前提是有行数)
//        if (totalRows > 1 && sheet.getRow(0) != null) {
//            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
//        }
//        List < JobRecordDto > crdList = new ArrayList < JobRecordDto > ();
//        //获取公共参数
//        String nameString = sheet.getRow(0).getCell(0).getStringCellValue();//标题
//        Row rowFirst = sheet.getRow(1);
//        String workAreaString = rowFirst.getCell(1).getStringCellValue();//工区
//        String dateString = convertCheckDate(rowFirst.getCell(4));//日期
//        String onlineNumber = String.valueOf(rowFirst.getCell(7).getNumericCellValue());//在册人数
//        float allJoinerSum = 0;
//        float alldirectHourSum = 0;
//        float allotherHourSum= 0;
//        float allHourSum = 0;
//        // 循环Excel行数,从第四行读取
//        for (int r = 3; r < totalRows; r++) {
//            Row row = sheet.getRow(r);
//            if (row == null||r == totalRows-1) {
//                continue;
//            }
//            JobRecordDto jobRecordDto = new JobRecordDto(nameString,workAreaString,dateString,onlineNumber);
//            if (r==this.totalRows-2) {
//            	jobRecordDto.setProject("总合计");;
//            	jobRecordDto.setContent("");
//            	jobRecordDto.setJoiner("");
//            	jobRecordDto.setJoinerNumber(String.valueOf(allJoinerSum));
//            	jobRecordDto.setDirectHour(String.valueOf(alldirectHourSum));
//            	jobRecordDto.setOtherHour(String.valueOf(allotherHourSum));
//            	jobRecordDto.setAllHour(String.valueOf(allHourSum));
//            	 // 添加到list
//                crdList.add(jobRecordDto);
//            	continue;
//            }
//            String directHourNumString = new String();
//            String otherHourString = new String();
//            // 循环Excel的列
//            for (int c = 1; c < this.totalCells; c++) {
//                Cell cell = row.getCell(c);
//               
//               if (null != cell) {
//                    if (c == 1) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		jobRecordDto.setProject(String.valueOf(cell.getNumericCellValue()));
//							continue;
//                    	}
//                    		jobRecordDto.setProject(cell.getStringCellValue());
//                    		continue;
//                    }
//                    if (c == 2) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		jobRecordDto.setContent(String.valueOf(cell.getNumericCellValue()));
//							continue;
//                    	}
//                    	jobRecordDto.setContent(cell.getStringCellValue());
//                        continue;
//                    }
//                    if (c == 3) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		jobRecordDto.setJoiner(String.valueOf(cell.getNumericCellValue()));
//							continue;
//                    	}
//                    	jobRecordDto.setJoiner(cell.getStringCellValue());
//                        continue;
//                    }
//                    if (c == 4) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		String joinerNumber = String.valueOf(cell.getNumericCellValue());
//                    		jobRecordDto.setJoinerNumber(joinerNumber);
//                    		allJoinerSum+=Float.valueOf(joinerNumber);
//							continue;
//                    	}
//                    	String joinerNumber = cell.getStringCellValue();
//                    	jobRecordDto.setJoinerNumber(joinerNumber);
//                    	allJoinerSum+=Float.valueOf(joinerNumber);
//                        continue;
//                    }
//                    
//                    if (c == 5) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		directHourNumString = String.valueOf(cell.getNumericCellValue());
//                    		jobRecordDto.setDirectHour(String.valueOf(cell.getNumericCellValue()));
//                    		alldirectHourSum+=Float.valueOf(directHourNumString);
//							continue;
//                    	}
//                    	directHourNumString = cell.getStringCellValue();
//                    	jobRecordDto.setDirectHour(directHourNumString);
//                    	alldirectHourSum+=Float.valueOf(directHourNumString);
//                        continue;
//                    }
//                   
//                    if (c == 6) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		otherHourString = String.valueOf(cell.getNumericCellValue());
//                    		jobRecordDto.setOtherHour(otherHourString);
//                    		allotherHourSum+=Float.valueOf(otherHourString);
//							continue;
//                    	}
//                    	 otherHourString = cell.getStringCellValue();
//                    	jobRecordDto.setOtherHour(otherHourString);
//                    	allotherHourSum+=Float.valueOf(otherHourString);
//                        continue;
//                    }
//                    if (c == 7) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                    		String allHourString = String.valueOf(cell.getNumericCellValue());
//                    		if (StringUtils.isNotBlank(allHourString)) {
//                    			jobRecordDto.setAllHour(allHourString);
//                    			allHourSum+=Float.valueOf(allHourString);
//                    			continue;
//							}
//                    		Integer sumInteger = Integer.valueOf(directHourNumString)+Integer.valueOf(otherHourString);
//                    		jobRecordDto.setAllHour(String.valueOf(sumInteger));
//                    		allHourSum+=Float.valueOf(sumInteger);
//                    		continue;
//                    	}else {
//                    		String allHourString = cell.getStringCellValue();
//							if (StringUtils.isNotBlank(allHourString)) {
//								jobRecordDto.setAllHour(allHourString);
//                    			allHourSum+=Float.valueOf(allHourString);
//                    			continue;
//							}
//							float sumInteger = Float.valueOf(directHourNumString)+Float.valueOf(otherHourString);
//	                		jobRecordDto.setAllHour(String.valueOf(sumInteger));
//	                		allHourSum+=sumInteger;
//	                        continue;
//						}
//                    	
//                    }
//                    
//            }
//          
//        }
//         // 添加到list
//            crdList.add(jobRecordDto);
//        }
//        return crdList;
//        }
    
    
    	/**
		     * 验证EXCEL文件
		* @param filePath
		* @return
		*/
		public boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
		errorMsg = "文件名不是excel格式";
		return false;
		}
		return true;
		}
		
	    // @描述：是否是2003的excel，返回true是2003 
	    public static boolean isExcel2003(String filePath) {
	        return filePath.matches("^.+\\.(?i)(xls)$");
	    }
	    //@描述：是否是2007的excel，返回true是2007 
	    public static boolean isExcel2007(String filePath) {
	        return filePath.matches("^.+\\.(?i)(xlsx)$");
	    }
	    //上传文件
	    @Override
	    public List < Document > uploadFile(List < MultipartFile > files) {

	        List < Document > uploadFiles = new ArrayList < > ();
	        //读取上传文件，封装为上传文件对象
	        files.forEach(file -> {
	            if (StringUtils.isBlank(file.getOriginalFilename())) {
	                return;
	            }
	            Document uploadFile = new Document();
	            uploadFile.put("name", file.getOriginalFilename());
	            uploadFile.put("date", new Date());
	            String id = UUIDUtils.getUUID();
	            uploadFile.put("id", id);
	            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	            String savePath = uploadPath + "/" + id + suffix;
	            uploadFile.put("path", savePath);
	            try {
	                file.transferTo(new File(savePath));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            uploadFiles.add(uploadFile);
	        });
	        return uploadFiles;
	    }
	    
	    @Override
	    public void deleteAllDocument(String collectionName, String currentDay, String orgId){
	    	try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            FindIterable < Document > findIterable = md.getCollection(collectionName).find(new Document("date", currentDay).append("orgId", orgId).append("status", 1));
	            if(findIterable!=null){
	            	md.getCollection(collectionName).deleteMany(new Document("date", currentDay).append("orgId", orgId).append("status", 1));
	            }
	    	}
	    }
	    
		@Override
		public Document addDocument(Document document, String collectionName) {
			try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            document.append("status", 1);
	            document.append("busiId", UUIDUtils.getUUID());
	            md.getCollection(collectionName).insertOne(document);
	        }
	        return document;
		}
		@Override
		public boolean checkData(GridDto<Document> result,String startDate,String endDate, String project) {
			
			  try {
		            if (StringUtils.isNotBlank(startDate)) {
		            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            	 sdf.parse(startDate);
		            }
		            if (StringUtils.isNotBlank(endDate)) {
		            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            	sdf.parse(endDate);
		            }
		            
		        } catch (ParseException e) {
		            result.setHasError(true);
		            result.setError("时间格式错误");
		            return false;
		        }
			return true;
			
		}
		@Override
		public Long findAllDocumentCount(String collectionName,String workshopType,String workshopId,String workareaId, String startDate,String endDate, String project, String orgId, int orgType) {
			try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(workshopType, workshopId,workareaId, startDate,endDate, project, orgId, orgType);
	            return md.getCollection(collectionName).count(filter);
	        }
		}
		private Bson getFilter(String workshopType,String workshopId,String workareaId, String startDate,String endDate, String project, String orgId, int orgType) {
			Bson filter = Filters.eq("status", 1);
			filter = Filters.and(filter, Filters.ne("filesStatus", "1"));
			if (StringUtils.isNotBlank(orgId)) {
				if(orgType==1503){
					filter = Filters.and(filter, Filters.eq("orgId", orgId));
				}
				else if(orgType==1502){
					if (StringUtils.isNotBlank(workareaId)) {
			        	filter = Filters.and(filter, Filters.eq("orgId", workareaId));
			        }else{
			        	filter = Filters.and(filter, Filters.eq("orgId",orgId));
			        }
				}
			}
	        if (StringUtils.isNotBlank(startDate)) {
	            filter = Filters.and(filter, Filters.gte("date", startDate));
	        }
	        if (StringUtils.isNotBlank(endDate)) {
	        	filter = Filters.and(filter, Filters.lte("date", endDate));
	        }
	        if (StringUtils.isNotBlank(project)) {
	            filter = Filters.and(filter, Filters.regex("project", project));
	        }
//	        if (StringUtils.isNotBlank(workareaId)) {
//	        	filter = Filters.and(filter, Filters.eq("orgId", workareaId));
//	        }else{
//	        	filter = Filters.and(filter, Filters.eq("orgId",orgId));
//	        }
	        if (StringUtils.isNotBlank(workshopId)) {
	        	if("1503".equals(workshopType)){
					filter = Filters.and(filter, Filters.eq("orgId", workshopId));
				}else if("1502".equals(workshopType)){
					filter = Filters.and(filter, Filters.eq("orgId", workshopId));
				}
	        }
	        return filter;
		}
		@Override
		public List<Document> findAllDocument(String collectionName,String workshopType,String workshopId,String workareaId, String startDate,String endDate, String project, String orgId, int orgType, int start, int limit) {
			List < Document > results = new ArrayList < > ();
	        try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(workshopType, workshopId,workareaId ,startDate, endDate, project, orgId, orgType);
	            FindIterable < Document > findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("date", -1));
	            findIterable.forEach((Block < ? super Document > ) results::add);
	        }
	        results.stream().forEach(d -> {
	            d.append("docId", d.getObjectId("_id").toHexString());
	            d.append("dateAndOrgId", d.getString("date")+","+d.getString("orgId"));
	            d.remove("_id");
	        });
	        return results;
		}
		
		@Override
	    public Document findDocumentById(String id, String collectionName) {
	        try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            FindIterable < Document > findIterable = md.getCollection(collectionName).find(new Document("_id", new ObjectId(id)).append("status", 1));
	            Document doc = findIterable.first();

	            doc.append("docId", doc.getObjectId("_id").toHexString());
	            doc.remove("_id");

	            return doc;
	        }
	    }
		
		 @Override
		    public Document modifyDocument(Document doc, String collectionName) {
		        try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
		            MongoDatabase md = mc.getDatabase(mongoDatabase);
		            return md.getCollection(collectionName)
		                .findOneAndUpdate(
		                    Filters.eq("_id", new ObjectId(doc.getString("docId"))), new Document("$set", doc)
		                );
		        }
		    }
		@Override
		public Workbook exportExcel(String date, String workShop, String workArea,String collectionName) throws Exception {
			 HSSFWorkbook wb = null ;
			if (workArea.equals("所有工区")) {
				//获取车间所有工区
				List<Map<String, Object>> partsList = this.getDeparts(workShop);
				int size = partsList.size();
				
				String baseUrlString = "/static/exportModel/JonRecord_room_month/jobRecord_RoomMoth1.xls";
				wb=  readFileUtil.getWorkBook(baseUrlString);
				//设置表头
				setExcelTitle(date,workShop,wb,size);
				
				/**
				 * 统计总数
				 */
				List<JobRecordDto> jobRecordAllWorkAreaDtos = new ArrayList<JobRecordDto>();
				/**
				 * 值班数据统计
				 */
				float joinerNumberZhiban = 0;
				float directHourZhiban = 0;
				float otherHourZhiban = 0;
				float allHourZhiban = 0;
				/**
				 * 日常维修工作
				 */
				float joinerNumberRichang = 0;
				float directHourRichang = 0;
				float otherHourRichang = 0;
				float allHourRichang = 0;
				/**
				 * 故障处理
				 */
				float joinerNumberGz = 0;
				float directHourGz = 0;
				float otherHourGz = 0;
				float allHourGz = 0;
				/**
				 * 专项活动
				 */
				float joinerNumberZx = 0;
				float directHourZx  = 0;
				float otherHourZx  = 0;
				float allHourZx  = 0;
				/**
				 * 施工配合
				 */
				float joinerNumberSg = 0;
				float directHourSg = 0;
				float otherHourSg = 0;
				float allHourSg = 0;
				/**
				 * 重要文电学习
				 */
				float joinerNumberWd = 0;
				float directHourWd  = 0;
				float otherHourWd  = 0;
				float allHourWd  = 0;
				/**
				 * 外出培训学习情况
				 */
				float joinerNumberPx = 0;
				float directHourPx = 0;
				float otherHourPx = 0;
				float allHourPx = 0;
				/**
				 * 休假情况
				 */
				float joinerNumberXj = 0;
				float directHourXj = 0;
				float otherHourXj = 0;
				float allHourXj = 0;
				/**
				 * 其他
				 */
				float joinerNumberQt = 0;
				float directHourQt = 0;
				float otherHourQt = 0;
				float allHourQt = 0;
				/**
				 * 总合计
				 */
				float joinerNumberHj = 0;
				float directHourHj = 0;
				float otherHourHj = 0;
				float allHourHj = 0;
				for (int i = 0; i < partsList.size(); i++) {
					String workAreaNameString =(String) partsList.get(i).get("orgName");
					List<JobRecordDto> allDtos = getDateByFilter(date,workAreaNameString,collectionName);
					if (allDtos==null) {
						continue;
					}
					//将数据计算成汇总数
					List<JobRecordDto> jobRecordDtos = getJobRecordDtoStatics(allDtos);
					if (jobRecordDtos==null) {
						continue;
					}
					//汇总值班情况
					joinerNumberZhiban+=Float.valueOf(jobRecordDtos.get(0).getJoinerNumber());
					directHourZhiban+=Float.valueOf(jobRecordDtos.get(0).getDirectHour());
					otherHourZhiban+=Float.valueOf(jobRecordDtos.get(0).getOtherHour());
					allHourZhiban+=Float.valueOf(jobRecordDtos.get(0).getAllHour());
					
					//汇总日常维修
					joinerNumberRichang+=Float.valueOf(jobRecordDtos.get(1).getJoinerNumber());
					directHourRichang+=Float.valueOf(jobRecordDtos.get(1).getDirectHour());
					otherHourRichang+=Float.valueOf(jobRecordDtos.get(1).getOtherHour());
					allHourRichang+=Float.valueOf(jobRecordDtos.get(1).getAllHour());
					
					//汇总故障处理
					joinerNumberGz+=Float.valueOf(jobRecordDtos.get(2).getJoinerNumber());
					directHourGz+=Float.valueOf(jobRecordDtos.get(2).getDirectHour());
					otherHourGz+=Float.valueOf(jobRecordDtos.get(2).getOtherHour());
					allHourGz+=Float.valueOf(jobRecordDtos.get(2).getAllHour());
					
					//汇总专项活动
					joinerNumberZx+=Float.valueOf(jobRecordDtos.get(3).getJoinerNumber());
					directHourZx+=Float.valueOf(jobRecordDtos.get(3).getDirectHour());
					otherHourZx+=Float.valueOf(jobRecordDtos.get(3).getOtherHour());
					allHourZx+=Float.valueOf(jobRecordDtos.get(3).getAllHour());
					
					//汇总施工配合
					joinerNumberSg+=Float.valueOf(jobRecordDtos.get(4).getJoinerNumber());
					directHourSg+=Float.valueOf(jobRecordDtos.get(4).getDirectHour());
					otherHourSg+=Float.valueOf(jobRecordDtos.get(4).getOtherHour());
					allHourSg+=Float.valueOf(jobRecordDtos.get(4).getAllHour());
					
					//汇总重要文电学习
					joinerNumberWd+=Float.valueOf(jobRecordDtos.get(5).getJoinerNumber());
					directHourWd+=Float.valueOf(jobRecordDtos.get(5).getDirectHour());
					otherHourWd+=Float.valueOf(jobRecordDtos.get(5).getOtherHour());
					allHourWd+=Float.valueOf(jobRecordDtos.get(5).getAllHour());
					
					//汇总外出培训学习情况
					joinerNumberPx+=Float.valueOf(jobRecordDtos.get(6).getJoinerNumber());
					directHourPx+=Float.valueOf(jobRecordDtos.get(6).getDirectHour());
					otherHourPx+=Float.valueOf(jobRecordDtos.get(6).getOtherHour());
					allHourPx+=Float.valueOf(jobRecordDtos.get(6).getAllHour());
					
					//汇总休假情况
					joinerNumberXj+=Float.valueOf(jobRecordDtos.get(7).getJoinerNumber());
					directHourXj+=Float.valueOf(jobRecordDtos.get(7).getDirectHour());
					otherHourXj+=Float.valueOf(jobRecordDtos.get(7).getOtherHour());
					allHourXj+=Float.valueOf(jobRecordDtos.get(7).getAllHour());
					
					//汇总其他
					joinerNumberQt+=Float.valueOf(jobRecordDtos.get(8).getJoinerNumber());
					directHourQt+=Float.valueOf(jobRecordDtos.get(8).getDirectHour());
					otherHourQt+=Float.valueOf(jobRecordDtos.get(8).getOtherHour());
					allHourQt+=Float.valueOf(jobRecordDtos.get(8).getAllHour());
					
					//汇总休假情况
					joinerNumberHj+=Float.valueOf(jobRecordDtos.get(9).getJoinerNumber());
					directHourHj+=Float.valueOf(jobRecordDtos.get(9).getDirectHour());
					otherHourHj+=Float.valueOf(jobRecordDtos.get(9).getOtherHour());
					allHourHj+=Float.valueOf(jobRecordDtos.get(9).getAllHour());
					wb = writeDataInExcel(date,workShop,workAreaNameString,jobRecordDtos,"",baseUrlString,i,wb);
				}
				JobRecordDto jobRecordDto2 = setDateJobRecord("值班",joinerNumberZhiban,directHourZhiban,otherHourZhiban,allHourZhiban);
				JobRecordDto jobRecordDto3 = setDateJobRecord("日常维修工作",joinerNumberRichang,directHourRichang,otherHourRichang,allHourRichang);
				JobRecordDto jobRecordDto4 = setDateJobRecord("故障处理",joinerNumberGz,directHourGz,otherHourGz,allHourGz);
				JobRecordDto jobRecordDto5 = setDateJobRecord("专项活动",joinerNumberZx,directHourZx,otherHourZx,allHourZx);
				JobRecordDto jobRecordDto6 = setDateJobRecord("施工配合",joinerNumberSg,directHourSg,otherHourSg,allHourSg);
				JobRecordDto jobRecordDto7 = setDateJobRecord("重要文电学习",joinerNumberWd,directHourWd,otherHourWd,allHourWd);
				JobRecordDto jobRecordDto8 = setDateJobRecord("外出培训学习情况",joinerNumberPx,directHourPx,otherHourPx,allHourPx);
				JobRecordDto jobRecordDto9 = setDateJobRecord("休假情况",joinerNumberXj,directHourXj,otherHourXj,allHourXj);
				JobRecordDto jobRecordDto10 = setDateJobRecord("其他",joinerNumberQt,directHourQt,otherHourQt,allHourQt);
				JobRecordDto jobRecordDto11 = setDateJobRecord("总合计",joinerNumberHj,directHourHj,otherHourHj,allHourHj);
				
				jobRecordAllWorkAreaDtos.add(jobRecordDto2);
				jobRecordAllWorkAreaDtos.add(jobRecordDto3);
				jobRecordAllWorkAreaDtos.add(jobRecordDto4);
				jobRecordAllWorkAreaDtos.add(jobRecordDto5);
				jobRecordAllWorkAreaDtos.add(jobRecordDto6);
				jobRecordAllWorkAreaDtos.add(jobRecordDto7);
				jobRecordAllWorkAreaDtos.add(jobRecordDto8);
				jobRecordAllWorkAreaDtos.add(jobRecordDto9);
				jobRecordAllWorkAreaDtos.add(jobRecordDto10);
				jobRecordAllWorkAreaDtos.add(jobRecordDto11);
				
			wb = writeDataInExcel(date,workShop,"合计",jobRecordAllWorkAreaDtos,"",baseUrlString,size,wb);
				
			}else {
				String dateString = date.substring(0, 7);
				List<JobRecordDto> allDtos = getDateByFilter(dateString,workArea,collectionName);
	
				String onlineString = "";
				//将数据计算成汇总数
				List<JobRecordDto> jobRecordDtos = getJobRecordDtoStatics(allDtos);
				//将数据写入excel
				wb = writeDataInExcel(dateString,workShop,workArea,jobRecordDtos,onlineString);
			}
			return wb;
		}
		private void setExcelTitle(String date, String workShop, HSSFWorkbook wb, int size) {
			String dateMonthString = date.substring(0, 7);//2019-5-56
			String titleString = workShop+dateMonthString.replace("-", "年")+"月工时统计表";
			HSSFSheet s = wb.getSheetAt(0);//获取sheet页
			Row row1 = s.getRow(0);
			Cell cell1 = row1.getCell(0);//表头
			cell1.setCellValue(titleString);
			//合并表头
			for (int i = 0; i < size; i++) {
				row1.createCell(i*4+2);
			}
			CellRangeAddress region = new CellRangeAddress(0, 0, 0,size*4+5);
	    	s.addMergedRegion(region);
	    	HSSFCellStyle cellStyle = wb.createCellStyle();
	    	HSSFFont font = wb.createFont();
	    	font.setFontName("黑体");  
			font.setFontHeightInPoints((short) 20);//设置字体大小 
			cellStyle.setFont(font);
	    	cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中    	
	    	cell1.setCellStyle(cellStyle);
			
		}
		private HSSFWorkbook writeDataInExcel(String date, String workShop, String workArea,
				List<JobRecordDto> jobRecordDtos, String onlineString, String baseUrlString, int i,HSSFWorkbook wb) throws Exception {
			HSSFSheet s = wb.getSheetAt(0);//获取sheet页
			//创建表格
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中    	
			for (int j = 1; j < 13; j++) {
				Row rowdRow= s.getRow(j);
				Cell cell = rowdRow.createCell(i*4+2);
				cell.setCellStyle(cellStyle);
				Cell cell2 = rowdRow.createCell(i*4+3);
				cell2.setCellStyle(cellStyle);
				Cell cell3 = rowdRow.createCell(i*4+4);
				cell3.setCellStyle(cellStyle);
				Cell cell4 = rowdRow.createCell(i*4+5);
				cell4.setCellStyle(cellStyle);
				/**
				 * 表头设值，合并
				 */
				if (j==1) {
					CellRangeAddress region = new CellRangeAddress(1, 1,i*4+2,i*4+5);
			    	s.addMergedRegion(region);
			    	cell.setCellValue(workArea);
				}
				if (j==2) {
					cell.setCellValue("平均人数");
					cell2.setCellValue("直接工时");
					cell3.setCellValue("间接工时");
					cell4.setCellValue("合计工时");
				}
				if (j>=3) {
					cell.setCellValue(jobRecordDtos.get(j-3).getJoinerNumber());
					cell2.setCellValue(jobRecordDtos.get(j-3).getDirectHour());
					cell3.setCellValue(jobRecordDtos.get(j-3).getOtherHour());
					cell4.setCellValue(jobRecordDtos.get(j-3).getAllHour());
				}
				
			}
			return wb;
		}
		private HSSFWorkbook writeDataInExcel(String date, String workShop, String workArea,
				List<JobRecordDto> jobRecordDtos, String onlineString) throws Exception {
			HSSFWorkbook wb = null;
			String dateMonthString = date.substring(0, 7);//2019-5-56
			String titleString = workArea+"班组"+date.replace("-", "年")+"月工时统计表";
			wb =  readFileUtil.getWorkBook(MONTH_WORKAREA_MODEL);
			HSSFSheet s = wb.getSheetAt(0);//获取sheet页
			Row row1 = s.getRow(0);
			Row rowSec= s.getRow(1);
			Cell cell1 = row1.getCell(0);//表头
			Cell cellSecond = rowSec.getCell(1);//表头
			Cell cellFour = rowSec.getCell(4);//表头
			cell1.setCellValue(titleString);
			cellSecond.setCellValue(workArea);
			cellFour.setCellValue(onlineString);
			cell1.setCellValue(titleString);
			cellSecond.setCellValue(workArea);
			cellFour.setCellValue(onlineString);
			for (int i = 0; i <jobRecordDtos.size(); i++) {
				Row row = s.getRow(i+3);
				Cell cell2 = row.getCell(2);
				Cell cell3 = row.getCell(3);
				Cell cell4 = row.getCell(4);
				cell2.setCellValue(jobRecordDtos.get(i).getDirectHour());
				cell3.setCellValue(jobRecordDtos.get(i).getOtherHour());
				cell4.setCellValue(jobRecordDtos.get(i).getAllHour());
			}
			return wb;
		}
		private List<JobRecordDto> getJobRecordDtoStatics(List<JobRecordDto> allDtos) {
			List<JobRecordDto> JobRecordDto = new ArrayList<JobRecordDto>();
			/**
			 * 值班数据统计
			 */
			float joinerNumberZhiban = 0;
			float directHourZhiban = 0;
			float otherHourZhiban = 0;
			float allHourZhiban = 0;
			/**
			 * 日常维修工作
			 */
			float joinerNumberRichang = 0;
			float directHourRichang = 0;
			float otherHourRichang = 0;
			float allHourRichang = 0;
			/**
			 * 故障处理
			 */
			float joinerNumberGz = 0;
			float directHourGz = 0;
			float otherHourGz = 0;
			float allHourGz = 0;
			/**
			 * 专项活动
			 */
			float joinerNumberZx = 0;
			float directHourZx  = 0;
			float otherHourZx  = 0;
			float allHourZx  = 0;
			/**
			 * 施工配合
			 */
			float joinerNumberSg = 0;
			float directHourSg = 0;
			float otherHourSg = 0;
			float allHourSg = 0;
			/**
			 * 重要文电学习
			 */
			float joinerNumberWd = 0;
			float directHourWd  = 0;
			float otherHourWd  = 0;
			float allHourWd  = 0;
			/**
			 * 外出培训学习情况
			 */
			float joinerNumberPx = 0;
			float directHourPx = 0;
			float otherHourPx = 0;
			float allHourPx = 0;
			/**
			 * 休假情况
			 */
			float joinerNumberXj = 0;
			float directHourXj = 0;
			float otherHourXj = 0;
			float allHourXj = 0;
			/**
			 * 其他
			 */
			float joinerNumberQt = 0;
			float directHourQt = 0;
			float otherHourQt = 0;
			float allHourQt = 0;
			/**
			 * 总合计
			 */
			float joinerNumberHj = 0;
			float directHourHj = 0;
			float otherHourHj = 0;
			float allHourHj = 0;
			for (JobRecordDto jobRecordDto2 : allDtos) {
				String projectString = jobRecordDto2.getProject();
				String joinerNumber =  jobRecordDto2.getJoinerNumber();
				String directHour = jobRecordDto2.getDirectHour();
				String otherHour = jobRecordDto2.getOtherHour();
				String allHour = jobRecordDto2.getAllHour();
				if (projectString.equals("值班情况")) {
					joinerNumberZhiban += Float.valueOf(joinerNumber);
					directHourZhiban += Float.valueOf(directHour);
					otherHourZhiban += Float.valueOf(otherHour);
					allHourZhiban += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("日常维修工作")) {
					joinerNumberRichang += Float.valueOf(joinerNumber);
					directHourRichang += Float.valueOf(directHour);
					otherHourRichang += Float.valueOf(otherHour);
					allHourRichang += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("故障处理")) {
					joinerNumberGz += Float.valueOf(joinerNumber);
					directHourGz += Float.valueOf(directHour);
					otherHourGz += Float.valueOf(otherHour);
					allHourGz += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("专项活动")) {
					joinerNumberZx += Float.valueOf(joinerNumber);
					directHourZx += Float.valueOf(directHour);
					otherHourZx += Float.valueOf(otherHour);
					allHourZx += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("施工配合")) {
					joinerNumberSg += Float.valueOf(joinerNumber);
					directHourSg += Float.valueOf(directHour);
					otherHourSg += Float.valueOf(otherHour);
					allHourSg += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("重要文电学习")) {
					joinerNumberWd += Float.valueOf(joinerNumber);
					directHourWd += Float.valueOf(directHour);
					otherHourWd += Float.valueOf(otherHour);
					allHourWd += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("外出培训学习情况")) {
					joinerNumberPx += Float.valueOf(joinerNumber);
					directHourPx += Float.valueOf(directHour);
					otherHourPx += Float.valueOf(otherHour);
					allHourPx += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("休假情况")) {
					joinerNumberXj += Float.valueOf(joinerNumber);
					directHourXj += Float.valueOf(directHour);
					otherHourXj += Float.valueOf(otherHour);
					allHourXj += Float.valueOf(allHour);
					continue;
				}
				if (projectString.equals("其他")) {
					joinerNumberQt += Float.valueOf(joinerNumber);
					directHourQt += Float.valueOf(directHour);
					otherHourQt += Float.valueOf(otherHour);
					allHourQt += Float.valueOf(allHour);
					continue;
				}
				
			}
			joinerNumberHj=joinerNumberZhiban+joinerNumberRichang+joinerNumberGz+
					joinerNumberZx+joinerNumberSg+joinerNumberWd+joinerNumberPx+joinerNumberXj
					+joinerNumberQt;
			directHourHj=directHourZhiban+directHourRichang+directHourGz+
					directHourZx+directHourSg+directHourWd+directHourPx+directHourXj
					+directHourQt;
			otherHourHj=otherHourZhiban+otherHourRichang+otherHourGz+
					otherHourZx+otherHourSg+otherHourWd+otherHourPx+otherHourXj
					+otherHourQt;
			allHourHj=allHourZhiban+allHourRichang+allHourGz+
					allHourZx+allHourSg+allHourWd+allHourPx+allHourXj
					+allHourQt;
			JobRecordDto jobRecordDto2 = setDateJobRecord("值班",joinerNumberZhiban,directHourZhiban,otherHourZhiban,allHourZhiban);
			JobRecordDto jobRecordDto3 = setDateJobRecord("日常维修工作",joinerNumberRichang,directHourRichang,otherHourRichang,allHourRichang);
			JobRecordDto jobRecordDto4 = setDateJobRecord("故障处理",joinerNumberGz,directHourGz,otherHourGz,allHourGz);
			JobRecordDto jobRecordDto5 = setDateJobRecord("专项活动",joinerNumberZx,directHourZx,otherHourZx,allHourZx);
			JobRecordDto jobRecordDto6 = setDateJobRecord("施工配合",joinerNumberSg,directHourSg,otherHourSg,allHourSg);
			JobRecordDto jobRecordDto7 = setDateJobRecord("重要文电学习",joinerNumberWd,directHourWd,otherHourWd,allHourWd);
			JobRecordDto jobRecordDto8 = setDateJobRecord("外出培训学习情况",joinerNumberPx,directHourPx,otherHourPx,allHourPx);
			JobRecordDto jobRecordDto9 = setDateJobRecord("休假情况",joinerNumberXj,directHourXj,otherHourXj,allHourXj);
			JobRecordDto jobRecordDto10 = setDateJobRecord("其他",joinerNumberQt,directHourQt,otherHourQt,allHourQt);
			JobRecordDto jobRecordDto11 = setDateJobRecord("总合计",joinerNumberHj,directHourHj,otherHourHj,allHourHj);
			
			JobRecordDto.add(jobRecordDto2);
			JobRecordDto.add(jobRecordDto3);
			JobRecordDto.add(jobRecordDto4);
			JobRecordDto.add(jobRecordDto5);
			JobRecordDto.add(jobRecordDto6);
			JobRecordDto.add(jobRecordDto7);
			JobRecordDto.add(jobRecordDto8);
			JobRecordDto.add(jobRecordDto9);
			JobRecordDto.add(jobRecordDto10);
			JobRecordDto.add(jobRecordDto11);
			return JobRecordDto;
		}
		private JobRecordDto setDateJobRecord(String project, float joinerNumber, float directHour, float otherHour, float allHour) {
			JobRecordDto jobRecordDto = new JobRecordDto();
			jobRecordDto.setProject(project);
			jobRecordDto.setJoinerNumber(String.valueOf(joinerNumber));
			jobRecordDto.setDirectHour(String.valueOf(directHour));
			jobRecordDto.setOtherHour(String.valueOf(otherHour));
			jobRecordDto.setAllHour(String.valueOf(allHour));
			
			return jobRecordDto;
		}
		private List<JobRecordDto> getDateByFilter(String date, String workArea,String collectionName) {
			 List<Document> results = new ArrayList<>();
			 List<JobRecordDto> jobRecordDtos = new ArrayList<JobRecordDto>();
		        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
		            MongoDatabase md = mc.getDatabase(mongoDatabase);
		            Bson filter = getExportFilter(date,workArea);
		            Document doc=new Document();
		            doc.append("date", 1);
		            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).sort(doc);
		            findIterable.forEach((Block<? super Document>) results::add);
		        }
		
		  results.stream().forEach(d-> { JobRecordDto jobRecordDto = new JobRecordDto();
		  jobRecordDto.setName(d.getString("name").toString());
		  jobRecordDto.setDate(d.getString("date").toString());
		  jobRecordDto.setWorkArea(d.getString("workArea").toString());
		  jobRecordDto.setOnlineNumber(d.getString("onlineNumber").toString());
		  jobRecordDto.setJoinerNumber(d.getString("joinerNumber").toString());
		  jobRecordDto.setDirectHour(d.getString("directHour").toString());
		  jobRecordDto.setOtherHour(d.getString("otherHour").toString());
		  jobRecordDto.setAllHour(d.getString("allHour").toString());
		  jobRecordDto.setProject(d.getString("project").toString());
		   jobRecordDtos.add(jobRecordDto); });
		 
			return jobRecordDtos;
		}
		private Bson getExportFilter(String date, String workArea) {
			Bson filters=Filters.eq("status",1);
			String dateMonthString = date.substring(0, 7);//截取为2019-5-56
			if (StringUtils.isNotBlank(dateMonthString)) {
				filters = Filters.and(filters,Filters.regex("date",dateMonthString));
			}
			if (StringUtils.isNotBlank(workArea)) {
				filters = Filters.and(filters,Filters.eq("workArea",workArea));
			}
			return filters;
		}
		 
		
		@Override
		public List<Map<String, Object>> getWorkShops() {
			String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.type_ = :type";
			Map<String,Object> paramMap = new HashMap<String,Object>();
	        paramMap.put("type", "1502");//1502:车间
			List<Map<String, Object>> workShopList=template.query(sql,paramMap,new CCOrganizationMapper());
			return workShopList;
		}
		
		@Override
		public List<Map<String, Object>> getShopAndDepart(String pid, String curId) {
			String sql = "";
	        String startSql = "SELECT T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_,COUNT(O.ORG_ID_) AS CHILDCOUNT"+
	                " FROM CFG_BASE_ORGANIZATION T LEFT JOIN CFG_BASE_ORGANIZATION O"+
	                " ON T.ORG_ID_ = O.PARENT_ID_"+
	                " WHERE T.PARENT_ID_ = :pid AND T.DELETE_STATE_=1 AND T.TYPE_<>:type";
	        String endSql = " GROUP BY T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_"+
	                " ORDER BY T.ORGINDEX_";
	        Map<String,Object> paramMap = new HashMap<String,Object>();
	        paramMap.put("pid", pid);
	        paramMap.put("type", 1501);//150:科室

	        if(StringUtils.isNotBlank(curId)){
	            sql = startSql + " AND T.ORG_ID_ <> :curId " + endSql;
	            paramMap.put("curId", curId);
	        }else{
	            sql = startSql + endSql;
	        }
	        List<Map<String,Object>> childList = template.query(sql, paramMap, new ShopAndDepartMapper());
	        return childList;
		}

		@Override
		public List<Map<String, Object>> getDeparts(String workShopName) {
			String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.parent_id_=(select t.org_id_ from cfg_base_organization t where t.org_name_=:workShopName)";
			Map<String,Object> paramMap = new HashMap<String,Object>();
	        paramMap.put("workShopName", workShopName);
	        List<Map<String, Object>> departList=template.query(sql, paramMap,new CCOrganizationMapper());
			return departList;
		}
		class CCOrganizationMapper implements RowMapper<Map<String, Object>> {
			public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("value", rs.getString("ORG_ID_"));
				p.put("text", rs.getString("ORG_NAME_"));
				return p;
			}

		}
		 class ShopAndDepartMapper implements RowMapper<Map<String,Object>> {
		        public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
		            Map<String,Object> o = new HashMap<>();
		            o.put("id",rs.getString("ORG_ID_"));
		            o.put("name",rs.getString("ORG_NAME_"));
		            o.put("type",rs.getInt("TYPE_"));
		            o.put("desc",rs.getString("DESC_"));
		            int childcount = rs.getInt("CHILDCOUNT");
		            if(childcount == 0){
		                o.put("isdept",true);
		            }else{
		                o.put("isdept",false);
		            }
		            return o;
		        }

		    }
		@Override
		public List<JobRecordDto> findAllDocumentOfExport(String collectString, String workshopIdOfexport,
				String workareaIdOfexport, String startDateOfexport, String endDateOfexport,
				String projectDivOfexport) {
			List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilterOfexport(collectString, workshopIdOfexport,workareaIdOfexport,startDateOfexport,endDateOfexport,projectDivOfexport);
	            FindIterable<Document> findIterable = md.getCollection(collectString).find(filter).sort(new Document("createDate", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        List<JobRecordDto> JobRecordDto= new ArrayList<JobRecordDto>();
	        results.stream().forEach(d-> {
	        	JobRecordDto jobRecordDto= new JobRecordDto();
	            jobRecordDto.setDate( (String)d.get("date"));
	            jobRecordDto.setOrgName( (String)d.get("orgName"));
	            jobRecordDto.setProject( (String)d.get("project"));
	            jobRecordDto.setContent( (String)d.get("content"));
	            jobRecordDto.setJoiner( (String)d.get("joiner"));
	            jobRecordDto.setJoinerNumber( (String)d.get("joinerNumber"));
	            JobRecordDto.add(jobRecordDto);
	        });
	      
			return JobRecordDto;
		}
		private Bson getFilterOfexport(String collectString, String workshopIdOfexport, String workareaIdOfexport,
				String startDateOfexport, String endDateOfexport, String projectDivOfexport) {
			Bson filters=Filters.eq("status",1);
			filters = Filters.and(filters, Filters.ne("filesStatus", "1"));
			if (StringUtils.isNotBlank(workshopIdOfexport)) {
				filters = Filters.and(filters,Filters.eq("orgId",workshopIdOfexport));
			}
			if (StringUtils.isNotBlank(workareaIdOfexport)) {
				filters = Filters.and(filters,Filters.eq("orgId",workareaIdOfexport));
			}
			if (StringUtils.isNotBlank(startDateOfexport)) {
				filters = Filters.and(filters,Filters.gt("date",startDateOfexport));
			}
			if (StringUtils.isNotBlank(endDateOfexport)) {
				filters = Filters.and(filters,Filters.lt("date",endDateOfexport));
			}
			if (StringUtils.isNotBlank(projectDivOfexport)) {
				filters = Filters.and(filters,Filters.eq("project",projectDivOfexport));
			}
			return filters;
		}


}
