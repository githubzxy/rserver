package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.List;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;

public interface YearMonthPutieAnysisService {
	
	/**
	 * 汇总段下所选车间的数据
	* anysisSegment 
	* @param ids 所选数据的id
	* @param attachPath 由前端静态数据传的附表路径，对应数据库附表路径字段名
	* @param user 登录用户
	* @return ResultMsg
	 */
	public ResultMsg anysisSegment(String ids,String attachPath,User user);
	
	/**
	 * 
	 * workShopSummary 车间汇总工区数据
	 * @param id 车间业务数据的id
	 * @param user 当前登陆用户
	 * @param attachPath 汇总的年月报表类型
	 * @return
	 */
	public ResultMsg workShopSummary(String id, User user, String attachPath);
	
	/**
	* 根据id和附表类型获取车间的Excel
	* getCJExcelsByIdAndAttach 
	* @param ids
	* @param attachPath
	* @return
	* List<String>
	* @author luoyan
	 */
	public List<String> getCJExcelsByIdAndAttach(String ids, String attachPath); 

}
