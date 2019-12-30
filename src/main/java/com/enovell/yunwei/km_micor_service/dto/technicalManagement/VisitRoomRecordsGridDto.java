package com.enovell.yunwei.km_micor_service.dto.technicalManagement;
/**
 * 进出人员信息登记
 * @author zhouxy
 *
 */
public class VisitRoomRecordsGridDto {

	/**
	 * 线别
	 */
	private String railLine;
	/**
	 * 所属机房 
	 */
	private String machineRoom;
	private String date;
	private String visitDate;
	private String visitName;
	private String visitFrom;
	private String helper;
	private String leaveDate;
	private String jobContent;
	private String remark;
	public String getRailLine() {
		return railLine;
	}
	public void setRailLine(String railLine) {
		this.railLine = railLine;
	}
	public String getMachineRoom() {
		return machineRoom;
	}
	public void setMachineRoom(String machineRoom) {
		this.machineRoom = machineRoom;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitName() {
		return visitName;
	}
	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}
	public String getVisitFrom() {
		return visitFrom;
	}
	public void setVisitFrom(String visitFrom) {
		this.visitFrom = visitFrom;
	}
	public String getHelper() {
		return helper;
	}
	public void setHelper(String helper) {
		this.helper = helper;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getJobContent() {
		return jobContent;
	}
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
