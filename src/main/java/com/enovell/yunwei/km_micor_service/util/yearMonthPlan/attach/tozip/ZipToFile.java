package com.enovell.yunwei.km_micor_service.util.yearMonthPlan.attach.tozip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * 项目名称：RINMS2MAIN
 * 类名称：ZipToFile   
 * 类描述:  压缩文件写入
 * 创建人：lidt 
 * 创建时间：2016-12-16 下午2:12:13
 * 修改人：lidt 
 * 修改时间：2016-12-16 下午2:12:13   
 *
 */
public class ZipToFile {

	public static final int BUFFER = 1024;// 缓存大小

	/**
	 * zip压缩功能. 压缩baseDir(文件夹目录)下所有文件，包括子目录
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void zipFile(String baseDir, String fileName) {
		ZipOutputStream zos = null;
		InputStream is = null;
		try {
			List fileList = getSubFiles(new File(baseDir));
			zos = new ZipOutputStream(new FileOutputStream(fileName));
			zos.setEncoding("GBK");
			ZipEntry ze = null;
			byte[] buf = new byte[BUFFER];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
					zos.write(buf, 0, readLen);
				}

			}
			is.close();
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List getSubFiles(File baseDir) {
		List ret = new ArrayList();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}


}
