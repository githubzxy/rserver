/**
 * 段领导审批
 * @author yangli
 * @date 19-2-21
 */
define('kmms/maintainManage/maintainPlan/maintainApproved/maintainApprovedAdd',
	[
	 	'bui/common',
	 	'bui/form',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/form/FormContainer',
	 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer');
    var maintainApprovedAdd = BUI.Overlay.Dialog.extend({
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
			$("#formContainer #auditResult").append("<option value=''>请选择</option>");
			$("#formContainer #auditResult").append("<option  value='1'>审核通过</option>");
			$("#formContainer #auditResult").append("<option  value='0'>退回</option>");
        },
        
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '审核结果：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<select name="auditResult" id="auditResult" style="width:49.5%" data-rules="{required:true}"/></select>'
                },
                {
                	label : '备注：',
//                	redStarFlag : true,
                	itemColspan : 2,
                	item : '<textarea name="auditRemark" id="auditRemark" style="width:99.5%;height:165px" /></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'maintainApprovedAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'maintainApprovedAddDialog'},
            elAttrs : {value: {id:"maintainApprovedAdd"}},
            title:{value:'审核'},
            width:{value:500},
            height:{value:300},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            shiftId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            parentId:{},//登录用户上级组织机构ID
            auditStatus:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('maintainApprovedAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    if($("#formContainer #auditResult").val()=="1"){
                    	formData.append('flowState',"5");//流程状态（审核通过）
                    }else if($("#formContainer #auditResult").val()=="0"){
                    	formData.append('flowState',"0");//流程状态（审核不通过，退回至草稿状态）
                    }
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/maintainApprovedAction/updateDoc");
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
    return maintainApprovedAdd;
});