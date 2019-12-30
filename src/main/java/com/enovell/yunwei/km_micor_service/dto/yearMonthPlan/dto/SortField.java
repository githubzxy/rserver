package com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto;

/**
 * 排序用DTO
 * @author RoySong
 * 2017年5月27日--下午2:06:54
 */
public class SortField<T>{
	private int idx;
	private T field;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public T getField() {
		return field;
	}
	public void setField(T field) {
		this.field = field;
	}
	@Override
	public String toString() {
		return "SortField [idx=" + idx + ", field=" + field + "]";
	}
	
}
