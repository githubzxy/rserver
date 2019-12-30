package com.enovell.yunwei.km_micor_service.dto.technicalManagement;

import java.io.Serializable;

public class MachineRoomDto implements Serializable{
	 /**   
	  * 变量 serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   

	
	private static final long serialVersionUID = 2498745844291122326L;

	/**
	 * Id
	 */
	private String id;
	/**
	 * 机房编码
	 */
	private String machineCode;
	/**
	 * 机房名称
	 */
	private String machineName;
	/**
	 * 线别名称
	 */
	private String name;
	/**
	 * 维护单位
	 */
	private String maintenanceOrg;
	/**
	 * 投产日期
	 */
	private String commissioningDate;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 线别Id
	 */
	private String lineId;
	/**
	 * 
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMaintenanceOrg() {
		return maintenanceOrg;
	}
	public void setMaintenanceOrg(String maintenanceOrg) {
		this.maintenanceOrg = maintenanceOrg;
	}
	public String getCommissioningDate() {
		return commissioningDate;
	}
	public void setCommissioningDate(String commissioningDate) {
		this.commissioningDate = commissioningDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	@Override
	public String toString() {
		return "MachineRoomDto [id=" + id + ", machineCode=" + machineCode + ", machineName=" + machineName + ", name="
				+ name + ", maintenanceOrg=" + maintenanceOrg + ", commissioningDate=" + commissioningDate + ", remark="
				+ remark + ", lineId=" + lineId + "]";
	}
	
}
