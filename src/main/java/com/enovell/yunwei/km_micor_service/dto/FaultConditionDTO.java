
/**   
 * 文件名：FileSystemDTO.java    
 * 版本信息：IRMS1.0   
 * 日期：2013-4-22 上午10:56:47   
 * Copyright Enovell Corporation 2013    
 * 版权所有   
 *   
 */

package com.enovell.yunwei.km_micor_service.dto;

import java.io.Serializable;


/**      
 * 项目名称：kunmingrinms
 * 类名称：FileSystemDTO   
 * 类描述:  文件系统查询类
 * 创建人：zhangsihong
 * 创建时间：2013-4-22 上午10:56:47
 * 修改人：zhangsihong 
 * 修改时间：2013-4-22 上午10:56:47   
 *    
 */

public class FaultConditionDTO implements Serializable {
	
	
	private static final long serialVersionUID = 6795821810493722055L;

	/**
	 * 处所
	 */
	private String location;
	
	/**
	 * 类别
	 */
	private String faultType;
	
	/**
	 * 部门ID
	 */
	private String departmentId;
	
	/**
	 * 
	 */
	private String startTime;
	
	/**
	 * 
	 */
	private String endTime;
	
	/**
	 * 表名
	 */
	private String collectionName;
	
	
	
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
