package com.enovell.yunwei.km_micor_service.action.securityManage.accidentTroubleObstacleManage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.AccidentTroubleDto;
import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.securityManage.accidentTroubleObstacleManage.AccidentTroubleObstacleQueryService;
import com.enovell.yunwei.km_micor_service.util.ExportExcel;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：AccidentTroubleObstacleQueryAction   
 * 类描述:  事故故障障碍功能模块信息基本操作及导入导出
 * 创建人：zhouxingyu
 * 创建时间：2019年3月24日 下午5:14:53
 * 修改人：zhouxingyu 
 * 修改时间：2019年3月24日 下午5:14:53   
 *
 */
@RestController
@RequestMapping("/accidentTroubleObstacleQueryAction")
public class AccidentTroubleObstacleQueryAction {
	@Resource(name = "accidentTroubleObstacleQueryService")
    private AccidentTroubleObstacleQueryService service;
	@PostMapping("/findAll")
    public GridDto<Document> findAll(
                                     @RequestParam String collectionName,
									 String infoResult,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, infoResult, startUploadDate, endUploadDate));
        result.setRows(service.findAllDocument(collectionName, infoResult, startUploadDate, endUploadDate, start, limit));
        return result;
    }
	
	@PostMapping("/findById")
    public ResultMsg findDocById(
    							@RequestParam("id") String id,
    							@RequestParam("collectionName") String collectionName) {
        Document result = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", result);
    }
//	@PostMapping("/exportXls")
	
	/**
	 * 
	 * exportDataToExcel 导出excel
	 * 
	 * @param exportXlsJson
	 * @param request
	 * @param response
	 */
	@PostMapping("/exportXls")
	public void exportDataToExcel(
			@RequestParam("exportXlsJson") String exportXlsJson,
			HttpServletRequest request, HttpServletResponse response) {

		List<AccidentTroubleDto> dataList = new ArrayList<AccidentTroubleDto>();
		AccidentTroubleDto[] dtos = JsonUtil.jsonToJavaObj(exportXlsJson,
				AccidentTroubleDto[].class);
		Collections.addAll(dataList, dtos);
		//对信息做展示处理
		service.convertInfoResult(dataList);
		String[] headerTableColumns = new String[] {
				"时间" + "_" + "30" + "_" + "my.getCreateDate()",
				"信息反馈部门" + "_" + "30" + "_" + "my.getBackOrgName()",
				"信息反馈人" + "_" + "20" + "_" + "my.getBackPerson()",
				"类型" + "_" + "20" + "_" + "my.getType()",
				"内容及处理情况" + "_" + "30" + "_" + "my.getDetail()",
				"信息后果" + "_" + "30" + "_" + "my.getInfoResult()",
				"备注" + "_" + "30" + "_" + "my.getRemark()" 
				};

		Map<String, Object> expandJexlContext = new HashMap<String, Object>();
		ExportExcel<AccidentTroubleDto> export = new ExportExcel<AccidentTroubleDto>();
		export.exportXls("事故故障障碍导出表", headerTableColumns, expandJexlContext,
				dataList, request,response);
	}
	
}
