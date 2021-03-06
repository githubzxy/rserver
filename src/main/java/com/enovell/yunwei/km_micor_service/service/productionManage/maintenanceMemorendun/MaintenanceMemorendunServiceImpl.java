package com.enovell.yunwei.km_micor_service.service.productionManage.maintenanceMemorendun;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.MaintenanceMemorendunDto;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：MaintenanceMemorendunServiceImpl   
 * 类描述:  维修备忘录模块实现
 * 创建人：zhouxingyu
 * 创建时间：2019年3月26日 下午3:52:02
 * 修改人：zhouxingyu 
 * 修改时间：2019年3月26日 下午3:52:02   
 *
 */
@Service(value = "maintenanceMemorendunService")
public class MaintenanceMemorendunServiceImpl implements MaintenanceMemorendunService {

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
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;

    @Override
    public Document addDocument(Document doc, String collectionName) {
        try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            doc.append("status", 1);
            doc.append("busiId", UUIDUtils.getUUID());
            md.getCollection(collectionName).insertOne(doc);
        }
        return doc;
    }
    @Override
    public Long findAllDocumentCount(String collectionName, String name, String startUploadDate, String endUploadDate, String orgId, String auditStatus,String dutyDepartment) {
        try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(name, startUploadDate, endUploadDate, orgId, auditStatus,dutyDepartment);
            return md.getCollection(collectionName).count(filter);
        }
    }

    private Bson getFilter(String name, String startUploadDate, String endUploadDate, String orgId, String auditStatus,String dutyDepartment) {
        Bson filter = Filters.eq("status", 1);
        if (StringUtils.isNotBlank(startUploadDate)) {
            filter = Filters.and(filter, Filters.gte("checkDate", startUploadDate));
        }
        if (StringUtils.isNotBlank(endUploadDate)) {
            filter = Filters.and(filter, Filters.lte("checkDate", endUploadDate));
        }
        if (StringUtils.isNotBlank(dutyDepartment)) {
        	filter = Filters.and(filter, Filters.eq("dutyDepartment", dutyDepartment));
        }

        return filter;
    }
    @Override
    public List < Document > findAllDocument(String collectionName, String name, String startUploadDate, String endUploadDate, String orgId, int start, int limit, String auditStatus,String dutyDepartment) {
        List < Document > results = new ArrayList < > ();
        try (MongoClient mc = new MongoClient(mongoHost, mongoPort)) {
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = getFilter(name, startUploadDate, endUploadDate, orgId, auditStatus,dutyDepartment);
            FindIterable < Document > findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
            findIterable.forEach((Block < ? super Document > ) results::add);
        }
        results.stream().forEach(d -> {
            d.append("docId", d.getObjectId("_id").toHexString());
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
    public List < MaintenanceMemorendunDto > getExcelInfo(MultipartFile mfile) {
        String fileName = mfile.getOriginalFilename(); //获取文件名
        List < MaintenanceMemorendunDto > crdList = null;
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

    private List < MaintenanceMemorendunDto > createExcel(InputStream inputStream, boolean isExcel2003) {
        List < MaintenanceMemorendunDto > crdList = null;
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
    private List < MaintenanceMemorendunDto > readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List < MaintenanceMemorendunDto > crdList = new ArrayList < MaintenanceMemorendunDto > ();
        // 循环Excel行数
        for (int r = 3; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            MaintenanceMemorendunDto maintenanceMemorendunDto = new MaintenanceMemorendunDto();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 1)
                        try {
                            convertCheckDate(cell, maintenanceMemorendunDto);
                            continue;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            break;
                        } //处理时间
                    if (c == 2) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setChecker(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setChecker(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 3) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setProblemFrom(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setProblemFrom(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 4) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setProblemInfo(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setProblemInfo(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 5) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setDutyDepartment(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setDutyDepartment(cell.getStringCellValue());
                        continue;
                    }
//                    if (c == 6) {
//                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//							maintenanceMemorendunDto.setHelpDepartment(String.valueOf(cell.getNumericCellValue()));
//							continue;
//                    	}
//                        maintenanceMemorendunDto.setHelpDepartment(cell.getStringCellValue());
//                        continue;
//                    }
                    if (c == 6) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setEndDate(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setEndDate(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 7) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setChangeInfo(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setChangeInfo(cell.getStringCellValue());
                        continue;

                    }
                    if (c == 8) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setResult(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setResult(cell.getStringCellValue());
                        continue;
                    }
                    if (c == 9) {
                    	if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							maintenanceMemorendunDto.setNote(String.valueOf(cell.getNumericCellValue()));
							continue;
                    	}
                        maintenanceMemorendunDto.setNote(cell.getStringCellValue());
                        continue;
                    }
                }

            }
            // 添加到list
            crdList.add(maintenanceMemorendunDto);
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
    private void convertCheckDate(Cell cell, MaintenanceMemorendunDto maintenanceMemorendunDto) throws ParseException {
        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            //用于转化为日期格式
            Date d = cell.getDateCellValue();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formater.format(d);
            maintenanceMemorendunDto.setCheckDate(dateString);
        } else {
            // 用于格式化数字，只保留数字的整数部分
            DecimalFormat df = new DecimalFormat("0");
            String dateString = df.format(cell.getNumericCellValue());
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formater.format(dateString);
            maintenanceMemorendunDto.setCheckDate(dateStr);
        }
    }
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
	public boolean checkData(GridDto<Document> result, String checkDate, String endDate) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  try {
	            if (StringUtils.isNotBlank(checkDate)) {
	            	 sdf.parse(checkDate);
	            }
	            if (StringUtils.isNotBlank(endDate)) {
	            	 sdf.parse(endDate);
	            }
	        } catch (ParseException e) {
	            result.setHasError(true);
	            result.setError("时间格式错误");
	            return false;
	        }
		return true;
	}



}