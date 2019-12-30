
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.MergeRange;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.SortField;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.ExcelUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthReportFileUtil;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthMongoTestImpl   
 * 类描述: monogodb
 * 创建人：quyy 
 * 创建时间：2017年9月22日 下午5:06:59 
 *    
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Transactional
@Service("tXYearMonthMongoService")
public class TXYearMonthMongoImpl implements TXYearMonthMongoService{

	@Resource(name = "yearMonthMongoService")
	private YearMonthMongoService yearMonthMongoService;
	
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
	 * 维护工作内容的序号
	 */
	private static final String ITEM_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"3";
	/**
	 * 第四列维护工作内容
	 */
	private static final String ITEM_KEY = ExcelUtil.DATA_KEY_PREFIX+"4";
	/**
	 * 第五列适用范围
	 */
	private static final String RANGE_KEY  = ExcelUtil.DATA_KEY_PREFIX+"5";
	/**
	 * 第六列单位
	 */
	private static final String UNIT_KEY = ExcelUtil.DATA_KEY_PREFIX+"6";
	
	/**
	 * 年表代表数字
	 */
	private static final String YEAR_REPORT = "1";
	
	/**
	 * 月表代表数字
	 */
	private static final String MONTH_REPORT = "2";
	
	/**
	 * 通信年月表EXCEL中的数据起始行，从第8行开始，值为7
	 */
	private static final int DATA_START_ROW_NUM = 7;
	
	
	private List<String> getSortedItem(String collectionName, String deptName) {
		Map<String,Object> params = new HashMap<>();
		params.put(DEPT_NAME_KEY, deptName);
		List<Map> datas = yearMonthMongoService.queryforFieldsAndSort(
				collectionName, new String[]{ITEM_IDX_KEY,ITEM_KEY}, new String[]{ITEM_IDX_KEY}, new boolean[]{true},params);
		return generateSortResult(datas,ITEM_IDX_KEY,ITEM_KEY);
	}
	
	/**获取设备名称的顺序 key-设备名 value-排序数字
	 * @param workShopData
	 * @return
	 */
	private List<String> getSortedDept(String collectionName) {
		List<Map> datas = yearMonthMongoService.queryforFieldsAndSort(
				collectionName, new String[]{ALL_IDX_KEY,DEPT_NAME_KEY}, new String[]{ALL_IDX_KEY}, new boolean[]{true},null);
		return generateSortResult(datas,ALL_IDX_KEY,DEPT_NAME_KEY);
	}
	
	private List<String> generateSortResult(List<Map> datas, String idxColName, String valueColName) {
		List<String> deptList = datas.stream()
		//将结果集转换为map，key为名称，value为序号
		.map(m->{
			Map<String,Integer> item = new HashMap<String,Integer>();
			Integer idx;
			try{
				idx = Double.valueOf(m.get(idxColName).toString()).intValue();
			}catch(Exception e){
				idx = 100;
			}
			item.put(m.get(valueColName).toString(), idx);
			return item;
		})
		//进行去重，并且将最大的序号作为名称的序号值
		.reduce((t,u)->{
			for(String ukey : u.keySet()){
				Integer tvalue = t.get(ukey);
				Integer uvalue = u.get(ukey);
				if(tvalue == null || tvalue.intValue() < uvalue.intValue()){
					t.put(ukey, uvalue);
				}
			}
			return t;
		}).get().entrySet().stream()
		//将结果集先转换为SortField集合，进行排序，最后按顺序输出名称列表
		.map(m -> {
			SortField<String> sf = new SortField<String>();
			sf.setField(m.getKey());
			sf.setIdx(m.getValue());
			return sf;
		})
		.sorted((o1,o2)->o1.getIdx() - o2.getIdx())
		.map(SortField :: getField)
		.collect(Collectors.toList());
		return deptList;
	}
	
