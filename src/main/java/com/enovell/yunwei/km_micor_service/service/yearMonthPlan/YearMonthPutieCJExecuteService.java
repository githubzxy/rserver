package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;

public interface YearMonthPutieCJExecuteService {
	/**
	 * 
	 * changeWorkshopStatus 改变车间业务数据状态
	 * @param id 车间业务数据id
	 * @param status 状态
	 * @author chenshuang
	 */
	void changeWorkshopStatus(String id, String status);
	
	/**
	 * 查询车间附件展示数据
	* getAttachDataByGQId 
	* @param id 车间业务数据id
	* @return
	* List<AttachShowDto>
	* @author quyy
	 */
	public List<AttachShowDto> getAttachDataByCJId(String id);
	
	/**
	 * 查询段附件展示数据
	* getAttachDataDuan 
	* @param ids 车间id
	* @return
	* List<AttachShowDto>
	* @author quyy
	 */
	public List<AttachShowDto> getAttachDataDuan(String ids);
	/**
	 * 
	 * reportToDuan 车间上报到段
	 * @param reportId 车间业务数据id
	 * @author chenshuang
	 */
	void reportToDuan(String reportId);
	

}
