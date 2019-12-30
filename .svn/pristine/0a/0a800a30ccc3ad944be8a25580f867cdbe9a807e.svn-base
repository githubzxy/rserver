package com.enovell.yunwei.km_micor_service.service.securityManage.securityQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service("dayNumService")
public class DayNumServiceImpl implements DayNumService{
	
	 @Autowired
	 private MongoTemplate mt;
	@Override
	public Document findNum(String collectionName) {
		Document doc = mt.findOne(new Query(), Document.class, collectionName);
		return doc;
	}

	@Override
	public Document updateNum(String dayNum, String collectionName) {//5d8978c0cd24712bc0962f6b
		Document document = this.findNum(collectionName);
		Query query = new Query(Criteria.where("_id").is(document.getObjectId("_id").toString()));
		Update update = new Update();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateStr = "";
        currentDateStr = sdf.format(currentDate);
        update.set("updateDate", currentDateStr);
        update.set("dayNum", dayNum);
		mt.updateFirst(query, update, collectionName);
		return document;
	}

}