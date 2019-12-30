package com.enovell.yunwei.km_micor_service.action.securityManage.securityQuery;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.securityManage.securityQuery.DayNumService;

@RestController
@RequestMapping("/dayNumAction")
public class DayNumAction {

	@Resource(name = "dayNumService")
    private DayNumService service;
	
	@PostMapping("/findNum")
    public ResultMsg findNum(@RequestParam("collectionName") String collectionName
    							) {
		collectionName = "dayNum";
        Document result = service.findNum(collectionName);
        return ResultMsg.getSuccess("查询完成", result);
    }
	

	@PostMapping("/updateNum")
    public ResultMsg updateNum(
    		@RequestParam("dayNum") String dayNum,
    		String collectionName) {
		collectionName = "dayNum";
        Document result = service.updateNum(dayNum,collectionName);
        return ResultMsg.getSuccess("查询完成", result);
    }
	
}
