package com.enovell.yunwei.km_micor_service.util;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ReadFileUtil
 * @Description: 读取文件工具 
 * @author luoyan
 * @date 2018年5月16日 上午10:45:24 
 */

@Component
public class ReadFileUtil {
	
	@Autowired
	private ResourceLoader resourceLoader;
	/**
	 * 
	 * @Title: readFile 
	 * @param path 文件的相对于classpath路径
	 * @return
	 * @throws IOException
	 * String 文件内容
	 * @author luoyan
	 */
//	public String readFile(String path) throws IOException{
//		return IOUtils.toString(resourceLoader.getResource("classpath:"+path).getInputStream(), "UTF-8");
//	}
	
	/**
	 * 读取excel文件
	 * @param path
	 * @return HSSFWorkbook
	 * @throws IOException
	 */
	public HSSFWorkbook getWorkBook(String path)throws IOException{
		return new HSSFWorkbook(resourceLoader.getResource("classpath:"+path).getInputStream());
	}
}
