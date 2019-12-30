/**
 * 详情模块
 */
seajs.use('kmms/communicationDevice/deviceRecordLedger/deviceRecord.css');
define('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerInfo',
								['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var deviceRecordLedgerInfo = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
                {
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
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/deviceRecordLedgerAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						  $("#formContainer #location").val(data.location);
	            		    $('#formContainer #deviceName').val(data.deviceName);
	            		    $("#formContainer #type").val(data.type);
	                        $("#formContainer #maintainUnit").val(data.maintainUnit);
	            		    $('#formContainer #person').val(data.person); 
	            		    $("#formContainer #vender").val(data.vender);
	                        $("#formContainer #modelNumber").val(data.modelNumber);
	            		    $('#formContainer #useTime').val(data.useTime); 
	            		    $("#formContainer #railwayLine").val(data.railwayLine);
	            		    $('#formContainer #remark').val(data.remark);
                    }
          		}
			});
		},
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
		            var childs = [
							   {
                    label : '设备处所：',
                    itemColspan : 1,
                    item : '<input type="text" name="location" id="location" readonly  style="width:99%" />'
                },{
                    label : '设备名称：',
                    itemColspan : 1,
                    item : '<input type="text" name="deviceName" id="deviceName" readonly style="width:99%" />'
                },{
                    label : '设备类别：',
                    itemColspan : 1,
                    item : '<input type="text" name="type" id="type" readonly style="width:99%" />'
                },{
                    label : '维护单位：',
                    itemColspan : 1,
                    item : '<input type="text" name="maintainUnit" id="maintainUnit" readonly  style="width:99%" />'
                },{
                    label : '包机人：',
                    itemColspan : 1,
                    item : '<input type="text" name="person" id="person"  readonly style="width:99%" />'
                },{
                    label : '设备厂家：',
                    itemColspan : 1,
                    item : '<input type="text"  name="vender" id="vender" readonly style="width:99%;"  />'
                },{
                    label : '设备型号：',
                    itemColspan : 1,
                    item : '<input type="text"  name="modelNumber" id="modelNumber" readonly style="width:99%;"  />'
                },{
                    label : '使用时间：',
                    itemColspan : 1,
                    item : '<input type="text" name="useTime" id="useTime"   readonly  style="width:99%"/>'
                },{
                    label : '所属铁路线 ：',
                    itemColspan : 2,
                    item : '<input type="text" name="railwayLine" id="railwayLine" readonly  style="width:99%" />'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" readonly style="width:99.5%;height:50px"/></textarea>'
                }
            ];
			var form = new FormContainer({
				id : 'deviceRecordLedgerInfoShow',
				colNum : colNum,
				formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'deviceRecordLedgerInfoDialog'},
			title:{value:'详细信息'},
			width:{value:600},
            height:{value:330},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return deviceRecordLedgerInfo;
});