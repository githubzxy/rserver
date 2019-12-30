package com.enovell.yunwei.km_micor_service.action.dayToJobManageAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.ConstructCooperateService;

@RequestMapping("/constructCooperateAction")
@RestController
public class ConstructCooperateAction {
	@Resource(name="constructCooperateService")
	private ConstructCooperateService service;
	@Resource(name = "userService")
    private UserService userService;
	
	@RequestMapping("/getChildrenByPidAndCurId")
    public List<Map<String,Object>> getChildrenByPidAndCurId( @RequestParam("id") String id,
                                                              @RequestParam(required=false,value="curId") String curId){
        return userService.getChildrenByPidAndCurId(id, curId);
    }
	
	@PostMapping("/findAllDatas")
	public GridDto<Document> findAllDatas(@RequestParam String collectionName,
			String startUploadDate,
			String endUploadDate,
			@RequestParam int start,
			@RequestParam int limit,
			@RequestParam String userId){
		GridDto<Document> result=new GridDto<>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
        String currentDay = "";
		currentDay = sdf.format(currentDate);
		result.setResults(service.findAllDocumentCount(collectionName, startUploadDate, endUploadDate,currentDay,userId));
		result.setRows(service.quryAllDatas(collectionName, startUploadDate, endUploadDate, start, limit,currentDay,userId));
		return result;
		
	}
	
	 @PostMapping("/addData")
	    public ResultMsg addDoc(@RequestParam(name = "date") String date,
	    						@RequestParam(name = "line") String line,
	    						@RequestParam(name = "workShop") String workShop,
	                            @RequestParam(name = "depart") String depart,
	                            @RequestParam(name = "local") String local,
	                            @RequestParam(name = "constructUnit") String constructUnit,
	                            @RequestParam(name = "constructPro") String constructPro,
	                            String cooperMan,
	                            @RequestParam(name = "constructContent") String constructContent,
	                            @RequestParam(name = "cableSituation") String cableSituation,
	                            @RequestParam(name = "remark") String remark,
	                            @RequestParam(name = "collectionName") String collectionName,
	                            HttpServletRequest request) {
	        Document document = new Document();
	        
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        try {
//				document.put("date", sdf.parse(date));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
	        document.put("date", date);
	        document.put("line", line);
	        document.put("workShop", workShop);
	        document.put("depart", depart);
	        document.put("local", local);
	        document.put("constructUnit", constructUnit);
	        document.put("constructPro", constructPro);
	        document.put("cooperMan", cooperMan);
	        document.put("constructContent", constructContent);
	        document.put("cableSituation", cableSituation);
	        document.put("summaryDate", "");//汇总日期
	        document.put("summaryPersonId", "");//汇总人ID
	        document.put("summaryPersonName", "");//汇总人名称
	        document.put("remark", remark);
	        Document res = service.addDocument(document, collectionName);
	        return ResultMsg.getSuccess("新增成功", res);
	    }
	 @PostMapping("/getWorkShop")
	 public List<Map<String, Object>> getWorkShop(){
		 
		return service.getWorkShops();
		 
	 }
	 @PostMapping("/getDepart")
	 public List<Map<String, Object>> getDepart(String workShopName){
		 
		return service.getDeparts(workShopName);
		 
	 }
	 @PostMapping("/getLines")
	 public List<String> getLines(){
		 
		 return service.getLineData();
		 
	 }
	 @PostMapping("/findById")
	 public Document findById(String id,String collectionName){
		 
		 return service.findDatasById(id,collectionName);
		 
	 }
	 @PostMapping("/getcooperMan")
	 public List<Map<String, String>> getcooperMan(String orgName){
		 List<String> dataList=service.getcooperManById(orgName);
		 List<Map<String, String>> cooperManList=new ArrayList<Map<String, String>>();
		 for (String string : dataList) {
			 Map<String,String> map=new HashMap();
			map.put("text", string);
			map.put("value", string);
			cooperManList.add(map);
		}
		 return cooperManList;
		 
	 }
	 
	 @PostMapping("/updateDoc")
	    public ResultMsg updateDoc(	@RequestParam("id") String id,
	    							@RequestParam(name = "date") String date,
									@RequestParam(name = "line") String line,
									@RequestParam(name = "workShop") String workShop,
					                @RequestParam(name = "depart") String depart,
					                @RequestParam(name = "local") String local,
					                @RequestParam(name = "constructUnit") String constructUnit,
					                @RequestParam(name = "constructPro") String constructPro,
					                @RequestParam(name = "cooperMan") String cooperMan,
					                @RequestParam(name = "constructContent") String constructContent,
					                @RequestParam(name = "cableSituation") String cableSituation,
					                @RequestParam(name = "remark") String remark,
					                @RequestParam(name = "collectionName") String collectionName,
					                HttpServletRequest request) {
	        Document doc = service.findDatasById(id,collectionName);
	        doc.put("date", date);
	        doc.put("line", line);
	        doc.put("workShop", workShop);
	        doc.put("depart", depart);
	        doc.put("local", local);
	        doc.put("constructUnit", constructUnit);
	        doc.put("constructPro", constructPro);
	        doc.put("cooperMan", cooperMan);
	        doc.put("constructContent", constructContent);
	        doc.put("cableSituation", cableSituation);
	        doc.put("summaryDate", "");//汇总日期
	        doc.put("summaryPersonId", "");//汇总人ID
	        doc.put("summaryPersonName", "");//汇总人名称
	        doc.put("remark", remark);
	        Document res = service.updateDocument(doc, collectionName);
	        return ResultMsg.getSuccess("修改成功", res);
	    }
	

}
