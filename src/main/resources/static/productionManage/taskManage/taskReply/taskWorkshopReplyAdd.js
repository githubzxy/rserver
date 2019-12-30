/**
 * 临时任务车间回复界面
 * @author zhouxingyu
 * @date 19-5-13
 */
define('kmms/productionManage/taskManage/taskReply/taskWorkshopReplyAdd',
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
    var taskWorkshopReplyAdd = BUI.Overlay.Dialog.extend({
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
                    text:'回复',
                    elCls : 'button',
                    handler : function(){
                        var pass = _self.get('pass');
                        if(pass){
                        	pass.call(_self);
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
                url:'/kmms/taskApplyAction/findById',
                data:{id : shiftId,collectionName : "task"},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #taskName").val(data.taskName);
                        $("#formContainer #executiveStaff").val(data.executiveStaff);
                        $("#formContainer #executiveStaffId").val(data.executiveStaffId);
                        $("#formContainer #systemType").val(data.systemType);
                        $("#formContainer #sortLevel").val(data.sortLevel);
                        $("#formContainer #remark").val(data.remark);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
                        $("#formContainer #checkAdvice").val(data.checkAdvice);
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
					label : '回复说明：',
					redStarFlag : true,
					itemColspan : 2,
					item : '<textarea name="workshopReply" id="workshopReply" style="width:99.5%;height:100px" data-rules="{required:true}"/></textarea>'
				},
            	{
                    label: '任务标题：',
                    itemColspan: 2,
                    item: '<input type="text" name="taskName" id="taskName" style="width:99.5%" readonly/>'
                },
                {
                	label: '执行部门：',
                	itemColspan: 2,
                	item: '<input type="text" name="executiveStaff" id="executiveStaff" readonly/>'+'<input type="hidden" name="executiveStaffId" id="executiveStaffId" readonly/>'
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
                	label : '技术科科长审核意见：',
                	itemColspan : 2,
                	item : '<textarea name="checkAdvice" id="checkAdvice" style="width:99.5%;height:100px" readonly/></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'taskWorkshopReplyAddForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'260px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id:{value : 'taskWorkshopReplyAddDialog'},
            elAttrs:{value: {id:"taskWorkshopReplyAdd"}},
            title:{value:'车间回复'},
            width:{value:650},
            height:{value:360},
            closeAction:{value:'destroy'},
            mask:{value:true},
            IssueTaskOfWorkShopReply:{},
            IssueTaskOfWorkShop:{},
            
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            shiftId:{},//用于查询单条数据
            pass:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('taskWorkshopReplyAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		//data.collectionName=_self.get('collectionName');
	        		data.IssueTaskOfWorkShopReply=_self.get('IssueTaskOfWorkShopReply');
	        		data.IssueTaskOfWorkShop=_self.get('IssueTaskOfWorkShop');
	        		data.replyUserId = _self.get("userId");
	        		data.replyUserName = _self.get("userName");
	        		data.executiveOrgId = _self.get("orgId");
	        		data.executiveOrgName = _self.get("orgName");
	        		data.flowState="6.5";//回复过但未完成
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/taskWorkshopReplyAction/replyDoc',
        				el : _self.get('el'),
        				loadMsg : '回复中...'
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
    return taskWorkshopReplyAdd;
});