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
@RequestMapping("/transAction")
public class TransAction {
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
	@PostMapping("/addDoc")
	public ResultMsg addDoc(
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "systemName") String systemName,
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "deviceId") String deviceId,
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
			@RequestParam(name = "totalCapacity") String totalCapacity, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			@RequestParam(name = "configChannel") String configChannel, 
			@RequestParam(name = "assetRatio") String assetRatio, 
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
		document.put("publicType", "trans_dwdm");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("systemName", systemName);
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("deviceId", deviceId);
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
		document.put("totalCapacity", totalCapacity); 
		document.put("roadCapacity", roadCapacity); 
		document.put("configChannel", configChannel); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据  传输与接入网系统-同步数字体系(SDH)-02
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/sdhAddDoc")
	public ResultMsg sdhAddDoc(
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "systemName") String systemName,
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "deviceId") String deviceId,
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
			@RequestParam(name = "totalCapacity") String totalCapacity, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			@RequestParam(name = "assetRatio") String assetRatio, 
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
		document.put("publicType", "trans_sdh");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("systemName", systemName);
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("deviceId", deviceId);
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
		document.put("totalCapacity", totalCapacity); 
		document.put("roadCapacity", roadCapacity); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据  传输与接入网系统-准同步数字传输体系-03
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/numberTransAddDoc")
	public ResultMsg numberTransAddDoc(
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
			@RequestParam(name = "totalCapacity") String totalCapacity, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			@RequestParam(name = "assetRatio") String assetRatio, 
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
		document.put("publicType", "trans_numberTrans");
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
		document.put("totalCapacity", totalCapacity); 
		document.put("roadCapacity", roadCapacity); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}

	/**
	 *传输与接入网系统-其它设备-04
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
			@RequestParam(name = "accessType") String accessType, 
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "totalCapacity") String totalCapacity, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			@RequestParam(name = "assetRatio") String assetRatio, 
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
		document.put("publicType", "trans_other");
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
		document.put("accessType", accessType); 
		document.put("manufacturers", manufacturers); 
		document.put("deviceType", deviceType); 
		document.put("useUnit", useUnit); 
		document.put("totalCapacity", totalCapacity); 
		document.put("roadCapacity", roadCapacity); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 传输与接入网系统-光传送网(OTN)-05
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/otnAddDoc")
	public ResultMsg otnAddDoc(
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "systemName") String systemName,
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "deviceId") String deviceId,
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
			@RequestParam(name = "totalCapacity") String totalCapacity, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			@RequestParam(name = "configChannel") String configChannel, 
			@RequestParam(name = "assetRatio") String assetRatio, 
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
		document.put("publicType", "trans_otn");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("systemName", systemName);
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("deviceId", deviceId);
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
		document.put("totalCapacity", totalCapacity); 
		document.put("roadCapacity", roadCapacity); 
		document.put("configChannel", configChannel); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
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
			String workshop, 
			String workArea,
			String deviceName,
			String publicType,
			String deviceClass,
			String deviceType,
			String manufacturers,
			@RequestParam int start,
			@RequestParam int limit) {
		GridDto<Document> result = new GridDto<>();
		result.setResults(
				service.findAllDocumentCount(collectionName, workshop, workArea, deviceName, publicType,deviceClass,deviceType,manufacturers));
		result.setRows(
				service.findAllDocument(collectionName, workshop, workArea, deviceName, publicType, deviceClass,deviceType,manufacturers,start, limit));
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

/**
 * 
 * updateDoc 传输与接入网系统-波分(DWDM)-01修改
 * @return
 */
	@PostMapping("/updateDoc")
	public ResultMsg updateDoc(@RequestParam("id") String id, 
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "systemName") String systemName,
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "deviceId") String deviceId,
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
			@RequestParam(name = "totalCapacity") String totalCapacity, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			@RequestParam(name = "configChannel") String configChannel, 
			@RequestParam(name = "assetRatio") String assetRatio, 
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
		document.put("systemName", systemName);
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("deviceId", deviceId);
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
		document.put("totalCapacity", totalCapacity); 
		document.put("roadCapacity", roadCapacity); 
		document.put("configChannel", configChannel); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	/**
	 * 
	 * sdhUpdateDoc 传输与接入网系统-同步数字体系(SDH)-02
	 * @return
	 */
		@PostMapping("/sdhUpdateDoc")
		public ResultMsg sdhUpdateDoc(@RequestParam("id") String id, 
				@RequestParam(name = "workshop") String workshop,
				@RequestParam(name = "workArea") String workArea,
				@RequestParam(name = "combinationClass") String combinationClass,
				@RequestParam(name = "deviceClass") String deviceClass, 
				@RequestParam(name = "systemName") String systemName,
				@RequestParam(name = "deviceCode") String deviceCode,
				@RequestParam(name = "deviceName") String deviceName,
				@RequestParam(name = "deviceId") String deviceId,
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
				@RequestParam(name = "totalCapacity") String totalCapacity, 
				@RequestParam(name = "roadCapacity") String roadCapacity, 
				@RequestParam(name = "assetRatio") String assetRatio, 
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
			document.put("systemName", systemName);
			document.put("deviceCode", deviceCode);
			document.put("deviceName", deviceName);
			document.put("deviceId", deviceId);
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
			document.put("totalCapacity", totalCapacity); 
			document.put("roadCapacity", roadCapacity); 
			document.put("assetRatio", assetRatio); 
			document.put("productionDate", productionDate); 
			document.put("useDate", useDate); 
			document.put("deviceOperationStatus", deviceOperationStatus); 
			document.put("stopDate", stopDate); 
			document.put("scrapDate", scrapDate); 
			document.put("fixedAssetsCode", fixedAssetsCode); 
			document.put("remark", remark);
			document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
			
			Document res = service.updateDocument(document, collectionName);
			return ResultMsg.getSuccess("修改成功", res);
		}
		/**
		 * 
		 * numberTransUpdateDoc 传输与接入网系统-准同步数字传输体系-03
		 * @return
		 */
			@PostMapping("/numberTransUpdateDoc")
			public ResultMsg numberTransUpdateDoc(@RequestParam("id") String id, 
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
					@RequestParam(name = "totalCapacity") String totalCapacity, 
					@RequestParam(name = "roadCapacity") String roadCapacity, 
					@RequestParam(name = "assetRatio") String assetRatio, 
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
				document.put("totalCapacity", totalCapacity); 
				document.put("roadCapacity", roadCapacity); 
				document.put("assetRatio", assetRatio); 
				document.put("productionDate", productionDate); 
				document.put("useDate", useDate); 
				document.put("deviceOperationStatus", deviceOperationStatus); 
				document.put("stopDate", stopDate); 
				document.put("scrapDate", scrapDate); 
				document.put("fixedAssetsCode", fixedAssetsCode); 
				document.put("remark", remark);
				document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
				
				Document res = service.updateDocument(document, collectionName);
				return ResultMsg.getSuccess("修改成功", res);
			}
			/**
			 * 
			 * otherUpdateDoc 传输与接入网系统-其它设备-04
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
				@RequestParam(name = "accessType") String accessType, 
				@RequestParam(name = "manufacturers") String manufacturers, 
				@RequestParam(name = "deviceType") String deviceType, 
				@RequestParam(name = "useUnit") String useUnit, 
				@RequestParam(name = "totalCapacity") String totalCapacity, 
				@RequestParam(name = "roadCapacity") String roadCapacity, 
				@RequestParam(name = "assetRatio") String assetRatio, 
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
			document.put("accessType", accessType); 
			document.put("manufacturers", manufacturers); 
			document.put("deviceType", deviceType); 
			document.put("useUnit", useUnit); 
			document.put("totalCapacity", totalCapacity); 
			document.put("roadCapacity", roadCapacity); 
			document.put("assetRatio", assetRatio); 
			document.put("productionDate", productionDate); 
			document.put("useDate", useDate); 
			document.put("deviceOperationStatus", deviceOperationStatus); 
			document.put("stopDate", stopDate); 
			document.put("scrapDate", scrapDate); 
			document.put("fixedAssetsCode", fixedAssetsCode); 
			document.put("remark", remark);
			document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
			
			Document res = service.updateDocument(document, collectionName);
			return ResultMsg.getSuccess("修改成功", res);
		}
		/**
		 * 
		 * otnUpdateDoc 传输与接入网系统-光传送网(OTN)-05
		 * @return
		 */
			@PostMapping("/otnUpdateDoc")
			public ResultMsg otnUpdateDoc(@RequestParam("id") String id, 
					@RequestParam(name = "workshop") String workshop,
					@RequestParam(name = "workArea") String workArea,
					@RequestParam(name = "combinationClass") String combinationClass,
					@RequestParam(name = "deviceClass") String deviceClass, 
					@RequestParam(name = "systemName") String systemName,
					@RequestParam(name = "deviceCode") String deviceCode,
					@RequestParam(name = "deviceName") String deviceName,
					@RequestParam(name = "deviceId") String deviceId,
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
					@RequestParam(name = "totalCapacity") String totalCapacity, 
					@RequestParam(name = "roadCapacity") String roadCapacity, 
					@RequestParam(name = "configChannel") String configChannel, 
					@RequestParam(name = "assetRatio") String assetRatio, 
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
				document.put("systemName", systemName);
				document.put("deviceCode", deviceCode);
				document.put("deviceName", deviceName);
				document.put("deviceId", deviceId);
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
				document.put("totalCapacity", totalCapacity); 
				document.put("roadCapacity", roadCapacity); 
				document.put("configChannel", configChannel); 
				document.put("assetRatio", assetRatio); 
				document.put("productionDate", productionDate); 
				document.put("useDate", useDate); 
				document.put("deviceOperationStatus", deviceOperationStatus); 
				document.put("stopDate", stopDate); 
				document.put("scrapDate", scrapDate); 
				document.put("fixedAssetsCode", fixedAssetsCode); 
				document.put("remark", remark);
				document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
				
				Document res = service.updateDocument(document, collectionName);
				return ResultMsg.getSuccess("修改成功", res);
			}
