package com.enovell.yunwei.km_micor_service.action.productionManage.procuratorialDaily;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.action.technicalManagement.technicalDocument.PoiExport;
import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ProcuratorialDailyDto;
import com.enovell.yunwei.km_micor_service.dto.productManage.JobRecordDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.procuratorialDaily.ProcuratorialDailyService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.enovell.yunwei.km_micor_service.util.ReadExcelProcuDaily;

@RestController
@RequestMapping("/procuratorialDailyAction")
public class ProcuratorialDailyAction {
	@Resource(name = "procuratorialDailyService")
	private ProcuratorialDailyService service;
	@Resource(name = "userService")
	private UserService userService;
//	@Resource(name = "networkManageInfoService")s
	/**
	 * getSystemDate 获取当前系统时间
	 * 
	 * @return String
	 */
	@PostMapping("/getSystemDate")
	public String getSystemDate() {
		return JsonUtil.getSystemDate();
	}

	/**
	 * 新增数据
	 * 
	 * @param 劳资号：number
	 * @param 姓名：staffName
	 * @param 性别：gender
	 * @param 出生日期：birthday
	 * @param 参加工作时间：entryDate
	 * @param 学历：education
	 * @param 车间：workshop
	 * @param 班组：teamGroup
	 * @param 职名：position
	 * @param 电话:phoneNum
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/addDoc")
	public ResultMsg addDoc(
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "department") String department,
			@RequestParam(name = "date") String date,
			@RequestParam(name = "inspector") String inspector, 
			@RequestParam(name = "site") String site,
			@RequestParam(name = "content") String content,
			@RequestParam(name = "problem") String problem,
			@RequestParam(name = "require") String require,
			@RequestParam(name = "condition") String condition,
			@RequestParam(name = "functionary") String functionary, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("creatDateStr", creatDateStr);
		document.put("workshop", workshop);
		document.put("department", department);
		document.put("date", date);
		document.put("inspector", inspector);
		document.put("site", site);
		document.put("content", content);
		document.put("problem", problem);
		document.put("require", require);
		document.put("condition", condition);
		document.put("functionary", functionary);
		document.put("remark", remark);
		document.put("userId", userId);
		
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}

	/**
	 * 主页显示
	 * 
	 * @param collectionName
	 * @param name
	 * @param startUploadDate
	 * @param endUploadDate
	 * @param auditStatus
	 * @param orgId
	 * @param start
	 * @param limit
	 * @return
	 */
	@PostMapping("/findAll")
	public GridDto<Document> findAll(@RequestParam String collectionName,
			@RequestParam String userId,
			String workshop,
			String startdate, 
			String enddate,
			@RequestParam int start,
			@RequestParam int limit) {
		GridDto<Document> result = new GridDto<>();
		result.setResults(
				service.findAllDocumentCount(collectionName, userId, workshop, startdate,enddate));
		result.setRows(
				service.findAllDocument(collectionName, userId, workshop, startdate, start, limit,enddate));
		return result;
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 *            数据id
	 * @param collectionName
	 *            表名
	 * @return 状态返回
	 */
	@PostMapping("/removeDoc")
	public ResultMsg removeDoc(@RequestParam("id") String id,
			@RequestParam("userId") String userId, 
			@RequestParam("collectionName") String collectionName) {
		  List<String> ids = Arrays.asList(id.split(","));
	        List<String> userIdList = new ArrayList<String>();
	        ids.forEach(s->{
	        	 Document res = service.findDocumentById(s, collectionName);
	        	 userIdList.add((String) res.get("userId"));
	        });
	      //判断选择的记录是否全为草稿状态
	   	 boolean userIdReat =userIdList.stream().allMatch(f->f.equals(userId)==true);
	   	 System.out.println(userIdReat);
	     if(userIdReat==true){
	    	 service.removeDocument(ids, collectionName);
	    	 return ResultMsg.getSuccess("删除成功");
	     }else{
	    	 return ResultMsg.getFailure("请选择自己添加的记录删除");
	     }
	}

	/**
	 * 查询一条记录
	 * 
	 * @param id
	 * @param collectionName
	 * @return
	 */
	@PostMapping("/findById")
	public ResultMsg findDocById(@RequestParam("id") String id, @RequestParam("collectionName") String collectionName) {
		Document res = service.findDocumentById(id, collectionName);
		return ResultMsg.getSuccess("查询完成", res);
	}

	@PostMapping("/updateDoc")
	public ResultMsg updateDoc(@RequestParam("id") String id, 
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "department") String department,
			@RequestParam(name = "date") String date,
			@RequestParam(name = "inspector") String inspector, 
			@RequestParam(name = "site") String site,
			@RequestParam(name = "content") String content,
			@RequestParam(name = "problem") String problem,
			@RequestParam(name = "require") String require,
			@RequestParam(name = "condition") String condition,
			@RequestParam(name = "functionary") String functionary, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName, 
			HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("workshop", workshop);
		document.put("department", department);
		document.put("date", date);
		document.put("inspector", inspector);
		document.put("site", site);
		document.put("content", content);
		document.put("problem", problem);
		document.put("require", require);
		document.put("condition", condition);
		document.put("functionary", functionary);
		document.put("remark", remark);	
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	 //导入检修日志Excel
     @RequestMapping("/Import")
	 @ResponseBody
		public ResultMsg wxImport(
				 @RequestParam(name="file_excel",required=false) MultipartFile file,
				 @RequestParam(name = "userId") String userId,
				 HttpServletRequest request,
				 HttpServletResponse response
				) throws IOException{
    	try {
    		ReadExcelProcuDaily readExcel =new ReadExcelProcuDaily();
			List<ProcuratorialDailyDto> fdpList = readExcel.getExcelInfo(file).stream().filter
					(s->s.getWorkshop()!=null&&s.getDepartment()!=null).collect(Collectors.toList());//将读取的表格记录去重，通过必填项过滤
	    	 fdpList.stream().forEach(s->{
	    	    String collectionName ="procuratorialDaily";
	    	    Document document = new Document();
	    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		String creatDateStr = "";
	    		try {
	    			creatDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("creatDateStr", creatDateStr);
	    		document.put("workshop", s.getWorkshop());
	    		document.put("department", s.getDepartment());
	    		document.put("date", s.getDate());
	    		document.put("inspector", s.getInspector());
	    		document.put("site", s.getSite());
	    		document.put("content", s.getContent());
	    		document.put("problem", s.getProblem());
	    		document.put("require", s.getRequire());
	    		document.put("condition", s.getCondition());
	    		document.put("functionary", s.getFunctionary());
	    		document.put("remark", s.getRemark());
	    		document.put("userId", userId);	
			    service.addDocument(document,collectionName);
	    	 });
	    	 return ResultMsg.getSuccess("导入成功");
		} catch (Exception e) {
	    	 return ResultMsg.getFailure("导入失败");
		}
    		
    	}
     
     @PostMapping(value = "/exportXlsBy")
		public void exportXls(
				String workshopOfexport,
				String startdateOfexport,
				String enddateOfexport,
				HttpServletRequest request,
				HttpServletResponse response) {
			List<ProcuratorialDailyDto> dataList = new ArrayList<ProcuratorialDailyDto>();
			dataList = service.findAllDocumentByexport(workshopOfexport, startdateOfexport,enddateOfexport);
		/*
		 * ProcuratorialDailyDto[] dtos = JsonUtil.jsonToJavaObj(exportXlsJson,
		 * ProcuratorialDailyDto[].class); Collections.addAll(dataList, dtos);
		 */
			String[] headerTableColumns = new String[]{ 
					" 填报车间" + "_" +"30" + "_" + "my.getWorkshop()",
					"填报部门" + "_" +"30" + "_" + "my.getDepartment()",
					"时间" + "_" +"30" + "_" + "my.getDate()",
					"检查人" + "_" +"30" + "_" + "my.getInspector()",
					"检查地点" + "_" +"30" + "_" + "my.getSite()",
					"检查内容" + "_" +"60" + "_" + "my.getContent()",
					"发现问题" + "_" +"60" + "_" + "my.getProblem()",
					"整改要求" + "_" +"60" + "_" + "my.getRequire()",
					"整改落实情况" + "_" +"60" + "_" + "my.getCondition()",
					"整改负责人" + "_" +"60" + "_" + "my.getFunctionary()",
					"备注" + "_" +"60" + "_" + "my.getRemark()",
			}; 
			PoiExport<ProcuratorialDailyDto> export = new PoiExport<ProcuratorialDailyDto>();
			export.exportXls("检查日报",headerTableColumns, new HashMap(), dataList, request, response);
		}
}
