/**
 * 普铁年月报表-车间执行界面 车间汇总/详情
 */
define("yearMonthPlan/yearMonthPuTie/execute/WorkShopPuTieYearMonthAttachExecute",[
	'bui/overlay','bui/data',
	'common/data/PostLoad',
	'common/grid/GridBtn'
	],function(require){
	var Overlay = require('bui/overlay'),Data = require('bui/data'),
	PostLoad = require('common/data/PostLoad'),
	GridBtn = require('common/grid/GridBtn');
	
	var WorkShopPuTieYearMonthAttachExecute = Overlay.Dialog.extend({
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
				var filePath = _self.get('filePath');
				//详情
				if(target.hasClass('detail')){
					var tempv = window.open("kmms/common/loading.html");
					tempv.location.href = '/pageoffice/openPage?filePath=' + filePath + e.item.filePath;
				}
				//车间汇总执行表
				if(target.hasClass('workShopGather')){
					var postLoad = new PostLoad({
						url:"/kmms/yearMonthPutieAnysisAction/anysisWorkShopExecute.cn",
						el:_self.get('el')
					});
					postLoad.load({
						attachPath:e.item.attachType,
						id : id,
						userString : JSON.stringify(_self.get('user')),
					},function(result){
						if(result.status=='1'){
							var tempv = window.open("kmms/common/loading.html");
							tempv.location.href = '/pageoffice/openPage?filePath=' + filePath + result.data;
						}
					});
				}
			});
		},
		_initSearchGrid : function(){
			var _self = this;
			var isWorkShopGather = _self.get('isWorkShopGather');
			var store = new Data.Store({
				url : "/kmms/yearMonthPutieCJExecuteAction/getAttachDataByCJId.cn",
				proxy : {
					method : 'post',
					dataType : 'json'
				},
				params : {id:_self.get('id')},
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
				dataIndex : 'filePath',
				width: '50%',
				renderer:function(value,obj){
					if(obj.hasReport==0){
						return '<span>年月报表编制流程中未找到相应的计划表</span>';
					}
					if(isWorkShopGather){
						return '<span id="workShopGather" class="grid-command workShopGather">汇总</span>';
					}
					if(obj.filePath==null){
						return '<span>无报表数据可查阅</span>';
					}
					return '<span id="attachDetail" class="grid-command detail">详情</span>';
				},
			}];
			var editGrid = new GridBtn({
				id : 'editGrid',
				columns : columns,
				store : store,
				width : '100%',
				height: 88,
				paging : false
			});
			return editGrid;
		}
	},{
		ATTRS:{
			closeAction:{value:"destroy"},
			id : {},//车间业务数据id
			title:{},
			filePath:{},
			user:{},
			width:{value:700},
			isWorkShopGather:{value:false},//是否是车间汇总弹出框
			elAttrs : {value: {id : 'WorkShopPuTieYearMonthAttachExecute'}},
			mask : {value : true},//非模态弹出框
		}
	});
	return WorkShopPuTieYearMonthAttachExecute;
});