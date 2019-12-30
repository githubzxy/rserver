package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.VideoIrontowerDto;
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

public class ReadExcelVideoIrontower {
	//总行数
	private int totalRows = 0;
	//总条数
	private int totalCells = 0;
	//错误信息接收器
	private String errorMsg;
	//构造方法
	public ReadExcelVideoIrontower(){}
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
	public List<VideoIrontowerDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<VideoIrontowerDto> fdpList = null;
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
	public List<VideoIrontowerDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<VideoIrontowerDto> fdpList = null;
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
	private List<VideoIrontowerDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<VideoIrontowerDto> fdpList = new ArrayList<VideoIrontowerDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			VideoIrontowerDto videoIrontowerDto = new VideoIrontowerDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 1){
						videoIrontowerDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){
						videoIrontowerDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){
						videoIrontowerDto.setCombinationClass(cell.getStringCellValue());
					}
					else if (c == 4){
						videoIrontowerDto.setDeviceClass(cell.getStringCellValue());
					}
					else if (c == 5){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceCode = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setDeviceCode(deviceCode);
						}else{
							videoIrontowerDto.setDeviceCode(cell.getStringCellValue());
						}
					}
					else if (c == 6){
						videoIrontowerDto.setDeviceName(cell.getStringCellValue());
					}
					else if (c == 7){
						videoIrontowerDto.setSite_station_line(cell.getStringCellValue());
					}
					else if (c == 8){
						videoIrontowerDto.setSite_station_name(cell.getStringCellValue());
					}
					else if (c == 9){
						videoIrontowerDto.setSite_station_place(cell.getStringCellValue());
					}
					else if (c == 10){
						videoIrontowerDto.setSite_range_line(cell.getStringCellValue());
					}
					else if (c == 11){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_range_upPost = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setSite_range_upPost(site_range_upPost);
						}else{
							videoIrontowerDto.setSite_range_upPost(cell.getStringCellValue());
						}
					}
					else if (c == 12){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String site_range_downPost = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setSite_range_downPost(site_range_downPost);
						}else{
							videoIrontowerDto.setSite_range_downPost(cell.getStringCellValue());
						}
					}
					else if (c == 13){
						videoIrontowerDto.setSite_other_line(cell.getStringCellValue());
					}
					else if (c == 14){
						videoIrontowerDto.setSite_other_place(cell.getStringCellValue());
					}

					else if (c == 15){
						videoIrontowerDto.setAssetOwnership(cell.getStringCellValue());
					}
					else if (c == 16){
						videoIrontowerDto.setOwnershipUnitName(cell.getStringCellValue());
					}
					else if (c == 17){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							videoIrontowerDto.setOwnershipUnitCode(ownershipUnitCode);
						}else{

							videoIrontowerDto.setOwnershipUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 18){
						videoIrontowerDto.setMaintainBody(cell.getStringCellValue());
					}
					else if (c == 19){
						videoIrontowerDto.setMaintainUnitName(cell.getStringCellValue());
					}
					else if (c == 20){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setMaintainUnitCode(maintainUnitCode);
						}else{
							videoIrontowerDto.setMaintainUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 21){
							videoIrontowerDto.setTowerType(cell.getStringCellValue());
					}
					else if (c == 22){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String towerHeight = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setTowerHeight(towerHeight);
						}else{
							videoIrontowerDto.setTowerHeight(cell.getStringCellValue());
						}
					}
					else if (c == 23){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String longitude = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setLongitude(longitude);
						}else{
							videoIrontowerDto.setLongitude(cell.getStringCellValue());
						}
					}
					else if (c == 24){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String latitude = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setLatitude(latitude);
						}else{
							videoIrontowerDto.setLatitude(cell.getStringCellValue());
						}
					}
					else if (c == 25){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String altitude = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setAltitude(altitude);
						}else{
							videoIrontowerDto.setAltitude(cell.getStringCellValue());
						}
					}
					else if (c == 26){
						videoIrontowerDto.setManufacturers(cell.getStringCellValue());
					}
					else if (c == 27){
						videoIrontowerDto.setUseUnit(cell.getStringCellValue());
					}
					else if (c == 28) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							videoIrontowerDto.setProductionDate(entryDateStr);
						} else {
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							videoIrontowerDto.setProductionDate(entryDate);
						}
					}
					else if (c == 29){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							videoIrontowerDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							videoIrontowerDto.setUseDate(entryDate);
						}
					}
					else if (c == 30){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							videoIrontowerDto.setMiddleRepairDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							videoIrontowerDto.setMiddleRepairDate(entryDate);
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
							videoIrontowerDto.setLargeRepairDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							videoIrontowerDto.setLargeRepairDate(entryDate);
						}
					}
					else if (c == 32){
						videoIrontowerDto.setDeviceOperationStatus(cell.getStringCellValue());
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
							videoIrontowerDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							videoIrontowerDto.setStopDate(entryDate);
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
							videoIrontowerDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							videoIrontowerDto.setScrapDate(entryDate);
						}
					}
					else if (c == 35){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							videoIrontowerDto.setFixedAssetsCode(fixedAssetsCode);
						}else{
							videoIrontowerDto.setFixedAssetsCode(cell.getStringCellValue());
						}
					}
					else if (c == 36){
						videoIrontowerDto.setRemark(cell.getStringCellValue());
					}
				}
			}
			// 添加到list
			fdpList.add(videoIrontowerDto);
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
