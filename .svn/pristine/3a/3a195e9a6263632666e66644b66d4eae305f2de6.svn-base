
/**   
 * 文件名：AutoBuildCompleteTable.java    
 * @author quyy  
 * 日期：2017年12月26日 下午4:39:02      
 *   
 */

package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.MergeRange;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.ExcelUtil;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：AutoBuildCompleteTable   
 * 类描述: 自动生成完成表 
 * 创建人：quyy 
 * 创建时间：2017年12月26日 下午4:39:02 
 *    
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Service("autoBuildCompleteTableService")
public class AutoBuildCompleteTableImpl implements AutoBuildCompleteTableService{
	
	/**
	 * 第一列和设备名称对应的序号
	 */
	private static final String ALL_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"0";
	/**
	 * 第二列设备处所
	 */
	private static final String LOCALE_KEY = ExcelUtil.DATA_KEY_PREFIX+"1";
	/**
	 * 第三列设备名称
	 */
	private static final String DEPT_NAME_KEY = ExcelUtil.DATA_KEY_PREFIX+"2";
	/**
	 * 第四列维护工作内容的序号
	 */
	private static final String ITEM_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"3";
	/**
	 * 第五列维护工作内容
	 */
	private static final String ITEM_KEY = ExcelUtil.DATA_KEY_PREFIX+"4";
	
	/**
	 * 通信年月表EXCEL中的数据起始行
	 */
	private static int DATA_START_ROW_NUM = 4;
	
	/**
	 * 计划表中开始出现设备包保人的数据条数
	 */
	private static int DATA_NUM = 0;

	/**
	 * 
	 * getGuarantorMergeRange 合并设备包保人这行数据的信息
	 * @author quyy
	 * @param s
	 * @param rowNum
	 * @param cellNum
	 * @return
	 */
	private MergeRange getAdditionalDataMergeRange(HSSFSheet s,int firstRow,int lastRow,int firstcell,int lastCell) {
		MergeRange mergeRange =  new MergeRange();
		mergeRange.setFirstCol(firstcell);
		mergeRange.setLastCol(lastCell);
		mergeRange.setFirstRow(firstRow);
		mergeRange.setLastRow(lastRow);
		CellRangeAddress cra = new CellRangeAddress(mergeRange.getFirstRow(), mergeRange.getLastRow(), mergeRange.getFirstCol(), mergeRange.getLastCol());
		s.addMergedRegion(cra);
		return mergeRange;
	}
	/**根据统计结果生成对应的excel
	 * @param ww
	 * @param workShopData
	 * @return
	 */
	private String generateWorkShopFirstExcel(List<Map> workShopData, String headerFilePath, String completeFilePath,
			int overhaulStartCellNum, int overhalEndCellNum,int cellNum) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(headerFilePath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			List<MergeRange> deptRange = getAndMergeDeptRange(workShopData,s);
			for(int i=4;i<overhaulStartCellNum;i++) {
				mergeWorkItemRange(deptRange,i,s);//合并单元格  
			}
			for(int i=overhalEndCellNum+2;i<cellNum+1;i++) {
				mergeWorkItemRange(deptRange,i,s);//合并单元格  
			}
			HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(wb);
			HSSFCellStyle cellLeftStyle = ExcelUtil.getCellLeftStyle(wb);
			setWorkShopFirstExcelValue(workShopData, s,  cellStyle,cellLeftStyle,overhaulStartCellNum,overhalEndCellNum,cellNum);
			fos = new FileOutputStream(completeFilePath);
			wb.write(fos);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
				fos.close();
				wb.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return completeFilePath;
	}
	/**生成计划表8_2中的后面几行附件数据在它完成表中，注意，计划表中的附件数据的所占的行数和顺序不能随意更改
	 * @param ww
	 * @param workShopData
	 * @return
	 */
	private String generateExcelAdditionalData8_2(List<Map> datas,String completeFilePath,
			int overhaulStartCellNum, int overhalEndCellNum,int cellNum) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(completeFilePath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			//计划表的附加数据的开始行数
			int additionalDataStartRow = DATA_NUM+DATA_START_ROW_NUM;
			//合并设备包保人信息
			getAdditionalDataMergeRange(s,additionalDataStartRow,additionalDataStartRow,0,cellNum);
			//合并段负责人
			getAdditionalDataMergeRange(s,additionalDataStartRow+1,additionalDataStartRow+1,0,cellNum);
			//合并说明
			getAdditionalDataMergeRange(s,additionalDataStartRow+2,additionalDataStartRow+7,0,cellNum);
			HSSFCellStyle cellLeftStyle = ExcelUtil.getCellLeftStyle(wb);
			setExcelAdditionalData8_2(datas, s,cellLeftStyle,overhaulStartCellNum,overhalEndCellNum,cellNum);
			fos = new FileOutputStream(completeFilePath);
			wb.write(fos);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
				wb.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return completeFilePath;
	}
	
