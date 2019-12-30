/**
 * 工区回复界面
 * @author yangsy
 * @date 19-3-6
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderReply/circuitWorkOrderWorkAreaReplyAdd',
		[
		 	'bui/common',
		 	'bui/form',
		 	'bui/select',
			'bui/data',
			'bui/calendar',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
			'common/uploader/ViewUploader',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	  Calendar = r('bui/calendar'),
    	  FormContainer= r('common/form/FormContainer'),
    	  OrganizationPicker = r('common/org/OrganizationPicker'),
    	  PostLoad = r('common/data/PostLoad'),
    	  UpdateUploader = r('common/uploader/UpdateUploader'),
    	  ViewUploader = r('common/uploader/ViewUploader');
    var CircuitWorkOrderWorkAreaReplyAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
            //定义按键
            var buttons = [
                 {
                    text:'回复',
                    elCls : 'button',
                    handler : function(){
                        var pass = _self.get('pass');
                        if(pass){
                        	pass.call(_self);
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
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/circuitWorkOrderApplyAction/findById',
                data:{id : shiftId,collectionName : "circuitWorkOrder"},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #workOrderName").val(data.workOrderName);
                        $("#formContainer #executiveStaff").val(data.executiveStaff);
                        $("#formContainer #executiveStaffId").val(data.executiveStaffId);
                        $("#formContainer #systemType").val(data.systemType);
                        $("#formContainer #workOrderType").val(data.workOrderType);
                        $("#formContainer #remark").val(data.remark);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
                        $("#formContainer #checkAdvice").val(data.checkAdvice);
                        $("#formContainer #approveAdvice").val(data.approveAdvice);
                    }
                }
            })
        },
		
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
				{
					label : '回复说明：',
					redStarFlag : true,
					itemColspan : 2,
					item : '<textarea name="workAreaReply" id="workAreaReply" style="width:99.5%;height:100px" data-rules="{required:true}"/></textarea>'
				},
            	{
                    label: '工单名称：',
                    itemColspan: 2,
                    item: '<input type="text" name="workOrderName" id="workOrderName" style="width:99.5%" readonly/>'
                },
                {
                	label: '执行部门：',
                	itemColspan: 2,
                	item: '<input type="text" name="executiveStaff" id="executiveStaff" readonly/>'+'<input type="hidden" name="executiveStaffId" id="executiveStaffId" readonly/>'
                },
                {
                    label: '系统类别：',
                    itemColspan: 1,
                    item: '<input type="text" name="systemType" id="systemType" style="width:99%" readonly/>'
                },
                {
                	label: '工单类型：',
                	itemColspan: 1,
                	item: '<input type="text" name="workOrderType" id="workOrderType" style="width:99%" readonly/>'
                },
                {
                	label: '备注：',
                	itemColspan: 2,
                	item: '<textarea name="remark" id="remark" style="width:99.5%;height:50px" readonly/></textarea>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
				{
                	label : '技术科科长审核意见：',
                	itemColspan : 2,
                	item : '<textarea name="checkAdvice" id="checkAdvice" style="width:99.5%;height:100px" readonly/></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'circuitWorkOrderWorkAreaReplyAddForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'260px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id:{value : 'circuitWorkOrderWorkAreaReplyAddDialog'},
            elAttrs:{value: {id:"circuitWorkOrderWorkAreaReplyAdd"}},
            title:{value:'工区回复'},
            width:{value:650},
            height:{value:360},
            closeAction:{value:'destroy'},
            mask:{value:true},
            circuitDistributeWorkOrderReply:{},
            circuitDistributeWorkOrder:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            shiftId:{},//用于查询单条数据
            pass:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('circuitWorkOrderWorkAreaReplyAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
//	        		data.collectionName=_self.get('collectionName');
	    	    	data.circuitDistributeWorkOrderReply=_self.get('circuitDistributeWorkOrderReply');
	        		data.circuitDistributeWorkOrder=_self.get('circuitDistributeWorkOrder');
	        		data.replyUserId = _self.get("userId");
	        		data.replyUserName = _self.get("userName");
	        		data.distributeOrgId = _self.get("orgId");
	        		data.distributeOrgName = _self.get("orgName");
	        		data.flowState="7.5";//回复过但未完成
//	        		data.flowState="8";//回复
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/circuitWorkOrderWorkAreaReplyAction/replyDoc',
        				el : _self.get('el'),
        				loadMsg : '回复中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});
                }
            },
            events : {
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return CircuitWorkOrderWorkAreaReplyAdd;
});