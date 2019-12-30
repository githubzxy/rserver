package com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.domain;

import java.util.Date;

import com.enovell.yunwei.enocommon.utils.DataExistStatus;

/**
 * 年月报表编制车间实体类
* @Title: YearMonthWorkShop.java 
* @Package com.enovell.yunwei.yearMonthReport.domain 
* @date 2017年9月19日 上午10:38:25 
* @author luoyan  
*/
public class YearMonthPutieCJ {
	
	private String id;

	/**
	 * 报表名称
	 */
	private String name;
	
	/**
	 * 所属年份
	 */
	private String year;
	
	/**
	 * 所属部门id
	 */
	private String orgId;
	
	/**
	 * 所属部门名称
	 */
	private String orgName;
	
	/**
	 * 状态
	 */
	private String status;
	
	
	/**
	 * 上报时间
	 */
	private Date reportTime;
	
	/**
	 * 打回原因
	 */
	private String failReason;
	
	/**
	 * 是否存在（true 存在 false 已被删除）
	 */
	private Boolean exist=DataExistStatus.EXIST;
	
	/**
	 * 附表8-1
	 */
	private String attachPath8_1;
	private String attachName8_1;
	
	/**
	 * 执行附表8-1 
	 */
	private String attachPathExecute8_1;
	
	/**
	 * 附表8-2
	 */
	private String attachPath8_2;
	private String attachName8_2;
	
	
	/**
	 * 执行附表8-2
	 */
	private String attachPathExecute8_2;
	
	/**
	 * 附表8-3
	 */
	private String attachPath8_3;
	private String attachName8_3;
	
	/**
	 * 执行附表8-3
	 */
	private String attachPathExecute8_3;

	
	/**
	 * 附表8-4
	 */
	private String attachPath8_4;
	private String attachName8_4;
	
	/**
	 * 执行附表8-4
	 */
	private String attachPathExecute8_4;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	public Boolean getExist() {
		return exist;
	}
	public void setExist(Boolean exist) {
		this.exist = exist;
	}
	
	public String getAttachPath8_1() {
		return attachPath8_1;
	}
	public void setAttachPath8_1(String attachPath8_1) {
		this.attachPath8_1 = attachPath8_1;
	}
	
	public String getAttachName8_1() {
		return attachName8_1;
	}
	public void setAttachName8_1(String attachName8_1) {
		this.attachName8_1 = attachName8_1;
	}
	
	public String getAttachPath8_2() {
		return attachPath8_2;
	}
	public void setAttachPath8_2(String attachPath8_2) {
		this.attachPath8_2 = attachPath8_2;
	}
	
	public String getAttachName8_2() {
		return attachName8_2;
	}
	public void setAttachName8_2(String attachName8_2) {
		this.attachName8_2 = attachName8_2;
	}
	
	public String getAttachPath8_3() {
		return attachPath8_3;
	}
	public void setAttachPath8_3(String attachPath8_3) {
		this.attachPath8_3 = attachPath8_3;
	}
	
	public String getAttachName8_3() {
		return attachName8_3;
	}
	public void setAttachName8_3(String attachName8_3) {
		this.attachName8_3 = attachName8_3;
	}
	
	public String getAttachPath8_4() {
		return attachPath8_4;
	}
	public void setAttachPath8_4(String attachPath8_4) {
		this.attachPath8_4 = attachPath8_4;
	}
	
	public String getAttachName8_4() {
		return attachName8_4;
	}
	public void setAttachName8_4(String attachName8_4) {
		this.attachName8_4 = attachName8_4;
	}
	
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	public String getAttachPathExecute8_1() {
		return attachPathExecute8_1;
	}
	public void setAttachPathExecute8_1(String attachPathExecute8_1) {
		this.attachPathExecute8_1 = attachPathExecute8_1;
	}
	
	public String getAttachPathExecute8_2() {
		return attachPathExecute8_2;
	}
	public void setAttachPathExecute8_2(String attachPathExecute8_2) {
		this.attachPathExecute8_2 = attachPathExecute8_2;
	}
	
	public String getAttachPathExecute8_3() {
		return attachPathExecute8_3;
	}
	public void setAttachPathExecute8_3(String attachPathExecute8_3) {
		this.attachPathExecute8_3 = attachPathExecute8_3;
	}
	
	public String getAttachPathExecute8_4() {
		return attachPathExecute8_4;
	}
	public void setAttachPathExecute8_4(String attachPathExecute8_4) {
		this.attachPathExecute8_4 = attachPathExecute8_4;
	}
	

}
