package com.enovell.yunwei.km_micor_service.service.productionManage.overhaulRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainfzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainfzWordCell;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainzfzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainzfzWordCell;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainzjzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.HighTrainzjzWordCell;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.TrainzjzDto;
import com.enovell.yunwei.km_micor_service.dto.overhaulRecord.TrainzjzWordCell;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
@Service("overhaulRecordService")
public class  OverhaulRecordServiceImpl implements  OverhaulRecordService{
		@Value("${spring.data.mongodb.host}")
	    private String mongoHost;
	    @Value("${spring.data.mongodb.port}")
	    private int mongoPort;
	    @Value("${spring.data.mongodb.database}")
	    private String mongoDatabase;
	    @Autowired
		private NamedParameterJdbcTemplate template;
	    @Value("${overhaulRecordPath}")
		private String overhaulRecordPath;//设备检修记录模板存放路径
		@Value("${overhaulRecordDownloadPath}")
		private String overhaulRecordDownloadPath;//生成文件的存放路径（用于后端写出）
		@Value("${overhaulRecordFileDownloadPath}")
		private String overhaulRecordFileDownloadPath;//生成文件的存放路径（用于前端导出）
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
    public List<Document> findAllDocument(String collectionName,  String userId,String workshop,String workArea,String line,String machineRoom,String overhaulName,String startUploadDate,String endUploadDate,int start, int limit) {
		 List<Document> results = new ArrayList<>();
	        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, workshop, workArea, line, machineRoom, overhaulName, startUploadDate, endUploadDate);
	            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("overhaulDate", -1));
	            findIterable.forEach((Block<? super Document>) results::add);
	        }
	        results.stream().forEach(d-> {
	            d.append("docId",d.getObjectId("_id").toHexString());
	            d.append("orgIdAndorgType",d.get("orgId")+","+d.get("orgType"));
	            d.remove("_id");
	        });
	        return results;
	}

	@Override
    public long findAllDocumentCount(String collectionName,  String userId, String workshop,String workArea,String line,String machineRoom,String overhaulName, String startUploadDate,String endUploadDate) {
		 try(MongoClient mc = new MongoClient(mongoHost,mongoPort)) {
	            MongoDatabase md = mc.getDatabase(mongoDatabase);
	            Bson filter = getFilter(userId, workshop,  workArea, line, machineRoom, overhaulName, startUploadDate, endUploadDate);
	            return  md.getCollection(collectionName).count(filter);
	        }
	}
	/**
     * 分页查询条件封装
     * @param name 查询名称
     * @return 查询条件
     */
    private Bson getFilter(String userId ,String workshop,	String workArea,String line,String machineRoom,String overhaulName,String startUploadDate,String endUploadDate) {
        Bson filter = Filters.eq("status",1);
        if(StringUtils.isNotBlank(workshop)){
            filter = Filters.and(filter,Filters.regex("workshop",workshop));
        }
        if(StringUtils.isNotBlank(workArea)){
            filter = Filters.and(filter,Filters.regex("workArea",workArea));
        }
        if(StringUtils.isNotBlank(line)){
            filter = Filters.and(filter,Filters.regex("line",line));
        }
        if(StringUtils.isNotBlank(machineRoom)){
            filter = Filters.and(filter,Filters.regex("machineRoom",machineRoom));
        }
        if(StringUtils.isNotBlank(overhaulName)){
            filter = Filters.and(filter,Filters.regex("overhaulName",overhaulName));
        }
        if(StringUtils.isNotBlank(startUploadDate)){
            filter = Filters.and(filter,Filters.gte("overhaulDate",startUploadDate));
        }
        if(StringUtils.isNotBlank(endUploadDate)){
            filter = Filters.and(filter,Filters.lte("overhaulDate",endUploadDate));
        }
        return filter;
    }
    @Override
	public List<String> getWorkShops() {
		String sql="select t.org_name_ from cfg_base_organization t where t.type_ = :type";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", 1502);
		List<String> workShopList=template.queryForList(sql,paramMap, String.class);
		return workShopList;
	}

    @Override
	public List<String> getWorAreasByName(String workshop) {
		String sql="select t.org_name_ from cfg_base_organization t where t.parent_id_=(select s.org_id_ from cfg_base_organization s where s.org_name_=:workshop and s.delete_state_ = 1) and t.delete_state_ = 1";
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("workshop",workshop);
		List<String> workAreaList=template.queryForList(sql,paramMap, String.class);
		return workAreaList;
	}

    @Override
 	public List<String> getMachineroomByLine(String line) {
    	String sql="select t.machine_name_ from res_base_rail_machineroom t where t.line_ = :line";
 		Map<String,Object> paramMap = new HashMap<String,Object>();
         paramMap.put("line",line);
 		List<String> roomList=template.queryForList(sql,paramMap, String.class);
 		return roomList;
 	}
    @Override
    public List<String> getMachineroom() {
    	String sql="select t.machine_name_ from res_base_rail_machineroom t";
    	List<String> roomList=template.queryForList(sql,new HashMap<>(), String.class);
    	return roomList;
    }

	@Override
	public String exportHighTrainfz(HighTrainfzDto dto,String machineRoom,String overhaulPerson,String overhaulDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String outFilePath = date+"HighTrainfz.docx";
		String year = overhaulDate.substring(0,4);
		String month = overhaulDate.substring(5,7);
		String day = overhaulDate.substring(8,10);
		try {
			FileInputStream fis = new FileInputStream(new File(overhaulRecordPath+"highTrainFZ.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				
				
				XWPFRun title = table.getRow(HighTrainfzWordCell.title.getRow()).getCell(HighTrainfzWordCell.title.getCol()).getParagraphArray(0).createRun();
				title.setFontFamily("宋体");
				title.setFontSize(12);
				title.setText("站名："+machineRoom+"	检修人："+overhaulPerson+"	检修日期："+year+"年"+month+"月"+day+"日");
				
				
				//-----------------------------------------第一个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_first_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_first_state.getRow()).getCell(HighTrainfzWordCell.fz_first_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_first_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_first_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_first_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_first_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_first_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_second_state.getRow()).getCell(HighTrainfzWordCell.fz_first_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_first_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_first_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_first_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_first_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_first_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_third_state.getRow()).getCell(HighTrainfzWordCell.fz_first_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_first_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_first_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_first_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_first_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_first_fourth_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_fourth_state.getRow()).getCell(HighTrainfzWordCell.fz_first_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_first_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_first_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_first_fourth_remark.getRow()).getCell(HighTrainfzWordCell.fz_first_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_first_fourth_remark());
				}
				//-----------------------------------------第二个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_second_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_first_state.getRow()).getCell(HighTrainfzWordCell.fz_second_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_second_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_second_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_second_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_second_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_second_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_second_state.getRow()).getCell(HighTrainfzWordCell.fz_second_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_second_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_second_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_second_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_second_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_second_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_third_state.getRow()).getCell(HighTrainfzWordCell.fz_second_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_second_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_second_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_second_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_second_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_second_fourth_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_fourth_state.getRow()).getCell(HighTrainfzWordCell.fz_second_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_second_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_second_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_fourth_remark.getRow()).getCell(HighTrainfzWordCell.fz_second_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_second_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getFz_second_fifth_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_fifth_state.getRow()).getCell(HighTrainfzWordCell.fz_second_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_second_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_second_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_second_fifth_remark.getRow()).getCell(HighTrainfzWordCell.fz_second_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_second_fifth_remark());
				}
				//-----------------------------------------第三 个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_third_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_first_state.getRow()).getCell(HighTrainfzWordCell.fz_third_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_third_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_third_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_third_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_third_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_third_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_second_state.getRow()).getCell(HighTrainfzWordCell.fz_third_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_third_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_third_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_third_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_third_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_third_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_third_state.getRow()).getCell(HighTrainfzWordCell.fz_third_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_third_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_third_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_third_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_third_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getFz_third_fourth_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_fourth_state.getRow()).getCell(HighTrainfzWordCell.fz_third_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_third_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_third_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_third_fourth_remark.getRow()).getCell(HighTrainfzWordCell.fz_third_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_third_fourth_remark());
				}
				//-----------------------------------------第四个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_fourth_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fourth_first_state.getRow()).getCell(HighTrainfzWordCell.fz_fourth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_fourth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_fourth_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fourth_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_fourth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_fourth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_fourth_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fourth_second_state.getRow()).getCell(HighTrainfzWordCell.fz_fourth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_fourth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_fourth_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fourth_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_fourth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_fourth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_fourth_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fourth_third_state.getRow()).getCell(HighTrainfzWordCell.fz_fourth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_fourth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_fourth_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fourth_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_fourth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_fourth_third_remark());
				}
				//-----------------------------------------第五个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_fifth_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fifth_first_state.getRow()).getCell(HighTrainfzWordCell.fz_fifth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_fifth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_fifth_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fifth_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_fifth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_fifth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_fifth_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fifth_second_state.getRow()).getCell(HighTrainfzWordCell.fz_fifth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_fifth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_fifth_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fifth_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_fifth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_fifth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_fifth_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fifth_third_state.getRow()).getCell(HighTrainfzWordCell.fz_fifth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_fifth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_fifth_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_fifth_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_fifth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_fifth_third_remark());
				}
				//-----------------------------------------第六个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_sixth_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_sixth_first_state.getRow()).getCell(HighTrainfzWordCell.fz_sixth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_sixth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_sixth_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_sixth_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_sixth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_sixth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_sixth_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_sixth_second_state.getRow()).getCell(HighTrainfzWordCell.fz_sixth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_sixth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_sixth_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_sixth_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_sixth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_sixth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_sixth_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_sixth_third_state.getRow()).getCell(HighTrainfzWordCell.fz_sixth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_sixth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_sixth_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_sixth_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_sixth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_sixth_third_remark());
				}
				//-----------------------------------------第七个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_seventh_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_seventh_first_state.getRow()).getCell(HighTrainfzWordCell.fz_seventh_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_seventh_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_seventh_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_seventh_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_seventh_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_seventh_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_seventh_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_seventh_second_state.getRow()).getCell(HighTrainfzWordCell.fz_seventh_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_seventh_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_seventh_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_seventh_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_seventh_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_seventh_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_seventh_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_seventh_third_state.getRow()).getCell(HighTrainfzWordCell.fz_seventh_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_seventh_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_seventh_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_seventh_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_seventh_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_seventh_third_remark());
				}
				//-----------------------------------------第八个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_eighth_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_eighth_first_state.getRow()).getCell(HighTrainfzWordCell.fz_eighth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_eighth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_eighth_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_eighth_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_eighth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_eighth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_eighth_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_eighth_second_state.getRow()).getCell(HighTrainfzWordCell.fz_eighth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_eighth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_eighth_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_eighth_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_eighth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_eighth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_eighth_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_eighth_third_state.getRow()).getCell(HighTrainfzWordCell.fz_eighth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_eighth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_eighth_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_eighth_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_eighth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_eighth_third_remark());
				}
				//-----------------------------------------第九个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_ninth_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_ninth_first_state.getRow()).getCell(HighTrainfzWordCell.fz_ninth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_ninth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_ninth_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_ninth_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_ninth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_ninth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_ninth_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_ninth_second_state.getRow()).getCell(HighTrainfzWordCell.fz_ninth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_ninth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_ninth_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_ninth_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_ninth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_ninth_second_remark());
				}
				
				//-----------------------------------------第十个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getFz_tenth_first_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_first_state.getRow()).getCell(HighTrainfzWordCell.fz_tenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_tenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_tenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_first_remark.getRow()).getCell(HighTrainfzWordCell.fz_tenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_tenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_tenth_second_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_second_state.getRow()).getCell(HighTrainfzWordCell.fz_tenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_tenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_tenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_second_remark.getRow()).getCell(HighTrainfzWordCell.fz_tenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_tenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getFz_tenth_third_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_third_state.getRow()).getCell(HighTrainfzWordCell.fz_tenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_tenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getFz_tenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_third_remark.getRow()).getCell(HighTrainfzWordCell.fz_tenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_tenth_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getFz_tenth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_fourth_state.getRow()).getCell(HighTrainfzWordCell.fz_tenth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getFz_tenth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getFz_tenth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainfzWordCell.fz_tenth_fourth_remark.getRow()).getCell(HighTrainfzWordCell.fz_tenth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getFz_tenth_fourth_remark());
				}
			};
			FileOutputStream fos = new FileOutputStream(new File(overhaulRecordDownloadPath+outFilePath));
			xdoc.write(fos);
			xdoc.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(overhaulRecordFileDownloadPath+outFilePath);
		return overhaulRecordFileDownloadPath+outFilePath;
	}

	@Override
	public String exportHighTrainzjz(HighTrainzjzDto dto,String machineRoom,String overhaulPerson,String overhaulDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String outFilePath = date+"HighTrainzjz.docx";
		String year = overhaulDate.substring(0,4);
		String month = overhaulDate.substring(5,7);
		String day = overhaulDate.substring(8,10);
		if(overhaulPerson==null){
			overhaulPerson = dto.getOverhaulPerson();
		}
		try {
			FileInputStream fis = new FileInputStream(new File(overhaulRecordPath+"highTrainZJZ.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				
				XWPFRun title = table.getRow(HighTrainzjzWordCell.title.getRow()).getCell(HighTrainzjzWordCell.title.getCol()).getParagraphArray(0).createRun();
				title.setFontFamily("宋体");
				title.setFontSize(12);
				title.setText("站名："+machineRoom+"	检修人："+overhaulPerson+"	检修日期："+year+"年"+month+"月"+day+"日");
				
				
				//-----------------------------------------第一个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_first_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_first_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_first_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_first_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_first_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_first_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_first_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_first_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_first_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_first_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_first_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_first_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_first_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_first_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_first_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_first_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_first_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_first_fourth_remark());
				}
				//-----------------------------------------第二个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_second_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_second_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_second_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_second_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_fifth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_fifth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_fifth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_sixth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_sixth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_sixth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_sixth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_sixth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_seventh_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_seventh_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_seventh_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_second_seventh_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_second_seventh_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_second_seventh_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_second_seventh_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_second_seventh_remark());
				}
				//-----------------------------------------第三 个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_third_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_third_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_third_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_third_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_third_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_third_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_third_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_third_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_third_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_third_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_third_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_third_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_third_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_third_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_third_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_third_second_remark());
				}
				
				//-----------------------------------------第四个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_third_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_fourth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_fifth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_fifth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_fifth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_sixth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_sixth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_sixth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_sixth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_sixth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_seventh_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_seventh_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_seventh_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourth_seventh_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourth_seventh_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourth_seventh_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourth_seventh_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourth_seventh_remark());
				}
				//-----------------------------------------第五个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifth_third_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_fifth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifth_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifth_fifth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifth_fifth_remark());
				}
				//-----------------------------------------第六个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_sixth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_sixth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_sixth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_sixth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_sixth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_sixth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_sixth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_sixth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_sixth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_sixth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_sixth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_sixth_third_remark());
				}
				//-----------------------------------------第七个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_seventh_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventh_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventh_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventh_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_seventh_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventh_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventh_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventh_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_seventh_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventh_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventh_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventh_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_seventh_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventh_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventh_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventh_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_seventh_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventh_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventh_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventh_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_seventh_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventh_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventh_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventh_third_remark());
				}
				//-----------------------------------------第八个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eighth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eighth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eighth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eighth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eighth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eighth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eighth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_eighth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eighth_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eighth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eighth_fourth_remark());
				}
				//-----------------------------------------第九个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_second_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_third_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_fifth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_fifth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_fifth_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_sixth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_sixth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_sixth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_sixth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_sixth_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_seventh_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_seventh_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_seventh_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_ninth_seventh_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_ninth_seventh_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_ninth_seventh_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_ninth_seventh_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_ninth_seventh_remark());
				}
				//-----------------------------------------第十个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_tenth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_tenth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_tenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_tenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_tenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_tenth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_tenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_tenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_tenth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_tenth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_tenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_tenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_tenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_tenth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_tenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_tenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_tenth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_tenth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_tenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_tenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_tenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_tenth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_tenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_tenth_third_remark());
				}
				//-----------------------------------------第十一个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_eleventh_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eleventh_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eleventh_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eleventh_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_eleventh_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eleventh_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eleventh_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eleventh_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_eleventh_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eleventh_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eleventh_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eleventh_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_eleventh_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eleventh_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eleventh_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eleventh_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_eleventh_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eleventh_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_eleventh_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_eleventh_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_eleventh_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_eleventh_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_eleventh_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_eleventh_third_remark());
				}
				//-----------------------------------------第十二个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_twelfth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_twelfth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_twelfth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_twelfth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_twelfth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_twelfth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_twelfth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_twelfth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_twelfth_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_twelfth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_twelfth_fourth_remark());
				}
				//-----------------------------------------第十三个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_thirteenth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_thirteenth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_thirteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_thirteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_thirteenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_thirteenth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_thirteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_thirteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_thirteenth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_thirteenth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_thirteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_thirteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_thirteenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_thirteenth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_thirteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_thirteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_thirteenth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_thirteenth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_thirteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_thirteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_thirteenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_thirteenth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_thirteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_thirteenth_third_remark());
				}
				//-----------------------------------------第十四个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_fourteenth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourteenth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourteenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourteenth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourteenth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourteenth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourteenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourteenth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fourteenth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourteenth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fourteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fourteenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fourteenth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fourteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fourteenth_third_remark());
				}
				//-----------------------------------------第十五个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_fifteenth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifteenth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifteenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifteenth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifteenth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifteenth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifteenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifteenth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_fifteenth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifteenth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_fifteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_fifteenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_fifteenth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_fifteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_fifteenth_third_remark());
				}
				//-----------------------------------------第十六个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_sixteenth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixteenth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_sixteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_sixteenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixteenth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_sixteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_sixteenth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixteenth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_sixteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_sixteenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixteenth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_sixteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_sixteenth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixteenth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_sixteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_sixteenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_sixteenth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_sixteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_sixteenth_third_remark());
				}
				//-----------------------------------------第十七个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_first_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_first_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventeenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_first_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventeenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_second_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_second_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventeenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_second_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventeenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_third_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_third_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventeenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_third_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventeenth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_fourth_state.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getHtzjz_seventeenth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getHtzjz_seventeenth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzjzWordCell.htzjz_seventeenth_fourth_remark.getRow()).getCell(HighTrainzjzWordCell.htzjz_seventeenth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getHtzjz_seventeenth_fourth_remark());
				}
			};
			FileOutputStream fos = new FileOutputStream(new File(overhaulRecordDownloadPath+outFilePath));
			xdoc.write(fos);
			xdoc.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(overhaulRecordFileDownloadPath+outFilePath);
		return overhaulRecordFileDownloadPath+outFilePath;
	}

	@Override
	public String exportTrainzjz(TrainzjzDto dto,String machineRoom,String overhaulPerson,String overhaulDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String outFilePath = date+"Trainzjz.docx";
		String year = overhaulDate.substring(0,4);
		String month = overhaulDate.substring(5,7);
		String day = overhaulDate.substring(8,10);
		if(overhaulPerson==null){
			overhaulPerson = dto.getOverhaulPerson();
		}
		try {
			FileInputStream fis = new FileInputStream(new File(overhaulRecordPath+"trainZJZ.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				
				
				XWPFRun title = table.getRow(TrainzjzWordCell.title.getRow()).getCell(TrainzjzWordCell.title.getCol()).getParagraphArray(0).createRun();
				title.setFontFamily("宋体");
				title.setFontSize(12);
				title.setText("站名："+machineRoom+"	检修人："+overhaulPerson+"	检修日期："+year+"年"+month+"月"+day+"日");
				
				
				//-----------------------------------------第一个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_first_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_first_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_first_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_first_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_first_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_first_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_first_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_first_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_first_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_first_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_first_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_first_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_first_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_first_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_first_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_first_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_first_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_first_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_first_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_first_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_first_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_first_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_first_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_first_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_first_fifth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_fifth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_first_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_first_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_first_fifth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_first_fifth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_first_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_first_fifth_remark());
				}
				//-----------------------------------------第二个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_second_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_second_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_second_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_second_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_second_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_second_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_second_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_second_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_second_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_second_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_second_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_second_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_second_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_second_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_second_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_second_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_second_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_second_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_second_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_second_fifth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_fifth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_second_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_second_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_fifth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_fifth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_second_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_second_fifth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_sixth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_sixth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_second_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_second_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_second_sixth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_second_sixth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_second_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_second_sixth_remark());
				}
				
				//-----------------------------------------第三 个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_third_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_third_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_third_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_third_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_third_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_third_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_third_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_third_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_third_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_third_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_third_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_third_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_third_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_third_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_third_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_third_second_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_third_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_third_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_third_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_third_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_third_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_third_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_third_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_third_third_remark());
				}
				
				//-----------------------------------------第四个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourth_third_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourth_fourth_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_fifth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_fifth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fourth_fifth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourth_fifth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourth_fifth_remark());
				}
				
				//-----------------------------------------第五个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_third_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_fifth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_fifth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_fifth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_fifth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_fifth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_sixth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_sixth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_sixth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_sixth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_sixth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_seventh_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_seventh_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_seventh_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifth_seventh_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifth_seventh_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifth_seventh_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifth_seventh_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifth_seventh_remark());
				}
				//-----------------------------------------第六个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_sixth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_sixth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_sixth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_sixth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_sixth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_sixth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_sixth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_sixth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_sixth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_sixth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_sixth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_sixth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_sixth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_sixth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_sixth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_sixth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_sixth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_sixth_third_remark());
				}
				//-----------------------------------------第七个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_seventh_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventh_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_seventh_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_seventh_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_seventh_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventh_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_seventh_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_seventh_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_seventh_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventh_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_seventh_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_seventh_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_seventh_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventh_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_seventh_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_seventh_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_seventh_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventh_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_seventh_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_seventh_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_seventh_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventh_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_seventh_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_seventh_third_remark());
				}
				//-----------------------------------------第八个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_fifth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_fifth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_fifth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_fifth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_fifth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_sixth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_sixth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_sixth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_sixth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_sixth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_seventh_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_seventh_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_seventh_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighth_seventh_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighth_seventh_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighth_seventh_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighth_seventh_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighth_seventh_remark());
				}
				//-----------------------------------------第九个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_ninth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_ninth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_ninth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_ninth_second_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_ninth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_ninth_third_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_ninth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_ninth_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_ninth_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_ninth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_ninth_fourth_remark());
				}
				//-----------------------------------------第十个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_tenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_tenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_tenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_tenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_tenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_tenth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_tenth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_tenth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_fifth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_fifth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_tenth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_fifth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_fifth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_tenth_fifth_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_sixth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_sixth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_tenth_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_tenth_sixth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_tenth_sixth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_tenth_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_tenth_sixth_remark());
				}
				//-----------------------------------------第十一个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_eleventh_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eleventh_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eleventh_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eleventh_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eleventh_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eleventh_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eleventh_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eleventh_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_eleventh_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eleventh_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eleventh_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eleventh_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_eleventh_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eleventh_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eleventh_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eleventh_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_eleventh_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eleventh_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eleventh_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eleventh_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eleventh_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eleventh_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eleventh_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eleventh_third_remark());
				}
				//-----------------------------------------第十二个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_twelfth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_twelfth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_twelfth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_twelfth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_twelfth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_twelfth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_twelfth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_twelfth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_twelfth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_twelfth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_twelfth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_twelfth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_twelfth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_twelfth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_twelfth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_twelfth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_twelfth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_twelfth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_twelfth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_twelfth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_twelfth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_twelfth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_twelfth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_twelfth_third_remark());
				}
				//-----------------------------------------第十三个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_thirteenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_thirteenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_thirteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_thirteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_thirteenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_thirteenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_thirteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_thirteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_thirteenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_thirteenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_thirteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_thirteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_thirteenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_thirteenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_thirteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_thirteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_thirteenth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_thirteenth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_thirteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_thirteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_thirteenth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_thirteenth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_thirteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_thirteenth_third_remark());
				}
				//-----------------------------------------第十四个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_fourteenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourteenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fourteenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourteenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourteenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourteenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourteenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourteenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fourteenth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourteenth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fourteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fourteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fourteenth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fourteenth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fourteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fourteenth_third_remark());
				}
				//-----------------------------------------第十五个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_fifteenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifteenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifteenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifteenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifteenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifteenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifteenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifteenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_fifteenth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifteenth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_fifteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_fifteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_fifteenth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_fifteenth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_fifteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_fifteenth_third_remark());
				}
				//-----------------------------------------第十六个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_sixteenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixteenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_sixteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_sixteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_sixteenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixteenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_sixteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_sixteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_sixteenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixteenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_sixteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_sixteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_sixteenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_sixteenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_sixteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_sixteenth_second_remark());
				}
				
				//-----------------------------------------第十七个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_seventeenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventeenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_seventeenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_seventeenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_seventeenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventeenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_seventeenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_seventeenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_seventeenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventeenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_seventeenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_seventeenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_seventeenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventeenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_seventeenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_seventeenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_seventeenth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventeenth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_seventeenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_seventeenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_seventeenth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_seventeenth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_seventeenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_seventeenth_third_remark());
				}
				
				//-----------------------------------------第十八个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_eighteenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighteenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighteenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighteenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighteenth_first_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighteenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighteenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_eighteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_eighteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_eighteenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_eighteenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_eighteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_eighteenth_second_remark());
				}
				//-----------------------------------------第十九个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_first_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_first_state.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_nineteenth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_first_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_first_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_nineteenth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_second_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_second_state.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_nineteenth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_second_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_second_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_nineteenth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_third_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_third_state.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_nineteenth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_third_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_third_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_nineteenth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_fourth_state())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_fourth_state.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getPtzjz_nineteenth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getPtzjz_nineteenth_fourth_remark())){
					XWPFRun run = table.getRow(TrainzjzWordCell.ptzjz_nineteenth_fourth_remark.getRow()).getCell(TrainzjzWordCell.ptzjz_nineteenth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getPtzjz_nineteenth_fourth_remark());
				}
			};
			FileOutputStream fos = new FileOutputStream(new File(overhaulRecordDownloadPath+outFilePath));
			xdoc.write(fos);
			xdoc.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(overhaulRecordFileDownloadPath+outFilePath);
		return overhaulRecordFileDownloadPath+outFilePath;
	}

	@Override
	public String exportHighTrainzfz(HighTrainzfzDto dto,String machineRoom,String overhaulPerson,String overhaulDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String outFilePath = date+"HighTrainzfz.docx";
		String year = overhaulDate.substring(0,4);
		String month = overhaulDate.substring(5,7);
		String day = overhaulDate.substring(8,10);
		try {
			FileInputStream fis = new FileInputStream(new File(overhaulRecordPath+"highTrainZFZ.docx"));
			XWPFDocument xdoc = new XWPFDocument(fis);
			for (XWPFTable table : xdoc.getTables()) {
				
				
				XWPFRun title = table.getRow(HighTrainzfzWordCell.title.getRow()).getCell(HighTrainzfzWordCell.title.getCol()).getParagraphArray(0).createRun();
				title.setFontFamily("宋体");
				title.setFontSize(12);
				title.setText("站名："+machineRoom+"	检修人："+overhaulPerson+"	检修日期："+year+"年"+month+"月"+day+"日");
				
				
				//-----------------------------------------第一个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_first_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_first_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_first_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_first_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_first_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_first_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_first_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_first_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_first_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_first_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_first_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_first_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_first_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_first_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_first_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_first_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_first_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_first_third_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_first_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_fourth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_first_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_first_fourth_state().equals("0")){
	          	    run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_first_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_first_fourth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_first_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_first_fourth_remark());
				}
				//-----------------------------------------第二个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_second_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_second_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_second_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_second_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_second_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_second_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_second_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_second_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_second_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_second_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_second_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_second_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_second_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_second_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_second_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_second_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_second_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_second_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_second_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_second_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_second_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_second_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_second_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_second_third_remark());
				}
				//-----------------------------------------第三 个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_third_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_third_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_third_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_third_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_third_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_third_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_third_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_third_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_third_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_third_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_third_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_third_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_third_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_third_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_third_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_third_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_third_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_third_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_third_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_fourth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_third_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_third_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_third_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_fourth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_third_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_third_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_third_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_fifth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_third_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_third_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_third_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_third_fifth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_third_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_third_fifth_remark());
				}
				//-----------------------------------------第四个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_fourth_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_fourth_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_fourth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_fourth_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_fourth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_fourth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_fifth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_fifth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_fifth_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_sixth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_sixth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_sixth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_sixth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_sixth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_sixth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_sixth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_sixth_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_seventh_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_seventh_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_seventh_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_seventh_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_seventh_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_seventh_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_seventh_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_seventh_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_eighth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_eighth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_eighth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fourth_eighth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fourth_eighth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fourth_eighth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fourth_eighth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fourth_eighth_remark());
				}
				//-----------------------------------------第五个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_fifth_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fifth_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fifth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fifth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fifth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fifth_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fifth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fifth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_fifth_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fifth_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fifth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fifth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_fifth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fifth_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fifth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fifth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_fifth_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fifth_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_fifth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_fifth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_fifth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_fifth_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_fifth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_fifth_third_remark());
				}
				//-----------------------------------------第六个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_sixth_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_sixth_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_sixth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_sixth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_sixth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_sixth_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_sixth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_sixth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_sixth_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_sixth_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_sixth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_sixth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_sixth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_sixth_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_sixth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_sixth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_sixth_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_sixth_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_sixth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_sixth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_sixth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_sixth_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_sixth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_sixth_third_remark());
				}
				//-----------------------------------------第七个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_seventh_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_seventh_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_seventh_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_seventh_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_seventh_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_seventh_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_seventh_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_seventh_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_seventh_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_seventh_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_seventh_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_seventh_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_seventh_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_seventh_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_seventh_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_seventh_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_seventh_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_seventh_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_seventh_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_seventh_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_seventh_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_seventh_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_seventh_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_seventh_third_remark());
				}
				//-----------------------------------------第八个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_eighth_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_eighth_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_eighth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_eighth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_eighth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_eighth_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_eighth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_eighth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_eighth_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_eighth_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_eighth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_eighth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_eighth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_eighth_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_eighth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_eighth_second_remark());
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_eighth_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_eighth_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_eighth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_eighth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_eighth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_eighth_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_eighth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_eighth_third_remark());
				}
				//-----------------------------------------第九个设备----------------------------------------------
				if(StringUtils.isNotBlank(dto.getZfz_ninth_first_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_first_state.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_first_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_ninth_first_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				if(StringUtils.isNotBlank(dto.getZfz_ninth_first_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_first_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_first_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_ninth_first_remark());
				}
				
				
				if(StringUtils.isNotBlank(dto.getZfz_ninth_second_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_second_state.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_second_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_ninth_second_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_ninth_second_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_second_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_second_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_ninth_second_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_ninth_third_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_third_state.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_third_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_ninth_third_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_ninth_third_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_third_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_third_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_ninth_third_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_ninth_fourth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_fourth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_fourth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_ninth_fourth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_ninth_fourth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_fourth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_fourth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_ninth_fourth_remark());
				}
				if(StringUtils.isNotBlank(dto.getZfz_ninth_fifth_state())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_fifth_state.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_fifth_state.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					if(dto.getZfz_ninth_fifth_state().equals("0")){
					run.setText("不正常");
					}else{
					run.setText("正常");
					}
				}
				
				if(StringUtils.isNotBlank(dto.getZfz_ninth_fifth_remark())){
					XWPFRun run = table.getRow(HighTrainzfzWordCell.zfz_ninth_fifth_remark.getRow()).getCell(HighTrainzfzWordCell.zfz_ninth_fifth_remark.getCol()).getParagraphArray(0).createRun();
					run.setFontFamily("宋体");
					run.setFontSize(12);
					run.setText(dto.getZfz_ninth_fifth_remark());
				}
				
			};
			FileOutputStream fos = new FileOutputStream(new File(overhaulRecordDownloadPath+outFilePath));
			xdoc.write(fos);
			xdoc.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(overhaulRecordFileDownloadPath+outFilePath);
		return overhaulRecordFileDownloadPath+outFilePath;
	}

}

