package com.enovell.yunwei.km_micor_service.action.securityManage.safetyAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.securityManage.safetyAnalysis.SafetyAnalysisService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：SafetyAnalysisAction   
 * 类描述:  安全分析控制器
 * 创建人：zhouxingyu
 * 创建时间：2019年7月17日 上午10:19:53
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月17日 上午10:19:53   
 *
 */
@RestController
@RequestMapping("/safetyAnalysisAction")
public class SafetyAnalysisAction {
	
	@Resource(name = "safetyAnalysisService")
    private SafetyAnalysisService service;
    @Resource(name = "userService")
    private UserService userService;
    @PostMapping("/addDoc")
    public ResultMsg addDoc(@RequestParam(name = "name") String name,
    						@RequestParam(name = "orgSelectName") String orgSelectName,
    						@RequestParam(name = "orgSelectId") String orgSelectId,
    						@RequestParam(name = "checkDate") String checkDate,
//                            @RequestParam(name = "fileCols") String fileCols,
                            @RequestParam(name = "uploadFileArr") String uploadFileArr,
                            @RequestParam(name = "collectionName") String collectionName,
                            @RequestParam(name = "userId") String userId,
                            HttpServletRequest request) {
        Document document = new Document();
//        Arrays.stream(fileCols.split(",")).forEach(f -> {
//            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
//            document.put(f, files);
//        });
        document.put("name", name);
        document.put("createDate", new Date());
        document.put("checkDate", checkDate);
        document.put("createUserId", userId);
        Map<String, Object> user = userService.getUserById(userId);
        document.put("createUserName", user.get("USER_NAME_"));
        document.put("orgId", orgSelectId);
        document.put("userId", userId);
        document.put("orgName", orgSelectName);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }
    
    
    /**
     * 删除数据
     *
     * @param id             数据id
     * @param collectionName 表名
     * @return 状态返回
     */
    @PostMapping("/removeDoc")
    public ResultMsg removeDoc(@RequestParam("id") String id,
    						   @RequestParam("userId") String userId,
                               @RequestParam("collectionName") String collectionName) {
    	 String orgId = userService.getOrgIdByUser(userId);
        List<String> ids = Arrays.asList(id.split(","));
        List<String> userIdList = new ArrayList<String>();
        ids.forEach(s->{
        	 Document res = service.findDocumentById(s, collectionName);
        	 userIdList.add((String) res.get("orgId"));
        });
   	 boolean userIdReat =userIdList.stream().allMatch(f->f.equals(orgId)==true);
     if(userIdReat==true){
    	 service.removeDocument(ids, collectionName);
    	 return ResultMsg.getSuccess("删除成功");
     }else{
    	 return ResultMsg.getFailure("请选择所属机构添加的记录删除");
     }
    }
    
    
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc(@RequestParam("id") String id,
    						    @RequestParam(name = "name") String name,
    							@RequestParam(name = "orgSelectName") String orgSelectName,
    							@RequestParam(name = "orgSelectId") String orgSelectId,
    							@RequestParam(name = "checkDate") String checkDate,
//                               @RequestParam(name = "fileCols") String fileCols,
    							@RequestParam(name = "uploadFileArr") String uploadFileArr,
    							@RequestParam("collectionName") String collectionName,
                               HttpServletRequest request) {
        Document doc = service.findDocumentById(id,collectionName);
//        Arrays.stream(fileCols.split(",")).forEach(f->{
//            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
//            List<Document> nowFile = doc.get(f,List.class);
//            //保存新的上传文件
//            nowFile.addAll(files);
//            //删除已删除的上传文件
//            String delFile = request.getParameter("del-"+f);
//            List<String> delFileIds = Arrays.asList(delFile.split(","));
//            doc.put(f,nowFile.stream().filter(item-> !delFileIds.contains(item.getString("id"))).collect(Collectors.toList()));
//        });
        doc.put("name", name);
        doc.put("checkDate", checkDate);
        doc.put("orgId", orgSelectId);
        doc.put("orgName", orgSelectName);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        doc.put("uploadFileArr", uploadFile);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
                                     String name,
                                     String orgSelectName,
         							 String orgSelectId,
                                     String auditStatus,
                                     String orgId,
                                     @RequestParam int start, @RequestParam int limit) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Date startDate = null;
        Date endDate = null;
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, name, startDate, endDate, orgId,auditStatus,orgSelectId));
        result.setRows(service.findAllDocument(collectionName, name, startDate, endDate, orgId, start, limit,auditStatus,orgSelectId));
        return result;
    }

}
