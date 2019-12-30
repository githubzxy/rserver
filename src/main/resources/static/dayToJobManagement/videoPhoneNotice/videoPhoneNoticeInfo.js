/**
 * 机房机架详情模块
 */
define('kmms/dayToJobManagement/videoPhoneNotice/videoPhoneNoticeInfo',['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var videoPhoneNoticeInfo = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
                {
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
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/videoPhoneAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						 $("#formContainer #noticeDateStr").val(data.noticeDateStr);
	                        $("#formContainer #backOrgName").val(data.backOrgName);
	            		    $('#formContainer #backPerson').val(data.backPerson);
	            		    $('#formContainer #noticeContent').val(data.noticeContent);
	            		    $('#formContainer #remark').val(data.remark);
                    }
          		}
			});
		},
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
            var childs = [
                          {
                              label : '时间：',
                              itemColspan : 2,
                              item : '<input type="text" style="border:none;width:98%;height:100%;" id="noticeDateStr" name="noticeDateStr"  readonly="readonly"/>'
                          },{
                              label : '反馈部门：',
                              itemColspan : 1,
                              item : '<input type="text" name="backOrgName" id="backOrgName"   readonly="readonly"/>'
                          },{
                              label : '反馈人：',
                              itemColspan : 1,
                              item : '<input type="text"  name="backPerson" id="backPerson"  style="width:99%" readonly="readonly"/>'
                          },{
                              label : '内容及处理情况：',
                              itemColspan : 2,
                              item : '<textarea style="border:none;width: 99%;resize: none;" id="noticeContent" name="noticeContent" maxlength="900" readonly="readonly">'
                          },{
                              label : '备注：',
                              itemColspan : 2,
                              item : '<textarea style="border:none;width: 99%;resize: none;" id="remark" name="remark" maxlength="900" readonly="readonly">'
                          }
            ];
			var form = new FormContainer({
				id : 'shiftShow',
				colNum : colNum,
				formChildrens : childs,
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'shiftInfoDialog'},
			title:{value:'通知详细信息'},
            width:{value:620},
            height:{value:300},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return videoPhoneNoticeInfo;
});