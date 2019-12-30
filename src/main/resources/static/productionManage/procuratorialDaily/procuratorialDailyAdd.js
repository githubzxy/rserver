/**
 * 新增
 * @author 
 * @date 
 */
define('kmms/productionManage/procuratorialDaily/procuratorialDailyAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var procuratorialDailyAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	$("#formContainer #workshop").val(_self.get("parentOrgName"));
        	$("#formContainer #department").val(_self.get("orgName"));
        },
        bindUI : function(){
            var _self = this;
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(e){
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
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '填报车间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="workshop" id="workshop" style="width:99%" readonly data-rules="{required:true}"/>'
                },{
                    label : '填报部门：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="department" id="department" style="width:99%" readonly data-rules="{required:true}"/>'
                },{
                    label : '检查日期：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="date" id="date" class="calendar" readonly data-rules="{required:true}" style="width:99%"/>'
                },{
                    label : '检查人：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="inspector" id="inspector" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '检查地点：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="site" id="site"  style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '检查内容：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<textarea  name="content" id="content" style="width:99.5%;height:50px" data-rules="{required:true}"/>'
                },{
                    label : '发现问题：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<textarea  name="problem" id="problem" style="width:99.5%;height:50px" data-rules="{required:true}"/>'
                },{
                    label : '整改要求：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<textarea  name="require" id="require" style="width:99.5%;height:50px" data-rules="{required:true}"/>'
                },{
                    label : '整改落实情况：',
                    itemColspan : 2,
                    item : '<textarea name="condition" id="condition" style="width:99.5%;height:50px"/>'
                },{
                    label : '整改负责人：',
                    itemColspan : 2,
                    item : '<input type="text" name="functionary" id="functionary" style="width:99%"/>'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'procuratorialDailyAddForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'procuratorialDailyAddDialog'},
            elAttrs : {value: {id:"procuratorialDailyAdd"}},
            title:{value:'新增'},
            width:{value:630},
            height:{value:540},
            contextPath : {},
            closeAction : {value:'destroy'},
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('procuratorialDailyAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/procuratorialDailyAction/addDoc");
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
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return procuratorialDailyAdd;
});