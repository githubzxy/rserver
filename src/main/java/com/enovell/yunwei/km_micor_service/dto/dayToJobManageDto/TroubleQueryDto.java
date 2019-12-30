package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class TroubleQueryDto implements Serializable {

	private static final long serialVersionUID = 4822225327928480404L;
//责任车间	 
public String obligationDepart;
//线别
public String troubleLineName ;
//日期
public String troubleDate ;
//故障设备名称
public String troubleDevice ;
//故障地点
public String troubleSite ;
//故障概况
public String troubleGeneral;
//发生时间
public String  troubleOccurDate;
//恢复时间
public String  troubleRecoverDate;
//延时时间
public String  troubleDelayMinutes;
//影响车次
public String  troubleTrainNumber;
//处理经过
public String  troubleDisposePass;
//原因分析
public String  troubleReasonAnalyse;
//防范措施
public String  troubleMeasure;
//定性定责
public String  troubleFixDuty;
//考核情况
public String  troubleCheckSituation;
//备注
public String  troubleRemark;
public String getObligationDepart() {
	return obligationDepart;
}
public void setObligationDepart(String obligationDepart) {
	this.obligationDepart = obligationDepart;
}
public String getTroubleLineName() {
	return troubleLineName;
}
public void setTroubleLineName(String troubleLineName) {
	this.troubleLineName = troubleLineName;
}
public String getTroubleDate() {
	return troubleDate;
}
public void setTroubleDate(String troubleDate) {
	this.troubleDate = troubleDate;
}
public String getTroubleDevice() {
	return troubleDevice;
}
public void setTroubleDevice(String troubleDevice) {
	this.troubleDevice = troubleDevice;
}
public String getTroubleSite() {
	return troubleSite;
}
public void setTroubleSite(String troubleSite) {
	this.troubleSite = troubleSite;
}
public String getTroubleGeneral() {
	return troubleGeneral;
}
public void setTroubleGeneral(String troubleGeneral) {
	this.troubleGeneral = troubleGeneral;
}
public String getTroubleOccurDate() {
	return troubleOccurDate;
}
public void setTroubleOccurDate(String troubleOccurDate) {
	this.troubleOccurDate = troubleOccurDate;
}
public String getTroubleRecoverDate() {
	return troubleRecoverDate;
}
public void setTroubleRecoverDate(String troubleRecoverDate) {
	this.troubleRecoverDate = troubleRecoverDate;
}
public String getTroubleDelayMinutes() {
	return troubleDelayMinutes;
}
public void setTroubleDelayMinutes(String troubleDelayMinutes) {
	this.troubleDelayMinutes = troubleDelayMinutes;
}
public String getTroubleTrainNumber() {
	return troubleTrainNumber;
}
public void setTroubleTrainNumber(String troubleTrainNumber) {
	this.troubleTrainNumber = troubleTrainNumber;
}
public String getTroubleDisposePass() {
	return troubleDisposePass;
}
public void setTroubleDisposePass(String troubleDisposePass) {
	this.troubleDisposePass = troubleDisposePass;
}
public String getTroubleReasonAnalyse() {
	return troubleReasonAnalyse;
}
public void setTroubleReasonAnalyse(String troubleReasonAnalyse) {
	this.troubleReasonAnalyse = troubleReasonAnalyse;
}
public String getTroubleMeasure() {
	return troubleMeasure;
}
public void setTroubleMeasure(String troubleMeasure) {
	this.troubleMeasure = troubleMeasure;
}
public String getTroubleFixDuty() {
	return troubleFixDuty;
}
public void setTroubleFixDuty(String troubleFixDuty) {
	this.troubleFixDuty = troubleFixDuty;
}
public String getTroubleCheckSituation() {
	return troubleCheckSituation;
}
public void setTroubleCheckSituation(String troubleCheckSituation) {
	this.troubleCheckSituation = troubleCheckSituation;
}
public String getTroubleRemark() {
	return troubleRemark;
}
public void setTroubleRemark(String troubleRemark) {
	this.troubleRemark = troubleRemark;
}
@Override
public String toString() {
	return "TroubleQueryDto [obligationDepart=" + obligationDepart + ", troubleLineName=" + troubleLineName
			+ ", troubleDate=" + troubleDate + ", troubleDevice=" + troubleDevice + ", troubleSite=" + troubleSite
			+ ", troubleGeneral=" + troubleGeneral + ", troubleOccurDate=" + troubleOccurDate + ", troubleRecoverDate="
			+ troubleRecoverDate + ", troubleDelayMinutes=" + troubleDelayMinutes + ", troubleTrainNumber="
			+ troubleTrainNumber + ", troubleDisposePass=" + troubleDisposePass + ", troubleReasonAnalyse="
			+ troubleReasonAnalyse + ", troubleMeasure=" + troubleMeasure + ", troubleFixDuty=" + troubleFixDuty
			+ ", troubleCheckSituation=" + troubleCheckSituation + ", troubleRemark=" + troubleRemark + "]";
}



}
