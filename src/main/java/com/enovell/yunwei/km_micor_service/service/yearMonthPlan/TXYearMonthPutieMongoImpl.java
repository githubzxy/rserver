
/**   
 * 文件名：YearMonthMongoTestImpl.java    
 * @author quyy  
 * 日期：2017年9月22日 下午5:06:59      
 *   
 */

package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.MergeRange;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.ExcelUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthReportFileUtil;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthMongoTestImpl   
 * 类描述: 通信年月报表_编制汇总（普铁）
 * 创建人：ghg 
 * 创建时间：2017年9月22日 下午5:06:59 
 *    
 */
@Transactional
@Service("tXYearMonthPutieMongoService")
public class TXYearMonthPutieMongoImpl implements TXYearMonthPutieMongoService{

	@Resource(name = "yearMonthPutieMongoService")
	private YearMonthPutieMongoService yearMonthMongoService;
	
	/**
	 * 第0列对应类别
	 */
	private static final String ALL_TYPE_KEY = ExcelUtil.DATA_KEY_PREFIX+"0";
	/**
	 * 第1列和设备名称对应的序号
	 */
	private static final String ALL_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"1";
	/**
	 * 第2列设备处所
	 */
	private static final String LOCALE_KEY = ExcelUtil.DATA_KEY_PREFIX+"2";
	/**
	 * 第3列设备名称
	 */
	private static final String DEPT_NAME_KEY = ExcelUtil.DATA_KEY_PREFIX+"3";
	/**
	 * 第4列维护工作内容的序号
	 */
//	private static final String ITEM_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"4";
	/**
	 * 第5列维护工作内容
	 */
	private static final String ITEM_KEY = ExcelUtil.DATA_KEY_PREFIX+"4";
	/**
	 * 第6列单位
	 */
	private static final String UNIT_KEY  = ExcelUtil.DATA_KEY_PREFIX+"5";
	/**
	 * 第10列责任单位
	 */
	private static final String RESPONSE_ORG_KEY  = ExcelUtil.DATA_KEY_PREFIX+"9";
	/**
	 * 第22列维修类型
	 */
//	private static final String REPAIR_TYPE_KEY  = ExcelUtil.DATA_KEY_PREFIX+"22";
	
	/**
	 * 年表代表数字
	 */
	@SuppressWarnings("unused")
	private static final String YEAR_REPORT = "1";
	
	/**
	 * 月表代表数字
	 */
	@SuppressWarnings("unused")
	private static final String MONTH_REPORT = "2";
	
	/**
	 * 通信年月表EXCEL中的数据起始行，从第5行开始，值为4
	 */
	private static final int DATA_START_ROW_NUM = 4;
	
	/**
	 * 最后一行（段负责人）
	 */
	private static final String LAST_ROW_CELL0_ = "段负责人";
	
	
//	private List<String> getSortedItem(String collectionName, String deptName) {
//		Map<String,Object> params = new HashMap<>();
//		params.put(DEPT_NAME_KEY, deptName);
//		@SuppressWarnings("rawtypes")
//		List<Map> datas = yearMonthMongoService.queryforFieldsAndSort(
//				collectionName, new String[]{ITEM_IDX_KEY,ITEM_KEY}, new String[]{ITEM_IDX_KEY}, new boolean[]{true},params);
//		return generateSortResult(datas,ITEM_IDX_KEY,ITEM_KEY);
//	}
	
