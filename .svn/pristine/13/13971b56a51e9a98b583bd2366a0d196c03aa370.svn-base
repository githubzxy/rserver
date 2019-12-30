package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.enovell.yunwei.km_micor_service.dto.PointOuterMaintainApplyDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.PointOuterMaintainApplyService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/pointOuterMaintainApplyAction")
public class PointOuterMaintainApplyAction {
	@Resource(name = "pointOuterMaintainApplyService")
    private PointOuterMaintainApplyService service;
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
  * @param unit 单位
  * @param serial 编号
  * @param workTime 作业时间
  * @param lineName 线别
  * @param section 区间
  * @param station 站点
  * @param workPrincipal 作业负责人
  * @param phone 联系电话
  * @param auditor 审核人
  * @param auditDate 审核日期
  * @param approver 审批人
  * @param approveDate 审批日期
  * @param attendPeople 参加人员
  * @param louver 天窗作业
  * @param workContentRange 作业内容及范围
  * @param workPrepareCarry 作业机械料具准备及携带情况
  * @param peopleWorkSiteBackWay  人员到达作业地点及返回路线
  * @param workPeopleDivision 作业人员分工
  * @param safeguardDivision 防护人员分工
  * @param dredgeSkillCondition 开通技术条件
  * @param safetyAttentionMatter 安全风险控制措施
  * @param flowState 审核状态
  * @param orgId 组织ID
  * @param orgName 组织名
  * @param collectionName 表名
  * @param request
  * @return
  */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(
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
//                            @RequestParam(name = "attendPeople") String attendPeople,
//                            @RequestParam(name = "louver") String louver,
                            @RequestParam(name = "workContentRange") String workContentRange,
                            @RequestParam(name = "workOrgCondition") String workOrgCondition,
                            @RequestParam(name = "localeDefendMeasure") String localeDefendMeasure,
                            @RequestParam(name = "relevantDemand") String relevantDemand,
//                            @RequestParam(name = "workPrepareCarry") String workPrepareCarry,
//                            @RequestParam(name = "peopleWorkSiteBackWay") String peopleWorkSiteBackWay,
//                            @RequestParam(name = "workPeopleDivision") String workPeopleDivision,
//                            @RequestParam(name = "safeguardDivision") String safeguardDivision,
//                            @RequestParam(name = "dredgeSkillCondition") String dredgeSkillCondition,
//                            @RequestParam(name = "safetyAttentionMatter") String safetyAttentionMatter,
                            @RequestParam(name = "flowState") String flowState,
                            @RequestParam(name = "orgId") String orgId,
                            @RequestParam(name = "orgName") String orgName,
                            @RequestParam(name = "parentId") String parentId,
                            @RequestParam(name = "uploadFileArr") String uploadFileArr,
                            @RequestParam(name = "collectionName") String collectionName,
                            HttpServletRequest request) {
        Document document = new Document();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateStr = "";
        currentDateStr = sdf.format(currentDate);
        
        document.put("createDate", currentDateStr);
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
        document.put("flowState", flowState);
        document.put("orgId", orgId);
        document.put("orgName", orgName);
        document.put("parentId", parentId);
        document.put("summaryDate", "");//汇总日期
        document.put("summaryPersonId", "");//汇总人ID
        document.put("summaryPersonName", "");//汇总人名称
        document.put("workDate", workTimeStart.substring(0, 10));//年月日
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
        
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
        
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
        service.removeDocument(ids, collectionName);
        return ResultMsg.getSuccess("删除成功");
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
        result.setResults(service.findAllDocumentCount(collectionName, userId, orgId, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, orgId, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( 
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
            @RequestParam(name = "flowState") String flowState,
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
        document.put("flowState", flowState);
        document.put("auditDate", "");
    	document.put("auditor", "");
    	document.put("auditorId", "");
    	document.put("auditorOrg", "");
    	document.put("auditorOrgId", "");
    	document.put("approveDate", "");
    	document.put("approver", "");
    	document.put("approverId", "");
    	document.put("approverOrgName", "");
    	document.put("approverOrgId", "");
        if(StringUtils.isNotBlank(auditAdvice)){
        	document.put("auditAdvice", "");
        }
        if(StringUtils.isNotBlank(approveAdvice)){
        	document.put("approveAdvice", "");
        }
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
        document.put("workDate", workTimeStart.substring(0, 10));//年月日
        
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
        
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
    @PostMapping("/updateExecuteDoc")
    public ResultMsg updateExecuteDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "checkSituation") String checkSituation,
    		@RequestParam(name = "situationRemark") String situationRemark,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		String auditAdvice,
    		String approveAdvice,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
    	
    	if("1".equals(checkSituation)){//TODO 有大部分相同类型代码 待重构
    		if(StringUtils.isNotBlank(situationRemark)){
    			document.put("workareaFinish", "已完成："+situationRemark);
    		}else{
    			document.put("workareaFinish", "已完成");
    		}
        }
        if("0".equals(checkSituation)){
        	if(StringUtils.isNotBlank(situationRemark)){
        		document.put("workareaFinish", "未完成："+situationRemark);
        	}else{
        		document.put("workareaFinish", "未完成");
        	}
        }
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("执行成功", res);
    }
    
    @PostMapping("/exportApplyFrom")
	@ResponseBody
	public ResultMsg txdWriteBusiWord(@RequestBody PointOuterMaintainApplyDto dto,HttpServletRequest request) {
//		String rootPathTomcat = request.getSession().getServletContext().getRealPath("/transDownload/");
		return ResultMsg.getSuccess(service.exportApplyFrom(dto));
	}
    
}