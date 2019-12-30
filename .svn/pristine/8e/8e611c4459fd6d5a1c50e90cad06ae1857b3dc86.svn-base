package com.enovell.yunwei.km_micor_service.action.emergencyManage.skitsRescueCompletion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.action.technicalManagement.technicalDocument.PoiExport;
import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoDocumentDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoGridDto;
import com.enovell.yunwei.km_micor_service.service.emergencyManage.exerciseScheme.ExerciseSchemeService;
import com.enovell.yunwei.km_micor_service.service.emergencyManage.skitsRescueCompletion.SkitsRescueCompletionService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：TechnicalDocumentAction   
 * 类描述:  机房技术资料及台账管理
 * 创建人：zhouxingyu
 * 创建时间：2019年3月27日 下午2:36:33
 * 修改人：zhouxingyu 
 * 修改时间：2019年3月27日 下午2:36:33   
 *
 */
@RestController
@RequestMapping("/skitsRescueCompletionAction")
public class SkitsRescueCompletionAction {

	@Resource(name = "skitsRescueCompletionService")
	private SkitsRescueCompletionService service;
	/**
	 * 
	 * addTechnicalInfo 添加技术资料
	 * 
	 * @param request
	 * @param address
	 *            施工地点
	 * @param remark
	 *            备注
	 * @param uploadFileArr
	 *            上传文件
	 * @return ResultMsg
	 */
	@PostMapping(value = "/addDoc")
	public ResultMsg addTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "date",required=false) String date,
			@RequestParam(name = "line",required=false) String line,
			@RequestParam(name = "site",required=false) String site,
			@RequestParam(name = "orgDepart",required=false) String orgDepart,
			@RequestParam(name = "organizer",required=false) String organizer,
			@RequestParam(name = "content",required=false) String content,
			@RequestParam(name = "joinDepart",required=false) String joinDepart,
			@RequestParam(name = "publicCarCount",required=false) String publicCarCount,
			@RequestParam(name = "rentCarCount",required=false) String rentCarCount,
			@RequestParam(name = "userId",required=false) String userId,
			@RequestParam(name = "orgId",required=false) String orgId,
			@RequestParam(name = "collectionName",required=false) String collectionName,
			@RequestParam(name = "uploadFile",required=false) String uploadFile) {
		
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBObject file =(DBObject) JSON.parse(uploadFile);
		document.put("creatDate", creatDateStr);
		document.put("date", date);
		document.put("line", line);
		document.put("site", site);
		document.put("orgDepart", orgDepart);
		document.put("organizer", organizer);
		document.put("content", content);
		document.put("joinDepart", joinDepart);
		document.put("publicCarCount", publicCarCount);
		document.put("rentCarCount", rentCarCount);
		document.put("userId", userId);
		document.put("orgId", orgId);
		document.put("uploadFile", file);
		Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);

	}
	/**
	 * 
	 * findById 根据id查询数据
	 * @param id
	 * @param collectionName
	 * @return
	 */
	 @PostMapping("/findById")
	 public Document findById(String id,String collectionName){
		 return service.findDatasById(id,collectionName);
	 }

	@PostMapping(value = "/updateDoc")
	public ResultMsg updateTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "id",required=false) String id,
			@RequestParam(name = "date",required=false) String date,
			@RequestParam(name = "line",required=false) String line,
			@RequestParam(name = "site",required=false) String site,
			@RequestParam(name = "orgDepart",required=false) String orgDepart,
			@RequestParam(name = "organizer",required=false) String organizer,
			@RequestParam(name = "content",required=false) String content,
			@RequestParam(name = "joinDepart",required=false) String joinDepart,
			@RequestParam(name = "publicCarCount",required=false) String publicCarCount,
			@RequestParam(name = "rentCarCount",required=false) String rentCarCount,
			@RequestParam(name = "collectionName",required=false) String collectionName,
			@RequestParam(name = "uploadFile",required=false) String uploadFile) {
		  Document document = service.findDatasById(id,collectionName);
		  DBObject file =(DBObject) JSON.parse(uploadFile);
		    document.put("date", date);
			document.put("line", line);
			document.put("site", site);
			document.put("orgDepart", orgDepart);
			document.put("organizer", organizer);
			document.put("content", content);
			document.put("joinDepart", joinDepart);
			document.put("publicCarCount", publicCarCount);
			document.put("rentCarCount", rentCarCount);		  document.put("collectionName", collectionName);
		  document.put("uploadFile", file);
		  Document res = service.updateDocument(document, collectionName);
		  return ResultMsg.getSuccess("修改成功", res);
	}

	/**
	 * 
	 * getAllTechnicalInfoInfos 查询技术资料
	 * 
	 * @param depart
	 *            所属部门
	 * @param name
	 *            资料名称
	 * @param technicalInfoType
	 *            资料类别
	 * @param createStartDate
	 *            创建开始时间
	 * @param createEndDate
	 *            创建结束时间
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/findAllDatas", method = RequestMethod.POST)
	@ResponseBody
	public GridDto<Document> getAllTechnicalInfoInfos(
			@RequestParam(value = "collectionName", required = false) String collectionName,
			@RequestParam(value = "userId", required = false) String userId,
			String line,
			String orgDepart,
			String joinDepart,
			String createStartDate,
			String createEndDate,
			@RequestParam("start") int start, @RequestParam("limit") int limit) {
		GridDto<Document> result=new GridDto<>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
        String currentDay = "";
		currentDay = sdf.format(currentDate);
		result.setResults(service.findAllDocumentCount(collectionName,line,orgDepart,joinDepart,createStartDate, createEndDate,currentDay,userId));
		result.setRows(service.findAllDocument(collectionName,line,orgDepart,joinDepart,createStartDate, createEndDate, start, limit,currentDay,userId));
		return result;
	}


	
}
