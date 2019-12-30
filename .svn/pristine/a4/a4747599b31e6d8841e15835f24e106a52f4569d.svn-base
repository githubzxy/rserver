/**
 * 
 */
package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;
import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkAreaDto;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkShopDto;


/**
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthPuTieWSService   
 * 类描述:  普铁车间编制流程service
 * 创建人：chenshuang 
 * 创建时间：2017年12月1日 下午5:14:22  
 *
 */
public interface  YearMonthPuTieWSService {
	
	/**
	 * 工区上报时新增车间数据
	* add 
	* @param name 报表名称
	* @param year 年份
	* @param orgId 车间id
	* @param orgName 车间名称
	* String 返回新增数据的id
	* @author 
	 */
	public String add(String name, String year, String orgId,String orgName);
	
	/**
	 * 判断是否存在该数据 依据：同年同组织机构且存在视为已存在
	* existData 
	* @param table 表名
	* @param year 年份
	* @param orgId 组织机构id
	* @param id 修改时，除去本条数据,若不必排除，则传null
	* @return
	* boolean
	* @author luoyan
	 */
	public boolean existData(String table,String year, String orgId,String id);
	
	/**
	 * 
	 * updateWorkAreaStatus 改变工区业务数据的状态
	 * @author quyy
	 * @param ids 工区id
	 * @param status 工区状态
	 * @param reasons 打回原因
	 * @return
	 */
	public boolean updateWorkAreaStatus(List<String> ids,String status,String reasons);
	
	/**
	 * 
	 * updateWorkAreaStatus 改变车间业务数据的状态
	 * @author quyy
	 * @param ids 车间id
	 * @param status 车间状态
	 * @return
	 */
	public boolean changeWorkshopStatus(List<String> ids,String status);
	/**
	 * 
	 * reportToDuan 车间报表上报到段
	 * @param id 车间报表id
	 * @author chenshuang
	 */
	void reportToDuan(String id);
	
	/**
	 * 
	 * getDatas 根据条件查询数据
	 * @param dto 查询条件YearMonthWorkAreaDto
	 * @param orgId 当前登录用户组织机构id
	 * @return
	 */
	public List<YearMonthWorkShopDto> getDatas(YearMonthWorkAreaDto dto,String orgId); 
	
	/**
	 * 
	 * auditBatch 批量审核通过
	 * @author 
	 * @param workShopIds 车间业务数据的id集合
	 * @param workAreaIds 工区业务数据的id集合
	 * @param status 工区状态
	 * @return
	 */
	public boolean auditBatchPass(List<String> workAreaIds,String status);
	
	/**
	 * 
	 * auditBatchNotPass 批量审核通过
	 * @author 
	 * @param workShopIds 车间业务数据的id集合
	 * @param workAreaIds 工区业务数据的id集合
	 * @param status 工区状态
	 * @param reasons 打回原因
	 * @return
	 */
	public boolean auditBatchNotPass(List<String> workAreaIds,String status,String reasons);
	
	/**
	 * 
	 * getAttachPath 根据id查询车间报表路径
	 * @param id  车间业务id
	 * @param attachType 附表类型
	 * @return
	 * @author 
	 */
	public String getAttachPath(String id, String attachType);

	/**
	 * 段查询列表数据
	* getSegmentDatas 
	* @param dto 查询条件
	* @param orgId 查询条件--多个车间id,用逗号隔开
	* @return
	* List<YearMonthWorkShopDto>
	* @author luoyan
	 */
	public List<YearMonthWorkShopDto> getSegmentDatas(YearMonthWorkAreaDto dto, String orgId);
	
	/**
	 * 
	 * updateAttachFilePath 更新车间业务数据中的各个报表路径
	 * @author 
	 *  @param id 更新的那条车间业务数据的id
	 * @param attachPath 更新的报表字段
	 * @param attachPathValue 更新为的报表路径值
	 */
	public void updateAttachFilePath(String id,String attachPath,String attachPathValue);

	/**
	 * 批量删除
	* deleteBatch 
	* @param ids 车间或工区id
	* void
	* @author 
	 */
	public void deleteBatch(List<String> ids);
	
	/**
	* 根据id和附表类型获取车间Dto
	* getCJExcelsByIdAndAttach 
	* @param ids 车间数据id
	* @param attachPath 报表的名称字段名
	* @return
	* List<String>
	* @author 
	 */
	public List<YearMonthWorkShopDto> getCJExcelsByIdAndAttach(String ids, String attachPath);
	
	
	/**
	 * 
	 * getYearMonthWorkShopDtoById 根据车间数据的id查询车间的详情
	 * @author 
	 * @param id 车间数据的id
	 * @return
	 */
	public YearMonthWorkShopDto	 getYearMonthWorkShopDtoById(String id);
	
	/**
	 * 根据车间数据id和附表获取车间及其下的所有工区Excel，若车间没有Excel，则其下的工区也不获取
	 * getCJAndGQExcelsByIdAndAttach 
	 * @param ids  车间数据id
	 * @param attachPath 报表的名称字段名
	 * @return
	 * List<YearMonthWorkShopDto>
	 * @author 
	 */
	public List<YearMonthWorkShopDto> getCJAndGQExcelsByIdAndAttach(String ids, String attachPath);
	
	/**
	 * 根据车间数据id和附表获取车间及其下的所有工区Excel，若车间没有Excel，也要获取其下的工区Excel
	 * getExcelsByIdAndAttachPutie 
	 * @param ids 车间数据id
	 * @param attachPath 报表的名称字段名
	 * @return
	 * List<YearMonthWorkShopDto>
	 * @author luoyan
	 */
	public List<YearMonthWorkShopDto> getExcelsByIdAndAttachPutie(String ids, String attachPath);
	
}


