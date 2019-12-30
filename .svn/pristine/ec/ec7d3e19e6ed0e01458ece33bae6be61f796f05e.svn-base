/**
 * 重点工作完成情况详情模块
 */
define('kmms/dayToJobManagement/duanTraceSafetyInfomation/duanTraceSafetyInfomationInfo',['bui/common','common/form/FormContainer',
	'common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		FormContainer= r('common/form/FormContainer');
	var duanTraceSafetyInfomationInfo = BUI.Overlay.Dialog.extend({
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
				url:'/kmms/duanTraceSafetyInfomationAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						 $("#formContainer #noticeDateStr").val(data.noticeDateStr);
	            		    $('#formContainer #noticeContent').val(data.noticeContent);
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
                              label : '内容及处理情况：',
                              itemColspan : 2,
                              item : '<textarea style="border:none;width: 99%;resize: none;" id="noticeContent" name="noticeContent" maxlength="900" readonly="readonly">'
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
			title:{value:'情况详细信息'},
            width:{value:620},
            height:{value:200},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return duanTraceSafetyInfomationInfo;
});