	/**对分组聚合后的数据进行排序
	 * @param workShopData
	 * @param collectionName 
	 * @param allItemIdx 
	 * @param deptNameIdx 
	 * @return
	 */
	private List<Map> sortWorkShopData(List<Map> workShopData, String collectionName, List<String> deptNameIdx, Map<String, List<String>> allItemIdx) {
		//按照填报的顺序重新定义设备名称的序号并更新mongodb中数据
		IntStream.range(0, deptNameIdx.size()).forEach(i->{
			Map<String,Object> params = new HashMap<>();
			params.put(DEPT_NAME_KEY, deptNameIdx.get(i));
			Map<String,Object> updateSets = new HashMap<>();
			updateSets.put(ALL_IDX_KEY, i + 1);
			yearMonthMongoService.update(collectionName, params, updateSets);
		});
		
		//按照填报的顺序根据设备名称分组重新定义维护项目的序号并更新mongodb中数据
		IntStream.range(0, deptNameIdx.size()).forEach(i->{
			generateOneItemData(allItemIdx.get(deptNameIdx.get(i)),deptNameIdx.get(i),collectionName);
		});
		//按照填报的顺序按照设备名称重新定义设备处所并更新mongodb中数据
		IntStream.range(0, deptNameIdx.size()).forEach(i->{
			generateLocaleData(deptNameIdx.get(i),collectionName);
		});
		List<Map> result = yearMonthMongoService.queryforFieldsAndSort(collectionName, 
				null, new String[]{ALL_IDX_KEY,ITEM_IDX_KEY}, 
				new boolean[]{true,true,false}, null);
		return result;
	}
	
	
	private void generateOneItemData(List<String> itemIdx,String deptName, String collectionName) {
		IntStream.range(0, itemIdx.size()).forEach(i->{
			Map<String,Object> params = new HashMap<>();
			params.put(DEPT_NAME_KEY, deptName);
			params.put(ITEM_KEY, itemIdx.get(i));
			Map<String,Object> updateSets = new HashMap<>();
			updateSets.put(ITEM_IDX_KEY, i + 1);
			yearMonthMongoService.update(collectionName, params, updateSets);
		});
	}

