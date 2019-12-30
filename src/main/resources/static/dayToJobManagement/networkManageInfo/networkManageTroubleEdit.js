/**
 * 故障修改
 * @author yangsy
 * @date 19-1-17
 */
define('kmms/dayToJobManagement/networkManageInfo/networkManageTroubleEdit',
	[
	 	'bui/common',
	 	'bui/form',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/form/FormContainer',
	 	'common/org/OrganizationPicker',
	 	'common/uploader/UpdateUploader',
	 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer'),
        UpdateUploader = r('common/uploader/UpdateUploader');
    var NetworkManageTroubleEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
//        	_self._initOrganizationPicker();
        	_self._initDate();
//        	_self._initSelect();
        	_self._getShowData();
//        	$('#formContainer #backOrgName').val(_self.get('rootOrgText'));
//		    $('#formContainer #backOrgId').val(_self.get('rootOrgId'));
        },
        bindUI : function(){
            var _self = this;
            $("#formContainer #troubleOccurDate").on("change",function(){
            	if($("#formContainer #troubleOccurDate").val()!=""&&$("#formContainer #troubleRecoverDate").val()!=""){
            		var postLoad = new PostLoad({
                        url : '/kmms/networkManageInfoAction/getDelayMinutes.cn',
                        el : _self.get('el'),
                        loadMsg : '加载中...'
                    });
                    postLoad.load({occurDate:$("#formContainer #troubleOccurDate").val(),recoverDate:$("#formContainer #troubleRecoverDate").val()},function (date) {
                    	if(date){
                    		$('#formContainer #troubleDelayMinutes').val(date);
                    	}
                    });
            	}
            });
            $("#formContainer #troubleRecoverDate").on("change",function(){
            	if($("#formContainer #troubleOccurDate").val()!=""&&$("#formContainer #troubleRecoverDate").val()!=""){
            		var postLoad = new PostLoad({
                        url : '/kmms/networkManageInfoAction/getDelayMinutes.cn',
                        el : _self.get('el'),
                        loadMsg : '加载中...'
                    });
                    postLoad.load({occurDate:$("#formContainer #troubleOccurDate").val(),recoverDate:$("#formContainer #troubleRecoverDate").val()},function (date) {
                    	if(date){
                    		$('#formContainer #troubleDelayMinutes').val(date);
                    	}
                    });
            	}
            });
//            var orgPicker=_self.get('orgPicker');
            
//            /**
//             * 组织机构选择
//             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #backOrgName').val(e.org.text);
//    		    $('#formContainer #backOrgId').val(e.org.id);
//            });

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
            
            //部门下拉选选项根据车间而变化
	        $("#formContainer #troubleWorkshop").on('change',function(){
	        	$("#formContainer #troubleWorkarea").empty();
	        	var orgName=$("#formContainer #troubleWorkshop").val();
	        	_self._getDepart(orgName);
	        });
        },
        
//        _initSelect: function(){
//        	$("#formContainer #troubleWorkshop").append("<option  value='1'>车间1</option>");
//        	$("#formContainer #troubleWorkshop").append("<option  value='2'>车间2</option>");
//        	$("#formContainer #troubleWorkarea").append("<option  value='1'>工区1</option>");
//        	$("#formContainer #troubleWorkarea").append("<option  value='2'>工区2</option>");
//			$("#formContainer #troubleLineName").append("<option  value='1'>线1</option>");
//			$("#formContainer #troubleLineName").append("<option  value='2'>线2</option>");
//        },
        
        /**
         * 获取部门下拉选数据
         */
        _getDepart:function(troubleWorkshop,troubleWorkarea){
        	var _self=this;
        	 $.ajax({
                 url:'/kmms/networkManageInfoAction/getDepart',
                 data:{workShopName : troubleWorkshop},
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 for(var i=0;i<res.length;i++){
                		 $("#formContainer #troubleWorkarea").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                	 $("#formContainer #troubleWorkarea option[value='"+troubleWorkarea+"']").attr("selected","selected");
                 }
             })
        },
        /**
         * 获取车间下拉选数据
         */
        _getWorkShop:function(troubleObligationDepart){
        	 var _self = this;
             $.ajax({
                 url:'/kmms/networkManageInfoAction/getWorkShop',
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 $("#formContainer #troubleObligationDepart").append("<option  value=''>请选择</option>");
                	 for(var i=0;i<res.length;i++){
                		 $("#formContainer #troubleObligationDepart").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
	               	 $("#formContainer #troubleObligationDepart option[value='"+troubleObligationDepart+"']").attr("selected","selected");
                 }
             })
        }, 
//        /**
//         * 获取车间下拉选数据
//         */
//        _getWorkShop:function(troubleWorkshop){
//        	 var _self = this;
//             $.ajax({
//                 url:'/kmms/networkManageInfoAction/getWorkShop',
//                 type:'post',
//                 dataType:"json",
//                 success:function(res){
//                	 for(var i=0;i<res.length;i++){
//                		 $("#formContainer #troubleWorkshop").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
//                	 }
//                	 $("#formContainer #troubleWorkshop option[value='"+troubleWorkshop+"']").attr("selected","selected");
//                 }
//             })
//        },
//        
//        /**
//         * 获取线别
//         */
//        _getLines:function(troubleLineName){
//        	var _self=this;
//       	 $.ajax({
//	                url:'/kmms/networkManageInfoAction/getLines',
//	                type:'post',
//	                dataType:"json",
//	                success:function(res){
//	               	 $("#formContainer #troubleLineName").append("<option  value=''>请选择</option>");
//	               	 for(var i=0;i<res.length;i++){
//	               		 $("#formContainer #troubleLineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
//	               	 }
//	               	 $("#formContainer #troubleLineName option[value='"+troubleLineName+"']").attr("selected","selected");
//                }
//            });
//        },
        
        /**
         * 初始化时间
         */
        _initDate: function () {
            var _self = this;
            var troubleDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #troubleDate',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #troubleDate'
            });
            _self.set('troubleDate', troubleDate);
            var troubleOccurDate = new Calendar.DatePicker({//加载日历控件
            	trigger: '#formContainer #troubleOccurDate',
            	showTime: true,
            	autoRender: true,
            	textField:'#formContainer #troubleOccurDate'
            });
            _self.set('troubleOccurDate', troubleOccurDate);
            var troubleRecoverDate = new Calendar.DatePicker({//加载日历控件
            	trigger: '#formContainer #troubleRecoverDate',
            	showTime: true,
            	autoRender: true,
            	textField:'#formContainer #troubleRecoverDate'
            });
            _self.set('troubleRecoverDate', troubleRecoverDate);
        },
        
