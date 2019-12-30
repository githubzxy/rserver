/**
 * 详情
 * @author yangsy
 * @date 19-1-17
 */
define('kmms/dayToJobManagement/networkManageInfo/networkManageInfoDetailZa',
		[
		 	'bui/common',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/uploader/ViewUploader',
		 ],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var NetworkManageInfoDetailZa = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
			_self._initSelect();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
                {
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
		
//		_initSelect: function(){
//        	$("#formContainer #infoResult").append("<option  value='0'>无</option>");
//        	$("#formContainer #infoResult").append("<option  value='1'>事故</option>");
//			$("#formContainer #infoResult").append("<option  value='2'>故障</option>");
//			$("#formContainer #infoResult").append("<option  value='3'>障碍</option>");
//			
//			$("#formContainer #lost").append("<option  value='0'>否</option>");
//			$("#formContainer #lost").append("<option  value='1'>是</option>");
//        },
        
        _setInfoResultValue : function(value){
        	if(value=='0'){
        		return "无";
        	}else if(value=="1"){
        		return "事故";
        	}else if(value=="2"){
        		return "故障";
        	}else if(value=="3"){
        		return "障碍";
        	}
        },
        
        _setLostValue : function(value){
        	if(value=='0'){
        		return "否";
        	}else if(value=="1"){
        		return "是";
        	}
        },
        
		/**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/networkManageInfoAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(result){
					var data = result.data;
					if(data){
                        $("#formContainer #createDate").val(data.createDate);
                        $("#formContainer #type").val(data.type);
                        $("#formContainer #backOrgName").val(data.backOrgName);
                        $("#formContainer #backPerson").val(data.backPerson);
                        $("#formContainer #infoResult").val(_self._setInfoResultValue(data.infoResult));
                        $("#formContainer #lost").val(_self._setLostValue(data.lost));
                        $("#formContainer #detail").val(data.detail);
                        $("#formContainer #remark").val(data.remark);
                        
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
            		    $("#formContainer #system").val(data.system);
                        $("#formContainer #systemType").val(data.systemType);
            		    if(data.obstacleUploadFileArr) {
            		    	_self._initViewUploader(data.obstacleUploadFileArr);
            		    }
                    }
          		}
			});
		},
		
//		//时间戳转时间
//		_timestampToTime : function(timestamp) {
//			if(timestamp){
//				var date = new Date(timestamp);
//				Y = date.getFullYear() + '-';
//				M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
//				D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
//				h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
//		        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
//		        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
//		        return Y+M+D+h+m+s;
//			}else{
//				return "";
//			}
//	    },
		
//		_renderUploadView(file){
//			var _self = this,html="";
//			file.forEach(function(f){
//                html+= '<div class="success"><label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label><span style="float: right;"><a href="' + _self.get('downloadUrl') + f.path+'&fileName='+f.name + '">下载</a>&nbsp;' +
//                    '<a href="' + _self.get('previewUrl') + f.path + '" target="_blank">预览</a></span></div>';
//            });
//			return html;
//		},
		/**
		 * 初始化上传文件（仅用于查看）
		 */
		_initViewUploader:function(uploadFiles){
			var _self = this;
			var viewFiles = new ViewUploader({
				render: '#formContainer #viewUploadfile',
				alreadyItems : uploadFiles,
				previewOnline : true
			});
			viewFiles.render();
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
//                    itemColspan : 1,
//                    item : '<input type="text"  name="name" id="name" style="width:99%" readonly>'+
//                        '<input type="hidden"  name="fileCols" id="fileCols" ' +
//                        'value="uploadfile">'
//                },
				{
				    label : '时间：',
				    itemColspan : 1,
				    item : '<input type="text" name="createDate" id="createDate" style="width:99%" readonly/>'
				},
				{
				    label : '类型：',
				    itemColspan : 1,
				    item : '<input type="text" name="type" id="type" style="width:99%" readonly/>'
				},
				{
				    label : '反馈部门：',
				    itemColspan : 1,
				    item : '<input type="text" name="backOrgName" id="backOrgName" style="width:99%" readonly/>'
				},
				{
				    label : '反馈人：',
				    itemColspan : 1,
				    item : '<input type="text" name="backPerson" id="backPerson" style="width:99%" readonly/>'
				},
				{
				    label : '信息后果：',
				    itemColspan : 1,
				    item : '<input type="text" name="infoResult" id="infoResult" style="width:99%" readonly/>'
				},
				{
					label : '是否遗留：',
					itemColspan : 1,
					item : '<input type="text" name="lost" id="lost" style="width:99%" readonly/>'
				},
				{
                    label : '设备所属系统：',
                    itemColspan : 1,
                    item : '<input type="text"  name="system" id="system" style="width:99%" readonly/></input>'
                },
                {
                	label : '设备类别：',
                	itemColspan : 1,
                	item : '<input type="text" name="systemType" id="systemType" style="width:99%" readonly/></input>'
                },
				{
				    label : '内容及处理情况：',
				    itemColspan : 2,
				    item : '<textarea name="detail" id="detail" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '备注：',
					itemColspan : 2,
					item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '障碍信息',
					itemColspan : 2,
					item : '<input type="text" style="width:99.5%" disabled="disabled"/>'
				},
				{
					label : '责任部门：',
					itemColspan : 2,
					item : '<input type="text" name="obstacleObligationDepart" id="obstacleObligationDepart" style="width:99.5%"  readonly/>'
				},
				{
					label : '时间：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDate" id="obstacleDate" style="width:99%" readonly/>'
				},
				{
					label : '障碍处所：',
					itemColspan : 1,
					item : '<input type="text" name="obstaclePlace" id="obstaclePlace" style="width:99%" readonly/>'
				},
				{
					label : '设备类别：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDeviceType" id="obstacleDeviceType" style="width:99.5%" readonly/>'
				},
				{
					label : '设备名称：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDeviceName" id="obstacleDeviceName" style="width:99%" readonly/>'
				},
				{
					label : '发生时间：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleOccurDate" id="obstacleOccurDate" style="width:99%" readonly/>'
				},
				{
					label : '恢复时间：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleRecoverDate" id="obstacleRecoverDate" style="width:99%" readonly/>'
				},
				{
					label : '延时分钟：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDelayMinutes" id="obstacleDelayMinutes" style="width:99%" readonly/>'
				},
				{
					label : '受理人：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleReceiver" id="obstacleReceiver" style="width:99%" readonly/>'
				},
				{
					label : '延误客车：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDelayCoach" id="obstacleDelayCoach" style="width:99%" readonly/>'
				},
				{
					label : '延误货车：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDelayTruck" id="obstacleDelayTruck" style="width:99%" readonly/>'
				},
				{
					label : '障碍类别：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleType" id="obstacleType" style="width:99%" readonly/>'
				},
				{
					label : '障碍责任：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleDuty" id="obstacleDuty" style="width:99%" readonly/>'
				},
				{
					label : '障碍现象：',
					itemColspan : 2,
					item : '<textarea name="obstaclePhenomenon" id="obstaclePhenomenon" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '原因分析：',
					itemColspan : 2,
					item : '<textarea name="obstacleReasonAnalyse" id="obstacleReasonAnalyse" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '防范措施：',
					itemColspan : 2,
					item : '<textarea name="obstacleMeasure" id="obstacleMeasure" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '申告人：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleSuingPeople" id="obstacleSuingPeople" style="width:99%" readonly/>'
				},
				{
					label : '申告时间：',
					itemColspan : 1,
					item : '<input type="text" name="obstacleSuingDate" id="obstacleSuingDate" style="width:99%" readonly/>'
				},
				{
					label: '附件：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" ></div>'
//					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
			var form = new FormContainer({
				id : 'networkManageInfoDetailZaShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'435px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'networkManageInfoDetailZaDialog'},
			title:{value:'详情'},
            width:{value:620},
            height:{value:540},
            closeAction : {value:'destroy'},
            mask : {value:true},
            contextPath : {},
            shiftId : {},
            userId:{},
			collectionName:{},
		}
	});
	return NetworkManageInfoDetailZa;
});