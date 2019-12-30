package com.enovell.yunwei.km_micor_service.dto.communicationResumeManage;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：DispatchStationChangerDto   
 * 类描述:  调度系统-车站交换机
 * 创建人：zhouxingyu
 * 创建时间：2019年6月19日 下午4:18:00
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月19日 下午4:18:00   
 *
 */
public class DispatchStationChangerDto extends PublicDto{
	private static final long serialVersionUID = -7727255805195417458L;
	private String userNumber;//用户数
	private String numOfTrunkConf;//2M端口配置容量*
	private String numOfTrunkUsed;//2M端口实际使用数量*
	private String numOfUserConf;//值班台数量*
	private String numOfUserUsed;//电话分机数量*
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getNumOfTrunkConf() {
		return numOfTrunkConf;
	}
	public void setNumOfTrunkConf(String numOfTrunkConf) {
		this.numOfTrunkConf = numOfTrunkConf;
	}
	public String getNumOfTrunkUsed() {
		return numOfTrunkUsed;
	}
	public void setNumOfTrunkUsed(String numOfTrunkUsed) {
		this.numOfTrunkUsed = numOfTrunkUsed;
	}
	public String getNumOfUserConf() {
		return numOfUserConf;
	}
	public void setNumOfUserConf(String numOfUserConf) {
		this.numOfUserConf = numOfUserConf;
	}
	public String getNumOfUserUsed() {
		return numOfUserUsed;
	}
	public void setNumOfUserUsed(String numOfUserUsed) {
		this.numOfUserUsed = numOfUserUsed;
	}
	
}
