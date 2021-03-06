package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthOperStatus;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttachPuTie;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.domain.YearMonthPutieCJ;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.domain.YearMonthPutieGQ;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkAreaDto;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkShopDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.util.DataExistStatus;
import com.enovell.yunwei.km_micor_service.util.DeviceRecordPlaceUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.AttachShowUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.JDBCUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanTableUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthReportFileUtil;
/**
* @ClassName: YearMonthWorkAreaServiceImpl 
* @Description: 年月报表编制工区业务实现类
* @date 2017年9月19日 上午8:59:27 
* @author luoyan
 */
@Transactional
@Service("yearMonthPutieGQService")
@SuppressWarnings("rawtypes")
public class YearMonthPutieGQServiceImpl implements YearMonthPutieGQService {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource(name="yearMonthPuTieWSService")
	private YearMonthPuTieWSService yearMonthPuTieWSService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Autowired
	private MongoTemplate mt;
	
	private void updateYearMonth(String id, String toYearFileName, String toMonthFileName) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String updateSql="UPDATE YEAR_MONTH_PUTIE_GQ "+
						 "SET ATTACH_PATH8_1=:toYearFileName "+
						 ",ATTACH_PATH8_2=:toMonthFileName "+
						 "WHERE ID_ =:id "+
						 "AND EXIST_=:exist";
		paramMap.put("toYearFileName",toYearFileName);
		paramMap.put("toMonthFileName",toMonthFileName);
		paramMap.put("id",id);
		paramMap.put("exist",DataExistStatus.EXIST);
		jdbcTemplate.update(updateSql, paramMap);
		
	}
	
	@Override
	public ResultMsg importReport(String uploadFileYearPath, String uploadFileMonthPath, String id,String name, String year,
			User user) {
		System.out.println(uploadFileYearPath);
		System.out.println(uploadFileMonthPath);
		System.out.println(id);
		System.out.println(name);
		System.out.println(year);
		System.out.println(user);
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		// 获取父组织机构信息
		String workShopName = String.valueOf(userService.getParentOrgbyOrgId(orgId).get("ORG_NAME_"));
		
		//1、获取年月报表配置数据
		Map<String , String> yearMonthConfig = findAllDocument("deviceNameWorkManage");
		
//		System.out.println(yearMonthConfig);
		
		//年表模板路径
		String toYearFilePath = YearMonthPlanFilePathUtils.getPuTie(user.getOrganization().getId(), YearMonthPutieGQServiceImpl.class);
		System.out.println(toYearFilePath);
		//2.1年表验证及数据（工作内容）插入生成年表模板
		String toYearFileName = createYearTableByUploadFile(yearMonthConfig,uploadFileYearPath,toYearFilePath,orgName,workShopName,"year");
		//月表模板路径
		String toMonthFilePath = YearMonthPlanFilePathUtils.getPuTie(user.getOrganization().getId(), YearMonthPutieGQServiceImpl.class);
		System.out.println(toMonthFilePath);
		//2.2月表验证及数据（工作内容）插入生成月表模板
		String toMonthFileName = createMonthTableByUploadFile(yearMonthConfig,uploadFileMonthPath,toMonthFilePath,orgName,workShopName,"month");
		
		if(StringUtils.isBlank(toYearFileName)&&StringUtils.isNotBlank(toMonthFileName)){
			return ResultMsg.getFailure("年表附件中有填写错误的信息！");
		}
		if(StringUtils.isNotBlank(toYearFileName)&&StringUtils.isBlank(toMonthFileName)){
			return ResultMsg.getFailure("月表附件中有填写错误的信息！");
		}
		if(StringUtils.isBlank(toYearFileName)&&StringUtils.isBlank(toMonthFileName)){
			return ResultMsg.getFailure("年表和月表附件中均有填写错误的信息！");
		}
		
		updateYearMonth(id,toYearFileName,toMonthFileName);
		return ResultMsg.getSuccess("上传附件并生成编制表成功！");
	}
	
	private Map<String , String> findAllDocument(String collectionName) {
	    List<Document> resultDocuments = mt.find(new Query(), Document.class,collectionName);
	    Map<String , String> yearMonthConfig = new HashMap<String, String>();
	    resultDocuments.stream().forEach(d -> {
	            d.append("docId", d.getObjectId("_id").toHexString());
	            d.remove("_id");
	            String key = d.getString("countYear")+"-"+d.getString("deviceName");
	            String value = d.getString("workContent");
	            yearMonthConfig.put(key, value);
	            
	        });
	    return yearMonthConfig;
	}

	private String createYearTableByUploadFile(Map<String , String> data, String uploadFilePath, String createPath,String orgName,String workShopName,String type){
		File toFile = new File(createPath);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(uploadFilePath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			boolean checkStatus  = checkUploadFileAndInsertData(s, data , orgName, workShopName, type);
			if(checkStatus==false){
				fis.close();
				wb.close();
				return "";
			}
			fos = new FileOutputStream(toFile);
//			对空模板插入获取的模板数据
//			insertData(s, data , orgName, workShopName, type);
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
		return toFile.getName();
	}
	
	private String createMonthTableByUploadFile(Map<String , String> data, String uploadFilePath, String createPath,String orgName,String workShopName,String type){
		File toFile = new File(createPath);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(uploadFilePath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			boolean checkStatus  = checkUploadFileAndInsertData(s, data , orgName, workShopName, type);
			if(checkStatus==false){
				fis.close();
				wb.close();
				return "";
			}
			fos = new FileOutputStream(toFile);
//			对空模板插入获取的模板数据
//			insertData(s, data , orgName, workShopName, type);
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
		return toFile.getName();
	}
	
	private boolean checkUploadFileAndInsertData(HSSFSheet s,Map<String , String> data,String orgName,String workShopName,String type){
		
		String title = "   昆明通信段   "+ workShopName +"   "+ orgName +"   工长：       车间主任：                   年    月     日";
		
		HSSFRow row2 = s.getRow(2);
		HSSFCell cell0 = row2.getCell(0);//表头
		cell0.setCellValue(title);
		
		int lastRowNum = s.getLastRowNum();
		for (int i = 6; i < lastRowNum; i++) {
			HSSFRow row = s.getRow(i);
			String keyString = row.getCell(0).getStringCellValue()+"-"+row.getCell(3).getStringCellValue();
			String valueString = data.get(keyString);
			if(StringUtils.isBlank(valueString)) {
				return false;
			}
			row.getCell(4).setCellValue(valueString);
		}
		return true;
	}
	
	/**
	 * 
	 * createTable 创建报表模板入口
	 * 
	 * @param data 获取的模板数据
	 * @param modelPath 模板的文件存放路径
	 * @param createPath 生成好的（填了基础数据）模板存放路径
	 * @param orgName 登录人组织机构名称
	 * @param workShopName 登录人上级组织机构名称
	 * @return 模板文件名
	 */
	private String createTable(Map<String,List<Map>> data, String modelPath, String createPath,String orgName,String workShopName,String type){
		File toFile = new File(createPath);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(modelPath);
			wb =  new HSSFWorkbook(fis);
			HSSFSheet s = wb.getSheetAt(0);
			fos = new FileOutputStream(toFile);
			//对空模板插入获取的模板数据
			insertData(s, data , orgName, workShopName, type);
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
		return toFile.getName();
	}
	/**
	 * 
	 * insertData 插入模板数据
	 * 
	 * @param s
	 * @param data 获取的模板数据
	 * @param orgName 登录人组织机构名称
	 * @param workShopName 登录人上级组织机构名称
	 */
	private void insertData(HSSFSheet s,Map<String,List<Map>> data,String orgName,String workShopName,String type){
		
		String title = "   昆明通信段   "+ workShopName +"   "+ orgName +"   工长：       车间主任：                   年    月     日";
		
		HSSFRow row2 = s.getRow(2);
		HSSFCell cell0 = row2.getCell(0);//表头
		cell0.setCellValue(title);
		List<Map> dataAllList = new ArrayList<Map>();
		List<Integer> typeCommonNumber = new ArrayList<Integer>();//每个分组（分组依据type:周期对应的中文类别）下的数据个数的集合
		for (String key : data.keySet()) {
			dataAllList.addAll(data.get(key));
			typeCommonNumber.add(data.get(key).size());
		}
		if("year".equals(type)) {
			insertYearTable(s, dataAllList, typeCommonNumber);
		}else {
			insertMonthTable(s, dataAllList, typeCommonNumber);
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
	/**
	 * 
	 * insertYearTable 
	 * 
	 * @param s
	 * @param data 
	 * @param deviceCommonNumber 
	 * @return
	 */
	private void insertYearTable(HSSFSheet s, List<Map> data, List<Integer> typeCommonNumber) {
		
		List<Map> result = data;
		int resultNum = result.size();
		int rows = resultNum - 1;
		int lastRowNum = s.getLastRowNum();
		// 将第7行以下的全部下移的行数
		int shiftDownRow = lastRowNum + rows;
		s.shiftRows(7, 8, shiftDownRow, true, false);
		HSSFRow source = s.getRow(6);// 从row7开始复制
		HSSFRow target = s.createRow(6);// 从row8开始粘贴
		copyRow(source, target);
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow = s.getRow(6 + i);// 从row7开始复制
			HSSFRow targetRow = s.createRow(7 + i);// 从row8开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(7 + lastRowNum + rows, 8 + lastRowNum + rows, shiftUpRow, true, false);

		int count = 6;

		for (int i = 0; i < typeCommonNumber.size(); i++) {
			if (typeCommonNumber.get(i) == 1) {
				count += typeCommonNumber.get(i);
				continue;
			}
			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 0, 0));//合并第一列
//			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 1, 1));
//			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 2, 2));
//			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 3, 3));
			count += typeCommonNumber.get(i);

		}

		String type = String.valueOf(result.get(0).get("type"));
		int serial = 1;

		for (int i = 0; i < result.size(); i++) {
			HSSFRow row = s.getRow(6 + i);
			HSSFCell cell0 = row.getCell(0);// 类别
			HSSFCell cell1 = row.getCell(1);// 序号
			HSSFCell cell2 = row.getCell(2);// 设备处所
			HSSFCell cell3 = row.getCell(3);// 设备名称
			HSSFCell cell4 = row.getCell(4);// 工作内容
			HSSFCell cell5 = row.getCell(5);// 单位
			HSSFCell cell8 = row.getCell(8);// 每年次数
			HSSFCell cell9 = row.getCell(9);// 负责单位
			cell0.setCellValue(String.valueOf(result.get(i).get("type")));

			if (type.equals(String.valueOf(result.get(i).get("type")))) {
				cell1.setCellValue(String.valueOf(serial));
			} else {
				serial = 1;
				type = String.valueOf(result.get(i).get("type"));
				cell1.setCellValue(String.valueOf(serial));
			}
			serial++;

			cell2.setCellValue(String.valueOf(result.get(i).get("uniplace")));
			cell3.setCellValue(String.valueOf(result.get(i).get("deviceName")));
			cell4.setCellValue(String.valueOf(result.get(i).get("workContent")));
			cell5.setCellValue(String.valueOf(result.get(i).get("unit")));
			cell8.setCellValue(String.valueOf(result.get(i).get("countYear")));
			cell9.setCellValue(String.valueOf(result.get(i).get("workArea")));
		}
			
	}
	private void insertMonthTable(HSSFSheet s, List<Map> data, List<Integer> typeCommonNumber) {
		
		List<Map> result = data;
		int resultNum = result.size();
		int rows = resultNum - 1;
		int lastRowNum = s.getLastRowNum();
		// 将第7行以下的全部下移的行数
		int shiftDownRow = lastRowNum + rows;
		s.shiftRows(7, 8, shiftDownRow, true, false);
		HSSFRow source = s.getRow(6);// 从row7开始复制
		HSSFRow target = s.createRow(6);// 从row7开始粘贴
		copyRow(source, target);
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow = s.getRow(6 + i);// 从row7开始复制
			HSSFRow targetRow = s.createRow(7 + i);// 从row8开始粘贴
			copyRow(sourceRow, targetRow);
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(7 + lastRowNum + rows, 8 + lastRowNum + rows, shiftUpRow, true, false);
		
		int count = 6;
		
		for (int i = 0; i < typeCommonNumber.size(); i++) {
			if (typeCommonNumber.get(i) == 1) {
				count += typeCommonNumber.get(i);
				continue;
			}
			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 0, 0));
//			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 1, 1));
//			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 2, 2));
//			s.addMergedRegion(new CellRangeAddress(count, count + typeCommonNumber.get(i) - 1, 3, 3));
			count += typeCommonNumber.get(i);
			
		}
		
		String type = String.valueOf(result.get(0).get("type"));
		int serial = 1;
		
		for (int i = 0; i < result.size(); i++) {
			HSSFRow row = s.getRow(6 + i);
			HSSFCell cell0 = row.getCell(0);// 类别
			HSSFCell cell1 = row.getCell(1);// 序号
			HSSFCell cell2 = row.getCell(2);// 设备处所
			HSSFCell cell3 = row.getCell(3);// 设备名称
			HSSFCell cell4 = row.getCell(4);// 工作内容
			HSSFCell cell5 = row.getCell(5);// 单位
			HSSFCell cell7 = row.getCell(7);// 每月次数
			cell0.setCellValue(String.valueOf(result.get(i).get("type")));
			
			if (type.equals(String.valueOf(result.get(i).get("type")))) {
				cell1.setCellValue(String.valueOf(serial));
			} else {
				serial = 1;
				type = String.valueOf(result.get(i).get("type"));
				cell1.setCellValue(String.valueOf(serial));
			}
			serial++;
			
			cell2.setCellValue(String.valueOf(result.get(i).get("uniplace")));
			cell3.setCellValue(String.valueOf(result.get(i).get("deviceName")));
			cell4.setCellValue(String.valueOf(result.get(i).get("workContent")));
			cell5.setCellValue(String.valueOf(result.get(i).get("unit")));
			cell7.setCellValue(String.valueOf(result.get(i).get("countYear")));
		}
		
	}
	
	@Override
	public ResultMsg addReport(String name, String year, User user) {
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		// 获取父组织机构信息
		String workShopName = String.valueOf(userService.getParentOrgbyOrgId(orgId).get("ORG_NAME_"));
		boolean existData = yearMonthPuTieWSService.existData("YEAR_MONTH_PUTIE_GQ", year, orgId,null);
		if(existData){
			return ResultMsg.getFailure("该年数据已存在！");
		}
//		// TODO 1.根据查询出的履历数据获取设备与工作内容的关系数据（用于年表）
//		// 根据组织机构名查询履历簿（deviceRecord），得到设备分类、设备处所、车间、工区
//		List<Map> deviceRecord = mt.find(new Query(Criteria.where("status").is(1).and("workArea").is(orgName)), Map.class, "deviceRecord");
//		// 根据设备类别分组
//		Map<String, List<Map>> groupDeviceList = deviceRecord.stream().collect(Collectors.groupingBy(s -> String.valueOf(s.get("deviceClass"))));
//		// 封装履历簿数据
//		List<Map> deviceList = new ArrayList<Map>();
//		// 封装设备名称集合数据
//		List<String> deviceNameList = new ArrayList<String>();
//		// 对设备处所去重
//		distinctByUniplace(orgName, workShopName, groupDeviceList, deviceList, deviceNameList);
//		
//		// 1.1.根据得到的设备分类查询中间表（deviceNameWorkManage），封装数据，数据结构为：类别，设备处所，设备名称(设备分类)，工作内容，单位，每年次数，负责单位（登录工区）
//		List<Map> deviceNameWorkManageYearList = mt.find(new Query(Criteria.where("deviceName").in(deviceNameList).and("yearOrMonth").is("year")), Map.class, YearMonthPlanTableUtils.getDeviceCheckWorkManage());
//		// 1.2.根据设备名称（设备分类）将履历簿数据与中间表数据合并在一起（以中间表数据为主体）
//		List<Map> yearList = combineDeviceListAndDeviceNameWorkManageList(deviceList, deviceNameWorkManageYearList);
//		// 1.3.根据类别（type）进行分组
//		Map<String, List<Map>> groupYearData = yearList.stream().collect(Collectors.groupingBy(s -> String.valueOf(s.get("type"))));
//		// 1.4.根据已合并的数据生成年表模板
//		String toYearFilePath = YearMonthPlanFilePathUtils.getPuTie(user.getOrganization().getId(), YearMonthPutieGQServiceImpl.class);
//		// 1.5.年表模板的生成
//		String toYearFileName = createTable(groupYearData,YearMonthPlanFilePathUtils.getPuTieTpl() + YearMonthReportFileUtil.KMMS_YEAR_TABLE,toYearFilePath,orgName,workShopName,"year");
//		
//		// TODO 2.根据查询出的履历数据获取设备与工作内容的关系数据（用于月表）
//		// 2.1.根据得到的设备分类查询中间表（deviceNameWorkManage），封装数据，数据结构为：类别，设备处所，设备名称(设备分类)，工作内容，单位，每年次数，负责单位（登录工区）
//		List<Map> deviceNameWorkManageMonthList = mt.find(new Query(Criteria.where("deviceName").in(deviceNameList).and("yearOrMonth").is("month")), Map.class, YearMonthPlanTableUtils.getDeviceCheckWorkManage());
//		// 2.2.根据设备名称（设备分类）将履历簿数据与中间表数据合并在一起（以中间表数据为主体）
//		List<Map> monthList = combineDeviceListAndDeviceNameWorkManageList(deviceList, deviceNameWorkManageMonthList);
//		// 2.3.根据类别（type）进行分组
//		Map<String, List<Map>> groupMonthData = monthList.stream().collect(Collectors.groupingBy(s -> String.valueOf(s.get("type"))));
//		// 2.4.根据已合并的数据生成月表模板
//		String toMonthFilePath = YearMonthPlanFilePathUtils.getPuTie(user.getOrganization().getId(), YearMonthPutieGQServiceImpl.class);
//		// 2.5.月表模板的生成
//		String toMonthFileName = createTable(groupMonthData,YearMonthPlanFilePathUtils.getPuTieTpl() + YearMonthReportFileUtil.KMMS_MONTH_TABLE,toMonthFilePath,orgName,workShopName,"month");
		
		// 3.将生成的月表和年表模板路径入库
//		insertYearMonth(name, year, user, toYearFileName, toMonthFileName);
		insertYearMonth(name, year, user, "", "");
		return ResultMsg.getSuccess("新增成功！");
	}
	
	/**  
	 * combineDeviceListAndDeviceNameWorkManageList 根据设备名称（设备分类）将履历簿数据与中间表数据合并在一起（以中间表数据为主体）
	 * 
	 * @param deviceList 履历簿数据
	 * @param deviceNameWorkManageYearList 中间表数据
	 * @return 
	 */  
	@SuppressWarnings("unchecked")
	private List<Map> combineDeviceListAndDeviceNameWorkManageList(List<Map> deviceList,
			List<Map> deviceNameWorkManageYearList) {
		List<Map> yearList = new ArrayList<Map>();
		for (Map yearMap : deviceNameWorkManageYearList) {
			String deviceName = String.valueOf(yearMap.get("deviceName"));
			for (Map deviceMap : deviceList) {
				if (deviceName.equals(deviceMap.get("deviceName"))) {
					yearMap.putAll(deviceMap);
					yearList.add(yearMap);
				}
			}
		}
		return yearList;
	}
	
	/**  
	 * distinctByUniplace 根据设备处所（uniplace）字段进行去重
	 * 
	 * @param orgName 组织机构
	 * @param workShopName 车间名
	 * @param groupDeviceList 设备类别分组数据
	 * @param deviceList 封装履历簿数据
	 * @param deviceNameList 封装设备名称集合数据
	 */  
	@SuppressWarnings("unchecked")
	private void distinctByUniplace(String orgName, String workShopName, Map<String, List<Map>> groupDeviceList,
			List<Map> deviceList, List<String> deviceNameList) {
		for (Map.Entry<String, List<Map>> entry : groupDeviceList.entrySet()) {
			Map deviceMap = new HashMap();
			List<String> uniplaceList = new ArrayList<String>();
			entry.getValue().stream().forEach(e -> {
				Object uniplaceObj = e.get(DeviceRecordPlaceUtil.uniplace);
				if (uniplaceObj != null) {
					String uniplace = String.valueOf(uniplaceObj);
					uniplaceList.add(uniplace);
				}
			});
			String uniplace = uniplaceList.stream().distinct().collect(Collectors.joining(","));
			deviceMap.put("workshop", workShopName);
			deviceMap.put("workArea", orgName);
			deviceMap.put("deviceName", entry.getKey());
			deviceMap.put("uniplace", uniplace);
			deviceList.add(deviceMap);
			deviceNameList.add(entry.getKey());
		}
	}

	/**  
	 * insertYearMonth 将生成的月表和年表模板路径入库
	 * 
	 * @param name 年月报表名称
	 * @param year 年份
	 * @param user 用户
	 * @param toYearFileName 年表文件名
	 * @param toMonthFileName 月表文件名 
	 */  
	private void insertYearMonth(String name, String year, User user, String toYearFileName, String toMonthFileName) {
		String sql = " INSERT INTO YEAR_MONTH_PUTIE_GQ ( "
				+ " 	ID_, "
				+ " 	CREATE_TIME_, "
				+ " 	EXIST_, "
				+ " 	NAME_, "
				+ " 	ORG_ID_, "
				+ " 	ORG_NAME_, "
				+ " 	STATUS_, "
				+ " 	USER_ID_, "
				+ " 	USER_NAME_, "
				+ " 	YEAR_, "
				+ " 	ATTACH_PATH8_1, "
				+ " 	ATTACH_PATH8_2 "
				+ " ) VALUES ( "
				+ " 	:id, "
				+ " 	:createTime, "
				+ " 	:exist, "
				+ " 	:name, "
				+ " 	:orgId, "
				+ " 	:orgName, "
				+ " 	:status, "
				+ " 	:userId, "
				+ " 	:userName, "
				+ " 	:year, "
				+ " 	:toYearFileName, "
				+ " 	:toMonthFileName "
				+ " ) ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", JDBCUtil.getUUID32());
		paramMap.put("createTime", new Date());
		paramMap.put("exist", DataExistStatus.EXIST);
		paramMap.put("name", name);
		paramMap.put("orgId", user.getOrganization().getId());
		paramMap.put("orgName", user.getOrganization().getName());
		paramMap.put("status", YearMonthOperStatus.WRITE_WORKAREA_DRAFT);
		paramMap.put("userId", user.getId());
		paramMap.put("userName", user.getName());
		paramMap.put("year", year);
