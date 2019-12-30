package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.constructionImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：ConstructionAuditImpl   
 * 类描述:  获取施工方案的指定状态的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午10:51:06
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午10:51:06   
 *
 */
@Service("constructionAudit")
public class ConstructionAuditImpl implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "constructionPlan";
    
    @Autowired
    private MongoTemplate mt;
    
    //电路工单状态类,待段科室审批
    private  String auditStatus = "1";
    
	@Override
	public Long getTodoCount(String userId) {
		Criteria criteria = Criteria.where("auditStatus").is(auditStatus).and("status").is(1);
		return mt.count(new Query(criteria), COLLECTION_NAME);
	}
}
