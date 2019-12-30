package com.enovell.yunwei.km_micor_service.util;
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
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ConstructRepairDto;

public class ReadExcel {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcel(){}
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
	public List<ConstructRepairDto> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		System.out.println("文件名"+fileName);
		List<ConstructRepairDto> crdList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
		  crdList = createExcel(mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crdList;
	}


  /**
   * 根据excel里面的内容信息
   * @param is 输入流
   * @param isExcel2003 excel是2003还是2007版本
   * @return
 * @throws ParseException 
   * @throws IOException
   */
	public List<ConstructRepairDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
		List<ConstructRepairDto> crdList = null;
		try{
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			 crdList = readExcelValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return crdList;
	}  
  /**
   * 读取Excel里面的信息
   * @param wb
   * @return
 * @throws ParseException 
   */
	private List<ConstructRepairDto> readExcelValue(Workbook wb) throws ParseException {
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
		List<ConstructRepairDto> crdList = new ArrayList<ConstructRepairDto>();
		// 循环Excel行数
		for (int r = 3; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			ConstructRepairDto constructRepairDto = new ConstructRepairDto();
			// 循环Excel的列

			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 if (c == 1){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							//将科学计数法的格式转换成正常格式
							DecimalFormat df =new DecimalFormat("0");
							String date = String.valueOf(df.format(cell.getNumericCellValue()));
							StringBuffer str =new StringBuffer(date);
							str.insert(6, "-");
							str.insert(4, "-");
							date=str.toString();
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								sdf.setLenient(false);
								if(sdf.parse(date) != null){
								constructRepairDto.setDate(date);
								}
							} catch (Exception e) {
								break;
							}
						}else{
							String date =cell.getStringCellValue();
							StringBuffer str =new StringBuffer(date);
							str.insert(6, "-");
							str.insert(4, "-");
							date=str.toString();
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								sdf.setLenient(false);
//								sdf.parse(date);
								if(sdf.parse(date) != null){
								constructRepairDto.setDate(date);
								}
							} catch (Exception e) {
								break;
							}
							constructRepairDto.setDate(date);
						}
					}
					else if (c == 2){  
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String planNum = String.valueOf(cell.getNumericCellValue());
							//将字符串的后两位（.0）去掉
							planNum=planNum.substring(0,planNum.length()-2);
							constructRepairDto.setPlanNum(planNum);

						}else{

							constructRepairDto.setPlanNum(cell.getStringCellValue());
						}
					}

					else if (c == 4){   

						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

							String line = String.valueOf(cell.getNumericCellValue());
							constructRepairDto.setLine(line);
						}else{

							constructRepairDto.setLine(cell.getStringCellValue());
						}
					}
					else if (c == 8){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String grade = String.valueOf(cell.getNumericCellValue());
							constructRepairDto.setGrade(grade);
						}else{
							constructRepairDto.setGrade(cell.getStringCellValue());
						}
					}
					else if (c == 11){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String repairType = String.valueOf(cell.getNumericCellValue());
							constructRepairDto.setRepairType(repairType);
						}else{
							constructRepairDto.setRepairType(cell.getStringCellValue());
						}
					}
					else if (c == 15){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String constructContent = String.valueOf(cell.getNumericCellValue());
							constructRepairDto.setConstructContent(constructContent);
						}else{
							constructRepairDto.setConstructContent(cell.getStringCellValue());
						}
					}
					else if (c == 17){
								try {
									String planTime = String.valueOf(cell.getStringCellValue());
									//获取单元格括号里的内容
									String  strMiu = planTime.substring(planTime.indexOf("(")+1, planTime.indexOf(")"));
									//去除字符串后两位
									strMiu = strMiu.substring(0,strMiu.length()-2);
									constructRepairDto.setApplyMinute(strMiu);
									//截取单元格字符串起始时间
									String startDatestr = planTime.substring(0,5);
									//转换成字符串时间格式
									StringBuffer str =new StringBuffer(startDatestr);
									str.insert(5, ":00");
									str.insert(0, " ");
									str.insert(0, constructRepairDto.getDate());
									//设置到dto
									String planAgreeTimeStart = str.toString();
//									constructRepairDto.setPlanAgreeTimeStart(planAgreeTimeStart);
									//计算结束时间
									SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date endDate = df.parse(planAgreeTimeStart);
									endDate.setTime(endDate.getTime()+Integer.valueOf(strMiu)*60*1000);
									String planAgreeTimeEnd = String.valueOf(df.format(endDate));
//									constructRepairDto.setPlanAgreeTimeEnd(planAgreeTimeEnd);
									//拼接计划批准时间
									StringBuffer strAgree =new StringBuffer(planAgreeTimeEnd);
									strAgree.insert(0, "-");
									strAgree.insert(0, planAgreeTimeStart);
									String planAgreeTime = strAgree.toString();
									constructRepairDto.setPlanAgreeTime(planAgreeTime);
									df.setLenient(false);
									//验证解析的数据是否正确,若正确再插入
									if(df.parse(planAgreeTimeStart) != null && df.parse(planAgreeTimeStart) != null){
										constructRepairDto.setPlanAgreeTimeStart(planAgreeTimeStart);
										constructRepairDto.setPlanAgreeTimeEnd(planAgreeTimeEnd);
									}
								} catch (Exception e) {
									break;
								}
								
								
								
					}

				}
			}
			// 添加到list
			crdList.add(constructRepairDto);
		}
		return crdList;
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
