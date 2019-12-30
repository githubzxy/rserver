/** 
 * 导入工作日志
 * @author zhouxingyu
 * @date 19-4-1
 */
define('kmms/productionManage/JobRecord/jobRecordImport',[
										'bui/overlay',
										'bui/uploader',
										'bui/mask',
										'common/data/PostLoad',
										'common/form/FormContainer'
										],function(require){
	var Overlay = require('bui/overlay'),
	Uploader = require('bui/uploader'),
	Mask = require('bui/mask'),
	PostLoad = require('common/data/PostLoad'),
	FormContainer = require('common/form/FormContainer');
	var jobRecordImport = Overlay.Dialog.extend({
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
				window.location.href = '/kmms/exportModel/jobRecord.xls';
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
					item : '<input class="button button-small" id="downLoadTemplate" type="button" value="工作日志模板下载" style="width:50%;height:30px">'
						+ '<span style="color:red;width:50%;height:30px;text-align: center;float:right;line-height: 30px;" >注：请按工作日志模板导入</span>'
						+ '<input type="hidden" id="perId" name="perId"/>'
				},{
					label : '附件',
					itemColspan : 2,
					redStarFlag : true,
					item : '<input type="file" data-rules="{required:true}" id="importFile" name="file_excel" style="height:60px;width:100%" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>'
						+ '<input type="hidden" id="userId" name="userId"/>'
						+ '<input type="hidden" id="userName" name="userName"/>'
						+ '<input type="hidden" id="orgId" name="orgId"/>'
						+ '<input type="hidden" id="orgName" name="orgName"/>'
						+ '<input type="hidden" id="parentId" name="parentId"/>'
						+ '<input type="hidden" id="currentDay" name="currentDay"/>'
				}
			];
			var form = new FormContainer({
				id : 'jobRecordImport',
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
			elAttrs : {value: {id:"jobRecordImport"}},
			title : {value : '导入工作日志信息'},
			width : {value : 600},
			height : {value : 200},
			mask : {value : true},//非模态弹出框
			contextPath : {},
			perId : {},
			userId:{},
			orgId:{},
			parentId:{},
			userName:{},
			orgName:{},
			currentDay:{},
			closeAction : {
				value : 'destroy'
			},
			events : {
                value : {'completeAddSave' : true}
            },
			success : {
				value : function(){
					var _self = this,contextPath = _self.get('contextPath'),
					perId = _self.get('perId'),
					userId = _self.get('userId'),
                	orgId= _self.get('orgId'),
                	parentId= _self.get('parentId'),
                	userName= _self.get('userName'),
                	currentDay= _self.get('currentDay'),
					orgName= _self.get('orgName');
					var importForm=_self.getChild('jobRecordImport',true);
					if(!importForm.isValid()){
						return;
					}
					// 验证上传文件,通过则将form提交
					if(_self.fileValidate($('#importFile'))){
						$('#formContainer #userId').val(userId);
						$('#formContainer #orgId').val(orgId);
						$('#formContainer #parentId').val(parentId);
						$('#formContainer #userName').val(userName);
						$('#formContainer #orgName').val(orgName);
						$('#formContainer #currentDay').val(currentDay);
						$('#formContainer #perId').val(perId);
						var formData = new FormData(importForm.get('el')[0])
						var xhr = new XMLHttpRequest();
						xhr.open("POST","/kmms/jobRecordAction/Import");
						xhr.send(formData);
						xhr.onload = function(e){
							if(e.target.response){
								var result = JSON.parse(e.target.response);
								_self.fire("completeAddSave",{
	                                result : result
	                            });
							}
						}
					}
			    }
			}
		}
	});
	return jobRecordImport;
});