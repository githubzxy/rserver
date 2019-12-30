package com.enovell.yunwei.km_micor_service.action.productionManage.dayDutyManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.productionManage.dayDutyManage.DayDutyManageService;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

@RestController
@RequestMapping("/dayDutyManageAction")
public class DayDutyManageAction {
	@Resource(name = "dayDutyManageService")
	private DayDutyManageService service;
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
			@RequestParam(name = "leader") String leader, 
			@RequestParam(name = "cadre") String cadre,
			@RequestParam(name = "dispatch") String dispatch,
			@RequestParam(name = "date") String date,
			@RequestParam(name = "orgName") String orgName,
			@RequestParam(name = "orgId") String orgId,
			@RequestParam(name = "docId") String docId,
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId,
			HttpServletRequest request) {
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("creatDateStr", creatDateStr);
		document.put("leader", leader);
		document.put("cadre", cadre);
		document.put("date", date);
		document.put("dispatch", dispatch);
		document.put("orgName", orgName);
		document.put("orgId", orgId);
		document.put("userId", userId);
		
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}

	/**
	 * 根据点击日期查询一条记录
	 * 
	 * @param id
	 * @param collectionName
	 * @return
	 */
	@PostMapping("/findByDate")
	public ResultMsg findDocByDate(@RequestParam("date") String date, @RequestParam("collectionName") String collectionName) {
		Document res = service.findDocumentByDate(date, collectionName);
		return ResultMsg.getSuccess("查询完成", res);
	}

	@PostMapping("/updateDoc")
	public ResultMsg updateDoc(
			@RequestParam(name = "leader") String leader, 
			@RequestParam(name = "cadre") String cadre,
			@RequestParam(name = "dispatch") String dispatch,
			@RequestParam(name = "date") String date,
			@RequestParam(name = "orgName") String orgName,
			@RequestParam(name = "orgId") String orgId,
			@RequestParam(name = "docId") String docId,
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId,
			HttpServletRequest request) {
		Document document = service.findDocumentById(docId, collectionName);
		document.put("leader", leader);
		document.put("cadre", cadre);
		document.put("dispatch", dispatch);
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	
}
