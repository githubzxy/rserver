package com.enovell.yunwei.km_micor_service.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
    NamedParameterJdbcTemplate template;

	@Override
	public List<Map<String, Object>> getBtnPers(String perId, String userId) {
//		String eql = "from Permission p  "
//				+ " left join fetch p.functionPo "
//				+ " left join fetch p.module "
//				+ " left join fetch p.roles r " 
//				+ " left join fetch r.users u "
//				+ " where u.id =:id "
//				+ " and p.parent.id = :perId "
//				+ " and p.permissionMark = :permissionMark "
//				+ " order by p.sn ";
//		
//		Query query = session.createQuery(eql);
//		query.setParameter("id", userId);
//		query.setParameter("perId",perId);
//		query.setParameter("permissionMark","yunweibtn");
//		List<Map<String, Object>> perList = query.list();
//		return perList;
//		String sql = "SELECT * FROM CFG_SAFE_PERMISSION P,CFG_SAFE_USER U,CFG_SAFE_USER_ROLE UR,CFG_SAFE_ROLE_PERMISSION RP WHERE user_id_ = :id";
//        Map<String,Object> param = new HashMap<>();
//        param.put("id",userId);
//        param.put("perId",perId);
//        param.put("permissionMark","yunweibtn");
//        return template.query(sql,param);
		
//		String perSql = " SELECT "
//				+ " p.permission_id_ as id_,'' as href_,'' as icon_,"
//				+ " p.sn_ as idx_,p.permission_mark_ as mark_,p.parent_id_ as parent_id_,1 as exist_,"
//				+ " p.permission_desc_ as text_,2 as type_ "
//				+ " FROM  "
//				+ "   cfg_safe_permission p  "
//				+ " LEFT JOIN "
//				+ "   cfg_safe_role_PERMISSION RP "
//				+ " ON "
//				+ "   P.permission_id_ = RP.PERMISSION_ID_ "
//				+ " LEFT JOIN "
//				+ "   CFG_SAFE_USER_ROLE UR "
//				+ " ON "
//				+ "   RP.ROLE_ID_ = UR.ROLE_ID_ "
//				+ " AND "
//				+ "   UR.USER_ID_ = :userId "
//				+ " WHERE  "
//				+ "   P.PARENT_ID_ = :perId ";
		String perSql = " SELECT "
				+ " p.permission_id_ as id_,"
				+ " p.sn_ as idx_,p.permission_mark_ as mark_,p.parent_id_ as parent_id_,1 as exist_,"
				+ " p.permission_desc_ as text_,2 as type_ "
				+ " FROM "
				+ "   CFG_SAFE_PERMISSION P , "
				+ "   CFG_SAFE_ROLE_PERMISSION RP , "
				+ "   CFG_SAFE_USER_ROLE UR "
				+ " WHERE "
				+ "   P.permission_id_ = RP.PERMISSION_ID_ "
				+ " AND "
				+ "   RP.ROLE_ID_ = UR.ROLE_ID_ "
				+ " AND "
				+ "   UR.USER_ID_ = :userId "
				+ " AND "
				+ "   P.PARENT_ID_ = :perId "
				+ " AND "
				+ "   P.PERMISSION_MARK_ = :mark";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("perId", perId);
		paramMap.put("mark", "yunweibtn");
		List<Map<String, Object>> perList = template.query(perSql, paramMap, new PermissionMapper());
		return perList;
		
	}

}

class PermissionMapper implements RowMapper<Map<String, Object>> {

	public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("id", rs.getString("ID_"));
		p.put("text", rs.getString("TEXT_"));
//		p.setHref(rs.getString("HREF_"));
//		p.setIcon(rs.getString("ICON_"));
//		p.setIdx(rs.getInt("IDX_"));
//		p.setMark(rs.getString("MARK_"));
//		p.setParentId(rs.getString("PARENT_ID_"));
//		p.setExist(rs.getBoolean("EXIST_"));
//		p.setText(rs.getString("TEXT_"));
//		p.setType(rs.getInt("TYPE_"));
		return p;
	}

}

class PermissionDto{
	private String id;
	private String parentId;
	private String text;
	private List<PermissionDto> children = new ArrayList<PermissionDto>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<PermissionDto> getChildren() {
		return children;
	}
	public void setChildren(List<PermissionDto> children) {
		this.children = children;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}