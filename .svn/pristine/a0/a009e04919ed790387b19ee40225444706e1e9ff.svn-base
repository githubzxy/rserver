package com.enovell.yunwei.km_micor_service.action.integratedManage.attendanceManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.CommonPageService;
import com.enovell.yunwei.km_micor_service.service.integratedManage.attendanceManage.AttendanceManageService;
import com.enovell.yunwei.km_micor_service.service.userInfoManage.UserInfoManageService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * kunmingTXD
 * 昆明微服务考勤管理填写Action
 *
 * @author yangsy
 * @date 19-3-29
 */
@RestController
@RequestMapping("/attendanceManageAction")
public class AttendanceManageAction {
	@Resource(name = "attendanceManageService")
    private AttendanceManageService service;
	@Resource(name = "userInfoManageService")
	private UserInfoManageService userInfoManageService;
	@Resource(name = "commonPageService")
    private CommonPageService comService;
	
	@PostMapping("/getUsersByOrgId")
    public GridDto<Document> getUsersByOrgId(
    		@RequestParam("orgId") String orgId,
    		@RequestParam("collectionName") String collectionName,
    		@RequestParam("attendanceManage") String attendanceManage,
    		@RequestParam("attendanceUserChange") String attendanceUserChange,
    		@RequestParam("date") String date
//    		@RequestParam int start, @RequestParam int limit) {
    		) {
		GridDto<Document> result = new GridDto<>();
//        result.setResults(service.getUsersCountByOrgId(orgId, collectionName, attendanceManage, attendanceUserChange, date));
//        result.setRows(service.getUsersByOrgId(orgId, collectionName, attendanceManage, attendanceUserChange, date, start, limit));
        result.setRows(service.getUsersByOrgId(orgId, collectionName, attendanceManage, attendanceUserChange, date,0,0));
        return result;
    }
	
	@PostMapping("/getUsersCountByOrgId")
    public long getUsersCountByOrgId(
    		@RequestParam("orgId") String orgId,
    		@RequestParam("collectionName") String collectionName) {
        return service.getUsersCountByOrgId(orgId, collectionName, "", "" ,"");
    }
//	@PostMapping("/getAttendanceCountByOrgId")
//	public long getAttendanceCountByOrgId(
//			@RequestParam("orgId") String orgId,
//			@RequestParam("collectionName") String collectionName) {
//		return service.getAttendanceCountByOrgId(orgId, collectionName);
//	}
	
	/**
     * 查询一条记录
     * @param id
     * @param collectionName
     * @return
     */
    @PostMapping("/findById")
    public ResultMsg findDocById(@RequestParam("docId") String docId,@RequestParam("date") String date,
                                 @RequestParam("collectionName") String collectionName) {
        Document res = service.findDocumentById(docId, date, collectionName);
        return ResultMsg.getSuccess("查询完成", res);
    }
    
    
    @PostMapping("/getUserByDepartmentName")
    public List<Document> getUserByDepartmentName(
    		@RequestParam("department") String department,
    		@RequestParam("orgId") String orgId,
    		@RequestParam("userInfoManage") String userInfoManage) {
    	List<Document> res = service.getUserByDepartmentName(department, orgId ,userInfoManage);
    	return res;
    }
    
