/**
 * 包含事故信息的详情
 * @author yangsy
 * @date 19-1-17
 */
define('kmms/dayToJobManagement/networkManageInfo/networkManageInfoDetailSg',
		[
		 	'bui/common',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/uploader/ViewUploader',
		 ],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var NetworkManageInfoDetailSg = BUI.Overlay.Dialog.extend({
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
        	$("#formContainer #infoResult").append("<option  value='0'>无</option>");
        	$("#formContainer #infoResult").append("<option  value='1'>事故</option>");
			$("#formContainer #infoResult").append("<option  value='2'>故障</option>");
			$("#formContainer #infoResult").append("<option  value='3'>障碍</option>");
			
			$("#formContainer #lost").append("<option  value='0'>否</option>");
			$("#formContainer #lost").append("<option  value='1'>是</option>");
        },
        
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
                        
                        $("#formContainer #accidentObligationDepart").val(data.accidentObligationDepart);
                        $("#formContainer #accidentLineName").val(data.accidentLineName);
                        $("#formContainer #accidentDate").val(data.accidentDate);
                        $('#formContainer #accidentLevel').val(data.accidentLevel);
            		    $('#formContainer #accidentDetailLevel').val(data.accidentDetailLevel);
            		    $('#formContainer #accidentDigest').val(data.accidentDigest);
            		    $('#formContainer #accidentReason').val(data.accidentReason);
            		    $('#formContainer #accidentGeneral').val(data.accidentGeneral);
            		    $('#formContainer #accidentDutySituation').val(data.accidentDutySituation);
            		    $("#formContainer #system").val(data.system);
                        $("#formContainer #systemType").val(data.systemType);
            		    if(data.accidentUploadFileArr) {
            				_self._initViewUploader(data.accidentUploadFileArr);
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
					label : '事故信息',
					itemColspan : 2,
					item : '<input type="text" style="width:99.5%" disabled="disabled"/>'
				},
				{
					label : '责任部门：',
					itemColspan : 2,
					item : '<input type="text" name="accidentObligationDepart" id="accidentObligationDepart" style="width:99.5%"  readonly/>'
				},
				{
					label : '线名：',
					itemColspan : 1,
					item : '<input type="text" name="accidentLineName" id="accidentLineName" style="width:99%" readonly/>'
				},
				{
                    label : '时间：',
                    itemColspan : 1,
                    item : '<input type="text" name="accidentDate" id="accidentDate" style="width:99%" readonly/>'
                },
                {
                    label : '事故等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="accidentLevel" id="accidentLevel" style="width:99%" readonly/>'
                },
                {
                    label : '详细等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="accidentDetailLevel" id="accidentDetailLevel" style="width:99%" readonly/>'
                },
                {
                    label : '事故摘要：',
                    itemColspan : 2,
                    item : '<textarea name="accidentDigest" id="accidentDigest" style="width:99.5%;height:50px" readonly/></textarea>'
                },
                {
                	label : '事故原因：',
                	itemColspan : 2,
                	item : '<textarea name="accidentReason" id="accidentReason" style="width:99.5%;height:50px" readonly/></textarea>'
                },
                {
                	label : '事故概况：',
                	itemColspan : 2,
                	item : '<textarea name="accidentGeneral" id="accidentGeneral" style="width:99.5%;height:50px" readonly/></textarea>'
                },
                {
                	label : '定责情况：',
                	itemColspan : 2,
                	item : '<textarea name="accidentDutySituation" id="accidentDutySituation" style="width:99.5%;height:50px" readonly/></textarea>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" ></div>'
//					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
			var form = new FormContainer({
				id : 'networkManageInfoDetailSgShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'435px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'networkManageInfoDetailSgDialog'},
//            previewUrl:{value:'/pageoffice/openPage?filePath='},
//            downloadUrl:{value:'/kmms/commonAction/download?path='},
			title:{value:'详情'},
            width:{value:620},
            height:{value:540},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            shiftId : {},
            userId:{},
			collectionName:{},
		}
	});
	return NetworkManageInfoDetailSg;
});