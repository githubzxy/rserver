/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/productionManage/dayDutyManage/dayDutyManageAdd',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','common/data/PostLoad',],function(r){
    var BUI = r('bui/common'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer');
    var dayDutyManageAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
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
        	
            var _self = this,date = _self.get('date');
            if(_self.get('docId')!=null&&_self.get('docId')!=""){
            $.ajax({
                url:'/kmms/dayDutyManageAction/findByDate',
                data:{date: date,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #leader").val(data.leader);
            		    $('#formContainer #cadre').val(data.cadre);
            		    $("#formContainer #dispatch").val(data.dispatch);
                    }
                }
            })
            }
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '值班领导：',
                    itemColspan : 2,
                    item : '<input type="text" name="leader" id="leader" style="width:99%"/>'
                },{
                    label : '值班干部：',
                    itemColspan : 2,
                    item : '<input type="text" name="cadre" id="cadre" style="width:99%"/>'
                },{
                    label : '值班调度：',
                    itemColspan : 2,
                    item : '<input type="text" name="dispatch" id="dispatch" style="width:99%"/>'
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
            title:{value:'新增值班人员'},
            width:{value:380},
            height:{value:200},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            date:{},
            docId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('DuanAdd');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    var docId = _self.get('docId');
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('orgName',_self.get('orgName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('date',_self.get('date'));
                    formData.append('docId',docId);
                    var xhr = new XMLHttpRequest();
                    if(docId==null||docId==""){
                    xhr.open("POST", "/kmms/dayDutyManageAction/addDoc");
                    }else{
                    xhr.open("POST", "/kmms/dayDutyManageAction/updateDoc");
                    }
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
    return dayDutyManageAdd;
});