package com.enovell.yunwei.km_micor_service.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.convert.converter.ConditionalConverter;

import com.enovell.yunwei.km_micor_service.action.attachFile.AttachFileAction;

public class ExportExcel <T>{

	/**
	 * 
	 * exportXls 导出xls文件
	 * 表头格式如下：<br />
	 * String[] headerTableColumns = new String[]{	<br />
	 * 	        "用户名" + "_" +"15" + "_" + "my.getName()",	<br />
	 * 	        "地址" + "_" +"20" + "_" + "my.getAddress()",	<br />
	 * 	        "年龄" + "_" +"15" + "_" + "my.getAge()",	<br />
	 * 	        "测试" + "_" +"15" + "_" + "my.getTest().getId()"	<br />
	 * 	};	<br />
	 * @param fileName 文件名称
	 * @param headerName excel表头
	 * @param headerTableColumns 表格表头
	 * @param expandJexlContext 扩展JexlContext
	 * @param dataList 数据
	 * @param request 
	 * @param response
	 */
	public void exportXls(String fileName, String headerName, String[] headerTableColumns, Map<?, ?> expandJexlContext,  List<T> dataList, 
			HttpServletRequest request, HttpServletResponse response) {
		
		HSSFWorkbook workbook = createWorkbook(headerName, headerTableColumns, expandJexlContext, dataList);
		outFile(fileName, workbook, request, response);
	}
	
	/**
	 * 
	 * exportXls 导出xls文件
	 * 表头格式如下：<br />
	 * String[] headerTableColumns = new String[]{	<br />
	 * 	        "用户名" + "_" +"15" + "_" + "my.getName()",	<br />
	 * 	        "地址" + "_" +"20" + "_" + "my.getAddress()",	<br />
	 * 	        "年龄" + "_" +"15" + "_" + "my.getAge()",	<br />
	 * 	        "测试" + "_" +"15" + "_" + "my.getTest().getId()"	<br />
	 * 	};	<br />
	 * @param fileName 文件名称
	 * @param headerTableColumns 表格表头
	 * @param expandJexlContext 扩展JexlContext
	 * @param dataList 数据
	 * @param request 
	 * @param response
	 */
	public void exportXls(String fileName, String[] headerTableColumns, Map<?, ?> expandJexlContext,  List<T> dataList, 
			HttpServletRequest request, HttpServletResponse response) {
		
		exportXls(fileName, null, headerTableColumns, expandJexlContext, dataList, request, response);
	}
	
	/**
	 * 
	 * createWorkbook 创建excel文件	<br />
	 * @param headerTableColumns 表头
	 * @param expandJexlContext 扩展JexlContext
	 * @param dataList 数据
	 * @return workbook
	 */
    @SuppressWarnings("unchecked")
	private  HSSFWorkbook createWorkbook(String headerName, String[] headerTableColumns, Map<?, ?> expandJexlContext,  List<T> dataList) {
    	HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		int rowNum = 0;
		
		if(StringUtils.isNotBlank(headerName)) {
			// 创建excel表头
			rowNum = createHeader(rowNum, workbook, sheet, headerName, headerTableColumns);
			rowNum ++;
		}
		// 创建表格表头
		rowNum = createTableHeader(rowNum, workbook, sheet, headerTableColumns);
		
		HSSFCellStyle style = getCellStyle(workbook,false);
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Row row = null;
		Cell cell = null;
		T t = null;
		for(int i = 0; i < dataList.size(); i ++){
			t = dataList.get(i);
			rowNum ++;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(25);
			
			// 设置序号单元格
			cell = row.createCell(0);
			cell.setCellStyle(style);
			// 序号从1开始
			cell.setCellValue(i+1);
			
			for(int j = 0; j < headerTableColumns.length; j++){
				String methodStr = headerTableColumns[j].split("_")[2];
				try {
					// Expression Language 的解析引擎，可以将字符串转换为代码执行。
					Expression expression = ExpressionFactory.createExpression(methodStr);
					JexlContext context = JexlHelper.createContext();
					context.setVars(expandJexlContext);
					context.getVars().put("my", t);
					Object value = expression.evaluate(context);
					
					String cellValue = "";
					if (value instanceof Date){
					    Date date = (Date)value;
					    cellValue = sd.format(date);
					}else{
					    cellValue = null != value ? value.toString() : "";
					}
					// 从序号后添加单元格
					cell = row.createCell(j + 1);
					cell.setCellStyle(style);
					cell.setCellValue(cellValue);
				} catch (Exception e) {
				    e.printStackTrace();
				}
			}        
		}
		return workbook;
	}
    
    /**
     * 
     * createHeader 创建excel表头
     * @param rowNum
     * @param workbook
     * @param sheet
     * @param headerName
     * @param headerTableColumns
     * @return
     */
    private int createHeader(int rowNum, HSSFWorkbook workbook, HSSFSheet sheet, String headerName, String[] headerTableColumns) {
    	
    	HSSFCellStyle style = getCellStyle(workbook,true);
        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30);
        Cell cell = null;
        cell = row.createCell(0);
        cell.setCellValue(headerName);
        cell.setCellStyle(style);
        
        // 合并单元格
        manualMergerCell(sheet, 0, 0, 0, headerTableColumns.length);
        
        return rowNum;
    }
    
    /**
     * 
     * createTableHeader 创建表格表头
     * @param workbook
     * @param sheet
     * @param headerTableColumns
     * @return
     */
    private  int createTableHeader(int rowNum, HSSFWorkbook workbook, HSSFSheet sheet, String[] headerTableColumns){
        HSSFCellStyle style = getCellStyle(workbook,true);
        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30);
        Cell cell = null;
        // 默认添加序号列
        cell = row.createCell(0);
        sheet.setColumnWidth(0, 7 * 256);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        
        for(int i = 0; i < headerTableColumns.length; i++) {
        	// 从序号后添加单元格
            cell = row.createCell(i + 1);
            String[] column = headerTableColumns[i].split("_");
            // 从序号后设置列宽度
            sheet.setColumnWidth(i + 1, Integer.valueOf(column[1]) * 256);
            cell.setCellValue(column[0]);
            cell.setCellStyle(style);
        }
        
        return rowNum;
	}
    
	private HSSFCellStyle getCellStyle(HSSFWorkbook workbook, boolean isHeader){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // excel换行条件：1、cellStyle.setWrapText(true);2、在要换行处添加\n就可以换行了
        style.setWrapText(true);
		// 单元格平水和垂直对齐方式
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        if (isHeader) {
        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());

            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style.setFont(font);
        }        
        return style;
    }
	
	/**
	 * 
	 * manualMergerCell 手动合并单元格
	 * @param sheet 
	 * @param firstRow 开始行
	 * @param lastRow 结束行
	 * @param firstColumn 开始列
	 * @param lastColumn 结束列
	 */
	private void manualMergerCell(Sheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn) {
		
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));
	}
	
	/**
	 * 
	 * outFile 输出文件
	 * @param fileName
	 * @param workbook
	 * @param request
	 * @param response
	 */
	private void outFile(String fileName, Workbook workbook, 
								HttpServletRequest request, HttpServletResponse response)  {
		ServletOutputStream out = null;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			fileName = AttachFileAction.urlEncoder(request, fileName + formatDate.format(new Date()));
			
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("binary/octet-stream");
			
			response.setHeader("Content-disposition", "attachment; fileName = " + fileName + ".xls");
			
			out = response.getOutputStream();
			workbook.write(out); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
