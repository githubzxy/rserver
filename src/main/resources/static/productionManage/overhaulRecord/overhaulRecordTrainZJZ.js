/**
 * 高铁防灾通信设备日常维护记录新增
 */
define('kmms/productionManage/overhaulRecord/overhaulRecordTrainZJZ',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var overhaulRecordTrainZJZ = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
//        	console.log(_self.get('line'));
//        	console.log(_self.get('userId'));
        },
        bindUI : function(){
            var _self = this;
            
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(e){
                        var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
                    }
                },{
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                        if(this.onCancel() !== false){
                            this.close();
                        }
                    }
                }
            ];
            _self.set('buttons',buttons);
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var form = new FormContainer({
                id : 'overhaulRecordAddForm',
                elStyle:{overflowY:'scroll',height:'430px'},
                content :'<table  style="width:100%;table-layout:fixed" border="1" bgcolor="#cccccc" align="center" class="tableLayout"><tbody>'+	
	                '<tr class="thTdPadding" >'+
	            	'<input type="text" style="width:95.5%;" placeholder="当设备状态为不正常时请在备注中说明原因" readonly/>'+
			       '</tr>'+
                	'<tr class="thTdPadding" style="height:40px">'+
			       '<th >设备名称</th>'+
			       '<th style="width:30%">检修内 容'+
				   '</th>'+
				   '<th style="width:20%">设备状态'+
			       '</th>'+
			       '<th style="width:30%">备注'+
				   '</td>'+
			       '</tr>'+
//--------------------------第一个设备-----------------------------------------
			       '<tr class="thTdPadding">'+
			       '<th rowspan="5">传输、接入网设备</th>'+
				   '<th style="width:98%;" >1.电源有无异常告警'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_first_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_first_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_first_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2.设备外部清扫检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_first_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_first_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_first_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、防尘网清扫检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_first_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_first_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_first_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、机柜指示灯观察</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_first_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_first_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_first_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">5、台帐资料核对检查，电路标记核对、修改</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_first_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_first_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_first_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
//	---------------------------第二个设备------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="6">高频开关电源</th>'+
				   '<th style="width:98%;" >1、有无异常告警'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_second_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_second_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_second_first_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、设备外部清扫检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_second_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_second_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_second_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、两路电转换试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_second_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_second_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_second_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、停电试验（含电池放电试验）</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_second_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_second_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_second_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">5、指示灯检查试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_second_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_second_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_second_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">6、各电源连接端子、标识检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_second_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_second_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_second_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
	//-----------------------------------第三个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">数据网设备</th>'+
				   '<th style="width:98%;" >1、设备外部清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_third_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_third_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_third_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、防尘滤网的清洁</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_third_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_third_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_third_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">3、附属设备及缆线、标签检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_third_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_third_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_third_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				    //-----------------------------------第四个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="5">车站数字调度交换设备</th>'+
				   '<th style="width:98%;" >1、设备外部清扫及配线检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_fourth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_fourth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、附属设备及连线检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fourth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_fourth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、交换机风扇运行检查确认</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fourth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fourth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、检查分系统每一块板的工作指示灯是否异常</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fourth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fourth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">5、将设备异常信息反馈至GSM-R维护中心</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fourth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fourth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第五个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="7">车站数字调度操作台</th>'+
				   '<th style="width:98%;" >1、访问用户使用情况，对用户反应的信息进行重点处理'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_fifth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_fifth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、操作台接线整理、麦克风及连线检查、表面清洁、更换不清晰用户标签</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_fifth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、设备状态、按键灵敏度、时间跟踪状态等检查、调整</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fifth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、站间行车电话备用通道（实回线）呼叫通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fifth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">5、调度业务、区间业务、站间业务、站场业务呼叫通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fifth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">6、应急分机呼入、呼出通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifth_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fifth_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">7、调度应急分机通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifth_seventh_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifth_seventh_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fifth_seventh_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第六个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">专用电话分机</th>'+
				   '<th style="width:98%;" >1.设备外部清扫及配线检查查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_sixth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_sixth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_sixth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2.通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_sixth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_sixth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_sixth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3.电池电压测量</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_sixth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_sixth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_sixth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第七个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">综合引入柜</th>'+
				   '<th style="width:98%;" >1、设备清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_seventh_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_seventh_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_seventh_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、配线端子清扫、检查，电缆及配线整理，根据应用变更情况及时对标识进行调整、更换</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_seventh_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_seventh_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_seventh_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、分线盒、卡模、尾纤标识、光缆标识检查核对，2M头、尾纤、光缆引入检查。2M头、尾纤头紧固，运用情况在线检查，标识、端口台帐检查核对。</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_seventh_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_seventh_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_seventh_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第八个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="7">450M无线列调设备</th>'+
				   '<th style="width:98%;" >1、访问用户运用情况'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_eighth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_eighth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、车站无线设备检查、清扫，检修标签无损坏、检修日期在合格期内、标识无损坏，设备外观无损坏，表面清洁</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_eighth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、控制盒检查，功能按键、PTT键无卡滞、MIC送话清楚、听筒声音清晰、扬声器声音清晰</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_eighth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、通话试验，上行、下行、站内呼叫通话试验，同频呼入、异频呼入通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_eighth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">5、车次号接收解码器检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_eighth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">6、监听上、下行车次车机联控呼叫通话情况</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighth_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_eighth_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">7、车站无线列调便携台外观、天线、充电器检查，台账数量核对、通话功能试验。</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighth_seventh_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighth_seventh_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_eighth_seventh_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第九个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">GSM-R无线网设备（基站）</th>'+
				   '<th style="width:98%;" >1、设备巡视、查看设备运行状态'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_ninth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_ninth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_ninth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、接头、连接线检查、配线端子清扫、检查，配线整理，根据应用变更情况及时对标识进行调整、更换</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_ninth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_ninth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_ninth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">3、风扇检查、清理防尘网；设备外部清扫</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_ninth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_ninth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_ninth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">4、基本业务测试（移动-移动、移动-固定、固定-移动、短号码、功能号码测试）</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_ninth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_ninth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_ninth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="6">铁塔或屋顶天线</th>'+
				   '<th style="width:98%;" >1、检查杆塔的稳固性，无裂纹、无晃动及倾斜'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_tenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_tenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_tenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查周边地质、塔底水泥变化，无开裂、塌陷、沉降</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_tenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_tenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_tenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查塔底螺栓、塔体铁靴，无脱落、缺失、严重锈蚀</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_tenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_tenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_tenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、检查杆塔拉线，无松脱、锈蚀、拉线上无攀藤植物</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_tenth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_tenth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_tenth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">5、检查避雷针稳固情况，无扭曲、弯曲、变形、晃动</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_tenth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_tenth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_tenth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">6、检查天馈线的捆扎情况，无松脱</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_tenth_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_tenth_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_tenth_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十一个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">通信雷电综合防护设施</th>'+
				   '<th style="width:98%;" >1、检查浪涌保护器的失效指示和断路开关状态'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_eleventh_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eleventh_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_eleventh_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查防雷箱的指示灯，检查并记录雷击计数</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eleventh_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eleventh_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_eleventh_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查室外地网标识</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eleventh_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eleventh_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_eleventh_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十二个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">空调设备</th>'+
				   '<th style="width:98%;" >1、检查空调工作状况'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_twelfth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_twelfth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_twelfth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查空调的回风温度、湿度</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_twelfth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_twelfth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_twelfth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查空调给、排水管路</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_twelfth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_twelfth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_twelfth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十三个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">机房降温设备</th>'+
				   '<th style="width:98%;" >1、新风防尘网：（1）外部防尘网检查清洁，（2）内部防尘网检查清洁'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_thirteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_thirteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_thirteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、新风室内出风窗、排水管检查清堵，室外机固定件检查除锈</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_thirteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_thirteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_thirteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、运行状况检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_thirteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_thirteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_thirteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十四个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">语音监控设备</th>'+
				   '<th style="width:98%;" >1、连接线、接头检查；'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_fourteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_fourteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、设备外部清扫；</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fourteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_fourteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、语音监控主机、解码器及附属设备运行状态检查；</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fourteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fourteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fourteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十五个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">视频监控设备</th>'+
				   '<th style="width:98%;" >1、摄像机、云台、编码器（视频服务器）、防护罩、视频光端机、网络设备工作状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_fifteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_fifteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、机柜、编码器（视频服务器）、网络设备外观检查清扫，语音功能试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_fifteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、联系客服工区试验、检查确认设备运用正常</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_fifteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_fifteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_fifteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十六个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="2">半自动闭塞通道光传输设备</th>'+
				   '<th style="width:98%;" >1、设备外部清扫、设备配线、标签标识检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_sixteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_sixteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_sixteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、各单板运行指示检查确认、告警指示检查确认</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_sixteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_sixteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_sixteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十七个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">电池</th>'+
				   '<th style="width:98%;" >1、电池、电池架清扫'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_seventeenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_seventeenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_seventeenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查外壳是否有膨胀变形或破裂</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_seventeenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_seventeenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_seventeenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查是否有渗漏电解液</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_seventeenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_seventeenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="ptzjz_seventeenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				 //-----------------------------------第十八个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="2">动力环境监控</th>'+
				   '<th style="width:98%;" >1、设备清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_eighteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_eighteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、与动力环境监控网管进行各项功能试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_eighteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_eighteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_eighteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				 //-----------------------------------第十九个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">机房</th>'+
				   '<th style="width:98%;" >1、机房清扫、门窗检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="ptzjz_nineteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_nineteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="ptzjz_nineteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、引入口封堵检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_nineteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_nineteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_nineteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">3、照明灯具检查处理</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_nineteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_nineteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_nineteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">4、干缆屏蔽地线连接是否牢固</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="ptzjz_nineteenth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="ptzjz_nineteenth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="ptzjz_nineteenth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '</tbody>'+
			       '</table>'
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'overhaulRecordAddDialog'},
            elAttrs : {value: {id:"overhaulRecordAdd"}},
            title:{value:'新增普铁中间站通信设备日常维护记录'},
            width:{value:630},
            height:{value:530},
            contextPath : {},
            checkPeople : {},
            workshop : {},
            workArea : {},
            line : {},
            machineRoom : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('overhaulRecordAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('checkPeople',_self.get('checkPeople'));
                    formData.append('workshop',_self.get('workshop'));
                    formData.append('workArea',_self.get('workArea'));
                    formData.append('line',_self.get('line'));
                    formData.append('machineRoom',_self.get('machineRoom'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/overhaulRecordAction/addTrainZJZ");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSavePTZJZ",{
                                result : result
                            });
                        }
                    }

                }
            },
            events : {
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return overhaulRecordTrainZJZ;
});