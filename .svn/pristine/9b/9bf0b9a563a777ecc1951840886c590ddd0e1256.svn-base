package com.enovell.yunwei.km_micor_service.service.dayToJobManageService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("dayToJobSumService")
public class DayToJobSumServiceImpl implements DayToJobSumService {

	 @Value("${spring.data.mongodb.host}")
	 private String mongoHost;
	 @Value("${spring.data.mongodb.port}")
	 private int mongoPort;
	 @Value("${spring.data.mongodb.database}")
	 private String mongoDatabase;
	 @Resource(name="pointOuterMaintainQueryService")
	 private PointOuterMaintainQueryService service;
	 /**
	  * 事故故障汇总
	  */
	@Override
	public Map<String,List<Document>> allSum( String currentDay, String userId) {
		Map<String,List<Document>> map=new HashMap();
		List<Document> accidentResults=new ArrayList<>();//事故，故障集合
		List<Document> obstacleResults=new ArrayList<>();//障碍集合
		List<Document> netResults=new ArrayList<>();//网管信息集合
		List<Document> otherResults=new ArrayList<>();//其他生产信息集合
		List<Document> lostResults=new ArrayList<>();//遗留信息集合
		List<Document> repairResults=new ArrayList<>();//施工维修天窗信息集合
		List<Document> videoResults=new ArrayList<>();//电视电话紧急通知集合
		List<Document> cooperResults=new ArrayList<>();//施工配合集合
		List<Document> outsideResults=new ArrayList<>();//点外作业情况集合
		List<Document> mainJobResults=new ArrayList<>();//重点工作集合
		List<Document> leaderSafeResults=new ArrayList<>();//上级安全集合
		List<Document> duanSafeResults=new ArrayList<>();//段安全问题集合
		List<Document> companyCheckResults=new ArrayList<>();//集团公司重点追查安全问题集合
		List<Document> duanCheckResults=new ArrayList<>();//段重点追查安全问题集合
		List<Document> cadreDutyResults=new ArrayList<>();//干部值班情况集合
		List<Document> inspectionSuperiorsResults=new ArrayList<>();//上级检查情况集合
		List<Document> pointOuterResults=new ArrayList<>();//点外集合
		try(MongoClient mc=new MongoClient(mongoHost,mongoPort)){
			MongoDatabase md=mc.getDatabase(mongoDatabase);
			Bson commonFilter=getCommonFilter(currentDay,userId);//通用的筛选条件
			//获取事故，故障汇总数据
			Bson accidentFilter=getAccidentFilter(currentDay,userId);
			FindIterable<Document> accidentIterable = md.getCollection("informationData").find(accidentFilter).sort(new Document("createDate", 1));
			accidentIterable.forEach((Block<? super Document>) accidentResults::add);
			//获取障碍汇总数据
			Bson obstacleFilter=getObstacleFilter(currentDay,userId);
			FindIterable<Document> obstacleIterable = md.getCollection("informationData").find(obstacleFilter).sort(new Document("createDate", 1));
			obstacleIterable.forEach((Block<? super Document>) obstacleResults::add);
			//获取网管汇总数据
			Bson netFilter=getNetFilter(currentDay,userId);
			FindIterable<Document> netIterable = md.getCollection("informationData").find(netFilter).sort(new Document("createDate", 1));
			netIterable.forEach((Block<? super Document>) netResults::add);
			//其他生产信息汇总数据
			Bson otherFilter=getOtherFilter(currentDay,userId);
			FindIterable<Document> otherIterable = md.getCollection("informationData").find(otherFilter).sort(new Document("createDate", 1));
			otherIterable.forEach((Block<? super Document>) otherResults::add);
			//获取遗留信息汇总数据
			Bson lostFilter=getLostFilter(currentDay,userId);
			FindIterable<Document> lostIterable = md.getCollection("informationData").find(lostFilter).sort(new Document("createDate", 1));
			lostIterable.forEach((Block<? super Document>) lostResults::add);
			//施工维修天窗信息汇总数据
			Bson repairFilter=getRepairFilter(currentDay,userId); //add  yangsy 2019-05-10
			FindIterable<Document> repairIterable = md.getCollection("constructRepair").find(repairFilter).sort(new Document("date", 1));
			repairIterable.forEach((Block<? super Document>) repairResults::add);
			//电视电话通知汇总数据
			FindIterable<Document> videoIterable = md.getCollection("videoPhoneNotice").find(commonFilter).sort(new Document("noticeDateStr", 1));
			videoIterable.forEach((Block<? super Document>) videoResults::add);
			//施工配合汇总数据
			FindIterable<Document> cooperIterable = md.getCollection("constructCooperate").find(commonFilter).sort(new Document("date", 1));
			cooperIterable.forEach((Block<? super Document>) cooperResults::add);
			//点外作业汇总数据
			FindIterable<Document> outsideIterable = md.getCollection("outsideWorkCondition").find(commonFilter).sort(new Document("noticeDateStr", 1));
			outsideIterable.forEach((Block<? super Document>) outsideResults::add);
			//重点工作汇总数据
			FindIterable<Document> mainJobIterable = md.getCollection("completionOfKeyTasks").find(commonFilter).sort(new Document("noticeDateStr", 1));
			mainJobIterable.forEach((Block<? super Document>) mainJobResults::add);
			//上级安全汇总数据
			FindIterable<Document> leaderSafeIterable = md.getCollection("superiorSafetyNotification").find(commonFilter).sort(new Document("noticeDateStr", 1));
			leaderSafeIterable.forEach((Block<? super Document>) leaderSafeResults::add);
			//段安全问题汇总数据
			FindIterable<Document> duanSafeIterable = md.getCollection("duanSafetyNotification").find(commonFilter).sort(new Document("noticeDateStr", 1));
			duanSafeIterable.forEach((Block<? super Document>) duanSafeResults::add);
			//集团公司重点追查汇总数据
			FindIterable<Document> companyCheckIterable = md.getCollection("companyTraceSafetyInfomation").find(commonFilter).sort(new Document("noticeDateStr", 1));
			companyCheckIterable.forEach((Block<? super Document>) companyCheckResults::add);
			//段重点追查汇总数据
			FindIterable<Document> duanCheckIterable = md.getCollection("duanTraceSafetyInfomation").find(commonFilter).sort(new Document("noticeDateStr", 1));
			duanCheckIterable.forEach((Block<? super Document>) duanCheckResults::add);
			//干部值班情况
			FindIterable<Document> cadreDutyIterable = md.getCollection("cadreDuty").find(commonFilter).sort(new Document("noticeDateStr", 1));
			cadreDutyIterable.forEach((Block<? super Document>) cadreDutyResults::add);
			//上级检查情况
			FindIterable<Document> inspectionSuperiorsIterable = md.getCollection("inspectionSuperiors").find(commonFilter).sort(new Document("noticeDateStr", 1));
			inspectionSuperiorsIterable.forEach((Block<? super Document>) inspectionSuperiorsResults::add);
			//点外汇总数据
			Bson pointOutFilter=getPointOutFilter(currentDay,userId);
			FindIterable<Document> pointOuterIterable = md.getCollection("pointOuterMaintain").find(pointOutFilter).sort(new Document("createDate", 1));
			pointOuterIterable.forEach((Block<? super Document>) pointOuterResults::add);
		}
		
		map.put("accidentSum", accidentResults);
		map.put("obstacleSum", obstacleResults);
		map.put("netSum", netResults);
		map.put("otherSum", otherResults);
		map.put("lostSum", lostResults);
		map.put("repairSum", repairResults);
		map.put("videoSum", videoResults);
		map.put("cooperSum", cooperResults);
		map.put("outsideSum", outsideResults);
		map.put("mainJobSum", mainJobResults);
		map.put("leaderSafeSum", leaderSafeResults);
		map.put("duanSafeSum", duanSafeResults);
		map.put("companyCheckSum", companyCheckResults);
		map.put("duanCheckSum", duanCheckResults);
		map.put("cadreDutySum", cadreDutyResults);
		map.put("inspectionSuperiorsSum", inspectionSuperiorsResults);
		map.put("pointOuterSum", pointOuterResults);
		return map;
	}
	

