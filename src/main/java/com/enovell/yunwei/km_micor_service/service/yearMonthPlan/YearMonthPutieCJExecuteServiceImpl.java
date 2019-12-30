package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthOperStatus;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttachPuTie;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkShopDto;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.AttachShowUtil;
@Transactional
@Service("yearMonthPutieCJExecuteService")
public class YearMonthPutieCJExecuteServiceImpl implements YearMonthPutieCJExecuteService {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Resource(name="yearMonthPutieGQService")
	private YearMonthPutieGQService yearMonthPutieGQService;
	
	@Resource(name="yearMonthPuTieWSService")
	private YearMonthPuTieWSService yearMonthPuTieWSService;
	
	@Override
	public void changeWorkshopStatus(String id,String status){
		String sql ="update YEAR_MONTH_PUTIE_CJ set status_=:status "
				+ " where id_=:id";
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("status", status);
		paramMap.put("id", id);
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public void reportToDuan(String reportId) {
		String status=YearMonthOperStatus.WORKSHOP_EXECUTE_REPORT;
		String sql="update YEAR_MONTH_PUTIE_CJ set STATUS_=:status, "
				+ "REPORT_TIME_=:reportTime "
				+ "where ID_ =:id ";
		Date reportTime=new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("id", reportId);
		paramMap.put("reportTime", reportTime);
		namedParameterJdbcTemplate.update(sql,paramMap);
	}
	
	@Override
	public List<AttachShowDto> getAttachDataByCJId(String id) {
		List<AttachShowDto> result = AttachShowUtil.getExecuteAttachDataPutieSingle();
		//根据id查询车间业务数据的详情
		YearMonthWorkShopDto workshopData = yearMonthPuTieWSService.getYearMonthWorkShopDtoById(id);
		//拼装附件展示数据
		result = getAttachDataByWorkShopData(result,workshopData);
		return result;
	}
	
	/**
	 * 
	 * getAttachDataByWorkAreaData 根据车间数据封装它的附件展示信息数据
	 * @author quyy
	 * @param workShopData 年月报表车间业务数据
	 * @param attachDtos 附件详情集合
	 * @return
	 */
	private List<AttachShowDto> getAttachDataByWorkShopData(List<AttachShowDto> attachDtos,YearMonthWorkShopDto workShopData){
		setAttachShowDtoEspecialData(attachDtos.get(0), workShopData.getAttachPathExecute8_1(),yearMonthPutieGQService.getHasReport(workShopData.getId(), YearMonthReportAttachPuTie.ATTACH_PATH8_1));//设置 高铁通信设备_年表 的值
		return attachDtos;
	}
	
	/**
	 * 
	 * setAttachShowDtoEspecialData 设置AttachShowDto的特殊值
	 * @author quyy
	 * @param dto 附件详情
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	private AttachShowDto setAttachShowDtoEspecialData(AttachShowDto dto,String filePath,String hasReport){
		dto.setFilePath(filePath);
		dto.setHasReport(hasReport);
		return dto;
	}

	@Override
	public List<AttachShowDto> getAttachDataDuan(String ids) {
		List<AttachShowDto> result = AttachShowUtil.getAttachDataPutie();
		if(StringUtils.isBlank(ids)) {
			return result;
		}
		result = yearMonthPutieGQService.getAttachDataOfSegmentData(result, ids);
		return result;
	}
}
