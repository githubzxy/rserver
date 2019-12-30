package com.enovell.yunwei.km_micor_service.util;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.FloodDuardPointDto;

public class ReadExcelFlood {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelFlood(){}
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
	public List<FloodDuardPointDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<FloodDuardPointDto> fdpList = null;
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
	public List<FloodDuardPointDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<FloodDuardPointDto> fdpList = null;
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
	private List<FloodDuardPointDto> readExcelValue(Workbook wb) throws ParseException {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		System.out.println("gaolei dayin============" +sheet);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("行数======="+this.totalRows);
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("总列数=========="+this.totalCells);
		}
		List<FloodDuardPointDto> fdpList = new ArrayList<FloodDuardPointDto>();
		// 循环Excel行数
		for (int r = 3; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			FloodDuardPointDto floodDuardPointDto = new FloodDuardPointDto();
			// 循环Excel的列

			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 if (c == 1){
							floodDuardPointDto.setOrgSelectName(cell.getStringCellValue());
					}
				   else if (c == 2){  

						floodDuardPointDto.setWorkArea(cell.getStringCellValue());
				   }
					else if (c == 3){  

							floodDuardPointDto.setLineName(cell.getStringCellValue());
					}
					else if (c == 4){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String section = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setSection(section);
						}else{
							floodDuardPointDto.setSection(cell.getStringCellValue());
						}
					}
					else if (c == 5){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String guardName = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setGuardName(guardName);
						}else{
							floodDuardPointDto.setGuardName(cell.getStringCellValue());
						}
					}
					else if (c == 6){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String typeLT = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setTypeLT(typeLT);
						}else{
							floodDuardPointDto.setTypeLT(cell.getStringCellValue());
						}
					}
					else if (c == 7){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String countLT = String.valueOf(cell.getNumericCellValue());
							countLT=countLT.substring(0,countLT.length()-2);
							floodDuardPointDto.setCountLT(countLT);
						}else{
							floodDuardPointDto.setCountLT(cell.getStringCellValue());
						}
					}
					else if (c == 8){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String typeYJ = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setTypeYJ(typeYJ);
						}else{
							floodDuardPointDto.setTypeYJ(cell.getStringCellValue());
						}
					}
					else if (c == 9){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String countYJ = String.valueOf(cell.getNumericCellValue());
							countYJ=countYJ.substring(0,countYJ.length()-2);
							floodDuardPointDto.setCountYJ(countYJ);
						}else{
							floodDuardPointDto.setCountYJ(cell.getStringCellValue());
						}
					}
					else if (c == 10){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String typeGD = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setTypeGD(typeGD);
						}else{
							floodDuardPointDto.setTypeGD(cell.getStringCellValue());
						}
					}
					else if (c == 11){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String typeGW = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setTypeGW(typeGW);
						}else{
							floodDuardPointDto.setTypeGW(cell.getStringCellValue());
						}
					}
					else if (c == 12){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String countGW = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setCountGW(countGW);
						}else{
							floodDuardPointDto.setCountGW(cell.getStringCellValue());
						}
					}
					else if (c == 13){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String phoneNumGW = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setPhoneNumGW(phoneNumGW);
						}else{
							floodDuardPointDto.setPhoneNumGW(cell.getStringCellValue());
						}
					}
				 
					else if (c == 14){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String condition = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setCondition(condition);
						}else{
							floodDuardPointDto.setCondition(cell.getStringCellValue());
						}
					}
					else if (c == 15){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String phoneNum = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setPhoneNum(phoneNum);
						}else{
							floodDuardPointDto.setPhoneNum(cell.getStringCellValue());
						}
					}
					else if (c == 16){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String phoneAP = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setPhoneAP(phoneAP);
						}else{
							floodDuardPointDto.setPhoneAP(cell.getStringCellValue());
							}
						
					}
					else if (c == 17){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String leadType = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setLeadType(leadType);
						}else{
							floodDuardPointDto.setLeadType(cell.getStringCellValue());
							}
						
				  }
					else if (c == 18){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String leadExtent = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setLeadExtent(leadExtent);
						}else{
							String leadExtent2 = String.valueOf(cell.getStringCellValue());
							leadExtent2.replace('m', '米');
							floodDuardPointDto.setLeadExtent(leadExtent2);
						}
					}
					else if (c == 19){  

						floodDuardPointDto.setKsStatus(cell.getStringCellValue());
				}
					else if (c == 20){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String remark = String.valueOf(cell.getNumericCellValue());
							floodDuardPointDto.setRemark(remark);
						}else{
							floodDuardPointDto.setRemark(cell.getStringCellValue());
						}
				  }
					

				}
			}
			// 添加到list
			fdpList.add(floodDuardPointDto);
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
