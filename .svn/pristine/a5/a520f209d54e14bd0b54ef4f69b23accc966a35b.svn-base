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

@RestController
@RequestMapping("/dataAction")
public class DataAction {
    @Resource(name = "transService")
    private TransService service;

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
     * @param collectionName 存储表名
     * @return 保存后的数据对象
     */
    @PostMapping("/addDocRouter")
    public ResultMsg addDocRouter(
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
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "slotType") String slotType,
            @RequestParam(name = "applicationLevel") String applicationLevel,
            @RequestParam(name = "slotTotalNumber") String slotTotalNumber,
            @RequestParam(name = "slotUseNumber") String slotUseNumber,
            @RequestParam(name = "_622MportConfigNumber") String _622MportConfigNumber,
            @RequestParam(name = "_622MportUseNumber") String _622MportUseNumber,
            @RequestParam(name = "_155MportConfigNumber") String _155MportConfigNumber,
            @RequestParam(name = "_155MportUseNumber") String _155MportUseNumber,
            @RequestParam(name = "_2MportConfigNumber") String _2MportConfigNumber,
            @RequestParam(name = "_2MportUseNumber") String _2MportUseNumber,
            @RequestParam(name = "GEportConfigNumber") String GEportConfigNumber,
            @RequestParam(name = "GEportUseNumber") String GEportUseNumber,
            @RequestParam(name = "FEportConfigNumber") String FEportConfigNumber,
            @RequestParam(name = "FEportUseNumber") String FEportUseNumber,
            @RequestParam(name = "otherPortConfigNumber") String otherPortConfigNumber,
            @RequestParam(name = "otherPortUseNumber") String otherPortUseNumber,
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
        document.put("publicType", "data_router");
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
        document.put("slotType", slotType);
        document.put("applicationLevel", applicationLevel);
        document.put("slotTotalNumber", slotTotalNumber);
        document.put("slotUseNumber", slotUseNumber);
        document.put("_622MportConfigNumber", _622MportConfigNumber);
        document.put("_622MportUseNumber", _622MportUseNumber);
        document.put("_155MportConfigNumber", _155MportConfigNumber);
        document.put("_155MportUseNumber", _155MportUseNumber);
        document.put("_2MportConfigNumber", _2MportConfigNumber);
        document.put("_2MportUseNumber", _2MportUseNumber);
        document.put("GEportConfigNumber", GEportConfigNumber);
        document.put("GEportUseNumber", GEportUseNumber);
        document.put("FEportConfigNumber", FEportConfigNumber);
        document.put("FEportUseNumber", FEportUseNumber);
        document.put("otherPortConfigNumber", otherPortConfigNumber);
        document.put("otherPortUseNumber", otherPortUseNumber);
        // Map<String, Object> org = userService.getOrgbyUserId(userId);
        // document.put("createOrg", org.get("ORG_ID_"));
        // document.put("createOrgName", org.get("ORG_NAME_"));

