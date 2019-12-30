package com.enovell.yunwei.km_micor_service.action.technicalManagement.machineRoomManage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.MachineRoomDto;
import com.enovell.yunwei.km_micor_service.service.technical.machineRoomManage.MachineRoomManageService;

@RestController
@RequestMapping("/machineRoomManageAction")
public class MachineRoomManageAction {
	
	@Resource(name = "machineRoomManageService")
	private MachineRoomManageService service;
	
	/**
	 * 根据线别名称获取该线别下的机房名称
	 * @param line 线别名称
	 * @return List<String>
	 */
	@PostMapping("/getMachineRoomsByLine")
	public List<String> getMachineRoomsByLine(@RequestParam(name="line") String line) {
		return service.getMachineRoomsByLine(line);
	}
	
	/**
	 * 无条件获取Oracle机房名称表中的所有数据
	 * @return List<String>
	 */
	@PostMapping("/getMachineRooms")
	public List<String> getMachineRooms(){
		return service.getMachineRoomData();
	}
	
	/**
	 * 主页条件查询
	 * @param request
	 * @param machineCode 机房编码
	 * @param machineName 机房名称
	 * @param nameCondition 线别名称
	 * @param remark 备注
	 * @param start 0
	 * @param limit 10
	 * @return
	 */
	@PostMapping(value = "/findAll")
	public GridDto<MachineRoomDto> findAllInfo(HttpServletRequest request,
			@RequestParam(name = "machineCode",required=false) String machineCode,
			@RequestParam(name = "machineName",required=false) String machineName,
			@RequestParam(name = "nameCondition",required=false) String nameCondition,
			@RequestParam(name = "remark",required=false) String remark,
			@RequestParam int start,
			@RequestParam int limit
			) {
		return service.getAllDataBySearch(machineCode,machineName,nameCondition,remark,start,limit);
	}
	
	/**
	 * 新增
	 */
	@PostMapping(value="/addLine")
	public ResultMsg addReport(@RequestBody MachineRoomDto dto){
//        System.err.println(dto);
		ResultMsg rm = new ResultMsg();
		try {
			rm = service.addLine(dto);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setStatus(ResultMsg.FAIL_STATUS);
			rm.setMsg("新增失败！请稍后重试。");
		}
		return rm;
		
	}
	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	@PostMapping("/findById")
	public MachineRoomDto findById(String id){
		return service.getDataById(id);
	}
	 
	@PostMapping(value = "/update")
	public ResultMsg updateInfo(HttpServletRequest request,@RequestBody MachineRoomDto dto){
		ResultMsg rm = new ResultMsg();
		try {
			rm = service.update(dto);
		}catch(Exception ex) {
			ex.printStackTrace();
			rm.setStatus(ResultMsg.FAIL_STATUS);
			rm.setMsg("修改失败！");
		}
		return rm;
		  
	}
	
	/**
	 * 删除机房名称数据（物理删除）
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
