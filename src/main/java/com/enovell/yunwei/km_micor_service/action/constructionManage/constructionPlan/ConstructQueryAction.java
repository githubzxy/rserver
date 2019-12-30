package com.enovell.yunwei.km_micor_service.action.constructionManage.constructionPlan;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.service.constructionManage.constructionPlan.ConstructQueryService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：ConstructQueryAction   
 * 类描述:  施工方案查询
 * 创建人：zhouxingyu
 * 创建时间：2019年7月8日 上午10:36:00
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月8日 上午10:36:00   
 *
 */
@RestController
@RequestMapping("/constructQueryAction")
public class ConstructQueryAction {
	@Resource(name = "constructQueryService")
    private ConstructQueryService service;
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
