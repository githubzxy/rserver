package com.enovell.yunwei.km_micor_service.adInterFace;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enovell.system.common.domain.User;
import com.enovell.yunwei.km_micor_service.service.UserService;

/**
 * 
 * 项目名称：busi-changsha-andiao
 * 类名称：IndexEntryAction   
 * 类描述：综合网管跳转微服务 入口Action
 * 创建人：zsh
 * 创建时间：2018年7月17日 下午2:56:14
 * 修改人：zsh
 * 修改时间：2018年7月17日 下午2:56:14   
 *
 */
@Controller
public class IndexEntryAction {

	@Value("${projectName}")
	private String projectName;
	
	@Resource(name = "userService")
	private UserService userService;
	
	/**
	 * 
	 * session 入口方法
	 * 样例：
	 * http://localhost:7000/kmms/indexKmms?actionPath=pageKmms/page/attendance.cn&userId=8affa0735391f425015483d40be50074
	 * 
	 * 
	 * @param actionPath 业务模块action路径
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping("/indexKmms")
	public String session(
			@RequestParam String actionPath,
			@RequestParam String userId,
			@RequestParam String threePerId
			){
		return "redirect:" + projectName
				+ "setUserToSession?actionPath=" + actionPath
				+ "&userId=" + userId
				+ "&threePerId=" + threePerId;
	}
	
	/**
	 * 
	 * setUserToSession 根据userId查询User并存入Session中
	 * @param actionPath 业务模块action路径
	 * @param userId 用户id
	 * @param threePerId 三级菜单权限id
	 * @param request
	 * @return
	 */
	@RequestMapping("/setUserToSession")
	public String setUserToSession(
			@RequestParam String actionPath,
			@RequestParam String userId,
			@RequestParam String threePerId,
			HttpServletRequest request
			) {
		User user = userService.getUserObjectByUserId(userId);
		request.getSession().setAttribute("user", user);
		return "forward:/" + actionPath;
	}
}
