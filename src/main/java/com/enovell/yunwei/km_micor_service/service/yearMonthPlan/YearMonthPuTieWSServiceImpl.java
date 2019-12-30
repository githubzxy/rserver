package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthOperStatus;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttachPuTie;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkAreaDto;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkShopDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.util.DataExistStatus;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.JDBCUtil;

/**
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthPuTieWSServiceImpl
 * 类描述:  年月报表编制车间业务实现类
 * 创建人：chenshuang 
 * 创建时间：2017年12月1日 下午5:08:09  
 *
 */
@Transactional
@Service("yearMonthPuTieWSService")
public class YearMonthPuTieWSServiceImpl implements YearMonthPuTieWSService {
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "yearMonthPutieGQService")
	private YearMonthPutieGQService yearMonthPutieGQService;
	
	@Override
	public String add(String name, String year, String orgId, String orgName) {
		// 先判断车间是否已存在该数据，判断依据：组织机构、年份、未删除
		boolean e = existData("YEAR_MONTH_PUTIE_CJ",year,orgId,null);
		if(e){	//存在该数据,改变状态
			//获取该数据id
			String id = getWorkshopId(year,orgId,DataExistStatus.EXIST);
			return updateWorkshopStatus(id);
		}else{	//不存在该数据,新增
			return addWorkshopData(name, year, orgId, orgName);
		}
	}
	
	/**
	 * getWorkshopId 根据年份及部门id查询车间报表的id
	 * @param year 年份
	 * @param orgId 部门id
	 * @param exist 状态（数据是否存在）
	 * @return String 车间业务数据id
	 */
	private String getWorkshopId(String year,String orgId,boolean exist) {
		String sql = "select id_ from YEAR_MONTH_PUTIE_CJ "
				 + " WHERE YEAR_=:year and ORG_ID_=:orgId AND EXIST_=:exist";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("year",year);
		paramMap.put("orgId",orgId);
		paramMap.put("exist",DataExistStatus.EXIST);
		String id = namedJdbcTemplate.queryForObject(sql, paramMap, String.class);
		return id;
	}
	
	/**
	 * updateWorkshopStatus 更新工区数据(新增数据时用)
	 * @param id 车间业务数据id
	 * @return
	 */
	private String updateWorkshopStatus(String id) {
		String updateSql=" UPDATE YEAR_MONTH_PUTIE_CJ "
				 + " SET STATUS_=:status , "
				 + "ATTACH_PATH8_1=:attachPath8_1 ,ATTACH_PATH8_2=:attachPath8_2,"
				 + "ATTACH_PATH8_3=:attachPath8_3,ATTACH_PATH8_4=:attachPath8_4 "
				 + " WHERE id_=:id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status",YearMonthOperStatus.WRITE_WORKSHOP_AUDIT);
		paramMap.put("id",id);
		paramMap.put("attachPath8_1","");
		paramMap.put("attachPath8_2","");
		paramMap.put("attachPath8_3","");
		paramMap.put("attachPath8_4","");
		namedJdbcTemplate.update(updateSql, paramMap);
		return id;
	}
	
	/**
	 * addWorkshopData 新增一条车间报表数据
	 * @param name 报表名称
	 * @param year 年份
	 * @param orgId 部门id
	 * @param orgName 部门名称
	 * @return
	 */
	private String addWorkshopData(String name,String year,String orgId,String orgName) {
		String addSql = "insert into YEAR_MONTH_PUTIE_CJ ("
				+ "ID_,EXIST_,NAME_,ORG_ID_,"
				+ "ORG_NAME_,STATUS_,YEAR_"
				+ ") values (:id,:exist,:name,:orgId,"
				+ ":orgName,:status,:year)";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String id = JDBCUtil.getUUID32();
		paramMap.put("id",id );
		paramMap.put("exist", DataExistStatus.EXIST);
		paramMap.put("name", name);
		paramMap.put("orgId", orgId);
		paramMap.put("orgName", orgName);
		paramMap.put("status", YearMonthOperStatus.WRITE_WORKSHOP_AUDIT);
		paramMap.put("year", year);
		namedJdbcTemplate.update(addSql, paramMap);
		return id;
	}
	
	
	@Override
	public boolean existData(String table,String year,String orgId,String id) {
		String sql = "select count(id_) from "+table 
				+ " where YEAR_=:year and ORG_ID_=:orgId and EXIST_=:exist";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(id)){
			sql += " and id_!=:id ";
			paramMap.put("id", id);
		}
		paramMap.put("exist", DataExistStatus.EXIST);
		paramMap.put("orgId", orgId);
		paramMap.put("year", year);
		Long count = namedJdbcTemplate.queryForObject(sql, paramMap, Long.class);
		if(count<=0){
			return false; //不存在该数据
		}
		return true; //存在该数据
	}
	
	
	@Override
	public void reportToDuan(String id){
		String status=YearMonthOperStatus.WRITE_WORKSHOP_REPORT;//"车间上报"状态
		String sql="update YEAR_MONTH_PUTIE_CJ set STATUS_=:status, "
				+ "REPORT_TIME_=:reportTime "
				+ "where ID_ =:id ";
		Date reportTime=new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("id", id);
		paramMap.put("reportTime", reportTime);
		namedJdbcTemplate.update(sql,paramMap);
	}

	@Override
	public List<YearMonthWorkShopDto> getDatas(YearMonthWorkAreaDto dto,String orgId) {
		List<YearMonthWorkShopDto> result = new ArrayList<YearMonthWorkShopDto>();
		result = getWorkShopDatas(dto,orgId);
		for (YearMonthWorkShopDto yearMonthWorkShopDto : result) {
			//查询当前车间下所有工区的年月报表业务数据
			yearMonthWorkShopDto.setChildren(getWorkAreaDatas(dto,yearMonthWorkShopDto.getId(),yearMonthWorkShopDto));
		}
		return result;
	}
	/**
	 * 
	 * getWorkAreaDatas 查询当前车间下所有工区的年月报表业务数据
	 * @author quyy
	 * @param dto 查询条件
	 * @param pid 当前车间id
	 * @param workShopData 年月报表（车间dto）
	 * @return 
	 */
	private List<YearMonthWorkShopDto> getWorkAreaDatas(YearMonthWorkAreaDto dto,String pid,YearMonthWorkShopDto workShopData){
		List<YearMonthWorkShopDto> result = new ArrayList<YearMonthWorkShopDto>();
		//查询出已上报的工区数据
		result = getWritedWorkAreaDatas(dto, pid);
		//查找出该车间下的所有工区数据 
//		List<Organization> organizations = organizationService.getChildrenByParentId(workShopData.getOrgId());
		List<Map<String, Object>> organizations = userService.getChildrenByParentId(workShopData.getOrgId());
		//找出没有填报的工区
		List<Map<String, Object>> notWritedWorkArea = getNotWritedWorkArea(result, organizations);
		//新增没有填报的工区数据
		for (Map<String, Object> organization : notWritedWorkArea) {
			YearMonthWorkShopDto yearMonthWorkShopDto = new YearMonthWorkShopDto();
			yearMonthWorkShopDto.setStatus(YearMonthOperStatus.WRITE_WORKAREA_NOTWRITED);
			yearMonthWorkShopDto.setYear(workShopData.getYear());
			yearMonthWorkShopDto.setOrgId(String.valueOf(organization.get("id")));
			yearMonthWorkShopDto.setOrgName(String.valueOf(organization.get("name")));
			result.add(yearMonthWorkShopDto);
		}
		result = setIsdeptValue(result, true);
		return result;
	}
	
	/**
	 * 
	 * getNotWritedWorkArea 根据车间下面的所有工区数据和该车间下已填报的工区数据来查找该车间下面没有填报的工区数据
	 * @author quyy
	 * @param writedDatas 已填报的工区数据
	 * @param organizations 所有的工区数据
	 * @return
	 */
	private List<Map<String, Object>> getNotWritedWorkArea(List<YearMonthWorkShopDto> writedDatas,List<Map<String, Object>> organizations){
		Iterator<Map<String, Object>> iterator = organizations.iterator();
        while(iterator.hasNext()){
        	Map<String, Object> organization = iterator.next();
        	for (YearMonthWorkShopDto yearMonthWorkShopDto : writedDatas) {
				if(yearMonthWorkShopDto.getOrgId().equals(organization.get("id"))) {
					iterator.remove();
				}
			}
        }
		return organizations;
	}
	/**
	 * 
	 * getWorkAreaDatas 查询当前车间下所有工区的年月报表业务数据
	 * @author quyy
	 * @param dto 查询条件YearMonthWorkAreaDto
	 * @param orgId 当前车间id
	 * @return
	 */
	private List<YearMonthWorkShopDto> getWritedWorkAreaDatas(YearMonthWorkAreaDto dto,String pid){
		List<YearMonthWorkShopDto> result = new ArrayList<YearMonthWorkShopDto>();
		String sql = " SELECT * FROM YEAR_MONTH_PUTIE_GQ t WHERE 1=1 AND t.YEAR_MONTH_WORKSHOP =:pid  and t.EXIST_ =1 ";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(dto.getName())){
			sql += " AND t.NAME_ like :name";
			paramMap.put("name", "%"+dto.getName()+"%");
		}
		if(StringUtils.isNotBlank(dto.getYear())){
			sql += " AND t.YEAR_ = :year";
			paramMap.put("year", dto.getYear());
		}
		if(!YearMonthOperStatus.EXECUTE_PAGE.equals(dto.getPage())){
			sql += " AND t.STATUS_ !=:status";
			paramMap.put("status",YearMonthOperStatus.WRITE_WORKAREA_DRAFT);
		}
		if(YearMonthOperStatus.EXECUTE_PAGE.equals(dto.getPage())){
			sql += " AND cast(t.STATUS_ as int) >=:status";
			paramMap.put("status",YearMonthOperStatus.WORKAREA_EXECUTE_REPORT);
		}
		paramMap.put("pid",pid);
		sql += " ORDER BY t.REPORT_TIME_ desc ";
		result = namedJdbcTemplate.query(sql,paramMap, new YearMonthWorkShopDtoMapper());
		return result;
	}
	
	/**
	 * 
	 * setIsdeptValue 设置YearMonthWorkShopDto中的iddept字段的值
	 * @author quyy
	 * @param yearMonthWorkShopDtos
	 * @param idDeptValue 是否是子节点
	 * @return
	 */
	private List<YearMonthWorkShopDto> setIsdeptValue(List<YearMonthWorkShopDto> yearMonthWorkShopDtos,boolean idDeptValue){
		for (YearMonthWorkShopDto yearMonthWorkShopDto : yearMonthWorkShopDtos) {
			yearMonthWorkShopDto.setIsdept(idDeptValue);
		}
		return yearMonthWorkShopDtos;
	}
	/**
	 * 
	 * getWorkShopDatas 查询当前车间的年月报表业务数据
	 * @author quyy
	 * @param dto 年月表前端查询条件YearMonthWorkAreaDto
	 * @param orgId 部门id
	 * @return
	 */
	private List<YearMonthWorkShopDto> getWorkShopDatas(YearMonthWorkAreaDto dto,String orgId){
		List<YearMonthWorkShopDto> result = new ArrayList<YearMonthWorkShopDto>();
		String sql = " SELECT * FROM YEAR_MONTH_PUTIE_CJ T WHERE 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql = createSql(sql, dto, paramMap);
		if(StringUtils.isNotBlank(orgId)){
			sql += " and t.ORG_ID_ =:orgId ";
			paramMap.put("orgId", orgId);
		}
		if(YearMonthOperStatus.EXECUTE_PAGE.equals(dto.getPage())){
			sql += " and cast (t.STATUS_ as int) >=:status1 ";
			paramMap.put("status1", YearMonthOperStatus.WORKSHOP_EXECUTE_AUDIT);
		}
		sql += " ORDER BY t.YEAR_ DESC ";
		result = namedJdbcTemplate.query(sql,paramMap, new YearMonthWorkShopDtoMapper());
		result = setIsdeptValue(result, false);
		return result;
	}
	
	private String createSql(String sql, YearMonthWorkAreaDto dto, Map<String, Object> paramMap) {
		if(StringUtils.isNotBlank(dto.getName())){
			sql += " and t.NAME_ like :name";
			paramMap.put("name", "%"+dto.getName().trim()+"%");
		}
		if(StringUtils.isNotBlank(dto.getYear())){
			sql += " and t.YEAR_ = :year";
			paramMap.put("year", dto.getYear());
		}
		if(StringUtils.isNotBlank(dto.getStatus())){
			sql += " and t.STATUS_ = :status";
			paramMap.put("status", dto.getStatus());
		}
		sql += " and t.EXIST_ = :exist ";
		paramMap.put("exist", DataExistStatus.EXIST);
		return sql;
	}


	@Override
	public boolean changeWorkshopStatus(List<String> ids, String status) {
		String sql=" update YEAR_MONTH_PUTIE_CJ set STATUS_=:status "
				+ "  WHERE ID_ in (:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("ids", ids);
		namedJdbcTemplate.update(sql, paramMap);
		return true;
	}
	/**
	 * updateWorkAreaStatus 改变工区业务数据的状态（重载方法）
	 * @param ids 工区id
	 * @param status 状态
	 * @return
	 */
	public boolean updateWorkAreaStatus(List<String> ids, String status) {
		return updateWorkAreaStatus(ids, status, null);
	}
	
	@Override
	public boolean updateWorkAreaStatus(List<String> ids, String status,String reasons) {
		String sql=" update YEAR_MONTH_PUTIE_GQ set STATUS_=:status ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(reasons)) {
			sql += " ,FAIL_REASON_ =:reasons";
			paramMap.put("reasons", reasons);
		}
		sql += "  WHERE ID_ in (:ids)";
		
		paramMap.put("status", status);
		paramMap.put("ids", ids);
		namedJdbcTemplate.update(sql, paramMap);
		return true;
	}


	@Override
	public boolean auditBatchPass( List<String> workAreaIds, String status) {
		//批量修改工区数据的状态
		if(CollectionUtils.isNotEmpty(workAreaIds)) {
			updateWorkAreaStatus(workAreaIds, status);
			yearMonthPutieGQService.updateWorkShopStatus(workAreaIds, status);
		}
		return true;
	}


	@Override
	public boolean auditBatchNotPass( List<String> workAreaIds, String status,
			String reasons) {
		//批量修改工区数据的状态
		if(CollectionUtils.isNotEmpty(workAreaIds)) {
			updateWorkAreaStatus(workAreaIds, status,reasons);
			yearMonthPutieGQService.updateWorkShopStatus(workAreaIds,status);
		}
		return true;
	}
	
	@Override
	public String getAttachPath(String id, String attachType) {
		String sql = "select "+attachType+ " from YEAR_MONTH_PUTIE_CJ where ID_ = :id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		String attachPath = namedJdbcTemplate.queryForObject(sql,paramMap,String.class);
		return attachPath;
	}


	@Override
	public List<YearMonthWorkShopDto> getSegmentDatas(YearMonthWorkAreaDto dto, String orgId) {
		List<YearMonthWorkShopDto> result = getSegmentResult(dto,orgId);
		for (YearMonthWorkShopDto yearMonthWorkShopDto : result) {
			//查询当前车间下所有工区的年月报表业务数据
			yearMonthWorkShopDto.setChildren(getWorkAreaDatas(dto,yearMonthWorkShopDto.getId(),yearMonthWorkShopDto));
		}
		return result;
	}
	/**
	 * 
	 * getSegmentResult 根据查询条件查询工区数据
	 * @param dto 报表名称、年份、状态查询页面
	 * @param orgId 部门id
	 * @return
	 */
	private List<YearMonthWorkShopDto> getSegmentResult(YearMonthWorkAreaDto dto,String orgId){
		List<YearMonthWorkShopDto> result = new ArrayList<YearMonthWorkShopDto>();
		String sql = " SELECT * FROM YEAR_MONTH_PUTIE_CJ T WHERE 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql = createSql(sql, dto, paramMap);
		if(StringUtils.isNotBlank(orgId)){
			sql += " and t.ORG_ID_ in(:orgId) ";
			String[] ids = orgId.split(",");
			paramMap.put("orgId", Arrays.asList(ids));
		}
		if(YearMonthOperStatus.EXECUTE_PAGE.equals(dto.getPage())){
			sql += " and cast(t.STATUS_ as int) >=:status1 ";
			paramMap.put("status1", YearMonthOperStatus.WORKSHOP_EXECUTE_AUDIT);
		}
		sql += " ORDER BY t.YEAR_ DESC ";
		result = namedJdbcTemplate.query(sql,paramMap, new YearMonthWorkShopDtoMapper());
		for (YearMonthWorkShopDto yearMonthWorkShopDto : result) {
			yearMonthWorkShopDto.setIsdept(false);
		}
		return result;
	}


	@Override
	public void updateAttachFilePath(String id,String attachPath,String attachPathValue) {
		String sql=" update YEAR_MONTH_PUTIE_CJ t set t."+attachPath+"=:attachPath where t.id_ =:id ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attachPath", attachPathValue);
		paramMap.put("id", id);
		namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public void deleteBatch(List<String> ids) {
		String delGQsql = "update YEAR_MONTH_PUTIE_GQ set EXIST_=:exist "
				+ "where ID_ in (:ids) ";
		String delCJsql = "update YEAR_MONTH_PUTIE_CJ set EXIST_=:exist "
				+ "where ID_ in (:ids) ";
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("exist",DataExistStatus.NOT_EXIST);
		paramMap.put("ids",ids);
		namedJdbcTemplate.update(delGQsql, paramMap);
		namedJdbcTemplate.update(delCJsql, paramMap);
	}


	@Override
	public List<YearMonthWorkShopDto> getCJExcelsByIdAndAttach(String ids, String attachPath) {
		String[] idArr = ids.split(",");
		String sql = " select * from YEAR_MONTH_PUTIE_CJ where ID_ in(:ids) and "+attachPath+" is not null";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", Arrays.asList(idArr));
		List<YearMonthWorkShopDto> result  = namedJdbcTemplate.query(sql, paramMap, new YearMonthWorkShopDtoMapper());
		return result;
	}



	@Override
	public YearMonthWorkShopDto getYearMonthWorkShopDtoById(String id) {
		String sql = " select * from YEAR_MONTH_PUTIE_CJ where ID_ =:id and EXIST_=:exist";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("exist", DataExistStatus.EXIST);
	    YearMonthWorkShopDto result  = namedJdbcTemplate.queryForObject(sql, paramMap, new YearMonthWorkShopDtoMapper());
		return result;
	}


	@Override
	public List<YearMonthWorkShopDto> getCJAndGQExcelsByIdAndAttach(String ids, String attachPath) {
		//查询车间数据
		List<YearMonthWorkShopDto> result  = getCJExcelsByIdAndAttach(ids, attachPath);
		
		for (YearMonthWorkShopDto yearMonthWorkShopDto : result) {
			YearMonthWorkAreaDto dto = new YearMonthWorkAreaDto();
			//查询当前车间下所有工区的年月报表业务数据 
			yearMonthWorkShopDto.setChildren(getWritedWorkAreaDatas(dto , yearMonthWorkShopDto.getId()));
		}
		return result;
	}
	
	@Override
	public List<YearMonthWorkShopDto> getExcelsByIdAndAttachPutie(String ids, String attachPath) {
		String[] idArr = ids.split(",");
		List<YearMonthWorkShopDto> result = new ArrayList<YearMonthWorkShopDto>();
		for (int i = 0; i < idArr.length; i++) {
			//根据id获取车间数据
			YearMonthWorkShopDto workShopDto = getYearMonthWorkShopDtoById(idArr[i]);
			//查询当前车间下所有工区的年月报表业务数据 
			workShopDto.setChildren(getWorkAreaDtoByPid(workShopDto.getId(),attachPath));
			result.add(workShopDto);
		}
		return result;
	}
	/**
	 * 根据车间数据id和附表获取工区数据：指定附表已填报且不为草稿
	 * getWorkAreaDtoByPid 
	 * @param id
	 * @param attachPath
	 * @return
	 * List<YearMonthWorkShopDto>
	 * @author luoyan
	 */
	private List<YearMonthWorkShopDto> getWorkAreaDtoByPid(String id, String attachPath) {
		
		String sql = " SELECT * FROM YEAR_MONTH_PUTIE_GQ t  "
				+ "WHERE t.STATUS_ !=:status AND t.YEAR_MONTH_WORKSHOP =:id "
				+ "  and t.EXIST_ =:exist and t."+YearMonthReportAttachPuTie.FILE_NAME_MAP_PUTIE.get(attachPath)+" is not null ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status",YearMonthOperStatus.WRITE_WORKAREA_DRAFT);
		paramMap.put("id",id);
		paramMap.put("exist",DataExistStatus.EXIST);
		return namedJdbcTemplate.query(sql,paramMap, new YearMonthWorkShopDtoMapper());
	}


}
class YearMonthWorkShopDtoMapper implements RowMapper<YearMonthWorkShopDto>{
	@Override
	public YearMonthWorkShopDto mapRow(ResultSet rs, int a) throws SQLException {
		YearMonthWorkShopDto y = new YearMonthWorkShopDto();
		y.setId(rs.getString("ID_"));
		y.setName(rs.getString("NAME_"));
		y.setYear(rs.getString("YEAR_"));
		y.setOrgId(rs.getString("ORG_ID_"));
		y.setOrgName(rs.getString("ORG_NAME_"));
		y.setStatus(rs.getString("STATUS_"));
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
		y.setAttachPathExecute8_1(rs.getString("ATTACH_EXECUTE8_1"));
		y.setAttachPathExecute8_2(rs.getString("ATTACH_EXECUTE8_2"));
		y.setAttachPathExecute8_3(rs.getString("ATTACH_EXECUTE8_3"));
		y.setAttachPathExecute8_4(rs.getString("ATTACH_EXECUTE8_4"));
		return y;
	}
	
	
	
}


