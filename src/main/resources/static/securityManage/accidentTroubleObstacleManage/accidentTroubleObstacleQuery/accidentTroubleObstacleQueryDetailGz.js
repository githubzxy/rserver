/**
 * 故障详情
 * @author yangsy
 * @date 19-2-27
 */
define('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailGz',
		[
		 	'bui/common',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/uploader/ViewUploader',
		 ],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var AccidentTroubleObstacleDetailGz = BUI.Overlay.Dialog.extend({
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
		_initSelect: function(){
        	$("#formContainer #infoResult").append("<option  value='1'>事故</option>");
			$("#formContainer #infoResult").append("<option  value='2'>故障</option>");
			$("#formContainer #infoResult").append("<option  value='3'>障碍</option>");
			
			$("#formContainer #lost").append("<option  value='0'>否</option>");
			$("#formContainer #lost").append("<option  value='1'>是</option>");
        },
        
        _setInfoResultValue : function(value){
        	if(value=="1"){
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
				url:'/kmms/accidentTroubleObstacleQueryAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(result){
					var data = result.data;
					if(data){
						$("#formContainer #troubleObligationDepart").val(data.troubleObligationDepart);

                        $("#formContainer #createDate").val(data.createDate);
                        $("#formContainer #type").val(data.type);
                        $("#formContainer #backOrgName").val(data.backOrgName);
                        $("#formContainer #backPerson").val(data.backPerson);
                        $("#formContainer #infoResult").val(_self._setInfoResultValue(data.infoResult));
                        $("#formContainer #lost").val(_self._setLostValue(data.lost));
                        $("#formContainer #detail").val(data.detail);
                        $("#formContainer #remark").val(data.remark);
                        
//                        $("#formContainer #troubleWorkshop").val(data.troubleWorkshop);
//                        $("#formContainer #troubleWorkarea").val(data.troubleWorkarea);
                        $('#formContainer #troubleLineName').val(data.troubleLineName);
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
            		    if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            				
            			}
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
//				{
//				    label : '时间：',
//				    itemColspan : 1,
//				    item : '<input type="text" name="createDate" id="createDate" style="width:99%" readonly/>'
//				},
//				{
//				    label : '类型：',
//				    itemColspan : 1,
//				    item : '<input type="text" name="type" id="type" style="width:99%" readonly/>'
//				},
//				{
//				    label : '反馈部门：',
//				    itemColspan : 1,
//				    item : '<input type="text" name="backOrgName" id="backOrgName" style="width:99%" readonly/>'
//				},
//				{
//				    label : '反馈人：',
//				    itemColspan : 1,
//				    item : '<input type="text" name="backPerson" id="backPerson" style="width:99%" readonly/>'
//				},
//				{
//				    label : '信息后果：',
//				    itemColspan : 1,
//				    item : '<input type="text" name="infoResult" id="infoResult" style="width:99%" readonly/>'
//				},
//				{
//					label : '是否遗留：',
//					itemColspan : 1,
//					item : '<input type="text" name="lost" id="lost" style="width:99%" readonly/>'
//				},
//				{
//				    label : '内容及处理情况：',
//				    itemColspan : 2,
//				    item : '<textarea name="detail" id="detail" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '备注：',
//					itemColspan : 2,
//					item : '<textarea name="remark" id="remark" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '故障信息',
//					itemColspan : 2,
//					item : '<input type="text" style="width:99.5%" disabled="disabled"/>'
//				},
				{
					label : '责任部门：',
					itemColspan : 2,
					item : '<input type="text" name="troubleObligationDepart" id="troubleObligationDepart" style="width:99.5%"  readonly/>'
				},
//				{
//					label : '车间：',
//					itemColspan : 1,
//					item : '<input type="text" name="troubleWorkshop" id="troubleWorkshop" style="width:99%" readonly/>'
//				},
//				{
//					label : '工区：',
//					itemColspan : 1,
//					item : '<input type="text" name="troubleWorkarea" id="troubleWorkarea" style="width:99%" readonly/>'
//				},
				{
					label : '线名：',
					itemColspan : 1,
					item : '<input type="text" name="troubleLineName" id="troubleLineName" style="width:99%" readonly/>'
				},
				{
					label : '时间：',
					itemColspan : 1,
					item : '<input type="text" name="troubleDate" id="troubleDate" style="width:99%" readonly/>'
				},
				{
				    label : '故障地点：',
				    itemColspan : 1,
				    item : '<input type="text" name="troubleSite" id="troubleSite" style="width:99%" readonly/>'
				},
				{
					label : '故障设备：',
					itemColspan : 1,
					item : '<input type="text" name="troubleDevice" id="troubleDevice" style="width:99%" readonly/>'
				},
				{
					label : '发生时间：',
					itemColspan : 1,
					item : '<input type="text" name="troubleOccurDate" id="troubleOccurDate" style="width:99%" readonly/>'
				},
				{
					label : '恢复时间：',
					itemColspan : 1,
					item : '<input type="text" name="troubleRecoverDate" id="troubleRecoverDate" style="width:99%" readonly/>'
				},
				{
					label : '延时分钟：',
					itemColspan : 1,
					item : '<input type="text" name="troubleDelayMinutes" id="troubleDelayMinutes" style="width:99%" readonly/>'
				},
				{
					label : '影响车次：',
					itemColspan : 1,
					item : '<input type="text" name="troubleTrainNumber" id="troubleTrainNumber" style="width:99%" readonly/>'
				},
				{
					label : '故障概况：',
					itemColspan : 2,
					item : '<textarea name="troubleGeneral" id="troubleGeneral" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '处理经过：',
					itemColspan : 2,
					item : '<textarea name="troubleDisposePass" id="troubleDisposePass" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '原因分析：',
					itemColspan : 2,
					item : '<textarea name="troubleReasonAnalyse" id="troubleReasonAnalyse" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '防范措施：',
					itemColspan : 2,
					item : '<textarea name="troubleMeasure" id="troubleMeasure" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '定性定责：',
					itemColspan : 2,
					item : '<textarea name="troubleFixDuty" id="troubleFixDuty" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '考核情况：',
					itemColspan : 2,
					item : '<textarea name="troubleCheckSituation" id="troubleCheckSituation" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '备注：',
					itemColspan : 2,
					item : '<textarea name="troubleRemark" id="troubleRemark" style="width:99.5%;height:50px" readonly/></textarea>'
				},{
                    label : '附件：',
                    itemColspan : 2,
    				item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
			var form = new FormContainer({
				id : 'accidentTroubleObstacleDetailGzShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'accidentTroubleObstacleDetailGzDialog'},
			title:{value:'详情'},
            width:{value:650},
            height:{value:500},
            closeAction : {value:'destroy'},
            mask : {value:true},
            contextPath : {},
            shiftId : {},
			collectionName:{},
		}
	});
	return AccidentTroubleObstacleDetailGz;
});