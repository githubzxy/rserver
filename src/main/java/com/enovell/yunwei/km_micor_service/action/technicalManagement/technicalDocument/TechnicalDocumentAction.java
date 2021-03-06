package com.enovell.yunwei.km_micor_service.action.technicalManagement.technicalDocument;

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

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoDocumentDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.TechnicalInfoGridDto;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.NetworkManageInfoService;
import com.enovell.yunwei.km_micor_service.service.technical.technicalDocumentManagement.TechnicalDocumentManageService;
import com.enovell.yunwei.km_micor_service.util.CommonPoiExportExcel;
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
@RequestMapping("/technicalDocumentAction")
public class TechnicalDocumentAction {

	@Resource(name = "TechnicalDocumentInfoService")
	private TechnicalDocumentManageService service;
	@Resource(name = "networkManageInfoService")
	private NetworkManageInfoService networkManageInfoService;
	/**
	 * 
	 * addTechnicalInfo 添加技术资料
	 * 
	 * @param request
	 * @param name
	 *            技术资料名
	 * @param technicalInfoType
	 *            资料类别
	 * @param address
	 *            施工地点
	 * @param remark
	 *            备注
	 * @param uploadFileArr
	 *            上传文件
	 * @return ResultMsg
	 */
	@PostMapping(value = "/addTechnical")
	public ResultMsg addTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "name",required=false) String name,
			@RequestParam(name = "machineRoomDialog",required=false) String machineRoom,
			@RequestParam(name = "railLineDialog",required=false) String railLine,
			@RequestParam(name = "userId",required=false) String userId,
			@RequestParam(name = "orgId",required=false) String orgId,
			@RequestParam(name = "remark",required=false) String remark,
			@RequestParam(name = "collectionName",required=false) String collectionName,
			@RequestParam(name = "uploadFile",required=false) String uploadFile) {
		
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBObject file =(DBObject) JSON.parse(uploadFile);
		document.put("creatDate", creatDateStr);
		document.put("name", name);
		document.put("machineRoom", machineRoom);
		document.put("railLine", railLine);
		//判断机房线别是否属于库中；
		List<String> lineList = networkManageInfoService.getLineData();
		boolean isContainLine = lineList.contains(railLine);
		List<String> stationList = networkManageInfoService.getStationNoConditionData();
		boolean isContainStation = stationList.contains(machineRoom);
		if (isContainLine==false) {
			return ResultMsg.getFailure("线别数据输入有误,请输入正确的线别");
		}
		if (isContainStation==false) {
			return ResultMsg.getFailure("机房数据输入有误,请输入正确的机房");
		}
		document.put("userId", userId);
		document.put("orgId", orgId);
		document.put("remark", remark);
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

	@PostMapping(value = "/updateTechnical")
	public ResultMsg updateTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "id",required=false) String id,
			@RequestParam(name = "name",required=false) String name,
			@RequestParam(name = "machineRoom",required=false) String machineRoom,
			@RequestParam(name = "machineRoomEditDialog",required=false) String machineRoomEditDialog,
			@RequestParam(name = "railLine",required=false) String railLine,
			@RequestParam(name = "railLineEditDialog",required=false) String railLineEditDialog,
			@RequestParam(name = "userId",required=false) String userId,
			@RequestParam(name = "orgId",required=false) String orgId,
			@RequestParam(name = "remark",required=false) String remark,
			@RequestParam(name = "collectionName",required=false) String collectionName,
			@RequestParam(name = "uploadFile",required=false) String uploadFile) {
		  Document document = service.findDatasById(id,collectionName);
		  DBObject file =(DBObject) JSON.parse(uploadFile);
		  document.put("name", name);
		  document.put("machineRoom", machineRoom);
		  //判断机房线别是否属于库中；
		  List<String> lineList = networkManageInfoService.getLineData();
		  List<String> stationList = networkManageInfoService.getStationNoConditionData();
		  if (StringUtils.isNotBlank(railLineEditDialog)) {
			  boolean isContainLine = lineList.contains(railLineEditDialog);
			  if (isContainLine==false) {
					return ResultMsg.getFailure("线别数据输入有误,请输入正确的线别");
				}
			  document.put("railLine", railLineEditDialog);
		  }else {
			  document.put("railLine", railLine);
		  }
		  if (StringUtils.isNotBlank(machineRoomEditDialog)) {
			boolean isContainStation = stationList.contains(machineRoomEditDialog);
			if (isContainStation==false) {
				return ResultMsg.getFailure("机房数据输入有误,请输入正确的机房");
				}
			  document.put("machineRoom", machineRoomEditDialog);
		  }else {
			  document.put("machineRoom", machineRoom);
		  }
		  document.put("updateUser", userId);
		  document.put("remark", remark);
		  document.put("collectionName", collectionName);
		  document.put("uploadFile", file);
		  Document res = service.updateDocument(document, collectionName);
		  return ResultMsg.getSuccess("修改成功", res);
	}

	/**
	 * 
	 * deleteTechnicalInfos 批量删除技术资料
	 * 
	 * @param ids
	 * @return ResultMsg
	 */
	@RequestMapping(value = "/delTechnical", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg deleteTechnicalInfos(@RequestParam("jsonIds") String jsonIds) {
		return null;
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
			String name,
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
		result.setResults(service.findAllDocumentCount(collectionName,name,machineRoom, railLine,createStartDate, createEndDate,currentDay,userId));
		result.setRows(service.findAllDocument(collectionName,name,machineRoom, railLine,createStartDate, createEndDate, start, limit,currentDay,userId));
		return result;
	}

	/**
	 * 获取技术资料记录数
	 * 
	 * @param technicalInfoDTO
	 * @return
	 */
	public int getTechnicalInfoInfoCount(TechnicalInfoDocumentDto technicalInfoDTO) {
		return 0;

		//return ser.getTechnicalInfoInfoCount(technicalInfoDTO);
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
		List<TechnicalInfoGridDto> dataList = new ArrayList<TechnicalInfoGridDto>();
		TechnicalInfoGridDto[] dtos = JsonUtil.jsonToJavaObj(exportXlsJson, TechnicalInfoGridDto[].class);
		Collections.addAll(dataList, dtos);
		String[] headerTableColumns = new String[]{ 
				"资料名称" + "_" +"60" + "_" + "my.getName()",
				"线别" + "_" +"30" + "_" + "my.getRailLine()",
				"机房" + "_" +"30" + "_" + "my.getMachineRoom()",
				"创建时间" + "_" +"25" + "_" + "my.getCreatDate()"
		}; 
		PoiExport<TechnicalInfoGridDto> export = new PoiExport<TechnicalInfoGridDto>();
		export.exportXls("机房技术资料及台账",headerTableColumns, new HashMap(), dataList, request, response);
	}
	
	 @PostMapping("/getMachineRooms")
	 public List<String> getLines(@RequestParam(name = "railLine")String railLine){
		 return service.getMachineRooms(railLine);
	 }
	
}
