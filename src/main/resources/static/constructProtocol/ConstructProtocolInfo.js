/**
 * 详情模块
 */
define('kmms/constructProtocol/ConstructProtocolInfo',['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
	    ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var ConstructProtocolInfo = BUI.Overlay.Dialog.extend({
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
	                        $("#formContainer #proName").val(data.proName);
	                        $("#formContainer #proCompany").val(data.proCompany);
	                        $("#formContainer #line").val(data.line);
	                        $("#formContainer #workShop").val(data.workShop);
	                        $("#formContainer #beginDate").val(data.beginDate);
	                        $("#formContainer #overDate").val(data.overDate);
	                        $("#formContainer #proPlace").val(data.proPlace);
	                        $("#formContainer #isContract").val(data.isContract);
	                        if(data.uploadFileArr) {
	            				_self._initViewUploader(data.uploadFileArr);
	            			}
	                    }
	                }
	            })
	            _self.set('delData',delData);
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
                    label : '协议名称：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text"  name="proName" id="proName"  style="width:99%" readonly>'
                },{
                    label : '施工单位：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="proCompany" id="proCompany" readonly />'
                },{
                    label : '所属线别：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="line" id="line" readonly />'
                },{
                    label : '责任车间：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="workShop" id="workShop" readonly />'
                },{
                    label : '开工日期：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="beginDate" id="beginDate" readonly />'
                },{
                    label : '竣工日期：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="overDate" id="overDate" readonly />'
                },{
                    label : '工程地点：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="proPlace" id="proPlace" readonly />'
                },{
                    label : '是否签订技术服务合同：',
                    redStarFlag : false,
                    itemColspan : 1,
                    item : '<input type="text" name="isContract" id="isContract" readonly />'
                },{
                    label : '上传附件：',
                    itemColspan : 2,
                    item : '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'constructProtocolInfoForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        },
		
	},{
		ATTRS : {
			id : {value : 'constructProtocolInfoDialog'},
			title:{value:'详情'},
			width:{value:660},
            height:{value:340},
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
	return ConstructProtocolInfo;
});