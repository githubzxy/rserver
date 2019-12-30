package com.enovell.yunwei.km_micor_service.dto.technicalManagement;

public class DeviceNameWorkDto {

	private String id;
	private String deviceName;//设备名称（类别）
	private String workContent;//工作内容
	private String countYear;//周期
	private String remark;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	public String getCountYear() {
		return countYear;
	}
	public void setCountYear(String countYear) {
		this.countYear = countYear;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
