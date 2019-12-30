package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;

import java.io.IOException;
import java.io.InputStream;
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

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineLightDto;

public class ReadExcelLineLight {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelLineLight(){}
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
	public List<LineLightDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<LineLightDto> fdpList = null;
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
	public List<LineLightDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<LineLightDto> fdpList = null;
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
	private List<LineLightDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<LineLightDto> fdpList = new ArrayList<LineLightDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			LineLightDto LineLightDto = new LineLightDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 if (c == 1){
						 LineLightDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){  
						LineLightDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){   
						LineLightDto.setCombinationClass(cell.getStringCellValue());
					}
					else if (c == 4){   
						LineLightDto.setDeviceClass(cell.getStringCellValue());
					}
					else if (c == 5){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setDeviceCode(deviceCode);							
						}else{
							LineLightDto.setDeviceCode(cell.getStringCellValue());							
						}
					}
					else if (c == 6){
						LineLightDto.setDeviceName(cell.getStringCellValue());
					}
					else if (c == 7){   
						
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String laidLength = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setLaidLength(laidLength);					
						}else{
							LineLightDto.setLaidLength(cell.getStringCellValue());							
						}						
					}
					else if (c == 8){   
						
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String laidMethod = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setLaidMethod(laidMethod);							
						}else{
							LineLightDto.setLaidMethod(cell.getStringCellValue());							
						}						
					}
					else if (c == 9){   
						
						LineLightDto.setSite_start_station_line(cell.getStringCellValue());
						
					}
					else if (c == 10){   
						
						LineLightDto.setSite_start_station_name(cell.getStringCellValue());
						
					}
					else if (c == 11){   
						
						LineLightDto.setSite_start_station_place(cell.getStringCellValue());
						
					}
					else if (c == 12){   
						
						LineLightDto.setSite_start_range_line(cell.getStringCellValue());
						
					}
				 
					else if (c == 13){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String post = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setSite_start_range_post(post);							
						}else{

							LineLightDto.setSite_start_range_post(cell.getStringCellValue());							
						}
					}
					else if (c == 14){   
						
						LineLightDto.setSite_start_range_place(cell.getStringCellValue());
						
					}
					else if (c == 15){   
						
						LineLightDto.setSite_start_other_line(cell.getStringCellValue());
						
					}
					else if (c == 16){   
						
						LineLightDto.setSite_start_other_place(cell.getStringCellValue());
						
					}
					else if (c == 17){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_start_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setSite_start_machineRoomCode(site_start_machineRoomCode);						
						}else{

							LineLightDto.setSite_start_machineRoomCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 18){   
						
						LineLightDto.setSite_end_station_line(cell.getStringCellValue());
						
					}
					else if (c == 19){   
						
						LineLightDto.setSite_end_station_name(cell.getStringCellValue());
						
					}
					else if (c == 20){   
						
						LineLightDto.setSite_end_station_place(cell.getStringCellValue());
						
					}
					else if (c == 21){   
						
						LineLightDto.setSite_end_range_line(cell.getStringCellValue());
						
					}
				 
					else if (c == 22){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							
							String post = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setSite_end_range_post(post);							
						}else{
							
							LineLightDto.setSite_end_range_post(cell.getStringCellValue());							
						}
					}
					else if (c == 23){   
						
						LineLightDto.setSite_end_range_place(cell.getStringCellValue());
						
					}
					else if (c == 24){   
						
						LineLightDto.setSite_end_other_line(cell.getStringCellValue());
						
					}
					else if (c == 25){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_end_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setSite_end_other_place(site_end_machineRoomCode);						
						}else{
							
							LineLightDto.setSite_end_other_place(cell.getStringCellValue());							
						}
						
					}
					else if (c == 26){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_end_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setSite_end_machineRoomCode(site_end_machineRoomCode);						
						}else{
							
							LineLightDto.setSite_end_machineRoomCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 27){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String assetOwnership = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setAssetOwnership(assetOwnership);							
						}else{

							LineLightDto.setAssetOwnership(cell.getStringCellValue());							
						}
						
					}
					else if (c == 28){   
						
						LineLightDto.setOwnershipUnitName(cell.getStringCellValue());
						
					}
					else if (c == 29){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String ownershipUnitCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setOwnershipUnitCode(ownershipUnitCode);							
						}else{

							LineLightDto.setOwnershipUnitCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 30){   
						
						LineLightDto.setMaintainBody(cell.getStringCellValue());
						
					}
					else if (c == 31){   
						
						LineLightDto.setMaintainUnitName(cell.getStringCellValue());
						
					}
					else if (c == 32){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setMaintainUnitCode(maintainUnitCode);							
						}else{

							LineLightDto.setMaintainUnitCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 33){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							
							String manufacturers = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setManufacturers(manufacturers);							
						}else{
							
							LineLightDto.setManufacturers(cell.getStringCellValue());							
						}
						
					}
					else if (c == 34){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String deviceType = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setDeviceType(deviceType);							
						}else{

							LineLightDto.setDeviceType(cell.getStringCellValue());							
						}
						
					}
					else if (c == 35){   
						
						LineLightDto.setUseUnit(cell.getStringCellValue());
						
					}
					else if (c == 36){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String totalCapacity = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setTotalCapacity(totalCapacity);							
						}else{

							LineLightDto.setTotalCapacity(cell.getStringCellValue());							
						}
						
					}
					else if (c == 37){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							
							String coreNumber = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setCoreNumber(coreNumber);						
						}else{
							
							LineLightDto.setCoreNumber(cell.getStringCellValue());							
						}
						
					}
					else if (c == 38){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							
							String useCoreNumber = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setUseCoreNumber(useCoreNumber);						
						}else{
							
							LineLightDto.setUseCoreNumber(cell.getStringCellValue());							
						}
						
					}
					else if (c == 39){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							
							String assetRatio = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setAssetRatio(assetRatio);						
						}else{
							
							LineLightDto.setAssetRatio(cell.getStringCellValue());							
						}
						
					}
					else if (c == 40){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineLightDto.setProductionDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineLightDto.setProductionDate(entryDate);
						}
						
					}
					else if (c == 41){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineLightDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineLightDto.setUseDate(entryDate);
						}
						
					}
					else if (c == 42){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineLightDto.setMiddleRepairDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineLightDto.setMiddleRepairDate(entryDate);
						}
						
					}
					else if (c == 43){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineLightDto.setLargeRepairDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineLightDto.setLargeRepairDate(entryDate);
						}
						
					}
					else if (c == 44){   
						LineLightDto.setDeviceOperationStatus(cell.getStringCellValue());
					}
					else if (c == 45){   
						
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineLightDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineLightDto.setStopDate(entryDate);
						}
						
					}
					else if (c == 46){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineLightDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineLightDto.setScrapDate(entryDate);
						}
						
					}
					else if (c == 47){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							LineLightDto.setFixedAssetsCode(fixedAssetsCode);							
						}else{

							LineLightDto.setFixedAssetsCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 48){   
						LineLightDto.setRemark(cell.getStringCellValue());
					}
				}
			}
			// 添加到list
			fdpList.add(LineLightDto);
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