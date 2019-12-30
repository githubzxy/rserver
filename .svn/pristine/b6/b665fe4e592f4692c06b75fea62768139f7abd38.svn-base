package com.enovell.yunwei.km_micor_service.service;

import java.util.List;
import java.util.Map;

import com.enovell.system.common.domain.User;

/**
 * kunmingTXD
 * 用户Service
 * @author bili
 * @date 18-11-20
 */
public interface UserService {
    String USER_TABLE_NAME = "cfg_safe_user";
    String ORG_TABLE_NAME = "cfg_base_organization";
    /**
     * 获取用户
     * @param id 用户id
     * @return 用户数据
     */
    Map<String,Object> getUserById(String id);

    /**
     * 获取用户的组织机构
     * @param id 用户id
     * @return 用户组织机构数据
     */
    Map<String,Object> getOrgbyUserId(String id);

    /**
     * 获取组织机构Id
     */
    String getOrgIdByUser(String userId);
    /**
     *
     * getChildrenByPidAndCurId 通过Pid（父id）和不等于curId查询其children
     * @param pid 父id
     * @param curId 选中节点id
     */
    List<Map<String,Object>> getChildrenByPidAndCurId(String pid, String curId);
    
    /**
	 * 只获取车间级别的数据
	 */
	List<Map<String, Object>> getWorkShops();
	
	/**
	 * 根据车间获取工区级别数据
	 */
	List<Map<String, Object>> getDeparts(String workShopName);
    
	/**
	 * 获取科室和车间数据
	 * @param pid
	 * @param curId
	 */
    List<Map<String,Object>> getCadreAndShop(String pid, String curId);
    
    /**
     * 获取车间和工区数据
     * @param pid
     * @param curId
     */
    List<Map<String, Object>> getShopAndDepart(String pid, String curId);
    
    /**
     * 根据组织机构Id获取用户
     */
    List<Map<String,Object>> getUserByOrgId(String orgId);

	Map<String, Object> getParentOrgById(String parentIdString);

	/**
	 * 
	 * getPerDatasByUserId 获取三级菜单权限数据
	 * @param userId
	 * @return
	 */
	List<String> getPerDatasByUserId(String userId);
	
	/**  
	 * getParentOrgbyOrgId 根据id获取父机构信息
	 * 
	 * @param id 组织机构id
	 * @return 
	 */  
	Map<String,Object> getParentOrgbyOrgId(String id);
	
	/**
	 * 
	 * getChildrenByParentId 根据父机构id获取子机构
	 * @param parentId
	 * @return
	 */
	List<Map<String, Object>> getChildrenByParentId(String parentId);
	
	/**
	 * 
	 * getUserObjectByUserId 根据用户ID获取用户对象（包含所有信息）
	 * 
	 * @param userId 用户ID
	 * @return
	 */
	User getUserObjectByUserId(String userId);
	
}
