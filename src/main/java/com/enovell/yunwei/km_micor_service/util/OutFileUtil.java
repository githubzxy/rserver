package com.enovell.yunwei.km_micor_service.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @ClassName: OutFileUtil
 * @Description: 输出文件，用于导出Excel 
 * @author luoyan
 * @date 2018年5月17日 上午10:43:09 
 */
public class OutFileUtil {
	
	
	/**
	 * 
	 * outFile 输出文件
	 * @param fileName
	 * @param workbook
	 * @param request
	 * @param response
	 */
	public static void outFile(String fileName, Workbook workbook, 
								HttpServletRequest request, HttpServletResponse response)  {
		ServletOutputStream out = null;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			fileName = fileName + formatDate.format(new Date());
			
			request.setCharacterEncoding("UTF-8");
			fileName = URLEncoder.encode(fileName, "UTF-8");
			
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("binary/octet-stream");
			
			response.setHeader("Content-disposition", "attachment; fileName = " + fileName + ".xls");
			
			out = response.getOutputStream();
			workbook.write(out); 
			out.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
