package com.enovell.yunwei.km_micor_service.action.productionManage.circuitWorkOrderManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage.CircuitWorkOrderWorkshopReplyService;

@RestController
@RequestMapping("/circuitWorkOrderWorkshopReplyAction")
public class CircuitWorkOrderWorkshopReplyAction {
	@Resource(name = "circuitWorkOrderWorkshopReplyService")
    private CircuitWorkOrderWorkshopReplyService service;
    @Resource(name = "userService")
    private UserService userService;
    
    @PostMapping("/getDepart")
	 public List<Map<String, Object>> getDepart(String workShopName){
    	return service.getDeparts(workShopName);
	 }
    
    /**
     * 查询一条记录
     * @param id
     * @param collectionName
     * @return
     */
    @PostMapping("/findById")
    public ResultMsg findDocById(@RequestParam("id") String id,
    							 @RequestParam("executiveOrgId") String executiveOrgId,
                                 @RequestParam("collectionName") String collectionName) {
        Document res = service.findDocumentById(id, executiveOrgId, collectionName);
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
									 String orgId,
									 String docId,
									 String workOrderName,
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
        result.setResults(service.findAllDocumentCount(collectionName, docId, orgId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, docId, orgId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    @PostMapping("/findIssueReplyAll")
    public GridDto<Document> findIssueReplyAll(@RequestParam String collectionName,
					    		String docId,
					    		String executiveOrgId,
					    		@RequestParam int start, @RequestParam int limit) {
    	GridDto<Document> result = new GridDto<>();
    	result.setResults(service.findIssueReplyAllDocumentCount(collectionName, docId, executiveOrgId));
    	result.setRows(service.findIssueReplyAllDocument(collectionName, docId, executiveOrgId, start, limit));
    	return result;
    }
    
    @PostMapping("/replyDoc")
    public ResultMsg replyDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
//    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "circuitIssueWorkOrderReply") String circuitIssueWorkOrderReply,
    		@RequestParam(name = "circuitIssueWorkOrder") String circuitIssueWorkOrder,
    		@RequestParam(name = "replyUserId") String replyUserId,
    		@RequestParam(name = "replyUserName") String replyUserName,
    		@RequestParam(name = "executiveOrgId") String executiveOrgId,
    		@RequestParam(name = "executiveOrgName") String executiveOrgName,
    		@RequestParam(name = "workshopReply") String workshopReply,
    		HttpServletRequest request) {
    	
    	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date currentDate = new Date();
    	String currentStr = "";
    	currentStr = sdfDay.format(currentDate);
    	
    	Document document = service.findDocumentById(id,executiveOrgId,circuitIssueWorkOrder);
    	Document newDocument = new Document();
    	
    	newDocument.put("docId", id);
    	newDocument.put("executiveOrgId", executiveOrgId);
    	newDocument.put("executiveOrgName", executiveOrgName);
    	newDocument.put("replyUserId", replyUserId);
    	newDocument.put("replyUserName", replyUserName);
    	newDocument.put("workshopReply", workshopReply);
    	newDocument.put("replyTime", currentStr);
    	document.put("flowState", flowState);
    	service.addReplyDocument(newDocument, circuitIssueWorkOrderReply, document, circuitIssueWorkOrder);
//    	service.setFlowState(res.getString("docId"),collectionName);
    	return ResultMsg.getSuccess("操作成功");
    }
    @PostMapping("/finishDoc")
    public ResultMsg finishDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "executiveOrgId") String executiveOrgId,
    		HttpServletRequest request) {
    	
    	Document document = service.findDocumentById(id,executiveOrgId,collectionName);
    	
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName, flowState);
    	service.setFlowState(res.getString("docId"),collectionName);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    @PostMapping("/distributeDoc")
    public ResultMsg distributeDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "distributeWorkAreaName") String distributeWorkAreaName,
    		@RequestParam(name = "distributeWorkAreaId") String distributeWorkAreaId,
    		@RequestParam(name = "distributeRemark") String distributeRemark,
    		@RequestParam(name = "executiveUserName") String executiveUserName,
    		@RequestParam(name = "executiveOrgId") String executiveOrgId,
    		HttpServletRequest request) {
    	
    	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date currentDate = new Date();
    	String currentStr = "";
    	currentStr = sdfDay.format(currentDate);
    	
    	Document document = service.findDocumentById(id,executiveOrgId,collectionName);
    	
    	document.put("distributeWorkAreaName", distributeWorkAreaName);
    	document.put("distributeWorkAreaId", distributeWorkAreaId);
    	document.put("distributeRemark", distributeRemark);
    	document.put("distributeTime", currentStr);
    	document.put("replyUserName", executiveUserName);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName, flowState);
    	return ResultMsg.getSuccess("派发成功", res);
    }
    
}