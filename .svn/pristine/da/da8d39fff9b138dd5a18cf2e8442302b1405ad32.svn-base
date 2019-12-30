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
import com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage.CircuitWorkOrderCheckService;

@RestController
@RequestMapping("/circuitWorkOrderCheckAction")
public class CircuitWorkOrderCheckAction {
	@Resource(name = "circuitWorkOrderCheckService")
    private CircuitWorkOrderCheckService service;
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
        result.setResults(service.findAllDocumentCount(collectionName, userId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    /**
     * 审核信息更新
     * @param id
     * @param flowState
     * @param collectionName
     * @param checkUserId
     * @param checkUserName
     * @param checkAdvice
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/updateCheckDoc")
    public ResultMsg updateCheckDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		@RequestParam(name = "checkUserId") String checkUserId,
    		@RequestParam(name = "checkUserName") String checkUserName,
    		String checkAdvice,
    		HttpServletRequest request) {
    	
    	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentStr = "";
        currentStr = sdfDay.format(currentDate);
    	
    	Document document = service.findDocumentById(id,collectionName);
    	if("6".equals(flowState)){
    		if(StringUtils.isNotBlank(checkAdvice)){
    			document.put("checkAdvice", "审核通过："+checkAdvice);
    		}else{
    			document.put("checkAdvice", "审核通过");
    		}
        }
        if("5".equals(flowState)){
        	if(StringUtils.isNotBlank(checkAdvice)){
        		document.put("checkAdvice", "审核不通过："+checkAdvice);
        	}else{
    			document.put("checkAdvice", "审核不通过");
    		}
        }
    	
    	document.put("checkUserId", checkUserId);
    	document.put("checkUserName", checkUserName);
    	document.put("checkTime", currentStr);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName, flowState);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    
}