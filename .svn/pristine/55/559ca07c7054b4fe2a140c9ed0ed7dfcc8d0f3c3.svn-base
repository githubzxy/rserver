/**
 * 维修方案申请数据详情
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyDetail',
		[
		 	'bui/common',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/uploader/ViewUploader',
		 ],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var SkylightMaintenanceApplyDetail = BUI.Overlay.Dialog.extend({
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
				url:'/kmms/skylightMaintenanceApplyAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(result){
					var data = result.data;
					if(data){
						console.log(data);
            		    $("#formContainer #project").val(data.project);
            		    $("#formContainer #type").val(data.type);
                        $("#formContainer #orgSelectName").val(data.orgSelectName);
                        $("#formContainer #createDateStr").val(data.createDateStr);
                        $("#formContainer #createUserName").val(data.createUserName);
                        
                        $('#formContainer #skillAuditor').val(data.skillAuditor);
            		    $('#formContainer #skillAuditDate').val(data.skillAuditDate);
            		    $('#formContainer #safeAuditor').val(data.safeAuditor);
            		    $('#formContainer #safeAuditDate').val(data.safeAuditDate);
            		    $('#formContainer #dispatchAuditor').val(data.dispatchAuditor);
            		    $('#formContainer #dispatchAuditDate').val(data.dispatchAuditDate);
            		    $('#formContainer #approver').val(data.approver);
            		    $('#formContainer #approveDate').val(data.approveDate);
            		    
            		    
            		    $('#formContainer #skillAuditAdvice').val(data.skillAuditAdvice);
            		    $('#formContainer #safeAuditAdvice').val(data.safeAuditAdvice);
            		    $('#formContainer #dispatchAuditAdvice').val(data.dispatchAuditAdvice);
            		    $('#formContainer #approveAdvice').val(data.approveAdvice);
            		    $('#formContainer #situationRemark').val(data.situationRemark);
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
            {
                label : '施工项目：',
                redStarFlag : true,
                itemColspan : 2,
                item : '<input type="text"  name="project" id="project" style="width:99%" readonly>'
            },{
                label : '类型：',
                redStarFlag : true,
                itemColspan : 1,
                item : '<input type="text"  name="type" id="type" style="width:99%" readonly>'
            },{
                label : '提交部门：',
                redStarFlag : true,
                itemColspan : 1,
                item : '<input type="text"  name="orgSelectName" id="orgSelectName" style="width:99%" readonly>'
            },{
                label : '创建时间：',
                redStarFlag : true,
                itemColspan : 1,
                item : '<input type="text"  name="createDateStr" id="createDateStr" style="width:99%" readonly>'
            },
            {
                label : '创建人：',
                redStarFlag : true,
                itemColspan : 1,
                item : '<input type="text"  name="createUserName" id="createUserName" style="width:99%" readonly>'
            },
            {
                label : '附件：',
                itemColspan : 2,
				item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
            },{
				label : '技术科审核人：',
				itemColspan : 1,
				item : '<input type="text" name="skillAuditor" id="skillAuditor" style="width:99%" readonly/>'
			},{
				label : '技术科审核日期：',
				itemColspan : 1,
				item : '<input type="text" name="skillAuditDate" id="skillAuditDate" style="width:99%" readonly/>'
			},{
				label : '安全科审核人：',
				itemColspan : 1,
				item : '<input type="text" name="safeAuditor" id="safeAuditor" style="width:99%" readonly/>'
			},{
				label : '安全科审核日期：',
				itemColspan : 1,
				item : '<input type="text" name="safeAuditDate" id="safeAuditDate" style="width:99%" readonly/>'
			},{
				label : '调度科审核人：',
				itemColspan : 1,
				item : '<input type="text" name="dispatchAuditor" id="dispatchAuditor" style="width:99%" readonly/>'
			},{
				label : '调度科审核日期：',
				itemColspan : 1,
				item : '<input type="text" name="dispatchAuditDate" id="dispatchAuditDate" style="width:99%" readonly/>'
			},{
				label : '审批人：',
				itemColspan : 1,
				item : '<input type="text" name="approver" id="approver" style="width:99%" readonly/>'
			},{
				label : '审批日期：',
				itemColspan : 1,
				item : '<input type="text" name="approveDate" id="approveDate" style="width:99%" readonly/>'
			},{
				label : '技术科审核意见：',
				itemColspan : 2,
				item : '<textarea name="skillAuditAdvice" id="skillAuditAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
			},{
				label : '安全科审核意见：',
				itemColspan : 2,
				item : '<textarea name="safeAuditAdvice" id="safeAuditAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
			},{
				label : '调度科审核意见：',
				itemColspan : 2,
				item : '<textarea name="dispatchAuditAdvice" id="dispatchAuditAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
			},{
				label : '段领导审批意见：',
				itemColspan : 2,
				item : '<textarea name="approveAdvice" id="approveAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
			},{
				label : '车间回复：',
				itemColspan : 2,
				item : '<textarea name="situationRemark" id="situationRemark" style="width:99.5%;height:50px" readonly/></textarea>'
			}
            ];
			var form = new FormContainer({
				id : 'skylightMaintenanceApplyDetailShow',
				colNum : colNum,
				formChildrens : childs,
				elStyle:{overflowY:'scroll',height:'300px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'skylightMaintenanceApplyDetailDialog'},
			title:{value:'详情'},
            width:{value:650},
            height:{value:400},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            shiftId : {},
            userId : {},
			collectionName:{},
		}
	});
	return SkylightMaintenanceApplyDetail;
});