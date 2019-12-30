package com.enovell.yunwei.km_micor_service.dto.productManage;

public class MaintenanceMemorendunDto {


	private String checkDate;//检查时间
	private String checker;//检查人
	private String problemFrom;//问题处所
	private String dutyDepartment;//所属部门
//	private String helpDepartment;//协助部门
	private String endDate;//整改时限
	private String problemInfo;//发现问题
	private String changeInfo;//整改情况
	private String result;//整改结果
	private String note;//备注
	private String status;//状态
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getProblemFrom() {
		return problemFrom;
	}
	public void setProblemFrom(String problemFrom) {
		this.problemFrom = problemFrom;
	}
	public String getDutyDepartment() {
		return dutyDepartment;
	}
	public void setDutyDepartment(String dutyDepartment) {
		this.dutyDepartment = dutyDepartment;
	}
//	public String getHelpDepartment() {
//		return helpDepartment;
//	}
//	public void setHelpDepartment(String helpDepartment) {
//		this.helpDepartment = helpDepartment;
//	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProblemInfo() {
		return problemInfo;
	}
	public void setProblemInfo(String problemInfo) {
		this.problemInfo = problemInfo;
	}
	public String getChangeInfo() {
		return changeInfo;
	}
	public void setChangeInfo(String changeInfo) {
		this.changeInfo = changeInfo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MaintenanceMemorendunDto [checkDate=" + checkDate + ", checker=" + checker + ", problemFrom="
				+ problemFrom + ", dutyDepartment=" + dutyDepartment + ", endDate=" + endDate + ", problemInfo="
				+ problemInfo + ", changeInfo=" + changeInfo + ", result=" + result + ", note=" + note + ", status="
				+ status + "]";
	}
	
}
