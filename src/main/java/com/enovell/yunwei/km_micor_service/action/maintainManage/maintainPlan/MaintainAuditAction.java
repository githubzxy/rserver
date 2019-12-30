package com.enovell.yunwei.km_micor_service.action.maintainManage.maintainPlan;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.maintainManage.maintainPlan.MaintainAuditService;

@RestController
@RequestMapping("/maintainAuditAction")
public class MaintainAuditAction {
	
	@Resource(name="maintainAuditService")
	private MaintainAuditService service;
	
	@PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
                                     String name,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String auditStatus,
                                     String orgId,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, name, startUploadDate, endUploadDate,auditStatus,orgId));
        result.setRows(service.findAllDocument(collectionName, name, startUploadDate, endUploadDate,auditStatus, start, limit,orgId));
        return result;
    }
	
	@PostMapping("/updateDoc")
    public ResultMsg updateDoc(	@RequestParam("id") String id,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "collectionName") String collectionName,
            @RequestParam(name = "auditRemark") String auditRemark,
            @RequestParam(name = "orgId") String orgId,
				                HttpServletRequest request) {
        Document doc = service.findDatasById(id,collectionName);
//        if(StringUtils.isBlank(auditRemark)||auditRemark==null) 
//        	auditRemark="";
        if(flowState.equals("0")) {
        	auditRemark="退回："+auditRemark;
        }else {
        	auditRemark="审核通过："+auditRemark;
        }
        if(orgId.equals("402891b45b5fd02c015b74a20fd10033")) {//安全科
        	doc.put("safeAuditRemark", auditRemark);
        }else if(orgId.equals("402891b45b5fd02c015b74c97d740037")) {
        	doc.put("dispatchAuditRemark", auditRemark);
        }
        
        doc.put("auditStatus", flowState);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }

}
