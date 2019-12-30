/**
 * 编辑
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dayToJobManagement/duanTraceSafetyInfomation/duanTraceSafetyInfomationEdit',['bui/common','common/form/FormContainer',
   'bui/form',],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer');
    var duanTraceSafetyInfomationEdit = BUI.Overlay.Dialog.extend({
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
            //定义按键
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
                              label : '日期：',
                              redStarFlag : true,
                              itemColspan : 2,
                              item : '<input type="text" name="noticeDateStr" id="noticeDateStr" style="width:99%" class="calendar calendar-time" data-rules="{required:true}" readonly/>'
                          },{
                              label : '内容及处理情况：',
                              itemColspan : 2,
                              item : '<textarea style="border:none;width: 99%;resize: none;" id="noticeContent" name="noticeContent" maxlength="900">'
                          }
            ];
            var form = new FormContainer({
                id : 'DuanAdd',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'shiftAddDialog'},
            elAttrs : {value: {id:"shiftAdd"}},
            title:{value:'编辑情况'},
            width:{value:620},
            height:{value:200},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('DuanAdd');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/duanTraceSafetyInfomationAction/updateDoc");
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
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return duanTraceSafetyInfomationEdit;
});