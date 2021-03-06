/**
 * 点外维修执行情况填写
 * @author yangsy
 * @date 19-1-22
 */
define('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyExecute',
	[
	 	'bui/common',
	 	'bui/form',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/form/FormContainer',
	 	'common/org/OrganizationPicker',
	 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var PointOuterMaintainApplyExecute = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initSelect();
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
        
        _initSelect: function(){
			$("#formContainer #checkSituation").append("<option  value='1'>已完成</option>");
			$("#formContainer #checkSituation").append("<option  value='0'>未完成</option>");
        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '兑现情况：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<select name="checkSituation" id="checkSituation" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
                {
                	label : '情况说明：',
//                	redStarFlag : true,
                	itemColspan : 2,
                	item : '<textarea name="situationRemark" id="situationRemark" style="width:99.5%;height:160px" /></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'pointOuterMaintainApplyExecuteForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'pointOuterMaintainApplyExecuteDialog'},
            elAttrs : {value: {id:"pointOuterMaintainApplyExecute"}},
            title:{value:'执行情况'},
            width:{value:450},
            height:{value:300},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
//            index : {value:'2000'},
            contextPath : {},
            collectionName:{},
            shiftId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            parentId:{},//登录用户上级组织机构ID
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('pointOuterMaintainApplyExecuteForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		if($("#formContainer #checkSituation").val()=="0"){
	        			if($("#formContainer #situationRemark").val()==""){
	        				BUI.Message.Alert('请填写未完成原因！');
	        				return;
	        			}
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    if($("#formContainer #checkSituation").val()=="1"){
                    	formData.append('flowState',"7");//流程状态（已完成）
                    }else if($("#formContainer #checkSituation").val()=="0"){
                    	formData.append('flowState',"6");//流程状态（未完成）
                    }
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/pointOuterMaintainApplyAction/updateExecuteDoc");
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
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return PointOuterMaintainApplyExecute;
});