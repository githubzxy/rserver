package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;

/**
* @Title: YearMonthZipToFileService.java 
* @Package com.enovell.yunwei.yearMonthReport.service 
* @date 2017年11月2日 下午3:20:39 
* @author luoyan  
*/
public interface YearMonthPutieZipToFileService {
	
	/**
	 * 段科室导出Excel数据
	 * zipToFileSegment 
	 * @param ids
	 * @param attachPath
	 * @return
	 * ResultMsg
	 */
	public ResultMsg zipToFileSegment (String ids, String attachPath);
	
}
