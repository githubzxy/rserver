/**
 * 填写审核结果界面
 * @author zhouxingyu
 * @date 19-5-13
 */
define('kmms/productionManage/taskManage/taskCheck/taskCheckAdd',
		[
		 	'bui/common',
		 	'bui/form',
		 	'bui/select',
			'bui/data',
			'bui/calendar',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
			'common/uploader/ViewUploader',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	  Calendar = r('bui/calendar'),
    	  FormContainer= r('common/form/FormContainer'),
    	  OrganizationPicker = r('common/org/OrganizationPicker'),
    	  PostLoad = r('common/data/PostLoad'),
    	  UpdateUploader = r('common/uploader/UpdateUploader'),
    	  ViewUploader = r('common/uploader/ViewUploader');
    var taskCheckAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._getShowData();
			_self._initSelect();
        },
        bindUI : function(){
            var _self = this;
            
            //定义按键
            var buttons = [
                 {
                    text:'审核通过',
                    elCls : 'button',
                    handler : function(){
                        var pass = _self.get('pass');
                        if(pass){
                        	pass.call(_self);
                        }
                    }
                },{
                    text:'回退',
                    elCls : 'button',
                    handler : function(){
                        var back = _self.get('back');
                        if(back){
                        	back.call(_self);
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
        	var _self = this;
        	$("#formContainer #linkManName").val(_self.get('userName'));
			$("#formContainer #systemType").append("<option value=''>请选择</option>");
			$("#formContainer #systemType").append("<option  value='电报'>电报</option>");
			$("#formContainer #systemType").append("<option  value='无线'>无线</option>");
			$("#formContainer #systemType").append("<option  value='有线'>有线</option>");
			$("#formContainer #systemType").append("<option  value='高铁'>高铁</option>");
			$("#formContainer #systemType").append("<option  value='通用'>通用</option>");
			$("#formContainer #systemType").append("<option  value='秋鉴'>秋鉴</option>");
			
			$("#formContainer #sortLevel").append("<option value=''>请选择</option>");
			$("#formContainer #sortLevel").append("<option  value='紧急'>紧急</option>");
			$("#formContainer #sortLevel").append("<option  value='普通'>普通</option>");
        },
        
        /**
		 * 初始化上传文件（仅用于查看）
		 */
		_initViewUploader:function(uploadFiles){
			var _self = this;
			var viewFiles = new ViewUploader({
				render: '#formContainer #viewUploadfile',
				alreadyItems : uploadFiles,
				previewOnline : true
			});
			viewFiles.render();
		},

        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/taskCheckAction/findById',
                data:{id : shiftId,collectionName : _self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #taskName").val(data.taskName);
                        $("#formContainer #linkManName").val(data.linkManName);
                        $("#formContainer #taskEndDate").val(data.taskEndDate);
                        $("#formContainer #sendOrgName").val(data.sendOrgName);
                        $("#formContainer #sendOrgId").val(data.sendOrgId);
                        $("#formContainer #executiveStaff").val(data.executiveStaff);
                        $("#formContainer #executiveStaffId").val(data.executiveStaffId);
                        $("#formContainer #systemType").val(data.systemType);
                        $("#formContainer #sortLevel").val(data.sortLevel);
                        $("#formContainer #remark").val(data.remark);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
                    }
                }
            })
        },
		
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
            	{
                    label: '任务标题：',
                    itemColspan: 2,
                    item: '<input type="text" name="taskName" id="taskName" style="width:99.5%" readonly/>'
                },
                {
                    label: '联系人：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<input type="text" name="linkManName" id="linkManName" readonly data-rules="{required:true}" readonly style="width:99.5%"/>'
                },
                {
                    label: '完成期限：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<input type="text" name="taskEndDate" id="taskEndDate" readonly data-rules="{required:true}" class="calendar" style="width:98.5%"/>'
                },
                {
                	label: '执行部门：',
                	itemColspan: 2,
                	item: '<input type="text" name="executiveStaff" id="executiveStaff" readonly/>'+'<input type="hidden" name="executiveStaffId" id="executiveStaffId" readonly/>'
                },
                {
                	label: '抄送部门：',
                	redStarFlag: true,
                	itemColspan: 2,
                	item: '<input type="text" name="sendOrgName" id="sendOrgName" readonly/>'+'<input type="hidden" name="sendOrgId" id="sendOrgId" readonly/>'
                 },
                {
                    label: '任务类别：',
                    itemColspan: 1,
                    item: '<input type="text" name="systemType" id="systemType" style="width:99%" readonly/>'
                },
                {
                	label: '紧急程度：',
                	itemColspan: 1,
                	item: '<input type="text" name="sortLevel" id="sortLevel" style="width:99%" readonly/>'
                },
                {
                	label: '备注：',
                	itemColspan: 2,
                	item: '<textarea name="remark" id="remark" style="width:99.5%;height:50px" readonly/></textarea>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
				{
                	label : '审核意见：',
                	itemColspan : 2,
                	item : '<textarea name="checkAdvice" id="checkAdvice" style="width:99.5%;height:100px" /></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'taskCheckAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id:{value : 'taskCheckAddDialog'},
            elAttrs:{value: {id:"taskCheckAdd"}},
            title:{value:'审核'},
            width:{value:650},
            height:{value:550},
            closeAction:{value:'destroy'},
            mask:{value:true},
            collectionName:{},
            IssueTaskOfWorkShop : {value:'IssueTaskOfWorkShop'},//存储表名（下发子单）
            userId:{},
            userName:{},
            shiftId:{},//用于查询单条数据
            pass:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('taskCheckAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('collectionName');
	        		data.IssueTaskOfWorkShop = _self.get('IssueTaskOfWorkShop');
	        		data.checkUserId = _self.get("userId");
	        		data.checkUserName = _self.get("userName");
	        		data.flowState="6";//审核通过（待回复）
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/taskCheckAction/updateCheckDoc',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});
                }
            },
            back:{
            	value : function(){
            		var _self = this;
            		var formAdd = _self.getChild('taskCheckAddForm');
            		//验证不通过
            		if(!formAdd.isValid()){
            			return;
            		}
            		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('collectionName');
	        		data.checkUserId = _self.get("userId");
	        		data.checkUserName = _self.get("userName");
	        		data.flowState="5";//审核不通过
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/taskCheckAction/updateCheckDoc',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});
            	}
            },
            events : {
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return taskCheckAdd;
});