package com.enovell.yunwei.km_micor_service.action.constructionManage.constructionPlan;


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
import com.enovell.yunwei.km_micor_service.service.constructionManage.constructionPlan.ConstructApprovedService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：ConstructApprovedAction   
 * 类描述:  施工方案管理---段领导审批
 * 创建时间：2019年7月8日 上午10:29:46
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月8日 上午10:29:46   
 *
 */
@RestController
@RequestMapping("/constructApprovedAction")
public class ConstructApprovedAction {
	
	@Resource(name="constructApprovedService")
	private ConstructApprovedService service;
	
	@PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
                                     String name,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String auditStatus,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, name, startUploadDate, endUploadDate,auditStatus));
        result.setRows(service.findAllDocument(collectionName, name, startUploadDate, endUploadDate,auditStatus, start, limit));
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
        if(StringUtils.isNotBlank(auditRemark)) {
        	doc.put("leaderAuditRemark", auditRemark);
        }
        doc.put("auditStatus", flowState);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }

}
