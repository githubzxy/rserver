package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * 项目名称：yearMonthPlan
 * 类名称：YearMonthPlanFilePathUtils   
 * 类描述:  年月报表文件路径工具类
 * 创建人：lidt 
 * 创建时间：2018年12月20日 下午2:54:16
 * 修改人：lidt 
 * 修改时间：2018年12月20日 下午2:54:16   
 *
 */
@Component
public class YearMonthPlanFilePathUtils {

	/**
	 * 时间字符串格式：年月日时分秒毫秒
	 */
	private static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";
	
	/**
	 * 文件分隔符：_
	 */
	private static final String DELIMIT  = "_";
	
	/**
	 * 编制流程文件
	 */
	public static final String COMPILE  = "Compile";
	
	/**
	 * 执行流程文件
	 */
	public static final String EXECUTE  = "Execute";
	
	/**
	 * 高铁年月报表 业务文件存放路径
	 */
	private static String gaoTie;
	
	/**
	 * 普铁年月报表 业务文件存放路径
	 */
	private static String puTie;
	
	/**
	 * 技术支持中心年月报表 业务文件存放路径
	 */
	private static String techCenter;
	
	/**
	 * 高铁年月报表 模板文件存放路径
	 */
	private static String gaoTieTpl;
	
	/**
	 * 普铁年月报表 模板文件存放路径
	 */
	private static String puTieTpl;
	
	/**
	 * 普铁年月报表 模板Xml文件存放路径
	 */
	private static String putieXml;
	
	/**
	 * 技术支持中心年月报表 模板文件存放路径
	 */
	private static String techCenterTpl;
	
	/**
	 * 批量导出生成的压缩文件
	 */
	private static String zipfile;
	
	/**
	 * 批量导出生成的压缩文件路径
	 */
	private static String zipfilePath;
	
	/**
	 * 履历簿
	 */
	private static String deviceRecord;
	/**
	 * 设备与工作内容关系表
	 */
	private static String deviceCheckWorkManage;
	
	@Value("${gaoTie}")
	public void setGaoTie(String gaoTie) {
		YearMonthPlanFilePathUtils.gaoTie = gaoTie;
	}
	
	public static String getGaoTie() {
		return gaoTie;
	}
	
	@Value("${puTie}")
	public void setPuTie(String puTie) {
		YearMonthPlanFilePathUtils.puTie = puTie;
	}
	
	public static String getPuTie() {
		return puTie;
	}
	
	@Value("${techCenter}")
	public void setTechCenter(String techCenter) {
		YearMonthPlanFilePathUtils.techCenter = techCenter;
	}
	
	public static String getTechCenter() {
		return techCenter;
	}
	
	@Value("${gaoTieTpl}")
	public void setGaoTieTpl(String gaoTieTpl) {
		YearMonthPlanFilePathUtils.gaoTieTpl = gaoTieTpl;
	}
	
	public static String getGaoTieTpl() {
		return gaoTieTpl;
	}
	
	@Value("${puTieTpl}")
	public void setPuTieTpl(String puTieTpl) {
		YearMonthPlanFilePathUtils.puTieTpl = puTieTpl;
	}
	
	public static String getPuTieTpl() {
		return puTieTpl;
	}

	@Value("${techCenterTpl}")
	public void setTechCenterTpl(String techCenterTpl) {
		YearMonthPlanFilePathUtils.techCenterTpl = techCenterTpl;
	}
	
	public static String getTechCenterTpl() {
		return techCenterTpl;
	}

	@Value("${putieXml}")
	public void setPutieXml(String putieXml) {
		YearMonthPlanFilePathUtils.putieXml = putieXml;
	}
	
	public static String getPutieXml() {
		return putieXml;
	}

	@Value("${zipfile}")
	public void setZipfile(String zipfile) {
		YearMonthPlanFilePathUtils.zipfile = zipfile;
	}
	
	public static String getZipfile() {
		return zipfile;
	}

	@Value("${zipfilePath}")
	public void setZipfilePath(String zipfilePath) {
		YearMonthPlanFilePathUtils.zipfilePath = zipfilePath;
	}
	
	public static String getZipfilePath() {
		return zipfilePath;
	}
	
	public static String getDeviceRecord() {
		return deviceRecord;
	}

