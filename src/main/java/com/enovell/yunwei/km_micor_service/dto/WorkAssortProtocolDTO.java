package com.enovell.yunwei.km_micor_service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 项目名称：kunmingrinms
 * 类名称：WorkAssortProtocolExportDTO   
 * 类描述: 施工配合协议导出excel的DTO
 * 创建人：liwt 
 * 创建时间：2018-12-13 
 *
 */
public class WorkAssortProtocolDTO implements Serializable {

	private static final long serialVersionUID = 8932984849448464515L;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 所属部门ID
	 */
	private String orgId;
	/**
	 * 开始时间
	 */
	String startUploadDate;
	
	/**
	 * 结束时间
	 */
	String endUploadDate;
	
	/**
	 * 创建时间
	 */
	private String collectionName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getStartUploadDate() {
		return startUploadDate;
	}

	public void setStartUploadDate(String startUploadDate) {
		this.startUploadDate = startUploadDate;
	}

	public String getEndUploadDate() {
		return endUploadDate;
	}

	public void setEndUploadDate(String endUploadDate) {
		this.endUploadDate = endUploadDate;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	
	
}
