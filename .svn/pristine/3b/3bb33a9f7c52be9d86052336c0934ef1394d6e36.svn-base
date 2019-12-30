package com.enovell.yunwei.km_micor_service.action.productionManage.other.cfg;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**    
* @Description: 待办事项配置文件  
* @date 2019年3月4日
* @author luoyan     
*/
@Component
@ConfigurationProperties(prefix = "todoCfg")
public class TodoListConfig {

	private Map<String, Map<String, String>> todoList = new HashMap<>();

	public Map<String, Map<String, String>> getTodoList() {
		return todoList;
	}

	public void setTodoList(Map<String, Map<String, String>> todoList) {
		this.todoList = todoList;
	}
	
	
}
