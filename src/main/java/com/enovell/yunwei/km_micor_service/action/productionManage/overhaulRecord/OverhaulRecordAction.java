package com.enovell.yunwei.km_micor_service.action.productionManage.overhaulRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainfzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainzfzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainzjzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.TrainzjzDto;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.ConstructCooperateService;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.util.ActionUtils;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

@RestController
@RequestMapping("/overhaulRecordAction")
public class OverhaulRecordAction {
	@Resource(name = "overhaulRecordService")
	private OverhaulRecordService service;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "constructCooperateService")
	private ConstructCooperateService constructCooperateService;

	/**
	 * getSystemDate 获取当前系统时间
	 * 
	 * @return String
	 */
	@PostMapping("/getSystemDate")
	public String getSystemDate() {
		return JsonUtil.getSystemDate();
	}

	// 线别下拉选
	@PostMapping("/getLines")
	public List<Map<String, String>> getLines() {
		List<String> lineList = constructCooperateService.getLineData();
		List<Map<String, String>> lineDatas = new ArrayList<>();
		for (String string : lineList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			lineDatas.add(map);
		}
		return lineDatas;
	}

	// 机房下拉选
	@PostMapping("/getMachineRooms")
	public List<Map<String, String>> getMachineRooms() {
		List<String> jfList = constructCooperateService.getLineData();
		List<Map<String, String>> jfDatas = new ArrayList<>();
		for (String string : jfList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			jfDatas.add(map);
		}
		return jfDatas;
	}

	// 车间下拉选
	@PostMapping("/getworkShop")
	public List<Map<String, String>> getworkShop() {
		List<String> cjList = service.getWorkShops();
		List<Map<String, String>> cjDatas = new ArrayList<>();
		for (String string : cjList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			cjDatas.add(map);
		}
		return cjDatas;
	}

	// 工区下拉选
	@PostMapping("/getworkArea")
	public List<Map<String, String>> getworkArea(@RequestParam(name = "workshop") String workshop) {
		List<String> gqList = service.getWorAreasByName(workshop);
		List<Map<String, String>> gqDatas = new ArrayList<>();
		for (String string : gqList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			gqDatas.add(map);
		}
		return gqDatas;
	}

	// 根据线别获取机房机房下拉选
	@PostMapping("/getMachineroom")
	public List<Map<String, String>> getMachineroom(@RequestParam(name = "line") String line) {
		List<String> jfList = service.getMachineroomByLine(line);
		List<Map<String, String>> jfDatas = new ArrayList<>();
		for (String string : jfList) {
			Map<String, String> map = new HashMap();
			map.put("text", string);
			map.put("value", string);
			jfDatas.add(map);
		}
		return jfDatas;
	}

	/**
	 * 新增防灾模板
	 * 
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/addHighTrainFZ")
	public ResultMsg addDoc(@RequestParam(name = "fz_first_first_state", required = false) String fz_first_first_state,
			@RequestParam(name = "fz_first_first_remark") String fz_first_first_remark,
			@RequestParam(name = "fz_first_second_state", required = false) String fz_first_second_state,
			@RequestParam(name = "fz_first_second_remark") String fz_first_second_remark,
			@RequestParam(name = "fz_first_third_state", required = false) String fz_first_third_state,
			@RequestParam(name = "fz_first_third_remark") String fz_first_third_remark,
			@RequestParam(name = "fz_first_fourth_state", required = false) String fz_first_fourth_state,
			@RequestParam(name = "fz_first_fourth_remark") String fz_first_fourth_remark,
			@RequestParam(name = "fz_second_first_state", required = false) String fz_second_first_state,
			@RequestParam(name = "fz_second_first_remark") String fz_second_first_remark,
			@RequestParam(name = "fz_second_second_state", required = false) String fz_second_second_state,
			@RequestParam(name = "fz_second_second_remark") String fz_second_second_remark,
			@RequestParam(name = "fz_second_third_state", required = false) String fz_second_third_state,
			@RequestParam(name = "fz_second_third_remark") String fz_second_third_remark,
			@RequestParam(name = "fz_second_fourth_state", required = false) String fz_second_fourth_state,
			@RequestParam(name = "fz_second_fourth_remark") String fz_second_fourth_remark,
			@RequestParam(name = "fz_second_fifth_state", required = false) String fz_second_fifth_state,
			@RequestParam(name = "fz_second_fifth_remark") String fz_second_fifth_remark,
			@RequestParam(name = "fz_third_first_state", required = false) String fz_third_first_state,
			@RequestParam(name = "fz_third_first_remark") String fz_third_first_remark,
			@RequestParam(name = "fz_third_second_state", required = false) String fz_third_second_state,
			@RequestParam(name = "fz_third_second_remark") String fz_third_second_remark,
			@RequestParam(name = "fz_third_third_state", required = false) String fz_third_third_state,
			@RequestParam(name = "fz_third_third_remark") String fz_third_third_remark,
			@RequestParam(name = "fz_third_fourth_state", required = false) String fz_third_fourth_state,
			@RequestParam(name = "fz_third_fourth_remark") String fz_third_fourth_remark,
			@RequestParam(name = "fz_fourth_first_state", required = false) String fz_fourth_first_state,
			@RequestParam(name = "fz_fourth_first_remark") String fz_fourth_first_remark,
			@RequestParam(name = "fz_fourth_second_state", required = false) String fz_fourth_second_state,
			@RequestParam(name = "fz_fourth_second_remark") String fz_fourth_second_remark,
			@RequestParam(name = "fz_fourth_third_state", required = false) String fz_fourth_third_state,
			@RequestParam(name = "fz_fourth_third_remark") String fz_fourth_third_remark,
			@RequestParam(name = "fz_fifth_first_state", required = false) String fz_fifth_first_state,
			@RequestParam(name = "fz_fifth_first_remark") String fz_fifth_first_remark,
			@RequestParam(name = "fz_fifth_second_state", required = false) String fz_fifth_second_state,
			@RequestParam(name = "fz_fifth_second_remark") String fz_fifth_second_remark,
			@RequestParam(name = "fz_fifth_third_state", required = false) String fz_fifth_third_state,
			@RequestParam(name = "fz_fifth_third_remark") String fz_fifth_third_remark,
			@RequestParam(name = "fz_sixth_first_state", required = false) String fz_sixth_first_state,
			@RequestParam(name = "fz_sixth_first_remark") String fz_sixth_first_remark,
			@RequestParam(name = "fz_sixth_second_state", required = false) String fz_sixth_second_state,
			@RequestParam(name = "fz_sixth_second_remark") String fz_sixth_second_remark,
			@RequestParam(name = "fz_sixth_third_state", required = false) String fz_sixth_third_state,
			@RequestParam(name = "fz_sixth_third_remark") String fz_sixth_third_remark,
			@RequestParam(name = "fz_seventh_first_state", required = false) String fz_seventh_first_state,
			@RequestParam(name = "fz_seventh_first_remark") String fz_seventh_first_remark,
			@RequestParam(name = "fz_seventh_second_state", required = false) String fz_seventh_second_state,
			@RequestParam(name = "fz_seventh_second_remark") String fz_seventh_second_remark,
			@RequestParam(name = "fz_seventh_third_state", required = false) String fz_seventh_third_state,
			@RequestParam(name = "fz_seventh_third_remark") String fz_seventh_third_remark,
			@RequestParam(name = "fz_eighth_first_state", required = false) String fz_eighth_first_state,
			@RequestParam(name = "fz_eighth_first_remark") String fz_eighth_first_remark,
			@RequestParam(name = "fz_eighth_second_state", required = false) String fz_eighth_second_state,
			@RequestParam(name = "fz_eighth_second_remark") String fz_eighth_second_remark,
			@RequestParam(name = "fz_eighth_third_state", required = false) String fz_eighth_third_state,
			@RequestParam(name = "fz_eighth_third_remark") String fz_eighth_third_remark,
			@RequestParam(name = "fz_ninth_first_state", required = false) String fz_ninth_first_state,
			@RequestParam(name = "fz_ninth_first_remark") String fz_ninth_first_remark,
			@RequestParam(name = "fz_ninth_second_state", required = false) String fz_ninth_second_state,
			@RequestParam(name = "fz_ninth_second_remark") String fz_ninth_second_remark,
			@RequestParam(name = "fz_tenth_first_state", required = false) String fz_tenth_first_state,
			@RequestParam(name = "fz_tenth_first_remark") String fz_tenth_first_remark,
			@RequestParam(name = "fz_tenth_second_state", required = false) String fz_tenth_second_state,
			@RequestParam(name = "fz_tenth_second_remark") String fz_tenth_second_remark,
			@RequestParam(name = "fz_tenth_third_state", required = false) String fz_tenth_third_state,
			@RequestParam(name = "fz_tenth_third_remark") String fz_tenth_third_remark,
			@RequestParam(name = "fz_tenth_fourth_state", required = false) String fz_tenth_fourth_state,
			@RequestParam(name = "fz_tenth_fourth_remark") String fz_tenth_fourth_remark,
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "line") String line,
			@RequestParam(name = "machineRoom") String machineRoom,
			@RequestParam(name = "collectionName") String collectionName, 
			@RequestParam(name = "userId") String userId,
			HttpServletRequest request) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("overhaulDate", creatDateStr);
		document.put("overhaulName", "高铁防灾设备检修");
		document.put("fz_first_first_state", fz_first_first_state);
		document.put("fz_first_first_remark", fz_first_first_remark);
		document.put("fz_first_second_state", fz_first_second_state);
		document.put("fz_first_second_remark", fz_first_second_remark);
		document.put("fz_first_third_state", fz_first_third_state);
		document.put("fz_first_third_remark", fz_first_third_remark);
		document.put("fz_first_fourth_state", fz_first_fourth_state);
		document.put("fz_first_fourth_remark", fz_first_fourth_remark);
		document.put("fz_second_first_state", fz_second_first_state);
		document.put("fz_second_first_remark", fz_second_first_remark);
		document.put("fz_second_second_state", fz_second_second_state);
		document.put("fz_second_second_remark", fz_second_second_remark);
		document.put("fz_second_third_state", fz_second_third_state);
		document.put("fz_second_third_remark", fz_second_third_remark);
		document.put("fz_second_fourth_state", fz_second_fourth_state);
		document.put("fz_second_fourth_remark", fz_second_fourth_remark);
		document.put("fz_second_fifth_state", fz_second_fifth_state);
		document.put("fz_second_fifth_remark", fz_second_fifth_remark);
		document.put("fz_third_first_state", fz_third_first_state);
		document.put("fz_third_first_remark", fz_third_first_remark);
		document.put("fz_third_second_state", fz_third_second_state);
		document.put("fz_third_second_remark", fz_third_second_remark);
		document.put("fz_third_third_state", fz_third_third_state);
		document.put("fz_third_third_remark", fz_third_third_remark);
		document.put("fz_third_fourth_state", fz_third_fourth_state);
		document.put("fz_third_fourth_remark", fz_third_fourth_remark);
		document.put("fz_fourth_first_state", fz_fourth_first_state);
		document.put("fz_fourth_first_remark", fz_fourth_first_remark);
		document.put("fz_fourth_second_state", fz_fourth_second_state);
		document.put("fz_fourth_second_remark", fz_fourth_second_remark);
		document.put("fz_fourth_third_state", fz_fourth_third_state);
		document.put("fz_fourth_third_remark", fz_fourth_third_remark);
		document.put("fz_fifth_first_state", fz_fifth_first_state);
		document.put("fz_fifth_first_remark", fz_fifth_first_remark);
		document.put("fz_fifth_second_state", fz_fifth_second_state);
		document.put("fz_fifth_second_remark", fz_fifth_second_remark);
		document.put("fz_fifth_third_state", fz_fifth_third_state);
		document.put("fz_fifth_third_remark", fz_fifth_third_remark);
		document.put("fz_sixth_first_state", fz_sixth_first_state);
		document.put("fz_sixth_first_remark", fz_sixth_first_remark);
		document.put("fz_sixth_second_state", fz_sixth_second_state);
		document.put("fz_sixth_second_remark", fz_sixth_second_remark);
		document.put("fz_sixth_third_state", fz_sixth_third_state);
		document.put("fz_sixth_third_remark", fz_sixth_third_remark);
		document.put("fz_seventh_first_state", fz_seventh_first_state);
		document.put("fz_seventh_first_remark", fz_seventh_first_remark);
		document.put("fz_seventh_second_state", fz_seventh_second_state);
		document.put("fz_seventh_second_remark", fz_seventh_second_remark);
		document.put("fz_seventh_third_state", fz_seventh_third_state);
		document.put("fz_seventh_third_remark", fz_seventh_third_remark);
		document.put("fz_eighth_first_state", fz_eighth_first_state);
		document.put("fz_eighth_first_remark", fz_eighth_first_remark);
		document.put("fz_eighth_second_state", fz_eighth_second_state);
		document.put("fz_eighth_second_remark", fz_eighth_second_remark);
		document.put("fz_eighth_third_state", fz_eighth_third_state);
		document.put("fz_eighth_third_remark", fz_eighth_third_remark);
		document.put("fz_ninth_first_state", fz_ninth_first_state);
		document.put("fz_ninth_first_remark", fz_ninth_first_remark);
		document.put("fz_ninth_second_state", fz_ninth_second_state);
		document.put("fz_ninth_second_remark", fz_ninth_second_remark);
		document.put("fz_tenth_first_state", fz_tenth_first_state);
		document.put("fz_tenth_first_remark", fz_tenth_first_remark);
		document.put("fz_tenth_second_state", fz_tenth_second_state);
		document.put("fz_tenth_second_remark", fz_tenth_second_remark);
		document.put("fz_tenth_third_state", fz_tenth_third_state);
		document.put("fz_tenth_third_remark", fz_tenth_third_remark);
		document.put("fz_tenth_fourth_state", fz_tenth_fourth_state);
		document.put("fz_tenth_fourth_remark", fz_tenth_fourth_remark);
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("line", line);
		document.put("machineRoom", machineRoom);
		document.put("userId", userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		document.put("orgId", org.get("ORG_ID_"));
		document.put("overhaulPerson", org.get("ORG_NAME_"));
		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);

	}
	@PostMapping("/addHighTrainZFZ")
	public ResultMsg addDocZFZ(@RequestParam(name = "zfz_first_first_state", required = false) String zfz_first_first_state,
			@RequestParam(name = "zfz_first_first_remark") String zfz_first_first_remark,
			@RequestParam(name = "zfz_first_second_state", required = false) String zfz_first_second_state,
			@RequestParam(name = "zfz_first_second_remark") String zfz_first_second_remark,
			@RequestParam(name = "zfz_first_third_state", required = false) String zfz_first_third_state,
			@RequestParam(name = "zfz_first_third_remark") String zfz_first_third_remark,
			@RequestParam(name = "zfz_first_fourth_state", required = false) String zfz_first_fourth_state,
			@RequestParam(name = "zfz_first_fourth_remark") String zfz_first_fourth_remark,
			
			@RequestParam(name = "zfz_second_first_state", required = false) String zfz_second_first_state,
			@RequestParam(name = "zfz_second_first_remark") String zfz_second_first_remark,
			@RequestParam(name = "zfz_second_second_state", required = false) String zfz_second_second_state,
			@RequestParam(name = "zfz_second_second_remark") String zfz_second_second_remark,
			@RequestParam(name = "zfz_second_third_state", required = false) String zfz_second_third_state,
			@RequestParam(name = "zfz_second_third_remark") String zfz_second_third_remark,
			
			@RequestParam(name = "zfz_third_first_state", required = false) String zfz_third_first_state,
			@RequestParam(name = "zfz_third_first_remark") String zfz_third_first_remark,
			@RequestParam(name = "zfz_third_second_state", required = false) String zfz_third_second_state,
			@RequestParam(name = "zfz_third_second_remark") String zfz_third_second_remark,
			@RequestParam(name = "zfz_third_third_state", required = false) String zfz_third_third_state,
			@RequestParam(name = "zfz_third_third_remark") String zfz_third_third_remark,
			@RequestParam(name = "zfz_third_fourth_state", required = false) String zfz_third_fourth_state,
			@RequestParam(name = "zfz_third_fourth_remark") String zfz_third_fourth_remark,
			@RequestParam(name = "zfz_third_fifth_state", required = false) String zfz_third_fifth_state,
			@RequestParam(name = "zfz_third_fifth_remark") String zfz_third_fifth_remark,
			
			@RequestParam(name = "zfz_fourth_first_state", required = false) String zfz_fourth_first_state,
			@RequestParam(name = "zfz_fourth_first_remark") String zfz_fourth_first_remark,
			@RequestParam(name = "zfz_fourth_second_state", required = false) String zfz_fourth_second_state,
			@RequestParam(name = "zfz_fourth_second_remark") String zfz_fourth_second_remark,
			@RequestParam(name = "zfz_fourth_third_state", required = false) String zfz_fourth_third_state,
			@RequestParam(name = "zfz_fourth_third_remark") String zfz_fourth_third_remark,
			@RequestParam(name = "zfz_fourth_fourth_state", required = false) String zfz_fourth_fourth_state,
			@RequestParam(name = "zfz_fourth_fourth_remark") String zfz_fourth_fourth_remark,
			@RequestParam(name = "zfz_fourth_fifth_state", required = false) String zfz_fourth_fifth_state,
			@RequestParam(name = "zfz_fourth_fifth_remark") String zfz_fourth_fifth_remark,
			@RequestParam(name = "zfz_fourth_sixth_state", required = false) String zfz_fourth_sixth_state,
			@RequestParam(name = "zfz_fourth_sixth_remark") String zfz_fourth_sixth_remark,
			@RequestParam(name = "zfz_fourth_seventh_state", required = false) String zfz_fourth_seventh_state,
			@RequestParam(name = "zfz_fourth_seventh_remark") String zfz_fourth_seventh_remark,
			@RequestParam(name = "zfz_fourth_eighth_state", required = false) String zfz_fourth_eighth_state,
			@RequestParam(name = "zfz_fourth_eighth_remark") String zfz_fourth_eighth_remark,
			
			@RequestParam(name = "zfz_fifth_first_state", required = false) String zfz_fifth_first_state,
			@RequestParam(name = "zfz_fifth_first_remark") String zfz_fifth_first_remark,
			@RequestParam(name = "zfz_fifth_second_state", required = false) String zfz_fifth_second_state,
			@RequestParam(name = "zfz_fifth_second_remark") String zfz_fifth_second_remark,
			@RequestParam(name = "zfz_fifth_third_state", required = false) String zfz_fifth_third_state,
			@RequestParam(name = "zfz_fifth_third_remark") String zfz_fifth_third_remark,
			
			@RequestParam(name = "zfz_sixth_first_state", required = false) String zfz_sixth_first_state,
			@RequestParam(name = "zfz_sixth_first_remark") String zfz_sixth_first_remark,
			@RequestParam(name = "zfz_sixth_second_state", required = false) String zfz_sixth_second_state,
			@RequestParam(name = "zfz_sixth_second_remark") String zfz_sixth_second_remark,
			@RequestParam(name = "zfz_sixth_third_state", required = false) String zfz_sixth_third_state,
			@RequestParam(name = "zfz_sixth_third_remark") String zfz_sixth_third_remark,
			
			@RequestParam(name = "zfz_seventh_first_state", required = false) String zfz_seventh_first_state,
			@RequestParam(name = "zfz_seventh_first_remark") String zfz_seventh_first_remark,
			@RequestParam(name = "zfz_seventh_second_state", required = false) String zfz_seventh_second_state,
			@RequestParam(name = "zfz_seventh_second_remark") String zfz_seventh_second_remark,
			@RequestParam(name = "zfz_seventh_third_state", required = false) String zfz_seventh_third_state,
			@RequestParam(name = "zfz_seventh_third_remark") String zfz_seventh_third_remark,
			
			@RequestParam(name = "zfz_eighth_first_state", required = false) String zfz_eighth_first_state,
			@RequestParam(name = "zfz_eighth_first_remark") String zfz_eighth_first_remark,
			@RequestParam(name = "zfz_eighth_second_state", required = false) String zfz_eighth_second_state,
			@RequestParam(name = "zfz_eighth_second_remark") String zfz_eighth_second_remark,
			@RequestParam(name = "zfz_eighth_third_state", required = false) String zfz_eighth_third_state,
			@RequestParam(name = "zfz_eighth_third_remark") String zfz_eighth_third_remark,
			
			@RequestParam(name = "zfz_ninth_first_state", required = false) String zfz_ninth_first_state,
			@RequestParam(name = "zfz_ninth_first_remark") String zfz_ninth_first_remark,
			@RequestParam(name = "zfz_ninth_second_state", required = false) String zfz_ninth_second_state,
			@RequestParam(name = "zfz_ninth_second_remark") String zfz_ninth_second_remark,
			@RequestParam(name = "zfz_ninth_third_state", required = false) String zfz_ninth_third_state,
			@RequestParam(name = "zfz_ninth_third_remark") String zfz_ninth_third_remark,
			@RequestParam(name = "zfz_ninth_fourth_state", required = false) String zfz_ninth_fourth_state,
			@RequestParam(name = "zfz_ninth_fourth_remark") String zfz_ninth_fourth_remark,
			@RequestParam(name = "zfz_ninth_fifth_state", required = false) String zfz_ninth_fifth_state,
			@RequestParam(name = "zfz_ninth_fifth_remark") String zfz_ninth_fifth_remark,
			
			
			
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "line") String line,
			@RequestParam(name = "machineRoom") String machineRoom,
			@RequestParam(name = "collectionName") String collectionName, 
			@RequestParam(name = "userId") String userId,
			HttpServletRequest request) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("overhaulDate", creatDateStr);
		document.put("overhaulName", "直放站通信设备检修");
		document.put("zfz_first_first_state", zfz_first_first_state);
		document.put("zfz_first_first_remark", zfz_first_first_remark);
		document.put("zfz_first_second_state", zfz_first_second_state);
		document.put("zfz_first_second_remark", zfz_first_second_remark);
		document.put("zfz_first_third_state", zfz_first_third_state);
		document.put("zfz_first_third_remark", zfz_first_third_remark);
		document.put("zfz_first_fourth_state", zfz_first_fourth_state);
		document.put("zfz_first_fourth_remark", zfz_first_fourth_remark);
		
		document.put("zfz_second_first_state", zfz_second_first_state);
		document.put("zfz_second_first_remark", zfz_second_first_remark);
		document.put("zfz_second_second_state", zfz_second_second_state);
		document.put("zfz_second_second_remark", zfz_second_second_remark);
		document.put("zfz_second_third_state", zfz_second_third_state);
		document.put("zfz_second_third_remark", zfz_second_third_remark);
		
		document.put("zfz_third_first_state", zfz_third_first_state);
		document.put("zfz_third_first_remark", zfz_third_first_remark);
		document.put("zfz_third_second_state", zfz_third_second_state);
		document.put("zfz_third_second_remark", zfz_third_second_remark);
		document.put("zfz_third_third_state", zfz_third_third_state);
		document.put("zfz_third_third_remark", zfz_third_third_remark);
		document.put("zfz_third_fourth_state", zfz_third_fourth_state);
		document.put("zfz_third_fourth_remark", zfz_third_fourth_remark);
		document.put("zfz_third_fifth_state", zfz_third_fifth_state);
		document.put("zfz_third_fifth_remark", zfz_third_fifth_remark);
		
		document.put("zfz_fourth_first_state", zfz_fourth_first_state);
		document.put("zfz_fourth_first_remark", zfz_fourth_first_remark);
		document.put("zfz_fourth_second_state", zfz_fourth_second_state);
		document.put("zfz_fourth_second_remark", zfz_fourth_second_remark);
		document.put("zfz_fourth_third_state", zfz_fourth_third_state);
		document.put("zfz_fourth_third_remark", zfz_fourth_third_remark);
		document.put("zfz_fourth_fourth_state", zfz_fourth_fourth_state);
		document.put("zfz_fourth_fourth_remark", zfz_fourth_fourth_remark);
		document.put("zfz_fourth_fifth_state", zfz_fourth_fifth_state);
		document.put("zfz_fourth_fifth_remark", zfz_fourth_fifth_remark);
		document.put("zfz_fourth_sixth_state", zfz_fourth_sixth_state);
		document.put("zfz_fourth_sixth_remark", zfz_fourth_sixth_remark);
		document.put("zfz_fourth_seventh_state", zfz_fourth_seventh_state);
		document.put("zfz_fourth_seventh_remark", zfz_fourth_seventh_remark);
		document.put("zfz_fourth_eighth_state", zfz_fourth_eighth_state);
		document.put("zfz_fourth_eighth_remark", zfz_fourth_eighth_remark);
		
		document.put("zfz_fifth_first_state", zfz_fifth_first_state);
		document.put("zfz_fifth_first_remark", zfz_fifth_first_remark);
		document.put("zfz_fifth_second_state", zfz_fifth_second_state);
		document.put("zfz_fifth_second_remark", zfz_fifth_second_remark);
		document.put("zfz_fifth_third_state", zfz_fifth_third_state);
		document.put("zfz_fifth_third_remark", zfz_fifth_third_remark);
		
		document.put("zfz_sixth_first_state", zfz_sixth_first_state);
		document.put("zfz_sixth_first_remark", zfz_sixth_first_remark);
		document.put("zfz_sixth_second_state", zfz_sixth_second_state);
		document.put("zfz_sixth_second_remark", zfz_sixth_second_remark);
		document.put("zfz_sixth_third_state", zfz_sixth_third_state);
		document.put("zfz_sixth_third_remark", zfz_sixth_third_remark);
		
		document.put("zfz_seventh_first_state", zfz_seventh_first_state);
		document.put("zfz_seventh_first_remark", zfz_seventh_first_remark);
		document.put("zfz_seventh_second_state", zfz_seventh_second_state);
		document.put("zfz_seventh_second_remark", zfz_seventh_second_remark);
		document.put("zfz_seventh_third_state", zfz_seventh_third_state);
		document.put("zfz_seventh_third_remark", zfz_seventh_third_remark);
		
		document.put("zfz_eighth_first_state", zfz_eighth_first_state);
		document.put("zfz_eighth_first_remark", zfz_eighth_first_remark);
		document.put("zfz_eighth_second_state", zfz_eighth_second_state);
		document.put("zfz_eighth_second_remark", zfz_eighth_second_remark);
		document.put("zfz_eighth_third_state", zfz_eighth_third_state);
		document.put("zfz_eighth_third_remark", zfz_eighth_third_remark);
		
		document.put("zfz_ninth_first_state", zfz_ninth_first_state);
		document.put("zfz_ninth_first_remark", zfz_ninth_first_remark);
		document.put("zfz_ninth_second_state", zfz_ninth_second_state);
		document.put("zfz_ninth_second_remark", zfz_ninth_second_remark);
		document.put("zfz_ninth_third_state", zfz_ninth_third_state);
		document.put("zfz_ninth_third_remark", zfz_ninth_third_remark);
		document.put("zfz_ninth_fourth_state", zfz_ninth_fourth_state);
		document.put("zfz_ninth_fourth_remark", zfz_ninth_fourth_remark);
		document.put("zfz_ninth_fifth_state", zfz_ninth_fifth_state);
		document.put("zfz_ninth_fifth_remark", zfz_ninth_fifth_remark);
		
		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("line", line);
		document.put("machineRoom", machineRoom);
		document.put("userId", userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		document.put("orgId", org.get("ORG_ID_"));
		document.put("overhaulPerson", org.get("ORG_NAME_"));
		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);

	}
	/**
	 * 新增高铁中间站模板
	 * 
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/addHighTrainZJZ")
	public ResultMsg addDocZJZ(
			@RequestParam(name = "htzjz_first_first_state", required = false) String htzjz_first_first_state,
			@RequestParam(name = "htzjz_first_first_remark") String htzjz_first_first_remark,
			@RequestParam(name = "htzjz_first_second_state", required = false) String htzjz_first_second_state,
			@RequestParam(name = "htzjz_first_second_remark") String htzjz_first_second_remark,
			@RequestParam(name = "htzjz_first_third_state", required = false) String htzjz_first_third_state,
			@RequestParam(name = "htzjz_first_third_remark") String htzjz_first_third_remark,
			@RequestParam(name = "htzjz_first_fourth_state", required = false) String htzjz_first_fourth_state,
			@RequestParam(name = "htzjz_first_fourth_remark") String htzjz_first_fourth_remark,
			
			@RequestParam(name = "htzjz_second_first_state", required = false) String htzjz_second_first_state,
			@RequestParam(name = "htzjz_second_first_remark") String htzjz_second_first_remark,
			@RequestParam(name = "htzjz_second_second_state", required = false) String htzjz_second_second_state,
			@RequestParam(name = "htzjz_second_second_remark") String htzjz_second_second_remark,
			@RequestParam(name = "htzjz_second_third_state", required = false) String htzjz_second_third_state,
			@RequestParam(name = "htzjz_second_third_remark") String htzjz_second_third_remark,
			@RequestParam(name = "htzjz_second_fourth_state", required = false) String htzjz_second_fourth_state,
			@RequestParam(name = "htzjz_second_fourth_remark") String htzjz_second_fourth_remark,
			@RequestParam(name = "htzjz_second_fifth_state", required = false) String htzjz_second_fifth_state,
			@RequestParam(name = "htzjz_second_fifth_remark") String htzjz_second_fifth_remark,
			@RequestParam(name = "htzjz_second_sixth_state", required = false) String htzjz_second_sixth_state,
			@RequestParam(name = "htzjz_second_sixth_remark") String htzjz_second_sixth_remark,
			@RequestParam(name = "htzjz_second_seventh_state", required = false) String htzjz_second_seventh_state,
			@RequestParam(name = "htzjz_second_seventh_remark") String htzjz_second_seventh_remark,
			
			@RequestParam(name = "htzjz_third_first_state", required = false) String htzjz_third_first_state,
			@RequestParam(name = "htzjz_third_first_remark") String htzjz_third_first_remark,
			@RequestParam(name = "htzjz_third_second_state", required = false) String htzjz_third_second_state,
			@RequestParam(name = "htzjz_third_second_remark") String htzjz_third_second_remark,
			
			@RequestParam(name = "htzjz_fourth_first_state", required = false) String htzjz_fourth_first_state,
			@RequestParam(name = "htzjz_fourth_first_remark") String htzjz_fourth_first_remark,
			@RequestParam(name = "htzjz_fourth_second_state", required = false) String htzjz_fourth_second_state,
			@RequestParam(name = "htzjz_fourth_second_remark") String htzjz_fourth_second_remark,
			@RequestParam(name = "htzjz_fourth_third_state", required = false) String htzjz_fourth_third_state,
			@RequestParam(name = "htzjz_fourth_third_remark") String htzjz_fourth_third_remark,
			@RequestParam(name = "htzjz_fourth_fourth_state", required = false) String htzjz_fourth_fourth_state,
			@RequestParam(name = "htzjz_fourth_fourth_remark") String htzjz_fourth_fourth_remark,
			@RequestParam(name = "htzjz_fourth_fifth_state", required = false) String htzjz_fourth_fifth_state,
			@RequestParam(name = "htzjz_fourth_fifth_remark") String htzjz_fourth_fifth_remark,
			@RequestParam(name = "htzjz_fourth_sixth_state", required = false) String htzjz_fourth_sixth_state,
			@RequestParam(name = "htzjz_fourth_sixth_remark") String htzjz_fourth_sixth_remark,
			@RequestParam(name = "htzjz_fourth_seventh_state", required = false) String htzjz_fourth_seventh_state,
			@RequestParam(name = "htzjz_fourth_seventh_remark") String htzjz_fourth_seventh_remark,
			
			@RequestParam(name = "htzjz_fifth_first_state", required = false) String htzjz_fifth_first_state,
			@RequestParam(name = "htzjz_fifth_first_remark") String htzjz_fifth_first_remark,
			@RequestParam(name = "htzjz_fifth_second_state", required = false) String htzjz_fifth_second_state,
			@RequestParam(name = "htzjz_fifth_second_remark") String htzjz_fifth_second_remark,
			@RequestParam(name = "htzjz_fifth_third_state", required = false) String htzjz_fifth_third_state,
			@RequestParam(name = "htzjz_fifth_third_remark") String htzjz_fifth_third_remark,
			@RequestParam(name = "htzjz_fifth_fourth_state", required = false) String htzjz_fifth_fourth_state,
			@RequestParam(name = "htzjz_fifth_fourth_remark") String htzjz_fifth_fourth_remark,
			@RequestParam(name = "htzjz_fifth_fifth_state", required = false) String htzjz_fifth_fifth_state,
			@RequestParam(name = "htzjz_fifth_fifth_remark") String htzjz_fifth_fifth_remark,
			
			@RequestParam(name = "htzjz_sixth_first_state", required = false) String htzjz_sixth_first_state,
			@RequestParam(name = "htzjz_sixth_first_remark") String htzjz_sixth_first_remark,
			@RequestParam(name = "htzjz_sixth_second_state", required = false) String htzjz_sixth_second_state,
			@RequestParam(name = "htzjz_sixth_second_remark") String htzjz_sixth_second_remark,
			@RequestParam(name = "htzjz_sixth_third_state", required = false) String htzjz_sixth_third_state,
			@RequestParam(name = "htzjz_sixth_third_remark") String htzjz_sixth_third_remark,
			
			@RequestParam(name = "htzjz_seventh_first_state", required = false) String htzjz_seventh_first_state,
			@RequestParam(name = "htzjz_seventh_first_remark") String htzjz_seventh_first_remark,
			@RequestParam(name = "htzjz_seventh_second_state", required = false) String htzjz_seventh_second_state,
			@RequestParam(name = "htzjz_seventh_second_remark") String htzjz_seventh_second_remark,
			@RequestParam(name = "htzjz_seventh_third_state", required = false) String htzjz_seventh_third_state,
			@RequestParam(name = "htzjz_seventh_third_remark") String htzjz_seventh_third_remark,
			
			@RequestParam(name = "htzjz_eighth_first_state", required = false) String htzjz_eighth_first_state,
			@RequestParam(name = "htzjz_eighth_first_remark") String htzjz_eighth_first_remark,
			@RequestParam(name = "htzjz_eighth_second_state", required = false) String htzjz_eighth_second_state,
			@RequestParam(name = "htzjz_eighth_second_remark") String htzjz_eighth_second_remark,
			@RequestParam(name = "htzjz_eighth_third_state", required = false) String htzjz_eighth_third_state,
			@RequestParam(name = "htzjz_eighth_third_remark") String htzjz_eighth_third_remark,
			@RequestParam(name = "htzjz_eighth_fourth_state", required = false) String htzjz_eighth_fourth_state,
			@RequestParam(name = "htzjz_eighth_fourth_remark") String htzjz_eighth_fourth_remark,
			
			@RequestParam(name = "htzjz_ninth_first_state", required = false) String htzjz_ninth_first_state,
			@RequestParam(name = "htzjz_ninth_first_remark") String htzjz_ninth_first_remark,
			@RequestParam(name = "htzjz_ninth_second_state", required = false) String htzjz_ninth_second_state,
			@RequestParam(name = "htzjz_ninth_second_remark") String htzjz_ninth_second_remark,
			@RequestParam(name = "htzjz_ninth_third_state", required = false) String htzjz_ninth_third_state,
			@RequestParam(name = "htzjz_ninth_third_remark") String htzjz_ninth_third_remark,
			@RequestParam(name = "htzjz_ninth_fourth_state", required = false) String htzjz_ninth_fourth_state,
			@RequestParam(name = "htzjz_ninth_fourth_remark") String htzjz_ninth_fourth_remark,
			@RequestParam(name = "htzjz_ninth_fifth_state", required = false) String htzjz_ninth_fifth_state,
			@RequestParam(name = "htzjz_ninth_fifth_remark") String htzjz_ninth_fifth_remark,
			@RequestParam(name = "htzjz_ninth_sixth_state", required = false) String htzjz_ninth_sixth_state,
			@RequestParam(name = "htzjz_ninth_sixth_remark") String htzjz_ninth_sixth_remark,
			@RequestParam(name = "htzjz_ninth_seventh_state", required = false) String htzjz_ninth_seventh_state,
			@RequestParam(name = "htzjz_ninth_seventh_remark") String htzjz_ninth_seventh_remark,
			
			@RequestParam(name = "htzjz_tenth_first_state", required = false) String htzjz_tenth_first_state,
			@RequestParam(name = "htzjz_tenth_first_remark") String htzjz_tenth_first_remark,
			@RequestParam(name = "htzjz_tenth_second_state", required = false) String htzjz_tenth_second_state,
			@RequestParam(name = "htzjz_tenth_second_remark") String htzjz_tenth_second_remark,
			@RequestParam(name = "htzjz_tenth_third_state", required = false) String htzjz_tenth_third_state,
			@RequestParam(name = "htzjz_tenth_third_remark") String htzjz_tenth_third_remark,
			
			@RequestParam(name = "htzjz_eleventh_first_state", required = false) String htzjz_eleventh_first_state,
			@RequestParam(name = "htzjz_eleventh_first_remark") String htzjz_eleventh_first_remark,
			@RequestParam(name = "htzjz_eleventh_second_state", required = false) String htzjz_eleventh_second_state,
			@RequestParam(name = "htzjz_eleventh_second_remark") String htzjz_eleventh_second_remark,
			@RequestParam(name = "htzjz_eleventh_third_state", required = false) String htzjz_eleventh_third_state,
			@RequestParam(name = "htzjz_eleventh_third_remark") String htzjz_eleventh_third_remark,
			
			@RequestParam(name = "htzjz_twelfth_first_state", required = false) String htzjz_twelfth_first_state,
			@RequestParam(name = "htzjz_twelfth_first_remark") String htzjz_twelfth_first_remark,
			@RequestParam(name = "htzjz_twelfth_second_state", required = false) String htzjz_twelfth_second_state,
			@RequestParam(name = "htzjz_twelfth_second_remark") String htzjz_twelfth_second_remark,
			@RequestParam(name = "htzjz_twelfth_third_state", required = false) String htzjz_twelfth_third_state,
			@RequestParam(name = "htzjz_twelfth_third_remark") String htzjz_twelfth_third_remark,
			@RequestParam(name = "htzjz_twelfth_fourth_state", required = false) String htzjz_twelfth_fourth_state,
			@RequestParam(name = "htzjz_twelfth_fourth_remark") String htzjz_twelfth_fourth_remark,
			
			@RequestParam(name = "htzjz_thirteenth_first_state", required = false) String htzjz_thirteenth_first_state,
			@RequestParam(name = "htzjz_thirteenth_first_remark") String htzjz_thirteenth_first_remark,
			@RequestParam(name = "htzjz_thirteenth_second_state", required = false) String htzjz_thirteenth_second_state,
			@RequestParam(name = "htzjz_thirteenth_second_remark") String htzjz_thirteenth_second_remark,
			@RequestParam(name = "htzjz_thirteenth_third_state", required = false) String htzjz_thirteenth_third_state,
			@RequestParam(name = "htzjz_thirteenth_third_remark") String htzjz_thirteenth_third_remark,
			
			@RequestParam(name = "htzjz_fourteenth_first_state", required = false) String htzjz_fourteenth_first_state,
			@RequestParam(name = "htzjz_fourteenth_first_remark") String htzjz_fourteenth_first_remark,
			@RequestParam(name = "htzjz_fourteenth_second_state", required = false) String htzjz_fourteenth_second_state,
			@RequestParam(name = "htzjz_fourteenth_second_remark") String htzjz_fourteenth_second_remark,
			@RequestParam(name = "htzjz_fourteenth_third_state", required = false) String htzjz_fourteenth_third_state,
			@RequestParam(name = "htzjz_fourteenth_third_remark") String htzjz_fourteenth_third_remark,
			
			@RequestParam(name = "htzjz_fifteenth_first_state", required = false) String htzjz_fifteenth_first_state,
			@RequestParam(name = "htzjz_fifteenth_first_remark") String htzjz_fifteenth_first_remark,
			@RequestParam(name = "htzjz_fifteenth_second_state", required = false) String htzjz_fifteenth_second_state,
			@RequestParam(name = "htzjz_fifteenth_second_remark") String htzjz_fifteenth_second_remark,
			@RequestParam(name = "htzjz_fifteenth_third_state", required = false) String htzjz_fifteenth_third_state,
			@RequestParam(name = "htzjz_fifteenth_third_remark") String htzjz_fifteenth_third_remark,
			
			@RequestParam(name = "htzjz_sixteenth_first_state", required = false) String htzjz_sixteenth_first_state,
			@RequestParam(name = "htzjz_sixteenth_first_remark") String htzjz_sixteenth_first_remark,
			@RequestParam(name = "htzjz_sixteenth_second_state", required = false) String htzjz_sixteenth_second_state,
			@RequestParam(name = "htzjz_sixteenth_second_remark") String htzjz_sixteenth_second_remark,
			@RequestParam(name = "htzjz_sixteenth_third_state", required = false) String htzjz_sixteenth_third_state,
			@RequestParam(name = "htzjz_sixteenth_third_remark") String htzjz_sixteenth_third_remark,
			
			@RequestParam(name = "htzjz_seventeenth_first_state", required = false) String htzjz_seventeenth_first_state,
			@RequestParam(name = "htzjz_seventeenth_first_remark") String htzjz_seventeenth_first_remark,
			@RequestParam(name = "htzjz_seventeenth_second_state", required = false) String htzjz_seventeenth_second_state,
			@RequestParam(name = "htzjz_seventeenth_second_remark") String htzjz_seventeenth_second_remark,
			@RequestParam(name = "htzjz_seventeenth_third_state", required = false) String htzjz_seventeenth_third_state,
			@RequestParam(name = "htzjz_seventeenth_third_remark") String htzjz_seventeenth_third_remark,
			@RequestParam(name = "htzjz_seventeenth_fourth_state", required = false) String htzjz_seventeenth_fourth_state,
			@RequestParam(name = "htzjz_seventeenth_fourth_remark") String htzjz_seventeenth_fourth_remark, 
			
			
			@RequestParam(name = "checkPeople") String checkPeople,
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "line") String line,
			@RequestParam(name = "machineRoom") String machineRoom,
			@RequestParam(name = "collectionName") String collectionName, 
			@RequestParam(name = "userId") String userId,
			HttpServletRequest request) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("overhaulDate", creatDateStr);
		document.put("overhaulName", "高铁中间站设备检修");
		document.put( "htzjz_first_first_state",htzjz_first_first_state);
		document.put( "htzjz_first_first_remark",htzjz_first_first_remark);
		document.put( "htzjz_first_second_state",htzjz_first_second_state);
		document.put( "htzjz_first_second_remark",htzjz_first_second_remark);
		document.put( "htzjz_first_third_state", htzjz_first_third_state);
		document.put( "htzjz_first_third_remark", htzjz_first_third_remark);
		document.put( "htzjz_first_fourth_state", htzjz_first_fourth_state);
		document.put( "htzjz_first_fourth_remark", htzjz_first_fourth_remark);
		
		document.put( "htzjz_second_first_state", htzjz_second_first_state);
		document.put( "htzjz_second_first_remark", htzjz_second_first_remark);
		document.put( "htzjz_second_second_state", htzjz_second_second_state);
		document.put( "htzjz_second_second_remark", htzjz_second_second_remark);
		document.put( "htzjz_second_third_state", htzjz_second_third_state);
		document.put( "htzjz_second_third_remark", htzjz_second_third_remark);
		document.put( "htzjz_second_fourth_state", htzjz_second_fourth_state);
		document.put( "htzjz_second_fourth_remark", htzjz_second_fourth_remark);
		document.put( "htzjz_second_fifth_state", htzjz_second_fifth_state);
		document.put( "htzjz_second_fifth_remark", htzjz_second_fifth_remark);
		document.put( "htzjz_second_sixth_state", htzjz_second_sixth_state);
		document.put( "htzjz_second_sixth_remark", htzjz_second_sixth_remark);
		document.put( "htzjz_second_seventh_state", htzjz_second_seventh_state);
		document.put( "htzjz_second_seventh_remark", htzjz_second_seventh_remark);
		
		document.put( "htzjz_third_first_state", htzjz_third_first_state);
		document.put( "htzjz_third_first_remark", htzjz_third_first_remark);
		document.put( "htzjz_third_second_state", htzjz_third_second_state);
		document.put( "htzjz_third_second_remark", htzjz_third_second_remark);
		
		document.put( "htzjz_fourth_first_state", htzjz_fourth_first_state);
		document.put( "htzjz_fourth_first_remark", htzjz_fourth_first_remark);
		document.put( "htzjz_fourth_second_state", htzjz_fourth_second_state);
		document.put( "htzjz_fourth_second_remark", htzjz_fourth_second_remark);
		document.put( "htzjz_fourth_third_state", htzjz_fourth_third_state);
		document.put( "htzjz_fourth_third_remark", htzjz_fourth_third_remark);
		document.put( "htzjz_fourth_fourth_state", htzjz_fourth_fourth_state);
		document.put( "htzjz_fourth_fourth_remark", htzjz_fourth_fourth_remark);
		document.put( "htzjz_fourth_fifth_state", htzjz_fourth_fifth_state);
		document.put( "htzjz_fourth_fifth_remark", htzjz_fourth_fifth_remark);
		document.put( "htzjz_fourth_sixth_state", htzjz_fourth_sixth_state);
		document.put( "htzjz_fourth_sixth_remark", htzjz_fourth_sixth_remark);
		document.put( "htzjz_fourth_seventh_state", htzjz_fourth_seventh_state);
		document.put( "htzjz_fourth_seventh_remark", htzjz_fourth_seventh_remark);
		
		document.put( "htzjz_fifth_first_state", htzjz_fifth_first_state);
		document.put( "htzjz_fifth_first_remark", htzjz_fifth_first_remark);
		document.put( "htzjz_fifth_second_state", htzjz_fifth_second_state);
		document.put( "htzjz_fifth_second_remark", htzjz_fifth_second_remark);
		document.put( "htzjz_fifth_third_state", htzjz_fifth_third_state);
		document.put( "htzjz_fifth_third_remark", htzjz_fifth_third_remark);
		document.put( "htzjz_fifth_fourth_state", htzjz_fifth_fourth_state);
		document.put( "htzjz_fifth_fourth_remark", htzjz_fifth_fourth_remark);
		document.put( "htzjz_fifth_fifth_state", htzjz_fifth_fifth_state);
		document.put( "htzjz_fifth_fifth_remark", htzjz_fifth_fifth_remark);
		
		document.put( "htzjz_sixth_first_state", htzjz_sixth_first_state);
		document.put( "htzjz_sixth_first_remark", htzjz_sixth_first_remark);
		document.put( "htzjz_sixth_second_state", htzjz_sixth_second_state);
		document.put( "htzjz_sixth_second_remark", htzjz_sixth_second_remark);
		document.put( "htzjz_sixth_third_state", htzjz_sixth_third_state);
		document.put( "htzjz_sixth_third_remark", htzjz_sixth_third_remark);
		
		document.put( "htzjz_seventh_first_state", htzjz_seventh_first_state);
		document.put( "htzjz_seventh_first_remark", htzjz_seventh_first_remark);
		document.put( "htzjz_seventh_second_state", htzjz_seventh_second_state);
		document.put( "htzjz_seventh_second_remark", htzjz_seventh_second_remark);
		document.put( "htzjz_seventh_third_state", htzjz_seventh_third_state);
		document.put( "htzjz_seventh_third_remark", htzjz_seventh_third_remark);
		
		document.put( "htzjz_eighth_first_state", htzjz_eighth_first_state);
		document.put( "htzjz_eighth_first_remark", htzjz_eighth_first_remark);
		document.put( "htzjz_eighth_second_state", htzjz_eighth_second_state);
		document.put( "htzjz_eighth_second_remark", htzjz_eighth_second_remark);
		document.put( "htzjz_eighth_third_state", htzjz_eighth_third_state);
		document.put( "htzjz_eighth_third_remark", htzjz_eighth_third_remark);
		document.put( "htzjz_eighth_fourth_state", htzjz_eighth_fourth_state);
		document.put( "htzjz_eighth_fourth_remark", htzjz_eighth_fourth_remark);
		
		document.put( "htzjz_ninth_first_state", htzjz_ninth_first_state);
		document.put( "htzjz_ninth_first_remark", htzjz_ninth_first_remark);
		document.put( "htzjz_ninth_second_state", htzjz_ninth_second_state);
		document.put( "htzjz_ninth_second_remark", htzjz_ninth_second_remark);
		document.put( "htzjz_ninth_third_state", htzjz_ninth_third_state);
		document.put( "htzjz_ninth_third_remark", htzjz_ninth_third_remark);
		document.put( "htzjz_ninth_fourth_state", htzjz_ninth_fourth_state);
		document.put( "htzjz_ninth_fourth_remark", htzjz_ninth_fourth_remark);
		document.put( "htzjz_ninth_fifth_state", htzjz_ninth_fifth_state);
		document.put( "htzjz_ninth_fifth_remark", htzjz_ninth_fifth_remark);
		document.put( "htzjz_ninth_sixth_state", htzjz_ninth_sixth_state);
		document.put( "htzjz_ninth_sixth_remark", htzjz_ninth_sixth_remark);
		document.put( "htzjz_ninth_seventh_state", htzjz_ninth_seventh_state);
		document.put( "htzjz_ninth_seventh_remark", htzjz_ninth_seventh_remark);
		
		document.put( "htzjz_tenth_first_state", htzjz_tenth_first_state);
		document.put( "htzjz_tenth_first_remark", htzjz_tenth_first_remark);
		document.put( "htzjz_tenth_second_state", htzjz_tenth_second_state);
		document.put( "htzjz_tenth_second_remark", htzjz_tenth_second_remark);
		document.put( "htzjz_tenth_third_state", htzjz_tenth_third_state);
		document.put( "htzjz_tenth_third_remark", htzjz_tenth_third_remark);
		
		document.put( "htzjz_eleventh_first_state", htzjz_eleventh_first_state);
		document.put( "htzjz_eleventh_first_remark", htzjz_eleventh_first_remark);
		document.put( "htzjz_eleventh_second_state", htzjz_eleventh_second_state);
		document.put( "htzjz_eleventh_second_remark", htzjz_eleventh_second_remark);
		document.put( "htzjz_eleventh_third_state", htzjz_eleventh_third_state);
		document.put( "htzjz_eleventh_third_remark", htzjz_eleventh_third_remark);
		
		document.put( "htzjz_twelfth_first_state", htzjz_twelfth_first_state);
		document.put( "htzjz_twelfth_first_remark", htzjz_twelfth_first_remark);
		document.put( "htzjz_twelfth_second_state", htzjz_twelfth_second_state);
		document.put( "htzjz_twelfth_second_remark", htzjz_twelfth_second_remark);
		document.put( "htzjz_twelfth_third_state", htzjz_twelfth_third_state);
		document.put( "htzjz_twelfth_third_remark", htzjz_twelfth_third_remark);
		document.put( "htzjz_twelfth_fourth_state", htzjz_twelfth_fourth_state);
		document.put( "htzjz_twelfth_fourth_remark", htzjz_twelfth_fourth_remark);
		
		document.put( "htzjz_thirteenth_first_state", htzjz_thirteenth_first_state);
		document.put( "htzjz_thirteenth_first_remark", htzjz_thirteenth_first_remark);
		document.put( "htzjz_thirteenth_second_state", htzjz_thirteenth_second_state);
		document.put( "htzjz_thirteenth_second_remark", htzjz_thirteenth_second_remark);
		document.put( "htzjz_thirteenth_third_state", htzjz_thirteenth_third_state);
		document.put( "htzjz_thirteenth_third_remark", htzjz_thirteenth_third_remark);
		
		document.put( "htzjz_fourteenth_first_state", htzjz_fourteenth_first_state);
		document.put( "htzjz_fourteenth_first_remark", htzjz_fourteenth_first_remark);
		document.put( "htzjz_fourteenth_second_state", htzjz_fourteenth_second_state);
		document.put( "htzjz_fourteenth_second_remark", htzjz_fourteenth_second_remark);
		document.put( "htzjz_fourteenth_third_state", htzjz_fourteenth_third_state);
		document.put( "htzjz_fourteenth_third_remark", htzjz_fourteenth_third_remark);
		
		document.put( "htzjz_fifteenth_first_state", htzjz_fifteenth_first_state);
		document.put( "htzjz_fifteenth_first_remark", htzjz_fifteenth_first_remark);
		document.put( "htzjz_fifteenth_second_state", htzjz_fifteenth_second_state);
		document.put( "htzjz_fifteenth_second_remark", htzjz_fifteenth_second_remark);
		document.put( "htzjz_fifteenth_third_state", htzjz_fifteenth_third_state);
		document.put( "htzjz_fifteenth_third_remark", htzjz_fifteenth_third_remark);
		
		document.put( "htzjz_sixteenth_first_state", htzjz_sixteenth_first_state);
		document.put( "htzjz_sixteenth_first_remark", htzjz_sixteenth_first_remark);
		document.put( "htzjz_sixteenth_second_state", htzjz_sixteenth_second_state);
		document.put( "htzjz_sixteenth_second_remark", htzjz_sixteenth_second_remark);
		document.put( "htzjz_sixteenth_third_state", htzjz_sixteenth_third_state);
		document.put( "htzjz_sixteenth_third_remark", htzjz_sixteenth_third_remark);
		
		document.put( "htzjz_seventeenth_first_state", htzjz_seventeenth_first_state);
		document.put( "htzjz_seventeenth_first_remark", htzjz_seventeenth_first_remark);
		document.put( "htzjz_seventeenth_second_state", htzjz_seventeenth_second_state);
		document.put( "htzjz_seventeenth_second_remark", htzjz_seventeenth_second_remark);
		document.put( "htzjz_seventeenth_third_state", htzjz_seventeenth_third_state);
		document.put( "htzjz_seventeenth_third_remark", htzjz_seventeenth_third_remark);
		document.put( "htzjz_seventeenth_fourth_state", htzjz_seventeenth_fourth_state);
		document.put( "htzjz_seventeenth_fourth_remark", htzjz_seventeenth_fourth_remark); 
		
		document.put("checkPeople", checkPeople);
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("line", line);
		document.put("machineRoom", machineRoom);
		document.put("userId", userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		document.put("orgId", org.get("ORG_ID_"));
		document.put("overhaulPerson", org.get("ORG_NAME_"));
		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);

	}
	/**
	 * 新增普铁中间站模板
	 * 
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/addTrainZJZ")
	public ResultMsg addDocPTZJZ(
			@RequestParam(name = "ptzjz_first_first_state", required = false) String ptzjz_first_first_state,
			@RequestParam(name = "ptzjz_first_first_remark") String ptzjz_first_first_remark,
			@RequestParam(name = "ptzjz_first_second_state", required = false) String ptzjz_first_second_state,
			@RequestParam(name = "ptzjz_first_second_remark") String ptzjz_first_second_remark,
			@RequestParam(name = "ptzjz_first_third_state", required = false) String ptzjz_first_third_state,
			@RequestParam(name = "ptzjz_first_third_remark") String ptzjz_first_third_remark,
			@RequestParam(name = "ptzjz_first_fourth_state", required = false) String ptzjz_first_fourth_state,
			@RequestParam(name = "ptzjz_first_fourth_remark") String ptzjz_first_fourth_remark,
			@RequestParam(name = "ptzjz_first_fifth_state", required = false) String ptzjz_first_fifth_state,
			@RequestParam(name = "ptzjz_first_fifth_remark") String ptzjz_first_fifth_remark,
			
			@RequestParam(name = "ptzjz_second_first_state", required = false) String ptzjz_second_first_state,
			@RequestParam(name = "ptzjz_second_first_remark") String ptzjz_second_first_remark,
			@RequestParam(name = "ptzjz_second_second_state", required = false) String ptzjz_second_second_state,
			@RequestParam(name = "ptzjz_second_second_remark") String ptzjz_second_second_remark,
			@RequestParam(name = "ptzjz_second_third_state", required = false) String ptzjz_second_third_state,
			@RequestParam(name = "ptzjz_second_third_remark") String ptzjz_second_third_remark,
			@RequestParam(name = "ptzjz_second_fourth_state", required = false) String ptzjz_second_fourth_state,
			@RequestParam(name = "ptzjz_second_fourth_remark") String ptzjz_second_fourth_remark,
			@RequestParam(name = "ptzjz_second_fifth_state", required = false) String ptzjz_second_fifth_state,
			@RequestParam(name = "ptzjz_second_fifth_remark") String ptzjz_second_fifth_remark,
			@RequestParam(name = "ptzjz_second_sixth_state", required = false) String ptzjz_second_sixth_state,
			@RequestParam(name = "ptzjz_second_sixth_remark") String ptzjz_second_sixth_remark,
			
			@RequestParam(name = "ptzjz_third_first_state", required = false) String ptzjz_third_first_state,
			@RequestParam(name = "ptzjz_third_first_remark") String ptzjz_third_first_remark,
			@RequestParam(name = "ptzjz_third_second_state", required = false) String ptzjz_third_second_state,
			@RequestParam(name = "ptzjz_third_second_remark") String ptzjz_third_second_remark,
			@RequestParam(name = "ptzjz_third_third_state", required = false) String ptzjz_third_third_state,
			@RequestParam(name = "ptzjz_third_third_remark") String ptzjz_third_third_remark,
			
			@RequestParam(name = "ptzjz_fourth_first_state", required = false) String ptzjz_fourth_first_state,
			@RequestParam(name = "ptzjz_fourth_first_remark") String ptzjz_fourth_first_remark,
			@RequestParam(name = "ptzjz_fourth_second_state", required = false) String ptzjz_fourth_second_state,
			@RequestParam(name = "ptzjz_fourth_second_remark") String ptzjz_fourth_second_remark,
			@RequestParam(name = "ptzjz_fourth_third_state", required = false) String ptzjz_fourth_third_state,
			@RequestParam(name = "ptzjz_fourth_third_remark") String ptzjz_fourth_third_remark,
			@RequestParam(name = "ptzjz_fourth_fourth_state", required = false) String ptzjz_fourth_fourth_state,
			@RequestParam(name = "ptzjz_fourth_fourth_remark") String ptzjz_fourth_fourth_remark,
			@RequestParam(name = "ptzjz_fourth_fifth_state", required = false) String ptzjz_fourth_fifth_state,
			@RequestParam(name = "ptzjz_fourth_fifth_remark") String ptzjz_fourth_fifth_remark,
			
			@RequestParam(name = "ptzjz_fifth_first_state", required = false) String ptzjz_fifth_first_state,
			@RequestParam(name = "ptzjz_fifth_first_remark") String ptzjz_fifth_first_remark,
			@RequestParam(name = "ptzjz_fifth_second_state", required = false) String ptzjz_fifth_second_state,
			@RequestParam(name = "ptzjz_fifth_second_remark") String ptzjz_fifth_second_remark,
			@RequestParam(name = "ptzjz_fifth_third_state", required = false) String ptzjz_fifth_third_state,
			@RequestParam(name = "ptzjz_fifth_third_remark") String ptzjz_fifth_third_remark,
			@RequestParam(name = "ptzjz_fifth_fourth_state", required = false) String ptzjz_fifth_fourth_state,
			@RequestParam(name = "ptzjz_fifth_fourth_remark") String ptzjz_fifth_fourth_remark,
			@RequestParam(name = "ptzjz_fifth_fifth_state", required = false) String ptzjz_fifth_fifth_state,
			@RequestParam(name = "ptzjz_fifth_fifth_remark") String ptzjz_fifth_fifth_remark,
			@RequestParam(name = "ptzjz_fifth_sixth_state", required = false) String ptzjz_fifth_sixth_state,
			@RequestParam(name = "ptzjz_fifth_sixth_remark") String ptzjz_fifth_sixth_remark,
			@RequestParam(name = "ptzjz_fifth_seventh_state", required = false) String ptzjz_fifth_seventh_state,
			@RequestParam(name = "ptzjz_fifth_seventh_remark") String ptzjz_fifth_seventh_remark,
			
			@RequestParam(name = "ptzjz_sixth_first_state", required = false) String ptzjz_sixth_first_state,
			@RequestParam(name = "ptzjz_sixth_first_remark") String ptzjz_sixth_first_remark,
			@RequestParam(name = "ptzjz_sixth_second_state", required = false) String ptzjz_sixth_second_state,
			@RequestParam(name = "ptzjz_sixth_second_remark") String ptzjz_sixth_second_remark,
			@RequestParam(name = "ptzjz_sixth_third_state", required = false) String ptzjz_sixth_third_state,
			@RequestParam(name = "ptzjz_sixth_third_remark") String ptzjz_sixth_third_remark,
			
			@RequestParam(name = "ptzjz_seventh_first_state", required = false) String ptzjz_seventh_first_state,
			@RequestParam(name = "ptzjz_seventh_first_remark") String ptzjz_seventh_first_remark,
			@RequestParam(name = "ptzjz_seventh_second_state", required = false) String ptzjz_seventh_second_state,
			@RequestParam(name = "ptzjz_seventh_second_remark") String ptzjz_seventh_second_remark,
			@RequestParam(name = "ptzjz_seventh_third_state", required = false) String ptzjz_seventh_third_state,
			@RequestParam(name = "ptzjz_seventh_third_remark") String ptzjz_seventh_third_remark,
			
			@RequestParam(name = "ptzjz_eighth_first_state", required = false) String ptzjz_eighth_first_state,
			@RequestParam(name = "ptzjz_eighth_first_remark") String ptzjz_eighth_first_remark,
			@RequestParam(name = "ptzjz_eighth_second_state", required = false) String ptzjz_eighth_second_state,
			@RequestParam(name = "ptzjz_eighth_second_remark") String ptzjz_eighth_second_remark,
			@RequestParam(name = "ptzjz_eighth_third_state", required = false) String ptzjz_eighth_third_state,
			@RequestParam(name = "ptzjz_eighth_third_remark") String ptzjz_eighth_third_remark,
			@RequestParam(name = "ptzjz_eighth_fourth_state", required = false) String ptzjz_eighth_fourth_state,
			@RequestParam(name = "ptzjz_eighth_fourth_remark") String ptzjz_eighth_fourth_remark,
			@RequestParam(name = "ptzjz_eighth_fifth_state", required = false) String ptzjz_eighth_fifth_state,
			@RequestParam(name = "ptzjz_eighth_fifth_remark") String ptzjz_eighth_fifth_remark,
			@RequestParam(name = "ptzjz_eighth_sixth_state", required = false) String ptzjz_eighth_sixth_state,
			@RequestParam(name = "ptzjz_eighth_sixth_remark") String ptzjz_eighth_sixth_remark,
			@RequestParam(name = "ptzjz_eighth_seventh_state", required = false) String ptzjz_eighth_seventh_state,
			@RequestParam(name = "ptzjz_eighth_seventh_remark") String ptzjz_eighth_seventh_remark,
			
			@RequestParam(name = "ptzjz_ninth_first_state", required = false) String ptzjz_ninth_first_state,
			@RequestParam(name = "ptzjz_ninth_first_remark") String ptzjz_ninth_first_remark,
			@RequestParam(name = "ptzjz_ninth_second_state", required = false) String ptzjz_ninth_second_state,
			@RequestParam(name = "ptzjz_ninth_second_remark") String ptzjz_ninth_second_remark,
			@RequestParam(name = "ptzjz_ninth_third_state", required = false) String ptzjz_ninth_third_state,
			@RequestParam(name = "ptzjz_ninth_third_remark") String ptzjz_ninth_third_remark,
			@RequestParam(name = "ptzjz_ninth_fourth_state", required = false) String ptzjz_ninth_fourth_state,
			@RequestParam(name = "ptzjz_ninth_fourth_remark") String ptzjz_ninth_fourth_remark,
			
			@RequestParam(name = "ptzjz_tenth_first_state", required = false) String ptzjz_tenth_first_state,
			@RequestParam(name = "ptzjz_tenth_first_remark") String ptzjz_tenth_first_remark,
			@RequestParam(name = "ptzjz_tenth_second_state", required = false) String ptzjz_tenth_second_state,
			@RequestParam(name = "ptzjz_tenth_second_remark") String ptzjz_tenth_second_remark,
			@RequestParam(name = "ptzjz_tenth_third_state", required = false) String ptzjz_tenth_third_state,
			@RequestParam(name = "ptzjz_tenth_third_remark") String ptzjz_tenth_third_remark,
			@RequestParam(name = "ptzjz_tenth_fourth_state", required = false) String ptzjz_tenth_fourth_state,
			@RequestParam(name = "ptzjz_tenth_fourth_remark") String ptzjz_tenth_fourth_remark,
			@RequestParam(name = "ptzjz_tenth_fifth_state", required = false) String ptzjz_tenth_fifth_state,
			@RequestParam(name = "ptzjz_tenth_fifth_remark") String ptzjz_tenth_fifth_remark,
			@RequestParam(name = "ptzjz_tenth_sixth_state", required = false) String ptzjz_tenth_sixth_state,
			@RequestParam(name = "ptzjz_tenth_sixth_remark") String ptzjz_tenth_sixth_remark,
			
			@RequestParam(name = "ptzjz_eleventh_first_state", required = false) String ptzjz_eleventh_first_state,
			@RequestParam(name = "ptzjz_eleventh_first_remark") String ptzjz_eleventh_first_remark,
			@RequestParam(name = "ptzjz_eleventh_second_state", required = false) String ptzjz_eleventh_second_state,
			@RequestParam(name = "ptzjz_eleventh_second_remark") String ptzjz_eleventh_second_remark,
			@RequestParam(name = "ptzjz_eleventh_third_state", required = false) String ptzjz_eleventh_third_state,
			@RequestParam(name = "ptzjz_eleventh_third_remark") String ptzjz_eleventh_third_remark,
			
			@RequestParam(name = "ptzjz_twelfth_first_state", required = false) String ptzjz_twelfth_first_state,
			@RequestParam(name = "ptzjz_twelfth_first_remark") String ptzjz_twelfth_first_remark,
			@RequestParam(name = "ptzjz_twelfth_second_state", required = false) String ptzjz_twelfth_second_state,
			@RequestParam(name = "ptzjz_twelfth_second_remark") String ptzjz_twelfth_second_remark,
			@RequestParam(name = "ptzjz_twelfth_third_state", required = false) String ptzjz_twelfth_third_state,
			@RequestParam(name = "ptzjz_twelfth_third_remark") String ptzjz_twelfth_third_remark,
			
			@RequestParam(name = "ptzjz_thirteenth_first_state", required = false) String ptzjz_thirteenth_first_state,
			@RequestParam(name = "ptzjz_thirteenth_first_remark") String ptzjz_thirteenth_first_remark,
			@RequestParam(name = "ptzjz_thirteenth_second_state", required = false) String ptzjz_thirteenth_second_state,
			@RequestParam(name = "ptzjz_thirteenth_second_remark") String ptzjz_thirteenth_second_remark,
			@RequestParam(name = "ptzjz_thirteenth_third_state", required = false) String ptzjz_thirteenth_third_state,
			@RequestParam(name = "ptzjz_thirteenth_third_remark") String ptzjz_thirteenth_third_remark,
			
			@RequestParam(name = "ptzjz_fourteenth_first_state", required = false) String ptzjz_fourteenth_first_state,
			@RequestParam(name = "ptzjz_fourteenth_first_remark") String ptzjz_fourteenth_first_remark,
			@RequestParam(name = "ptzjz_fourteenth_second_state", required = false) String ptzjz_fourteenth_second_state,
			@RequestParam(name = "ptzjz_fourteenth_second_remark") String ptzjz_fourteenth_second_remark,
			@RequestParam(name = "ptzjz_fourteenth_third_state", required = false) String ptzjz_fourteenth_third_state,
			@RequestParam(name = "ptzjz_fourteenth_third_remark") String ptzjz_fourteenth_third_remark,
			
			@RequestParam(name = "ptzjz_fifteenth_first_state", required = false) String ptzjz_fifteenth_first_state,
			@RequestParam(name = "ptzjz_fifteenth_first_remark") String ptzjz_fifteenth_first_remark,
			@RequestParam(name = "ptzjz_fifteenth_second_state", required = false) String ptzjz_fifteenth_second_state,
			@RequestParam(name = "ptzjz_fifteenth_second_remark") String ptzjz_fifteenth_second_remark,
			@RequestParam(name = "ptzjz_fifteenth_third_state", required = false) String ptzjz_fifteenth_third_state,
			@RequestParam(name = "ptzjz_fifteenth_third_remark") String ptzjz_fifteenth_third_remark,
			
			@RequestParam(name = "ptzjz_sixteenth_first_state", required = false) String ptzjz_sixteenth_first_state,
			@RequestParam(name = "ptzjz_sixteenth_first_remark") String ptzjz_sixteenth_first_remark,
			@RequestParam(name = "ptzjz_sixteenth_second_state", required = false) String ptzjz_sixteenth_second_state,
			@RequestParam(name = "ptzjz_sixteenth_second_remark") String ptzjz_sixteenth_second_remark,
			
			@RequestParam(name = "ptzjz_seventeenth_first_state", required = false) String ptzjz_seventeenth_first_state,
			@RequestParam(name = "ptzjz_seventeenth_first_remark") String ptzjz_seventeenth_first_remark,
			@RequestParam(name = "ptzjz_seventeenth_second_state", required = false) String ptzjz_seventeenth_second_state,
			@RequestParam(name = "ptzjz_seventeenth_second_remark") String ptzjz_seventeenth_second_remark,
			@RequestParam(name = "ptzjz_seventeenth_third_state", required = false) String ptzjz_seventeenth_third_state,
			@RequestParam(name = "ptzjz_seventeenth_third_remark") String ptzjz_seventeenth_third_remark,
			
			@RequestParam(name = "ptzjz_eighteenth_first_state", required = false) String ptzjz_eighteenth_first_state,
			@RequestParam(name = "ptzjz_eighteenth_first_remark") String ptzjz_eighteenth_first_remark,
			@RequestParam(name = "ptzjz_eighteenth_second_state", required = false) String ptzjz_eighteenth_second_state,
			@RequestParam(name = "ptzjz_eighteenth_second_remark") String ptzjz_eighteenth_second_remark,
			
			@RequestParam(name = "ptzjz_nineteenth_first_state", required = false) String ptzjz_nineteenth_first_state,
			@RequestParam(name = "ptzjz_nineteenth_first_remark") String ptzjz_nineteenth_first_remark,
			@RequestParam(name = "ptzjz_nineteenth_second_state", required = false) String ptzjz_nineteenth_second_state,
			@RequestParam(name = "ptzjz_nineteenth_second_remark") String ptzjz_nineteenth_second_remark,
			@RequestParam(name = "ptzjz_nineteenth_third_state", required = false) String ptzjz_nineteenth_third_state,
			@RequestParam(name = "ptzjz_nineteenth_third_remark") String ptzjz_nineteenth_third_remark,
			@RequestParam(name = "ptzjz_nineteenth_fourth_state", required = false) String ptzjz_nineteenth_fourth_state,
			@RequestParam(name = "ptzjz_nineteenth_fourth_remark") String ptzjz_nineteenth_fourth_remark,
			
			@RequestParam(name = "checkPeople") String checkPeople,
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "line") String line,
			@RequestParam(name = "machineRoom") String machineRoom,
			@RequestParam(name = "collectionName") String collectionName, 
			@RequestParam(name = "userId") String userId,
			HttpServletRequest request) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
		Document document = new Document();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatDateStr = "";
		try {
			creatDateStr = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.put("overhaulDate", creatDateStr);
		document.put("overhaulName", "普铁中间站设备检修");
		document.put( "ptzjz_first_first_state",ptzjz_first_first_state);
		document.put( "ptzjz_first_first_remark",ptzjz_first_first_remark);
		document.put( "ptzjz_first_second_state",ptzjz_first_second_state);
		document.put( "ptzjz_first_second_remark",ptzjz_first_second_remark);
		document.put( "ptzjz_first_third_state", ptzjz_first_third_state);
		document.put( "ptzjz_first_third_remark", ptzjz_first_third_remark);
		document.put( "ptzjz_first_fourth_state", ptzjz_first_fourth_state);
		document.put( "ptzjz_first_fourth_remark", ptzjz_first_fourth_remark);
		document.put( "ptzjz_first_fifth_state", ptzjz_first_fifth_state);
		document.put( "ptzjz_first_fifth_remark", ptzjz_first_fifth_remark);
		
		document.put( "ptzjz_second_first_state", ptzjz_second_first_state);
		document.put( "ptzjz_second_first_remark", ptzjz_second_first_remark);
		document.put( "ptzjz_second_second_state", ptzjz_second_second_state);
		document.put( "ptzjz_second_second_remark", ptzjz_second_second_remark);
		document.put( "ptzjz_second_third_state", ptzjz_second_third_state);
		document.put( "ptzjz_second_third_remark", ptzjz_second_third_remark);
		document.put( "ptzjz_second_fourth_state", ptzjz_second_fourth_state);
		document.put( "ptzjz_second_fourth_remark", ptzjz_second_fourth_remark);
		document.put( "ptzjz_second_fifth_state", ptzjz_second_fifth_state);
		document.put( "ptzjz_second_fifth_remark", ptzjz_second_fifth_remark);
		document.put( "ptzjz_second_sixth_state", ptzjz_second_sixth_state);
		document.put( "ptzjz_second_sixth_remark", ptzjz_second_sixth_remark);
		
		document.put( "ptzjz_third_first_state", ptzjz_third_first_state);
		document.put( "ptzjz_third_first_remark", ptzjz_third_first_remark);
		document.put( "ptzjz_third_second_state", ptzjz_third_second_state);
		document.put( "ptzjz_third_second_remark", ptzjz_third_second_remark);
		document.put( "ptzjz_third_third_state", ptzjz_third_third_state);
		document.put( "ptzjz_third_third_remark", ptzjz_third_third_remark);
		
		document.put( "ptzjz_fourth_first_state", ptzjz_fourth_first_state);
		document.put( "ptzjz_fourth_first_remark", ptzjz_fourth_first_remark);
		document.put( "ptzjz_fourth_second_state", ptzjz_fourth_second_state);
		document.put( "ptzjz_fourth_second_remark", ptzjz_fourth_second_remark);
		document.put( "ptzjz_fourth_third_state", ptzjz_fourth_third_state);
		document.put( "ptzjz_fourth_third_remark", ptzjz_fourth_third_remark);
		document.put( "ptzjz_fourth_fourth_state", ptzjz_fourth_fourth_state);
		document.put( "ptzjz_fourth_fourth_remark", ptzjz_fourth_fourth_remark);
		document.put( "ptzjz_fourth_fifth_state", ptzjz_fourth_fifth_state);
		document.put( "ptzjz_fourth_fifth_remark", ptzjz_fourth_fifth_remark);
		
		document.put( "ptzjz_fifth_first_state", ptzjz_fifth_first_state);
		document.put( "ptzjz_fifth_first_remark", ptzjz_fifth_first_remark);
		document.put( "ptzjz_fifth_second_state", ptzjz_fifth_second_state);
		document.put( "ptzjz_fifth_second_remark", ptzjz_fifth_second_remark);
		document.put( "ptzjz_fifth_third_state", ptzjz_fifth_third_state);
		document.put( "ptzjz_fifth_third_remark", ptzjz_fifth_third_remark);
		document.put( "ptzjz_fifth_fourth_state", ptzjz_fifth_fourth_state);
		document.put( "ptzjz_fifth_fourth_remark", ptzjz_fifth_fourth_remark);
		document.put( "ptzjz_fifth_fifth_state", ptzjz_fifth_fifth_state);
		document.put( "ptzjz_fifth_fifth_remark", ptzjz_fifth_fifth_remark);
		document.put( "ptzjz_fifth_sixth_state", ptzjz_fifth_sixth_state);
		document.put( "ptzjz_fifth_sixth_remark", ptzjz_fifth_sixth_remark);
		document.put( "ptzjz_fifth_seventh_state", ptzjz_fifth_seventh_state);
		document.put( "ptzjz_fifth_seventh_remark", ptzjz_fifth_seventh_remark);
		
		document.put( "ptzjz_sixth_first_state", ptzjz_sixth_first_state);
		document.put( "ptzjz_sixth_first_remark", ptzjz_sixth_first_remark);
		document.put( "ptzjz_sixth_second_state", ptzjz_sixth_second_state);
		document.put( "ptzjz_sixth_second_remark", ptzjz_sixth_second_remark);
		document.put( "ptzjz_sixth_third_state", ptzjz_sixth_third_state);
		document.put( "ptzjz_sixth_third_remark", ptzjz_sixth_third_remark);
		
		document.put( "ptzjz_seventh_first_state", ptzjz_seventh_first_state);
		document.put( "ptzjz_seventh_first_remark", ptzjz_seventh_first_remark);
		document.put( "ptzjz_seventh_second_state", ptzjz_seventh_second_state);
		document.put( "ptzjz_seventh_second_remark", ptzjz_seventh_second_remark);
		document.put( "ptzjz_seventh_third_state", ptzjz_seventh_third_state);
		document.put( "ptzjz_seventh_third_remark", ptzjz_seventh_third_remark);
		
		document.put( "ptzjz_eighth_first_state", ptzjz_eighth_first_state);
		document.put( "ptzjz_eighth_first_remark", ptzjz_eighth_first_remark);
		document.put( "ptzjz_eighth_second_state", ptzjz_eighth_second_state);
		document.put( "ptzjz_eighth_second_remark", ptzjz_eighth_second_remark);
		document.put( "ptzjz_eighth_third_state", ptzjz_eighth_third_state);
		document.put( "ptzjz_eighth_third_remark", ptzjz_eighth_third_remark);
		document.put( "ptzjz_eighth_fourth_state", ptzjz_eighth_fourth_state);
		document.put( "ptzjz_eighth_fourth_remark", ptzjz_eighth_fourth_remark);
		document.put( "ptzjz_eighth_fifth_state", ptzjz_eighth_fifth_state);
		document.put( "ptzjz_eighth_fifth_remark", ptzjz_eighth_fifth_remark);
		document.put( "ptzjz_eighth_sixth_state", ptzjz_eighth_sixth_state);
		document.put( "ptzjz_eighth_sixth_remark", ptzjz_eighth_sixth_remark);
		document.put( "ptzjz_eighth_seventh_state", ptzjz_eighth_seventh_state);
		document.put( "ptzjz_eighth_seventh_remark", ptzjz_eighth_seventh_remark);
		
		document.put( "ptzjz_ninth_first_state", ptzjz_ninth_first_state);
		document.put( "ptzjz_ninth_first_remark", ptzjz_ninth_first_remark);
		document.put( "ptzjz_ninth_second_state", ptzjz_ninth_second_state);
		document.put( "ptzjz_ninth_second_remark", ptzjz_ninth_second_remark);
		document.put( "ptzjz_ninth_third_state", ptzjz_ninth_third_state);
		document.put( "ptzjz_ninth_third_remark", ptzjz_ninth_third_remark);
		document.put( "ptzjz_ninth_fourth_state", ptzjz_ninth_fourth_state);
		document.put( "ptzjz_ninth_fourth_remark", ptzjz_ninth_fourth_remark);
		
		document.put( "ptzjz_tenth_first_state", ptzjz_tenth_first_state);
		document.put( "ptzjz_tenth_first_remark", ptzjz_tenth_first_remark);
		document.put( "ptzjz_tenth_second_state", ptzjz_tenth_second_state);
		document.put( "ptzjz_tenth_second_remark", ptzjz_tenth_second_remark);
		document.put( "ptzjz_tenth_third_state", ptzjz_tenth_third_state);
		document.put( "ptzjz_tenth_third_remark", ptzjz_tenth_third_remark);
		document.put( "ptzjz_tenth_fourth_state", ptzjz_tenth_fourth_state);
		document.put( "ptzjz_tenth_fourth_remark", ptzjz_tenth_fourth_remark);
		document.put( "ptzjz_tenth_fifth_state", ptzjz_tenth_fifth_state);
		document.put( "ptzjz_tenth_fifth_remark", ptzjz_tenth_fifth_remark);
		document.put( "ptzjz_tenth_sixth_state", ptzjz_tenth_sixth_state);
		document.put( "ptzjz_tenth_sixth_remark", ptzjz_tenth_sixth_remark);
		
		document.put( "ptzjz_eleventh_first_state", ptzjz_eleventh_first_state);
		document.put( "ptzjz_eleventh_first_remark", ptzjz_eleventh_first_remark);
		document.put( "ptzjz_eleventh_second_state", ptzjz_eleventh_second_state);
		document.put( "ptzjz_eleventh_second_remark", ptzjz_eleventh_second_remark);
		document.put( "ptzjz_eleventh_third_state", ptzjz_eleventh_third_state);
		document.put( "ptzjz_eleventh_third_remark", ptzjz_eleventh_third_remark);
		
		document.put( "ptzjz_twelfth_first_state", ptzjz_twelfth_first_state);
		document.put( "ptzjz_twelfth_first_remark", ptzjz_twelfth_first_remark);
		document.put( "ptzjz_twelfth_second_state", ptzjz_twelfth_second_state);
		document.put( "ptzjz_twelfth_second_remark", ptzjz_twelfth_second_remark);
		document.put( "ptzjz_twelfth_third_state", ptzjz_twelfth_third_state);
		document.put( "ptzjz_twelfth_third_remark", ptzjz_twelfth_third_remark);
		
		document.put( "ptzjz_thirteenth_first_state", ptzjz_thirteenth_first_state);
		document.put( "ptzjz_thirteenth_first_remark", ptzjz_thirteenth_first_remark);
		document.put( "ptzjz_thirteenth_second_state", ptzjz_thirteenth_second_state);
		document.put( "ptzjz_thirteenth_second_remark", ptzjz_thirteenth_second_remark);
		document.put( "ptzjz_thirteenth_third_state", ptzjz_thirteenth_third_state);
		document.put( "ptzjz_thirteenth_third_remark", ptzjz_thirteenth_third_remark);
		
		document.put( "ptzjz_fourteenth_first_state", ptzjz_fourteenth_first_state);
		document.put( "ptzjz_fourteenth_first_remark", ptzjz_fourteenth_first_remark);
		document.put( "ptzjz_fourteenth_second_state", ptzjz_fourteenth_second_state);
		document.put( "ptzjz_fourteenth_second_remark", ptzjz_fourteenth_second_remark);
		document.put( "ptzjz_fourteenth_third_state", ptzjz_fourteenth_third_state);
		document.put( "ptzjz_fourteenth_third_remark", ptzjz_fourteenth_third_remark);
		
		document.put( "ptzjz_fifteenth_first_state", ptzjz_fifteenth_first_state);
		document.put( "ptzjz_fifteenth_first_remark", ptzjz_fifteenth_first_remark);
		document.put( "ptzjz_fifteenth_second_state", ptzjz_fifteenth_second_state);
		document.put( "ptzjz_fifteenth_second_remark", ptzjz_fifteenth_second_remark);
		document.put( "ptzjz_fifteenth_third_state", ptzjz_fifteenth_third_state);
		document.put( "ptzjz_fifteenth_third_remark", ptzjz_fifteenth_third_remark);
		
		document.put( "ptzjz_sixteenth_first_state", ptzjz_sixteenth_first_state);
		document.put( "ptzjz_sixteenth_first_remark", ptzjz_sixteenth_first_remark);
		document.put( "ptzjz_sixteenth_second_state", ptzjz_sixteenth_second_state);
		document.put( "ptzjz_sixteenth_second_remark", ptzjz_sixteenth_second_remark);
		
		document.put( "ptzjz_seventeenth_first_state", ptzjz_seventeenth_first_state);
		document.put( "ptzjz_seventeenth_first_remark", ptzjz_seventeenth_first_remark);
		document.put( "ptzjz_seventeenth_second_state", ptzjz_seventeenth_second_state);
		document.put( "ptzjz_seventeenth_second_remark", ptzjz_seventeenth_second_remark);
		document.put( "ptzjz_seventeenth_third_state", ptzjz_seventeenth_third_state);
		document.put( "ptzjz_seventeenth_third_remark", ptzjz_seventeenth_third_remark);
		
		document.put( "ptzjz_eighteenth_first_state", ptzjz_eighteenth_first_state);
		document.put( "ptzjz_eighteenth_first_remark", ptzjz_eighteenth_first_remark);
		document.put( "ptzjz_eighteenth_second_state", ptzjz_eighteenth_second_state);
		document.put( "ptzjz_eighteenth_second_remark", ptzjz_eighteenth_second_remark);
		
		document.put( "ptzjz_nineteenth_first_state", ptzjz_nineteenth_first_state);
		document.put( "ptzjz_nineteenth_first_remark", ptzjz_nineteenth_first_remark);
		document.put( "ptzjz_nineteenth_second_state", ptzjz_nineteenth_second_state);
		document.put( "ptzjz_nineteenth_second_remark", ptzjz_nineteenth_second_remark);
		document.put( "ptzjz_nineteenth_third_state", ptzjz_nineteenth_third_state);
		document.put( "ptzjz_nineteenth_third_remark", ptzjz_nineteenth_third_remark);
		document.put( "ptzjz_nineteenth_fourth_state", ptzjz_nineteenth_fourth_state);
		document.put( "ptzjz_nineteenth_fourth_remark", ptzjz_nineteenth_fourth_remark);
		
		document.put("checkPeople", checkPeople);
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("line", line);
		document.put("machineRoom", machineRoom);
		document.put("userId", userId);
		Map<String, Object> org = userService.getOrgbyUserId(userId);
		document.put("orgId", org.get("ORG_ID_"));
		document.put("overhaulPerson", org.get("ORG_NAME_"));
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
			String workArea,
			String lineCondition,
			String machineRoomCondition,
			String overhaulName,
			String startUploadDate,
            String endUploadDate,
			@RequestParam int start, 
			@RequestParam int limit) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
	        Date startDate = null;
	        Date endDate = null;
	        GridDto<Document> result = new GridDto<>();
	        try {
	            if (StringUtils.isNotBlank(startUploadDate)) {
	                startDate = sdf.parse(startUploadDate);
	            }
	            if (StringUtils.isNotBlank(endUploadDate)) {
	                endDate = sdf.parse(endUploadDate);
	            }
	        } catch (ParseException e) {
	           result.setHasError(true);
	           result.setError("时间格式错误");
	           return result;
	        }
		result.setResults(service.findAllDocumentCount(collectionName, userId, workshop, workArea,lineCondition,machineRoomCondition,overhaulName,startUploadDate,endUploadDate));
		result.setRows(service.findAllDocument(collectionName, userId, workshop, workArea,lineCondition,machineRoomCondition,overhaulName,startUploadDate,endUploadDate,start, limit));
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
	public ResultMsg removeDoc(@RequestParam("id") String id, @RequestParam("collectionName") String collectionName,HttpServletRequest request) {
		
		if(!checkAdress(request)){
			return ResultMsg.getFailure("请在机房现场执行数据的操作！");
		}
		
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
    //查看高铁防灾设备检修记录
	@PostMapping("/exportHighTrainFZ")
	@ResponseBody
	public ResultMsg txdWriteBusiWord(@RequestBody HighTrainfzDto dto,HttpServletRequest request) {
		return ResultMsg.getSuccess(service.exportHighTrainfz(dto,dto.getMachineRoom(),dto.getOverhaulPerson(),dto.getOverhaulDate()));
	}

	// 查看高铁防灾设备检修记录
	@PostMapping("/exportHighTrainZFZ")
	@ResponseBody
	public ResultMsg txdWriteBusiWordZFZ(@RequestBody HighTrainzfzDto dto,HttpServletRequest request) {
		return ResultMsg.getSuccess(service.exportHighTrainzfz(dto,dto.getMachineRoom(),dto.getOverhaulPerson(),dto.getOverhaulDate()));
	}

	// 查看高铁防灾设备检修记录
	@PostMapping("/exportHighTrainZJZ")
	@ResponseBody
	public ResultMsg txdWriteBusiWordZJZ(@RequestBody HighTrainzjzDto dto,HttpServletRequest request) {
		return ResultMsg.getSuccess(service.exportHighTrainzjz(dto,dto.getMachineRoom(),dto.getCheckPeople(),dto.getOverhaulDate()));
	}

	// 查看高铁防灾设备检修记录
	@PostMapping("/exportTrainZJZ")
	@ResponseBody
	public ResultMsg txdWriteBusiWordPTZJZ(@RequestBody TrainzjzDto dto,HttpServletRequest request) {
		return ResultMsg.getSuccess(service.exportTrainzjz(dto,dto.getMachineRoom(),dto.getCheckPeople(),dto.getOverhaulDate()));
	}
	
	private boolean checkAdress(HttpServletRequest request){
		 String ip = ActionUtils.getIpAddr(request);
		 if(ip.indexOf("0:0")!=-1){
			 return true;
		 }
		 if(ip.indexOf("192.168")!=-1){
			 return true;
		 }
		 if(ip.indexOf("10.222")!=-1){
			 return true;
		 }
		 if(ip.indexOf("172.23")!=-1){
			 return true;
		 }
		 return false;
		 
	 }
	
}