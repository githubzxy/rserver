package com.enovell.yunwei.km_micor_service.dto.communicationResumeManage;

import java.io.Serializable;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：PublicDto   
 * 类描述:  通信履历各表公共属性的提取
 * 创建人：zhouxingyu
 * 创建时间：2019年6月18日 上午11:23:55
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月18日 上午11:23:55   
 *
 */
public class PublicDto implements Serializable{
	private static final long serialVersionUID = 4977474043354926476L;
	private String workshop;//车间*
	private String workArea;//班组*
	private String combinationClass;//(组合分类)
	private String deviceClass;//(设备分类)
	private String deviceCode;//设备（设施）编码 *
	private String deviceName;//设备名称*
	private String site_station_line;//车站-所属线路
	private String site_station_name;//车站-车站名称
	private String site_station_place;//车站-安装地点
	private String site_range_line;//区间-所属线路
	private String site_range_post;//区间-公里标
	private String site_range_place;//区间-安装地点
	private String site_other_line;//其它-所属线路
	private String site_other_place;//其它-安装地点
	private String site_machineRoomCode;//机房、接入点编码*
	private String assetOwnership;//资产归属*
	private String ownershipUnitName;//权属单位名称*
	private String ownershipUnitCode;//权属单位编码*
	private String maintainBody;//维护主体*
	private String maintainUnitName;//维护单位名称*
	private String maintainUnitCode;//维护单位编码*
	private String manufacturers;//设备厂家*
	private String deviceType;//设备型号*
	private String useUnit;//使用单位
	private String productionDate;//出厂日期
	private String useDate;//使用日期*
	private String deviceOperationStatus;//设备运行状态*
	private String stopDate;//停用日期
	private String scrapDate;//报废日期
	private String fixedAssetsCode;//固资编号
	private String remark;//备注 ；共31个属性
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getWorkArea() {
		return workArea;
	}
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	public String getCombinationClass() {
		return combinationClass;
	}
	public void setCombinationClass(String combinationClass) {
		this.combinationClass = combinationClass;
	}
	public String getDeviceClass() {
		return deviceClass;
	}
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getSite_station_line() {
		return site_station_line;
	}
	public void setSite_station_line(String site_station_line) {
		this.site_station_line = site_station_line;
	}
	public String getSite_station_name() {
		return site_station_name;
	}
	public void setSite_station_name(String site_station_name) {
		this.site_station_name = site_station_name;
	}
	public String getSite_station_place() {
		return site_station_place;
	}
	public void setSite_station_place(String site_station_place) {
		this.site_station_place = site_station_place;
	}
	public String getSite_range_line() {
		return site_range_line;
	}
	public void setSite_range_line(String site_range_line) {
		this.site_range_line = site_range_line;
	}
	public String getSite_range_post() {
		return site_range_post;
	}
	public void setSite_range_post(String site_range_post) {
		this.site_range_post = site_range_post;
	}
	public String getSite_range_place() {
		return site_range_place;
	}
	public void setSite_range_place(String site_range_place) {
		this.site_range_place = site_range_place;
	}
	public String getSite_other_line() {
		return site_other_line;
	}
	public void setSite_other_line(String site_other_line) {
		this.site_other_line = site_other_line;
	}
	public String getSite_other_place() {
		return site_other_place;
	}
	public void setSite_other_place(String site_other_place) {
		this.site_other_place = site_other_place;
	}
	public String getSite_machineRoomCode() {
		return site_machineRoomCode;
	}
	public void setSite_machineRoomCode(String site_machineRoomCode) {
		this.site_machineRoomCode = site_machineRoomCode;
	}
	public String getAssetOwnership() {
		return assetOwnership;
	}
	public void setAssetOwnership(String assetOwnership) {
		this.assetOwnership = assetOwnership;
	}
	public String getOwnershipUnitName() {
		return ownershipUnitName;
	}
	public void setOwnershipUnitName(String ownershipUnitName) {
		this.ownershipUnitName = ownershipUnitName;
	}
	public String getOwnershipUnitCode() {
		return ownershipUnitCode;
	}
	public void setOwnershipUnitCode(String ownershipUnitCode) {
		this.ownershipUnitCode = ownershipUnitCode;
	}
	public String getMaintainBody() {
		return maintainBody;
	}
	public void setMaintainBody(String maintainBody) {
		this.maintainBody = maintainBody;
	}
	public String getMaintainUnitName() {
		return maintainUnitName;
	}
	public void setMaintainUnitName(String maintainUnitName) {
		this.maintainUnitName = maintainUnitName;
	}
	public String getMaintainUnitCode() {
		return maintainUnitCode;
	}
	public void setMaintainUnitCode(String maintainUnitCode) {
		this.maintainUnitCode = maintainUnitCode;
	}
	public String getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getUseUnit() {
		return useUnit;
	}
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getDeviceOperationStatus() {
		return deviceOperationStatus;
	}
	public void setDeviceOperationStatus(String deviceOperationStatus) {
		this.deviceOperationStatus = deviceOperationStatus;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	public String getScrapDate() {
		return scrapDate;
	}
	public void setScrapDate(String scrapDate) {
		this.scrapDate = scrapDate;
	}
	public String getFixedAssetsCode() {
		return fixedAssetsCode;
	}
	public void setFixedAssetsCode(String fixedAssetsCode) {
		this.fixedAssetsCode = fixedAssetsCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
