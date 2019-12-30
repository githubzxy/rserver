package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessCableDto;
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

public class ReadExcelWirelessCable {
	//总行数
	private int totalRows = 0;
	//总条数
	private int totalCells = 0;
	//错误信息接收器
	private String errorMsg;
	//构造方法
	public ReadExcelWirelessCable(){}
	//获取总行数
	public int getTotalRows()  { return totalRows;}
	//获取总列数
	public int getTotalCells() {  return totalCells;}
	//获取错误信息
	public String getErrorInfo() { return errorMsg; }

	/**
	 * 读EXCEL文件，获取信息集合
	 * @param
	 * @return
	 */
	public List<WirelessCableDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<WirelessCableDto> fdpList = null;
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
	public List<WirelessCableDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<WirelessCableDto> fdpList = null;
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
	private List<WirelessCableDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<WirelessCableDto> fdpList = new ArrayList<WirelessCableDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			WirelessCableDto wirelessCableDto = new WirelessCableDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 1){
						wirelessCableDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){
						wirelessCableDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){
						wirelessCableDto.setCombinationClass(cell.getStringCellValue());
					}
					else if (c == 4){
						wirelessCableDto.setDeviceClass(cell.getStringCellValue());
					}
					else if (c == 5){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceCode = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setDeviceCode(deviceCode);
						}else{
							wirelessCableDto.setDeviceCode(cell.getStringCellValue());
						}
					}
					else if (c == 6){
						wirelessCableDto.setDeviceName(cell.getStringCellValue());
					}
					else if (c == 7){
						wirelessCableDto.setSite_start_station_line(cell.getStringCellValue());
					}
					else if (c == 8){
						wirelessCableDto.setSite_start_station_name(cell.getStringCellValue());
					}
					else if (c == 9){
						wirelessCableDto.setSite_start_station_place(cell.getStringCellValue());
					}
					else if (c == 10){
						wirelessCableDto.setSite_start_range_line(cell.getStringCellValue());
					}
					else if (c == 11){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String post = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setSite_start_range_post(post);
						}else{
							wirelessCableDto.setSite_start_range_post(cell.getStringCellValue());
						}
					}
					else if (c == 12){
						wirelessCableDto.setSite_start_range_place(cell.getStringCellValue());
					}
					else if (c == 13){
						wirelessCableDto.setSite_start_other_line(cell.getStringCellValue());
					}
					else if (c == 14){
						wirelessCableDto.setSite_start_other_place(cell.getStringCellValue());
					}
					else if (c == 15){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setSite_start_machineRoomCode(site_machineRoomCode);
						}else{
							wirelessCableDto.setSite_start_machineRoomCode(cell.getStringCellValue());
						}
					}

					else if (c == 16){
						wirelessCableDto.setSite_end_station_line(cell.getStringCellValue());
					}
					else if (c == 17){
						wirelessCableDto.setSite_end_station_name(cell.getStringCellValue());
					}
					else if (c == 18){
						wirelessCableDto.setSite_end_station_place(cell.getStringCellValue());
					}
					else if (c == 19){
						wirelessCableDto.setSite_end_range_line(cell.getStringCellValue());
					}
					else if (c == 20){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String post = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setSite_end_range_post(post);
						}else{
							wirelessCableDto.setSite_end_range_post(cell.getStringCellValue());
						}
					}
					else if (c == 21){
						wirelessCableDto.setSite_end_range_place(cell.getStringCellValue());
					}
					else if (c == 22){
						wirelessCableDto.setSite_end_other_line(cell.getStringCellValue());
					}
					else if (c == 23){
						wirelessCableDto.setSite_end_other_place(cell.getStringCellValue());
					}
					else if (c == 24){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_machineRoomCode = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setSite_end_machineRoomCode(site_machineRoomCode);
						}else{
							wirelessCableDto.setSite_end_machineRoomCode(cell.getStringCellValue());
						}
					}

					else if (c == 25){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String cableLength = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setCableLength(cableLength);
						}else{
							wirelessCableDto.setCableLength(cell.getStringCellValue());
						}
					}

					else if (c == 26){
						wirelessCableDto.setAssetOwnership(cell.getStringCellValue());
					}
					else if (c == 27){
						wirelessCableDto.setOwnershipUnitName(cell.getStringCellValue());
					}
					else if (c == 28){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							wirelessCableDto.setOwnershipUnitCode(ownershipUnitCode);
						}else{

							wirelessCableDto.setOwnershipUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 29){
						wirelessCableDto.setMaintainBody(cell.getStringCellValue());
					}
					else if (c == 30){
						wirelessCableDto.setMaintainUnitName(cell.getStringCellValue());
					}
					else if (c == 31){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setMaintainUnitCode(maintainUnitCode);
						}else{
							wirelessCableDto.setMaintainUnitCode(cell.getStringCellValue());
						}
					}

					else if (c == 32){
						wirelessCableDto.setManufacturers(cell.getStringCellValue());
					}
					else if (c == 33){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceType = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setDeviceType(deviceType);
						}else{
							wirelessCableDto.setDeviceType(cell.getStringCellValue());
						}
					}
					else if (c == 34){
						wirelessCableDto.setUseUnit(cell.getStringCellValue());
					}
					else if (c == 35) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessCableDto.setProductionDate(entryDateStr);
						} else {
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessCableDto.setProductionDate(entryDate);
						}
					}
					else if (c == 36){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessCableDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessCableDto.setUseDate(entryDate);
						}
					}

					else if (c == 37){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessCableDto.setMiddleRepairDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessCableDto.setMiddleRepairDate(entryDate);
						}
					}
					else if (c == 38){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessCableDto.setLargeRepairDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessCableDto.setLargeRepairDate(entryDate);
						}
					}


					else if (c == 39){
						wirelessCableDto.setDeviceOperationStatus(cell.getStringCellValue());
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
							wirelessCableDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessCableDto.setStopDate(entryDate);
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
							wirelessCableDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessCableDto.setScrapDate(entryDate);
						}
					}
					else if (c == 42){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							wirelessCableDto.setFixedAssetsCode(fixedAssetsCode);
						}else{
							wirelessCableDto.setFixedAssetsCode(cell.getStringCellValue());
						}
					}
					else if (c == 43){
						wirelessCableDto.setRemark(cell.getStringCellValue());
					}
				}
			}
			// 添加到list
			fdpList.add(wirelessCableDto);
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
