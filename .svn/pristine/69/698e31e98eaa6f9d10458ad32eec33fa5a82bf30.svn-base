package com.enovell.yunwei.km_micor_service.service.technical.systemDevice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.SystemDeviceDto;
import com.enovell.yunwei.km_micor_service.util.PagingUtil;
@Service("systemDeviceService")
public class SystemDeviceServiceImpl implements SystemDeviceService{

	@Autowired
	NamedParameterJdbcTemplate template;
	@Override
	public GridDto<SystemDeviceDto> getDataBy(String system, String deviceType, int start, int limit) {
		String sql="SELECT id_ id,device_type_ deviceType,system_ system,remark_ remark FROM system_and_devicetype where 1=1";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(system)) {
			sql+= "AND system_ = :system ";
			paramMap.put("system", system.trim());
		}
		if (StringUtils.isNotBlank(deviceType)) {
			sql+= "AND device_type_ = :deviceType ";
			paramMap.put("deviceType",deviceType.trim());
		}
		List<SystemDeviceDto> rows = template.query(sql, paramMap,new LineNameMapper());
		GridDto<SystemDeviceDto> gd = new GridDto<SystemDeviceDto>();
		gd.setRows(PagingUtil.getPagingListData(rows, start, limit));
		gd.setResults(Long.valueOf(rows.size()));
		return gd;
	}
	
	
	
	
	class LineNameMapper implements RowMapper<SystemDeviceDto> {

		public SystemDeviceDto mapRow(ResultSet rs, int idx) throws SQLException {
			
			SystemDeviceDto ld = new SystemDeviceDto();
			ld.setDocId(rs.getString("id"));
			ld.setDeviceType(rs.getString("deviceType"));
			ld.setSystem(rs.getString("system"));
			ld.setRemark(rs.getString("remark"));
			return ld;
		}

	}




	@Override
	public SystemDeviceDto getDataById(String id) {
		String sql="SELECT id_ id,device_type_ deviceType,system_ system,remark_ remark FROM system_and_devicetype where id_=:id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		return  template.queryForObject(sql, paramMap, new LineNameMapper());
	}




	@Override
	public SystemDeviceDto updateTechnicalInfo(String id, String system, String deviceType, String remark) {
		String sql="update system_and_devicetype set device_type_ = :deviceType, system_ = :system, remark_ = :remark where id_ = :id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		paramMap.put("system", system);
		paramMap.put("deviceType", deviceType);
		paramMap.put("remark", remark);
		template.update(sql, paramMap);
		SystemDeviceDto systemDeviceDto = new SystemDeviceDto();
		systemDeviceDto.setId(id);
		systemDeviceDto.setSystem(system);
		systemDeviceDto.setDeviceType(deviceType);
		systemDeviceDto.setRemark(remark);
		return systemDeviceDto;
	}




	@Override
	public void remove(List<String> ids) {
		String sql="delete from system_and_devicetype where id_ =:id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for (int i = 0; i < ids.size(); i++) {
			paramMap.put("id", ids.get(i));
			template.update(sql, paramMap);
		}
		return ;
		
	}




//	@Override
//	public List<String> getAllSystemData() {
//		String sql="SELECT system_ system FROM system_and_devicetype group by system_ ";
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		return template.queryForList(sql, paramMap, String.class);
//	}
	
	@Override
	public List<String> getAllSystemData() {
		String sql ="select t.system_ from system_and_devicetype t ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		List<String> list=template.queryForList(sql,paramMap, String.class);
		List<String> result = removeDuplicate(list);
		return result;
	}
	
	private List<String> removeDuplicate(List<String> list){  
		List<String> result = new ArrayList<String>();  
        for(int i=0;i<list.size();i++){  
            if(!result.contains(list.get(i))){  
            	result.add(list.get(i));  
            }  
        }  
        return result;  
    } 




	@Override
	public List<String> getAllDeviceData(String system) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String sql;
		if(StringUtils.isNotBlank(system)) {
			 sql="select t.device_type_ from system_and_devicetype t where t.system_ = :system";
			paramMap.put("system",system);
		}else {
			 sql="select t.device_type_ from system_and_devicetype t";
		}
		return template.queryForList(sql, paramMap, String.class);
	}


	@Override
	public SystemDeviceDto addInfo(String system, String deviceType, String remark) {
		String sql="insert into system_and_devicetype(id_,device_type_,system_,remark_) values(:id,:deviceType,:system,:remark)";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String uuId = UUID.randomUUID().toString().replace("-", "");
		paramMap.put("id", uuId);
		paramMap.put("system", system);
		paramMap.put("deviceType", deviceType);
		paramMap.put("remark", remark);
		template.update(sql, paramMap);
		SystemDeviceDto systemDeviceDto = new SystemDeviceDto();
		systemDeviceDto.setId(uuId);
		systemDeviceDto.setSystem(system);
		systemDeviceDto.setDeviceType(deviceType);
		systemDeviceDto.setRemark(remark);
		return systemDeviceDto;
	}
	
}
