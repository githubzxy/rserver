package com.enovell.yunwei.km_micor_service.adInterFace;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;

@Controller
@RequestMapping("/pageKmms")
public class PageKmmsAction {

	@Resource(name = "userService")
	private UserService userService;

	// 段日交班信息页面
	@RequestMapping(value = "/page/dailyShiftForDuan", method = RequestMethod.GET)
	public String dailyShiftDuan(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "dailyShiftForDuan";
	}

	// 车间日交班信息页面
	@RequestMapping(value = "/page/dailyShiftForCj", method = RequestMethod.GET)
	public String dailyShiftCj(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "dailyShiftForCj";
	}

	// 防洪
	@RequestMapping(value = "/page/floodGuardPoint", method = RequestMethod.GET)
	public String floodGuardPoint(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "floodGuadPointManagement";
	}

	// 综合管理-考勤管理-入口模块
	@RequestMapping(value = "/page/attendance", method = RequestMethod.GET)
	public String attendance(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "attendance";
	}

	/*************************** 安全风险管理模块 *********************************/
	// 安全风险数据库页面
	@RequestMapping(value = "/page/securityRiskDatabase", method = RequestMethod.GET)
	public String securityRiskDatabase(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "securityRiskDatabase";
	}

	// 安全风险项点页面
	@RequestMapping(value = "/page/securityRiskPoint", method = RequestMethod.GET)
	public String securityRiskPoint(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "securityRiskPoint";
	}

	/*************************** 安全分析模块 *********************************/
	// 安全分析页面
	@RequestMapping(value = "/page/safetyAnalysis", method = RequestMethod.GET)
	public String safetyAnalysis(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "safetyAnalysis";
	}

	/*************************** 施工计划管理模块 *********************************/
	// 施工计划页面
	@RequestMapping(value = "/page/workPlan", method = RequestMethod.GET)
	public String workPlan(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "workPlan";
	}

	// 通用台账-施工配合协议-入口模块
	@RequestMapping(value = "/page/workAssortProtocol", method = RequestMethod.GET)
	public String workAssortProtocol(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "workAssortProtocol";
	}

	/*************************** 点内维修计划管理模块 *********************************/
	// 点内维修计划计划页面
	@RequestMapping(value = "/page/pointInnerMaintainPlan", method = RequestMethod.GET)
	public String pointInnerMaintainPlan(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "pointInnerMaintainPlan";
	}

	// 施工管理-点外维修计划管理-点外维修计划-入口模块
	@RequestMapping(value = "/page/pointOuterMaintainPlan", method = RequestMethod.GET)
	public String pointOuterMaintainPlan(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "pointOuterMaintainPlan";
	}

	// 应急管理-应急通信设备台账-检修记录-入口模块
	@RequestMapping(value = "/page/maintenanceRecord", method = RequestMethod.GET)
	public String maintenanceRecord(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "maintenanceRecord";
	}

	// 事故故障障碍管理
	@RequestMapping(value = "/page/fault", method = RequestMethod.GET)
	public String fault(@RequestParam String userId, HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "faultManagement";
	}

	// 日交班信息
	@RequestMapping("/page/dayToJob")
	public String hello(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		String systemBeforeDate = JsonUtil.getSystemBeforeDate();
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("systemBeforeDate", String.valueOf(systemBeforeDate));// 当前系统日期的前一天
		return "dayToJob";
	}

	// 施工配合
	@RequestMapping("/page/constructCooperate")
	public String constructCooperate(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "constructCooperate";
	}

	// 网管信息
	@RequestMapping("/page/networkManageInfo")
	public String networkManageInfo(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "networkManageInfo";
	}

	// 其他生产信息
	@RequestMapping("/page/otherProductionInfo")
	public String otherProductionInfo(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "otherProductionInfo";
	}

	// 遗留信息
	@RequestMapping("/page/remainInfo")
	public String remainInfo(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "remainInfo";
	}

	// 施工维修天窗信息
	@RequestMapping("/page/constructRepair")
	public String constructRepair(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "constructRepair";
	}

	// 电视电话会议通知
	@RequestMapping("/page/videoPhoneNotice")
	public String videoPhoneNotice(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "videoPhoneNotice";
	}

	// 上级安全问题通知书情况
	@RequestMapping("/page/superiorSafetyNotification")
	public String superiorSafetyNotification(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "superiorSafetyNotification";
	}

