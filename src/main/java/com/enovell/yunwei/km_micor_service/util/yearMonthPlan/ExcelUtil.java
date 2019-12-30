package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.MergeRange;

/**
 * 年月表专供excel操作工具类
 * @author Roy
 * 2017年5月23日--下午3:19:53
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ExcelUtil {

	/**
	 * 在mongo中单元格数据的key前缀，统一为cell，一行中第一个单元格的key为cell0,第二个为cell1，依此类推
	 */
	public static final String DATA_KEY_PREFIX = "cell";
	/**
	 * 对单元格字符串值进行合并时的连接符
	 */
	public static final String STRING_CONCAT_SYMBOL= "&";
	/**
	 * 单元格的换行符
	 */
	public static final String NEWLINE_SYMBOL = "\r\n";
	
	/**读取一行中所有单元格的值
	 * @param row
	 * @param listCombineCell
	 * @param colNum 
	 * @return
	 */
	public static List<String> readRow(HSSFRow row, String[][] listCombineCell, int colNum) {
		colNum = (colNum == 0) ? row.getPhysicalNumberOfCells() : colNum;
		final List<String> result = new ArrayList<String>();
		IntStream.range(0, colNum).forEach(c->{
			HSSFCell cell = row.getCell(c);
			String value = (cell != null) ? getCellValue(cell) : "";
			value = (value.equals("BLANK")) ? getCombineCellValue(listCombineCell, cell, row.getSheet()) : value;
			result.add(value);
		});
		return result;
	}
	
	/**获取合并单元格的值
	 * @param listCombineCell
	 * @param cell
	 * @param sheet
	 * @return
	 */
	private static String getCombineCellValue(String[][] listCombineCell, HSSFCell cell, HSSFSheet sheet) {
		try{
			MergeRange result = getMergeRangeByCell(listCombineCell,cell.getRowIndex(),cell.getColumnIndex());
//			int rowIndex = cell.getRowIndex(),columnIndex = cell.getColumnIndex();
//			MergeRange result = listCombineCell.stream().filter(m->{
//				return rowIndex <= m.getLastRow() && rowIndex >= m.getFirstRow() && columnIndex <= m.getLastCol() && columnIndex >= m.getFirstCol();
//			}).findFirst().get();
//			CellRangeAddress result = listCombineCell.stream().filter(ca->{
//				//TODO 性能瓶颈，待解决
//				return cell.getColumnIndex() <= ca.getLastColumn() 
//						&& cell.getColumnIndex() >= ca.getFirstColumn()
//						&& cell.getRowIndex() <= ca.getLastRow() 
//						&& cell.getRowIndex() >= ca.getFirstRow();
//			}).findFirst().get();
			if(result == null)
				return "";
			HSSFRow fRow = sheet.getRow(result.getFirstRow());
			HSSFCell fCell = fRow.getCell(result.getFirstCol());
			return getCellValue(fCell);
		}catch(NoSuchElementException e){
			return "";
		}
	}
	
	
	/**    
	 * getMergeRangeByCell 通过单元格的坐标查找对应的合并单元格范围
	 * @author quyy
	 * @param listCombineCell
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	private static MergeRange getMergeRangeByCell(String[][] listCombineCell, int rowIndex, int columnIndex) {
		String get = listCombineCell[rowIndex][columnIndex];
		if(StringUtils.isNotBlank(get)) {
			String[] fstr = get.split(",");
			MergeRange result = new MergeRange();
			result.setFirstRow(Integer.parseInt(fstr[0]));
			result.setFirstCol(Integer.parseInt(fstr[1]));
			return result;
		}
		return null;
	}

	/**获取所有合并单元格的位置集合
	 * @param sheet
	 * @param colNum 
	 * @return
	 */
	private static String[][] getCombineCell(HSSFSheet sheet, int colNum) {
		String[][] result = new String[sheet.getPhysicalNumberOfRows()+1][colNum+1];
		IntStream.range(0,sheet.getNumMergedRegions())
		.forEach(i->{
			CellRangeAddress ca = sheet.getMergedRegion(i);
			for(int row = ca.getFirstRow();row <= ca.getLastRow();row++) {
				for(int col = ca.getFirstColumn();col <= ca.getLastColumn();col++) {
					result[row][col] = ca.getFirstRow() + "," + ca.getFirstColumn();
				}
			}
		});
		return result;
	}
	
	/**生成表头 格式按左到右的顺序生成数字，前缀为cell
	 * @param row
	 * @return
	 */
	public static List<String> getColKey(HSSFRow row) {
		List<String> result = new ArrayList<String>();
		IntStream.range(0, row.getPhysicalNumberOfCells()).forEach(i->result.add(DATA_KEY_PREFIX + i));
		return result;
	}
	
	/**通过表头定义集合和值集合生成单行JSON格式数据
	 * @param oneRow
	 * @param colKeyList
	 * @param rowDataList
	 * @return
	 */
	public static Map<String, Object> generateRowData(List<String> colKeyList,
			List<String> rowDataList) {
		final Map<String, Object> oneRow = new HashMap<String, Object>();
		IntStream.range(0, colKeyList.size()).forEach(i->{
			if(i < rowDataList.size())
				oneRow.put(colKeyList.get(i), rowDataList.get(i));
		});
		return oneRow;
	}
	
	/**取出指定sheet页的数据，按行封装成map对象，key为cell1、cell2....，value是对应的值（取出sheet页的所有数据）
	 * @param s sheet页
	 * @param tableHeaderRowNum 表头字段所在行，从0开始计算
	 * @param dataStartRowNum 数据起始行，从0开始计算
	 * @return
	 */
	public static List<Map> getSheetData(HSSFSheet s,int tableHeaderRowNum,int dataStartRowNum){
		List<String> colKeyList = getColKey(s.getRow(tableHeaderRowNum));
		String[][] listCombineCell = getCombineCell(s,colKeyList.size());
		final List<Map> allTableData = new ArrayList<Map>();
		IntStream.range(dataStartRowNum, s.getPhysicalNumberOfRows())
		.forEach(i->{
			HSSFRow row = s.getRow(i);
			if (row != null && !row.getZeroHeight()) {//空行或者隐藏行不加入数据集
				List<String> rowDataList = readRow(row, listCombineCell,colKeyList.size());
				allTableData.add(generateRowData(colKeyList, rowDataList));
			}
		});
		return removeEmptyRowData(allTableData);
	}
	
	/**移除值全部无效的空行
	 * @param allTableData
	 * @return
	 */
	public static List<Map> removeEmptyRowData(List<Map> allTableData) {
		List<Map> result = new ArrayList<>();
		allTableData.stream()
		.filter(m->{return rowDataIsNotEmpty(m);})
		.forEach(m -> {
			result.add((Map)m);
		});
		return result;
	}

	/**判断一行数据是否有效,一行数据中只有一个单元格的有效数据就认为此行数据有效
	 * @param data
	 * @return
	 */
	public static boolean rowDataIsNotEmpty(Map<String, Object> datas) {
		return datas.entrySet().stream().anyMatch(m->{
			return cellDataIsValid(m.getValue());
		});
	}
	
	/**根据单元格的值获取对应的浮点值，保留两位小数
	 * @param ori
	 * @return
	 */
	public static double getDoubleValue(Object ori){
		try{
			BigDecimal bd = new BigDecimal(ori.toString());
			return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}catch(NumberFormatException e){
			return 0;
		}
	}
	
	/**合并单元格
	 * @param s
	 * @param mergeRanges
	 */
	public static void mergeCells(final HSSFSheet s, List<MergeRange> mergeRanges) {
		mergeRanges.stream().forEach(m->{
			if(m.getFirstRow() != m.getLastRow()){
				CellRangeAddress cra = new CellRangeAddress(m.getFirstRow(), m.getLastRow(), m.getFirstCol(), m.getLastCol());
				s.addMergedRegion(cra);
			}
		});
	}
	
	/**生成统一序号合并单元格的值
	 * @param s
	 * @param idxRange
	 * @param workShopData
	 */
	public static void generateMergedIdxValue(HSSFSheet s, List<MergeRange> idxMergeRange) {
		for(int i = 0;i < idxMergeRange.size();i++){
			MergeRange m = idxMergeRange.get(i);
			HSSFRow idxRow = s.getRow(m.getFirstRow());
			HSSFCell idxCell = idxRow.getCell(m.getFirstCol());
			idxCell.setCellValue(i + 1);//序号从1开始
		}
	}

	/**判断一个单元格的数据是否有效
	 * @param data
	 * @return
	 */
	public static boolean cellDataIsValid(Object data) {
		if(data == null)
			return false;
		String dataStr = data.toString().trim();
		if(StringUtils.isBlank(dataStr))
			return false;
		if(dataStr.trim().equals("0"))
			return false;
		return true;
	}

	public static String getCellValue(HSSFCell cell) {
		String value = null;
		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
			case FORMULA:
				value = "" + cell.getCellFormula();
				break;
			case NUMERIC:
				value = "" + cell.getNumericCellValue();
				break;
			case STRING:
				value = cell.getStringCellValue();
				break;
			case BLANK:
				value = "BLANK";
				break;
			case BOOLEAN:
				value = "" + cell.getBooleanCellValue();
				break;
			case ERROR:
				value = "" + cell.getErrorCellValue();
				break;
			default:
				value = "" + cell.getCellTypeEnum();
			}
		}
		return value;
	}
	
	/**单元格样式：加边框，文字居中换行
	 * @param wb
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	
	/**单元格样式：加边框，文字居左换行
	 * @param wb
	 */
	public static HSSFCellStyle getCellLeftStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	
	/**单元格样式：加边框，文字居左换行,用于普铁年月表
	 * @param wb
	 */
	public static HSSFCellStyle getPuTieCellLeftStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setFontName("宋体");
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	/**单元格样式：加边框，文字居中换行，用于普铁年月表
	 * @param wb
	 */
	public static HSSFCellStyle getPuTieCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setFontName("宋体");
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	
	/**
	 * 读取所有工区的计划excel封装成集合 （读取所有数据）
	* getWorkshopData 
	* @param allWorkAreaExcels
	* @param headerRownum
	* @param dataStartRownum
	* @return
	* List<Map>
	* @author luoyan
	 */
	public static List<Map> getWorkshopData(List<String> allWorkAreaExcels,int headerRownum,int dataStartRownum,int monthNum,String filePath) {
		
		//循环读取工区的excel文件并封装成待插入数据
		List<Map> result = allWorkAreaExcels.parallelStream()
		.map(fileName ->{
			fileName = filePath + fileName;
			FileInputStream fis = null;
			HSSFWorkbook wb = null;
			List<Map> allTableData = new ArrayList<>();
			try {
				fis = new FileInputStream(fileName);
				wb =  new HSSFWorkbook(fis);
				HSSFSheet sheet = wb.getSheetAt(monthNum);
				allTableData = ExcelUtil.getSheetData(sheet, headerRownum, dataStartRownum);
				return allTableData;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try{
					fis.close();
					wb.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			return allTableData;
		}).reduce((o,t) -> {
			o.addAll(t);
			return o;
		}).get();
		return result;
	}
	
	/**  
	 * getYearWorkshopData 获取年表数据
	 * 
	 * @param allWorkAreaExcels
	 * @param headerRownum
	 * @param dataStartRownum
	 * @param monthNum
	 * @param filePath
	 * @return 
	 */  
	public static List<Map> getYearWorkshopData(List<String> allWorkAreaExcels,int headerRownum,int dataStartRownum,int monthNum,String filePath) {
		
		//循环读取工区的excel文件并封装成待插入数据
		List<Map> result = allWorkAreaExcels.parallelStream()
				.map(fileName ->{
					fileName = filePath + fileName;
					FileInputStream fis = null;
					HSSFWorkbook wb = null;
					List<Map> allTableData = new ArrayList<>();
					try {
						fis = new FileInputStream(fileName);
						wb =  new HSSFWorkbook(fis);
						HSSFSheet sheet = wb.getSheetAt(monthNum);
						allTableData = ExcelUtil.getSheetData(sheet, headerRownum, dataStartRownum);
						return allTableData;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try{
							fis.close();
							wb.close();
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
					return allTableData;
				}).reduce((o,t) -> {
					o.addAll(t);
					return o;
				}).get();
		return result;
	}
	
	
	/**
	 * 读取单个excel封装成集合 
	* getDatasByFilePath 
	* @param filePath
	* @param headerRownum
	* @param dataStartRownum
	* @return
	* List<Map>
	* @author quyy
	 */
	public static List<Map> getDatasByFilePath(String filePath,int headerRownum,int dataStartRownum) {
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		List<Map> allTableData = new ArrayList<>();
		try {
			fis = new FileInputStream(filePath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet sheet = wb.getSheetAt(0);
			allTableData = ExcelUtil.getSheetData(sheet, headerRownum, dataStartRownum);
			return allTableData;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				fis.close();
				wb.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return allTableData;
	}
}
