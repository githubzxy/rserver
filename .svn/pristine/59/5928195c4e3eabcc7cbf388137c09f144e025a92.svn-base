/**
 * 编辑
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dayToJobManagement/videoPhoneNotice/videoPhoneNoticeEdit',['bui/common','common/form/FormContainer',
   'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var videoPhoneNoticeEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
        	var _self=this;
        	_self._initOrganizationPicker();
        	$('#formContainer #backOrgName').val(_self.get('rootOrgText'));
		    $('#formContainer #backOrgId').val(_self.get('rootOrgId'));
            _self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #backOrgName').val(e.org.text);
    		    $('#formContainer #backOrgId').val(e.org.id);
            });            
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
                url:'/kmms/videoPhoneAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #noticeDateStr").val(data.noticeDateStr);
                        $("#formContainer #backOrgName").val(data.backOrgName);
            		    $('#formContainer #backPerson').val(data.backPerson);
            		    $('#formContainer #noticeContent').val(data.noticeContent);
            		    $('#formContainer #remark').val(data.remark);
                    }
                }
            })
        },

        
        /**
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #backOrgName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:220,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },

        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                           {
                    label : '时间：',
                    itemColspan : 2,
                    item : '<input type="text" style="border:none;width:98%;height:100%;font-size: 10.0pt;" id="noticeDateStr" name="noticeDateStr" class="calendar calendar-time" readonly="readonly"/>'
                },{
                    label : '反馈部门：',
                    itemColspan : 1,
                    item : '<input type="text" name="backOrgName" id="backOrgName" style="width:99%" />'
                    	+'<input type="hidden" name="backOrgId" id="backOrgId"  readonly/>'                },{
                    label : '反馈人：',
                    itemColspan : 1,
                    item : '<input type="text"  name="backPerson" id="backPerson"  style="width:99%">'
                },{
                    label : '内容及处理情况：',
                    itemColspan : 2,
                    item : '<textarea style="border:none;width: 99%;resize: none;" id="noticeContent" name="noticeContent" maxlength="900">'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea style="border:none;width: 99%;resize: none;" id="remark" name="remark" maxlength="900">'
                }
            ];
            var form = new FormContainer({
                id : 'DuanAdd',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'shiftAddDialog'},
            elAttrs : {value: {id:"shiftAdd"}},
            title:{value:'编辑通知'},
            width:{value:620},
            height:{value:300},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('DuanAdd');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/videoPhoneAction/updateDoc");
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
    return videoPhoneNoticeEdit;
});