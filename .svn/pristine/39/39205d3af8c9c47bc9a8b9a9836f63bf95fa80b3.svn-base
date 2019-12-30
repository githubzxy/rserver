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
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineElectricityDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineHoleDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineLightDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineLineAndLightDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineLineDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LineOtherDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.LinePipelineDto;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLineElectricity;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLineHole;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLineLight;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLineLine;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLineLineAndLight;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLineOther;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelLinePipeline;
@Service("lineService")
public class LineServiceImpl implements LineService{
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
	public ResultMsg importLineLight(String publicType, MultipartFile file, String userId, String orgId) {
    	ReadExcelLineLight readExcel =new ReadExcelLineLight();
    	List<LineLightDto> fdpList = readExcel.getExcelInfo(file);
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
    	Map<String, Object> checkNullMap = checkNullRowLineLight(fdpList);
    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLineLight(fdpList);
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
		    		document.put("laidLength", s.getLaidLength());
		    		document.put("laidMethod", s.getLaidMethod());
		    		
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
		    		
		    		document.put("assetOwnership", s.getAssetOwnership());
		    		document.put("ownershipUnitName", s.getOwnershipUnitName());
		    		document.put("ownershipUnitCode", s.getOwnershipUnitCode()); 
		    		document.put("maintainBody", s.getMaintainBody()); 
		    		document.put("maintainUnitName", s.getMaintainUnitName()); 
		    		document.put("maintainUnitCode", s.getMaintainUnitCode()); 
		    		document.put("manufacturers", s.getManufacturers()); 
		    		document.put("deviceType", s.getDeviceType()); 
		    		document.put("useUnit", s.getUseUnit()); 
		    		document.put("totalCapacity", s.getTotalCapacity()); 
		    		document.put("coreNumber", s.getCoreNumber()); 
		    		document.put("useCoreNumber", s.getUseCoreNumber()); 
		    		document.put("assetRatio", s.getAssetRatio()); 
		    		
		    		
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
	public Map<String,Object> checkNullRowLineLight(List<LineLightDto> fdpList) {
	 Map<String, Object> map = new HashMap<String, Object>();
   //检验每一必填列数据中是否有未填，默认为true
 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
 	 boolean laidLengthNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLaidLength())==false);
 	 boolean laidMethodNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLaidMethod())==false);
 	 
 	 boolean site_start_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_start_machineRoomCode())==false);
 	 boolean site_end_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_end_machineRoomCode())==false);
 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
 	 boolean totalCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTotalCapacity())==false);
 	 boolean coreNumberNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCoreNumber())==false);
 	 boolean useCoreNumberNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseCoreNumber())==false);
 	 boolean assetRatioNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetRatio())==false);
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
 	 }else if(laidLengthNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'敷设长度'列是否有空白格"));
 		 return map;
 	 }else if(laidMethodNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'敷设方式'列是否有空白格"));
 		 return map;
 	 }else if(site_start_machineRoomCodeNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查起点'机房、接入点编码'列是否有空白格"));
 		return map;
 	 }else if(site_end_machineRoomCodeNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查止点'机房、接入点编码'列是否有空白格"));
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
 	 }else if(useUnitNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
 		 return map;
 	 }else if(totalCapacityNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'总容量'列是否有空白格"));
 		 return map;
 	 }else if(coreNumberNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'路产芯数'列是否有空白格"));
 		 return map;
 	 }else if(useCoreNumberNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'在用芯数'列是否有空白格"));
 		 return map;
 	 }else if(assetRatioNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产比例'列是否有空白格"));
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
	public Map<String,Object> checkWorkshopAndWorkAreaLineLight(List<LineLightDto> fdpList) {
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
	public ResultMsg importLineElectricity(String publicType, MultipartFile file, String userId, String orgId){
	ReadExcelLineElectricity readExcel =new ReadExcelLineElectricity();
	List<LineElectricityDto> fdpList = readExcel.getExcelInfo(file);
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
	Map<String, Object> checkNullMap = checkDtoNullRowLineElectricity(fdpList);
	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLineElectricity(fdpList);
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
	    		document.put("laidLength", s.getLaidLength());
	    		document.put("laidMethod", s.getLaidMethod());
	    		
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
	    		
	    		document.put("assetOwnership", s.getAssetOwnership());
	    		document.put("ownershipUnitName", s.getOwnershipUnitName());
	    		document.put("ownershipUnitCode", s.getOwnershipUnitCode()); 
	    		document.put("maintainBody", s.getMaintainBody()); 
	    		document.put("maintainUnitName", s.getMaintainUnitName()); 
	    		document.put("maintainUnitCode", s.getMaintainUnitCode()); 
	    		document.put("manufacturers", s.getManufacturers()); 
	    		document.put("deviceType", s.getDeviceType()); 
	    		document.put("useUnit", s.getUseUnit()); 
	    		document.put("totalCapacity", s.getTotalCapacity()); 
	    		document.put("useCoreNumber", s.getUseCoreNumber()); 
	    		document.put("assetRatio", s.getAssetRatio()); 
	    		
	    		
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
	    		transService.addDocument(document,collectionName);
    	 });
    	 //删除数据库中来源为上一次导入的数据
    	 transService.removeDocument(reatList, "deviceRecord");
    	 return ResultMsg.getSuccess("导入成功");
	}
	}
  public Map<String,Object> checkDtoNullRowLineElectricity(List<LineElectricityDto> fdpList) {
	Map<String, Object> map = new HashMap<String, Object>();
    //检验每一必填列数据中是否有未填，默认为true
	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
 	 boolean laidLengthNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLaidLength())==false);
 	 boolean laidMethodNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLaidMethod())==false);
 	 
 	 boolean site_start_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_start_machineRoomCode())==false);
 	 boolean site_end_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_end_machineRoomCode())==false);
 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
 	 boolean totalCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTotalCapacity())==false);
 	 boolean useCoreNumberNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseCoreNumber())==false);
 	 boolean assetRatioNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetRatio())==false);
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
 	 }else if(laidLengthNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'敷设长度'列是否有空白格"));
 		 return map;
 	 }else if(laidMethodNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'敷设方式'列是否有空白格"));
 		 return map;
 	 }else if(site_start_machineRoomCodeNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查起点'机房、接入点编码'列是否有空白格"));
 		return map;
 	 }else if(site_end_machineRoomCodeNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查止点'机房、接入点编码'列是否有空白格"));
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
 	 }else if(useUnitNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
 		 return map;
 	 }else if(totalCapacityNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'总容量'列是否有空白格"));
 		 return map;
 	 }else if(useCoreNumberNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'在用芯数'列是否有空白格"));
 		 return map;
 	 }else if(assetRatioNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产比例'列是否有空白格"));
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
	public Map<String,Object> checkWorkshopAndWorkAreaLineElectricity(List<LineElectricityDto> fdpList) {
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
	public ResultMsg importLineLine(String publicType, MultipartFile file, String userId, String orgId) {
		ReadExcelLineLine readExcel =new ReadExcelLineLine();
    	List<LineLineDto> fdpList = readExcel.getExcelInfo(file);
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
    	Map<String, Object> checkNullMap = checkNullRowLineLine(fdpList);
    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLineLine(fdpList);
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
		    		document.put("laidLength", s.getLaidLength());
		    		document.put("laidMethod", s.getLaidMethod());
		    		
		    		document.put("site_line", s.getSite_line());
		    		document.put("site_start_station", s.getSite_start_station()); 
		    		document.put("site_start_place", s.getSite_start_place());
		    		document.put("site_start_machineRoomCode", s.getSite_start_machineRoomCode());
		    		
		    		document.put("site_end_station", s.getSite_end_station());
		    		document.put("site_end_place", s.getSite_end_place());
		    		document.put("site_end_machineRoomCode", s.getSite_end_machineRoomCode());
		    		
		    		document.put("assetOwnership", s.getAssetOwnership());
		    		document.put("ownershipUnitName", s.getOwnershipUnitName());
		    		document.put("ownershipUnitCode", s.getOwnershipUnitCode()); 
		    		document.put("maintainBody", s.getMaintainBody()); 
		    		document.put("maintainUnitName", s.getMaintainUnitName()); 
		    		document.put("maintainUnitCode", s.getMaintainUnitCode()); 
		    		document.put("manufacturers", s.getManufacturers()); 
		    		document.put("deviceType", s.getDeviceType()); 
		    		document.put("useUnit", s.getUseUnit()); 
		    		document.put("lineType", s.getLineType()); 
		    		document.put("useLogarithm", s.getUseLogarithm()); 
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
    		transService.removeDocument(reatList, "deviceRecord");
	    	 return ResultMsg.getSuccess("导入成功");
    	}
	}
	 /**
		 * 验证导入数据中的必填项是否有未填列
		 * @return 
		 */
		public Map<String,Object> checkNullRowLineLine(List<LineLineDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
	   //检验每一必填列数据中是否有未填，默认为true
	 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
	 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
	 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
	 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
	 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
	 	 boolean laidLengthNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLaidLength())==false);
	 	 boolean laidMethodNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLaidMethod())==false);
	 	 
	 	 boolean site_start_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_start_machineRoomCode())==false);
	 	 boolean site_end_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_end_machineRoomCode())==false);
	 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
	 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
	 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
	 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
	 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
	 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
	 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
	 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
	 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
	 	 boolean lineTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLineType())==false);
	 	 boolean useLogarithmNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseLogarithm())==false);
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
	 	 }else if(laidLengthNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'敷设长度'列是否有空白格"));
	 		 return map;
	 	 }else if(laidMethodNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'敷设方式'列是否有空白格"));
	 		 return map;
	 	 }else if(site_start_machineRoomCodeNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查起点'机房、接入点编码'列是否有空白格"));
	 		return map;
	 	 }else if(site_end_machineRoomCodeNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查止点'机房、接入点编码'列是否有空白格"));
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
	 	 }else if(useUnitNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
	 		 return map;
	 	 }else if(lineTypeNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'线路类型'列是否有空白格"));
	 		 return map;
	 	 }else if(useLogarithmNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'在用对数'列是否有空白格"));
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
		public Map<String,Object> checkWorkshopAndWorkAreaLineLine(List<LineLineDto> fdpList) {
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
	public ResultMsg importLineLineAndLight(String publicType, MultipartFile file, String userId, String orgId) {
		ReadExcelLineLineAndLight readExcel =new ReadExcelLineLineAndLight();
    	List<LineLineAndLightDto> fdpList = readExcel.getExcelInfo(file);
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
    	Map<String, Object> checkNullMap = checkNullRowLineLineAndLight(fdpList);
    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLineLineAndLight(fdpList);
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
		    		
		    		document.put("totalCapacity", s.getTotalCapacity()); 
		    		document.put("roadCapacity", s.getRoadCapacity()); 
		    		document.put("assetRatio", s.getAssetRatio()); 
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
    		transService.removeDocument(reatList, "deviceRecord");
	    	 return ResultMsg.getSuccess("导入成功");
    	}
	}
	 /**
	 * 验证导入数据中的必填项是否有未填列
	 * @return 
	 */
	public Map<String,Object> checkNullRowLineLineAndLight(List<LineLineAndLightDto> fdpList) {
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
 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
 	 
 	 boolean totalCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTotalCapacity())==false);
 	 boolean roadCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getRoadCapacity())==false);
 	 boolean assetRatioNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetRatio())==false);
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
 	 }else if(useUnitNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
 		 return map;
 	 }else if(totalCapacityNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'总容量'列是否有空白格"));
 		 return map;
 	 }else if(roadCapacityNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'路产容量'列是否有空白格"));
 		 return map;
 	 }else if(assetRatioNull==true){
 		 map.put("isNull", false);
 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产比例'列是否有空白格"));
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
	public Map<String,Object> checkWorkshopAndWorkAreaLineLineAndLight(List<LineLineAndLightDto> fdpList) {
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
	public ResultMsg importLinePipeline(String publicType, MultipartFile file, String userId, String orgId) {
		ReadExcelLinePipeline readExcel =new ReadExcelLinePipeline();
    	List<LinePipelineDto> fdpList = readExcel.getExcelInfo(file);
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
    	Map<String, Object> checkNullMap = checkNullRowLinePipeline(fdpList);
    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLinePipeline(fdpList);
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
		    		
		    		document.put("assetOwnership", s.getAssetOwnership());
		    		document.put("ownershipUnitName", s.getOwnershipUnitName());
		    		document.put("ownershipUnitCode", s.getOwnershipUnitCode()); 
		    		document.put("maintainBody", s.getMaintainBody()); 
		    		document.put("maintainUnitName", s.getMaintainUnitName()); 
		    		document.put("maintainUnitCode", s.getMaintainUnitCode()); 
		    		document.put("manufacturers", s.getManufacturers()); 
		    		document.put("deviceType", s.getDeviceType()); 
		    		document.put("useUnit", s.getUseUnit()); 
		    		
		    		document.put("totalCapacity", s.getTotalCapacity()); 
		    		document.put("roadCapacity", s.getRoadCapacity()); 
		    		document.put("assetRatio", s.getAssetRatio()); 
		    		document.put("length", s.getLength()); 
		    		
		    		
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
    		transService.removeDocument(reatList, "deviceRecord");
	    	 return ResultMsg.getSuccess("导入成功");
    	}
	}
	 /**
		 * 验证导入数据中的必填项是否有未填列
		 * @return 
		 */
		public Map<String,Object> checkNullRowLinePipeline(List<LinePipelineDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
	   //检验每一必填列数据中是否有未填，默认为true
	 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
	 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
	 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
	 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
	 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
	 	 
	 	 boolean site_start_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_start_machineRoomCode())==false);
	 	 boolean site_end_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_end_machineRoomCode())==false);
	 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
	 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
	 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
	 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
	 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
	 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
	 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
	 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
	 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
	 	 boolean lengthNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLength())==false);
	 	 
	 	 boolean totalCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTotalCapacity())==false);
	 	 boolean roadCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getRoadCapacity())==false);
	 	 boolean assetRatioNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetRatio())==false);
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
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查起点'机房、接入点编码'列是否有空白格"));
	 		 return map;
	 	 }else if(site_end_machineRoomCodeNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查止点'机房、接入点编码'列是否有空白格"));
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
	 	 }else if(useUnitNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
	 		 return map;
	 	 }else if(lengthNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'长度'列是否有空白格"));
	 		 return map;
	 	 }else if(totalCapacityNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'总容量'列是否有空白格"));
	 		 return map;
	 	 }else if(roadCapacityNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'路产容量'列是否有空白格"));
	 		 return map;
	 	 }else if(assetRatioNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产比例'列是否有空白格"));
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
		public Map<String,Object> checkWorkshopAndWorkAreaLinePipeline(List<LinePipelineDto> fdpList) {
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
		public ResultMsg importLineHole(String publicType, MultipartFile file, String userId, String orgId) {
			ReadExcelLineHole readExcel =new ReadExcelLineHole();
	    	List<LineHoleDto> fdpList = readExcel.getExcelInfo(file);
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
	    	Map<String, Object> checkNullMap = checkNullRowLineHole(fdpList);
	    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLineHole(fdpList);
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
			    		document.put("deviceName", s.getDeviceName());
			    		
			    		
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
			    		document.put("site", s.getSite()); 
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
		public Map<String,Object> checkNullRowLineHole(List<LineHoleDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
	   //检验每一必填列数据中是否有未填，默认为true
	 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
	 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
	 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
	 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
	 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
	 	 
	 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
	 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
	 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
	 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
	 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
	 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
	 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
	 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
	 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
	 	 
	 	 boolean useDateNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseDate())==false);
	 	 boolean deviceOperationStatusNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceOperationStatus())==false);
	 	 boolean siteNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite())==false);
	 	 
	 	 
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
	 	 }else if(useUnitNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
	 		 return map;
	 	 }else if(useDateNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用日期'列是否有空白格"));
	 		return map;
	 	 }else if(deviceOperationStatusNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备运行状态'列是否有空白格"));
	 		return map;
	 	 }else if(siteNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'安装位置'列是否有空白格"));
	 		 return map;
	 	 }else {
	 		map.put("isNull", true);
	 		return map; 
	 	 }
		}
		//验证车间是否为系统中数据，工区是否在车间下
		public Map<String,Object> checkWorkshopAndWorkAreaLineHole(List<LineHoleDto> fdpList) {
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
		public ResultMsg importLineOther(String publicType, MultipartFile file, String userId, String orgId) {
			ReadExcelLineOther readExcel =new ReadExcelLineOther();
	    	List<LineOtherDto> fdpList = readExcel.getExcelInfo(file);
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
	    	Map<String, Object> checkNullMap = checkNullRowLineOther(fdpList);
	    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaLineOther(fdpList);
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
			    		
			    		document.put("assetOwnership", s.getAssetOwnership());
			    		document.put("ownershipUnitName", s.getOwnershipUnitName());
			    		document.put("ownershipUnitCode", s.getOwnershipUnitCode()); 
			    		document.put("maintainBody", s.getMaintainBody()); 
			    		document.put("maintainUnitName", s.getMaintainUnitName()); 
			    		document.put("maintainUnitCode", s.getMaintainUnitCode()); 
			    		document.put("manufacturers", s.getManufacturers()); 
			    		document.put("deviceType", s.getDeviceType()); 
			    		document.put("useUnit", s.getUseUnit()); 
			    		
			    		document.put("totalCapacity", s.getTotalCapacity()); 
			    		document.put("roadCapacity", s.getRoadCapacity()); 
			    		document.put("assetRatio", s.getAssetRatio()); 
			    		document.put("length", s.getLength()); 
			    		
			    		
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
	    		transService.removeDocument(reatList, "deviceRecord");
		    	 return ResultMsg.getSuccess("导入成功");
	    	}
		}
		 /**
		 * 验证导入数据中的必填项是否有未填列
		 * @return 
		 */
		public Map<String,Object> checkNullRowLineOther(List<LineOtherDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
	   //检验每一必填列数据中是否有未填，默认为true
	 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
	 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
	 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
	 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
	 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
	 	 
	 	 boolean site_start_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_start_machineRoomCode())==false);
	 	 boolean site_end_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_end_machineRoomCode())==false);
	 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
	 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
	 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
	 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
	 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
	 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
	 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
	 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
	 	 boolean useUnitNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getUseUnit())==false);
	 	 boolean lengthNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getLength())==false);
	 	 
	 	 boolean totalCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getTotalCapacity())==false);
	 	 boolean roadCapacityNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getRoadCapacity())==false);
	 	 boolean assetRatioNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetRatio())==false);
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
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查起点'机房、接入点编码'列是否有空白格"));
	 		 return map;
	 	 }else if(site_end_machineRoomCodeNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查止点'机房、接入点编码'列是否有空白格"));
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
	 	 }else if(useUnitNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'使用单位'列是否有空白格"));
	 		 return map;
	 	 }else if(lengthNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'长度'列是否有空白格"));
	 		 return map;
	 	 }else if(totalCapacityNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'总容量'列是否有空白格"));
	 		 return map;
	 	 }else if(roadCapacityNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'路产容量'列是否有空白格"));
	 		 return map;
	 	 }else if(assetRatioNull==true){
	 		 map.put("isNull", false);
	 		 map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'资产比例'列是否有空白格"));
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
		public Map<String,Object> checkWorkshopAndWorkAreaLineOther(List<LineOtherDto> fdpList) {
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

	
