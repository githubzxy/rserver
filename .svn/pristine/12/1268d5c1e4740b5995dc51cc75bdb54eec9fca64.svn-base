package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.MaintainPlan;

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
 * 类名称：PointInnerSecondMaintainApproveImpl   
 * 类描述:    获取二级维修指定状态的数据的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:54:59
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:54:59   
 *
 */
@Service("pointInnerSecondMaintainApprove")
public class PointInnerSecondMaintainApproveImpl implements TodoCountCommonService{

	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "pointInnerSecondMaintain";
    
    @Autowired
    private MongoTemplate mt;
    @Resource(name = "userService")
    private UserService userService;
    //二级维修状态类，待审核
    private  String flowState = "1";
    
	@Override
	public Long getTodoCount(String userId) {
		String orgIdString = userService.getOrgIdByUser(userId);
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("parentId").is(orgIdString));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}
}
