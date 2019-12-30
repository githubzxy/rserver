package com.enovell.yunwei.km_micor_service.action.securityManage.accidentQuery;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.AccidentQueryFindDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ObstacleQueryFindDto;
import com.enovell.yunwei.km_micor_service.service.securityManage.accidentQuery.AccidentQueryService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.enovell.yunwei.km_micor_service.util.OutFileUtil;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：TroubleQueryAction   
 * 类描述:  事故故障障碍功能模块信息基本操作及导入导出
 * 创建人：zhouxingyu
 * 创建时间：2019年3月24日 下午5:14:53
 * 修改人：zhouxingyu 
 * 修改时间：2019年3月24日 下午5:14:53   
 *
 */
@RestController
@RequestMapping("/accidentQueryAction")
public class AccidentQueryAction {
	@Resource(name = "accidentQueryService")
    private AccidentQueryService service;
	
	@PostMapping("/findAll")
    public GridDto<Document> findAll(
                                     @RequestParam String collectionName,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String accidentObligationDepart,
                                     String accidentLineName,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, startUploadDate, endUploadDate, accidentObligationDepart,accidentLineName));
        result.setRows(service.findAllDocument(collectionName, startUploadDate,endUploadDate,accidentObligationDepart,accidentLineName,start,limit));
        return result;
    }
	
	@PostMapping("/findById")
    public ResultMsg findDocById(
    							@RequestParam("id") String id,
    							@RequestParam("collectionName") String collectionName) {
        Document result = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", result);
    }
    @RequestMapping("/exportXls")
	@ResponseBody
	public String exportDataToExcel(@RequestParam("exportXlsJson") String exportXlsJson,
			HttpServletRequest request, HttpServletResponse response){
    	    AccidentQueryFindDto dto = JsonUtil.jsonToJavaObj(exportXlsJson, AccidentQueryFindDto.class);
		try {
			Workbook workbook = service.exportExcel(dto);
			OutFileUtil.outFile("事故表",workbook,request,response);
			if(workbook==null){
				return "0";//无数据
			}else{
				return "1";//导出成功
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return "2";//失败
		}
		
	}
}