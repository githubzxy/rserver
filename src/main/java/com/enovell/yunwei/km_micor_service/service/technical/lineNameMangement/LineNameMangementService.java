package com.enovell.yunwei.km_micor_service.service.technical.lineNameMangement;

import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.LineNameDto;
/**
 * 
 * @ProjectName：RINMS2MAIN
 * @ClassName：LineNameMangementService 
 * @Description: 昆明安调Oracle数据库线别名称表管理Service
 * @date：2017年11月1日 下午2:25:58
 * @modifier：yangsy
 * @ModifyTime：2019年11月12日 下午2:25:58   
 *
 */
public interface LineNameMangementService {
	
	/**
	 * 根据线别名称获取线别ID
	 * @return
	 */
	String getLineDataIdByLineName(String LineName);
	
	/**
	 * 获取线名数据（用于下拉选择）
	 * @return
	 */
	List<String> getLineData();

	/**
	 * 主页条件查询
	 * @param name 线别名称
	 * @param desc 备注
	 * @param start 0
	 * @param limit 10
	 * @return
	 */
	GridDto<LineNameDto> getAllDataBySearch(String name,String desc,int start, int limit);
	
	/**
	 * 
	 * @Title: addLine
	 * @Description: 	      新增线别
	 * @return ResultMsg (返回类型 )
	 * @throws 
	 */
	ResultMsg addLine(String lineName,String desc) throws Exception;
	
	/**
	 * 
	 * @Title: getDataById
	 * @Description:  		根据id查询线别
	 * @param reportId		业务报表id
	 * @throws 
	 */
	LineNameDto getDataById(String id);
	
	/**
	 * 修改
	 */
	ResultMsg update(String id,String name,String desc);
	
	/**
	 * @Title: delete
	 * @Description:删除线别（物理删除）
	 */
	int deleteById(String id);
	
}
