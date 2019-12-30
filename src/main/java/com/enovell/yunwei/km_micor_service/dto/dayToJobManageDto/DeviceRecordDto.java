package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class DeviceRecordDto implements Serializable {
	/**
	 * 用于导入设备履历Excel的dto
	 */
	private static final long serialVersionUID = -1686306364475979479L;
   /**
    * 设备处所
    */
	private String location;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备类别
	 */
	private String type;
	/**
	 * 维护单位
	 */
	private String maintainUnit;
	/**
	 * 包机人
	 */
	private String person;
	/**
	 * 设备厂家
	 */
	private String vender;
	/**
	 *设备型号
	 */
	private String	modelNumber;
	/**
	 * 使用时间
	 */
	private String useTime;
	/**
	 * 所属铁路线
	 */
	private String railwayLine;
	/**
	 * 备注
	 */
	private String remark;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMaintainUnit() {
		return maintainUnit;
	}
	public void setMaintainUnit(String maintainUnit) {
		this.maintainUnit = maintainUnit;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getVender() {
		return vender;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getRailwayLine() {
		return railwayLine;
	}
	public void setRailwayLine(String railwayLine) {
		this.railwayLine = railwayLine;
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
		return "DeviceRecordDto [location=" + location + ", deviceName=" + deviceName + ", type=" + type
				+ ", maintainUnit=" + maintainUnit + ", person=" + person + ", vender=" + vender + ", modelNumber="
				+ modelNumber + ", useTime=" + useTime + ", railwayLine=" + railwayLine + ", remark=" + remark + "]";
	}
	
	
	
}
