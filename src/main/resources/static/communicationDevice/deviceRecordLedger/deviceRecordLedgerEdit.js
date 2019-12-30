/**
 * 编辑
 * @author xiekun
 * @date 19-3-25
 */
seajs.use('kmms/communicationDevice/deviceRecordLedger/deviceRecord.css');
define('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerEdit',['bui/common','common/form/FormContainer',
   'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var deviceRecordLedgerEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._getShowData();
//			_self._initOrganizationPicker();
        },
        bindUI : function(){
            var _self = this;
            
//            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #orgSelectName').val(e.org.text);
//    		    $('#formContainer #orgSelectId').val(e.org.id);
//            });
            
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
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
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
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
            })
        },
//        
//        /**
//         * 初始化组织机构选择
//         * @private
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #orgSelectName',
//                rootOrgId:_self.get('rootOrgId'),//必填项
//                rootOrgText:_self.get('rootOrgText'),//必填项
//                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
//                autoHide: true,
//                align : {
//                    points:['bl','tl']
//                },
//                zIndex : '10000',
//                width:493,
//                height:200
//            });
//            orgPicker.render();
//            _self.set('orgPicker',orgPicker);
//        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                           {
                    label : '设备处所：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="location" id="location" data-rules="{required:true}" style="width:99%" />'
                },{
                    label : '设备名称：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="deviceName" id="deviceName" data-rules="{required:true}" style="width:99%" />'
                },{
                    label : '设备类别：',
                    itemColspan : 1,
                    item : '<input type="text" name="type" id="type"  style="width:99%" />'
                },{
                    label : '维护单位：',
                    itemColspan : 1,
                    item : '<input type="text" name="maintainUnit" id="maintainUnit"   style="width:99%" />'
                },{
                    label : '包机人：',
                    itemColspan : 1,
                    item : '<input type="text" name="person" id="person"  style="width:99%" />'
                },{
                    label : '设备厂家：',
                    itemColspan : 1,
                    item : '<input type="text"  name="vender" id="vender" style="width:99%"  />'
                },{
                    label : '设备型号：',
                    itemColspan : 1,
                    item : '<input type="text"  name="modelNumber" id="modelNumber" style="width:99%;"  />'
                },{
                    label : '使用时间：',
                    itemColspan : 1,
                    item : '<input type="text" name="useTime" id="useTime" class="calendar"  readonly  style="width:99%"/>'
                },{
                    label : '所属铁路线 ：',
                    itemColspan : 2,
                    item : '<input type="text" name="railwayLine" id="railwayLine"  style="width:99%" />'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'deviceRecordLedgerEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'deviceRecordLedgerEditDialog'},
            elAttrs : {value: {id:"deviceRecordLedgerEdit"}},
            title:{value:'编辑'},
            width:{value:600},
            height:{value:330},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('deviceRecordLedgerEditForm'),delData=_self.get('delData');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/deviceRecordLedgerAction/updateDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave",{
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
    return deviceRecordLedgerEdit;
});