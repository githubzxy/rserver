package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * 项目名称：yearMonthPlan
 * 类名称：YearMonthPlanFilePathUtils   
 * 类描述:  年月报表文件路径工具类
 * 创建人：lidt 
 * 创建时间：2018年12月20日 下午2:54:16
 * 修改人：lidt 
 * 修改时间：2018年12月20日 下午2:54:16   
 *
 */
@Component
public class YearMonthPlanTableUtils {

	/**
	 * 履历簿
	 */
	private static String deviceRecord;
	/**
	 * 设备与工作内容关系表
	 */
	private static String deviceCheckWorkManage;
	
	
	public static String getDeviceRecord() {
		return deviceRecord;
	}

	@Value("${deviceRecord}")
	public void setDeviceRecord(String deviceRecord) {
		YearMonthPlanTableUtils.deviceRecord = deviceRecord;
	}

	public static String getDeviceCheckWorkManage() {
		return deviceCheckWorkManage;
	}

	@Value("${deviceCheckWorkManage}")
	public void setDeviceCheckWorkManage(String deviceCheckWorkManage) {
		YearMonthPlanTableUtils.deviceCheckWorkManage = deviceCheckWorkManage;
	}
	
}