	//点外作业筛选条件
	private Bson getPointOutFilter(String currentDay, String userId) {
		Date now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.HOUR, -24);
		Date befor=calendar.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String befoTime=sdf.format(befor);
		
		Bson filters=Filters.eq("status",1);
		filters = Filters.and(filters,Filters.eq("workDate",befoTime));
		filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
	    filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		
		return filters;
	}


	//事故筛选条件
	private Bson getAccidentFilter(String currentDay,String userId) {
		Bson filters=Filters.eq("status",1);
		 filters = Filters.and(filters,Filters.in("infoResult","1","2"));//事故故障
		 filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
	     filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		return filters;
	}
	//障碍筛选条件
	private Bson getObstacleFilter(String currentDay, String userId) {
		Bson filters=Filters.eq("status",1);
		 filters = Filters.and(filters,Filters.eq("infoResult","3"));//障碍
		 filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
	     filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		return filters;
	}
	//网管信息筛选条件
	private Bson getNetFilter(String currentDay, String userId) {
		Bson filters=Filters.eq("status",1);
		 filters = Filters.and(filters,Filters.eq("projectType","network"));//网管信息
		 filters = Filters.and(filters,Filters.eq("infoResult","0"));//无信息后果
		 filters = Filters.and(filters,Filters.eq("lost","0"));//无遗留
		 filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
	     filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		return filters;
	}
	//其他生产信息筛选条件
	private Bson getOtherFilter(String currentDay, String userId) {
		Bson filters=Filters.eq("status",1);
		filters = Filters.and(filters,Filters.eq("projectType","other"));//网管信息
		filters = Filters.and(filters,Filters.eq("infoResult","0"));//无信息后果
		filters = Filters.and(filters,Filters.eq("lost","0"));//无遗留
		filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
		filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		return filters;
	}
	//遗留信息筛选条件
	private Bson getLostFilter(String currentDay, String userId) {
		Bson filters=Filters.eq("status",1);
		filters = Filters.and(filters,Filters.in("projectType","other","network"));//网管信息
		filters = Filters.and(filters,Filters.eq("infoResult","0"));//无信息后果
		filters = Filters.and(filters,Filters.eq("lost","1"));//无遗留
		filters = Filters.and(filters,Filters.eq("print","1"));//打印：是
		filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
		filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		return filters;
	}
	
	//施工维修天窗 - yangsy 2019-05-10
	private Bson getRepairFilter(String currentDay, String userId) {
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		String day = "";
		Date date = new Date();
		day = sdfDay.format(date);
		Bson filters=Filters.eq("status",1);
		filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
		filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		filters = Filters.and(filters,Filters.lt("actOverTime",day+" 08:00:00"));//每天只汇总8点整以前的数据
		return filters;
	}
	
	//通用筛选条件（电视电话通知,施工配合,重点工作,上级安全,段安全,集团重点追查,段重点追查，干部值班情况，上级检查情况，点外作业情况）
	private Bson getCommonFilter(String currentDay, String userId) {
		Bson filters=Filters.eq("status",1);
		filters = Filters.and(filters,Filters.in("summaryDate",currentDay,""));//当前系统日期与汇总日期相同或者汇总日期为空
		filters = Filters.and(filters,Filters.in("summaryPersonId",userId,""));//当前登陆用户ID与汇总人ID相同或者汇总人ID为空
		return filters;
	}


	/**
	 * 保存汇总信息，修改汇总状态
	 */
	@Override
	public void saveSumInfo(Map<String, List<Document>> map,String userId,String userName) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String currentDay =sdf.format(new Date());
		//修改事故，故障的汇总信息修改
        List<ObjectId> accidentIds=getSumId(map,userId,userName,currentDay,"accidentSum");
        List<ObjectId> obstacleIds=getSumId(map,userId,userName,currentDay,"obstacleSum");
        List<ObjectId> netIds=getSumId(map,userId,userName,currentDay,"netSum");
        List<ObjectId> otherIds=getSumId(map,userId,userName,currentDay,"otherSum");
