/**
 * 导入年月表
 */
define('yearMonthPlan/yearMonthPuTie/compile/WorkAreaPuTieYearMonthImport',[
										'bui/overlay',
										'bui/mask',
										'common/form/FormContainer',
										'common/uploader/UpdateUploader',
										'common/data/PostLoad',
										],function(require){
	var Overlay = require('bui/overlay'),
	Mask = require('bui/mask'),
	FormContainer = require('common/form/FormContainer'),
	PostLoad = require('common/data/PostLoad'),
	UpdateUploader = require('common/uploader/UpdateUploader');
	var WorkAreaPuTieYearMonthImport = Overlay.Dialog.extend({
		//初始化弹框控件
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
//			$("#WorkAreaPuTieYearMonthImport .bui-ext-close").css("display","none");
			var _self=this;
			_self._initUploader();
		},
		//绑定事件
		bindUI : function(){
			var _self = this;
			contextPath = _self.get('contextPath');
			/**
			 * 下载模板方法
			 */
//			$('#downLoadTemplate').on('click',function(){
//				window.location.href = '/kmms/exportModel/floodGuardPointTemp.xls';
//			});
			$('#downLoadTemplateYear').on('click',function(){
				window.location.href = '/kmms/exportModel/kmmsYearTable.xls';
			});
			$('#downLoadTemplateMonth').on('click',function(){
				window.location.href = '/kmms/exportModel/kmmsMonthTable.xls';
			});
			//定义按键
			var buttons = [
               {
                  text:'导入',
                  elCls : 'button',
                  handler : function(){
                	  var loadMask = new Mask.LoadMask({
   	    	           el : _self.get('el'),
   	    	           msg : '正在导入....'
   	    		    });
                	  var success = _self.get('success');
			            if(success){
			              loadMask.show();
			              success.call(_self);
//			              this.close();
			            }
                  }
                },{
                  text:'取消',
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
		
		_initUploader:function(){
			var _self = this;
			//上传附件
			var uploaderYear = new UpdateUploader({
				render : '#formContainer #uploadfileYear',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        isSuccess : function(result){
					return true;
		        },
			});
			uploaderYear.render();
			_self.set('uploaderYear',uploaderYear);
			
			var uploaderMonth = new UpdateUploader({
				render : '#formContainer #uploadfileMonth',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        isSuccess : function(result){
					return true;
		        },
			});
			uploaderMonth.render();
			_self.set('uploaderMonth',uploaderMonth);
		},
		
		/**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData:function(uploader){
			var _self = this,uploader = _self.get(uploader);
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
					label : '填表规则备注',
					itemColspan : 2,
					item : '<span style="height:120px;text-align: left;float:right;line-height: 20px;" >周期与设备名称为必填，检修工作项目不用填写为空即可，上传后根据周期与设备名称信息系统自动匹配对应的检修工作项目。当周期与设备名称填写有误，有误的判断：周期内容不规范，设备名称不规范，即周期与设备名称在设备项目与周期配置（年月报表工作内容配置）模块中没有对应数据时则无法导入成功，周期与设备名称两个数据的组合将匹配一个检修工作项目内容，因此周期与设备名称的组合可以参考设备项目与周期配置（年月报表工作内容配置）模块中的信息以进行正确的填写。</span>'
				},
				{
					label : '年表模板',
					itemColspan : 2,
					item : '<input class="button button-small" id="downLoadTemplateYear" type="button" value="年表模板下载" style="width:50%;height:30px">'
						+ '<span style="color:red;width:50%;height:30px;text-align: center;float:right;line-height: 30px;" >注：请按年表模板导入</span>'
//						+ '<input type="hidden" id="perId" name="perId"/>'
				},
				{
					label : '年表附件',
					itemColspan : 2,
					redStarFlag : true,
//					item : '<input type="file" data-rules="{required:true}" id="importFile" name="file_excel" style="height:60px;width:100%" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>'
					item : '<div name="uploadfileYear" id="uploadfileYear" style="height:60px;overflow-y:auto"></div>'
				},
				{
					label : '月表模板',
					itemColspan : 2,
					item : '<input class="button button-small" id="downLoadTemplateMonth" type="button" value="月表模板下载" style="width:50%;height:30px">'
						+ '<span style="color:red;width:50%;height:30px;text-align: center;float:right;line-height: 30px;" >注：请按月表模板导入</span>'
				},
				{
					label : '月表附件',
					itemColspan : 2,
					redStarFlag : true,
//					item : '<input type="file" data-rules="{required:true}" id="importFile" name="file_excel" style="height:60px;width:100%" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>'
					item : '<div name="uploadfileMonth" id="uploadfileMonth" style="height:60px;overflow-y:auto"></div>'
				}
			];
			var form = new FormContainer({
				id : 'WorkAreaPuTieYearMonthImport',
				colNum : colNum,
				formChildrens : childs
			});
			return form;
		},
		/**
         * 文件名验证
         */
        fileValidate : function(fileObj){ 
        	var _self = this;
        	var flag = true;
        	// 上传文件不能为空，格式必须为xsl和xlsx
        	var filepath = fileObj.val(); 
        		//验证最后一个后缀是否是Excel文件
        		var fileArr = filepath.split("\\"); 
        		var fileTArr = fileArr[fileArr.length-1].toLowerCase().split("."); 
        		var filetype = fileTArr[fileTArr.length-1]; 
        		if(filetype != "xls" && filetype != "xlsx"){
        			$(".bui-ext-mask").addClass("fileMask");// 添加遮罩层
        			BUI.Message.Alert('请选择*.xls;*.xlsx文件,进行导入！',function(){
            			$(".bui-ext-mask").removeClass("fileMask");// 移除遮罩层
            		});
        			flag = false; 
//        		}
        	}
        	//返回验证标记
        	return flag;
        }
	},{
		ATTRS : {//定义弹框属性
			elAttrs : {value: {id:"WorkAreaPuTieYearMonthImport"}},
			title : {value : '导入年月表'},
			width : {value : 600},
//			collectionName:{},
			height : {value : 410},
			mask : {value : true},//非模态弹出框
			contextPath : {},
			userId:{},
			perId : {},
			user : {},
			id : {},//年月表主键
			name : {},//年月表名称
			year : {},//年份
			closeAction : {
				value : 'destroy'
			},
			success : {
				value : function(){
					var _self = this,contextPath = _self.get('contextPath'),userId = _self.get('userId');
//					var importForm=_self.getChild('WorkAreaPuTieYearMonthImport',true);
//					if(!importForm.isValid()){
//						return;
//					}
					var formAdd = _self.getChild('WorkAreaPuTieYearMonthImport');
					//获取上传文件
            		var uploadFileArrYear = _self._getUploadFileData('uploaderYear');
            		var uploadFileArrMonth = _self._getUploadFileData('uploaderMonth');
            		
            		if(uploadFileArrYear.length!=1||uploadFileArrMonth.length!=1){
	        			Mask.maskElement('#WorkAreaPuTieYearMonthImport');
	        			BUI.Message.Alert("请年表和月表各上传一个附件！",function(){
	        				Mask.unmaskElement('#WorkAreaPuTieYearMonthImport');
	        			},'error');
//	        			$("#WorkAreaPuTieYearMonthImport .bui-ext-close").css("display","none");
	        			return;
	        		}
            		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	    	    	
	    	    	data.uploadFileArrYear=JSON.stringify(uploadFileArrYear);
	    	    	data.uploadFileArrMonth=JSON.stringify(uploadFileArrMonth);
	    	    	data.uploadFileYearPath=uploadFileArrYear[0].path;
	    	    	data.uploadFileMonthPath=uploadFileArrMonth[0].path;
	    	    	data.id=_self.get('id');
	        		data.name=_self.get('name');
	        		data.year=_self.get('year');
	        		data.userString = JSON.stringify(_self.get('user'));
                    var pl = new PostLoad({
        				url : '/zuul/kmms/yearMonthPuTieGQAction/importReport',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeImport",{
                                result : e
                            });
                        }
        			});
            		
//	                    var formData = new FormData(importForm.get('el')[0]);
//	                    formData.append('collectionName',_self.get('collectionName'));
//	                    formData.append('userId',_self.get('userId'));
//	                    var xhr = new XMLHttpRequest();
//	                    xhr.open("POST", "/kmms/floodGuardPointAction/Import");
//	                    xhr.send(formData);
//	                    xhr.onload = function (e) {
//	                        if (e.target.response) {
//	                            var result = JSON.parse(e.target.response);
//	                            _self.fire("completeImport",{
//	                                result : result
//	                            });
//	                        }
//	                    }

	                }
	            },
	            events : {
	                value : {
	                    'completeImport' : true,
	                }
	            },
	            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
	            rootOrgText:{value:'昆明通信段'},
	        }
	});
	return WorkAreaPuTieYearMonthImport;
});