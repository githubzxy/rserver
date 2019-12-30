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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TatOtherDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TatSpcDto;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTatOther;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTatSpc;
@Service("tatService")
public class TatServiceImpl implements TatService{
		@Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
		@Autowired
		private NamedParameterJdbcTemplate template;
		@Resource(name = "overhaulRecordService")
		private OverhaulRecordService overhaulService;
		@Resource(name = "transService")
		private TransService transService;

	@Override
	public ResultMsg importTatSpc(String publicType, MultipartFile file, String userId, String orgId) {
    	ReadExcelTatSpc readExcel =new ReadExcelTatSpc();
    	List<TatSpcDto> fdpList = readExcel.getExcelInfo(file);
    	//查询一遍数据库中的所有数据用于和表格中的数据比较
    	List<Document> listAll = transService.getAllDocumentByPublicType("deviceRecord",publicType);
    	//存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
    	List<String> reatList = new ArrayList<String>();
    	//表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
    	listAll.stream().forEach(s->{
		//判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
    		if(StringUtils.isNotBlank((String) s.get("source"))==false){
    			reatList.add((String)s.get("docId"));
    		}
    	});
    	//验证表格中的必填数据是否有空
    	Map<String, Object> checkNullMap = checkNullRowTatSpc(fdpList);
    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTatSpc(fdpList);
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
		    		document.put("manufacturers", s.getManufacturers()); 
		    		document.put("deviceType", s.getDeviceType()); 
		    		document.put("useUnit", s.getUseUnit()); 
		    		document.put("productionDate", s.getProductionDate()); 
		    		document.put("useDate", s.getUseDate()); 
		    		document.put("deviceOperationStatus", s.getDeviceOperationStatus()); 
		    		document.put("stopDate", s.getStopDate()); 
		    		document.put("scrapDate", s.getScrapDate()); 
		    		document.put("userLineConfigCapacity", s.getUserLineConfigCapacity()); 
		    		document.put("actualUserNumber", s.getActualUserNumber()); 
		    		document.put("trunkConfigCapacity", s.getTrunkConfigCapacity()); 
		    		document.put("trunkActualCapacity", s.getTrunkActualCapacity()); 
		    		document.put("remoteUserModule_totalCapacity", s.getRemoteUserModule_totalCapacity()); 
		    		document.put("remoteUserModule_actuallCapacity", s.getRemoteUserModule_actuallCapacity()); 
		    		document.put("holdWithSignal", s.getHoldWithSignal()); 
		    		
		    		document.put("fixedAssetsCode", s.getFixedAssetsCode()); 
		    		document.put("remark", s.getRemark());
		    		
		    		document.put("userId", userId);
		    		document.put("orgId", orgId);
		    		transService.addDocument(document,collectionName);
	    	 });
    		transService.removeDocument(reatList, "deviceRecord");
	    	 return ResultMsg.getSuccess("导入成功");
    	}
	}
  /**
	 * 验证导入数据中的必填项是否有未填列
	 * @return 
	 */
	public Map<String,Object> checkNullRowTatSpc(List<TatSpcDto> fdpList) {
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
 	 
 	 boolean userLineConfigCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUserLineConfigCapacity())==false);
 	 boolean actualUserNumberNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getActualUserNumber())==false);
 	 boolean trunkConfigCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTrunkConfigCapacity())==false);
 	 boolean trunkActualCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTrunkActualCapacity())==false);
 	 boolean remoteUserModule_totalCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getRemoteUserModule_totalCapacity())==false);
 	 boolean remoteUserModule_actuallCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getRemoteUserModule_actuallCapacity())==false);
 	 boolean holdWithSignalNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getHoldWithSignal())==false);
 	 
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
 		
 	}else if(userLineConfigCapacityNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'用户线配置容量'列是否有空白格"));
 		return map;
 	}else if(actualUserNumberNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'实装用户数'列是否有空白格"));
 		return map;
 	}else if(trunkConfigCapacityNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'中继线配置容量'列是否有空白格"));
 		return map;
 	}else if(trunkActualCapacityNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'中继线实际使用容量'列是否有空白格"));
 		return map;
 	}else if(remoteUserModule_totalCapacityNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'远端用户模块(总容量)'列是否有空白格"));
 		return map;
 	}else if(remoteUserModule_actuallCapacityNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'远端用户模块(实际使用容量)'列是否有空白格"));
 		return map;
 	}else if(holdWithSignalNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'信令方式'列是否有空白格"));
 		return map;
 	 }else {
 		map.put("isNull", true);
 		return map; 
 	 }
	}
	//验证车间是否为系统中数据，工区是否在车间下
	public Map<String,Object> checkWorkshopAndWorkAreaTatSpc(List<TatSpcDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
      //获取所有车间
		List<String> cjList = transService.getCadreAndShop();
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
	public ResultMsg importTatOther(String publicType, MultipartFile file, String userId, String orgId){
	ReadExcelTatOther readExcel =new ReadExcelTatOther();
	List<TatOtherDto> fdpList = readExcel.getExcelInfo(file);
	//查询一遍数据库中的所有数据用于和表格中的数据比较
	List<Document> listAll = transService.getAllDocumentByPublicType("deviceRecord",publicType);
	//存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
	List<String> reatList = new ArrayList<String>();
	//表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
	listAll.stream().forEach(s->{
		//
		if(StringUtils.isNotBlank((String) s.get("source"))==false){
			reatList.add((String)s.get("docId"));
		}
	});
	//验证表格中的必填数据是否有空
	Map<String, Object> checkNullMap = checkDtoNullRowTatOther(fdpList);
	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTatOther(fdpList);
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
	    		document.put("capacityCallsNumber", s.getCapacityCallsNumber()); 
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
	    		transService.addDocument(document,collectionName);
    	 });
    	 //删除数据库中来源为上一次导入的数据
    	 transService.removeDocument(reatList, "deviceRecord");
    	 return ResultMsg.getSuccess("导入成功");
	}
	}
  public Map<String,Object> checkDtoNullRowTatOther(List<TatOtherDto> fdpList) {
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
	public Map<String,Object> checkWorkshopAndWorkAreaTatOther(List<TatOtherDto> fdpList) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取所有车间
		List<String> cjList = transService.getCadreAndShop();
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

}

	
