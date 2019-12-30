package com.enovell.yunwei.km_micor_service.action.productionManage.other;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.service.productionManage.other.TodoListService;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**    
* @Description: 查询待办事项    
* @date 2019年3月4日
* @author luoyan     
*/
@Controller
@RequestMapping("/todoList")
public class TodoListAction {

	@Resource(name="todoListService")
	private TodoListService todoListService;
	
	/**
	 * @Description: 获取当前用户的待办事项模块（名称路径）和对应的条数 
	 * @param request
	 * @return GridDto<Map<String,Object>>
	 * @author luoyan
	 */
	@RequestMapping(value="getTodoList")
	@ResponseBody
	public Object getTodoList(String callback,String userId,String start,String limit) {
		List<Map<String,Object>> todoList = todoListService.getTodoList(userId);
		//进行分页
		Integer endInteger = Integer.valueOf(start)+Integer.valueOf(limit);
		if (endInteger>todoList.size()) {
			endInteger = todoList.size();
		}
		List<Map<String,Object>> pageList = todoList.subList(Integer.valueOf(start), endInteger);
		GridDto<Map<String, Object>> dto = new GridDto<Map<String, Object>>();
		dto.setRows(pageList);
		Long countLong = (long) todoList.size();
		dto.setResults(countLong);
		if(callback==null){
			return dto;
		}
		JSONPObject o = new JSONPObject(callback, dto);
		return o;
	}
	
}
