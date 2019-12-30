package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.MaintainPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：SkylightMaintenanceApplyImpl   
 * 类描述:    获取维修方案指定状态的数据的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:55:09
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:55:09   
 *
 */
@Service("skylightMaintenanceApply")
public class SkylightMaintenanceApplyImpl implements TodoCountCommonService{

	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "skylightMaintenanceApply";
    
    @Autowired
    private MongoTemplate mt;
    
    //施工维修状态类
    private  String flowState = "0";
    
	@Override
	public Long getTodoCount(String userId) {
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("createUserId").is(userId));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}
}
