package com.enovell.yunwei.km_micor_service.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 项目名称：guangtieYearMonth
 * 类名称：JsonUtil   
 * 类描述 : Json工具类  中文容易出乱码，一般不使用
 * 创建人：张思红 
 * 创建时间：2014-9-25 下午4:27:33
 * 修改人：张思红 
 * 修改时间：2014-9-25 下午4:27:33   
 *
 */
public class JsonUtil {

	/**
	 * yyyy-MM-dd HH:mm:ss  日期和时间
	 */
	public static final String DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd 日期
	 */
	public static final String DATE = "yyyy-MM-dd";
	
	/**
	 * 获取当天日期的前一天的日期
	 * @return
	 */
	public static String getSystemBeforeDate(){
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DATE, -1);
		Date date = ca.getTime();
		return javaToJsonAsStringFormatToDATE(date);
	}
	
	public static String getSystemDate(){
		return javaToJsonAsString(new Date());
	}
	
	public static String getSystemDateToYearMonthDay(Object obj){
		return javaToJsonAsString(obj, DATE);
	}
	
	public static String javaToJsonAsStringFormatToDATE(Object obj) {
		return javaToJsonAsString(obj, DATE);
	}
	
	public static String javaToJsonAsString(Object obj) {
		return javaToJsonAsString(obj, DATE_AND_TIME);
	}
	/**
	 * 
	 * javaToJsonAsString java对象转json对象（java数组、集合转json数组）
	 * @param obj 待转换java对象（待转换java数组、集合）
	 * @param DateFormate 日期格式
	 * @return json对象的字符串（json数组的字符串）
	 */
	public static String javaToJsonAsString(Object obj, String DateFormate) {
		
		String jsonAsString = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			// 格式化日期
			objectMapper.setDateFormat(new SimpleDateFormat(DateFormate));
			// 将java转换为json对象的字符串
			jsonAsString = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonAsString;
	}
	
	
	public static <T>T jsonToJavaObj(String jsonStr, Class<T> classType) {
		
		return jsonToJavaObj(jsonStr, classType, DATE_AND_TIME);
	}
	/**
	 * 
	 * jsonToJavaObj json转java对象（json数组转java数组、集合）
	 * @param jsonStr json对象字符串（json数组字符串）
	 * @param classType 类的类型（需要转换的类）
	 * 	@param DateFormate 日期格式
	 * @return java对象（java数组、集合）<br />
	 * 	@see 例子：<br/>
	 * json对象转java对象<br/>
	 * 	String str = "{\"id\":\"1\",\"status\":41}";<br/>
	 * InnerRequestBase base = jsonToJavaObj(str, InnerRequestBase.class);<br/><br/>
	 * 
	 * json数组转java数组、集合<br/>
	 * String str = "[{\"id\":\"1\",\"status\":41},{\"id\":\"2\",\"status\":42}]";<br/>
	 * InnerRequestBase[] base = jsonToJavaObj(str, InnerRequestBase[].class);<br/>
	 */
	public static <T>T jsonToJavaObj(String jsonStr, Class<T> classType, String DateFormate) {
		
		ObjectMapper objectMapper = null;
		T obj = null;
		try {
			objectMapper = new ObjectMapper();
			// 允许特殊字符：如制表符，反斜杠等等
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			objectMapper.setDateFormat(new SimpleDateFormat(DateFormate));
			obj = objectMapper.readValue(jsonStr, classType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static void main(String[] args) {
		
//		String str = "{\"id\":\"1\",\"status\":41}";
//		InnerRequestBase base = jsonToJavaObj(str, InnerRequestBase.class);
//		System.out.println(base);
		
//		String str = "[{\"id\":\"1\",\"status\":41},{\"id\":\"2\",\"status\":42}]";
//		InnerRequestBase[] base = jsonToJavaObj(str, InnerRequestBase[].class);
//		System.out.println(base[0]);
//		System.out.println(base[1]);
	}
}
