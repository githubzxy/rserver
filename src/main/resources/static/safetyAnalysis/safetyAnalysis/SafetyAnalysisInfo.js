/**
 * 详情模块
 */
define('kmms/safetyAnalysis/safetyAnalysis/SafetyAnalysisInfo',[
	'bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader'],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var SafetyAnalysisInfo = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
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
		/**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/commonAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						$("#formContainer #name").val(data.name);
						$("#formContainer #checkDate").val(data.checkDate);
                        $("#formContainer #orgName").val(data.orgName);
                        $("#formContainer #createDate").val(_self._timestampToTime(data.createDate));
                        $("#formContainer #createUserName").val(data.createUserName);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
                    }
          		}
			});
		},
		//时间戳转时间
		_timestampToTime : function(timestamp) {
			if(timestamp){
				var date = new Date(timestamp);
				Y = date.getFullYear() + '-';
				M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
				D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
				h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
		        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
		        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		        return Y+M+D+h+m+s;
			}else{
				return "";
			}
	    },
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
			var childs = [
                {
                    label : '年月份：',
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" style="width:99%" readonly>'+
                        '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label: '召开时间：',
                    itemColspan: 2,
                    item: '<input type="text"  name="checkDate" id="checkDate" style="width:99%" readonly />',
                }, {
                    label : '所属部门：',
                    itemColspan : 2,
                    item : '<input type="text"  name="orgName" id="orgName" style="width:99%" readonly>'
                },{
                    label : '创建时间：',
                    itemColspan : 1,
                    item : '<input type="text"  name="createDate" id="createDate" style="width:99%" readonly>'
                },
                {
                    label : '创建人：',
                    itemColspan : 1,
                    item : '<input type="text"  name="createUserName" id="createUserName" style="width:99%" readonly>'
                },
                {
                    label : '附件：',
                    itemColspan : 2,
                    item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
			var form = new FormContainer({
				id : 'safetyAnalysisInfoShow',
				colNum : colNum,
				formChildrens : childs,
			});
			_self.set('formContainer',form);
			return form;
		},
	},{
		ATTRS : {
			id : {value : 'safetyAnalysisInfoDialog'},
			title:{value:'详细信息'},
            width:{value:620},
            height:{value:330},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return SafetyAnalysisInfo;
});