package com.enovell.yunwei.km_micor_service.action.yearMonthPlan;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthOperStatus;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.domain.YearMonthPutieGQ;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkAreaDto;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.WorkAreaExecuteService;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.YearMonthPuTieGQExecuteService;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.YearMonthPutieGQService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

/**
 * 年月报表执行工区action
 * 项目名称：RINMS2MAIN
 * 类名称：YearMonthWorkAreaExecuteAction   
 * 类描述:  
 * 创建人：chenshuang 
 * 创建时间：2017年11月9日 下午4:11:02  
 *
 */

@Controller
@RequestMapping(value = "yearMonthPuTieGQExecuteAction")
public class YearMonthPuTieGQExecuteAction {
	
	@Resource(name="yearMonthPutieGQService")
	private YearMonthPutieGQService yearMonthWorkAreaService;
	
	@Resource(name="yearMonthPuTieGQExecuteService")
	private YearMonthPuTieGQExecuteService service;
	
	@Resource(name="workAreaExecuteService")
	private WorkAreaExecuteService workAreaExecuteService;
	
	/**    
	 * getAttachDataByGQId 工区执行，点击编辑查询列表数据
	 *
	 * @param id 普铁年月报表数据id
	 * @return
	 */
	@RequestMapping(value = "getAttachDataByGQId", method = RequestMethod.POST)
	@ResponseBody
	public String getAttachDataByGQId(HttpServletRequest request,
			@RequestParam("id") String id,
			String userString) {
		User user = JsonUtil.jsonToJavaObj(userString, User.class);
		List<AttachShowDto> result = workAreaExecuteService.getAttachDataByWAId(id, user);
		return JsonUtil.javaToJsonAsString(result);
	}
	
	/**
	 * 
	 * queryAllData 工区分页查询数据
	 * @param request
	 * @param name 名称
	 * @param year 年份
	 * @param status 状态
	 * @param start
	 * @param limit
	 * @return
	 * @author chenshuang
	 */
	@RequestMapping(value = "queryAllData",method = RequestMethod.POST)
	@ResponseBody
	public GridDto<YearMonthPutieGQ> queryAllData(HttpServletRequest request,
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) String year, 
			@RequestParam(required = false) String status, 
			String userString,
			@RequestParam("start")int start,@RequestParam("limit")int limit){
		User user = JsonUtil.jsonToJavaObj(userString, User.class);
		YearMonthWorkAreaDto dto = new YearMonthWorkAreaDto();
		if (StringUtils.isNotBlank(name)) {
			dto.setName(name);
		}
		if (StringUtils.isNotBlank(year)) {
			dto.setYear(year);
		}
		if (StringUtils.isNotBlank(status)) {
			dto.setStatus(status);
		}
		dto.setPage(YearMonthOperStatus.EXECUTE_PAGE);
		GridDto<YearMonthPutieGQ> gd = yearMonthWorkAreaService.queryAllData(dto, start, limit,user);
		return gd;
	}
	
	/**
	 * 
	 * reportToWorkShop 工区上报到车间
	 * @param request
	 * @param id 工区业务数据id
	 * @return ResultMsg
	 */
	@RequestMapping(value = "reportToWorkShop", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg reportToWorkShop(@RequestParam("id") String id) {
		try {
			service.reportToWorkshop(id);
			return ResultMsg.getSuccess("上报成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("上报失败！");
		}
	}
	
	@RequestMapping(value = "getAttachExecuteDataDuan", method = RequestMethod.POST)
	@ResponseBody
	public String getAttachExecuteDataDuan(@RequestParam("ids") String ids) {
		List<AttachShowDto> result = yearMonthWorkAreaService.getAttachExecuteDataDuan(ids);
		return JsonUtil.javaToJsonAsString(result);
		
	}
}
