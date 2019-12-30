/**
 * II级维修审批
 * @author yangsy
 * @date 19-2-22
 */
define('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyReply',
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
    var PointInnerSecondMaintainApplyReply = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
//        	_self._initSelect();
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
        
//        _initSelect: function(){
//			$("#formContainer #approveResult").append("<option value=''>请选择</option>");
//			$("#formContainer #approveResult").append("<option  value='1'>审批通过</option>");
//			$("#formContainer #approveResult").append("<option  value='0'>退回</option>");
//        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
//                {
//                    label : '审批结果：',
//                    redStarFlag : true,
//                    itemColspan : 2,
//                    item : '<select name="approveResult" id="approveResult" style="width:100%" data-rules="{required:true}"/></select>'
//                },
                {
                	label : '回复说明：',
                	redStarFlag : true,
                	itemColspan : 2,
                	item : '<textarea name="workareaReply" id="workareaReply" style="width:99.5%;height:115px" data-rules="{required:true}"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'pointInnerSecondMaintainApplyReplyForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'pointInnerSecondMaintainApplyReplyDialog'},
            elAttrs : {value: {id:"pointInnerSecondMaintainApplyReply"}},
            title:{value:'工区回复'},
            width:{value:500},
            height:{value:220},
            closeAction : {value:'destroy'},
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('pointInnerSecondMaintainApplyReplyForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
//                    if($("#formContainer #approveResult").val()=="1"){//审批通过
//                    	formData.append('flowState',"2");//流程状态 待回复
//                    }else if($("#formContainer #approveResult").val()=="0"){
//                    	formData.append('flowState',"3");//流程状态 已回退
//                    }
                    formData.append('flowState',"4");//流程状态 已结束
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/pointInnerSecondMaintainApplyAction/updateReplyDoc");
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
    return PointInnerSecondMaintainApplyReply;
});