//	 //导入人员信息Excel
//     @RequestMapping("/dwdmImport")
//	 @ResponseBody
//		public ResultMsg wxImport(
//				 @RequestParam(name="file_excel",required=false) MultipartFile file,
//				 @RequestParam(name="userId",required=false) String userId,
//				 @RequestParam(name="orgId",required=false) String loginOrgId,
//				 @RequestParam(name="orgType",required=false) String loginOrgType,
//				 HttpServletRequest request,
//				 HttpServletResponse response
//				) throws IOException{
//    	try {
//    		 ReadExcelTransDwdm readExcel =new ReadExcelTransDwdm();
//    		 List<TransDwdmDto> fdpList = readExcel.getExcelInfo(file);
//    		 
//    		 //查询一遍数据库中的所有数据用于和表格中的数据比较
//			 List<Document> listAll = service.findAllDocument("deviceRecord",null,null,null,null,null,null,null, 0, 0);
//        	//存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
//			 List<String> reatList = new ArrayList<String>();
//			//表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
//			 listAll.stream().forEach(s->{
//			//判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
//				 if(StringUtils.isNotBlank((String) s.get("source"))==false){
//				 reatList.add((String)s.get("docId"));
//				 }
//			 });
////			 
//			 //验证表格中的必填数据是否有空
//			 Map<String, Object> checkNullMap = checkNullRow(fdpList);
//			 Map<String, Object> checkCjMap = checkWorkshopAndWorkArea(fdpList);
//			 if((boolean) checkNullMap.get("isNull")==false){
//				 return (ResultMsg) checkNullMap.get("ResultMsg");
//			 }
//			 //验证填写的车间工区是否正确
//			 else if((boolean) checkCjMap.get("isExist")==false) {
//				 return (ResultMsg) checkCjMap.get("ResultMsg");
//			 }
//			 else {
//	    	 fdpList.stream().forEach(s->{
//	    	    String collectionName ="deviceRecord";
//	    	    Document document = new Document();
//	    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    		String creatDateStr = "";
//	    		try {
//	    			creatDateStr = sdf.format(new Date());
//	    		} catch (Exception e) {
//	    			e.printStackTrace();
//	    		}
//	    		document.put("creatDateStr", creatDateStr);
//	    		document.put("publicType", "trans_dwdm");
//	    		
//	    		document.put("workshop", s.getWorkshop());
//	    		document.put("workArea", s.getWorkArea());
//	    		document.put("combinationClass", s.getCombinationClass());
//	    		document.put("deviceClass", s.getDeviceClass()); 
//	    		document.put("systemName", s.getSystemName());
//	    		document.put("deviceCode", s.getDeviceCode());
//	    		document.put("deviceName", s.getDeviceName());
//	    		document.put("deviceId", s.getDeviceId());
//	    		document.put("site_station_line", s.getSite_station_line());
//	    		document.put("site_station_name", s.getSite_station_name()); 
//	    		document.put("site_station_place", s.getSite_station_place());
//	    		document.put("site_range_line", s.getSite_range_line());
//	    		document.put("site_range_post", s.getSite_range_post());
//	    		document.put("site_range_place", s.getSite_range_place()); 
//	    		document.put("site_other_line", s.getSite_other_line());
//	    		document.put("site_other_place", s.getSite_other_place());
//	    		document.put("site_machineRoomCode", s.getSite_machineRoomCode());
//	    		document.put("assetOwnership", s.getAssetOwnership());
//	    		document.put("ownershipUnitName", s.getOwnershipUnitName());
//	    		document.put("ownershipUnitCode", s.getOwnershipUnitCode()); 
//	    		document.put("maintainBody", s.getMaintainBody()); 
//	    		document.put("maintainUnitName", s.getMaintainUnitName()); 
//	    		document.put("maintainUnitCode", s.getMaintainUnitCode()); 
//	    		document.put("manufacturers", s.getManufacturers()); 
//	    		document.put("deviceType", s.getDeviceType()); 
//	    		document.put("useUnit", s.getUseUnit()); 
//	    		document.put("totalCapacity", s.getTotalCapacity()); 
//	    		document.put("roadCapacity", s.getRoadCapacity()); 
//	    		document.put("configChannel", s.getConfigChannel()); 
//	    		document.put("assetRatio", s.getAssetRatio()); 
//	    		document.put("productionDate", s.getProductionDate()); 
//	    		document.put("useDate", s.getUseDate()); 
//	    		document.put("deviceOperationStatus", s.getDeviceOperationStatus()); 
//	    		document.put("stopDate", s.getStopDate()); 
//	    		document.put("scrapDate", s.getScrapDate()); 
//	    		document.put("fixedAssetsCode", s.getFixedAssetsCode()); 
//	    		document.put("remark", s.getRemark());
//	    		
//	    		document.put("userId", userId);
//	    		document.put("orgId", loginOrgId);
//			    service.addDocument(document,collectionName);
//	    	 });
//	    	 //删除数据库中来源为上一次导入的数据
//	    	 service.removeDocument(reatList, "deviceRecord");
//	    	 return ResultMsg.getSuccess("导入成功");
//			 }
//		} catch (Exception e) {
//	    	 return ResultMsg.getFailure("导入失败");
//		}
//     }
    @PostMapping("/getchildOrgId")
	public  List<String> getchildOrgId(String orgId) {
    	List<Map<String, Object>> list =service.getChildIdByOrgId(orgId);
    	List<String> orgIdlist = new ArrayList<String>();
    	list.stream().forEach(s->{
    		orgIdlist.add((String) s.get("orgId"));
    	});
		return orgIdlist;
	}
	
