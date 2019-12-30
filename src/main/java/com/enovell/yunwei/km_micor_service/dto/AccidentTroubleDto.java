package com.enovell.yunwei.km_micor_service.dto;

import java.io.Serializable;
/**
 *用于导出事故/故障/障碍的查询结果
 * @author Administrator
 *
 */
public class AccidentTroubleDto implements Serializable{
	private static final long serialVersionUID = 6795821810493722055L;

	/**
	 * 创建时间
	 */
	private String createDate;
	
	/**
	 * 信息反馈部门
	 */
	private String backOrgName;
	
	/**
	 * 信息反馈人
	 */
	private String backPerson;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 内容及处理情况
	 */
	private String detail;
	
	/**
	 * 信息后果
	 */
	private String infoResult;
	/**
	 * 备注
	 */
	private String remark;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getBackOrgName() {
		return backOrgName;
	}
	public void setBackOrgName(String backOrgName) {
		this.backOrgName = backOrgName;
	}
	public String getBackPerson() {
		return backPerson;
	}
	public void setBackPerson(String backPerson) {
		this.backPerson = backPerson;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getInfoResult() {
		return infoResult;
	}
	public void setInfoResult(String infoResult) {
		this.infoResult = infoResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AccidentTroubleDto [createDate=" + createDate + ", backOrgName=" + backOrgName + ", backPerson="
				+ backPerson + ", type=" + type + ", detail=" + detail + ", infoResult=" + infoResult + ", remark="
				+ remark + "]";
	}
	
	

}
