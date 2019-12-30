package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessHandDto;
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

public class ReadExcelWirelessHand {
	//总行数
	private int totalRows = 0;
	//总条数
	private int totalCells = 0;
	//错误信息接收器
	private String errorMsg;
	//构造方法
	public ReadExcelWirelessHand(){}
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
	public List<WirelessHandDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<WirelessHandDto> fdpList = null;
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
	public List<WirelessHandDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<WirelessHandDto> fdpList = null;
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
	private List<WirelessHandDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<WirelessHandDto> fdpList = new ArrayList<WirelessHandDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			WirelessHandDto wirelessHandDto = new WirelessHandDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 1){
						wirelessHandDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){
						wirelessHandDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){
						wirelessHandDto.setCombinationClass(cell.getStringCellValue());
					}
					else if (c == 4){
						wirelessHandDto.setDeviceClass(cell.getStringCellValue());
					}
					else if (c == 5){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String deviceCode = String.valueOf(cell.getNumericCellValue());
							wirelessHandDto.setDeviceCode(deviceCode);
						}else{
							wirelessHandDto.setDeviceCode(cell.getStringCellValue());
						}
					}
					else if (c == 6){
						wirelessHandDto.setDeviceName(cell.getStringCellValue());
					}
					else if (c == 7){
						wirelessHandDto.setSite_line(cell.getStringCellValue());
					}
					else if (c == 8){
						wirelessHandDto.setSite_department(cell.getStringCellValue());
					}

					else if (c == 9){
						wirelessHandDto.setAssetOwnership(cell.getStringCellValue());
					}
					else if (c == 10){
						wirelessHandDto.setOwnershipUnitName(cell.getStringCellValue());
					}
					else if (c == 11){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							wirelessHandDto.setOwnershipUnitCode(ownershipUnitCode);
						}else{

							wirelessHandDto.setOwnershipUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 12){
						wirelessHandDto.setMaintainBody(cell.getStringCellValue());
					}
					else if (c == 13){
						wirelessHandDto.setMaintainUnitName(cell.getStringCellValue());
					}
					else if (c == 14){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							wirelessHandDto.setMaintainUnitCode(maintainUnitCode);
						}else{
							wirelessHandDto.setMaintainUnitCode(cell.getStringCellValue());
						}
					}
					else if (c == 15){
						wirelessHandDto.setManufacturers(cell.getStringCellValue());
					}
					else if (c == 16){
						wirelessHandDto.setDeviceType(cell.getStringCellValue());
					}
					else if (c == 17){
						wirelessHandDto.setUseUnit(cell.getStringCellValue());
					}
					else if (c == 18) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessHandDto.setProductionDate(entryDateStr);
						} else {
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessHandDto.setProductionDate(entryDate);
						}
					}
					else if (c == 19){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessHandDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessHandDto.setUseDate(entryDate);
						}
					}

					else if (c == 20){
						wirelessHandDto.setDeviceOperationStatus(cell.getStringCellValue());
					}
					else if (c == 21){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessHandDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessHandDto.setStopDate(entryDate);
						}
					}
					else if (c == 22){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String entryDateStr = "";
							try {
								entryDateStr = sdf.format(entryDate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							wirelessHandDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							wirelessHandDto.setScrapDate(entryDate);
						}
					}
					else if (c == 23){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							wirelessHandDto.setFixedAssetsCode(fixedAssetsCode);
						}else{
							wirelessHandDto.setFixedAssetsCode(cell.getStringCellValue());
						}
					}
					else if (c == 24){
						wirelessHandDto.setRemark(cell.getStringCellValue());
					}
				}
			}
			// 添加到list
			fdpList.add(wirelessHandDto);
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
