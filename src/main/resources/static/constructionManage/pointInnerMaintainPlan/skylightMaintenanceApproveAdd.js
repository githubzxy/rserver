/**
 * 维修方案审核新增
 * @author yangsy
 * @date 19-1-22
 */
define('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApproveAdd',
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
    var SkylightMaintenanceApproveAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
//        	_self._initOrganizationPicker();
//        	_self._initDate();
//        	_self._getLines();//获取线别下拉选数据
        	_self._initSelect();
//        	$('#formContainer #backOrgName').val(_self.get('rootOrgText'));
//		    $('#formContainer #backOrgId').val(_self.get('rootOrgId'));
        },
        bindUI : function(){
            var _self = this;
            
//            var orgPicker=_self.get('orgPicker');
            
//            /**
//             * 组织机构选择
//             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #backOrgName').val(e.org.text);
//    		    $('#formContainer #backOrgId').val(e.org.id);
//            });

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
			$("#formContainer #approveResult").append("<option value=''>请选择</option>");
			$("#formContainer #approveResult").append("<option  value='1'>审批通过</option>");
			$("#formContainer #approveResult").append("<option  value='0'>退回</option>");
        },
        

        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '审批结果：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<select name="approveResult" id="approveResult" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
                {
                	label : '备注：',
//                	redStarFlag : true,
                	itemColspan : 2,
                	item : '<textarea name="approveRemark" id="approveRemark" style="width:99.5%;height:165px" /></textarea>'
                },
//                {
//                    label : '附件：',
//                    itemColspan : 2,
//                    item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>'
//                }
            ];
            var form = new FormContainer({
                id : 'skylightMaintenanceApproveAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'skylightMaintenanceApproveDialog'},
            elAttrs : {value: {id:"skylightMaintenanceApproveAdd"}},
            title:{value:'审批'},
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
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('skylightMaintenanceApproveAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    if($("#formContainer #approveResult").val()=="1"){
                    	formData.append('flowState',"6");//流程状态（审批通过）
                    }else if($("#formContainer #approveResult").val()=="0"){
                    	formData.append('flowState',"9");//流程状态（审批不通过）
                    }
                    formData.append('userId',_self.get('userId'));
                    formData.append('userName',_self.get('userName'));
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('orgName',_self.get('orgName'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/skylightMaintenanceApproveAction/updateDoc");
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
    return SkylightMaintenanceApproveAdd;
});