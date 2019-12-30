package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.cricuitWorkOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：CircuitWorkOrderCheckImpl   
 * 类描述:  获取电路工单的指定状态的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:51:34
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:51:34   
 *
 */
@Service("circuitWorkOrderCheck")
public class CircuitWorkOrderCheckImpl implements TodoCountCommonService{

	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "circuitWorkOrder";
    
    @Autowired
    private MongoTemplate mt;
    
    //电路工单状态类,技术科待审核
    private  String flowState = "4";
	@Override
	public Long getTodoCount(String userId) {
		Criteria criteria = Criteria.where("flowState").is(flowState);
		criteria.andOperator(Criteria.where("status").is(1));
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}

}
