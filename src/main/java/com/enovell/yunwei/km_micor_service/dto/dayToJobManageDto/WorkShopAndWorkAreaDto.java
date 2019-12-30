package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;
/**
 * 
 * @author Administrator
 *
 */
public class WorkShopAndWorkAreaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1209009637043295404L;
	/**
	 * 车间id
	 */
	private String orgId;
	/**
	 * 车间名字
	 */
	private String orgName;
	/**
	 * 部门名字
	 */
	private String departName;
	/**
	 * 人员
	 */
	private String manName;
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
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getManName() {
		return manName;
	}
	public void setManName(String manName) {
		this.manName = manName;
	}
	@Override
	public String toString() {
		return "WorkShopAndWorkAreaDto [orgId=" + orgId + ", orgName=" + orgName + ", departName=" + departName
				+ ", manName=" + manName + "]";
	}
	
	
	

}
