
/**   
 * 文件名：CopyExcelSheetByJXl.java    
 * @author quyy  
 * 日期：2017年11月2日 下午1:51:07      
 *   
 */

package com.enovell.yunwei.km_micor_service.util.yearMonthPlan;

/**      
 * 项目名称：RINMS2MAIN
 * 类名称：CopyExcelSheetByJxl   
 * 类描述: 使用jxl，复制多个excel文件的sheet页到一个excel文件的多个sheet中
 * 创建人：quyy 
 * 创建时间：2017年11月2日 下午1:51:07 
 *    
 */

public class CopyExcelSheetByJxl {
	/**
	 * 被复制的文件路径名称
	 */
	public static final String FILE_PATH = "filePath";
	/**
	 * 对应的生成复制文件excel的Sheet页的名称
	 */
	public static final String SHEET_NAME = "sheetName";
	
//	public static Boolean getExcelwithSheets(List<Map<String,String>> fromFileDatas,//被复制的excel文件路径（filePath），以及对应的生成的复制文件excel的Sheet页的名称(sheetName)，
//											  String copyToPath,       //生成的复制文件excel路径
//											  int fromFileSheetNum    //复制excel文件的第几个sheet页
//											 ) {
//		 WritableWorkbook wwb = null;
//		 try {
//			wwb = Workbook.createWorkbook(new File(copyToPath));
//			for (int i=0;i<fromFileDatas.size();i++) {
//				Map<String, String> map = fromFileDatas.get(i);
//				copyExcelSheet(map.get(FILE_PATH),map.get(SHEET_NAME),i,wwb,fromFileSheetNum);
//			}
//			wwb.write();
//			wwb.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//	/**
//	 * 
//	 * copyExcelSheet 复制excel的sheet页
//	 * @author quyy
//	 * @param filePath 被复制的excel文件路径
//	 * @param sheetName 生成的复制文件excel的sheet名称
//	 * @param sheetNum 生成的复制文件excel的sheet序号
//	 * @param wwb 生成的复制文件的WritableWorkbook
//	 * @return
//	 */
//	private static void copyExcelSheet(String filePath,String sheetName,int sheetNum,WritableWorkbook wwb,int fromFileSheetNum) {
//		 Workbook wb = null;
//		 try {
//			 wb = Workbook.getWorkbook(new File(filePath));
//			 Sheet sheet = wb.getSheet(fromFileSheetNum);
//			 wwb.importSheet(sheetName,sheetNum,sheet);
//			 wb.close();
//		 }catch(Exception e) {
//			 e.printStackTrace();
//		 }
//	}	
//	
//	/**
//	 * 
//	 * replaceSheet 替换workShopFilePath中的第replaceSheetNum个sheet页为gatherFilePath中的第一个sheet页
//	 * @author quyy
//	 * @param workShopFilePath 车间附件表路径
//	 * @param gatherFilePath 车间汇总后的附件表
//	 * @param tofilePath 替换后的文件存放路径
//	 * @param replaceSheetNum 被替换的sheet页序号
//	 */
//	public static void replaceSheet(String workShopFilePath,String gatherFilePath,String tofilePath,int replaceSheetNum) {
//		Workbook workShopWb = null;
//		WritableWorkbook wwb = null;
//		 try {
//			workShopWb = Workbook.getWorkbook(new File(workShopFilePath));
//			Workbook  gatherWb = Workbook.getWorkbook(new File(gatherFilePath));
//			Sheet gatherFileSheet = gatherWb.getSheet(0);
//			wwb = Workbook.createWorkbook(new File(tofilePath));
//			for(int i= 0;i<12;i++) {
//				Sheet sheet = workShopWb.getSheet(i);
//				if(i == replaceSheetNum) {
//					wwb.importSheet(i+1+"月", i, gatherFileSheet);
//					continue;
//				}
//				wwb.importSheet(i+1+"月", i, sheet);
//			}
//			new File(workShopFilePath).delete();
//			new File(gatherFilePath).delete();
//			workShopWb.close();
//			gatherWb.close();
//			wwb.write();
//			wwb.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 
//	 * getSheet12 生成一个12个空白sheet页的文件
//	 * @author quyy
//	 * @param filePath 文件路径
//	 */
//	public static void getSheet12(String filePath) {
//		WritableWorkbook wwb = null;
//		 try {
//			wwb = Workbook.createWorkbook(new File(filePath));
//			for(int i= 0;i<12;i++) {
//				wwb.createSheet(i+1+"月", i);
//			}
//			wwb.write();
//			wwb.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public static void main(String[] args) {
////		List<Map<String,String>> fromFileDatas = new ArrayList<Map<String,String>>();
////		for(int i = 0; i< 10; i++) {
////			Map<String, String> map = new HashMap<String, String>();
////			map.put(FILE_PATH, "C:\\Users\\quyinyin\\Desktop\\txYear8_1.xls");
////			map.put(SHEET_NAME, "test"+i);
////			fromFileDatas.add(map);
////		}
////		Long startTime = System.currentTimeMillis();
////		getExcelwithSheets(fromFileDatas,"C:\\\\Users\\\\quyinyin\\\\Desktop\\\\temp.xls",0);
////		Long endTime = System.currentTimeMillis();
////		System.out.println((endTime - startTime)+"ms");
////		System.out.println("----------------");
////		replaceSheet("D:\\workShopFile.xls","D:\\gatherFile.xls","D:\\toFile.xls", 1);
////		System.out.println("------------------");
////		getSheet12("D:/test.xls");
////		System.out.println("-------------");
//		getMonthReportMould12("C:\\Users\\quyinyin\\Desktop\\年月报表相关资料\\年月表（普铁、技术支持中心）\\附件1：广州通信段普速铁路年月表v1.1（通用版）.xls", "D:\\test.xls");
//		System.out.println("--------------");
//	}
//	
//	/**
//	 * 将单个sheet复制12份，生成新的excel(包含12个sheet)文件
//	 * getMonthReportMould12  获取包含12个sheet的excel文件
//	 * @param singleMouldPath 文件路径（包含一个sheet）
//	 * @param mouldPath 文件路径（包含12个sheet）
//	 * @author chenshuang
//	 */
//	public static void getMonthReportMould12(String singleMouldPath,String mouldPath) {
//		getMonthReportMould12(singleMouldPath,mouldPath,false);
//	}
//	/**
//	 * 将单个sheet复制12份，生成新的excel(包含12个sheet)文件
//	 * getMonthReportMould12  获取包含12个sheet的excel文件
//	 * @param singleMouldPath 文件路径（包含一个sheet）
//	 * @param mouldPath 文件路径（包含12个sheet）
//	 * @param saveName true：保存原来的sheet名称，false：不保存.
//	 * 普铁月表需保存原sheet名称
//	 * @author chenshuang
//	 */
//	public static void getMonthReportMould12(String singleMouldPath,String mouldPath,boolean saveName) {
//		WritableWorkbook newWorkbook = null;
//		 try {
//			 Workbook w = Workbook.getWorkbook(new File(singleMouldPath));
//			 Sheet sheet= w.getSheet(0);
//			 String sheetName=sheet.getName();
//			 newWorkbook = Workbook.createWorkbook(new File(mouldPath));
//			for(int i= 0;i<12;i++) {
//				newWorkbook.importSheet( saveName?(i+1)+sheetName:(i+1)+"月", i, sheet);
//			}
//			newWorkbook.write();
//			w.close();
//			newWorkbook.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
