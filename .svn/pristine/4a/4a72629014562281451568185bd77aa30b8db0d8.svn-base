package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.cricuitWorkOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：CricuitWorkOrderApplyImpl   
 * 类描述:  获取电路工单的指定状态的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:52:07
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:52:07   
 *
 */
@Service("circuitWorkOrderApply")
public class CricuitWorkOrderApplyImpl implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "circuitWorkOrder";
    
    @Autowired
    private MongoTemplate mt;
    
    //电路工单状态类
    private  String flowState = "0";
    
	@Override
	public Long getTodoCount(String userId) {
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("createUserId").is(userId));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}

}