	/**生成计划表8_3和8_4中的后面几行附件数据在它完成表中，注意，计划表中的设备包保人信息的所占的行数和顺序不能随意更改
	 * @param ww
	 * @param workShopData
	 * @return
	 */
	private String generateExcelAdditionalData8_3(List<Map> datas,String completeFilePath,
			int overhaulStartCellNum, int overhalEndCellNum,int cellNum) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(completeFilePath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			//计划表的设备包保人的开始行数
			int additionalDataStartRow = DATA_NUM+DATA_START_ROW_NUM;
			HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(wb);
			setExcelAdditionalData8_2(datas, s,cellStyle,overhaulStartCellNum,overhalEndCellNum,cellNum);
			//合并设备包保人信息
			getAdditionalDataMergeRange(s,additionalDataStartRow,additionalDataStartRow+1,0,4);
			//合并设备包保人
			getAdditionalDataMergeRange(s,additionalDataStartRow,additionalDataStartRow,5,8);
			//合并设备包保人字母代表
			getAdditionalDataMergeRange(s,additionalDataStartRow+1,additionalDataStartRow+1,5,8);
			//合并段负责人
			getAdditionalDataMergeRange(s,additionalDataStartRow+2,additionalDataStartRow+3,0,cellNum);
			fos = new FileOutputStream(completeFilePath);
			wb.write(fos);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
				wb.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return completeFilePath;
	}
	
	/**设置excel的值
	 * @param ww
	 * @param workShopData
	 * @param s
	 * @param idxRange
	 * @param localeRange
	 * @param deptRange
	 * @param cellStyle
	 */
	private void setExcelAdditionalData8_2(List<Map> datas, HSSFSheet s,HSSFCellStyle cellLeftStyle,int overhaulStartCellNum, int overhalEndCellNum,int cellNum) {
		for(int k = 0;k<datas.size();k++) {
			HSSFRow row = s.createRow(DATA_NUM + DATA_START_ROW_NUM+k);
			Map rowData = datas.get(k);
			for (int i=0;i<cellNum + 1 ;i++) {
				int j = i;
				HSSFCell cell = row.createCell(i);//计划完成 年表
				cell.setCellStyle(cellLeftStyle);
				if(i==overhaulStartCellNum) {
					cell.setCellValue(String.valueOf(rowData.get(ExcelUtil.DATA_KEY_PREFIX+cellNum)));
					continue;
				}
				if(i>overhaulStartCellNum) {
					j = i-1;
				}
				cell.setCellValue(String.valueOf(rowData.get(ExcelUtil.DATA_KEY_PREFIX+j)));
			}
		}
	}
	/**根据设备名的合并单元格范围合并工作项目、单位和计划数量的单元格
	 * @param deptRange
	 * @param colNum
	 * @param s 
	 * @return
	 */
	private List<MergeRange> mergeWorkItemRange(List<MergeRange> deptRange, int colNum, HSSFSheet s) {
		List<MergeRange> result = new ArrayList<MergeRange>();
		for(int i = 0;i < deptRange.size();i++){
			MergeRange dept = deptRange.get(i);
			//在设备名称的每个合并格范围内，每两行合并一次，这两行对应同一工作项目的计划行和完成行
			for(int start = dept.getFirstRow();start < dept.getLastRow();start = start + 2){
				MergeRange item = new MergeRange();
				item.setFirstCol(colNum);
				item.setLastCol(colNum);
				item.setFirstRow(start);
				item.setLastRow(start + 1);
				result.add(item);
			}
		}
		ExcelUtil.mergeCells(s, result);
		return result;
	}
	/**设置车间统计初步计划excel的值
	 * @param ww
	 * @param workShopData
	 * @param s
	 * @param idxRange
	 * @param localeRange
	 * @param deptRange
	 * @param cellStyle
	 */
	private void setWorkShopFirstExcelValue(List<Map> workShopData, HSSFSheet s,HSSFCellStyle cellStyle,HSSFCellStyle cellLeftStyle,int overhaulStartCellNum, int overhalEndCellNum,int cellNum) {
		for(int i = 0;i < workShopData.size();i++){
			Map<String,Object> wsa = workShopData.get(i);
			HSSFRow r = s.createRow(i + DATA_START_ROW_NUM);
			insertWorkshopFirstRowData(r,wsa,cellStyle,cellLeftStyle,overhaulStartCellNum,overhalEndCellNum,cellNum);
		}
	}
	
