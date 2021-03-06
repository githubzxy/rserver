/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var FloodGuardPointAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._getLines();
         	_self._getWorkshops();//获取车间下拉选数据
//        	_self._initOrganizationPicker();
         	$("#formContainer #ksStatus").append("<option  value='是'>是</option>");
 			$("#formContainer #ksStatus").append("<option  value='否'>否</option>");
        },
        bindUI : function(){
            var _self = this;
            
//            var orgPicker=_self.get('orgPicker');
            
//            /**
//             * 组织机构选择
//             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #orgSelectName').val(e.org.text);
//    		    $('#formContainer #orgSelectId').val(e.org.id);
//            });
            //工区下拉选选项根据车间而变化
            $("#formContainer #orgSelectName").on('change', function() {
                $("#formContainer #workArea").empty();
                var workshop = $("#formContainer #orgSelectName").val();
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
//        
//        /**
//         * 初始化组织机构选择
//         * @private
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #orgSelectName',
//                rootOrgId:_self.get('rootOrgId'),//必填项
//                rootOrgText:_self.get('rootOrgText'),//必填项
//                url : '/kmms/commonAction/getWorkShop',//必填项
//                autoHide: true,
//                align : {
//                    points:['bl','tl']
//                },
//                zIndex : '10000',
//                width:493,
//                height:200
//            });
//            orgPicker.render();
//            _self.set('orgPicker',orgPicker);
//        },
        /**
         * 获取车间
         */
        _getWorkshops:function(){
        	var _self=this;
       	 $.ajax({
	                url:'/kmms/overhaulRecordAction/getworkShop',
	                type:'post',
	                dataType:"json",
	                success:function(res){
	               	 $("#formContainer #orgSelectName").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
	               		 $("#formContainer #orgSelectName").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
	               	 var workshop = $("#formContainer #orgSelectName").val();
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
	               	 $("#formContainer #workArea").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
//	               		 console.log(res[i].value);
	               		 $("#formContainer #workArea").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
                }
            });
        },
        /**
         * 获取线别
         */
        _getLines:function(){
        	var _self=this;
        	$.ajax({
        		url:'/kmms/networkManageInfoAction/getLines',
        		type:'post',
        		dataType:"json",
        		success:function(res){
        			$("#formContainer #lineName").append("<option  value=''>请选择</option>");
        			for(var i=0;i<res.length;i++){
        				$("#formContainer #lineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
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
                    label : '所属车间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select type="text" name="orgSelectName" style="width:99.5%" id="orgSelectName" data-rules="{required:true}" />'
                },{
                    label : '所属工区：',
                    redStarFlag : true,
                    itemColspan : 1,
					item : '<select name="workArea" id="workArea" style="width:99.5%" data-rules="{required:true}"/></select>'
                },{
                    label : '铁路线别：',
                    redStarFlag : true,
                    itemColspan : 1,
					item : '<select name="lineName" id="lineName" style="width:99.5%" data-rules="{required:true}"/></select>'
                },{
                    label : '区间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="section" id="section" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '看守点名称：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="guardName" id="guardName" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '无线设备：',
                    itemColspan : 2,
                    item : 
                    	   '<table class="wxsbTable" style="width:100%;table-layout:fixed" ><tbody>'+	
	                       '<tr class="thTdPadding">'+
					       '<th >列调便携电台</th>'+
					       '<th style="width:20%">型号：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="typeLT" name="typeLT"  style="width:98%;" />'+
						   '</td>'+
						   '<th style="width:20%">数量：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="countLT" name="countLT"  style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '<tr class="thTdPadding">'+
					       '<th >预警便携电台</th>'+
					       '<th style="width:20%">型号：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="typeYJ" name="typeYJ"  style="width:98%;" />'+
						   '</td>'+
						   '<th style="width:20%">数量：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="countYJ" name="countYJ"  style="width:98%;" />'+
						   '</td>'+
					       '<tr class="thTdPadding">'+
					       '<th >固定电台</th>'+
					       '<th style="width:20%">型号：'+
					       '</th>'+
					       '<td colspan="3">'+
						   '<input type="text" id="typeGD" name="typeGD"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   /*******************************************************************************/
						   '<tr class="thTdPadding">'+
					       '<th rowspan="3">G网手持台</th>'+
					       '<th style="width:20%" >型号：'+
					       '</th>'+
					       '<td style="width:30%" colspan="3">'+
						   '<input type="text" id="typeGW" name="typeGW"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >数量：'+
					       '</th>'+
					       '<td style="width:30%" colspan="3">'+
						   '<input type="text" id="countGW" name="countGW"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" ">电话号码：'+
					       '</th>'+
					       '<td style="width:30%" colspan="3">'+
						   '<input type="text" id="phoneNumGW" name="phoneNumGW" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					      
						   '<tr class="thTdPadding"><th style="width:20%" colspan="2">守机联控情况：'+
					       '</th>'+
					       '<td colspan="3">'+
						   '<input type="text" id="condition" name="condition"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
					       '</tbody>'+
					       '</table>'
					     
                },{
                    label : '自动电话：',
                    itemColspan : 2,
                    item : 
                    	   '<table class="wxsbTable" style="width:100%;table-layout:fixed" ><tbody>'+	
                    	   '<tr class="thTdPadding"><th style="width:33.54%" colspan="2">电话号码：'+
					       '</th>'+
					       '<td colspan="3">'+
						   '<input type="text" id="phoneNum" name="phoneNum"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding"><th style="width:33.54%" colspan="2">自动电话接入点：'+
					       '</th>'+
					       '<td colspan="3">'+
						   '<input type="text" id="phoneAP" name="phoneAP"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding"><th style="width:33.54%" colspan="2">引入线类型：'+
					       '</th>'+
					       '<td colspan="3">'+
						   '<input type="text" id="leadType" name="leadType"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding"><th style="width:20%" colspan="2">引入线长度：'+
					       '</th>'+
					       '<td colspan="3">'+
						   '<input type="text" id="leadExtent" name="leadExtent"  style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
					       '</tbody>'+
					       '</table>'
					     
                },{
                    label : '是否常年看守点：',
                    itemColspan : 2,
					item : '<select name="ksStatus" id="ksStatus" style="width:99.5%;height:40px" ><option value="" >请选择</option></select>'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'floodGuardPointAddForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'floodGuardPointAddDialog'},
            elAttrs : {value: {id:"floodGuardPointAdd"}},
            title:{value:'新增'},
            width:{value:630},
            height:{value:500},
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
                    var formAdd = _self.getChild('floodGuardPointAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/floodGuardPointAction/addDoc");
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
    return FloodGuardPointAdd;
});