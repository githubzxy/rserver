package com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto;

/**
 * excel合并单元格范围dto
 * @author Roy
 * 2017年5月23日--上午10:44:48
 */
public class MergeRange{
	private int firstRow;
	private int lastRow;
	private int firstCol;
	private int lastCol;
	public int getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}
	public int getLastRow() {
		return lastRow;
	}
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
	public int getFirstCol() {
		return firstCol;
	}
	public void setFirstCol(int firstCol) {
		this.firstCol = firstCol;
	}
	public int getLastCol() {
		return lastCol;
	}
	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}
	@Override
	public String toString() {
		return "MergeRange [firstRow=" + firstRow + ", lastRow=" + lastRow + ", firstCol=" + firstCol + ", lastCol="
				+ lastCol + "]";
	}
	
}
