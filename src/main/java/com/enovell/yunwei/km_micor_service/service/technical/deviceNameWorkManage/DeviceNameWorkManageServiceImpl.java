package com.enovell.yunwei.km_micor_service.service.technical.deviceNameWorkManage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.MaintenanceMemorendunDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.DeviceNameWorkDto;
import com.enovell.yunwei.km_micor_service.util.UUIDUtils;

@Service("deviceNameWorkManageService")
public class DeviceNameWorkManageServiceImpl implements DeviceNameWorkManageService{
  
  @Autowired
  private MongoTemplate mt;
  //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    @Value("${user.uploadPath}")
    private String uploadPath;
  @Override
  public Document addDocument(Document document, String collectionName) {
    document.put("status", 1);
    mt.save(document,collectionName);
    return document;
  }

  @Override
  public Long findAllDocumentCount(String collectionName, String deviceName, String workContent) {
    Criteria criteria = getCriteriaQuery(deviceName,workContent);
    Long cout = mt.count(new Query(criteria),collectionName);
    return cout;
  }

  @Override
  public List<Document> findAllDocument(String collectionName, String deviceName, String workContent, int start,
      int limit) {
	  
    Criteria criteria = getCriteriaQuery(deviceName,workContent);
    List<Document> resultDocuments = mt.find(new Query(criteria).skip(start).limit(limit), Document.class,collectionName);
    resultDocuments.stream().forEach(d -> {
            d.append("docId", d.getObjectId("_id").toHexString());
            d.remove("_id");
        });
    return resultDocuments;
  }

  private Criteria getCriteriaQuery(String deviceName, String workContent) {
    Criteria criteria = Criteria.where("status").is(1);
    if (StringUtils.isNotBlank(deviceName)) {
      criteria.and("deviceName").regex(deviceName);
    }
    if (StringUtils.isNotBlank(workContent)) {
      criteria.and("workContent").regex(workContent);
    }
    return criteria;
  }

  @Override
  public Document findDocumentById(String id, String collectionName) {
    Document doc = mt.findOne(new Query(Criteria.where("_id").is(id).and("status").is(1)), Document.class, collectionName);
    doc.append("docId", doc.getObjectId("_id").toHexString());
        doc.remove("_id");
    return doc;
  }

  @Override
  public Document modifyDocument(Document document, String collectionName,String id) {
    Query query = new Query(Criteria.where("_id").is(id).and("status").is(1));
    Update update = new Update();
        update.set("deviceName", document.get("deviceName"));
        update.set("workContent", document.get("workContent"));
        update.set("yearOrMonth", document.get("yearOrMonth"));
        update.set("type", document.get("type"));
        update.set("countYear", document.get("countYear"));
        update.set("unit", document.get("unit"));
        update.set("remark", document.get("remark"));
        update.set("status", document.get("status"));
    mt.updateFirst(query, update, collectionName);
    return document;
  }

  @Override
  public void removeDocument(List<String> ids, String collectionName) {
    Query query = Query.query(Criteria.where("_id").in(ids));
    mt.remove(query,collectionName);
  }
  
  
  @Override
  public List < DeviceNameWorkDto > getExcelInfo(MultipartFile mfile) {
      String fileName = mfile.getOriginalFilename(); //获取文件名
      List < DeviceNameWorkDto > crdList = null;
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

  private List < DeviceNameWorkDto > createExcel(InputStream inputStream, boolean isExcel2003) {
      List < DeviceNameWorkDto > crdList = null;
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
   * getMergeRowNum 获取当前excel的cell合并的行数
   * @param cell
   * @param sheet
   * @return
   */
  public static int getMergeRowNum(Cell cell, Sheet sheet) {
      int mergeSize = 1;
      List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
      for (CellRangeAddress cellRangeAddress : mergedRegions) {
          if (cellRangeAddress.isInRange(cell)) {
              //获取合并的行数
              mergeSize = cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow() + 1;
              break;
          }
      }
      return mergeSize;
  }
  /**
   * 
   * isNumericzidai 判断是否位数字
   * @param str
   * @return
   */
  public static boolean isNumericzidai(String str) {
    Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
    return false;
    }
    return true;
  }
    
  private List < DeviceNameWorkDto > readExcelValue(Workbook wb) {
      // 得到第一个shell
      Sheet sheet = wb.getSheetAt(0);
      // 得到Excel的行数
      this.totalRows = sheet.getPhysicalNumberOfRows();
      // 得到Excel的列数(前提是有行数)
      if (totalRows > 1 && sheet.getRow(0) != null) {
          this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
      }
      List < DeviceNameWorkDto > crdList = new ArrayList < DeviceNameWorkDto > ();
      // 循环Excel行数
      String firstCluString;
      int firstCluStringNum;//类别的行数
      for (int r = 2; r < totalRows;) {
          Row row = sheet.getRow(r);
          if (row == null) {
            r=r+1;
            continue;
          }
          Cell cell = row.getCell(0);//类别
          int rowNum = cell.getRowIndex();//行号
          // 循环Excel的列 
          if (null != cell) {
             //获取第一格的值做判断
              if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                  firstCluString = String.valueOf(cell.getNumericCellValue());
                  r=r+1;
                  continue;
              }
              firstCluString = cell.getStringCellValue();
              if(StringUtils.isBlank(firstCluString)||firstCluString.equals("类别")||isNumericzidai(firstCluString)) {//是数字或者是类别==》跳出
                  r=r+1;
                  continue;
              }
              firstCluStringNum = getMergeRowNum(cell,sheet);
              for (int i = 0; i < firstCluStringNum;) {
                  DeviceNameWorkDto deviceNameWorkDto = new DeviceNameWorkDto();
                  deviceNameWorkDto.setDeviceName(firstCluString);
                  Row row2 = sheet.getRow(rowNum);
                  Cell cell3 = row2.getCell(3);//周期
                  if(cell3.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                      deviceNameWorkDto.setCountYear(String.valueOf(cell3.getNumericCellValue()));
                  }
                  deviceNameWorkDto.setCountYear(String.valueOf(cell3.getStringCellValue()));
                  Cell cell4 = row2.getCell(4);//备注
                  if(cell4.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                      deviceNameWorkDto.setRemark(String.valueOf(cell4.getNumericCellValue()));
                  }
                  deviceNameWorkDto.setRemark(String.valueOf(cell4.getStringCellValue()));
                  //以周期行数循环获取工作内容
                  int fourCluStringNum = getMergeRowNum(cell3,sheet);
                  StringBuffer workContent = new StringBuffer();
                  for (int j = 0; j < fourCluStringNum; j++) {
                    //获取工作内容+；叠加
                    Row row3 = sheet.getRow(rowNum);
                    Cell cell2 = row3.getCell(2);//工作内容
                    if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                      workContent.append(String.valueOf(cell2.getNumericCellValue()));
                   }
                  int num = j+1;
                  workContent.append(num+"、"+cell2.getStringCellValue());
                  rowNum = rowNum+1;
              }
              deviceNameWorkDto.setWorkContent(workContent.toString());
              crdList.add(deviceNameWorkDto);
              i=i+fourCluStringNum;
          }
          r = rowNum;
         }
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

  @Override
  public void removeAll() {
    mt.dropCollection("deviceNameWorkManage");
  }
}
