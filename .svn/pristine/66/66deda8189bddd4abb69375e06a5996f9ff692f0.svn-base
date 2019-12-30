package com.enovell.yunwei.km_micor_service.action.technicalManagement.lineMangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.LineNameDto;
import com.enovell.yunwei.km_micor_service.service.technical.lineNameMangement.LineNameMangementService;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：LineMangementAction   
 * 类描述：线别名称管理
 * 创建人：zhouxingyu
 * 创建时间：2019年5月30日 下午2:36:33
 * 修改人：yangsy
 * 修改时间：2019年11月12日 下午2:36:33   
 */
@RestController
@RequestMapping("/lineNameMangementAction")
public class LineMangementAction {
	
	@Resource(name = "lineNameService")
	private LineNameMangementService service;
	
	/**
	 * 无条件获取Oracle线别名称表中的所有数据
	 * @return List<Map<String, String>>
	 */
//	@PostMapping("/getLinesToMap")
//	public List<Map<String, String>> getLinesToMap() {
//		List<String> lineList = service.getLineData();
//		List<Map<String, String>> lineMaps = new ArrayList<>();
//		for (String string : lineList) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("text", string);
//			map.put("value", string);
//			lineMaps.add(map);
//		}
//		return lineMaps;
//	}
	
	/**
	 * 无条件获取Oracle线别名称表中的所有数据
	 * @return List<String>
	 */
	@PostMapping("/getLines")
	public List<String> getLines(){
		return service.getLineData();
	}
	
	/**
	 * 主页查询
	 * @param request
	 * @param name 线别名称
	 * @param desc 备注
	 * @param start 0
	 * @param limit 10
	 * @return
	 */
	@PostMapping(value = "/findAll")
	public GridDto<LineNameDto> findAllInfo(HttpServletRequest request,
			@RequestParam(name = "railLine",required=false) String name,
			@RequestParam(name = "desc",required=false) String desc,
			@RequestParam int start,
			@RequestParam int limit
			) {
		return service.getAllDataBySearch(name,desc,start,limit);
	}
	
	/**新增*/
	@PostMapping(value="/addLine")
	public ResultMsg addReport(
			@RequestParam("railLine")String railLine,
			@RequestParam("desc")String desc
			){

		ResultMsg rm = new ResultMsg();
		try {
			rm = service.addLine(railLine, desc);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setStatus(ResultMsg.FAIL_STATUS);
			rm.setMsg("新增失败！请稍后重试。");
		}
		return rm;
		
	}
	/**
	 * 
	 * findById 根据id查询数据
	 * @param id
	 * @param collectionName
	 * @return
	 */
	 @PostMapping("/findById")
	 public LineNameDto findById(String id){
		 return service.getDataById(id);
	 }
	 
	@PostMapping(value = "/update")
	public ResultMsg updateInfo(HttpServletRequest request,
			@RequestParam(name = "id",required=false) String id,
			@RequestParam(name = "railLineEditDialog",required=false) String railLineEditDialog,
			@RequestParam(name = "desc",required=false) String desc
			
			) {
		ResultMsg rm = new ResultMsg();
		try {
			rm = service.update(id,railLineEditDialog,desc);
		}catch(Exception ex) {
			ex.printStackTrace();
			rm.setStatus(ResultMsg.FAIL_STATUS);
			rm.setMsg("修改失败！");
		}
		return rm;
		  
	}
	
	/**
	 * 删除所选择的线别数据（物理删除）
	 * @param id 以“,”分隔的字符串
	 * @return
	 */
	@PostMapping("/removeDoc")
	public ResultMsg removeDoc(@RequestParam("id") String id){
		List<String> ids = Arrays.asList(id.split(","));
		ids.forEach(s-> service.deleteById(s));
		return ResultMsg.getSuccess("删除成功");
	}

}
