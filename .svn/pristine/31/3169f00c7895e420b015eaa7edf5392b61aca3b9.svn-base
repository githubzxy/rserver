package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.enovell.system.common.domain.User;

@SuppressWarnings("rawtypes")
public class BuildTableByThymeleaf {
	
	/**
	 * （技术支持中心）完成表xml文件，用于thymeleaf的xml模板 文件名称
	 */
	private static final String CTABLE_XML_PLAN_NAME = "excelWorkbook";
	
	/**
	 * 
	 * buildCompleteTable 生成完成表的xml文件根据计划表的数据
	 * @param planDatas 计划表12个月的数据
	 * @param xmlFilePath 生成xml的路径
	 * @param workAreaName 工区组织机构
	 * @param year 年份
	 * @return
	 */
	public static String buildCompleteTable(List<List<Map>> planDatas,
			String xmlFilePath, User user, String workShopName, String year) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", planDatas);
		// 设置当前年份
		map.put("year", year);
		// 设置车间组织机构
		map.put("workShopName", workShopName);
		// 设置工区组织机构
		map.put("workAreaName", user.getOrganization().getName());
		// 使用XML Thymeleaf 模板
		String xmlString = getTableXmlData(map);
		// 将xml的string数据写入指定路径的文件中
		FileWriter writer;
		try {
			writer = new FileWriter(xmlFilePath);
			writer.write(xmlString);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String xmlFileName = xmlFilePath.split("/")[xmlFilePath.split("/").length - 1];
		return xmlFileName;
	}
	/**
	 * 
	 * getTemplateResolver 生成一个thymeleaf模板的解析器
	 * @author quyy
	 * @param filePath
	 * @return
	 */
	private static FileTemplateResolver getTemplateResolver(String filePath) {
		FileTemplateResolver templateResolver = new FileTemplateResolver();
		templateResolver.setPrefix(filePath);
		templateResolver.setSuffix(".xml");
		templateResolver.setTemplateMode("XML");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	/**
	 * 
	 * getPlanData Render 使用XML Thymeleaf模板
	 * @author quyy
	 * @param data 
	 * @return
	 */
	private static String getTableXmlData(Map<String, Object> data) {
		FileTemplateResolver templateResolver = getTemplateResolver(YearMonthPlanFilePathUtils.getPutieXml());
		TemplateEngine engine = new TemplateEngine();
		engine.setTemplateResolver(templateResolver);
		Context context = new Context();
		context.setVariables(data);
		String content = engine.process(CTABLE_XML_PLAN_NAME, context);
		return content;
	}
	
	private static List<Map> getMapdata(){
		List<Map> datas = new ArrayList<>();
	
		for (int i = 0; i < 6; i++) {
			Map<String, Object> pinfo = new HashMap<>();
			pinfo.put("workContent", "工作内容");
			pinfo.put("unit", "个");
			pinfo.put("count", "3");
			pinfo.put("dateWork1", "1");
			pinfo.put("dateWork5", "2");
			pinfo.put("dateWork6", "2");
			datas.add(pinfo);
		}
		
		for (int i = 0; i < 10; i++) {
			Map<String, Object> pinfo = new HashMap<>();
			pinfo.put("workContent", "test");
			pinfo.put("unit", "只");
			pinfo.put("count", "3");
			datas.add(pinfo);
		}
		return datas;
	}
	
	private static Map<String, Object> getdata(){
		List<Map> planData = getMapdata(); 
		List<List<Map>> testDatas = new ArrayList<>();
		for(int i =0 ;i<2;i++) {
			testDatas.add(planData);
		}
		Map<String, Object> map = new HashMap<>();
		Calendar calendar=Calendar.getInstance();
		//设置当前年份
		map.put("year", calendar.get(Calendar.YEAR));
		map.put("list", testDatas);
		return map;
	}
	
	public static void main(String[] args) {
		
		Map<String, Object> planDatas1 = getdata();//测试数据
		Long startTime = System.currentTimeMillis();
		String xmlString = getTableXmlData(planDatas1);
		
		try {  
//            File file = new File("D:/test66.xls");  
            FileOutputStream fos = new FileOutputStream("D:/test66.xls");
            
            fos.write(xmlString.getBytes());  
            fos.close(); 
            
            // 创建输入流，读取Excel  
//            FileInputStream fis = new FileInputStream("D:/test66.xls"); 
//            HSSFWorkbook wb = new HSSFWorkbook(fis);
//            wb.write();
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
//		FileWriter writer;
//	    try {
//	      writer = new FileWriter("D:/test22.xml");
//	      writer.write(xmlString);
//	      writer.flush();
//	      writer.close();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
	    Long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}
}
