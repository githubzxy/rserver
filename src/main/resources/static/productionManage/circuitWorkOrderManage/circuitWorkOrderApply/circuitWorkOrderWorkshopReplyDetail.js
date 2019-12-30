/**
 * 车间回复详情界面
 * @author yangsy
 * @date 19-3-7
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkshopReplyDetail',
		[
		 	'bui/common',
		 	'bui/form',
		 	'bui/grid',
		 	'bui/select',
			'bui/data',
			'bui/calendar',
			'common/grid/GridBtn',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
			'common/uploader/ViewUploader',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	Grid = r('bui/grid'),
    	GridBtn = r('common/grid/GridBtn'),
    	  Calendar = r('bui/calendar'),
    	  FormContainer= r('common/form/FormContainer'),
    	  OrganizationPicker = r('common/org/OrganizationPicker'),
    	  PostLoad = r('common/data/PostLoad'),
    	  UpdateUploader = r('common/uploader/UpdateUploader'),
    	  ViewUploader = r('common/uploader/ViewUploader');
    var CircuitWorkOrderWorkshopReplyDetail = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
//            _self.addChild(_self._initFormContainer());
            _self.addChild(_self._initGridContainer());
        },
        renderUI:function(){
			var _self=this;
			$("#circuitWorkOrderWorkshopReplyDetail .bui-ext-close").css("display","none");
			$('.circuitWorkOrderWorkshopReplyDetailDialogClass .bui-stdmod-body').attr("style","overflow-y:hidden;overflow-x:hidden;");
			$('.circuitWorkOrderWorkshopReplyDetailDialogClass .bui-grid .bui-grid-body').attr("style","overflow-y:scroll;overflow-x:auto;height:300px;");
			var store = _self.get('workshopReplyStore');
			store.load();
//			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
            //定义按键
            var buttons = [
                 {
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                    	var close = _self.get('close');
                        if(close){
                        	close.call(_self);
                        }
//                        if(this.onCancel() !== false){
//                            this.close();
//                        }
                    }
                }
            ];
            _self.set('buttons',buttons);
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
			        	title:'车间部门',
			        	dataIndex:'executiveOrgName',
			        	elCls:'center',
			        	width:'15%'
			        },
			        {
			        	title:'车间回复人',
			        	dataIndex:'replyUserName',
			        	elCls:'center',
			        	width:'15%'
			        },
			        {
			        	title:'回复时间',
			        	dataIndex:'replyTime',
			        	elCls:'center',
			        	width:'25%',
			        	renderer:Grid.Format.datetimeRenderer
			        },
//			        {
//			        	title:'状态',
//			        	dataIndex:'flowState',
//			        	elCls:'center',
//			        	width:'10%',
//			        	renderer : function(e) {
//        					if(e == "7"){
//        						return "待回复"
//        					} else if(e == "8"){
//        						return "已回复"
//        					} else if(e == "-1"){
//        						return "已结束"
//        					}
//        				}
//			        },
					{
						title:'回复内容',
                        dataIndex:'workshopReply',
                        elCls : 'center',
                        width:'45%',
					}]
		},
		
		_initGridData : function(){
			var _self = this;
			var store = new Data.Store({
				url:'/kmms/circuitWorkOrderWorkshopReplyAction/findIssueReplyAll',
				pageSize:10,
				params:{docId:_self.get('shiftId'),executiveOrgId:_self.get('executiveOrgId'),collectionName : "circuitIssueWorkOrderReply"},
				proxy:{
					method:'post',
					dataType:'json'
				},
			});
			_self.set('workshopReplyStore',store);
			return store;
		},
        
        
        /**
		 * 初始化上传文件（仅用于查看）
		 */
//		_initViewUploader:function(uploadFiles){
//			var _self = this;
//			var viewFiles = new ViewUploader({
//				render: '#formContainer #viewUploadfile',
//				alreadyItems : uploadFiles,
//				previewOnline : true
//			});
//			viewFiles.render();
//		},

        /**
         * 获取显示数据
         */
//        _getShowData : function(){
//            var _self = this,shiftId = _self.get('shiftId'),executiveOrgId = _self.get('executiveOrgId');
//            $.ajax({
//                url:'/kmms/circuitWorkOrderWorkshopReplyAction/findById',
//                data:{id : shiftId,executiveOrgId,executiveOrgId,collectionName : "circuitIssueWorkOrder"},
//                type:'post',
//                dataType:"json",
//                success:function(result){
//                    var data = result.data;
//                    if(data){
//                    	$("#formContainer #replyTime").val(data.replyTime);
//                        $("#formContainer #workshopReply").val(data.workshopReply);
//                    }
//                }
//            })
//        },
		
        /**
         * 初始化FormContainer
         */
//        _initFormContainer : function(){
//            var _self = this;
//            var colNum = 2;
//            var childs = [
//				{
//					label : '回复时间：',
//					itemColspan : 2,
//					item : '<input type="text" name="replyTime" id="replyTime" style="width:99.5%;" readonly/>'
//				},
//				{
//					label : '回复说明：',
//					itemColspan : 2,
//					item : '<textarea name="workshopReply" id="workshopReply" style="width:99.5%;height:220px" readonly/></textarea>'
//				},
//            ];
//            var form = new FormContainer({
//                id : 'circuitWorkOrderWorkshopReplyDetailForm',
//                colNum : colNum,
//                formChildrens : childs,
////                elStyle:{overflowY:'scroll',height:'260px'}
//            });
//            _self.set('formContainer',form);
//            return form;
//        }
    },{
        ATTRS : {
            id:{value : 'circuitWorkOrderWorkshopReplyDetailDialog'},
            elAttrs:{value: {id:"circuitWorkOrderWorkshopReplyDetail"}},
            elCls : {value:'circuitWorkOrderWorkshopReplyDetailDialogClass'},
            title:{value:'车间回复详情'},
            width:{value:800},
            height:{value:400},
            closeAction:{value:'destroy'},
            mask:{value:true},
            collectionName:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            executiveOrgId:{},
            shiftId:{},//用于查询单条数据
            close:{
	           	 value : function(){
	           		 var _self = this;
	           		 _self.fire("closeDialog",{
	                        result : "close"
	                    });
	           	 }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return CircuitWorkOrderWorkshopReplyDetail;
});