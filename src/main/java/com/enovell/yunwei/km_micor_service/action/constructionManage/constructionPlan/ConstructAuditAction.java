package com.enovell.yunwei.km_micor_service.action.constructionManage.constructionPlan;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.constructionManage.constructionPlan.ConstructAuditService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：ConstructAuditAction   
 * 类描述:  施工方案科室审核
 * 创建人：zhouxingyu
 * 创建时间：2019年7月8日 上午10:31:01
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月8日 上午10:31:01   
 *
 */
@RestController
@RequestMapping("/constructAuditAction")
public class ConstructAuditAction {
	
	@Resource(name="constructAuditService")
	private ConstructAuditService service;
	
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
        if(flowState.equals("0")) {
        	auditRemark="退回："+auditRemark;
        }else {
        	auditRemark="审核通过："+auditRemark;
        }
        if(orgId.equals("402891b45b5fd02c015b74a20fd10033")) {//安全科Id：402891b45b5fd02c015b74a20fd10033
        	doc.put("safeAuditRemark", auditRemark);
        }else if(orgId.equals("402891b45b5fd02c015b74c97d740037")) {//调度科ID：402891b45b5fd02c015b74c97d740037
        	doc.put("dispatchAuditRemark", auditRemark);
        }
        
        doc.put("auditStatus", flowState);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }

}
