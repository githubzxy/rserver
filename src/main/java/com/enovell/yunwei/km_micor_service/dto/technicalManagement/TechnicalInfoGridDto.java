package com.enovell.yunwei.km_micor_service.dto.technicalManagement;

public class TechnicalInfoGridDto {

	/**
	 * 资料名称
	 */
	private String name;

	
	/**
	 * 线别
	 */
	private String railLine;
	/**
	 * 所属机房 
	 */
	private String machineRoom;
	/**
	 * 创建日期
	 */
	private String  creatDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
	

	
}
