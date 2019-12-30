package com.enovell.yunwei.km_micor_service.action.technicalManagement.communicationResumeManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.NetworkManageInfoService;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage.TransService;
import com.enovell.yunwei.km_micor_service.util.DeviceRecordPlaceUtil;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

@RestController
@RequestMapping("/tatAction")
public class TatAction {
	@Resource(name = "transService")
	private TransService service;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "overhaulRecordService")
	private OverhaulRecordService overhaulService;
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
	// 车间下拉选
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
	 * 新增数据  传输与接入网系统-波分(DWDM)-01
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/spcAddDoc")
	public ResultMsg addDoc(
			
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "site_station_line") String site_station_line,
			@RequestParam(name = "site_station_name") String site_station_name, 
			@RequestParam(name = "site_station_place") String site_station_place,
			@RequestParam(name = "site_range_line") String site_range_line,
			@RequestParam(name = "site_range_post") String site_range_post,
			@RequestParam(name = "site_range_place") String site_range_place, 
			@RequestParam(name = "site_other_line") String site_other_line,
			@RequestParam(name = "site_other_place") String site_other_place,
			@RequestParam(name = "site_machineRoomCode") String site_machineRoomCode,
			@RequestParam(name = "assetOwnership") String assetOwnership,
			@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
			@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode, 
			@RequestParam(name = "maintainBody") String maintainBody, 
			@RequestParam(name = "maintainUnitName") String maintainUnitName, 
			@RequestParam(name = "maintainUnitCode") String maintainUnitCode, 
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
			@RequestParam(name = "stopDate") String stopDate, 
			@RequestParam(name = "scrapDate") String scrapDate, 
			@RequestParam(name = "userLineConfigCapacity") String userLineConfigCapacity, 
			@RequestParam(name = "actualUserNumber") String actualUserNumber, 
			@RequestParam(name = "trunkConfigCapacity") String trunkConfigCapacity, 
			@RequestParam(name = "trunkActualCapacity") String trunkActualCapacity, 
			@RequestParam(name = "remoteUserModule_totalCapacity") String remoteUserModule_totalCapacity, 
			@RequestParam(name = "remoteUserModule_actuallCapacity") String remoteUserModule_actuallCapacity, 
			@RequestParam(name = "holdWithSignal") String holdWithSignal, 
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "orgId") String orgId,
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
		document.put("publicType", "tat_spc");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("site_station_line", site_station_line);
		document.put("site_station_name", site_station_name); 
		document.put("site_station_place", site_station_place);
		document.put("site_range_line", site_range_line);
		document.put("site_range_post", site_range_post);
		document.put("site_range_place", site_range_place); 
		document.put("site_other_line", site_other_line);
		document.put("site_other_place", site_other_place);
		document.put("site_machineRoomCode", site_machineRoomCode);
		document.put("assetOwnership", assetOwnership);
		document.put("ownershipUnitName", ownershipUnitName);
		document.put("ownershipUnitCode", ownershipUnitCode); 
		document.put("maintainBody", maintainBody); 
		document.put("maintainUnitName", maintainUnitName); 
		document.put("maintainUnitCode", maintainUnitCode); 
		document.put("manufacturers", manufacturers); 
		document.put("deviceType", deviceType); 
		document.put("useUnit", useUnit); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		
		document.put("userLineConfigCapacity", userLineConfigCapacity);
		document.put("actualUserNumber", actualUserNumber);
		document.put("trunkConfigCapacity", trunkConfigCapacity);
		document.put("trunkActualCapacity", trunkActualCapacity);
		document.put("remoteUserModule_totalCapacity", remoteUserModule_totalCapacity);
		document.put("remoteUserModule_actuallCapacity", remoteUserModule_actuallCapacity);
		document.put("holdWithSignal", holdWithSignal);
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	
	/**
	 * 新增数据  电报及电话系统-其它设备-02
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/otherAddDoc")
	public ResultMsg otherAddDoc(
			
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "site_station_line") String site_station_line,
			@RequestParam(name = "site_station_name") String site_station_name, 
			@RequestParam(name = "site_station_place") String site_station_place,
			@RequestParam(name = "site_range_line") String site_range_line,
			@RequestParam(name = "site_range_post") String site_range_post,
			@RequestParam(name = "site_range_place") String site_range_place, 
			@RequestParam(name = "site_other_line") String site_other_line,
			@RequestParam(name = "site_other_place") String site_other_place,
			@RequestParam(name = "site_machineRoomCode") String site_machineRoomCode,
			
			@RequestParam(name = "assetOwnership") String assetOwnership,
			@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
			@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode, 
			@RequestParam(name = "maintainBody") String maintainBody, 
			@RequestParam(name = "maintainUnitName") String maintainUnitName, 
			@RequestParam(name = "maintainUnitCode") String maintainUnitCode, 
			@RequestParam(name = "capacityCallsNumber") String capacityCallsNumber, 
			@RequestParam(name = "capacityUnit") String capacityUnit, 
			
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
			@RequestParam(name = "stopDate") String stopDate, 
			@RequestParam(name = "scrapDate") String scrapDate, 
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "orgId") String orgId,
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
		document.put("publicType", "tat_other");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("site_station_line", site_station_line);
		document.put("site_station_name", site_station_name); 
		document.put("site_station_place", site_station_place);
		document.put("site_range_line", site_range_line);
		document.put("site_range_post", site_range_post);
		document.put("site_range_place", site_range_place); 
		document.put("site_other_line", site_other_line);
		document.put("site_other_place", site_other_place);
		document.put("site_machineRoomCode", site_machineRoomCode);
		document.put("assetOwnership", assetOwnership);
		document.put("ownershipUnitName", ownershipUnitName);
		document.put("ownershipUnitCode", ownershipUnitCode); 
		document.put("maintainBody", maintainBody); 
		document.put("maintainUnitName", maintainUnitName); 
		document.put("maintainUnitCode", maintainUnitCode); 
		document.put("manufacturers", manufacturers); 
		document.put("deviceType", deviceType); 
		document.put("useUnit", useUnit); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("capacityCallsNumber", capacityCallsNumber);
		document.put("capacityUnit", capacityUnit);
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
/**
 * 
 * updateDoc 电报及电话系统-程控交换机-01
 * @return
 */
	@PostMapping("/spcUpdateDoc")
	public ResultMsg spcUpdateDoc(@RequestParam("id") String id, 
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "site_station_line") String site_station_line,
			@RequestParam(name = "site_station_name") String site_station_name, 
			@RequestParam(name = "site_station_place") String site_station_place,
			@RequestParam(name = "site_range_line") String site_range_line,
			@RequestParam(name = "site_range_post") String site_range_post,
			@RequestParam(name = "site_range_place") String site_range_place, 
			@RequestParam(name = "site_other_line") String site_other_line,
			@RequestParam(name = "site_other_place") String site_other_place,
			@RequestParam(name = "site_machineRoomCode") String site_machineRoomCode,
			@RequestParam(name = "assetOwnership") String assetOwnership,
			@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
			@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode, 
			@RequestParam(name = "maintainBody") String maintainBody, 
			@RequestParam(name = "maintainUnitName") String maintainUnitName, 
			@RequestParam(name = "maintainUnitCode") String maintainUnitCode, 
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
			@RequestParam(name = "stopDate") String stopDate, 
			@RequestParam(name = "scrapDate") String scrapDate, 
			@RequestParam(name = "userLineConfigCapacity") String userLineConfigCapacity, 
			@RequestParam(name = "actualUserNumber") String actualUserNumber, 
			@RequestParam(name = "trunkConfigCapacity") String trunkConfigCapacity, 
			@RequestParam(name = "trunkActualCapacity") String trunkActualCapacity, 
			@RequestParam(name = "remoteUserModule_totalCapacity") String remoteUserModule_totalCapacity, 
			@RequestParam(name = "remoteUserModule_actuallCapacity") String remoteUserModule_actuallCapacity, 
			@RequestParam(name = "holdWithSignal") String holdWithSignal, 
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName, 
			HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据
		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("site_station_line", site_station_line);
		document.put("site_station_name", site_station_name); 
		document.put("site_station_place", site_station_place);
		document.put("site_range_line", site_range_line);
		document.put("site_range_post", site_range_post);
		document.put("site_range_place", site_range_place); 
		document.put("site_other_line", site_other_line);
		document.put("site_other_place", site_other_place);
		document.put("site_machineRoomCode", site_machineRoomCode);
		document.put("assetOwnership", assetOwnership);
		document.put("ownershipUnitName", ownershipUnitName);
		document.put("ownershipUnitCode", ownershipUnitCode); 
		document.put("maintainBody", maintainBody); 
		document.put("maintainUnitName", maintainUnitName); 
		document.put("maintainUnitCode", maintainUnitCode); 
		document.put("manufacturers", manufacturers); 
		document.put("deviceType", deviceType); 
		document.put("useUnit", useUnit); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		
		document.put("userLineConfigCapacity", userLineConfigCapacity);
		document.put("actualUserNumber", actualUserNumber);
		document.put("trunkConfigCapacity", trunkConfigCapacity);
		document.put("trunkActualCapacity", trunkActualCapacity);
		document.put("remoteUserModule_totalCapacity", remoteUserModule_totalCapacity);
		document.put("remoteUserModule_actuallCapacity", remoteUserModule_actuallCapacity);
		document.put("holdWithSignal", holdWithSignal);
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	/**
	 * 
	 * updateDoc 电报及电话系统-程控交换机-01
	 * @return
	 */
		@PostMapping("/otherUpdateDoc")
		public ResultMsg otherUpdateDoc(@RequestParam("id") String id, 
				@RequestParam(name = "workshop") String workshop,
				@RequestParam(name = "workArea") String workArea,
				@RequestParam(name = "combinationClass") String combinationClass,
				@RequestParam(name = "deviceClass") String deviceClass, 
				@RequestParam(name = "deviceCode") String deviceCode,
				@RequestParam(name = "deviceName") String deviceName,
				@RequestParam(name = "site_station_line") String site_station_line,
				@RequestParam(name = "site_station_name") String site_station_name, 
				@RequestParam(name = "site_station_place") String site_station_place,
				@RequestParam(name = "site_range_line") String site_range_line,
				@RequestParam(name = "site_range_post") String site_range_post,
				@RequestParam(name = "site_range_place") String site_range_place, 
				@RequestParam(name = "site_other_line") String site_other_line,
				@RequestParam(name = "site_other_place") String site_other_place,
				@RequestParam(name = "site_machineRoomCode") String site_machineRoomCode,
				
				@RequestParam(name = "assetOwnership") String assetOwnership,
				@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
				@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode, 
				@RequestParam(name = "maintainBody") String maintainBody, 
				@RequestParam(name = "maintainUnitName") String maintainUnitName, 
				@RequestParam(name = "maintainUnitCode") String maintainUnitCode, 
				@RequestParam(name = "capacityCallsNumber") String capacityCallsNumber, 
				@RequestParam(name = "capacityUnit") String capacityUnit, 
				
				@RequestParam(name = "manufacturers") String manufacturers, 
				@RequestParam(name = "deviceType") String deviceType, 
				@RequestParam(name = "useUnit") String useUnit, 
				@RequestParam(name = "productionDate") String productionDate, 
				@RequestParam(name = "useDate") String useDate, 
				@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
				@RequestParam(name = "stopDate") String stopDate, 
				@RequestParam(name = "scrapDate") String scrapDate, 
				@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
				@RequestParam(name = "remark") String remark,  
				@RequestParam(name = "collectionName") String collectionName, 
				HttpServletRequest request) {
			Document document = service.findDocumentById(id, collectionName);
			document.put("source", "0");//此字段用于判断是否为导入的表格中的数据
			
			document.put("workshop", workshop);
			document.put("workArea", workArea);
			document.put("combinationClass", combinationClass);
			document.put("deviceClass", deviceClass); 
			document.put("deviceCode", deviceCode);
			document.put("deviceName", deviceName);
			document.put("site_station_line", site_station_line);
			document.put("site_station_name", site_station_name); 
			document.put("site_station_place", site_station_place);
			document.put("site_range_line", site_range_line);
			document.put("site_range_post", site_range_post);
			document.put("site_range_place", site_range_place); 
			document.put("site_other_line", site_other_line);
			document.put("site_other_place", site_other_place);
			document.put("site_machineRoomCode", site_machineRoomCode);
			document.put("assetOwnership", assetOwnership);
			document.put("ownershipUnitName", ownershipUnitName);
			document.put("ownershipUnitCode", ownershipUnitCode); 
			document.put("maintainBody", maintainBody); 
			document.put("maintainUnitName", maintainUnitName); 
			document.put("maintainUnitCode", maintainUnitCode); 
			document.put("manufacturers", manufacturers); 
			document.put("deviceType", deviceType); 
			document.put("useUnit", useUnit); 
			document.put("productionDate", productionDate); 
			document.put("useDate", useDate); 
			document.put("deviceOperationStatus", deviceOperationStatus); 
			document.put("stopDate", stopDate); 
			document.put("scrapDate", scrapDate); 
			document.put("fixedAssetsCode", fixedAssetsCode); 
			document.put("remark", remark);
			document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
			document.put("capacityCallsNumber", capacityCallsNumber);
			document.put("capacityUnit", capacityUnit);
			
			Document res = service.updateDocument(document, collectionName);
			return ResultMsg.getSuccess("修改成功", res);
		}
	
	
}