	/**获取设备名称的顺序 key-设备名 value-排序数字
	 * @param workShopData
	 * @return
	 */
//	private List<String> getSortedDept(String collectionName) {
//		@SuppressWarnings("rawtypes")
//		List<Map> datas = yearMonthMongoService.queryforFieldsAndSort(
//				collectionName, new String[]{ALL_IDX_KEY,DEPT_NAME_KEY}, new String[]{ALL_IDX_KEY}, new boolean[]{true},null);
//		return generateSortResult(datas,ALL_IDX_KEY,DEPT_NAME_KEY);
//	}
	
//	private List<String> generateSortResult(@SuppressWarnings("rawtypes") List<Map> datas, String idxColName, String valueColName) {
//		List<String> deptList = datas.stream()
//		//将结果集转换为map，key为名称，value为序号
//		.map(m->{
//			Map<String,Integer> item = new HashMap<String,Integer>();
//			Integer idx;
//			try{
//				idx = Double.valueOf(m.get(idxColName).toString()).intValue();
//			}catch(Exception e){
//				idx = 100;
//			}
//			item.put(m.get(valueColName).toString(), idx);
//			return item;
//		})
//		//进行去重，并且将最大的序号作为名称的序号值
//		.reduce((t,u)->{
//			for(String ukey : u.keySet()){
//				Integer tvalue = t.get(ukey);
//				Integer uvalue = u.get(ukey);
//				if(tvalue == null || tvalue.intValue() < uvalue.intValue()){
//					t.put(ukey, uvalue);
//				}
//			}
//			return t;
//		}).get().entrySet().stream()
//		//将结果集先转换为SortField集合，进行排序，最后按顺序输出名称列表
//		.map(m -> {
//			SortField<String> sf = new SortField<String>();
//			sf.setField(m.getKey());
//			sf.setIdx(m.getValue());
//			return sf;
//		})
//		.sorted((o1,o2)->o1.getIdx() - o2.getIdx())
//		.map(SortField :: getField)
//		.collect(Collectors.toList());
//		return deptList;
//	}
	
	/**对分组聚合后的数据进行排序
	 * @param workShopData
	 * @param collectionName 
	 * @param allItemIdx 
	 * @param deptNameIdx 
	 * @return
	 */
//	@SuppressWarnings("rawtypes")
//	private List<Map> sortWorkShopData(List<Map> workShopData, String collectionName, List<String> deptNameIdx, Map<String, List<String>> allItemIdx) {
//		//按照填报的顺序重新定义设备名称的序号并更新mongodb中数据
//		IntStream.range(0, deptNameIdx.size()).forEach(i->{
//			Map<String,Object> params = new HashMap<>();
//			params.put(DEPT_NAME_KEY, deptNameIdx.get(i));
//			Map<String,Object> updateSets = new HashMap<>();
//			updateSets.put(ALL_IDX_KEY, i + 1);
//			yearMonthMongoService.update(collectionName, params, updateSets);
//		});
//		
//		//按照填报的顺序根据设备名称分组重新定义维护项目的序号并更新mongodb中数据
//		IntStream.range(0, deptNameIdx.size()).forEach(i->{
//			generateOneItemData(allItemIdx.get(deptNameIdx.get(i)),deptNameIdx.get(i),collectionName);
//		});
//		//按照填报的顺序按照设备名称重新定义设备处所并更新mongodb中数据
//		IntStream.range(0, deptNameIdx.size()).forEach(i->{
//			generateLocaleData(deptNameIdx.get(i),collectionName);
//		});
//		List<Map> result = yearMonthMongoService.queryforFieldsAndSort(collectionName, 
//				null, new String[]{ALL_IDX_KEY,ITEM_IDX_KEY}, 
//				new boolean[]{true,true,false}, null);
//		return result;
//	}
	
	
//	private void generateOneItemData(List<String> itemIdx,String deptName, String collectionName) {
//		IntStream.range(0, itemIdx.size()).forEach(i->{
//			Map<String,Object> params = new HashMap<>();
//			params.put(DEPT_NAME_KEY, deptName);
//			params.put(ITEM_KEY, itemIdx.get(i));
//			Map<String,Object> updateSets = new HashMap<>();
//			updateSets.put(ITEM_IDX_KEY, i + 1);
//			yearMonthMongoService.update(collectionName, params, updateSets);
//		});
//	}

