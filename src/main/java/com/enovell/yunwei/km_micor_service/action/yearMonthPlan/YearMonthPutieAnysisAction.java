package com.enovell.yunwei.km_micor_service.action.yearMonthPlan;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.YearMonthPutieAnysisService;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.YearMonthPutieZipToFileService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

/**
 * 年月表汇总action
* @Title: YearMonthAnysisAction.java 
* @Package com.enovell.yunwei.yearMonthReport.web 
* @date 2017年9月25日 上午10:30:00 
* @author luoyan  
*/
@Controller
@RequestMapping(value = "yearMonthPutieAnysisAction")
public class YearMonthPutieAnysisAction {
	
	@Resource(name="yearMonthPutieAnysisService")
	private YearMonthPutieAnysisService service;
	
	@Resource(name="yearMonthPutieZipToFileService")
	private YearMonthPutieZipToFileService yearMonthZipToFileService;
	
	/**
	 * 汇总段下所选车间的数据
	* anysisSegment 
	* @param ids 所选车间数据的id
	* @param attachPath 由前端静态数据传的附表路径，对应数据库附表路径字段名
	* @return ResultMsg
	 */
	@RequestMapping(value = "anysisSegment",method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg anysisSegment(
			@RequestParam("ids")String ids,
			@RequestParam("attachPath")String attachPath,
			HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		return service.anysisSegment(ids, attachPath, user);
	}
	/**
	 * 
	 * anysisWorkShop 车间编制流程汇总所选工区数据
	 * @param ids 车间业务数据id
	 * @return
	 */
	@RequestMapping(value = "anysisWorkShop", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg anysisWorkShop(String userString,
			@RequestParam("ids") String ids, 
			@RequestParam("attachPath") String attachPath) {
		User user = JsonUtil.jsonToJavaObj(userString, User.class);
		return service.workShopSummary(ids, user, attachPath);
	}
	
	/**
	 * 
	 * anysisWorkShopExecute 车间执行流程完成表的汇总
	 * @param ids 车间业务数据id
	 * @return
	 */
	@RequestMapping(value = "anysisWorkShopExecute", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg anysisWorkShopExecute(String userString,
			@RequestParam("id") String id, 
			@RequestParam("attachPath") String attachPath) {
		User user = JsonUtil.jsonToJavaObj(userString, User.class);
		return service.workShopSummary(id, user, attachPath);
	}

	/**
	* 科室打包下载车间及工区Excel
	* zipToFileSegment 
	* @param ids
	* @param attachPath
	* @return
	* ResultMsg
	 */
	@RequestMapping(value = "/zipToFileSegment", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg zipToFileSegment (@RequestParam("ids")String ids, 
			@RequestParam("attachPath")String attachPath){
		if (StringUtils.isBlank(ids)) {
			return ResultMsg.getFailure("所选数据不能为空！");
		}
		try {
			return yearMonthZipToFileService.zipToFileSegment(ids, attachPath);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("导出失败！");
		}
	}
	
}
