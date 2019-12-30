package com.enovell.yunwei.km_micor_service.util;
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

import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ProcuratorialDailyDto;
/**
 * 
 * @author xiekun
 * 导入检修日志表
 *
 */
public class ReadExcelProcuDaily {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelProcuDaily(){}
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
	public List<ProcuratorialDailyDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<ProcuratorialDailyDto> fdpList = null;
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
	public List<ProcuratorialDailyDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<ProcuratorialDailyDto> fdpList = null;
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
	private List<ProcuratorialDailyDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<ProcuratorialDailyDto> fdpList = new ArrayList<ProcuratorialDailyDto>();
		// 循环Excel行数
		for (int r = 4; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			ProcuratorialDailyDto procuratorialDailyDto = new ProcuratorialDailyDto();
			// 循环Excel的列

			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 if (c == 1){
							procuratorialDailyDto.setWorkshop(cell.getStringCellValue());
					}
					else if (c == 2){  

							procuratorialDailyDto.setDepartment(cell.getStringCellValue());
					}
					else if (c == 3){   
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							   Date date = cell.getDateCellValue();
							   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					    		String dateStr = "";
					    		try {
					    			dateStr = sdf.format(date);
					    		} catch (Exception e) {
					    			e.printStackTrace();
					    		}
					    		procuratorialDailyDto.setDate(dateStr);
						}else{
							String date = String.valueOf(cell.getStringCellValue());
							date.replace('/', '-');
							procuratorialDailyDto.setDate(date);
						}
					}
					else if (c == 4){
							procuratorialDailyDto.setInspector(cell.getStringCellValue());
						
					}
					else if (c == 5){
							procuratorialDailyDto.setSite(cell.getStringCellValue());
					}
					else if (c == 6){
							procuratorialDailyDto.setContent(cell.getStringCellValue());
					}
					else if (c == 7){
							procuratorialDailyDto.setProblem(cell.getStringCellValue());
					}
					else if (c == 8){
							procuratorialDailyDto.setRequire(cell.getStringCellValue());
					}
					else if (c == 9){
							procuratorialDailyDto.setCondition(cell.getStringCellValue());
					}
					else if (c == 10){
							procuratorialDailyDto.setFunctionary(cell.getStringCellValue());
					}
					else if (c == 11){
							procuratorialDailyDto.setRemark(cell.getStringCellValue());
					}
				  }
				}
			// 添加到list
			fdpList.add(procuratorialDailyDto);
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
