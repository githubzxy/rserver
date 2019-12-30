
/**   
 * 文件名：YearMonthReportAttach.java    
 * @author quyy  
 * 日期：2017年11月10日 上午11:24:40      
 *   
 */

package com.enovell.yunwei.km_micor_service.dto.yearMonthPlan;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthReportFileUtil;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthReportAttach   
 * 类描述: 年月报表的附件类 (普铁)
 * 创建人：quyy 
 * 创建时间：2017年11月10日 上午11:24:40 
 *    
 */

public class YearMonthReportAttachPuTie implements Serializable{

	private static final long serialVersionUID = -3969828073802011803L;
	
	/**
	 * 编制 通信年表字段名
	 */
	public static final String ATTACH_PATH8_1="ATTACH_PATH8_1";
	//对应报表中文名称
	public static final String ATTACH_PATH8_1_NAME="通信设备年度维修工作计划表";
	//对应报表的名称字段名
	public static final String ATTACH_NAME8_1="ATTACH_NAME8_1";
	//对应报表的模板文件
	public static final String ATTACH_PATH8_1_MOULD="txYearPuTie8_1";
	
	/**
	 * 编制 通信月表字段名
	 */
	public static final String ATTACH_PATH8_2="ATTACH_PATH8_2";
	//对应报表中文名称
	public static final String ATTACH_PATH8_2_NAME="通信设备月度维修工作计划表";
	//对应报表的名称字段名
	public static final String ATTACH_NAME8_2="ATTACH_NAME8_2";
	//对应报表的模板文件
	public static final String ATTACH_PATH8_2_MOULD="txMonthPuTie8_2";
	
	/**
	 * 编制 网管年表字段名
	 */
	public static final String ATTACH_PATH8_3="ATTACH_PATH8_3";
	//对应报表中文名称
	public static final String ATTACH_PATH8_3_NAME="通信设备月度计划表（出入库、库修工区月表）";
	//对应报表的名称字段名
	public static final String ATTACH_NAME8_3="ATTACH_NAME8_3";
		//对应报表的模板文件
	public static final String ATTACH_PATH8_3_MOULD="txMonthPuTie8_3";
		
	/**
	 * 编制 网管月表字段名
	 */
	public static final String ATTACH_PATH8_4="ATTACH_PATH8_4";
	//对应报表中文名称
	public static final String ATTACH_PATH8_4_NAME="通信设备月度计划表（检测工区月表）";
	//对应报表的名称字段名
	public static final String ATTACH_NAME8_4="ATTACH_NAME8_4";
		//对应报表的模板文件
	public static final String ATTACH_PATH8_4_MOULD="txMonthPuTie8_4";
	
		
	
	/**
	 * 执行 通信年表字段名
	 */
	public static final String ATTACH_EXECUTE8_1="ATTACH_EXECUTE8_1";
	//对应报表中文名称
	public static final String ATTACH_EXECUTE8_1_NAME="通信设备年度维修工作完成表";
	//对应报表的模板文件
	public static final String ATTACH_EXECUTE8_1_MOULD="txYearPuTieExecute8_1";
		
	/**
	 * 执行 通信月表字段名
	 */
	public static final String ATTACH_EXECUTE8_2="ATTACH_EXECUTE8_2";
	//对应报表中文名称
	public static final String ATTACH_EXECUTE8_2_NAME="通信设备月度完成表（工区、值班点版）";
	//对应报表的模板文件(12个sheet页)
	public static final String ATTACH_EXECUTE8_2_MOULD="txMonthPuTieExecute8_2";
	//对应报表的模板文件(1个sheet页)
    public static final String ATTACH_EXECUTE8_2_MOULD_1="txMonthPuTieExecuteMould8_2";
	
		
	/**
	 * 执行 网管年表字段名
	 */
	public static final String ATTACH_EXECUTE8_3="ATTACH_EXECUTE8_3";
	//对应报表中文名称
	public static final String ATTACH_EXECUTE8_3_NAME="通信设备月度完成表（出入库、库修工区月表）";
	//对应报表的模板文件(12个sheet页)
	public static final String ATTACH_EXECUTE8_3_MOULD="txMonthPuTieExecute8_3";
	//对应报表的模板文件(1个sheet页)
	public static final String ATTACH_EXECUTE8_3_MOULD_1="txMonthPuTieExecuteMould8_3";
	
