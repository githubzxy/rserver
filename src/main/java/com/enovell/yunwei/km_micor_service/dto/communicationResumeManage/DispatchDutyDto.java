package com.enovell.yunwei.km_micor_service.dto.communicationResumeManage;

/**
 * 
 * 项目名称：km_micor_service
 * 类名称：DispatchDutyDto   
 * 类描述: 调度系统-车站值班台
 * 创建人：zhouxingyu
 * 创建时间：2019年6月19日 下午4:14:56
 * 修改人：zhouxingyu 
 * 修改时间：2019年6月19日 下午4:14:56   
 *
 */
public class DispatchDutyDto extends PublicDto{
	private static final long serialVersionUID = -1491666064824574795L;
	private String numbeDigital;//所属数字环序号
	private String numberDispatch;//所属调度操作台序号
	public String getNumbeDigital() {
		return numbeDigital;
	}
	public void setNumbeDigital(String numbeDigital) {
		this.numbeDigital = numbeDigital;
	}
	public String getNumberDispatch() {
		return numberDispatch;
	}
	public void setNumberDispatch(String numberDispatch) {
		this.numberDispatch = numberDispatch;
	}
	 
	
}