//		paramMap.put("toYearFileName", toYearFileName);
//		paramMap.put("toMonthFileName", toMonthFileName);
		paramMap.put("toYearFileName", "");
		paramMap.put("toMonthFileName", "");
		jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public GridDto<YearMonthPutieGQ> queryAllData(YearMonthWorkAreaDto dto, int start, int limit,User user) {
		GridDto<YearMonthPutieGQ> gd = new GridDto<YearMonthPutieGQ>();
		List<YearMonthPutieGQ> rows = getRowsByDto(dto,start,limit,user);
		Long results = getCountByDto(dto,user);
		gd.setRows(rows);
		gd.setResults(results);
		return gd;
	}
	
	/**
	 * getCountByDto 查询工区业务数据记录(数量)
	 * @param dto 查询条件YearMonthPutieGQDto
	 * @param user 当前登录用户
	 * @return Long 数据条数
	 */
	private Long getCountByDto(YearMonthWorkAreaDto dto,User user) {
		String sql = "select count(y.id_) from YEAR_MONTH_PUTIE_GQ y where 1=1 AND Y.ORG_ID_=:orgId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql = creatSql(sql,dto,paramMap);
//		paramMap.put("orgId", userService.getUserById(user.getId()).getOrganization().getId());
		paramMap.put("orgId", userService.getOrgbyUserId(user.getId()).get("ORG_ID_"));
		Long count = jdbcTemplate.queryForObject(sql, paramMap, Long.class);
		return count;
	}
	
	/**
	 * getRowsByDto 查询工区业务数据(分页查询)
	 * @param dto 查询条件YearMonthPutieGQDto
	 * @param start 开始
	 * @param limit 数据条数
	 * @param user 当前登录用户
	 * @return
	 */
	private List<YearMonthPutieGQ> getRowsByDto(YearMonthWorkAreaDto dto, int start, int limit,User user) {
		String sql = "select * from (select m.*,ROWNUM RN from (select y.* from YEAR_MONTH_PUTIE_GQ  y where 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql = creatSql(sql,dto,paramMap) + " AND Y.ORG_ID_=:orgId order by y.CREATE_TIME_ desc) m) where RN between :start and :end ";