	/**根据统计结果生成对应的excel
	 * @param ww
	 * @param workShopData
	 * @return
	 */
	private String generateWorkShopFirstExcel(List<Map> workShopData,String reportNum,User user) {
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		String excelFile = YearMonthPlanFilePathUtils.getGaoTie(orgId, TXYearMonthMongoImpl.class);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		String fileName = YearMonthReportFileUtil.TXMONTH_TEMPLATE_NAME;
		if(reportNum.equals(YEAR_REPORT)) {
			fileName = YearMonthReportFileUtil.TXYEAR_TEMPLATE_NAME;
		}
		try{
			fis = new FileInputStream(YearMonthPlanFilePathUtils.getGaoTieTpl() + fileName);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			List<MergeRange> deptRange = getAndMergeDeptRange(workShopData,s);
			List<MergeRange> idxRange = getRangeAndMergeByDeptRange(deptRange,0,s);
			List<MergeRange> localeRange = getRangeAndMergeByDeptRange(deptRange,1,s);
			
			HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(wb);
			HSSFCellStyle cellLeftStyle = ExcelUtil.getCellLeftStyle(wb);
			setWorkShopFirstExcelValue(workShopData, s, idxRange, localeRange,deptRange, cellStyle,reportNum,cellLeftStyle,orgName);
			fos = new FileOutputStream(excelFile);
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
		return excelFile;
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
	private void setWorkShopFirstExcelValue(List<Map> workShopData,
			HSSFSheet s, List<MergeRange> idxRange, List<MergeRange> localeRange, List<MergeRange> deptRange,
			HSSFCellStyle cellStyle,String reportNum,HSSFCellStyle cellLeftStyle,String orgName) {
		//填报单位设值
		HSSFRow titleRow = s.getRow(1);
		HSSFCell titleCell = titleRow.getCell(0);//C列
		String value = titleCell.getStringCellValue();
		titleCell.setCellValue(value+"("+orgName+")");
//		//填报日期设值
//		HSSFCell submitDate = submitRow.getCell(35);//对应AJ列
//		submitDate.setCellValue(StringDateToDateUtil.getYHDDateString(new Date()));
		//表格从第6行起的汇总数据设值
		for(int i = 0;i < workShopData.size();i++){
			Map<String,Object> wsa = workShopData.get(i);
			HSSFRow r = s.createRow(i + DATA_START_ROW_NUM);
			insertWorkshopFirstRowData(r,wsa,cellStyle,reportNum,cellLeftStyle);
		}
	}
	
	/**插入车间初步计划单行数据
	 * @param r
	 * @param wsa
	 * @param cellStyle
	 */
	private HSSFRow insertWorkshopFirstRowData(HSSFRow r, Map<String, Object> wsa, HSSFCellStyle cellStyle,String reportNum,HSSFCellStyle cellLeftStyle) {
		int cellMaxIndex = 40;//月表的数据列数
		if(reportNum.equals(YEAR_REPORT)) {
			cellMaxIndex = 22;//年表的数据列数
		}
		HSSFCell idx = r.createCell(0);//序号
		idx.setCellStyle(cellStyle);
		idx.setCellValue(Integer.parseInt(wsa.get(ALL_IDX_KEY).toString()));
		HSSFCell locale = r.createCell(1);//设备所在处所
		locale.setCellStyle(cellStyle);
		locale.setCellValue(generateLocale(wsa.get(LOCALE_KEY).toString()));
		HSSFCell depName = r.createCell(2);//设备名称
		depName.setCellStyle(cellStyle);
		depName.setCellValue(wsa.get(DEPT_NAME_KEY).toString());
		HSSFCell itemIdx = r.createCell(3);//工作内容序号
		itemIdx.setCellStyle(cellStyle);
		itemIdx.setCellValue(wsa.get(ITEM_IDX_KEY).toString());
		HSSFCell workContent = r.createCell(4);//工作内容
		workContent.setCellStyle(cellLeftStyle);
		workContent.setCellValue(wsa.get(ITEM_KEY).toString());
		HSSFCell workItem = r.createCell(5);//适用范围
		workItem.setCellStyle(cellStyle);
		workItem.setCellValue(wsa.get(RANGE_KEY).toString());
		HSSFCell unit = r.createCell(6);//单位
		unit.setCellStyle(cellStyle);
		unit.setCellValue(wsa.get(UNIT_KEY).toString());
		
		for(int i = 7;i < cellMaxIndex;i++){
			HSSFCell cell = r.createCell(i);
			cell.setCellStyle(cellStyle);
			Object value = wsa.get(ExcelUtil.DATA_KEY_PREFIX + i);
			if(value == null
					) {
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
			if(MONTH_REPORT.equals(reportNum)&&i==8) {//通信年表的第10列数据（年计划数量），是按照字符串形式连接在一起的
				cell.setCellValue(generateLocale(value.toString()));
				continue;
			}
			if(YEAR_REPORT.equals(reportNum)&&i==9) {//通信年表的第10列数据（年计划数量），是按照字符串形式连接在一起的
				cell.setCellValue(generateLocale(value.toString()));
				continue;
			}
			if(ExcelUtil.getDoubleValue(value.toString()) == 0){
				cell.setCellValue("");
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
		List<Map> locales = yearMonthMongoService.search(collectionName, new String[]{LOCALE_KEY}, params);
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
	/**
	 * 
	 * writeDataByFilePath  通过报表路径读出数据，并写入mongodb数据库中
	 * @author quyy
	 * @param filePaths
	 * @return
	 */
	private String writeDataByFilePath(List<String> filePaths) {
		String collectionNameBefore = "reportData"+System.currentTimeMillis();
		int dataStartRownum = 7;
		//根据年月表的路径用poi读出数据
		String filePath = YearMonthPlanFilePathUtils.getGaoTie();
		List<Map> datas = ExcelUtil.getWorkshopData(filePaths, 5, dataStartRownum,0,filePath);
		//将数据写入mongodb中
		yearMonthMongoService.batchInsert(datas, collectionNameBefore);
		return collectionNameBefore;
	}
	
	@Override
	public String summaryOfTxYear(List<String> filePaths,User user) {
		//1.将数据读出并写入mongodb中
		String collectionNameBefore =writeDataByFilePath(filePaths);
		//2.用mongodb分组聚合数据
		int[] gourpIndex = new int[]{0,2,3,4,5,6};//需要分组的文档key下标
		int[] addIdx = new int[16];//需要累加的文档key下标
		for(int i = 7;i < 22;i++){//累加日期填报值
			addIdx[i - 7] = i;
		}
		addIdx[15] = 1;//设备处所需要累加,以字符串形式连接在一起
		String collectionNameSummary = "summaryData"+System.currentTimeMillis();
		List<Map> workShopData = yearMonthMongoService.groupbyAdd(collectionNameBefore, gourpIndex, addIdx);
		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);
		//3.将设备名称排序
		List<String> deptNameIdx = getSortedDept(collectionNameSummary);
		Map<String,List<String>> allItemIdx = new HashMap<>();
		//4.对工作内容进行排序
		for(int i = 0;i < deptNameIdx.size();i++){
			List<String> itemIdx = getSortedItem(collectionNameSummary,deptNameIdx.get(i));
			allItemIdx.put(deptNameIdx.get(i), itemIdx);
		}
		yearMonthMongoService.clearCollection(collectionNameSummary);
		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);//将日期填报累加后的值重新插入mongodb
		//5.对分组聚合后的数据进行排序
		workShopData = sortWorkShopData(null,collectionNameSummary,deptNameIdx,allItemIdx);
		//6.根据统计结果生成对应的excel
		String workShopFile = generateWorkShopFirstExcel(workShopData,YEAR_REPORT,user);
		//7.删表
		yearMonthMongoService.clearCollection(collectionNameBefore);
		yearMonthMongoService.clearCollection(collectionNameSummary);
		return workShopFile;
	}
	@Override
	public String summaryOfTxMonth(List<String> filePaths,User user) {
		//1.将数据读出并写入mongodb中
		String collectionNameBefore =writeDataByFilePath(filePaths);
		//2.用mongodb分组聚合数据
		int[] gourpIndex = new int[]{0,2,3,4,5,6};//需要分组的文档key下标
		int[] addIdx = new int[34];//需要累加的文档key下标
		for(int i = 7;i < 40;i++){//累加日期填报值
			addIdx[i - 7] = i;
		}
		addIdx[33] = 1;//设备处所需要累加,以字符串形式连接在一起
		String collectionNameSummary = "summaryData"+System.currentTimeMillis();
		List<Map> workShopData = yearMonthMongoService.groupbyAdd(collectionNameBefore, gourpIndex, addIdx);
		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);
		//3.将设备名称排序
		List<String> deptNameIdx = getSortedDept(collectionNameSummary);
		Map<String,List<String>> allItemIdx = new HashMap<>();
		//4.对工作内容进行排序
		for(int i = 0;i < deptNameIdx.size();i++){
			List<String> itemIdx = getSortedItem(collectionNameSummary,deptNameIdx.get(i));
			allItemIdx.put(deptNameIdx.get(i), itemIdx);
		}
		yearMonthMongoService.clearCollection(collectionNameSummary);
		yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);//将日期填报累加后的值重新插入mongodb
		//5.对分组聚合后的数据进行排序
		workShopData = sortWorkShopData(null,collectionNameSummary,deptNameIdx,allItemIdx);
		//6.根据统计结果生成对应的excel
		String workShopFile = generateWorkShopFirstExcel(workShopData,MONTH_REPORT,user);
		//7.删表
		yearMonthMongoService.clearCollection(collectionNameBefore);
		yearMonthMongoService.clearCollection(collectionNameSummary);
		return workShopFile;
	}
}
