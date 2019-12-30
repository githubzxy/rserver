/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dailyShift/fault/faultAdd',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var FaultAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._initFaultType();
        	_self._initOrganizationPicker();
        	$('#formContainer #orgSelectName').val(_self.get('orgName'));
		    $('#formContainer #orgSelectId').val(_self.get('orgId'));
		    $('#formContainer #departmentName').val(_self.get('orgName'));
		    $('#formContainer #departmentId').val(_self.get('orgId'));
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            var departmentPicker=_self.get('departmentPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #orgSelectName').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
            });
            departmentPicker.on('orgSelected',function (e) {
            	$('#formContainer #departmentName').val(e.org.text);
            	$('#formContainer #departmentId').val(e.org.id);
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
                width:230,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
            
            var departmentPicker = new OrganizationPicker({
                trigger : '#formContainer #departmentName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:230,
                height:200
            });
            departmentPicker.render();
            _self.set('departmentPicker',departmentPicker);
        },
        
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #date',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #date'
            });
            var occurrenceTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #occurrenceTime',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #occurrenceTime'
            });
            var endTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #recoveryTime',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #recoveryTime'
            });
            _self.set('date', date);
            _self.set('occurrenceTime', occurrenceTime);
            _self.set('recoveryTime', recoveryTime);
        },
        
        /**
         * 初始化类别
         */
        _initFaultType : function(){
        	$("#formContainer #faultType").append("<option value='1'>事故</option>");
			$("#formContainer #faultType").append("<option value='2'>故障</option>");
			$("#formContainer #faultType").append("<option value='3'>障碍</option>");
        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '处所：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="location" id="location" data-rules="{required:true}" style="width:99.5%">'+
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label : '日期：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" data-rules="{required:true}" class="calendar" style="width:99%" readonly>'
                },{
                    label : '类别：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="faultType" id="faultType" style="width:99.5%;" data-rules="{required:true}"><option value="">请选择</option></select>'
                },{
                    label : '发生时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="occurrenceTime" id="occurrenceTime" data-rules="{required:true}" class="calendar" style="width:99%" readonly>'
                },{
                    label : '恢复时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="recoveryTime" id="recoveryTime" data-rules="{required:true}" class="calendar" style="width:99%" readonly>'
                },{
                    label : '责任部门：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="departmentName" id="departmentName" style="width:99%" readonly data-rules="{required:true}"/><input type="hidden" name="departmentId" id="departmentId" readonly/>'
                },{
                    label : '所属部门：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName" style="width:99%" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId" readonly/>'
                },{
                    label : '主要内容：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<textarea  name="mainContent" id="mainContent" data-rules="{required:true}" style="height:20px;width:99.5%"></textarea>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>'
                }
            ];
            var form = new FormContainer({
                id : 'faultAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'faultAddDialog'},
            elAttrs : {value: {id:"faultAdd"}},
            title:{value:'新增'},
            width:{value:610},
            height:{value:365},
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
                    var formAdd = _self.getChild('faultAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/faultManagementAction/addDoc");
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
    return FaultAdd;
});