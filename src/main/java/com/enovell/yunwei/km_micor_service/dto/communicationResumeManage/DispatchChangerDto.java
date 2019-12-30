package com.enovell.yunwei.km_micor_service.dto.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.PublicDto;
/**
 * 
 * 项目名称：km_micor_service
 * 类名称：DispatchChangerDto   
 * 类描述: 调度系统-调度交换机 
 * 创建人：zhouxingyu
 * 创建时间：2019年6月19日 下午4:13:37
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月19日 下午4:13:37   
 *
 */
public class DispatchChangerDto extends PublicDto {
	private static final long serialVersionUID = -8777937855908689684L;
	private String userNumber;//用户数
	private String portConf;//2M端口配置容量
	private String numOfPortUsed;//2M端口实际使用数量
	private String numOfDispatch;//调度台数量
	private String numOfPhone;//电话分机数量
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getPortConf() {
		return portConf;
	}
	public void setPortConf(String portConf) {
		this.portConf = portConf;
	}
	public String getNumOfPortUsed() {
		return numOfPortUsed;
	}
	public void setNumOfPortUsed(String numOfPortUsed) {
		this.numOfPortUsed = numOfPortUsed;
	}
	public String getNumOfDispatch() {
		return numOfDispatch;
	}
	public void setNumOfDispatch(String numOfDispatch) {
		this.numOfDispatch = numOfDispatch;
	}
	public String getNumOfPhone() {
		return numOfPhone;
	}
	public void setNumOfPhone(String numOfPhone) {
		this.numOfPhone = numOfPhone;
	}
	
}
