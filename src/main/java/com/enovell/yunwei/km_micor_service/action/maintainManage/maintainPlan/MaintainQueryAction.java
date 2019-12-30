package com.enovell.yunwei.km_micor_service.action.maintainManage.maintainPlan;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.service.maintainManage.maintainPlan.MaintainQueryService;

@RestController
@RequestMapping("/maintainQueryAction")
public class MaintainQueryAction {
	@Resource(name = "maintainQueryService")
    private MaintainQueryService service;
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
}
