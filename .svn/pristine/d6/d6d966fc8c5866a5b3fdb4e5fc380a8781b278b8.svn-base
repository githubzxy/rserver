package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.List;
import java.util.Map;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;

/**
 * 
 * @ProjectName：RINMS2MAIN
 * @ClassName：WorkAreaExecute   
 * @Description: 技术支持中心工作执行页面Service
 * @author： gaohg
 * @date：2018年1月22日 下午2:58:00
 * @modifier：
 * @ModifyTime：2018年1月22日 下午2:58:00   
 *
 */
public interface WorkAreaExecuteService {

	/**
	 * 
	 * @Title: getAttachDataByWAId
	 * @Description:  工区执行 附件展示数据 
	 * @param id      工区年月报表id
	 * @param user    登录用户
	 * @return List<AttachShowDto> (返回类型 )
	 */
	List<AttachShowDto> getAttachDataByWAId(String id, User user);
	
	/**
	 * readExecuteReportData 读取所有工区的计划excel封装成集合 (读取0~lastColumn列的数据)
	 * @param allWorkAreaExcels excel文件名集合
	 * @param headerRownum 表头行号(从0开始)
	 * @param dataStartRownum 数据起始行号(从0开始)
	 * @param filePath 文件路径(不包含文件名)
	 * @return List<Integer, List<Map>> key为月份，value为月份对应的报表内容
	 * @author chenShuang
	 */
	@SuppressWarnings("rawtypes")
	List<List<Map>> readExecuteReportData(List<String> allWorkAreaExcels, int headerRownum, int dataStartRownum,
			String filePath);
	
}
