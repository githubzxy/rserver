package com.enovell.yunwei.km_micor_service.action.constructionManage.pointInnerMaintainPlan;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.PointInnerSecondMaintainApplyDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.SkylightMaintenanceApplyDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan.PointInnerSecondMaintainApplyService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/pointInnerSecondMaintainApplyAction")
public class PointInnerSecondMaintainApplyAction {
	@Resource(name = "pointInnerSecondMaintainApplyService")
    private PointInnerSecondMaintainApplyService service;
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
     * 新增II级维修
     * @param constructionProject 施工项目
     * @param submitOrgId 提交部门ID（登录人所属组织机构ID）
     * @param submitOrgName 提交部门名称（登录人所属组织机构名称）
     * @param userId 登录人ID
     * @param userName 登录人名称
     * @param parentOrgId 登录用户所属机构的上级机构ID
     * @param flowState 流程状态
     * @param uploadFileArr 附件
     * @param collectionName mongo存放表名
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(
    						@RequestParam(name = "constructionProject") String constructionProject,
    						@RequestParam(name = "submitOrgId") String submitOrgId,
    						@RequestParam(name = "submitOrgName") String submitOrgName,
    						@RequestParam(name = "userId") String userId,
                            @RequestParam(name = "userName") String userName,
                            @RequestParam(name = "parentId") String parentId,
                            @RequestParam(name = "flowState") String flowState,
                            @RequestParam(name = "uploadFileArr") String uploadFileArr,
                            @RequestParam(name = "collectionName") String collectionName,
                            HttpServletRequest request) {
        Document document = new Document();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateStr = "";
        currentDateStr = sdf.format(currentDate);
        
        document.put("createDate", currentDateStr);
        document.put("constructionProject", constructionProject);
        document.put("submitOrgId", submitOrgId);
        document.put("submitOrgName", submitOrgName);
        document.put("userId", userId);
        document.put("userName", userName);
        document.put("parentId", parentId);
        document.put("flowState", flowState);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
//        document.put("summaryDate", "");//汇总日期
//        document.put("summaryPersonId", "");//汇总人ID
//        document.put("summaryPersonName", "");//汇总人名称
//        if("1".equals(flowState)){
//        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
//        	String currentDay = "";
//        	try {
//        		currentDay = sdfDay.format(new Date());
//        	} catch (Exception e) {
//        		e.printStackTrace();
//        	}
//        	document.put("applyDate", currentDay);
//        }else{
//        	document.put("applyDate", "");
//        }
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
     * 主页查询 
     * @param collectionName 查询表名
     * @param userId 登录用户ID
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
									 @RequestParam String userId,
									 String constructionProject,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);
        result.setResults(service.findAllDocumentCount(collectionName, userId, currentDay, constructionProject, flowState, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, currentDay, constructionProject, flowState, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    /**
     * 
     * @param id
     * @param constructionProject
     * @param flowState
     * @param uploadFileArr
     * @param collectionName
     * @param auditAdvice
     * @param approveAdvice
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "constructionProject") String constructionProject,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "uploadFileArr") String uploadFileArr,
            @RequestParam(name = "collectionName") String collectionName,
            String approveAdvice,
            HttpServletRequest request) {
        Document document = service.findDocumentById(id,collectionName);
        document.put("constructionProject", constructionProject);
        document.put("flowState", flowState);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
    	document.put("approverId", "");
    	document.put("approver", "");
    	document.put("approveDate", "");
        document.put("approveAdvice", "");
//        if("1".equals(flowState)){
//        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
//       	 	String currentDay = "";
//            try {
//                currentDay = sdfDay.format(new Date());
//            } catch (Exception e) {
//            	e.printStackTrace();
//            }
//            document.put("applyDate", currentDay);
//        }
        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
    @PostMapping("/updateReplyDoc")
    public ResultMsg updateReplyDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "workareaReply") String workareaReply,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		String auditAdvice,
    		String approveAdvice,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
//    	if("1".equals(checkSituation)){
//        	document.put("workareaFinish", "已完成："+situationRemark);
//        }
//        if("0".equals(checkSituation)){
//        	document.put("workareaFinish", "未完成："+situationRemark);
//        }
    	document.put("workareaReply", workareaReply);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    
    @PostMapping("/exportApplyFrom")
	@ResponseBody
	public ResultMsg txdWriteBusiWord(@RequestBody PointInnerSecondMaintainApplyDto dto,HttpServletRequest request) {
		return ResultMsg.getSuccess(service.exportApplyFrom(dto));
	}
}