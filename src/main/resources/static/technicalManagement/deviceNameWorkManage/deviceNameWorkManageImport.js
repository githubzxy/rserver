/** 
 * 导入年月表工作内容配置
 * @author zhouxingyu
 * @date 19-12-1
 */
define('kmms/technicalManagement/deviceNameWorkManage/deviceNameWorkManageImport',
		[
	'bui/overlay',
	'bui/uploader',
	'bui/mask',
	'common/form/FormContainer',
	],function(require){
	var Overlay = require('bui/overlay'),
	Uploader = require('bui/uploader'),
	Mask = require('bui/mask'),
	FormContainer = require('common/form/FormContainer');
	var deviceNameWorkManageImport = BUI.Overlay.Dialog.extend({
		//初始化弹框控件
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		//绑定事件
		bindUI : function(){
			var _self = this;
			contextPath = _self.get('contextPath');
			/**
			 * 下载模板方法
			 */
			$('#downLoadTemplate').on('click',function(){
				window.location.href = '/kmms/exportModel/deviceNameWork.xls';
			});
			//定义按键
			var buttons = [
               {
                  text:'导入',
                  elCls : 'button',
                  handler : function(){
                	  var success = _self.get('success');
			            if(success){
			              success.call(_self);
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
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
			var childs = [
				{
					label : '标准模板',
					itemColspan : 2,
					item : '<input class="button button-small" id="downLoadTemplate" type="button" value="年月表工作内容模板下载" style="width:50%;height:30px">'
						+ '<span style="color:red;width:50%;height:30px;text-align: center;float:right;line-height: 30px;" >注：请按年月表工作内容模板导入</span>'
						+ '<input type="hidden" id="perId" name="perId"/>'
				},{
					label : '附件',
					itemColspan : 2,
					redStarFlag : true,
					item : '<input type="file" data-rules="{required:true}" id="importFile" name="file_excel" style="height:60px;width:100%" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>'
				}
			];
			var form = new FormContainer({
				id : 'deviceNameWorkManageImport',
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
        	var filepath = fileObj.val(); 
        		var fileArr = filepath.split("\\"); 
        		var fileTArr = fileArr[fileArr.length-1].toLowerCase().split("."); 
        		var filetype = fileTArr[fileTArr.length-1]; 
        		if(filetype != "xls" && filetype != "xlsx"){
        			$(".bui-ext-mask").addClass("fileMask");// 添加遮罩层
        			BUI.Message.Alert('请选择*.xls;*.xlsx文件,进行导入！',function(){
            			$(".bui-ext-mask").removeClass("fileMask");// 移除遮罩层
            		});
        			flag = false; 
        	}
        	return flag;
        }
	},{
		ATTRS : {//定义弹框属性
			elAttrs : {value: {id:"deviceNameWorkManageImport"}},
			title : {value : '导入年月表配置'},
			width : {value : 617},
			height : {value : 200},
			mask : {value : true},//非模态弹出框
			contextPath : {},
			perId : {},
			userId:{},
			closeAction : {
				value : 'destroy'
			},
			success : {
				value : function(){
					var _self = this,contextPath = _self.get('contextPath'),perId = _self.get('perId'),userId = _self.get('userId');
					var importForm=_self.getChild('deviceNameWorkManageImport',true);
					if(!importForm.isValid()){
						return;
					}
                    var formData = new FormData(importForm.get('el')[0]);
                    formData.append('userId',userId);
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/deviceNameWorkManageAction/Import");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeImport",{
                                result : result
                            });
                        }
                    }
					
					
			    }
			}
		}
	});
	return deviceNameWorkManageImport;
});