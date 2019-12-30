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
 * 类名称：CircuitWorkOrderApproveImpl   
 * 类描述:  获取电路工单的指定状态的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:51:21
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:51:21   
 *
 */
@Service("circuitWorkOrderApprove")
public class CircuitWorkOrderApproveImpl implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "";
    
    @Autowired
    private MongoTemplate mt;
    
    //电路工单状态类,段领导待审批
    private  String submitEd = "4";
	@Override
	public Long getTodoCount(String userId) {
		//Criteria criteria = Criteria.where("status").is(submitEd);
		//return mt.count(new Query(criteria), COLLECTION_NAME);
		//暂时不适用
		return 0l;
	}

}
