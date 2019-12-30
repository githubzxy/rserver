package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.task;

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
 * 类名称：TaskWorkshopReply   
 * 类描述:  查询临时任务的指定状态的数据的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午11:10:53
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午11:10:53   
 *
 */
@Service("taskWorkshopReply")
public class TaskWorkshopReply implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "IssueTaskOfWorkShop";
    
    @Autowired
    private MongoTemplate mt;
    @Resource(name = "userService")
    private UserService userService;
    //临时任务状态类
    private  String flowState = "6";
    
	@Override
	public Long getTodoCount(String userId) {
		String orgId = userService.getOrgIdByUser(userId);
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("executiveOrgId").is(orgId));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}
}
