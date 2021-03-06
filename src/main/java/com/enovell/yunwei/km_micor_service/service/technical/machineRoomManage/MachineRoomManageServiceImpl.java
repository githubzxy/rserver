package com.enovell.yunwei.km_micor_service.service.technical.machineRoomManage;

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
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.MachineRoomDto;
import com.enovell.yunwei.km_micor_service.service.technical.lineNameMangement.LineNameMangementService;
import com.enovell.yunwei.km_micor_service.util.PagingUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.JDBCUtil;

@Service("machineRoomManageService")
public class MachineRoomManageServiceImpl implements MachineRoomManageService {
	
	@Resource(name = "lineNameService")
	private LineNameMangementService lineNameMangementService;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
 	public List<String> getMachineRoomsByLine(String line) {
		String lineId = lineNameMangementService.getLineDataIdByLineName(line);
		if(StringUtils.isBlank(lineId)){
			return null;
		}
    	String sql = "select t.machine_name_ from res_base_rail_machineroom t where t.line_id_ = :lineId";
 		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("lineId",lineId);
 		List<String> roomList = namedJdbcTemplate.queryForList(sql, paramMap, String.class);
 		return roomList;
 	}
	
	@Override
	public List<String> getMachineRoomData() {
    	String sql="select t.machine_name_ from res_base_rail_machineroom t";
		List<String> workShopList=namedJdbcTemplate.queryForList(sql, new HashMap<>(), String.class);
		return workShopList;
	}

	@Override
	public GridDto<MachineRoomDto> getAllDataBySearch(String machineCode, String machineName,String name, String remark, int start, int limit) {
		
		String sql = "select t.*,r.name_ from res_base_rail_machineroom t left join res_base_rail_line r on t.line_id_= r.rail_line_id_ where 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(machineCode)){
			sql += " and t.MACHINE_CODE_ like :machineCode ";
			paramMap.put("machineCode", "%"+machineCode.trim()+"%");
		}
		if(StringUtils.isNotBlank(machineName)){
			sql += " and t.MACHINE_NAME_ like :machineName ";
			paramMap.put("machineName", "%"+machineName.trim()+"%");
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and r.NAME_ like :name ";
			paramMap.put("name", "%"+name.trim()+"%");
		}
		if(StringUtils.isNotBlank(remark)){
			sql += " and t.REMARK_ like :remark ";
			paramMap.put("remark", "%"+remark.trim()+"%");
		}
		sql += " order by t.commissioning_date_ desc";
		List<MachineRoomDto> rows = namedJdbcTemplate.query(sql, paramMap, new MachineRoomMapper());
		GridDto<MachineRoomDto> gd = new GridDto<MachineRoomDto>();
		gd.setRows(PagingUtil.getPagingListData(rows, start, limit));
		gd.setResults(Long.valueOf(rows.size()));
		return gd;
		
	}
	
	@Override
	public ResultMsg addLine(MachineRoomDto dto) throws Exception {
		ResultMsg rm = new ResultMsg();
		String id = JDBCUtil.getUUID32();
		String lineId = lineNameMangementService.getLineDataIdByLineName(dto.getName());
		String sql = "INSERT INTO RES_BASE_RAIL_MACHINEROOM "
			     + "(ID_,MACHINE_CODE_,MACHINE_NAME_,MAINTENANCE_ORG_,COMMISSIONING_DATE_,REMARK_,LINE_ID_) "
			     + "VALUES (:id,:machineCode,:machineName,:maintenanceOrg,:commissioningDate,:remark,:lineId)";
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("machineCode",dto.getMachineCode());
		paramMap.put("machineName",dto.getMachineName());
		paramMap.put("maintenanceOrg",dto.getMaintenanceOrg());
		paramMap.put("commissioningDate",dto.getCommissioningDate());
		paramMap.put("remark",dto.getRemark());
		paramMap.put("lineId",lineId);
		namedJdbcTemplate.update(sql, paramMap);
		rm.setMsg("新增成功！");
		rm.setStatus(ResultMsg.SUC_STATUS);

		return rm;
	}

	@Override
	public MachineRoomDto getDataById(String id) {
		String sql = "select t.*,r.name_ from res_base_rail_machineroom t left join res_base_rail_line r on t.line_id_= r.rail_line_id_  "
		          + "where ID_ =:id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		MachineRoomDto dto = new MachineRoomDto();
		paramMap.put("id",id);
		dto = namedJdbcTemplate.queryForObject(sql,paramMap,new MachineRoomMapper());
		return dto;
	}

	@Override
	public ResultMsg update(MachineRoomDto dto) {
		
		ResultMsg rm = new ResultMsg();
		String lineId = lineNameMangementService.getLineDataIdByLineName(dto.getName());
		String sql = "UPDATE RES_BASE_RAIL_MACHINEROOM "
		 		 + "SET MACHINE_CODE_ =:machineCode,MACHINE_NAME_ =:machineName,MAINTENANCE_ORG_=:maintenanceOrg,COMMISSIONING_DATE_=:commissioningDate,REMARK_=:remark,LINE_ID_=:lineId "
		 		 + "WHERE ID_ =:id ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", dto.getId());
		paramMap.put("machineCode", dto.getMachineCode());
		paramMap.put("machineName", dto.getMachineName());
		paramMap.put("maintenanceOrg", dto.getMaintenanceOrg());
		paramMap.put("commissioningDate", dto.getCommissioningDate());
		paramMap.put("remark", dto.getRemark());
		paramMap.put("lineId", lineId);
		namedJdbcTemplate.update(sql, paramMap);
		rm.setMsg("修改成功！");
		rm.setStatus(ResultMsg.SUC_STATUS);
		return rm;
	}

	@Override
	public int deleteById(String id) {
		String sql = " DELETE "
				+ " FROM "
				+ " 	RES_BASE_RAIL_MACHINEROOM F "
				+ " WHERE "
				+ " 	F.ID_ = :id ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		int result = namedJdbcTemplate.update(sql, paramMap);
		return result;
	}

}
class MachineRoomMapper implements RowMapper<MachineRoomDto> {

	public MachineRoomDto mapRow(ResultSet rs, int idx) throws SQLException {
		
//		Map<String, Object> p = new HashMap<String, Object>();
		MachineRoomDto ld = new MachineRoomDto();
		ld.setId(rs.getString("ID_"));
		ld.setMachineCode(rs.getString("MACHINE_CODE_"));
		ld.setMachineName(rs.getString("MACHINE_NAME_"));
		ld.setMaintenanceOrg(rs.getString("MAINTENANCE_ORG_"));
		ld.setCommissioningDate(rs.getString("COMMISSIONING_DATE_"));
		ld.setRemark(rs.getString("REMARK_"));
		ld.setLineId(rs.getString("LINE_ID_"));
		ld.setName(rs.getString("NAME_"));
		
		return ld;
	}

}

