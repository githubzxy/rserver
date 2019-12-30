package com.enovell.yunwei.km_micor_service.service.integratedManage.fileShare;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

/**文件共享业务逻辑实现
 * @author roysong
 * 2019年5月16日-下午5:09:29
 */
@Service("FileShareService")
public class FileShareServiceImpl implements FileShareService {
	@Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;
	@Override
	public GridDto<Document> findAll(int start, int limit, String collectionName, String parentId, String name) {
        try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Document filter = new Document("status","1");
            if(StringUtils.isNotBlank(name)) {
            	filter.append("name", new Document("$regex",name));
            }else if(StringUtils.isNotBlank(parentId)) {
            	filter.append("parentId", parentId);
            }else {
            	filter.append("parentId", "");
            }
            FindIterable<Document> findIterable = md.getCollection(collectionName).find(filter).skip(start).limit(limit).sort(new Document("type", 1));
            List<Document> results = new ArrayList<>();
            findIterable.forEach((Block<? super Document>) results::add);
            long count = md.getCollection(collectionName).count(filter);
            results.stream().forEach(d-> {
            	d.put("id",d.getObjectId("_id").toHexString());
            	d.remove("_id");
            });
            GridDto<Document> result = new GridDto<Document>();
            result.setResults(count);
            result.setRows(results);
            return result;
        }catch (Exception e) {
     	   throw e;
        }
	}

	@Override
	public Document getById(String id, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = new Document("_id",new ObjectId(id));
			return md.getCollection(collectionName).find(filter).first();
       }catch (Exception e) {
    	   throw e;
       }
	}

	@Override
	public void deleteById(String id, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = new Document("_id",new ObjectId(id));
            Document update = new Document("$set", new Document("status", "0"));
			md.getCollection(collectionName).updateOne(filter , update );
       }catch (Exception e) {
    	   throw e;
       }
	}

	@Override
	public void add(Document doc, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            md.getCollection(collectionName).insertOne(doc);
       }catch (Exception e) {
    	   throw e;
       }
	}

	@Override
	public void update(Document doc, String collectionName) {
		try(MongoClient mc = new MongoClient(mongoHost,mongoPort)){
            MongoDatabase md = mc.getDatabase(mongoDatabase);
            Bson filter = new Document("_id",new ObjectId(doc.getString("id")));
            Document updateValue = new Document();
			doc.entrySet().stream().forEach(d -> updateValue.append(d.getKey(), d.getValue()));
			Document update = new Document("$set",updateValue);
			md.getCollection(collectionName).updateOne(filter , update);
			// 如果目录名称改变，代表相关路径都应改变名称
			if(doc.containsKey("name")) {
				Bson navfilter = new Document("nav.id",doc.getString("id"));
				Document updateNav = new Document("$set",new Document("nav.$.name",doc.get("name").toString()));
				md.getCollection(collectionName).updateMany(navfilter, updateNav);
			}
       }catch (Exception e) {
    	   throw e;
       }
	}

}
