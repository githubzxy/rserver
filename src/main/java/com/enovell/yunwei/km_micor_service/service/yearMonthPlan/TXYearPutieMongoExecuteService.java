
/**   
 * 文件名：YearMonthMongoTestService.java    
 * @author quyy  
 * 日期：2017年9月22日 下午5:06:07      
 *   
 */

package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.List;

import com.enovell.system.common.domain.User;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthMongoTestService   
 * 类描述: 普铁通信年表汇总 
 * 创建人：cs
 * 创建时间：2017年9月22日 下午5:06:07 
 *    
 */
public interface TXYearPutieMongoExecuteService {

	/**
	 * 
	 * summaryOfTxYear 完成表汇总
	 * @param filePaths 要汇总的报表路径集合
	 * @param user 登录用户
	 * @param year 年份
	 * @return
	 */
	public String summaryOfTxYear(List<String> filePaths,User user, String year);
	
}
