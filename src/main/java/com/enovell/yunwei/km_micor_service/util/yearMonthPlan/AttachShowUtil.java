
/**   
 * 文件名：AttachShowUtil.java    
 * @author quyy  
 * 日期：2017年11月8日 上午10:56:21      
 *   
 */

package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import java.util.ArrayList;
import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttach;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttachPuTie;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;

/**
 * TODO 待路径规划      
 * 项目名称：RINMS2MAIN
 * 类名称：AttachShowUtil   
 * 类描述: 附件展示工具类 
 * 创建人：quyy 
 * 创建时间：2017年11月8日 上午10:56:21 
 *    
 */

public class AttachShowUtil {

	//-----------------------------------------------------------高铁---------------------------------------------------------------//
	/**
	 * 
	 * getAttachDataExecute （高铁）封装段年月报表附件展示的基本信息数据
	 * @author quyy
	 * @return
	 */
	public static List<AttachShowDto> getAttachData(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttach.ATTACH_PATH8_1_NAME, YearMonthReportAttach.ATTACH_PATH8_1, YearMonthReportAttach.ATTACH_NAME8_1, YearMonthReportAttach.ATTACH_PATH8_1_MOULD));//设置 高铁通信设备_年表 的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttach.ATTACH_PATH8_3_NAME, YearMonthReportAttach.ATTACH_PATH8_3, YearMonthReportAttach.ATTACH_NAME8_3, YearMonthReportAttach.ATTACH_PATH8_3_MOULD));//设置 高铁网管设备_年表 的值
		return result;
	}
	/**
	 * 
	 * getworkAreaExecuteAttachData （高铁）封装段工区月报表执行附件展示的基本信息数据
	 * @return
	 * @author chenshuang
	 */
	public static List<AttachShowDto> getExecuteAttachData(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttach.ATTACH_EXECUTE8_1_NAME, YearMonthReportAttach.ATTACH_EXECUTE8_1, null, YearMonthReportAttach.ATTACH_EXECUTE8_1_MOULD));//设置 高铁通信设备完成情况统计_年表 的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttach.ATTACH_EXECUTE8_3_NAME, YearMonthReportAttach.ATTACH_EXECUTE8_3, null, YearMonthReportAttach.ATTACH_EXECUTE8_3_MOULD));//设置 高铁网管设备完成情况统计_年表 的值
		return result;
	}
	/**
	 * 
	 * getworkAreaExecuteAttachData （高铁）封装段工区月报表编制附件展示的基本信息数据
	 * @return
	 * @author chenshuang
	 */
	public static List<AttachShowDto> getPlanAttachData(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttach.ATTACH_PATH8_1_NAME, YearMonthReportAttach.ATTACH_PATH8_1, YearMonthReportAttach.ATTACH_NAME8_1, YearMonthReportAttach.ATTACH_PATH8_1_MOULD));//设置 高铁通信设备_年表 的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttach.ATTACH_PATH8_3_NAME, YearMonthReportAttach.ATTACH_PATH8_3, YearMonthReportAttach.ATTACH_NAME8_3, YearMonthReportAttach.ATTACH_PATH8_3_MOULD));//设置 高铁网管设备_年表 的值
		return result;
	}
	//---------------------------------------------------------普铁---------------------------------------------------//
	/**
	 * 
	 * getAttachDataExecute (普铁)封装段年月报表附件展示的基本信息数据
	 * @author quyy
	 * @return
	 */
	public static List<AttachShowDto> getAttachDataPutie(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_1_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_1, YearMonthReportAttachPuTie.ATTACH_NAME8_1, YearMonthReportAttachPuTie.ATTACH_PATH8_1_MOULD));//设置普铁通信设备年度维修工作计划表的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_2_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_2, YearMonthReportAttachPuTie.ATTACH_NAME8_2, YearMonthReportAttachPuTie.ATTACH_PATH8_2_MOULD));//设置普铁通信设备月度计划表（工区、值班点版） 的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_3_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_3, YearMonthReportAttachPuTie.ATTACH_NAME8_3, YearMonthReportAttachPuTie.ATTACH_PATH8_3_MOULD));//设置普铁通信设备月度计划表（出入库、库修工区月表）的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_4_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_4, YearMonthReportAttachPuTie.ATTACH_NAME8_4, YearMonthReportAttachPuTie.ATTACH_PATH8_4_MOULD));//设置普铁通信设备月度计划表（检测工区月表）的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1_MOULD));//设置普铁通信设备年度维修工作完成表的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2_MOULD_1));//设置普铁通信设备月度完成表（工区、值班点版） 的值（1个sheet）
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3_MOULD_1));//设置普铁通信设备月度完成表（出入库、库修工区月表）的值（1个sheet）
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4_MOULD_1));//设置普铁通信设备月度完成表（检测工区月表）的值（1个sheet）
		return result;
	}
	/**
	 * 
	 * getworkAreaExecuteAttachData (普铁)封装段工区月报表执行附件展示的基本信息数据
	 * @return
	 * @author chenshuang
	 */
	public static List<AttachShowDto> getExecuteAttachDataPutie(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1_MOULD));//设置 普铁通信设备完成情况统计_年表 的值