//		paramMap.put("orgId", userService.getUserById(user.getId()).getOrganization().getId());
		paramMap.put("orgId", userService.getOrgbyUserId(user.getId()).get("ORG_ID_"));
		paramMap.put("start", start+1);
		paramMap.put("end", start+limit);
		List<YearMonthPutieGQ> rows = jdbcTemplate.query(sql, paramMap, new YearMonthWorkAreaMapper());
		return rows;
	}

	private String creatSql(String sql, YearMonthWorkAreaDto dto, Map<String, Object> paramMap) {
		if(StringUtils.isNotBlank(dto.getName())){
			sql += " and y.NAME_ like :name ";
			paramMap.put("name", "%"+dto.getName().trim()+"%");
		}
		if(StringUtils.isNotBlank(dto.getYear())){
			sql += " and y.YEAR_ = :year ";
			paramMap.put("year", dto.getYear());
		}
		if(StringUtils.isNotBlank(dto.getStatus())){
			sql += " and y.STATUS_ = :status";
			paramMap.put("status", dto.getStatus());
		}
		if(YearMonthOperStatus.EXECUTE_PAGE.equals(dto.getPage())){
			sql += " and cast(y.STATUS_ as int) >= :status1";
			paramMap.put("status1", YearMonthOperStatus.WRITE_WORKAREA_SEGMENT_PASS);
		}
		sql += " and y.EXIST_ = :exist ";
		paramMap.put("exist", DataExistStatus.EXIST);
		return sql;
	}

	@Override
	public String getAttachPath(String id, String attachType) {
		String sql = "select "+attachType+ " from YEAR_MONTH_PUTIE_GQ where ID_ = :id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		String attachPath = jdbcTemplate.queryForObject(sql,paramMap,String.class);
		return attachPath;
	}
	
	
	@Override
	public List<Map<String, Object>> queryAllDataById(String id) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String querySql="SELECT * FROM YEAR_MONTH_PUTIE_GQ WHERE ID_ =:id AND EXIST_=:exist";
		paramMap.put("id",id);
		paramMap.put("exist",DataExistStatus.EXIST);
		return jdbcTemplate.queryForList(querySql, paramMap);
		
	}
	
	@Override
	public ResultMsg updateReportById(String id, String name, String year, User user){
		String orgId = user.getOrganization().getId();
		boolean existData = yearMonthPuTieWSService.existData("YEAR_MONTH_PUTIE_GQ", year, orgId,id);
		if(existData){
			return ResultMsg.getFailure("该年数据已存在！");
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String updateSql="UPDATE YEAR_MONTH_PUTIE_GQ "+
						 "SET NAME_=:name "+
						 ",YEAR_=:year "+
						 "WHERE ID_ =:id "+
						 "AND EXIST_=:exist";
		paramMap.put("name",name);
		paramMap.put("year",year);
		paramMap.put("id",id);
		paramMap.put("exist",DataExistStatus.EXIST);
		jdbcTemplate.update(updateSql, paramMap);
		return ResultMsg.getSuccess("修改成功！");
		
	}
	
	@Override
	public int deleteReports(String ids) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String updateSql="UPDATE YEAR_MONTH_PUTIE_GQ "+
						 "SET EXIST_=:not_exist "+
						 "WHERE ID_ =:id ";
		paramMap.put("id",ids);
		paramMap.put("not_exist",DataExistStatus.NOT_EXIST);
		return jdbcTemplate.update(updateSql, paramMap);
	}
	
	@Override
	public void reportToWorkShop(String id,String year,User user) {
		//增加车间数据
		String orgId = user.getOrganization().getId();
//		Organization parentOrg = organizationService.getParentOrgByChildrenId(orgId);
		Map<String, Object> map = userService.getParentOrgbyOrgId(orgId);
		orgId = String.valueOf(map.get("ORG_ID_"));
		String parentOrgName = String.valueOf(map.get("ORG_NAME_"));
		
		// 广州通信段客户提出车间年月报表默认名称为“2018年普铁通信设备年月表（惠州通信车间）”
		String cjName = year + "年通信设备年月表（" + parentOrgName + "）";
		String wsId = yearMonthPuTieWSService.add(cjName, year, orgId, parentOrgName);
		//上报工区数据
		String sql="UPDATE YEAR_MONTH_PUTIE_GQ "+
						 " SET STATUS_=:status,REPORT_TIME_=:reportTime,YEAR_MONTH_WORKSHOP=:wsId, "+
						 " FAIL_REASON_=:failReason WHERE ID_ =:id ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id",id);
		paramMap.put("status",YearMonthOperStatus.WRITE_WORKAREA_REPORT);
		paramMap.put("reportTime",new Date());
		paramMap.put("wsId", wsId);
		paramMap.put("failReason", "");
		jdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public boolean updateWorkShopStatus(List<String> ids,String status) {
		//第一步  根据工区数据id集合来查询车间数据id集合
		List<String> workShopIds = getWorkShopId(ids);
		//第二步 设置车间和工区的状态
		//根据工区数据的状态获取车间数据的状态
		String workShopStatus = getWorkShopStatus(status);
		//根据业务需求，重新封装工区数据的状态
		List<String> workAreaStatusList = getWorkAreaStatus(status);
		//第三步  根据其下属工区的状态更新车间数据的状态
		for (String workShopId : workShopIds) {
			updateStatusByWorkShopId(workShopId, workShopStatus,workAreaStatusList);
		}
		return true;
	}
	/**
	 * 
	 * getWorkShopId 根据工区数据id集合来查询车间数据id集合
	 * @author quyy
	 * @param ids 工区数据id集合
	 * @return
	 */
	private List<String> getWorkShopId(List<String> ids){
		String sql = "SELECT DISTINCT y.YEAR_MONTH_WORKSHOP FROM YEAR_MONTH_PUTIE_GQ y WHERE y.ID_ IN (:ids)  ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		List<String> workShopIds = jdbcTemplate.queryForList(sql, paramMap, String.class);
		return workShopIds;
	}
	/**
	 * 
	 * updateStatusByWorkShopId 根据其下属工区的状态更新车间数据的状态
	 * @author quyy
	 * @param workShopId 车间id
	 * @param workAreaStatus 工区状态 （车间审核通过或者车间审核不通过）
	 * @return 
	 */
	private void updateStatusByWorkShopId(String workShopId,String workShopStatus,List<String> workAreaStatusList) {
		String sql = " update YEAR_MONTH_PUTIE_CJ cj set cj.STATUS_=:workShopStatus where  "
				   + " (SELECT count(*) FROM YEAR_MONTH_PUTIE_GQ y where y.status_ not in (:workAreaStatus)  and y.YEAR_MONTH_WORKSHOP =:workShopId AND y.EXIST_ =:exist)=0 "
				   + " and cj.id_ =:workShopDataId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("workShopStatus", workShopStatus);
		paramMap.put("workShopId", workShopId);
		paramMap.put("workAreaStatus", workAreaStatusList);
		paramMap.put("exist", DataExistStatus.EXIST);
		paramMap.put("workShopDataId", workShopId);
		jdbcTemplate.update(sql, paramMap);
	}
	/**
	 * 
	 * getWorkAreaStatus 根据工区数据的状态重新封装工区数据的状态，原因：
	 * 1.当某一车间下所有的工区数据的状态都为车间审核通过或者段科室审核通过时，车间数据的状态变为车间审核通过
	 * 2.当某一车间下的所有工区数据的状态都为某一状态时，对应的车间数据状态就变为那一状态
	 * @author quyy
	 * @param workAreaStatus
	 * @return
	 */
	private List<String> getWorkAreaStatus(String workAreaStatus){
		List<String> result = new ArrayList<String>();
		if(workAreaStatus.equals(YearMonthOperStatus.WRITE_WORKAREA_WORKSHOP_PASS)) {
			result.add(workAreaStatus);
			result.add(YearMonthOperStatus.WRITE_WORKAREA_SEGMENT_PASS);
			result.add(YearMonthOperStatus.WORKAREA_EXECUTE_REPORT);
			result.add(YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL);
			result.add(YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_PASS);
			result.add(YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_FAIL);
			result.add(YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_PASS);
		}
		else if(workAreaStatus.equals(YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_PASS)){
			result.add(workAreaStatus);
			result.add(YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_PASS);
		}else {
			result.add(workAreaStatus);
		}
		return result;
	}
	/**
	 * 
	 * getWorkShopStatus 根据工区数据的状态获取车间数据的状态
	 * @author quyy
	 * @param workAreaStatus 工区数据的状态
	 * @return
	 */
	private String getWorkShopStatus(String workAreaStatus) {
		String workShopStatus = YearMonthOperStatus.WRITE_WORKSHOP_PASS;
		if(workAreaStatus.equals(YearMonthOperStatus.WRITE_WORKAREA_WORKSHOP_FAIL)) {
			workShopStatus = YearMonthOperStatus.WRITE_WORKSHOP_FAIL;
		}else if(workAreaStatus.equals(YearMonthOperStatus.WRITE_WORKAREA_SEGMENT_FAIL)) {
			workShopStatus = YearMonthOperStatus.WRITE_WORKSHOP_SEGMENT_FAIL;
		}else if(workAreaStatus.equals(YearMonthOperStatus.WRITE_WORKAREA_SEGMENT_PASS)) {
			workShopStatus = YearMonthOperStatus.WRITE_WORKSHOP_SEGMENT_PASS;
		}else if(workAreaStatus.equals(YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL)) {
			workShopStatus = YearMonthOperStatus.WORKSHOP_EXECUTE_FAIL;
		}else if(workAreaStatus.equals(YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_PASS)) {
			workShopStatus = YearMonthOperStatus.WORKSHOP_EXECUTE_PASS;
		}else if(workAreaStatus.equals(YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_FAIL)) {
			workShopStatus = YearMonthOperStatus.WORKSHOP_SEGMENT_EXECUTE_FAIL;
		}else if(workAreaStatus.equals(YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_PASS)) {
			workShopStatus = YearMonthOperStatus.WORKSHOP_SEGMENT_EXECUTE_PASS;
		}
		return workShopStatus;
	}
	@Override
	public List<YearMonthPutieGQ> getworkAreaDatas(String workShopDataId) {
		String sql = "select * from YEAR_MONTH_PUTIE_GQ y  where y.YEAR_MONTH_WORKSHOP =:workShopDataId AND y.EXIST_ =1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("workShopDataId", workShopDataId);
		List<YearMonthPutieGQ> datas = jdbcTemplate.query(sql, paramMap, new YearMonthWorkAreaMapper());
		return datas;
	}
	
	@Override
	public void workAreaUnlock(List<String> workAreaIdLists) {
		String sql="update YEAR_MONTH_PUTIE_GQ set STATUS_=:status "
				+ "where id_ in(:workAreaIds) ";
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("status", YearMonthOperStatus.WRITE_WORKAREA_UNLOCK);
		paramMap.put("workAreaIds",workAreaIdLists);
		jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void saveFileName(String id, String attachType, String fileName) {
		String sql = " update YEAR_MONTH_PUTIE_GQ GQ set GQ."+attachType+"=:fileName where "
				   + " GQ.ID_ =:id ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fileName", fileName);
		paramMap.put("id", id);
		jdbcTemplate.update(sql, paramMap);
	}


	/**
	 * 
	 * getWorkAreaDataById 根据id查询工区业务数据的详情
	 * @author quyy
	 * @param id 工区业务数据id
	 * @return
	 */
	public YearMonthPutieGQ getWorkAreaDataById(String id) {
		String sql = " SELECT * FROM YEAR_MONTH_PUTIE_GQ gq WHERE gq.id_ =:id AND gq.EXIST_=:exist ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("exist",DataExistStatus.EXIST);
		YearMonthPutieGQ workAreaData = jdbcTemplate.queryForObject(sql, paramMap, new YearMonthWorkAreaMapper());
		return workAreaData;
	}
	/**
	 * 
	 * getAttachDataByWorkAreaData 根据工区数据封装它的附件展示信息数据
	 * @author quyy
	 * @param workAreaData 年月报表工区业务数据
	 * @return
	 */
	private List<AttachShowDto> getAttachDataByWorkAreaData(List<AttachShowDto> attachDtos,YearMonthPutieGQ workAreaData){
			setAttachShowDtoEspecialData(attachDtos.get(0), workAreaData.getAttachPath8_1(), workAreaData.getAttachName8_1(),null,null);//设置 高铁通信设备_年表 的值
			setAttachShowDtoEspecialData(attachDtos.get(1), workAreaData.getAttachPath8_2(), workAreaData.getAttachName8_2(),null,null);//设置 高铁通信设备_月表 的值
//			setAttachShowDtoEspecialData(attachDtos.get(2), workAreaData.getAttachPath8_3(), workAreaData.getAttachName8_3(),null,null);//设置 高铁网管设备_年表 的值
//			setAttachShowDtoEspecialData(attachDtos.get(3), workAreaData.getAttachPath8_4(), workAreaData.getAttachName8_4(),null,null);//设置 高铁网管设备_月表 的值
		return attachDtos;
	}
	
	/**
	 * 
	 * getAttachDataByWorkAreaData 根据车间数据封装它的附件展示信息数据
	 * @author quyy
	 * @param workShopData 年月报表车间业务数据
	 * @param attachDtos  附件详情集合
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<AttachShowDto> getAttachDataByWorkShopData(List<AttachShowDto> attachDtos,YearMonthWorkShopDto workShopData){
		setAttachShowDtoEspecialData(attachDtos.get(0), workShopData.getAttachPath8_1(), workShopData.getAttachName8_1(),getReportTotal(workShopData.getOrgId()),getHasReport(workShopData.getId(), attachDtos.get(0).getAttachType()));//设置 高铁通信设备_年表 的值
		setAttachShowDtoEspecialData(attachDtos.get(1), workShopData.getAttachPath8_2(), workShopData.getAttachName8_2(),getReportTotal(workShopData.getOrgId()),getHasReport(workShopData.getId(), attachDtos.get(1).getAttachType()));//设置 高铁通信设备_月表 的值
		setAttachShowDtoEspecialData(attachDtos.get(2), workShopData.getAttachPath8_3(), workShopData.getAttachName8_3(),getReportTotal(workShopData.getOrgId()),getHasReport(workShopData.getId(), attachDtos.get(2).getAttachType()));//设置 高铁网管设备_年表 的值
		setAttachShowDtoEspecialData(attachDtos.get(3), workShopData.getAttachPath8_4(), workShopData.getAttachName8_4(),getReportTotal(workShopData.getOrgId()),getHasReport(workShopData.getId(), attachDtos.get(3).getAttachType()));//设置 高铁网管设备_月表 的值
		return attachDtos;
	}
	/**
	 * 
	 * setAttachShowDtoEspecialData 设置AttachShowDto的特殊值
	 * @author quyy
	 * @param dto 附件详情
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	private AttachShowDto setAttachShowDtoEspecialData(AttachShowDto dto,String filePath,String fileName,String reportTotal,String hasReport){
		dto.setFilePath(filePath);
		dto.setFileName(fileName);
		dto.setReportTotal(reportTotal);
		dto.setHasReport(hasReport);
		dto.setReportPecent(hasReport+"/"+reportTotal);
		return dto;
	}
	
	@Override
	public String getReportTotal(String wsId) {
//		List<Organization> organizations = organizationService.getChildrenByParentId(wsId);
		List<Map<String, Object>> organizations = userService.getChildrenByParentId(wsId);
		return organizations.size()+"";
	}

	@Override
	public String getHasReport(String id, String attachPath) {
		/**
		 * 修改人：luoyan 2017-12-24 id改为传多个车间数据id的拼接字符串
		 */
		String[] ids = id.split(",");
		String sql = " SELECT count(*) FROM YEAR_MONTH_PUTIE_GQ t  "
				+ "WHERE t.STATUS_ !=:status AND t.YEAR_MONTH_WORKSHOP in(:ids)"
				+ "  and t.EXIST_ =:exist and t."+YearMonthReportAttachPuTie.FILE_NAME_MAP.get(attachPath)+" is not null ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status",YearMonthOperStatus.WRITE_WORKAREA_DRAFT);
		paramMap.put("ids",Arrays.asList(ids));
		paramMap.put("exist",DataExistStatus.EXIST);
		long count = jdbcTemplate.queryForObject(sql, paramMap, long.class);
		return count+"";
	}

	@Override
	public List<AttachShowDto> getAttachDataByGQId(String id) {
		List<AttachShowDto> result = AttachShowUtil.getPlanAttachDataPutie();
		//根据id查询工区业务数据的详情
		YearMonthPutieGQ workAreaData = getWorkAreaDataById(id);
		//拼装附件展示数据
		result = getAttachDataByWorkAreaData(result,workAreaData);
		return result;
	}

	@Override
	public List<AttachShowDto> getAttachDataByCJId(String id) {
		List<AttachShowDto> result = AttachShowUtil.getWSPlanAttachData();
		//根据id查询车间业务数据的详情
		YearMonthWorkShopDto workshopData = yearMonthPuTieWSService.getYearMonthWorkShopDtoById(id);
		//拼装附件展示数据
		setAttachShowDtoEspecialData(result.get(0), workshopData.getAttachPath8_1(),
				workshopData.getAttachName8_1(),getReportTotal(workshopData.getOrgId()),
				getHasReport(workshopData.getId(), result.get(0).getAttachType()));//设置 高铁通信设备_年表 的值 
		setAttachShowDtoEspecialData(result.get(1), workshopData.getAttachPath8_2(),
				workshopData.getAttachName8_2(),getReportTotal(workshopData.getOrgId()),
				getHasReport(workshopData.getId(), result.get(1).getAttachType()));//设置 高铁通信设备_月表 的值 
		return result;
	}

	@Override
	public List<AttachShowDto> getAttachDataDuan(String ids) {
		List<AttachShowDto> result = AttachShowUtil.getPlanAttachDataPutie();
		if(StringUtils.isBlank(ids)) {
			return result;
		}
		result = getAttachDataOfSegmentData(result, ids);
		return result;
	}
	
	@Override
	public List<AttachShowDto> getAttachPlanDataDuan(String ids) {
		List<AttachShowDto> result = AttachShowUtil.getPlanAttachDataPutie();
		if(StringUtils.isBlank(ids)) {
			return result;
		}
		result = getAttachDataOfSegmentDataPutie(result, ids);
		return result;
	}
	
	@Override
	public List<AttachShowDto> getAttachExecuteDataDuan(String ids) {
		List<AttachShowDto> result = AttachShowUtil.getExecuteAttachDataPutie();
		if(StringUtils.isBlank(ids)) {
			return result;
		}
		result = getAttachDataOfSegmentDataPutie(result, ids);
		return result;
	}
	
	/**
	 * 普铁段附件展示信息
	 * getAttachDataOfSegmentDataPutie 
	 * @param attachDtos 附件信息集合
	 * @param ids 所选车间数据id
	 * @return
	 * List<AttachShowDto> 包含是否能导出的信息的集合
	 * @author luoyan
	 */
	private List<AttachShowDto> getAttachDataOfSegmentDataPutie(List<AttachShowDto> attachDtos, String ids) {
		setAttachShowDtoSegmentData(attachDtos.get(0),isSegmentGatherExport(ids, attachDtos.get(0).getAttachType()));//设置 高铁通信设备_年表 的值
		setAttachShowDtoSegmentData(attachDtos.get(1),isSegmentGatherExport(ids, attachDtos.get(1).getAttachType()));//设置 高铁通信设备_月表 的值
//		setAttachShowDtoSegmentData(attachDtos.get(2),isSegmentGatherExport(ids, attachDtos.get(2).getAttachType()));//设置 高铁网管设备_年表 的值
//		setAttachShowDtoSegmentData(attachDtos.get(3),isSegmentGatherExport(ids, attachDtos.get(3).getAttachType()));//设置 高铁网管设备_月表 的值
		return attachDtos;
	}

	/**
	 * 
	 * getAttachDataByWorkAreaData 根据车间数据封装它的附件展示信息数据
	 * @author quyy
	 * @param attachDtos 附件详情集合
	 * @param ids 车间id
	 * @return
	 */
	@Override
	public List<AttachShowDto> getAttachDataOfSegmentData(List<AttachShowDto> attachDtos,String ids){
		setAttachShowDtoSegmentData(attachDtos.get(0),isSegmentGather(ids, attachDtos.get(0).getAttachType()));//设置 高铁通信设备_年表 的值
		setAttachShowDtoSegmentData(attachDtos.get(1),isSegmentGather(ids, attachDtos.get(1).getAttachType()));//设置 高铁通信设备_月表 的值
		setAttachShowDtoSegmentData(attachDtos.get(2),isSegmentGather(ids, attachDtos.get(2).getAttachType()));//设置 高铁网管设备_年表 的值
		setAttachShowDtoSegmentData(attachDtos.get(3),isSegmentGather(ids, attachDtos.get(3).getAttachType()));//设置 高铁网管设备_月表 的值
		return attachDtos;
	}
	
	/**
	 * 
	 * setAttachShowDtoSegmentData 设置“段科室能否汇总所选的报表”的值
	 * @author quyy
	 * @param dto 
	 * @param isSegmentGather 段科室能否汇总所选的报表
	 * @return
	 */
	private AttachShowDto setAttachShowDtoSegmentData(AttachShowDto dto,boolean isSegmentGather){
		dto.setSegmentGather(isSegmentGather);
		return dto;
	}
	
	/**
	 * 
	 * isSegmentGather 根据所选车间id集合 和 报表类型段科室判断是否可以对它所选报表进行汇总
	 * @author quyy
	 * @param ids 车间id
	 * @param attachType 报表的名称字段名
	 * @return
	 */
	private boolean isSegmentGather(String ids,String attachType) {
		List<YearMonthWorkShopDto> yearMonthWorkShopDtos = yearMonthPuTieWSService.getCJExcelsByIdAndAttach(ids, attachType);
		if(CollectionUtils.isEmpty(yearMonthWorkShopDtos)) {
			return false;
		}
		return true;
	}
	
	private boolean isSegmentGatherExport(String ids,String attachType) {
		long count = reportCount(ids,attachType);
		if(count>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 指定多个车间数据id和附表类型 查找这些车间下该附表填报的数量:非草稿、存在
	 * reportCount 
	 * @param ids 车间数据id
	 * @param attachPath 附表类型
	 * @return 附表填报的数量
	 * long 
	 * @author luoyan
	 */
	private long reportCount(String ids, String attachPath) {
		
		String[] idArr = ids.split(",");
		String sql = " SELECT count(*) FROM YEAR_MONTH_PUTIE_GQ t  "
				+ "WHERE t.STATUS_ !=:status AND t.YEAR_MONTH_WORKSHOP in(:ids)"
				+ "  and t.EXIST_ =:exist and t."+YearMonthReportAttachPuTie.FILE_NAME_MAP_PUTIE.get(attachPath)+" is not null ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status",YearMonthOperStatus.WRITE_WORKAREA_DRAFT);
		paramMap.put("ids",Arrays.asList(idArr));
		paramMap.put("exist",DataExistStatus.EXIST);
		long count = jdbcTemplate.queryForObject(sql, paramMap, long.class);
		return count;
	}
	
	@Override
	public String getWorkshopName(String workAreaId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//根据工区年月报表id查询对应的工区id
		String queryOrgIdsql = "select ORG_ID_ from YEAR_MONTH_PUTIE_GQ where ID_ = :workAreaId";
		paramMap.put("workAreaId", workAreaId);
		String orgId = jdbcTemplate.queryForObject(queryOrgIdsql,paramMap,String.class);
		
		//根据工区id查询工区上级车间id
		String queryWsIdsql = "select PARENT_ID_ from CFG_BASE_ORGANIZATION where ORG_ID_ = :orgId";
		paramMap.put("orgId", orgId);
		String workshopId = jdbcTemplate.queryForObject(queryWsIdsql,paramMap,String.class);
		
		//根据车间id查询车间名字
		String queryWsNamesql = "select ORG_NAME_ from CFG_BASE_ORGANIZATION where ORG_ID_ = :workshopId";
		paramMap.put("workshopId", workshopId);
		String workshopName = jdbcTemplate.queryForObject(queryWsNamesql,paramMap,String.class);
		
		return workshopName;
		
	}
	
	class YearMonthWorkAreaMapper implements RowMapper<YearMonthPutieGQ>{
		@Override
		public YearMonthPutieGQ mapRow(ResultSet rs, int a) throws SQLException {
			YearMonthPutieGQ y = new YearMonthPutieGQ();
			y.setId(rs.getString("ID_"));
			y.setName(rs.getString("NAME_"));
			y.setYear(rs.getString("YEAR_"));
			y.setOrgId(rs.getString("ORG_ID_"));
			y.setOrgName(rs.getString("ORG_NAME_"));
			y.setStatus(rs.getString("STATUS_"));
			y.setCreateTime(rs.getTimestamp("CREATE_TIME_"));
			y.setUserId(rs.getString("USER_ID_"));
			y.setUserName(rs.getString("USER_NAME_"));
			y.setReportTime(rs.getTimestamp("REPORT_TIME_"));
			y.setFailReason(rs.getString("FAIL_REASON_"));
			y.setExist(rs.getBoolean("EXIST_"));
			y.setAttachPath8_1(rs.getString("ATTACH_PATH8_1"));
			y.setAttachName8_1(rs.getString("ATTACH_NAME8_1"));
			y.setAttachPath8_2(rs.getString("ATTACH_PATH8_2"));
			y.setAttachName8_2(rs.getString("ATTACH_NAME8_2"));
			y.setAttachPath8_3(rs.getString("ATTACH_PATH8_3"));
			y.setAttachName8_3(rs.getString("ATTACH_NAME8_3"));
			y.setAttachPath8_4(rs.getString("ATTACH_PATH8_4"));
			y.setAttachName8_4(rs.getString("ATTACH_NAME8_4"));
			y.setAttachPathExecute8_1(rs.getString("ATTACH_Execute8_1"));
			y.setAttachPathExecute8_2(rs.getString("ATTACH_Execute8_2"));
			y.setAttachPathExecute8_3(rs.getString("ATTACH_Execute8_3"));
			y.setAttachPathExecute8_4(rs.getString("ATTACH_Execute8_4"));
			YearMonthPutieCJ yearMonthWorkShop = new YearMonthPutieCJ();
			yearMonthWorkShop.setId(rs.getString("YEAR_MONTH_WORKSHOP"));
			y.setYearMonthWorkShop(yearMonthWorkShop);
			return y;
		}
	}
	
}
