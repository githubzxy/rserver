/**
 * 电路工单详情
 * @author yangsy
 * @date 19-3-1
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail',
		[
		 	'bui/grid',
		 	'bui/common',
		 	'bui/data',
		 	'bui/mask',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/grid/GridBtn',
		 	'common/uploader/ViewUploader',
		 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkshopReplyDetail',
		 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaReplyDetail'
		 ],function(r){
	var BUI = r('bui/common'),Controller = BUI.Component.Controller,
		Grid = r('bui/grid'),
		Data = r('bui/data'),
		Mask = r('bui/mask'),
		ViewUploader = r('common/uploader/ViewUploader'),
		GridBtn = r('common/grid/GridBtn'),
		circuitWorkOrderWorkshopReplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkshopReplyDetail'),
		circuitWorkOrderWorkAreaReplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaReplyDetail'),
		FormContainer = r('common/form/FormContainer');
	var CircuitWorkOrderApplyDetail = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
			_self.addChild(_self._createGridTitleDiv());
			_self.addChild(_self._initGridContainer());
		},
		renderUI : function(){
			var _self = this;
			
			$('.circuitWorkOrderApplyDetailDialogClass .bui-stdmod-body').attr("style","overflow-y:hidden;overflow-x:hidden;");
			$('.circuitWorkOrderApplyDetailDialogClass .bui-grid .bui-grid-body').attr("style","overflow-y:scroll;overflow-x:auto;height:100px;");
			
			_self._initSelect();
			//显示数据
			_self._getShowData();
			var store = _self.get('workshopReplyStore');
			store.load();
		},
		bindUI : function(){
			var _self = this;
			
			var table = _self.getChild("workshopReplyDetailGrid",true);
			
			 /**
             * 操作按钮
             */
            table.on('cellclick',function(ev){
                var record = ev.record, //点击行的记录
                	  target = $(ev.domTarget),
                	  docId = record.docId,//点击的元素（数据库主键的值String）
                	  executiveOrgId = record.executiveOrgId;
//                	  busiId = record.busiId;//自定义的业务ID
                console.log(docId);
                console.log(executiveOrgId);
                /**
                 * 详情
                 */
                if(target.hasClass('workshopDetailBtn')){
//                	Mask.maskElement('#circuitWorkOrderApplyDetail');
//                	var infoDialog = new circuitWorkOrderWorkshopReplyDetail({
//                        shiftId:docId,
//                        executiveOrgId:executiveOrgId
//                    });
//                    infoDialog.show();
//        			Mask.unmaskElement('#circuitWorkOrderApplyDetail');
//        			$(".bui-message .bui-ext-close").css("display","none");
                    var infoDialog = new circuitWorkOrderWorkshopReplyDetail({
                        shiftId:docId,
                        executiveOrgId:executiveOrgId
                    });
                    Mask.maskElement('#circuitWorkOrderApplyDetail');
                    infoDialog.show();
                    infoDialog.on('closeDialog',function(e){
                    	infoDialog.close();
	                	Mask.unmaskElement('#circuitWorkOrderApplyDetail');
	                });
                }
                if(target.hasClass('workAreaDetailBtn')){
                	var infoDialog = new circuitWorkOrderWorkAreaReplyDetail({
                		shiftId:docId,
                		executiveOrgId:executiveOrgId
                	});
                	Mask.maskElement('#circuitWorkOrderApplyDetail');
                	infoDialog.show();
                	infoDialog.on('closeDialog',function(e){
                    	infoDialog.close();
	                	Mask.unmaskElement('#circuitWorkOrderApplyDetail');
	                });
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
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/circuitWorkOrderApplyAction/findById',
          		data:{id : shiftId,collectionName : _self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(result){
					var data = result.data;
					if(data){
						$("#formContainer #workOrderName").val(data.workOrderName);
						$("#formContainer #receivePeople").val(data.receivePeople);
						$("#formContainer #receivePeopleId").val(data.receivePeopleId);
						$("#formContainer #receiveTime").val(data.receiveTime);
                        $("#formContainer #executiveStaff").val(data.executiveStaff);
                        $("#formContainer #executiveStaffId").val(data.executiveStaffId);
                        $("#formContainer #systemType").val(data.systemType);
                        $("#formContainer #workOrderType").val(data.workOrderType);
                        $("#formContainer #remark").val(data.remark);
                        if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
            		    $('#formContainer #checkUserName').val(data.checkUserName);
            		    $('#formContainer #checkTime').val(data.checkTime);
            		    $('#formContainer #checkAdvice').val(data.checkAdvice);
                    }
          		}
			});
		},
		
		_createGridTitleDiv : function(){
			var _self = this;
			var div = new Controller({
				id : 'workshopReplyDetail',
				elTagName : 'div',
				elStyle : {'padding-top':'10px'},
				content : '<span>' + '■&nbsp' + '回复信息' + '</span>',
			});
			return div;
		},
		
		_initGridContainer : function(){
			var _self = this;
			var replyDetailGrid = BUI.merge({
				id : 'workshopReplyDetailGrid',
				columns : _self._initColumns(),
				store : _self._initGridData(),
				height:'auto',
				loadMask:true,
				useEmptyCell : false,
				elCls : 'workshopReplyDetailGridClass',
				plugins : [Grid.Plugins.RowNumber],
			});
			var gridBtn = new GridBtn(replyDetailGrid);
			return gridBtn;
		},
		
		_initColumns : function(){
			return [
			        {
			        	title:'部门',
			        	dataIndex:'executiveOrgName',
			        	elCls:'center',
			        	width:'35%'
			        },
			        {
			        	title:'派发执行人',
			        	dataIndex:'replyUserName',
			        	elCls:'center',
			        	width:'30%'
			        },
			        {
			        	title:'状态',
			        	dataIndex:'flowState',
			        	elCls:'center',
			        	width:'15%',
			        	renderer : function(e) {
        					if(e == "0") {
        						return "草稿";
        					} else if(e == "1") {
        						return "待签收";
        					} else if(e == "2") {
        						return "已签收";
        					} else if(e == "3") {
        						return "待提交";
        					} else if(e == "4") {
        						return "待审核";
        					} else if(e == "5"){
        						return "审核不通过";
        					} else if(e == "6"){
        						return "待回复"
        					} else if(e == "6.5"){
        						return "待完成"
        					} else if(e == "7"){
        						return "已派发"
        					} else if(e == "8"){
        						return "已完成"
        					} else if(e == "-1"){
        						return "已结束"
        					}
        				}
			        },
//			        {
//			        	title:'回复内容',
//			        	dataIndex:'workshopReply',
//			        	elCls:'center',
//			        	width:'20%',
//			        	renderer:function (value) {
//                        	var button = '';
//                        	if(value){
//                        		button += '<span  class="grid-command detailBtn">查看车间回复</span>';
//                        	}
//                            return button;
//                        }
//					},
					{
						title:'回复详情',
                        dataIndex:'WorkshopReplyAndDistributeTime',
                        elCls : 'center',
                        width:'20%',
                        renderer:function (value) {
                        	
                        	var valueArray = value.split(",");
                        	console.log(valueArray);
                            var button = '';
                            if((valueArray[0]=="null"&&valueArray[1]=="null")||valueArray[0]!="null"){
                            	button += '<span  class="grid-command workshopDetailBtn">查看车间回复</span>';
                            }else if(valueArray[0]=="null"&&valueArray[1]!="null"){
                            	button += '<span  class="grid-command workAreaDetailBtn">查看工区回复</span>';
                        	}
                            return button;
                        	
//                        	var button = '';
//                        	if(value){
//                        		button += '<span  class="grid-command detailBtn">查看车间回复</span>';
//                        	} else {
//                        		button += '<span  class="grid-command detailBtn">查看工区回复</span>';
//                        	}
//                            return button;
                        }
					}]
		},
		
		_initGridData : function(){
			var _self = this;
			var store = new Data.Store({
				url:'/kmms/circuitWorkOrderWorkshopReplyAction/findAll',
				pageSize:10,
				params:{docId:_self.get('shiftId'),collectionName:"circuitIssueWorkOrder"},
				proxy:{
					method:'post',
					dataType:'json'
				},
			});
			_self.set('workshopReplyStore',store);
			return store;
		},
		
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
            var childs = [
            	{
                    label: '工单名称：',
                    itemColspan: 2,
                    item: '<input type="text" name="workOrderName" id="workOrderName" style="width:99.5%" readonly/>'
                },
                {
                	label: '网管中心：',
                	itemColspan: 1,
                	item: '<input type="text" name="receivePeople" id="receivePeople" style="width:99%" readonly />'+'<input type="hidden" name="receivePeopleId" id="receivePeopleId" readonly/>'
                },
                {
                	label: '签收时间：',
                	itemColspan: 1,
                	item: '<input type="text" name="receiveTime" id="receiveTime" style="width:99%" readonly />'
                },
                {
                	label: '执行部门：',
                	itemColspan: 2,
                	item: '<input type="text" name="executiveStaff" id="executiveStaff" readonly/>'+'<input type="hidden" name="executiveStaffId" id="executiveStaffId" readonly/>'
                },
                {
                    label: '系统类别：',
                    itemColspan: 1,
//                    item: '<select name="systemType" id="systemType" style="width:99.5%" disabled/></select>'
                    item: '<input type="text" name="systemType" id="systemType" style="width:99%" readonly/>'
                },
                {
                	label: '工单类型：',
                	itemColspan: 1,
//                	item: '<select name="workOrderType" id="workOrderType" style="width:99.5%" disabled/></select>'
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
					label : '审核人：',
					itemColspan : 1,
					item : '<input type="text" name="checkUserName" id="checkUserName" style="width:99%" readonly/>'
				},
				{
					label : '审核日期：',
					itemColspan : 1,
					item : '<input type="text" name="checkTime" id="checkTime" style="width:99%" readonly/>'
				},
				{
					label : '审核意见：',
					itemColspan : 2,
					item : '<textarea name="checkAdvice" id="checkAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
				},
//				{
//					label : '工区回复情况：',
//					itemColspan : 2,
//					item : '<textarea name="workareaReply" id="workareaReply" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
            ];
			var form = new FormContainer({
				id : 'circuitWorkOrderApplyDetailShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'260px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'circuitWorkOrderApplyDetailDialog'},
			elCls : {value:'circuitWorkOrderApplyDetailDialogClass'},
			elAttrs : {value : {id:"circuitWorkOrderApplyDetail"}},
			title:{value:'详情'},
            width:{value:850},
            height:{value:850},
            closeAction : {value:'destroy'},
            mask : {value:true},
            contextPath : {},
            shiftId : {},
			collectionName:{},
		}
	});
	return CircuitWorkOrderApplyDetail;
});