package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.cricuitWorkOrder;

import java.util.Map;

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
 * 类名称：CircuitWorkOrderWorkshopReplyImpl   
 * 类描述:  获取电路工单的指定状态的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:51:57
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:51:57   
 *
 */
@Service("circuitWorkOrderWorkshopReply")
public class CircuitWorkOrderWorkshopReplyImpl implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "circuitIssueWorkOrder";
    
    @Autowired
    private MongoTemplate mt;
    @Resource(name = "userService")
    private UserService userService;
    //电路工单状态类,待车间回复
    private  String flowState = "6";
	@Override
	public Long getTodoCount(String userId) {
		String orgId = userService.getOrgIdByUser(userId);
//		System.out.println("组织机构"+orgId);
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("executiveOrgId").is(orgId));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}

}
