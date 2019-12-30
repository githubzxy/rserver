/**
 * 普铁年月报表-工区('编辑'/'详情')/车间(子'详情')/段科室(子'详情')编制界面
 */
define("yearMonthPlan/yearMonthPuTie/compile/WorkAreaPuTieYearMonthEdit",[
	'bui/overlay','bui/data',
	'common/grid/GridBtn'
	],function(require){
	var Overlay = require('bui/overlay'),Data = require('bui/data'),
	GridBtn = require('common/grid/GridBtn');
	
	var WorkAreaPuTieYearMonthEdit = Overlay.Dialog.extend({
		initializer:function(){
			var _self=this;	
			_self.addChild(_self._initSearchGrid());
		},
		renderUI:function(){
			var _self=this;	
			//重写 buttons
			_self.set("buttons",[]);
		},
		bindUI : function(){
			var _self=this,editGrid = _self.getChild('editGrid'),store=_self.get('store');
			store.load();
			$('.updateReportStatus').on('click',function(){
				store.load();
			});
			editGrid.on("itemclick",function(e){
				var target = $(e.domTarget);
				var id = _self.get('id');
				var filePath = _self.get('filePath');
				//填报
				if(target.hasClass('report')){
					var tempv = window.open("kmms/common/loading.html");
					tempv.location.href = '/pageoffice/yearMonthPlanEditExcelPage?filePath=' + filePath + e.item.filePath
					+ "&id=" + id
					+ "&attachType=" + e.item.attachName
					+ "&afterSavedUrl=/kmms/yearMonthPuTieGQAction/saveFileName";
				}
				//详情
				if(target.hasClass('detail')){
					var tempv = window.open("kmms/common/loading.html");
					tempv.location.href = '/pageoffice/openPage?filePath=' + filePath +e.item.filePath;
				}
			});
		},
		_initSearchGrid : function(){
			var _self = this;
			var status = _self.get('status');
			var store = new Data.Store({
				url : "/kmms/yearMonthPuTieGQAction/getAttachDataByGQId.cn",
				proxy : {
					method : 'post',
					dataType : 'json'
				},
				params : {id:_self.get('id')},
			});
			_self.set('store',store);
			var columns = [{
				title : '年月表名称',
				dataIndex : 'reportName',
				elCls : 'center',
				width : '50%'
			},{
				title:'填报状态',
				dataIndex : 'fileName',
				elCls : 'center',
				width: '20%',
				renderer:function(value,o){
					if(value!=null){
						return "已填报"
					}else{
						return "未填报";
					}
				},
			},{
				title:'操作',
				elCls : 'center',
				dataIndex : 'fileName',
				width: '30%',
				renderer:function(value,obj){
					var btnStr = "";
					if(value!=null){
						btnStr = '<span id="attachDetail" class="grid-command detail">详情</span>';
					}
					switch(status){
						case YearMonthWorkAreaStatus.WRITE_WORKAREA_UNLOCK:
						case YearMonthWorkAreaStatus.WRITE_WORKAREA_DRAFT:
						case YearMonthWorkAreaStatus.WRITE_WORKAREA_WORKSHOP_FAIL:
						case YearMonthWorkAreaStatus.WRITE_WORKAREA_SEGMENT_FAIL:
							return '<span id="attachReport" class="grid-command report">填报</span>&nbsp;&nbsp;'+btnStr;
						default:
							return btnStr;
					}
				}
			}];
			var editGrid = new GridBtn({
				id : 'editGrid',
				columns : columns,
				store : store,
				width : '100%',
				height: 163,
				paging : false,
				loadMask : true,
				tbar:{ 
					items : [{
						btnCls : 'button button-small updateReportStatus',
						text : '<i class="icon-refresh"></i>更新填报状态'
					}]
				}
			});
			return editGrid;
		}
	},{
		ATTRS:{
			closeAction:{value:"destroy"},
			id : {},
			title:{},
			status : {},
			filePath : {},
			width:{value:700},
			elAttrs : {value: {id : 'WorkAreaPuTieYearMonthEdit'}},
			mask : {value : true},//非模态弹出框
		}		
	});
	return WorkAreaPuTieYearMonthEdit;
});