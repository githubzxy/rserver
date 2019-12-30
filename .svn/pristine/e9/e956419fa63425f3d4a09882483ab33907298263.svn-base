package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.MaintainPlan;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;

@Service("communicationNetworkSafeAudit")
public class CommunicationNetworkSafeAudit implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "communicationNetworkApply";
    
    @Autowired
    private MongoTemplate mt;
    @Resource(name = "userService")
    private UserService userService;
    //跨局通信网状态类，提交
    private  String flowState = "2,3";

    @Override
	public Long getTodoCount(String userId) {
    	
		Criteria criteria = Criteria.where("flowState").in(flowState).and("status").is(1);
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}
}