        document.put("userId", userId);
        document.put("orgId", orgId);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);

    }

    /**
     * 新增数据
     *
     * @param collectionName 存储表名
     * @return 保存后的数据对象
     */
    @PostMapping("/addDocChanger")
    public ResultMsg addDocChanger(
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
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "applicationLevel") String applicationLevel,
            @RequestParam(name = "slotTotalNumber") String slotTotalNumber,
            @RequestParam(name = "slotUseNumber") String slotUseNumber,
            @RequestParam(name = "GEportConfigNumber") String GEportConfigNumber,
            @RequestParam(name = "GEportUseNumber") String GEportUseNumber,
            @RequestParam(name = "FEportConfigNumber") String FEportConfigNumber,
            @RequestParam(name = "FEportUseNumber") String FEportUseNumber,
            @RequestParam(name = "otherPortConfigNumber") String otherPortConfigNumber,
            @RequestParam(name = "otherPortUseNumber") String otherPortUseNumber,
            @RequestParam(name = "capacity") String capacity,
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
        document.put("publicType", "data_changer");
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
        document.put("applicationLevel", applicationLevel);
        document.put("slotTotalNumber", slotTotalNumber);
        document.put("slotUseNumber", slotUseNumber);
        document.put("GEportConfigNumber", GEportConfigNumber);
        document.put("GEportUseNumber", GEportUseNumber);
        document.put("FEportConfigNumber", FEportConfigNumber);
        document.put("FEportUseNumber", FEportUseNumber);
        document.put("otherPortConfigNumber", otherPortConfigNumber);
        document.put("otherPortUseNumber", otherPortUseNumber);
        document.put("capacity", capacity);
        // Map<String, Object> org = userService.getOrgbyUserId(userId);
        // document.put("createOrg", org.get("ORG_ID_"));
        // document.put("createOrgName", org.get("ORG_NAME_"));
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }

    /**
     * 新增数据
     *
     * @param collectionName 存储表名
     * @return 保存后的数据对象
     */
    @PostMapping("/addDocOther")
    public ResultMsg addDocOther(
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
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "accessType") String accessType,
            @RequestParam(name = "capacityUnit") String capacityUnit,
            @RequestParam(name = "capacity") String capacity,
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
        document.put("publicType", "data_other");
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
        document.put("accessType", accessType);
        document.put("capacityUnit", capacityUnit);
        document.put("capacity", capacity);
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
     * @param start
     * @param limit
     * @return
     */
    @PostMapping("/findAll")
    public GridDto<Document> findAll(
            @RequestParam String collectionName,
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
                service.findAllDocumentCount(collectionName, workshop, workArea, deviceName, publicType, deviceClass, deviceType, manufacturers));
        result.setRows(
                service.findAllDocument(collectionName, workshop, workArea, deviceName, publicType, deviceClass, deviceType, manufacturers, start, limit));
        return result;
    }

    /**
     * 删除数据
     *
     * @param id             数据id
     * @param collectionName 表名
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


    @PostMapping("/updateDocRouter")
    public ResultMsg updateDocRouter(
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
            @RequestParam(name = "remark") String remark,
            @RequestParam(name = "collectionName") String collectionName,
            @RequestParam(name = "slotType") String slotType,
            @RequestParam(name = "applicationLevel") String applicationLevel,
            @RequestParam(name = "slotTotalNumber") String slotTotalNumber,
            @RequestParam(name = "slotUseNumber") String slotUseNumber,
            @RequestParam(name = "_622MportConfigNumber") String _622MportConfigNumber,
            @RequestParam(name = "_622MportUseNumber") String _622MportUseNumber,
            @RequestParam(name = "_155MportConfigNumber") String _155MportConfigNumber,
            @RequestParam(name = "_155MportUseNumber") String _155MportUseNumber,
            @RequestParam(name = "_2MportConfigNumber") String _2MportConfigNumber,
            @RequestParam(name = "_2MportUseNumber") String _2MportUseNumber,
            @RequestParam(name = "GEportConfigNumber") String GEportConfigNumber,
            @RequestParam(name = "GEportUseNumber") String GEportUseNumber,
            @RequestParam(name = "FEportConfigNumber") String FEportConfigNumber,
            @RequestParam(name = "FEportUseNumber") String FEportUseNumber,
            @RequestParam(name = "otherPortConfigNumber") String otherPortConfigNumber,
            @RequestParam(name = "otherPortUseNumber") String otherPortUseNumber,
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
        document.put("slotType", slotType);
        document.put("applicationLevel", applicationLevel);
        document.put("slotTotalNumber", slotTotalNumber);
        document.put("slotUseNumber", slotUseNumber);
        document.put("_622MportConfigNumber", _622MportConfigNumber);
        document.put("_622MportUseNumber", _622MportUseNumber);
        document.put("_155MportConfigNumber", _155MportConfigNumber);
        document.put("_155MportUseNumber", _155MportUseNumber);
        document.put("_2MportConfigNumber", _2MportConfigNumber);
        document.put("_2MportUseNumber", _2MportUseNumber);
        document.put("GEportConfigNumber", GEportConfigNumber);
        document.put("GEportUseNumber", GEportUseNumber);
        document.put("FEportConfigNumber", FEportConfigNumber);
        document.put("FEportUseNumber", FEportUseNumber);
        document.put("otherPortConfigNumber", otherPortConfigNumber);
        document.put("otherPortUseNumber", otherPortUseNumber);

        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }


    @PostMapping("/updateDocChanger")
    public ResultMsg updateDocChanger(
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
            @RequestParam(name = "remark") String remark,
            @RequestParam(name = "collectionName") String collectionName,
            @RequestParam(name = "applicationLevel") String applicationLevel,
            @RequestParam(name = "slotTotalNumber") String slotTotalNumber,
            @RequestParam(name = "slotUseNumber") String slotUseNumber,
            @RequestParam(name = "GEportConfigNumber") String GEportConfigNumber,
            @RequestParam(name = "GEportUseNumber") String GEportUseNumber,
            @RequestParam(name = "FEportConfigNumber") String FEportConfigNumber,
            @RequestParam(name = "FEportUseNumber") String FEportUseNumber,
            @RequestParam(name = "otherPortConfigNumber") String otherPortConfigNumber,
            @RequestParam(name = "otherPortUseNumber") String otherPortUseNumber,
            @RequestParam(name = "capacity") String capacity,
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
        document.put("applicationLevel", applicationLevel);
        document.put("slotTotalNumber", slotTotalNumber);
        document.put("slotUseNumber", slotUseNumber);
        document.put("GEportConfigNumber", GEportConfigNumber);
        document.put("GEportUseNumber", GEportUseNumber);
        document.put("FEportConfigNumber", FEportConfigNumber);
        document.put("FEportUseNumber", FEportUseNumber);
        document.put("otherPortConfigNumber", otherPortConfigNumber);
        document.put("otherPortUseNumber", otherPortUseNumber);
        document.put("capacity", capacity);

        Document res = service.updateDocument(document, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }


    @PostMapping("/updateDocOther")
    public ResultMsg updateDocOther(
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
            @RequestParam(name = "remark") String remark,
            @RequestParam(name = "collectionName") String collectionName,
            @RequestParam(name = "accessType") String accessType,
            @RequestParam(name = "capacityUnit") String capacityUnit,
            @RequestParam(name = "capacity") String capacity,
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
        document.put("accessType", accessType);
        document.put("capacityUnit", capacityUnit);
        document.put("capacity", capacity);

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
    public List<String> getchildOrgId(String orgId) {
        List<Map<String, Object>> list = service.getChildIdByOrgId(orgId);
        List<String> orgIdlist = new ArrayList<String>();
        list.stream().forEach(s -> {
            orgIdlist.add((String) s.get("orgId"));
        });
        return orgIdlist;
    }

}