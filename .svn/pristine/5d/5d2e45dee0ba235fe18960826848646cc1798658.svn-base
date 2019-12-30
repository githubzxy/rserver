/**
 * II级维修数据详情
 * @author yangsy
 * @date 19-2-22
 */
define('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyDetail',
		[
		 	'bui/common',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/uploader/ViewUploader',
		 ],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer = r('common/form/FormContainer');
	var PointInnerSecondMaintainApplyDetail = BUI.Overlay.Dialog.extend({
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
				alreadyItems : uploadFiles
			});
			viewFiles.render();
		},
		
		/**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/pointInnerSecondMaintainApplyAction/findById',
          		data:{id : shiftId,collectionName : _self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(result){
					var data = result.data;
					if(data){
						$("#formContainer #constructionProject").val(data.constructionProject);
                        $("#formContainer #submitOrgName").val(data.submitOrgName);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
            		    $('#formContainer #approver').val(data.approver);
            		    $('#formContainer #approveDate').val(data.approveDate);
            		    $('#formContainer #approveAdvice').val(data.approveAdvice);
            		    $('#formContainer #workareaReply').val(data.workareaReply);
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
		
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
            var childs = [
            	{
                    label: '维修方案：',
                    itemColspan: 2,
                    item: '<input type="text" name="constructionProject" id="constructionProject" style="width:99.5%" readonly/>'
                },
                {
                    label: '提交部门：',
                    itemColspan: 2,
                    item: '<input type="text" name="submitOrgName" id="submitOrgName" readonly/>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
				{
					label : '审批人：',
					itemColspan : 1,
					item : '<input type="text" name="approver" id="approver" style="width:99%" readonly/>'
				},
				{
					label : '审批日期：',
					itemColspan : 1,
					item : '<input type="text" name="approveDate" id="approveDate" style="width:99%" readonly/>'
				},
				{
					label : '车间审批意见：',
					itemColspan : 2,
					item : '<textarea name="approveAdvice" id="approveAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '工区回复情况：',
					itemColspan : 2,
					item : '<textarea name="workareaReply" id="workareaReply" style="width:99.5%;height:50px" readonly/></textarea>'
				},
            ];
			var form = new FormContainer({
				id : 'pointInnerSecondMaintainApplyDetailShow',
				colNum : colNum,
				formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'pointInnerSecondMaintainApplyDetailDialog'},
			title:{value:'详情'},
            width:{value:650},
            height:{value:420},
            closeAction : {value:'destroy'},
            mask : {value:true},
            contextPath : {},
            shiftId : {},
			collectionName:{},
		}
	});
	return PointInnerSecondMaintainApplyDetail;
});