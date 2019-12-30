
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
import java.util.Collections;
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
import org.apache.poi.ss.util.CellRangeAddress;
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
 * 类名称：TXYearMonthMongoExecuteImpl   
 * 类描述: 通信年月报表_执行汇总（普铁）
 * 创建人：chengshuang 
 * 创建时间：2017年9月22日 下午5:06:59 
 *    
 */
@Transactional
@Service("tXYearPutieMongoExecuteService")
public class TXYearMonthMongoExecuteImpl implements TXYearPutieMongoExecuteService{

	@Resource(name = "yearMonthMongoService")
	private YearMonthMongoService yearMonthMongoService;
	
	/**
	 * 第0列和设备名称对应的序号
	 */
	private static final String ALL_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"0";
	/**
	 * 第1列设备处所
	 */
	private static final String LOCALE_KEY = ExcelUtil.DATA_KEY_PREFIX+"1";
	/**
	 * 第2列设备名称
	 */
	private static final String DEPT_NAME_KEY = ExcelUtil.DATA_KEY_PREFIX+"2";
	/**
	 * 第3列维护工作内容的序号
	 */
	private static final String ITEM_IDX_KEY = ExcelUtil.DATA_KEY_PREFIX+"3";
	/**
	 * 第4列维护工作内容
	 */
	private static final String ITEM_KEY = ExcelUtil.DATA_KEY_PREFIX+"3";
	/**
	 * 第5列单位
	 */
	private static final String UNIT_KEY  = ExcelUtil.DATA_KEY_PREFIX+"4";
	/**
	 * 第6列计划数量
	 */
	private static final String PLAN_SUM  = ExcelUtil.DATA_KEY_PREFIX+"5";
	/**
	 * 第7列计划完成/年表
	 */
	private static final String PLAN_COM_KEY = ExcelUtil.DATA_KEY_PREFIX+"6";
	/**
	 * 完成表EXCEL中的数据起始行，从第6行开始，值为5
	 */
	private static final int DATA_START_ROW_NUM = 5;
	
	
	private List<String> getSortedItem(String collectionName, String deptName) {
		Map<String,Object> params = new HashMap<>();
		params.put(DEPT_NAME_KEY, deptName);
		@SuppressWarnings("rawtypes")
		List<Map> datas = yearMonthMongoService.queryforFieldsAndSort(
				collectionName, new String[]{ITEM_IDX_KEY,ITEM_KEY}, new String[]{ITEM_IDX_KEY}, new boolean[]{true},params);
		return generateSortResult(datas,ITEM_IDX_KEY,ITEM_KEY);
	}
	
	/**获取设备名称的顺序 key-设备名 value-排序数字
	 * @param workShopData
	 * @return
	 */
	private List<String> getSortedDept(String collectionName) {
		@SuppressWarnings("rawtypes")
		List<Map> datas = yearMonthMongoService.queryforFieldsAndSort(
				collectionName, new String[]{ALL_IDX_KEY,DEPT_NAME_KEY}, new String[]{ALL_IDX_KEY}, new boolean[]{true},null);
		return generateSortResult(datas,ALL_IDX_KEY,DEPT_NAME_KEY);
	}
	
