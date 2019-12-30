package com.enovell.yunwei.km_micor_service.action.productionManage.circuitWorkOrderManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage.CircuitWorkOrderApproveService;

@RestController
@RequestMapping("/circuitWorkOrderApproveAction")
public class CircuitWorkOrderApproveAction {
	@Resource(name = "circuitWorkOrderApproveService")
    private CircuitWorkOrderApproveService service;
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
									 String workOrderName,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);
        result.setResults(service.findAllDocumentCount(collectionName, userId, currentDay, workOrderName, flowState, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, currentDay, workOrderName, flowState, startUploadDate, endUploadDate, start, limit));
        return result;
    }
   /**
    * 审批
    * @param id
    * @param flowState
    * @param collectionName
    * @param approveUserId
    * @param approveUserName
    * @param approveAdvice
    * @param request
    * @return ResultMsg
    */
    @PostMapping("/updateApproveDoc")
    public ResultMsg updateApproveDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "approveUserId") String approveUserId,
    		@RequestParam(name = "approveUserName") String approveUserName,
    		String approveAdvice,
    		HttpServletRequest request) {
    	
    	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentStr = "";
        currentStr = sdfDay.format(currentDate);
    	
    	Document document = service.findDocumentById(id,collectionName);
    	if("7".equals(flowState)){
    		if(StringUtils.isNotBlank(approveAdvice)){
    			document.put("approveAdvice", "审批通过："+approveAdvice);
    		}else{
    			document.put("approveAdvice", "审批通过");
    		}
        }
        if("6".equals(flowState)){
        	if(StringUtils.isNotBlank(approveAdvice)){
        		document.put("approveAdvice", "审批不通过："+approveAdvice);
        	}else{
    			document.put("approveAdvice", "审批不通过");
    		}
        }
    	
    	document.put("approveUserId", approveUserId);
    	document.put("approveUserName", approveUserName);
    	document.put("approveTime", currentStr);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName, flowState);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    
}