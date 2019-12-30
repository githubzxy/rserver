/**
 * 详情模块
 */
define('kmms/userInfoManager/userInfoManageInfo',['bui/common','common/form/FormContainer',
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
	                        $("#formContainer #birthday").val(data.birthday);
	            		    $('#formContainer #entryDate').val(data.entryDate); 
	            		    $("#formContainer #education").val(data.education);
	                        $("#formContainer #workshop").val(data.workshop);
	            		    $('#formContainer #teamGroup').val(data.teamGroup); 
	            		    $("#formContainer #position").val(data.position);
	                        $("#formContainer #phoneNum").val(data.phoneNum);
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
                              label : '劳资号：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="number" id="number" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '姓名：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="staffName" id="staffName" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '性别：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="gender" id="gender" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '出生日期：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="birthday" id="birthday"  readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '参加工作时间：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="entryDate" id="entryDate"  readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '学历：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="education" id="education" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '车间(科室)：',
                              redStarFlag : true,
                              itemColspan : 1,
                              item : '<input type="text" name="workshop" id="workshop" readonly style="width:99%" data-rules="{required:true}"/>'
                          },{
                              label : '班组：',
                              itemColspan : 1,
                              item : '<input type="text" name="teamGroup" id="teamGroup" readonly style="width:99%" />'
                          },{
                              label : '职名：',
                              itemColspan : 1,
                              item : '<input type="text" name="position" id="position" readonly style="width:99%" />'
                          },{
                              label : '联系电话：',
                              itemColspan : 1,
                              item : '<input type="text" name="phoneNum" id="phoneNum" readonly style="width:99%" />'
                          },{
                              label : '备注：',
                              itemColspan : 2,
                              item : '<textarea name="remark" id="remark" readonly style="width:99.5%;height:50px"/></textarea>'
                          }
            ];
			var form = new FormContainer({
				id : 'userInfoManageInfoShow',
				colNum : colNum,
				formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'userInfoManageInfoDialog'},
            previewUrl:{value:'/pageoffice/openPage?filePath='},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
			title:{value:'详细信息'},
            width:{value:610},
            height:{value:330},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return userInfoManageInfo;
});