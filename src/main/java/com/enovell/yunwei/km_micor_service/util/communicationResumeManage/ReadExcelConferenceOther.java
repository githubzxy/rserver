package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.ConferenceMcuDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.ConferenceOtherDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WiredWiredDto;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：ReadExcelConferenceOther   
 * 类描述: 读取excel---会议-其他设备 
 * 创建人：zhouxingyu
 * 创建时间：2019年6月19日 下午4:32:11
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月19日 下午4:32:11   
 *
 */
public class ReadExcelConferenceOther {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelConferenceOther(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;} 
    //获取总列数
    public int getTotalCells() {  return totalCells;} 
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }    

  /**
   * 读EXCEL文件，获取信息集合
   * @param fielName
   * @return
   */
	public List<ConferenceOtherDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<ConferenceOtherDto> fdpList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
		  fdpList = createExcel(mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fdpList;
	}


  /**
   * 根据excel里面的内容信息
   * @param is 输入流
   * @param isExcel2003 excel是2003还是2007版本
   * @return
   * @throws ParseException 
   * @throws IOException
   */
	public List<ConferenceOtherDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<ConferenceOtherDto> fdpList = null;
		try{
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			 fdpList = readExcelValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fdpList;
	}  
  /**
   * 读取Excel里面的信息
   * @param wb
   * @return
 * @throws ParseException 
   */
	private List<ConferenceOtherDto> readExcelValue(Workbook wb) throws ParseException {
		// 得到第一个sheWiredWiredDtoll
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("行数======="+this.totalRows);
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("总列数=========="+this.totalCells);
		}
		List<ConferenceOtherDto> fdpList = new ArrayList<ConferenceOtherDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			ConferenceOtherDto conferenceOtherDto = new ConferenceOtherDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 1){
						conferenceOtherDto.setWorkshop(cell.getStringCellValue());
					 continue;
					}
					if (c == 2){  
						conferenceOtherDto.setWorkArea(cell.getStringCellValue());
						continue;
					}
					if (c == 3){   
						conferenceOtherDto.setCombinationClass(cell.getStringCellValue());
						continue;
					}
				    if (c == 4){   
				    	conferenceOtherDto.setDeviceClass(cell.getStringCellValue());
						continue;
					}
					if (c == 5){   
						conferenceOtherDto.setDeviceCode(cell.getStringCellValue());
						continue;
					}
					if (c == 6){   
						conferenceOtherDto.setDeviceName(cell.getStringCellValue());
						continue;
					}
					if (c == 7){   
						conferenceOtherDto.setSite_station_line(cell.getStringCellValue());
						continue;
					}
					if (c == 8){   
						conferenceOtherDto.setSite_station_name(cell.getStringCellValue());
						continue;
					}
					if (c == 9){   
						conferenceOtherDto.setSite_station_place(cell.getStringCellValue());
						continue;
					}
					if (c == 10){   
						conferenceOtherDto.setSite_range_line(cell.getStringCellValue());
						continue;
					}
					if (c == 11){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String post = String.valueOf(cell.getNumericCellValue());
							conferenceOtherDto.setSite_range_post(post);							
						}else{

							conferenceOtherDto.setSite_range_post(cell.getStringCellValue());							
						}
						continue;
					}
					if (c == 12){   
						conferenceOtherDto.setSite_range_place(cell.getStringCellValue());
						continue;
					}
					if (c == 13){   
						conferenceOtherDto.setSite_other_line(cell.getStringCellValue());
						continue;
					}
					if (c == 14){   
						conferenceOtherDto.setSite_other_place(cell.getStringCellValue());
						continue;
					}
					if (c == 15){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							conferenceOtherDto.setSite_machineRoomCode(site_machineRoomCode);							
						}else{
							conferenceOtherDto.setSite_machineRoomCode(cell.getStringCellValue());							
						}
						continue;						
					}
					if (c == 16){   
						conferenceOtherDto.setAssetOwnership(cell.getStringCellValue());
						continue;			
					}
					if (c == 17){   
						conferenceOtherDto.setOwnershipUnitName(cell.getStringCellValue());
						continue;			
					}
					if (c == 18){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							conferenceOtherDto.setOwnershipUnitCode(ownershipUnitCode);							
						}else{
							conferenceOtherDto.setOwnershipUnitCode(cell.getStringCellValue());							
						}
						continue;									
					}
					if (c == 19){   
						conferenceOtherDto.setMaintainBody(cell.getStringCellValue());
						continue;			
					}
					if (c == 20){   
						conferenceOtherDto.setMaintainUnitName(cell.getStringCellValue());
						continue;		
					}
					if (c == 21){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							conferenceOtherDto.setMaintainUnitCode(maintainUnitCode);							
						}else{
							conferenceOtherDto.setMaintainUnitCode(cell.getStringCellValue());							
						}
						continue;								
					}

					/*
					 * if (c == 23){ conferenceMcuDto.setRoadCapacity(cell.getStringCellValue());
					 * continue; } if (c == 24){
					 * conferenceMcuDto.setAssetRatio(cell.getStringCellValue()); continue; } if (c
					 * == 25){ conferenceMcuDto.setCapacityUnit(cell.getStringCellValue());
					 * continue; }
					 */
					if (c == 22){   
						conferenceOtherDto.setManufacturers(cell.getStringCellValue());
						continue;		
					}
					if (c == 23){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceType = String.valueOf(cell.getNumericCellValue());
							conferenceOtherDto.setDeviceType(deviceType);							
						}else{
							conferenceOtherDto.setDeviceType(cell.getStringCellValue());							
						}
						continue;								
					}
					if (c == 24){   
						conferenceOtherDto.setUseUnit(cell.getStringCellValue());
						continue;			
					}
				    if (c == 25){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
				    		conferenceOtherDto.setProductionDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							conferenceOtherDto.setProductionDate(entryDate);
						}
						continue;
					}
					if (c == 26){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
				    		conferenceOtherDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							conferenceOtherDto.setUseDate(entryDate);
						}
						continue;
					}
					if (c == 27){   
						conferenceOtherDto.setDeviceOperationStatus(cell.getStringCellValue());
						continue;
					}
					if (c == 28){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
				    		conferenceOtherDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							conferenceOtherDto.setStopDate(entryDate);
						}
						continue;						
					}
					if (c == 29){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
				    		conferenceOtherDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							conferenceOtherDto.setScrapDate(entryDate);
						}
						continue;						
						}
					 
					if (c == 30){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							conferenceOtherDto.setFixedAssetsCode(fixedAssetsCode);							
						}else{

							conferenceOtherDto.setFixedAssetsCode(cell.getStringCellValue());							
						}
						continue;						
					}
					if (c == 31){ 
						
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceType = String.valueOf(cell.getNumericCellValue());
							conferenceOtherDto.setRemark(String.valueOf(cell.getNumericCellValue()));		
						}else{
							conferenceOtherDto.setRemark(cell.getStringCellValue());
						}
						continue;
					}
	
				}
			}
			// 添加到list
			fdpList.add(conferenceOtherDto);
		}
		return fdpList;
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
    public static boolean isExcel2003(String filePath)  {  
         return filePath.matches("^.+\\.(?i)(xls)$");  
     }  
    //@描述：是否是2007的excel，返回true是2007 
    public static boolean isExcel2007(String filePath)  {  
         return filePath.matches("^.+\\.(?i)(xlsx)$");  
     }  
}
