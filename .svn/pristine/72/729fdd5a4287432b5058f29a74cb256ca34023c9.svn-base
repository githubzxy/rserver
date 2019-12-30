package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

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
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.PointOuterMaintainAuditService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/pointOuterMaintainAuditAction")
public class PointOuterMaintainAuditAction {
	
	@Resource(name = "pointOuterMaintainAuditService")
    private PointOuterMaintainAuditService service;
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
									 String unitName,
									 String unitId,
									 String lineName,
									 String lineType,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);
        result.setResults(service.findAllDocumentCount(collectionName, userId, orgId, unitName, unitId, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, orgId, unitName, unitId, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    
    /**
     * 审核（更新数据）
     * @param id 数据主键
     * @param auditResult 审核结果（"1":审核通过，"0":审核不通过）
     * @param auditRemark 审核意见
     * @param auditorId 当前车间登陆账号ID
     * @param auditorName 当前车间登陆账号名称或者手动填写的姓名
     * @param auditorOrgId 当前登陆车间的组织机构ID
     * @param auditorOrgName 当前登陆车间的组织机构名称
     * @param flowState 流程状态
     * @param collectionName 数据库表名
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "auditResult") String auditResult,
			@RequestParam(name = "auditRemark") String auditRemark,
			@RequestParam(name = "userId") String auditorId,
			@RequestParam(name = "userName") String auditorName,
			@RequestParam(name = "orgId") String auditorOrgId,
			@RequestParam(name = "orgName") String auditorOrgName,
			@RequestParam(name = "ddkOrgId") String ddkOrgId,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "collectionName") String collectionName,
            HttpServletRequest request) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String auditDate = "";
    	 try {
    		 auditDate = sdf.format(new Date());
         } catch (Exception e) {
        	 e.printStackTrace();
         }
    	
        Document document = service.findDocumentById(id,collectionName);
        document.put("auditorId", auditorId);
        document.put("auditor", auditorName);
        document.put("auditorOrgId", auditorOrgId);
        document.put("auditorOrg", auditorOrgName);
        document.put("auditDate", auditDate);
        
        document.put("ddkOrgId", ddkOrgId);
        
        if("1".equals(auditResult)){
        	if(StringUtils.isNotBlank(auditRemark)){
        		document.put("auditAdvice", "审核通过："+auditRemark);
        	}else{
        		document.put("auditAdvice", "审核通过");
        	}
        }
        if("0".equals(auditResult)){
        	if(StringUtils.isNotBlank(auditRemark)){
        		document.put("auditAdvice", "审核不通过："+auditRemark);
        	}else{
        		document.put("auditAdvice", "审核不通过");
        	}
        }
        document.put("flowState", flowState);
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("审核成功", res);
    }
    
    @PostMapping("/updateContent")
    public ResultMsg updateContent( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "unit") String unit,
			@RequestParam(name = "serial") String serial,
			@RequestParam(name = "workTimeStart") String workTimeStart,
			@RequestParam(name = "workTimeEnd") String workTimeEnd,
			@RequestParam(name = "lineType") String lineType,
			@RequestParam(name = "formContainerlineName") String lineName,
            @RequestParam(name = "section") String section,
            @RequestParam(name = "station") String station,
            @RequestParam(name = "workPrincipal") String workPrincipal,
            @RequestParam(name = "phone") String phone,
//            @RequestParam(name = "attendPeople") String attendPeople,
//            @RequestParam(name = "louver") String louver,
            @RequestParam(name = "workContentRange") String workContentRange,
            @RequestParam(name = "workOrgCondition") String workOrgCondition,
            @RequestParam(name = "localeDefendMeasure") String localeDefendMeasure,
            @RequestParam(name = "relevantDemand") String relevantDemand,
//            @RequestParam(name = "workPrepareCarry") String workPrepareCarry,
//            @RequestParam(name = "peopleWorkSiteBackWay") String peopleWorkSiteBackWay,
//            @RequestParam(name = "workPeopleDivision") String workPeopleDivision,
//            @RequestParam(name = "safeguardDivision") String safeguardDivision,
//            @RequestParam(name = "dredgeSkillCondition") String dredgeSkillCondition,
//            @RequestParam(name = "safetyAttentionMatter") String safetyAttentionMatter,
            @RequestParam(name = "uploadFileArr") String uploadFileArr,
            @RequestParam(name = "collectionName") String collectionName,
            String auditAdvice,
            String approveAdvice,
            HttpServletRequest request) {
        Document document = service.findDocumentById(id,collectionName);
        document.put("unit", unit);
        document.put("serial", serial);
        document.put("workTime", workTimeStart+"-"+workTimeEnd.substring(11,19));
        document.put("workTimeStart", workTimeStart);
        document.put("workTimeEnd", workTimeEnd);
        document.put("lineType", lineType);
        document.put("lineName", lineName);
        document.put("section", section);
        document.put("station", station);
        document.put("workPrincipal", workPrincipal);
        document.put("phone", phone);
//        document.put("attendPeople", attendPeople);
//        document.put("louver", louver);
        document.put("workContentRange", workContentRange);
        document.put("workOrgCondition", workOrgCondition);
        document.put("localeDefendMeasure", localeDefendMeasure);
        document.put("relevantDemand", relevantDemand);
//        document.put("workPrepareCarry", workPrepareCarry);
//        document.put("peopleWorkSiteBackWay", peopleWorkSiteBackWay);
//        document.put("workPeopleDivision", workPeopleDivision);
//        document.put("safeguardDivision", safeguardDivision);
//        document.put("dredgeSkillCondition", dredgeSkillCondition);
//        document.put("safetyAttentionMatter", safetyAttentionMatter);
        
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
        
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
}