/**
 * 进出机房新增模块
 * @author zhouxingyu
 * @date 19-5-30
 */
define('/kmms/technicalManagement/visitRoomRecords/visitRoomRecordsAdd',
		['bui/common',
		'bui/data',
		 'common/form/FormContainer',
		 'bui/uploader',
		 'common/uploader/UpdateUploader',
		 'common/data/PostLoad',
		 'kmms/technicalManagement/visitRoomRecords/SelectSuggest'
		 ],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var	Uploader = r('bui/uploader');
	var	UpdateUploader = r('common/uploader/UpdateUploader');
	var	PostLoad = r('common/data/PostLoad');
	var SelectSuggest = r("kmms/technicalManagement/visitRoomRecords/SelectSuggest");
	var visitRoomRecordsAdd = BUI.Overlay.Dialog.extend({
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
			//_self._initUploader();
//			_self._initRailAndRoom();
			_self._getLines();
			_self._getStation();
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
		 * 初始化表单
		 */
		_initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
				{
					label: '线别：',
					redStarFlag: true,
					itemColspan: 1,
					item : '<div id="railLineDialog" name="railLineDialog" style="width: 200px;"/>'
				},{
					label: '机房：',
					redStarFlag: true,
					itemColspan: 1,
					item : '<div id="machineRoomDialog" name="machineRoomDialog" style="width: 200px;"/>'
				},
				{
					label: '日期：',
					redStarFlag: true,
					itemColspan: 1,
					item: '<input type="text" name="date" id="date" class="calendar"  />'
				},
				{
					label: '进入时间：',
					redStarFlag: true,
					itemColspan: 1,
					item: '<input type="text" name="visitDate" id="visitDate" class="calendar calendar-time"  />'
				},
				{
					label: '进入的人员姓名：',
					itemColspan: 2,
					item: '<input type="text" name="visitName" id="visitName"   >'
				},
				{
					label: '单位：',
					itemColspan: 2,
					item: '<input type="text" name="visitFrom" id="visitFrom"   >'
				},
				{
					label: '工作内容：',
					itemColspan: 2,
					item: '<input type="text" name="jobContent" id="jobContent"   >'
				},
				{
					label: '接待人：',
					itemColspan: 1,
					item: '<input type="text" name="helper" id="helper"   >'
				},
				{
					label: '离开时间：',
					itemColspan: 1,
					item: '<input type="text" name="leaveDate" id="leaveDate"  class="calendar calendar-time" />'
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
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        },
//		_initFormContainer: function(){
//			var form = new FormContainer({
//				id: 'fileFormAdd',
//				colNum: 2,
////				elStyle: { overflowY: 'scroll'},
//				formChildrens:[
//				{
//					label: '资料名称：',
//					redStarFlag: true,
//					itemColspan: 2,
//					item: '<input type="text" name="name" id="name" data-rules="{required:true,maxlength:200}"/>'
//				},{
//					label: '线别：',
//					itemColspan: 1,
//					item : '<div id="railLineDialog" name="railLineDialog" />'
//				},{
//					label: '机房：',
//					itemColspan: 1,
//					item : '<div id="machineRoomDialog" name="machineRoomDialog"/>'
//				},{
//					label: '附件：',
//					itemColspan: 2,
//					item: '<div name="uploadFile" id="uploadFile"  style="height:100px;overflow-y:auto"></div>'
//				},{
//					label: '备注：',
//					itemColspan : 2,
//					item:'<textarea name="remark" id="remark" style="width:99.5%;height:95px;overflow-y :auto" maxlength="250" placeholder="最多输入250字"/>'
//				}] 
//			});
//			
//			return form;
//		},
		/**
		 * 初始化上传文件
		 */
		_initUploader:function(){
//			var _self = this;
//			var contextPath = _self.get('contextPath');
//			Uploader.Theme.addTheme('uploadView', {//自定义附件组件的显示主题
//				elCls: 'defaultTheme',
//				queue:{
//					//结果的模板，可以根据不同状态进进行设置
//					resultTpl: {
//						'default': '<div class="default">{name}</div>',
//						'success': '<div class="success"><label class="fileLabel" href="#" title={name}>{name}</label></div>',
//						'error': '<div class="error"><span title="{name}"></span><span class="uploader-error">{msg}</span></div>',
//						'progress': '<div class="progress"><div class="bar" style="width:{loadedPercent}%"></div></div>'
//					}
//				}
//			});
//			//上传附件
//			var uploader = new Uploader.Uploader({
//				render: '#uploadFile',
//				theme: 'uploadView',//使用自定义主题
//				url: '/zuul/kmms/atachFile/upload.cn',
//				isSuccess: function(result){
//					return true;
//				},
//				rules: {
//		            //文的类型
//		            ext: ['.doc,.docx,.xls,.xlsx,.ppt,.pptx,.vsd,.pdf,.jpg,.tif,.dwg','文件类型只能为{0}'],
//		            //文件大小的最小值,这个单位是kb
//		            minSize: [0, '文件的大小不能小于{0}KB'],
//		            //文件大小的最大值,单位也是kb
//		            maxSize: [1024 * 1024, '文件大小不能大于1G']
//				}
//			});
//			uploader.render();
//			_self.set('uploader',uploader);
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
         * 获取线别
         */
        _getLines:function(){
        	var _self=this;
        	var nameData = [];
        	$.ajax({
        		url:'/kmms/networkManageInfoAction/getLines',
        		type:'post',
        		dataType:"json",
        		success:function(res){
        			for(var i=0;i<res.length;i++){
        				nameData.push(res[i]);
        			}
        		}
        	});
        	
        	var suggest = new SelectSuggest({
                renderName: '#formContainer #railLineDialog',
                inputName: 'railLineDialog',
                renderData: nameData,
                width: 222
            });
        	
        },
        
        _getStation:function(){
        	var _self=this;
        	var nameData = [];
        	$.ajax({
        		url:'/kmms/networkManageInfoAction/getStationNoCondition',
        		type:'post',
        		dataType:"json",
        		success:function(res){
        			for(var i=0;i<res.length;i++){
        				nameData.push(res[i]);
        			}
        		}
        	});
        	
        	var suggest = new SelectSuggest({
                renderName: '#formContainer #machineRoomDialog',
                inputName: 'machineRoomDialog',
                renderData: nameData,
                width: 222
            });
        },
//		 /**
//         * 初始化线别
//         */
//        _initRailAndRoom:function(){
//        	var _self = this;
//			var nameData = [];
//			var postLoad = new PostLoad({
//				url:'/kmms/constructCooperateAction/getLines',
//			});
//			postLoad.load({},function(result){
//				if(result==null){
//					return;
//				}
//				for(var i=0;i<result.length;i++){
//					nameData.push(result[i]);
//				}
//			});
//			/**
//			 * 初始化线别
//			 */
//			var suggest = new SelectSuggest({
//				renderName : '#railLineDialog',
//				inputName : 'railLineDialog',
//				renderData : nameData,
//				width:510
//			});
//			/**
//			 * 初始化机房
//			 */
//			var suggest2 = new SelectSuggest({
//				renderName : '#machineRoomDialog',
//				inputName : 'machineDialog',
//				renderData : [],
//				width:510
//			});
//        },
//        /**
//         * 选择线别_initRoom初始化机房数据
//         */
//        _initMachineRoom:function(railLine){
//				var nameData = [];
//    			var postLoad = new PostLoad({
//    				url:'/kmms/technicalDocumentAction/getMachineRooms',
//    			});
//    			postLoad.load({railLine:railLine},function(result){
//    				if(result==null){
//    					return;
//    				}
//    				for(var i=0;i<result.length;i++){
//    					nameData.push(result[i]);
//    				}
//    			});
//    			var suggest = new SelectSuggest({
//    				renderName : '#machineRoomDialog',
//    				inputName : 'machineRoomDialog',
//    				renderData : nameData,
//    				width:510
//    			});
//    			
//
//        },
        
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
        
//		/**
//		 * 获取上传文件数据(上传的)
//		 */
//		getUploadFileData:function(){
//			var _self = this,uploader = _self.get('uploader');
//			var arr = new Array(); //声明数组,存储数据发往后台
//			//获取上传文件的队列
//			var fileArray = uploader.get("queue").getItems();
//			for(var i in fileArray){
//		    	var dto = new _self.UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
//				arr[i] = dto; // 向集合添加对象
//			};
//			return arr;
//		},
//		/**
//		  * 声明上传文件对象
//		  * @param name 上传文件名
//		  * @param path 上传文件路径
//		  */
//		UploadFileDto: function(name,path){
//			this.name = name;
//			this.path = path;
//		},
		
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
    		data.collectionName = _self.get('collectionName');
			data.userId = _self.get('userId');
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/visitRoomRecordsAction/addTechnical',
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
			title: {value : '新增资料'},
			width: {value:650},
	        height: {value:400},
	        contextPath: {},
	        closeAction: {value: 'destroy'},//关闭时销毁加载到主页面的HTML对象
	        mask: {value: true},
	        events: {
	        	value: {
	        		'completeAddSave' : true,
	        	}
	        }
		}
	});
	return visitRoomRecordsAdd;
});