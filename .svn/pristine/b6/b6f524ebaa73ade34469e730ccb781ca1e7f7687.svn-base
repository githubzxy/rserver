
/**   
 * 文件名：ZipToFileUtil.java    
 * 版本信息：IRMS1.0   
 * 日期：2018年12月10日 上午11:17:28   
 * Copyright Enovell Corporation 2018    
 * 版权所有   
 *   
 */

package com.enovell.yunwei.km_micor_service.util.yearMonthPlan.attach;

import java.io.File;

import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.attach.tozip.ZipToFile;

/**      
 * 项目名称：yearMonthPlan
 * 类名称：ZipToFileUtil   
 * 类描述:  年月报表打包压缩文件工具类
 * 创建人：lidt 
 * 创建时间：2018年12月10日 上午11:17:28
 * 修改人：lidt 
 * 修改时间：2018年12月10日 上午11:17:28   
 *    
 */
public class ZipToFileUtil {
	
	/**    
	 * createZipFile 1.创建压缩文件和被压缩文件夹，并删除已存在的
	 *
	 * @author lidt
	 * @date 2018年12月10日 下午2:06:38 
	 * @return 压缩文件路径
	 */
	public static String createZipFile() {
		File folder = new File(YearMonthPlanFilePathUtils.getZipfilePath());
		deleteFile(folder);
		folder.mkdirs();
		File file = new File(YearMonthPlanFilePathUtils.getZipfile());
		file.delete(); 
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return YearMonthPlanFilePathUtils.getZipfile();
	}
	
	/**    
	 * zipFileAndDel 3.压缩文件;4.删除被压缩文件夹
	 *
	 * @author lidt
	 * @date 2018年12月10日 下午2:18:16 
	 * @param targetZip 压缩文件路径
	 */
	public static void zipFileAndDel(String targetZip) {
		//3.压缩文件
		ZipToFile.zipFile(YearMonthPlanFilePathUtils.getZipfilePath(), targetZip);
		//4.删除被压缩文件夹
		deleteFile(new File(YearMonthPlanFilePathUtils.getZipfilePath()));
	}
	
	/**
	 * deleteFile 删除已存在的文件夹及下面的文件
	 * @param folder
	 */
	private static void deleteFile(File folder) {
		if (folder.exists() && folder.isFile()) {
			folder.delete();
		}
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
			folder.delete();
		}
	}
	
}