//        List<ObjectId> lostIds=getSumId(map,userId,userName,currentDay,"lostSum");
        accidentIds.addAll(obstacleIds);
        accidentIds.addAll(netIds);
        accidentIds.addAll(otherIds);
//        accidentIds.addAll(lostIds);
        updateSumData(accidentIds,userId,userName,currentDay,"informationData");
        //施工维修天窗汇总信息修改
        List<ObjectId> repairIds=getSumId(map,userId,userName,currentDay,"repairSum");
        updateSumData(repairIds,userId,userName,currentDay,"constructRepair");
        //电视电话紧急通知汇总信息修改
        List<ObjectId> videoIds=getSumId(map,userId,userName,currentDay,"videoSum");
        updateSumData(videoIds,userId,userName,currentDay,"videoPhoneNotice");
        //施工配合汇总信息修改
        List<ObjectId> cooperIds=getSumId(map,userId,userName,currentDay,"cooperSum");
        updateSumData(cooperIds,userId,userName,currentDay,"constructCooperate");
        //点外作业情况汇总信息修改
        List<ObjectId> outsideIds=getSumId(map,userId,userName,currentDay,"outsideSum");
        updateSumData(outsideIds,userId,userName,currentDay,"outsideWorkCondition");
        //重点工作汇总信息修改
        List<ObjectId> mainJobIds=getSumId(map,userId,userName,currentDay,"mainJobSum");
        updateSumData(mainJobIds,userId,userName,currentDay,"completionOfKeyTasks");
        //上级安全汇总信息修改
        List<ObjectId> leaderSafeIds=getSumId(map,userId,userName,currentDay,"leaderSafeSum");
        updateSumData(leaderSafeIds,userId,userName,currentDay,"superiorSafetyNotification");
        //段安全汇总信息修改
        List<ObjectId> duanSafeIds=getSumId(map,userId,userName,currentDay,"duanSafeSum");
        updateSumData(duanSafeIds,userId,userName,currentDay,"duanSafetyNotification");
        //集团公司重点追查安全问题汇总信息修改
        List<ObjectId> companyCheckIds=getSumId(map,userId,userName,currentDay,"companyCheckSum");
        updateSumData(companyCheckIds,userId,userName,currentDay,"companyTraceSafetyInfomation");
        //段重点追查安全问题汇总信息修改
        List<ObjectId> duanCheckIds=getSumId(map,userId,userName,currentDay,"duanCheckSum");
        updateSumData(duanCheckIds,userId,userName,currentDay,"duanTraceSafetyInfomation");
        //干部值班情况修改
        List<ObjectId> cadreDutyIds=getSumId(map,userId,userName,currentDay,"cadreDutySum");
        updateSumData(cadreDutyIds,userId,userName,currentDay,"cadreDuty");
        //上级检查情况修改
        List<ObjectId> inspectionSuperiorsIds=getSumId(map,userId,userName,currentDay,"inspectionSuperiorsSum");
        updateSumData(inspectionSuperiorsIds,userId,userName,currentDay,"inspectionSuperiors");
        //点外汇总信息修改
        List<ObjectId> pointOuterIds=getSumId(map,userId,userName,currentDay,"pointOuterSum");
        updateSumData(pointOuterIds,userId,userName,currentDay,"pointOuterMaintain");
        
	}

	@Override
	public List<ObjectId> getSumId(Map<String, List<Document>> map, String userId, String userName, String currentDay,
			String key) {
		List<Document> results=map.get(key);
		List<String> sumIds=new ArrayList<String>();
		for (Document d : results) {
			String sumId=d.get("_id").toString();
			sumIds.add(sumId);
		}
		List<ObjectId> objIds = sumIds.stream().map(ObjectId::new).collect(Collectors.toList());
		return objIds;
	}


	@Override
	public void updateSumData(List<ObjectId> objIds, String userId, String userName, String currentDay,
			String collectionName) {
		Document query = new Document("_id",new Document("$in",objIds));	
		Document updatePersonId= new Document("$set",new Document("summaryPersonId",userId));
		Document updatePersonName= new Document("$set",new Document("summaryPersonName",userName));
		Document updateSummaryDate= new Document("$set",new Document("summaryDate",currentDay));
		try(MongoClient mc = new MongoClient(mongoHost, mongoPort)){
			MongoDatabase md = mc.getDatabase(mongoDatabase);
			md.getCollection(collectionName).updateMany(query, updatePersonId);
			md.getCollection(collectionName).updateMany(query, updatePersonName);
			md.getCollection(collectionName).updateMany(query, updateSummaryDate);
		}
		
	}

}
