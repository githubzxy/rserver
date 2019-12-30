package com.enovell.yunwei.km_micor_service.action.technicalManagement.communicationResumeManage;

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
import com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage.conference.ConferenceService;
import com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage.dispatch.DispatchService;
import com.enovell.yunwei.km_micor_service.util.DeviceRecordPlaceUtil;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：WiredSystemAction   
 * 类描述:  通信调度系统控制器
 * 创建人：zhouxingyu
 * 创建时间：2019年6月17日 上午9:48:47
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月17日 上午9:48:47   
 *
 */
@RestController
@RequestMapping("/dispatchAction")
public class DispatchAction {
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name= "dispatchService")
	private DispatchService service;
	
	
	/**
	 * 新增数据
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/trunk/addDoc")
	public ResultMsg addDocOftrunk(
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
			@RequestParam(name = "numOfTrunkConf") String numOfTrunkConf, 
			@RequestParam(name = "numOfTrunkUsed") String numOfTrunkUsed, 
			@RequestParam(name = "numOfUserConf") String numOfUserConf, 
			@RequestParam(name = "numOfUserUsed") String numOfUserUsed, 
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
		document.put("publicType", "dispatch_trunk");
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
		document.put("numOfTrunkConf", numOfTrunkConf); 
		document.put("numOfTrunkUsed", numOfTrunkUsed); 
		document.put("numOfUserConf", numOfUserConf); 
		document.put("numOfUserUsed", numOfUserUsed); 
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
	

	@PostMapping("/trunk/updateDoc")
	public ResultMsg updateDocOftrunk(@RequestParam("id") String id, 
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
			@RequestParam(name = "numOfTrunkConf") String numOfTrunkConf, 
			@RequestParam(name = "numOfTrunkUsed") String numOfTrunkUsed, 
			@RequestParam(name = "numOfUserConf") String numOfUserConf, 
			@RequestParam(name = "numOfUserUsed") String numOfUserUsed, 
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
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
		document.put("numOfTrunkConf", numOfTrunkConf); 
		document.put("numOfTrunkUsed", numOfTrunkUsed); 
		document.put("numOfUserConf", numOfUserConf); 
		document.put("numOfUserUsed", numOfUserUsed); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	/**
	 * 新增数据
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/dispatchChanger/addDoc")
	public ResultMsg addDocOfdispatchChanger(
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
			@RequestParam(name = "userNumber") String userNumber, 
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "portConf") String portConf, 
			@RequestParam(name = "numOfPortUsed") String numOfPortUsed, 
			@RequestParam(name = "numOfDispatch") String numOfDispatch, 
			@RequestParam(name = "numOfPhone") String numOfPhone, 
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
		document.put("publicType", "dispatch_dispatchChanger");
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
		document.put("userNumber", userNumber); 
		document.put("manufacturers", manufacturers); 
		document.put("deviceType", deviceType); 
		document.put("useUnit", useUnit); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("portConf", portConf); 
		document.put("numOfPortUsed", numOfPortUsed); 
		document.put("numOfDispatch", numOfDispatch); 
		document.put("numOfPhone", numOfPhone); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	

	@PostMapping("/dispatchChanger/updateDoc")
	public ResultMsg updateDocOfdispatchChanger(@RequestParam("id") String id, 
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
			@RequestParam(name = "userNumber") String userNumber,
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
			@RequestParam(name = "stopDate") String stopDate, 
			@RequestParam(name = "scrapDate") String scrapDate, 
			@RequestParam(name = "portConf") String portConf, 
			@RequestParam(name = "numOfPortUsed") String numOfPortUsed, 
			@RequestParam(name = "numOfDispatch") String numOfDispatch, 
			@RequestParam(name = "numOfPhone") String numOfPhone, 
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
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
		document.put("userNumber", userNumber); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("portConf", portConf); 
		document.put("numOfPortUsed", numOfPortUsed); 
		document.put("numOfDispatch", numOfDispatch); 
		document.put("numOfPhone", numOfPhone); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	
	
	/**
	 * 新增数据
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/stationChanger/addDoc")
	public ResultMsg addDocOfstationChanger(
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
			@RequestParam(name = "userNumber") String userNumber,
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
			@RequestParam(name = "stopDate") String stopDate, 
			@RequestParam(name = "scrapDate") String scrapDate, 
			@RequestParam(name = "numOfTrunkConf") String numOfTrunkConf, 
			@RequestParam(name = "numOfTrunkUsed") String numOfTrunkUsed, 
			@RequestParam(name = "numOfUserConf") String numOfUserConf, 
			@RequestParam(name = "numOfUserUsed") String numOfUserUsed, 
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
		document.put("publicType", "dispatch_stationChanger");
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
		document.put("userNumber", userNumber); 
		document.put("manufacturers", manufacturers); 
		document.put("deviceType", deviceType); 
		document.put("useUnit", useUnit); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("numOfTrunkConf", numOfTrunkConf); 
		document.put("numOfTrunkUsed", numOfTrunkUsed); 
		document.put("numOfUserConf", numOfUserConf); 
		document.put("numOfUserUsed", numOfUserUsed); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	

	@PostMapping("/stationChanger/updateDoc")
	public ResultMsg updateDocOfstationChanger(@RequestParam("id") String id, 
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
			@RequestParam(name = "userNumber") String userNumber,
			@RequestParam(name = "manufacturers") String manufacturers, 
			@RequestParam(name = "deviceType") String deviceType, 
			@RequestParam(name = "useUnit") String useUnit, 
			@RequestParam(name = "productionDate") String productionDate, 
			@RequestParam(name = "useDate") String useDate, 
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus, 
			@RequestParam(name = "stopDate") String stopDate, 
			@RequestParam(name = "scrapDate") String scrapDate, 
			@RequestParam(name = "numOfTrunkConf") String numOfTrunkConf, 
			@RequestParam(name = "numOfTrunkUsed") String numOfTrunkUsed, 
			@RequestParam(name = "numOfUserConf") String numOfUserConf, 
			@RequestParam(name = "numOfUserUsed") String numOfUserUsed, 
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
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
		document.put("userNumber", userNumber); 
		document.put("productionDate", productionDate); 
		document.put("useDate", useDate); 
		document.put("deviceOperationStatus", deviceOperationStatus); 
		document.put("stopDate", stopDate); 
		document.put("scrapDate", scrapDate); 
		document.put("numOfTrunkConf", numOfTrunkConf); 
		document.put("numOfTrunkUsed", numOfTrunkUsed); 
		document.put("numOfUserConf", numOfUserConf); 
		document.put("numOfUserUsed", numOfUserUsed); 
		document.put("fixedAssetsCode", fixedAssetsCode); 
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}


	/**
	 * 新增数据
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/duty/addDoc")
	public ResultMsg addDocOfduty(
			@RequestParam(name = "numbeDigital") String numbeDigital,
			@RequestParam(name = "numberDispatch") String numberDispatch,
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
		document.put("publicType", "dispatch_duty");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		document.put("numbeDigital", numbeDigital);
		document.put("numberDispatch", numberDispatch);
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
		document.put("userId", userId);
		document.put("orgId", orgId);
		// Map<String, Object> org = userService.getOrgbyUserId(userId);
		// document.put("createOrg", org.get("ORG_ID_"));
		// document.put("createOrgName", org.get("ORG_NAME_"));
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	@PostMapping("/duty/updateDoc")
	public ResultMsg updateDocOfduty(@RequestParam("id") String id, 
			@RequestParam(name = "numbeDigital") String numbeDigital,
			@RequestParam(name = "numberDispatch") String numberDispatch,
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
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据
		document.put("numbeDigital", numbeDigital);
		document.put("numberDispatch", numberDispatch);
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
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}

	@PostMapping("/other/updateDoc")
	public ResultMsg updateDocOfother(@RequestParam("id") String id, 
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
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
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
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	
	/**
	 * 新增数据
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/other/addDoc")
	public ResultMsg addDocOfother(
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
		document.put("publicType", "dispatch_other");
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
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	

	@PostMapping("/station/updateDoc")
	public ResultMsg updateDocOfstation(@RequestParam("id") String id, 
			@RequestParam(name = "numberSwitchboar") String numberSwitchboar,
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
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode, 
			@RequestParam(name = "remark") String remark, 
			@RequestParam(name = "collectionName") String collectionName,
			@RequestParam(name = "userId") String userId, HttpServletRequest request) {
		Document document = service.findDocumentById(id, collectionName);
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据
		document.put("numberSwitchboar", numberSwitchboar);
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
		
		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	/**
	 * 新增数据
	 * 
	 * @param 备注：remark
	 * @param collectionName
	 *            存储表名
	 * @return 保存后的数据对象
	 */
	@PostMapping("/station/addDoc")
	public ResultMsg addDocOfstation(
			@RequestParam(name = "numberSwitchboar") String numberSwitchboar,
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
		document.put("publicType", "dispatch_station");
		document.put("source", "0");//此字段用于判断是否为导入的表格中的数据

		document.put("numberSwitchboar", numberSwitchboar);
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
		document.put("userId", userId);
		document.put("orgId", orgId);
   		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
   	 
	}
	

	
}
