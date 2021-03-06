package com.enovell.yunwei.km_micor_service.service.productionManage.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.action.productionManage.other.cfg.TodoListConfig;
import com.enovell.yunwei.km_micor_service.service.UserService;

/**    
* @Description: 查询待办事项    
* @date 2019-6-20
* @author zhouxy 
*/

@Service("todoListService")
public class TodoListServiceImpl implements TodoListService {

	@Autowired
	private TodoListConfig todoListConfig;
	@Autowired
	private ApplicationContext applicationContext;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Override
	public Long getTodoCount(String userId) {
		try {
			Long count = 0L;
			for(Map<String, String> map : getMsgThreeModel(userId)){
				String beanName = map.get("service");
				TodoCountCommonService service = (TodoCountCommonService)applicationContext.getBean(beanName);
				count += service.getTodoCount(userId);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * 根据用户id查询当前用户需要消息待办提醒的三级菜单数据
	 * @param userId
	 * @return
	 */
	private List<Map<String, String>> getMsgThreeModel(String userId){
		List<Map<String, String>>  result = new ArrayList<>();
		//1.根据用户查询三级菜单权限数据
		
		List<String> perIds = userService.getPerDatasByUserId(userId);
		//2.获取当前用户需要消息待办提醒的三级菜单数据
		Map<String, Map<String, String>> map = todoListConfig.getTodoList();
		for(Entry<String, Map<String, String>> entry : map.entrySet()){
			entry.getValue().put("id", entry.getKey());
			if(perIds.contains(entry.getKey())) result.add(entry.getValue());
		}
		return result;
		
	}
	
	@Override
	public List<Map<String, Object>> getTodoList(String userId) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			for(Map<String, String> serviceMap : getMsgThreeModel(userId)){
				String beanName = serviceMap.get("service");
				TodoCountCommonService service = (TodoCountCommonService)applicationContext.getBean(beanName);
				Map<String, Object> taskMap = new HashMap<>();
				long count = service.getTodoCount(userId);
				if(count==0){
					continue; //待办为0的不显示
				}
				taskMap.put("count", count);
				taskMap.putAll(serviceMap);
				result.add(taskMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
