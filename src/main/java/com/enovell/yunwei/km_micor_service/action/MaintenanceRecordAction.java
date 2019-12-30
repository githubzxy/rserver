package com.enovell.yunwei.km_micor_service.action;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.CommonPageService;
import com.enovell.yunwei.km_micor_service.service.UserService;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * kunmingTXD
 * 昆明微服务---检修记录Action
 *
 * @author liwt
 * @date 18-12-12
 */
@RestController
@RequestMapping("/maintenanceRecordAction")
public class MaintenanceRecordAction {
    @Resource(name = "commonPageService")
    private CommonPageService service;
    @Resource(name = "userService")
    private UserService userService;
    
    @RequestMapping(value="/getTime",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg getTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time=sdf.format(date);
		return ResultMsg.getSuccess("时间返回成功", time);
	}
    
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
                            @RequestParam(name = "createDate") String createDate,
                            @RequestParam(name = "collectionName") String collectionName,
                            @RequestParam(name = "userId") String userId,
                            HttpServletRequest request) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Document document = new Document();
        Arrays.stream(fileCols.split(",")).forEach(f -> {
            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
            document.put(f, files);
        });
        document.put("name", name);
        try {
			document.put("createDate", sdf.parse(createDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        document.put("createUserId", userId);
        Map<String, Object> user = userService.getUserById(userId);
        document.put("createUserName", user.get("USER_NAME_"));
        document.put("orgId", orgSelectId);
        document.put("orgName", orgSelectName);
        //默认添加设置状态为待审批 状态为 "0"
        document.put("auditStatus", 0);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }

    @PostMapping("/updateDoc")
    public ResultMsg updateDoc(@RequestParam("id") String id,
    							@RequestParam(name = "name") String name,
    							@RequestParam(name = "orgSelectName") String orgSelectName,
    							@RequestParam(name = "createDate") String createDate,
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
        doc.put("createDate", createDate);
        //默认添加设置状态为待审批 状态为 "0"
        doc.put("auditStatus", 0);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
}
