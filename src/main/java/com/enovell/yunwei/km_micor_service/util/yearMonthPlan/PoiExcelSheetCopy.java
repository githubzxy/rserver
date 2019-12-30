
/**   
 * 文件名：PoiExcelSheetCopy.java    
 * 版本信息：IRMS1.0   
 * 日期：2019年1月9日 下午2:18:32   
 * Copyright Enovell Corporation 2019    
 * 版权所有   
 *   
 */

package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;

/**      
 * 项目名称：yearMonthPlan
 * 类名称：PoiExcelSheetCopy   
 * 类描述:  POI操作Excel进行Sheet页的复制
 * 创建人：lidt 
 * 创建时间：2019年1月9日 下午2:18:32
 * 修改人：lidt 
 * 修改时间：2019年1月9日 下午2:18:32   
 *    
 */
@SuppressWarnings("resource")
public class PoiExcelSheetCopy {
	
	/**
	 * 被复制的文件路径名称
	 */
	public static final String FILE_PATH = "filePath";
	/**
	 * 对应的生成复制文件excel的Sheet页的名称
	 */
	public static final String SHEET_NAME = "sheetName";
	
	/**    
	 * getExcelwithSheets 获取复制的sheet文件
	 * @param fromFileDatas 被复制的excel文件路径（filePath），以及对应的生成的复制文件excel的Sheet页的名称(sheetName)
	 * @param copyToPath 生成的复制文件excel路径
	 * @param fromFileSheetNum 复制excel文件的第几个sheet页
	 */
	public static void getExcelwithSheets(List<Map<String, String>> fromFileDatas, String copyToPath,int fromFileSheetNum) {
		HSSFWorkbook newExcelCreat = new HSSFWorkbook();
		try {
			for (int i = 0; i < fromFileDatas.size(); i++) {
				Map<String, String> map = fromFileDatas.get(i);
				InputStream in = new FileInputStream(map.get(FILE_PATH));
				HSSFWorkbook fromExcel = new HSSFWorkbook(in);
				HSSFSheet oldSheet = fromExcel.getSheetAt(fromFileSheetNum);
				HSSFSheet newSheet = newExcelCreat.createSheet(map.get(SHEET_NAME));
				copySheet(oldSheet, newSheet);
			}
			FileOutputStream fileOut = new FileOutputStream(copyToPath);
			newExcelCreat.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将单个sheet复制12份，生成新的excel(包含12个sheet)文件
	 * getMonthReportMould12  获取包含12个sheet的excel文件
	 * @param fromPath 来源文件路径（包含一个sheet）
	 * @param toPath 目的文件路径（包含12个sheet）
	 * @param saveName true：保存原来的sheet名称，false：不保存.
	 */
	public static void getMonthReportMould12(String fromPath,String toPath,boolean saveName) {
		HSSFWorkbook newExcelCreat = new HSSFWorkbook();
		try {
			for (int i = 1; i <= 12; i++) {
				InputStream in = new FileInputStream(fromPath);
				HSSFWorkbook fromExcel = new HSSFWorkbook(in);
				HSSFSheet oldSheet = fromExcel.getSheetAt(0);
				String sheetName = oldSheet.getSheetName();
				HSSFSheet newSheet = newExcelCreat.createSheet(saveName ? i + sheetName : i + "月");
				copySheet(oldSheet, newSheet);
			}
			FileOutputStream fileOut = new FileOutputStream(toPath);
			newExcelCreat.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**    
	 * copySheet 拷贝Sheet
	 * @param fromSheet 来源sheet
	 * @param toSheet 目标sheet
	 */
	public static void copySheet(HSSFSheet fromSheet, HSSFSheet toSheet) {
		// 从来源行中找到对应的合并单元格，并将目标行对应区域的单元格进行合并
		mergeSheetAllRegion(fromSheet, toSheet);
		// 设置列宽
		for (int i = 0; i <= fromSheet.getRow(fromSheet.getFirstRowNum()).getLastCellNum(); i++) {
			toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
		}
		// 拷贝行数据
		for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			HSSFRow oldRow = (HSSFRow) rowIt.next();
			HSSFRow newRow = toSheet.createRow(oldRow.getRowNum());
			copyRow(oldRow, newRow);
		}
	}
	
    /**    
     * mergeSheetAllRegion 从来源行中找到对应的合并单元格，并将目标行对应区域的单元格进行合并
     * @param fromSheet 来源sheet
     * @param toSheet 目标sheet
     */
	public static void mergeSheetAllRegion(HSSFSheet fromSheet,	HSSFSheet toSheet) {
		int num = fromSheet.getNumMergedRegions();
		CellRangeAddress cellR = null;
		for (int i = 0; i < num; i++) {
			cellR = fromSheet.getMergedRegion(i);
			toSheet.addMergedRegion(cellR);
		}
	}
    
    /**    
     * copyRow 拷贝行数据
     * @param oldRow 来源行
     * @param toRow 目的行
     */
	public static void copyRow(HSSFRow oldRow, HSSFRow toRow) {
//		toRow.setHeight(oldRow.getHeight());
		for (Iterator cellIt = oldRow.cellIterator(); cellIt.hasNext();) {
			HSSFCell tmpCell = (HSSFCell) cellIt.next();
			HSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
			// 拷贝单元格
			copyCell(tmpCell, newCell);
		}
	}

    /**    
     * copyCell 拷贝单元格
     * @param fromCell 来源单元格
     * @param newCell 目的单元格
     */
    public static void copyCell(HSSFCell fromCell, HSSFCell newCell) {
    	// 同一excel文件中单元格的样式及字体拷贝
    	copyCellStyleAndFont(fromCell, newCell);
        if (fromCell.getCellComment() != null) {  
        	newCell.setCellComment(fromCell.getCellComment());  
        }  
        // 不同数据类型处理  
        switch (fromCell.getCellTypeEnum()) {
		case STRING:
			newCell.setCellValue(fromCell.getRichStringCellValue());
			break;
		case NUMERIC:
			newCell.setCellValue(fromCell.getNumericCellValue());
			break;
		case FORMULA:
			newCell.setCellFormula(fromCell.getCellFormula());
			break;
		case BOOLEAN:
			newCell.setCellValue(fromCell.getBooleanCellValue());
			break;
		case ERROR:
			newCell.setCellValue(fromCell.getErrorCellValue());
			break;
		default:
			newCell.setCellValue(fromCell.getRichStringCellValue());
			break;
		}
    }

	/** 
	 * copyCellStyleAndFont 同一excel文件中单元格的样式及字体拷贝
	 * @param sourceCell 源单元格
	 * @param targetCell 拷贝到的单元格
	 */
	public static void copyCellStyleAndFont(HSSFCell sourceCell,HSSFCell targetCell) {
		// 获取拷贝后的样式
		Map<String, Object> styleMap = getCopyStyle(sourceCell.getCellStyle());
		CellUtil.setCellStyleProperties(targetCell, styleMap);
		// 单元格字体样式
		Font newFont = copyFont(sourceCell.getCellStyle(), sourceCell.getSheet().getWorkbook(), targetCell.getSheet().getWorkbook());
		CellUtil.setFont(targetCell, newFont);
	}
    
    /** 
     * getCopyStyle 获取拷贝后的样式
     * @param cellStyle 源样式
     * @return
     */
    public static Map<String, Object> getCopyStyle(CellStyle cellStyle) {
        if(cellStyle == null) {
            return new HashMap<String, Object>();
        }   
        /*  
         * 不使用“newCellStyle.cloneStyleFrom(cellStyle)”样式拷贝的原因：
         * Office Excel 中弹出提示信息： 此文件中的某些文本格式可能已经更改，因为它已经超出最多允许字体数。关闭其他文档再试一次可能有用。文件错误。数据可能丢失。
         * WPS Excel 正常
         * 
         * 解决方案：每个样式逐一拷贝
         */
        Map<String, Object> styleMap = new HashMap<String, Object>();
        // 是否换行
        styleMap.put(CellUtil.WRAP_TEXT, cellStyle.getWrapText());
        // 单元格边框样式
        styleMap.put(CellUtil.BORDER_BOTTOM, cellStyle.getBorderBottomEnum().getCode());
        styleMap.put(CellUtil.BORDER_LEFT, cellStyle.getBorderLeftEnum().getCode());
        styleMap.put(CellUtil.BORDER_RIGHT, cellStyle.getBorderRightEnum().getCode());
        styleMap.put(CellUtil.BORDER_TOP, cellStyle.getBorderTopEnum().getCode());
        // 单元格背景颜色
        styleMap.put(CellUtil.FILL_PATTERN, cellStyle.getFillPatternEnum().getCode());
        styleMap.put(CellUtil.FILL_FOREGROUND_COLOR, cellStyle.getFillForegroundColor());
        // 单元格平水和垂直对齐方式                                                                                                                                          
        styleMap.put(CellUtil.ALIGNMENT, cellStyle.getAlignmentEnum().getCode());
        styleMap.put(CellUtil.VERTICAL_ALIGNMENT, cellStyle.getVerticalAlignmentEnum().getCode());
        return styleMap;
    }
    
	/**
	 * copyFont 拷贝字体
	 * @param cellStyle 源样式
	 * @param workbook 源工作薄
	 * @param newWorkbook 新工作薄
	 * @return
	 */
	private static Font copyFont(CellStyle cellStyle, Workbook workbook, Workbook newWorkbook) {
		
		Font font = workbook.getFontAt(cellStyle.getFontIndex());
		// 查询newWorkbook是否存在字体
		Font findedFont = newWorkbook.findFont(font.getBold(), font.getColor(), font.getFontHeight(), 
				font.getFontName(), font.getItalic(), font.getStrikeout(), font.getTypeOffset(), font.getUnderline());

		if(findedFont == null) {
			// 创建字体
			Font newFont = newWorkbook.createFont();
			newFont.setBold(font.getBold());
			newFont.setColor(font.getColor());
			newFont.setFontHeight(font.getFontHeight());
			newFont.setFontName(font.getFontName());
			newFont.setItalic(font.getItalic());
			newFont.setStrikeout(font.getStrikeout());
			newFont.setTypeOffset(font.getTypeOffset());
			newFont.setUnderline(font.getUnderline());
			return newFont;
		} 
		return findedFont;
	}
	
}
