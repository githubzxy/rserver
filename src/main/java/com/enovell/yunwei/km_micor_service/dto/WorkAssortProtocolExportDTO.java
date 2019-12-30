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
public class WorkAssortProtocolExportDTO implements Serializable {

	private static final long serialVersionUID = 8932984849448464515L;

	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 所属部门
	 */
	private String organization;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 创建人
	 * @return
	 * @author	liwt
	 */
	private String createUser;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	
	
}
