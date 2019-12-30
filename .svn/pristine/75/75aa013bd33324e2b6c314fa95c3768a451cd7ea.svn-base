package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.List;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;

/**
 * 
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthWorkAreaExecuteService   
 * 类描述: 年月报表执行工区接口 
 * 创建人：quyy 
 * 创建时间：2017年11月8日 上午11:06:39 
 *
 */
public interface YearMonthPuTieGQExecuteService {
	/**
	 * 查询工区执行附件展示数据
	* getAttachDataByGQId 
	* @param id 年月报表id
	* @param user 登录用户 
	* @return
	* List<AttachShowDto>
	 */
	public List<AttachShowDto> getAttachDataByGQId(String id, User user);
	/**
	 * 
	 * changeWorkareaStatus 改变工区数据状态
	 * @param id 工区业务数据id
	 * @param status 工区报表状态
	 * @author chenshuang
	 */
	public void changeWorkareaStatus(String id, String status);
	/**
	 * 
	 * reportToWorkshop 工区上报到车间
	 * @param id
	 * @author chenshuang
	 */
	public void reportToWorkshop(String id);
	/**
	 * getMonthReportMould 获取月表模板（执行月表，包含12个sheet页）。此处将模板的sheet页复制12份
	 * @param filePath 模板路径（单个sheet页路径，不包含项目名）
	 * @author chenshuang
	 */
	public void getMonthReportMould(String filePath);
}
