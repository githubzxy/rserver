package com.enovell.yunwei.km_micor_service.action.userInfoManage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.enovell.yunwei.km_micor_service.dto.dayToJobManageDto.UserInfoManageDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.NetworkManageInfoService;
import com.enovell.yunwei.km_micor_service.service.userInfoManage.UserInfoManageService;
import com.enovell.yunwei.km_micor_service.util.ExportExcel;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import com.enovell.yunwei.km_micor_service.util.ReadExcelUserInfo;

@RestController
@RequestMapping("/userInfoManageAction")
public class UserInfoManageAction {
	@Resource(name = "userInfoManageService")
	private UserInfoManageService service;
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
	// 车间（科室）下拉选
	@PostMapping("/getCadreAndShop")
	public List<Map<String, String>> getCadreAndShop() {
		List<String> cjList = service.getCadreAndShop();
		List<Map<String, String>> cjDatas = new ArrayList<>();
		for (String string : cjList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			cjDatas.add(map);
		}
		return cjDatas;
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
			@RequestParam(name = "number") String number,
			@RequestParam(name = "staffName") String staffName,
			@RequestParam(name = "gender") String gender,
			@RequestParam(name = "birthday") String birthday, 
			@RequestParam(name = "entryDate") String entryDate,
			@RequestParam(name = "education") String education,
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "teamGroup") String teamGroup,
			@RequestParam(name = "position") String position,
			@RequestParam(name = "phoneNum") String phoneNum, 
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
		document.put("number", number);
		document.put("staffName", staffName);
		document.put("gender", gender);
		document.put("birthday", birthday);
		document.put("entryDate", entryDate);
		document.put("education", education);
		document.put("workshop", workshop);
		document.put("teamGroup", teamGroup);
		document.put("position", position);
		document.put("phoneNum", phoneNum);
		document.put("remark", remark);
		if(StringUtils.isNotBlank(teamGroup)){
		String orgId = service.getOrgIdByWorkshop(teamGroup);
		String orgType = service.getOrgTypeByWorkshop(teamGroup);
		   if(orgId!=null){
    		document.put("orgId", orgId);
    		document.put("orgType", orgType);
    		}else{
    			//若增加的班组与数据库不匹配，则插入车间组织机构id
    		document.put("orgId", service.getOrgIdByWorkshop(workshop));
    		document.put("orgType",service.getOrgIdByWorkshop(workshop));
    		}
		}else{
		String orgId = service.getOrgIdByWorkshop(workshop);
		String orgType = service.getOrgTypeByWorkshop(workshop);
		    if(orgId!=null){
    		document.put("orgId", orgId);
    		document.put("orgType", orgType);
    		}else{
    		document.put("orgId", "");
    		document.put("orgType", "");
    		}
		}
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
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
			@RequestParam("orgId") String loginOrgId,
			@RequestParam("orgType") String loginOrgType,
			String workshop,
			String teamGroup,
			String selectOrgType,
			String phoneNum, 
			String staffName, 
			@RequestParam int start,
			@RequestParam int limit) {
		GridDto<Document> result = new GridDto<>();
		result.setResults(
				service.findAllDocumentCount(collectionName, userId,loginOrgId, loginOrgType,workshop,teamGroup,selectOrgType,phoneNum, staffName));
		result.setRows(
				service.findAllDocument(collectionName, userId,loginOrgId, loginOrgType, workshop,teamGroup,selectOrgType, phoneNum, staffName, start, limit));
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

	@PostMapping("/updateOtherDoc")
	public ResultMsg updateOtherDoc(@RequestParam("id") String id, 
			@RequestParam(name = "staffName") String staffName,
			@RequestParam(name = "gender") String gender,
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "position") String position,
			@RequestParam(name = "phoneNum") String phoneNum, 
			@RequestParam(name = "collectionName") String collectionName, 
			HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("staffName", staffName);
		document.put("gender", gender);
		document.put("workshop", workshop);
		document.put("position", position);
		document.put("phoneNum", phoneNum);
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}

	@PostMapping("/updateDoc")
	public ResultMsg updateDoc(@RequestParam("id") String id, 
			@RequestParam(name = "number") String number,
			@RequestParam(name = "staffName") String staffName,
			@RequestParam(name = "gender") String gender,
			@RequestParam(name = "birthday") String birthday, 
			@RequestParam(name = "entryDate") String entryDate,
			@RequestParam(name = "education") String education,
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "teamGroup") String teamGroup,
			@RequestParam(name = "position") String position,
			@RequestParam(name = "phoneNum") String phoneNum, 
			@RequestParam(name = "remark") String remark,  
			@RequestParam(name = "collectionName") String collectionName, 
			HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("number", number);
		document.put("staffName", staffName);
		document.put("gender", gender);
		document.put("birthday", birthday);
		document.put("entryDate", entryDate);
		document.put("education", education);
		document.put("workshop", workshop);
		document.put("teamGroup", teamGroup);
		document.put("position", position);
		document.put("phoneNum", phoneNum);
		document.put("remark", remark);
		if(StringUtils.isNotBlank(teamGroup)){
			String orgId = service.getOrgIdByWorkshop(teamGroup);
			String orgType = service.getOrgTypeByWorkshop(teamGroup);
			   if(orgId!=null){
	    		document.put("orgId", orgId);
	    		document.put("orgType", orgType);
	    		}else{
	    			//若增加的班组与数据库不匹配，则插入车间组织机构id
	    		document.put("orgId", service.getOrgIdByWorkshop(workshop));
	    		document.put("orgType", service.getOrgTypeByWorkshop(workshop));
	    		}
			}else{
			String orgId = service.getOrgIdByWorkshop(workshop);
			String orgType = service.getOrgTypeByWorkshop(workshop);
			    if(orgId!=null){
	    		document.put("orgId", orgId);
	    		document.put("orgType", orgType);
	    		}else{
	    		document.put("orgId", "");
	    		document.put("orgType", "");
	    		}
			}
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	 //导入人员信息Excel
     @RequestMapping("/Import")
	 @ResponseBody
		public ResultMsg wxImport(
				 @RequestParam(name="file_excel",required=false) MultipartFile file,
				 @RequestParam(name="userId",required=false) String userId,
				 @RequestParam(name="orgId",required=false) String loginOrgId,
				 @RequestParam(name="orgType",required=false) String loginOrgType,
				 HttpServletRequest request,
				 HttpServletResponse response
				) throws IOException{
    	try {
    		 ReadExcelUserInfo readExcel =new ReadExcelUserInfo();
			 List<Document> listAll = service.findAllDocument("userInfoManage", userId,loginOrgId,loginOrgType,null,null,null,null,null, 0, 0);
	    	 List<UserInfoManageDto> fdpList = readExcel.getExcelInfo(file);
	    	//存放和表格劳资号相同的记录id,用于删除旧数据
			 List<String> reatList = new ArrayList<String>();
			 List<String> numberList = new ArrayList<String>();
			 fdpList.stream().forEach(s->{
				 numberList.add(s.getNumber());
			 });
			//表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
			 listAll.stream().forEach(s->{
				 
				 //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
				 if(numberList.contains((String)s.get("number"))){
				 reatList.add((String)s.get("docId"));
				 }
			 });
	    	 fdpList.stream().forEach(s->{
	    	    String collectionName ="userInfoManage";
	    	    Document document = new Document();
	    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		String creatDateStr = "";
	    		try {
	    			creatDateStr = sdf.format(new Date());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		document.put("creatDateStr", creatDateStr);
	    		document.put("number",s.getNumber());
	    		document.put("staffName",s.getStaffName());
	    		document.put("gender",s.getGender() );
	    		document.put("birthday",s.getBirthday());
	    		document.put("entryDate",s.getEntryDate() );
	    		document.put("education", s.getEducation());
	    		document.put("workshop",s.getWorkshop() );
	    		document.put("teamGroup",s.getTeamGroup());
	    		document.put("position",s.getPosition());
	    		document.put("phoneNum",s.getPhoneNum() );
	    		document.put("remark", s.getRemark());
	    		if(StringUtils.isNotBlank(s.getTeamGroup())){
	    			String orgId = service.getOrgIdByWorkshop(s.getTeamGroup());
	    			String orgType = service.getOrgTypeByWorkshop(s.getTeamGroup());
	    			   if(orgId!=null){
	    	    		document.put("orgId", orgId);
	    	    		document.put("orgType", orgType);
	    	    		}else{
	    	    			//若增加的班组与数据库不匹配，则插入车间组织机构id
	    	    		document.put("orgId", service.getOrgIdByWorkshop(s.getWorkshop()));
	    	    		document.put("orgType",service.getOrgIdByWorkshop(s.getWorkshop()));
	    	    		}
	    			}else{
	    			String orgId = service.getOrgIdByWorkshop(s.getWorkshop());
	    			String orgType = service.getOrgTypeByWorkshop(s.getWorkshop());
	    			    if(orgId!=null){
	    	    		document.put("orgId", orgId);
	    	    		document.put("orgType", orgType);
	    	    		}else{
	    	    		document.put("orgId", "");
	    	    		document.put("orgType", "");
	    	    		}
	    			}
			    service.addDocument(document,collectionName);
	    	 });
	    	 //删除数据库中线别下看守点和导入数据重复的数据
	    	 service.removeDocument(reatList, "userInfoManage");
	    	 return ResultMsg.getSuccess("导入成功");
		} catch (Exception e) {
	    	 return ResultMsg.getFailure("导入失败");
		}
    		
    	}
    @PostMapping("/getchildOrgId")
	public  List<String> getchildOrgId(String orgId) {
    	List<Map<String, Object>> list =service.getChildIdByOrgId(orgId);
    	List<String> orgIdlist = new ArrayList<String>();
    	list.stream().forEach(s->{
    		orgIdlist.add((String) s.get("orgId"));
    	});
		return orgIdlist;
	}
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

		List<UserInfoManageDto> dataList = new ArrayList<UserInfoManageDto>();
		UserInfoManageDto[] dtos = JsonUtil.jsonToJavaObj(exportXlsJson,
				UserInfoManageDto[].class);
//		//查询所有所有数据
//		List <Document> list = service.findAllDocument("userInfoManage","","","","","","","", 0, 0);
//		list.stream().forEach(s->{
//			UserInfoManageDto dto = new UserInfoManageDto();
//			dto.setNumber(s.get("number").toString());
//			dto.setStaffName(s.get("staffName").toString());
//			dto.setGender(s.get("gender").toString());
//			dto.setBirthday(s.get("birthday").toString());
//			dto.setEntryDate(s.get("entryDate").toString());
//			dto.setEducation(s.get("education").toString());
//			dto.setWorkshop(s.get("workshop").toString());
//			dto.setTeamGroup(s.get("teamGroup").toString());
//			dto.setPosition(s.get("position").toString());
//			dto.setPhoneNum(s.get("phoneNum").toString());
//			dto.setRemark(s.get("remark").toString());
//			dataList.add(dto);
//		});
		Collections.addAll(dataList, dtos);
		String[] headerTableColumns = new String[] {
				"劳资号" + "_" + "30" + "_" + "my.getNumber()",
				"姓名" + "_" + "30" + "_" + "my.getStaffName()",
				"性别" + "_" + "20" + "_" + "my.getGender()",
				"出生日期" + "_" + "20" + "_" + "my.getBirthday()",
				"参加工作时间" + "_" + "30" + "_" + "my.getEntryDate()",
				"学历" + "_" + "30" + "_" + "my.getEducation()",
				"车间（科室）" + "_" + "30" + "_" + "my.getWorkshop()" ,
				"班组" + "_" + "30" + "_" + "my.getTeamGroup()" ,
				"职名" + "_" + "30" + "_" + "my.getPosition()" ,
				"联系电话" + "_" + "30" + "_" + "my.getPhoneNum()",
				"备注" + "_" + "30" + "_" + "my.getRemark()" 
				};

		Map<String, Object> expandJexlContext = new HashMap<String, Object>();
		ExportExcel<UserInfoManageDto> export = new ExportExcel<UserInfoManageDto>();
		export.exportXls("人员信息导出表", headerTableColumns, expandJexlContext,
				dataList, request,response);
	}
	
}
