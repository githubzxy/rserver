package com.enovell.yunwei.km_micor_service.service.technical.machineRoomManage;

import java.util.List;

import com.enovell.yunwei.km_micor_service.dto.GridDto;
import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import com.enovell.yunwei.km_micor_service.dto.technicalManagement.MachineRoomDto;
/**
 * 
 * @ProjectName：RINMS2MAIN
 * @ClassName：MachineRoomManageService  
 * @Description: 机房名称Service
 * @date：2017年11月1日 下午2:25:58
 * @modifier：
 * @ModifyTime：2019年11月13日 下午2:25:58   
 *
 */
public interface MachineRoomManageService {
	
	/**
	 * 根据线别名称获取机房名称数据（用于Suggest下拉选择）
	 * @param line
	 * @return
	 */
	List<String> getMachineRoomsByLine(String line);
	
	/**
	 * 获取机房数据（用于Suggest下拉选择）
	 * @return
	 */
	List<String> getMachineRoomData();

	/**
	 * 
	 * @Title: getAllDataBySearch  
	 * @Description: 	通过条件查询数据
	 * @param pid		父类id
	 * @throws 
	 */
	GridDto<MachineRoomDto> getAllDataBySearch(String machineCode, String machineName,String name,String remark,int start, int limit);
	
	/**
	 * 
	 * @Title: addLine
	 * @Description: 	      新增线别
	 * @return ResultMsg (返回类型 )
	 * @throws 
	 */
	ResultMsg addLine(MachineRoomDto dto) throws Exception;
	
	/**
	 * 
	 * @Title: getDataById
	 * @Description:  		根据id查询线别
	 * @param reportId		业务报表id
	 * @throws 
	 */
	MachineRoomDto getDataById(String id);
	
	/**
	 * 修改
	 */
	ResultMsg update(MachineRoomDto dto);
	
	/**
	 * 
	 * @Title: delete
	 * @Description:    删除线别
	 */
	int deleteById(String id);
	
}
