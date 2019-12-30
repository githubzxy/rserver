package com.enovell.yunwei.km_micor_service.service.securityManage.securityQuery;

import org.bson.Document;

public interface DayNumService {

	Document findNum(String collectionName);

	Document updateNum(String dayNum, String collectionName);

}
