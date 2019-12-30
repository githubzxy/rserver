package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class ObstacleQueryDto implements Serializable {

	private static final long serialVersionUID = 4822225987928480404L;
//责任车间	 
public String obligationDepart;
public String obstacleType ;
public String obstacleDuty ;
public String obstacleDate ;
public String obstaclePlace ;
public String obstacleDeviceType;
public String  obstacleDeviceName;
public String  obstacleOccurDate;
public String  obstacleRecoverDate;
public String  obstacleDelayMinutes;
public String  obstacleReceiver;
public String  obstacleDelayCoach;
public String  obstacleDelayTruck;
public String  obstaclePhenomenon;
public String  obstacleReasonAnalyse;
public String  obstacleMeasure;
public String  obstacleSuingPeople;
public String  obstacleSuingDate;
public String getObligationDepart() {
	return obligationDepart;
}
public void setObligationDepart(String obligationDepart) {
	this.obligationDepart = obligationDepart;
}
public String getObstacleType() {
	return obstacleType;
}
public void setObstacleType(String obstacleType) {
	this.obstacleType = obstacleType;
}
public String getObstacleDuty() {
	return obstacleDuty;
}
public void setObstacleDuty(String obstacleDuty) {
	this.obstacleDuty = obstacleDuty;
}
public String getObstacleDate() {
	return obstacleDate;
}
public void setObstacleDate(String obstacleDate) {
	this.obstacleDate = obstacleDate;
}
public String getObstaclePlace() {
	return obstaclePlace;
}
public void setObstaclePlace(String obstaclePlace) {
	this.obstaclePlace = obstaclePlace;
}
public String getObstacleDeviceType() {
	return obstacleDeviceType;
}
public void setObstacleDeviceType(String obstacleDeviceType) {
	this.obstacleDeviceType = obstacleDeviceType;
}
public String getObstacleDeviceName() {
	return obstacleDeviceName;
}
public void setObstacleDeviceName(String obstacleDeviceName) {
	this.obstacleDeviceName = obstacleDeviceName;
}
public String getObstacleOccurDate() {
	return obstacleOccurDate;
}
public void setObstacleOccurDate(String obstacleOccurDate) {
	this.obstacleOccurDate = obstacleOccurDate;
}
public String getObstacleRecoverDate() {
	return obstacleRecoverDate;
}
public void setObstacleRecoverDate(String obstacleRecoverDate) {
	this.obstacleRecoverDate = obstacleRecoverDate;
}
public String getObstacleDelayMinutes() {
	return obstacleDelayMinutes;
}
public void setObstacleDelayMinutes(String obstacleDelayMinutes) {
	this.obstacleDelayMinutes = obstacleDelayMinutes;
}
public String getObstacleReceiver() {
	return obstacleReceiver;
}
public void setObstacleReceiver(String obstacleReceiver) {
	this.obstacleReceiver = obstacleReceiver;
}
public String getObstacleDelayCoach() {
	return obstacleDelayCoach;
}
public void setObstacleDelayCoach(String obstacleDelayCoach) {
	this.obstacleDelayCoach = obstacleDelayCoach;
}
public String getObstacleDelayTruck() {
	return obstacleDelayTruck;
}
public void setObstacleDelayTruck(String obstacleDelayTruck) {
	this.obstacleDelayTruck = obstacleDelayTruck;
}
public String getObstaclePhenomenon() {
	return obstaclePhenomenon;
}
public void setObstaclePhenomenon(String obstaclePhenomenon) {
	this.obstaclePhenomenon = obstaclePhenomenon;
}
public String getObstacleReasonAnalyse() {
	return obstacleReasonAnalyse;
}
public void setObstacleReasonAnalyse(String obstacleReasonAnalyse) {
	this.obstacleReasonAnalyse = obstacleReasonAnalyse;
}
public String getObstacleMeasure() {
	return obstacleMeasure;
}
public void setObstacleMeasure(String obstacleMeasure) {
	this.obstacleMeasure = obstacleMeasure;
}
public String getObstacleSuingPeople() {
	return obstacleSuingPeople;
}
public void setObstacleSuingPeople(String obstacleSuingPeople) {
	this.obstacleSuingPeople = obstacleSuingPeople;
}
public String getObstacleSuingDate() {
	return obstacleSuingDate;
}
public void setObstacleSuingDate(String obstacleSuingDate) {
	this.obstacleSuingDate = obstacleSuingDate;
}
@Override
public String toString() {
	return "ObstacleQueryDto [obligationDepart=" + obligationDepart + ", obstacleType=" + obstacleType
			+ ", obstacleDuty=" + obstacleDuty + ", obstacleDate=" + obstacleDate + ", obstaclePlace=" + obstaclePlace
			+ ", obstacleDeviceType=" + obstacleDeviceType + ", obstacleDeviceName=" + obstacleDeviceName
			+ ", obstacleOccurDate=" + obstacleOccurDate + ", obstacleRecoverDate=" + obstacleRecoverDate
			+ ", obstacleDelayMinutes=" + obstacleDelayMinutes + ", obstacleReceiver=" + obstacleReceiver
			+ ", obstacleDelayCoach=" + obstacleDelayCoach + ", obstacleDelayTruck=" + obstacleDelayTruck
			+ ", obstaclePhenomenon=" + obstaclePhenomenon + ", obstacleReasonAnalyse=" + obstacleReasonAnalyse
			+ ", obstacleMeasure=" + obstacleMeasure + ", obstacleSuingPeople=" + obstacleSuingPeople
			+ ", obstacleSuingDate=" + obstacleSuingDate + "]";
}



}
