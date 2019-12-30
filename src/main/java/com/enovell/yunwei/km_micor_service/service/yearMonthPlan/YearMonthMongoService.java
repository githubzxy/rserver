package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.util.List;
import java.util.Map;

/**
 * 通过mongo进行汇总统计业务逻辑接口
 * @author quyy
 * 2017年5月25日--下午12:13:58
 */
@SuppressWarnings("rawtypes")
public interface YearMonthMongoService {

	/**进行汇总聚合累加
	 * @param collectionName 表名
	 * @param groupIdxs 聚合的字段序号
	 * @param addIndxs 累加的字段序号
	 * @return
	 */
	List<Map> groupbyAdd(String collectionName,int[] groupIdxs,int[] addIndxs);

	/**清除指定表的所有数据
	 * @param collectionName
	 */
	void clearCollection(String collectionName);
	
	/**批量添加数据
	 * @param workShopData
	 * @param collectionName
	 */
	void batchInsert(List<Map> workShopData,String collectionName);
	
	/**从指定表中获取指定的某些字段值并按排序字段进行排序
	 * @param collectionName 表名
	 * @param fields 获取的字段名集合,如果为null,则返回所有字段
	 * @param sortField 排序字段
	 * @param bs 排序标识符，true为升序，false为降序，注意，排序标识符的数量和顺序需要和排序字段相匹配
	 * @param params 查询条件
	 * @return
	 */
	List<Map> queryforFieldsAndSort(String collectionName,String[] fields,String[] sortField,boolean[] bs,Map<String,Object> params);
	
	/**查询指定记录
	 * @param collectionName 表名
	 * @param fields 获取的字段名集合
	 * @param params 查询条件
	 * @return
	 */
	List<Map> search(String collectionName,String[] fields,Map<String,Object> params);
	
	/**更新指定记录 
	 * @param collectionName 表名
	 * @param params 查询条件
	 * @param updateSets update的字段及值
	 */
	void update(String collectionName,Map<String,Object> params,Map<String,Object> updateSets);

}
