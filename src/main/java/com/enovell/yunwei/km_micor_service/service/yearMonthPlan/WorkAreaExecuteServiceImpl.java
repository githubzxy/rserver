package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.domain.YearMonthPutieGQ;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.AttachShowUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.ExcelUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthReportFileUtil;

@Service("workAreaExecuteService")
@Transactional
public class WorkAreaExecuteServiceImpl implements WorkAreaExecuteService{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource(name="yearMonthPutieGQService")
	private YearMonthPutieGQService yearMonthWorkAreaService;
	
	@Resource(name="userService")
	private UserService userService;
	
	/** 
	 *  年表与月表计划表第3列设备处所
	 */ 
	private static final String DEPT_DEVICEPLACE_KEY = ExcelUtil.DATA_KEY_PREFIX+"2";
	/**
	 * 年表与月表计划表第4列设备名称
	 */
	private static final String DEPT_NAME_KEY = ExcelUtil.DATA_KEY_PREFIX+"3";
	/**
	 * 年表与月表计划表第5列维护工作内容
	 */
	private static final String ITEM_KEY = ExcelUtil.DATA_KEY_PREFIX+"4";
	/**
	 * 年表与月表计划表第6列—单位
	 */
	private static final String UNIT = ExcelUtil.DATA_KEY_PREFIX+"5";
	/**
	 * 年表与月表计划表第7列数量
	 */
	private static final String CONUT = ExcelUtil.DATA_KEY_PREFIX+"6";
	/**
	 * 年表计划表第9列每年次数
	 */
	private static final String ANNUAL_TIMES = ExcelUtil.DATA_KEY_PREFIX+"8";
	/**
	 * 年表计划表第11列检修月程开始列
	 */
	private static final int WORK_MONTH_START = 10;
	/**
	 * 年表计划表第22列检修月程结束列
	 */
	private static final int WORK_MONTH_END = 21;
	/**
	 * 计划表中月表的sheetNum
	 */
	private static final int MONTH_REPORT_SHEETNUM = 0;
	/**
	 * 计划表中年表的sheetNum
	 */
	private static final int YEAR_REPORT_SHEETNUM = 1;
	/** 
	 *  执行表—设备处所—第2列
	 */ 
	private static final String EXECUTE_DEVICEPLACE = "devicePlace";
	/**
	 * 执行表—设备名称—第3列
	 */
	private static final String EXECUTE_DEVICENAME = "deviceName";
	/**
	 * 执行表—工作内容—第4列
	 */
	private static final String EXECUTE_WORK_CONTENT = "workContent";
	/**
	 * 执行表—单位—第三列
	 */
	private static final String EXECUTE_UNIT = "unit";
	/**
	 * 执行表—数量—第四列
	 */
	private static final String EXECUTE_COUNT = "count";
	/**
	 * 执行表—检修日程
	 */
	private static final String EXECUTE_DATE_WORK = "dateWork";
	
	@Override
	public List<AttachShowDto> getAttachDataByWAId(String id, User user) {
		
		List<AttachShowDto> result = AttachShowUtil.getExecuteAttachDataPutie();
		//根据id查询工区业务数据的详情
		YearMonthPutieGQ workAreaData = yearMonthWorkAreaService.getWorkAreaDataById(id);
		//获取完成表路径
		String execute8_1 = getExecuteReportPath(workAreaData.getAttachPathExecute8_1(),workAreaData.getAttachPath8_1(),workAreaData.getAttachName8_2(),workAreaData.getYear(),user);
		//将完成表路径写入数据库中
		updateExecutePath(execute8_1,id);
		workAreaData = yearMonthWorkAreaService.getWorkAreaDataById(id);
		//拼装附件展示数据
		result = getAttachDataByWorkAreaData(result,workAreaData);
		return result;
	}
	
	/**
	 * getExecuteReportPath 通过计划表生成完成表，并返回完成表路径（文件名）
	 * @param executePath 完成表路径
	 * @param yearPath 年表路径
	 * @param monthPath 月表路径
	 * @param year 年份
	 * @param user 登录用户
	 * @return
	 */
	private String getExecuteReportPath(String executePath, String yearPath,
			String monthPath, String year, User user) {
		//当计划表存在，完成表不存在时，根据计划表生成完成表
		if(StringUtils.isBlank(executePath) && StringUtils.isNotBlank(monthPath)){
			executePath = createExecuteExcel(yearPath, monthPath, user, year);
		}
		return executePath;
	}
	
