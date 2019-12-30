package com.enovell.yunwei.km_micor_service.service.yearMonthPlan;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.YearMonthReportAttachPuTie;
import com.enovell.yunwei.km_micor_service.dto.yearMonthPlan.dto.YearMonthWorkShopDto;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.YearMonthPlanFilePathUtils;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.attach.ZipToFileUtil;
import com.enovell.yunwei.km_micor_service.util.yearMonthPlan.attach.tozip.FileUtil;

/**
* @Title: YearMonthZipToFileServiceImpl.java 
* @Package com.enovell.yunwei.yearMonthReport.service.impl 
* @date 2017年11月2日 下午3:21:07 
* @author luoyan  
*/

@Transactional
@Service("yearMonthPutieZipToFileService")
public class YearMonthPutieZipToFileServiceImpl implements YearMonthPutieZipToFileService  {
	
	@Resource(name = "yearMonthPuTieWSService")
	private YearMonthPuTieWSService yearMonthWorkShopService;
	
	@Override
	public ResultMsg zipToFileSegment(String ids, String attachPath){
		List<YearMonthWorkShopDto> dtos = yearMonthWorkShopService.getExcelsByIdAndAttachPutie(ids, attachPath);
		if(CollectionUtils.isEmpty(dtos)) {
			return ResultMsg.getFailure("没有报表数据可以导出！");
	    }
		return ResultMsg.getSuccess("导出成功！",zipToFile(ids, attachPath, dtos));
	}
	
	private String zipToFile(String ids, String attachPath,List<YearMonthWorkShopDto> dtos){
		// 1.创建压缩文件和被压缩文件夹，并删除已存在的
		String targetZip = ZipToFileUtil.createZipFile();
		try {
			// 2.获取所选车间的Excel文件复制并放入被压缩文件夹
			for (YearMonthWorkShopDto d : dtos) {
				dealWithFile(YearMonthPlanFilePathUtils.getZipfilePath(), d, attachPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3.压缩文件，并删除被压缩文件夹
		ZipToFileUtil.zipFileAndDel(targetZip);
		return targetZip;
	}
	
	/**
	 * 取出Excel并复制文件进入被压缩文件夹
	 * dealWithCopyFile 
	 * @param targetUrl 文件夹路径
	 * @param d 数据源
	 * @param attachPath 附表类型
	 * @return
	 * @throws IOException
	 */
	private void dealWithFile(String targetUrl, YearMonthWorkShopDto d,String attachPath) throws IOException {
		String sourcePath = "";
		switch (attachPath) {
		case YearMonthReportAttachPuTie.ATTACH_PATH8_1:
			sourcePath = d.getAttachPath8_1();
			break;
		case YearMonthReportAttachPuTie.ATTACH_PATH8_2:
			sourcePath = d.getAttachPath8_2();
			break;
		case YearMonthReportAttachPuTie.ATTACH_PATH8_3:
			sourcePath = d.getAttachPath8_3();
			break;
		case YearMonthReportAttachPuTie.ATTACH_PATH8_4:
			sourcePath = d.getAttachPath8_4();
			break;
		case YearMonthReportAttachPuTie.ATTACH_EXECUTE8_1:
			sourcePath = d.getAttachPathExecute8_1();
			break;
		case YearMonthReportAttachPuTie.ATTACH_EXECUTE8_2:
			sourcePath = d.getAttachPathExecute8_2();
			break;
		case YearMonthReportAttachPuTie.ATTACH_EXECUTE8_3:
			sourcePath = d.getAttachPathExecute8_3();
			break;
		case YearMonthReportAttachPuTie.ATTACH_EXECUTE8_4:
			sourcePath = d.getAttachPathExecute8_4();
			break;

		}
		
		//如果车间文件不为空，复制车间文件
		if(StringUtils.isNotBlank(sourcePath)){
			//文件以车间+年份命名
			File targetFile = new File(targetUrl + "/" + d.getOrgName()+"_"+d.getYear()+".xls");
			File sourceFile = new File(YearMonthPlanFilePathUtils.getPuTie() + sourcePath);
			FileUtil.copyFile(sourceFile, targetFile);
		}
		
		//工区文件
		List<YearMonthWorkShopDto> workAreaDtos = d.getChildren(); 
		//如果工区文件不为空，复制工区文件
		if(CollectionUtils.isEmpty(workAreaDtos)){
			return;
		}
		targetUrl += "/" + d.getOrgName()+"_"+d.getYear();
		File targetFloder = new File(targetUrl);
		
		targetFloder.mkdirs();
		for (YearMonthWorkShopDto wa : workAreaDtos) {
			dealWithFile(targetUrl, wa,attachPath);
		}
	}

}
