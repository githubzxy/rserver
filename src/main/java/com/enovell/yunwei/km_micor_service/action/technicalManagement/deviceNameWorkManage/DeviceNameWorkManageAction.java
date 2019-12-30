package com.enovell.yunwei.km_micor_service.action.technicalManagement.deviceNameWorkManage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.productManage.MaintenanceMemorendunDto;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.DeviceNameWorkDto;
import com.enovell.yunwei.km_micor_service.service.technical.deviceNameWorkManage.DeviceNameWorkManageService;

/**      
 * 项目名称：km_micor_service
 * 类名称：package-info   
 * 类描述:  
 * 创建人：zhouxingyu
 * 创建时间：2019年7月23日 上午10:46:25
 * 修改人：zhouxingyu 
 * 修改时间：2019年7月23日 上午10:46:25   
 *    
 */
@RestController
@RequestMapping("/deviceNameWorkManageAction")
public class DeviceNameWorkManageAction {
	
	@Resource(name = "deviceNameWorkManageService")
	private DeviceNameWorkManageService service;
	
	@PostMapping(value = "/addDevice")
	public ResultMsg addTechnicalInfo(HttpServletRequest request,
			@RequestParam(name = "deviceName",required=false) String deviceName,
			@RequestParam(name = "workContent",required=false) String workContent,
			@RequestParam(name = "yearOrMonth",required=false) String yearOrMonth,
			@RequestParam(name = "type",required=false) String type,
			@RequestParam(name = "countYear",required=false) String countYear,
			@RequestParam(name = "unit",required=false) String unit,
			@RequestParam(name = "remark",required=false) String remark,
			@RequestParam(name = "collectionName",required=false) String collectionName
			){
		Document document = new Document();
		document.put("deviceName", deviceName);
		document.put("workContent", workContent);
		document.put("yearOrMonth", yearOrMonth);
		document.put("type", type);
		document.put("countYear", countYear);
		document.put("unit", unit);
		document.put("remark", remark);
		Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
	}
    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
                                     String deviceName,
                                     String workContent,
                                     @RequestParam int start, @RequestParam int limit) {
        GridDto<Document> result = new GridDto<>();
        result.setResults(service.findAllDocumentCount(collectionName, deviceName, workContent));
        result.setRows(service.findAllDocument(collectionName, deviceName, workContent, start, limit));
        return result;
    }
    
    @PostMapping("/findById")
    public ResultMsg findDocById(@RequestParam("id") String id,
        @RequestParam("collectionName") String collectionName) {
        Document res = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", res);
    }
    
    @PostMapping("/modifyDoc")
    public ResultMsg modifyDoc(
        @RequestParam("id") String id,
        @RequestParam(name = "deviceName",required=false) String deviceName,
		@RequestParam(name = "workContent",required=false) String workContent,
		@RequestParam(name = "yearOrMonth",required=false) String yearOrMonth,
		@RequestParam(name = "type",required=false) String type,
		@RequestParam(name = "countYear",required=false) String countYear,
		@RequestParam(name = "unit",required=false) String unit,
		@RequestParam(name = "remark",required=false) String remark,
		@RequestParam(name = "collectionName",required=false) String collectionName
		){
        Document document = service.findDocumentById(id, collectionName);
		document.put("deviceName", deviceName);
		document.put("workContent", workContent);
		document.put("yearOrMonth", yearOrMonth);
		document.put("type", type);
		document.put("countYear", countYear);
		document.put("unit", unit);
		document.put("remark", remark);
        document.put("status", 1);
        Document res = service.modifyDocument(document, collectionName,id);
        return ResultMsg.getSuccess("修改成功", res);
    }
    
    @PostMapping("/removeDoc")
	public ResultMsg removeDoc(@RequestParam("id") String id, @RequestParam("collectionName") String collectionName) {
		List<String> ids = Arrays.asList(id.split(","));
		service.removeDocument(ids, collectionName);
		return ResultMsg.getSuccess("删除成功");
	}
    
    
    //导入年月表配置Excel
    @PostMapping("/Import")
    @ResponseBody
    public ResultMsg wxImport(DeviceNameWorkDto dto,
        @RequestParam(name = "file_excel", required = false) MultipartFile file,
        @RequestParam(name = "userId", required = false) String userId,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        try {
            List < DeviceNameWorkDto > crdList = null;
            crdList = service.getExcelInfo(file);
            List<MultipartFile> MultipartFiles = new ArrayList<MultipartFile>();
            MultipartFiles.add(file);
            List < Document > files = service.uploadFile(MultipartFiles);
            //删除数据库中数据
            service.removeAll();
            crdList.stream().forEach(s -> {
                String collectionName = "deviceNameWorkManage";
                Document document = new Document();
                document.put("files", files);
                document.put("deviceName", s.getDeviceName());
                document.put("workContent", s.getWorkContent());
                document.put("countYear", s.getCountYear());
                document.put("remark", s.getRemark());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		String creatDateStr = "";
	    		try {
	    			creatDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("creatDateStr", creatDateStr);
                Document res = service.addDocument(document, collectionName);
            });
            return ResultMsg.getSuccess("导入成功");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResultMsg.getFailure("导入失败");
        }

    }
    
    
}
