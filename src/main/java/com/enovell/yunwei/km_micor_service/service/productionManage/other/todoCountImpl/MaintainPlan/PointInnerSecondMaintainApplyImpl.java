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
 * 类名称：PointInnerSecondMaintainApplyImpl   
 * 类描述:  获取二级维修指定状态的数据的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:53:53
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:53:53   
 *
 */
@Service("pointInnerSecondMaintainApply")
public class PointInnerSecondMaintainApplyImpl implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "pointInnerSecondMaintain";
    
    @Autowired
    private MongoTemplate mt;
    
    //二级维修状态类，草稿，回退
    private  String flowState = "0";
    
	@Override
	public Long getTodoCount(String userId) {
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("userId").is(userId));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}
}
