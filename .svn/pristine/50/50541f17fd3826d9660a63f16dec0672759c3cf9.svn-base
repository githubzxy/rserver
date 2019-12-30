/**
 * 新增
 * @author yangli
 * @date 19-2-20
 */
define('kmms/maintainManage/maintainPlan/maintainApply/maintainApplyAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/uploader/UpdateUploader','common/data/PostLoad',],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer'),
        PostLoad = r('common/data/PostLoad'),
        UpdateUploader = r('common/uploader/UpdateUploader');
    var maintainApplyAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initUploader();
        },
        bindUI : function(){
            var _self = this;
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
                 },
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
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '维修项目：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99.5%">'
//                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
//                        'value="uploadfile">'
                },{
                    label : '提交部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="depart" id="depart" value="技术科" readonly />'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'maintainApplyAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'maintainApplyAddDialog'},
            elAttrs : {value: {id:"maintainApplyAdd"}},
            title:{value:'新增'},
            width:{value:610},
            height:{value:265},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            parentId:{},
            successApply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('maintainApplyAddForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('flowState',"1");//流程状态(提交申请)
                    formData.append('orgId',_self.get('orgId'));//用于该页面过滤查询数据（登陆用户的组织机构与申情人的组织机构相同）
                    formData.append('orgName',_self.get('orgName'));
                    formData.append('parentId',_self.get('parentId'));//用于审核页面过滤查询数据（登陆用户的组织机构与申情人的上级组织机构相同）
                    formData.append('userId',_self.get('userId'));
                    formData.append('userName',_self.get('userName'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/maintainApplyAction/addDoc");
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
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('maintainApplyAddForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
//                    var formData = new FormData(formAdd.get('el')[0]);
//                    formData.append('collectionName',_self.get('collectionName'));
//                    formData.append('flowState',"0");//流程状态（草稿）
//                    formData.append('orgId',_self.get('orgId'));//用于该页面过滤查询数据（登陆用户的组织机构与申情人的组织机构相同）
//                    formData.append('orgName',_self.get('orgName'));
//                    formData.append('parentId',_self.get('parentId'));//用于审核页面过滤查询数据（登陆用户的组织机构与申情人的上级组织机构相同）
//                    formData.append('userId',_self.get('userId'));
//                    formData.append('userName',_self.get('userName'));
	        		var data = formAdd.serializeToObject();
	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
	        		data.collectionName=_self.get('collectionName');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		data.parentId=_self.get('parentId');
	        		data.flowState="0";
	        		
//                    var xhr = new XMLHttpRequest();
//                    xhr.open("POST", "/zuul/kmms/maintainApplyAction/addDoc");
//                    xhr.send(formData);
//                    xhr.onload = function (e) {
//                        if (e.target.response) {
//                            var result = JSON.parse(e.target.response);
//                            _self.fire("completeAddSave",{
//                                result : result
//                            });
//                        }
//                    }
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/maintainApplyAction/addDoc',
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
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return maintainApplyAdd;
});