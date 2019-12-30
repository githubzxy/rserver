/**
 * 维修方案审核新增
 * @author yangsy
 * @date 19-1-22
 */
define('kmms/constructionManage/pointInnerMaintainPlan/communicationNetwork/communicationNetworkSkillAuditAdd',
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
    var communicationNetworkSkillAuditAdd = BUI.Overlay.Dialog.extend({
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
            

            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
                   	 //审核通过时，必填审批人
    	                if($("#formContainer #approverId").val()==""&& $("#formContainer #skillAuditResult").val()=="1"){
    	                	  BUI.Message.Alert('请选择审批人!');
    	                }else{
    	                var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
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
			$("#formContainer #skillAuditResult").append("<option value=''>请选择</option>");
			$("#formContainer #skillAuditResult").append("<option  value='1'>复核通过</option>");
			$("#formContainer #skillAuditResult").append("<option  value='0'>退回</option>");
			
			$("#formContainer #approverId").append("<option value=''>请选择</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2fe97fcc0000'>张杰</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2fea902d0001'>鲁光明</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2feae6370002'>解传江</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2feb4ee60003'>赵跃武</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2fec6d1a0004'>裴纪升</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2fec92300005'>刘波</option>");
			$("#formContainer #approverId").append("<option  value='8a4ee1106a2fe534016a2fecd7830006'>许亚宏</option>");
			
        },
        
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '复核结果：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="skillAuditResult" id="skillAuditResult" style="width:99.5%" data-rules="{required:true}"/></select>'
                },{
                    label : '审核人：',
                    itemColspan : 1,
                    item : '<select name="approverId" id="approverId" style="width:99.5%" "/></select>'
                },{
                	label : '备注：',
//                	redStarFlag : true,
                	itemColspan : 2,
                	item : '<textarea name="skillAuditRemark" id="skillAuditRemark" style="width:99%;height:165px" /></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'communicationNetworkSkillAuditAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'communicationNetworkSkillAuditAddDialog'},
            elAttrs : {value: {id:"communicationNetworkSkillAuditAdd"}},
            title:{value:'复核'},
            width:{value:620},
            height:{value:320},
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
                    var formAdd = _self.getChild('communicationNetworkSkillAuditAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    if($("#formContainer #skillAuditResult").val()=="1"){
                    	formData.append('flowState',"2");//流程状态（审核通过）
                    }else if($("#formContainer #skillAuditResult").val()=="0"){
                    	formData.append('flowState',"9");//流程状态（审核不通过）
                    	formData.append('approver',"");	
                    }
                    //段领导姓名
                    if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2fe97fcc0000"){
                    formData.append('approver',"张杰");
                    }else if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2fea902d0001"){
                    formData.append('approver',"鲁光明");	
                    }else if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2feae6370002"){
                    formData.append('approver',"解传江");	
                    }else if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2feb4ee60003"){
                    formData.append('approver',"赵跃武");	
                    }else if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2fec6d1a0004"){
                    formData.append('approver',"裴纪升");	
                    }else if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2fec92300005"){
                    formData.append('approver',"刘波");	
                    }else if($("#formContainer #approverId").val()=="8a4ee1106a2fe534016a2fecd7830006"){
                    formData.append('approver',"许亚宏");	
                    }
                    formData.append('userId',_self.get('userId'));
                    formData.append('userName',_self.get('userName'));
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('orgName',_self.get('orgName'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/communicationNetworkSkillAuditAction/updateDoc");
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
    return communicationNetworkSkillAuditAdd;
});