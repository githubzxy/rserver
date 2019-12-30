/**
 * 高铁年月报表-段科室编制/执行 '编辑模板'按钮
 */
define("yearMonthPlan/yearMonthPuTie/compile/SegmentPuTieYearMonthEditTempl",[
	'bui/overlay','bui/data',
	'common/grid/GridBtn'
	],function(require){
	var Overlay = require('bui/overlay'),Data = require('bui/data'),
	GridBtn = require('common/grid/GridBtn');
	
	var SegmentPuTieYearMonthEditTempl = Overlay.Dialog.extend({
		initializer : function(){
			var _self=this;	
			_self.addChild(_self._initSearchGrid());
		},
		renderUI : function(){
			var _self = this;	
			//重写 buttons
			_self.set("buttons",[]);
		},
		bindUI : function(){
			var _self = this,editGrid = _self.getChild('editGrid');
			editGrid.on("itemclick",function(e){
				var target = $(e.domTarget);
				var tplPath = _self.get('tplPath');
				//编辑模板
				if(target.hasClass('moudleEdit')){
					var tempv = window.open("kmms/common/loading.html");
					tempv.location.href = '/pageoffice?filePath=' + tplPath + e.item.attachMoudleName + ".xls";
				}
			});
		},
		_initSearchGrid : function(){
			var _self = this;
			var store = new Data.Store({
				url : "/kmms/yearMonthPuTieGQAction/getAttachDataDuan.cn",
				proxy : {
					method : 'post',
					dataType : 'json'
				},
				params : {ids:""},
				autoLoad :true
			});
			var columns = [{
				title : '年月表名称',
				dataIndex : 'reportName',
				elCls : 'center',
				width : '50%'
			},{
				title:'操作',
				elCls : 'center',
				width: '50%',
				renderer:function(){
					return '<span id="moudleEdit" class="grid-command moudleEdit">编辑</span>'; 
				}
			}];
			var editGrid = new GridBtn({
				id : 'editGrid',
				columns : columns,
				store : store,
				width : '100%',
				height: 245,
				paging : false
			});
			return editGrid;
		}
	},{
		ATTRS:{
			title:{value:'编辑年月报表模板'},
			closeAction:{value:"destroy"},
			mask : {value : true},
			tplPath : {},
			width:{value:700},
		}
	});
	return SegmentPuTieYearMonthEditTempl;
});