	/**
	 * 
	 * @Title: createExecuteExcel
	 * @Description:         根据计划表生成完成表
	 * @param yearPath  年表路径
	 * @param monthPath  月表路径
	 * @param user 登录用户
	 * @param workAreaName 工区组织机构
	 * @param year 年份
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String createExecuteExcel(String yearPath, String monthPath, User user,
			String year) {
		String orgId = user.getOrganization().getId();
		String workShopName = String.valueOf(userService.getParentOrgbyOrgId(orgId).get("ORG_NAME_"));
		//表头和数据起始行行号(从0开始)
		int headerRownum = 3;
		int dataStartRownum = 6;
		List<String> allWorkAreaExcels = new ArrayList<String>(); 
		allWorkAreaExcels.add(yearPath);
		allWorkAreaExcels.add(monthPath);
		List<List<Map>> readExecuteReportData = readExecuteReportData(allWorkAreaExcels, headerRownum, dataStartRownum, YearMonthPlanFilePathUtils.getPuTie());
		String toExecuteFilePath = YearMonthPlanFilePathUtils.getPuTie(user.getOrganization().getId(), WorkAreaExecuteServiceImpl.class);
		String modelPath = YearMonthPlanFilePathUtils.getPuTieTpl() + YearMonthReportFileUtil.KMMS_EXECUTE_TABLE;
		String completeTable = createExecuteTable(readExecuteReportData, user.getOrganization().getName(), workShopName, year, toExecuteFilePath, modelPath);
//		String completeTable = BuildTableByThymeleaf.buildCompleteTable(readExecuteReportData, xmlFilePath, user, workShopName, year);
		return completeTable;
	}
	
	private String createExecuteTable(List<List<Map>> data, String orgName,String workShopName,String year,String createPath,String modelPath){
		File toFile = new File(createPath);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		try{
			fis = new FileInputStream(modelPath);
			wb =  new HSSFWorkbook(fis);
			for(int i = 0 ; i < 12 ; i++) {
				HSSFSheet s = wb.getSheetAt(i);
				fos = new FileOutputStream(toFile);
				//对空模板插入获取的模板数据
				insertData(s, data.get(i) , orgName, workShopName, year);
				wb.write(fos);
			}
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
	@SuppressWarnings({ "unused", "rawtypes" })
	private void insertData(HSSFSheet s,List<Map> data,String orgName,String workShopName,String year){
		
		HSSFRow row2 = s.getRow(2);
		HSSFCell cell2 = row2.getCell(2);//表头
		HSSFCell cell34 = row2.getCell(34);//表头
		cell2.setCellValue(orgName);//填报单位
		cell34.setCellValue(year);//填报年份
		int i = data.size();
		List<Map> dataAllList = new ArrayList<Map>();
		insertExecuteTable(s, data);
	}
	/**
	 * 
	 * insertExecuteTable 
	 * 
	 * @param s
	 * @param data 
	 */
	private void insertExecuteTable(HSSFSheet s, List<Map> data) {
		
		List<Map> result = data;
		int resultNum = result.size();
		int rows = resultNum - 1;
		int lastRowNum = s.getLastRowNum();
		// 将第7行以下的全部下移的行数
		int shiftDownRow = lastRowNum + rows*2;
		s.shiftRows(7, 8, shiftDownRow, true, false);
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow1 = s.getRow(5 + i*2);// 从row6开始复制
			HSSFRow targetRow1 = s.createRow(7 + i*2);// 从row8开始粘贴
			copyRow(sourceRow1, targetRow1);
			HSSFRow sourceRow2 = s.getRow(6 + i*2);// 从row7开始复制
			HSSFRow targetRow2 = s.createRow(8 + i*2);// 从row9开始粘贴
			copyRow(sourceRow2, targetRow2);
			s.addMergedRegion(new CellRangeAddress(7+i*2,8+i*2,0,0));
			s.addMergedRegion(new CellRangeAddress(7+i*2,8+i*2,1,1));
			s.addMergedRegion(new CellRangeAddress(7+i*2,8+i*2,2,2));
			s.addMergedRegion(new CellRangeAddress(7+i*2,8+i*2,3,3));
			s.addMergedRegion(new CellRangeAddress(7+i*2,8+i*2,4,4));
			s.addMergedRegion(new CellRangeAddress(7+i*2,8+i*2,5,5));
		}
		int shiftUpRow = -lastRowNum;
		s.shiftRows(7 + lastRowNum + rows*2, 8 + lastRowNum + rows*2, shiftUpRow, true, false);

