package com.enovell.yunwei.km_micor_service.action.technicalManagement.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.UserService;
import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.NetworkManageInfoService;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage.TransService;
import com.enovell.yunwei.km_micor_service.util.DeviceRecordPlaceUtil;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 视频监控类 新增，修改，详情
 */
@RestController
@RequestMapping("/videoAction")
public class VideoAction {
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


	/**
	 * 视频监控类-视频监控类-01 新增数据
	 */
	@PostMapping("/addDocVideo")
	public ResultMsg addDocVideo(
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
			@RequestParam(name = "roadCapacity") String roadCapacity,
			@RequestParam(name = "assetRatio") String assetRatio,
			@RequestParam(name = "productionDate") String productionDate,
			@RequestParam(name = "useDate") String useDate,
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus,
			@RequestParam(name = "stopDate") String stopDate,
			@RequestParam(name = "scrapDate") String scrapDate,
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode,
			@RequestParam(name = "capacity") String capacity,
			@RequestParam(name = "capacityUnit") String capacityUnit,

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
		document.put("publicType", "video_video");
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
		document.put("roadCapacity", roadCapacity);
		document.put("assetRatio", assetRatio);
		document.put("productionDate", productionDate);
		document.put("useDate", useDate);
		document.put("deviceOperationStatus", deviceOperationStatus);
		document.put("stopDate", stopDate);
		document.put("scrapDate", scrapDate);
		document.put("fixedAssetsCode", fixedAssetsCode);
		document.put("capacity", capacity);
		document.put("capacityUnit", capacityUnit);

		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
	}
	//视频监控类-铁塔-02 新增
	@PostMapping("/addDocIrontower")
	public ResultMsg addDocIrontower(
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

			@RequestParam(name = "site_range_upPost") String site_range_upPost,
			@RequestParam(name = "site_range_downPost") String site_range_downPost,
			@RequestParam(name = "site_other_line") String site_other_line,
			@RequestParam(name = "site_other_place") String site_other_place,
			@RequestParam(name = "assetOwnership") String assetOwnership,
			@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
			@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode,
			@RequestParam(name = "maintainBody") String maintainBody,
			@RequestParam(name = "maintainUnitName") String maintainUnitName,
			@RequestParam(name = "maintainUnitCode") String maintainUnitCode,
			@RequestParam(name = "manufacturers") String manufacturers,
			@RequestParam(name = "useUnit") String useUnit,
			@RequestParam(name = "productionDate") String productionDate,
			@RequestParam(name = "useDate") String useDate,
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus,
			@RequestParam(name = "stopDate") String stopDate,
			@RequestParam(name = "scrapDate") String scrapDate,
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode,

			@RequestParam(name = "towerType") String towerType,
			@RequestParam(name = "towerHeight") String towerHeight,
			@RequestParam(name = "longitude") String longitude,
			@RequestParam(name = "latitude") String latitude,
			@RequestParam(name = "altitude") String altitude,
			@RequestParam(name = "middleRepairDate") String middleRepairDate,
			@RequestParam(name = "largeRepairDate") String largeRepairDate,

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
		document.put("publicType", "video_irontower");
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
		document.put("site_range_upPost", site_range_upPost);
		document.put("site_range_downPost", site_range_downPost);
		document.put("site_other_line", site_other_line);
		document.put("site_other_place", site_other_place);
		document.put("assetOwnership", assetOwnership);
		document.put("ownershipUnitName", ownershipUnitName);
		document.put("ownershipUnitCode", ownershipUnitCode);
		document.put("maintainBody", maintainBody);
		document.put("maintainUnitName", maintainUnitName);
		document.put("maintainUnitCode", maintainUnitCode);
		document.put("manufacturers", manufacturers);
		document.put("useUnit", useUnit);
		document.put("productionDate", productionDate);
		document.put("useDate", useDate);
		document.put("deviceOperationStatus", deviceOperationStatus);
		document.put("stopDate", stopDate);
		document.put("scrapDate", scrapDate);
		document.put("fixedAssetsCode", fixedAssetsCode);

		document.put("towerType", towerType);
		document.put("towerHeight", towerHeight);
		document.put("longitude", longitude);
		document.put("latitude", latitude);
		document.put("altitude", altitude);
		document.put("middleRepairDate", middleRepairDate);
		document.put("largeRepairDate", largeRepairDate);

		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putPostPlace(site_station_place, site_range_upPost, site_range_downPost, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
	}
	//视频监控类-摄像机-03 新增
	@PostMapping("/addDocVidicon")
	public ResultMsg addDocVidicon(
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
			@RequestParam(name = "vidiconType") String vidiconType,

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
		document.put("publicType", "video_vidicon");
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
		document.put("vidiconType", vidiconType);

		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putCommonPlace(site_station_place, site_range_place, site_other_place));
		document.put("userId", userId);
		document.put("orgId", orgId);
		Document res = service.addDocument(document, collectionName);
		return ResultMsg.getSuccess("新增成功", res);
	}

