package com.enovell.yunwei.km_micor_service.dto;
/**    
* @Description: 维修方案申请表需填入数据处的行和列号枚举  
* @date 2019年3月25日
* @author yangsy
*/
public enum SkylightMaintenanceApplyWordCell {
	//字段同km_micor_service/dto/SkylightMaintenanceApplyDto.java
	project(2,1),
	type(3,1),
	orgName(3,3),
	createDateStr(4,1),
	createUserName(4,3),
	uploadFileArr(5,1),
	skillAuditor(6,1),
	skillAuditDate(6,3),
	safeAuditor(7,1),
	safeAuditDate(7,3),
	dispatchAuditor(8,1),
	dispatchAuditDate(8,3),
	approver(9,1),
	approveDate(9,3),
	skillAuditAdvice(10,1),
	safeAuditAdvice(11,1),
	dispatchAuditAdvice(12,1),
	approveAdvice(13,1);
	
	private int row;
	private int col;
	
	private SkylightMaintenanceApplyWordCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	
}
