/**
 * 工区回复详情界面
 * @author yangsy
 * @date 19-8-21
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderWorkAreaDistributeReplyDetail',
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
    var CircuitWorkOrderWorkAreaDistributeReplyDetail = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
//            _self.addChild(_self._initFormContainer());
            _self.addChild(_self._initGridContainer());
        },
        renderUI:function(){
			var _self=this;
			$("#circuitWorkOrderWorkAreaDistributeReplyDetail .bui-ext-close").css("display","none");
			$('.circuitWorkOrderWorkAreaDistributeReplyDetailDialogClass .bui-stdmod-body').attr("style","overflow-y:hidden;overflow-x:hidden;");
			$('.circuitWorkOrderWorkAreaDistributeReplyDetailDialogClass .bui-grid .bui-grid-body').attr("style","overflow-y:scroll;overflow-x:auto;height:300px;");
			var store = _self.get('workAreaDistributeReplyStore');
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
				id : 'workAreaDistributeReplyDetailGrid',
				columns : _self._initColumns(),
				store : _self._initGridData(),
				height:'auto',
				loadMask:true,
				useEmptyCell : false,
				elCls : 'workAreaDistributeReplyDetailGridClass',
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
			        	width:'15%'
			        },
			        {
			        	title:'工区回复人',
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
                        dataIndex:'workAreaReply',
                        elCls : 'center',
                        width:'45%',
					}]
		},
		
		_initGridData : function(){
			var _self = this;
			var store = new Data.Store({
				url:'/kmms/circuitWorkOrderWorkAreaReplyAction/findDistributeReplyAll',
				pageSize:10,
				params:{docId:_self.get('shiftId'),distributeOrgId:_self.get('distributeOrgId'),collectionName : "circuitDistributeWorkOrderReply"},
				proxy:{
					method:'post',
					dataType:'json'
				},
			});
			_self.set('workAreaDistributeReplyStore',store);
			return store;
		},
        
    },{
        ATTRS : {
            id:{value : 'circuitWorkOrderWorkAreaDistributeReplyDetailDialog'},
            elAttrs:{value: {id:"circuitWorkOrderWorkAreaDistributeReplyDetail"}},
            elCls : {value:'circuitWorkOrderWorkAreaDistributeReplyDetailDialogClass'},
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
            distributeOrgId:{},
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
    return CircuitWorkOrderWorkAreaDistributeReplyDetail;
});