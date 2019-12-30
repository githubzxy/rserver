/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/userInfoManager/userInfoManageAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var userInfoManageAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._getWorkshops();//获取车间下拉选数据

        },
        bindUI : function(){
            var _self = this;
            //工区下拉选选项根据车间而变化
            $("#formContainer #workshop").on('change', function() {
                $("#formContainer #teamGroup").empty();
                var workshop = $("#formContainer #workshop").val();
                _self._getWorkAreas(workshop);

            });
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
         * 获取科室和车间
         */
        _getWorkshops:function(){
        	var _self=this;
       	 $.ajax({
	                url:'/kmms/userInfoManageAction/getCadreAndShop',
	                type:'post',
	                dataType:"json",
	                success:function(res){
		             $("#formContainer #workshop").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
	               		 $("#formContainer #workshop").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
	               	 var workshop = $("#formContainer #workshop").val();
	               	 _self._getWorkAreas(workshop);
                }
            });
        },
        /**
         * 获取工区
         */
        _getWorkAreas:function(workshop){
        	var _self=this;
       	 $.ajax({
	                url:'/kmms/overhaulRecordAction/getworkArea',
	                type:'post',
	                dataType:"json",
	                data: { workshop:workshop},
	                success:function(res){
			          $("#formContainer #teamGroup").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
	               	  $("#formContainer #teamGroup").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
                }
            });
        },
//       
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
//				{
//                    label : '所属车间：',
//                    redStarFlag : true,
//                    itemColspan : 1,
//                    item : '<input type="text" name="orgSelectName" style="width:98%" id="orgSelectName" readonly /><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
//                },
                {
                    label : '劳资号：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="number" id="number" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '姓名：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="staffName" id="staffName" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '性别：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="gender" id="gender" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '出生日期：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="birthday" id="birthday" class="calendar" readonly style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '参加工作时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="entryDate" id="entryDate" class="calendar"  readonly style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '学历：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="education" id="education" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '车间(科室)：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select type="text" name="workshop" id="workshop" style="width:99%" ></select>'
                },{
                    label : '班组：',
                    itemColspan : 1,
                    item : '<select type="text" name="teamGroup" id="teamGroup" style="width:99%"></select>'
                },{
                    label : '职名：',
                    itemColspan : 1,
                    item : '<input type="text" name="position" id="position" style="width:99%" />'
                },{
                    label : '联系电话：',
                    itemColspan : 1,
                    item : '<input type="text" name="phoneNum" id="phoneNum" style="width:99%" />'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'userInfoManageAddForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'userInfoManageAddDialog'},
            elAttrs : {value: {id:"userInfoManageAdd"}},
            title:{value:'新增'},
            width:{value:630},
            height:{value:330},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('userInfoManageAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/userInfoManageAction/addDoc");
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
    return userInfoManageAdd;
});