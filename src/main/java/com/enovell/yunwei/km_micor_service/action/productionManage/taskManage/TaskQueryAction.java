package com.enovell.yunwei.km_micor_service.action.productionManage.taskManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.taskManage.TaskQueryService;

@RestController
@RequestMapping("/taskQueryAction")
public class TaskQueryAction {
	@Resource(name = "taskQueryService")
    private TaskQueryService service;
    @Resource(name = "userService")
    private UserService userService;
    
    /**
     * 查询一条记录
     * @param id
     * @param collectionName
     * @return
     */
    @PostMapping("/findById")
    public ResultMsg findDocById(@RequestParam("id") String id,
                                 @RequestParam("collectionName") String collectionName) {
        Document res = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", res);
    }
    /**
     * 
     * @param collectionName
     * @param userId
     * @param workOrderName
     * @param flowState
     * @param startUploadDate
     * @param endUploadDate
     * @param start
     * @param limit
     * @return
     */
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
									 @RequestParam String userId,
									 String orgId,
									 String taskName,
									 String systemType,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);
        result.setResults(service.findAllDocumentCount(collectionName, userId, currentDay, taskName, systemType, flowState, startUploadDate, endUploadDate,orgId));
        result.setRows(service.findAllDocument(collectionName, userId, currentDay, taskName, systemType, flowState, startUploadDate, endUploadDate, start, limit,orgId));
        return result;
    }
    
}