	// 重点工作完成情况
	@RequestMapping("/page/completionOfKeyTasks")
	public String completionOfKeyTasks(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "completionOfKeyTasks";
	}

	// 集团公司重点追查安全信息
	@RequestMapping("/page/companyTraceSafetyInfomation")
	public String companyTraceSafetyInfomation(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "companyTraceSafetyInfomation";
	}

	// 段重点追查安全信息
	@RequestMapping("/page/duanTraceSafetyInfomation")
	public String duanTraceSafetyInfomation(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "duanTraceSafetyInfomation";
	}

	// 段发安全问题通知书情况
	@RequestMapping("/page/duanSafetyNotification")
	public String duanSafetyNotification(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "duanSafetyNotification";
	}

	// 点外维修申请
	@RequestMapping("/page/pointOuterMaintainApply")
	public String pointOuterMaintainApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointOuterMaintainApply";
	}

	// 点外维修审核
	@RequestMapping("/page/pointOuterMaintainAudit")
	public String pointOuterMaintainAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointOuterMaintainAudit";
	}

	// 点外维修审批
	@RequestMapping("/page/pointOuterMaintainApprove")
	public String pointOuterMaintainApprove(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointOuterMaintainApprove";
	}

	// 点外维修查询
	@RequestMapping("/page/pointOuterMaintainQuery")
	public String pointOuterMaintainQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointOuterMaintainQuery";
	}

	// 二级维修方案申请
	@RequestMapping("/page/pointInnerSecondMaintainApply")
	public String pointInnerSecondMaintainApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointInnerSecondMaintainApply";
	}

	// 二级维修方案审批
	@RequestMapping("/page/pointInnerSecondMaintainApprove")
	public String pointInnerSecondMaintainApprove(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointInnerSecondMaintainApprove";
	}

	// 二级维修方案查询
	@RequestMapping("/page/pointInnerSecondMaintainQuery")
	public String pointInnerSecondMaintainQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "pointInnerSecondMaintainQuery";
	}

	// 事故/故障/障碍查询
	@RequestMapping("/page/accidentTroubleObstacleQuery")
	public String accidentTroubleObstacleQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "accidentTroubleObstacleQuery";
	}

	// 施工管理-施工计划管理-施工计划申请
	@RequestMapping("/page/constructionApply")
	public String constructionPlan(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		return "constructionApply";
	}

	// 施工管理-施工计划管理-施工计划科室审核
	@RequestMapping("/page/constructionAudit")
	public String constructionAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		// request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		return "constructionAudit";
	}

	// 施工管理-施工计划管理-施工计划段领导审批
	@RequestMapping("/page/constructionApproved")
	public String constructionApproved(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "constructionApproved";
	}

	// 施工管理-施工计划管理-施工计划查询
	@RequestMapping("/page/constructionQuery")
	public String constructionQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "constructionQuery";
	}

	// 天窗维修申请
	@RequestMapping("/page/skylightMaintenanceApply")
	public String skylightMaintenanceApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "skylightMaintenanceApply";
	}

	// 天窗维修技术科审核
	@RequestMapping("/page/skylightMaintenanceSkillAudit")
	public String skylightMaintenanceSkillAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "skylightMaintenanceSkillAudit";
	}

	// 天窗维修安全科审核
	@RequestMapping("/page/skylightMaintenanceSafeAudit")
	public String skylightMaintenanceSafeAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "skylightMaintenanceSafeAudit";
	}

	// 天窗维修调度科审核
	@RequestMapping("/page/skylightMaintenanceDispatchAudit")
	public String skylightMaintenanceDispatchAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "skylightMaintenanceDispatchAudit";
	}

	// 天窗维修段领导审批
	@RequestMapping("/page/skylightMaintenanceApprove")
	public String skylightMaintenanceApprove(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "skylightMaintenanceApprove";
	}

	// 点外维修查询
	@RequestMapping("/page/skylightMaintenanceApplyQuery")
	public String skylightMaintenanceApplyQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "skylightMaintenanceApplyQuery";
	}

	// 应急管理-防洪看守点管理
	@RequestMapping("/page/floodGuardPointManage")
	public String floodGuardPointManage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "floodGuadPointManage";
	}

	// 生产管理-电路工单管理-电路工单新增（技术科）
	@RequestMapping("/page/circuitWorkOrderApply")
	public String circuitWorkOrderApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderApply";
	}

	// 生产管理-电路工单管理-电路工单编辑（网管中心）
	@RequestMapping("/page/circuitWorkOrderNetworkCenter")
	public String circuitWorkOrderNetworkCenter(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderNetworkCenter";
	}

	// 生产管理-电路工单管理-电路工单审核（技术科科长）
	@RequestMapping("/page/circuitWorkOrderCheck")
	public String circuitWorkOrderCheck(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderCheck";
	}

	// 生产管理-电路工单管理-电路工单审批（段领导）
	@RequestMapping("/page/circuitWorkOrderApprove")
	public String circuitWorkOrderApprove(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderApprove";
	}

	// 生产管理-电路工单管理-电路工单查询
	@RequestMapping("/page/circuitWorkOrderQuery")
	public String circuitWorkOrderQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderQuery";
	}

	// 生产管理-电路工单管理-电路工单车间回复
	@RequestMapping("/page/circuitWorkOrderWorkshopReply")
	public String circuitWorkOrderWorkshopReply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderWorkshopReply";
	}

	// 生产管理-电路工单管理-电路工单工区回复
	@RequestMapping("/page/circuitWorkOrderWorkAreaReply")
	public String circuitWorkOrderWorkAreaReply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "circuitWorkOrderWorkAreaReply";
	}

	// 施工管理-施工协议
	@RequestMapping("/page/constructProtocol")
	public String constructProtocol(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "constructProtocol";
	}

	// 施工管理-维修方案管理-维修方案申请
	@RequestMapping("/page/maintainApply")
	public String maintainApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		return "maintainApply";
	}

	// 施工管理-维修方案管理-维修方案科室审核
	@RequestMapping("/page/maintainAudit")
	public String maintainAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		// request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		return "maintainAudit";
	}

	// 施工管理-维修方案管理-维修方案段领导审批
	@RequestMapping("/page/maintainApproved")
	public String maintainApproved(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "maintainApproved";
	}

	// 施工管理-维修方案管理-维修方案查询
	@RequestMapping("/page/maintainQuery")
	public String maintainQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "maintainQuery";
	}

	// 综合管理-人员信息管理（劳人科）
	@RequestMapping("/page/userInfoManagePage")
	public String userInfoManagePage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("orgType", org.get("TYPE_"));// 获取登录用户组织类型
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "userInfoManager";
	}

	// 综合管理-人员信息管理（其他）
	@RequestMapping("/page/userInfoManageOtherPage")
	public String userInfoManageOtherPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("orgType", org.get("TYPE_"));// 获取登录用户组织类型
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "userInfoManagerOther";
	}

	// //综合管理-人员信息管理（劳人科）
	// @RequestMapping("/page/userInfoManagePage")
	// public String userInfoManagePage(HttpServletRequest request){
	// String threePerId = request.getParameter("threePerId");
	// String userId = request.getParameter("userId");//登陆用户ID
	// Map<String, Object> user = userService.getUserById(userId);
	// Map<String, Object> org = userService.getOrgbyUserId(userId);
	// request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
	// request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
	// request.setAttribute("perId", threePerId);
	// request.setAttribute("userId", userId);//登陆用户ID
	// request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
	// return "userInfoManager";
	// }
	// //综合管理-人员信息管理（其他）
	// @RequestMapping("/page/userInfoManageOtherPage")
	// public String userInfoManageOtherPage(HttpServletRequest request){
	// String threePerId = request.getParameter("threePerId");
	// String userId = request.getParameter("userId");//登陆用户ID
	// Map<String, Object> user = userService.getUserById(userId);
	// Map<String, Object> org = userService.getOrgbyUserId(userId);
	// request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
	// request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
	// request.setAttribute("perId", threePerId);
	// request.setAttribute("userId", userId);//登陆用户ID
	// request.setAttribute("orgType", org.get("TYPE_"));//获取登录用户组织类型
	// request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
	// return "userInfoManagerOther";
	// }
	// 考勤管理
	@RequestMapping("/page/attendanceManagePage")
	public String attendanceManagePage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "attendanceManage";
	}

	@RequestMapping("/page/attendanceManageCollectPage")
	public String attendanceManageCollectPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "attendanceManageCollect";
	}

	@RequestMapping("/page/attendanceManageCollectQueryPage")
	public String attendanceManageCollectQueryPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "attendanceManageCollectQuery";
	}

	// 技术管理-技术资料管理-机房技术资料及台账
	@RequestMapping("/page/technicalDocumentPage")
	public String technicalDocument(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "technicalDocumentPage";
	}
	// 技术管理-技术资料管理-年月报表工作内容配置
	@RequestMapping("/page/deviceNameWorkManage")
	public String deviceNameWorkManage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));
		return "deviceNameWorkManage";						
	}			
	// //生产管理-检查日报
	// @RequestMapping("/page/procuratorialDailyPage")
	// public String procuratorialDailyPage(HttpServletRequest request){
	// String threePerId = request.getParameter("threePerId");
	// String userId = request.getParameter("userId");//登陆用户ID
	// Map<String, Object> user = userService.getUserById(userId);
	// Map<String, Object> org = userService.getOrgbyUserId(userId);
	// request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
	// request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
	// request.setAttribute("perId", threePerId);
	// request.setAttribute("userId", userId);//登陆用户ID
	// request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
	// request.setAttribute("orgType",
	// org.get("TYPE_"));//登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
	// return "procuratorialDaily";
	// }
	// 生产管理-检查日报
	@RequestMapping("/page/procuratorialDailyPage")
	public String procuratorialDailyPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		// 页面展示需要， 工区需要查询其所属车间
		String parentNameString = (String) org.get("ORG_NAME_");
		if (org.get("TYPE_").toString().equals("1503")) {// 是工区还需获取到 其所属车间机构信息
			String parentIdString = (String) org.get("PARENT_ID_");
			Map<String, Object> ParentOrg = userService.getParentOrgById(parentIdString);
			parentNameString = (String) ParentOrg.get("ORG_NAME_");
		}
		request.setAttribute("parentOrgName", parentNameString);
		return "procuratorialDaily";
	}

	// 生产管理-维修工作备忘录
	@RequestMapping("/page/maintenanceMemorendunPage")
	public String maintenanceMemorendunPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "maintenanceMemorendun";
	}

	// 生产管理-工作日志
	@RequestMapping("/page/jobRecordPage")
	public String jobRecord(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String currentDateStr = "";
		currentDateStr = sdf.format(currentDate);
		request.setAttribute("currentDay", currentDateStr);
		return "jobRecordPage";
	}

	// 生产管理-工作日志查询（领导使用）
	@RequestMapping("/page/jobRecordQueryPage")
	public String jobRecordQueryPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String currentDateStr = "";
		currentDateStr = sdf.format(currentDate);
		request.setAttribute("currentDay", currentDateStr);
		return "jobRecordQueryPage";
	}

	// 生产管理-检修记录
	@RequestMapping("/page/overhaulRecordPage")
	public String overhaulRecordPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "overhaulRecord";
	}

	// 生产管理-日交班管理-值班管理
	@RequestMapping("/page/dayDutyManagePage")
	public String dayDutyManagePage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "dayDutyManagePage";
	}

	// 干部值班情况
	@RequestMapping("/page/cadreDuty")
	public String cadreDuty(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "cadreDuty";
	}

	// 上级检查情况
	@RequestMapping("/page/inspectionSuperiors")
	public String inspectionSuperiors(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "inspectionSuperiors";
	}

	// 生产管理-临时任务管理-临时任务新增
	@RequestMapping("/page/taskApply")
	public String taskApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "taskApply";
	}

	// 生产管理-临时任务管理-临时任务审核（技术科科长）
	@RequestMapping("/page/taskCheck")
	public String taskCheck(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "taskCheck";
	}

	// 生产管理-临时任务管理-临时任务查询（所有）
	@RequestMapping("/page/taskQuery")
	public String taskQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "taskQuery";
	}

	// 生产管理-临时任务管理-临时任务查询（所有）
	@RequestMapping("/page/taskQueryOfSendUser")
	public String taskQueryOfSendUser(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "taskQueryOfSendUser";
	}

	// 生产管理-临时任务管理-临时任务车间回复
	@RequestMapping("/page/taskWorkshopReply")
	public String taskWorkshopReply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "taskWorkshopReply";
	}

	// 生产管理-临时任务管理-临时任务工区回复
	@RequestMapping("/page/taskWorkAreaReply")
	public String taskWorkAreaReply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "taskWorkAreaReply";
	}

	@RequestMapping("/page/fileShare")
	public String in(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "fileShare";
	}

	// 技术管理-技术资料管理-进出机房人员信息
	@RequestMapping("/page/visitRoomRecordsPage")
	public String visitRoomRecords(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "visitRoomRecords";
	}

	// 应急管理-演练方案
	@RequestMapping("/page/exerciseSchemePage")
	public String exerciseSchemePage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "exerciseSchemePage";
	}

	// 应急管理-演练/抢险完成情况
	@RequestMapping("/page/skitsRescueCompletion")
	public String skitsRescueCompletion(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "skitsRescueCompletion";
	}

	// 跨局通信网申请
	@RequestMapping("/page/communicationNetworkApply")
	public String communicationNetworkApply(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "communicationNetworkApply";
	}

	// 跨局通信网技术科审核
	@RequestMapping("/page/communicationNetworkSkillAudit")
	public String communicationNetworkSkillAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "communicationNetworkSkillAudit";
	}

	// 跨局通信网安全科审核
	@RequestMapping("/page/communicationNetworkSafeAudit")
	public String communicationNetworkSafeAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "communicationNetworkSafeAudit";
	}

	// 跨局通信网调度科审核
	@RequestMapping("/page/communicationNetworkDispatchAudit")
	public String communicationNetworkDispatchAudit(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "communicationNetworkDispatchAudit";
	}

	// 跨局通信网段领导审批
	@RequestMapping("/page/communicationNetworkApprove")
	public String communicationNetworkApprove(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "communicationNetworkApprove";
	}

	// 跨局通信网查询
	@RequestMapping("/page/communicationNetworkApplyQuery")
	public String communicationNetworkApplyQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "communicationNetworkApplyQuery";
	}

	// 技术管理-通信履历/传输与接入网系统
	@RequestMapping("/page/transmissionNetworkSystemPage")
	public String transmissionNetworkSystemPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "transmissionNetworkSystem";
	}

	// 技术管理-通信履历/电报及电话系统
	@RequestMapping("/page/telegraphAndTelephoneSystem")
	public String telegraphAndTelephonePage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "telegraphAndTelephoneSystem";
	}

	// 技术管理-通信履历/视频监控类
	@RequestMapping("/page/videoMonitorSystem")
	public String videoMonitorSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "videoMonitorSystem";
	}

	// 技术管理-通信履历/无线类
	@RequestMapping("/page/wirelessSystem")
	public String wirelessSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "wirelessSystem";
	}

	// 技术管理-通信履历/有线类
	@RequestMapping("/page/wiredSystem")
	public String wiredSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "wiredSystem";
	}

	// 技术管理-通信履历/会议系统类
	@RequestMapping("/page/conferenceSystem")
	public String conferenceSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "conferenceSystem";
	}

	// 技术管理-通信履历/数据通信系统
	@RequestMapping("/page/dataCommunicationSystem")
	public String dataCommunicationSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "dataCommunicationSystem";
	}

	// 技术管理-通信履历/调度通信系统
	@RequestMapping("/page/dispatchCommunicationSystem")
	public String dispatchCommunicationSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "dispatchCommunicationSystem";
	}

	// 技术管理-通信履历/通信线路系统
	@RequestMapping("/page/communicationLineSystem")
	public String communicationLineSystem(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "communicationLineSystem";
	}

	/*************************************** 普铁年月报表 ***************************************/
	/**
	 * 普铁年月报表 http://localhost:7001/kmms/yearMonthPlanYunwei/entryPagePuTie
	 * http://localhost:7001/kmms/yearMonthPlanYunwei/workAreaPuTieYearMonth
	 * http://localhost:7001/kmms/yearMonthPlanYunwei/workShopPuTieYearMonth
	 * http://localhost:7001/kmms/yearMonthPlanYunwei/segmentPuTieYearMonth
	 * http://localhost:7001/kmms/yearMonthPlanYunwei/workAreaPuTieYearMonthExecute
	 * http://localhost:7001/kmms/yearMonthPlanYunwei/workShopPuTieYearMonthExecute
	 * http://localhost:7001/kmms/yearMonthPlanYunwei/segmentPuTieYearMonthExecute
	 */

	/**
	 * entryPagePuTie 普铁年月报表入口界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:29:07
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/entryPagePuTie", method = RequestMethod.GET)
	public String entryPagePuTie(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("orgType", user.getOrganization().getType());
		return "yearMonthPlan/yearMonthPuTie/entry/entryPagePuTie";
	}

	/**
	 * workAreaPuTieYearMonth 普铁年月报表-工区编制界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:54:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/workAreaPuTieYearMonth", method = RequestMethod.GET)
	public String workAreaPuTieYearMonth(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("user", user);
		request.setAttribute("filePath", YearMonthPlanFilePathUtils.getPuTie());
		return "yearMonthPlan/yearMonthPuTie/compile/workAreaPuTieYearMonth";
	}

	/**
	 * workShopPuTieYearMonth 普铁年月报表-车间编制界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:54:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/workShopPuTieYearMonth", method = RequestMethod.GET)
	public String workShopPuTieYearMonth(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("user", user);
		request.setAttribute("filePath", YearMonthPlanFilePathUtils.getPuTie());
		return "yearMonthPlan/yearMonthPuTie/compile/workShopPuTieYearMonth";
	}

	/**
	 * segmentPuTieYearMonth 普铁年月报表-段科室编制界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:54:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/segmentPuTieYearMonth", method = RequestMethod.GET)
	public String segmentPuTieYearMonth(HttpServletRequest request) {
		request.setAttribute("filePath", YearMonthPlanFilePathUtils.getPuTie());
		request.setAttribute("tplPath", YearMonthPlanFilePathUtils.getPuTieTpl());
		return "yearMonthPlan/yearMonthPuTie/compile/segmentPuTieYearMonth";
	}

	/**
	 * workAreaPuTieYearMonthExecute 普铁年月报表-工区执行界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:54:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/workAreaPuTieYearMonthExecute", method = RequestMethod.GET)
	public String workAreaPuTieYearMonthExecute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("user", user);
		request.setAttribute("filePath", YearMonthPlanFilePathUtils.getPuTie());
		return "yearMonthPlan/yearMonthPuTie/execute/workAreaPuTieYearMonthExecute";
	}

	/**
	 * workShopPuTieYearMonthExecute 普铁年月报表-车间执行界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:54:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/workShopPuTieYearMonthExecute", method = RequestMethod.GET)
	public String workShopPuTieYearMonthExecute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("user", user);
		request.setAttribute("filePath", YearMonthPlanFilePathUtils.getPuTie());
		return "yearMonthPlan/yearMonthPuTie/execute/workShopPuTieYearMonthExecute";
	}

	/**
	 * segmentPuTieYearMonthExecute 普铁年月报表-段科室执行界面
	 *
	 * @author lidt
	 * @date 2018年11月30日 下午4:54:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page/segmentPuTieYearMonthExecute", method = RequestMethod.GET)
	public String segmentPuTieYearMonthExecute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("user", user);
		request.setAttribute("filePath", YearMonthPlanFilePathUtils.getPuTie());
		request.setAttribute("tplPath", YearMonthPlanFilePathUtils.getPuTieTpl());
		return "yearMonthPlan/yearMonthPuTie/execute/segmentPuTieYearMonthExecute";
	}
	
	// 故障查询主页
	@RequestMapping("/page/troubleQuery")
	public String troubleQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "troubleQueryPage";
	}

	// 障碍查询主页
	@RequestMapping("/page/obstacleQuery")
	public String obstacleQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "obstacleQueryPage";
	}

	// 事故查询主页
	@RequestMapping("/page/accidentQuery")
	public String accidentQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "accidentQueryPage";
	}

	// 隐患查询主页
	@RequestMapping("/page/securityQuery")
	public String securityQuery(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "securityQueryPage";
	}
	
	//点外作业情况主页
	@RequestMapping("/page/outsideWorkCondition")
	public String outsideWorkCondition(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "outsideWorkCondition";
	}
	
	@RequestMapping("/page/systemDevicePage")
	public String systemDevicePage(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");//登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);//登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));//登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "systemAndDevice";
	}

	//线别名称管理
	@RequestMapping("/page/lineNameMangementPage")
	public String lineNameMangementPage(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");//登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);//登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));//登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "lineNameMangement";
	}
	//机房信息库管理
	@RequestMapping("/page/machineRoomManagePage")
	public String  machineRoomManagePage(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");//登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);//登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));//登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "machineRoomManage";
	}
	//安全生产天数基准值设置
	@RequestMapping("/page/productionDaySet")
	public String productionDaySet(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("parentId", org.get("PARENT_ID_"));// 登陆用户的上级机构ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "productionDaySet";
	}

}