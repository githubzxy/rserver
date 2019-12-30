package com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto;

import java.io.Serializable;

public class SecurityQueryDto implements Serializable {

   
	 /**   
	  * 变量 serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	 
	
	private static final long serialVersionUID = 9076455025229350070L;
//责任车间	 
public String obligationDepart;
public String securityLineName ;
public String securityDate ;
public String securityLevel ;
public String securityDetailLevel ;
public String securityDigest;
public String  securityReason;
public String  securityGeneral;
public String  securityDutySituation;
public String getObligationDepart() {
	return obligationDepart;
}
public void setObligationDepart(String obligationDepart) {
	this.obligationDepart = obligationDepart;
}
public String getSecurityLineName() {
	return securityLineName;
}
public void setSecurityLineName(String securityLineName) {
	this.securityLineName = securityLineName;
}
public String getSecurityDate() {
	return securityDate;
}
public void setSecurityDate(String securityDate) {
	this.securityDate = securityDate;
}
public String getSecurityLevel() {
	return securityLevel;
}
public void setSecurityLevel(String securityLevel) {
	this.securityLevel = securityLevel;
}
public String getSecurityDetailLevel() {
	return securityDetailLevel;
}
public void setSecurityDetailLevel(String securityDetailLevel) {
	this.securityDetailLevel = securityDetailLevel;
}
public String getSecurityDigest() {
	return securityDigest;
}
public void setSecurityDigest(String securityDigest) {
	this.securityDigest = securityDigest;
}
public String getSecurityReason() {
	return securityReason;
}
public void setSecurityReason(String securityReason) {
	this.securityReason = securityReason;
}
public String getSecurityGeneral() {
	return securityGeneral;
}
public void setSecurityGeneral(String securityGeneral) {
	this.securityGeneral = securityGeneral;
}
public String getSecurityDutySituation() {
	return securityDutySituation;
}
public void setSecurityDutySituation(String securityDutySituation) {
	this.securityDutySituation = securityDutySituation;
}
@Override
public String toString() {
	return "SecurityQueryDto [obligationDepart=" + obligationDepart + ", securityLineName=" + securityLineName
			+ ", securityDate=" + securityDate + ", securityLevel=" + securityLevel + ", securityDetailLevel="
			+ securityDetailLevel + ", securityDigest=" + securityDigest + ", securityReason=" + securityReason
			+ ", securityGeneral=" + securityGeneral + ", securityDutySituation=" + securityDutySituation + "]";
}




}
