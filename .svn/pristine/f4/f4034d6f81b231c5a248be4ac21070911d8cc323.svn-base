/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dailyShift/duan/dailyShiftForDuanAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var RackAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initOrganizationPicker();
        	$('#formContainer #orgSelectName').val(_self.get('orgName'));
		    $('#formContainer #orgSelectId').val(_self.get('orgId'));
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #orgSelectName').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
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
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #orgSelectName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:493,
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
                    label : '资料名称：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99%">'+
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="accident,alarm,handdown,stare,tel,construction,keytask,security,keysecurity,check,duty">'
                },{
                    label : '所属部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                },{
                    label : '事故、故障、障碍：',
                    itemColspan : 2,
                    item : '<input name="accident" id="accident"  accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '告警监测信息处理情况：',
                    itemColspan : 2,
                    item : '<input name="alarm" id="alarm" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '遗留信息处理情况：',
                    itemColspan : 2,
                    item : '<input name="handdown" id="handdown" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '停点施工、维修天窗及干部把关盯控情况：',
                    itemColspan : 2,
                    item : '<input name="stare" id="stare"  accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '电视电话会议、电报、紧急通知处理情况：',
                    itemColspan : 2,
                    item : '<input name="tel" id="tel" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '施工配合信息：',
                    itemColspan : 2,
                    item : '<input name="construction" id="construction" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '重点工作完成情况：',
                    itemColspan : 2,
                    item : '<input name="keytask" id="keytask" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '上级、段安全问题通知书情况：',
                    itemColspan : 2,
                    item : '<input name="security" id="security" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '路局、段重点追查安全信息：',
                    itemColspan : 2,
                    item : '<input name="keysecurity" id="keysecurity" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '上级部门检查情况：',
                    itemColspan : 2,
                    item : '<input name="check" id="check" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto" type="file" multiple/>'
                },{
                    label : '干部值班情况：',
                    itemColspan : 2,
                    item : '<input name="duty" id="duty" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx" style="height:70px;overflow-y :auto"  type="file" multiple/>'
                }
            ];
            var form = new FormContainer({
                id : 'DuanAdd',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'shiftAddDialog'},
            elAttrs : {value: {id:"shiftAdd"}},
            title:{value:'新增交班'},
            width:{value:610},
            height:{value:500},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            orgName:{},
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
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/commonAction/addDoc");
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
    return RackAdd;
});