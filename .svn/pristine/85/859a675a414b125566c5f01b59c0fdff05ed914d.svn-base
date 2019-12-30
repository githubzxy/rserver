
/**   
 * 文件名：AutoBuildCompleteTableService.java    
 * @author quyy  
 * 日期：2017年12月27日 下午2:18:50      
 *   
 */

package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：AutoBuildCompleteTableService   
 * 类描述: 根据计划表自动生成完成表—service  
 * 创建人：quyy 
 * 创建时间：2017年12月27日 下午2:18:50 
 *    
 */

public interface AutoBuildCompleteTableService {

	/**
	 * 
	 * getCompleteExcelByPlanExcel 根据计划表生成完成表，用于最后几行没有其他格式的数据的计划表
	 * @author quyy
	 * @param planFilePath 计划表路径（全路径，包括文件名称，以下路径都是）
	 * @param headerFilePath 完成表表头文件路径
	 * @param completeFilePath 存放完成表的路径
	 * @param overhaulStartCellNum 计划表检修日程或月程开始列数-1
	 * @param overhalEndCellNum 计划表检修日程或月程结束列数-1
	 * @param cellNum 计划表的总列数
	 * @param herderStartRowNum 计划表表头开始行数-1
	 * @param dataStartRowNum 计划表数据开始行数-1
	 * @return
	 */
	public String getCompleteExcelByPlanExcel(String planFilePath,String headerFilePath,String completeFilePath,
			                                   int overhaulStartCellNum,int overhalEndCellNum,int cellNum,int herderStartRowNum,int dataStartRowNum);
	
	/**
	 * 
	 * getComletePuTie8_2ByPlanExcel 根据计划表生成完成表，只用于普铁科年月表中的8_2表
	 * @author quyy
	 * @param planFilePath 计划表路径（全路径，包括文件名称，以下路径都是）
	 * @param headerFilePath 完成表表头文件路径
	 * @param completeFilePath 存放完成表的路径
	 * @param overhaulStartCellNum 计划表检修日程或月程开始列数-1
	 * @param overhalEndCellNum 计划表检修日程或月程结束列数-1
	 * @param cellNum 计划表的总列数
	 * @param herderStartRowNum 计划表表头开始行数-1
	 * @param dataStartRowNum 计划表数据开始行数-1
	 * @return
	 */
	public String getComletePuTie8_2ByPlanExcel(String planFilePath,String headerFilePath,String completeFilePath,
            int overhaulStartCellNum,int overhalEndCellNum,int cellNum,int herderStartRowNum,int dataStartRowNum);
	
	/**
	 * 
	 * getComletePuTie8_3ByPlanExcel 根据计划表生成完成表，只用于普铁科年月表中的8_2和8_3表
	 * @author quyy
	 * @param planFilePath 计划表路径（全路径，包括文件名称，以下路径都是）
	 * @param headerFilePath 完成表表头文件路径
	 * @param completeFilePath 存放完成表的路径
	 * @param overhaulStartCellNum 计划表检修日程或月程开始列数-1
	 * @param overhalEndCellNum 计划表检修日程或月程结束列数-1
	 * @param cellNum 计划表的总列数
	 * @param herderStartRowNum 计划表表头开始行数-1
	 * @param dataStartRowNum 计划表数据开始行数-1
	 * @return
	 */
	public String getComletePuTie8_3ByPlanExcel(String planFilePath,String headerFilePath,String completeFilePath,
            int overhaulStartCellNum,int overhalEndCellNum,int cellNum,int herderStartRowNum,int dataStartRowNum);
}
