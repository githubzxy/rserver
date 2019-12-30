package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TransDwdmDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TransNumberTransDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TransOtherDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TransOtnDto;
import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.TransSdhDto;
import com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord.OverhaulRecordService;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTransDwdm;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTransNumberTrans;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTransOther;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTransOtn;
import com.enovell.yunwei.km_micor_service.util.communicationResumeManage.ReadExcelTransSdh;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("transService")
public class TransServiceImpl implements TransService{
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
	@Override
	public Document addDocument(Document doc, String collectionName) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            doc.append("status",1);
	            md.getCollection(collectionName).insertOne(doc);
	        }
	        return doc;
	}

	@Override
	public Document updateDocument(Document doc, String collectionName) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            return md.getCollection(collectionName)
	                    .findOneAndUpdate(
	                            Filters.eq("_id", new ObjectId(doc.getString("docId"))),new Document("$set",doc)
	                    );
	        }
	}

	@Override
	public void removeDocument(List<String> ids, String collectionName) {
		 List<ObjectId> objIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
	        Document query = new Document("_id",new Document("$in",objIds));
	        Document update= new Document("$set",new Document("status",0));
	        try(MongoClient mc = new MongoClient(mongoHost, mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            md.getCollection(collectionName).updateMany(query,update);
	        }
		
	}

	@Override
	public Document findDocumentById(String id, String collectionName) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(new Document("_id", new ObjectId(id)).append("status",1));
	            Document doc = findIterable.first();
	            doc.append("docId",doc.getObjectId("_id").toHexString());
	            doc.remove("_id");
	            return doc;
	        }
	}

	@Override
    public List<Document> findAllDocument(String collectionName, String workshop, String workArea, String deviceName, String publicType,String deviceClass,String deviceType,String manufacturers, int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(  workshop,  workArea,  deviceName,  publicType, deviceClass, deviceType, manufacturers);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("creatDateStr", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
    public long findAllDocumentCount(String collectionName,  String workshop, String workArea, String deviceName, String publicType,String deviceClass,String deviceType,String manufacturers) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(workshop,workArea,deviceName,publicType, deviceClass, deviceType, manufacturers);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}
	/**
     * 分页查询条件封装
     * @param name 查询名称
     * @param startUploadDate 开始时间
     * @param endUploadDate 结束时间
     * @param orgId 组织机构id
     * @return 查询条件
     */
    private Bson getFilter( String workshop, String workArea, String deviceName, String publicType,String deviceClass,String deviceType,String manufacturers) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workshop)){
            filter = Filters.and(filter,Filters.regex("workshop",workshop));
        }
        if(StringUtils.isNotBlank(workArea)){
            filter = Filters.and(filter,Filters.regex("workArea",workArea));
        }
        if(StringUtils.isNotBlank(deviceName)){
            filter = Filters.and(filter,Filters.regex("deviceName",deviceName));
        }
        if(StringUtils.isNotBlank(publicType)){
            filter = Filters.and(filter,Filters.eq("publicType",publicType));
        }
        if(StringUtils.isNotBlank(deviceClass)){
            filter = Filters.and(filter,Filters.regex("deviceClass",deviceClass));
        }
        if(StringUtils.isNotBlank(deviceType)){
            filter = Filters.and(filter,Filters.regex("deviceType",deviceType));
        }
        if(StringUtils.isNotBlank(manufacturers)){
            filter = Filters.and(filter,Filters.regex("manufacturers",manufacturers));
        }