	private List<String> generateSortResult(@SuppressWarnings("rawtypes") List<Map> datas, String idxColName, String valueColName) {
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
	@SuppressWarnings("rawtypes")
	private List<Map> sortWorkShopData(List<Map> workShopData, String collectionName, List<String> deptNameIdx, Map<String, List<String>> allItemIdx,String planComplete) {
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
				null, new String[]{ALL_IDX_KEY,ITEM_IDX_KEY,planComplete}, 
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
	 * @param workShopDataList 12个sheet的汇总数据
	 * @param user 登录用户
	 * @param year 年份
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String generateWorkShopFirstExcel(List<List<Map>> workShopDataList,User user, String year) {
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		String workShopFile = YearMonthPlanFilePathUtils.getPuTie(orgId, TXYearMonthMongoExecuteImpl.class);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try {
			fis = new FileInputStream(YearMonthPlanFilePathUtils.getPuTieTpl() + YearMonthReportFileUtil.KMMS_EXECUTE_TABLE_TEMP);
			wb = new HSSFWorkbook(fis);
			HSSFCellStyle cellStyle = ExcelUtil.getPuTieCellStyle(wb);
			// 循环12个sheet页的数据，workShopDataList的固定大小为12
			for (int j = 0; j < workShopDataList.size(); j++) {
				// 拿到对应sheet页
				HSSFSheet s = wb.getSheetAt(j);
				List<Map> workShopData = workShopDataList.get(j);
				// 合并单元格（A,B,C,D,E,F列）
				for (int i = 0; i < workShopData.size(); i++) {
					s.addMergedRegion(new CellRangeAddress(7 + i * 2, 8 + i * 2, 0, 0));
					s.addMergedRegion(new CellRangeAddress(7 + i * 2, 8 + i * 2, 1, 1));
					s.addMergedRegion(new CellRangeAddress(7 + i * 2, 8 + i * 2, 2, 2));
					s.addMergedRegion(new CellRangeAddress(7 + i * 2, 8 + i * 2, 3, 3));
					s.addMergedRegion(new CellRangeAddress(7 + i * 2, 8 + i * 2, 4, 4));
					s.addMergedRegion(new CellRangeAddress(7 + i * 2, 8 + i * 2, 5, 5));
				}
				setWorkShopFirstExcelValue(workShopData, s, cellStyle, orgName, year);
			}
			fos = new FileOutputStream(workShopFile);
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workShopFile;
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
	
	/**
	 * 
	 * setWorkShopFirstExcelValue 设置车间统计初步计划excel的值
	 * 
	 * @param workShopData 汇总数据
	 * @param s
	 * @param cellStyle
	 * @param orgName 车间组织机构名
	 * @param year 年份
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setWorkShopFirstExcelValue(List<Map> workShopData, HSSFSheet s, HSSFCellStyle cellStyle,
			String orgName, String year) {
		// 组织机构设值（将组织机构名称加在标题后）
		HSSFRow titleRow = s.getRow(1);
		HSSFCell titleCell = titleRow.getCell(0);
		String value = titleCell.getStringCellValue();
		titleCell.setCellValue(value + "(" + orgName + ")");
		// 年份
		HSSFRow row2 = s.getRow(2);
		HSSFCell cell34 = row2.getCell(34);
		cell34.setCellValue(Integer.valueOf(year));
		// 表格从第6行起(值为5)的汇总数据设值
		for (int i = 0; i < workShopData.size(); i++) {
			Map<String, Object> wsa = workShopData.get(i);
			HSSFRow r = s.createRow(i + DATA_START_ROW_NUM);
			insertWorkshopFirstRowData(r, wsa, cellStyle);
		}
	}
	
	/**插入车间初步计划单行数据
	 * @param r
	 * @param wsa
	 * @param cellStyle
	 */
	private void insertWorkshopFirstRowData(HSSFRow r, Map<String, Object> wsa, HSSFCellStyle cellStyle) {
		int	cellMaxIndex = 39;//完成表的总数据列数（从0开始算）
		HSSFCell idx = r.createCell(0);//序号
		idx.setCellStyle(cellStyle);
		idx.setCellValue(Integer.valueOf(wsa.get(ALL_IDX_KEY).toString()));
		
		HSSFCell locale = r.createCell(1);//设备所在处所
		locale.setCellStyle(cellStyle);
		locale.setCellValue(generateLocale(wsa.get(LOCALE_KEY).toString()));
		
		HSSFCell depName = r.createCell(2);//设备名称
		depName.setCellStyle(cellStyle);
		depName.setCellValue(wsa.get(DEPT_NAME_KEY).toString());
		
		HSSFCell workContent = r.createCell(3);//工作项目
		workContent.setCellStyle(cellStyle);
		workContent.setCellValue(wsa.get(ITEM_KEY).toString());
		
		HSSFCell unit = r.createCell(4);//单位
		unit.setCellStyle(cellStyle);
		unit.setCellValue(wsa.get(UNIT_KEY).toString());
		
		HSSFCell planSum = r.createCell(5);//计划数量
		planSum.setCellStyle(cellStyle);
		planSum.setCellValue(ExcelUtil.getDoubleValue(wsa.get(PLAN_SUM).toString()));
		
		HSSFCell planCom = r.createCell(6);//计划完成
		planCom.setCellStyle(cellStyle);
		planCom.setCellValue(String.valueOf(wsa.get(PLAN_COM_KEY)).substring(0, 2));
		//各月份的汇总数据（7~39）
		for (int i = 7; i < cellMaxIndex; i++) {
			HSSFCell cell = r.createCell(i);
			cell.setCellStyle(cellStyle);
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
				cell.setCellValue(generateLocale(value.toString()));
				continue;
			}
			cell.setCellValue("");
		}
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
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("unchecked")
	private List<MergeRange> getAndMergeDeptRange(@SuppressWarnings("rawtypes") List<Map> workShopData,HSSFSheet s) {
		List<MergeRange> deptRange = new ArrayList<MergeRange>();
		String deptName = "";
		MergeRange dept = new MergeRange();
		dept.setFirstCol(2);//设备名称只占第3列，所以没有列合并，开始列和结束列都为2
		dept.setLastCol(2);
		for(int i = 0;i < workShopData.size();i++){
			Map<String,Object> wa = workShopData.get(i);
			String deptTemp = wa.get(DEPT_NAME_KEY).toString();
			if(StringUtils.isBlank(deptName)){//设备名称第一行必定有值，将赋给比较对象后，直接进入下一行
				dept.setFirstRow(i + DATA_START_ROW_NUM);//设备名称从第5行（值为4）开始，所以所有的行数皆等于序号加4
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
//	@SuppressWarnings("rawtypes")
//	private String writeDataByFilePath(List<String> filePaths) {
//		String collectionNameBefore = "reportData" + System.currentTimeMillis();
//		// 读取第一个sheet页（值为0）
//		int sheetNum = 0;
//		String filePath = YearMonthPlanFilePathUtils.getPuTie();
//		// 根据完成表的路径用poi读出数据
//		List<Map> datas = ExcelUtil.getWorkshopData(filePaths, DATA_START_ROW_NUM, DATA_START_ROW_NUM, sheetNum, filePath);
//		// 将数据写入mongodb中
//		yearMonthMongoService.batchInsert(datas, collectionNameBefore);
//		return collectionNameBefore;
//	}
	@SuppressWarnings("rawtypes")
	private List<String> writeDataByFilePath(List<String> filePaths) {
		List<String> collectionNameBeforeList = new ArrayList<String>();
		// 循环解析并插入12个sheet页的数据到mongodb中
		for (int i = 0; i < 12; i++) {
			String collectionNameBefore = "reportDataExecute" + System.currentTimeMillis();
			String filePath = YearMonthPlanFilePathUtils.getPuTie();
			// 根据完成表的路径用poi读出数据
			List<Map> datas = ExcelUtil.getWorkshopData(filePaths, DATA_START_ROW_NUM, DATA_START_ROW_NUM, i, filePath);
			// 将数据写入mongodb中
			yearMonthMongoService.batchInsert(datas, collectionNameBefore);
			collectionNameBeforeList.add(collectionNameBefore);
		}
		return collectionNameBeforeList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String summaryOfTxYear(List<String> filePaths,User user, String year) {
		// 1.将数据读出并写入mongodb中
//		String collectionNameBefore = writeDataByFilePath(filePaths);
		List<String> collectionNameBeforeList = writeDataByFilePath(filePaths);
		// 2.用mongodb分组聚合数据
		int[] gourpIndex = new int[] { 2, 3, 4, 6 };// 需要分组的文档key下标
		int[] addIdx = new int[35];// 需要累加的文档key下标
		for (int i = 7; i < 39; i++) {// 累加日期填报值
			addIdx[i - 7] = i;
		}
		addIdx[32] = 1;// 设备处所需要累加,以字符串形式连接在一起
		addIdx[33] = 5;// 计划数量
		addIdx[34] = 6;// 计划完成
		List<List<Map>> workShopDataList = new ArrayList<List<Map>>();
		// 循环插入12个sheet页已汇总的数据到mongodb中
		collectionNameBeforeList.forEach(collectionNameBefore -> {
			List<Map> workShopData = yearMonthMongoService.groupbyAdd(collectionNameBefore, gourpIndex, addIdx);
			workShopDataList.add(workShopData);
		});
		List<String> collectionNameSummaryList = new ArrayList<String>();
		// 删除12个sheet的最后一行“审批人”
		workShopDataList.forEach(workShopData -> {
			workShopData.remove(0);
			// 因为workShopData里的数据cell6字段的值（完成/计划）是相反的，所以将数据重新倒排，把cell6字段变成（计划/完成）
			Collections.reverse(workShopData);
			// 重新排序赋值给序号（cell0）
			for (int i = 0, index = 1; i < workShopData.size(); i++) {
				if (i % 2 != 0) {
					index++;
				}
				workShopData.get(i).put(ALL_IDX_KEY, index);
			}
			String collectionNameSummary = "summaryDataExecute" + System.currentTimeMillis();
			yearMonthMongoService.batchInsert(workShopData, collectionNameSummary);
			collectionNameSummaryList.add(collectionNameSummary);
		});
		
		// 6.根据统计结果生成对应的excel
		String workShopFileName = generateWorkShopFirstExcel(workShopDataList, user, year);
		// 7.删表
		collectionNameBeforeList.forEach(collectionNameBefore -> {
			yearMonthMongoService.clearCollection(collectionNameBefore);
		});
		collectionNameSummaryList.forEach(collectionNameSummary -> {
			yearMonthMongoService.clearCollection(collectionNameSummary);
		});
		return workShopFileName;
	}
	
}