	/**根据统计结果生成对应的excel
	 * @param workShopData
	 * @param user 登录用户
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String generateWorkShopFirstExcel( List<Map> workShopData,User user) {
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		String workShopFile = YearMonthPlanFilePathUtils.getPuTie(orgId, TXYearMonthPutieMongoImpl.class);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(YearMonthPlanFilePathUtils.getPuTieTpl() + YearMonthReportFileUtil.KMMS_YEAR_TABLE_TEMP);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			getAndMergeDeptRange(workShopData,s);
			HSSFCellStyle cellStyle = ExcelUtil.getPuTieCellStyle(wb);
			HSSFCellStyle cellLeftStyle = ExcelUtil.getPuTieCellLeftStyle(wb);
			setWorkShopFirstExcelValue(workShopData, s, cellStyle,cellLeftStyle,orgName);
			fos = new FileOutputStream(workShopFile);
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
		return workShopFile;
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
	private void setWorkShopFirstExcelValue(@SuppressWarnings("rawtypes") List<Map> workShopData, HSSFSheet s,
			HSSFCellStyle cellStyle, HSSFCellStyle cellLeftStyle, String orgName) {
		// 填报单位设值
		HSSFRow titleRow = s.getRow(0);
		HSSFCell titleCell = titleRow.getCell(0);
		String value = titleCell.getStringCellValue();
		titleCell.setCellValue(value + "(" + orgName + ")");
		
		int resultNum = workShopData.size();
		int rows = resultNum - 1;
		int lastRowNum = s.getLastRowNum();
		// 将第5行以下的全部下移的行数
		int shiftDownRow = lastRowNum + rows;
		s.shiftRows(5, 6, shiftDownRow, true, false);
		HSSFRow source = s.getRow(4);// 从row5开始复制
		HSSFRow target = s.createRow(4);// 从row5开始粘贴
		copyRow(source, target);
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow = s.getRow(4 + i);// 从row5+i开始复制
			HSSFRow targetRow = s.createRow(5 + i);// 从row6+i开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(5 + lastRowNum + rows, 6 + lastRowNum + rows, shiftUpRow, true, false);
		
		// 表格从第5行起的汇总数据设值
		for (int i = 0; i < workShopData.size(); i++) {
			@SuppressWarnings("unchecked")
			Map<String, Object> wsa = workShopData.get(i);
			HSSFRow r = s.getRow(i + DATA_START_ROW_NUM);
			insertWorkshopFirstRowData(r, wsa, cellStyle, cellLeftStyle);
		}
	}
	
	public static void copyRow(final HSSFRow sourceRow,final HSSFRow targetRow) {
//		targetRow.setHeight(sourceRow.getHeight());//不设置行高（为了在单元格内填入数据时能根据内容多少自动调整行高）
		IntStream.range(0, sourceRow.getPhysicalNumberOfCells()).forEach(i -> {
			HSSFCell sourceCell = sourceRow.getCell(i);
			if(sourceCell == null) return;
			HSSFCell targetCell = targetRow.createCell(i);
			copyCellStyleAndFont(sourceCell,targetCell);
		});
		copyMergeArea(sourceRow,targetRow);
	}
	
	public static void copyCellStyleAndFont(HSSFCell sourceCell,HSSFCell targetCell) {
		Map<String, Object> styleMap = getCopyStyle(sourceCell.getCellStyle());
		CellUtil.setCellStyleProperties(targetCell, styleMap);
		CellUtil.setFont(targetCell, sourceCell.getSheet().getWorkbook().getFontAt(sourceCell.getCellStyle().getFontIndex()));
	}
	
	private static void copyMergeArea(HSSFRow sourceRow, HSSFRow targetRow) {
		List<CellRangeAddress> sourceMerge = hasMergeArea(sourceRow);
		if(CollectionUtils.isEmpty(sourceMerge)) return;
		HSSFSheet sheet = targetRow.getSheet();
		sourceMerge.stream().forEach(s -> {
			sheet.addMergedRegion(new CellRangeAddress(
					targetRow.getRowNum(), 
					targetRow.getRowNum(), 
					s.getFirstColumn(), 
					s.getLastColumn()));
		});
	}
	
	private static List<CellRangeAddress> hasMergeArea(HSSFRow sourceRow) {
		return sourceRow.getSheet().getMergedRegions().stream()
		.filter(s -> 
			s.getFirstRow() == sourceRow.getRowNum() &&
			s.getLastRow() == sourceRow.getRowNum()
		).collect(Collectors.toList());
	}
	
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
        styleMap.put(CellUtil.WRAP_TEXT, true);
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
	
	/**插入车间初步计划单行数据
	 * @param r
	 * @param wsa
	 * @param cellStyle
	 */
	private HSSFRow insertWorkshopFirstRowData(HSSFRow r, Map<String, Object> wsa, HSSFCellStyle cellStyle,HSSFCellStyle cellLeftStyle) {
		int	cellMaxIndex = 22;//年表的数据列数
		HSSFCell type = r.getCell(0);//类别
//		type.setCellStyle(cellStyle);
		type.setCellValue(wsa.get(ALL_TYPE_KEY).toString());
		
		HSSFCell idx = r.getCell(1);//序号
//		idx.setCellStyle(cellStyle);
		idx.setCellValue(Integer.parseInt(wsa.get(ALL_IDX_KEY).toString()));
		
		HSSFCell locale = r.getCell(2);//设备所在处所
//		locale.setCellStyle(cellStyle);
		locale.setCellValue(generateLocale(wsa.get(LOCALE_KEY).toString()));
		
		HSSFCell depName = r.getCell(3);//设备名称
//		depName.setCellStyle(cellStyle);
		depName.setCellValue(wsa.get(DEPT_NAME_KEY).toString());
		
//		HSSFCell itemIdx = r.getCell(4);//工作内容序号
//		itemIdx.setCellStyle(cellStyle);
//		itemIdx.setCellValue(wsa.get(ITEM_IDX_KEY).toString());
		
		HSSFCell workContent = r.getCell(4);//工作内容
//		workContent.setCellStyle(cellLeftStyle);
		workContent.setCellValue(wsa.get(ITEM_KEY).toString());
		
		HSSFCell unit = r.getCell(5);//单位
//		unit.setCellStyle(cellStyle);
		unit.setCellValue(wsa.get(UNIT_KEY).toString());
		
		HSSFCell responseOrg = r.getCell(9);//责任单位
//		responseOrg.setCellStyle(cellStyle);
		responseOrg.setCellValue(generateLocale(wsa.get(RESPONSE_ORG_KEY).toString()));
		
//		HSSFCell repairType = r.getCell(22);//维修类型
//		repairType.setCellStyle(cellStyle);
//		repairType.setCellValue(generateLocale(wsa.get(REPAIR_TYPE_KEY).toString()));
		
		//通信年表的第7,8,9列(从0开始)数据（总数量，年计划数量，每年次数），是按照字符串形式连接在一起的
		for (int i = 6; i < 9; i++) {
			HSSFCell cell = r.getCell(i);
//			cell.setCellStyle(cellStyle);
			Object value = wsa.get(ExcelUtil.DATA_KEY_PREFIX + i);
			if(ExcelUtil.getDoubleValue(value.toString()) != 0){
				cell.setCellValue(ExcelUtil.getDoubleValue(value.toString()));
				continue;
			}
			if(value.toString().equals("0.0")) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) == 0){
				cell.setCellValue(generateLocale(value.toString()));
				continue;
			}
			cell.setCellValue("");
		}
		//向excel中填入各月份的汇总数据
		for (int i = 10; i < cellMaxIndex; i++) {
			HSSFCell cell = r.getCell(i);
//			cell.setCellStyle(cellStyle);
			Object value = wsa.get(ExcelUtil.DATA_KEY_PREFIX + i);
			if(value == null) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) != 0){
				cell.setCellValue(ExcelUtil.getDoubleValue(value.toString()));
				continue;
			}
			if(value.toString().equals("0.0")) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) == 0){
				cell.setCellValue("");
				continue;
			}
		}
		return r;
	}
	/**生成设备处所合并单元格的值，需要将设备名称对应的所有设备处所去重后加在一起
	 * @param localeStr
	 * @return
	 */
	private String generateLocale(String localeStr) {
		String[] locales = localeStr.split(ExcelUtil.STRING_CONCAT_SYMBOL);
		TreeSet<String> localeSet = new TreeSet<String>();//使用treeset去除重复项并保持原有顺序
		CollectionUtils.addAll(localeSet, locales);
		String locale = "";
		if(!localeSet.isEmpty()){
			for(String l : localeSet){
				l="BLANK".equals(l)||"0".equals(l)?"":l;
				locale += l + ExcelUtil.NEWLINE_SYMBOL;
			}
		}
		return locale.substring(0,locale.length()-2);
	}
	/**从相同设备名称的数据中取出最长的设备处所并更新对应所有相同设备名称的设备处所数据
	 * 为什么要这么做？因为设备处所字段在excel文件中是一个合并单元格，而合并单元格中仅展示第一个单元格的值
	 * 而我们的统计数据是按设备名称、维护项目、计划完成进行分组的，如果正好第一个单元格对应的维护项目仅在
	 * 一个工区中存在，那么我们归并出来的数据必然只有这一个工区的设备处所，和实际情况不符。因为其它维护项目
	 * 可能还存在其它设备处所，所以循环所有维护项目的设备处所，以最长的一个作为设备名称对应的设备处所
	 * @param string
	 * @param collectionName
	 */
	private void generateLocaleData(String deptName, String collectionName) {
		Map<String,Object> params = new HashMap<>();
		params.put(DEPT_NAME_KEY, deptName);
		@SuppressWarnings("rawtypes")
		List<Map> locales = yearMonthMongoService.search(collectionName, new String[]{LOCALE_KEY}, params);
		@SuppressWarnings("rawtypes")
		Map maxLocale = locales.stream().reduce((m1,m2)->{
			String locale1 = m1.get(LOCALE_KEY).toString(),locale2 = m2.get(LOCALE_KEY).toString();
			if(locale1.length() > locale2.length()){
				return m1;
			}else{
				return m2;
			}
		}).get();
		
		Map<String, Object> updateSets = new HashMap<>();
		updateSets.put(LOCALE_KEY, maxLocale.get(LOCALE_KEY));
		yearMonthMongoService.update(collectionName, params, updateSets );
	}
	/**根据设备名的合并单元格范围生成序号和设备处所的合并单元格范围并进行合并
	 * @param deptRange
	 * @param colNum
	 * @param s 
	 * @return
	 */
	private List<MergeRange> getRangeAndMergeByDeptRange(List<MergeRange> deptRange, int colNum, HSSFSheet s) {
		List<MergeRange> result = new ArrayList<MergeRange>();
		for(int i = 0;i < deptRange.size();i++){
			MergeRange idx = new MergeRange();
			MergeRange dept = deptRange.get(i);
			idx.setFirstCol(colNum);
			idx.setLastCol(colNum);
			idx.setFirstRow(dept.getFirstRow());
			idx.setLastRow(dept.getLastRow());
			result.add(idx);
		}
		ExcelUtil.mergeCells(s, result);
		return result;
	}
	/**根据统计数据获取设备名称的合并单元格范围并进行合并
	 * @param workShopData
	 * @return
	 */
	private List<MergeRange> getAndMergeDeptRange(@SuppressWarnings("rawtypes") List<Map> workShopData,HSSFSheet s) {
		List<MergeRange> deptRange = new ArrayList<MergeRange>();
		String typeName = "";//类别
		MergeRange type = new MergeRange();
		type.setFirstCol(0);
		type.setLastCol(0);
		for(int i = 0;i < workShopData.size();i++){
			@SuppressWarnings("unchecked")
			Map<String,Object> wa = workShopData.get(i);
			String typeTemp = wa.get(ALL_TYPE_KEY).toString();
			if(StringUtils.isBlank(typeName)){//类别第一行必定有值，将赋给比较对象后，直接进入下一行
				type.setFirstRow(i + DATA_START_ROW_NUM);//类别从第7行开始，所以所有的行数皆等于序号加6
				typeName = typeTemp;
				continue;
			}
			if(!typeName.equals(typeTemp)){//本行类别和上一行不相同时
				int endRow = i + DATA_START_ROW_NUM - 1;//说明上一行为上个类别的结束行，行数等于序号加4再减1
				type.setLastRow(endRow);
				if(endRow != type.getFirstRow())//如果相同类别只占一行，则不进行合并
					deptRange.add(type);
				type = new MergeRange();
				type.setFirstCol(0);
				type.setLastCol(0);
				type.setFirstRow(i + DATA_START_ROW_NUM);
				typeName = typeTemp;
			}
			if(i == workShopData.size() - 1){//循环结束时，最后一行即最后一个类别的最后行数
				type.setLastRow(i + DATA_START_ROW_NUM);
				if(type.getFirstRow() != type.getLastRow())//如果相同类别只占一行，则不进行合并
					deptRange.add(type);
			}
		}
		ExcelUtil.mergeCells(s, deptRange);
		return deptRange;
	}
	/**
	 * 
	 * writeDataByFilePath  通过报表路径读出数据，并写入mongodb数据库中
	 * @author quyy
	 * @param filePaths
	 * @return
	 */
	private String writeDataByFilePath(List<String> filePaths) {
		String collectionNameBefore = "reportData"+System.currentTimeMillis();
		//从第5行(值为4)开始
		int dataStartRownum = 6;
		String filePath = YearMonthPlanFilePathUtils.getPuTie();
		//根据年月表的路径用poi读出数据
		@SuppressWarnings("rawtypes")
		List<Map> datas = ExcelUtil.getWorkshopData(filePaths, 7, dataStartRownum,0,filePath);
		//将数据写入mongodb中
		yearMonthMongoService.batchInsert(datas, collectionNameBefore);
		return collectionNameBefore;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String summaryOfTxYear(List<String> filePaths,User user) {
		//1.将数据读出并写入mongodb中
		String collectionNameBefore =writeDataByFilePath(filePaths);
		//2.用mongodb分组聚合数据
		int[] gourpIndex = new int[]{0,3,4,5,8};//需要分组的文档key下标
		int[] addIdx = new int[16];//需要累加的文档key下标
		for(int i = 10;i < 22;i++){//累加日期填报值
			addIdx[i - 10] = i;
		}
		addIdx[12] = 2;//设备处所需要累加,以字符串形式连接在一起
		addIdx[13] = 6;//总数量
		addIdx[14] = 7;//年计划数量
//		addIdx[15] = 8;//每年次数
		addIdx[15] = 9;//责任单位
		String collectionNameSummary = "summaryData"+System.currentTimeMillis();
		List<Map> workShopData = yearMonthMongoService.groupbyAdd(collectionNameBefore, gourpIndex, addIdx);
		// TODO 最后一行段未进行删除
		workShopData.remove(workShopData.size()-1);
		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);
		//3.将设备名称排序
//		List<String> deptNameIdx = getSortedDept(collectionNameSummary);
//		Map<String,List<String>> allItemIdx = new HashMap<>();
		//4.对工作内容进行排序
//		for(int i = 0;i < deptNameIdx.size();i++){
//			List<String> itemIdx = getSortedItem(collectionNameSummary,deptNameIdx.get(i));
//			allItemIdx.put(deptNameIdx.get(i), itemIdx);
//		}
//		yearMonthMongoService.clearCollection(collectionNameSummary);
//		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);//将日期填报累加后的值重新插入mongodb
		//5.对分组聚合后的数据进行排序
//		workShopData = sortWorkShopData(null,collectionNameSummary,deptNameIdx,allItemIdx);
		int placeIdx = 1;
		String cell0Temp = null;
		for(int i = 0;i < workShopData.size();i++) {
			Map oneRow = workShopData.get(i);
			if(cell0Temp == null || !cell0Temp.equals(oneRow.get("cell0").toString())) {//第一行或者不同类别，序号从1开始
				cell0Temp = oneRow.get("cell0").toString();
				placeIdx = 1;
			}else{//同一类别，序号累加
				placeIdx++;
			}
			oneRow.put("cell1", String.valueOf(placeIdx));
		}
		//6.根据统计结果生成对应的excel
		String workShopFileName = generateWorkShopFirstExcel(workShopData,user);
		//7.删表
		yearMonthMongoService.clearCollection(collectionNameBefore);
		yearMonthMongoService.clearCollection(collectionNameSummary);
		return workShopFileName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String summaryOfTxMonth(List<String> filePaths, User user) {
		//1.将数据读出并写入mongodb中
		String collectionNameBefore =writeDataByFilePath(filePaths);
		//2.用mongodb分组聚合数据
		int[] gourpIndex = new int[]{0,3,4,5,7};//需要分组的文档key下标
		int[] addIdx = new int[33];//需要累加的文档key下标
		for(int i = 8;i < 39;i++){//累加日期填报值
			addIdx[i - 8] = i;
		}
		addIdx[31] = 2;//设备处所需要累加,以字符串形式连接在一起
		addIdx[32] = 6;//数量
//		addIdx[33] = 7;//每月次数
		String collectionNameSummary = "summaryData"+System.currentTimeMillis();
		List<Map> workShopData = yearMonthMongoService.groupbyAdd(collectionNameBefore, gourpIndex, addIdx);
		// TODO 最后一行段未进行删除
//		workShopData.remove(workShopData.size()-1);
		// 过滤“段负责人”行
		workShopData = workShopData.stream().filter(e -> {
			if (String.valueOf(e.get("cell0")).contains(LAST_ROW_CELL0_)) {
				return false;
			}
			return true;
		}).collect(Collectors.toList());
		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);
		int placeIdx = 1;
		String cell0Temp = null;
		for(int i = 0;i < workShopData.size();i++) {
			Map oneRow = workShopData.get(i);
			if(cell0Temp == null || !cell0Temp.equals(oneRow.get("cell0").toString())) {//第一行或者不同类别，序号从1开始
				cell0Temp = oneRow.get("cell0").toString();
				placeIdx = 1;
			}else{//同一类别，序号累加
				placeIdx++;
			}
			oneRow.put("cell1", String.valueOf(placeIdx));
		}
		//6.根据统计结果生成对应的excel
		String workShopFileName = generateWorkShopFirstExcelMonth(workShopData,user);
		//7.删表
		yearMonthMongoService.clearCollection(collectionNameBefore);
		yearMonthMongoService.clearCollection(collectionNameSummary);
		return workShopFileName;
	}
	
	/**根据统计结果生成对应的excel
	 * @param workShopData
	 * @param user 登录用户
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String generateWorkShopFirstExcelMonth(List<Map> workShopData,User user) {
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		String workShopFile = YearMonthPlanFilePathUtils.getPuTie(orgId, TXYearMonthPutieMongoImpl.class);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(YearMonthPlanFilePathUtils.getPuTieTpl() + YearMonthReportFileUtil.KMMS_MONTH_TABLE_TEMP);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			getAndMergeDeptRange(workShopData,s);
			setWorkShopFirstExcelValueMonth(workShopData, s, orgName);
			fos = new FileOutputStream(workShopFile);
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
		return workShopFile;
	}
	
	/**设置车间统计初步计划excel的值
	 * @param workShopData
	 * @param s
	 * @param orgName 车间组织结构名
	 */
	private void setWorkShopFirstExcelValueMonth(@SuppressWarnings("rawtypes") List<Map> workShopData, HSSFSheet s,
			String orgName) {
		// 填报单位设值
		HSSFRow titleRow = s.getRow(0);
		HSSFCell titleCell = titleRow.getCell(0);// C列
		String value = titleCell.getStringCellValue();
		titleCell.setCellValue(value + "(" + orgName + ")");
		
		int resultNum = workShopData.size();
		int rows = resultNum - 1;
		int lastRowNum = s.getLastRowNum();
		// 将第5行以下的全部下移的行数
		int shiftDownRow = lastRowNum + rows;
		s.shiftRows(5, 6, shiftDownRow, true, false);
		HSSFRow source = s.getRow(4);// 从row5开始复制
		HSSFRow target = s.createRow(4);// 从row5开始粘贴
		copyRow(source, target);
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow = s.getRow(4 + i);// 从row5+i开始复制
			HSSFRow targetRow = s.createRow(5 + i);// 从row6+i开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(5 + lastRowNum + rows, 6 + lastRowNum + rows, shiftUpRow, true, false);
		
		// 表格从第5行起的汇总数据设值
		for (int i = 0; i < workShopData.size(); i++) {
			@SuppressWarnings("unchecked")
			Map<String, Object> wsa = workShopData.get(i);
			HSSFRow r = s.getRow(i + DATA_START_ROW_NUM);
			insertWorkshopFirstRowDataMonth(r, wsa);
		}
	}
	
	/**插入车间初步计划单行数据
	 * @param r
	 * @param wsa
	 */
	private void insertWorkshopFirstRowDataMonth(HSSFRow r, Map<String, Object> wsa) {
		int	cellMaxIndex = 39;//年表的数据列数
		HSSFCell type = r.getCell(0);//类别
		type.setCellValue(wsa.get(ALL_TYPE_KEY).toString());
		
		HSSFCell idx = r.getCell(1);//序号
		idx.setCellValue(Integer.parseInt(wsa.get(ALL_IDX_KEY).toString()));
		
		HSSFCell locale = r.getCell(2);//设备所在处所
		locale.setCellValue(generateLocale(wsa.get(LOCALE_KEY).toString()));
		
		HSSFCell depName = r.getCell(3);//设备名称
		depName.setCellValue(wsa.get(DEPT_NAME_KEY).toString());
		
		HSSFCell workContent = r.getCell(4);//工作内容
		workContent.setCellValue(wsa.get(ITEM_KEY).toString());
		
		HSSFCell unit = r.getCell(5);//单位
		unit.setCellValue(wsa.get(UNIT_KEY).toString());
		
		//通信年表的第7,8列(从0开始)数据（数量，每月次数），是按照字符串形式连接在一起的
		for (int i = 6; i < 8; i++) {
			HSSFCell cell = r.getCell(i);
			Object value = wsa.get(ExcelUtil.DATA_KEY_PREFIX + i);
			if(ExcelUtil.getDoubleValue(value.toString()) != 0){
				cell.setCellValue(ExcelUtil.getDoubleValue(value.toString()));
				continue;
			}
			if(value.toString().equals("0.0")) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) == 0){
				cell.setCellValue(generateLocale(value.toString()));
				continue;
			}
			cell.setCellValue("");
		}
		//向excel中填入各月份的汇总数据
		for (int i = 8; i < cellMaxIndex; i++) {
			HSSFCell cell = r.getCell(i);
			Object value = wsa.get(ExcelUtil.DATA_KEY_PREFIX + i);
			if(value == null) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) != 0){
				cell.setCellValue(ExcelUtil.getDoubleValue(value.toString()));
				continue;
			}
			if(value.toString().equals("0.0")) {
				cell.setCellValue("");
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) == 0){
				cell.setCellValue("");
				continue;
			}
		}
	}
	
}
