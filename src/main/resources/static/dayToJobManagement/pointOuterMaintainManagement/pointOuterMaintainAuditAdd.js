/**
 * 点外维修审核新增
 * @author yangsy
 * @date 19-1-22
 */
define('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainAuditAdd',
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
    var PointOuterMaintainAuditAdd = BUI.Overlay.Dialog.extend({
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
            
            $("#formContainer #userName").val(_self.get("userName"));
            
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
			$("#formContainer #auditResult").append("<option  value='1'>审核通过</option>");
			$("#formContainer #auditResult").append("<option  value='0'>退回</option>");
        },
        
//        /**
//         * 初始化时间
//         */
//        _initDate: function () {
//            var _self = this;
//            var workTime = new Calendar.DatePicker({//加载日历控件
//                trigger: '#formContainer #workTime',
//                showTime: true,
//                autoRender: true,
//                textField:'#formContainer #workTime'
//            });
//            _self.set('workTime', workTime);
//            
////            var postLoad = new PostLoad({
////                url : '/kmms/networkManageInfoAction/getSystemDate.cn',
////                el : _self.get('el'),
////                loadMsg : '加载中...'
////            });
////            postLoad.load({},function (date) {
////            	if(date){
////            		$('#formContainer #createDate').val(date);
////            	}
////            });
//        },
        
//        /**
//         * 初始化组织机构选择
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #backOrgName',
//                rootOrgId:_self.get('rootOrgId'),//必填项
//                rootOrgText:_self.get('rootOrgText'),//必填项
//                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
//                autoHide: true,
//                align : {
//                    points:['bl','tl']
//                },
//                zIndex : '10000',
//                width:220,
//                height:200
//            });
//            orgPicker.render();
//            _self.set('orgPicker',orgPicker);
//        },
        
//        /**
//         * 获取线别
//         */
//        _getLines:function(){
//        	var _self=this;
//        	$.ajax({
//        		url:'/kmms/networkManageInfoAction/getLines',
//        		type:'post',
//        		dataType:"json",
//        		success:function(res){
//        			$("#formContainer #lineName").append("<option  value=''>请选择</option>");
//        			for(var i=0;i<res.length;i++){
//        				$("#formContainer #lineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
//        			}
//        		}
//        	});
//        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
//                {
//                    label : '资料名称：',
//                    redStarFlag : true,
//                    itemColspan : 2,
//                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99.5%">'+
//                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
//                        'value="uploadfile">'
//                },
                {
                    label : '审核结果：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<select name="auditResult" id="auditResult" style="width:100%" data-rules="{required:true}"/></select>'
                },
                {
                    label : '审核人：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="userName" id="userName" style="width:99.5%" data-rules="{required:true}"/>'
                },
                {
                	label : '备注：',
                	itemColspan : 2,
                	item : '<textarea name="auditRemark" id="auditRemark" style="width:99.5%;height:160px" /></textarea>'
                },
//                {
//                    label : '附件：',
//                    itemColspan : 2,
//                    item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>'
//                }
            ];
            var form = new FormContainer({
                id : 'pointOuterMaintainAuditAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'pointOuterMaintainAuditAddDialog'},
            elAttrs : {value: {id:"pointOuterMaintainAuditAdd"}},
            title:{value:'审核'},
            width:{value:450},
            height:{value:330},
            closeAction : {value:'destroy'},
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
                    var formAdd = _self.getChild('pointOuterMaintainAuditAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    if($("#formContainer #auditResult").val()=="1"){
                    	formData.append('flowState',"3");//流程状态（审核通过）
                    }else if($("#formContainer #auditResult").val()=="0"){
                    	formData.append('flowState',"2");//流程状态（审核不通过）
                    }
                    formData.append('userId',_self.get('userId'));
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('orgName',_self.get('orgName'));
                    formData.append('ddkOrgId',"402891b45b5fd02c015b74c97d740037");//生产调度指挥中心组织机构ID
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/pointOuterMaintainAuditAction/updateDoc");
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
    return PointOuterMaintainAuditAdd;
});