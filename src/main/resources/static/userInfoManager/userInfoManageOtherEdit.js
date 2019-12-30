/**
 * 编辑
 * @author Bili
 * @date 18-11-21
 */
define('kmms/userInfoManager/userInfoManageOtherEdit',['bui/common','common/form/FormContainer',
   'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var userInfoManageEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
                        var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
                    }
                },{
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
            var _self = this,shiftId = _self.get('shiftId');
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
            })
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
                              item : '<input type="text" name="number" id="number" style="width:99%" readonly/>'
                          },
                          {
                              label : '姓名：',
                              itemColspan : 1,
                              item : '<input type="text" name="staffName" id="staffName" style="width:99%" readonly/>'
                          },{
                              label : '性别：',
                              itemColspan : 1,
                              item : '<input type="text" name="gender" id="gender" style="width:99%" readonly/>'
                          },{
                              label : '职名：',
                              itemColspan : 1,
                              item : '<input type="text" name="position" id="position" style="width:99%" readonly/>'
                          },{
                              label : '车间/科室：',
                              itemColspan : 1,
                              item : '<input type="text" name="workshop" id="workshop" style="width:99%" readonly/>'
                          },{
                              label : '班组：',
                              itemColspan : 1,
                              item : '<input type="text" name="teamGroup" id="teamGroup" style="width:99%" readonly/>'
                          },{
                              label : '联系电话：',
                              itemColspan : 2,
                              item : '<input type="text" name="phoneNum" id="phoneNum" style="width:99%" />'
                          }
            ];
            var form = new FormContainer({
                id : 'userInfoManageEditForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'userInfoManageEditDialog'},
            elAttrs : {value: {id:"userInfoManageEdit"}},
            title:{value:'编辑'},
            width:{value:650},
            height:{value:240},
            contextPath : {},
            closeAction : {value:'destroy'},
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('userInfoManageEditForm'),delData=_self.get('delData');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/userInfoManageAction/updateOtherDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave",{
                                result : result
                            });
                        }
                    }
                }
            },
            events : {
                value : {
                    'completeAddSave' : true,
                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return userInfoManageEdit;
});