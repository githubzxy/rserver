/**   
 * Copyright © 2019 eSunny Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.enovell.yunwei.km_micor_service.util 
 * @author: lidt   
 * @date: 2019年7月11日 下午4:07:46 
 */
package com.enovell.yunwei.km_micor_service.util;

import org.apache.commons.lang.StringUtils;

/**   
 *    
  *  项目名称：km_micor_service   
  *  类名称：DeviceRecordPlaceUtil   
  *  类描述：   
  *  创建人：lidt
  *  创建时间：2019年7月11日 下午4:07:46
 *    
 */
public class DeviceRecordPlaceUtil {
	
	/**
	 * 拼接符
	 */
	private static String link = "-";
	
	/**
	 * 统一入库的地点字段名
	 */
	public static String uniplace = "uniplace";
	
	/**  
	 * putCommonPlace 插入普通地点数据
	 * 
	 * @param site_station_place 安装位置-车站-安装地点
	 * @param site_range_place 安装位置-区间-安装地点
	 * @param site_other_place 安装位置-其他-安装地点
	 * @return 
	 */  
	public static String putCommonPlace(String site_station_place, String site_range_place, String site_other_place) {
		if (StringUtils.isNotBlank(site_station_place)) {
			return site_station_place;
		}
		if (StringUtils.isNotBlank(site_range_place)) {
			return site_range_place;
		}
		if (StringUtils.isNotBlank(site_other_place)) {
			return site_other_place;
		}
		return "";
	}
	
	/**  
	 * putPostPlace 插入带有公里标的地点数据
	 * 
	 * @param site_station_place 安装位置-车站-安装地点
	 * @param site_range_upPost 安装位置-区间-上行公里标
	 * @param site_range_downPost 安装位置-区间-下行公里标
	 * @param site_other_place 安装位置-其他-安装地点
	 * @return 
	 */  
	public static String putPostPlace(String site_station_place, String site_range_upPost, String site_range_downPost,
			String site_other_place) {
		if (StringUtils.isNotBlank(site_station_place)) {
			return site_station_place;
		}
		if (StringUtils.isNotBlank(site_other_place)) {
			return site_other_place;
		}
		if (StringUtils.isNotBlank(site_range_upPost) && StringUtils.isNotBlank(site_range_downPost)) {
			return site_range_upPost + link + site_range_downPost;
		} else if (StringUtils.isNotBlank(site_range_upPost)) {
			return site_range_upPost;
		} else if (StringUtils.isNotBlank(site_range_downPost)) {
			return site_range_downPost;
		}
		return "";
	}
	
	/**  
	 * putLinePlace 插入电缆/光缆相关类别的地点数据
	 * 
	 * @param site_start_station_place 安装位置（起始）-车站-安装地点
	 * @param site_start_range_place 安装位置（起始）-区间-安装地点
	 * @param site_start_other_place 安装位置（起始）-其他-安装地点
	 * @param site_end_station_place 安装位置（终止）-车站-安装地点
	 * @param site_end_range_place 安装位置（终止）-区间-安装地点
	 * @param site_end_other_place 安装位置（终止）-其他-安装地点
	 * @return 
	 */  
	public static String putLinePlace(String site_start_station_place, String site_start_range_place,
			String site_start_other_place, String site_end_station_place, String site_end_range_place,
			String site_end_other_place) {
		String start = "";
		String end = "";
		if (StringUtils.isNotBlank(site_start_station_place)) {
			start = site_start_station_place;
		}
		if (StringUtils.isNotBlank(site_start_range_place)) {
			start = site_start_range_place;
		}
		if (StringUtils.isNotBlank(site_start_other_place)) {
			start = site_start_other_place;
		}
		if (StringUtils.isNotBlank(site_end_station_place)) {
			end = site_end_station_place;
		}
		if (StringUtils.isNotBlank(site_end_range_place)) {
			end = site_end_range_place;
		}
		if (StringUtils.isNotBlank(site_end_other_place)) {
			end = site_end_other_place;
		}
		if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
			return start + link + end;
		} else if (StringUtils.isNotBlank(start)) {
			return start;
		} else if (StringUtils.isNotBlank(end)) {
			return end;
		}
		return "";
	}
	
	/**  
	 * putLinellaePlace 插入线条相关类别的地点数据
	 * 
	 * @param site_start_place 安装位置-起始-安装地点
	 * @param site_end_place 安装位置-止点-安装地点
	 * @return 
	 */  
	public static String putLinellaePlace(String site_start_place, String site_end_place) {
		if (StringUtils.isNotBlank(site_start_place) && StringUtils.isNotBlank(site_end_place)) {
			return site_start_place + link + site_end_place;
		} else if (StringUtils.isNotBlank(site_start_place)) {
			return site_start_place;
		} else if (StringUtils.isNotBlank(site_end_place)) {
			return site_end_place;
		}
		return "";
	}
	
	/**
	 * putHolePlace 插入人孔(手孔)和手持相关类别的地点数据
	 * 
	 * @param site 安装位置
	 * @return
	 */
	public static String putSitePlace(String site) {
		if (StringUtils.isNotBlank(site)) {
			return site;
		}
		return "";
	}
	
	/**  
	 * putMobilePlace 插入移动设备相关类别的地点数据
	 * 
	 * @param site_vehicleType 安装位置-车型
	 * @param site_vehicleCode 安装位置-车型代码\车辆型号代码
	 * @param site_vehicleNumber 安装位置-机车号\车辆号
	 * @return 
	 */  
	public static String putMobilePlace(String site_vehicleType, String site_vehicleCode, String site_vehicleNumber) {
		if (StringUtils.isNotBlank(site_vehicleType) && StringUtils.isNotBlank(site_vehicleCode)
				&& StringUtils.isNotBlank(site_vehicleNumber)) {
			return site_vehicleType + link + site_vehicleCode + link + site_vehicleNumber;
		} else if (StringUtils.isNotBlank(site_vehicleType) && StringUtils.isNotBlank(site_vehicleCode)) {
			return site_vehicleType + link + site_vehicleCode;
		} else if (StringUtils.isNotBlank(site_vehicleType) && StringUtils.isNotBlank(site_vehicleNumber)) {
			return site_vehicleType + link + site_vehicleNumber;
		} else if (StringUtils.isNotBlank(site_vehicleCode) && StringUtils.isNotBlank(site_vehicleNumber)) {
			return site_vehicleCode + link + site_vehicleNumber;
		} else if (StringUtils.isNotBlank(site_vehicleType)) {
			return site_vehicleType;
		} else if (StringUtils.isNotBlank(site_vehicleCode)) {
			return site_vehicleCode;
		} else if (StringUtils.isNotBlank(site_vehicleNumber)) {
			return site_vehicleNumber;
		}
		return "";
	}
	
	

}
