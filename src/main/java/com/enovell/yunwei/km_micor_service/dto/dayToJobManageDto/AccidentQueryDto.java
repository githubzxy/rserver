package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class AccidentQueryDto implements Serializable {

   
	 /**   
	  * 变量 serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	 
	
	private static final long serialVersionUID = -151293657218587577L;
//责任车间	 
public String obligationDepart;
public String accidentLineName ;
public String accidentDate ;
public String accidentLevel ;
public String accidentDetailLevel ;
public String accidentDigest;
public String  accidentReason;
public String  accidentGeneral;
public String  accidentDutySituation;
public String getObligationDepart() {
	return obligationDepart;
}
public void setObligationDepart(String obligationDepart) {
	this.obligationDepart = obligationDepart;
}
public String getAccidentLineName() {
	return accidentLineName;
}
public void setAccidentLineName(String accidentLineName) {
	this.accidentLineName = accidentLineName;
}
public String getAccidentDate() {
	return accidentDate;
}
public void setAccidentDate(String accidentDate) {
	this.accidentDate = accidentDate;
}
public String getAccidentLevel() {
	return accidentLevel;
}
public void setAccidentLevel(String accidentLevel) {
	this.accidentLevel = accidentLevel;
}
public String getAccidentDetailLevel() {
	return accidentDetailLevel;
}
public void setAccidentDetailLevel(String accidentDetailLevel) {
	this.accidentDetailLevel = accidentDetailLevel;
}
public String getAccidentDigest() {
	return accidentDigest;
}
public void setAccidentDigest(String accidentDigest) {
	this.accidentDigest = accidentDigest;
}
public String getAccidentReason() {
	return accidentReason;
}
public void setAccidentReason(String accidentReason) {
	this.accidentReason = accidentReason;
}
public String getAccidentGeneral() {
	return accidentGeneral;
}
public void setAccidentGeneral(String accidentGeneral) {
	this.accidentGeneral = accidentGeneral;
}
public String getAccidentDutySituation() {
	return accidentDutySituation;
}
public void setAccidentDutySituation(String accidentDutySituation) {
	this.accidentDutySituation = accidentDutySituation;
}
@Override
public String toString() {
	return "AccidentQueryDto [obligationDepart=" + obligationDepart + ", accidentLineName=" + accidentLineName
			+ ", accidentDate=" + accidentDate + ", accidentLevel=" + accidentLevel + ", accidentDetailLevel="
			+ accidentDetailLevel + ", accidentDigest=" + accidentDigest + ", accidentReason=" + accidentReason
			+ ", accidentGeneral=" + accidentGeneral + ", accidentDutySituation=" + accidentDutySituation + "]";
}




}
