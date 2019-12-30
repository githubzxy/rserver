package com.enovell.yunwei.km_micor_service.action.productionManage.circuitWorkOrderManage;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage.CircuitWorkOrderApplyService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/circuitWorkOrderApplyAction")
public class CircuitWorkOrderApplyAction {
	@Resource(name = "circuitWorkOrderApplyService")
    private CircuitWorkOrderApplyService service;
    @Resource(name = "userService")
    private UserService userService;
    
    /**
     * 获取Oracle数据库车间数据集合
     * @return List<Map<String, String>>
     */
    @PostMapping("/getWorkShop")
	 public List<Map<String, Object>> getWorkShop(){
		return service.getWorkShops();
	 }
    
    /**
     * getSystemDate 获取当前系统时间
     * @return String
     */
    @PostMapping("/getSystemDate")
    public String getSystemDate() {
        return JsonUtil.getSystemDate();
    }
    /**
     * 电路工单新增
     * @param workOrderName
     * @param executiveStaffId
     * @param executiveStaff
     * @param systemType
     * @param workOrderType
     * @param remark
     * @param userId
     * @param userName
     * @param parentId
     * @param flowState
     * @param uploadFileArr
     * @param collectionName
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(
    						@RequestParam(name = "workOrderName") String workOrderName,
    						String receivePeopleId,
    						String receivePeople,
    						@RequestParam(name = "executiveStaffId") String executiveStaffId,
    						@RequestParam(name = "executiveStaff") String executiveStaff,
    						@RequestParam(name = "systemType") String systemType,
    						@RequestParam(name = "workOrderType") String workOrderType,
    						@RequestParam(name = "remark") String remark,
    						@RequestParam(name = "userId") String userId,
                            @RequestParam(name = "userName") String userName,
                            @RequestParam(name = "parentId") String parentId,
                            @RequestParam(name = "flowState") String flowState,
                            @RequestParam(name = "uploadFileArr") String uploadFileArr,
                            @RequestParam(name = "collectionName") String collectionName,
                            HttpServletRequest request) {
        Document document = new Document();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateStr = "";
        currentDateStr = sdf.format(currentDate);
        
        document.put("createDate", currentDateStr);
        document.put("workOrderName", workOrderName);
        if(StringUtils.isNotBlank(receivePeople)){
        	document.put("receivePeopleId", receivePeopleId);
        	document.put("receivePeople", receivePeople);
        }else{
        	document.put("receivePeopleId", "");
        	document.put("receivePeople", "");
        }
        document.put("executiveStaffId", executiveStaffId);
        document.put("executiveStaff", executiveStaff);
        document.put("systemType", systemType);
        document.put("workOrderType", workOrderType);
        document.put("remark", remark);
        document.put("createUserId", userId);
        document.put("createUserName", userName);
//        document.put("parentId", parentId);
        document.put("flowState", flowState);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
//        document.put("summaryDate", "");//汇总日期
//        document.put("summaryPersonId", "");//汇总人ID
//        document.put("summaryPersonName", "");//汇总人名称
//        if("1".equals(flowState)){
//        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
//        	String currentDay = "";
//        	try {
//        		currentDay = sdfDay.format(new Date());
//        	} catch (Exception e) {
//        		e.printStackTrace();
//        	}
//        	document.put("applyDate", currentDay);
//        }else{
//        	document.put("applyDate", "");
//        }
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
    /**
     * 主页查询 
     * @param collectionName 查询表名
     * @param userId 登录用户ID
     * @param constructionProject 施工项目
     * @param flowState 流程状态
     * @param startUploadDate 
     * @param endUploadDate
     * @param start 0
     * @param limit 20
     * @return GridDto<Document>
     */
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
									 @RequestParam String userId,
									 String workOrderName,
									 String systemType,
									 String flowState,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String currentDay = "";
        GridDto<Document> result = new GridDto<>();
        currentDay = sdfDay.format(currentDate);
        result.setResults(service.findAllDocumentCount(collectionName, userId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, userId, currentDay, workOrderName, systemType, flowState, startUploadDate, endUploadDate, start, limit));
        return result;
    }
    /**
     * 修改草稿状态的数据
     * @param id
     * @param workOrderName
     * @param executiveStaff
     * @param executiveStaffId
     * @param systemType
     * @param workOrderType
     * @param remark
     * @param flowState
     * @param uploadFileArr
     * @param collectionName
     * @param approveAdvice
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/modifyDoc")
    public ResultMsg modifyDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "workOrderName") String workOrderName,
            String receivePeople,
            String receivePeopleId,
            @RequestParam(name = "executiveStaffId") String executiveStaffId,
			@RequestParam(name = "executiveStaff") String executiveStaff,
            @RequestParam(name = "systemType") String systemType,
            @RequestParam(name = "workOrderType") String workOrderType,
            @RequestParam(name = "remark") String remark,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "uploadFileArr") String uploadFileArr,
            @RequestParam(name = "collectionName") String collectionName,
            HttpServletRequest request) {
        Document document = service.findDocumentById(id,collectionName);
        document.put("workOrderName", workOrderName);
        if(StringUtils.isNotBlank(receivePeople)){
        	document.put("receivePeopleId", receivePeopleId);
        	document.put("receivePeople", receivePeople);
        }else{
        	document.put("receivePeopleId", "");
        	document.put("receivePeople", "");
        }
        document.put("executiveStaffId", executiveStaffId);
        document.put("executiveStaff", executiveStaff);
        document.put("systemType", systemType);
        document.put("workOrderType", workOrderType);
        document.put("remark", remark);
        document.put("flowState", flowState);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
//    	document.put("approverId", "");
//    	document.put("approver", "");
//    	document.put("approveDate", "");
//        document.put("approveAdvice", "");
//        if("1".equals(flowState)){
//        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
//       	 	String currentDay = "";
//            try {
//                currentDay = sdfDay.format(new Date());
//            } catch (Exception e) {
//            	e.printStackTrace();
//            }
//            document.put("applyDate", currentDay);
//        }
        Document res = service.modifyDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    @PostMapping("/remodifyDoc")
    public ResultMsg remodifyDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "workOrderName") String workOrderName,
    		String receivePeople,
            String receivePeopleId,
    		@RequestParam(name = "executiveStaff") String executiveStaff,
    		@RequestParam(name = "executiveStaffId") String executiveStaffId,
    		@RequestParam(name = "systemType") String systemType,
    		@RequestParam(name = "workOrderType") String workOrderType,
    		@RequestParam(name = "remark") String remark,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "uploadFileArr") String uploadFileArr,
    		@RequestParam(name = "collectionName") String collectionName,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
    	document.put("workOrderName", workOrderName);
    	 if(StringUtils.isNotBlank(receivePeople)){
         	document.put("receivePeopleId", receivePeopleId);
         	document.put("receivePeople", receivePeople);
         }else{
         	document.put("receivePeopleId", "");
         	document.put("receivePeople", "");
         }
    	document.put("executiveStaff", executiveStaff);
    	document.put("executiveStaffId", executiveStaffId);
    	document.put("systemType", systemType);
    	document.put("workOrderType", workOrderType);
    	document.put("remark", remark);
    	document.put("flowState", flowState);
    	DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
    	document.put("uploadFileArr", uploadFile);
    	document.put("checkUserId", "");
    	document.put("checkUserName", "");
    	document.put("checkTime", "");
        document.put("checkAdvice", "");
//        document.put("approveUserId", "");
//        document.put("approveUserName", "");
//        document.put("approveTime", "");
//        document.put("approveAdvice", "");
//        if("1".equals(flowState)){
//        	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
//       	 	String currentDay = "";
//            try {
//                currentDay = sdfDay.format(new Date());
//            } catch (Exception e) {
//            	e.printStackTrace();
//            }
//            document.put("applyDate", currentDay);
//        }
    	Document res = service.modifyDocument(document, collectionName);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    /**
     * 修改待申请的数据但不提交审核只更新数据
     * @param id
     * @param workOrderName
     * @param executiveStaff
     * @param executiveStaffId
     * @param systemType
     * @param workOrderType
     * @param remark
     * @param collectionName
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "workOrderName") String workOrderName,
    		@RequestParam(name = "executiveStaff") String executiveStaff,
    		@RequestParam(name = "executiveStaffId") String executiveStaffId,
    		@RequestParam(name = "systemType") String systemType,
    		@RequestParam(name = "workOrderType") String workOrderType,
    		@RequestParam(name = "remark") String remark,
    		@RequestParam(name = "collectionName") String collectionName,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
    	document.put("workOrderName", workOrderName);
    	document.put("executiveStaff", executiveStaff);
    	document.put("executiveStaffId", executiveStaffId);
    	document.put("systemType", systemType);
    	document.put("workOrderType", workOrderType);
    	document.put("remark", remark);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("保存成功", res);
    }
    /**
     * 提交电路工单到技术科科长审核
     * @param id
     * @param workOrderName
     * @param executiveStaff
     * @param executiveStaffId
     * @param systemType
     * @param workOrderType
     * @param remark
     * @param collectionName
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/submitDoc")
    public ResultMsg submitDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "workOrderName") String workOrderName,
    		@RequestParam(name = "executiveStaff") String executiveStaff,
    		@RequestParam(name = "executiveStaffId") String executiveStaffId,
    		@RequestParam(name = "systemType") String systemType,
    		@RequestParam(name = "workOrderType") String workOrderType,
    		@RequestParam(name = "remark") String remark,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
    	document.put("workOrderName", workOrderName);
    	document.put("executiveStaff", executiveStaff);
    	document.put("executiveStaffId", executiveStaffId);
    	document.put("systemType", systemType);
    	document.put("workOrderType", workOrderType);
    	document.put("remark", remark);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("提交成功", res);
    }
    
    @PostMapping("/updateReplyDoc")
    public ResultMsg updateReplyDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "workareaReply") String workareaReply,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		String auditAdvice,
    		String approveAdvice,
    		HttpServletRequest request) {
    	Document document = service.findDocumentById(id,collectionName);
//    	if("1".equals(checkSituation)){
//        	document.put("workareaFinish", "已完成："+situationRemark);
//        }
//        if("0".equals(checkSituation)){
//        	document.put("workareaFinish", "未完成："+situationRemark);
//        }
    	document.put("workareaReply", workareaReply);
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("操作成功", res);
    }
    
}