	/**
	 * 主页显示
	 *
	 * @param collectionName
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
	 * @param id
	 * @param collectionName
	 * @return
	 */
	@PostMapping("/findById")
	public ResultMsg findDocById(@RequestParam("id") String id, @RequestParam("collectionName") String collectionName) {
		Document res = service.findDocumentById(id, collectionName);
		return ResultMsg.getSuccess("查询完成", res);
	}

	//视频监控类-视频监控类-01 修改
	@PostMapping("/updateDocVideo")
	public ResultMsg updateDoc(
			@RequestParam("id") String id,
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
			@RequestParam(name = "roadCapacity") String roadCapacity,
			@RequestParam(name = "assetRatio") String assetRatio,
			@RequestParam(name = "productionDate") String productionDate,
			@RequestParam(name = "useDate") String useDate,
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus,
			@RequestParam(name = "stopDate") String stopDate,
			@RequestParam(name = "scrapDate") String scrapDate,
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode,
			@RequestParam(name = "capacity") String capacity,
			@RequestParam(name = "capacityUnit") String capacityUnit,

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
		document.put("capacity", capacity);
		document.put("capacityUnit", capacityUnit);

		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}
	//视频监控类-铁塔-02 修改
	@PostMapping("/updateDocIrontower")
	public ResultMsg updateDocIrontower(
			@RequestParam("id") String id,
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

			@RequestParam(name = "site_range_upPost") String site_range_upPost,
			@RequestParam(name = "site_range_downPost") String site_range_downPost,
			@RequestParam(name = "site_other_line") String site_other_line,
			@RequestParam(name = "site_other_place") String site_other_place,
			@RequestParam(name = "assetOwnership") String assetOwnership,
			@RequestParam(name = "ownershipUnitName") String ownershipUnitName,
			@RequestParam(name = "ownershipUnitCode") String ownershipUnitCode,
			@RequestParam(name = "maintainBody") String maintainBody,
			@RequestParam(name = "maintainUnitName") String maintainUnitName,
			@RequestParam(name = "maintainUnitCode") String maintainUnitCode,
			@RequestParam(name = "manufacturers") String manufacturers,
			@RequestParam(name = "useUnit") String useUnit,
			@RequestParam(name = "productionDate") String productionDate,
			@RequestParam(name = "useDate") String useDate,
			@RequestParam(name = "deviceOperationStatus") String deviceOperationStatus,
			@RequestParam(name = "stopDate") String stopDate,
			@RequestParam(name = "scrapDate") String scrapDate,
			@RequestParam(name = "fixedAssetsCode") String fixedAssetsCode,

			@RequestParam(name = "towerType") String towerType,
			@RequestParam(name = "towerHeight") String towerHeight,
			@RequestParam(name = "longitude") String longitude,
			@RequestParam(name = "latitude") String latitude,
			@RequestParam(name = "altitude") String altitude,
			@RequestParam(name = "middleRepairDate") String middleRepairDate,
			@RequestParam(name = "largeRepairDate") String largeRepairDate,

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
		document.put("site_range_upPost", site_range_upPost);
		document.put("site_range_downPost", site_range_downPost);
		document.put("site_other_line", site_other_line);
		document.put("site_other_place", site_other_place);
		document.put("assetOwnership", assetOwnership);
		document.put("ownershipUnitName", ownershipUnitName);
		document.put("ownershipUnitCode", ownershipUnitCode);
		document.put("maintainBody", maintainBody);
		document.put("maintainUnitName", maintainUnitName);
		document.put("maintainUnitCode", maintainUnitCode);
		document.put("manufacturers", manufacturers);
		document.put("useUnit", useUnit);
		document.put("productionDate", productionDate);
		document.put("useDate", useDate);
		document.put("deviceOperationStatus", deviceOperationStatus);
		document.put("stopDate", stopDate);
		document.put("scrapDate", scrapDate);
		document.put("fixedAssetsCode", fixedAssetsCode);

		document.put("towerType", towerType);
		document.put("towerHeight", towerHeight);
		document.put("longitude", longitude);
		document.put("latitude", latitude);
		document.put("altitude", altitude);
		document.put("middleRepairDate", middleRepairDate);
		document.put("largeRepairDate", largeRepairDate);
		document.put("remark", remark);
		document.put(DeviceRecordPlaceUtil.uniplace, DeviceRecordPlaceUtil.putPostPlace(site_station_place, site_range_upPost, site_range_downPost, site_other_place));

		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
	}

	//视频监控类-摄像机-03 修改
	@PostMapping("/updateDocVidicon")
	public ResultMsg updateDocVidicon(
			@RequestParam("id") String id,
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
			@RequestParam(name = "vidiconType") String vidiconType,

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
		document.put("vidiconType", vidiconType);

		Document res = service.updateDocument(document, collectionName);
		return ResultMsg.getSuccess("修改成功", res);
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



}