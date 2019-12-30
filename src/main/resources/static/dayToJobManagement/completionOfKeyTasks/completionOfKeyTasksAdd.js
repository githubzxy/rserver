/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksAdd',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','common/data/PostLoad',],function(r){
    var BUI = r('bui/common'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer');
    var completionOfKeyTasksAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        },
        bindUI : function(){
            var _self = this;
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
                url : '/kmms/completionOfKeyTasksAction/getSystemDate',
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
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '日期：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="noticeDateStr" id="noticeDateStr" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
                },{
                    label : '工作项目：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="workProgram" id="workProgram" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '完成情况：',
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
            title:{value:'新增工作情况'},
            width:{value:620},
            height:{value:260},
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
                    xhr.open("POST", "/kmms/completionOfKeyTasksAction/addDoc");
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
    return completionOfKeyTasksAdd;
});