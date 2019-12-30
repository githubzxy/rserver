package com.enovell.yunwei.km_micor_service.action.integratedManage.attendanceManage;

import java.util.ArrayList;
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
import com.enovell.yunwei.km_micor_service.service.integratedManage.attendanceManage.AttendanceManageCollectQueryService;

/**
 * kunmingTXD
 * 昆明微服务考勤管理填写Action
 *
 * @author yangsy
 * @date 19-3-29
 */
@RestController
@RequestMapping("/attendanceManageCollectQueryAction")
public class AttendanceManageCollectQueryAction {
	@Resource(name = "attendanceManageCollectQueryService")
    private AttendanceManageCollectQueryService service;
	
	@PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String attendanceManageCollect,
									 @RequestParam String orgId,
									 @RequestParam int orgType,
                                     String startUploadDate,
                                     String endUploadDate,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDataCount(attendanceManageCollect, orgId, orgType, startUploadDate, endUploadDate));
        result.setRows(service.findAllData(attendanceManageCollect, orgId, orgType, startUploadDate, endUploadDate, start, limit));
        return result;
    }
	
	
	@PostMapping("/updateCollectDoc")
	public ResultMsg updateCollectDoc(	
			String id,
			String filePath,
			String date,
			String attendanceOrgName,
			String attendanceOrgId,
			String attendanceManageCollect,
			String workshopQueryData,
			String flowState,
			HttpServletRequest request) {
		Document doc = service.findDatasById(id,attendanceManageCollect);
		doc.put("workshopQueryData", workshopQueryData);
		if(StringUtils.isNotBlank(flowState)){
			doc.put("flowState", flowState);
		}
		Document res = service.updateCollectDocument(doc, attendanceManageCollect);
		if("-1".equals(flowState)){
			service.importSumData(filePath,date,attendanceOrgName,attendanceOrgId);
			return ResultMsg.getSuccess("操作成功", res);
		}else{
			return ResultMsg.getSuccess("退回成功", res);
		}
		
	}
	
	@PostMapping("/sumCollectDoc")
	public ResultMsg addDoc(
							@RequestParam(name = "year") String year,
							@RequestParam(name = "month") String month,
                            @RequestParam(name = "attendanceManageSum") String attendanceManageSum,
                            HttpServletRequest request) {
        
        String filePath = service.collectTable(year, month, attendanceManageSum);
        
        return ResultMsg.getSuccess("导出成功", filePath);
	}
	@PostMapping("/sumRangeCollectDoc")
	public ResultMsg sumRangeCollectDoc(
			@RequestParam(name = "year") String year,
			@RequestParam(name = "monthStart") String monthStart,
			@RequestParam(name = "monthEnd") String monthEnd,
			@RequestParam(name = "attendanceManageSum") String attendanceManageSum,
			HttpServletRequest request) {
		String filePath = "";
		if(monthStart.equals(monthEnd)){
			filePath = service.collectTable(year, monthEnd, attendanceManageSum);
		}else{
			int start = Integer.valueOf(monthStart.substring(0, monthStart.indexOf("月")));
			int end = Integer.valueOf(monthEnd.substring(0, monthEnd.indexOf("月")));
			List<String> monthList = new ArrayList<String>();
			monthList.add(monthStart);
			if(end-start>1){
				for(int i=1; i<end-start; i++){
					monthList.add(start+i+"月");
				}
			}
			monthList.add(monthEnd);
			filePath = service.collectRangeTable(year, monthList, attendanceManageSum);
		}
		return ResultMsg.getSuccess("导出成功", filePath);
	}
	
}