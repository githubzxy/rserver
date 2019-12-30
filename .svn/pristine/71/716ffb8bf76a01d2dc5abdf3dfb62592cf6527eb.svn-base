package com.enovell.yunwei.km_micor_service.service.productionManage.other.todoCountImpl.pointOuterMaintain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoCountCommonService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：PointOuterMaintainQuery   
 * 类描述:  查询点外维修指定状态数据的数量统计
 * 创建人：zhouxingyu
 * 创建时间：2019年5月23日 上午11:09:22
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月23日 上午11:09:22   
 *
 */
@Service("pointOuterMaintainQuery")
public class PointOuterMaintainQuery implements TodoCountCommonService{
	/**
     * 存储表名
     */
    private String COLLECTION_NAME = "";
    
    @Autowired
    private MongoTemplate mt;
    
    //点外维修状态类
    private  String submitEd = "4";
    
	@Override
	public Long getTodoCount(String userId) {
		//Criteria criteria = Criteria.where("status").is(submitEd);
		//return mt.count(new Query(criteria), COLLECTION_NAME);
		//暂时不用
		return 0L;
	}

}