//        filter = Filters.and(filter,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
//        filter = Filters.and(filter,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
        return filter;
    }
	@Override
	public String getOrgIdByWorkshop(String workshop) {
		String sql="(select t.org_id_ from cfg_base_organization t where t.org_name_=:workshop and t.delete_state_=1)";
		 Map<String,String> param = new HashMap<>();
	        param.put("workshop",workshop);
	        try {
	            return template.queryForObject(sql, param, String.class);
	        }catch (EmptyResultDataAccessException e){
	            return null;
	        }
	
	}

	@Override
	public String getOrgTypeByWorkshop(String workshop) {
		String sql="(select t.type_ from cfg_base_organization t where t.org_name_=:workshop and t.delete_state_=1)";
		 Map<String,String> param = new HashMap<>();
	        param.put("workshop",workshop);
	        try {
	            return template.queryForObject(sql, param, String.class);
	        }catch (EmptyResultDataAccessException e){
	            return null;
	        }
	
	}
	@Override
	public List<Map<String, Object>> getChildIdByOrgId(String loginOrgId) {
		String sql="select t.org_id_ from cfg_base_organization t where t.parent_id_=:loginOrgId and t.delete_state_=1";
		 Map<String,Object> param = new HashMap<>();
	        param.put("loginOrgId",loginOrgId);
	        return template.query(sql, param,new ChildOrgIdMapper());
	        
	}

	@Override
	public List<String> getCadreAndShop() {
		String sql="select t.org_name_ from cfg_base_organization t where t.type_ = :type and  t.delete_state_ = 1 order by t.type_ ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", 1502);
		List<String> workShopList=template.queryForList(sql,paramMap, String.class);
		return workShopList;
	}

	@Override
	public List<Document> getAllDocumentByPublicType(String collectionName, String publicType) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = Filters.eq("status",1);
	            filter = Filters.and(filter,Filters.eq("publicType",publicType));
