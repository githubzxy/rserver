/**
 * 系统线路新增
 * @author zhouxingyu
 * @date 19-8-14
 */
define('kmms/technicalManagement/systemDevice/systemDeviceAdd',
		['bui/common',
		'bui/data',
		 'common/form/FormContainer',
		 'bui/uploader',
		 'common/uploader/UpdateUploader',
		 'common/data/PostLoad',
		 'bui/tooltip'
		 ],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var	Uploader = r('bui/uploader');
	var Tooltip = r('bui/tooltip');
	var	UpdateUploader = r('common/uploader/UpdateUploader');
	var	PostLoad = r('common/data/PostLoad');
	var systemDevicePageNewAdd = BUI.Overlay.Dialog.extend({
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
		},

		/**
		 * 绑定事件
		 */
		bindUI: function(){
			var _self = this;
			//隐藏本页面单选框里的第一个空白框
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
		 * 初始化表单
		 */
		_initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
				{
					label: '系统：',
					redStarFlag: true,
					itemColspan: 2,
					item: '<input type="text" name="system" id="system" data-rules="{required:true,maxlength:200}"/>'
				},{
					label: '设备：',
					itemColspan: 2,
					item : '<input  type="text" id="deviceType" name="deviceType" data-rules="{required:true,maxlength:200}"/>'
				},{
					label: '备注：',
					itemColspan : 2,
					item:'<textarea name="remark" id="remark" style="width:99.5%;height:100px;overflow-y :auto" maxlength="250" placeholder="最多输入250字"/>'
				}
            ];
            var form = new FormContainer({
                id : 'fileFormAdd',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
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
    		//var uploadFile = _self._getUploadFileData();
    		//data.uploadFile=JSON.stringify(uploadFile);
    		//data.collectionName = _self.get('collectionName');
			//data.userId = _self.get('userId');
			//data.orgId=_self.get('orgId');
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/systemDeviceAction/addInfo',
				el : _self.get('el'),
				loadMsg : '保存中...'
			}); 
    		
			postLoad.load(data, function(result){
				
				if(result.status==2){
					//消息提示框
					BUI.Message.Confirm(result.msg,null,'error');
				}else if(result != null){
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
			width: {value:650},
	        height: {value:280},
	        contextPath: {},
	        userId:{},
            orgId:{},
	        closeAction: {value: 'destroy'},//关闭时销毁加载到主页面的HTML对象
	        mask: {value: true},
	        events: {
	        	value: {
	        		'completeAddSave' : true,
	        	}
	        }
		}
	});
	return systemDevicePageNewAdd;
});