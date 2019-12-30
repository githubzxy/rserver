/**
 * 新增
 * @author yangli
 * @date 19-1-14
 */
define('kmms/dayToJobManagement/dayToJob/DayToJobAdd',
		[
		 	'bui/common',
		 	'bui/calendar',
		 	'bui/form',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer');
    var DayToJobAdd = BUI.Overlay.Dialog.extend({
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
        	$('#formContainer #dispatch').val(_self.get("userName"));
            var postLoad = new PostLoad({
                url : '/kmms/dayToJobAction/getSystemBeforeDate.cn',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #date').val(date);
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
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" style="width:99%" readonly />'
                },{
                    label : '值班调度：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="dispatch" id="dispatch" style="width:99%" readonly/>'
                },{
                    label : '值班领导：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="leader" id="leader" style="width:99%"/>'
                },{
                    label : '值班干部：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="cadre" id="cadre" style="width:99%"/>'
                }
            ];
            var form = new FormContainer({
                id : 'dayToJobAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'dayToJobAddDialog'},
            elAttrs : {value: {id:"dayToJobAdd"}},
            title:{value:'新增'},
            width:{value:600},
            height:{value:165},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('dayToJobAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/dayToJobAction/addData");
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
                value : {'completeAddSave' : true,}
            },
        }
    });
    return DayToJobAdd;
});