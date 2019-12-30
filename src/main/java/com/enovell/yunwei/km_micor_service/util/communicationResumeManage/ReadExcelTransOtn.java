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

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TransOtnDto;

public class ReadExcelTransOtn {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelTransOtn(){}
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
	public List<TransOtnDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<TransOtnDto> fdpList = null;
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
	public List<TransOtnDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<TransOtnDto> fdpList = null;
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
	private List<TransOtnDto> readExcelValue(Workbook wb) throws ParseException {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("行数======="+this.totalRows);
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("总列数=========="+this.totalCells);
		}
		List<TransOtnDto> fdpList = new ArrayList<TransOtnDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			TransOtnDto TransOtnDto = new TransOtnDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 if (c == 1){
						 TransOtnDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){  

						TransOtnDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){   
						
						TransOtnDto.setCombinationClass(cell.getStringCellValue());
						
					}
					else if (c == 4){   
						
						TransOtnDto.setDeviceClass(cell.getStringCellValue());
						
					}
					else if (c == 5){   
						
						TransOtnDto.setSystemName(cell.getStringCellValue());
						
					}
					else if (c == 6){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String deviceCode = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setDeviceCode(deviceCode);							
						}else{

							TransOtnDto.setDeviceCode(cell.getStringCellValue());							
						}
//						TransOtnDto.setDeviceCode(cell.getStringCellValue());
						
					}
					else if (c == 7){   
						
						TransOtnDto.setDeviceName(cell.getStringCellValue());
						
					}
					else if (c == 8){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String deviceId = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setDeviceId(deviceId);							
						}else{

							TransOtnDto.setDeviceId(cell.getStringCellValue());							
						}
//						TransOtnDto.setDeviceId(cell.getStringCellValue());
						
					}
					else if (c == 9){   
						
						TransOtnDto.setSite_station_line(cell.getStringCellValue());
						
					}
					else if (c == 10){   
						
						TransOtnDto.setSite_station_name(cell.getStringCellValue());
						
					}
					else if (c == 11){   
						
						TransOtnDto.setSite_station_place(cell.getStringCellValue());
						
					}
					else if (c == 12){   
						
						TransOtnDto.setSite_range_line(cell.getStringCellValue());
						
					}
					else if (c == 13){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String post = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setSite_range_post(post);							
						}else{

							TransOtnDto.setSite_range_post(cell.getStringCellValue());							
						}
//						TransOtnDto.setSite_range_post(cell.getStringCellValue());
						
					}
					else if (c == 14){   
						
						TransOtnDto.setSite_range_place(cell.getStringCellValue());
						
					}
					else if (c == 15){   
						
						TransOtnDto.setSite_other_line(cell.getStringCellValue());
						
					}
					else if (c == 16){   
						
						TransOtnDto.setSite_other_place(cell.getStringCellValue());
						
					}
					else if (c == 17){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String site_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setSite_machineRoomCode(site_machineRoomCode);							
						}else{

							TransOtnDto.setSite_machineRoomCode(cell.getStringCellValue());							
						}
//						TransOtnDto.setSite_machineRoomCode(cell.getStringCellValue());
						
					}
					else if (c == 18){   
						
						TransOtnDto.setAssetOwnership(cell.getStringCellValue());
						
					}
					else if (c == 19){   
						
						TransOtnDto.setOwnershipUnitName(cell.getStringCellValue());
						
					}
					else if (c == 20){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							TransOtnDto.setOwnershipUnitCode(ownershipUnitCode);							
						}else{

							TransOtnDto.setOwnershipUnitCode(cell.getStringCellValue());							
						}
//						TransOtnDto.setOwnershipUnitCode(cell.getStringCellValue());
						
					}
					else if (c == 21){   
						
						TransOtnDto.setMaintainBody(cell.getStringCellValue());
						
					}
					else if (c == 22){   
						
						TransOtnDto.setMaintainUnitName(cell.getStringCellValue());
						
					}
					else if (c == 23){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setMaintainUnitCode(maintainUnitCode);							
						}else{

							TransOtnDto.setMaintainUnitCode(cell.getStringCellValue());							
						}
//						TransOtnDto.setMaintainUnitCode(cell.getStringCellValue());
						
					}
					else if (c == 24){   
						
						TransOtnDto.setManufacturers(cell.getStringCellValue());
						
					}
					else if (c == 25){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String deviceType = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setDeviceType(deviceType);							
						}else{

							TransOtnDto.setDeviceType(cell.getStringCellValue());							
						}
//						TransOtnDto.setDeviceType(cell.getStringCellValue());
						
					}
					else if (c == 26){   
						
						TransOtnDto.setUseUnit(cell.getStringCellValue());
						
					}
					else if (c == 27){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String totalCapacity = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setTotalCapacity(totalCapacity);							
						}else{

							TransOtnDto.setTotalCapacity(cell.getStringCellValue());							
						}
//						TransOtnDto.setTotalCapacity(cell.getStringCellValue());
						
					}
					else if (c == 28){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String roadCapacity = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setRoadCapacity(roadCapacity);							
						}else{

							TransOtnDto.setRoadCapacity(cell.getStringCellValue());							
						}
//						TransOtnDto.setRoadCapacity(cell.getStringCellValue());
						
					}
					else if (c == 29){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String configChannel = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setConfigChannel(configChannel);							
						}else{

							TransOtnDto.setConfigChannel(cell.getStringCellValue());							
						}
//						TransOtnDto.setConfigChannel(cell.getStringCellValue());
						
					}
					else if (c == 30){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String assetRatio = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setAssetRatio(assetRatio);							
						}else{

							TransOtnDto.setAssetRatio(cell.getStringCellValue());							
						}
//						TransOtnDto.setAssetRatio(cell.getStringCellValue());
						
					}
					else if (c == 31){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							TransOtnDto.setProductionDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							TransOtnDto.setProductionDate(entryDate);
						}
						
					}
					else if (c == 32){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							TransOtnDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							TransOtnDto.setUseDate(entryDate);
						}
						
//						TransOtnDto.setUseDate(cell.getStringCellValue());
						
					}
					else if (c == 33){   
						
						TransOtnDto.setDeviceOperationStatus(cell.getStringCellValue());
						
					}
					else if (c == 34){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							TransOtnDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							TransOtnDto.setStopDate(entryDate);
						}
//						TransOtnDto.setStopDate(cell.getStringCellValue());
						
					}
					else if (c == 35){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							TransOtnDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							TransOtnDto.setScrapDate(entryDate);
						}
//						TransOtnDto.setScrapDate(cell.getStringCellValue());
						
					}
					else if (c == 36){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							TransOtnDto.setFixedAssetsCode(fixedAssetsCode);							
						}else{

							TransOtnDto.setFixedAssetsCode(cell.getStringCellValue());							
						}
//						TransOtnDto.setFixedAssetsCode(cell.getStringCellValue());
						
					}
					else if (c == 37){   
						
						TransOtnDto.setRemark(cell.getStringCellValue());
						
					}
	
				}
			}
			// 添加到list
			fdpList.add(TransOtnDto);
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
