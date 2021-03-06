/**
 * 普铁年月报表-工区执行界面 主页模块，包含上查询下列表。
 */
seajs.use('yearMonthPlan/common/commonTypeMap.js');
define('yearMonthPlan/yearMonthPuTie/execute/WorkAreaPuTieYearMonthExecute',[
	'bui/common','bui/data','bui/grid',
	'common/data/PostLoad',
	'common/date/YearPicker',
	'common/grid/SearchGridContainer',
	'yearMonthPlan/yearMonthPuTie/execute/WorkAreaPuTieYearMonthEditExecute',
	],function(require){
	var BUI = require('bui/common'), Component = BUI.Component,
	Data = require('bui/data'),Grid = require('bui/grid'),
	PostLoad = require('common/data/PostLoad'),
	YearPicker = require('common/date/YearPicker'),
	SearchGridContainer = require('common/grid/SearchGridContainer'),
	WorkAreaPuTieYearMonthEditExecute = require('yearMonthPlan/yearMonthPuTie/execute/WorkAreaPuTieYearMonthEditExecute');
	
	var WorkAreaPuTieYearMonthExecute = Component.Controller.extend({
		initializer : function() {
			var _self = this;
			var searchGridContainer = new SearchGridContainer({
				searchForm : _self._initSearchForm(),
				columns : _self._initColumns(),
				store : _self._initGridStore(),
				searchGrid : _self._initSearchGrid(),
		    });
			_self.addChild(searchGridContainer);
		},
		renderUI : function(){
			var _self = this;
			// 初始化年份控件
			_self._initYearPicker();				
		},
		/**
		 *  初始化年份控件
		 */
		_initYearPicker : function(){
			var yearPicker = new YearPicker({
				trigger : '#reportYearId',
				autoHide : true,
				align : {
					points:['bl','tl']
				},
				success:function(){
					var year = this.get('year');
					$('#reportYearId').val(year);
					this.hide();
				},
				cancel:function(){
					this.hide();
				},
			});
			yearPicker.render();
		},
		bindUI : function() {
			var _self = this;
			var store = _self.get('store');
			var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
			var tbar = table.get('tbar');
			
			table.on("itemclick",function(e){
				var target = $(e.domTarget);
				//'编辑' 链接事件
				if(target.hasClass('edit')){
					var edit = new WorkAreaPuTieYearMonthEditExecute({
						title:'工区年月报表完成情况统计以及查看',
						id : e.item.id,
						status : e.item.status,
						filePath : _self.get('filePath'),
						user : _self.get('user'),
						listeners : {
							closing : function(){//关闭窗口时刷新列表
								store.load();
							}
						},
					});
					edit.show();
				}
				//'上报' 链接事件
				if(target.hasClass('submit')){
		    		BUI.Message.Confirm('确定上报此年月表吗?<br/>',function(){
		    			var postLoad=new PostLoad({
		    				el:_self.get('el'),
		    				url:'/kmms/yearMonthPuTieGQExecuteAction/reportToWorkShop.cn',
		    				loadMsg:'上报中...'
		    			});
		    			postLoad.load({
	    					id:e.item.id,
			    			name:e.item.name,
			    			year:e.item.year,
	    				},function(result){
		    				if(result.status=='1'){
		    					tbar.msg({msg:result.msg,status:result.status});
							}else{
								tbar.msg({msg:result.msg});
							}
		    				store.load();
		    			});
		    		},'question');
				}
			});
		},
		/**
		 * 上查询模块
		 */
		_initSearchForm : function() {
			var _self = this;
  			return {
  				items : [{
	  				label : '名称',
	  				item : '<input name="name" type="text" id="reportNameId"/>'
	  			},{
	  				label : '年份',
	  				item : '<input name="year" type="text" id="reportYearId" readonly="readonly"/>'
	  			},{
	  				label : '状态',
	  				item : '<select name="status" id="reportStatusId">' +
	  							'<option value="">请选择</option>'+   
			  					'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKAREA_SEGMENT_PASS+'">'+YearMonthWorkAreaStatus.WRITE_WORKAREA_SEGMENT_PASS_NAME+'</option>'  + 
								'<option value="'+YearMonthWorkAreaStatus.WORKAREA_EXECUTE_REPORT+'">'+YearMonthWorkAreaStatus.WORKAREA_EXECUTE_REPORT_NAME+'</option>' 		+
								'<option value="'+YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS+'">'+YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS_NAME+'</option>'       +
								'<option value="'+YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL+'">'+YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL_NAME+'</option>' 		+ 
//								'<option value="'+YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_FAIL+'">'+YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_FAIL_NAME+'</option>'	+ 
//								'<option value="'+YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_PASS+'">'+YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_PASS_NAME+'</option>'  + 
	  						'</select>'
	  			}]
  			};
		},
		/**
		 * 下列表表头模块
		 */
		_initColumns : function(){
			var _self = this;
			/*表格列设置*/
			var columns = [{
				title : '年月表名称',
				dataIndex : 'name',
				elCls : 'center',
				width : '50%'
			},{
				title:'年份',
				dataIndex:'year',
				elCls : 'center',
				width: '80'
			},{
				title : '所属部门',
				dataIndex : 'orgName',
				elCls : 'center',
				width : '150'
			},{
				title:'创建时间',
				dataIndex:'createTime',
				elCls : 'center',
				renderer:Grid.Format.datetimeRenderer,
				width: '80'
			},{
				title:'填报人',
				dataIndex:'userName',
				elCls : 'center',
				width: '150'
			},{
				title:'上报时间',
				dataIndex:'reportTime',
				elCls : 'center',
				renderer:Grid.Format.datetimeRenderer,
				width: '80'
			},{
				title : '退回原因',
				dataIndex : 'failReason',
				elCls : 'center',
				width : '200'
			},{
				title : '审核状态',
				dataIndex : 'status',
				elCls : 'center',
				width : '150',
				renderer :function(value){
					return YearMonthWorkAreaStatus.statusName(value)
				} 
			},{
				title:'操作',
				elCls : 'center',
				width: '150',
				dataIndex : 'status',
				renderer:function(value,obj){
					//判断执行表是否存在(当执行表存在时才显示‘上报’按钮)
					var reportBtn =obj.attachPathExecute8_1?'&nbsp;&nbsp;<span id="submitId" class="grid-command submit">上报</span>':'';
					switch(value){
						case YearMonthWorkAreaStatus.WRITE_WORKAREA_SEGMENT_PASS:
						case YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_FAIL:
//						case YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_FAIL:
//						case YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_PASS:
						case YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS:
							return '<span id="editId" class="grid-command edit">编辑</span>'
							 	   +reportBtn;
						default:
							return '<span id="editId" class="grid-command edit">详情</span>';
					}
				},
			}];
			return columns;
		},
		/**
		 * 获取下列表数据函数
		 */
		_initGridStore : function(){
			var _self = this;
			var store = new Data.Store({
				url : "/kmms/yearMonthPuTieGQExecuteAction/queryAllData.cn",
				params : {
					name : '',
					year : '',
					status : '',
					userString : JSON.stringify(_self.get('user')),
				},
				proxy : {
					method : 'post',
					dataType : 'json'
				},
				pageSize : 10,
				autoLoad :true
			});
			_self.set('store',store);
			return store;
		},
		/**
		 * 下查询表格上方按钮函数
		 */
		_initSearchGrid : function(){
			var _self = this;
			var searchGrid = {
				tbarItems :	[],
		        plugins : [
		        	Grid.Plugins.CheckSelection,//多选框插件
					Grid.Plugins.RowNumber//列序号插件
				],
			};
			return searchGrid;
		},
	}, {
		ATTRS : {
			filePath : {},
			user : {}
		}
	})
	return WorkAreaPuTieYearMonthExecute;
});