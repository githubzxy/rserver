package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class ConstructRepairFindDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6855802235337697788L;
	/**
	 * 项目
	 */
	private String project;
	
	/**
	 * 计划号
	 */
	private String planNum;
	/**
	 * 施工内容
	 */
	private String constructContent;

	/**
	 * 开始时间
	 */
	String startUploadDate;
	
	/**
	 * 结束时间
	 */
	String endUploadDate;
	/**
	 * 表名
	 */
	private String collectionName;
	/**
	 * userId
	 */
	private String userId;
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
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
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPlanNum() {
		return planNum;
	}
	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
	public String getConstructContent() {
		return constructContent;
	}
	public void setConstructContent(String constructContent) {
		this.constructContent = constructContent;
	}
	@Override
	public String toString() {
		return "ConstructRepairFindDto [project=" + project + ", planNum=" + planNum + ", constructContent="
				+ constructContent + ", startUploadDate=" + startUploadDate + ", endUploadDate=" + endUploadDate
				+ ", collectionName=" + collectionName + ", userId=" + userId + "]";
	}
	
}
