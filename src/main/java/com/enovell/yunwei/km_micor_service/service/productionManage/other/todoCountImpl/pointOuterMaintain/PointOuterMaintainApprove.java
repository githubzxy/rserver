package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.pointOuterMaintain;

import javax.annotation.Resource;

import org.apache.poi.hssf.record.UseSelFSRecord;
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
 * 类名称：PointOuterMaintainApprove   
 * 类描述:  查询点外维修指定状态数据的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午11:09:04
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午11:09:04   
 *
 */
@Service("pointOuterMaintainApprove")
public class PointOuterMaintainApprove implements TodoCountCommonService{

	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "pointOuterMaintain";
    
    @Autowired
    private MongoTemplate mt;
    @Resource(name = "userService")
    private UserService userService;
    //点外维修状态类
    private  String flowState = "3";
    
	@Override
	public Long getTodoCount(String userId) {
		String orgIdString = userService.getOrgIdByUser(userId);
		Criteria criteria = Criteria.where("flowState").is(flowState).and("status").is(1);
		criteria.andOperator(Criteria.where("ddkOrgId").is(orgIdString));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}

}
