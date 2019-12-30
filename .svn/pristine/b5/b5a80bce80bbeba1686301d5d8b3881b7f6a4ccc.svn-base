package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessWirelessDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadExcelWirelessWireless {
	//总行数
	private int totalRows = 0;
	//总条数
	private int totalCells = 0;
	//错误信息接收器
	private String errorMsg;
	//构造方法
	public ReadExcelWirelessWireless(){}
	//获取总行数
	public int getTotalRows()  { return totalRows;}
	//获取总列数
	public int getTotalCells() {  return totalCells;}
	//获取错误信息
	public String getErrorInfo() { return errorMsg; }

	/**
	 * 读EXCEL文件，获取信息集合
	 * @return
	 */
	public List<WirelessWirelessDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<WirelessWirelessDto> fdpList = null;
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
	public List<WirelessWirelessDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<WirelessWirelessDto> fdpList = null;
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
	private List<WirelessWirelessDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<WirelessWirelessDto> fdpList = new ArrayList<WirelessWirelessDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			WirelessWirelessDto wirelessWirelessDto = new WirelessWirelessDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 1){
						wirelessWirelessDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){
						wirelessWirelessDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){
						wirelessWirelessDto.setCombinationClass(cell.getStringCellValue());
					}
					else if (c == 4){
						wirelessWirelessDto.setDeviceClass(cell.getStringCellValue());
					}
					else if (c == 5){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceCode = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setDeviceCode(deviceCode);
						}else{
							wirelessWirelessDto.setDeviceCode(cell.getStringCellValue());
						}
					}
					else if (c == 6){
						wirelessWirelessDto.setDeviceName(cell.getStringCellValue());
					}
					else if (c == 7){
						wirelessWirelessDto.setSite_station_line(cell.getStringCellValue());
					}
					else if (c == 8){
						wirelessWirelessDto.setSite_station_name(cell.getStringCellValue());
					}
					else if (c == 9){
						wirelessWirelessDto.setSite_station_place(cell.getStringCellValue());
					}
					else if (c == 10){
						wirelessWirelessDto.setSite_range_line(cell.getStringCellValue());
					}
					else if (c == 11){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String post = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setSite_range_post(post);
						}else{
							wirelessWirelessDto.setSite_range_post(cell.getStringCellValue());
						}
					}
					else if (c == 12){
						wirelessWirelessDto.setSite_range_place(cell.getStringCellValue());
					}
					else if (c == 13){
						wirelessWirelessDto.setSite_other_line(cell.getStringCellValue());
					}
					else if (c == 14){
						wirelessWirelessDto.setSite_other_place(cell.getStringCellValue());
					}
					else if (c == 15){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setSite_machineRoomCode(site_machineRoomCode);
						}else{
							wirelessWirelessDto.setSite_machineRoomCode(cell.getStringCellValue());
						}
					}
					else if (c == 16){
						wirelessWirelessDto.setAssetOwnership(cell.getStringCellValue());
					}
					else if (c == 17){
						wirelessWirelessDto.setOwnershipUnitName(cell.getStringCellValue());
					}
					else if (c == 18){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							wirelessWirelessDto.setOwnershipUnitCode(ownershipUnitCode);
						}else{

							wirelessWirelessDto.setOwnershipUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 19){
						wirelessWirelessDto.setMaintainBody(cell.getStringCellValue());
					}
					else if (c == 20){
						wirelessWirelessDto.setMaintainUnitName(cell.getStringCellValue());
					}
					else if (c == 21){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setMaintainUnitCode(maintainUnitCode);
						}else{
							wirelessWirelessDto.setMaintainUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 22){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String capacity = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setCapacity(capacity);
						}else{
							wirelessWirelessDto.setCapacity(cell.getStringCellValue());
						}
					}
					else if (c == 23){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String roadCapacity = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setRoadCapacity(roadCapacity);
						}else{
							wirelessWirelessDto.setRoadCapacity(cell.getStringCellValue());
						}
					}
					else if (c == 24){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String assetRatio = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setAssetRatio(assetRatio);
						}else{

							wirelessWirelessDto.setAssetRatio(cell.getStringCellValue());
						}
					}
					else if (c == 25){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String useCapacity = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setUseCapacity(useCapacity);
						}else{
							wirelessWirelessDto.setUseCapacity(cell.getStringCellValue());
						}
					}
					else if (c == 26){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String capacityUnit = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setCapacityUnit(capacityUnit);
						}else{
							wirelessWirelessDto.setCapacityUnit(cell.getStringCellValue());
						}
					}
					else if (c == 27){
						wirelessWirelessDto.setManufacturers(cell.getStringCellValue());
					}
					else if (c == 28){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceType = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setDeviceType(deviceType);
						}else{
							wirelessWirelessDto.setDeviceType(cell.getStringCellValue());
						}
					}
					else if (c == 29){
						wirelessWirelessDto.setUseUnit(cell.getStringCellValue());
					}
					else if (c == 30) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessWirelessDto.setProductionDate(entryDateStr);
						} else {
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessWirelessDto.setProductionDate(entryDate);
						}
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
							wirelessWirelessDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessWirelessDto.setUseDate(entryDate);
						}
					}
					else if (c == 32){
						wirelessWirelessDto.setDeviceOperationStatus(cell.getStringCellValue());
					}
					else if (c == 33){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessWirelessDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessWirelessDto.setStopDate(entryDate);
						}
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
							wirelessWirelessDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessWirelessDto.setScrapDate(entryDate);
						}
					}
					else if (c == 35){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							wirelessWirelessDto.setFixedAssetsCode(fixedAssetsCode);
						}else{
							wirelessWirelessDto.setFixedAssetsCode(cell.getStringCellValue());
						}
					}
					else if (c == 36){
						wirelessWirelessDto.setRemark(cell.getStringCellValue());
					}
				}
			}
			// 添加到list
			fdpList.add(wirelessWirelessDto);
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
