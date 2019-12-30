/**
 * 工区回复详情界面
 * @author yangsy
 * @date 19-3-7
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaReplyDetail',
		[
		 	'bui/common',
		 	'bui/form',
		 	'bui/grid',
		 	'bui/select',
			'bui/data',
			'bui/mask',
			'bui/calendar',
			'common/grid/GridBtn',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
			'common/uploader/ViewUploader',
			'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaDistributeReplyDetail'
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	  Calendar = r('bui/calendar'),
    	  Grid = r('bui/grid'),
    	  GridBtn = r('common/grid/GridBtn'),
    	  Mask = r('bui/mask'),
    	  FormContainer= r('common/form/FormContainer'),
    	  OrganizationPicker = r('common/org/OrganizationPicker'),
    	  PostLoad = r('common/data/PostLoad'),
    	  UpdateUploader = r('common/uploader/UpdateUploader'),
    	  ViewUploader = r('common/uploader/ViewUploader'),
    	  circuitWorkOrderWorkAreaDistributeReplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaDistributeReplyDetail');
    var CircuitWorkOrderWorkAreaReplyDetail = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initGridContainer());
        },
        renderUI:function(){
			var _self=this;
			$("#circuitWorkOrderWorkAreaReplyDetail .bui-ext-close").css("display","none");
			$('.circuitWorkOrderWorkAreaReplyDetailDialogClass .bui-stdmod-body').attr("style","overflow-y:hidden;overflow-x:hidden;");
			$('.circuitWorkOrderWorkAreaReplyDetailDialogClass .bui-grid .bui-grid-body').attr("style","overflow-y:scroll;overflow-x:auto;height:300px;");
			var store = _self.get('workAreaReplyStore');
			store.load();
        },
        bindUI : function(){
            var _self = this;
            
            var table = _self.getChild("workAreaReplyDetailGrid",true);
            
            table.on('cellclick',function(ev){
                var record = ev.record, //点击行的记录
                	  target = $(ev.domTarget),
                	  docId = record.docId,//点击的元素（数据库主键的值String）
                	  distributeOrgId = record.distributeOrgId;
//                console.log(record);
                /**
                 * 详情
                 */
                if(target.hasClass('replyDetailBtn')){
                    var infoDialog = new circuitWorkOrderWorkAreaDistributeReplyDetail({
                        shiftId:docId,
                        distributeOrgId:distributeOrgId
                    });
                    Mask.maskElement('#circuitWorkOrderWorkAreaReplyDetail');
                    infoDialog.show();
                    infoDialog.on('closeDialog',function(e){
                    	infoDialog.close();
	                	Mask.unmaskElement('#circuitWorkOrderWorkAreaReplyDetail');
	                });
                }
            });
            
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
				id : 'workAreaReplyDetailGrid',
				columns : _self._initColumns(),
				store : _self._initGridData(),
				height:'auto',
				loadMask:true,
				useEmptyCell : false,
				elCls : 'workAreaReplyDetailGridClass',
				plugins : [Grid.Plugins.RowNumber],
			});
			var gridBtn = new GridBtn(replyDetailGrid);
			return gridBtn;
		},
		
		_initColumns : function(){
			return [
			        {
			        	title:'工区部门',
			        	dataIndex:'distributeOrgName',
			        	elCls:'center',
			        	width:'40%'
			        },
//			        {
//			        	title:'工区回复人',
//			        	dataIndex:'replyUserName',
//			        	elCls:'center',
//			        	width:'15%'
//			        },
//			        {
//			        	title:'回复时间',
//			        	dataIndex:'replyTime',
//			        	elCls:'center',
//			        	width:'25%',
//			        	renderer:Grid.Format.datetimeRenderer
//			        },
			        {
			        	title:'状态',
			        	dataIndex:'flowState',
			        	elCls:'center',
			        	width:'20%',
			        	renderer : function(e) {
        					if(e == "7"){
        						return "待回复"
        					} else if(e == "7.5"){
        						return "待完成"
        					} else if(e == "8"){
        						return "已完成"
        					} else if(e == "-1"){
        						return "已结束"
        					}
        				}
			        },
					{
						title:'回复详情',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'40%',
                        renderer:function (value) {
                            var button = '<span  class="grid-command replyDetailBtn">查看进度</span>';
                            return button;
                        }
					}]
		},
		
		_initGridData : function(){
			var _self = this;
			var store = new Data.Store({
				url:'/kmms/circuitWorkOrderWorkAreaReplyAction/findAll',
				pageSize:10,
				params:{docId:_self.get('shiftId'),executiveOrgId:_self.get('executiveOrgId'),collectionName : "circuitDistributeWorkOrder"},
				proxy:{
					method:'post',
					dataType:'json'
				},
			});
			_self.set('workAreaReplyStore',store);
			return store;
		},

        /**
         * 获取显示数据
         */
//        _getShowData : function(){
//            var _self = this,shiftId = _self.get('shiftId'),executiveOrgId = _self.get('executiveOrgId');
//            $.ajax({
//                url:'/kmms/circuitWorkOrderWorkAreaReplyAction/findAll',
//                data:{id : shiftId,executiveOrgId,executiveOrgId,collectionName : "circuitIssueWorkOrder"},
//                type:'post',
//                dataType:"json",
//                success:function(result){
//                    var data = result.data;
//                    if(data){
//                        $("#formContainer #workshopReply").val(data.workshopReply);
//                    }
//                }
//            })
//        },
		
    },{
        ATTRS : {
            id:{value : 'circuitWorkOrderWorkAreaReplyDetailDialog'},
            elAttrs:{value: {id:"circuitWorkOrderWorkAreaReplyDetail"}},
            elCls : {value:'circuitWorkOrderWorkAreaReplyDetailDialogClass'},
            title:{value:'工区回复详情'},
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
    return CircuitWorkOrderWorkAreaReplyDetail;
});