//		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2_MOULD));//设置 普铁通信设备完成情况统计_月表 的值
//		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3_MOULD));//设置 普铁网管设备完成情况统计_年表 的值
//		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4_MOULD));//设置 普铁网管设备完成情况统计_月表 的值
		return result;
	}
	/**
	 * 
	 * getExecuteAttachDataPutieSingle(普铁)封装段工区月报表编制附件展示的基本信息数据
	 * @return
	 * @author gaohg
	 */
	public static List<AttachShowDto> getExecuteAttachDataPutieSingle(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1_NAME, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1, null, YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1_MOULD));//设置 普铁通信设备完成情况统计_年表 的值
		return result;
	}
	/**
	 * 
	 * getworkAreaExecuteAttachData (普铁)封装段工区月报表编制附件展示的基本信息数据
	 * @return
	 * @author chenshuang
	 */
	public static List<AttachShowDto> getPlanAttachDataPutie(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_1_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_1, YearMonthReportAttachPuTie.ATTACH_NAME8_1, YearMonthReportAttachPuTie.ATTACH_PATH8_1_MOULD));//设置 普铁通信设备_年表 的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_2_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_2, YearMonthReportAttachPuTie.ATTACH_NAME8_2, YearMonthReportAttachPuTie.ATTACH_PATH8_2_MOULD));//设置 普铁通信设备_月表 的值
//		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_3_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_3, YearMonthReportAttachPuTie.ATTACH_NAME8_3, YearMonthReportAttachPuTie.ATTACH_PATH8_3_MOULD));//设置 普铁网管设备_年表 的值
//		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_4_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_4, YearMonthReportAttachPuTie.ATTACH_NAME8_4, YearMonthReportAttachPuTie.ATTACH_PATH8_4_MOULD));//设置 普铁网管设备_月表 的值
		return result;
	}
	
	/**
	 * 
	 * getWSPlanAttachData (普铁)车间（编制）汇总及详情展示附表数据
	 * @return
	 * @author chenshuang
	 */
	public static List<AttachShowDto> getWSPlanAttachData(){
		List<AttachShowDto> result = new ArrayList<AttachShowDto>();
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_1_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_1, YearMonthReportAttachPuTie.ATTACH_NAME8_1, YearMonthReportAttachPuTie.ATTACH_PATH8_1_MOULD));//设置 高铁通信设备_年表 的值
		result.add(setAttachShowDtoSomeData(YearMonthReportAttachPuTie.ATTACH_PATH8_2_NAME, YearMonthReportAttachPuTie.ATTACH_PATH8_2, YearMonthReportAttachPuTie.ATTACH_NAME8_2, YearMonthReportAttachPuTie.ATTACH_PATH8_2_MOULD));//设置 高铁通信设备_月表 的值
		return result;
	}
	
	/**
	 * 
	 * setAttachShowDtoSomeData 设置AttachShowDto的基本值
	 * @author quyy
	 * @param reportName 
	 * @param attachType
	 * @param attachName
	 * @param moudleName
	 * @return
	 */
	private static AttachShowDto setAttachShowDtoSomeData(String reportName,String attachType,String attachName,String moudleName){
		AttachShowDto dto = new AttachShowDto();
		dto.setAttachName(attachName);
		dto.setAttachType(attachType);
		dto.setReportName(reportName);
		dto.setAttachMoudleName(moudleName);
		return dto;
	}
}
