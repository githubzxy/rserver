/**
 * 新增
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/deviceNameWorkManage/deviceNameWorkManageAdd',
		['bui/common',
		'bui/data',
		 'common/form/FormContainer',
		 'bui/uploader',
		 'common/uploader/UpdateUploader',
		 'common/data/PostLoad',
		 ],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var	Uploader = r('bui/uploader');
	var	UpdateUploader = r('common/uploader/UpdateUploader');
	var	PostLoad = r('common/data/PostLoad');
	var deviceNameWorkManagePageNewAdd = BUI.Overlay.Dialog.extend({
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
			_self._initYearOrMonth();
			//_self._getLines();
			//_self._getStation();
		},

		/**
		 * 绑定事件
		 */
		bindUI: function(){
			var _self = this;
			//标识下拉选触发
            $("#yearOrMonth").on('change',function() {
                var yearOrMonth = $("#yearOrMonth").val();
                $("#type").empty();
                _self._initType(yearOrMonth);

            });
            //类别下拉触发
            $("#type").on('change',function() {
                var type = $("#type").val();
                _self._initCountYear(type);

            });
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
//			//添加资料名称悬浮提示
//    		$('#accountNameAdd').on('mouseover',function(){
//    			var str=$('#accountNameAdd').val();
//    			$('#accountNameAdd').attr('title',str);
//    		});
//    		//添加施工地点悬浮提示
//    		$('#place').on('mouseover',function(){
//    			var str=$('#place').val();
//    			$('#place').attr('title',str);
//    		});
		},
		
		/**
		 * 初始化表单
		 */
		_initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
				{
					label: '设备名称：',
					redStarFlag: true,
					itemColspan: 2,
					item: '<input type="text" name="deviceName" id="deviceName" style="width:99.5%;" data-rules="{required:true,maxlength:200}"/>'
				},{
					label: '工作内容：',
					redStarFlag: true,
					itemColspan: 2,
					item : '<textarea id="workContent" name="workContent" style="width:99.5%;height:80px;overflow-y:auto" data-rules="{required:true}"/>'
				},{
					label: '标识：',
					redStarFlag: true,
					itemColspan: 1,
					item : '<select id="yearOrMonth" name="yearOrMonth" style="width:99.5%" data-rules="{required:true}"/>'
				},{
					label: '类别：',
					redStarFlag: true,
					itemColspan: 1,
					item : '<select id="type" name="type" style="width:99.5%" data-rules="{required:true}"/>'
				},{
					label: '周期：',
					itemColspan: 1,
					item : '<input type="text" id="countYear" name="countYear" />'
				},{
					label: '单位：',
					itemColspan: 1,
					item : '<input type="text" id="unit" name="unit" />'
				},{
					label: '备注：',
					itemColspan : 2,
					item:'<textarea name="remark" id="remark" style="width:99.5%;height:100px;overflow-y:auto" maxlength="200" placeholder="最多输入200字"/>'
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
        _initCountYear:function(type){
        	if(type=='检修（月）'){
        		$("#countYear").val("12");
        	}else if(type=='检修（双月）'){
        		$("#countYear").val("6");
        	}else if(type=='检修（季度）'){
        		$("#countYear").val("4");
        	}else if(type=='检修（半年）'){
        		$("#countYear").val("2");
        	}else if(type=='检修（年）'){
        		$("#countYear").val("1");
        	}else if(type=='整修'){
        		$("#countYear").val("根据需要");
        	}else if(type=='网管巡视（日）'){
        		$("#countYear").val("31");
        	}else if(type=='日常检修（周）'){
        		$("#countYear").val("4");
        	}else if(type=='日常检修（月）'){
        		$("#countYear").val("1");
        	}
        },
        _initType:function(yearOrMonth){
        	if(yearOrMonth=='year'){
        		$("#type").append("<option  value=''>请选择</option>");
        		$("#type").append("<option  value='检修（月）'>检修（月）</option>");
        		$("#type").append("<option  value='检修（双月）'>检修（双月）</option>");
        		$("#type").append("<option  value='检修（季度）'>检修（季度）</option>");
        		$("#type").append("<option  value='检修（半年）'>检修（半年）</option>");
        		$("#type").append("<option  value='检修（年）'>检修（年）</option>");
        		$("#type").append("<option  value='整修'>整修</option>");
        		return;
        	}
        	if(yearOrMonth=='month'){
        		$("#type").append("<option  value=''>请选择</option>");
        		$("#type").append("<option  value='网管巡视（日）'>网管巡视（日）</option>");
        		$("#type").append("<option  value='日常检修（周）'>日常检修（周）</option>");
        		$("#type").append("<option  value='日常检修（月）'>日常检修（月）</option>");
        		return;
        	}
        },
        _initYearOrMonth:function(){
        	$("#yearOrMonth").append("<option  value=''>请选择</option>");
        	$("#yearOrMonth").append("<option  value='year'>年表</option>");
        	$("#yearOrMonth").append("<option  value='month'>月表</option>");
        },
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
    		/*//获取上传文件
    		var uploadFile = _self._getUploadFileData();
    		data.uploadFile=JSON.stringify(uploadFile);*/
    		data.collectionName = _self.get('collectionName');
			data.userId = _self.get('userId');
			data.orgId=_self.get('orgId');
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/deviceNameWorkManageAction/addDevice',
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
			title: {value : '新增配置 '},
			width: {value:650},
	        height: {value:380},
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
	return deviceNameWorkManagePageNewAdd;
});