package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessCableDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessHandDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessIrontowerDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessMobileDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessRadioDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.WirelessWirelessDto;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelWirelessCable;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelWirelessHand;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelWirelessIrontower;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelWirelessMobile;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelWirelessRadio;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelWirelessWireless;

@Service("wirelessService")
public class WirelessServiceImpl implements WirelessService {
    @Resource(name = "transService")
    TransService service;
    @Resource(name = "overhaulRecordService")
    OverhaulRecordService overhaulService;


    @Override
    public ResultMsg importWirelessWireless(String publicType, MultipartFile file, String userId, String orgId) {
        ReadExcelWirelessWireless readExcel =new ReadExcelWirelessWireless();
        List<WirelessWirelessDto> fdpList = readExcel.getExcelInfo(file);
        //查询一遍数据库中的所有数据用于和表格中的数据比较
        List<Document> listAll = service.getAllDocumentByPublicType("deviceRecord",publicType);
        //存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
        List<String> reatList = new ArrayList<String>();
        //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
        listAll.stream().forEach(s->{
            //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
            if(StringUtils.isNotBlank((String) s.get("source"))==false){
                reatList.add((String)s.get("docId"));
            }
        });
//    	 删除数据库中来源为上一次导入的数据
        System.out.println("===================");
        reatList.forEach(System.out::println);
        //验证表格中的必填数据是否有空
        Map<String, Object> checkNullMap = checkNullRowWirelessWireless(fdpList);
        Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaWirelessWireless(fdpList);
        if((boolean) checkNullMap.get("isNull")==false){
            return (ResultMsg) checkNullMap.get("ResultMsg");
        }
        //验证填写的车间工区是否正确
        else if((boolean) checkCjMap.get("isExist")==false) {
            return (ResultMsg) checkCjMap.get("ResultMsg");
        }else {
            fdpList.stream().forEach(s->{
                String collectionName ="deviceRecord";
                Document document = new Document();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creatDateStr = "";
                try {
                    creatDateStr = sdf.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                document.put("creatDateStr", creatDateStr);
                document.put("publicType", publicType);

                document.put("workshop", s.getWorkshop());
                document.put("workArea", s.getWorkArea());
                document.put("combinationClass", s.getCombinationClass());
                document.put("deviceClass", s.getDeviceClass());
                document.put("deviceCode", s.getDeviceCode());
                document.put("deviceName", s.getDeviceName());
                document.put("site_station_line", s.getSite_station_line());
                document.put("site_station_name", s.getSite_station_name());
                document.put("site_station_place", s.getSite_station_place());
                document.put("site_range_line", s.getSite_range_line());
                document.put("site_range_post", s.getSite_range_post());
                document.put("site_range_place", s.getSite_range_place());
                document.put("site_other_line", s.getSite_other_line());
                document.put("site_other_place", s.getSite_other_place());
                document.put("site_machineRoomCode", s.getSite_machineRoomCode());
                document.put("assetOwnership", s.getAssetOwnership());
                document.put("ownershipUnitName", s.getOwnershipUnitName());
                document.put("ownershipUnitCode", s.getOwnershipUnitCode());
                document.put("maintainBody", s.getMaintainBody());
                document.put("maintainUnitName", s.getMaintainUnitName());
                document.put("maintainUnitCode", s.getMaintainUnitCode());

                document.put("capacity", s.getCapacity());
                document.put("roadCapacity", s.getRoadCapacity());
                document.put("assetRatio", s.getAssetRatio());
                document.put("useCapacity", s.getUseCapacity());
                document.put("capacityUnit", s.getCapacityUnit());

                document.put("manufacturers", s.getManufacturers());
                document.put("deviceType", s.getDeviceType());
                document.put("useUnit", s.getUseUnit());
                document.put("productionDate", s.getProductionDate());
                document.put("useDate", s.getUseDate());
                document.put("deviceOperationStatus", s.getDeviceOperationStatus());
                document.put("stopDate", s.getStopDate());
                document.put("scrapDate", s.getScrapDate());
                document.put("fixedAssetsCode", s.getFixedAssetsCode());
                document.put("remark", s.getRemark());

                document.put("userId", userId);
                document.put("orgId", orgId);
                service.addDocument(document,collectionName);
            });
            service.removeDocument(reatList, "deviceRecord");
            return ResultMsg.getSuccess("导入成功");
        }
    }
    /**
     * 验证导入数据中的必填项是否有未填列
     * @return
     */
    public Map<String,Object> checkNullRowWirelessWireless(List<WirelessWirelessDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //检验每一必填列数据中是否有未填，默认为true
        boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
        boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
        boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
        boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
        boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
        boolean site_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_machineRoomCode())==false);
        boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
        boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
        boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
        boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
        boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
        boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
        boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
        boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
        boolean useDateNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseDate())==false);
        boolean deviceOperationStatusNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceOperationStatus())==false);

        if(workshopNull==true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
            return map;
        }else if(workAreaNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
            return map;
        }else if(combinationClassNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
            return map;
        }else if(deviceClassNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
            return map;
        }else if(deviceNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
            return map;
        }else if(site_machineRoomCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'机房、接入点编码'列是否有空白格"));
            return map;
        }else if(assetOwnershipNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
            return map;
        }else if(ownershipUnitNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
            return map;
        }else if(ownershipUnitCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
            return map;
        }else if(maintainBodyNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
            return map;
        }else if(maintainUnitNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
            return map;
        }else if(maintainUnitCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
            return map;
        }else if(manufacturers==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
            return map;
        }else if(deviceTypeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备型号'列是否有空白格"));
            return map;
        }else if(useDateNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
            return map;
        }else if(deviceOperationStatusNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
            return map;
        }else {
            map.put("isNull", true);
            return map;
        }
    }
    //验证车间是否为系统中数据，工区是否在车间下
    public Map<String,Object> checkWorkshopAndWorkAreaWirelessWireless(List<WirelessWirelessDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取所有车间
        List<String> cjList = service.getCadreAndShop();
        //workshopCheck为true时，导入的车间全在系统中
        boolean workshopCheck =fdpList.stream().allMatch(f->(cjList.contains( f.getWorkshop())==true));
        //判断导入的工区是否在导入的车间下
        boolean workAreaCheck =fdpList.stream().allMatch(f->(overhaulService.getWorAreasByName(f.getWorkshop()).contains( f.getWorkArea())==true));
        if(workshopCheck==false){
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
            return map;
        }
        else if(workAreaCheck==false){
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
            return map;
        }
        else {
            map.put("isExist", true);
            return map;
        }
    }



    @Override
    public ResultMsg importWirelessCable(String publicType, MultipartFile file, String userId, String orgId) {
        ReadExcelWirelessCable readExcel =new ReadExcelWirelessCable();
        List<WirelessCableDto> fdpList = readExcel.getExcelInfo(file);
        //查询一遍数据库中的所有数据用于和表格中的数据比较
        List<Document> listAll = service.getAllDocumentByPublicType("deviceRecord",publicType);
        //存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
        List<String> reatList = new ArrayList<String>();
        //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
        listAll.stream().forEach(s->{
            //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
            if(StringUtils.isNotBlank((String) s.get("source"))==false){
                reatList.add((String)s.get("docId"));
            }
        });
//    	 删除数据库中来源为上一次导入的数据
        System.out.println("===================");
        reatList.forEach(System.out::println);
        //验证表格中的必填数据是否有空
        Map<String, Object> checkNullMap = checkNullRowWirelessCable(fdpList);
        Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaWirelessCable(fdpList);
        if((boolean) checkNullMap.get("isNull")==false){
            return (ResultMsg) checkNullMap.get("ResultMsg");
        }
        //验证填写的车间工区是否正确
        else if((boolean) checkCjMap.get("isExist")==false) {
            return (ResultMsg) checkCjMap.get("ResultMsg");
        }else {
            fdpList.stream().forEach(s->{
                String collectionName ="deviceRecord";
                Document document = new Document();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creatDateStr = "";
                try {
                    creatDateStr = sdf.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                document.put("creatDateStr", creatDateStr);
                document.put("publicType", publicType);

                document.put("workshop", s.getWorkshop());
                document.put("workArea", s.getWorkArea());
                document.put("combinationClass", s.getCombinationClass());
                document.put("deviceClass", s.getDeviceClass());
                document.put("deviceCode", s.getDeviceCode());
                document.put("deviceName", s.getDeviceName());
                document.put("site_start_station_line", s.getSite_start_station_line());
                document.put("site_start_station_name", s.getSite_start_station_name());
                document.put("site_start_station_place", s.getSite_start_station_place());
                document.put("site_start_range_line", s.getSite_start_range_line());
                document.put("site_start_range_post", s.getSite_start_range_post());
                document.put("site_start_range_place", s.getSite_start_range_place());
                document.put("site_start_other_line", s.getSite_start_other_line());
                document.put("site_start_other_place", s.getSite_start_other_place());
                document.put("site_start_machineRoomCode", s.getSite_start_machineRoomCode());

                document.put("site_end_station_line", s.getSite_end_station_line());
                document.put("site_end_station_name", s.getSite_end_station_name());
                document.put("site_end_station_place", s.getSite_end_station_place());
                document.put("site_end_range_line", s.getSite_end_range_line());
                document.put("site_end_range_post", s.getSite_end_range_post());
                document.put("site_end_range_place", s.getSite_end_range_place());
                document.put("site_end_other_line", s.getSite_end_other_line());
                document.put("site_end_other_place", s.getSite_end_other_place());
                document.put("site_end_machineRoomCode", s.getSite_end_machineRoomCode());
                document.put("cableLength", s.getCableLength());

                document.put("assetOwnership", s.getAssetOwnership());
                document.put("ownershipUnitName", s.getOwnershipUnitName());
                document.put("ownershipUnitCode", s.getOwnershipUnitCode());
                document.put("maintainBody", s.getMaintainBody());
                document.put("maintainUnitName", s.getMaintainUnitName());
                document.put("maintainUnitCode", s.getMaintainUnitCode());

                document.put("manufacturers", s.getManufacturers());
                document.put("deviceType", s.getDeviceType());
                document.put("useUnit", s.getUseUnit());
                document.put("productionDate", s.getProductionDate());
                document.put("useDate", s.getUseDate());

                document.put("middleRepairDate", s.getMiddleRepairDate());
                document.put("largeRepairDate", s.getLargeRepairDate());

                document.put("deviceOperationStatus", s.getDeviceOperationStatus());
                document.put("stopDate", s.getStopDate());
                document.put("scrapDate", s.getScrapDate());
                document.put("fixedAssetsCode", s.getFixedAssetsCode());
                document.put("remark", s.getRemark());

                document.put("userId", userId);
                document.put("orgId", orgId);
                service.addDocument(document,collectionName);
            });
            service.removeDocument(reatList, "deviceRecord");
            return ResultMsg.getSuccess("导入成功");
        }
    }
    /**
     * 验证导入数据中的必填项是否有未填列
     * @return
     */
    public Map<String,Object> checkNullRowWirelessCable(List<WirelessCableDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //检验每一必填列数据中是否有未填，默认为true
        boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
        boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
        boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
        boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
        boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
        boolean site_start_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_start_machineRoomCode())==false);
        boolean site_end_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_end_machineRoomCode())==false);
        boolean cableLengthNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCableLength())==false);
        boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
        boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
        boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
        boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
        boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
        boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
        boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
        boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
        boolean useDateNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseDate())==false);
        boolean deviceOperationStatusNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceOperationStatus())==false);

        if(workshopNull==true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
            return map;
        }else if(workAreaNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
            return map;
        }else if(combinationClassNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
            return map;
        }else if(deviceClassNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
            return map;
        }else if(deviceNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
            return map;
        }else if(site_start_machineRoomCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'起始机房、接入点编码'列是否有空白格"));
            return map;
        }else if(site_end_machineRoomCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'终止机房、接入点编码'列是否有空白格"));
            return map;
        }else if(cableLengthNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'长度'列是否有空白格"));
            return map;
        }else if(assetOwnershipNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
            return map;
        }else if(ownershipUnitNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
            return map;
        }else if(ownershipUnitCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
            return map;
        }else if(maintainBodyNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
            return map;
        }else if(maintainUnitNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
            return map;
        }else if(maintainUnitCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
            return map;
        }else if(manufacturers==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
            return map;
        }else if(deviceTypeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备型号'列是否有空白格"));
            return map;
        }else if(useDateNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
            return map;
        }else if(deviceOperationStatusNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
            return map;
        }else {
            map.put("isNull", true);
            return map;
        }
    }
    //验证车间是否为系统中数据，工区是否在车间下
    public Map<String,Object> checkWorkshopAndWorkAreaWirelessCable(List<WirelessCableDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取所有车间
        List<String> cjList = service.getCadreAndShop();
        //workshopCheck为true时，导入的车间全在系统中
        boolean workshopCheck =fdpList.stream().allMatch(f->(cjList.contains( f.getWorkshop())==true));
        //判断导入的工区是否在导入的车间下
        boolean workAreaCheck =fdpList.stream().allMatch(f->(overhaulService.getWorAreasByName(f.getWorkshop()).contains( f.getWorkArea())==true));
        if(workshopCheck==false){
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
            return map;
        }
        else if(workAreaCheck==false){
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
            return map;
        }
        else {
            map.put("isExist", true);
            return map;
        }
    }



    @Override
    public ResultMsg importWirelessRadio(String publicType, MultipartFile file, String userId, String orgId) {
        ReadExcelWirelessRadio readExcel =new ReadExcelWirelessRadio();
        List<WirelessRadioDto> fdpList = readExcel.getExcelInfo(file);
        //查询一遍数据库中的所有数据用于和表格中的数据比较
        List<Document> listAll = service.getAllDocumentByPublicType("deviceRecord",publicType);
        //存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
        List<String> reatList = new ArrayList<String>();
        //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
        listAll.stream().forEach(s->{
            //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
            if(StringUtils.isNotBlank((String) s.get("source"))==false){
                reatList.add((String)s.get("docId"));
            }
        });
//    	 删除数据库中来源为上一次导入的数据
        System.out.println("===================");
        reatList.forEach(System.out::println);
        //验证表格中的必填数据是否有空
        Map<String, Object> checkNullMap = checkNullRowWirelessRadio(fdpList);
        Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaWirelessRadio(fdpList);
        if((boolean) checkNullMap.get("isNull")==false){
            return (ResultMsg) checkNullMap.get("ResultMsg");
        }
        //验证填写的车间工区是否正确
        else if((boolean) checkCjMap.get("isExist")==false) {
            return (ResultMsg) checkCjMap.get("ResultMsg");
        }else {
            fdpList.stream().forEach(s->{
                String collectionName ="deviceRecord";
                Document document = new Document();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creatDateStr = "";
                try {
                    creatDateStr = sdf.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                document.put("creatDateStr", creatDateStr);
                document.put("publicType", publicType);

                document.put("workshop", s.getWorkshop());
                document.put("workArea", s.getWorkArea());
                document.put("combinationClass", s.getCombinationClass());
                document.put("deviceClass", s.getDeviceClass());
                document.put("deviceCode", s.getDeviceCode());
                document.put("deviceName", s.getDeviceName());
                document.put("site_station_line", s.getSite_station_line());
                document.put("site_station_name", s.getSite_station_name());
                document.put("site_station_place", s.getSite_station_place());
                document.put("site_range_line", s.getSite_range_line());
                document.put("site_range_post", s.getSite_range_post());
                document.put("site_range_place", s.getSite_range_place());
                document.put("site_other_line", s.getSite_other_line());
                document.put("site_other_place", s.getSite_other_place());
                document.put("site_machineRoomCode", s.getSite_machineRoomCode());
                document.put("assetOwnership", s.getAssetOwnership());
                document.put("ownershipUnitName", s.getOwnershipUnitName());
                document.put("ownershipUnitCode", s.getOwnershipUnitCode());
                document.put("maintainBody", s.getMaintainBody());
                document.put("maintainUnitName", s.getMaintainUnitName());
                document.put("maintainUnitCode", s.getMaintainUnitCode());

                document.put("phaseAlternatingLine", s.getPhaseAlternatingLine());
                document.put("quadNumber", s.getQuadNumber());
                document.put("grandTrine", s.getGrandTrine());
                document.put("trainNumberCheck", s.getTrainNumberCheck());
                document.put("dispatchCommand", s.getDispatchCommand());

                document.put("manufacturers", s.getManufacturers());
                document.put("deviceType", s.getDeviceType());
                document.put("useUnit", s.getUseUnit());
                document.put("productionDate", s.getProductionDate());
                document.put("useDate", s.getUseDate());
                document.put("deviceOperationStatus", s.getDeviceOperationStatus());
                document.put("stopDate", s.getStopDate());
                document.put("scrapDate", s.getScrapDate());
                document.put("fixedAssetsCode", s.getFixedAssetsCode());
                document.put("remark", s.getRemark());

                document.put("userId", userId);
                document.put("orgId", orgId);
                service.addDocument(document,collectionName);
            });
            service.removeDocument(reatList, "deviceRecord");
            return ResultMsg.getSuccess("导入成功");
        }
    }
    /**
     * 验证导入数据中的必填项是否有未填列
     * @return
     */
    public Map<String,Object> checkNullRowWirelessRadio(List<WirelessRadioDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //检验每一必填列数据中是否有未填，默认为true
        boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
        boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
        boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
        boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
        boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
        boolean site_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_machineRoomCode())==false);
        boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
        boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
        boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
        boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
        boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
        boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);

        boolean phaseAlternatingLineNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getPhaseAlternatingLine())==false);
        boolean quadNumberNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getQuadNumber())==false);
        boolean grandTrineNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getGrandTrine())==false);
        boolean trainNumberCheckNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTrainNumberCheck())==false);
        boolean dispatchCommandNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDispatchCommand())==false);

        boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
        boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
        boolean useDateNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseDate())==false);
        boolean deviceOperationStatusNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceOperationStatus())==false);

        if(workshopNull==true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
            return map;
        }else if(workAreaNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
            return map;
        }else if(combinationClassNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
            return map;
        }else if(deviceClassNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
            return map;
        }else if(deviceNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
            return map;
        }else if(site_machineRoomCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'机房、接入点编码'列是否有空白格"));
            return map;
        }else if(assetOwnershipNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
            return map;
        }else if(ownershipUnitNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
            return map;
        }else if(ownershipUnitCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
            return map;
        }else if(maintainBodyNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
            return map;
        }else if(maintainUnitNameNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
            return map;
        }else if(maintainUnitCodeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
            return map;
        }else if(phaseAlternatingLineNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'制式'列是否有空白格"));
            return map;
        }else if(quadNumberNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'四频组序号'列是否有空白格"));
            return map;
        }else if(grandTrineNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'大三角'列是否有空白格"));
            return map;
        }else if(trainNumberCheckNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车次号校验'列是否有空白格"));
            return map;
        }else if(dispatchCommandNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'调度命令传送'列是否有空白格"));
            return map;
        }else if(manufacturers==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
            return map;
        }else if(deviceTypeNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备型号'列是否有空白格"));
            return map;
        }else if(useDateNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
            return map;
        }else if(deviceOperationStatusNull==true){
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
            return map;
        }else {
            map.put("isNull", true);
            return map;
        }
    }
    //验证车间是否为系统中数据，工区是否在车间下
    public Map<String,Object> checkWorkshopAndWorkAreaWirelessRadio(List<WirelessRadioDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取所有车间
        List<String> cjList = service.getCadreAndShop();
        //workshopCheck为true时，导入的车间全在系统中
        boolean workshopCheck =fdpList.stream().allMatch(f->(cjList.contains( f.getWorkshop())==true));
        //判断导入的工区是否在导入的车间下
        boolean workAreaCheck =fdpList.stream().allMatch(f->(overhaulService.getWorAreasByName(f.getWorkshop()).contains( f.getWorkArea())==true));
        if(workshopCheck==false){
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
            return map;
        }
        else if(workAreaCheck==false){
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
            return map;
        }
        else {
            map.put("isExist", true);
            return map;
        }
    }


    @Override
    public ResultMsg importWirelessIrontower(String publicType, MultipartFile file, String userId, String orgId) {
        ReadExcelWirelessIrontower readExcel = new ReadExcelWirelessIrontower();
        List<WirelessIrontowerDto> fdpList = readExcel.getExcelInfo(file);
        //查询一遍数据库中的所有数据用于和表格中的数据比较
        List<Document> listAll = service.getAllDocumentByPublicType("deviceRecord", publicType);
        //存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
        List<String> reatList = new ArrayList<String>();
        //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
        listAll.stream().forEach(s -> {
            //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
            if (StringUtils.isNotBlank((String) s.get("source")) == false) {
                reatList.add((String) s.get("docId"));
            }
        });
//    	 删除数据库中来源为上一次导入的数据
        System.out.println("===================");
        reatList.forEach(System.out::println);
        //验证表格中的必填数据是否有空
        Map<String, Object> checkNullMap = checkNullRowIrontower(fdpList);
        Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaIrontower(fdpList);
        if ((boolean) checkNullMap.get("isNull") == false) {
            return (ResultMsg) checkNullMap.get("ResultMsg");
        }
        //验证填写的车间工区是否正确
        else if ((boolean) checkCjMap.get("isExist") == false) {
            return (ResultMsg) checkCjMap.get("ResultMsg");
        } else {
            fdpList.stream().forEach(s -> {
                String collectionName = "deviceRecord";
                Document document = new Document();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creatDateStr = "";
                try {
                    creatDateStr = sdf.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                document.put("creatDateStr", creatDateStr);
                document.put("publicType", publicType);

                document.put("workshop", s.getWorkshop());
                document.put("workArea", s.getWorkArea());
                document.put("combinationClass", s.getCombinationClass());
                document.put("deviceClass", s.getDeviceClass());
                document.put("deviceCode", s.getDeviceCode());
                document.put("deviceName", s.getDeviceName());
                document.put("site_station_line", s.getSite_station_line());
                document.put("site_station_name", s.getSite_station_name());
                document.put("site_station_place", s.getSite_station_place());
                document.put("site_range_line", s.getSite_range_line());
                document.put("site_range_upPost", s.getSite_range_upPost());
                document.put("site_range_downPost", s.getSite_range_downPost());
                document.put("site_other_line", s.getSite_other_line());
                document.put("site_other_place", s.getSite_other_place());
                document.put("assetOwnership", s.getAssetOwnership());
                document.put("ownershipUnitName", s.getOwnershipUnitName());
                document.put("ownershipUnitCode", s.getOwnershipUnitCode());
                document.put("maintainBody", s.getMaintainBody());
                document.put("maintainUnitName", s.getMaintainUnitName());
                document.put("maintainUnitCode", s.getMaintainUnitCode());
                document.put("towerType", s.getTowerType());
                document.put("towerHeight", s.getTowerHeight());
                document.put("longitude", s.getLongitude());
                document.put("latitude", s.getLatitude());
                document.put("altitude", s.getAltitude());
                document.put("manufacturers", s.getManufacturers());
                document.put("useUnit", s.getUseUnit());
                document.put("productionDate", s.getProductionDate());
                document.put("useDate", s.getUseDate());
                document.put("middleRepairDate", s.getMiddleRepairDate());
                document.put("largeRepairDate", s.getLargeRepairDate());
                document.put("deviceOperationStatus", s.getDeviceOperationStatus());
                document.put("stopDate", s.getStopDate());
                document.put("scrapDate", s.getScrapDate());
                document.put("fixedAssetsCode", s.getFixedAssetsCode());
                document.put("remark", s.getRemark());

                document.put("userId", userId);
                document.put("orgId", orgId);
                service.addDocument(document, collectionName);
            });
            service.removeDocument(reatList, "deviceRecord");
            return ResultMsg.getSuccess("导入成功");
        }
    }
    /**
     * 验证导入数据中的必填项是否有未填列
     *
     * @return
     */
    public Map<String, Object> checkNullRowIrontower(List<WirelessIrontowerDto> fdpList) {


        Map<String, Object> map = new HashMap<String, Object>();
        //检验每一必填列数据中是否有未填，默认为true
        boolean workshopNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getWorkshop()) == false);
        boolean workAreaNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getWorkArea()) == false);
        boolean combinationClassNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getCombinationClass()) == false);
        boolean deviceClassNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceClass()) == false);
        boolean deviceNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceName()) == false);
        boolean assetOwnershipNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getAssetOwnership()) == false);
        boolean ownershipUnitNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getOwnershipUnitName()) == false);
        boolean ownershipUnitCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getOwnershipUnitCode()) == false);
        boolean maintainBodyNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainBody()) == false);
        boolean maintainUnitNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainUnitName()) == false);
        boolean maintainUnitCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainUnitCode()) == false);

        boolean towerTypeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getTowerType()) == false);
        boolean towerHeightNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getTowerHeight()) == false);
        boolean longitudeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getLongitude()) == false);
        boolean latitudeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getLatitude()) == false);
        boolean altitudeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getAltitude()) == false);

        boolean manufacturers = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getManufacturers()) == false);
        boolean useDateNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getUseDate()) == false);
        boolean deviceOperationStatusNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceOperationStatus()) == false);

        if (workshopNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
            return map;
        } else if (workAreaNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
            return map;
        } else if (combinationClassNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
            return map;
        } else if (deviceClassNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
            return map;
        } else if (deviceNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
            return map;
        } else if (assetOwnershipNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
            return map;
        } else if (ownershipUnitNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
            return map;
        } else if (ownershipUnitCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
            return map;
        } else if (maintainBodyNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
            return map;
        } else if (maintainUnitNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
            return map;
        } else if (maintainUnitCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
            return map;
        } else if (towerTypeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'铁塔类型'列是否有空白格"));
            return map;
        } else if (towerHeightNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'塔高'列是否有空白格"));
            return map;
        } else if (longitudeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'经度'列是否有空白格"));
            return map;
        } else if (latitudeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'纬度'列是否有空白格"));
            return map;
        } else if (altitudeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'海拔高度'列是否有空白格"));
            return map;
        } else if (manufacturers == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
            return map;
        } else if (useDateNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
            return map;
        } else if (deviceOperationStatusNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
            return map;
        } else {
            map.put("isNull", true);
            return map;
        }
    }
    //验证车间是否为系统中数据，工区是否在车间下
    public Map<String, Object> checkWorkshopAndWorkAreaIrontower(List<WirelessIrontowerDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取所有车间
        List<String> cjList = service.getCadreAndShop();
        //workshopCheck为true时，导入的车间全在系统中
        boolean workshopCheck = fdpList.stream().allMatch(f -> (cjList.contains(f.getWorkshop()) == true));
        //判断导入的工区是否在导入的车间下
        boolean workAreaCheck = fdpList.stream().allMatch(f -> (overhaulService.getWorAreasByName(f.getWorkshop()).contains(f.getWorkArea()) == true));
        if (workshopCheck == false) {
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
            return map;
        } else if (workAreaCheck == false) {
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
            return map;
        } else {
            map.put("isExist", true);
            return map;
        }
    }


    @Override
    public ResultMsg importWirelessMobile(String publicType, MultipartFile file, String userId, String orgId) {
        ReadExcelWirelessMobile readExcel = new ReadExcelWirelessMobile();
        List<WirelessMobileDto> fdpList = readExcel.getExcelInfo(file);
        //查询一遍数据库中的所有数据用于和表格中的数据比较
        List<Document> listAll = service.getAllDocumentByPublicType("deviceRecord", publicType);
        //存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
        List<String> reatList = new ArrayList<String>();
        //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
        listAll.stream().forEach(s -> {
            //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
            if (StringUtils.isNotBlank((String) s.get("source")) == false) {
                reatList.add((String) s.get("docId"));
            }
        });
//    	 删除数据库中来源为上一次导入的数据
        System.out.println("===================");
        reatList.forEach(System.out::println);
        //验证表格中的必填数据是否有空
        Map<String, Object> checkNullMap = checkNullRowMobile(fdpList);
        Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaMobile(fdpList);
        if ((boolean) checkNullMap.get("isNull") == false) {
            return (ResultMsg) checkNullMap.get("ResultMsg");
        }
        //验证填写的车间工区是否正确
        else if ((boolean) checkCjMap.get("isExist") == false) {
            return (ResultMsg) checkCjMap.get("ResultMsg");
        } else {
            fdpList.stream().forEach(s -> {
                String collectionName = "deviceRecord";
                Document document = new Document();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creatDateStr = "";
                try {
                    creatDateStr = sdf.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                document.put("creatDateStr", creatDateStr);
                document.put("publicType", publicType);

                document.put("workshop", s.getWorkshop());
                document.put("workArea", s.getWorkArea());
                document.put("combinationClass", s.getCombinationClass());
                document.put("deviceClass", s.getDeviceClass());
                document.put("deviceCode", s.getDeviceCode());
                document.put("deviceName", s.getDeviceName());
                document.put("site_line", s.getSite_line());
                document.put("site_vehicleType", s.getSite_vehicleType());
                document.put("site_vehicleCode", s.getSite_vehicleType());
                document.put("site_vehicleNumber", s.getSite_vehicleNumber());
                document.put("assetOwnership", s.getAssetOwnership());
                document.put("ownershipUnitName", s.getOwnershipUnitName());
                document.put("ownershipUnitCode", s.getOwnershipUnitCode());
                document.put("maintainBody", s.getMaintainBody());
                document.put("maintainUnitName", s.getMaintainUnitName());
                document.put("maintainUnitCode", s.getMaintainUnitCode());
                document.put("capacity", s.getCapacity());
                document.put("manufacturers", s.getManufacturers());
                document.put("deviceType", s.getDeviceType());
                document.put("useUnit", s.getUseUnit());
                document.put("productionDate", s.getProductionDate());
                document.put("useDate", s.getUseDate());
                document.put("deviceOperationStatus", s.getDeviceOperationStatus());
                document.put("stopDate", s.getStopDate());
                document.put("scrapDate", s.getScrapDate());
                document.put("fixedAssetsCode", s.getFixedAssetsCode());
                document.put("remark", s.getRemark());

                document.put("userId", userId);
                document.put("orgId", orgId);
                service.addDocument(document, collectionName);
            });
            service.removeDocument(reatList, "deviceRecord");
            return ResultMsg.getSuccess("导入成功");
        }
    }
    /**
     * 验证导入数据中的必填项是否有未填列
     *
     * @return
     */
    public Map<String, Object> checkNullRowMobile(List<WirelessMobileDto> fdpList) {


        Map<String, Object> map = new HashMap<String, Object>();
        //检验每一必填列数据中是否有未填，默认为true
        boolean workshopNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getWorkshop()) == false);
        boolean workAreaNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getWorkArea()) == false);
        boolean combinationClassNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getCombinationClass()) == false);
        boolean deviceClassNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceClass()) == false);
        boolean deviceNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceName()) == false);
        boolean assetOwnershipNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getAssetOwnership()) == false);
        boolean ownershipUnitNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getOwnershipUnitName()) == false);
        boolean ownershipUnitCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getOwnershipUnitCode()) == false);
        boolean maintainBodyNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainBody()) == false);
        boolean maintainUnitNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainUnitName()) == false);
        boolean maintainUnitCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainUnitCode()) == false);

        boolean site_vehicleTypeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getSite_vehicleType()) == false);
        boolean site_vehicleCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getSite_vehicleCode()) == false);
        boolean site_vehicleNumberNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getSite_vehicleNumber()) == false);
        boolean deviceTypeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceType()) == false);

        boolean manufacturers = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getManufacturers()) == false);
        boolean useDateNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getUseDate()) == false);
        boolean deviceOperationStatusNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceOperationStatus()) == false);

        if (workshopNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
            return map;
        } else if (workAreaNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
            return map;
        } else if (combinationClassNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
            return map;
        } else if (deviceClassNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
            return map;
        } else if (deviceNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
            return map;
        } else if (site_vehicleTypeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'安装位置-车型'列是否有空白格"));
            return map;
        } else if (site_vehicleCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'安装位置-车型代码\\车辆型号代码'列是否有空白格"));
            return map;
        } else if (site_vehicleNumberNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'安装位置-机车号\\车辆号'列是否有空白格"));
            return map;
        } else if (assetOwnershipNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
            return map;
        } else if (ownershipUnitNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
            return map;
        } else if (ownershipUnitCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
            return map;
        } else if (maintainBodyNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
            return map;
        } else if (maintainUnitNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
            return map;
        } else if (maintainUnitCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
            return map;
        } else if (manufacturers == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
            return map;
        } else if (deviceTypeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备型号'列是否有空白格"));
            return map;
        } else if (useDateNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
            return map;
        } else if (deviceOperationStatusNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
            return map;
        } else {
            map.put("isNull", true);
            return map;
        }
    }
    //验证车间是否为系统中数据，工区是否在车间下
    public Map<String, Object> checkWorkshopAndWorkAreaMobile(List<WirelessMobileDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取所有车间
        List<String> cjList = service.getCadreAndShop();
        //workshopCheck为true时，导入的车间全在系统中
        boolean workshopCheck = fdpList.stream().allMatch(f -> (cjList.contains(f.getWorkshop()) == true));
        //判断导入的工区是否在导入的车间下
        boolean workAreaCheck = fdpList.stream().allMatch(f -> (overhaulService.getWorAreasByName(f.getWorkshop()).contains(f.getWorkArea()) == true));
        if (workshopCheck == false) {
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
            return map;
        } else if (workAreaCheck == false) {
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
            return map;
        } else {
            map.put("isExist", true);
            return map;
        }
    }


    @Override
    public ResultMsg importWirelessHand(String publicType, MultipartFile file, String userId, String orgId) {
        ReadExcelWirelessHand readExcel = new ReadExcelWirelessHand();
        List<WirelessHandDto> fdpList = readExcel.getExcelInfo(file);
        //查询一遍数据库中的所有数据用于和表格中的数据比较
        List<Document> listAll = service.getAllDocumentByPublicType("deviceRecord", publicType);
        //存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
        List<String> reatList = new ArrayList<String>();
        //表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
        listAll.stream().forEach(s -> {
            //判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
            if (StringUtils.isNotBlank((String) s.get("source")) == false) {
                reatList.add((String) s.get("docId"));
            }
        });
//    	 删除数据库中来源为上一次导入的数据
        System.out.println("===================");
        reatList.forEach(System.out::println);
        //验证表格中的必填数据是否有空
        Map<String, Object> checkNullMap = checkNullRowHand(fdpList);
        Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaHand(fdpList);
        if ((boolean) checkNullMap.get("isNull") == false) {
            return (ResultMsg) checkNullMap.get("ResultMsg");
        }
        //验证填写的车间工区是否正确
        else if ((boolean) checkCjMap.get("isExist") == false) {
            return (ResultMsg) checkCjMap.get("ResultMsg");
        } else {
            fdpList.stream().forEach(s -> {
                String collectionName = "deviceRecord";
                Document document = new Document();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creatDateStr = "";
                try {
                    creatDateStr = sdf.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                document.put("creatDateStr", creatDateStr);
                document.put("publicType", publicType);

                document.put("workshop", s.getWorkshop());
                document.put("workArea", s.getWorkArea());
                document.put("combinationClass", s.getCombinationClass());
                document.put("deviceClass", s.getDeviceClass());
                document.put("deviceCode", s.getDeviceCode());
                document.put("deviceName", s.getDeviceName());
                document.put("site_line", s.getSite_line());
                document.put("site_department", s.getSite_department());
                document.put("assetOwnership", s.getAssetOwnership());
                document.put("ownershipUnitName", s.getOwnershipUnitName());
                document.put("ownershipUnitCode", s.getOwnershipUnitCode());
                document.put("maintainBody", s.getMaintainBody());
                document.put("maintainUnitName", s.getMaintainUnitName());
                document.put("maintainUnitCode", s.getMaintainUnitCode());
                document.put("manufacturers", s.getManufacturers());
                document.put("deviceType", s.getDeviceType());
                document.put("useUnit", s.getUseUnit());
                document.put("productionDate", s.getProductionDate());
                document.put("useDate", s.getUseDate());
                document.put("deviceOperationStatus", s.getDeviceOperationStatus());
                document.put("stopDate", s.getStopDate());
                document.put("scrapDate", s.getScrapDate());
                document.put("fixedAssetsCode", s.getFixedAssetsCode());
                document.put("remark", s.getRemark());

                document.put("userId", userId);
                document.put("orgId", orgId);
                service.addDocument(document, collectionName);
            });
            service.removeDocument(reatList, "deviceRecord");
            return ResultMsg.getSuccess("导入成功");
        }
    }
    /**
     * 验证导入数据中的必填项是否有未填列
     *
     * @return
     */
    public Map<String, Object> checkNullRowHand(List<WirelessHandDto> fdpList) {


        Map<String, Object> map = new HashMap<String, Object>();
        //检验每一必填列数据中是否有未填，默认为true
        boolean workshopNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getWorkshop()) == false);
        boolean workAreaNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getWorkArea()) == false);
        boolean combinationClassNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getCombinationClass()) == false);
        boolean deviceClassNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceClass()) == false);
        boolean deviceNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceName()) == false);
        boolean assetOwnershipNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getAssetOwnership()) == false);
        boolean ownershipUnitNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getOwnershipUnitName()) == false);
        boolean ownershipUnitCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getOwnershipUnitCode()) == false);
        boolean maintainBodyNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainBody()) == false);
        boolean maintainUnitNameNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainUnitName()) == false);
        boolean maintainUnitCodeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getMaintainUnitCode()) == false);

        boolean deviceTypeNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceType()) == false);
        boolean site_departmentNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getSite_department()) == false);

        boolean manufacturers = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getManufacturers()) == false);
        boolean useDateNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getUseDate()) == false);
        boolean deviceOperationStatusNull = fdpList.stream().anyMatch(f -> StringUtils.isNotBlank(f.getDeviceOperationStatus()) == false);

        if (workshopNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列是否有空白格"));
            return map;
        } else if (workAreaNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列是否有空白格"));
            return map;
        } else if (combinationClassNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'组合分类'列是否有空白格"));
            return map;
        } else if (deviceClassNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备分类'列是否有空白格"));
            return map;
        } else if (deviceNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
            return map;
        }  else if (site_departmentNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'安装位置-专业部门'列是否有空白格"));
            return map;
        }  else if (assetOwnershipNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产归属'列是否有空白格"));
            return map;
        } else if (ownershipUnitNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位名称'列是否有空白格"));
            return map;
        } else if (ownershipUnitCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'权属单位编码'列是否有空白格"));
            return map;
        } else if (maintainBodyNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护主体'列是否有空白格"));
            return map;
        } else if (maintainUnitNameNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位名称'列是否有空白格"));
            return map;
        } else if (maintainUnitCodeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'维护单位编码'列是否有空白格"));
            return map;
        } else if (manufacturers == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备厂家'列是否有空白格"));
            return map;
        } else if (deviceTypeNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备型号'列是否有空白格"));
            return map;
        } else if (useDateNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
            return map;
        } else if (deviceOperationStatusNull == true) {
            map.put("isNull", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
            return map;
        } else {
            map.put("isNull", true);
            return map;
        }
    }
    //验证车间是否为系统中数据，工区是否在车间下
    public Map<String, Object> checkWorkshopAndWorkAreaHand(List<WirelessHandDto> fdpList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取所有车间
        List<String> cjList = service.getCadreAndShop();
        //workshopCheck为true时，导入的车间全在系统中
        boolean workshopCheck = fdpList.stream().allMatch(f -> (cjList.contains(f.getWorkshop()) == true));
        //判断导入的工区是否在导入的车间下
        boolean workAreaCheck = fdpList.stream().allMatch(f -> (overhaulService.getWorAreasByName(f.getWorkshop()).contains(f.getWorkArea()) == true));
        if (workshopCheck == false) {
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'车间'列数据是否按系统填写"));
            return map;
        } else if (workAreaCheck == false) {
            map.put("isExist", false);
            map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'班组'列数据是否全部属于对应车间"));
            return map;
        } else {
            map.put("isExist", true);
            return map;
        }
    }
}

