package com.enovell.yunwei.km_micor_service.service;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.enovell.yunwei.km_micor_service.service.dayToJobManageService.DayToJobService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * kunmingTXD
 * 公用service测试
 * @author bili
 * @date 18-11-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonPageServiceImplTest {
    @Resource(name="commonPageService")
    private CommonPageService commonPageService;
    @Resource(name="dayToJobService")
	private DayToJobService dayToJobService;

    @Test
    public void uploadFile() {
    }

    @Test
    public void findDocumentById() {
        String id = "5bf3bedf8dd8136b501f2020";
        Document res = commonPageService.findDocumentById(id,"test");
        System.out.println("res = " + res);
    }

    @Test
    public void findAllDocument() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse("2018-11-20 15:30:00");
        Date endDate = sdf.parse("2018-11-20 18:30:00");
        List<Document> result = commonPageService.findAllDocument("dailyShiftForDuan",null,null,null,null,0,10,null);
//        List<Document> result = commonPageService.findAllDocument("test","testAdd",null,null,null,0,10);
//        List<Document> result = commonPageService.findAllDocument("test","testAdd",startDate,null,null,0,10);
//        List<Document> result = commonPageService.findAllDocument("test","testAdd",startDate,endDate,null,0,10);
//        List<Document> result = commonPageService.findAllDocument("test","testAdd",startDate,endDate,"8affa0735391f425015483d40be50074",0,10);
        result.forEach(System.out::println);
//        long count = commonPageService.findAllDocumentCount("test",null,null,null,null);
//          long count = commonPageService.findAllDocumentCount("test","testAdd",null,null,null);
//        long count = commonPageService.findAllDocumentCount("test","testAdd",startDate,null,null);
//        long count = commonPageService.findAllDocumentCount("test","testAdd",startDate,endDate,null);
//        long count = commonPageService.findAllDocumentCount("test","testAdd",startDate,endDate,"8affa0735391f425015483d40be50074");
//        System.out.println("count = " + count);
    }

    @Test
    public void addDocument() {
        Document test = new Document();
        test.put("createDate",new Date());
        test.put("name","testName2");
        test.put("testCol1","12");
        test.put("testCol2",new ArrayList<String>());
        Document res = commonPageService.addDocument(test,"test");
        System.out.println("res = " + res);
    }

    @Test
    public void updateDocument() {
        Document upTest = new Document();
        upTest.put("_id",new ObjectId("5bf3bedf8dd8136b501f2020"));
        upTest.put("testCol1","111");
        upTest.put("testCol2",new ArrayList<String>());
        Document res = commonPageService.updateDocument(upTest,"test");
        System.out.println("res = " + res);
    }

    @Test
    public void removeDocument() {
        List<String> ids = new ArrayList<>();
        ids.add("5bf3d2f28dd813024f9a695b");
        ids.add("5bf3d3f88dd813057730e2aa");
        ids.add("5bf3d5708dd81307ef9a6170");
        ids.add("5bf3d65a8dd81309d241d6d7");
        commonPageService.removeDocument(ids,"test");
    }
    
    @Test
    public void allSum(){
    	String userName = "昆明通信车间";
    	String userId = "8affa0735391f425015483c974fa006e";
    	String a = dayToJobService.createGenerateSumTable("","",userName, userId,"test1","test2","2019-01-24","test3");
    }
}