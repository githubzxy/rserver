
/**   
 * 文件名：YearMonthWorkAreaExecuteImpl.java    
 * @author quyy  
 * 日期：2017年11月8日 上午11:07:18      
 *   
 */

package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthOperStatus;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttachPuTie;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.domain.YearMonthPutieGQ;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.AttachShowUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.PoiExcelSheetCopy;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthCopyFileUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthReportFileUtil;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthWorkAreaExecuteImpl   
 * 类描述: 年月报表执行工区业务实现类 
 * 创建人：quyy 
 * 创建时间：2017年11月8日 上午11:07:18 
 *    
 */
@Transactional
@Service("yearMonthPuTieGQExecuteService")
public class YearMonthPuTieGQExecuteImpl implements YearMonthPuTieGQExecuteService{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name="yearMonthPutieGQService")
	private YearMonthPutieGQService yearMonthWorkAreaService;
	
	@Resource(name="yearMonthPutieCJExecuteService")
	private YearMonthPutieCJExecuteService yearMonthWorkshopExecuteService;
	
	@Resource(name="autoBuildCompleteTableService")
	private AutoBuildCompleteTableService autoBuildCompleteTableService;
	
	@Override
	public List<AttachShowDto> getAttachDataByGQId(String id, User user) {
		List<AttachShowDto> result = AttachShowUtil.getExecuteAttachDataPutie();
		//根据id查询工区业务数据的详情
		YearMonthPutieGQ workAreaData = yearMonthWorkAreaService.getWorkAreaDataById(id);
		//首先判断工区的执行报表数据是否存在，如果不存在，复制一份模板文件赋值给它
		if(StringUtils.isBlank(workAreaData.getAttachPathExecute8_1())) {
			try {
				setExecuteFileForWorkArea(workAreaData.getId(), user);
				workAreaData = yearMonthWorkAreaService.getWorkAreaDataById(id);
				//根据计划表生成完成表
				createPlanExcel(workAreaData, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//拼装附件展示数据
		result = getAttachDataByWorkAreaData(result,workAreaData);
		return result;
	}
	
	/**
	 * setExecuteFileForWorkArea 设置工区执行文件的文件名并存入数据库中
	 * @param id 工区业务数据id
	 * @param user 登录用户
	 */
	private void setExecuteFileForWorkArea(String id, User user) {
		String orgId = user.getOrganization().getId();
		Map<String,Object> attachPath = new HashMap<String, Object>();
		int i = 1;
		for(Entry<String, String> entry : YearMonthReportAttachPuTie.FILE_MAP_EXECUTE.entrySet()){
			//复制后的路径放入每个对应的附表
			String filePath = YearMonthPlanFilePathUtils.getPuTie(orgId, YearMonthPuTieGQExecuteImpl.class);
			StringBuilder fileName =  new StringBuilder(filePath.split("/")[filePath.split("/").length - 1]);
			fileName.insert(fileName.length() - 4, i);
			attachPath.put(entry.getKey(), fileName);
			i++;
		}
		//准备状态、时间和组织机构等
		String sql = "update YEAR_MONTH_PUTIE_GQ SET ";
		sql = creatDynamicSql(sql);
		sql += "  where id_=:id ";
		//根据模板数量的不同，动态拼装sql
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(attachPath);
		paramMap.put("id", id);
		jdbcTemplate.update(sql, paramMap);
	}
	
	//根据计划表生成完成表
	private void createPlanExcel(YearMonthPutieGQ workAreaData, User user){
		
		//overhaulStartCellNum  计划表检修日程或月程开始列数-1
		//overhalEndCellNum     计划表检修日程或月程结束列数-1
		//cellNum               计划表的总列数
		//herderStartRowNum     计划表表头开始行数-1
		//dataStartRowNum       计划表数据开始行数-1
		int overhaulStartCellNum8_1 = 10;
		int overhalEndCellNum8_1 = 21;
		int cellNum8_1 = 23;
		int herderStartRowNum8_1 = 2;
		int dataStartRowNum8_1 = 4;

		int overhaulStartCellNum8_2 = 8;
		int overhalEndCellNum8_2 = 38;
		int cellNum8_2 = 39;
		int herderStartRowNum8_2 = 2;
		int dataStartRowNum8_2 = 4;

		int overhaulStartCellNum8_3 = 8;
		int overhalEndCellNum8_3 = 42;
		int cellNum8_3 = 44;
		int herderStartRowNum8_3 = 2;
		int dataStartRowNum8_3 = 6;

		int overhaulStartCellNum8_4 = 8;
		int overhalEndCellNum8_4 = 38;
		int cellNum8_4 = 40;
		int herderStartRowNum8_4 = 2;
		int dataStartRowNum8_4 = 6;
		//报表绝对路径
		String planFilePath = YearMonthPlanFilePathUtils.getPuTie();
		String headerFilePath = YearMonthPlanFilePathUtils.getPuTieTpl();//+"txYearPuTieExecute8_1_temp.xls";
		String completeFilePath = YearMonthPlanFilePathUtils.getPuTie();
		String completeExcelByPlanExcelPath = autoBuildCompleteTableService.getCompleteExcelByPlanExcel(planFilePath+workAreaData.getAttachPath8_1(),headerFilePath+YearMonthReportFileUtil.TXYEAR_TEMPLATE_EXECUTE_PUTIE, completeFilePath+workAreaData.getAttachPathExecute8_1(), overhaulStartCellNum8_1, overhalEndCellNum8_1, cellNum8_1, herderStartRowNum8_1, dataStartRowNum8_1);
		updateCopyFileA2(user, completeExcelByPlanExcelPath, false);
		if(workAreaData.getAttachName8_2()!=null) {
			String comletePuTie8_2ByPlanExcelPath = autoBuildCompleteTableService.getComletePuTie8_2ByPlanExcel(planFilePath+workAreaData.getAttachPath8_2(), headerFilePath+YearMonthReportFileUtil.TXMONTH8_2_EXECUTE_PUTIE, completeFilePath+workAreaData.getAttachPathExecute8_2(), overhaulStartCellNum8_2, overhalEndCellNum8_2, cellNum8_2, herderStartRowNum8_2, dataStartRowNum8_2);
			PoiExcelSheetCopy.getMonthReportMould12(completeFilePath+workAreaData.getAttachPathExecute8_2(), completeFilePath+workAreaData.getAttachPathExecute8_2(),true);
			updateCopyFileA2(user, comletePuTie8_2ByPlanExcelPath, true);
		}
		if(workAreaData.getAttachName8_3()!=null) {
			String comletePuTie8_3ByPlanExcelPath = autoBuildCompleteTableService.getComletePuTie8_3ByPlanExcel(planFilePath+workAreaData.getAttachPath8_3(), headerFilePath+YearMonthReportFileUtil.TXMONTH8_3_EXECUTE_PUTIE, completeFilePath+workAreaData.getAttachPathExecute8_3(), overhaulStartCellNum8_3, overhalEndCellNum8_3, cellNum8_3, herderStartRowNum8_3, dataStartRowNum8_3);
			PoiExcelSheetCopy.getMonthReportMould12(completeFilePath+workAreaData.getAttachPathExecute8_3(), completeFilePath+workAreaData.getAttachPathExecute8_3(),true);
			updateCopyFileA2(user, comletePuTie8_3ByPlanExcelPath, true);
		}
		if(workAreaData.getAttachName8_4()!=null) {
			String comletePuTie8_3ByPlanExcelPath = autoBuildCompleteTableService.getComletePuTie8_3ByPlanExcel(planFilePath+workAreaData.getAttachPath8_4(), headerFilePath+YearMonthReportFileUtil.TXMONTH8_4_EXECUTE_PUTIE, completeFilePath+workAreaData.getAttachPathExecute8_4(), overhaulStartCellNum8_4, overhalEndCellNum8_4, cellNum8_4, herderStartRowNum8_4, dataStartRowNum8_4);
			PoiExcelSheetCopy.getMonthReportMould12(completeFilePath+workAreaData.getAttachPathExecute8_4(), completeFilePath+workAreaData.getAttachPathExecute8_4(),true);
			updateCopyFileA2(user, comletePuTie8_3ByPlanExcelPath, true);
		}
	}
	
	/**
	 * 
	 * updateCopyFileA2 将工区、车间名字和创建时间填入已复制的Excel表格指定区域A2
	 *
	 * @author lidt
	 * @date 2018年12月13日 上午10:21:58 
	 * @param user 填报人
	 * @param copyFilePath 复制后的文件路径
	 * @param isMonth 是月表
	 */
	private void updateCopyFileA2(User user, String copyFilePath, Boolean isMonth) {
		String orgId = user.getOrganization().getId();
		String orgName = user.getOrganization().getName();
		// 填报人(工区)父组织机构(车间)信息
//		Organization parentOrg = organizationService.getParentOrgByChildrenId(orgId);
		Map<String, Object> map = userService.getParentOrgbyOrgId(orgId);
		String workShopName = String.valueOf(map.get("ORG_NAME_"));
		if (!isMonth) {
			YearMonthCopyFileUtil.updateCopyFileGaoTie(orgName, workShopName, copyFilePath, "A2");
		} else {
			for (int i = 0; i < 12; i++) {
				YearMonthCopyFileUtil.updateCopyFilePuTie(orgName, workShopName, copyFilePath, i);
			}
		}
	}
	
	@Override
	public void getMonthReportMould(String fileName){
		//获取模板路径（包含12个sheet页）
		String mouldPath=getMonthReportFilePath(fileName).get("mouldPath");
		//获取模板路径（包含1个sheet页）
		String singleMouldPath=getMonthReportFilePath(fileName).get("singleMouldPath");
//		CopyExcelSheetByJxl.getMonthReportMould12(singleMouldPath, mouldPath,true);
	}
	
	/**
	 * getMonthReportFilePath 通过文件名获取相应的文件路
	 * @param fileName 文件名
	 * @return
	 * @author chenshuang
	 */
	private Map<String,String> getMonthReportFilePath(String fileName){
		//模板路径（包含12个sheet页）
		String mouldPath=null;
		//模板路径（包含1个sheet页）
		String singleMouldPath=null;
		String filePath = YearMonthPlanFilePathUtils.getPuTieTpl();
		if(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2_MOULD_1.equals(fileName)){
			singleMouldPath = filePath+YearMonthReportFileUtil.EXECUTE_MOULD8_2_1;
			mouldPath = filePath+YearMonthReportFileUtil.EXECUTE_MOULD8_2_12;
		}
		if(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3_MOULD_1.equals(fileName)){
			singleMouldPath = filePath+YearMonthReportFileUtil.EXECUTE_MOULD8_3_1;
			mouldPath = filePath+YearMonthReportFileUtil.EXECUTE_MOULD8_3_12;
		}
		if(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4_MOULD_1.equals(fileName)){
			singleMouldPath = filePath+YearMonthReportFileUtil.EXECUTE_MOULD8_4_1;
			mouldPath = filePath+YearMonthReportFileUtil.EXECUTE_MOULD8_4_12;
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("singleMouldPath", singleMouldPath);
		map.put("mouldPath", mouldPath);
		return map;
	}
	private String creatDynamicSql(String sql) {
		for(Entry<String, String> entry : YearMonthReportAttachPuTie.FILE_MAP_EXECUTE.entrySet()){
			sql += entry.getKey()+"=:"+entry.getKey()+",";
		}
		return sql.substring(0,sql.length()-1);
	}
	
	/**
	 * 
	 * getAttachDataByWorkAreaData 根据工区数据封装它的附件展示信息数据
	 * @author quyy
	 * @param workAreaData 年月报表工区业务数据
	 * @return
	 */
	private List<AttachShowDto> getAttachDataByWorkAreaData(List<AttachShowDto> attachDtos,YearMonthPutieGQ workAreaData){
			setAttachShowDtoEspecialData(attachDtos.get(0), workAreaData.getAttachPathExecute8_1(),workAreaData.getAttachName8_1());//设置 高铁通信设备_年表 的值
			setAttachShowDtoEspecialData(attachDtos.get(1), workAreaData.getAttachPathExecute8_2(),workAreaData.getAttachName8_2());//设置 高铁通信设备_月表 的值
			setAttachShowDtoEspecialData(attachDtos.get(2), workAreaData.getAttachPathExecute8_3(),workAreaData.getAttachName8_3());//设置 高铁网管设备_年表 的值
			setAttachShowDtoEspecialData(attachDtos.get(3), workAreaData.getAttachPathExecute8_4(),workAreaData.getAttachName8_4());//设置 高铁网管设备_月表 的值
		return attachDtos;
	}
	/**
	 * 
	 * setAttachShowDtoEspecialData 设置AttachShowDto的特殊值
	 * @author quyy
	 * @param dto 
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	private AttachShowDto setAttachShowDtoEspecialData(AttachShowDto dto,String filePath,String fileNameOfPlan){
		dto.setFilePath(filePath);
		dto.setFileName(fileNameOfPlan);
		return dto;
	}
	
	@Override
	public void changeWorkareaStatus(String id, String status) {
		String sql="update YEAR_MONTH_PUTIE_GQ set STATUS_=:status "
				+ "where id_=:id ";
		Map<String,Object> paramMap =new HashMap<String,Object>();
		paramMap.put("status", status);
		paramMap.put("id", id);
		jdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public void reportToWorkshop(String id) {
//		1.改变工区业务数据状态
		String sql="UPDATE YEAR_MONTH_PUTIE_GQ "+
				 " SET STATUS_=:status,REPORT_TIME_=:reportTime, "+
				 " FAIL_REASON_=:failReason WHERE ID_ =:id ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id",id);
		paramMap.put("status",YearMonthOperStatus.WORKAREA_EXECUTE_REPORT);
		paramMap.put("reportTime",new Date());
		paramMap.put("failReason", "");
		jdbcTemplate.update(sql, paramMap);
		YearMonthPutieGQ yearMonthWorkArea=yearMonthWorkAreaService.getWorkAreaDataById(id);
		String yearMonthWorkshopId=yearMonthWorkArea.getYearMonthWorkShop().getId();
//		2.改变车间业务数据状态
		yearMonthWorkshopExecuteService.changeWorkshopStatus(yearMonthWorkshopId,YearMonthOperStatus.WORKSHOP_EXECUTE_AUDIT);
	}
}
