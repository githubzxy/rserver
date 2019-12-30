package com.enovell.yunwei.km_micor_service.action.productionManage.taskManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.taskManage.TaskWorkAreaReplyService;

@RestController
@RequestMapping("/taskWorkAreaReplyAction")
public class TaskWorkAreaReplyAction {
	@Resource(name = "taskAreaReplyService")
    private TaskWorkAreaReplyService service;
    @Resource(name = "userService")
    private UserService userService;
    
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
						    		 String docId,
						    		 String executiveOrgId,
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
        result.setResults(service.findAllDocumentCount(collectionName,docId,executiveOrgId, orgId, currentDay, taskName, systemType, flowState, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName,docId,executiveOrgId, orgId, currentDay, taskName, systemType, flowState, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    @PostMapping("/findDistributeReplyAll")
    public GridDto<Document> findDistributeReplyAll(@RequestParam String collectionName,
					    		String docId,
					    		String distributeOrgId,
					    		@RequestParam int start, @RequestParam int limit) {
    	GridDto<Document> result = new GridDto<>();
    	result.setResults(service.findDistributeReplyAllDocumentCount(collectionName, docId, distributeOrgId));
    	result.setRows(service.findDistributeReplyAllDocument(collectionName, docId, distributeOrgId, start, limit));
    	return result;
    }
    

    @PostMapping("/replyDoc")
    public ResultMsg replyDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
//    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "taskWorkAreaReply") String taskWorkAreaReply,
    		@RequestParam(name = "IssueTaskOfWorkArea") String IssueTaskOfWorkArea,
    		@RequestParam(name = "replyUserId") String replyUserId,
    		@RequestParam(name = "replyUserName") String replyUserName,
    		@RequestParam(name = "distributeOrgId") String distributeOrgId,
    		@RequestParam(name = "distributeOrgName") String distributeOrgName,
    		@RequestParam(name = "workAreaReply") String workAreaReply,
    		HttpServletRequest request) {
    	
    	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date currentDate = new Date();
    	String currentStr = "";
    	currentStr = sdfDay.format(currentDate);
    	
    	
		/*
		 * Document document =
		 * service.findDocumentById(id,distributeOrgId,collectionName);
		 * 
		 * Document document =
		 * service.findDocumentById(id,distributeOrgId,circuitDistributeWorkOrder);
		 */
    	Document document = service.findDocumentById(id,distributeOrgId,IssueTaskOfWorkArea);
    	Document newDocument = new Document();
    	
    	newDocument.put("docId", id);
    	newDocument.put("replyUserId", replyUserId);
    	newDocument.put("replyUserName", replyUserName);
    	newDocument.put("distributeOrgId", distributeOrgId);
    	newDocument.put("distributeOrgName", distributeOrgName);
    	newDocument.put("workAreaReply", workAreaReply);
    	newDocument.put("replyTime", currentStr);
    	document.put("flowState", flowState);
    	service.addReplyDocument(newDocument, taskWorkAreaReply, document, IssueTaskOfWorkArea);
//    	service.setFlowState(res.getString("docId"),res.getString("executiveOrgId"),collectionName);
    	return ResultMsg.getSuccess("操作成功");
    }
    @PostMapping("/finishDoc")
    public ResultMsg finishDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "distributeOrgId") String distributeOrgId,
    		HttpServletRequest request) {
    	
    	Document document = service.findDocumentById(id,distributeOrgId,collectionName);
    	
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName, flowState);
    	service.setFlowState(res.getString("docId"),res.getString("executiveOrgId"),collectionName);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    
}