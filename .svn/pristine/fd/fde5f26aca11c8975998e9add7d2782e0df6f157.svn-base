package com.enovell.yunwei.km_micor_service.action.constructionManage.pointInnerMaintainPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.service.constructionManage.pointInnerMaintainPlan.PointInnerSecondMaintainQueryService;

@RestController
@RequestMapping("/pointInnerSecondMaintainQueryAction")
public class PointInnerSecondMaintainQueryAction {
	@Resource(name = "pointInnerSecondMaintainQueryService")
    private PointInnerSecondMaintainQueryService service;
	@PostMapping("/findAll")
    public GridDto<Document> findAll(
                                     @RequestParam String collectionName,
									 String constructionProject,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String submitOrgName,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, constructionProject, flowState, startUploadDate, endUploadDate,submitOrgName));
        result.setRows(service.findAllDocument(collectionName, constructionProject, flowState, startUploadDate, endUploadDate,submitOrgName,start, limit));
        return result;
    }
	/**
	 * 
	 * getAllworkArea 获取所有工区数据
	 * @return
	 */
	@PostMapping("/getAllworkArea")
	public List<Map<String, String>> getAllworkArea() {
		List<String> cjList = service.getAllworkArea();
		List<Map<String, String>> cjDatas = new ArrayList<>();
		for (String string : cjList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			cjDatas.add(map);
		}
		return cjDatas;
	}
}
