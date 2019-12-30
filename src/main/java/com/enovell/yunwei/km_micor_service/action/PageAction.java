package com.enovell.yunwei.km_micor_service.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enovell.yunwei.km_micor_service.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * kunmingTXD
 * 页面返回Aciton
 * @author bili
 * @date 18-11-21
 * 
 */
@Controller
public class PageAction {
	
	@Resource(name = "userService")
	private UserService userService;
	
	/**
	 * 日交班信息页面
	 * 
	 * @return page
	 */
	@RequestMapping("/page/dailyShiftForDuan")
	public String dailyShiftDuan(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "dailyShiftForDuan";
	}

	@RequestMapping("/page/dailyShiftForCj")
	public String dailyShiftCj(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "dailyShiftForCj";
	}

	@RequestMapping("/page/floodGuardPoint")
	public String floodGuardPoint(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "floodGuadPointManagement";
	}

	// 通用台账-施工配合协议-入口模块
	@RequestMapping("/page/workAssortProtocol")
	public String workAssortProtocol(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "workAssortProtocol";
	}

	// 综合管理-通知发布-入口模块
	@RequestMapping("/page/announcement")
	public String announcement(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "announcement";
	}

	// 综合管理-考勤管理-入口模块
	@RequestMapping("/page/attendance")
	public String attendance(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "attendance";
	}

	// 综合管理-人员信息管理-入口模块
	@RequestMapping("/page/personalInformation")
	public String personalInformation(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "personalInformation";
	}

	/*************************** 安全风险管理模块 *********************************/

	/**
	 * 安全风险数据库页面
	 */
	@RequestMapping("/page/securityRiskDatabase")
	public String securityRiskDatabase(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "securityRiskDatabase";
	}

	/**
	 * 安全风险项点页面
	 */
	@RequestMapping("/page/securityRiskPoint")
	public String securityRiskPoint(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "securityRiskPoint";
	}

	/*************************** 安全分析模块 *********************************/

	/**
	 * 安全分析页面
	 */
	@RequestMapping("/page/safetyAnalysis")
	public String safetyAnalysis(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		request.setAttribute("orgType", org.get("TYPE_"));// 登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
		return "safetyAnalysis";
	}

	/*************************** 施工计划管理模块 *********************************/

	/**
	 * 施工计划页面
	 */
	@RequestMapping("/page/workPlan")
	public String workPlan(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "workPlan";
	}

	/*************************** 点内维修计划管理模块 *********************************/

	/**
	 * 点内维修计划计划页面
	 */
	@RequestMapping("/page/pointInnerMaintainPlan")
	public String pointInnerMaintainPlan(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "pointInnerMaintainPlan";
	}

	// 施工管理-点外维修计划管理-点外维修计划-入口模块
	@RequestMapping("/page/pointOuterMaintainPlan")
	public String pointOuterMaintainPlan(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "pointOuterMaintainPlan";
	}

	// 应急管理-应急通信设备台账-检修记录
	@RequestMapping("/page/maintenanceRecord")
	public String maintenanceRecord(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "maintenanceRecord";
	}

	// 事故故障障碍管理
	@RequestMapping("/page/fault")
	public String fault(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));
		request.setAttribute("orgName", org.get("ORG_NAME_"));
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);
		return "faultManagement";
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

	// 二级维修申请
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

	// 二级维修审批
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

	// 二级维修查询
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

	// 施工管理-施工计划管理-施工计划申请
	@RequestMapping("/constructionPlan/constructionApply")
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

	// 方案维修申请
	@RequestMapping("/pointInnerMaintainPlan/skylightMaintenanceApply")
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

	// 施工管理-施工计划管理-施工方案科室审核
	@RequestMapping("/constructionPlan/constructionAudit")
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

	// 施工管理-施工方案管理-施工计划段领导审批
	@RequestMapping("/constructionPlan/constructionApproved")
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

	// 施工管理-施工方案管理-施工计划查询
	@RequestMapping("/constructionPlan/constructionQuery")
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

	// 天窗维修技术科审核
	@RequestMapping("/pointInnerMaintainPlan/skylightMaintenanceSkillAudit")
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
	@RequestMapping("/pointInnerMaintainPlan/skylightMaintenanceSafeAudit")
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
	@RequestMapping("/pointInnerMaintainPlan/skylightMaintenanceDispatchAudit")
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
	@RequestMapping("/pointInnerMaintainPlan/skylightMaintenanceApprove")
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
	@RequestMapping("/pointInnerMaintainPlan/skylightMaintenanceApplyQuery")
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

	// 跨局通信网申请
	@RequestMapping("/pointInnerMaintainPlan/communicationNetworkApply")
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
	@RequestMapping("/pointInnerMaintainPlan/communicationNetworkSkillAudit")
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
	@RequestMapping("/pointInnerMaintainPlan/communicationNetworkSafeAudit")
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
	@RequestMapping("/pointInnerMaintainPlan/communicationNetworkDispatchAudit")
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
	@RequestMapping("/pointInnerMaintainPlan/communicationNetworkApprove")
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
	@RequestMapping("/pointInnerMaintainPlan/communicationNetworkApplyQuery")
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

	// 应急管理-防洪看守点管理
	@RequestMapping("/emergencyManage/floodGuardPointManage")
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

	// 生产管理-临时任务管理-临时任务审批（段领导）
	@RequestMapping("/page/taskApprove")
	public String taskApprove(HttpServletRequest request) {
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
		return "taskApprove";
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
	// 生产管理-临时任务管理-临时任务抄送列表

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

	// 技术管理-通信设备管理-设备履历台账
	@RequestMapping("/page/deviceRecordLedgerPage")
	public String deviceRecordLedgerPage(HttpServletRequest request) {
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");// 登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));// 登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));// 登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);// 登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));// 登陆用户名称
		return "deviceRecordLedger";
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

	// 综合管理-机房管理-检修记录
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

	// 生产管理-值班管理
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
	public String outsideWorkCondition(HttpServletRequest request){
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