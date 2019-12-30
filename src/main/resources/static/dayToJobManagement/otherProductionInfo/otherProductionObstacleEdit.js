/**
 * 障碍修改
 * @author yangsy
 * @date 19-1-18
 */
define('kmms/dayToJobManagement/otherProductionInfo/otherProductionObstacleEdit',
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
    var OtherProductionObstacleEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._initSelect();
        	_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            $("#formContainer #obstacleOccurDate").on("change",function(){
            	if($("#formContainer #obstacleOccurDate").val()!=""&&$("#formContainer #obstacleRecoverDate").val()!=""){
            		var postLoad = new PostLoad({
                        url : '/kmms/otherProductionInfoAction/getDelayMinutes.cn',
                        el : _self.get('el'),
                        loadMsg : '加载中...'
                    });
                    postLoad.load({occurDate:$("#formContainer #obstacleOccurDate").val(),recoverDate:$("#formContainer #obstacleRecoverDate").val()},function (date) {
                    	if(date){
                    		$('#formContainer #obstacleDelayMinutes').val(date);
                    	}
                    });
            	}
            });
            $("#formContainer #obstacleRecoverDate").on("change",function(){
            	if($("#formContainer #obstacleOccurDate").val()!=""&&$("#formContainer #obstacleRecoverDate").val()!=""){
            		var postLoad = new PostLoad({
                        url : '/kmms/otherProductionInfoAction/getDelayMinutes.cn',
                        el : _self.get('el'),
                        loadMsg : '加载中...'
                    });
                    postLoad.load({occurDate:$("#formContainer #obstacleOccurDate").val(),recoverDate:$("#formContainer #obstacleRecoverDate").val()},function (date) {
                    	if(date){
                    		$('#formContainer #obstacleDelayMinutes').val(date);
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
        },
        
        _initSelect: function(){
        	$("#formContainer #obstacleDeviceType").append("<option  value='1'>设备类型1</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='2'>设备类型2</option>");
        },
        
        _initSelect: function(){
        	$("#formContainer #obstacleDeviceType").append("<option  value='通信线路及附属设备'>通信线路及附属设备</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='传输与接入网系统'>传输与接入网系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='数据通信系统'>数据通信系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='调度通信系统'>调度通信系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='综合视频监控系统'>综合视频监控系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='图像与语音监控系统'>图像与语音监控系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='时间与时钟同步系统'>时间与时钟同步系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='会议系统'>会议系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='通信电源及环境监控系统'>通信电源及环境监控系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='应急通信系统'>应急通信系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='GSM-R数字移动通信系统'>GSM-R数字移动通信系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='无线列调通信系统'>无线列调通信系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='电报及电话系统'>电报及电话系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='综合网管系统'>综合网管系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='广播与站场通信系统'>广播与站场通信系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='自然灾害及异物侵限系统'>自然灾害及异物侵限系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='机房空调及防雷接地系统'>机房空调及防雷接地系统</option>");
        	$("#formContainer #obstacleDeviceType").append("<option  value='客运引导系统'>客运引导系统</option>");
        },
        
        /**
         * 初始化时间
         */
        _initDate: function () {
            var _self = this;
            var obstacleDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #obstacleDate',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #obstacleDate'
            });
            _self.set('obstacleDate', obstacleDate);
            var obstacleOccurDate = new Calendar.DatePicker({//加载日历控件
            	trigger: '#formContainer #obstacleOccurDate',
            	showTime: true,
            	autoRender: true,
            	textField:'#formContainer #obstacleOccurDate'
            });
            _self.set('obstacleOccurDate', obstacleOccurDate);
            var obstacleRecoverDate = new Calendar.DatePicker({//加载日历控件
            	trigger: '#formContainer #obstacleRecoverDate',
            	showTime: true,
            	autoRender: true,
            	textField:'#formContainer #obstacleRecoverDate'
            });
            _self.set('obstacleRecoverDate', obstacleRecoverDate);
            var obstacleSuingDate = new Calendar.DatePicker({//加载日历控件
            	trigger: '#formContainer #obstacleSuingDate',
            	showTime: true,
            	autoRender: true,
            	textField:'#formContainer #obstacleSuingDate'
            });
            _self.set('obstacleSuingDate', obstacleSuingDate);
        },
        
        
        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/otherProductionInfoAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                    	_self._getWorkShop(data.obstacleObligationDepart);
						$("#formContainer #obstacleObligationDepart").val(data.obstacleObligationDepart);
                        $("#formContainer #obstacleDate").val(data.obstacleDate);
                        $("#formContainer #obstaclePlace").val(data.obstaclePlace);
                        $('#formContainer #obstacleDeviceType').val(data.obstacleDeviceType);
            		    $('#formContainer #obstacleDeviceName').val(data.obstacleDeviceName);
            		    $('#formContainer #obstacleOccurDate').val(data.obstacleOccurDate);
            		    $('#formContainer #obstacleRecoverDate').val(data.obstacleRecoverDate);
            		    $('#formContainer #obstacleDelayMinutes').val(data.obstacleDelayMinutes);
            		    $('#formContainer #obstacleReceiver').val(data.obstacleReceiver);
            		    $('#formContainer #obstacleDelayCoach').val(data.obstacleDelayCoach);
            		    $('#formContainer #obstacleDelayTruck').val(data.obstacleDelayTruck);
            		    $('#formContainer #obstacleType').val(data.obstacleType);
            		    $('#formContainer #obstacleDuty').val(data.obstacleDuty);
            		    $('#formContainer #obstaclePhenomenon').val(data.obstaclePhenomenon);
            		    $('#formContainer #obstacleReasonAnalyse').val(data.obstacleReasonAnalyse);
            		    $('#formContainer #obstacleMeasure').val(data.obstacleMeasure);
            		    $('#formContainer #obstacleSuingPeople').val(data.obstacleSuingPeople);
            		    $('#formContainer #obstacleSuingDate').val(data.obstacleSuingDate);
            		    if(data.obstacleUploadFileArr) {
            				_self._initUploader(data.obstacleUploadFileArr);
            			}else{
            				_self._initUploader();
            			}
                    }
                }
            })
        },
        /**
         * 获取车间下拉选数据
         */
        _getWorkShop:function(obstacleObligationDepart){
        	 var _self = this;
             $.ajax({
                 url:'/kmms/networkManageInfoAction/getWorkShop',
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 $("#formContainer #obstacleObligationDepart").append("<option  value=''>请选择</option>");
                	 for(var i=0;i<res.length;i++){
                		 $("#formContainer #obstacleObligationDepart").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
	               	 $("#formContainer #obstacleObligationDepart option[value='"+obstacleObligationDepart+"']").attr("selected","selected");
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
            	{
					label : '责任部门：',
					redStarFlag : true,
					itemColspan : 2,
					item : '<select type="text" name="obstacleObligationDepart" id="obstacleObligationDepart" style="width:99.5%"  data-rules="{required:true}"/>'
				},
				{
					label : '时间：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="obstacleDate" id="obstacleDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
				},
				{
					label : '障碍处所：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="obstaclePlace" id="obstaclePlace" style="width:99%" data-rules="{required:true}"/>'
				},
				{
					label : '设备类别：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<select name="obstacleDeviceType" id="obstacleDeviceType" style="width:99.5%" data-rules="{required:true}"/></select>'
				},
				{
					label : '设备名称：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="obstacleDeviceName" id="obstacleDeviceName" style="width:99%" data-rules="{required:true}"/>'
				},
				{
					label : '发生时间：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="obstacleOccurDate" id="obstacleOccurDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
				},
				{
					label : '恢复时间：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="obstacleRecoverDate" id="obstacleRecoverDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
				},
				{
					label : '延时分钟：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDelayMinutes" id="obstacleDelayMinutes" style="width:99%"/>'
				},
				{
					label : '受理人：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleReceiver" id="obstacleReceiver" style="width:99%"/>'
				},
				{
					label : '延误客车：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDelayCoach" id="obstacleDelayCoach" style="width:99%"/>'
				},
				{
					label : '延误货车：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDelayTruck" id="obstacleDelayTruck" style="width:99%"/>'
				},
				{
					label : '障碍类别：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleType" id="obstacleType" style="width:99%"/>'
				},
				{
					label : '障碍责任：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDuty" id="obstacleDuty" style="width:99%"/>'
				},
				{
					label : '障碍现象：',
					itemColspan : 2,
					item : '<textarea name="obstaclePhenomenon" id="obstaclePhenomenon" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '原因分析：',
					itemColspan : 2,
					item : '<textarea name="obstacleReasonAnalyse" id="obstacleReasonAnalyse" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '防范措施：',
					itemColspan : 2,
					item : '<textarea name="obstacleMeasure" id="obstacleMeasure" style="width:99.5%;height:50px"/></textarea>'
				},
				{
					label : '申告人：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleSuingPeople" id="obstacleSuingPeople" style="width:99%"/>'
				},
				{
					label : '申告时间：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleSuingDate" id="obstacleSuingDate" style="width:99%" class="calendar" readonly/>'
				},
				{
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'otherProductionObstacleEditForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'435px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'otherProductionObstacleEditDialog'},
            elAttrs : {value: {id:"otherProductionObstacleEdit"}},
            title:{value:'修改障碍情况'},
            width:{value:750},
            height:{value:530},
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
                    var formAdd = _self.getChild('otherProductionObstacleEditForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		 //获取上传文件
            		var obstacleUploadFileArr = _self._getUploadFileData();
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('obstacleUploadFileArr',JSON.stringify(obstacleUploadFileArr));
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('busiId',_self.get('busiId'));
                    formData.append('edit','edit');
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/otherProductionInfoAction/updateObstacleDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeObstacleAddSave",{
                                result : result
                            });
                        }
                    }
                }
            },
            events : {
                value : {'completeObstacleAddSave' : true,}//绑定保存按钮事件
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return OtherProductionObstacleEdit;
});