	/**
	 * 执行 网管月表字段名
	 */
	public static final String ATTACH_EXECUTE8_4="ATTACH_EXECUTE8_4";
	//对应报表中文名称v
	public static final String ATTACH_EXECUTE8_4_NAME="通信设备月度完成表（检测工区月表）";
	//对应报表的模板文件(12个sheet页)
	public static final String ATTACH_EXECUTE8_4_MOULD="txMonthPuTieExecute8_4";
	//对应报表的模板文件(1个sheet页)
	public static final String ATTACH_EXECUTE8_4_MOULD_1="txMonthPuTieExecuteMould8_4";
	
		
	
	public static final Map<String, String> FILE_NAME_MAP = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 1L;

		{
	    	put(ATTACH_PATH8_1, ATTACH_NAME8_1);  
	    	put(ATTACH_PATH8_2, ATTACH_NAME8_2); 
	    	put(ATTACH_PATH8_3, ATTACH_NAME8_3); 
	    	put(ATTACH_PATH8_4, ATTACH_NAME8_4); 
	    }
	};
	/**
	 * 附表在数据库文件路径字段与是否填报字段的对应关系（执行的是否填报与编制一致）
	 */
	public static final Map<String, String> FILE_NAME_MAP_PUTIE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 1L;

		{
			put(ATTACH_PATH8_1, ATTACH_NAME8_1);  
	    	put(ATTACH_PATH8_2, ATTACH_NAME8_2); 
	    	put(ATTACH_PATH8_3, ATTACH_NAME8_3); 
	    	put(ATTACH_PATH8_4, ATTACH_NAME8_4); 
	    	put(ATTACH_EXECUTE8_1, ATTACH_NAME8_1);  
	    	put(ATTACH_EXECUTE8_2, ATTACH_NAME8_2); 
	    	put(ATTACH_EXECUTE8_3, ATTACH_NAME8_3); 
	    	put(ATTACH_EXECUTE8_4, ATTACH_NAME8_4); 
	    }
	};
	/**
	 * 需要使用到的附表模板
	 * 如数据新增了模板只需数据库增加字段并配置此处map
	 * 注意：数据格式请保持map的key值与数据库字段对应一致，value值为该字段需要写入的附表的模板文件(不含路径)
	 * @author luoyan
	 */
	
	public static final Map<String, String> FILE_TEMPLATE_MAP = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 1L;

		{
	    	put(ATTACH_PATH8_1, ATTACH_PATH8_1_MOULD+".xls");  
	    	put(ATTACH_PATH8_2, ATTACH_PATH8_2_MOULD+".xls"); 
//	    	put(ATTACH_PATH8_3, ATTACH_PATH8_3_MOULD+".xls"); 
//	    	put(ATTACH_PATH8_4, ATTACH_PATH8_4_MOULD+".xls");  
	    }
	};
	
	
	/**
	 * 需要使用到的附表模板
	 * 如数据新增了模板只需数据库增加字段并配置此处map
	 * 注意：数据格式请保持map的key值与数据库字段对应一致，value值为该字段需要写入的附表的模板文件(不含路径)
	 */
	
	public static final Map<String, String> FILE_MAP_EXECUTE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 1L;

		{
			put(ATTACH_EXECUTE8_1, YearMonthReportFileUtil.TXYEAR_TEMPLATE_EXECUTE_PUTIE+".xls");  
	    	put(ATTACH_EXECUTE8_2, YearMonthReportFileUtil.TXMONTH8_2_EXECUTE_PUTIE+".xls"); 
	    	put(ATTACH_EXECUTE8_3, YearMonthReportFileUtil.TXMONTH8_3_EXECUTE_PUTIE+".xls"); 
	    	put(ATTACH_EXECUTE8_4, YearMonthReportFileUtil.TXMONTH8_4_EXECUTE_PUTIE+".xls");  
	    }
	};

}
