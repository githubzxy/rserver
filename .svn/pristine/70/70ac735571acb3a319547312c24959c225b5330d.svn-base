/**
 * 障碍新增
 * @author yangsy
 * @date 19-1-18
 */
define('kmms/dayToJobManagement/otherProductionInfo/otherProductionObstacleAdd',
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
    var OtherProductionObstacleAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
            
        },
        renderUI : function(){
        	var _self=this;
//        	_self._initOrganizationPicker();
        	_self._initDate();
        	_self._initSelect();
        	_self._initUploader();
        	_self._getWorkShop();//获取车间下拉选数据

//        	$('#formContainer #backOrgName').val(_self.get('rootOrgText'));
//		    $('#formContainer #backOrgId').val(_self.get('rootOrgId'));
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
            
            var postLoad = new PostLoad({
                url : '/kmms/otherProductionInfoAction/getSystemDate.cn',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #obstacleDate').val(date);
            	}
            });
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
         * 获取车间下拉选数据
         */
        _getWorkShop:function(){
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
                 }
             })
        },
        /**
		 * 初始化上传文件
		 */
		_initUploader:function(){
			var _self = this;
			//上传附件
			var uploader = new UpdateUploader({
				render : '#formContainer #uploadfile',
		        url : '/zuul/kmms/atachFile/upload.cn',
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
		_getUploadFileData:function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
			var fileArray = uploader.getSucFiles();
			for(var i in fileArray){
		    	var dto = new _self.UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
				arr[i] = dto; // 向集合添加对象
			};
			return arr;
		},
		
		/**
		  * 声明上传文件对象
		  * @param name 上传文件名
		  * @param path 上传文件路径
		  */
		UploadFileDto: function(name,path){
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
                id : 'otherProductionObstacleAddForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'435px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'otherProductionObstacleAddDialog'},
            elAttrs : {value: {id:"otherProductionObstacleAdd"}},
            title:{value:'新增障碍情况'},
            width:{value:750},
            height:{value:540},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            busiId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('otherProductionObstacleAddForm');
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
    return OtherProductionObstacleAdd;
});