package com.enovell.yunwei.km_micor_service.action;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.CommonPageService;
import com.enovell.yunwei.km_micor_service.service.UserService;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * kunmingTXD
 * 昆明微服务施工计划Action
 *
 * @author liwt
 * @date 18-11-26
 */
@RestController
@RequestMapping("/workPlanAction")
public class WorkPlanAction {
    @Resource(name = "commonPageService")
    private CommonPageService service;
    @Resource(name = "userService")
    private UserService userService;

    /**
     * 新增数据
     * 防止上传文件名乱码，需在路径最前加入/zuul，此处为localhost:homeport/zuul/kmms/commonAction/addDoc
     *
     * @param name           数据名称
     * @param fileCols       上传附件name 逗号分割
     * @param collectionName 存储表名
     * @param userId         上传人id
     * @param request        request
     * @return 保存后的数据对象
     */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(@RequestParam(name = "name") String name,
    						@RequestParam(name = "orgSelectName") String orgSelectName,
    						@RequestParam(name = "orgSelectId") String orgSelectId,
                            @RequestParam(name = "fileCols") String fileCols,
                            @RequestParam(name = "collectionName") String collectionName,
                            @RequestParam(name = "userId") String userId,
                            HttpServletRequest request) {
        Document document = new Document();
        Arrays.stream(fileCols.split(",")).forEach(f -> {
            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
            document.put(f, files);
        });
        document.put("name", name);
        document.put("createDate", new Date());
        document.put("createUserId", userId);
//        Map<String, Object> org = userService.getOrgbyUserId(userId);
//        document.put("createOrg", org.get("ORG_ID_"));
//        document.put("createOrgName", org.get("ORG_NAME_"));
        Map<String, Object> user = userService.getUserById(userId);
        document.put("createUserName", user.get("USER_NAME_"));
        document.put("orgId", orgSelectId);
        document.put("orgName", orgSelectName);
        //默认添加设置状态为待审批 状态为 "0"
        document.put("auditStatus", 0);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }
    
    /**
     * 审核数据，设置状态为已审核 ---- 1
     *
     * @param id             数据id
     * @param collectionName 表名
     * @return 状态返回
     */
    @PostMapping("/auditDoc")
    public ResultMsg auditDoc(@RequestParam("id") String id,
                               @RequestParam("collectionName") String collectionName) {
    	Document doc = service.findDocumentById(id,collectionName);
        //默认添加设置状态为待审批 状态为 "1"
        doc.put("auditStatus", 1);
        service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("审核成功");
    }

    @PostMapping("/updateDoc")
    public ResultMsg updateDoc(@RequestParam("id") String id,
    							@RequestParam(name = "name") String name,
    							@RequestParam(name = "orgSelectName") String orgSelectName,
    							@RequestParam(name = "orgSelectId") String orgSelectId,
                               @RequestParam(name = "fileCols") String fileCols,
                               @RequestParam("collectionName") String collectionName,
                               HttpServletRequest request) {
        Document doc = service.findDocumentById(id,collectionName);
        Arrays.stream(fileCols.split(",")).forEach(f->{
            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
            List<Document> nowFile = doc.get(f,List.class);
            //保存新的上传文件
            nowFile.addAll(files);
            //删除已删除的上传文件
            String delFile = request.getParameter("del-"+f);
            List<String> delFileIds = Arrays.asList(delFile.split(","));
            doc.put(f,nowFile.stream().filter(item-> !delFileIds.contains(item.getString("id"))).collect(Collectors.toList()));
        });
        doc.put("name", name);
        doc.put("orgId", orgSelectId);
        doc.put("orgName", orgSelectName);
        //默认添加设置状态为待审批 状态为 "0"
        doc.put("auditStatus", 0);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
}