	@Value("${deviceRecord}")
	public void setDeviceRecord(String deviceRecord) {
		YearMonthPlanFilePathUtils.deviceRecord = deviceRecord;
	}

	public static String getDeviceCheckWorkManage() {
		return deviceCheckWorkManage;
	}

	@Value("${deviceCheckWorkManage}")
	public void setDeviceCheckWorkManage(String deviceCheckWorkManage) {
		YearMonthPlanFilePathUtils.deviceCheckWorkManage = deviceCheckWorkManage;
	}
	
	/**    
	 * getExecuteByClass 获取流程类型
	 * @param entityClass 当前业务实现类
	 * @return 流程类型
	 */
	private static <T> String getExecuteByClass(Class<T> entityClass) {
		return entityClass.getSimpleName().contains(EXECUTE) ? EXECUTE : COMPILE;
	}
//============================业务文件处理start==================================
	/**
	 * 
	 * getGaoTie 获取 高铁年月报表 业务文件绝对路径（包含文件名称 + 后缀）
	 * @param orgId 组织机构id
	 * @param entityClass 当前业务实现类
	 * @return opt/extend/zhwg/yearMonthPlanFile/gaoTie/orgId_flowType_yyyyMMddHHmmssSSS.xls
	 */
	public static <T> String getGaoTie(String orgId, Class<T> entityClass) {
		return getAbsolutePath(gaoTie, orgId, getExecuteByClass(entityClass));
	}
	
	/**
	 * 
	 * getPuTie 获取 普铁年月报表  业务文件绝对路径（包含文件名称 + 后缀）
	 * @param orgId 组织机构id
	 * @param entityClass 当前业务实现类
	 * @return opt/extend/zhwg/yearMonthPlanFile/puTie/orgId_flowType_yyyyMMddHHmmssSSS.xls
	 */
	public static <T> String getPuTie(String orgId, Class<T> entityClass) {
		return getAbsolutePath(puTie, orgId, getExecuteByClass(entityClass));
	}
	
	/**
	 * 
	 * getTechCenter 获取 技术支持中心年月报表  业务文件绝对路径（包含文件名称 + 后缀）
	 * @param orgId 组织机构id
	 * @param entityClass 当前业务实现类
	 * @return opt/extend/zhwg/yearMonthPlanFile/techCenter/orgId_flowType_yyyyMMddHHmmssSSS.xls
	 */
	public static <T> String getTechCenter(String orgId, Class<T> entityClass) {
		return getAbsolutePath(techCenter, orgId, getExecuteByClass(entityClass));
	}
	
	/**
	 * 
	 * getTechCenterXML 获取 技术支持中心年月报表  XML业务文件绝对路径（包含文件名称 + 后缀）
	 * @param orgId 组织机构id
	 * @return opt/extend/zhwg/yearMonthPlanFile/puTie/orgId_flowType_yyyyMMddHHmmssSSS.xml
	 */
	public static String getTechCenterXML(String orgId) {
		return getXMLAbsolutePath(puTie, orgId);
	}
//============================业务文件处理end==================================
	
	/**
	 * 
	 * getAbsolutePath 获取文件绝对路径（包含文件名称 + 后缀）
	 * @param fileDirectory 文件目录
	 * @param orgId 组织机构id
	 * @param flowType 流程类型
	 * @return fileDirectory/orgId_flowType_yyyyMMddHHmmssSSS.xls
	 */
	private static String getAbsolutePath(String fileDirectory, String orgId, String flowType) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String absolutePath = fileDirectory + orgId + DELIMIT + flowType
				+ DELIMIT + sdf.format(new Date()) + ".xls";
		return absolutePath;
	}
	
	/**
	 * 
	 * getXMLAbsolutePath 获取XML文件绝对路径（包含文件名称 + 后缀）
	 * @param fileDirectory 文件目录
	 * @param orgId 组织机构id
	 * @return fileDirectory/orgId_yyyyMMddHHmmssSSS.xml
	 */
	private static String getXMLAbsolutePath(String fileDirectory, String orgId) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String absolutePath = fileDirectory + orgId + DELIMIT
				+ sdf.format(new Date()) + ".xml";
		return absolutePath;
	}

}
