/**
 * 考勤详情及打卡界面
 * @author yangsy
 * @date 19-3-29
 */
define('kmms/productionManage/dayDutyManage/dayDutyManageDetail',
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
		 	'kmms/integratedManage/attendanceManage/attendanceManageAdd'
		 ],function(r){
	var BUI = r('bui/common'),Controller = BUI.Component.Controller,
		Grid = r('bui/grid'),
		Data = r('bui/data'),
		Mask = r('bui/mask'),
		FieldSet = r('common/container/FieldsetContainer'),
		ViewUploader = r('common/uploader/ViewUploader'),
		GridBtn = r('common/grid/GridBtn'),
		dayDutyManageDetail = r('kmms/integratedManage/attendanceManage/attendanceManageAdd'),
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
			var store = _self.get('attendanceManageStore');
			store.load();
		},
		bindUI : function(){
			var _self = this;
			var store = _self.get('attendanceManageStore');
			var table = _self.getChild("attendanceManageDetailGrid",true);
			var tbar = table.get('tbar');
			
			 /**
             * 操作按钮
             */
            table.on('cellclick',function(ev){
                var record = ev.record, //点击行的记录
                	  target = $(ev.domTarget),
                	  docId = record.docId,//点击的元素（数据库主键的值String）		
                	  staffName = record.staffName;//点击的元素（数据库主键的值String）
                console.log(docId);
                console.log(staffName);
                
                
                if(target.hasClass('inputBtn')){
                	console.log("打开打卡界面");
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
	                addDialog.show();
	                addDialog.on('completeAddSave',function(e){
	                    tbar.msg(e.result);
	                    addDialog.close();
	                    store.load();
	                });
                }
                /**
                 * 详情
                 */
                if(target.hasClass('workshopDetailBtn')){
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
		        ],
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
			        	width:'20%'
			        },
			        {
			        	title:'考勤情况（早）',
			        	dataIndex:'morning',
			        	elCls:'center',
			        	width:'20%'
			        },
			        {
			        	title:'考勤情况（中）',
			        	dataIndex:'noon',
			        	elCls:'center',
			        	width:'20%'
			        },
			        {
			        	title:'考勤情况（晚）',
			        	dataIndex:'night',
			        	elCls:'center',
			        	width:'20%'
			        },
					{
						title:'打卡',
                        dataIndex:'',
                        elCls : 'center',
                        width:'20%',
                        renderer:function (value) {
                        	var button = '<span  class="grid-command inputBtn">填写</span>';
                            return button;
                        }
					}]
		},
		
		_initGridData : function(){
			var _self = this;
			var store = new Data.Store({
				url:'/kmms/attendanceManageAction/getUsersByOrgId',
				pageSize:20,
				params:{orgId : _self.get('orgId'),collectionName : _self.get('userInfoManage'),attendanceManage : _self.get('attendanceManage'),date : _self.get('date')},
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
            width:{value:850},
            height:{value:850},
            closeAction : {value:'destroy'},
            mask : {value:true},
//            shiftId : {},
			collectionName:{},
			userInfoManage : {},
            attendanceManage : {},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            date:{},
		}
	});
	return attendanceManageDetail;
});