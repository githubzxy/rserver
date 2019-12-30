package com.enovell.yunwei.km_micor_service.action.constructionManage.pointInnerMaintainPlan;

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
import com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan.PointInnerSecondMaintainApproveService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

@RestController
@RequestMapping("/pointInnerSecondMaintainApproveAction")
public class PointInnerSecondMaintainApproveAction {
	@Resource(name = "pointInnerSecondMaintainApproveService")
    private PointInnerSecondMaintainApproveService service;
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
//    /**
//     * 查询一条记录
//     * @param id
//     * @param collectionName
//     * @return
//     */
//    @PostMapping("/findById")
//    public ResultMsg findDocById(@RequestParam("id") String id,
//                                 @RequestParam("collectionName") String collectionName) {
//        Document res = service.findDocumentById(id, collectionName);
//        return ResultMsg.getSuccess("查询完成", res);
//    }
    /**
     * 主页查询 
     * @param collectionName 查询表名
     * @param orgId 登录用户所属组织机构ID
     * @param constructionProject 施工项目
     * @param flowState 流程状态
     * @param startUploadDate 
     * @param endUploadDate
     * @param start 0
     * @param limit 20
     * @return GridDto<Document>
     */
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
									 @RequestParam String orgId,
									 String constructionProject,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String submitOrgName,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);
        result.setResults(service.findAllDocumentCount(collectionName, orgId, currentDay, constructionProject, flowState, startUploadDate, endUploadDate,submitOrgName));
        result.setRows(service.findAllDocument(collectionName, orgId, currentDay, constructionProject, flowState, startUploadDate, endUploadDate,submitOrgName, start, limit));
        return result;
    }
    
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( @RequestParam("id") String id,
    		@RequestParam(name = "userId") String approverId,
    		@RequestParam(name = "userName") String approver,
            @RequestParam(name = "approveResult") String approveResult,
            @RequestParam(name = "approveAdvice") String approveAdvice,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "collectionName") String collectionName,
            HttpServletRequest request) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String approveDate = "";
    	approveDate = sdf.format(new Date());
    	
        Document document = service.findDocumentById(id,collectionName);
        
        document.put("approverId", approverId);
        document.put("approver", approver);
        document.put("approveDate", approveDate);
        
        if("1".equals(approveResult)){
        	if(StringUtils.isNotBlank(approveAdvice)){
        		document.put("approveAdvice", "审批通过："+approveAdvice);
        	}else{
        		document.put("approveAdvice", "审批通过");
        	}
        }
        if("0".equals(approveResult)){
        	if(StringUtils.isNotBlank(approveAdvice)){
        		document.put("approveAdvice", "审批不通过："+approveAdvice);
        	}else{
        		document.put("approveAdvice", "审批不通过");
        	}
        }
        
        document.put("flowState", flowState);
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
}