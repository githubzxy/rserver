package com.enovell.yunwei.km_micor_service.util;

import java.io.FileInputStream;
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

import com.enovell.yunwei.km_micor_service.dto.AttendanceSumDto;

public class ReadExcelAttendance {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcelAttendance(){}
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
	public List<AttendanceSumDto> getExcelInfo(String filePath,String date,String attendanceOrgName, String attendanceOrgId) {
//		String fileName = mFile.getOriginalFilename();//获取文件名
		List<AttendanceSumDto> fdpList = null;
		try {
			if (!validateExcel(filePath)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(filePath)) {
				isExcel2003 = false;
			}
			FileInputStream fis = new FileInputStream(filePath);
			fdpList = createExcel(fis, isExcel2003 ,date,attendanceOrgName,attendanceOrgId);
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
	public List<AttendanceSumDto> createExcel(InputStream is, boolean isExcel2003, String date,String attendanceOrgName, String attendanceOrgId) throws ParseException {
		List<AttendanceSumDto> fdpList = null;
		try{
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			 fdpList = readExcelValue(wb,date,attendanceOrgName,attendanceOrgId);// 读取Excel里面客户的信息
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
	private List<AttendanceSumDto> readExcelValue(Workbook wb,String date,String attendanceOrgName, String attendanceOrgId) throws ParseException {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
//		System.out.println("gaolei dayin============" +sheet);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
//		System.out.println("行数======="+this.totalRows);
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
//			System.out.println("总列数=========="+this.totalCells);
		}
		List<AttendanceSumDto> fdpList = new ArrayList<AttendanceSumDto>();
		// 循环Excel行数(8为行尾不需要的行数，5为行头不需要的行数)
		for (int r = 0; r < (totalRows-8-5)/3; r++) {
			Row row = sheet.getRow(r*3+5);
			if (row == null){
				continue;
			}
			AttendanceSumDto attendanceSumDto = new AttendanceSumDto();
			attendanceSumDto.setYearmonth(date);
			attendanceSumDto.setOrgName(attendanceOrgName);
			attendanceSumDto.setOrgId(attendanceOrgId);
			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
				 
					if (c == 1){
							
						attendanceSumDto.setNumber(cell.getStringCellValue());
					}
					else if (c == 2){
							
						attendanceSumDto.setPeopleName(cell.getStringCellValue());
					}
					else if (c == 3){
						
						attendanceSumDto.setPostName(cell.getStringCellValue());
					}
					else if (c == 36){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String xuexi = String.valueOf(cell.getNumericCellValue());
							xuexi=xuexi.substring(0,xuexi.length()-2);
							attendanceSumDto.setXuexi(xuexi);
						}else{
							attendanceSumDto.setXuexi(cell.getStringCellValue());
						}
					}
					else if (c == 37){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String gongchai = String.valueOf(cell.getNumericCellValue());
							gongchai=gongchai.substring(0,gongchai.length()-2);
							attendanceSumDto.setGongchai(gongchai);
						}else{
							attendanceSumDto.setGongchai(cell.getStringCellValue());
						}
					}
					else if (c == 38){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String yebanxiao = String.valueOf(cell.getNumericCellValue());
							attendanceSumDto.setYebanxiao(yebanxiao);
						}else{
							attendanceSumDto.setYebanxiao(cell.getStringCellValue());
						}
					}
					else if (c == 39){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String yebanda = String.valueOf(cell.getNumericCellValue());
							attendanceSumDto.setYebanda(yebanda);
						}else{
							attendanceSumDto.setYebanda(cell.getStringCellValue());
						}
					}
					else if (c == 40){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String riqin = String.valueOf(cell.getNumericCellValue());
							attendanceSumDto.setRiqin(riqin);
						}else{
							attendanceSumDto.setRiqin(cell.getStringCellValue());
						}
					}
					else if (c == 41){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String lunban = String.valueOf(cell.getNumericCellValue());
							attendanceSumDto.setLunban(lunban);
						}else{
							attendanceSumDto.setLunban(cell.getStringCellValue());
						}
					}
					else if (c == 42){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String nianxiu = String.valueOf(cell.getNumericCellValue());
							nianxiu=nianxiu.substring(0,nianxiu.length()-2);
							attendanceSumDto.setNianxiu(nianxiu);
						}else{
							attendanceSumDto.setNianxiu(cell.getStringCellValue());
						}
					}
					else if (c == 43){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String tanqin = String.valueOf(cell.getNumericCellValue());
							tanqin=tanqin.substring(0,tanqin.length()-2);
							attendanceSumDto.setTanqin(tanqin);
						}else{
							attendanceSumDto.setTanqin(cell.getStringCellValue());
						}
					}
					else if (c == 44){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String hunjia = String.valueOf(cell.getNumericCellValue());
							hunjia=hunjia.substring(0,hunjia.length()-2);
							attendanceSumDto.setHunjia(hunjia);
						}else{
							attendanceSumDto.setHunjia(cell.getStringCellValue());
						}
					}
					else if (c == 45){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String sangjia = String.valueOf(cell.getNumericCellValue());
							sangjia=sangjia.substring(0,sangjia.length()-2);
							attendanceSumDto.setSangjia(sangjia);
						}else{
							attendanceSumDto.setSangjia(cell.getStringCellValue());
						}
					}
					else if (c == 46){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String shijia = String.valueOf(cell.getNumericCellValue());
							shijia=shijia.substring(0,shijia.length()-2);
							attendanceSumDto.setShijia(shijia);
						}else{
							String shijia = String.valueOf(cell.getStringCellValue());
//							leadExtent2.replace('m', '米');
							attendanceSumDto.setShijia(shijia);
						}
					}
					else if (c == 47){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String bingjia = String.valueOf(cell.getNumericCellValue());
							bingjia=bingjia.substring(0,bingjia.length()-2);
							attendanceSumDto.setBingjia(bingjia);
						}else{
							attendanceSumDto.setBingjia(cell.getStringCellValue());
						}
					}
					else if (c == 48){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String zhuyuan = String.valueOf(cell.getNumericCellValue());
							zhuyuan=zhuyuan.substring(0,zhuyuan.length()-2);
							attendanceSumDto.setZhuyuan(zhuyuan);
						}else{
							attendanceSumDto.setZhuyuan(cell.getStringCellValue());
						}
					}
					else if (c == 49){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String chanjia = String.valueOf(cell.getNumericCellValue());
							chanjia=chanjia.substring(0,chanjia.length()-2);
							attendanceSumDto.setChanjia(chanjia);
						}else{
							attendanceSumDto.setChanjia(cell.getStringCellValue());
						}
					}
					else if (c == 50){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String jisheng = String.valueOf(cell.getNumericCellValue());
							jisheng=jisheng.substring(0,jisheng.length()-2);
							attendanceSumDto.setJisheng(jisheng);
						}else{
							attendanceSumDto.setJisheng(cell.getStringCellValue());
						}
					}
					else if (c == 51){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String huli = String.valueOf(cell.getNumericCellValue());
							huli=huli.substring(0,huli.length()-2);
							attendanceSumDto.setHuli(huli);
						}else{
							attendanceSumDto.setHuli(cell.getStringCellValue());
						}
					}
					else if (c == 52){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String xigong = String.valueOf(cell.getNumericCellValue());
							xigong=xigong.substring(0,xigong.length()-2);
							attendanceSumDto.setXigong(xigong);
						}else{
							attendanceSumDto.setXigong(cell.getStringCellValue());
						}
					}
					else if (c == 53){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String kuanggong = String.valueOf(cell.getNumericCellValue());
							kuanggong=kuanggong.substring(0,kuanggong.length()-2);
							attendanceSumDto.setKuanggong(kuanggong);
						}else{
							attendanceSumDto.setKuanggong(cell.getStringCellValue());
						}
					}
					else if (c == 54){
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							String total = String.valueOf(cell.getNumericCellValue());
							total=total.substring(0,total.length()-2);
							attendanceSumDto.setTotal(total);
						}else{
							attendanceSumDto.setTotal(cell.getStringCellValue());
						}
					}
				}
			}
			// 添加到list
			fdpList.add(attendanceSumDto);
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
