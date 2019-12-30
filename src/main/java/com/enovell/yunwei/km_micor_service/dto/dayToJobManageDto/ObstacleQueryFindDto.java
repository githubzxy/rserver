package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class ObstacleQueryFindDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1686306364475979379L;
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
	private String obstacleObligationDepart;
	/**
	 * 障碍类型
	 */
	private String obstacleType;
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
	public String getObstacleType() {
		return obstacleType;
	}
	public void setObstacleType(String obstacleType) {
		this.obstacleType = obstacleType;
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
	public String getObstacleObligationDepart() {
		return obstacleObligationDepart;
	}
	public void setObstacleObligationDepart(String obstacleObligationDepart) {
		this.obstacleObligationDepart = obstacleObligationDepart;
	}
	@Override
	public String toString() {
		return "ObstacleQueryFindDto [startUploadDate=" + startUploadDate + ", endUploadDate=" + endUploadDate
				+ ", obstacleObligationDepart=" + obstacleObligationDepart + ", obstacleType=" + obstacleType
				+ ", collectionName=" + collectionName + ", userId=" + userId + "]";
	}
	
	
}
