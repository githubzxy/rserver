package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.cricuitWorkOrder;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：CircuitWorkOrderWorkAreaReplyImpl   
 * 类描述:  获取电路工单的指定状态的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:51:46
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:51:46   
 *
 */
@Service("circuitWorkOrderWorkAreaReply")
public class CircuitWorkOrderWorkAreaReplyImpl implements TodoCountCommonService{

	/**
     * 存储表名  
     */
    private String COLLECTION_NAME = "circuitDistributeWorkOrder";
    
    @Autowired
    private MongoTemplate mt;
    @Resource(name = "userService")
    private UserService userService;
    //电路工单状态类,待工区回复
    private  String flowState = "7";
	@Override
	public Long getTodoCount(String userId) {
		String orgId = userService.getOrgIdByUser(userId);
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("distributeOrgId").is(orgId));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}

}
