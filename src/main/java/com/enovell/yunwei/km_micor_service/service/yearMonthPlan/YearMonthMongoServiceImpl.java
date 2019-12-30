package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.ExcelUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 通过mongo进行汇总统计业务逻辑实现
 * @author RoySong
 * 2017年5月25日--下午12:25:30
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Service("yearMonthMongoService")
public class YearMonthMongoServiceImpl implements YearMonthMongoService {
	/**
	 * function中的占位符，用于动态替换
	 */
	private static final String HOLDER = "$$$";
	/**
	 * map函数模板
	 */
	private static final String MAP_FUNCTION_TPL = "function(){emit({"+HOLDER+"},this);}";
	/**
	 * reduce函数模板
	 */
	private static final String REDUCE_FUNCTION_TPL = "function(key,values){"+HOLDER+"}";
	@Autowired
	private MongoTemplate mongoTemplate;
	/* (non-Javadoc)
	 * @see com.enovell.yunwei.yearMonth.service.AnysisMongoService#groupbyAdd(int[], int[])
	 */
	public List<Map> groupbyAdd(String collectionName,int[] groupIdxs, int[] addIndexs) {
		String mapFunction = generateMapFunction(groupIdxs);
		String reduceFunction = generateReduceFunction(addIndexs);
		MapReduceResults<Map> datas = mongoTemplate.mapReduce(collectionName, mapFunction, reduceFunction, Map.class);
		List<Map> result = new ArrayList<Map>();
		Iterator<Map> iter = datas.iterator();
		while(iter.hasNext()){
			Map oneRow = iter.next();
			Map baseInfo = (Map) oneRow.get("_id");
			Map cols = (Map) oneRow.get("value");
			baseInfo.putAll(cols);
			result.add(baseInfo);
		}
		return result;
	}
	/**通过累加的字段序号生成reduce function
	 * @param addIndexs
	 * @return
	 */
	private String generateReduceFunction(int[] addIndexs) {
		String content = "var result = {};values.forEach(function(val){";
		for(int idx : addIndexs){
			content += "var cellVal = val['cell" +idx+ "'];"
					+ "if(!result['cell" +idx+ "']){result['cell" +idx+ "'] = 0;}"
					+ "if(cellVal && !isNaN(cellVal)){"
					+ "result['cell" +idx+ "'] += parseFloat(cellVal);"
					+ "}else if(cellVal){"
					+ "if(result['cell" +idx+ "'] == '0'){"
					+ "result['cell" +idx+ "'] = cellVal;"
					+ "}else{"
					+ "result['cell" +idx+ "'] += '"+ExcelUtil.STRING_CONCAT_SYMBOL+"' + cellVal;"
					+ "}}";
		}
		content += "});return result;";
		return REDUCE_FUNCTION_TPL.replace(HOLDER, content);
	}
	/**通过聚合的字段序号生成map function
	 * @param groupIdxs
	 * @return
	 */
	private String generateMapFunction(int[] groupIdxs) {
		String emitContent = "";
		for(int i = 0;i < groupIdxs.length;i++){
			if(i == groupIdxs.length - 1){
				emitContent += "cell" + groupIdxs[i] + ":this.cell" +  groupIdxs[i];
			}else{
				emitContent += "cell" + groupIdxs[i] + ":this.cell" +  groupIdxs[i] + ",";
			}
		}
		return MAP_FUNCTION_TPL.replace(HOLDER, emitContent);
	}
	public void clearCollection(String collectionName) {
		mongoTemplate.dropCollection(collectionName);
	}
	
	public List<Map> queryforFieldsAndSort(String collectionName,String[] fields,String[] sortField,boolean[] asc,Map<String,Object> params){
		DBObject dbObject = new BasicDBObject();
		if(params != null)
			for(Entry<String, Object> e : params.entrySet()){
				dbObject.put(e.getKey(), e.getValue());
			}
	    DBObject fieldObject = new BasicDBObject();
	    if(fields != null)
		    for(String field : fields){
		    	fieldObject.put(field, true);
		    }
	    Query query = new BasicQuery(dbObject, fieldObject);
		if(ArrayUtils.isNotEmpty(sortField)){
			Sort sort = null ;
			for(int i = 0;i < sortField.length;i++){
				Direction flag = asc[i] ? Sort.Direction.ASC : Sort.Direction.DESC;
				String sfield = sortField[i];
				if(i == 0){
					sort = new Sort(new Sort.Order(flag,sfield));
				}else{
					sort = sort.and(new Sort(new Sort.Order(flag,sfield)));
				}
				
			}
			query.with(sort);
		}
		List<Map> result = mongoTemplate.find(query, Map.class, collectionName);
		return result;
	}
	@Override
	public void batchInsert(List<Map> workShopData, String collectionName) {
		mongoTemplate.insert(workShopData, collectionName);
	}
	
	public List<Map> search(String collectionName,String[] fields,Map<String,Object> params){
		DBObject dbObject = new BasicDBObject();
		if(params != null)
			for(Entry<String, Object> e : params.entrySet()){
				dbObject.put(e.getKey(), e.getValue());
			}
	    DBObject fieldObject = new BasicDBObject();
	    for(String field : fields){
	    	fieldObject.put(field, true);
	    }
		Query query = new BasicQuery(dbObject, fieldObject);
		List<Map> result = mongoTemplate.find(query, Map.class,collectionName);
		return result;
	}
	
	public void update(String collectionName,Map<String,Object> params,Map<String,Object> updateSets){
		if(MapUtils.isEmpty(params) || MapUtils.isEmpty(updateSets))
			return;
		Iterator<Entry<String, Object>> itor = params.entrySet().iterator();
		Entry<String, Object> first = itor.next();
		final Criteria c = Criteria.where(first.getKey()).is(first.getValue());
		while(itor.hasNext()){
			Entry<String, Object> item = itor.next();
			c.and(item.getKey()).is(item.getValue());
		}
		Query query = new Query(c);
		Update update = new Update();
		updateSets.entrySet().stream().forEach(e->{
			update.set(e.getKey(), e.getValue());
		});
		mongoTemplate.updateMulti(query, update , collectionName);
	}
}
