/**
 * 高铁防灾通信设备日常维护记录新增
 */
define('kmms/productionManage/overhaulRecord/overhaulRecordHighTrainZJZ',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var overhaulRecordHighTrainZJZ = BUI.Overlay.Dialog.extend({
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
			       '<th rowspan="4">传输、接入网设备</th>'+
				   '<th style="width:98%;" >1.电源有无异常告警'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_first_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_first_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea type="text"  name="htzjz_first_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2.设备外部清扫检查、防尘网清扫检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_first_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_first_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_first_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、机柜指示灯观察</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_first_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_first_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_first_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、台帐资料核对检查，电路标记核对、修改</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_first_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_first_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_first_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
//	---------------------------第二个设备------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="7">高频开关电源及UPS</th>'+
				   '<th style="width:98%;" >1、设备运行指示灯检查、试验有无异常告警'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_second_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_second_first_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、设备外部清扫检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_second_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_second_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、两路电转换试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_second_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_second_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、停电试验（含电池放电试验）</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_second_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_second_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">5、各电源连接端子、标识检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_second_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_second_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">6、UPS运行情况巡检：检查指示灯、输出电压电流、告警信息等</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_second_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_second_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">7、ups断路器、风扇、防雷保护单元等元件的外观及状态检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_second_seventh_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_second_seventh_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_second_seventh_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
	//-----------------------------------第三个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="2">数据网设备</th>'+
				   '<th style="width:98%;" >1、设备外部清扫检查、防尘滤网的清洁'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_third_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_third_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_third_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、附属设备及缆线、标签检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_third_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_third_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_third_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				    //-----------------------------------第四个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="7">车站数字调度交换设备及调度录音设备</th>'+
				   '<th style="width:98%;" >1、设备外部清扫及配线检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_fourth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_fourth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、附属设备及连线检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_fourth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、交换机风扇运行检查确认</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fourth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、检查分系统每一块板的工作指示灯是否异常</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fourth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">5、将设备异常信息反馈至GSM-R维护中心</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fourth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">6、时间校核</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourth_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fourth_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">7、录音调听试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourth_seventh_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourth_seventh_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fourth_seventh_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第五个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="5">车站数字调度操作台</th>'+
				   '<th style="width:98%;" >1、访问用户使用情况，对用户反应的信息进行重点处理'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_fifth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_fifth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、操作台接线整理、麦克风及连线检查、表面清洁、更换不清晰用户标签</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fifth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_fifth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、设备状态、按键灵敏度、时间跟踪状态等检查、调整</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fifth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fifth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、调度业务、站间、站场业务呼叫通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fifth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fifth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">5、应急分机呼入、呼出通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fifth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fifth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第六个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">专用电话分机</th>'+
				   '<th style="width:98%;" >1.设备外部清扫及配线检查查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_sixth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_sixth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_sixth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2.通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_sixth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_sixth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_sixth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3.电池电压测量</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_sixth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_sixth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_sixth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第七个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">综合引入(配线、ODF、DDF)柜</th>'+
				   '<th style="width:98%;" >1、设备清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_seventh_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventh_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_seventh_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、配线端子清扫、检查，电缆及配线整理，根据应用变更情况及时对标识进行调整、更换</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_seventh_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventh_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_seventh_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、分线盒、卡模、尾纤标识、光缆标识检查核对，2M头、尾纤、光缆引入检查。2M头、尾纤头紧固，运用情况在线检查，标识、端口台帐检查核对。</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_seventh_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventh_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_seventh_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第八个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">GSM-R无线网设备（基站）</th>'+
				   '<th style="width:98%;" >1、设备巡视、查看设备运行状态'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_eighth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eighth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_eighth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、接头、连接线检查、配线端子清扫、检查，配线整理，根据应用变更情况及时对标识进行调整、更换</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_eighth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eighth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_eighth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、风扇检查、清理防尘网；设备外部清扫</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_eighth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eighth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_eighth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、基本业务测试（移动-移动、移动-固定、固定-移动、短号码、功能号码测试）</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_eighth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eighth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_eighth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第九个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="7">铁塔及天馈线</th>'+
				   '<th style="width:98%;" >1、巡视周边基础，无塌陷，清除周边杂草、灌木'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_ninth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_ninth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查周边地质、塔底水泥变化，无开裂、塌陷、沉降；周边地质变化，无开裂、塌陷、沉降；</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_ninth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_ninth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">3、检查塔底螺栓、塔体铁靴，无脱落、缺失、严重锈蚀状况</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_ninth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_ninth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">4、检查安全警示标志牌，无缺失、损坏</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_ninth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_ninth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">5、观察塔身固定、倾斜情况，无扭曲、弯曲、变形、晃动</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_ninth_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_ninth_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">6、观察塔顶避雷针稳固情况，无扭曲、弯曲、变形、晃动</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_ninth_sixth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_sixth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_ninth_sixth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th  style="width:98%;">7、观察塔顶天线牢固情况，无松脱、倾斜及晃动</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_ninth_seventh_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_ninth_seventh_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_ninth_seventh_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">通信雷电综合防护设施</th>'+
				   '<th style="width:98%;" >1、检查浪涌保护器的失效指示和断路开关状态'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_tenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_tenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_tenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查防雷箱的指示灯，检查并记录雷击计数</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_tenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_tenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_tenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查室外地网标识</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_tenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_tenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_tenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十一个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">空调设备</th>'+
				   '<th style="width:98%;" >1、检查空调工作状况'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_eleventh_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eleventh_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_eleventh_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查空调的回风温度、湿度</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_eleventh_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eleventh_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_eleventh_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查空调给、排水管路</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_eleventh_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_eleventh_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_eleventh_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十二个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">电视会议终端及附属设备</th>'+
				   '<th style="width:98%;" >1、设备表面清扫及状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_twelfth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_twelfth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_twelfth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、连接缆线、标签检查、遥控器功能检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_twelfth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_twelfth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_twelfth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、图像、通话试验：音频输出、音频输入、视频输出、视频输入</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_twelfth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_twelfth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_twelfth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、摄像机设置及控制检查、话筒功能检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_twelfth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_twelfth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_twelfth_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十三个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">视频监控设备</th>'+
				   '<th style="width:98%;" >1、摄像机、云台、编码器（视频服务器）、防护罩、视频光端机、网络设备工作状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_thirteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_thirteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_thirteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、机柜、编码器（视频服务器）、网络设备外观检查清扫，语音功能试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_thirteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_thirteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_thirteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、联系综合视频维护工区试验、检查确认设备运用正常</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_thirteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_thirteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_thirteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十四个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">动力环境监控</th>'+
				   '<th style="width:98%;" >1、设备清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_fourteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_fourteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、与动力环境监控网管进行各项功能试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_fourteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、烟感探头清洁、检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fourteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fourteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fourteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十五个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">电池</th>'+
				   '<th style="width:98%;" >1、电池、电池架清扫'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_fifteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_fifteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查外壳是否有膨胀变形或破裂</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fifteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_fifteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查是否有渗漏电解液</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_fifteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_fifteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_fifteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十六个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">隧道应急设备</th>'+
				   '<th style="width:98%;" >1、设备表面清扫及状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_sixteenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_sixteenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_sixteenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、连接缆线、标签检查、运用指示灯状态检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_sixteenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_sixteenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_sixteenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、具备条件的，呼入、呼出通话试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_sixteenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_sixteenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_sixteenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十七个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">机房</th>'+
				   '<th style="width:98%;" >1、机房清扫、门、窗检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="htzjz_seventeenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventeenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="htzjz_seventeenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、引入口封堵检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_seventeenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventeenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="htzjz_seventeenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、照明灯具检查处理</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_seventeenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventeenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_seventeenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、机房地线排地线连接是否牢固</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="htzjz_seventeenth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="htzjz_seventeenth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="htzjz_seventeenth_fourth_remark"  style="width:98%;" ></textarea>'+
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
            title:{value:'新增高铁中间站通信设备日常维护记录'},
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
                    xhr.open("POST", "/zuul/kmms/overhaulRecordAction/addHighTrainZJZ");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSaveZJZ",{
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
    return overhaulRecordHighTrainZJZ;
});