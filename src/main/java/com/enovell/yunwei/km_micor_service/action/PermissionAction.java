package com.enovell.yunwei.km_micor_service.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.service.PermissionService;

@RestController
@RequestMapping(value="/permission")
public class PermissionAction {

	@Resource(name = "permissionService")
	private PermissionService permissionService;
	
	/**根据三级权限ID和当前用户获取按钮权限列表
	 * @param perId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getBtnPers", method = RequestMethod.POST)
	public List<Map<String, Object>> getBtnPers(@RequestParam String perId,@RequestParam String userId){
		List<Map<String, Object>> perList = permissionService.getBtnPers(perId,userId);
		return perList;
	}
}
