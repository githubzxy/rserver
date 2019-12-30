package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.dispathRoot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：DispathBacklogImpl   
 * 类描述:  调度命令待办
 * 创建人：zhouxingyu
 * 创建时间：2019年6月12日 下午4:34:42
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月12日 下午4:34:42   
 *
 */
@Service("DispathBacklogImpl")
public class DispathBacklogImpl implements TodoCountCommonService{

	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "DispathRoot";

    @Autowired
    NamedParameterJdbcTemplate template;
    @Resource(name = "userService")
    private UserService userService;
    //创建调令，处于草稿状态，待提交
    private  String submitEd = "3,5";
	@Override
	public Long getTodoCount(String userId) {
		Map<String, Object> orgMap = userService.getOrgbyUserId(userId);
		String orgNameString =  (String) orgMap.get("org_name_");
		String hql = " SELECT COUNT(d.id_) FROM DispathRoot d "
				+ " WHERE d.organization in :orgNameString AND d.exist_ = 1 and d.status_ = 3 or d.status_ = 5";
		 Map<String,Object> param = new HashMap<>();
	     param.put("orgNameString",orgNameString);
		return template.queryForObject(hql, param, Long.class);
	}
}
