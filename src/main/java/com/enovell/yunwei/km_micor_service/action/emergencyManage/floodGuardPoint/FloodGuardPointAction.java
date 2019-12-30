package com.enovell.yunwei.km_micor_service.action.emergencyManage.floodGuardPoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.FloodDuardPointDto;
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.FloodDuardPointFindDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.NetworkManageInfoService;
import com.enovell.yunwei.km_micor_service.service.emergencyManage.floodGuardPoint.FloodGuardPointService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.enovell.yunwei.km_micor_service.util.OutFileUtil;
import com.enovell.yunwei.km_micor_service.util.ReadExcelFlood;

@RestController
@RequestMapping("/floodGuardPointAction")
public class FloodGuardPointAction {
	@Resource(name = "floodGuardPointService")
	private FloodGuardPointService service;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "networkManageInfoService")
    private NetworkManageInfoService netService;
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
	 * @param 所属车间：orgSelectName
	 * @param 线别：line
	 * @param 区间：section
	 * @param 看守点名称：guardName
	 * @param 列调便携电台型号：typeLT
	 * @param 列调便携电台数量：countLT
	 * @param 预警便携电台型号：typeYJ
	 * @param 预警便携电台数量：countYJ
	 * @param 固定电台型号：typeGD
	 * @param 守机联控情况:condition
	 * @param 电话号码：phoneNum
	 * @param 自动电话接入点：phoneAP
	 * @param 线类型：leadType
	 * @param 引入线长度：leadExtent
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/addDoc")
	public ResultMsg addDoc(
			@RequestParam(name = "orgSelectName") String orgSelectName,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "lineName") String lineName,
			@RequestParam(name = "section") String section,
			@RequestParam(name = "guardName") String guardName,
			@RequestParam(name = "typeLT") String typeLT, 
			@RequestParam(name = "countLT") String countLT,
			@RequestParam(name = "typeYJ") String typeYJ,
			@RequestParam(name = "countYJ") String countYJ,
			@RequestParam(name = "typeGD") String typeGD,
			
			@RequestParam(name = "typeGW") String typeGW,
			@RequestParam(name = "countGW") String countGW,
			@RequestParam(name = "phoneNumGW") String phoneNumGW,
			
			@RequestParam(name = "condition") String condition,
			@RequestParam(name = "phoneNum") String phoneNum, 
			@RequestParam(name = "phoneAP") String phoneAP,
			@RequestParam(name = "leadType") String leadType,
			@RequestParam(name = "leadExtent") String leadExtent,
			@RequestParam(name = "ksStatus") String ksStatus,
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
		document.put("orgSelectName", orgSelectName);
		document.put("workArea", workArea);
		document.put("lineName", lineName);
		document.put("section", section);
		document.put("guardName", guardName);
		document.put("typeLT", typeLT);
		document.put("countLT", countLT);
		document.put("typeYJ", typeYJ);
		document.put("countYJ", countYJ);
		document.put("typeGD", typeGD);
		
		document.put("typeGW", typeGW);
		document.put("countGW", countGW);
		document.put("phoneNumGW", phoneNumGW);
		
		document.put("condition", condition);
		document.put("phoneNum", phoneNum);
		document.put("phoneAP", phoneAP);
		document.put("leadType", leadType);
		document.put("leadExtent", leadExtent);
		document.put("ksStatus", ksStatus);
		document.put("remark", remark);
		document.put("userId", userId);
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
		Map<String, Object> user = userService.getUserById(userId);
		document.put("createUserName", user.get("USER_NAME_"));
		//判断新增数据是否和数据库中重复
		List<Document> listAll = service.findAllDocument(collectionName, userId,null,null, null, null, null, null,0, 0);
		List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map =new HashMap<String,Object>();
		//将新增时的数据放入list中
		map.put("lineName", lineName);
		map.put("guardName", guardName);
		list.add(map);
		listAll.stream().forEach(s->{
		//获取属性值，将数据库中的数据放入list中
			HashMap<String, Object> map2 =new HashMap<String,Object>();
			map2.put("lineName",  s.get("lineName"));
			map2.put("guardName", s.get("guardName"));
		    list.add(map2);
		});
		//比较新增的数据填写的线别下的站点是否已存在
		long count =list.stream().distinct().count();
   	    boolean isRepeat = count<list.size();
   	    if(isRepeat==true){
   		 return ResultMsg.getFailure("新增失败！该线别下已有该站点！");
   	   }else{
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 }
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
	public GridDto<Document> findAll(@RequestParam String collectionName, @RequestParam String userId,
			String orgSelectName, String workArea,String lineName, String section, String guardName,String ksStatus, @RequestParam int start,
			@RequestParam int limit) {
		GridDto<Document> result = new GridDto<>();
		result.setResults(
				service.findAllDocumentCount(collectionName, userId, orgSelectName,workArea, lineName, section, guardName,ksStatus));
		result.setRows(
				service.findAllDocument(collectionName, userId, orgSelectName,workArea,lineName, section, guardName, ksStatus,start, limit));
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
                               @RequestParam("collectionName") String collectionName) {
        List<String> ids = Arrays.asList(id.split(","));
        service.removeDocument(ids, collectionName);
        return ResultMsg.getSuccess("删除成功");
    }
