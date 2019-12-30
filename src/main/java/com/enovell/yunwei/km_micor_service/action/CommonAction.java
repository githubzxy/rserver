package com.enovell.yunwei.km_micor_service.action;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.service.CommonPageService;
import com.enovell.yunwei.km_micor_service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * kunmingTXD
 * 昆明微服务公用Action
 *
 * @author bili
 * @date 18-11-20
 */
@RestController
@RequestMapping("/commonAction")
public class CommonAction {
    @Resource(name = "commonPageService")
    private CommonPageService service;
    @Resource(name = "userService")
    private UserService userService;

    /**
     * 新增数据
     * 防止上传文件名乱码，需在路径最前加入/zuul，此处为localhost:homeport/zuul/kmms/commonAction/addDoc
     *
     * @param name           数据名称
     * @param fileCols       上传附件name 逗号分割
     * @param collectionName 存储表名
     * @param userId         上传人id
     * @param request        request
     * @return 保存后的数据对象
     */
    @PostMapping("/addDoc")
    public ResultMsg addDoc(@RequestParam(name = "name") String name,
    						@RequestParam(name = "orgSelectName") String orgSelectName,
    						@RequestParam(name = "orgSelectId") String orgSelectId,
                            @RequestParam(name = "fileCols") String fileCols,
                            @RequestParam(name = "collectionName") String collectionName,
                            @RequestParam(name = "userId") String userId,
                            HttpServletRequest request) {
        Document document = new Document();
        Arrays.stream(fileCols.split(",")).forEach(f -> {
            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
            document.put(f, files);
        });
        document.put("name", name);
        document.put("createDate", new Date());
        document.put("createUserId", userId);
//        Map<String, Object> org = userService.getOrgbyUserId(userId);
//        document.put("createOrg", org.get("ORG_ID_"));
//        document.put("createOrgName", org.get("ORG_NAME_"));
        Map<String, Object> user = userService.getUserById(userId);
        document.put("createUserName", user.get("USER_NAME_"));
        document.put("orgId", orgSelectId);
        document.put("userId", userId);
        document.put("orgName", orgSelectName);
        Document res = service.addDocument(document, collectionName);
        return ResultMsg.getSuccess("新增成功", res);
    }

    /**
     * 删除数据
     *
     * @param id             数据id
     * @param collectionName 表名
     * @return 状态返回
     */
    @PostMapping("/removeDoc")
    public ResultMsg removeDoc(@RequestParam("id") String id,
    						   @RequestParam("userId") String userId,
                               @RequestParam("collectionName") String collectionName) {
        List<String> ids = Arrays.asList(id.split(","));
        List<String> userIdList = new ArrayList<String>();
        ids.forEach(s->{
        	 Document res = service.findDocumentById(s, collectionName);
        	 userIdList.add((String) res.get("userId"));
        });
   	 boolean userIdReat =userIdList.stream().allMatch(f->f.equals(userId)==true);
     if(userIdReat==true){
    	 service.removeDocument(ids, collectionName);
    	 return ResultMsg.getSuccess("删除成功");
     }else{
    	 return ResultMsg.getFailure("请选择自己添加的记录删除");
     }
    }

    @PostMapping("/updateDoc")
    public ResultMsg updateDoc(@RequestParam("id") String id,
    						    @RequestParam(name = "name") String name,
    							@RequestParam(name = "orgSelectName") String orgSelectName,
    							@RequestParam(name = "orgSelectId") String orgSelectId,
                               @RequestParam(name = "fileCols") String fileCols,
                               @RequestParam("collectionName") String collectionName,
                               HttpServletRequest request) {
        Document doc = service.findDocumentById(id,collectionName);
        Arrays.stream(fileCols.split(",")).forEach(f->{
            List<Document> files = service.uploadFile(((MultipartHttpServletRequest) request).getFiles(f));
            List<Document> nowFile = doc.get(f,List.class);
            //保存新的上传文件
            nowFile.addAll(files);
            //删除已删除的上传文件
            String delFile = request.getParameter("del-"+f);
            List<String> delFileIds = Arrays.asList(delFile.split(","));
            doc.put(f,nowFile.stream().filter(item-> !delFileIds.contains(item.getString("id"))).collect(Collectors.toList()));
        });
        doc.put("name", name);
        doc.put("orgId", orgSelectId);
        doc.put("orgName", orgSelectName);
        Document res = service.updateDocument(doc, collectionName);
        return ResultMsg.getSuccess("修改成功", res);
    }

    @PostMapping("/findById")
    public ResultMsg findDocById(@RequestParam("id") String id,
                                 @RequestParam("collectionName") String collectionName) {
        Document res = service.findDocumentById(id, collectionName);
        return ResultMsg.getSuccess("查询完成", res);
    }

    @PostMapping("/findAll")
    public GridDto<Document> findAll(@RequestParam String collectionName,
                                     String name,
                                     String startUploadDate,
                                     String endUploadDate,
                                     String auditStatus,
                                     String orgId,
                                     @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Date startDate = null;
        Date endDate = null;
        GridDto<Document> result = new GridDto<>();
        try {
            if (StringUtils.isNotBlank(startUploadDate)) {
                startDate = sdf.parse(startUploadDate);
            }
            if (StringUtils.isNotBlank(endUploadDate)) {
                endDate = sdf.parse(endUploadDate);
            }
        } catch (ParseException e) {
           result.setHasError(true);
           result.setError("时间格式错误");
           return result;
        }


        result.setResults(service.findAllDocumentCount(collectionName, name, startDate, endDate, orgId,auditStatus));
        result.setRows(service.findAllDocument(collectionName, name, startDate, endDate, orgId, start, limit,auditStatus));
        return result;
    }

    @GetMapping("/download")
    public void downLoad(@RequestParam String path, @RequestParam String fileName, HttpServletRequest request, HttpServletResponse response) {
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
    @RequestMapping("/getChildrenByPidAndCurId")
    public List<Map<String,Object>> getChildrenByPidAndCurId( @RequestParam("id") String id,
                                                              @RequestParam(required=false,value="curId") String curId){
        return userService.getChildrenByPidAndCurId(id, curId);
    }
    @RequestMapping("/getCadreAndShopByPidAndCurId")
    public List<Map<String,Object>> getCadreAndShopByPidAndCurId( @RequestParam("id") String id,
    		@RequestParam(required=false,value="curId") String curId){
    	return userService.getCadreAndShop(id, curId);
    }
    @PostMapping("/getWorkShop")
	 public List<Map<String, Object>> getWorkShop(){
		return userService.getWorkShops();
	 }
	 @PostMapping("/getDepart")
	 public List<Map<String, Object>> getDepart(String workShopName){
		return userService.getDeparts(workShopName);
	 }
	 @PostMapping("/getShopAndDepart")
	 public List<Map<String, Object>> getShopAndDepart(@RequestParam("id") String id,
            @RequestParam(required=false,value="curId") String curId){
		 return userService.getShopAndDepart(id, curId);
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
