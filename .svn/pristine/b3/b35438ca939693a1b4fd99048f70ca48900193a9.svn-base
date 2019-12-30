package com.enovell.yunwei.km_micor_service.action.yearMonthPlan;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enovell.yunwei.enocommon.dto.ResultMsg;
import com.enovell.yunwei.enocommon.utils.JsonUtil;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthOperStatus;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.AttachShowDto;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.YearMonthPuTieWSService;
import com.enovell.yunwei.km_micor_service.service.yearMonthPlan.YearMonthPutieCJExecuteService;

@Controller
@RequestMapping(value = "yearMonthPutieCJExecuteAction")
public class YearMonthPutieCJExecuteAction {

	@Resource(name = "yearMonthPuTieWSService")
	private YearMonthPuTieWSService yearMonthPuTieWSService;

	@Resource(name = "yearMonthPutieCJExecuteService")
	private YearMonthPutieCJExecuteService yearMonthPutieCJExecuteService;

	/**
	 * reportToDuan 车间上报到段
	 * @param request
	 * @param reportId 车间业务数据id
	 * @return
	 */
	@RequestMapping(value = "reportToDuan", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg reportToDuan(HttpServletRequest request,
			@RequestParam("reportId") String reportId) {
		try {
			yearMonthPutieCJExecuteService.reportToDuan(reportId);
			return ResultMsg.getSuccess("上报成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("上报失败！");
		}
	}

	/**
	 * auditNotPass 车间审核不通过
	 * @param workAreaIds 待审核车间id
	 * @param reasons 不通过原因
	 * @return
	 */
	@RequestMapping(value = "auditNotPass", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg auditNotPass(
			@RequestParam("workAreaIds") String workAreaIds,
			@RequestParam("reasons") String reasons) {
		try {
			String[] gcIds = workAreaIds.split(",");
			yearMonthPuTieWSService.auditBatchNotPass(Arrays.asList(gcIds),
					YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL,
					reasons);
			return ResultMsg.getSuccess("退回成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("退回失败！");
		}
	}

	/**
	 * auditPass 车间审核通过
	 * @param workAreaIds 待审核车间id
	 * @return
	 */
	@RequestMapping(value = "auditPass", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg auditPass(
			@RequestParam("workAreaIds") String workAreaIds) {
		try {
			String[] gcIds = workAreaIds.split(",");
			yearMonthPuTieWSService.auditBatchPass(Arrays.asList(gcIds),
					YearMonthOperStatus.WORKAREA_WORKSHOP_EXECUTE_PASS);
			return ResultMsg.getSuccess("通过成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("通过失败！");
		}
	}

	/**
	 * getAttachDataByCJId 查询车间附件展示数据
	 * @param id 车间业务数据id
	 * @return
	 */
	@RequestMapping(value = "getAttachDataByCJId", method = RequestMethod.POST)
	@ResponseBody
	public String getAttachDataByCJId(@RequestParam("id") String id) {
		List<AttachShowDto> result = yearMonthPutieCJExecuteService.getAttachDataByCJId(id);
		return JsonUtil.javaToJsonAsString(result);
	}
	
	/**
	 * getAttachDataDuan 查询段附件展示数据
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "getAttachDataDuan", method = RequestMethod.POST)
	@ResponseBody
	public String getAttachDataDuan(@RequestParam("ids") String ids) {
		List<AttachShowDto> result = yearMonthPutieCJExecuteService.getAttachDataDuan(ids);
		return JsonUtil.javaToJsonAsString(result);
	}
	///////////////////////////////// 段科室操作开始///////////////////////////////////////////////////

	/**
	 * segmentNotPass 段审核不通过
	 * @param workAreaIds 工区id
	 * @param reasons 不通过原因
	 * @return
	 */
	@RequestMapping(value = "segmentNotPass", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg segmentNotPass(
			@RequestParam("workAreaIds") String workAreaIds,
			@RequestParam("reasons") String reasons) {
		try {
			String[] gcIds = workAreaIds.split(",");
			yearMonthPuTieWSService.auditBatchNotPass(Arrays.asList(gcIds),
					YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_FAIL, reasons);
			return ResultMsg.getSuccess("退回成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("退回失败！");
		}
	}

	/**
	 * 段批量通过 segmentAuditbatchPass
	 * @param workAreaIds 待审核工区业务id
	 * @return ResultMsg
	 */
	@RequestMapping(value = "segmentAuditPass", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg segmentAuditPass(
			@RequestParam("workAreaIds") String workAreaIds) {
		try {
			String[] gcIds = workAreaIds.split(",");
			yearMonthPuTieWSService.auditBatchPass(Arrays.asList(gcIds),
					YearMonthOperStatus.WORKAREA_SEGMENT_EXECUTE_PASS);
			return ResultMsg.getSuccess("通过成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("通过失败！");
		}
	}
	///////////////////////////////////// 段科室操作结束/////////////////////////////////////////////////////////

}
