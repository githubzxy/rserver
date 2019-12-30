package com.enovell.yunwei.km_micor_service.action.securityManage.securityQuery;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.action.productionManage.other.socket.TodoListSocket;
import com.enovell.yunwei.km_micor_service.service.securityManage.securityQuery.DayNumService;

@Component
public class TotoAddDayNum{
	
	@Resource(name="dayNumService")
	private DayNumService service;
    /**
     * 每天0点定时执行任务
     *  @Scheduled(cron = "0 0 2 * * ?")　　//每天凌晨两点执行
     */
	@Scheduled(cron = "0 0 0 * * ?")
	public void execute(){
		String collectionName = "dayNum";
		Document reDocument = service.findNum(collectionName);
		String  string = (String) reDocument.get("dayNum");
		Integer integer = Integer.valueOf(string)+1;
		service.updateNum(integer.toString(), collectionName);
	}

    
}
