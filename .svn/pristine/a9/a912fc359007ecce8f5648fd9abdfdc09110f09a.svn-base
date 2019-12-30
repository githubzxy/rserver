package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.DayToJobService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.DayToJobSumService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;



@RestController
@RequestMapping("/dayToJobAction")
public class DayToJobAction {
	@Resource(name="dayToJobService")
	private DayToJobService service;
	@Resource(name="dayToJobSumService")
	private DayToJobSumService dayToJobSumService;
	
	 /**
     * getSystemDate 获取当前系统时间前一天的日期（字符串）
     * @return String
     */
    @PostMapping("/getSystemBeforeDate")
    public String getSystemDate() {
        return JsonUtil.getSystemBeforeDate();
    }
	
	@PostMapping("/findAllDatas")
	public GridDto<Document> findAllDatas(@RequestParam String collectionName,
			String startUploadDate,
			String endUploadDate,
			@RequestParam int start,
			@RequestParam int limit){
		GridDto<Document> result=new GridDto<>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=null;
		Date endDate=null;
		if(StringUtils.isNotBlank(startUploadDate)){
			try {
				startDate=sdf.parse(startUploadDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endUploadDate)){
			try {
				endDate=sdf.parse(endUploadDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		result.setResults(service.findAllDocumentCount(collectionName, startUploadDate, endUploadDate));
		result.setRows(service.quryAllDatas(collectionName, startUploadDate, endUploadDate, start, limit));
		return result;
		
	}
	@PostMapping("/addData")
	public ResultMsg addDoc(
	    						@RequestParam(name = "date") String date,
	    						@RequestParam(name = "dispatch") String dispatch,
	    						@RequestParam(name = "leader") String leader,
	                            @RequestParam(name = "cadre") String cadre,
	                            @RequestParam(name = "userId") String userId,
	                            @RequestParam(name = "collectionName") String collectionName,
	                            HttpServletRequest request) {
	        Document document = new Document();
//	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//	        try {
//				document.put("date", sdf.parse(date));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
	        document.put("date", date);
	        document.put("dispatch", dispatch);
	        document.put("leader", leader);
	        document.put("cadre", cadre);
	        document.put("userId", userId);
	        document.put("createDate", new Date());
	        Document res = service.addDocument(document, collectionName);
	        return ResultMsg.getSuccess("新增成功", res);
	}
	
	 /**
     * 根据主键ID查询对应数据
     * @param id
     * @param collectionName
     * @return ResultMsg
     */
    @PostMapping("/findById")
    public ResultMsg findDocById(
    							@RequestParam("id") String id,
    							@RequestParam("collectionName") String collectionName) {
        Document result = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", result);
    }
    /**
     * 根据主键ID查询对应数据
     * @param id
     * @param collectionName
     * @return ResultMsg
     */
    @PostMapping("/getDataByBeforeDate")
    public ResultMsg getDataByBeforeDate(
    		@RequestParam("systemBeforeDate") String systemBeforeDate,
    		@RequestParam("collectionName") String collectionName) {
    	Document result = service.findDocumentByBeforeDate(systemBeforeDate, collectionName);
    	return ResultMsg.getSuccess("查询完成", result);
    }
    
    /**
     * 修改一条记录的内容
     * @param id
     * @param createDate
     * @param type
     * @param backOrgName
     * @param backOrgId
     * @param backPerson
     * @param infoResult
     * @param lost
     * @param detail
     * @param remark
     * @param collectionName
     * @param request
     * @return
     */
    @PostMapping("/updateDoc")
    public ResultMsg updateDoc(
    							@RequestParam("id") String id,
    							@RequestParam(name = "date") String date,
    							@RequestParam(name = "dispatch") String dispatch,
    							@RequestParam(name = "leader") String leader,
    							@RequestParam(name = "cadre") String cadre,
                                @RequestParam("collectionName") String collectionName,
                                HttpServletRequest request) {
        Document doc = service.findDocumentById(id,collectionName);
        doc.put("date", date);
        doc.put("dispatch", dispatch);
        doc.put("leader", leader);
        doc.put("cadre", cadre);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }
    /**
     * 保存汇总信息
     */
    @PostMapping("/saveSumData")
    public void saveSumData(Map<String, List<Document>> map,String userId,String userName){
    	dayToJobSumService.saveSumInfo(map, userId, userName);
    }
    
    @RequestMapping(value="/generateSumTable",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg generateSumTable(
			@RequestParam("collectionName")String collectionName,
			@RequestParam("docId")String docId,
			@RequestParam("userName")String userName,
			@RequestParam("userId")String userId,
			@RequestParam("leader")String leader,
			@RequestParam("cadre")String cadre,
			@RequestParam("date")String date,
			@RequestParam("dispatch")String dispatch) {
		 try {
				return ResultMsg.getSuccess(service.createGenerateSumTable(collectionName,docId,userName,userId,leader,cadre,date,dispatch));
			} catch (Exception e) {
				e.printStackTrace();
				return ResultMsg.getFailure("查看失败！");
			}
	}

}