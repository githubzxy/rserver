package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class SecurityQueryFindDto implements Serializable {
	   
	 /**   
	  * 变量 serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	 
	
	private static final long serialVersionUID = -5707563920293603265L;
	/**
	 * 起始时间
	 */
	private String startUploadDate;
   /**
    * 截止时间
    */
	private String endUploadDate;
	/**
	 * 责任部门
	 */
	private String securityObligationDepart;
	/**
	 * 线名
	 */
	private String securityLineName;
	/**
	 * 表名
	 */
	private String collectionName;
	/**
	 * userId
	 */
	private String userId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSecurityLineName() {
		return securityLineName;
	}
	public void setSecurityLineName(String securityLineName) {
		this.securityLineName = securityLineName;
	}
	public String getSecurityObligationDepart() {
		return securityObligationDepart;
	}
	public void setSecurityObligationDepart(String securityObligationDepart) {
		this.securityObligationDepart = securityObligationDepart;
	}
	@Override
	public String toString() {
		return "SecurityQueryFindDto [startUploadDate=" + startUploadDate + ", endUploadDate=" + endUploadDate
				+ ", securityObligationDepart=" + securityObligationDepart + ", securityLineName=" + securityLineName
				+ ", collectionName=" + collectionName + ", userId=" + userId + "]";
	}
	
	
}
