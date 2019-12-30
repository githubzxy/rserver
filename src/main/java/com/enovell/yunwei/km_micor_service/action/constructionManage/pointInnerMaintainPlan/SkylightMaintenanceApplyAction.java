package com.enovell.yunwei.km_micor_service.action.constructionManage.pointInnerMaintainPlan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.SkylightMaintenanceApplyDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan.SkylightMaintenanceApplyService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/skylightMaintenanceApplyAction")
public class SkylightMaintenanceApplyAction {
	@Resource(name = "skylightMaintenanceApplyService")
    private SkylightMaintenanceApplyService service;
    @Resource(name = "userService")
    private UserService userService;
    
    /**
     * getSystemDate 获取当前系统时间
     * @return String
     */
    @PostMapping("/getSystemDate")
    public String getSystemDate() {
        return JsonUtil.getSystemDate();
    }
 /**
  * 新增数据
  * @param flowState 审核状态
  * @param orgId 组织ID
  * @param orgName 组织名
  * @param collectionName 表名
  * @param uploadFileArr 附件
  * @param request
  * @return
  */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(
    						@RequestParam(name = "project") String project,
    						@RequestParam(name = "type") String type,
    						@RequestParam(name = "orgSelectId") String orgSelectId,
    						@RequestParam(name = "orgSelectName") String orgSelectName,
                            @RequestParam(name = "flowState") String flowState,
                            @RequestParam(name = "uploadFileArr") String uploadFileArr,
                            @RequestParam(name = "orgId") String orgId,
                            @RequestParam(name = "orgName") String orgName,
                            @RequestParam(name = "userId") String userId,
                            @RequestParam(name = "parentId") String parentId,
                            @RequestParam(name = "collectionName") String collectionName,
                            HttpServletRequest request) {
        Document document = new Document();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   	    String createDateStr = "";
        try {
        	createDateStr = sdf.format(new Date());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        document.put("createDateStr", createDateStr);
        document.put("createDate", new Date());
        document.put("project", project);
        document.put("type", type);
        document.put("orgSelectId", orgSelectId);
        document.put("orgSelectName", orgSelectName);
        document.put("flowState", flowState);
        document.put("orgId", orgId);
        document.put("orgName", orgName);
        document.put("createUserId", userId);
        Map<String, Object> user = userService.getUserById(userId);
        document.put("createUserName", user.get("USER_NAME_"));
        document.put("parentId", parentId);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
//        document.put("summaryDate", "");//汇总日期
//        document.put("summaryPersonId", "");//汇总人ID
//        document.put("summaryPersonName", "");//汇总人名称
        if("1".equals(flowState)){
        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        	 String currentDay = "";
             try {
                 currentDay = sdfDay.format(new Date());
             } catch (Exception e) {
             	e.printStackTrace();
             }
        	document.put("applyDate", currentDay);
        }else{
        	document.put("applyDate", "");
        }
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }
    /**
     * 删除数据
     *
     * @param id             数据id
     * @param collectionName 表名
     * @return 状态返回
     */
    @PostMapping("/removeDoc")
    public ResultMsg removeDoc(@RequestParam("id") String id,
                               @RequestParam("collectionName") String collectionName) {
        List<String> ids = Arrays.asList(id.split(","));
        List<String> flowList = new ArrayList<String>();
        ids.forEach(s->{
        	 Document res = service.findDocumentById(s, collectionName);
        	 flowList.add((String) res.get("flowState"));
        });
      //判断选择的记录是否全为草稿状态
   	 boolean flowReat =flowList.stream().allMatch(f->f.equals("0")==true);
   	 System.out.println(flowReat);
     if(flowReat==true){
    	 service.removeDocument(ids, collectionName);
    	 return ResultMsg.getSuccess("删除成功");
     }else{
    	 return ResultMsg.getFailure("请选择草稿状态的记录删除");
     }
       
//        return ResultMsg.getSuccess("删除成功");
    }
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
     * 主页显示
     * @param collectionName
     * @param name
     * @param startUploadDate
     * @param endUploadDate
     * @param auditStatus
     * @param orgId
     * @param start
     * @param limit
     * @return
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
        result.setResults(service.findAllDocumentCount(collectionName, userId, orgId, currentDay, project,type,startUploadDate, endUploadDate,flowState));
        result.setRows(service.findAllDocument(collectionName, userId, orgId, currentDay,project,type,startUploadDate, endUploadDate,flowState,start, limit));
        return result;
    }
    
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "project") String project,
    		@RequestParam(name = "type") String type,
			@RequestParam(name = "orgSelectName") String orgSelectName,
			@RequestParam(name = "orgSelectId") String orgSelectId,
            @RequestParam(name = "uploadFileArr") String uploadFileArr,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "collectionName") String collectionName,
            String skillAuditAdvice,
            String safeAuditAdvice,
            String dispatchAuditAdvice,
            String approveAdvice,
            HttpServletRequest request) {
        Document document = service.findDocumentById(id,collectionName);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
        document.put("project", project);
        document.put("type", type);
        document.put("orgSelectName", orgSelectName);
        document.put("orgSelectId", orgSelectId);
        document.put("flowState", flowState);
        document.put("skillAuditDate", "");
    	document.put("skillAuditor", "");
    	document.put("skillAuditorId", "");
    	document.put("skillAuditorOrg", "");
    	document.put("skillAuditorOrgId", "");
    	
        document.put("safeAuditDate", "");
    	document.put("safeAuditor", "");
    	document.put("safeAuditorId", "");
    	document.put("safeAuditorOrg", "");
    	document.put("safeAuditorOrgId", "");
    	
    	document.put("dispatchAuditDate", "");
    	document.put("dispatchAuditor", "");
    	document.put("dispatchAuditorId", "");
    	document.put("dispatchAuditorOrg", "");
    	document.put("dispatchAuditorOrgId", "");
    	
    	document.put("approveDate", "");
    	document.put("approver", "");
    	document.put("approverId", "");
    	document.put("approverOrg", "");
    	document.put("approverOrgId", "");
    	
    	document.put("skillAuditAdvice", "");
    	document.put("safeAuditAdvice", "");
    	document.put("dispatchAuditAdvice", "");
    	document.put("approveAdvice", "");
//    	 if(StringUtils.isNotBlank(skillAuditAdvice)){
//         	document.put("skillAuditAdvice", "");
//         }
//        if(StringUtils.isNotBlank(safeAuditAdvice)){
//        	document.put("safeAuditAdvice", "");
//        }
//        if(StringUtils.isNotBlank(dispatchAuditAdvice)){
//        	document.put("dispatchAuditAdvice", "");
//        }
//        if(StringUtils.isNotBlank(approveAdvice)){
//        	document.put("approveAdvice", "");
//        }
        if("1".equals(flowState)){
        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
       	 String currentDay = "";
            try {
                currentDay = sdfDay.format(new Date());
            } catch (Exception e) {
            	e.printStackTrace();
            }
            document.put("applyDate", currentDay);
        }
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
    @PostMapping("/updateExecuteDoc")
    public ResultMsg updateExecuteDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "situationRemark") String situationRemark,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		String auditAdvice,
    		String approveAdvice,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
    	document.put("situationRemark", situationRemark);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    
    @PostMapping("/exportApplyFrom")
	@ResponseBody
	public ResultMsg txdWriteBusiWord(@RequestBody SkylightMaintenanceApplyDto dto,HttpServletRequest request) {
		return ResultMsg.getSuccess(service.exportApplyFrom(dto));
	}
    
}