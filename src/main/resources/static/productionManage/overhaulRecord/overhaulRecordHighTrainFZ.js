/**
 * 高铁防灾通信设备日常维护记录新增
 */
define('kmms/productionManage/overhaulRecord/overhaulRecordHighTrainFZ',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var overhaulRecordHighTrainFZ = BUI.Overlay.Dialog.extend({
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
                elStyle:{overflowY:'scroll',height:'400px'},
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
			       '<th rowspan="4">交流配电柜（箱）</th>'+
				   '<th style="width:98%;" >1、表面清扫'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_first_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_first_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_first_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、仪表显示、指示灯状态及转换开关位置检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_first_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_first_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_first_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、断路器、接触器外观状态检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_first_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_first_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_first_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、电缆架（沟）清扫及电源线外观检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_first_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_first_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_first_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
//	---------------------------第二个设备------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="5">自然灾害及异物侵限监测系统中监控单元设备</th>'+
				   '<th style="width:98%;" >1、设备状态巡视：空开状态、监控主机各指示灯显示状态、UPS指示灯状态、机柜各个板卡灯状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_second_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_second_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_second_first_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查防雷单元模块是否正常，有无发黑，焦糊味等情况出现</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_second_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_second_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_second_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、机柜外观整洁无破损，机柜箱门锁及开关是否正常。</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_second_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_second_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_second_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、检查继电器工作状态：继电器外罩完整、明亮、封闭良好；所有金属零件的防护层无龟裂、融化、脱落及锈蚀等现象；接点须清洁平整，无烧损或发黑、腐蚀等现象；各继电器状态应与软件界面显示状态一致。</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_second_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_second_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_second_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
			       '<th style="width:98%;">5、设备清扫，插接件及各部件螺丝检查、紧固</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_second_fifth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_second_fifth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_second_fifth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
	//-----------------------------------第三个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">综合引入(配线)柜</th>'+
				   '<th style="width:98%;" >1、设备清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_third_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_third_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_third_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、配线端子清扫、检查，电缆及配线整理，根据应用变更情况及时对标识进行调整、更换</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_third_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_third_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_third_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、分线盒、卡模、尾纤标识、光缆标识检查核对，2M头、尾纤、光缆引入检查。2M头、尾纤头紧固，运用情况在线检查，标识、端口台帐检查核对。</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_third_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_third_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_third_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
			       '<th style="width:98%;">4、基本业务测试（移动-移动、移动-固定、固定-移动、短号码、功能号码测试）</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_third_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_third_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_third_fourth_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+  
				    //-----------------------------------第四个设备----------------------------------
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">通信雷电综合防护设施</th>'+
				   '<th style="width:98%;" >1、检查浪涌保护器的失效指示和断路开关状态'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_fourth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_fourth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_fourth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查防雷箱的指示灯，检查并记录雷击计数</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_fourth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_fourth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_fourth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查室外地网标识</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_fourth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_fourth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_fourth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第五个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">空调设备</th>'+
				   '<th style="width:98%;" >1、检查空调工作状况'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_fifth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_fifth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_fifth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查空调的回风温度、湿度</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_fifth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_fifth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_fifth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查空调给、排水管路</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_fifth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_fifth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_fifth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第六个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">视频监控设备</th>'+
				   '<th style="width:98%;" >1、摄像机、云台、编码器（视频服务器）、防护罩、视频光端机、网络设备工作状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_sixth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_sixth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_sixth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、机柜、编码器（视频服务器）、网络设备外观检查清扫，语音功能试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_sixth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_sixth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_sixth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、联系综合视频维护工区试验、检查确认设备运用正常</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_sixth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_sixth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_sixth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第七个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">动力环境监控</th>'+
				   '<th style="width:98%;" >1、设备清扫检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_seventh_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_seventh_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_seventh_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、与动力环境监控网管进行各项功能试验</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_seventh_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_seventh_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_seventh_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、烟感探头清洁、检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_seventh_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_seventh_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_seventh_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第八个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="3">电池</th>'+
				   '<th style="width:98%;" >1、电池、电池架清扫'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_eighth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_eighth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_eighth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、检查外壳是否有膨胀变形或破裂</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_eighth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_eighth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_eighth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、检查是否有渗漏电解液</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_eighth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_eighth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_eighth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第九个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="2">其他设备</th>'+
				   '<th style="width:98%;" >1、设备表面清扫及状态检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_ninth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_ninth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_ninth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、连接缆线、标签检查、运用指示灯状态检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_ninth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_ninth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_ninth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
					//-----------------------------------第十个设备----------------------------------	
				   '<tr class="thTdPadding">'+
			       '<th rowspan="4">机房</th>'+
				   '<th style="width:98%;" >1、机房清扫、门、窗检查'+
				   '</th>'+
			       '<td style="width:30%">'+
				   '<input type="radio"  name="fz_tenth_first_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_tenth_first_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<input type="text"  name="fz_tenth_first_remark"  style="width:98%;" />'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th  style="width:98%;">2、引入口封堵检查</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_tenth_second_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_tenth_second_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text" name="fz_tenth_second_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
			       '<tr>'+
				   '<th style="width:98%;">3、照明灯具检查处理</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_tenth_third_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_tenth_third_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_tenth_third_remark"  style="width:98%;" ></textarea>'+
				   '</td>'+
				   '</tr>'+
				   '<tr>'+
				   '<th style="width:98%;">4、机房地线排地线连接是否牢固</th>'+
			       '<td style="width:30%">'+
			       '<input type="radio"  name="fz_tenth_fourth_state" value="0"  >不正常</input>'+
				   '<input type="radio"  name="fz_tenth_fourth_state" value="1"  >正常</input>'+
				   '</td>'+
				   '<td style="width:30%">'+
				   '<textarea  type="text"  name="fz_tenth_fourth_remark"  style="width:98%;" ></textarea>'+
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
            title:{value:'新增高铁防灾通信设备日常维护记录'},
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
                    xhr.open("POST", "/zuul/kmms/overhaulRecordAction/addHighTrainFZ");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSaveFZ",{
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
    return overhaulRecordHighTrainFZ;
});