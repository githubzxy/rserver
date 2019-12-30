package com.enovell.yunwei.km_micor_service.action;

import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolDTO;
import com.enovell.yunwei.km_micor_service.dto.WorkAssortProtocolExportDTO;
import com.enovell.yunwei.km_micor_service.service.WorkAssortProtocolService;
import com.enovell.yunwei.km_micor_service.util.CommonPoiExportExcel;
import com.enovell.yunwei.km_micor_service.util.JsonUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kunmingTXD
 * 昆明微服务施工计划Action
 *
 * @author liwt
 * @date 18-11-26
 */
@RestController
@RequestMapping("/workAssortProtocolAction")
public class WorkAssortProtocolAction {
	@Resource(name = "workAssortProtocolService")
	private WorkAssortProtocolService service;

	@RequestMapping("/exportXls")
	@ResponseBody
	public void exportXls(
			@RequestParam("exportXlsJson") String exportXlsJson,
			HttpServletRequest request,
			HttpServletResponse response){
		WorkAssortProtocolDTO workAssortProtocolDTO = JsonUtil.jsonToJavaObj(exportXlsJson, WorkAssortProtocolDTO.class);
		List<WorkAssortProtocolExportDTO> dataList= service.getAllFile(workAssortProtocolDTO);
		String[] headerTableColumns = new String[]{ 
				"施工项目" + "_" +"30" + "_" + "my.getFileName()",
				"所属部门" + "_" +"20" + "_" + "my.getOrganization()",
				"创建时间" + "_" +"30" + "_" + "my.getCreateDate()",
				"创建人" + "_" +"25" + "_" + "my.getCreateUser()",
		}; 
		Map<String, Object> expandJexlContext = new HashMap<String, Object>();
		expandJexlContext.put("tool", new SimpleDateFormat(JsonUtil.DATE_AND_TIME));

		CommonPoiExportExcel<WorkAssortProtocolExportDTO> export =  new CommonPoiExportExcel<WorkAssortProtocolExportDTO>();
		Workbook wb = export.exportXls("施工配合协议", headerTableColumns, expandJexlContext, dataList, null);
		ServletOutputStream out = null;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = urlEncoder(request, "施工配合协议");
			fileName = fileName +"-"+ formatDate.format(new Date());
			
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("binary/octet-stream");
			
			response.setHeader("Content-disposition", "attachment; fileName = " + fileName + ".xls");
			
			out = response.getOutputStream();
			wb.write(out);
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
	
	 /**
     * urlEncoder 防止附件中文乱码
     *
     * @param request
     * @param fileName
     * @return
     */
    public static String urlEncoder(HttpServletRequest request, String fileName) {
        try {
            // 将字母全部转化为大写，判断是否存在RV字符串
            if (request.getHeader("User-Agent").toUpperCase().indexOf("RV") > 0) {
                // 处理IE 的头部信息
                fileName = URLEncoder.encode(fileName, "UTF-8");// 对字符串进行URL加码，中文字符变成%+16进制
            } else {
                // 处理其他的头部信息
                fileName = new String(fileName.substring(fileName.lastIndexOf("/") + 1).getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
