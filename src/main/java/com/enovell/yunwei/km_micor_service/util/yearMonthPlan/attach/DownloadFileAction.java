package com.enovell.yunwei.km_micor_service.util.yearMonthPlan.attach;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 项目名称：yearMonthPlan
 * 类名称：DownloadFileAction   
 * 类描述:  年月报表批量导出Action
 * 创建人：lidt 
 * 创建时间：2018年12月10日 上午10:24:16
 * 修改人：lidt 
 * 修改时间：2018年12月10日 上午10:24:16   
 *
 */
@RequestMapping("/DownloadFileAction")
@Controller
public class DownloadFileAction {
	
	/**    
	 * expYearMonthPlan 批量导出年月报表压缩包
	 *
	 * @author lidt
	 * @date 2018年12月10日 上午10:24:33 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/expYearMonthPlan")
	public void expYearMonthPlan(HttpServletRequest request,HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");

		File file = null;
		InputStream inputStream = null;
		ByteArrayOutputStream bos = null;
		byte[] data = null;
		try {
			file = new File(filePath);
			if (file != null && !file.exists()) {
				response.setContentType("text/html; charset=utf-8");
				response.setCharacterEncoding("utf-8");
				response.getWriter().println("源文件不存在，无法下载");
				return;
			}

			inputStream = new FileInputStream(file);
			bos = new ByteArrayOutputStream(1024);
			data = new byte[1024];
			int n;
			while ((n = inputStream.read(data)) != -1) {
				bos.write(data, 0, n);
			}
			
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			fileName = fileName + "_" + formatDate.format(new Date()) + ".zip";
			fileName = URLEncoder.encode(fileName, "UTF-8");
			
			response.reset();
			response.setContentType("application/x-download");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition","attachment;filename=" + fileName);

			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bos.toByteArray());
			servletOutputStream.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (bos != null) {
					bos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
