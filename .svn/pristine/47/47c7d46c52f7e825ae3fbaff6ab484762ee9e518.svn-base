package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.DuanSafetyNotificationService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

@RestController
@RequestMapping("/duanSafetyNotificationAction")
public class DuanSafetyNotificationAction {
	@Resource(name = "duanSafetyNotificationService")
    private DuanSafetyNotificationService service;
    @Resource(name = "userService")
    private UserService userService;
    /**
     * getSystemDate 获取当前系统时间
     * @return String
     */
    @PostMapping("/getSystemDate")
    public String getSystemDate() {
        return JsonUtil.getSystemDate();
    }
    /**
     * 新增数据
     *
     * @param noticeDateStr  新增时间
     * @param orgSelectName 反馈部门
     * @param orgUserName 反馈人
     * @param noticeContent 内容及处理情况
     * @param remark 备注
     * @param collectionName 存储表名
     * @return 保存后的数据对象
     */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(@RequestParam(name = "noticeDateStr") String noticeDateStr,
                            @RequestParam(name = "noticeContent") String noticeContent,
                            @RequestParam(name = "collectionName") String collectionName,
//                            @RequestParam(name = "userId") String userId,
                            HttpServletRequest request) {
        Document document = new Document();
        document.put("noticeDateStr", noticeDateStr);
        document.put("noticeContent", noticeContent);
        document.put("summaryDate", "");//汇总日期
        document.put("summaryPersonId", "");//汇总人ID
        document.put("summaryPersonName", "");//汇总人名称
//        Map<String, Object> org = userService.getOrgbyUserId(userId);
//        document.put("createOrg", org.get("ORG_ID_"));
//        document.put("createOrgName", org.get("ORG_NAME_"));
//        Map<String, Object> user = userService.getUserById(userId);
//        document.put("createUserName", user.get("USER_NAME_"));
//        document.put("orgId", orgSelectId);
//        document.put("orgName", orgSelectName);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
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
                                     String startUploadDate,
                                     String endUploadDate,
                                     String noticeContent,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        Date startDate = null;
        Date endDate = null;
        GridDto<Document> result = new GridDto<>();
        try {
            if (StringUtils.isNotBlank(startUploadDate)) {
                startDate = sdf.parse(startUploadDate);
            }
            if (StringUtils.isNotBlank(endUploadDate)) {
                endDate = sdf.parse(endUploadDate);
            }
            currentDay = sdfDay.format(currentDate);
        } catch (ParseException e) {
           result.setHasError(true);
           result.setError("时间格式错误");
           return result;
        }


        result.setResults(service.findAllDocumentCount(collectionName, userId, currentDay, startUploadDate, endUploadDate,noticeContent));
        result.setRows(service.findAllDocument(collectionName, userId, currentDay, startUploadDate, endUploadDate,noticeContent, start, limit));
        return result;
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
                               @RequestParam("collectionName") String collectionName) {
        List<String> ids = Arrays.asList(id.split(","));
        service.removeDocument(ids, collectionName);
        return ResultMsg.getSuccess("删除成功");
    }
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
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( @RequestParam("id") String id,
    		  					@RequestParam(name = "noticeDateStr") String noticeDateStr,
    							@RequestParam(name = "noticeContent") String noticeContent,
    							@RequestParam(name = "collectionName") String collectionName,
                               HttpServletRequest request) {
        Document doc = service.findDocumentById(id,collectionName);
        doc.put("noticeDateStr", noticeDateStr);
        doc.put("noticeContent", noticeContent);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }

}
