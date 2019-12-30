/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dayToJobManagement/videoPhoneNotice/videoPhoneNoticeAdd',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','common/data/PostLoad','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var videoPhoneNoticeAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._initOrganizationPicker();
        	$('#formContainer #backOrgName').val(_self.get('rootOrgText'));
		    $('#formContainer #backOrgId').val(_self.get('rootOrgId'));
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
         * 初始化时间
         */
        _initDate: function () {
            var _self = this;
            var noticeDateStr = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #noticeDateStr',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #noticeDateStr'
            });
            _self.set('noticeDateStr', noticeDateStr);
            
            var postLoad = new PostLoad({
                url : '/kmms/videoPhoneAction/getSystemDate',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #noticeDateStr').val(date);
            	}
            });
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
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="noticeDateStr" id="noticeDateStr" style="width:99%" class="calendar calendar-time" data-rules="{required:true}" readonly/>'
                },{
                    label : '反馈部门：',
                    itemColspan : 1,
                    item : '<input type="text" name="backOrgName" id="backOrgName" style="width:99%" />'
                    	+'<input type="hidden" name="backOrgId" id="backOrgId"  readonly/>'
                },{
                    label : '反馈人：',
                    itemColspan : 1,
                    item : '<input type="text" name="backPerson" id="backPerson" style="width:99%" />'
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
            title:{value:'新增通知'},
            width:{value:620},
            height:{value:300},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
//            userId:{},
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
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('orgName',_self.get('orgName'));
//                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/videoPhoneAction/addDoc");
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
    return videoPhoneNoticeAdd;
});