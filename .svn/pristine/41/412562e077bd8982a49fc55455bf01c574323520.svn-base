package com.enovell.yunwei.km_micor_service.action.productionManage.maintenanceMemorendun;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ConstructRepairDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.MaintenanceMemorendunDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.maintenanceMemorendun.MaintenanceMemorendunService;
import com.enovell.yunwei.km_micor_service.util.ReadExcel;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：MaintenanceMemorendunAction   
 * 类描述:  生产管理-维修工作备忘录
 * 创建人：zhouxingyu
 * 创建时间：2019年3月25日 下午3:44:24
 * 修改人：zhouxingyu 
 * 修改时间：2019年3月25日 下午3:44:24   
 *
 */
@RestController
@RequestMapping("/maintenanceMemorendunAction")
public class MaintenanceMemorendunAction {
    @Resource(name = "maintenanceMemorendunService")
    private MaintenanceMemorendunService service;
    @Resource(name = "userService")
    private UserService userService;
    /**
     * 
     * addDoc 新增维修工作记录
     * @return
     */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(
        @RequestParam(name = "checkDate") String checkDate,
        @RequestParam(name = "checker") String checker,
        @RequestParam(name = "problemFrom") String problemFrom,
        @RequestParam(name = "dutyDepartment") String dutyDepartment,
        @RequestParam(name = "dutyDepartmentId") String dutyDepartmentId,
//        @RequestParam(name = "helpDepartment") String helpDepartment,
        @RequestParam(name = "endDate") String endDate,
        @RequestParam(name = "problemInfo") String problemInfo,
        @RequestParam(name = "changeInfo") String changeInfo,
        @RequestParam(name = "result") String result,
        @RequestParam(name = "note") String note,
        @RequestParam(name = "collectionName") String collectionName,
        @RequestParam(name = "userId") String userId,
        HttpServletRequest request,HttpSession session) {
        Document document = new Document();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = format.format(checkDate);
        document.put("checkDate", checkDate);
        document.put("checker", checker);
        document.put("problemFrom", problemFrom);
        document.put("dutyDepartment", dutyDepartment);
        document.put("dutyDepartmentId", dutyDepartmentId);
//        document.put("helpDepartment", helpDepartment);
        document.put("endDate", endDate);
        document.put("problemInfo", problemInfo);
        document.put("changeInfo", changeInfo);
        document.put("result", result);
        document.put("note", note);
        document.put("userId", userId);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("creatDateStr", creatDateStr);
        
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }
    /** 
     * updateDoc 更新记录
     * @return
     */
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc() {
        return ResultMsg.getSuccess("修改成功", "");

    }


    /**
     * 
     * findDocById 根据ID查询记录
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
     * 
     * findAll 根据条件查询记录
     * @param collectionName
     * @param userId
     * @param startUploadDate
     * @param endUploadDate
     * @param start
     * @param limit
     * @return
     */
    @PostMapping("/findAll")
    public GridDto < Document > findAll(@RequestParam String collectionName,
        String name,
        String checkDate,
        String endDate,
        String auditStatus,
        String dutyDepartment,
        String orgId,
        @RequestParam int start, @RequestParam int limit) {
        GridDto < Document > result = new GridDto < > ();
    	if (!service.checkData(result,checkDate,endDate)) return result; 
        result.setResults(service.findAllDocumentCount(collectionName, name, checkDate, endDate, orgId, auditStatus, dutyDepartment));
        result.setRows(service.findAllDocument(collectionName, name, checkDate, endDate, orgId, start, limit, auditStatus, dutyDepartment));
        return result;
    }


    /**
     * 
     * modifyDoc 修改添加
     * @param id
     * @param checkDate
     * @param checker
     * @param problemFrom
     * @param dutyDepartment
     * @param helpDepartment
     * @param endDate
     * @param problemInfo
     * @param changeInfo
     * @param result
     * @param note
     * @param collectionName
     * @param request
     * @return
     */
    @PostMapping("/modifyDoc")
    public ResultMsg modifyDoc(
        @RequestParam("id") String id,
        @RequestParam(name = "checkDate") String checkDate,
        @RequestParam(name = "checker") String checker,
        @RequestParam(name = "problemFrom") String problemFrom,
        @RequestParam(name = "dutyDepartment") String dutyDepartment,
        @RequestParam(name = "dutyDepartmentId") String dutyDepartmentId,
//        @RequestParam(name = "helpDepartment") String helpDepartment,
        @RequestParam(name = "endDate") String endDate,
        @RequestParam(name = "problemInfo") String problemInfo,
        @RequestParam(name = "changeInfo") String changeInfo,
        @RequestParam(name = "result") String result,
        @RequestParam(name = "note") String note,
        @RequestParam(name = "collectionName") String collectionName,
        HttpServletRequest request) {
        Document document = service.findDocumentById(id, collectionName);
        document.put("checkDate", checkDate);
        document.put("checker", checker);
        document.put("problemFrom", problemFrom);
        document.put("dutyDepartment", dutyDepartment);
        document.put("dutyDepartmentId", dutyDepartmentId);
//        document.put("helpDepartment", helpDepartment);
        document.put("endDate", endDate);
        document.put("problemInfo", problemInfo);
        document.put("changeInfo", changeInfo);
        document.put("result", result);
        document.put("note", note);
        document.put("status", 1);
        Document res = service.modifyDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    	
	//导入维修工作备忘录Excel
    @PostMapping("/Import")
    @ResponseBody
    public ResultMsg wxImport(MaintenanceMemorendunDto dto,
        @RequestParam(name = "file_excel", required = false) MultipartFile file,
        @RequestParam(name = "userId", required = false) String userId,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        try {
            List < MaintenanceMemorendunDto > crdList = null;
            crdList = service.getExcelInfo(file);
//            List<MultipartFile> MultipartFiles = new ArrayList<MultipartFile>();
//            List < Document > files = service.uploadFile(MultipartFiles);
            crdList.stream().forEach(s -> {
                String collectionName = "maintenanceMemorendun";
                Document document = new Document();
//                document.put("files", files);
                document.put("checkDate", s.getCheckDate());
                document.put("checker", s.getChecker());
                document.put("problemFrom", s.getProblemFrom());
                document.put("dutyDepartment", s.getDutyDepartment());
//                document.put("helpDepartment", s.getHelpDepartment());
                document.put("endDate", s.getEndDate());
                document.put("problemInfo", s.getProblemInfo());
                document.put("changeInfo", s.getChangeInfo());
                document.put("result", s.getResult());
                document.put("note", s.getNote());
                document.put("userId", userId);
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		String creatDateStr = "";
	    		try {
	    			creatDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("creatDateStr", creatDateStr);
                
                if (s.getCheckDate() != null && s.getDutyDepartment() != null && s.getEndDate() != null) {
                    Document res = service.addDocument(document, collectionName);
                }
            });
//            response.sendRedirect("../kmms/page/maintenanceMemorendunPage?userId=" + userId);
            return ResultMsg.getSuccess("导入成功");
        } catch (Exception e) {
//            response.sendRedirect("../kmms/page/maintenanceMemorendunPage?userId=" + userId);
            return ResultMsg.getFailure("导入失败");
        }

    }


}