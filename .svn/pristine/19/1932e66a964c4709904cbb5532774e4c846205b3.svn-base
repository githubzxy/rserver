package com.enovell.yunwei.km_micor_service.action.integratedManage.fileShare;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.integratedManage.fileShare.FileShareService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

/**文件共享action
 * @author roysong
 * @date 190516
 */
@Controller
@RequestMapping("/fileShareAction")
public class FileShareAction {

	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "FileShareService")
	private FileShareService fileShareService;
	
	/**文件共享入口页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/in")
	public String in(HttpServletRequest request){
		String threePerId = request.getParameter("threePerId");
        String userId = request.getParameter("userId");//登陆用户ID
        Map<String, Object> user = userService.getUserById(userId);
        Map<String, Object> org = userService.getOrgbyUserId(userId);
    	request.setAttribute("orgId", org.get("ORG_ID_"));//登陆用户组织机构ID
    	request.setAttribute("orgName", org.get("ORG_NAME_"));//登陆用户组织机构名称
        request.setAttribute("perId", threePerId);
        request.setAttribute("userId", userId);//登陆用户ID
        request.setAttribute("userName", user.get("USER_NAME_"));//登陆用户名称
		return "fileShare";
	}
	/**分页查询文件及目录数据
	 * @param start
	 * @param limit
	 * @param collectionName
	 * @param parentId 为空字符串时代表根目录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAll",method = RequestMethod.POST)
	public GridDto<Document> findAll(@RequestParam int start,@RequestParam int limit,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam String collectionName,@RequestParam String parentId){
		GridDto<Document> results = new GridDto<Document>();
		try {
			results = fileShareService.findAll(start,limit,collectionName,parentId,name);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	@ResponseBody
	@RequestMapping(value = "/getById",method = RequestMethod.POST)
	public Document getById(@RequestParam String id,@RequestParam String collectionName) {
		try {
			Document result = fileShareService.getById(id,collectionName);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/deleteById",method = RequestMethod.POST)
	public ResultMsg deleteById(@RequestParam String id,@RequestParam String collectionName) {
		try {
			fileShareService.deleteById(id,collectionName);
			return ResultMsg.getSuccess();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("出现网络问题，删除失败，请联系管理员");
		}
	}
	/**添加新目录
	 * @param data json字符串，具体字段参见addDic.js
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addDic",method = RequestMethod.POST)
	public ResultMsg addDic(@RequestParam String data) {
		try {
			Document dic = JsonUtil.jsonToJavaObj(data, Document.class);
			String collectionName = dic.get("collectionName").toString();
			dic.remove("collectionName");
			dic.put("createDate", new Date());
			fileShareService.add(dic,collectionName);
			return ResultMsg.getSuccess();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("出现网络问题，新建目录失败，请联系管理员");
		}
	}
	
	/**上传新文件
	 * @param data json字符串，具体字段参见addFile.js
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	public ResultMsg uploadFile(@RequestParam String data) {
		try {
			Document[] files = JsonUtil.jsonToJavaObj(data, Document[].class);
			for(Document file : files) {
				String collectionName = file.get("collectionName").toString();
				file.remove("collectionName");
				file.put("createDate", new Date());
				fileShareService.add(file,collectionName);
			}
			return ResultMsg.getSuccess();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("出现网络问题，上传文件失败，请联系管理员");
		}
	}
	
	/**修改目录
	 * @param data json字符串，具体字段参见updateDic.js
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDic",method = RequestMethod.POST)
	public ResultMsg updateDic(@RequestParam String data) {
		try {
			Document dic = JsonUtil.jsonToJavaObj(data, Document.class);
			String collectionName = dic.get("collectionName").toString();
			dic.remove("collectionName");
			dic.put("createDate", new Date());
			fileShareService.update(dic,collectionName);
			return ResultMsg.getSuccess();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMsg.getFailure("出现网络问题，修改目录失败，请联系管理员");
		}
	}
}