    /**
     * 添加助勤借调人员的信息
     * @param user 人员的ID和名字
     * @param collectionName 助勤借调中间表
     * @param date 助勤借调开始的时间
     * @param department 原来的组织机构名称
     * @param departmentId 原来的组织机构ID
     * @param toOrgId 助勤的组织机构ID
     * @param toOrgName 助勤的组织机构名称
     * @return
     */
    @PostMapping("/addUserChangeDoc")
    public ResultMsg addUserChangeDoc(
    		@RequestParam("user") String user,
    		@RequestParam("collectionName") String collectionName,
    		@RequestParam("date") String date,
    		@RequestParam("department") String department,
    		@RequestParam("departmentId") String departmentId,
    		@RequestParam("toOrgId") String toOrgId,
    		@RequestParam("toOrgName") String toOrgName) {
    	
    	String[] userIdAndName = user.split(",");
    	String userId = userIdAndName[0];
//    	String userName = userIdAndName[1];
    	
    	Document doc = userInfoManageService.findDocumentById(userId,"userInfoManage");
        doc.put("toDate", date);
        doc.put("department", department);
        doc.put("departmentId", departmentId);
        doc.put("toOrgId", toOrgId);
        doc.put("toOrgName", toOrgName);
        
        Document res = service.addDocument(doc, collectionName);
        return ResultMsg.getSuccess("添加成功", res);
    }
	
	
	@PostMapping("/findByUserId")
    public GridDto<Document> findAll(@RequestParam String collectionName,
                                     String name,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String auditStatus,
                                     String userId,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, name, startUploadDate, endUploadDate,auditStatus, userId));
        result.setRows(service.findAllDocument(collectionName, name, startUploadDate, endUploadDate,auditStatus, userId, start, limit));
        return result;
    }
	@PostMapping("/addDoc")
	public ResultMsg addDoc(
							@RequestParam(name = "docId") String docId,
							@RequestParam(name = "staffName") String staffName,
							@RequestParam(name = "date") String date,
    						@RequestParam(name = "morning") String morning,
                            @RequestParam(name = "noon") String noon,
                            @RequestParam(name = "night") String night,
                            String shifts,
                            @RequestParam(name = "daily") String daily,
                            @RequestParam(name = "turn") String turn,
                            @RequestParam(name = "userId") String userId,
                            @RequestParam(name = "userName") String userName,
                            @RequestParam(name = "orgId") String orgId,
                            @RequestParam(name = "orgName") String orgName,
                            @RequestParam(name = "collectionName") String collectionName,
                            HttpServletRequest request) {
		SimpleDateFormat sdfCreateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateStr = "";
        currentDateStr = sdfCreateTime.format(currentDate);
        
        Document doc = service.findDocumentById(docId, date, collectionName);
        
        if(doc!=null){
        	doc.put("morning", morning);
        	doc.put("noon", noon);
        	doc.put("night", night);
//        	doc.put("shifts", shifts);
        	doc.put("daily", daily);
        	doc.put("turn", turn);
        	Document res = service.updateDocument(doc, collectionName);
        	return ResultMsg.getSuccess("打卡成功", res);
        }
        
        Document document = new Document();
        document.put("docId", docId);
        document.put("staffName", staffName);
        document.put("date", date);
        document.put("createTime", currentDateStr);
        document.put("morning", morning);
        document.put("noon", noon);
        document.put("night", night);
//        document.put("shifts", shifts);
        document.put("daily", daily);
        document.put("turn", turn);
        document.put("userId", userId);
        document.put("userName", userName);
        document.put("orgId", orgId);
        document.put("orgName", orgName);
        document.put("year", date.substring(0, 4)+"年");
        String month = "";
        if(date.substring(5, 6).equals("0")){
        	month = date.substring(6, 7)+"月";
        }else{
        	month = date.substring(5, 7)+"月";
        }
        String day = "";
        if(date.substring(8, 9).equals("0")){
        	day = date.substring(9, 10)+"日";
        }else{
        	day = date.substring(8, 10)+"日";
        }
        document.put("month", month);
        document.put("day", day);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("打卡成功", res);
	}
	 @PostMapping("/updateDoc")
	    public ResultMsg updateDoc(	@RequestParam("id") String id,
	    		@RequestParam(name = "name") String name,
                @RequestParam(name = "depart") String depart,
                @RequestParam(name = "userId") String userId,
                @RequestParam(name = "userName") String userName,
                @RequestParam(name = "orgId") String orgId,
                @RequestParam(name = "orgName") String orgName,
                @RequestParam(name = "parentId") String parentId,
                @RequestParam(name = "flowState") String flowState,
                @RequestParam(name = "collectionName") String collectionName,
                @RequestParam(name = "uploadFileArr") String uploadFileArr,
					                HttpServletRequest request) {
	        Document doc = service.findDatasById(id,collectionName);
	        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
	        doc.put("uploadFileArr", uploadFile);
	        doc.put("name", name);
	        doc.put("depart", depart);
	        doc.put("userId", userId);
	        doc.put("depart", depart);
	        doc.put("userName", userName);
	        doc.put("orgId", orgId);
	        doc.put("orgName", orgName);
	        doc.put("parentId", parentId);
	        doc.put("auditStatus", flowState);
	        doc.put("safeAuditRemark", "");
	        doc.put("dispatchAuditRemark", "");
	        doc.put("leaderAuditRemark", "");
	        
	        Document res = service.updateDocument(doc, collectionName);
	        return ResultMsg.getSuccess("修改成功", res);
	    }
	 
//	 @PostMapping("/removeToBackDoc")
//	    public ResultMsg removeToBackDoc(
//	    		String id,
//	    		@RequestParam(name = "collectionName") String collectionName,
//            	HttpServletRequest request) {
//	        Document doc = service.getDataById(id,collectionName);
//	        doc.put("status", 0);
//	        Document res = service.removeToBackDoc(doc, collectionName);
//	        return ResultMsg.getSuccess("调回成功", res);
//	    }
	 @PostMapping("/removeBackDoc")
	 public ResultMsg removeBackDoc(
			 String docId,
			 @RequestParam(name = "collectionName") String collectionName,
			 HttpServletRequest request) {
		 Document doc = service.getDataByDocId(docId,collectionName);
		 doc.put("status", 0);
		 Document res = service.removeBackDoc(doc, collectionName);
		 return ResultMsg.getSuccess("调回成功", res);
	 }
	

}
