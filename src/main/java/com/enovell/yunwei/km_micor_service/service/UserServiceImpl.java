package com.enovell.yunwei.km_micor_service.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.system.common.domain.Organization;
import com.enovell.system.common.domain.User;
import com.enovell.yunwei.enocommon.utils.DataExistStatus;

/**
 * kunmingTXD
 *
 * @author bili
 * @date 18-11-20
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public Map<String, Object> getUserById(String id) {
        String sql = "SELECT * FROM "+USER_TABLE_NAME+" WHERE user_id_ = :id";
        Map<String,Object> param = new HashMap<>();
        param.put("id",id);
        return template.queryForMap(sql,param);
    }

    @Override
    public Map<String, Object> getOrgbyUserId(String id) {
    	//String sql = "SELECT org.* FROM "+USER_TABLE_NAME+" u LEFT JOIN "+ORG_TABLE_NAME+" org ON u.organization_id_ = org.org_id_ WHERE user_id_ = :id";
    	String sql = "SELECT * FROM "+USER_TABLE_NAME+" u LEFT JOIN "+ORG_TABLE_NAME+" org ON u.organization_id_ = org.org_id_ WHERE user_id_ = :id";
        Map<String,Object> param = new HashMap<>();
        param.put("id",id);
        return template.queryForMap(sql,param);
    }

    @Override
    public String getOrgIdByUser(String userId) {
        String sql = "SELECT org.org_id_ FROM "+USER_TABLE_NAME+" u LEFT JOIN "+ORG_TABLE_NAME+" org ON u.organization_id_ = org.org_id_ WHERE user_id_ = :id";
        Map<String,Object> param = new HashMap<>();
        param.put("id",userId);
        try {
            return template.queryForObject(sql, param, String.class);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    
    /**
     * 根据车间名称获取工区数据
     */
    @Override
	public List<Map<String, Object>> getDeparts(String workShopName) {
		String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.parent_id_=(select t.org_id_ from cfg_base_organization t where t.org_name_=:workShopName)";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("workShopName", workShopName);
        List<Map<String, Object>> departList=template.query(sql, paramMap,new CCOrganizationMapper());
		return departList;
	}
    
    /**
     * 只获取车间数据
     */
    @Override
	public List<Map<String, Object>> getWorkShops() {
		String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.type_ = :type";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", "1502");
		List<Map<String, Object>> workShopList=template.query(sql,paramMap,new CCOrganizationMapper());
		return workShopList;
	}
    class CCOrganizationMapper implements RowMapper<Map<String, Object>> {

		public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("value", rs.getString("ORG_ID_"));
			p.put("text", rs.getString("ORG_NAME_"));
			return p;
		}
	}
    
    /**
     * 只获取车间及工区的数据
     */
    @Override
	public List<Map<String, Object>> getShopAndDepart(String pid, String curId) {
//		String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.type_ = :type";
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("type", "1502");
//		List<Map<String, Object>> workShopList=template.query(sql,paramMap,new CCOrganizationMapper());
//		return workShopList;
		String sql = "";
        String startSql = "SELECT T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_,COUNT(O.ORG_ID_) AS CHILDCOUNT"+
                " FROM CFG_BASE_ORGANIZATION T LEFT JOIN CFG_BASE_ORGANIZATION O"+
                " ON T.ORG_ID_ = O.PARENT_ID_"+
//                " AND O.EXIST_ = '1'"+
                " WHERE T.PARENT_ID_ = :pid AND T.DELETE_STATE_=1 AND T.TYPE_<>:type";
//                " AND T.EXIST_ = '1'";
        String endSql = " GROUP BY T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_"+
                " ORDER BY T.ORGINDEX_";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("pid", pid);
        paramMap.put("type", 1501);

        if(StringUtils.isNotBlank(curId)){
            sql = startSql + " AND T.ORG_ID_ <> :curId " + endSql;
            paramMap.put("curId", curId);
        }else{
            sql = startSql + endSql;
        }
        List<Map<String,Object>> childList = template.query(sql, paramMap, new ShopAndDepartMapper());
        return childList;
	}
    class ShopAndDepartMapper implements RowMapper<Map<String,Object>> {
        public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
            Map<String,Object> o = new HashMap<>();
            o.put("id",rs.getString("ORG_ID_"));
            o.put("name",rs.getString("ORG_NAME_"));
            o.put("type",rs.getInt("TYPE_"));
            o.put("desc",rs.getString("DESC_"));
            int childcount = rs.getInt("CHILDCOUNT");
            if(childcount == 0){
                o.put("isdept",true);
            }else{
                o.put("isdept",false);
            }
            return o;
        }

    }
    
    /**
     * 只获取科室+车间级别的数据
     */
    @Override
	public List<Map<String, Object>> getCadreAndShop(String pid, String curId) {
//		String sql="select t.org_id_,t.org_name_ from cfg_base_organization t where t.type_ = :type";
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("type", "1502");
//		List<Map<String, Object>> workShopList=template.query(sql,paramMap,new CCOrganizationMapper());
//		return workShopList;
		String sql = "";
        String startSql = "SELECT T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_,COUNT(O.ORG_ID_) AS CHILDCOUNT"+
                " FROM CFG_BASE_ORGANIZATION T LEFT JOIN CFG_BASE_ORGANIZATION O"+
                " ON T.ORG_ID_ = O.PARENT_ID_"+
//                " AND O.EXIST_ = '1'"+
                " WHERE T.PARENT_ID_ = :pid AND T.DELETE_STATE_=1 AND T.TYPE_<>:type";
//                " AND T.EXIST_ = '1'";
        String endSql = " GROUP BY T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_"+
                " ORDER BY T.ORGINDEX_";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("pid", pid);
        paramMap.put("type", 1503);

        if(StringUtils.isNotBlank(curId)){
            sql = startSql + " AND T.ORG_ID_ <> :curId " + endSql;
            paramMap.put("curId", curId);
        }else{
            sql = startSql + endSql;
        }
        List<Map<String,Object>> childList = template.query(sql, paramMap, new CadreAndShopMapper());
        return childList;
	}
    class CadreAndShopMapper implements RowMapper<Map<String,Object>> {
        public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
            Map<String,Object> o = new HashMap<>();
            o.put("id",rs.getString("ORG_ID_"));
            o.put("name",rs.getString("ORG_NAME_"));
            o.put("type",rs.getInt("TYPE_"));
            o.put("desc",rs.getString("DESC_"));
            int childcount = rs.getInt("CHILDCOUNT");
//            if(childcount == 0){
                o.put("isdept",true);
//            }else{
//                o.put("isdept",false);
//            }
            return o;
        }
    }
    
    /**
     * 获取完整的组织机构树
     */
    @Override
    public List<Map<String, Object>> getChildrenByPidAndCurId(String pid, String curId) {
        String sql = "";
        String startSql = "SELECT T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_,COUNT(O.ORG_ID_) AS CHILDCOUNT"+
                " FROM CFG_BASE_ORGANIZATION T LEFT JOIN CFG_BASE_ORGANIZATION O"+
                " ON T.ORG_ID_ = O.PARENT_ID_"+
//                " AND O.EXIST_ = '1'"+
                " WHERE T.PARENT_ID_ = :pid AND T.DELETE_STATE_=1";
//                " AND T.EXIST_ = '1'";
        String endSql = " GROUP BY T.ORG_ID_,T.ORG_NAME_,T.ORGINDEX_,T.PARENT_ID_,T.TYPE_,T.DESC_"+
                " ORDER BY T.ORGINDEX_";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("pid", pid);

        if(StringUtils.isNotBlank(curId)){
            sql = startSql + " AND T.ORG_ID_ <> :curId " + endSql;
            paramMap.put("curId", curId);
        }else{
            sql = startSql + endSql;
        }
        List<Map<String,Object>> childList = template.query(sql, paramMap, new OrganizationMapper());
        return childList;
    }
    class OrganizationMapper implements RowMapper<Map<String,Object>> {
        public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
            Map<String,Object> o = new HashMap<>();
            o.put("id",rs.getString("ORG_ID_"));
            o.put("name",rs.getString("ORG_NAME_"));
            o.put("type",rs.getInt("TYPE_"));
            o.put("desc",rs.getString("DESC_"));
            int childcount = rs.getInt("CHILDCOUNT");
            if(childcount == 0){
                o.put("isdept",true);
            }else{
                o.put("isdept",false);
            }
            return o;
        }
    }
    class OrgMapper implements RowMapper<Map<String,Object>> {
    	public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
    		Map<String,Object> o = new HashMap<>();
    		o.put("id",rs.getString("ORG_ID_"));
    		o.put("name",rs.getString("ORG_NAME_"));
    		o.put("type",rs.getInt("TYPE_"));
    		o.put("desc",rs.getString("DESC_"));
    		return o;
    	}
    }
    
	@Override
	public List<Map<String, Object>> getUserByOrgId(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getParentOrgById(String parentIdString) {
		 String sql = "SELECT * FROM "+ORG_TABLE_NAME+" WHERE org_id_ = :id";
	        Map<String,Object> param = new HashMap<>();
	        param.put("id",parentIdString);
	        return template.queryForMap(sql,param);
	}
	
	@Override
	public List<String> getPerDatasByUserId(String userId) {
		String sql =    " select p.permission_id_ from cfg_safe_role_permission p " + 
						" where p.role_id_ in ( " + 
						"    select r.role_id_ from cfg_safe_user_role r " + 
						"    left join cfg_safe_user u on r.user_id_ = u.user_id_ " + 
						"    where u.user_id_ =:userId " + 
						" )";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", userId);
		List<String> result = template.queryForList(sql, paramMap,String.class);
		return result;
	}

	@Override
	public Map<String, Object> getParentOrgbyOrgId(String id) {
		String sql = " select * from cfg_base_organization o "
				+ " where o.ORG_ID_ =  "
				+ " (select PARENT_ID_ from cfg_base_organization t " + " where t.ORG_ID_ = :childrenId "
				+ " and t.DELETE_STATE_=:deleteState)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("childrenId", id);
		paramMap.put("deleteState", DataExistStatus.EXIST);
		return template.queryForMap(sql, paramMap);

	}

	@Override
	public List<Map<String, Object>> getChildrenByParentId(String parentId) {
		String sql = " select * from CFG_BASE_ORGANIZATION t "
				+ " where t.PARENT_ID_=:parentId "
				+ " and t.DELETE_STATE_=:deleteState ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentId", parentId);
		paramMap.put("deleteState", DataExistStatus.EXIST);
		List<Map<String,Object>> childList = template.query(sql, paramMap, new OrgMapper());
		return childList;
	}

	@Override
	public User getUserObjectByUserId(String userId) {
		String sql = "SELECT "
				+ "	    U.USER_ID_, "
				+ "	    U.USER_NAME_, "
				+ "	    U.ORGANIZATION_ID_, "
				+ "	    O.ORG_NAME_, "
				+ "	    O.TYPE_, "
				+ "	    O.PARENT_ID_, "
				+ "	    O.ORGINDEX_ "
				+ "	FROM "
				+ "	    CFG_SAFE_USER U, "
				+ "	    CFG_BASE_ORGANIZATION O "
				+ "	WHERE U.ORGANIZATION_ID_ = O.ORG_ID_ "
				+ "	AND   U.USER_ID_ = :userId "
				+ "	AND   U.USER_DELSTATE_ = :userDelstate ";
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("userDelstate", DataExistStatus.EXIST);
		User user = template.queryForObject(sql, paramMap, new UserMapper());
		return user;
	}
	
}

class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User u = new User();
		u.setId(rs.getString("USER_ID_"));
		u.setName(rs.getString("USER_NAME_"));
		Organization o = new Organization();
		o.setId(rs.getString("ORGANIZATION_ID_"));
		o.setName(rs.getString("ORG_NAME_"));
		o.setType(rs.getInt("TYPE_"));
		o.setOrgIndex(rs.getInt("ORGINDEX_"));
		u.setOrganization(o);
		return u;
	}
	
}
