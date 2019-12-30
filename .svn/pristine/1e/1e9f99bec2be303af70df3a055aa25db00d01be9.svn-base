package com.enovell.yunwei.km_micor_service.action.deviceRecordLedger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.AccidentTroubleDto;
import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.DeviceRecordDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.ProcuratorialDailyDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.deviceRecordLedger.DeviceRecordLedgerService;
import com.enovell.yunwei.km_micor_service.service.productionManage.procuratorialDaily.ProcuratorialDailyService;
import com.enovell.yunwei.km_micor_service.util.ExportExcel;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.enovell.yunwei.km_micor_service.util.ReadExcelDeviceRecord;
import com.enovell.yunwei.km_micor_service.util.ReadExcelProcuDaily;


@RestController
@RequestMapping("/deviceRecordLedgerAction")
public class DeviceRecordLedgerAction {
	@Resource(name = "deviceRecordLedgerService")
	private DeviceRecordLedgerService service;
	@Resource(name = "userService")
	private UserService userService;
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
	 * @param 设备处所：location
	 * @param 设备名称：deviceName
	 * @param 设备类别：type
	 * @param 维护单位：maintainUnit
	 * @param 包机人：person
	 * @param 设备厂家：vender
	 * @param 设备型号：modelNumber
	 * @param 使用时间：useTime
	 * @param 所属铁路线：railwayLine
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/addDoc")
	public ResultMsg addDoc(
			@RequestParam(name = "location") String location,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "type") String type,
			@RequestParam(name = "maintainUnit") String maintainUnit, 
			@RequestParam(name = "person") String person,
			@RequestParam(name = "vender") String vender,
			@RequestParam(name = "modelNumber") String modelNumber,
			@RequestParam(name = "useTime") String useTime,
			@RequestParam(name = "railwayLine") String railwayLine,
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
		document.put("location", location);
		document.put("deviceName", deviceName);
		document.put("type", type);
		document.put("maintainUnit", maintainUnit);
		document.put("person", person);
		document.put("vender", vender);
		document.put("modelNumber", modelNumber);
		document.put("useTime", useTime);
		document.put("railwayLine", railwayLine);
		document.put("remark", remark);
		
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
			String location,
			String deviceName, 
			@RequestParam int start,
			@RequestParam int limit) {
		GridDto<Document> result = new GridDto<>();
		result.setResults(
				service.findAllDocumentCount(collectionName, userId, location, deviceName));
		result.setRows(
				service.findAllDocument(collectionName, userId, location, deviceName, start, limit));
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
	public ResultMsg removeDoc(@RequestParam("id") String id, @RequestParam("collectionName") String collectionName) {
		List<String> ids = Arrays.asList(id.split(","));
		service.removeDocument(ids, collectionName);
		return ResultMsg.getSuccess("删除成功");
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
			@RequestParam(name = "location") String location,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "type") String type,
			@RequestParam(name = "maintainUnit") String maintainUnit, 
			@RequestParam(name = "person") String person,
			@RequestParam(name = "vender") String vender,
			@RequestParam(name = "modelNumber") String modelNumber,
			@RequestParam(name = "useTime") String useTime,
			@RequestParam(name = "railwayLine") String railwayLine,
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName, 
			HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("location", location);
		document.put("deviceName", deviceName);
		document.put("type", type);
		document.put("maintainUnit", maintainUnit);
		document.put("person", person);
		document.put("vender", vender);
		document.put("modelNumber", modelNumber);
		document.put("useTime", useTime);
		document.put("railwayLine", railwayLine);
		document.put("remark", remark);
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	 //导入设备履历Excel
     @RequestMapping("/Import")
	 @ResponseBody
		public ResultMsg wxImport(
				 @RequestParam(name="file_excel",required=false) MultipartFile file,
				 HttpServletRequest request,
				 HttpServletResponse response
				) throws IOException{
    	try {
    		ReadExcelDeviceRecord readExcel =new ReadExcelDeviceRecord();
    		List<DeviceRecordDto> fdpList = readExcel.getExcelInfo(file);
    		List<DeviceRecordDto> newfdpList =new ArrayList<DeviceRecordDto>();
    		for (DeviceRecordDto dRD : fdpList) {
    			if(StringUtils.isNotBlank(dRD.getLocation())){
    				newfdpList.add(dRD);
    			}
			}
			newfdpList.stream().forEach(s->{
	    	    String collectionName ="deviceRecordLedger";
	    	    Document document = new Document();
	    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		String creatDateStr = "";
	    		try {
	    			creatDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("creatDateStr", creatDateStr);
	    		document.put("location", s.getLocation());
	    		document.put("deviceName", s.getDeviceName());
	    		document.put("type", s.getType());
	    		document.put("maintainUnit", s.getMaintainUnit());
	    		document.put("person", s.getPerson());
	    		document.put("vender", s.getVender());
	    		document.put("modelNumber", s.getModelNumber());
	    		document.put("useTime", s.getUseTime());
	    		document.put("railwayLine", s.getRailwayLine());
	    		document.put("remark", s.getRemark());	
			    service.addDocument(document,collectionName);
	    	 });
	    	 return ResultMsg.getSuccess("导入成功");
		} catch (Exception e) {
	    	 return ResultMsg.getFailure("导入失败");
		}
    		
    	}
     @PostMapping("/exportXls")
 	public void exportDataToExcel(
 			@RequestParam("exportXlsJson") String exportXlsJson,
 			HttpServletRequest request, HttpServletResponse response) {

 		List<DeviceRecordDto> dataList = new ArrayList<DeviceRecordDto>();
 		DeviceRecordDto[] dtos = JsonUtil.jsonToJavaObj(exportXlsJson,
 				DeviceRecordDto[].class);
 		Collections.addAll(dataList, dtos);

 		String[] headerTableColumns = new String[] {
 				"设备处所" + "_" + "30" + "_" + "my.getLocation()",
 				"设备名称" + "_" + "30" + "_" + "my.getDeviceName()",
 				"设备类别" + "_" + "20" + "_" + "my.getType()",
 				"维护单位" + "_" + "20" + "_" + "my.getMaintainUnit()",
 				"包机人 " + "_" + "30" + "_" + "my.getPerson()",
 				"设备厂家" + "_" + "30" + "_" + "my.getVender()",
 				"设备型号" + "_" + "30" + "_" + "my.getModelNumber()",
 				"使用时间" + "_" + "30" + "_" + "my.getUseTime()",
 				"所属铁路线" + "_" + "30" + "_" + "my.getRailwayLine()",
 				"备注信息" + "_" + "30" + "_" + "my.getRemark()" 
 				};

 		Map<String, Object> expandJexlContext = new HashMap<String, Object>();
 		ExportExcel<DeviceRecordDto> export = new ExportExcel<DeviceRecordDto>();
 		export.exportXls("设备履历台账导出表", headerTableColumns, expandJexlContext,
 				dataList, request,response);
 	}
 	
     
     
}
