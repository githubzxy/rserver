package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellAddress;

/**  
* 创建时间：2017年9月11日 下午1:44:50  
* 项目名称：RINMS2MAIN  
* 文件名称：CopyFileUtil.java  
* 类说明：复制文件公用类
*/
public class YearMonthCopyFileUtil {
	
	/**    
	 * copyFile 将文件fromFile 的内容复制到  toFile文件中
     * 复制的可以是Excel等多种格式
	 *
	 * @param fromFilePath 被复制的文件绝对路径，包含文件名
	 * @param toFilePath 复制后的文件绝对路径，包含文件名
	 * @return 复制后的文件名
	 */
	public static String copyFile(String fromFilePath,String toFilePath) {
		File fromFile = new File(fromFilePath);
		File toFile = new File(toFilePath);
		FileInputStream ins = null;
		FileOutputStream out = null;
		try {
			ins = new FileInputStream(fromFile);
			out = new FileOutputStream(toFile);
			byte[] b = new byte[1024 * 5];
			int n = 0;
			while ((n = ins.read(b)) != -1) {
				out.write(b, 0, n);
			}
			// 刷新此缓冲的输出流
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("复制文件出错！");
		} finally {
			// 关闭流
			try {
				if (ins != null)
					ins.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return toFile.getName();
	}
	
	/**    
	 * updateCopyFileGaoTie 将工区、车间名字和创建时间填入已复制的Excel表格指定区域
	 * 高铁年月报表--A4单元格
	 * 普铁年月报表--A2单元格
	 *
	 * @author lidt
	 * @date 2018年12月12日 下午4:25:01 
	 * @param workAreaName 填报人组织机构名(工区)
	 * @param workShopName 填报人父组织机构名(车间)
	 * @param copyFilePath 复制后的文件路径
	 * @param cell 需要改变的单元格
	 */
	@SuppressWarnings("resource")
	public static void updateCopyFileGaoTie(String workAreaName, String workShopName, String copyFilePath, String cell) {
		// 获取填报时间(年月日)
		Calendar createTime = Calendar.getInstance();
		int year = createTime.get(Calendar.YEAR);
		int month = createTime.get(Calendar.MONTH) + 1;
		String fillInTime = year + "年" + month + "月26日";
		String insertRow = workShopName + "      " + workAreaName + "      工长：           车间主任：            填报时间：" + fillInTime;
		
		try {
			FileInputStream inputStream = new FileInputStream(copyFilePath);
			// 拿到文件转化为javapoi可操纵类型
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			// 关闭输入流
			inputStream.close();
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 获取对应行和列下标
			CellAddress address = new CellAddress(cell);
			// 得到行
			HSSFRow row = sheet.getRow(address.getRow());
			// 得到列
			HSSFCell column = row.getCell(address.getColumn());
			// 改变数据
			column.setCellValue(insertRow);
			// 写数据到该文件
			FileOutputStream outPutStream = new FileOutputStream(copyFilePath);
			workbook.write(outPutStream);
			// 清除缓存
			outPutStream.flush();
			// 关闭输出流
			outPutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**    
	 * updateCopyFilePuTie 将工区、车间名字和创建时间填入已复制的Excel表格指定区域
	 * 普铁年月报表--A2单元格
	 *
	 * @author lidt
	 * @date 2018年12月12日 下午4:25:01 
	 * @param workAreaName 填报人组织机构名(工区)
	 * @param workShopName 填报人父组织机构名(车间)
	 * @param copyFilePath 复制后的文件路径
	 * @param sheetIndex sheet页序号
	 */
	@SuppressWarnings("resource")
	public static void updateCopyFilePuTie(String workAreaName, String workShopName, String copyFilePath, int sheetIndex) {
		// 获取填报时间(年月日)
		Calendar createTime = Calendar.getInstance();
		int year = createTime.get(Calendar.YEAR);
		String fillInTime = year + "年" + (sheetIndex + 1) + "月26日";
		String insertRow = workShopName + "      " + workAreaName + "      工长：           车间主任：            填报时间：" + fillInTime;
		
		try {
			FileInputStream inputStream = new FileInputStream(copyFilePath);
			// 拿到文件转化为javapoi可操纵类型
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			// 关闭输入流
			inputStream.close();
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			// 获取对应行和列下标
			CellAddress address = new CellAddress("A2");
			// 得到行
			HSSFRow row = sheet.getRow(address.getRow());
			// 得到列
			HSSFCell column = row.getCell(address.getColumn());
			// 改变数据
			column.setCellValue(insertRow);
			// 写数据到该文件
			FileOutputStream outPutStream = new FileOutputStream(copyFilePath);
			workbook.write(outPutStream);
			// 清除缓存
			outPutStream.flush();
			// 关闭输出流
			outPutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**    
	 * updateCopyFileTechCenter 将工区、车间名字和创建时间填入已复制的Excel表格指定区域
	 * 技术支持中心年月报表--月表--AC3单元格
	 *                年表--O3单元格
	 * @author lidt
	 * @date 2018年12月12日 下午4:25:01 
	 * @param copyFilePath 复制后的文件路径
	 * @param sheetIndex sheet页序号
	 * @param cell 需要改变的单元格
	 */
	@SuppressWarnings("resource")
	public static void updateCopyFileTechCenter(String copyFilePath, int sheetIndex, String cell) {
		// 获取填报时间(年月日)
		Calendar createTime = Calendar.getInstance();
		int year = createTime.get(Calendar.YEAR);
		int month = createTime.get(Calendar.MONTH) + 1;
		String fillInTime = year + "年" + month + "月26日";
		
		try {
			FileInputStream inputStream = new FileInputStream(copyFilePath);
			// 拿到文件转化为javapoi可操纵类型
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			// 关闭输入流
			inputStream.close();
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			// 获取对应行和列下标
			CellAddress address = new CellAddress(cell);
			// 得到行
			HSSFRow row = sheet.getRow(address.getRow());
			// 得到列
			HSSFCell column = row.getCell(address.getColumn());
			// 改变数据
			column.setCellValue(fillInTime);
			// 写数据到该文件
			FileOutputStream outPutStream = new FileOutputStream(copyFilePath);
			workbook.write(outPutStream);
			// 清除缓存
			outPutStream.flush();
			// 关闭输出流
			outPutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
