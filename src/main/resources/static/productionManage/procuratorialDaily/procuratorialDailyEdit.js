/**
 * 编辑
 * @author xiekun
 * @date 19-3-25
 */
define('kmms/productionManage/procuratorialDaily/procuratorialDailyEdit',['bui/common','common/form/FormContainer',
   'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var procuratorialDailyEdit = BUI.Overlay.Dialog.extend({
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
                url:'/kmms/procuratorialDailyAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #workshop").val(data.workshop);
            		    $('#formContainer #department').val(data.department);
            		    $("#formContainer #date").val(data.date);
                        $("#formContainer #inspector").val(data.inspector);
            		    $('#formContainer #site').val(data.site); 
            		    $("#formContainer #content").val(data.content);
                        $("#formContainer #problem").val(data.problem);
            		    $('#formContainer #require').val(data.require); 
            		    $("#formContainer #condition").val(data.condition);
                        $("#formContainer #functionary").val(data.functionary);
            		    $('#formContainer #remark').val(data.remark);
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
			                    label : '填报车间：',
			                    redStarFlag : true,
			                    itemColspan : 1,
			                    item : '<input type="text" name="workshop" id="workshop" style="width:99%" data-rules="{required:true}"/>'
			                },{
			                    label : '填报部门：',
			                    redStarFlag : true,
			                    itemColspan : 1,
			                    item : '<input type="text" name="department" id="department" style="width:99%" data-rules="{required:true}"/>'
			                },{
			                    label : '检查日期：',
			                    redStarFlag : true,
			                    itemColspan : 1,
			                    item : '<input type="text" name="date" id="date" class="calendar"  readonly data-rules="{required:true}" style="width:99%"/>'
			                },{
			                    label : '检查人：',
			                    redStarFlag : true,
			                    itemColspan : 1,
			                    item : '<input type="text" name="inspector" id="inspector"   style="width:99%" data-rules="{required:true}"/>'
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
			                    item : '<textarea name="condition" id="condition" style="width:99.5%;height:50px" />'
			                },{
			                    label : '整改负责人：',
			                    itemColspan : 2,
			                    item : '<input type="text" name="functionary" id="functionary" style="width:99%" />'
			                },{
			                    label : '备注：',
			                    itemColspan : 2,
			                    item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
			                }
            ];
            var form = new FormContainer({
                id : 'procuratorialDailyEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'procuratorialDailyEditDialog'},
            elAttrs : {value: {id:"procuratorialDailyEdit"}},
            title:{value:'编辑'},
            width:{value:630},
            height:{value:540},
            contextPath : {},
            closeAction : {value:'destroy'},
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('procuratorialDailyEditForm'),delData=_self.get('delData');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/procuratorialDailyAction/updateDoc");
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
    return procuratorialDailyEdit;
});