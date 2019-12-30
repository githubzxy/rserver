package com.enovell.yunwei.km_micor_service.action.attachFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.UploadFileDto;

/**
 * 
 * 项目名称：std
 * 类名称：AttachFileAction   
 * 类描述:  附件处理 action
 * 创建人：lidt 
 * 创建时间：2017年11月13日 下午5:23:03
 * 修改人：lidt 
 * 修改时间：2017年11月13日 下午5:23:03   
 *
 */
@Controller
public class AttachFileAction {
	/**
	 * 服务器文件路径
	 */
	@Value("${filePath}")
	private String uploadPath;
	@Value("${tempPath}")
	private String tempPath;
	/**
	 * 
	 * upload 上传文件
	 *
	 * @author lidt
	 * @date 2017年11月14日 上午10:37:31 
	 * @param request
	 * @return UploadFileDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/atachFile/upload", method = RequestMethod.POST)
	@ResponseBody
	public UploadFileDto upload(@RequestParam("Filedata") MultipartFile file,
			HttpServletRequest request) throws Exception {
		// 根据名字找到对应的文件,这里文件名和文件成键值对关系
		String uploadFileName = file.getOriginalFilename();
		String[] tempNames = uploadFileName.split("\\.");// 分割上传文件名和后缀名
		// 将后缀名进行小写处理
		String postfix = tempNames[tempNames.length - 1].toLowerCase();
		UploadFileDto dto = new UploadFileDto();
		String newName = String.valueOf((new Date()).getTime());
		if (tempNames.length != 0) {
			newName += "." + postfix;
		}
		String pathAndName = uploadPath + "/" + newName; // 需要上传的目录
		dto.setName(uploadFileName);
		dto.setPath(pathAndName);

		File localFile = new File(pathAndName); // 要将文件写至这个路径下的文件
		try {
			file.transferTo(localFile); // 利用SpringMVC自带的读写文件方法
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;

	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/atachFile/download")
	public void download(
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("name");
		String path = request.getParameter("path");
		File fileLoad = new File(path);
		try {
			fileName = urlEncoder(request, fileName);

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("binary/octet-stream");

			response.setHeader("Content-disposition", "attachment; fileName = " + fileName);
			FileInputStream in = new FileInputStream(fileLoad);
			OutputStream out = response.getOutputStream();
			byte[] buffer = new byte[2048];
			int n = -1;
			while ((n = in.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
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
				fileName = new String(fileName.substring(fileName.lastIndexOf("/") + 1).getBytes("UTF-8"),"ISO8859-1");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 检查分片上传的临时文件数量
	 * @param fileName 文件名称
	 * @param size 文件大小
	 * @return 临时文件数量
	 */
	@RequestMapping(value = "/atachFile/checkFile", method = RequestMethod.POST)
	@ResponseBody
	public Object checkFile(@RequestParam("name") String fileName,@RequestParam("size") Integer size){
		try {
			fileName = URLEncoder.encode(fileName,"UTF-8");
			File tempDir = new File(tempPath);
			if(!tempDir.exists()){
				tempDir.mkdir();
			}
			String finalFileName = fileName;
			String[] files = tempDir.list((dir, name)->{
				return name.startsWith(finalFileName+"_"+size);
			});
			if(ArrayUtils.isEmpty(files)){
				return 0;
			}else{
				return files.length;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
	}

    /**
     * 上传分片文件
     * @param status 分片上传的起始和结束状态
     * @param name 分片文件拆分前的文件名
     * @param size 分片文件拆分前的大小
     * @param file 分片文件的上传文件名
     * @return
     */
	@RequestMapping(value = "/atachFile/uploadSlice")
	@ResponseBody
	public UploadFileDto uploadSlice(String status,String name,String size,@RequestParam("file") MultipartFile file){
        try {
            //保存文件
            File tempFile = new File(tempPath+"/"+URLEncoder.encode(file.getOriginalFilename(),"UTF-8"));
            file.transferTo(tempFile);
            //分片上传完成后，整合文件
            if(status.equals("end")){
                String tempName = URLEncoder.encode(name,"UTF-8")+"_"+size;
                UploadFileDto dto = mergeDocument(name,tempName);
                return dto;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

    /**
     * 合并文件
     * @param name 合并的文件名
     * @param tempName 临时文件名开始字符串
     */
    private UploadFileDto mergeDocument(String name, String tempName) throws UnsupportedEncodingException {
        //查找临时文件目录下需要合并的文件
        File tempFilePath = new File(tempPath);
        File[] tempFiles = tempFilePath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(tempName);
            }
        });
        try {
            Arrays.sort(tempFiles,(f1,f2)->{
                Integer fn1 = Integer.parseInt(f1.getName().substring(f1.getName().lastIndexOf("_")+1));
                Integer fn2 = Integer.parseInt(f2.getName().substring(f2.getName().lastIndexOf("_")+1));
                if(fn1>fn2){
                    return 1;
                }else{
                    return -1;
                }
            });
            //创建合并后的正式文件
            // 根据名字找到对应的文件,这里文件名和文件成键值对关系
            String[] tempNames = name.split("\\.");// 分割上传文件名和后缀名
            // 将后缀名进行小写处理
            String postfix = tempNames[tempNames.length - 1].toLowerCase();
            UploadFileDto dto = new UploadFileDto();
            String newName = String.valueOf((new Date()).getTime());
            if (tempNames.length != 0) {
                newName += "." + postfix;
            }
            String pathAndName = uploadPath + "/" + newName; // 需要上传的目录
            dto.setName(name);
            dto.setPath(pathAndName);
            //写入到正式文件
            RandomAccessFile localFile = new RandomAccessFile(pathAndName,"rw");
            for(int i=0;i<tempFiles.length;i++){
                RandomAccessFile tempFile = new RandomAccessFile(tempFiles[i],"rw");
                int length = (int) tempFile.length();
                byte[] b = new byte[length];
                localFile.write(b);
                tempFile.close();
            }
            localFile.close();
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //删除临时文件
            for(int i = 0; i<tempFiles.length; i++){
                tempFiles[i].delete();
            }
        }
        return null;
    }
}