		for (int i = 0; i < result.size(); i++) {
			HSSFRow row = s.getRow(5 + i*2);
			HSSFRow row1 = s.getRow(6 + i*2);
			HSSFCell cell0 = row.getCell(0);// 序号
			HSSFCell cell1 = row.getCell(1);// 设备处所
			HSSFCell cell2 = row.getCell(2);// 设备名称
			HSSFCell cell3 = row.getCell(3);// 工作项目
			HSSFCell cell4 = row.getCell(4);// 单位
			HSSFCell cell5 = row.getCell(5);// 计划数量
			HSSFCell cell61 = row.getCell(6);
			HSSFCell cell62 = row1.getCell(6);
			HSSFCell cell7 = row.getCell(7);
			HSSFCell cell8 = row.getCell(8);
			HSSFCell cell9 = row.getCell(9);
			HSSFCell cell10 = row.getCell(10);
			HSSFCell cell11 = row.getCell(11);
			HSSFCell cell12 = row.getCell(12);
			HSSFCell cell13 = row.getCell(13);
			HSSFCell cell14 = row.getCell(14);
			HSSFCell cell15 = row.getCell(15);
			HSSFCell cell16 = row.getCell(16);
			HSSFCell cell17 = row.getCell(17);
			HSSFCell cell18 = row.getCell(18);
			HSSFCell cell19 = row.getCell(19);
			HSSFCell cell20 = row.getCell(20);
			HSSFCell cell21 = row.getCell(21);
			HSSFCell cell22 = row.getCell(22);
			HSSFCell cell23 = row.getCell(23);
			HSSFCell cell24 = row.getCell(24);
			HSSFCell cell25 = row.getCell(25);
			HSSFCell cell26 = row.getCell(26);
			HSSFCell cell27 = row.getCell(27);
			HSSFCell cell28 = row.getCell(28);
			HSSFCell cell29 = row.getCell(29);
			HSSFCell cell30 = row.getCell(30);
			HSSFCell cell31 = row.getCell(31);
			HSSFCell cell32 = row.getCell(32);
			HSSFCell cell33 = row.getCell(33);
			HSSFCell cell34 = row.getCell(34);
			HSSFCell cell35 = row.getCell(35);
			HSSFCell cell36 = row.getCell(36);
			HSSFCell cell37 = row.getCell(37);
			cell0.setCellValue(String.valueOf(1+i));

			cell1.setCellValue(String.valueOf(result.get(i).get("devicePlace")));
			cell2.setCellValue(String.valueOf(result.get(i).get("deviceName")));
			cell3.setCellValue(String.valueOf(result.get(i).get("workContent")));
			cell4.setCellValue(String.valueOf(result.get(i).get("unit")));
			cell5.setCellValue(String.valueOf(result.get(i).get("count")));
			cell61.setCellValue(String.valueOf("计划"));
			cell62.setCellValue(String.valueOf("完成"));
			cell7.setCellValue(String.valueOf(result.get(i).get("dateWork1")==null?"":result.get(i).get("dateWork1")));
			cell8.setCellValue(String.valueOf(result.get(i).get("dateWork2")==null?"":result.get(i).get("dateWork2")));
			cell9.setCellValue(String.valueOf(result.get(i).get("dateWork3")==null?"":result.get(i).get("dateWork3")));
			cell10.setCellValue(String.valueOf(result.get(i).get("dateWork4")==null?"":result.get(i).get("dateWork4")));
			cell11.setCellValue(String.valueOf(result.get(i).get("dateWork5")==null?"":result.get(i).get("dateWork5")));
			cell12.setCellValue(String.valueOf(result.get(i).get("dateWork6")==null?"":result.get(i).get("dateWork6")));
			cell13.setCellValue(String.valueOf(result.get(i).get("dateWork7")==null?"":result.get(i).get("dateWork7")));
			cell14.setCellValue(String.valueOf(result.get(i).get("dateWork8")==null?"":result.get(i).get("dateWork8")));
			cell15.setCellValue(String.valueOf(result.get(i).get("dateWork9")==null?"":result.get(i).get("dateWork9")));
			cell16.setCellValue(String.valueOf(result.get(i).get("dateWork10")==null?"":result.get(i).get("dateWork10")));
			cell17.setCellValue(String.valueOf(result.get(i).get("dateWork11")==null?"":result.get(i).get("dateWork11")));
			cell18.setCellValue(String.valueOf(result.get(i).get("dateWork12")==null?"":result.get(i).get("dateWork12")));
			cell19.setCellValue(String.valueOf(result.get(i).get("dateWork13")==null?"":result.get(i).get("dateWork13")));
			cell20.setCellValue(String.valueOf(result.get(i).get("dateWork14")==null?"":result.get(i).get("dateWork14")));
			cell21.setCellValue(String.valueOf(result.get(i).get("dateWork15")==null?"":result.get(i).get("dateWork15")));
			cell22.setCellValue(String.valueOf(result.get(i).get("dateWork16")==null?"":result.get(i).get("dateWork16")));
			cell23.setCellValue(String.valueOf(result.get(i).get("dateWork17")==null?"":result.get(i).get("dateWork17")));
			cell24.setCellValue(String.valueOf(result.get(i).get("dateWork18")==null?"":result.get(i).get("dateWork18")));
			cell25.setCellValue(String.valueOf(result.get(i).get("dateWork19")==null?"":result.get(i).get("dateWork19")));
			cell26.setCellValue(String.valueOf(result.get(i).get("dateWork20")==null?"":result.get(i).get("dateWork20")));
			cell27.setCellValue(String.valueOf(result.get(i).get("dateWork21")==null?"":result.get(i).get("dateWork21")));
			cell28.setCellValue(String.valueOf(result.get(i).get("dateWork22")==null?"":result.get(i).get("dateWork22")));
			cell29.setCellValue(String.valueOf(result.get(i).get("dateWork23")==null?"":result.get(i).get("dateWork23")));
			cell30.setCellValue(String.valueOf(result.get(i).get("dateWork24")==null?"":result.get(i).get("dateWork24")));
			cell31.setCellValue(String.valueOf(result.get(i).get("dateWork25")==null?"":result.get(i).get("dateWork25")));
			cell32.setCellValue(String.valueOf(result.get(i).get("dateWork26")==null?"":result.get(i).get("dateWork26")));
			cell33.setCellValue(String.valueOf(result.get(i).get("dateWork27")==null?"":result.get(i).get("dateWork27")));
			cell34.setCellValue(String.valueOf(result.get(i).get("dateWork28")==null?"":result.get(i).get("dateWork28")));
			cell35.setCellValue(String.valueOf(result.get(i).get("dateWork29")==null?"":result.get(i).get("dateWork29")));
			cell36.setCellValue(String.valueOf(result.get(i).get("dateWork30")==null?"":result.get(i).get("dateWork30")));
			cell37.setCellValue(String.valueOf(result.get(i).get("dateWork31")==null?"":result.get(i).get("dateWork31")));
		}
			
	}
	
	public static void copyRow(final HSSFRow sourceRow,final HSSFRow targetRow) {
		// TODO 合并单元格无法根据内容设置自定义高度
//		targetRow.setHeight(sourceRow.getHeight());//不设置行高（为了在单元格内填入数据时能根据内容多少自动调整行高）
//		targetRow.setHeightInPoints(100);
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
	 * @Title: updateExecutePath
	 * @Description:      将完成表路径写入数据库中
	 * @param execute8_1      完成表路径
	 * @throws 
	 * @author gaohg
	 */
	private void updateExecutePath(String execute8_1,String id) {
		String sql="UPDATE YEAR_MONTH_PUTIE_GQ "+
				 " SET ATTACH_EXECUTE8_1 =:execute8_1"
				 + " WHERE ID_ =:id ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("execute8_1",execute8_1);
		paramMap.put("id",id);
		jdbcTemplate.update(sql, paramMap);
	}
	
	/**
	 * 
	 * @Title: getAttachDataByWorkAreaData
	 * @Description:                根据工区数据封装它的附件展示信息数据
	 * @param attachDtos            工区年月报表名字
	 * @param workAreaTechData      工区数据详情
	 * @return List<AttachShowDto> (返回类型 )
	 * @throws 
	 * @author gaohg
	 */
	private List<AttachShowDto> getAttachDataByWorkAreaData(List<AttachShowDto> attachDtos,
			YearMonthPutieGQ workAreaData) {
		setAttachShowDtoEspecialData(attachDtos.get(0), workAreaData.getAttachPathExecute8_1(), workAreaData.getAttachName8_1());//设置  的值
		return attachDtos;
	}

	/**
	 * 
	 * @Title: setAttachShowDtoEspecialData
	 * @Description:  设置AttachShowDto的特殊值
	 * @param dto
	 * @param filePath
	 * @param fileNameOfPlan
	 * @return AttachShowDto (返回类型 )
	 * @throws 
	 * @author gaohg
	 */
	private AttachShowDto setAttachShowDtoEspecialData(AttachShowDto dto,String filePath,String fileNameOfPlan){
		dto.setFilePath(filePath);
		dto.setFileName(fileNameOfPlan);
		return dto;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<List<Map>> readExecuteReportData(List<String> allWorkAreaExcels,int headerRownum,int dataStartRownum,String filePath) {
		//封装执行表所需要的数据，包含12个List<Map>,分别对应12个月的数据。
		List<List<Map>> reportData = new ArrayList<List<Map>>();
		List<String> yearWorkAreaExcels = new ArrayList<String>();
		yearWorkAreaExcels.add(allWorkAreaExcels.get(0));
		//获取报表数据年表
		List<Map> yearListData = ExcelUtil.getWorkshopData(yearWorkAreaExcels, headerRownum, dataStartRownum, MONTH_REPORT_SHEETNUM, filePath);
		//移除最后一行“段负责人”数据
		yearListData.remove(yearListData.size()-1);
		List<String> monthWorkAreaExcels = new ArrayList<String>();
		monthWorkAreaExcels.add(allWorkAreaExcels.get(1));
		//获取报表数据月表
		List<Map> monthListData = getMonthReportData(monthWorkAreaExcels, headerRownum, dataStartRownum, MONTH_REPORT_SHEETNUM, filePath);
		//移除最后一行“段负责人”数据
		monthListData.remove(monthListData.size()-1);
		//获取每月对应的数据（计划年表中，检修月程对应的列为11~22列），并将其封装到list中，
		for (int i = WORK_MONTH_START; i < WORK_MONTH_END+1; i++) {
			List<Map> listTem = new ArrayList<Map>();
			listTem = yearListData;
			//获取年表数据list，再添加月表数据list
			List<Map> yearList = getCheckMonthNumData(listTem,ExcelUtil.DATA_KEY_PREFIX+i);
			//将同一月份的年表和月表数据封装到同一个list中
			List<Map> listAll = fuseYearMonthData(monthListData, yearList);
			reportData.add(listAll);
		}
		return reportData;
	}
	
	/**
	 * fuseYearMonthData 针对每一个月融合年表与月表数据源
	 * @param monthListData 年表数据
	 * @param yearList 月表数据
	 * @return
	 * @author chenShuang
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> fuseYearMonthData(List<Map> monthListData, List<Map> yearList) {
		List<Map> listAll = new ArrayList<Map>();
		listAll.addAll(monthListData);
		listAll.addAll(yearList);
		//判断当前月份数据条数是否是8的倍数，若不是，用new HashMap()增加到8的倍数，后面再跟两张空白表来给客户填临时任务的数据
//		int remainder = listAll.size()%8;
//		if(remainder!=0){   //修改:luoyan 2018-3-1 无论条数是否是8的倍数 都需要增加两张空白表+上一张表数据凑齐8
		
//			for (int i = 0; i < 16+8-remainder; i++) {
//				listAll.add(new HashMap());
//			}
		
//		}
		return listAll;
	}

	/**
	 * getCheckMonthNumData 获取检修月程数据，并将统一月程的数据封装在一个list中
	 * @param list 报表所有数据
	 * @param monthColumn 计划表中检修月程对应的列（如：cell0）
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> getCheckMonthNumData(List<Map> yearList,String monthColumn){
		
		return yearList.stream().map(m -> {
			if (StringUtils.isNotBlank(String.valueOf(m.get(monthColumn)))) {
				return filterData(m, monthColumn);
			}
			return null;
		}).filter(m -> m != null).sorted((m1, m2) -> Integer.compare(getSortValue(m1), getSortValue(m2))).map(m -> {
			return m;
		}).collect(Collectors.toList());
	}
	
	/**
	 * filterData 封装完成表数据(年表)
	 * cell0--序号，cell1--工作内容，cell2--单位，cell3--序号，cell4--数量，cell5--数据类型，
	 * cell5中，1--年表，2--半年表，4--季表
	 * @param m 计划表数据
	 * @param monthColumn 计划表中检修月程对应的列（如：cell0）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map filterData(Map m, String monthColumn) {
		Map map = new HashMap();
		//获取计划表工作内容 
		String content = String.valueOf(m.get(ITEM_KEY));
		//根据计划表的每年次数获取报表类型，1--年表，2--半年表，4--季表，6--双月表
		String reportType = StringUtils.isNotBlank(
				String.valueOf(m.get(ANNUAL_TIMES)))
						? getReportType((int) ExcelUtil.getDoubleValue(m.get(ANNUAL_TIMES))) : "";
		// 设备处所
		String devicePlace = String.valueOf(m.get(DEPT_DEVICEPLACE_KEY));
		map.put(EXECUTE_DEVICEPLACE, devicePlace);
		// 设备名称
		String deviceName = String.valueOf(m.get(DEPT_NAME_KEY));
		map.put(EXECUTE_DEVICENAME, deviceName);
		// 工作内容
		String workContent = content + reportType;
		map.put(EXECUTE_WORK_CONTENT, workContent);
		//封装执行表单位
		map.put(EXECUTE_UNIT, String.valueOf(m.get(UNIT)));
		//封装数量
		int planDataCount = (int) ExcelUtil.getDoubleValue(m.get(monthColumn));
		if (planDataCount != 0) {
			map.put(EXECUTE_COUNT, planDataCount);
		}
		//封装每年次数
		map.put(ANNUAL_TIMES, calculateData(m.get(ANNUAL_TIMES)));
		return map;
	}
	
	/**
	 * addData 处理带有运算符的字符串
	 * @param Object 待处理数据
	 * @return Object
	 * @author chenShuang
	 */
	private Object calculateData(Object o){
		try {
			String data = String.valueOf(o);
			double sum = 0;
			//加
			if (data.contains("+")) {
				//将各加数封装在同一个数组中
				String[] s = data.split("\\+");
				for (int i = 0; i < s.length; i++) {
					sum += Double.parseDouble(s[i]);
				}
				return sum;
			} 
			//减
			if (data.contains("-")) {
				//将各被减数，减数封装在同一个数组中
				String[] s = data.split("\\-");
				sum = Double.parseDouble(s[0]);
				for (int i = 1; i < s.length; i++) {
					sum -= Double.parseDouble(s[i]);
				}
				return sum;
			}
			//乘
			if (data.contains("*")) {
				//将各乘数封装在同一个数组中
				String[] s = data.split("\\*");
				sum = Double.parseDouble(s[0]);
				for (int i = 1; i < s.length; i++) {
					sum *= Double.parseDouble(s[i]);
				}
				return sum;
			}
			//除
			if (data.contains("/")) {
				//将各被除数，除数封装在同一个数组中
				String[] s = data.split("\\/");
				sum = Double.parseDouble(s[0]);
				for (int i = 1; i < s.length; i++) {
					sum /= Double.parseDouble(s[i]);
				}
				return sum;
			}
			return o;
		} catch (Exception e){
			return o;
		}
	}
	
	/**
	 * getSortValue 定义数据排序字段(顺序为：半年表--年表--季表,)
	 * @param m 对应执行表某一行的map数据
	 * @return int
	 * @author chenShuang
	 */
	private int getSortValue(@SuppressWarnings("rawtypes") Map m){
		//获取每年次数的值，如果为空，则赋值为0
		int tem = (int)ExcelUtil.getDoubleValue(m.get(ANNUAL_TIMES));
		int sortValue=0;
		switch (tem) {
		case 2:
			sortValue=1;
			break;
		case 1:
			sortValue=2;
			break;
		case 4:
			sortValue=3;
			break;
	}
		return sortValue;
		
	}
	/**
	 * getReportType 根据计划表的每年次数获取报表类型
	 * @param type 1--年表，2--半年表，4--季表，6--双月表
	 * @return String
	 * @author chenShuang
	 */
	private String getReportType(int type) {
		String reportType = null;
		switch (type) {
			case 1:
				reportType="\n（年表）";
				break;
			case 2:
				reportType="\n（半年表）";
				break;
			case 4:
				reportType="\n（季表）";
				break;
			case 6:
				reportType="\n（双月表）";
				break;
			default:
				reportType = "";
		}
		return 	reportType;
	}
	
	/**
	 * 
	 * @Title: getMonthReportData
	 * @Description:               获取计划表中月表的数据，并封装到List<Map>中
	 * @param allWorkAreaExcels    excel文件名集合 
	 * @param headerRownum         表头行号(从0开始)
	 * @param dataStartRownum      数据起始行号(从0开始)
	 * @param monthReportSheetnum  计划表中月表的sheetNum(是0)
	 * @param filePath             文件路径(不包含文件名)
	 * @return List<List<Map>> (返回类型 )
	 * @throws 
	 * @author gaohg
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> getMonthReportData(List<String> allWorkAreaExcels, int headerRownum,
			int dataStartRownum, int monthReportSheetnum, String filePath) {
		//获取报表中月表数据
		List<Map> list=ExcelUtil.getWorkshopData(allWorkAreaExcels, headerRownum, dataStartRownum, monthReportSheetnum, filePath);
		return list.stream().map(m->{
			return  filterMonthData(m);
		}).collect(Collectors.toList());
		
	}

	/**
	 * 
	 * @Title: filterMonthData
	 * @Description: 将月表数据封装成map
	 * @param m      月表数据
	 * @return Map (返回类型 )
	 * @throws 
	 * @author gaohg
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map filterMonthData(Map m) {
		
		Map map = new HashMap();
		// 设备处所
		String devicePlace = String.valueOf(m.get(DEPT_DEVICEPLACE_KEY));
		map.put(EXECUTE_DEVICEPLACE, devicePlace);
		// 设备名称
		String deviceName = String.valueOf(m.get(DEPT_NAME_KEY));
		map.put(EXECUTE_DEVICENAME, deviceName);
		//获取计划表工作内容
		String content = String.valueOf(m.get(ITEM_KEY));
		//合并设备名称及工作数据
		String workKontent = content +"\n（月表）";
		//封装执行表工作内容
		map.put(EXECUTE_WORK_CONTENT, workKontent);
		//封装执行表单位
		map.put(EXECUTE_UNIT, String.valueOf(m.get(UNIT)));
		//封装数量
		int planDataCount = (int) ExcelUtil.getDoubleValue(m.get(CONUT));
		if (planDataCount != 0) {
			map.put(EXECUTE_COUNT, planDataCount);
		}
		//封装检修日程
		for (int i = 8; i < 39; i++) {
			int planData = (int)ExcelUtil.getDoubleValue(m.get(ExcelUtil.DATA_KEY_PREFIX+i));
			if(planData!=0) {
				map.put(EXECUTE_DATE_WORK+(i-7),planData);
			}
		}
		return map;
	}
}
