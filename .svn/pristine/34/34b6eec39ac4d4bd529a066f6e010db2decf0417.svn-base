package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.PointOuterMaintainQueryService;

@RestController
@RequestMapping("/pointOuterMaintainQueryAction")
public class PointOuterMaintainQueryAction {
	@Resource(name = "pointOuterMaintainQueryService")
    private PointOuterMaintainQueryService service;
    @Resource(name = "userService")
    private UserService userService;
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
     * 主页显示
     * @param collectionName
     * @param name
     * @param startUploadDate
     * @param endUploadDate
     * @param auditStatus
     * @param orgId
     * @param start
     * @param limit
     * @return
     */
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
									 @RequestParam String userId,
									 @RequestParam String orgId,
									 String unitName,
									 String unitId,
									 String unitType,
									 String lineName,
									 String lineType,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);

        result.setResults(service.findAllDocumentCount(collectionName, userId, orgId, unitName, unitId, unitType, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, orgId, unitName, unitId, unitType, lineName, lineType, flowState, currentDay, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    
}
