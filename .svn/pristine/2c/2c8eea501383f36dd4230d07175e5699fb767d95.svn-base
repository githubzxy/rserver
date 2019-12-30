/**
 * 普铁年月报表-段科室编制/执行界面 '批量导出'按钮
 */
define("yearMonthPlan/yearMonthPuTie/compile/SegmentPuTieYearMonthExport",[
	'bui/overlay','bui/data',
	'common/data/PostLoad',
	'common/grid/GridBtn'
	],function(require){
	var Overlay = require('bui/overlay'),Data = require('bui/data'),
	PostLoad = require('common/data/PostLoad'),
	GridBtn = require('common/grid/GridBtn');
	
	var SegmentPuTieYearMonthExport = Overlay.Dialog.extend({
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
			editGrid.on("itemclick",function(e){
				var target = $(e.domTarget);
				var id = _self.get('id');
				//导出
				if(target.hasClass('export')){
					var postLoad = new PostLoad({
						el:_self.get('el'),
						url:"/kmms/yearMonthPutieAnysisAction/zipToFileSegment.cn",
					});
					postLoad.load({attachPath:e.item.attachType,ids : id},function(result){
						if(result.status=='1'){
							window.location.href = '/kmms/DownloadFileAction/expYearMonthPlan?fileName='+encodeURI(e.item.reportName)+'&filePath='+result.data;
						}
					});
				}
			});
		},				
		_initSearchGrid : function(){
			var _self = this;
			var store = new Data.Store({
				url : _self.get('url'),
				proxy : {
					method : 'post',
					dataType : 'json'
				},
				params : {ids:_self.get('id')},
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
				dataIndex : 'segmentGather',
				width: '50%',
				renderer:function(value){
					if(value){
						return '<span id="attachExport" class="grid-command export">导出</span>'; 
					}else{
						return '<span>没有报表数据可以导出</span>';
					}
				}
			}];
			var editGrid = new GridBtn({
				id : 'editGrid',
				columns : columns,
				store : store,
				width : '100%',
				height: 163,
				paging : false
			});
			return editGrid;
		}
	},{
		ATTRS:{
			closeAction:{value:"destroy"},
			id : {},
			url : {},
			title:{value:'选择导出数据'},
			width:{value:700},
			elAttrs : {value: {id : 'SegmentPuTieYearMonthExport'}},
			mask : {value : true},//非模态弹出框
		}		
	});
	return SegmentPuTieYearMonthExport;
});