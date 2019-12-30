package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.dispathRoot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：DispathRootPageImpl   
 * 类描述:  安全管理-调度命令-创建调令待办提示
 * 创建人：zhouxingyu
 * 创建时间：2019年6月12日 下午3:21:08
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月12日 下午3:21:08   
 *
 */
@Service("dispathRootPageTodo")
public class DispathRootPageImpl implements TodoCountCommonService{

	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "DispathRoot";

    @Autowired
    NamedParameterJdbcTemplate template;
    //创建调令，处于草稿状态，待提交
    private  String submitEd = "1";
	@Override
	public Long getTodoCount(String userId) {
		String hql = " SELECT COUNT(d.id_) FROM DispathRoot d "
				+ " WHERE d.status_ = :submitEd  AND d.user_id_ = :userId AND d.exist_ = 1";
		 Map<String,Object> param = new HashMap<>();
	     param.put("userId",userId);
	     param.put("submitEd",submitEd);
		return template.queryForObject(hql, param, Long.class);
	}

}
