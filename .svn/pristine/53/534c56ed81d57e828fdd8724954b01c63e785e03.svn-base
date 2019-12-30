
/**   
 * 文件名：PagingUtil.java    
 * 版本信息：IRMS1.0   
 * 日期：2017年8月25日 下午2:10:55   
 * Copyright Enovell Corporation 2017    
 * 版权所有   
 *   
 */

package com.enovell.yunwei.km_micor_service.util;

import java.util.ArrayList;
import java.util.List;

/**      
 * 项目名称：std
 * 类名称：PagingUtil   
 * 类描述:  用于手动分页的工具类
 * 创建人：lidt 
 * 创建时间：2017年8月25日 下午2:10:55
 * 修改人：lidt 
 * 修改时间：2017年8月25日 下午2:10:55   
 *    
 */
public class PagingUtil {
	
	/**
	 * 
	 * getPagingListData 获取分页集合数据
	 *
	 * @author lidt
	 * @date 2017年8月25日 下午2:32:52 
	 * @param list 数据
	 * @param start 起始页
	 * @param limit 本页显示数目
	 * @return List<T>
	 */
	public static <T> List<T> getPagingListData(List<T> list, int start, int limit){
		List<T> sublist = new ArrayList<T>();
		if (start >= list.size()) {
			return sublist;
		} else if ((start + limit) >= list.size()) {
			return list.subList(start, list.size());
		} else if ((start + limit) < list.size()) {
			return list.subList(start, start + limit);
		}
		return sublist;
	}

}
