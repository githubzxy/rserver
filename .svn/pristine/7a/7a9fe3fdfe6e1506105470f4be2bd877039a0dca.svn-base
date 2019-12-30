/**
 * 维修方案申请数据新增
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyAdd',
	[
	 	'bui/common',
	 	'bui/form',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/form/FormContainer',
	 	'common/org/OrganizationPicker',
	 	'common/uploader/UpdateUploader',
	 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer'),
        UpdateUploader = r('common/uploader/UpdateUploader');
    var SkylightMaintenanceApplyAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initUploader();
        	_self._initOrganizationPicker();
        	$('#formContainer #orgSelectName').val(_self.get('orgName'));
		    $('#formContainer #orgSelectId').val(_self.get('orgId'));
//        	_self._initSelect();
        },
        bindUI : function(){
            var _self = this;
            var orgPicker=_self.get('orgPicker');
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #orgSelectName').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
            });
            //定义按键
            var buttons = [
                {
                    text:'申请',
                    elCls : 'button',
                    handler : function(){
                        var successApply = _self.get('successApply');
                        if(successApply){
                        	successApply.call(_self);
                        }
                    }
                },{
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
//			var fileArray = uploader.get("queue").getItemsByStatus('success');
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
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #orgSelectName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:493,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
//        _initSelect: function(){
//			$("#formContainer #type").append("<option  value='I级维修方案'>I级维修方案</option>");
//        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
            	{
                    label : '施工项目：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="project" id="project" data-rules="{required:true}" style="width:99.5%"/>'
                },{
                    label : '提交部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                },{
                    label : '类型：',
                    itemColspan : 2,
                    item : '<input type="text" name="type" id="type" value="I级维修方案" style="width:99.5%" readOnly/>'
                },{
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'skylightMaintenanceApplyAddForm',
                colNum : colNum,
                formChildrens : childs
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'pointOuterMaintainApplyAddDialog'},
            elAttrs : {value: {id:"pointOuterMaintainApplyAdd"}},
            title:{value:'新增'},
            width:{value:650},
            height:{value:300},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            parentId:{},//登录用户上级组织机构ID
            successApply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('skylightMaintenanceApplyAddForm');
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
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		data.flowState="1";
	        		
	        		 var pl = new PostLoad({
	        				url : '/zuul/kmms/skylightMaintenanceApplyAction/addDoc',
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
//                    var formData = new FormData(formAdd.get('el')[0]);
//                    formData.append('collectionName',_self.get('collectionName'));
//                    formData.append('flowState',"1");//流程状态
//                    formData.append('userId',_self.get('userId'));
//                    formData.append('orgId',_self.get('orgId'));//用于该页面过滤查询数据（登陆用户的组织机构与申情人的组织机构相同）
//                    formData.append('orgName',_self.get('orgName'));
//                    formData.append('parentId',_self.get('parentId'));//用于审核页面过滤查询数据（登陆用户的组织机构与申情人的上级组织机构相同）
//                    var xhr = new XMLHttpRequest();
//                    xhr.open("POST", "/zuul/kmms/skylightMaintenanceApplyAction/addDoc");
//                    xhr.send(formData);
//                    xhr.onload = function (e) {
//                        if (e.target.response) {
//                            var result = JSON.parse(e.target.response);
//                            _self.fire("completeAddSave",{
//                                result : result
//                            });
//                        }
//                    }
                }
            },
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('skylightMaintenanceApplyAddForm');
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
	        		data.orgName=_self.get('orgName');
	        		data.orgId=_self.get('orgId');
	        		data.flowState="0";
                    var pl = new PostLoad({
        				url : '/zuul/kmms/skylightMaintenanceApplyAction/addDoc',
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
//                    var formData = new FormData(formAdd.get('el')[0]);
//                    formData.append('collectionName',_self.get('collectionName'));
//                    formData.append('flowState',"0");//流程状态
//                    formData.append('userId',_self.get('userId'));
//                    formData.append('orgId',_self.get('orgId'));//用于该页面过滤查询数据（登陆用户的组织机构与申情人的组织机构相同）
//                    formData.append('orgName',_self.get('orgName'));
//                    formData.append('parentId',_self.get('parentId'));//用于审核页面过滤查询数据（登陆用户的组织机构与申情人的上级组织机构相同）
//                    var xhr = new XMLHttpRequest();
//                    xhr.open("POST", "/zuul/kmms/skylightMaintenanceApplyAction/addDoc");
//                    xhr.send(formData);
//                    xhr.onload = function (e) {
//                        if (e.target.response) {
//                            var result = JSON.parse(e.target.response);
//                            _self.fire("completeAddSave",{
//                                result : result
//                            });
//                        }
//                    }
                }
            },
            events : {
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return SkylightMaintenanceApplyAdd;
});