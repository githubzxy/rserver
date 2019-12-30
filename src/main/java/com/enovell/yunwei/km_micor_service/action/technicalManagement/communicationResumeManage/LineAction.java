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
@RequestMapping("/lineAction")
public class LineAction {
	@Resource(name = "transService")
	private TransService service;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "overhaulRecordService")
	private OverhaulRecordService overhaulService;
	@Resource(name = "networkManageInfoService")
    private NetworkManageInfoService netService;
	
	
	/**
	 * 新增数据  通信线路系统-光缆属性-01
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/lightAddDoc")
	public ResultMsg addDoc(

			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "laidLength") String laidLength,
			@RequestParam(name = "laidMethod") String laidMethod,
			
			@RequestParam(name = "site_start_station_line") String site_start_station_line,
			@RequestParam(name = "site_start_station_name") String site_start_station_name, 
			@RequestParam(name = "site_start_station_place") String site_start_station_place,
			@RequestParam(name = "site_start_range_line") String site_start_range_line,
			@RequestParam(name = "site_start_range_post") String site_start_range_post,
			@RequestParam(name = "site_start_range_place") String site_start_range_place, 
			@RequestParam(name = "site_start_other_line") String site_start_other_line,
			@RequestParam(name = "site_start_other_place") String site_start_other_place,
			@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
			
			@RequestParam(name = "site_end_station_line") String site_end_station_line,
			@RequestParam(name = "site_end_station_name") String site_end_station_name, 
			@RequestParam(name = "site_end_station_place") String site_end_station_place,
			@RequestParam(name = "site_end_range_line") String site_end_range_line,
			@RequestParam(name = "site_end_range_post") String site_end_range_post,
			@RequestParam(name = "site_end_range_place") String site_end_range_place, 
			@RequestParam(name = "site_end_other_line") String site_end_other_line,
			@RequestParam(name = "site_end_other_place") String site_end_other_place,
			@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
			
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
			@RequestParam(name = "coreNumber") String coreNumber, 
			@RequestParam(name = "useCoreNumber") String useCoreNumber, 
			@RequestParam(name = "assetRatio") String assetRatio, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "middleRepairDate") String middleRepairDate, 
			@RequestParam(name = "largeRepairDate") String largeRepairDate, 
			
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
		document.put("publicType", "line_light");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("laidLength", laidLength);
		document.put("laidMethod", laidMethod);
		
		document.put("site_start_station_line", site_start_station_line);
		document.put("site_start_station_name", site_start_station_name); 
		document.put("site_start_station_place", site_start_station_place);
		document.put("site_start_range_line", site_start_range_line);
		document.put("site_start_range_post", site_start_range_post);
		document.put("site_start_range_place", site_start_range_place); 
		document.put("site_start_other_line", site_start_other_line);
		document.put("site_start_other_place", site_start_other_place);
		document.put("site_start_machineRoomCode", site_start_machineRoomCode);
		
		document.put("site_end_station_line", site_end_station_line);
		document.put("site_end_station_name", site_end_station_name); 
		document.put("site_end_station_place", site_end_station_place);
		document.put("site_end_range_line", site_end_range_line);
		document.put("site_end_range_post", site_end_range_post);
		document.put("site_end_range_place", site_end_range_place); 
		document.put("site_end_other_line", site_end_other_line);
		document.put("site_end_other_place", site_end_other_place);
		document.put("site_end_machineRoomCode", site_end_machineRoomCode);
		
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
		document.put("coreNumber", coreNumber); 
		document.put("useCoreNumber", useCoreNumber); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("middleRepairDate", middleRepairDate); 
		document.put("largeRepairDate", largeRepairDate); 
		
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark); 
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据  通信线路系统-电缆属性-02
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/electricityAddDoc")
	public ResultMsg electricityaddDoc(

			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "laidLength") String laidLength,
			@RequestParam(name = "laidMethod") String laidMethod,
			
			@RequestParam(name = "site_start_station_line") String site_start_station_line,
			@RequestParam(name = "site_start_station_name") String site_start_station_name, 
			@RequestParam(name = "site_start_station_place") String site_start_station_place,
			@RequestParam(name = "site_start_range_line") String site_start_range_line,
			@RequestParam(name = "site_start_range_post") String site_start_range_post,
			@RequestParam(name = "site_start_range_place") String site_start_range_place, 
			@RequestParam(name = "site_start_other_line") String site_start_other_line,
			@RequestParam(name = "site_start_other_place") String site_start_other_place,
			@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
			
			@RequestParam(name = "site_end_station_line") String site_end_station_line,
			@RequestParam(name = "site_end_station_name") String site_end_station_name, 
			@RequestParam(name = "site_end_station_place") String site_end_station_place,
			@RequestParam(name = "site_end_range_line") String site_end_range_line,
			@RequestParam(name = "site_end_range_post") String site_end_range_post,
			@RequestParam(name = "site_end_range_place") String site_end_range_place, 
			@RequestParam(name = "site_end_other_line") String site_end_other_line,
			@RequestParam(name = "site_end_other_place") String site_end_other_place,
			@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
			
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
			@RequestParam(name = "useCoreNumber") String useCoreNumber, 
			@RequestParam(name = "assetRatio") String assetRatio, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "middleRepairDate") String middleRepairDate, 
			@RequestParam(name = "largeRepairDate") String largeRepairDate, 
			
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
		document.put("publicType", "line_electricity");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		document.put("laidLength", laidLength);
		document.put("laidMethod", laidMethod);
		
		document.put("site_start_station_line", site_start_station_line);
		document.put("site_start_station_name", site_start_station_name); 
		document.put("site_start_station_place", site_start_station_place);
		document.put("site_start_range_line", site_start_range_line);
		document.put("site_start_range_post", site_start_range_post);
		document.put("site_start_range_place", site_start_range_place); 
		document.put("site_start_other_line", site_start_other_line);
		document.put("site_start_other_place", site_start_other_place);
		document.put("site_start_machineRoomCode", site_start_machineRoomCode);
		
		document.put("site_end_station_line", site_end_station_line);
		document.put("site_end_station_name", site_end_station_name); 
		document.put("site_end_station_place", site_end_station_place);
		document.put("site_end_range_line", site_end_range_line);
		document.put("site_end_range_post", site_end_range_post);
		document.put("site_end_range_place", site_end_range_place); 
		document.put("site_end_other_line", site_end_other_line);
		document.put("site_end_other_place", site_end_other_place);
		document.put("site_end_machineRoomCode", site_end_machineRoomCode);
		
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
		document.put("useCoreNumber", useCoreNumber); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("middleRepairDate", middleRepairDate); 
		document.put("largeRepairDate", largeRepairDate); 
		
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark); 
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据  通信线路系统-线条属性-03
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/lineAddDoc")
	public ResultMsg lineAddDoc(
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass,
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "laidLength") String laidLength,
			@RequestParam(name = "laidMethod") String laidMethod,
			@RequestParam(name = "site_line") String site_line,
			@RequestParam(name = "site_start_station") String site_start_station,
			@RequestParam(name = "site_start_place") String site_start_place,
			@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
			@RequestParam(name = "site_end_station") String site_end_station,
			@RequestParam(name = "site_end_place") String site_end_place,
			@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
			@RequestParam(name = "assetOwnership") String assetOwnership,
			@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
			@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode,
			@RequestParam(name = "maintainBody") String maintainBody,
			@RequestParam(name = "maintainUnitName") String maintainUnitName,
			@RequestParam(name = "maintainUnitCode") String maintainUnitCode,
			@RequestParam(name = "manufacturers") String manufacturers,
			@RequestParam(name = "deviceType") String deviceType,
			@RequestParam(name = "useUnit") String useUnit,
			@RequestParam(name = "lineType") String lineType,
			@RequestParam(name = "useLogarithm") String useLogarithm,
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
		document.put("publicType", "line_line");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		document.put("workshop",workshop);
		document.put("workArea",workArea);
		document.put("combinationClass",combinationClass);
		document.put("deviceClass",deviceClass);
		document.put("deviceCode",deviceCode);
		document.put("deviceName",deviceName);
		document.put("laidLength",laidLength);
		document.put("laidMethod",laidMethod);
		document.put("site_line",site_line);
		document.put("site_start_station",site_start_station);
		document.put("site_start_place",site_start_place);
		document.put("site_start_machineRoomCode",site_start_machineRoomCode);
		document.put("site_end_station",site_end_station);
		document.put("site_end_place",site_end_place);
		document.put("site_end_machineRoomCode",site_end_machineRoomCode);
		document.put("assetOwnership",assetOwnership);
		document.put("ownershipUnitName",ownershipUnitName);
		document.put("ownershipUnitCode",ownershipUnitCode);
		document.put("maintainBody",maintainBody);
		document.put("maintainUnitName",maintainUnitName);
		document.put("maintainUnitCode",maintainUnitCode);
		document.put("manufacturers",manufacturers);
		document.put("deviceType",deviceType);
		document.put("useUnit",useUnit);
		document.put("lineType",lineType);
		document.put("useLogarithm",useLogarithm);
		document.put("productionDate",productionDate);
		document.put("useDate",useDate);
		document.put("deviceOperationStatus",deviceOperationStatus);
		document.put("stopDate",stopDate);
		document.put("scrapDate",scrapDate);
		document.put("fixedAssetsCode",fixedAssetsCode);
		document.put("remark",remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinellaePlace(site_start_place, site_end_place));
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据  通信线路系统-线缆与光缆配套(含电杆类)-04
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/lineAndLightAddDoc")
	public ResultMsg lineAndLightAddDoc(
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
		document.put("publicType", "line_lineAndLight");
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
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据 通信线路系统-管道-05
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/pipelineAddDoc")
	public ResultMsg pipelineAddDoc(

			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			
			@RequestParam(name = "site_start_station_line") String site_start_station_line,
			@RequestParam(name = "site_start_station_name") String site_start_station_name, 
			@RequestParam(name = "site_start_station_place") String site_start_station_place,
			@RequestParam(name = "site_start_range_line") String site_start_range_line,
			@RequestParam(name = "site_start_range_post") String site_start_range_post,
			@RequestParam(name = "site_start_range_place") String site_start_range_place, 
			@RequestParam(name = "site_start_other_line") String site_start_other_line,
			@RequestParam(name = "site_start_other_place") String site_start_other_place,
			@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
			
			@RequestParam(name = "site_end_station_line") String site_end_station_line,
			@RequestParam(name = "site_end_station_name") String site_end_station_name, 
			@RequestParam(name = "site_end_station_place") String site_end_station_place,
			@RequestParam(name = "site_end_range_line") String site_end_range_line,
			@RequestParam(name = "site_end_range_post") String site_end_range_post,
			@RequestParam(name = "site_end_range_place") String site_end_range_place, 
			@RequestParam(name = "site_end_other_line") String site_end_other_line,
			@RequestParam(name = "site_end_other_place") String site_end_other_place,
			@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
			
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
			@RequestParam(name = "assetRatio") String assetRatio, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "length") String length, 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			
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
		document.put("publicType", "line_pipeline");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		
		document.put("site_start_station_line", site_start_station_line);
		document.put("site_start_station_name", site_start_station_name); 
		document.put("site_start_station_place", site_start_station_place);
		document.put("site_start_range_line", site_start_range_line);
		document.put("site_start_range_post", site_start_range_post);
		document.put("site_start_range_place", site_start_range_place); 
		document.put("site_start_other_line", site_start_other_line);
		document.put("site_start_other_place", site_start_other_place);
		document.put("site_start_machineRoomCode", site_start_machineRoomCode);
		
		document.put("site_end_station_line", site_end_station_line);
		document.put("site_end_station_name", site_end_station_name); 
		document.put("site_end_station_place", site_end_station_place);
		document.put("site_end_range_line", site_end_range_line);
		document.put("site_end_range_post", site_end_range_post);
		document.put("site_end_range_place", site_end_range_place); 
		document.put("site_end_other_line", site_end_other_line);
		document.put("site_end_other_place", site_end_other_place);
		document.put("site_end_machineRoomCode", site_end_machineRoomCode);
		
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
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("length", length); 
		document.put("roadCapacity", roadCapacity); 
		
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}	
	/**
	 * 新增数据  通信线路系统-人孔(手孔)-06
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/holeAddDoc")
	public ResultMsg holeAddDoc(
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass,
			@RequestParam(name = "deviceName") String deviceName,
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
			@RequestParam(name = "site") String site,
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
		document.put("publicType", "line_hole");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		document.put("workshop",workshop);
		document.put("workArea",workArea);
		document.put("combinationClass",combinationClass);
		document.put("deviceClass",deviceClass);
		document.put("deviceName",deviceName);
		document.put("assetOwnership",assetOwnership);
		document.put("ownershipUnitName",ownershipUnitName);
		document.put("ownershipUnitCode",ownershipUnitCode);
		document.put("maintainBody",maintainBody);
		document.put("maintainUnitName",maintainUnitName);
		document.put("maintainUnitCode",maintainUnitCode);
		document.put("manufacturers",manufacturers);
		document.put("deviceType",deviceType);
		document.put("useUnit",useUnit);
		document.put("site",site);
		document.put("productionDate",productionDate);
		document.put("useDate",useDate);
		document.put("deviceOperationStatus",deviceOperationStatus);
		document.put("stopDate",stopDate);
		document.put("scrapDate",scrapDate);
		document.put("fixedAssetsCode",fixedAssetsCode);
		document.put("remark",remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putSitePlace(site));
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	/**
	 * 新增数据  通信线路系统-其他线缆-07
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
			
			@RequestParam(name = "site_start_station_line") String site_start_station_line,
			@RequestParam(name = "site_start_station_name") String site_start_station_name, 
			@RequestParam(name = "site_start_station_place") String site_start_station_place,
			@RequestParam(name = "site_start_range_line") String site_start_range_line,
			@RequestParam(name = "site_start_range_post") String site_start_range_post,
			@RequestParam(name = "site_start_range_place") String site_start_range_place, 
			@RequestParam(name = "site_start_other_line") String site_start_other_line,
			@RequestParam(name = "site_start_other_place") String site_start_other_place,
			@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
			
			@RequestParam(name = "site_end_station_line") String site_end_station_line,
			@RequestParam(name = "site_end_station_name") String site_end_station_name, 
			@RequestParam(name = "site_end_station_place") String site_end_station_place,
			@RequestParam(name = "site_end_range_line") String site_end_range_line,
			@RequestParam(name = "site_end_range_post") String site_end_range_post,
			@RequestParam(name = "site_end_range_place") String site_end_range_place, 
			@RequestParam(name = "site_end_other_line") String site_end_other_line,
			@RequestParam(name = "site_end_other_place") String site_end_other_place,
			@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
			
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
			@RequestParam(name = "assetRatio") String assetRatio, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "length") String length  , 
			@RequestParam(name = "roadCapacity") String roadCapacity, 
			
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
		document.put("publicType", "line_other");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		
		document.put("workshop", workshop);
		document.put("workArea", workArea);
		document.put("combinationClass", combinationClass);
		document.put("deviceClass", deviceClass); 
		document.put("deviceCode", deviceCode);
		document.put("deviceName", deviceName);
		
		document.put("site_start_station_line", site_start_station_line);
		document.put("site_start_station_name", site_start_station_name); 
		document.put("site_start_station_place", site_start_station_place);
		document.put("site_start_range_line", site_start_range_line);
		document.put("site_start_range_post", site_start_range_post);
		document.put("site_start_range_place", site_start_range_place); 
		document.put("site_start_other_line", site_start_other_line);
		document.put("site_start_other_place", site_start_other_place);
		document.put("site_start_machineRoomCode", site_start_machineRoomCode);
		
		document.put("site_end_station_line", site_end_station_line);
		document.put("site_end_station_name", site_end_station_name); 
		document.put("site_end_station_place", site_end_station_place);
		document.put("site_end_range_line", site_end_range_line);
		document.put("site_end_range_post", site_end_range_post);
		document.put("site_end_range_place", site_end_range_place); 
		document.put("site_end_other_line", site_end_other_line);
		document.put("site_end_other_place", site_end_other_place);
		document.put("site_end_machineRoomCode", site_end_machineRoomCode);
		
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
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("length", length); 
		document.put("roadCapacity", roadCapacity); 
		
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark); 
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
		
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
/**
 * 
 * updateDoc 通信线路系统-光缆属性-01
 * @return
 */
	@PostMapping("/lightUpdateDoc")
	public ResultMsg lightUpdateDoc(@RequestParam("id") String id, 
			@RequestParam(name = "workshop") String workshop,
			@RequestParam(name = "workArea") String workArea,
			@RequestParam(name = "combinationClass") String combinationClass,
			@RequestParam(name = "deviceClass") String deviceClass, 
			@RequestParam(name = "deviceCode") String deviceCode,
			@RequestParam(name = "deviceName") String deviceName,
			@RequestParam(name = "laidLength") String laidLength,
			@RequestParam(name = "laidMethod") String laidMethod,
			
			@RequestParam(name = "site_start_station_line") String site_start_station_line,
			@RequestParam(name = "site_start_station_name") String site_start_station_name, 
			@RequestParam(name = "site_start_station_place") String site_start_station_place,
			@RequestParam(name = "site_start_range_line") String site_start_range_line,
			@RequestParam(name = "site_start_range_post") String site_start_range_post,
			@RequestParam(name = "site_start_range_place") String site_start_range_place, 
			@RequestParam(name = "site_start_other_line") String site_start_other_line,
			@RequestParam(name = "site_start_other_place") String site_start_other_place,
			@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
			
			@RequestParam(name = "site_end_station_line") String site_end_station_line,
			@RequestParam(name = "site_end_station_name") String site_end_station_name, 
			@RequestParam(name = "site_end_station_place") String site_end_station_place,
			@RequestParam(name = "site_end_range_line") String site_end_range_line,
			@RequestParam(name = "site_end_range_post") String site_end_range_post,
			@RequestParam(name = "site_end_range_place") String site_end_range_place, 
			@RequestParam(name = "site_end_other_line") String site_end_other_line,
			@RequestParam(name = "site_end_other_place") String site_end_other_place,
			@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
			
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
			@RequestParam(name = "coreNumber") String coreNumber, 
			@RequestParam(name = "useCoreNumber") String useCoreNumber, 
			@RequestParam(name = "assetRatio") String assetRatio, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "middleRepairDate") String middleRepairDate, 
			@RequestParam(name = "largeRepairDate") String largeRepairDate, 
			
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
		document.put("laidLength", laidLength);
		document.put("laidMethod", laidMethod);
		
		document.put("site_start_station_line", site_start_station_line);
		document.put("site_start_station_name", site_start_station_name); 
		document.put("site_start_station_place", site_start_station_place);
		document.put("site_start_range_line", site_start_range_line);
		document.put("site_start_range_post", site_start_range_post);
		document.put("site_start_range_place", site_start_range_place); 
		document.put("site_start_other_line", site_start_other_line);
		document.put("site_start_other_place", site_start_other_place);
		document.put("site_start_machineRoomCode", site_start_machineRoomCode);
		
		document.put("site_end_station_line", site_end_station_line);
		document.put("site_end_station_name", site_end_station_name); 
		document.put("site_end_station_place", site_end_station_place);
		document.put("site_end_range_line", site_end_range_line);
		document.put("site_end_range_post", site_end_range_post);
		document.put("site_end_range_place", site_end_range_place); 
		document.put("site_end_other_line", site_end_other_line);
		document.put("site_end_other_place", site_end_other_place);
		document.put("site_end_machineRoomCode", site_end_machineRoomCode);
		
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
		document.put("coreNumber", coreNumber); 
		document.put("useCoreNumber", useCoreNumber); 
		document.put("assetRatio", assetRatio); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("middleRepairDate", middleRepairDate); 
		document.put("largeRepairDate", largeRepairDate); 
		
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark); 
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	
	/**
	 * 
	 * updateDoc 通信线路系统-电缆属性-02
	 * @return
	 */
		@PostMapping("/electricityUpdateDoc")
		public ResultMsg electricityUpdateDoc(@RequestParam("id") String id, 
				@RequestParam(name = "workshop") String workshop,
				@RequestParam(name = "workArea") String workArea,
				@RequestParam(name = "combinationClass") String combinationClass,
				@RequestParam(name = "deviceClass") String deviceClass, 
				@RequestParam(name = "deviceCode") String deviceCode,
				@RequestParam(name = "deviceName") String deviceName,
				@RequestParam(name = "laidLength") String laidLength,
				@RequestParam(name = "laidMethod") String laidMethod,
				
				@RequestParam(name = "site_start_station_line") String site_start_station_line,
				@RequestParam(name = "site_start_station_name") String site_start_station_name, 
				@RequestParam(name = "site_start_station_place") String site_start_station_place,
				@RequestParam(name = "site_start_range_line") String site_start_range_line,
				@RequestParam(name = "site_start_range_post") String site_start_range_post,
				@RequestParam(name = "site_start_range_place") String site_start_range_place, 
				@RequestParam(name = "site_start_other_line") String site_start_other_line,
				@RequestParam(name = "site_start_other_place") String site_start_other_place,
				@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
				
				@RequestParam(name = "site_end_station_line") String site_end_station_line,
				@RequestParam(name = "site_end_station_name") String site_end_station_name, 
				@RequestParam(name = "site_end_station_place") String site_end_station_place,
				@RequestParam(name = "site_end_range_line") String site_end_range_line,
				@RequestParam(name = "site_end_range_post") String site_end_range_post,
				@RequestParam(name = "site_end_range_place") String site_end_range_place, 
				@RequestParam(name = "site_end_other_line") String site_end_other_line,
				@RequestParam(name = "site_end_other_place") String site_end_other_place,
				@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
				
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
				@RequestParam(name = "useCoreNumber") String useCoreNumber, 
				@RequestParam(name = "assetRatio") String assetRatio, 
				@RequestParam(name = "productionDate") String productionDate, 
				@RequestParam(name = "useDate") String useDate, 
				@RequestParam(name = "middleRepairDate") String middleRepairDate, 
				@RequestParam(name = "largeRepairDate") String largeRepairDate, 
				
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
			document.put("laidLength", laidLength);
			document.put("laidMethod", laidMethod);
			
			document.put("site_start_station_line", site_start_station_line);
			document.put("site_start_station_name", site_start_station_name); 
			document.put("site_start_station_place", site_start_station_place);
			document.put("site_start_range_line", site_start_range_line);
			document.put("site_start_range_post", site_start_range_post);
			document.put("site_start_range_place", site_start_range_place); 
			document.put("site_start_other_line", site_start_other_line);
			document.put("site_start_other_place", site_start_other_place);
			document.put("site_start_machineRoomCode", site_start_machineRoomCode);
			
			document.put("site_end_station_line", site_end_station_line);
			document.put("site_end_station_name", site_end_station_name); 
			document.put("site_end_station_place", site_end_station_place);
			document.put("site_end_range_line", site_end_range_line);
			document.put("site_end_range_post", site_end_range_post);
			document.put("site_end_range_place", site_end_range_place); 
			document.put("site_end_other_line", site_end_other_line);
			document.put("site_end_other_place", site_end_other_place);
			document.put("site_end_machineRoomCode", site_end_machineRoomCode);
			
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
			document.put("useCoreNumber", useCoreNumber); 
			document.put("assetRatio", assetRatio); 
			document.put("productionDate", productionDate); 
			document.put("useDate", useDate); 
			document.put("middleRepairDate", middleRepairDate); 
			document.put("largeRepairDate", largeRepairDate); 
			
			document.put("deviceOperationStatus", deviceOperationStatus); 
			document.put("stopDate", stopDate); 
			document.put("scrapDate", scrapDate); 
			document.put("fixedAssetsCode", fixedAssetsCode); 
			document.put("remark", remark); 
			document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
			
			Document res = service.updateDocument(document, collectionName);
			return ResultMsg.getSuccess("修改成功", res);
		}
		/**
		 * 
		 * updateDoc 通信线路系统-线条属性-03
		 * @return
		 */
			@PostMapping("/lineUpdateDoc")
			public ResultMsg lineUpdateDoc(@RequestParam("id") String id, 
					@RequestParam(name = "workshop") String workshop,
					@RequestParam(name = "workArea") String workArea,
					@RequestParam(name = "combinationClass") String combinationClass,
					@RequestParam(name = "deviceClass") String deviceClass,
					@RequestParam(name = "deviceCode") String deviceCode,
					@RequestParam(name = "deviceName") String deviceName,
					@RequestParam(name = "laidLength") String laidLength,
					@RequestParam(name = "laidMethod") String laidMethod,
					@RequestParam(name = "site_line") String site_line,
					@RequestParam(name = "site_start_station") String site_start_station,
					@RequestParam(name = "site_start_place") String site_start_place,
					@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
					@RequestParam(name = "site_end_station") String site_end_station,
					@RequestParam(name = "site_end_place") String site_end_place,
					@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
					@RequestParam(name = "assetOwnership") String assetOwnership,
					@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
					@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode,
					@RequestParam(name = "maintainBody") String maintainBody,
					@RequestParam(name = "maintainUnitName") String maintainUnitName,
					@RequestParam(name = "maintainUnitCode") String maintainUnitCode,
					@RequestParam(name = "manufacturers") String manufacturers,
					@RequestParam(name = "deviceType") String deviceType,
					@RequestParam(name = "useUnit") String useUnit,
					@RequestParam(name = "lineType") String lineType,
					@RequestParam(name = "useLogarithm") String useLogarithm,
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
				
				document.put("workshop",workshop);
				document.put("workArea",workArea);
				document.put("combinationClass",combinationClass);
				document.put("deviceClass",deviceClass);
				document.put("deviceCode",deviceCode);
				document.put("deviceName",deviceName);
				document.put("laidLength",laidLength);
				document.put("laidMethod",laidMethod);
				document.put("site_line",site_line);
				document.put("site_start_station",site_start_station);
				document.put("site_start_place",site_start_place);
				document.put("site_start_machineRoomCode",site_start_machineRoomCode);
				document.put("site_end_station",site_end_station);
				document.put("site_end_place",site_end_place);
				document.put("site_end_machineRoomCode",site_end_machineRoomCode);
				document.put("assetOwnership",assetOwnership);
				document.put("ownershipUnitName",ownershipUnitName);
				document.put("ownershipUnitCode",ownershipUnitCode);
				document.put("maintainBody",maintainBody);
				document.put("maintainUnitName",maintainUnitName);
				document.put("maintainUnitCode",maintainUnitCode);
				document.put("manufacturers",manufacturers);
				document.put("deviceType",deviceType);
				document.put("useUnit",useUnit);
				document.put("lineType",lineType);
				document.put("useLogarithm",useLogarithm);
				document.put("productionDate",productionDate);
				document.put("useDate",useDate);
				document.put("deviceOperationStatus",deviceOperationStatus);
				document.put("stopDate",stopDate);
				document.put("scrapDate",scrapDate);
				document.put("fixedAssetsCode",fixedAssetsCode);
				document.put("remark",remark);
				document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinellaePlace(site_start_place, site_end_place));
				
				Document res = service.updateDocument(document, collectionName);
				return ResultMsg.getSuccess("修改成功", res);
			}
			/**
			 * 
			 * updateDoc 通信线路系统-线缆与光缆配套(含电杆类)-04
			 * @return
			 */
				@PostMapping("/lineAndLightUpdateDoc")
				public ResultMsg lineAndLightUpdateDoc(@RequestParam("id") String id, 
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
	 * updateDoc 通信线路系统-管道-05
	 * @return
	 */
		@PostMapping("/pipelineUpdateDoc")
		public ResultMsg pipelineUpdateDoc(@RequestParam("id") String id, 
				@RequestParam(name = "workshop") String workshop,
				@RequestParam(name = "workArea") String workArea,
				@RequestParam(name = "combinationClass") String combinationClass,
				@RequestParam(name = "deviceClass") String deviceClass, 
				@RequestParam(name = "deviceCode") String deviceCode,
				@RequestParam(name = "deviceName") String deviceName,
				
				@RequestParam(name = "site_start_station_line") String site_start_station_line,
				@RequestParam(name = "site_start_station_name") String site_start_station_name, 
				@RequestParam(name = "site_start_station_place") String site_start_station_place,
				@RequestParam(name = "site_start_range_line") String site_start_range_line,
				@RequestParam(name = "site_start_range_post") String site_start_range_post,
				@RequestParam(name = "site_start_range_place") String site_start_range_place, 
				@RequestParam(name = "site_start_other_line") String site_start_other_line,
				@RequestParam(name = "site_start_other_place") String site_start_other_place,
				@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
				
				@RequestParam(name = "site_end_station_line") String site_end_station_line,
				@RequestParam(name = "site_end_station_name") String site_end_station_name, 
				@RequestParam(name = "site_end_station_place") String site_end_station_place,
				@RequestParam(name = "site_end_range_line") String site_end_range_line,
				@RequestParam(name = "site_end_range_post") String site_end_range_post,
				@RequestParam(name = "site_end_range_place") String site_end_range_place, 
				@RequestParam(name = "site_end_other_line") String site_end_other_line,
				@RequestParam(name = "site_end_other_place") String site_end_other_place,
				@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
				
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
				@RequestParam(name = "assetRatio") String assetRatio, 
				@RequestParam(name = "productionDate") String productionDate, 
				@RequestParam(name = "useDate") String useDate, 
				@RequestParam(name = "length") String length, 
				@RequestParam(name = "roadCapacity") String roadCapacity, 
				
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
			
			document.put("site_start_station_line", site_start_station_line);
			document.put("site_start_station_name", site_start_station_name); 
			document.put("site_start_station_place", site_start_station_place);
			document.put("site_start_range_line", site_start_range_line);
			document.put("site_start_range_post", site_start_range_post);
			document.put("site_start_range_place", site_start_range_place); 
			document.put("site_start_other_line", site_start_other_line);
			document.put("site_start_other_place", site_start_other_place);
			document.put("site_start_machineRoomCode", site_start_machineRoomCode);
			
			document.put("site_end_station_line", site_end_station_line);
			document.put("site_end_station_name", site_end_station_name); 
			document.put("site_end_station_place", site_end_station_place);
			document.put("site_end_range_line", site_end_range_line);
			document.put("site_end_range_post", site_end_range_post);
			document.put("site_end_range_place", site_end_range_place); 
			document.put("site_end_other_line", site_end_other_line);
			document.put("site_end_other_place", site_end_other_place);
			document.put("site_end_machineRoomCode", site_end_machineRoomCode);
			
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
			document.put("assetRatio", assetRatio); 
			document.put("productionDate", productionDate); 
			document.put("useDate", useDate); 
			document.put("length", length); 
			document.put("roadCapacity", roadCapacity); 
			
			document.put("deviceOperationStatus", deviceOperationStatus); 
			document.put("stopDate", stopDate); 
			document.put("scrapDate", scrapDate); 
			document.put("fixedAssetsCode", fixedAssetsCode); 
			document.put("remark", remark);
			document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
			
			Document res = service.updateDocument(document, collectionName);
			return ResultMsg.getSuccess("修改成功", res);
		}
		/**
		 * 
		 * updateDoc 通信线路系统-人孔(手孔)-06
		 * @return
		 */
			@PostMapping("/holeUpdateDoc")
			public ResultMsg holeUpdateDoc(@RequestParam("id") String id, 
					@RequestParam(name = "workshop") String workshop,
					@RequestParam(name = "workArea") String workArea,
					@RequestParam(name = "combinationClass") String combinationClass,
					@RequestParam(name = "deviceClass") String deviceClass,
					@RequestParam(name = "deviceName") String deviceName,
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
					@RequestParam(name = "site") String site,
					@RequestParam(name = "scrapDate") String scrapDate,
					@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode,
					@RequestParam(name = "remark") String remark,

					@RequestParam(name = "collectionName") String collectionName, 
					HttpServletRequest request) {
				Document document = service.findDocumentById(id, collectionName);
				document.put("source", "0");//此字段用于判断是否为导入的表格中的数据
				document.put("workshop",workshop);
				document.put("workArea",workArea);
				document.put("combinationClass",combinationClass);
				document.put("deviceClass",deviceClass);
				document.put("deviceName",deviceName);
				document.put("assetOwnership",assetOwnership);
				document.put("ownershipUnitName",ownershipUnitName);
				document.put("ownershipUnitCode",ownershipUnitCode);
				document.put("maintainBody",maintainBody);
				document.put("maintainUnitName",maintainUnitName);
				document.put("maintainUnitCode",maintainUnitCode);
				document.put("manufacturers",manufacturers);
				document.put("deviceType",deviceType);
				document.put("useUnit",useUnit);
				document.put("site",site);
				document.put("productionDate",productionDate);
				document.put("useDate",useDate);
				document.put("deviceOperationStatus",deviceOperationStatus);
				document.put("stopDate",stopDate);
				document.put("scrapDate",scrapDate);
				document.put("fixedAssetsCode",fixedAssetsCode);
				document.put("remark",remark);
				document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putSitePlace(site));
				
				Document res = service.updateDocument(document, collectionName);
				return ResultMsg.getSuccess("修改成功", res);
			}
	/**
	 * 
	 * updateDoc 通信线路系统-其他线缆-07
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
				
				@RequestParam(name = "site_start_station_line") String site_start_station_line,
				@RequestParam(name = "site_start_station_name") String site_start_station_name, 
				@RequestParam(name = "site_start_station_place") String site_start_station_place,
				@RequestParam(name = "site_start_range_line") String site_start_range_line,
				@RequestParam(name = "site_start_range_post") String site_start_range_post,
				@RequestParam(name = "site_start_range_place") String site_start_range_place, 
				@RequestParam(name = "site_start_other_line") String site_start_other_line,
				@RequestParam(name = "site_start_other_place") String site_start_other_place,
				@RequestParam(name = "site_start_machineRoomCode") String site_start_machineRoomCode,
				
				@RequestParam(name = "site_end_station_line") String site_end_station_line,
				@RequestParam(name = "site_end_station_name") String site_end_station_name, 
				@RequestParam(name = "site_end_station_place") String site_end_station_place,
				@RequestParam(name = "site_end_range_line") String site_end_range_line,
				@RequestParam(name = "site_end_range_post") String site_end_range_post,
				@RequestParam(name = "site_end_range_place") String site_end_range_place, 
				@RequestParam(name = "site_end_other_line") String site_end_other_line,
				@RequestParam(name = "site_end_other_place") String site_end_other_place,
				@RequestParam(name = "site_end_machineRoomCode") String site_end_machineRoomCode,
				
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
				@RequestParam(name = "assetRatio") String assetRatio, 
				@RequestParam(name = "productionDate") String productionDate, 
				@RequestParam(name = "useDate") String useDate, 
				@RequestParam(name = "length") String length  , 
				@RequestParam(name = "roadCapacity") String roadCapacity, 
				
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
			
			document.put("site_start_station_line", site_start_station_line);
			document.put("site_start_station_name", site_start_station_name); 
			document.put("site_start_station_place", site_start_station_place);
			document.put("site_start_range_line", site_start_range_line);
			document.put("site_start_range_post", site_start_range_post);
			document.put("site_start_range_place", site_start_range_place); 
			document.put("site_start_other_line", site_start_other_line);
			document.put("site_start_other_place", site_start_other_place);
			document.put("site_start_machineRoomCode", site_start_machineRoomCode);
			
			document.put("site_end_station_line", site_end_station_line);
			document.put("site_end_station_name", site_end_station_name); 
			document.put("site_end_station_place", site_end_station_place);
			document.put("site_end_range_line", site_end_range_line);
			document.put("site_end_range_post", site_end_range_post);
			document.put("site_end_range_place", site_end_range_place); 
			document.put("site_end_other_line", site_end_other_line);
			document.put("site_end_other_place", site_end_other_place);
			document.put("site_end_machineRoomCode", site_end_machineRoomCode);
			
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
			document.put("assetRatio", assetRatio); 
			document.put("productionDate", productionDate); 
			document.put("useDate", useDate); 
			document.put("length", length); 
			document.put("roadCapacity", roadCapacity); 
			
			document.put("deviceOperationStatus", deviceOperationStatus); 
			document.put("stopDate", stopDate); 
			document.put("scrapDate", scrapDate); 
			document.put("fixedAssetsCode", fixedAssetsCode); 
			document.put("remark", remark); 
			document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putLinePlace(site_start_station_place, site_start_range_place, site_start_other_place, site_end_station_place, site_end_range_place, site_end_other_place));
			
			Document res = service.updateDocument(document, collectionName);
			return ResultMsg.getSuccess("修改成功", res);
		}
}