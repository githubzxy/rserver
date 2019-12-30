package com.enovell.yunwei.km_micor_service.dto;

import java.io.Serializable;

/**
 * 点外维修业务申请表实体类
 * @author yangsy 2019年3月25日
 */
public class PointOuterMaintainApplyDto implements Serializable {
	
	private static final long serialVersionUID = 5083589806110188002L;
	
	/**
	 * 表单申请时间
	 */
	private String applyDate;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 上级机构名称
	 */
	private String auditorOrg;
	/**
	 * 编号
	 */
	private String serial;
	/**
	 * 作业时间
	 */
	private String workTime;
	/**
	 * 线别
	 */
	private String lineName;
	/**
	 * 区间
	 */
	private String section;
	/**
	 * 站点
	 */
	private String station;
	/**
	 * 作业负责人
	 */
	private String workPrincipal;
	/**
	 * 作业负责人联系电话
	 */
	private String phone;
	/**
	 * 审核人
	 */
	private String auditor;
	/**
	 * 审核日期
	 */
	private String auditDate;
	/**
	 * 审批人
	 */
	private String approver;
	/**
	 * 审批日期
	 */
	private String approveDate;
	/**
	 * 参加人员
	 */
	private String attendPeople;
	/**
	 * 作业内容
	 */
	private String workContentRange;
	/**
	 * 作业组织情况
	 */
	private String workOrgCondition;
	/**
	 * 现场防护措施
	 */
	private String localeDefendMeasure;
	/**
	 * 相关要求
	 */
	private String relevantDemand;
	/**
	 * 作业机械料具准备及携带情况
	 */
	private String workPrepareCarry;
	/**
	 * 人员到达作业地点及返回路线
	 */
	private String peopleWorkSiteBackWay;
	/**
	 * 作业人员分工
	 */
	private String workPeopleDivision;
	/**
	 * 防护人员分工
	 */
	private String safeguardDivision;
	/**
	 * 安全风险控制措施
	 */
	private String safetyAttentionMatter;
	public String getWorkOrgCondition() {
		return workOrgCondition;
	}
	public void setWorkOrgCondition(String workOrgCondition) {
		this.workOrgCondition = workOrgCondition;
	}
	public String getLocaleDefendMeasure() {
		return localeDefendMeasure;
	}
	public void setLocaleDefendMeasure(String localeDefendMeasure) {
		this.localeDefendMeasure = localeDefendMeasure;
	}
	public String getRelevantDemand() {
		return relevantDemand;
	}
	public void setRelevantDemand(String relevantDemand) {
		this.relevantDemand = relevantDemand;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getWorkPrincipal() {
		return workPrincipal;
	}
	public void setWorkPrincipal(String workPrincipal) {
		this.workPrincipal = workPrincipal;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAttendPeople() {
		return attendPeople;
	}
	public void setAttendPeople(String attendPeople) {
		this.attendPeople = attendPeople;
	}
	public String getWorkContentRange() {
		return workContentRange;
	}
	public void setWorkContentRange(String workContentRange) {
		this.workContentRange = workContentRange;
	}
	public String getWorkPrepareCarry() {
		return workPrepareCarry;
	}
	public void setWorkPrepareCarry(String workPrepareCarry) {
		this.workPrepareCarry = workPrepareCarry;
	}
	public String getPeopleWorkSiteBackWay() {
		return peopleWorkSiteBackWay;
	}
	public void setPeopleWorkSiteBackWay(String peopleWorkSiteBackWay) {
		this.peopleWorkSiteBackWay = peopleWorkSiteBackWay;
	}
	public String getWorkPeopleDivision() {
		return workPeopleDivision;
	}
	public void setWorkPeopleDivision(String workPeopleDivision) {
		this.workPeopleDivision = workPeopleDivision;
	}
	public String getSafeguardDivision() {
		return safeguardDivision;
	}
	public void setSafeguardDivision(String safeguardDivision) {
		this.safeguardDivision = safeguardDivision;
	}
	public String getSafetyAttentionMatter() {
		return safetyAttentionMatter;
	}
	public void setSafetyAttentionMatter(String safetyAttentionMatter) {
		this.safetyAttentionMatter = safetyAttentionMatter;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public String getAuditorOrg() {
		return auditorOrg;
	}
	public void setAuditorOrg(String auditorOrg) {
		this.auditorOrg = auditorOrg;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	@Override
	public String toString() {
		return "PointOuterMaintainApplyDto [applyDate=" + applyDate + ", unit=" + unit + ", auditorOrg=" + auditorOrg
				+ ", serial=" + serial + ", workTime=" + workTime + ", lineName=" + lineName + ", section=" + section
				+ ", station=" + station + ", workPrincipal=" + workPrincipal + ", phone=" + phone + ", auditor="
				+ auditor + ", auditDate=" + auditDate + ", approver=" + approver + ", approveDate=" + approveDate
				+ ", attendPeople=" + attendPeople + ", workContentRange=" + workContentRange + ", workPrepareCarry="
				+ workPrepareCarry + ", peopleWorkSiteBackWay=" + peopleWorkSiteBackWay + ", workPeopleDivision="
				+ workPeopleDivision + ", safeguardDivision=" + safeguardDivision + ", safetyAttentionMatter="
				+ safetyAttentionMatter + "]";
	}
	
}