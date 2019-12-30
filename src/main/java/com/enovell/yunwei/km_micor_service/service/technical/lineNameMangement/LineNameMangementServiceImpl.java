package com.enovell.yunwei.km_micor_service.service.technical.lineNameMangement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.LineNameDto;
import com.enovell.yunwei.km_micor_service.util.PagingUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.JDBCUtil;

@Service("lineNameService")
public class LineNameMangementServiceImpl implements LineNameMangementService {
	
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public String getLineDataIdByLineName(String LineName) {
		String sql="select rail_line_id_ from res_base_rail_line  where name_ = :LineName";
		HashMap<String ,Object> paramMap = new HashMap<String ,Object>();
		paramMap.put("LineName", LineName);
		String lineId=namedJdbcTemplate.queryForObject(sql, paramMap, String.class);
		return lineId;
	}
	
	@Override
	public List<String> getLineData() {
    	String sql="select t.name_ from res_base_rail_line t";
		List<String> workShopList=namedJdbcTemplate.queryForList(sql,new HashMap<>(), String.class);
		return workShopList;
	}
	
	@Override
	public GridDto<LineNameDto> getAllDataBySearch(String name, String desc, int start, int limit) {
		
		String sql = "select y.* from RES_BASE_RAIL_LINE y where 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(name)){
			sql += " and y.NAME_ like :name ";
			paramMap.put("name", "%"+name.trim()+"%");
		}
		if(StringUtils.isNotBlank(desc)){
			sql += " and y.DESC_ like :desc ";
			paramMap.put("desc", "%"+desc.trim()+"%");
		}
		List<LineNameDto> rows = namedJdbcTemplate.query(sql, paramMap, new LineNameMapper());
		GridDto<LineNameDto> gd = new GridDto<LineNameDto>();
		gd.setRows(PagingUtil.getPagingListData(rows, start, limit));
		gd.setResults(Long.valueOf(rows.size()));
		return gd;
		
	}
	
	@Override
	public ResultMsg addLine(String lineName, String desc) throws Exception {
		ResultMsg rm = new ResultMsg();
		String id = JDBCUtil.getUUID32();
		
		String sql = "INSERT INTO RES_BASE_RAIL_LINE "
			     + "(RAIL_LINE_ID_,NAME_,DESC_) "
			     + "VALUES (:id,:name,:desc)";
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("name",lineName);
		paramMap.put("desc",desc);
		namedJdbcTemplate.update(sql, paramMap);
		rm.setMsg("新增成功！");
		rm.setStatus(ResultMsg.SUC_STATUS);
		return rm;
	}

	@Override
	public LineNameDto getDataById(String id) {
		String sql = "SELECT * FROM RES_BASE_RAIL_LINE "
		          + "WHERE RAIL_LINE_ID_ =:id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		LineNameDto dto = new LineNameDto();
		paramMap.put("id",id);
		dto = namedJdbcTemplate.queryForObject(sql,paramMap,new LineNameMapper());
		return dto;
	}

	@Override
	public ResultMsg update(String id,String name,String desc) {
		
		ResultMsg rm = new ResultMsg();
		String sql = "UPDATE RES_BASE_RAIL_LINE "
		 		 + "SET NAME_ =:name,DESC_ =:desc "
		 		 + "WHERE RAIL_LINE_ID_ =:id ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		paramMap.put("name", name);
		paramMap.put("desc", desc);
		namedJdbcTemplate.update(sql, paramMap);
		rm.setMsg("修改成功！");
		rm.setStatus(ResultMsg.SUC_STATUS);
		return rm;
	}

	@Override
	public int deleteById(String id) {
		String sql = " DELETE "
				+ " FROM "
				+ " 	RES_BASE_RAIL_LINE F "
				+ " WHERE "
				+ " 	F.RAIL_LINE_ID_ = :id ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		int result = namedJdbcTemplate.update(sql, paramMap);
		return result;
	}

}
class LineNameMapper implements RowMapper<LineNameDto> {

	public LineNameDto mapRow(ResultSet rs, int idx) throws SQLException {
		
//		Map<String, Object> p = new HashMap<String, Object>();
		LineNameDto ld = new LineNameDto();
		ld.setId(rs.getString("RAIL_LINE_ID_"));
		ld.setName(rs.getString("NAME_"));
		ld.setDesc(rs.getString("DESC_"));
		return ld;
	}

}

