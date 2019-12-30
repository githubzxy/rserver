/**
 * 导入施工维修信息表单
 */
define('kmms/dayToJobManagement/constructRepair/ConstructRepairImport',[
										'bui/overlay',
										'bui/mask',
										'common/form/FormContainer'
										],function(require){
	var Overlay = require('bui/overlay'),
	Mask = require('bui/mask'),
	FormContainer = require('common/form/FormContainer');
	var ConstructRepairImport = Overlay.Dialog.extend({
		//初始化弹框控件
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		//绑定事件
		bindUI : function(){
			var _self = this;
			contextPath = _self.get('contextPath');
			//定义按键
			var buttons = [
               {
                  text:'导入',
                  elCls : 'button',
                  handler : function(){
                	  var success = _self.get('success');
			            if(success){
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
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
			var childs = [
				{
					label : '附件',
					itemColspan : 2,
					redStarFlag : true,
					item : '<input type="file" data-rules="{required:true}" id="importFile" name="file_excel" style="height:60px;width:100%" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>'
						+ '<input type="hidden" id="userId" name="userId"/>'
				}
			];
			var form = new FormContainer({
				id : 'onDutyAllImport',
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
			elAttrs : {value: {id:"onDutyAllImport"}},
			title : {value : '导入施工维修信息'},
			width : {value : 617},
//			collectionName:{},
			height : {value : 170},
			mask : {value : true},//非模态弹出框
			contextPath : {},
			userId:{},
			perId : {},
			closeAction : {
				value : 'destroy'
			},
			success : {
				value : function(){
					var _self = this,contextPath = _self.get('contextPath'),userId = _self.get('userId');
					var importForm=_self.getChild('onDutyAllImport',true);
					if(!importForm.isValid()){
						return;
					}
					// 验证上传文件,通过则将form提交
					if(_self.fileValidate($('#importFile'))){
						// 设置Ation,提交路径
						$('#formContainer').attr('action', '/kmms/constructRepairAction/wxImport');
						// 设置method,提交方式
						$('#formContainer').attr('method', 'post');
						// 设置enctype,编码方式
						$('#formContainer').attr('enctype', 'multipart/form-data');
						
						$('#formContainer #userId').val(userId);
						
						$("#formContainer").submit();	
						
						_self.close();
					}
			    }
			}
		}
	});
	return ConstructRepairImport;
});