//	@PostMapping("/removeDoc")
//	public ResultMsg removeDoc(@RequestParam("id") String id,
//			@RequestParam("userId") String userId,
//			@RequestParam("collectionName") String collectionName) {
//		  List<String> ids = Arrays.asList(id.split(","));
//	        List<String> userIdList = new ArrayList<String>();
//	        ids.forEach(s->{
//	        	 Document res = service.findDocumentById(s, collectionName);
//	        	 userIdList.add((String) res.get("userId"));
//	        });
//	   	 boolean userIdReat =userIdList.stream().allMatch(f->f.equals(userId)==true);
//	   	 System.out.println(userIdReat);
//	     if(userIdReat==true){
//	    	 service.removeDocument(ids, collectionName);
//	    	 return ResultMsg.getSuccess("删除成功");
//	     }else{
//	    	 return ResultMsg.getFailure("请选择自己添加的记录删除");
//	     }
//	}

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
			@RequestParam(name = "orgSelectName") String orgSelectName,
			@RequestParam(name = "workArea") String workArea, 
			@RequestParam(name = "lineName") String lineName,
			@RequestParam(name = "section") String section,
			@RequestParam(name = "guardName") String guardName,
			@RequestParam(name = "typeLT") String typeLT, 
			@RequestParam(name = "countLT") String countLT,
			@RequestParam(name = "typeYJ") String typeYJ,
			@RequestParam(name = "countYJ") String countYJ,
			@RequestParam(name = "typeGD") String typeGD,
			
			@RequestParam(name = "typeGW") String typeGW,
			@RequestParam(name = "countGW") String countGW,
			@RequestParam(name = "phoneNumGW") String phoneNumGW,
			
			@RequestParam(name = "condition") String condition,
			@RequestParam(name = "phoneNum") String phoneNum, 
			@RequestParam(name = "phoneAP") String phoneAP,
			@RequestParam(name = "leadType") String leadType,
			@RequestParam(name = "leadExtent") String leadExtent,
			@RequestParam(name = "ksStatus") String ksStatus,
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName, 
			HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("orgSelectName", orgSelectName);
		document.put("workArea", workArea);
		document.put("lineName", lineName);
		document.put("section", section);
		document.put("guardName", guardName);
		document.put("typeLT", typeLT);
		document.put("countLT", countLT);
		document.put("typeYJ", typeYJ);
		document.put("countYJ", countYJ);
		document.put("typeGD", typeGD);
		
		document.put("typeGW", typeGW);
		document.put("countGW", countGW);
		document.put("phoneNumGW", phoneNumGW);
		
		document.put("condition", condition);
		document.put("phoneNum", phoneNum);
		document.put("phoneAP", phoneAP);
		document.put("leadType", leadType);
		document.put("leadExtent", leadExtent);
		document.put("ksStatus", ksStatus);

		document.put("remark", remark);
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	 //导入施工维修Excel
    @RequestMapping("/Import")
	 @ResponseBody
		public ResultMsg wxImport(
				 @RequestParam(name="file_excel",required=false) MultipartFile file,
				 @RequestParam(name="userId",required=false) String userId,
				 HttpServletRequest request,
				 HttpServletResponse response
				) throws IOException{
    	try {
    		 ReadExcelFlood readExcel =new ReadExcelFlood();
	    	 List<FloodDuardPointDto> fdpList = readExcel.getExcelInfo(file);
	    	 fdpList.forEach(System.err::println);
	    	 
			 List<Document> listAll = service.findAllDocument("floodGuardPointManage", userId, null,null, null, null, null,null, 0, 0);
             //存放和表格线别和看守点相同的记录id,用于删除旧数据
			 List<String> reatList = new ArrayList<String>();
			 //存放看守点线别拼接字符串的集合用于做比较
			 List<String> nameList = new ArrayList<String>();
			 fdpList.stream().forEach(s->{
				 nameList.add(s.getLineName()+s.getGuardName());
			 });
			 //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
			 listAll.stream().forEach(s->{
				 //判断导入名称和数组库中的名称是否匹配，如果匹配将对应的id放入list中
				 if(nameList.contains((String)s.get("lineName")+(String)s.get("guardName"))){
				 reatList.add((String)s.get("docId"));
				 }
			 });
	    	 //获取线别的list用于判断导入数据的线别是否存在
	    	 List<String> lineList = netService.getLineData();
	    	 //获取车间的list用于判断导入数据的线别是否存在 
	    	 List<Object> workshopList = new ArrayList<Object>();
	    	 List<Map<String,Object>> orgList = userService.getChildrenByPidAndCurId("8affa073533aa3d601533bbef63e0010", "昆明通信段");
	    	 orgList.stream().forEach(b->{
	    		 workshopList.add(b.get("name"));
	    	 });
	    	 //判断表格中线别下的看守点是否有重复
	    	 List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    	 fdpList.stream().forEach(a->{
	    		 HashMap<String,String> map = new HashMap<>();
	    		 map.put("guardName", a.getGuardName());
	    		 map.put("lineName", a.getLineName());
	    		 list.add(map);
	    	 });
	    	 //检查导入的线别下的看守点是否有重复
	    	 long count =list.stream().distinct().count();
	    	 boolean isRepeat = count<list.size();
             //检查导入的线别是否有和数据库中的线别不一致
	    	 boolean lineReat =fdpList.stream().anyMatch(f->lineList.contains(f.getLineName())==false);
	    	 //检查导入的车间是否有和数据库中的线别不一致
	    	 boolean workshopReat =fdpList.stream().anyMatch(f->workshopList.contains(f.getOrgSelectName())==false);
	    	 if(lineReat==true||workshopReat==true){
	    		 return ResultMsg.getFailure("导入失败！请检查车间或线别是否填写正确！");
	    	 }
	    	 else if(isRepeat==true){
	    		 return ResultMsg.getFailure("导入失败！请检查线别下的看守点是否重复！");
	    	 }else{
	    	 fdpList.stream().forEach(s->{
	    	 String collectionName ="floodGuardPointManage";
	    	    Document document = new Document();
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
	    		String creatDateStr = "";
	    		try {
	    			creatDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("creatDate", new Date());
	    		document.put("creatDateStr", creatDateStr);
	    		document.put("orgSelectName", s.getOrgSelectName());
	    		document.put("workArea", s.getWorkArea());

	    		document.put("lineName", s.getLineName());
	    		document.put("section", s.getSection());
	    		document.put("guardName", s.getGuardName());
	    		document.put("typeLT", s.getTypeLT());
	    		document.put("countLT", s.getCountLT());
	    		document.put("typeYJ", s.getTypeYJ());
	    		document.put("countYJ", s.getCountYJ());
	    		document.put("typeGD", s.getTypeGD());
	    		
	    		document.put("typeGW", s.getTypeGW());
	    		document.put("countGW", s.getCountGW());
	    		document.put("phoneNumGW", s.getPhoneNumGW());
	    		
	    		document.put("condition", s.getCondition());
	    		document.put("phoneNum", s.getPhoneNum());
	    		document.put("phoneAP", s.getPhoneAP());
	    		document.put("leadType", s.getLeadType());
	    		document.put("leadExtent", s.getLeadExtent());
	    		document.put("ksStatus", s.getKsStatus());

	    		document.put("remark", s.getRemark());
	    		document.put("userId", userId);
			    service.addDocument(document, collectionName);
	    	 });
	    	 //删除数据库中线别下看守点和导入数据重复的数据
	    	 service.removeDocument(reatList, "floodGuardPointManage");
	    	 return ResultMsg.getSuccess("导入成功");
    	}
		} catch (Exception e) {
	    	 return ResultMsg.getFailure("导入失败");
		}
    		
    	}
    
    @RequestMapping("/exportXls")
	@ResponseBody
	public String exportDataToExcel(@RequestParam("exportXlsJson") String exportXlsJson,
			HttpServletRequest request, HttpServletResponse response){
    	FloodDuardPointFindDto dto = JsonUtil.jsonToJavaObj(exportXlsJson, FloodDuardPointFindDto.class);
//        ResultMsg result=new ResultMsg();
		try {
			Workbook workbook = service.exportExcel(dto);
			OutFileUtil.outFile("昆明通信段I级防洪看守点通信设备统计表",workbook,request,response);
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