//        /**
//         * 初始化组织机构选择
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #backOrgName',
//                rootOrgId:_self.get('rootOrgId'),//必填项
//                rootOrgText:_self.get('rootOrgText'),//必填项
//                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
//                autoHide: true,
//                align : {
//                    points:['bl','tl']
//                },
//                zIndex : '10000',
//                width:220,
//                height:200
//            });
//            orgPicker.render();
//            _self.set('orgPicker',orgPicker);
//        },
        
        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/networkManageInfoAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                    	
//                    	_self._getDepart(data.troubleWorkshop,data.troubleWorkarea);
                    	_self._getWorkShop(data.troubleObligationDepart);
//                    	_self._getLines(data.troubleLineName);
                    	 $('#formContainer #troubleLineName').val(data.troubleLineName);
//                        $("#formContainer #troubleWorkshop").val(data.troubleWorkshop);
//                        $("#formContainer #troubleWorkarea").val(data.troubleWorkarea);
//                        $('#formContainer #troubleLineName').val(data.troubleLineName);
//						$("#formContainer #troubleObligationDepart").val(data.troubleObligationDepart);
            		    $('#formContainer #troubleDate').val(data.troubleDate);
            		    $('#formContainer #troubleSite').val(data.troubleSite);
            		    $('#formContainer #troubleDevice').val(data.troubleDevice);
            		    $('#formContainer #troubleOccurDate').val(data.troubleOccurDate);
            		    $('#formContainer #troubleRecoverDate').val(data.troubleRecoverDate);
            		    $('#formContainer #troubleDelayMinutes').val(data.troubleDelayMinutes);
            		    $('#formContainer #troubleTrainNumber').val(data.troubleTrainNumber);
            		    $('#formContainer #troubleGeneral').val(data.troubleGeneral);
            		    $('#formContainer #troubleDisposePass').val(data.troubleDisposePass);
            		    $('#formContainer #troubleReasonAnalyse').val(data.troubleReasonAnalyse);
            		    $('#formContainer #troubleMeasure').val(data.troubleMeasure);
            		    $('#formContainer #troubleFixDuty').val(data.troubleFixDuty);
            		    $('#formContainer #troubleCheckSituation').val(data.troubleCheckSituation);
            		    $('#formContainer #troubleRemark').val(data.troubleRemark);
            		    if(data.troubleUploadFileArr) {
            				_self._initUploader(data.troubleUploadFileArr);
            			}else{
            				_self._initUploader();
            			}
                    }
                }
            })
        },
        /**
		 * 初始化上传文件
		 */
		_initUploader:function(uploadFiles){
			var _self = this;
			var uploader = new UpdateUploader({
				render : '#formContainer #uploadfile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        alreadyItems : uploadFiles,
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
		/**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData : function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
			var fileArray = uploader.getSucFiles();
			for(var i in fileArray){
		    	var dto = new _self._UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
				arr[i] = dto; // 向集合添加对象
			};
			return arr;
		},
		
		/**
		  * 声明上传文件对象
		  * @param name 上传文件名
		  * @param path 上传文件路径
		  */
		_UploadFileDto : function(name,path){
			this.name = name;
			this.path = path;
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
//				{
//					label : '车间：',
//					redStarFlag : true,
//					itemColspan : 1,
//					item : '<select name="troubleWorkshop" id="troubleWorkshop" style="width:99.5%" data-rules="{required:true}"/></select>'
//				},
//				{
//					label : '工区：',
//					redStarFlag : true,
//					itemColspan : 1,
//					item : '<select name="troubleWorkarea" id="troubleWorkarea" style="width:99.5%" data-rules="{required:true}"/></select>'
//				},
            	{
					label : '责任部门：',
					redStarFlag : true,
					itemColspan : 2,
					item : '<select type="text" name="troubleObligationDepart" id="troubleObligationDepart" style="width:99.5%"  data-rules="{required:true}"/>'
				},
				{
					label : '线名：',
					redStarFlag : true,
					itemColspan : 1,
//					item : '<select name="troubleLineName" id="troubleLineName" style="width:99.5%" data-rules="{required:true}"/></select>'
						item : '<input type="text" name="troubleLineName" id="troubleLineName" style="width:99%" data-rules="{required:true}"/>'
				},
				{
					label : '时间：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="troubleDate" id="troubleDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
				},
				{
				    label : '故障地点：',
				    redStarFlag : true,
				    itemColspan : 1,
				    item : '<input type="text" name="troubleSite" id="troubleSite" style="width:99%" data-rules="{required:true}"/>'
				},
				{
					label : '故障设备：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="troubleDevice" id="troubleDevice" style="width:99%" data-rules="{required:true}"/>'
				},
				{
					label : '发生时间：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="troubleOccurDate" id="troubleOccurDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
				},
				{
					label : '恢复时间：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="troubleRecoverDate" id="troubleRecoverDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
				},
				{
					label : '延时分钟：',
					itemColspan : 1,
					item : '<input type="text" name="troubleDelayMinutes" id="troubleDelayMinutes" style="width:99%"/>'
				},
				{
					label : '影响车次：',
					itemColspan : 1,
					item : '<input type="text" name="troubleTrainNumber" id="troubleTrainNumber" style="width:99%"/>'
				},
				{
					label : '故障概况：',
					itemColspan : 2,
					item : '<textarea name="troubleGeneral" id="troubleGeneral" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '处理经过：',
					itemColspan : 2,
					item : '<textarea name="troubleDisposePass" id="troubleDisposePass" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '原因分析：',
					itemColspan : 2,
					item : '<textarea name="troubleReasonAnalyse" id="troubleReasonAnalyse" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '防范措施：',
					itemColspan : 2,
					item : '<textarea name="troubleMeasure" id="troubleMeasure" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '定性定责：',
					itemColspan : 2,
					item : '<textarea name="troubleFixDuty" id="troubleFixDuty" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '考核情况：',
					itemColspan : 2,
					item : '<textarea name="troubleCheckSituation" id="troubleCheckSituation" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '备注：',
					itemColspan : 2,
					item : '<textarea name="troubleRemark" id="troubleRemark" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'networkManageTroubleEditForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'435px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'networkManageTroubleEditDialog'},
            elAttrs : {value: {id:"networkManageTroubleEdit"}},
            title:{value:'修改故障情况'},
            width:{value:750},
            height:{value:540},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            shiftId:{},
            busiId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('networkManageTroubleEditForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//获取上传文件
            		var troubleUploadFileArr = _self._getUploadFileData();
            		/*if(troubleUploadFileArr.length==0){
	        			Mask.maskElement('#otherProductionAccidentEdit');
	        			BUI.Message.Alert("请上传附件！",function(){
	        				Mask.unmaskElement('#otherProductionAccidentEdit');
	        			},'error');
	        			$(".bui-message .bui-ext-close").css("display","none");
	        			return;
	        		}*/
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('troubleUploadFileArr',JSON.stringify(troubleUploadFileArr));
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('busiId',_self.get('busiId'));
                    formData.append('edit','edit');
//                    formData.append('userId',_self.get('userId'));
//                    formData.append('orgId',_self.get('orgId'));
//                    formData.append('orgName',_self.get('orgName'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/networkManageInfoAction/updateTroubleDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeTroubleAddSave",{
                                result : result
                            });
                        }
                    }
                }
            },
            events : {
                value : {
                    'completeTroubleAddSave' : true,
                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return NetworkManageTroubleEdit;
});