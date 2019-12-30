package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

@Controller
public class DayToJobManagePageAction {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping("/dayToJobManage/dayToJob")
	public String hello(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
        String systemBeforeDate = JsonUtil.getSystemBeforeDate();
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
        request.setAttribute("systemBeforeDate", String.valueOf(systemBeforeDate));//当前系统日期的前一天
		return "dayToJob";
	}




	//施工配合
	@RequestMapping("/dayToJobManage/constructCooperate")
	public String constructCooperate(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "constructCooperate";
	}

	
	//网管信息
	@RequestMapping("/dayToJobManage/networkManageInfo")
	public String networkManageInfo(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "networkManageInfo";
	}
	//其他生产信息
	@RequestMapping("/dayToJobManage/otherProductionInfo")
	public String otherProductionInfo(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");//登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);//登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "otherProductionInfo";
	}
	//遗留信息
	@RequestMapping("/dayToJobManage/remainInfo")
	public String remainInfo(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
		String userId = request.getParameter("userId");//登陆用户ID
		Map<String, Object> user = userService.getUserById(userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		request.setAttribute("perId", threePerId);
		request.setAttribute("userId", userId);//登陆用户ID
		request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "remainInfo";
	}
	//电视电话会议通知
	@RequestMapping("/dayToJobManage/videoPhoneNotice")
	public String videoPhoneNotice(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "videoPhoneNotice";
	}
	//施工维修天窗信息
	@RequestMapping("/dayToJobManage/constructRepair")
	public String constructRepair(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "constructRepair";
	}
	//上级安全问题通知书情况
	@RequestMapping("/dayToJobManage/superiorSafetyNotification")
	public String superiorSafetyNotification(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "superiorSafetyNotification";
	}
	//重点工作完成情况
	@RequestMapping("/dayToJobManage/completionOfKeyTasks")
	public String completionOfKeyTasks(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "completionOfKeyTasks";
	}
	//集团公司重点追查安全信息
	@RequestMapping("/dayToJobManage/companyTraceSafetyInfomation")
	public String companyTraceSafetyInfomation(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "companyTraceSafetyInfomation";
	}
	//段重点追查安全信息
	@RequestMapping("/dayToJobManage/duanTraceSafetyInfomation")
	public String duanTraceSafetyInfomation(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "duanTraceSafetyInfomation";
	}
	//段发安全问题通知书情况
	@RequestMapping("/dayToJobManage/duanSafetyNotification")
	public String duanSafetyNotification(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "duanSafetyNotification";
	}
		
		
	//点外维修申请
	@RequestMapping("/dayToJobManage/pointOuterMaintainApply")
	public String pointOuterMaintainApply(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
    	request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "pointOuterMaintainApply";
	}
	//点外维修申请
	@RequestMapping("/dayToJobManage/pointOuterMaintainAudit")
	public String pointOuterMaintainAudit(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
    	request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "pointOuterMaintainAudit";
	}
	//点外维修审批
		@RequestMapping("/dayToJobManage/pointOuterMaintainApprove")
		public String pointOuterMaintainApprove(HttpServletRequest request){
			String threePerId = request.getParameter("threePerId");
	        String userId = request.getParameter("userId");//登陆用户ID
	        Map<String, Object> user = userService.getUserById(userId);
	        Map<String, Object> org = userService.getOrgbyUserId(userId);
	    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
	    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
	    	request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
	        request.setAttribute("perId", threePerId);
	        request.setAttribute("userId", userId);//登陆用户ID
	        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
			return "pointOuterMaintainApprove";
		}
		//点外维修查询
				@RequestMapping("/dayToJobManage/pointOuterMaintainQuery")
				public String pointOuterMaintainQuery(HttpServletRequest request){
					String threePerId = request.getParameter("threePerId");
			        String userId = request.getParameter("userId");//登陆用户ID
			        Map<String, Object> user = userService.getUserById(userId);
			        Map<String, Object> org = userService.getOrgbyUserId(userId);
			    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
			    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
			    	request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
			        request.setAttribute("perId", threePerId);
			        request.setAttribute("userId", userId);//登陆用户ID
			        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
					return "pointOuterMaintainQuery";
				}
			//干部值班情况
			@RequestMapping("/dayToJobManage/cadreDuty")
			public String cadreDuty(HttpServletRequest request){
				String threePerId = request.getParameter("threePerId");
		        String userId = request.getParameter("userId");//登陆用户ID
		        Map<String, Object> user = userService.getUserById(userId);
		        Map<String, Object> org = userService.getOrgbyUserId(userId);
		    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		    	request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		        request.setAttribute("perId", threePerId);
		        request.setAttribute("userId", userId);//登陆用户ID
		        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
				return "cadreDuty";
			}
			
			//上级检查情况
			@RequestMapping("/dayToJobManage/inspectionSuperiors")
			public String inspectionSuperiors(HttpServletRequest request){
				String threePerId = request.getParameter("threePerId");
		        String userId = request.getParameter("userId");//登陆用户ID
		        Map<String, Object> user = userService.getUserById(userId);
		        Map<String, Object> org = userService.getOrgbyUserId(userId);
		    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
		    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
		    	request.setAttribute("parentId", org.get("PARENT_ID_"));//登陆用户的上级机构ID
		        request.setAttribute("perId", threePerId);
		        request.setAttribute("userId", userId);//登陆用户ID
		        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
				return "inspectionSuperiors";
			}
}