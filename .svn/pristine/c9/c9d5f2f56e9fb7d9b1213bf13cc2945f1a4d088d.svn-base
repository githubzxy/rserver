package com.enovell.yunwei.km_micor_service.action.constructionManage.constructionPlan;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.enovell.yunwei.km_micor_service.service.constructionManage.constructionPlan.ConstructApplyService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：ConstructApplyAction   
 * 类描述:  施工方案--申请
 * 创建人：zhouxingyu
 * 创建时间：2019年7月8日 上午10:34:48
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月8日 上午10:34:48   
 *
 */
@RestController
@RequestMapping("/constructApplyAction")
public class ConstructApplyAction {
	@Resource(name = "constructApplyService")
    private ConstructApplyService service;
	@Resource(name = "commonPageService")
    private CommonPageService comService;
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
	    						@RequestParam(name = "name") String name,
	                            @RequestParam(name = "depart") String depart,
	                            @RequestParam(name = "userId") String userId,
	                            @RequestParam(name = "uploadFileArr") String uploadFileArr,
	                            @RequestParam(name = "userName") String userName,
	                            @RequestParam(name = "orgId") String orgId,
	                            @RequestParam(name = "orgName") String orgName,
	                            @RequestParam(name = "parentId") String parentId,
	                            @RequestParam(name = "flowState") String flowState,
	                            @RequestParam(name = "collectionName") String collectionName,
	                            HttpServletRequest request) {
	        Document document = new Document();
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDate = new Date();
			DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
	        document.put("uploadFileArr", uploadFile);
	        document.put("name", name);
	        document.put("createDate", sdf.format(currentDate));
	        document.put("depart", depart);
	        document.put("userId", userId);
	        document.put("userName", userName);
	        document.put("orgId", orgId);
	        document.put("orgName", orgName);
	        document.put("parentId", parentId);
	        document.put("auditStatus", flowState);
	        document.put("safeAuditRemark", "");
	        document.put("dispatchAuditRemark", "");
	        document.put("leaderAuditRemark", "");
	        Document res = service.addDocument(document, collectionName);
	        return ResultMsg.getSuccess("新增成功", res);
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
	

}