//	/**
//	 * 验证导入数据中的必填项是否有未填列
//	 */
//	public Map<String,Object> checkNullRow(List<TransDwdmDto> fdpList) {
//	 Map<String, Object> map = new HashMap<String, Object>();
//     //检验每一必填列数据中是否有未填，默认为true
//   	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
//   	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
//   	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
//   	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
//   	 boolean systemNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSystemName())==false);
//   	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
//   	 boolean deviceIdNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceId())==false);
//   	 boolean site_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_machineRoomCode())==false);
//   	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
//   	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
//   	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
//   	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
//   	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
//   	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
//   	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
//   	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
//   	 boolean configChannelNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getConfigChannel())==false);
//   	 boolean useDateNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseDate())==false);
//   	 boolean deviceOperationStatusNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceOperationStatus())==false);
//   	 
//   	 if(workshopNull==true) {
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
//   		return map; 
//   	 }else if(workAreaNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
//   		return map;
//   	 }else if(combinationClassNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
//   		return map;
//   	 }else if(deviceClassNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
//   		return map;
//   	 }else if(systemNameNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'系统名称'列是否有空白格"));
//   		return map;
//   	 }else if(deviceNameNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
//   		return map;
//   	 }else if(deviceIdNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备ID'列是否有空白格"));
//   		return map;
//   	 }else if(site_machineRoomCodeNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'机房、接入点编码'列是否有空白格"));
//   		return map;
//   	 }else if(assetOwnershipNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
//   		return map;
//   	 }else if(ownershipUnitNameNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
//   		return map;
//   	 }else if(ownershipUnitCodeNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
//   		return map;
//   	 }else if(maintainBodyNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
//   		return map;
//   	 }else if(maintainUnitNameNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
//   		return map;
//   	 }else if(maintainUnitCodeNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
//   		return map;
//   	 }else if(manufacturers==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
//   		return map;
//   	 }else if(deviceTypeNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备型号'列是否有空白格"));
//   		return map;
//   	 }else if(configChannelNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'配置波道'列是否有空白格"));
//   		return map;
//   	 }else if(useDateNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
//   		return map;
//   	 }else if(deviceOperationStatusNull==true){
//   		map.put("isNull", false);
//   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
//   		return map;
//   	 }else {
//   		map.put("isNull", true);
//   		return map; 
//   	 }
//	}
//	//验证车间是否为系统中数据，工区是否在车间下
//	public Map<String,Object> checkWorkshopAndWorkArea(List<TransDwdmDto> fdpList) {
//		 Map<String, Object> map = new HashMap<String, Object>();
//        //获取所有车间
//		List<String> cjList = service.getCadreAndShop();
//	    //workshopCheck为true时，导入的车间全在系统中
//	   	boolean workshopCheck =fdpList.stream().allMatch(f->(cjList.contains( f.getWorkshop())==true));
//        //判断导入的工区是否在导入的车间下
//	   	boolean workAreaCheck =fdpList.stream().allMatch(f->(overhaulService.getWorAreasByName(f.getWorkshop()).contains( f.getWorkArea())==true));
//		if(workshopCheck==false){
//			map.put("isExist", false);
//	   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
//	   		return map;	
//		}
//		else if(workAreaCheck==false){
//			map.put("isExist", false);
//	   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
//	   		return map;	
//		}
//		else {
//			map.put("isExist", true);
//			return map;
//		}
//	}
	
}