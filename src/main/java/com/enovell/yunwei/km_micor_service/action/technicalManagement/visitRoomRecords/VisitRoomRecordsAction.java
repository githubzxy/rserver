package com.enovell.yunwei.km_micor_service.action.technicalManagement.visitRoomRecords;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.VisitRoomRecordsGridDto;
import com.enovell.yunwei.km_micor_service.service.technical.visitRoomRecords.VisitRoomRecordsService;
import com.enovell.yunwei.km_micor_service.util.ActionUtils;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：VisitRoomRecordsAction   
 * 类描述:  进出机房人员信息登记
 * 创建人：zhouxingyu
 * 创建时间：2019年5月30日 下午2:36:33
 * 修改人：zhouxingyu 
 * 修改时间：2019年5月30日 下午2:36:33   
 */
@RestController
@RequestMapping("/visitRoomRecordsAction")
public class VisitRoomRecordsAction {
	
	@Resource(name = "visitRoomRecordsService")
	private VisitRoomRecordsService service;
	
	@PostMapping(value = "/addTechnical")
	public ResultMsg addTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "machineRoomDialog",required=false) String machineRoom,
			@RequestParam(name = "railLineDialog",required=false) String railLine,
			@RequestParam(name = "userId",required=false) String userId,
			@RequestParam(name = "date",required=false) String date,
			@RequestParam(name = "visitDate",required=false) String visitDate,
			@RequestParam(name = "visitName") String visitName,
			@RequestParam(name = "visitFrom") String visitFrom,
			@RequestParam(name = "jobContent",required=false) String jobContent,
			@RequestParam(name = "helper") String helper,
			@RequestParam(name = "leaveDate") String leaveDate,
			@RequestParam(name = "remark",required=false) String remark,
			@RequestParam(name = "collectionName",required=false) String collectionName
			) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
		Document document = new Document();
		String railLineId =  service.getLineDataId(railLine);
		String machineRoomId =  service.getmachineRoomDataId(machineRoom);
		document.put("machineRoom", machineRoom);
		document.put("machineRoomId", machineRoomId);
		document.put("railLine", railLine);
		document.put("railLineId", railLineId);
		document.put("date", date);
		document.put("visitDate", visitDate);
		document.put("visitName", visitName);
		document.put("visitFrom", visitFrom);
		document.put("helper", helper);
		document.put("jobContent", jobContent);
		document.put("leaveDate", leaveDate);
		document.put("remark", remark);
		document.put("userId", userId);
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
	 
	@PostMapping(value = "/updateTechnical")
	public ResultMsg updateTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "id",required=false) String id,
			@RequestParam(name = "machineRoom",required=false) String machineRoom,
			@RequestParam(name = "machineRoomEditDialog",required=false) String machineRoomEditDialog,
			@RequestParam(name = "railLine",required=false) String railLine,
			@RequestParam(name = "railLineEditDialog",required=false) String railLineEditDialog,
			@RequestParam(name = "userId",required=false) String userId,
			@RequestParam(name = "date",required=false) String date,
			@RequestParam(name = "visitDate",required=false) String visitDate,
			@RequestParam(name = "visitName") String visitName,
			@RequestParam(name = "visitFrom") String visitFrom,
			@RequestParam(name = "jobContent") String jobContent,
			@RequestParam(name = "helper") String helper,
			@RequestParam(name = "leaveDate") String leaveDate,
			@RequestParam(name = "remark",required=false) String remark,
			@RequestParam(name = "collectionName",required=false) String collectionName
			) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
		  Document document = service.findDatasById(id,collectionName);
		  document.put("railLine", railLineEditDialog);
		  String railLineId =  service.getLineDataId(railLineEditDialog);
		  document.put("railLineId", railLineId);
		  document.put("machineRoom", machineRoomEditDialog);
		  String machineRoomId =  service.getmachineRoomDataId(machineRoomEditDialog);
		  document.put("machineRoomId", machineRoomId);
		  document.put("updateUser", userId);
		  document.put("remark", remark);
		  document.put("date", date);
		  document.put("visitDate", visitDate);
		  document.put("visitName", visitName);
		  document.put("visitFrom", visitFrom);
		  document.put("helper", helper);
		  document.put("leaveDate", leaveDate);
		  document.put("jobContent", jobContent);
		  document.put("collectionName", collectionName);
		  Document res = service.updateDocument(document, collectionName);
		  return ResultMsg.getSuccess("修改成功", res);
	}

	/**
	 * 
	 * deleteTechnicalInfos 批量删除进出人员信息
	 * 
	 * @param ids
	 * @return ResultMsg
	 */
	@RequestMapping(value = "/delTechnical", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg deleteTechnicalInfos(@RequestParam("jsonIds") String jsonIds) {
		return null;
	}




	@RequestMapping(value = "/findAllDatas", method = RequestMethod.POST)
	@ResponseBody
	public GridDto<Document> getAllTechnicalInfoInfos(
			@RequestParam(value = "collectionName", required = false) String collectionName,
			@RequestParam(value = "userId", required = false) String userId,
			 String helper,
			String machineRoom,
			String railLine,
			 String createStartDate,
			String createEndDate,
			@RequestParam("start") int start, @RequestParam("limit") int limit) {
		GridDto<Document> result=new GridDto<>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
        String currentDay = "";
		currentDay = sdf.format(currentDate);
		result.setResults(service.findAllDocumentCount(collectionName,helper,machineRoom, railLine,createStartDate, createEndDate,currentDay,userId));
		result.setRows(service.findAllDocument(collectionName,helper,machineRoom, railLine,createStartDate, createEndDate, start, limit,currentDay,userId));
		return result;
	}

	/**
	 * 
	 * exportXls 导出Excel
	 * @param name
	 * @param depart
	 * @param technicalInfoType
	 * @param createDate
	 * @param request
	 * @param response
	 */
	@PostMapping(value = "/exportXls")
	public void exportXls(@RequestParam(name = "exportXlsJson",required = false) String exportXlsJson,
			HttpServletRequest request,
			HttpServletResponse response) {
		List<VisitRoomRecordsGridDto> dataList = new ArrayList<VisitRoomRecordsGridDto>();
		VisitRoomRecordsGridDto[] dtos = JsonUtil.jsonToJavaObj(exportXlsJson, VisitRoomRecordsGridDto[].class);
		Collections.addAll(dataList, dtos);
		String[] headerTableColumns = new String[]{ 
				"线别" + "_" +"30" + "_" + "my.getRailLine()",
				"机房" + "_" +"30" + "_" + "my.getMachineRoom()",
				"时间" + "_" +"25" + "_" + "my.getDate()",
				"进入时间" + "_" +"25" + "_" + "my.getVisitDate()",
				"进入人的人员姓名" + "_" +"40" + "_" + "my.getVisitName()",
				"单位" + "_" +"40" + "_" + "my.getVisitFrom()",
				"工作内容" + "_" +"60" + "_" + "my.getJobContent()",
				"接待人" + "_" +"30" + "_" + "my.getHelper()",
				"离开时间" + "_" +"25" + "_" + "my.getLeaveDate()",
				"备注" + "_" +"30" + "_" + "my.getRemark()",
		}; 
		PoiExport<VisitRoomRecordsGridDto> export = new PoiExport<VisitRoomRecordsGridDto>();
		export.exportXls("进出机房人员信息登记表",headerTableColumns, new HashMap(), dataList, request, response);
	}
	
	 @PostMapping("/getMachineRooms")
	 public List<String> getLines(@RequestParam(name = "railLine")String railLine){
		 return service.getMachineRooms(railLine);
	 }
	 
	 private boolean checkAdress(HttpServletRequest request){
		 String ip = ActionUtils.getIpAddr(request);
		 if(ip.indexOf("0:0")!=-1){
			 return true;
		 }
		 if(ip.indexOf("192.168")!=-1){
			 return true;
		 }
		 if(ip.indexOf("10.222")!=-1){
			 return true;
		 }
		 if(ip.indexOf("172.23")!=-1){
			 return true;
		 }
		 return false;
		 
	 }
	
}
