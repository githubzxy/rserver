package com.enovell.yunwei.km_micor_service.dto;
/**    
* @Description: 维修方案申请表需填入数据处的行和列号枚举  
* @date 2019年3月25日
* @author yangsy
*/
public enum PointInnerSecondMaintainApplyWordCell {
	//字段同km_micor_service/dto/PointInnerSecondMaintainApplyDto.java
	//维修方案
	  constructionProject(2,1),
    //提交部门
	  submitOrgName(3,1),
	//附件
	  uploadFileArr(4,1),
	//审批人
	  approver(5,1),
	//审批日期
	  approveDate(5,3),
	//车间审批意见
	  approveAdvice(6,1);

	private int row;
	private int col;
	
	private PointInnerSecondMaintainApplyWordCell(int row, int col) {
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