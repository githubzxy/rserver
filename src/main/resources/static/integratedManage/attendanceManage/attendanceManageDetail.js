/**
 * 考勤详情及打卡界面
 * @author yangsy
 * @date 19-3-29
 */
define('kmms/integratedManage/attendanceManage/attendanceManageDetail',
		[
		 	'bui/grid',
		 	'bui/common',
		 	'bui/data',
		 	'bui/mask',
		 	'common/container/FieldsetContainer',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/grid/GridBtn',
		 	'common/uploader/ViewUploader',
		 	'kmms/integratedManage/attendanceManage/attendanceManageAdd',
		 	'kmms/integratedManage/attendanceManage/attendanceUserChange',
		 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkshopReplyDetail',
		 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaReplyDetail'
		 ],function(r){
	var BUI = r('bui/common'),Controller = BUI.Component.Controller,
		Grid = r('bui/grid'),
		Data = r('bui/data'),
		Mask = r('bui/mask'),
		FieldSet = r('common/container/FieldsetContainer'),
		ViewUploader = r('common/uploader/ViewUploader'),
		GridBtn = r('common/grid/GridBtn'),
		PostLoad = r('common/data/PostLoad'),
		attendanceManageAdd = r('kmms/integratedManage/attendanceManage/attendanceManageAdd'),
		attendanceUserChange = r('kmms/integratedManage/attendanceManage/attendanceUserChange'),
		circuitWorkOrderWorkshopReplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkshopReplyDetail'),
		circuitWorkOrderWorkAreaReplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaReplyDetail'),
		FormContainer = r('common/form/FormContainer');
	var attendanceManageDetail = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.set("title",_self.get("orgName")+_self.get("date")+"的考勤情况")
			var GridForm = new FieldSet({id : 'userFormFieldSet',items:[_self._initUserFormArea()]});
			_self.addChild(GridForm);
		},
		renderUI : function(){
			var _self = this;
			
			$('.attendanceManageDetailDialogClass .bui-stdmod-body').attr("style","overflow-y:hidden;overflow-x:hidden;");
			$('.attendanceManageDetailDialogClass .bui-grid .bui-grid-body').attr("style","overflow-y:scroll;overflow-x:auto;height:400px;");
			
//			_self._initSelect();
			//显示数据
//			_self._getShowData();
			var store = _self.get('attendanceManageStore');
			store.load();
		},
		bindUI : function(){
			var _self = this;
			var store = _self.get('attendanceManageStore');
			var table = _self.getChild("attendanceManageDetailGrid",true);
			var tbar = table.get('tbar');
			
			$('.changeBtn').on('click',function(e){
				var addDialog = new attendanceUserChange({
					userInfoManage:_self.get('userInfoManage'),
            		attendanceManage:_self.get('attendanceManage'),
                    userId:_self.get('userId'),
                    userName:_self.get('userName'),
                    orgId:_self.get('orgId'),
                    orgName:_self.get('orgName'),
                    date:_self.get('date'),
//                    docId:docId,
//                    staffName:staffName,
                });
            	Mask.maskElement('#attendanceManageDetail');
                addDialog.show();
                addDialog.on('completeAddSave',function(e){
                    tbar.msg(e.result);
                    addDialog.close();
                    store.load();
                    Mask.unmaskElement('#attendanceManageDetail');
                });
                addDialog.on('closeDialog',function(e){
                	addDialog.close();
                	Mask.unmaskElement('#attendanceManageDetail');
                });
            });
			
			 /**
             * 操作按钮
             */
            table.on('cellclick',function(ev){
                var record = ev.record, //点击行的记录
                	  target = $(ev.domTarget),
                	  docId = record.docId,//点击的元素（数据库主键的值String）		
                	  staffName = record.staffName;
                console.log(docId);
                console.log(staffName);
                
                
                if(target.hasClass('inputBtn')){
//                	console.log("打开打卡界面");
                	var addDialog = new attendanceManageAdd({
                		attendanceManage:_self.get('attendanceManage'),
                        userId:_self.get('userId'),
                        userName:_self.get('userName'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName'),
                        date:_self.get('date'),
                        docId:docId,
                        staffName:staffName,
	                });
                	Mask.maskElement('#attendanceManageDetail');
	                addDialog.show();
	                addDialog.on('completeAddSave',function(e){
	                    tbar.msg(e.result);
	                    addDialog.close();
	                    store.load();
	                    Mask.unmaskElement('#attendanceManageDetail');
	                });
	                addDialog.on('closeDialog',function(e){
	                	addDialog.close();
	                	Mask.unmaskElement('#attendanceManageDetail');
	                });
                }
                
                /**
                 * 从本部门调回到原部门
                 */
//                if(target.hasClass('toBackBtn')){
//                	BUI.Message.Confirm('确认要调回吗？',function(){
//                		var postLoad = new PostLoad({
//                			url : '/kmms/attendanceManageAction/removeToBackDoc.cn',
//                			el : _self.get('el'),
//                			loadMsg : '调回中...'
//                		});
//                		postLoad.load({id:docId,collectionName:_self.get('attendanceUserChange')},function (res) {
//                			tbar.msg(res);
//                			store.load();
//                		});
//                	},'question');
//                }
                /**
                 * 从其他部门调回到本部门
                 */
                if(target.hasClass('backBtn')){
                	BUI.Message.Confirm('确认要调回吗？',function(){
	                       var postLoad = new PostLoad({
	                           url : '/kmms/attendanceManageAction/removeBackDoc.cn',
	                           el : _self.get('el'),
	                           loadMsg : '调回中...'
	                       });
	                       postLoad.load({docId:docId,collectionName:_self.get('attendanceUserChange')},function (res) {
	                    	   tbar.msg(res);
	                    	   store.load();
	                       });
             	   },'question');
                }
                
                
                /**
                 * 详情
                 */
                if(target.hasClass('workshopDetailBtn')){
//                	Mask.maskElement('#attendanceManageDetail');
//                	var infoDialog = new circuitWorkOrderWorkshopReplyDetail({
//                        shiftId:docId,
//                        executiveOrgId:executiveOrgId
//                    });
//                    infoDialog.show();
//        			Mask.unmaskElement('#attendanceManageDetail');
//        			$(".bui-message .bui-ext-close").css("display","none");
                    var infoDialog = new circuitWorkOrderWorkshopReplyDetail({
                        shiftId:docId,
                        executiveOrgId:executiveOrgId
                    });
                    infoDialog.show();
                }
                if(target.hasClass('workAreaDetailBtn')){
                	var infoDialog = new circuitWorkOrderWorkAreaReplyDetail({
                		shiftId:docId,
                		executiveOrgId:executiveOrgId
                	});
                	infoDialog.show();
                }
            });
			
			
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
			 $("#formContainer #systemType").append("<option value=''>请选择</option>");
			 $("#formContainer #systemType").append("<option  value='传输接入系统'>传输接入系统</option>");
			 $("#formContainer #systemType").append("<option  value='数据网系统'>数据网系统</option>");
			 $("#formContainer #systemType").append("<option  value='GSM-R系统'>GSM-R系统</option>");
			 $("#formContainer #systemType").append("<option  value='其他'>其他</option>");
				
			 $("#formContainer #workOrderType").append("<option value=''>请选择</option>");
			 $("#formContainer #workOrderType").append("<option  value='电路开通'>电路开通</option>");
			 $("#formContainer #workOrderType").append("<option  value='电路变更'>电路变更</option>");
			 $("#formContainer #workOrderType").append("<option  value='电路停用'>电路停用</option>");
			 $("#formContainer #workOrderType").append("<option  value='网络优化'>网络优化</option>");
		 },
		
		/**
		 * 获取显示数据
		 */
//		_getShowData : function(){
//			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
//			$.ajax({
//				url:'/kmms/circuitWorkOrderApplyAction/findById',
//          		data:{id : shiftId,collectionName : _self.get('collectionName')},
//          		type:'post',
//          		dataType:"json",
//          		success:function(result){
//					var data = result.data;
//					if(data){
//						$("#formContainer #workOrderName").val(data.workOrderName);
//						$("#formContainer #receivePeople").val(data.receivePeople);
//						$("#formContainer #receivePeopleId").val(data.receivePeopleId);
//						$("#formContainer #receiveTime").val(data.receiveTime);
//                        $("#formContainer #executiveStaff").val(data.executiveStaff);
//                        $("#formContainer #executiveStaffId").val(data.executiveStaffId);
//                        $("#formContainer #systemType").val(data.systemType);
//                        $("#formContainer #workOrderType").val(data.workOrderType);
//                        $("#formContainer #remark").val(data.remark);
//                        if(data.uploadFileArr) {
//            				_self._initViewUploader(data.uploadFileArr);
//            			}
//            		    $('#formContainer #checkUserName').val(data.checkUserName);
//            		    $('#formContainer #checkTime').val(data.checkTime);
//            		    $('#formContainer #checkAdvice').val(data.checkAdvice);
//                    }
//          		}
//			});
//		},
		
//		_createGridTitleDiv : function(){
//			var _self = this;
//			var div = new Controller({
//				id : 'workshopReplyDetail',
//				elTagName : 'div',
//				elStyle : {'padding-top':'10px'},
//				content : '<span>' + '■&nbsp' + '回复信息' + '</span>',
//			});
//			return div;
//		},
		
		_initUserFormArea : function(){
			var _self = this;
			var attendanceManageDetail = BUI.merge({
				id : 'attendanceManageDetailGrid',
				columns : _self._initColumns(),
				store : _self._initGridData(),
				height:'auto',
				loadMask:true,
				useEmptyCell : false,
				elCls : 'attendanceManageDetailGridClass',
				plugins : [Grid.Plugins.RowNumber],
				tbarItems : [
		                        {
		                            id : 'add',
		                            btnCls : 'button button-small changeBtn',
		                            text : '<i class="icon-plus"></i>添加助勤人员',
		                        }
		        ],
		        paging : false
			});
			var gridBtn = new GridBtn(attendanceManageDetail);
			return gridBtn;
		},
		
		_initColumns : function(){
			return [
			        {
			        	title:'姓名',
			        	dataIndex:'staffName',
			        	elCls:'center',
			        	width:'10%'
			        },
			        {
			        	title:'考勤（早）',
			        	dataIndex:'morning',
			        	elCls:'center',
			        	width:'10%'
			        },
			        {
			        	title:'考勤（中）',
			        	dataIndex:'noon',
			        	elCls:'center',
			        	width:'10%'
			        },
			        {
			        	title:'考勤（晚）',
			        	dataIndex:'night',
			        	elCls:'center',
			        	width:'10%'
			        },
			        {
			        	title:'日勤（小时）',
			        	dataIndex:'daily',
			        	elCls:'center',
			        	width:'15%'
			        },
			        {
			        	title:'轮班（班数）',
			        	dataIndex:'turn',
			        	elCls:'center',
			        	width:'15%'
			        },
			        {
			        	title:'助勤/借调',
			        	dataIndex:'shifts',
			        	elCls:'center',
			        	width:'15%',
		        		renderer:function (value) {
                        	console.log(value);
                        	if(value!=""){
                        		var value1 = value.split(",")[0];
                            	var value2 = value.split(",")[1];
                        		return value1+value2;
                        	}else{
                        		return value;
                        	}
                        }
			        },
					{
						title:'打卡',
                        dataIndex:'shifts',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (value) {
                        	console.log(value);
                        	var button = "";
                        	if(value == ""){
                        		button += '<span  class="grid-command inputBtn">填写</span>';
                        	}
                        	if(value!=""){
                        		var value1 = value.split(",")[0];
                            	var value2 = value.split(",")[1];
                            	if(value2=="人员"){
                            		button += '<span  class="grid-command inputBtn">填写</span>';
                            		button += '<span  class="grid-command backBtn">调回原部门</span>';
                            	}
                            	if(value1=="外派"){
                            		button += '<span  class="grid-command backBtn">调回本部门</span>';
                            	}
                        	}
//                        	button += '<span  class="grid-command inputBtn">借调</span>';
                            return button;
                        }
					}]
		},
		
		_initGridData : function(){
			var _self = this;
			var store = new Data.Store({
				url:'/kmms/attendanceManageAction/getUsersByOrgId',
//				pageSize:20,
				params:{orgId : _self.get('orgId'),collectionName : _self.get('userInfoManage'),attendanceManage : _self.get('attendanceManage'),attendanceUserChange : _self.get('attendanceUserChange'),date : _self.get('date')},
				proxy:{
					method:'post',
					dataType:'json'
				},
			});
			_self.set('attendanceManageStore',store);
			return store;
		},
	},{
		ATTRS : {
			id : {value : 'attendanceManageDetailDialog'},
			elCls : {value:'attendanceManageDetailDialogClass'},
			elAttrs : {value : {id:"attendanceManageDetail"}},
			title:{value:'详情'},
            width:{value:1000},
            height:{value:850},
            closeAction : {value:'destroy'},
            mask : {value:true},
//            contextPath : {},
//            shiftId : {},
			collectionName:{},
			userInfoManage : {},
            attendanceManage : {},
            attendanceUserChange : {},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            date:{},
		}
	});
	return attendanceManageDetail;
});