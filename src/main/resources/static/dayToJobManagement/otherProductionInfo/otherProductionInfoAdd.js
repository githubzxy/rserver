/**
 * 新增
 * @author yangsy
 * @date 19-1-18
 */
define('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoAdd',
	[
	 	'bui/common',
	 	'bui/form',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/form/FormContainer',
	 	'common/org/OrganizationPicker',
	 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var OtherProductionInfoAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initOrganizationPicker();
        	_self._initDate();
        	_self._initSelect();
        	_self._initSystem();//初始化系统---类别
        	_self._initDevice();
        	$('#formContainer #backOrgName').val(_self.get('rootOrgText'));
		    $('#formContainer #backOrgId').val(_self.get('rootOrgId'));
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #backOrgName').val(e.org.text);
    		    $('#formContainer #backOrgId').val(e.org.id);
            });
            $("#formContainer #system").on('change',function(){
            	var system = $("#formContainer #system").val();
            	$("#formContainer #systemType").removeAttr("disabled");
            	_self._initDevice(system);
            });
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
        _initSystem:function(){
        	var _self=this;
        	$.ajax({
        		url:'/kmms/systemDeviceAction/getSystem',
        		type:'post',
        		dataType:"json",
        		success:function(res){
//        			var html = "<option>请选择</option>";
        			$("#formContainer #system").append("<option value=''>请选择</option>");
        			for(var i=0;i<res.length;i++){
        				$("#formContainer #system").append("<option value='"+res[i]+"'>"+res[i]+"</option>");
//        				html +='<option>'+res[i]+'</option>';
        			}
//        			$("#system").val(html);
        		}
        	});
        	
        	
        },
        _initDevice:function(system){
        	var _self=this;
        	$.ajax({
        		url:'/kmms/systemDeviceAction/getDevice',
        		type:'post',
        		dataType:"json",
        		data:{'system':system},
        		success:function(res){
//        			var html = "<option></option>";
        			$("#formContainer #systemType").empty();
        			$("#formContainer #systemType").append("<option value=''>请选择</option>");
        			for(var i=0;i<res.length;i++){
        				$("#formContainer #systemType").append("<option value='"+res[i]+"'>"+res[i]+"</option>");
//        				html +='<option>'+res[i]+'<option>';
        			}
//        			$("#deviceType").html(html);
        		}
        	});
        },
        _initSelect: function(){
        	$("#formContainer #type").append("<option  value='施工'>施工</option>");
        	$("#formContainer #type").append("<option  value='通知'>通知</option>");
        	$("#formContainer #type").append("<option  value='设备故障'>设备故障</option>");
        	$("#formContainer #type").append("<option  value='生产任务'>生产任务</option>");
        	$("#formContainer #type").append("<option  value='其他'>其他</option>");
        	
        	$("#formContainer #infoResult").append("<option  value='0'>无</option>");
        	$("#formContainer #infoResult").append("<option  value='1'>事故</option>");
			$("#formContainer #infoResult").append("<option  value='2'>故障</option>");
			$("#formContainer #infoResult").append("<option  value='3'>障碍</option>");
			$("#formContainer #infoResult").append("<option  value='4'>安全隐患</option>");
			
			$("#formContainer #lost").append("<option  value='0'>否</option>");
			$("#formContainer #lost").append("<option  value='1'>是</option>");
        },
        
        /**
         * 初始化时间
         */
        _initDate: function () {
            var _self = this;
            var createDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #createDate',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #createDate'
            });
            _self.set('createDate', createDate);
            
            var postLoad = new PostLoad({
                url : '/kmms/otherProductionInfoAction/getSystemDate.cn',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #createDate').val(date);
            	}
            });
        },
        
        /**
         * 初始化组织机构选择
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #backOrgName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:220,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
//                {
//                    label : '资料名称：',
//                    redStarFlag : true,
//                    itemColspan : 2,
//                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99.5%">'+
//                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
//                        'value="uploadfile">'
//                },
				{
                    label : '时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="createDate" id="createDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
                },
                {
                    label : '类型：',
                    redStarFlag : true,
                    itemColspan : 1,
//                    item : '<input type="text" name="type" id="type" style="width:99%" data-rules="{required:true}"/>'
                    item : '<select name="type" id="type" style="width:99.5%"/></select>'
                },
                {
                    label : '反馈部门：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="backOrgName" id="backOrgName" style="width:99%" data-rules="{required:true}"/>'
                    	+'<input type="hidden" name="backOrgId" id="backOrgId"  readonly/>'
                },
                {
                    label : '反馈人：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="backPerson" id="backPerson" style="width:99%" data-rules="{required:true}"/>'
                },
                {
                    label : '信息后果：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="infoResult" id="infoResult" style="width:99.5%"/></select>'
                },
                {
                	label : '是否遗留：',
                	redStarFlag : true,
                	itemColspan : 1,
                	item : '<select name="lost" id="lost" style="width:99.5%"/></select>'
                },
                {
                    label : '设备所属系统：',
                    itemColspan : 1,
                    item : '<select name="system" id="system" style="width:99.5%"/></select>'
                },
                {
                	label : '设备类别：',
                	itemColspan : 1,
                	item : '<select name="systemType" id="systemType" style="width:99.5%" disabled/></select>'
                },
                {
                    label : '内容及处理情况：',
                    itemColspan : 2,
                    item : '<textarea name="detail" id="detail" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '备注：',
                	itemColspan : 2,
                	item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                },
//                {
//                    label : '附件：',
//                    itemColspan : 2,
//                    item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>'
//                }
            ];
            var form = new FormContainer({
                id : 'otherProductionInfoAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'otherProductionInfoAddDialog'},
            elAttrs : {value: {id:"otherProductionInfoAdd"}},
            title:{value:'新增'},
            width:{value:620},
            height:{value:360},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},//mongo数据库表名
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('otherProductionInfoAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('userId',_self.get('userId'));
                    formData.append('orgId',_self.get('orgId'));
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('projectType',"other");//标识该保存的数据是在其他生产信息模块中使用
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/otherProductionInfoAction/addDoc");
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
                value : {'completeAddSave' : true,}//绑定保存按钮事件
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return OtherProductionInfoAdd;
});