/**
 * 详情（劳人科以外的部门使用）
 */
define('kmms/userInfoManager/userInfoManageOtherInfo',['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var userInfoManageInfo = BUI.Overlay.Dialog.extend({
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
				url:'/kmms/userInfoManageAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						 	$("#formContainer #number").val(data.number);
	            		    $('#formContainer #staffName').val(data.staffName);
	            		    $("#formContainer #gender").val(data.gender);
	                        $("#formContainer #workshop").val(data.workshop);
	                        $("#formContainer #teamGroup").val(data.teamGroup);
	            		    $("#formContainer #position").val(data.position);
	                        $("#formContainer #phoneNum").val(data.phoneNum);
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
                              label : '劳资号：',
                              itemColspan : 1,
                              item : '<input type="text" name="number" id="number" readonly style="width:99%" data-rules="{required:true}"/>'
                          },
                          {
                              label : '姓名：',
                              itemColspan : 1,
                              item : '<input type="text" name="staffName" id="staffName" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '性别：',
                              itemColspan : 1,
                              item : '<input type="text" name="gender" id="gender" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '职名：',
                              itemColspan : 1,
                              item : '<input type="text" name="position" id="position" readonly style="width:99%" />'
                          },{
                              label : '车间/科室：',
                              itemColspan : 1,
                              item : '<input type="text" name="workshop" id="workshop" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '班组：',
                              itemColspan : 1,
                              item : '<input type="text" name="teamGroup" id="teamGroup" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '联系电话：',
                              itemColspan : 2,
                              item : '<input type="text" name="phoneNum" id="phoneNum" readonly style="width:99%" />'
                          }
            ];
			var form = new FormContainer({
				id : 'userInfoManageInfoShow',
				colNum : colNum,
				formChildrens : childs,
			});
			_self.set('formContainer',form);
			return form;
		},
	},{
		ATTRS : {
			id : {value : 'userInfoManageInfoDialog'},
			title:{value:'详细信息'},
            width:{value:650},
            height:{value:240},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return userInfoManageInfo;
});