/**
 * 文件新增模块
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/emergencyManage/exerciseScheme/exerciseSchemeAdd',
		['bui/common',
		'bui/data',
		 'common/form/FormContainer',
		 'bui/uploader',
		 'common/uploader/UpdateUploader',
		 'common/data/PostLoad',
		 'kmms/technicalManagement/technicalDocument/SelectSuggest'
		 ],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var	Uploader = r('bui/uploader');
	var	UpdateUploader = r('common/uploader/UpdateUploader');
	var	PostLoad = r('common/data/PostLoad');
	var SelectSuggest = r("kmms/technicalManagement/technicalDocument/SelectSuggest");
	var ExerciseSchemeAdd = BUI.Overlay.Dialog.extend({
		/**
		 * 初始化
		 */
		initializer: function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		
		/**
		 * 渲染
		 */
		renderUI: function(){
			var _self = this;
			_self._initUploader();
			_self._getWorkshops();//获取部门下拉选数据
			 $("#formContainer #createUser").val(_self.get('userName'));
		},

		/**
		 * 绑定事件
		 */
		bindUI: function(){
			var _self = this;
            	//定义按键
			var buttons = [
				{
					text: '保存',
					elCls: 'button',
					handler: function(){
						_self._saveData();
					}
				},{
					text: '关闭',
					elCls: 'button',
					handler: function(){
						_self.close();
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
				render : '#formContainer #uploadFile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
		/**
		 * 初始化表单
		 */
		_initFormContainer: function(){
			var form = new FormContainer({
				id: 'fileFormAdd',
				colNum: 1,
				formChildrens:[
				{
					label: '所属部门：',
					redStarFlag: true,
					itemColspan: 1,
					item: '<select type="text" id="depart" name="depart" readonly style="width: 384px;"></select>'
				},{
					label: '创建人',
					itemColspan: 1,
					item : '<input type="text" id="createUser" name="createUser" readonly />'
				},{
					label: '附件：',
					itemColspan: 1,
					item: '<div name="uploadFile" id="uploadFile" style="height:80px;overflow-y:auto"></div>'
				}] 
			});
			return form;
		},
	    /**
         * 获取科室和车间
         */
        _getWorkshops:function(){
        	var _self=this;
       	 $.ajax({
	                url:'/kmms/userInfoManageAction/getCadreAndShop',
	                type:'post',
	                dataType:"json",
	                success:function(res){
		             $("#formContainer #depart").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
	               		 $("#formContainer #depart").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
                }
            });
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
		//保存数据
		_saveData:function(){
			var _self = this;
    		var form = _self.getChild('fileFormAdd',true);
    		//验证不通过
    		if(!form.isValid()){
    			return;
    		}
    		var data = form.serializeToObject();
    		//获取上传文件
    		var uploadFile = _self._getUploadFileData();
    		data.uploadFile=JSON.stringify(uploadFile);
    		data.collectionName = _self.get('collectionName');
			data.userId = _self.get('userId');
			data.orgId = _self.get('orgId');
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/exerciseSchemeAction/addDoc',
				el : _self.get('el'),
				loadMsg : '保存中...'
			}); 
    		
			postLoad.load(data, function(result){
				if(result != null){
					_self.fire("completeAddSave",{
						result : result
					});
				}
			});
		}
	},{
		ATTRS: {
			id: {value : 'fileAddDialog'},
			title: {value : '新增'},
			width: {value:500},
	        height: {value:250},
	        contextPath: {},
	        perId : {},
            userId : {},//登录用户ID
            userName : {},//登录用户名称
            orgId : {},//登录用户组织机构ID
            orgName : {},//登录用户组织机构名称
	        closeAction: {value: 'destroy'},//关闭时销毁加载到主页面的HTML对象
	        mask: {value: true},
	        events: {
	        	value: {
	        		/**
	        		 * 绑定保存按钮事件
	        		 */
	        		'completeAddSave' : true,
	        	}
	        }
		}
	});
	return ExerciseSchemeAdd;
});
