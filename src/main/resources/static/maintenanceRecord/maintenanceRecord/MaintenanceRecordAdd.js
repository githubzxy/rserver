/**
 * 新增
 * @author Liwt
 * @date 18-11-23
 */
define('kmms/maintenanceRecord/maintenanceRecord/MaintenanceRecordAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker','common/data/PostLoad','bui/calendar'],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer');
    var MaintenanceRecordAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
            var _self=this;
            _self._initOrganizationPicker();
            $('#formContainer #orgSelectName').val(_self.get('orgName'));
		    $('#formContainer #orgSelectId').val(_self.get('orgId'));
		    _self._initDate();
		    //判断是否需要获取当前时间
			_self._getNowTime();
        },
        bindUI : function(){
        	var _self = this,orgPicker=_self.get('orgPicker');
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
         * 初始化时间查询
         * @private
         */
        _initDate: function () {
            var _self = this;
            var startUploadDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#createDate',
                showTime: true,
                autoRender: true,
                textField:'#createDate'
            });
            _self.set('createDate', createDate);
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
                zIndex : '1200',
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
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99.5%">'+
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                }, 
                {
                    label : '检修部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                }, 
                {
                    label : '检修时间：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="createDate" id="createDate" readonly data-rules="{required:true}"/>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>'
                }
            ];
            var form = new FormContainer({
                id : 'maintenanceRecordAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        },
        //获取当前时间
		_getNowTime:function(){
			var _self = this;
			var postLoad = new PostLoad({
				url : '/zuul/kmms/maintenanceRecordAction/getTime',
				loadMsg : ''
			}); 
			postLoad.load({}, function(result){
				$('#formContainer #createDate').val(result.data);
			});
		}
    },{
        ATTRS : {
            id : {value : 'maintenanceRecordAddDialog'},
            elAttrs : {value: {id:"maintenanceRecordAdd"}},
            title:{value:'新增'},
            width:{value:610},
            height:{value:270},
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
                    var formAdd = _self.getChild('maintenanceRecordAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/maintenanceRecordAction/addDoc");
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
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return MaintenanceRecordAdd;
});