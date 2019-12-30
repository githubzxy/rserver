/**
 * 新增临时任务
 * @author zhouxingyu
 * @date 19-5-13
 */
define('kmms/productionManage/taskManage/taskApply/taskApplyAdd',
		[
			'bui/common',
			'bui/form',
			'bui/select',
			'bui/data',
			'bui/mask',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	FormContainer = r('common/form/FormContainer'),
    	Mask = r('bui/mask'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	PostLoad = r('common/data/PostLoad'),
    	UpdateUploader = r('common/uploader/UpdateUploader');
    var TaskApplyAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initUploader();
        	_self._initSelect();
        	_self._initWorkShopPicker();
        	_self._initOrganizationPicker();
        },
        bindUI : function(){
            var _self = this;
            $(".wg-help").css("cursor","pointer");
            $(".wg-help").on("click",function(e){
            	var checked = $("#formContainer #receivePeopleSelect").prop("checked");
            	if(e.target.tagName != "INPUT") {
            		checked = !checked;
            	}
            	$("#formContainer #receivePeopleSelect").attr("checked",checked);
            	if(checked){
            		$("#formContainer #receivePeople").attr("disabled",false);
            	}else{
            		$('#formContainer #receivePeople').val("");
        		    $('#formContainer #receivePeopleId').val("");
            		$("#formContainer #receivePeople").attr("disabled",true);
            	}
            });
            
            var workShopOrgPicker=_self.get('workShopOrgPicker');
            /**
             * 组织机构选择
             */
            workShopOrgPicker.on('orgSelected',function (e) {
            	var data = workShopOrgPicker.getSelection();
            	var text = "";
            	var id = "";
            	for(var i=0;i<data.length;i++){
            		text = text + ',' + data[i].text
            		id = id + ',' + data[i].value
            	}
            	$('#formContainer #executiveStaff').val(text.substring(1));
            	$('#formContainer #executiveStaffId').val(id.substring(1));
            });
            
            
            var orgPicker=_self.get('orgPicker');
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
    		    var data = orgPicker.getSelection();
    		    var text = "";
    		    var id = "";
    		    for(var i=0;i<data.length;i++){
    		    	text = text + ',' + data[i].text
    		    	id = id + ',' + data[i].id
    		    }
    		    $('#formContainer #sendOrgName').val(text.substring(1));
    		    $('#formContainer #sendOrgId').val(id.substring(1));
            });
            //定义按键
            var buttons = [
            	{
                    text:'发送',
                    elCls : 'button',
                    handler : function(){
                        var apply = _self.get('apply');
                        if(apply){
                        	apply.call(_self);
                        }
                    }
                },
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
                        var save = _self.get('save');
                        if(save){
                        	save.call(_self);
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
        
        _initWorkShopPicker:function(){
        	var _self=this;
        	var orgPicker = new OrganizationPicker({
        		trigger : '#executiveStaff',
        		rootOrgId:_self.get('rootOrgId'),//必填项
        		rootOrgText:_self.get('rootOrgText'),//必填项
        		url : '/kmms/circuitWorkOrderApplyAction/getWorkShop',//必填项
        		autoHide : true,
        		showRoot : false,//不显示根节点
        		multipleSelect: true,//多选
        		align : {
        			points:['bl','tl']
        		},
        		zIndex : '10000',
        		width:225,
        		height:225
        	});
        	orgPicker.render();
        	_self.set('workShopOrgPicker',orgPicker);
        	
        	
        	
        	
        },
        _initOrganizationPicker:function(){
        	var _self=this;
        	var orgPicker = new OrganizationPicker({
        		trigger : '#sendOrgName',
        		rootOrgId:_self.get('rootOrgId'),//必填项
        		rootOrgText:_self.get('rootOrgText'),//必填项
        		url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
        		autoHide : true,
        		multipleSelect: true,//多选
        		align : {
        			points:['bl','tl']
        		},
        		zIndex : '10000',
        		width:225,
        		height:225
        	});
        	orgPicker.render();
        	_self.set('orgPicker',orgPicker);
        },
        
        _getWorkShopData:function(){
        	var _self = this;
			var store = new Data.Store({
			    url : '/kmms/circuitWorkOrderApplyAction/getWorkShop', 
			    autoLoad : true,    
			    proxy : {
			      method : 'post',
			      dataType : 'json'
			    }
			});
			_self._setWorkShopSelect(store);
        },
        
        _setWorkShopSelect:function(store){
			var _self = this;
			$("#executiveStaff").empty();
			var render = $('#executiveStaff');
			var valueField = $('#executiveStaffId');
			var select = new Select.Select({
		          render:render,
		          valueField:valueField,
		          multipleSelect:true,
		          store:store
		    });
		    select.render();
		    _self.set("executiveStaffSelect",select);
		    _self._initCssOfSelect();
		},
		
		_initCssOfSelect:function(){
			$(".bui-select").css("width","99.5%");
		    $(".bui-select span").css("border","white");
		    $(".bui-select-input").css("width","99.5%");
		    $(".bui-select span").css("display","none");
		    $(".bui-select-list").css("overflow-y","auto");
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
		 * 初始化上传文件
		 */
		_initUploader:function(){
			var _self = this;
			//上传附件
			var uploader = new UpdateUploader({
				render : '#formContainer #uploadfile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
		
		/**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData:function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
			var fileArray = uploader.getSucFiles();
			for(var i in fileArray){
		    	var dto = new _self.UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
				arr[i] = dto; // 向集合添加对象
			};
			return arr;
		},
		
		/**
		  * 声明上传文件对象
		  * @param name 上传文件名
		  * @param path 上传文件路径
		  */
		UploadFileDto: function(name,path){
			this.name = name;
			this.path = path;
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
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text" name="taskName" id="taskName" data-rules="{required:true}" style="width:99.5%"/>'
                },{
                    label: '联系人：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<input type="text" name="linkManName" id="linkManName" data-rules="{required:true}" readonly style="width:99.5%"/>'
                },{
                    label: '完成期限：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<input type="text" name="taskEndDate" id="taskEndDate" data-rules="{required:true}" class="calendar" style="width:98.5%"/>'
                },{
                	label: '执行部门：',
                	redStarFlag: true,
                	itemColspan: 2,
                	item: '<input type="text" name="executiveStaff" id="executiveStaff" readonly/>'+'<input type="hidden" name="executiveStaffId" id="executiveStaffId" readonly/>'
                },{
                	label: '抄送部门：',
                	redStarFlag: true,
                	itemColspan: 2,
                	item: '<input type="text" name="sendOrgName" id="sendOrgName" readonly/>'+'<input type="hidden" name="sendOrgId" id="sendOrgId" readonly/>'
                 },{
                    label: '任务类别：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<select name="systemType" id="systemType" style="width:99.5%" data-rules="{required:true}"/></select>'
                },{
                    label: '紧急程度：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<select name="sortLevel" id="sortLevel" style="width:99.5%" data-rules="{required:true}"/></select>'
                },{
                	label: '备注：',
                	itemColspan: 2,
                	item: '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                },{
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'taskApplyAddForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'taskApplyAddDialog'},
            elAttrs : {value : {id:"taskApplyAdd"}},
            title:{value:'新增临时任务通知'},
            width:{value:650},
            height:{value:420},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},//登录用户ID（技术科用户）
            userName:{},//登录用户名称（技术科用户）
            orgId:{},//登录用户所属机构ID
            orgName:{},//登录用户所属机构名称
            parentId:{},//登录用户所属机构上级机构ID
            save:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('taskApplyAddForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
	        		data.collectionName=_self.get('collectionName');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.parentId=_self.get('parentId');
	        		data.flowState="0";//草稿
                    var pl = new PostLoad({
        				url : '/zuul/kmms/taskApplyAction/addDoc',
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
            apply:{
                value : function(){
                    var _self = this;
                    var checkedStatus = $("#formContainer #receivePeopleSelect").prop("checked");
                    var formAdd = _self.getChild('taskApplyAddForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		if(uploadFileArr.length==0){
	        			Mask.maskElement('#taskApplyAdd');
	        			BUI.Message.Alert("请上传附件！",function(){
	        				Mask.unmaskElement('#taskApplyAdd');
	        			},'error');
	        			$(".bui-message .bui-ext-close").css("display","none");
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	    	    	if(!data.executiveStaff){
	        			Mask.maskElement('#taskApplyAdd');
	        			BUI.Message.Alert("请选择执行部门！",function(){
	        				Mask.unmaskElement('#taskApplyAdd');
	        			},'error');
	        			$("#taskApplyAdd .bui-message .bui-ext-close").css("display","none");
	        			return;
	        		}
	    	    	
	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
	        		data.collectionName=_self.get('collectionName');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.parentId=_self.get('parentId');
	        		if(checkedStatus){
	        			data.flowState="1";//待签收
	        		}else{
	        			data.flowState="4";//待审核
	        		}
                    var pl = new PostLoad({
        				url : '/zuul/kmms/taskApplyAction/addDoc',
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
                value : {'completeAddSave' : true}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return TaskApplyAdd;
});