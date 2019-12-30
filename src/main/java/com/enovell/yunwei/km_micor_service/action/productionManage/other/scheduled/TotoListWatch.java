package com.enovell.yunwei.km_micor_service.action.productionManage.other.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enovell.yunwei.km_micor_service.action.productionManage.other.socket.TodoListSocket;

@Component
public class TotoListWatch{
	
	@Autowired
    private TodoListSocket todoListSocket;
    /**
     * 每5秒推送一次待办任务数
     */
    @Scheduled(cron = "0/5 * * * * ? ")
	public void execute(){
    	todoListSocket.sendMassage();
	}

}
