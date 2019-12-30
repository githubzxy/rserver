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

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineHoleDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineHoleDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineHoleDto;

public class ReadExcelLineHole {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelLineHole(){}
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
	public List<LineHoleDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<LineHoleDto> fdpList = null;
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
	public List<LineHoleDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<LineHoleDto> fdpList = null;
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
	private List<LineHoleDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<LineHoleDto> fdpList = new ArrayList<LineHoleDto>();
		//开始根据模板读取excel的数据
		// 循环Excel行数，每一行转化为一个dto
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			LineHoleDto LineHoleDto = new LineHoleDto();
			// 循环Excel的列,每个单元格数据转化为dto的属性值
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 if (c == 1){
						 LineHoleDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){  

						LineHoleDto.setWorkArea(cell.getStringCellValue());
					}
					else if (c == 3){   
						
						LineHoleDto.setCombinationClass(cell.getStringCellValue());
						
					}
					else if (c == 4){   
						
						LineHoleDto.setDeviceClass(cell.getStringCellValue());
						
					}
					else if (c == 5){   
						
						LineHoleDto.setDeviceName(cell.getStringCellValue());
						
					}
					else if (c == 6){   
						
						LineHoleDto.setAssetOwnership(cell.getStringCellValue());
						
					}
					else if (c == 7){   
						
						LineHoleDto.setOwnershipUnitName(cell.getStringCellValue());
						
					}
					else if (c == 8){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将表格中的科学计数法转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
							LineHoleDto.setOwnershipUnitCode(ownershipUnitCode);							
						}else{

							LineHoleDto.setOwnershipUnitCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 9){   
						
						LineHoleDto.setMaintainBody(cell.getStringCellValue());
						
					}
					else if (c == 10){   
						
						LineHoleDto.setMaintainUnitName(cell.getStringCellValue());
						
					}
					else if (c == 11){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
							LineHoleDto.setMaintainUnitCode(maintainUnitCode);							
						}else{

							LineHoleDto.setMaintainUnitCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 12){   
						
						LineHoleDto.setManufacturers(cell.getStringCellValue());
						
					}
					else if (c == 13){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String deviceType = String.valueOf(cell.getNumericCellValue());
							LineHoleDto.setDeviceType(deviceType);							
						}else{

							LineHoleDto.setDeviceType(cell.getStringCellValue());							
						}
						
					}
					else if (c == 14){   
						
						LineHoleDto.setUseUnit(cell.getStringCellValue());
						
					}
				
					else if (c == 15){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineHoleDto.setProductionDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineHoleDto.setProductionDate(entryDate);
						}
						
					}
					else if (c == 16){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineHoleDto.setUseDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineHoleDto.setUseDate(entryDate);
						}
						
//						LineHoleDto.setUseDate(cell.getStringCellValue());
						
					}
					else if (c == 17){   
						
						LineHoleDto.setDeviceOperationStatus(cell.getStringCellValue());
						
					}
					else if (c == 18){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							Date entryDate = cell.getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    		String entryDateStr = "";
				    		try {
				    			entryDateStr = sdf.format(entryDate);
				    		} catch (Exception e) {
				    			e.printStackTrace();
				    		}
							LineHoleDto.setStopDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineHoleDto.setStopDate(entryDate);
						}
//						LineHoleDto.setStopDate(cell.getStringCellValue());
						
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
							LineHoleDto.setScrapDate(entryDateStr);
						}else{
							String entryDate = String.valueOf(cell.getStringCellValue());
							entryDate.replace('/', '-');
							LineHoleDto.setScrapDate(entryDate);
						}
//						LineHoleDto.setScrapDate(cell.getStringCellValue());
						
					}
					else if (c == 20){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							
							String site = String.valueOf(cell.getNumericCellValue());
							LineHoleDto.setSite(site);							
						}else{
							
							LineHoleDto.setSite(cell.getStringCellValue());							
						}
						
					}
					else if (c == 21){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
							LineHoleDto.setFixedAssetsCode(fixedAssetsCode);							
						}else{

							LineHoleDto.setFixedAssetsCode(cell.getStringCellValue());							
						}
						
					}
					else if (c == 22){   
						
						LineHoleDto.setRemark(cell.getStringCellValue());
						
					}
	
				}
			}
			// 添加到list
			fdpList.add(LineHoleDto);
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
