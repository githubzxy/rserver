package com.enovell.yunwei.km_micor_service.dto.communicationResumeManage;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：DispatchTrunkDto   
 * 类描述:  调度系统-干线调度
 * 创建人：zhouxingyu
 * 创建时间：2019年6月19日 下午4:19:01
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月19日 下午4:19:01   
 *
 */
public class DispatchTrunkDto extends PublicDto{
	private static final long serialVersionUID = -4956381892508251212L;
	private String numOfTrunkConf;//中继线配置数量
	private String numOfTrunkUsed;//中继线使用数量
	private String numOfUserConf;//用户线配置数量
	private String numOfUserUsed;//用户线使用数量
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