//	            Bson filter = getFilter(  workshop,  workArea,  deviceName,  publicType, deviceClass, deviceType, manufacturers);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).sort(new Document("creatDateStr", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
	public ResultMsg importTransDwdm(String publicType, MultipartFile file, String userId, String orgId) {
    	ReadExcelTransDwdm readExcel =new ReadExcelTransDwdm();
    	List<TransDwdmDto> fdpList = readExcel.getExcelInfo(file);
    	//查询一遍数据库中的所有数据用于和表格中的数据比较
    	List<Document> listAll = getAllDocumentByPublicType("deviceRecord",publicType);
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
//   	    removeDocument(reatList, "deviceRecord");
    	//验证表格中的必填数据是否有空
    	Map<String, Object> checkNullMap = checkNullRowTransDwdm(fdpList);
    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTransDwdm(fdpList);
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
		    		document.put("systemName", s.getSystemName());
		    		document.put("deviceCode", s.getDeviceCode());
		    		document.put("deviceName", s.getDeviceName());
		    		document.put("deviceId", s.getDeviceId());
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
		    		document.put("configChannel", s.getConfigChannel()); 
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
				    addDocument(document,collectionName);
	    	 });
       	    removeDocument(reatList, "deviceRecord");
	    	 return ResultMsg.getSuccess("导入成功");
    	}
	}
  /**
	 * 验证导入数据中的必填项是否有未填列
	 * @return 
	 */
	public Map<String,Object> checkNullRowTransDwdm(List<TransDwdmDto> fdpList) {
	 Map<String, Object> map = new HashMap<String, Object>();
   //检验每一必填列数据中是否有未填，默认为true
 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
 	 boolean systemNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSystemName())==false);
 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
 	 boolean deviceIdNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceId())==false);
 	 boolean site_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_machineRoomCode())==false);
 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
 	 boolean configChannelNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getConfigChannel())==false);
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
 	 }else if(systemNameNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'系统名称'列是否有空白格"));
 		return map;
 	 }else if(deviceNameNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
 		return map;
 	 }else if(deviceIdNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备ID'列是否有空白格"));
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
 	 }else if(configChannelNull==true){
 		map.put("isNull", false);
 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'配置波道'列是否有空白格"));
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
	public Map<String,Object> checkWorkshopAndWorkAreaTransDwdm(List<TransDwdmDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
      //获取所有车间
		List<String> cjList = getCadreAndShop();
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
	public ResultMsg importTransSdh(String publicType, MultipartFile file, String userId, String orgId){
	ReadExcelTransSdh readExcel =new ReadExcelTransSdh();
	List<TransSdhDto> fdpList = readExcel.getExcelInfo(file);
	//查询一遍数据库中的所有数据用于和表格中的数据比较
	List<Document> listAll = getAllDocumentByPublicType("deviceRecord",publicType);
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
	Map<String, Object> checkNullMap = checkDtoNullRowTransSdh(fdpList);
	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTransSdh(fdpList);
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
//	    		document.put("source", "1");
	    		
	    		document.put("workshop", s.getWorkshop());
	    		document.put("workArea", s.getWorkArea());
	    		document.put("combinationClass", s.getCombinationClass());
	    		document.put("deviceClass", s.getDeviceClass()); 
	    		document.put("deviceCode", s.getDeviceCode());
	    		document.put("deviceName", s.getDeviceName());
	    		document.put("deviceId", s.getDeviceId());
	    		document.put("systemName", s.getSystemName());
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
			    addDocument(document,collectionName);
    	 });
    	 //删除数据库中来源为上一次导入的数据
    	 removeDocument(reatList, "deviceRecord");
    	 return ResultMsg.getSuccess("导入成功");
	}
	}
  public Map<String,Object> checkDtoNullRowTransSdh(List<TransSdhDto> fdpList) {
	Map<String, Object> map = new HashMap<String, Object>();
    //检验每一必填列数据中是否有未填，默认为true
  	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
  	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
  	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
  	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
  	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
  	 boolean deviceIdNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceId())==false);
  	 boolean systemNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSystemName())==false);
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
  	 }else if(deviceIdNull==true){
  		map.put("isNull", false);
  		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备ID'列是否有空白格"));
  		return map;
  	 }else if(systemNameNull==true){
   		map.put("isNull", false);
   		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'系统名称'列是否有空白格"));
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
	public Map<String,Object> checkWorkshopAndWorkAreaTransSdh(List<TransSdhDto> fdpList) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取所有车间
		List<String> cjList = getCadreAndShop();
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
	public ResultMsg importTransNumberTrans(String publicType, MultipartFile file, String userId, String orgId) {
		ReadExcelTransNumberTrans readExcel =new ReadExcelTransNumberTrans();
		List<TransNumberTransDto> fdpList = readExcel.getExcelInfo(file);
		//查询一遍数据库中的所有数据用于和表格中的数据比较
		List<Document> listAll = getAllDocumentByPublicType("deviceRecord",publicType);
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
		Map<String, Object> checkNullMap = checkDtoNullRowTransNumberTrans(fdpList);
		Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTransNumberTrans(fdpList);
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
				    addDocument(document,collectionName);
	    	 });
	    	 //删除数据库中来源为上一次导入的数据
	    	 removeDocument(reatList, "deviceRecord");
	    	 return ResultMsg.getSuccess("导入成功");
		}
	}
	 public Map<String,Object> checkDtoNullRowTransNumberTrans(List<TransNumberTransDto> fdpList) {
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
			public Map<String,Object> checkWorkshopAndWorkAreaTransNumberTrans(List<TransNumberTransDto> fdpList) {
				Map<String, Object> map = new HashMap<String, Object>();
				//获取所有车间
				List<String> cjList = getCadreAndShop();
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
			public ResultMsg importTransOther(String publicType, MultipartFile file, String userId, String orgId) {
				ReadExcelTransOther readExcel =new ReadExcelTransOther();
				List<TransOtherDto> fdpList = readExcel.getExcelInfo(file);
				//查询一遍数据库中的所有数据用于和表格中的数据比较
				List<Document> listAll = getAllDocumentByPublicType("deviceRecord",publicType);
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
				Map<String, Object> checkNullMap = checkDtoNullRowTransOther(fdpList);
				Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTransOther(fdpList);
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
				    		document.put("accessType", s.getAccessType()); 
				    		document.put("manufacturers", s.getManufacturers()); 
				    		document.put("deviceType", s.getDeviceType()); 
				    		document.put("useUnit", s.getUseUnit()); 
				    		document.put("totalCapacity", s.getTotalCapacity()); 
				    		document.put("roadCapacity", s.getRoadCapacity()); 
				    		document.put("assetRatio", s.getAssetRatio()); 
				    		document.put("capacityUnit", s.getCapacityUnit()); 
				    		document.put("productionDate", s.getProductionDate()); 
				    		document.put("useDate", s.getUseDate()); 
				    		document.put("deviceOperationStatus", s.getDeviceOperationStatus()); 
				    		document.put("stopDate", s.getStopDate()); 
				    		document.put("scrapDate", s.getScrapDate()); 
				    		document.put("fixedAssetsCode", s.getFixedAssetsCode()); 
				    		document.put("remark", s.getRemark());
				    		
				    		document.put("userId", userId);
				    		document.put("orgId", orgId);
						    addDocument(document,collectionName);
			    	 });
			    	 //删除数据库中来源为上一次导入的数据
			    	 removeDocument(reatList, "deviceRecord");
			    	 return ResultMsg.getSuccess("导入成功");
				}
			}
 public Map<String,Object> checkDtoNullRowTransOther(List<TransOtherDto> fdpList) {
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
		public Map<String,Object> checkWorkshopAndWorkAreaTransOther(List<TransOtherDto> fdpList) {
			Map<String, Object> map = new HashMap<String, Object>();
			//获取所有车间
			List<String> cjList = getCadreAndShop();
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
		public ResultMsg importTransOtn(String publicType, MultipartFile file, String userId, String orgId) {
			ReadExcelTransOtn readExcel =new ReadExcelTransOtn();
	    	List<TransOtnDto> fdpList = readExcel.getExcelInfo(file);
	    	//查询一遍数据库中的所有数据用于和表格中的数据比较
	    	List<Document> listAll = getAllDocumentByPublicType("deviceRecord",publicType);
	    	//存放数据来源于导入的数据的id,用于新导入时删除上一次导入数据
	    	List<String> reatList = new ArrayList<String>();
	    	//表格中的数据和数据库中的数据做比较，将数据库中相同的数据中存放在新list中
	    	listAll.stream().forEach(s->{
			//判断导入名称和数据库中的劳资号是否匹配，如果匹配将对应的id放入list中
	    		if(StringUtils.isNotBlank((String) s.get("source"))==false){
	    			reatList.add((String)s.get("docId"));
	    		}
	    	});
//	    	 删除数据库中来源为上一次导入的数据
//	   	    removeDocument(reatList, "deviceRecord");
	    	//验证表格中的必填数据是否有空
	    	Map<String, Object> checkNullMap = checkNullRowTransOtn(fdpList);
	    	Map<String, Object> checkCjMap = checkWorkshopAndWorkAreaTransOtn(fdpList);
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
			    		document.put("systemName", s.getSystemName());
			    		document.put("deviceCode", s.getDeviceCode());
			    		document.put("deviceName", s.getDeviceName());
			    		document.put("deviceId", s.getDeviceId());
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
			    		document.put("configChannel", s.getConfigChannel()); 
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
					    addDocument(document,collectionName);
		    	 });
	       	    removeDocument(reatList, "deviceRecord");
		    	 return ResultMsg.getSuccess("导入成功");
	    	}
		}
		 /**
		 * 验证导入数据中的必填项是否有未填列
		 * @return 
		 */
		public Map<String,Object> checkNullRowTransOtn(List<TransOtnDto> fdpList) {
		 Map<String, Object> map = new HashMap<String, Object>();
	   //检验每一必填列数据中是否有未填，默认为true
	 	 boolean workshopNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkshop())==false);
	 	 boolean workAreaNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getWorkArea())==false);
	 	 boolean combinationClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getCombinationClass())==false);
	 	 boolean deviceClassNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceClass())==false);
	 	 boolean systemNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSystemName())==false);
	 	 boolean deviceNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceName())==false);
	 	 boolean deviceIdNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceId())==false);
	 	 boolean site_machineRoomCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getSite_machineRoomCode())==false);
	 	 boolean assetOwnershipNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getAssetOwnership())==false);
	 	 boolean ownershipUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitName())==false);
	 	 boolean ownershipUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getOwnershipUnitCode())==false);
	 	 boolean maintainBodyNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainBody())==false);
	 	 boolean maintainUnitNameNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitName())==false);
	 	 boolean maintainUnitCodeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getMaintainUnitCode())==false);
	 	 boolean manufacturers =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getManufacturers())==false);
	 	 boolean deviceTypeNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getDeviceType())==false);
	 	 boolean configChannelNull =fdpList.stream().anyMatch(f->StringUtils.isNotBlank(f.getConfigChannel())==false);
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
	 	 }else if(systemNameNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'系统名称'列是否有空白格"));
	 		return map;
	 	 }else if(deviceNameNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备名称'列是否有空白格"));
	 		return map;
	 	 }else if(deviceIdNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'设备ID'列是否有空白格"));
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
	 	 }else if(configChannelNull==true){
	 		map.put("isNull", false);
	 		map.put("ResultMsg", ResultMsg.getFailure("导入失败!请检查'配置波道'列是否有空白格"));
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
		public Map<String,Object> checkWorkshopAndWorkAreaTransOtn(List<TransOtnDto> fdpList) {
			 Map<String, Object> map = new HashMap<String, Object>();
	      //获取所有车间
			List<String> cjList = getCadreAndShop();
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
class ChildOrgIdMapper implements RowMapper<Map<String, Object>> {
	
	public Map<String, Object> mapRow(ResultSet rs, int idx) throws SQLException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("orgId",rs.getString("org_id_"));
		return p;
	}
	
}