	/**插入车间初步计划单行数据
	 * @param r
	 * @param wsa
	 * @param cellStyle
	 */
	private HSSFRow insertWorkshopFirstRowData(HSSFRow r, Map<String, Object> wsa, HSSFCellStyle cellStyle,HSSFCellStyle cellLeftStyle,int overhaulStartCellNum, int overhalEndCellNum,int cellNum) {
		HSSFCell idx = r.createCell(0);//序号
		idx.setCellStyle(cellStyle);
		idx.setCellValue((int)ExcelUtil.getDoubleValue(String.valueOf(wsa.get(ALL_IDX_KEY)).trim()));
		HSSFCell locale = r.createCell(1);//设备所在处所
		locale.setCellStyle(cellStyle);
		if(String.valueOf(wsa.get(LOCALE_KEY)).equals("BLANK")) {
			locale.setCellValue("");
		}else {
			locale.setCellValue(String.valueOf(wsa.get(LOCALE_KEY)));
		}
		HSSFCell depName = r.createCell(2);//设备名称
		depName.setCellStyle(cellStyle);
		depName.setCellValue(String.valueOf(wsa.get(DEPT_NAME_KEY)));
		HSSFCell itemIdx = r.createCell(3);//工作内容序号
		itemIdx.setCellStyle(cellStyle);
		itemIdx.setCellValue((int)ExcelUtil.getDoubleValue(String.valueOf(wsa.get(ITEM_IDX_KEY))));
		HSSFCell workContent = r.createCell(4);//工作内容
		workContent.setCellStyle(cellLeftStyle);
		workContent.setCellValue(String.valueOf(wsa.get(ITEM_KEY)));
		for(int i = 5;i < cellNum+1;i++){
			int j = i;
			if(i==overhaulStartCellNum) {
				HSSFCell planCom = r.createCell(overhaulStartCellNum);//计划完成 年表
				planCom.setCellStyle(cellStyle);
				planCom.setCellValue(String.valueOf(wsa.get(ExcelUtil.DATA_KEY_PREFIX+cellNum)));
				continue;
			}
			if(i>overhaulStartCellNum) {
				j = i-1;
			}
			HSSFCell cell = r.createCell(i);
			cell.setCellStyle(cellStyle);
			Object value = wsa.get(ExcelUtil.DATA_KEY_PREFIX + j);
			if(String.valueOf(value).trim().equals("0.0")
			 ||String.valueOf(value).trim().equals("")
			 ||String.valueOf(value).equals("null")) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) != 0 ){
				cell.setCellValue(ExcelUtil.getDoubleValue(String.valueOf(value)));
				continue;
			}else {
				cell.setCellValue(String.valueOf(value));
			}
		}
		return r;
	}
	/**根据统计数据获取设备名称的合并单元格范围并进行合并
	 * @param workShopData
	 * @return
	 */
	private List<MergeRange> getAndMergeDeptRange(List<Map> workShopData,HSSFSheet s) {
		List<MergeRange> deptRange = new ArrayList<MergeRange>();
		String deptName = "";
		MergeRange dept = new MergeRange();
		dept.setFirstCol(2);//设备名称只占第3列，所以没有列合并，开始列和结束列都为2
		dept.setLastCol(2);
		for(int i = 0;i < workShopData.size();i++){
			Map<String,Object> wa = workShopData.get(i);
			String deptTemp = wa.get(DEPT_NAME_KEY).toString();
			if(StringUtils.isBlank(deptName)){//设备名称第一行必定有值，将赋给比较对象后，直接进入下一行
				dept.setFirstRow(i + DATA_START_ROW_NUM);//设备名称从第6行开始，所以所有的行数皆等于序号加5
				deptName = deptTemp;
				continue;
			}
			if(!deptName.equals(deptTemp)){//本行设备名称和上一行不相同时
				int endRow = i + DATA_START_ROW_NUM - 1;//说明上一行为上个设备的结束行，行数等于序号加5再减1
				dept.setLastRow(endRow);
				if(endRow != dept.getFirstRow())//如果相同设备名称只占一行，则不进行合并
					deptRange.add(dept);
				dept = new MergeRange();
				dept.setFirstCol(2);
				dept.setLastCol(2);
				dept.setFirstRow(i + DATA_START_ROW_NUM);
				deptName = deptTemp;
			}
			if(i == workShopData.size() - 1){//循环结束时，最后一行即最后一个设备的最后行数
				dept.setLastRow(i + DATA_START_ROW_NUM);
				if(dept.getFirstRow() != dept.getLastRow())//如果相同设备名称只占一行，则不进行合并
					deptRange.add(dept);
			}
		}
		ExcelUtil.mergeCells(s, deptRange);
		return deptRange;
	}

	@Override
	public String getCompleteExcelByPlanExcel(String planFilePath, String headerFilePath, String completeFilePath,
			int overhaulStartCellNum, int overhalEndCellNum,int cellNum,int herderStartRowNum,int dataStartRowNum) {
		DATA_START_ROW_NUM = dataStartRowNum;
		//用poi读出计划表excel中的数据，并且生成好完成表的数据
		List<Map> datas = writeDataByFilePath(planFilePath,overhaulStartCellNum,overhalEndCellNum,cellNum,herderStartRowNum);
		//将数据写入excel中，生成完成表。
		String workShopFileName = generateWorkShopFirstExcel(datas,headerFilePath,completeFilePath,
															  overhaulStartCellNum,overhalEndCellNum,cellNum);
		return workShopFileName;
	}
	/**
	 * 
	 * writeDataByFilePath  通过报表路径读出数据，并写入mongodb数据库中
	 * @author quyy
	 * @param filePaths
	 * @return
	 */
	private List<Map> writeDataByFilePath(String planFilePath,int overhaulStartCellNum, int overhalEndCellNum,int cellNum,int herderStartRowNum) {
		//根据年月表的路径用poi读出数据
		List<Map> datas = ExcelUtil.getDatasByFilePath(planFilePath, herderStartRowNum, DATA_START_ROW_NUM);
		List<Map> dataComplete = new ArrayList<Map>();
		for (Map map : datas) {
			map.put("cell"+cellNum, "计划");
			dataComplete.add(map);
			Map map2 = new HashMap();
			map2.putAll(map);
			map2.put("cell"+cellNum, "完成");
			for(int j=overhaulStartCellNum;j<overhalEndCellNum+1;j++) {
				map2.put("cell"+j, "");
			}
			dataComplete.add(map2);
		}
		return dataComplete;
	}
	
	/**
	 * 
	 * writeDataByFilePathOther  通过报表路径读出数据，并写入mongodb数据库中,用于普铁科中8-2，8_3,8_4的表
	 * @author quyy
	 * @param filePaths
	 * @return
	 */
	private List<Map> writeDataByFilePath8_2(String planFilePath,int overhaulStartCellNum, int overhalEndCellNum,int cellNum,int herderStartRowNum) {
		//根据年月表的路径用poi读出数据
		List<Map> datas = ExcelUtil.getDatasByFilePath(planFilePath, herderStartRowNum, DATA_START_ROW_NUM);
		List<Map> dataComplete = new ArrayList<Map>();
		int dataSize = 0;
		DATA_NUM = 0;
		for (Map map : datas) {
			if(ExcelUtil.getDoubleValue(map.get("cell0").toString()) == 0) {
				DATA_NUM = dataSize*2;
				map.put("cell"+cellNum, "");
				dataComplete.add(map);
				continue;
			}
			dataSize++;
			map.put("cell"+cellNum, "计划");
			dataComplete.add(map);
			Map map2 = new HashMap();
			map2.putAll(map);
			map2.put("cell"+cellNum, "完成");
			for(int j=overhaulStartCellNum;j<overhalEndCellNum+1;j++) {
				map2.put("cell"+j, "");
			}
			dataComplete.add(map2);
		}
		return dataComplete;
	}
	@Override
	public String getComletePuTie8_2ByPlanExcel(String planFilePath, String headerFilePath, String completeFilePath,
			int overhaulStartCellNum, int overhalEndCellNum, int cellNum, int herderStartRowNum, int dataStartRowNum) {
		DATA_START_ROW_NUM = dataStartRowNum;
		//用poi读出计划表excel中的数据，并且生成好完成表的数据
		List<Map> datas = writeDataByFilePath8_2(planFilePath,overhaulStartCellNum,overhalEndCellNum,cellNum,herderStartRowNum);
		if(DATA_NUM == 0) {
			//将数据写入excel中，生成完成表。
			String workShopFileName = generateWorkShopFirstExcel(datas,headerFilePath,completeFilePath,
																  overhaulStartCellNum,overhalEndCellNum,cellNum);
			return workShopFileName;
		}
		List<Map> dataMain = datas.subList(0, DATA_NUM);
		List<Map> dataAdditional = datas.subList(DATA_NUM, datas.size());
		//将数据写入excel中，生成完成表。
		String workShopFileName = generateWorkShopFirstExcel(dataMain,headerFilePath,completeFilePath,
															  overhaulStartCellNum,overhalEndCellNum,cellNum);
		generateExcelAdditionalData8_2(dataAdditional, completeFilePath, overhaulStartCellNum, overhalEndCellNum, cellNum);
		return workShopFileName;
	}
	@Override
	public String getComletePuTie8_3ByPlanExcel(String planFilePath, String headerFilePath, String completeFilePath,
			int overhaulStartCellNum, int overhalEndCellNum, int cellNum, int herderStartRowNum, int dataStartRowNum) {
		DATA_START_ROW_NUM = dataStartRowNum;
		//用poi读出计划表excel中的数据，并且生成好完成表的数据
		List<Map> datas = writeDataByFilePath8_2(planFilePath,overhaulStartCellNum,overhalEndCellNum,cellNum,herderStartRowNum);
		if(DATA_NUM == 0) {
			//将数据写入excel中，生成完成表。
			String workShopFileName = generateWorkShopFirstExcel(datas,headerFilePath,completeFilePath,
																  overhaulStartCellNum,overhalEndCellNum,cellNum);
			return workShopFileName;
		}
		List<Map> dataMain = datas.subList(0, DATA_NUM);
		List<Map> dataAdditional = datas.subList(DATA_NUM, datas.size());
		//将数据写入excel中，生成完成表。
		String workShopFileName = generateWorkShopFirstExcel(dataMain,headerFilePath,completeFilePath,
															  overhaulStartCellNum,overhalEndCellNum,cellNum);
		generateExcelAdditionalData8_3(dataAdditional, completeFilePath, overhaulStartCellNum, overhalEndCellNum, cellNum);
		return workShopFileName;
	}
}
