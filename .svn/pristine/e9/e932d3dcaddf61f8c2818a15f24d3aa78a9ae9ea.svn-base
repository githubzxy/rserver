/**
 * 维修方案申请详情模块
 */
define('kmms/maintainManage/maintainPlan/maintainApproved/maintainApprovedInfo',['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var maintainApprovedInfo = BUI.Overlay.Dialog.extend({
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
		 * 获取显示数据
		 */
		_getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId'),delData={};
            $.ajax({
                url:'/kmms/commonAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #name").val(data.name);
                        $("#formContainer #depart").val(data.depart);
                        $("#formContainer #safeAuditRemark").val(data.safeAuditRemark);
                        $("#formContainer #dispatchAuditRemark").val(data.dispatchAuditRemark);
                        $("#formContainer #leaderAuditRemark").val(data.leaderAuditRemark);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
                    }
                }
            })
        },
        /**
		 * 初始化上传文件（仅用于查看）
		 */
		_initViewUploader:function(uploadFiles){
			var _self = this;
			var viewFiles = new ViewUploader({
				render: '#formContainer #viewUploadfile',
				alreadyItems : uploadFiles
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
                    label : '维修项目：',
                    redStarFlag : false,
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99%" readonly>'+
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label : '提交部门：',
                    redStarFlag : false,
                    itemColspan : 2,
                    item : '<input type="text" name="depart" id="depart" readonly data-rules="{required:true}"/>'
                },{
                    label : '安全科意见：',
                    redStarFlag : false,
                    itemColspan : 2,
                    item : '<textarea type="text" name="safeAuditRemark" id="safeAuditRemark" readonly ></textarea>'
                },{
                    label : '安全调度指挥中心意见：',
                    redStarFlag : false,
                    itemColspan : 2,
                    item : '<textarea type="text" name="dispatchAuditRemark" id="dispatchAuditRemark" readonly ></textarea>'
                },{
                    label : '段领导意见：',
                    redStarFlag : false,
                    itemColspan : 2,
                    item : '<textarea type="text" name="leaderAuditRemark" id="leaderAuditRemark" readonly ></textarea>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'maintainApplyInfoForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        },
		
	},{
		ATTRS : {
			id : {value : 'maintainApprovedInfoDialog'},
			title:{value:'详情'},
			width:{value:610},
            height:{value:450},
            previewUrl:{value:'/pageoffice/openPage?filePath='},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return maintainApprovedInfo;
});