package com.enovell.yunwei.km_micor_service.action.productionManage.circuitWorkOrderManage;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.enovell.yunwei.km_micor_service.service.productionManage.circuitWorkOrderManage.CircuitWorkOrderNetworkCenterService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/circuitWorkOrderNetworkCenterAction")
public class CircuitWorkOrderNetworkCenterAction {
	@Resource(name = "circuitWorkOrderNetworkCenterService")
    private CircuitWorkOrderNetworkCenterService service;
    @Resource(name = "userService")
    private UserService userService;
    
    /**
     * 获取Oracle数据库车间数据集合
     * @return List<Map<String, String>>
     */
//    @PostMapping("/getWorkShop")
//	 public List<Map<String, String>> getWorkShop(){
//    	List<Map<String, Object>> dataList = service.getWorkShops();
//    	List<Map<String, String>> workShopList=new ArrayList<Map<String, String>>();
//		 for (Map<String, Object> dataListMap : dataList) {
//			 Map<String,String> map=new HashMap<String, String>();
//			 map.put("text", dataListMap.get("text").toString());
//			 map.put("value", dataListMap.get("value").toString());
//			 workShopList.add(map);
//		}
//		return workShopList;
//	 }
    
//    @PostMapping("/getWorkShop")
//	 public List<Map<String, Object>> getWorkShop(){
//		return service.getWorkShops();
//	 }
    
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
									 String flowState,
									 String systemType,
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
     * 网管中心编辑完附件发送给技术科提交申请
     * @param id 获取对应数据的依据
     * @param flowState 流程状态
     * @param collectionName 存储表名
     * @param request
     * @return ResultMsg
     */
    @PostMapping("/sendDoc")
    public ResultMsg sendDoc( 
    		@RequestParam("id") String id,
            @RequestParam(name = "flowState") String flowState,
            @RequestParam(name = "uploadFileArr") String uploadFileArr,
            @RequestParam(name = "collectionName") String collectionName,
            HttpServletRequest request) {
        Document document = service.findDocumentById(id,collectionName);
        document.put("flowState", flowState);
        DBObject uploadFile =(DBObject) JSON.parse(uploadFileArr);
        document.put("uploadFileArr", uploadFile);
        Document res = service.sendDocument(document, collectionName);
        return ResultMsg.getSuccess("发送成功", res);
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
    
    @PostMapping("/receiveDoc")
    public ResultMsg receiveDoc( 
    		@RequestParam("id") String id,
    		@RequestParam(name = "flowState") String flowState,
    		@RequestParam(name = "collectionName") String collectionName,
    		HttpServletRequest request) {
    	
    	Document document = service.findDocumentById(id,collectionName);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateStr = "";
        currentDateStr = sdf.format(currentDate);
        
        document.put("receiveTime", currentDateStr);
    	
    	document.put("flowState", flowState);
    	Document res = service.updateDocument(document, collectionName);
    	return ResultMsg.getSuccess("签收成功", res);
    }
    
}