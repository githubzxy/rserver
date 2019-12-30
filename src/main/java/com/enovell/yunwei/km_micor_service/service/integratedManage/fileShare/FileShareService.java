package com.enovell.yunwei.km_micor_service.service.integratedManage.fileShare;

import org.bson.Document;

import com.enovell.yunwei.km_micor_service.dto.GridDto;

/**文件共享业务逻辑接口
 * @author roysong
 * 2019年5月16日-下午4:29:15
 */
public interface FileShareService {

	/**分页查询文件及目录信息
	 * @param start
	 * @param limit
	 * @param collectionName
	 * @param parentId
	 * @param name 按名称模糊查询
	 * @return
	 */
	GridDto<Document> findAll(int start, int limit, String collectionName, String parentId, String name);

	/**根据id查询文件及目录
	 * @param id
	 * @param collectionName 
	 * @return
	 */
	Document getById(String id, String collectionName);

	/**根据id进行逻辑删除
	 * @param id
	 * @param collectionName 
	 */
	void deleteById(String id, String collectionName);

	/**添加新目录或者文件
	 * @param doc
	 * @param collectionName
	 */
	void add(Document doc, String collectionName);

	/**修改目录
	 * @param doc
	 * @param collectionName
	 */
	void update(Document doc, String collectionName);

}
