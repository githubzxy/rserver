/**
 * 普铁年月报表-工区('编辑'/'详情')/车间(子'详情')/段科室(子'详情')执行界面 
 */
define("yearMonthPlan/yearMonthPuTie/execute/WorkAreaPuTieYearMonthEditExecute",[
	'bui/overlay','bui/data',
	'common/data/PostLoad',
	'common/grid/GridBtn'
	],function(require){
	var Overlay = require('bui/overlay'),Data = require('bui/data'),
	PostLoad = require('common/data/PostLoad'),
	GridBtn = require('common/grid/GridBtn');
	
	var WorkAreaPuTieYearMonthEditExecute = Overlay.Dialog.extend({
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
			var _self=this,editGrid = _self.getChild('editGrid');
			_self.get('store').load();
			editGrid.on("itemclick",function(e){
				var target = $(e.domTarget);
				var id = _self.get('id');
				var filePath = _self.get('filePath');
				//填报 填报页面复用段科室（编制）的编辑模块页面
				if(target.hasClass('report')){
					var tempv = window.open("kmms/common/loading.html");
					tempv.location.href = '/pageoffice/yearMonthPlanEditExcelPage?filePath=' + filePath + e.item.filePath
					+ "&id=" + id
					+ "&attachType=" + e.item.attachType
					+ "&afterSavedUrl=/kmms/yearMonthPuTieGQAction/saveFileName";
				}
				//详情 详情页面复用工区（编制）的详情页面
				if(target.hasClass('detail')){
					var tempv = window.open("kmms/common/loading.html");
					tempv.location.href = '/pageoffice/openPage?filePath=' + filePath + e.item.filePath;
				}
			});
		},
		_initSearchGrid : function(){
			var _self = this;
			var status = _self.get('status');
			var store = new Data.Store({
				url : "/kmms/yearMonthPuTieGQExecuteAction/getAttachDataByGQId.cn",
				proxy : {
					method : 'post',
					dataType : 'json'
				},
				params : {
					id:_self.get('id'),
					userString : JSON.stringify(_self.get('user'))
				},
			});
			_self.set('store',store);
			var columns = [{
				title : '年月表名称',
				dataIndex : 'reportName',
				elCls : 'center',
				width : '50%'
			},{
				title:'操作',
				elCls : 'center',
				width: '50%',
				renderer:function(value,obj){
					if(obj.fileName==null){
						return "<span>年月报表编制流程中未找到相应的计划表</span>";
					}
					var btnStr = '<span id="attachDetail" class="grid-command detail">详情</span>';
					switch(status){
					case YearMonthWorkAreaStatus.WRITE_WORKAREA_SEGMENT_PASS:
					case YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL:
					case YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS:
//					case YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_FAIL:
//					case YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_PASS:
						return '<span id="attachReport" class="grid-command report">填报</span>&nbsp;&nbsp;'+btnStr;
					default:
						return btnStr
					}
				}
			}];
			var editGrid = new GridBtn({
				id : 'editGrid',
				columns : columns,
				store : store,
				width : '100%',
				height: 88,
				paging : false,
				loadMask : true
			});
			return editGrid;
		}
	},{
		ATTRS:{
			closeAction:{value:"destroy"},
			id : {},
			status : {},
			title:{},
			filePath:{},
			user :{},
			width:{value:700},
			elAttrs : {value: {id : 'WorkAreaPuTieYearMonthEditExecute'}},
			mask : {value : true},//非模态弹出框
		}
	});
	return WorkAreaPuTieYearMonthEditExecute;
});