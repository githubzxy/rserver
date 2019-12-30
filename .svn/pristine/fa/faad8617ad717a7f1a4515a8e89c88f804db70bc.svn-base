package com.enovell.yunwei.km_micor_service.action.constructionManage.pointInnerMaintainPlan;

import java.text.ParseException;
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
import com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan.SkylightMaintenanceDispatchAuditService;

@RestController
@RequestMapping("/skylightMaintenanceDispatchAuditAction")
public class SkylightMaintenanceDispatchAuditAction {
	
	@Resource(name = "skylightMaintenanceDispatchAuditService")
    private SkylightMaintenanceDispatchAuditService service;
    @Resource(name = "userService")
    private UserService userService;
    
    /**
     * 查询一条记录
     * @param id
     * @param collectionName
     * @return ResultMsg
     */
    @PostMapping("/findById")
    public ResultMsg findDocById(@RequestParam("id") String id,
                                 @RequestParam("collectionName") String collectionName) {
        Document res = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", res);
    }
    /**
     * 点外维修管理审核主页查询
     * @param collectionName 表明
     * @param userId 登陆用户ID（车间用户）
     * @param orgId 登陆用户所属组织机构ID（车间用户）
     * @param startUploadDate
     * @param endUploadDate
     * @param start 0
     * @param limit 20
     * @return GridDto
     */
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
									 @RequestParam String userId,
									 @RequestParam String orgId,
									 String project,
	                                 String type,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String flowState,
                                     String orgSelectName,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        Date startDate = null;
        Date endDate = null;
        GridDto<Document> result = new GridDto<>();
        try {
            if (StringUtils.isNotBlank(startUploadDate)) {
                startDate = sdf.parse(startUploadDate);
            }
            if (StringUtils.isNotBlank(endUploadDate)) {
                endDate = sdf.parse(endUploadDate);
            }
            currentDay = sdfDay.format(currentDate);
        } catch (ParseException e) {
           result.setHasError(true);
           result.setError("时间格式错误");
           return result;
        }
        result.setResults(service.findAllDocumentCount(collectionName, userId, orgId, currentDay, project,type,startUploadDate, endUploadDate,flowState,orgSelectName));
        result.setRows(service.findAllDocument(collectionName, userId, orgId, currentDay,project,type,startUploadDate, endUploadDate,flowState,orgSelectName,start, limit));
        return result;
    }
    
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "dispatchAuditResult") String dispatchAuditResult,
			@RequestParam(name = "dispatchAuditRemark") String dispatchAuditRemark,
			@RequestParam(name = "userId") String dispatchAuditorId,
			@RequestParam(name = "userName") String dispatchAuditor,
			@RequestParam(name = "orgId") String dispatchAuditorOrgId,
			@RequestParam(name = "orgName") String dispatchAuditorOrg,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "collectionName") String collectionName,
            HttpServletRequest request) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dispatchAuditDate = "";
    	 try {
    		 dispatchAuditDate = sdf.format(new Date());
         } catch (Exception e) {
        	 e.printStackTrace();
         }
    	
        Document document = service.findDocumentById(id,collectionName);
        
        document.put("dispatchAuditorId", dispatchAuditorId);
        document.put("dispatchAuditor", dispatchAuditor);
        document.put("dispatchAuditorOrgId", dispatchAuditorOrgId);
        document.put("dispatchAuditorOrg", dispatchAuditorOrg);
        document.put("dispatchAuditDate", dispatchAuditDate);
        if("1".equals(dispatchAuditResult)){
        	document.put("dispatchAuditAdvice", "调度科审核通过："+dispatchAuditRemark);
        	 if("4".equals(document.get("flowState"))) {
             	document.put("flowState", "5");
             }
             else {
             document.put("flowState", flowState);
             }
        }
        if("0".equals(dispatchAuditResult)){
        	document.put("dispatchAuditAdvice", "调度科审核不通过："+dispatchAuditRemark);
        	 document.put("flowState", flowState);
        }
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("调度科审核成功", res);
    }
    
}